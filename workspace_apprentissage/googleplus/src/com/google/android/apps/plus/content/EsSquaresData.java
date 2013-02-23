package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.util.StringUtils;
import com.google.api.services.plusi.model.DataUser;
import com.google.api.services.plusi.model.InvitedSquare;
import com.google.api.services.plusi.model.JoinedSquare;
import com.google.api.services.plusi.model.Square;
import com.google.api.services.plusi.model.SquareMember;
import com.google.api.services.plusi.model.SquareNotificationOptions;
import com.google.api.services.plusi.model.SquareProfile;
import com.google.api.services.plusi.model.SquareStream;
import com.google.api.services.plusi.model.SquareVisibility;
import com.google.api.services.plusi.model.SuggestedSquare;
import com.google.api.services.plusi.model.ViewerSquare;
import com.google.api.services.plusi.model.ViewerSquareCalculatedMembershipProperties;
import com.google.api.services.plusi.model.ViewerSquareJson;
import com.google.api.services.plusi.model.ViewerSquareSquareActivityStats;
import com.google.api.services.plusi.model.ViewerSquareSquareMemberStats;
import com.google.api.services.plusi.model.ViewerSquareStreamList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public final class EsSquaresData
{
  public static final String[] SQUARES_PROJECTION = { "square_id", "square_name", "tagline", "photo_url", "about_text", "joinability", "member_count", "membership_status", "is_member", "suggested", "post_visibility", "can_see_members", "can_see_posts", "can_join", "can_request_to_join", "can_share", "can_invite", "notifications_enabled", "square_streams", "sort_index", "inviter_gaia_id", "last_sync", "last_members_sync", "auto_subscribe", "disable_subscription", "unread_count" };
  private static final String[] SQUARE_MEMBERS_PROJECTION = { "link_person_id", "membership_status" };
  private static final String[] UPDATE_SQUARE_MEMBERSHIP_PROJECTION = { "post_visibility", "joinability", "square_streams" };

  static void cleanupData$3105fef4(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.delete("squares", "is_member=0 AND membership_status NOT IN (4,5)", null);
  }

  private static void deleteSquareStreams(Context paramContext, EsAccount paramEsAccount, String paramString, DbSquareStream[] paramArrayOfDbSquareStream)
  {
    if (paramArrayOfDbSquareStream != null)
    {
      int i = 0;
      int j = paramArrayOfDbSquareStream.length;
      while (i < j)
      {
        EsPostsData.deleteActivityStream(paramContext, paramEsAccount, EsPostsData.buildSquareStreamKey(paramString, paramArrayOfDbSquareStream[i].getStreamId(), false));
        i++;
      }
    }
    EsPostsData.deleteActivityStream(paramContext, paramEsAccount, EsPostsData.buildSquareStreamKey(paramString, null, false));
  }

  public static void dismissSquareInvitation(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("invitation_dismissed", Integer.valueOf(1));
    if (localSQLiteDatabase.update("squares", localContentValues, "square_id=?", new String[] { paramString }) > 0)
      paramContext.getContentResolver().notifyChange(EsProvider.SQUARES_URI, null);
  }

  public static Cursor getInvitedSquares(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString)
  {
    Uri localUri = EsProvider.appendAccountParameter(EsProvider.SQUARES_URI, paramEsAccount);
    ContentResolver localContentResolver = paramContext.getContentResolver();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(5);
    return localContentResolver.query(localUri, paramArrayOfString, "membership_status=? AND is_member=0 AND invitation_dismissed=0", arrayOfString, null);
  }

  private static int getJoinability(String paramString)
  {
    int i;
    if ("ANYONE".equals(paramString))
      i = 0;
    while (true)
    {
      return i;
      if ("REQUIRES_APPROVAL".equals(paramString))
        i = 1;
      else if ("REQUIRES_INVITE".equals(paramString))
        i = 2;
      else
        i = -1;
    }
  }

  public static Cursor getJoinedSquares(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString)
  {
    Uri localUri = EsProvider.appendAccountParameter(EsProvider.SQUARES_URI, paramEsAccount);
    return paramContext.getContentResolver().query(localUri, paramArrayOfString, "is_member!=0", null, paramString);
  }

  private static int getMembershipStatus(String paramString)
  {
    int i;
    if ("NONE".equals(paramString))
      i = 0;
    while (true)
    {
      return i;
      if ("OWNER".equals(paramString))
        i = 1;
      else if ("MODERATOR".equals(paramString))
        i = 2;
      else if ("MEMBER".equals(paramString))
        i = 3;
      else if ("PENDING".equals(paramString))
        i = 4;
      else if ("INVITED".equals(paramString))
        i = 5;
      else if ("BANNED".equals(paramString))
        i = 6;
      else if ("IGNORED".equals(paramString))
        i = 7;
      else
        i = -1;
    }
  }

  public static Cursor getSuggestedSquares(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString)
  {
    Uri localUri = EsProvider.appendAccountParameter(EsProvider.SQUARES_URI, paramEsAccount);
    return paramContext.getContentResolver().query(localUri, paramArrayOfString, "suggested!=0 AND is_member=0", null, "suggestion_sort_index");
  }

  private static int getVisibility(String paramString)
  {
    int i;
    if ("PUBLIC".equals(paramString))
      i = 0;
    while (true)
    {
      return i;
      if ("MEMBERS_ONLY".equals(paramString))
        i = 1;
      else
        i = -1;
    }
  }

  private static boolean hasSquareChanged(Cursor paramCursor, ViewerSquare paramViewerSquare)
  {
    String str1 = paramCursor.getString(1);
    String str2 = paramCursor.getString(2);
    String str3 = paramCursor.getString(3);
    String str4 = paramCursor.getString(4);
    int i = paramCursor.getInt(5);
    int j = paramCursor.getInt(6);
    int k = paramCursor.getInt(7);
    int m = paramCursor.getInt(10);
    int n = paramCursor.getInt(25);
    int i1;
    int i2;
    label111: int i3;
    label125: int i4;
    label139: int i5;
    label153: int i6;
    label167: int i7;
    label181: boolean bool1;
    label195: int i8;
    label209: int i9;
    label223: DbSquareStream[] arrayOfDbSquareStream;
    boolean bool2;
    if (paramCursor.getInt(8) != 0)
    {
      i1 = 1;
      if (paramCursor.getInt(11) == 0)
        break label334;
      i2 = 1;
      if (paramCursor.getInt(12) == 0)
        break label340;
      i3 = 1;
      if (paramCursor.getInt(13) == 0)
        break label346;
      i4 = 1;
      if (paramCursor.getInt(14) == 0)
        break label352;
      i5 = 1;
      if (paramCursor.getInt(15) == 0)
        break label358;
      i6 = 1;
      if (paramCursor.getInt(16) == 0)
        break label364;
      i7 = 1;
      if (paramCursor.getInt(17) == 0)
        break label370;
      bool1 = true;
      if (paramCursor.getInt(23) == 0)
        break label376;
      i8 = 1;
      if (paramCursor.getInt(24) == 0)
        break label382;
      i9 = 1;
      arrayOfDbSquareStream = DbSquareStream.deserialize(paramCursor.getBlob(18));
      SquareProfile localSquareProfile = paramViewerSquare.square.profile;
      if ((StringUtils.equals(str1, localSquareProfile.name)) && (StringUtils.equals(str2, localSquareProfile.tagline)) && (StringUtils.equals(str3, localSquareProfile.photoUrl)) && (StringUtils.equals(str4, localSquareProfile.aboutText)) && (k == getMembershipStatus(paramViewerSquare.viewerMembershipStatus)) && (i == getJoinability(paramViewerSquare.square.joinability)))
        break label388;
      bool2 = true;
    }
    while (true)
    {
      return bool2;
      i1 = 0;
      break;
      label334: i2 = 0;
      break label111;
      label340: i3 = 0;
      break label125;
      label346: i4 = 0;
      break label139;
      label352: i5 = 0;
      break label153;
      label358: i6 = 0;
      break label167;
      label364: i7 = 0;
      break label181;
      label370: bool1 = false;
      break label195;
      label376: i8 = 0;
      break label209;
      label382: i9 = 0;
      break label223;
      label388: ViewerSquareCalculatedMembershipProperties localViewerSquareCalculatedMembershipProperties = paramViewerSquare.calculatedMembershipProperties;
      if ((localViewerSquareCalculatedMembershipProperties != null) && ((i1 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.isMember)) || (i2 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canSeeMemberList)) || (i3 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canSeePosts)) || (i4 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canJoin)) || (i5 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canRequestToJoin)) || (i6 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canShareSquare)) || (i7 != PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canInviteToSquare))))
      {
        bool2 = true;
      }
      else if ((paramViewerSquare.square.visibility != null) && (m != getVisibility(paramViewerSquare.square.visibility.posts)))
      {
        bool2 = true;
      }
      else if (!TextUtils.isEmpty(paramViewerSquare.viewerNotificationSettings))
      {
        boolean bool3 = "ENABLED".equals(paramViewerSquare.viewerNotificationSettings);
        if (bool1 != bool3)
          bool2 = true;
      }
      else if ((paramViewerSquare.squareMemberStats != null) && (j != PrimitiveUtils.safeInt(paramViewerSquare.squareMemberStats.memberCount)))
      {
        bool2 = true;
      }
      else
      {
        if (paramViewerSquare.streams != null)
        {
          ViewerSquareStreamList localViewerSquareStreamList = paramViewerSquare.streams;
          int i10;
          if ((localViewerSquareStreamList == null) || (localViewerSquareStreamList.squareStream == null) || (localViewerSquareStreamList.squareStream.size() == 0))
            if ((arrayOfDbSquareStream == null) || (arrayOfDbSquareStream.length == 0))
              i10 = 1;
          while (true)
          {
            if (i10 != 0)
              break label815;
            bool2 = true;
            break;
            i10 = 0;
            continue;
            if (arrayOfDbSquareStream == null)
            {
              i10 = 0;
            }
            else
            {
              List localList = localViewerSquareStreamList.squareStream;
              if (localList.size() != arrayOfDbSquareStream.length)
              {
                i10 = 0;
              }
              else
              {
                int i11 = arrayOfDbSquareStream.length;
                int i12 = 0;
                int i13 = 0;
                while (true)
                {
                  if (i13 >= i11)
                    break label809;
                  DbSquareStream localDbSquareStream = arrayOfDbSquareStream[i13];
                  int i14 = i12 + 1;
                  SquareStream localSquareStream = (SquareStream)localList.get(i12);
                  if ((!StringUtils.equals(localDbSquareStream.getStreamId(), localSquareStream.id)) || (!StringUtils.equals(localDbSquareStream.getName(), localSquareStream.name)) || (!StringUtils.equals(localDbSquareStream.getDescription(), localSquareStream.description)))
                  {
                    i10 = 0;
                    break;
                  }
                  i13++;
                  i12 = i14;
                }
                label809: i10 = 1;
              }
            }
          }
        }
        label815: SquareNotificationOptions localSquareNotificationOptions = paramViewerSquare.squareNotificationOptions;
        if ((localSquareNotificationOptions != null) && ((i8 != PrimitiveUtils.safeBoolean(localSquareNotificationOptions.autoSubscribeOnJoin)) || (i9 != PrimitiveUtils.safeBoolean(localSquareNotificationOptions.disableSubscription))))
        {
          bool2 = true;
        }
        else
        {
          ViewerSquareSquareActivityStats localViewerSquareSquareActivityStats = paramViewerSquare.squareActivityStats;
          if ((localViewerSquareSquareActivityStats != null) && (n != PrimitiveUtils.safeInt(localViewerSquareSquareActivityStats.unreadPostCount)))
            bool2 = true;
          else
            bool2 = false;
        }
      }
    }
  }

  public static boolean insertSquare(Context paramContext, EsAccount paramEsAccount, ViewerSquare paramViewerSquare)
    throws IOException
  {
    boolean bool2;
    if (!validateSquare(paramViewerSquare))
    {
      bool2 = false;
      return bool2;
    }
    long l = System.currentTimeMillis();
    String str = paramViewerSquare.square.obfuscatedGaiaId;
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      try
      {
        while (true)
        {
          Cursor localCursor = localSQLiteDatabase.query("squares", SQUARES_PROJECTION, "square_id=?", new String[] { str }, null, null, null);
          try
          {
            boolean bool1 = localCursor.moveToNext();
            bool2 = false;
            Object localObject3 = null;
            if (bool1)
            {
              if (!hasSquareChanged(localCursor, paramViewerSquare))
                break label243;
              localObject3 = toContentValues(paramViewerSquare);
              bool2 = true;
              if (EsLog.isLoggable("EsSquaresData", 3))
                Log.d("EsSquaresData", "Update square: id=" + str + " name=" + paramViewerSquare.square.profile.name);
            }
            while (true)
            {
              ((ContentValues)localObject3).put("last_sync", Long.valueOf(l));
              ((ContentValues)localObject3).put("unread_count", Integer.valueOf(0));
              localCursor.close();
              if (localObject3 == null)
                break label284;
              localSQLiteDatabase.update("squares", (ContentValues)localObject3, "square_id=?", new String[] { str });
              localSQLiteDatabase.setTransactionSuccessful();
              localSQLiteDatabase.endTransaction();
              if (!bool2)
                break;
              paramContext.getContentResolver().notifyChange(EsProvider.SQUARES_URI, null);
              break;
              label243: ContentValues localContentValues2 = new ContentValues();
              localObject3 = localContentValues2;
              bool2 = false;
            }
          }
          finally
          {
            localCursor.close();
          }
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      label284: ContentValues localContentValues1 = toContentValues(paramViewerSquare);
      localContentValues1.put("last_sync", Long.valueOf(l));
      localSQLiteDatabase.insert("squares", null, localContentValues1);
      bool2 = true;
      if (EsLog.isLoggable("EsSquaresData", 3))
        Log.d("EsSquaresData", "Insert square: id=" + str + " name=" + paramViewerSquare.square.profile.name);
    }
  }

  public static int insertSquares(Context paramContext, EsAccount paramEsAccount, List<InvitedSquare> paramList, List<JoinedSquare> paramList1, List<SuggestedSquare> paramList2)
    throws IOException
  {
    HashMap localHashMap1 = new HashMap();
    int i = 0;
    int j = paramList.size();
    if (i < j)
    {
      InvitedSquare localInvitedSquare2 = (InvitedSquare)paramList.get(i);
      if ((localInvitedSquare2.inviter != null) && (localInvitedSquare2.inviter.size() > 0));
      for (SquareMember localSquareMember2 = (SquareMember)localInvitedSquare2.inviter.get(0); ; localSquareMember2 = null)
      {
        if (validateSquare(localInvitedSquare2.viewerSquare))
          localHashMap1.put(localInvitedSquare2.viewerSquare.square.obfuscatedGaiaId, new SquareData(localInvitedSquare2.viewerSquare, false, localSquareMember2, i, 0));
        i++;
        break;
      }
    }
    int k = 0;
    int m = paramList2.size();
    if (k < m)
    {
      SuggestedSquare localSuggestedSquare = (SuggestedSquare)paramList2.get(k);
      SquareData localSquareData4;
      if (validateSquare(localSuggestedSquare.viewerSquare))
      {
        if (!localHashMap1.containsKey(localSuggestedSquare.viewerSquare.square.obfuscatedGaiaId))
          break label271;
        localSquareData4 = (SquareData)localHashMap1.get(localSuggestedSquare.viewerSquare.square.obfuscatedGaiaId);
      }
      label271: for (SquareData localSquareData3 = new SquareData(localSquareData4.viewerSquare, true, localSquareData4.inviter, localSquareData4.sortIndex, k); ; localSquareData3 = new SquareData(localSuggestedSquare.viewerSquare, true, null, 0, k))
      {
        localHashMap1.put(localSuggestedSquare.viewerSquare.square.obfuscatedGaiaId, localSquareData3);
        k++;
        break;
      }
    }
    int n = 0;
    int i1 = paramList1.size();
    while (n < i1)
    {
      JoinedSquare localJoinedSquare = (JoinedSquare)paramList1.get(n);
      if (validateSquare(localJoinedSquare.viewerSquare))
        localHashMap1.put(localJoinedSquare.viewerSquare.square.obfuscatedGaiaId, new SquareData(localJoinedSquare.viewerSquare, false, null, n, 0));
      n++;
    }
    long l = System.currentTimeMillis();
    SQLiteDatabase localSQLiteDatabase1 = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase1.beginTransaction();
    HashMap localHashMap2;
    ArrayList localArrayList1;
    Cursor localCursor;
    String str2;
    SquareData localSquareData2;
    try
    {
      localHashMap2 = new HashMap();
      localArrayList1 = new ArrayList();
      localCursor = localSQLiteDatabase1.query("squares", SQUARES_PROJECTION, "is_member!=0 OR suggested!=0 OR membership_status IN (5,4)", null, null, null, null);
      try
      {
        while (true)
        {
          if (!localCursor.moveToNext())
            break label697;
          str2 = localCursor.getString(0);
          localSquareData2 = (SquareData)localHashMap1.remove(str2);
          if (localSquareData2 != null)
            break;
          localArrayList1.add(str2);
        }
      }
      finally
      {
        localCursor.close();
      }
    }
    finally
    {
      localSQLiteDatabase1.endTransaction();
    }
    int i6;
    if (hasSquareChanged(localCursor, localSquareData2.viewerSquare))
      i6 = 1;
    while (true)
    {
      ContentValues localContentValues2;
      if (i6 != 0)
      {
        localContentValues2 = toContentValues(localSquareData2);
        if (EsLog.isLoggable("EsSquaresData", 3))
          Log.d("EsSquaresData", "Update square: id=" + str2 + " name=" + localContentValues2.getAsString("square_name"));
        label581: localHashMap2.put(str2, localContentValues2);
        break;
        if (localCursor.getInt(9) == 0)
          break label1301;
      }
      label1295: label1301: for (int i5 = 1; ; i5 = 0)
      {
        String str3 = localCursor.getString(20);
        if (i5 == localSquareData2.suggested)
        {
          if (TextUtils.equals(str3, localSquareData2.getInviterGaiaId()))
            break label1307;
          break label1295;
          localContentValues2 = new ContentValues(2);
          Integer localInteger1 = Integer.valueOf(localSquareData2.sortIndex);
          localContentValues2.put("sort_index", localInteger1);
          Integer localInteger2 = Integer.valueOf(localSquareData2.suggestionSortIndex);
          localContentValues2.put("suggestion_sort_index", localInteger2);
          break label581;
          label697: localCursor.close();
          boolean bool = localArrayList1.isEmpty();
          int i2 = 0;
          if (!bool)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            int i3 = localArrayList1.size();
            String[] arrayOfString = new String[i3];
            localStringBuilder.append("square_id IN (");
            for (int i4 = 0; i4 < i3; i4++)
            {
              localStringBuilder.append("?,");
              arrayOfString[i4] = ((String)localArrayList1.get(i4));
            }
            localStringBuilder.setLength(-1 + localStringBuilder.length());
            localStringBuilder.append(")");
            localSQLiteDatabase1.delete("squares", localStringBuilder.toString(), arrayOfString);
            i2 = i3 + 0;
            if (EsLog.isLoggable("EsSquaresData", 3))
              Log.d("EsSquaresData", "Delete " + i3 + " squares");
          }
          Iterator localIterator1 = localHashMap2.entrySet().iterator();
          while (localIterator1.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator1.next();
            String str1 = (String)localEntry.getKey();
            localSQLiteDatabase1.update("squares", (ContentValues)localEntry.getValue(), "square_id=?", new String[] { str1 });
            i2++;
          }
          Iterator localIterator2 = localHashMap1.values().iterator();
          while (localIterator2.hasNext())
          {
            SquareData localSquareData1 = (SquareData)localIterator2.next();
            localSQLiteDatabase1.insertWithOnConflict("squares", null, toContentValues(localSquareData1), 5);
            i2++;
            if (EsLog.isLoggable("EsSquaresData", 3))
              Log.d("EsSquaresData", "Insert square: id=" + localSquareData1.viewerSquare.square.obfuscatedGaiaId + " name=" + localSquareData1.viewerSquare.square.profile.name);
          }
          ArrayList localArrayList2 = new ArrayList();
          Iterator localIterator3 = paramList.iterator();
          while (localIterator3.hasNext())
          {
            InvitedSquare localInvitedSquare1 = (InvitedSquare)localIterator3.next();
            if ((localInvitedSquare1.inviter != null) && (!localInvitedSquare1.inviter.isEmpty()))
            {
              SquareMember localSquareMember1 = (SquareMember)localInvitedSquare1.inviter.get(0);
              DataUser localDataUser = new DataUser();
              localDataUser.id = localSquareMember1.obfuscatedGaiaId;
              localDataUser.profilePhotoUrl = localSquareMember1.photoUrl;
              localDataUser.displayName = localSquareMember1.displayName;
              localArrayList2.add(localDataUser);
            }
          }
          EsPeopleData.replaceUsersInTransaction(localSQLiteDatabase1, localArrayList2);
          localSQLiteDatabase1.setTransactionSuccessful();
          localSQLiteDatabase1.endTransaction();
          SQLiteDatabase localSQLiteDatabase2 = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
          ContentValues localContentValues1 = new ContentValues();
          localContentValues1.put("last_squares_sync_time", Long.valueOf(l));
          localSQLiteDatabase2.update("account_status", localContentValues1, null, null);
          paramContext.getContentResolver().notifyChange(EsProvider.ACCOUNT_STATUS_URI, null);
          if (i2 != 0)
            paramContext.getContentResolver().notifyChange(EsProvider.SQUARES_URI, null);
          return i2;
        }
        i6 = 1;
        break;
      }
      label1307: i6 = 0;
    }
  }

  public static long queryLastSquaresSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_squares_sync_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  private static ContentValues toContentValues(SquareData paramSquareData)
    throws IOException
  {
    ContentValues localContentValues = toContentValues(paramSquareData.viewerSquare);
    localContentValues.put("inviter_gaia_id", paramSquareData.getInviterGaiaId());
    if (paramSquareData.suggested);
    for (int i = 1; ; i = 0)
    {
      localContentValues.put("suggested", Integer.valueOf(i));
      localContentValues.put("sort_index", Integer.valueOf(paramSquareData.sortIndex));
      localContentValues.put("suggestion_sort_index", Integer.valueOf(paramSquareData.suggestionSortIndex));
      return localContentValues;
    }
  }

  private static ContentValues toContentValues(ViewerSquare paramViewerSquare)
    throws IOException
  {
    SquareProfile localSquareProfile = paramViewerSquare.square.profile;
    ViewerSquareCalculatedMembershipProperties localViewerSquareCalculatedMembershipProperties = paramViewerSquare.calculatedMembershipProperties;
    int i = getMembershipStatus(paramViewerSquare.viewerMembershipStatus);
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("square_id", paramViewerSquare.square.obfuscatedGaiaId);
    localContentValues.put("square_name", localSquareProfile.name);
    localContentValues.put("tagline", localSquareProfile.tagline);
    localContentValues.put("photo_url", localSquareProfile.photoUrl);
    localContentValues.put("about_text", localSquareProfile.aboutText);
    localContentValues.put("joinability", Integer.valueOf(getJoinability(paramViewerSquare.square.joinability)));
    localContentValues.put("membership_status", Integer.valueOf(i));
    if (paramViewerSquare.square.visibility != null)
      localContentValues.put("post_visibility", Integer.valueOf(getVisibility(paramViewerSquare.square.visibility.posts)));
    int i2;
    int i3;
    label194: int i4;
    label219: int i5;
    label244: int i6;
    label269: int i7;
    label294: int i8;
    if (localViewerSquareCalculatedMembershipProperties != null)
      if (PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.isMember))
      {
        i2 = 1;
        localContentValues.put("is_member", Integer.valueOf(i2));
        if (!PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canSeeMemberList))
          break label498;
        i3 = 1;
        localContentValues.put("can_see_members", Integer.valueOf(i3));
        if (!PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canSeePosts))
          break label504;
        i4 = 1;
        localContentValues.put("can_see_posts", Integer.valueOf(i4));
        if (!PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canJoin))
          break label510;
        i5 = 1;
        localContentValues.put("can_join", Integer.valueOf(i5));
        if (!PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canRequestToJoin))
          break label516;
        i6 = 1;
        localContentValues.put("can_request_to_join", Integer.valueOf(i6));
        if (!PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canShareSquare))
          break label522;
        i7 = 1;
        localContentValues.put("can_share", Integer.valueOf(i7));
        if (!PrimitiveUtils.safeBoolean(localViewerSquareCalculatedMembershipProperties.canInviteToSquare))
          break label528;
        i8 = 1;
        label319: localContentValues.put("can_invite", Integer.valueOf(i8));
        if (paramViewerSquare.squareMemberStats != null)
          localContentValues.put("member_count", Integer.valueOf(PrimitiveUtils.safeInt(paramViewerSquare.squareMemberStats.memberCount)));
        if (!TextUtils.isEmpty(paramViewerSquare.viewerNotificationSettings))
          if (!"ENABLED".equals(paramViewerSquare.viewerNotificationSettings))
            break label573;
      }
    DbSquareStream[] arrayOfDbSquareStream;
    label516: label522: label528: label573: for (int i1 = 1; ; i1 = 0)
    {
      localContentValues.put("notifications_enabled", Integer.valueOf(i1));
      if ((paramViewerSquare.streams == null) || (paramViewerSquare.streams.squareStream == null))
        break label591;
      List localList = paramViewerSquare.streams.squareStream;
      arrayOfDbSquareStream = new DbSquareStream[localList.size()];
      for (int n = 0; n < arrayOfDbSquareStream.length; n++)
      {
        SquareStream localSquareStream = (SquareStream)localList.get(n);
        arrayOfDbSquareStream[n] = new DbSquareStream(localSquareStream.id, localSquareStream.name, localSquareStream.description);
      }
      i2 = 0;
      break;
      label498: i3 = 0;
      break label194;
      label504: i4 = 0;
      break label219;
      label510: i5 = 0;
      break label244;
      i6 = 0;
      break label269;
      i7 = 0;
      break label294;
      i8 = 0;
      break label319;
      if ((i == 3) || (i == 2) || (i == 1));
      for (int j = 1; ; j = 0)
      {
        localContentValues.put("is_member", Integer.valueOf(j));
        break;
      }
    }
    localContentValues.put("square_streams", DbSquareStream.serialize(arrayOfDbSquareStream));
    label591: SquareNotificationOptions localSquareNotificationOptions = paramViewerSquare.squareNotificationOptions;
    int k;
    if (localSquareNotificationOptions != null)
    {
      if (!PrimitiveUtils.safeBoolean(localSquareNotificationOptions.autoSubscribeOnJoin))
        break label686;
      k = 1;
      localContentValues.put("auto_subscribe", Integer.valueOf(k));
      if (!PrimitiveUtils.safeBoolean(localSquareNotificationOptions.disableSubscription))
        break label692;
    }
    label686: label692: for (int m = 1; ; m = 0)
    {
      localContentValues.put("disable_subscription", Integer.valueOf(m));
      ViewerSquareSquareActivityStats localViewerSquareSquareActivityStats = paramViewerSquare.squareActivityStats;
      if (localViewerSquareSquareActivityStats != null)
        localContentValues.put("unread_count", Integer.valueOf(PrimitiveUtils.safeInt(localViewerSquareSquareActivityStats.unreadPostCount)));
      return localContentValues;
      k = 0;
      break;
    }
  }

  public static void updateSquareMembership(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      try
      {
        localContentValues = new ContentValues();
        if (("JOIN".equals(paramString2)) || ("JOIN_WITH_SUBSCRIPTION".equals(paramString2)) || ("ACCEPT_INVITATION".equals(paramString2)) || ("ACCEPT_INVITATION_WITH_SUBSCRIPTION".equals(paramString2)))
        {
          if ("JOIN_WITH_SUBSCRIPTION".equals(paramString2))
            break label665;
          if ("ACCEPT_INVITATION_WITH_SUBSCRIPTION".equals(paramString2))
          {
            break label665;
            localContentValues.put("membership_status", Integer.valueOf(3));
            localContentValues.put("is_member", Integer.valueOf(1));
            localContentValues.put("can_see_members", Integer.valueOf(1));
            localContentValues.put("can_see_posts", Integer.valueOf(1));
            localContentValues.put("can_join", Integer.valueOf(0));
            localContentValues.put("can_request_to_join", Integer.valueOf(0));
            if (i != 0)
            {
              j = 1;
              localContentValues.put("notifications_enabled", Integer.valueOf(j));
              k = 1;
              if (k != 0)
                localSQLiteDatabase.update("squares", localContentValues, "square_id=?", new String[] { paramString1 });
              localSQLiteDatabase.setTransactionSuccessful();
              localSQLiteDatabase.endTransaction();
              if (k != 0)
                paramContext.getContentResolver().notifyChange(EsProvider.SQUARES_URI, null);
            }
          }
          else
          {
            i = 0;
            continue;
          }
          int j = 0;
          continue;
        }
        if (("APPLY_TO_JOIN".equals(paramString2)) || ("APPLY_TO_JOIN_WITH_SUBSCRIPTION".equals(paramString2)))
        {
          localContentValues.put("membership_status", Integer.valueOf(4));
          localContentValues.put("can_request_to_join", Integer.valueOf(0));
          k = 1;
          continue;
        }
        if ("CANCEL_JOIN_REQUEST".equals(paramString2))
        {
          localContentValues.put("membership_status", Integer.valueOf(0));
          localContentValues.put("can_request_to_join", Integer.valueOf(1));
          k = 1;
          continue;
        }
        if ("SUBSCRIBE".equals(paramString2))
        {
          localContentValues.put("notifications_enabled", Integer.valueOf(1));
          k = 1;
          continue;
        }
        if ("UNSUBSCRIBE".equals(paramString2))
        {
          localContentValues.put("notifications_enabled", Integer.valueOf(0));
          k = 1;
          continue;
        }
        boolean bool1 = "LEAVE".equals(paramString2);
        k = 0;
        if (!bool1)
          continue;
        m = -1;
        localCursor = localSQLiteDatabase.query("squares", UPDATE_SQUARE_MEMBERSHIP_PROJECTION, "square_id=?", new String[] { paramString1 }, null, null, null);
      }
      finally
      {
        try
        {
          ContentValues localContentValues;
          int m;
          boolean bool2 = localCursor.moveToNext();
          n = 0;
          Object localObject3 = null;
          if (bool2)
          {
            if (localCursor.getInt(0) != 0)
              break label677;
            n = 1;
            m = localCursor.getInt(1);
            DbSquareStream[] arrayOfDbSquareStream = DbSquareStream.deserialize(localCursor.getBlob(2));
            localObject3 = arrayOfDbSquareStream;
          }
          localCursor.close();
          localContentValues.put("membership_status", Integer.valueOf(0));
          localContentValues.put("is_member", Integer.valueOf(0));
          if (n == 0)
            break label641;
          i1 = 1;
          localContentValues.put("can_see_members", Integer.valueOf(i1));
          if (n == 0)
            break label647;
          i2 = 1;
          localContentValues.put("can_see_posts", Integer.valueOf(i2));
          if (m != 0)
            break label653;
          i3 = 1;
          localContentValues.put("can_join", Integer.valueOf(i3));
          if (m != 1)
            break label659;
          i4 = 1;
          localContentValues.put("can_request_to_join", Integer.valueOf(i4));
          if (n != 0)
            break label671;
          deleteSquareStreams(paramContext, paramEsAccount, paramString1, localObject3);
          localContentValues.putNull("square_streams");
        }
        finally
        {
          Cursor localCursor;
          localCursor.close();
        }
        localSQLiteDatabase.endTransaction();
      }
      label641: int i1 = 0;
      continue;
      label647: int i2 = 0;
      continue;
      label653: int i3 = 0;
      continue;
      label659: int i4 = 0;
      continue;
      label665: int i = 1;
      continue;
      label671: int k = 1;
      continue;
      label677: int n = 0;
    }
  }

  private static boolean validateSquare(ViewerSquare paramViewerSquare)
  {
    if ((paramViewerSquare != null) && (paramViewerSquare.square != null) && (paramViewerSquare.square.profile != null) && (!TextUtils.isEmpty(paramViewerSquare.square.obfuscatedGaiaId)));
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      if (EsLog.isLoggable("EsSquaresData", 6))
        Log.e("EsSquaresData", "Invalid ViewerSquare:\n" + ViewerSquareJson.getInstance().toPrettyString(paramViewerSquare));
    }
  }

  private static final class SquareData
  {
    public final SquareMember inviter;
    public final int sortIndex;
    public final boolean suggested;
    public final int suggestionSortIndex;
    public final ViewerSquare viewerSquare;

    public SquareData(ViewerSquare paramViewerSquare, boolean paramBoolean, SquareMember paramSquareMember, int paramInt1, int paramInt2)
    {
      this.viewerSquare = paramViewerSquare;
      this.suggested = paramBoolean;
      this.inviter = paramSquareMember;
      this.sortIndex = paramInt1;
      this.suggestionSortIndex = paramInt2;
    }

    public final String getInviterGaiaId()
    {
      if (this.inviter != null);
      for (String str = this.inviter.obfuscatedGaiaId; ; str = null)
        return str;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsSquaresData
 * JD-Core Version:    0.6.2
 */