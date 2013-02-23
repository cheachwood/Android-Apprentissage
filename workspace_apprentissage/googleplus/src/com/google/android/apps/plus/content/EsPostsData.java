package com.google.android.apps.plus.content;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.DownloadImageOperationNoCache;
import com.google.android.apps.plus.api.GetActivitiesOperation;
import com.google.android.apps.plus.api.GetNearbyActivitiesOperation;
import com.google.android.apps.plus.api.LocationQuery;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.ResourceRedirector;
import com.google.android.apps.plus.widget.EsWidgetProvider;
import com.google.android.apps.plus.widget.EsWidgetUtils;
import com.google.api.services.plusi.model.AppInvite;
import com.google.api.services.plusi.model.Comment;
import com.google.api.services.plusi.model.DataPlusOne;
import com.google.api.services.plusi.model.DeepLink;
import com.google.api.services.plusi.model.DomainData;
import com.google.api.services.plusi.model.EmbedClientItem;
import com.google.api.services.plusi.model.Explanation;
import com.google.api.services.plusi.model.Person;
import com.google.api.services.plusi.model.PlaceReviewJson;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusPhoto;
import com.google.api.services.plusi.model.PlusPhotoAlbum;
import com.google.api.services.plusi.model.PlusPhotosAddedToCollection;
import com.google.api.services.plusi.model.SquareUpdate;
import com.google.api.services.plusi.model.Thing;
import com.google.api.services.plusi.model.Update;
import com.google.api.services.plusi.model.VideoObject;
import com.google.api.services.plusi.model.WebPage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class EsPostsData
{
  private static final String[] ACTIVITY_TIMESTAMP_AND_STATUS_COLUMNS = { "activity_id", "modified", "data_state" };
  private static ArrayList<String> sEmbedsWhitelist;
  private static boolean sInitialized;
  private static int sLargePlayerSize;
  private static Integer sMaxContentLength;
  private static float sMaxPortraitAspectRatio;
  private static float sMinLandscapeAspectRatio;
  private static ArrayList<String> sMixinsWhitelist;
  private static ArrayList<String> sMixinsWithPopularWhitelist;
  private static ArrayList<String> sShareboxWhitelist;
  private static ArrayList<String> sStreamNamespaces;
  private static boolean sSyncEnabled;
  private static final Object sSyncLock = new Object();
  private static ArrayList<String> sWidgetStreamNamespaces;

  static
  {
    sSyncEnabled = true;
  }

  public static String buildActivitiesStreamKey(String paramString1, String paramString2, DbLocation paramDbLocation, boolean paramBoolean, int paramInt)
  {
    return buildStreamKey(paramString1, paramString2, paramDbLocation, paramBoolean, null, null, paramInt);
  }

  public static String buildSquareStreamKey(String paramString1, String paramString2, boolean paramBoolean)
  {
    return buildStreamKey(null, null, null, false, paramString1, paramString2, 0);
  }

  private static String buildStreamKey(String paramString1, String paramString2, DbLocation paramDbLocation, boolean paramBoolean, String paramString3, String paramString4, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((!TextUtils.isEmpty(paramString2)) && (paramString2.startsWith("f.")))
      paramString2 = paramString2.substring(2);
    localStringBuilder.append(paramString2);
    localStringBuilder.append('|');
    localStringBuilder.append(paramString1);
    localStringBuilder.append('|');
    if (paramDbLocation != null)
    {
      if (paramDbLocation.hasCoordinates())
      {
        localStringBuilder.append(paramDbLocation.getLatitudeE7());
        localStringBuilder.append(',');
        localStringBuilder.append(paramDbLocation.getLongitudeE7());
        localStringBuilder.append(',');
        localStringBuilder.append((int)paramDbLocation.getPrecisionMeters());
      }
      localStringBuilder.append('|');
      if (!paramBoolean)
        break label207;
    }
    label207: for (int i = 1; ; i = 0)
    {
      localStringBuilder.append(i);
      localStringBuilder.append('|');
      localStringBuilder.append(paramString3);
      localStringBuilder.append('|');
      localStringBuilder.append(paramString4);
      localStringBuilder.append('|');
      localStringBuilder.append(paramInt);
      return localStringBuilder.toString();
      localStringBuilder.append("null");
      break;
    }
  }

  static void cleanupData$3105fef4(SQLiteDatabase paramSQLiteDatabase)
  {
    if (!sSyncEnabled);
    while (true)
    {
      return;
      String str = buildActivitiesStreamKey(null, null, null, false, 0);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("stream_key NOT IN(");
      localStringBuilder.append(DatabaseUtils.sqlEscapeString(str));
      localStringBuilder.append(')');
      int i = paramSQLiteDatabase.delete("activity_streams", localStringBuilder.toString(), null);
      if (EsLog.isLoggable("EsPostsData", 3))
        Log.d("EsPostsData", "deleteNonEssentialStreams deleted streams: " + i);
      int j = paramSQLiteDatabase.delete("activities", "activity_id NOT IN (SELECT activity_id FROM activity_streams)", null);
      if (EsLog.isLoggable("EsPostsData", 3))
        Log.d("EsPostsData", "cleanupData deleted unreferenced activities: " + j);
      if (getAvailableStorage() < 16000000L)
      {
        int k = paramSQLiteDatabase.delete("activities", "activity_id IN (SELECT activity_id FROM activity_streams WHERE sort_index > 50)", null);
        if (EsLog.isLoggable("EsPostsData", 3))
          Log.d("EsPostsData", "cleanupData deleted \"all circles\" activities: " + k);
      }
      deleteUnusedLocations(paramSQLiteDatabase);
    }
  }

  private static void createCommentValues(Comment paramComment, String paramString, ContentValues paramContentValues)
  {
    paramContentValues.clear();
    String str = paramComment.commentId;
    paramContentValues.put("activity_id", paramString);
    paramContentValues.put("comment_id", str);
    paramContentValues.put("author_id", paramComment.obfuscatedId);
    paramContentValues.put("content", paramComment.text);
    paramContentValues.put("created", paramComment.timestamp);
    try
    {
      if (paramComment.plusone != null);
      for (byte[] arrayOfByte = DbPlusOneData.serialize(paramComment.plusone); ; arrayOfByte = null)
      {
        paramContentValues.put("plus_one_data", arrayOfByte);
        return;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
        paramContentValues.putNull("plus_one_data");
    }
  }

  public static void deleteActivity(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>>> deleteActivity id: " + paramString);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    List localList = getActivityStreams(localSQLiteDatabase, paramString);
    try
    {
      String[] arrayOfString = { paramString };
      localSQLiteDatabase.delete("activity_streams", "activity_id=?", arrayOfString);
      localSQLiteDatabase.delete("activities", "activity_id=?", arrayOfString);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      ContentResolver localContentResolver = paramContext.getContentResolver();
      Iterator localIterator = localList.iterator();
      if (localIterator.hasNext())
        localContentResolver.notifyChange(EsProvider.buildStreamUri(paramEsAccount, (String)localIterator.next()), null);
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void deleteActivityStream(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("stream_key IN(");
    localStringBuilder.append(DatabaseUtils.sqlEscapeString(paramString));
    localStringBuilder.append(')');
    int i = localSQLiteDatabase.delete("activity_streams", localStringBuilder.toString(), null);
    Uri localUri = EsProvider.buildStreamUri(paramEsAccount, paramString);
    paramContext.getContentResolver().notifyChange(localUri, null);
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", "deleteActivityStream deleted streams: " + i);
  }

  public static void deleteComment(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    String[] arrayOfString = { paramString };
    try
    {
      str = DatabaseUtils.stringForQuery(localSQLiteDatabase, "SELECT activity_id FROM activity_comments WHERE comment_id = ?", arrayOfString);
      if (EsLog.isLoggable("EsPostsData", 3))
        Log.d("EsPostsData", ">>>> deleteComment: " + paramString + " for activity: " + str);
      localSQLiteDatabase.beginTransaction();
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      try
      {
        String str;
        StringBuffer localStringBuffer = new StringBuffer(256);
        localStringBuffer.append("comment_id IN(");
        localStringBuffer.append(DatabaseUtils.sqlEscapeString(paramString));
        localStringBuffer.append(')');
        localSQLiteDatabase.delete("activity_comments", localStringBuffer.toString(), null);
        updateTotalCommentCountInTransaction(localSQLiteDatabase, str, -1);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (str != null);
        do
        {
          return;
          localSQLiteDoneException = localSQLiteDoneException;
        }
        while (!EsLog.isLoggable("EsPostsData", 5));
        Log.w("EsPostsData", "WARNING: could not find photo for the comment: " + paramString);
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
    }
  }

  private static void deleteUnusedLocations(SQLiteDatabase paramSQLiteDatabase)
  {
    Cursor localCursor = paramSQLiteDatabase.query("location_queries", new String[] { "_id" }, null, null, null, null, "_id DESC");
    if (localCursor == null);
    while (true)
    {
      return;
      if (localCursor.getCount() <= 1)
      {
        localCursor.close();
      }
      else
      {
        StringBuilder localStringBuilder;
        while (true)
        {
          try
          {
            localCursor.moveToFirst();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("_id IN(");
            int i = 1;
            if (!localCursor.moveToNext())
              break;
            if (i != 0)
            {
              i = 0;
              localStringBuilder.append(localCursor.getLong(0));
              continue;
            }
          }
          finally
          {
            localCursor.close();
          }
          localStringBuilder.append(',');
        }
        localStringBuilder.append(')');
        paramSQLiteDatabase.delete("location_queries", localStringBuilder.toString(), null);
        localCursor.close();
      }
    }
  }

  public static ServiceResult doActivityStreamSync(Context paramContext, EsAccount paramEsAccount, int paramInt1, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, int paramInt2, HttpOperation.OperationListener paramOperationListener, EsSyncAdapterService.SyncState paramSyncState)
    throws Exception
  {
    if (EsLog.isLoggable("EsPostsData", 3))
    {
      String str = buildActivitiesStreamKey(paramString2, paramString1, null, false, paramInt1);
      Log.d("EsPostsData", "doActivityStreamSync starting sync stream: " + str + ", count: " + paramInt2);
    }
    GetActivitiesOperation localGetActivitiesOperation = new GetActivitiesOperation(paramContext, paramEsAccount, paramInt1, paramString1, paramString2, paramString3, paramBoolean, paramString4, paramInt2, paramSyncState, null, paramOperationListener);
    localGetActivitiesOperation.start();
    if (localGetActivitiesOperation.getException() != null)
      throw localGetActivitiesOperation.getException();
    if (localGetActivitiesOperation.hasError())
      throw new IOException("Error: " + localGetActivitiesOperation.getErrorCode() + " [" + localGetActivitiesOperation.getReasonPhrase() + "]");
    return new ServiceResult(localGetActivitiesOperation);
  }

  public static ServiceResult doNearbyActivitiesSync(Context paramContext, EsAccount paramEsAccount, DbLocation paramDbLocation, String paramString, int paramInt, HttpOperation.OperationListener paramOperationListener, EsSyncAdapterService.SyncState paramSyncState)
    throws Exception
  {
    if (EsLog.isLoggable("EsPostsData", 3))
    {
      String str = buildActivitiesStreamKey(null, null, paramDbLocation, false, 2);
      Log.d("EsPostsData", "doNearbyActivitiesSync starting sync stream: " + str + ", count: " + paramInt);
    }
    GetNearbyActivitiesOperation localGetNearbyActivitiesOperation = new GetNearbyActivitiesOperation(paramContext, paramEsAccount, paramDbLocation, paramString, paramInt, paramSyncState, null, null);
    localGetNearbyActivitiesOperation.start();
    if (localGetNearbyActivitiesOperation.getException() != null)
      throw localGetNearbyActivitiesOperation.getException();
    if (localGetNearbyActivitiesOperation.hasError())
      throw new IOException("Error: " + localGetNearbyActivitiesOperation.getErrorCode() + " [" + localGetNearbyActivitiesOperation.getReasonPhrase() + "]");
    return new ServiceResult(localGetNearbyActivitiesOperation);
  }

  public static Bitmap getActivityImageData(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("activities", new String[] { "embed_media" }, "activity_id=?", new String[] { paramString }, null, null, null);
    Object localObject2;
    if (localCursor == null)
      localObject2 = null;
    while (true)
    {
      return localObject2;
      try
      {
        if ((localCursor.moveToFirst()) && (!localCursor.isNull(0)))
        {
          byte[] arrayOfByte = localCursor.getBlob(0);
          if ((arrayOfByte != null) && (arrayOfByte.length != 0))
          {
            DbEmbedMedia localDbEmbedMedia = DbEmbedMedia.deserialize(arrayOfByte);
            if ((localDbEmbedMedia != null) && (!TextUtils.isEmpty(localDbEmbedMedia.getImageUrl())))
            {
              Resources localResources = paramContext.getResources();
              int i = (int)localResources.getDimension(R.dimen.notification_bigpicture_width);
              int j = (int)localResources.getDimension(R.dimen.notification_bigpicture_width);
              DownloadImageOperationNoCache localDownloadImageOperationNoCache = new DownloadImageOperationNoCache(paramContext, paramEsAccount, new MediaImageRequest(localDbEmbedMedia.getImageUrl(), 3, i, j, true).getDownloadUrl(), null, null);
              localDownloadImageOperationNoCache.start();
              if (localDownloadImageOperationNoCache.getBitmap() != null)
              {
                Bitmap localBitmap = localDownloadImageOperationNoCache.getBitmap();
                localObject2 = localBitmap;
                localCursor.close();
                continue;
              }
            }
          }
        }
        localCursor.close();
        localObject2 = null;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  private static long getActivityLastEditedTime(Update paramUpdate)
  {
    return Math.max(PrimitiveUtils.safeLong(paramUpdate.updatedTimestampUsec) / 1000L, paramUpdate.timestamp.longValue());
  }

  private static long getActivityLastModifiedTime(Update paramUpdate)
  {
    long l = getActivityLastEditedTime(paramUpdate);
    if (paramUpdate.comment != null)
    {
      Iterator localIterator = paramUpdate.comment.iterator();
      while (localIterator.hasNext())
      {
        Comment localComment = (Comment)localIterator.next();
        l = Math.max(l, Math.max(PrimitiveUtils.safeLong(localComment.updatedTimestampUsec), localComment.timestamp.longValue()));
      }
    }
    if (paramUpdate.plusone != null)
      l = Math.max(l, ()PrimitiveUtils.safeDouble(paramUpdate.plusone.timeModifiedMs));
    return l;
  }

  private static HashMap<String, ActivityStatus> getActivityStatuses(SQLiteDatabase paramSQLiteDatabase, List<Update> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    ArrayList localArrayList = new ArrayList();
    localStringBuilder.append("activity_id IN (");
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Update localUpdate = (Update)localIterator.next();
      localStringBuilder.append("?,");
      localArrayList.add(localUpdate.updateId);
    }
    localStringBuilder.setLength(-1 + localStringBuilder.length());
    localStringBuilder.append(")");
    HashMap localHashMap = new HashMap();
    Cursor localCursor = paramSQLiteDatabase.query("activities", ACTIVITY_TIMESTAMP_AND_STATUS_COLUMNS, localStringBuilder.toString(), (String[])localArrayList.toArray(new String[0]), null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        String str = localCursor.getString(0);
        ActivityStatus localActivityStatus = new ActivityStatus((byte)0);
        localActivityStatus.timestamp = localCursor.getLong(1);
        localActivityStatus.dataStatus = localCursor.getInt(2);
        localHashMap.put(str, localActivityStatus);
      }
    }
    finally
    {
      localCursor.close();
    }
    return localHashMap;
  }

  private static List<String> getActivityStreams(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = paramSQLiteDatabase.query(true, "activity_streams", ActivityStreamKeyQuery.PROJECTION, "activity_id=?", new String[] { paramString }, null, null, null, null);
    if (localCursor == null);
    while (true)
    {
      return localArrayList;
      try
      {
        if (localCursor.moveToNext())
          localArrayList.add(localCursor.getString(0));
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  private static long getAvailableStorage()
  {
    try
    {
      StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
      l = localStatFs.getAvailableBlocks() * localStatFs.getBlockSize();
      if (EsLog.isLoggable("EsPostsData", 3))
        Log.d("EsPostsData", "getAvailableStorage: " + l);
      return l;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e("EsPostsData", "getAvailableStorage", localException);
        long l = -1L;
      }
    }
  }

  private static DbPlusOneData getCommentPlusOneData(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("activity_comments", new String[] { "plus_one_data" }, "comment_id=?", new String[] { paramString }, null, null, null);
    Object localObject1 = null;
    if (localCursor == null);
    while (true)
    {
      return localObject1;
      try
      {
        if (localCursor.moveToFirst())
        {
          if (localCursor.isNull(0))
          {
            localObject1 = new DbPlusOneData();
            localCursor.close();
            continue;
          }
          DbPlusOneData localDbPlusOneData = DbPlusOneData.deserialize(localCursor.getBlob(0));
          localObject1 = localDbPlusOneData;
          localCursor.close();
          continue;
        }
        localCursor.close();
        localObject1 = null;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static int getDefaultText(long paramLong)
  {
    int i;
    if ((0x1000 & paramLong) != 0L)
      i = R.string.card_auto_text_event;
    while (true)
    {
      return i;
      if ((0x4000 & paramLong) != 0L)
        i = R.string.card_auto_text_skyjam;
      else if ((0x40 & paramLong) != 0L)
        i = R.string.card_auto_text_album;
      else if ((0x80 & paramLong) != 0L)
        i = R.string.card_auto_text_video;
      else if ((0x8004 & paramLong) != 0L)
        i = R.string.card_auto_text_link;
      else if ((0x20 & paramLong) != 0L)
        i = R.string.card_auto_text_image;
      else if ((0x400000 & paramLong) != 0L)
        i = R.string.card_auto_text_emotishare;
      else if ((0x8 & paramLong) != 0L)
        i = R.string.card_auto_text_location;
      else if ((0x10000 & paramLong) != 0L)
        i = R.string.card_auto_text_review;
      else
        i = 0;
    }
  }

  public static ArrayList<String> getEmbedsWhitelist()
  {
    if (sEmbedsWhitelist == null)
    {
      ArrayList localArrayList = new ArrayList();
      sEmbedsWhitelist = localArrayList;
      localArrayList.add("SQUARE");
      sEmbedsWhitelist.add("SQUARE_INVITE");
      sEmbedsWhitelist.add("APP_INVITE");
      sEmbedsWhitelist.add("WEB_PAGE");
      sEmbedsWhitelist.add("PLUS_PHOTO");
      sEmbedsWhitelist.add("PLUS_PHOTO_ALBUM");
      sEmbedsWhitelist.add("VIDEO_OBJECT");
      sEmbedsWhitelist.add("CHECKIN");
      sEmbedsWhitelist.add("PLACE_REVIEW");
      sEmbedsWhitelist.add("PLUS_PHOTOS_ADDED_TO_COLLECTION");
      sEmbedsWhitelist.add("PLUS_EVENT");
      sEmbedsWhitelist.add("PLAY_MUSIC_TRACK");
      sEmbedsWhitelist.add("PLAY_MUSIC_ALBUM");
      sEmbedsWhitelist.add("HANGOUT_CONSUMER");
      sEmbedsWhitelist.add("EMOTISHARE");
      sEmbedsWhitelist.add("THING");
    }
    return sEmbedsWhitelist;
  }

  public static ArrayList<String> getMixinsWhitelist(boolean paramBoolean)
  {
    if (paramBoolean)
      if (sMixinsWithPopularWhitelist == null)
      {
        ArrayList localArrayList3 = new ArrayList();
        sMixinsWithPopularWhitelist = localArrayList3;
        localArrayList3.add("POPULAR_RECOMMENDATIONS");
        sMixinsWithPopularWhitelist.add("SQUARES");
        sMixinsWithPopularWhitelist.add("BIRTHDAYS");
      }
    for (ArrayList localArrayList2 = sMixinsWithPopularWhitelist; ; localArrayList2 = sMixinsWhitelist)
    {
      return localArrayList2;
      if (sMixinsWhitelist == null)
      {
        ArrayList localArrayList1 = new ArrayList();
        sMixinsWhitelist = localArrayList1;
        localArrayList1.add("SQUARES");
        sMixinsWhitelist.add("BIRTHDAYS");
      }
    }
  }

  private static int getMostRecentSortIndex(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("activity_streams", new String[] { "sort_index" }, "stream_key=?", new String[] { paramString }, null, null, "sort_index ASC", "1");
    int i;
    if (localCursor == null)
      i = 0;
    while (true)
    {
      return i;
      try
      {
        if (localCursor.moveToFirst())
        {
          int j = localCursor.getInt(0);
          i = j;
          localCursor.close();
          continue;
        }
        localCursor.close();
        i = 0;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  private static DbPlusOneData getPostPlusOneData(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("activities", new String[] { "plus_one_data" }, "activity_id=?", new String[] { paramString }, null, null, null);
    Object localObject1 = null;
    if (localCursor == null);
    while (true)
    {
      return localObject1;
      try
      {
        if (localCursor.moveToFirst())
        {
          if (localCursor.isNull(0))
          {
            localObject1 = new DbPlusOneData();
            localCursor.close();
            continue;
          }
          DbPlusOneData localDbPlusOneData = DbPlusOneData.deserialize(localCursor.getBlob(0));
          localObject1 = localDbPlusOneData;
          localCursor.close();
          continue;
        }
        localCursor.close();
        localObject1 = null;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static ArrayList<String> getShareboxEmbedsWhitelist()
  {
    if (sShareboxWhitelist == null)
    {
      ArrayList localArrayList = new ArrayList();
      sShareboxWhitelist = localArrayList;
      localArrayList.add("APP_INVITE");
      sShareboxWhitelist.add("WEB_PAGE");
      sShareboxWhitelist.add("VIDEO_OBJECT");
      sShareboxWhitelist.add("PLAY_MUSIC_TRACK");
      sShareboxWhitelist.add("PLAY_MUSIC_ALBUM");
      sShareboxWhitelist.add("THING");
    }
    return sShareboxWhitelist;
  }

  public static ArrayList<String> getStreamNamespaces(boolean paramBoolean)
  {
    if (paramBoolean)
      if (sWidgetStreamNamespaces == null)
      {
        ArrayList localArrayList3 = new ArrayList();
        sWidgetStreamNamespaces = localArrayList3;
        localArrayList3.add("STREAM");
        sWidgetStreamNamespaces.add("PHOTO");
        sWidgetStreamNamespaces.add("BIRTHDAY");
      }
    for (ArrayList localArrayList2 = sWidgetStreamNamespaces; ; localArrayList2 = sStreamNamespaces)
    {
      return localArrayList2;
      if (sStreamNamespaces == null)
      {
        ArrayList localArrayList1 = new ArrayList();
        sStreamNamespaces = localArrayList1;
        localArrayList1.add("STREAM");
        sStreamNamespaces.add("EVENT");
        sStreamNamespaces.add("SEARCH");
        sStreamNamespaces.add("PLUSONE");
        sStreamNamespaces.add("PHOTO");
        sStreamNamespaces.add("A2A");
        sStreamNamespaces.add("BIRTHDAY");
        sStreamNamespaces.add("PHOTOS_ADDED_TO_EVENT");
      }
    }
  }

  // ERROR //
  public static boolean hasStreamChanged(Context paramContext, EsAccount paramEsAccount, String paramString, List<Update> paramList)
  {
    // Byte code:
    //   0: aload_3
    //   1: ifnull +12 -> 13
    //   4: aload_3
    //   5: invokeinterface 760 1 0
    //   10: ifne +146 -> 156
    //   13: aconst_null
    //   14: astore 4
    //   16: aload_0
    //   17: aload_1
    //   18: invokestatic 231	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   21: invokevirtual 423	com/google/android/apps/plus/content/EsDatabaseHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   24: astore 5
    //   26: aload 5
    //   28: invokevirtual 238	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   31: aload 5
    //   33: ldc 119
    //   35: iconst_1
    //   36: anewarray 37	java/lang/String
    //   39: dup
    //   40: iconst_0
    //   41: ldc 39
    //   43: aastore
    //   44: ldc_w 723
    //   47: iconst_1
    //   48: anewarray 37	java/lang/String
    //   51: dup
    //   52: iconst_0
    //   53: aload_2
    //   54: aastore
    //   55: aconst_null
    //   56: aconst_null
    //   57: ldc_w 725
    //   60: ldc_w 727
    //   63: invokevirtual 730	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   66: astore 7
    //   68: aload 7
    //   70: ifnonnull +141 -> 211
    //   73: aconst_null
    //   74: astore 9
    //   76: aload 5
    //   78: invokevirtual 247	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   81: aload 5
    //   83: invokevirtual 250	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   86: ldc 127
    //   88: iconst_3
    //   89: invokestatic 133	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   92: ifeq +48 -> 140
    //   95: ldc 127
    //   97: new 55	java/lang/StringBuilder
    //   100: dup
    //   101: ldc_w 762
    //   104: invokespecial 138	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   107: aload_2
    //   108: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: ldc_w 764
    //   114: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: aload 4
    //   119: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: ldc_w 766
    //   125: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload 9
    //   130: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokestatic 144	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   139: pop
    //   140: aload 9
    //   142: ifnonnull +137 -> 279
    //   145: aload 4
    //   147: ifnonnull +126 -> 273
    //   150: iconst_0
    //   151: istore 10
    //   153: iload 10
    //   155: ireturn
    //   156: aload_3
    //   157: iconst_0
    //   158: invokeinterface 770 2 0
    //   163: checkcast 479	com/google/api/services/plusi/model/Update
    //   166: getfield 533	com/google/api/services/plusi/model/Update:updateId	Ljava/lang/String;
    //   169: astore 4
    //   171: ldc 127
    //   173: iconst_3
    //   174: invokestatic 133	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   177: ifeq -161 -> 16
    //   180: ldc 127
    //   182: new 55	java/lang/StringBuilder
    //   185: dup
    //   186: ldc_w 772
    //   189: invokespecial 138	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   192: aload_3
    //   193: invokeinterface 760 1 0
    //   198: invokevirtual 92	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   201: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   204: invokestatic 144	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   207: pop
    //   208: goto -192 -> 16
    //   211: aload 7
    //   213: invokeinterface 351 1 0
    //   218: ifeq +37 -> 255
    //   221: aload 7
    //   223: iconst_0
    //   224: invokeinterface 556 2 0
    //   229: astore 12
    //   231: aload 12
    //   233: astore 9
    //   235: aload 7
    //   237: invokeinterface 348 1 0
    //   242: goto -166 -> 76
    //   245: astore 6
    //   247: aload 5
    //   249: invokevirtual 250	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   252: aload 6
    //   254: athrow
    //   255: aconst_null
    //   256: astore 9
    //   258: goto -23 -> 235
    //   261: astore 8
    //   263: aload 7
    //   265: invokeinterface 348 1 0
    //   270: aload 8
    //   272: athrow
    //   273: iconst_1
    //   274: istore 10
    //   276: goto -123 -> 153
    //   279: aload 4
    //   281: ifnonnull +9 -> 290
    //   284: iconst_0
    //   285: istore 10
    //   287: goto -134 -> 153
    //   290: aload 4
    //   292: aload 9
    //   294: invokevirtual 775	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   297: ifne +9 -> 306
    //   300: iconst_1
    //   301: istore 10
    //   303: goto -150 -> 153
    //   306: iconst_0
    //   307: istore 10
    //   309: goto -156 -> 153
    //
    // Exception table:
    //   from	to	target	type
    //   31	81	245	finally
    //   235	242	245	finally
    //   263	273	245	finally
    //   211	231	261	finally
  }

  public static void insertActivitiesAndOverwrite(Context paramContext, EsAccount paramEsAccount, String paramString1, List<Update> paramList, String paramString2)
    throws IOException
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      try
      {
        insertActivitiesInTransaction(paramContext, paramEsAccount, localSQLiteDatabase, paramList, paramString2, true);
        if (paramString1 != null)
          insertActivitiesIntoStreamInTransaction$400325ad(localSQLiteDatabase, paramString1, paramList);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (paramString1 != null)
        {
          Uri localUri = EsProvider.buildStreamUri(paramEsAccount, paramString1);
          paramContext.getContentResolver().notifyChange(localUri, null);
          return;
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
        notifyActivityChange(localSQLiteDatabase, paramContext, paramEsAccount, ((Update)localIterator.next()).updateId);
    }
  }

  private static void insertActivitiesInTransaction(Context paramContext, EsAccount paramEsAccount, SQLiteDatabase paramSQLiteDatabase, List<Update> paramList, String paramString, boolean paramBoolean)
    throws IOException
  {
    if (!sInitialized)
    {
      sInitialized = true;
      Resources localResources = paramContext.getResources();
      WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sLargePlayerSize = Math.min(0xFFFFFFFE & Math.min(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels), localResources.getDimensionPixelSize(R.dimen.stream_media_max_fetch_size));
      sMinLandscapeAspectRatio = localResources.getDimension(R.dimen.media_min_landscape_aspect_ratio);
      sMaxPortraitAspectRatio = localResources.getDimension(R.dimen.media_max_portrait_aspect_ratio);
    }
    if (paramList.size() == 0)
      return;
    HashSet localHashSet = new HashSet();
    HashMap localHashMap = getActivityStatuses(paramSQLiteDatabase, paramList);
    int i;
    label141: ContentValues localContentValues;
    label158: Update localUpdate;
    String str1;
    int i7;
    label320: long l3;
    int j;
    label413: label473: String str3;
    label581: String str4;
    label665: label755: int k;
    label849: String str5;
    label893: int m;
    label944: int n;
    label1007: int i4;
    label1048: label1061: label1084: SquareUpdate localSquareUpdate;
    EmbedClientItem localEmbedClientItem;
    label1274: DbEmbedMedia localDbEmbedMedia;
    label1305: long l4;
    label1350: int i3;
    label1385: float f;
    label1429: label1521: boolean bool1;
    label1529: List localList;
    if (!TextUtils.equals("DEFAULT", paramString))
    {
      i = 1;
      localContentValues = new ContentValues();
      Iterator localIterator1 = paramList.iterator();
      while (localIterator1.hasNext())
      {
        localUpdate = (Update)localIterator1.next();
        str1 = localUpdate.updateId;
        if (EsLog.isLoggable("EsPostsData", 3))
          Log.d("EsPostsData", ">>>>> Activity id: " + str1 + ", author id: " + localUpdate.obfuscatedId + ", updated: " + localUpdate.timestamp + ", read: " + localUpdate.isRead);
        long l1 = getActivityLastEditedTime(localUpdate);
        long l2 = getActivityLastModifiedTime(localUpdate);
        ActivityStatus localActivityStatus = (ActivityStatus)localHashMap.get(str1);
        if (!paramBoolean)
        {
          if ((localActivityStatus != null) && (l2 == localActivityStatus.timestamp) && (localActivityStatus.dataStatus == 0))
            break label1599;
          i7 = 1;
          if (i7 == 0)
            break label1603;
        }
        l3 = 0L;
        EsPeopleData.replaceUserInTransaction(paramSQLiteDatabase, localUpdate.obfuscatedId, localUpdate.authorName, localUpdate.photoUrl);
        localContentValues.clear();
        localHashSet.clear();
        localContentValues.put("activity_id", str1);
        localContentValues.put("author_id", localUpdate.obfuscatedId);
        localContentValues.put("source_id", localUpdate.sourceStreamId);
        localContentValues.put("source_name", localUpdate.sourceStreamName);
        if (i == 0)
          break label1605;
        j = 1;
        localContentValues.put("data_state", Integer.valueOf(j));
        if ((localUpdate.place == null) || ((localUpdate.embed != null) && (localUpdate.embed.checkin != null)))
          break label1611;
        localContentValues.put("loc", DbLocation.serialize(localUpdate.place));
        l3 = 8L;
        if (sMaxContentLength == null)
          sMaxContentLength = Integer.valueOf(paramContext.getResources().getInteger(R.integer.stream_post_max_length));
        localContentValues.put("annotation", localUpdate.annotation);
        if (localUpdate.annotation == null)
          break label1622;
        String str9 = Html.fromHtml(localUpdate.annotation).toString();
        if (str9.length() > sMaxContentLength.intValue())
        {
          int i6 = -1 + sMaxContentLength.intValue();
          str9 = str9.substring(0, i6);
        }
        localContentValues.put("annotation_plaintext", str9);
        l3 |= 2L;
        localContentValues.put("title", localUpdate.title);
        if (localUpdate.title == null)
          break label1633;
        String str8 = Html.fromHtml(localUpdate.title).toString();
        if (str8.length() > sMaxContentLength.intValue())
        {
          int i5 = -1 + sMaxContentLength.intValue();
          str8 = str8.substring(0, i5);
        }
        localContentValues.put("title_plaintext", str8);
        l3 |= 1L;
        if ((localUpdate.originalItemId == null) || (TextUtils.equals(str1, localUpdate.originalItemId)))
        {
          String str2 = localUpdate.sharedFromItemId;
          str3 = null;
          str4 = null;
          if (str2 != null)
          {
            boolean bool3 = TextUtils.equals(str1, localUpdate.sharedFromItemId);
            str3 = null;
            str4 = null;
            if (bool3);
          }
        }
        else
        {
          if (localUpdate.sharedFromOriginalAuthor == null)
            break label1644;
          str3 = localUpdate.sharedFromOriginalAuthor.obfuscatedId;
          str4 = localUpdate.sharedFromOriginalAuthor.userName;
        }
        localContentValues.put("original_author_id", str3);
        localContentValues.put("original_author_name", str4);
        localContentValues.put("album_id", null);
        localContentValues.put("total_comment_count", localUpdate.totalCommentCount);
        localContentValues.put("public", localUpdate.isPublic);
        if ((!PrimitiveUtils.safeBoolean(localUpdate.isSpam)) || ((!TextUtils.isEmpty(localUpdate.moderationState)) && (!"NEW".equals(localUpdate.moderationState))))
          break label1685;
        k = 1;
        localContentValues.put("spam", Integer.valueOf(k));
        if ((!PrimitiveUtils.safeBoolean(localUpdate.isPublic)) || (PrimitiveUtils.safeBoolean(localUpdate.isRestrictedToDomain)))
          break label1691;
        str5 = paramContext.getString(R.string.acl_public);
        localContentValues.put("acl_display", str5);
        localContentValues.put("created", localUpdate.timestamp);
        localContentValues.put("modified", Long.valueOf(l2));
        if (l1 == localUpdate.timestamp.longValue())
          break label1811;
        m = 1;
        localContentValues.put("is_edited", Integer.valueOf(m));
        localContentValues.put("can_comment", localUpdate.canViewerComment);
        localContentValues.put("can_reshare", localUpdate.canViewerShare);
        localContentValues.put("has_muted", localUpdate.isMute);
        if (localUpdate.isRead != null)
          break label1817;
        n = -1;
        localContentValues.put("has_read", Integer.valueOf(n));
        if (localUpdate.explanation == null)
          break label1846;
        if (!"ITEM_POPULAR".equals(localUpdate.explanation.type))
          break label1840;
        i4 = 1;
        localContentValues.put("popular_post", Integer.valueOf(i4));
        if (localUpdate.plusone == null)
          break label1866;
        localContentValues.put("plus_one_data", DbPlusOneData.serialize(localUpdate.plusone));
        localSquareUpdate = localUpdate.squareUpdate;
        if (localSquareUpdate != null)
        {
          localContentValues.put("embed_square", DbEmbedSquare.serialize(localSquareUpdate));
          l3 |= 524288L;
        }
        if ((localUpdate.embed != null) && (localUpdate.embed.deepLinkData != null))
        {
          localContentValues.put("embed_deep_link", DbEmbedDeepLink.serialize(localUpdate.embed.deepLinkData, null));
          l3 |= 32768L;
        }
        if ((localUpdate.embed == null) || (localUpdate.embed.appInvite == null) || (localUpdate.embed.appInvite.callToAction == null) || (localUpdate.embed.appInvite.callToAction.deepLink == null))
          break label1876;
        localEmbedClientItem = localUpdate.embed.appInvite.about;
        localContentValues.put("embed_appinvite", DbEmbedDeepLink.serialize(localUpdate.embed.appInvite.callToAction.deepLink, localUpdate.embed.appInvite.callToAction.renderedLabel));
        l3 |= 131072L;
        if (localEmbedClientItem != null)
        {
          if (localEmbedClientItem.webPage == null)
            break label1886;
          WebPage localWebPage = localEmbedClientItem.webPage;
          localDbEmbedMedia = new DbEmbedMedia(localWebPage);
          if (localDbEmbedMedia != null)
          {
            localContentValues.put("embed_media", DbEmbedMedia.serialize(localDbEmbedMedia));
            if (!TextUtils.isEmpty(localDbEmbedMedia.getImageUrl()))
            {
              if (!localDbEmbedMedia.isVideo())
                break label2609;
              l4 = l3 | 0x80;
              int i1 = localDbEmbedMedia.getWidth();
              int i2 = localDbEmbedMedia.getHeight();
              if ((i1 < sLargePlayerSize) || (i2 < sLargePlayerSize))
                break label2620;
              i3 = 512;
              if ((i1 >= sLargePlayerSize) || (i2 >= sLargePlayerSize))
              {
                f = i1 / i2;
                if (f < sMinLandscapeAspectRatio)
                  break label2628;
                i3 = (int)(0x400 | i3);
              }
              l3 = l4 | i3;
            }
            if (!TextUtils.isEmpty(localDbEmbedMedia.getContentUrl()))
              l3 |= 4L;
            if (localDbEmbedMedia.isAlbum())
              l3 |= 64L;
            localHashSet.add(localDbEmbedMedia.getImageUrl());
          }
        }
        localContentValues.put("content_flags", Long.valueOf(l3));
        if (localActivityStatus == null)
          break label2650;
        paramSQLiteDatabase.update("activities", localContentValues, "activity_id=?", new String[] { str1 });
        if (localActivityStatus != null)
          break label2665;
        bool1 = true;
        updateMediaInTransaction(paramSQLiteDatabase, str1, localHashSet, bool1);
        if ((i == 0) && (localUpdate.comment != null) && (localUpdate.comment.size() > 0))
        {
          localList = localUpdate.comment;
          if (localActivityStatus != null)
            break label2671;
        }
      }
    }
    label1599: label1603: label1605: label1611: label1622: label1633: label1644: label2671: for (boolean bool2 = true; ; bool2 = false)
    {
      updateCommentsInTransaction(paramSQLiteDatabase, str1, localList, bool2);
      break label158;
      break;
      i = 0;
      break label141;
      i7 = 0;
      break label320;
      break label158;
      j = 0;
      break label413;
      localContentValues.putNull("loc");
      break label473;
      localContentValues.putNull("annotation_plaintext");
      break label581;
      localContentValues.putNull("title_plaintext");
      break label665;
      Person localPerson = localUpdate.sharedFromAuthor;
      str3 = null;
      str4 = null;
      if (localPerson == null)
        break label755;
      str3 = localUpdate.sharedFromAuthor.obfuscatedId;
      str4 = localUpdate.sharedFromOriginalAuthor.userName;
      break label755;
      k = 0;
      break label849;
      if ((PrimitiveUtils.safeBoolean(localUpdate.isPrivateToChatContacts)) || (PrimitiveUtils.safeBoolean(localUpdate.isPrivateToLatitudeFriends)))
      {
        str5 = paramContext.getString(R.string.acl_private_contacts);
        break label893;
      }
      if (PrimitiveUtils.safeBoolean(localUpdate.isSharedWithExtendedNetwork))
      {
        str5 = paramContext.getString(R.string.acl_extended_network);
        break label893;
      }
      if ((PrimitiveUtils.safeBoolean(localUpdate.isRestrictedToDomain)) && (PrimitiveUtils.safeBoolean(localUpdate.isPublic)))
      {
        if (localUpdate.restrictedDomainData != null)
        {
          str5 = localUpdate.restrictedDomainData.name;
          break label893;
        }
        str5 = "";
        break label893;
      }
      str5 = paramContext.getString(R.string.acl_limited);
      break label893;
      m = 0;
      break label944;
      if (localUpdate.isRead.booleanValue())
      {
        n = 1;
        break label1007;
      }
      n = 0;
      break label1007;
      i4 = 0;
      break label1048;
      if (i == 0)
        break label1061;
      localContentValues.put("popular_post", Integer.valueOf(0));
      break label1061;
      localContentValues.putNull("plus_one_data");
      break label1084;
      localEmbedClientItem = localUpdate.embed;
      break label1274;
      if (localEmbedClientItem.emotishare != null)
      {
        ResourceRedirector.getInstance();
        if (Property.ENABLE_EMOTISHARE.getBoolean())
        {
          DbEmbedEmotishare localDbEmbedEmotishare = new DbEmbedEmotishare(localEmbedClientItem.emotishare);
          localContentValues.put("embed_emotishare", DbEmbedEmotishare.serialize(localDbEmbedEmotishare));
          l3 |= 4194304L;
          localDbEmbedMedia = null;
          break label1305;
        }
      }
      if (localEmbedClientItem.plusPhoto != null)
      {
        PlusPhoto localPlusPhoto = localEmbedClientItem.plusPhoto;
        localDbEmbedMedia = new DbEmbedMedia(localPlusPhoto);
        break label1305;
      }
      if (localEmbedClientItem.plusPhotoAlbum != null)
      {
        PlusPhotoAlbum localPlusPhotoAlbum3 = localEmbedClientItem.plusPhotoAlbum;
        localDbEmbedMedia = new DbEmbedMedia(localPlusPhotoAlbum3);
        if (localEmbedClientItem.plusPhotoAlbum.associatedMedia == null)
          break label1305;
        Iterator localIterator2 = localEmbedClientItem.plusPhotoAlbum.associatedMedia.iterator();
        while (localIterator2.hasNext())
          localHashSet.add(((PlusPhoto)localIterator2.next()).originalMediaPlayerUrl);
        break label1305;
      }
      if (localEmbedClientItem.videoObject != null)
      {
        VideoObject localVideoObject = localEmbedClientItem.videoObject;
        localDbEmbedMedia = new DbEmbedMedia(localVideoObject);
        break label1305;
      }
      if (localEmbedClientItem.checkin != null)
      {
        localContentValues.put("loc", DbLocation.serialize(localEmbedClientItem.checkin));
        l3 |= 24L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.placeReview != null)
      {
        localContentValues.put("embed_place_review", PlaceReviewJson.getInstance().toByteArray(localEmbedClientItem.placeReview));
        l3 |= 65536L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.plusPhotosAddedToCollection != null)
      {
        PlusPhotosAddedToCollection localPlusPhotosAddedToCollection = localEmbedClientItem.plusPhotosAddedToCollection;
        PlusEvent localPlusEvent = localPlusPhotosAddedToCollection.plusEvent;
        localDbEmbedMedia = null;
        if (localPlusEvent == null)
          break label1305;
        PlusPhotoAlbum localPlusPhotoAlbum1 = localPlusPhotosAddedToCollection.associatedMediaDisplay;
        localDbEmbedMedia = null;
        if (localPlusPhotoAlbum1 == null)
          break label1305;
        String str7 = localPlusPhotosAddedToCollection.plusEvent.id;
        EsEventData.copyRsvpFromSummary(localPlusPhotosAddedToCollection.plusEvent, paramEsAccount);
        EsEventData.insertEventInTransaction$6b5f16b7(paramContext, paramEsAccount.getGaiaId(), paramSQLiteDatabase, str7, str1, localPlusPhotosAddedToCollection.plusEvent, localUpdate, null, null, 0);
        localContentValues.put("event_id", str7);
        PlusPhotoAlbum localPlusPhotoAlbum2 = localPlusPhotosAddedToCollection.associatedMediaDisplay;
        localDbEmbedMedia = new DbEmbedMedia(localPlusPhotoAlbum2);
        l3 |= 262144L;
        break label1305;
      }
      if (localEmbedClientItem.plusEvent != null)
      {
        String str6 = localEmbedClientItem.plusEvent.id;
        EsEventData.copyRsvpFromSummary(localEmbedClientItem.plusEvent, paramEsAccount);
        EsEventData.insertEventInTransaction$6b5f16b7(paramContext, paramEsAccount.getGaiaId(), paramSQLiteDatabase, str6, str1, localEmbedClientItem.plusEvent, localUpdate, null, null, 0);
        localContentValues.put("event_id", str6);
        l3 |= 4096L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.playMusicAlbum != null)
      {
        localContentValues.put("embed_skyjam", DbEmbedSkyjam.serialize(localEmbedClientItem.playMusicAlbum));
        l3 |= 16384L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.playMusicTrack != null)
      {
        localContentValues.put("embed_skyjam", DbEmbedSkyjam.serialize(localEmbedClientItem.playMusicTrack));
        l3 |= 16384L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.hangoutConsumer != null)
      {
        localContentValues.put("embed_hangout", DbEmbedHangout.serialize(localEmbedClientItem.hangoutConsumer));
        l3 |= 8192L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.square != null)
      {
        localContentValues.put("embed_square", DbEmbedSquare.serialize(localSquareUpdate, localEmbedClientItem.square));
        l3 |= 1048576L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.squareInvite != null)
      {
        localContentValues.put("embed_square", DbEmbedSquare.serialize(localSquareUpdate, localEmbedClientItem.squareInvite));
        l3 |= 2097152L;
        localDbEmbedMedia = null;
        break label1305;
      }
      if (localEmbedClientItem.thing != null)
      {
        Thing localThing = localEmbedClientItem.thing;
        localDbEmbedMedia = new DbEmbedMedia(localThing);
        break label1305;
      }
      Log.e("EsPostsData", "Found an embed we don't understand without a THING!");
      localDbEmbedMedia = null;
      break label1305;
      l4 = l3 | 0x20;
      break label1350;
      i3 = 256;
      break label1385;
      if (f > sMaxPortraitAspectRatio)
        break label1429;
      i3 = (int)(0x800 | i3);
      break label1429;
      paramSQLiteDatabase.insertWithOnConflict("activities", "activity_id", localContentValues, 5);
      break label1521;
      bool1 = false;
      break label1529;
    }
  }

  private static void insertActivitiesIntoStreamInTransaction$400325ad(SQLiteDatabase paramSQLiteDatabase, String paramString, List<Update> paramList)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", "insertActivitiesAndOverwrite in stream: " + paramString + " " + paramList.size());
    ContentValues localContentValues = new ContentValues(4);
    int i = getMostRecentSortIndex(paramSQLiteDatabase, paramString) - paramList.size();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Update localUpdate = (Update)localIterator.next();
      localContentValues.put("stream_key", paramString);
      localContentValues.put("activity_id", localUpdate.updateId);
      localContentValues.put("sort_index", Integer.valueOf(i));
      localContentValues.put("last_activity", Integer.valueOf(0));
      paramSQLiteDatabase.insertWithOnConflict("activity_streams", "activity_id", localContentValues, 4);
      i++;
    }
  }

  public static void insertComment(Context paramContext, EsAccount paramEsAccount, String paramString, Comment paramComment)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>> insertComment: " + paramComment.commentId + " for activity: " + paramString);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      createCommentValues(paramComment, paramString, localContentValues);
      localSQLiteDatabase.insertWithOnConflict("activity_comments", "activity_id", localContentValues, 5);
      updateTotalCommentCountInTransaction(localSQLiteDatabase, paramString, 1);
      EsPeopleData.replaceUserInTransaction(localSQLiteDatabase, paramComment.obfuscatedId, paramComment.authorName, paramComment.authorPhotoUrl);
      localSQLiteDatabase.setTransactionSuccessful();
      notifyActivityChange(localSQLiteDatabase, paramContext, paramEsAccount, paramString);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void insertLocations(Context paramContext, EsAccount paramEsAccount, LocationQuery paramLocationQuery, DbLocation paramDbLocation1, DbLocation paramDbLocation2, ArrayList<DbLocation> paramArrayList)
    throws IOException
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    String str = paramLocationQuery.getKey();
    localSQLiteDatabase.delete("location_queries", "key=?", new String[] { str });
    ArrayList localArrayList = new ArrayList();
    if (paramDbLocation1 != null)
      localArrayList.add(paramDbLocation1);
    if (paramDbLocation2 != null)
      localArrayList.add(paramDbLocation2);
    localArrayList.addAll(paramArrayList);
    ContentValues localContentValues;
    long l;
    if (localArrayList.size() > 0)
    {
      localContentValues = new ContentValues();
      localContentValues.put("key", str);
      l = localSQLiteDatabase.insertOrThrow("location_queries", "key", localContentValues);
      if (l >= 0L)
        localSQLiteDatabase.beginTransaction();
    }
    try
    {
      int i = localArrayList.size();
      for (int j = 0; j < i; j++)
      {
        DbLocation localDbLocation = (DbLocation)localArrayList.get(j);
        localContentValues.clear();
        localContentValues.put("qrid", Long.valueOf(l));
        localContentValues.put("name", localDbLocation.getLocationName());
        localContentValues.put("location", DbLocation.serialize(localDbLocation));
        localSQLiteDatabase.insertOrThrow("locations", "qrid", localContentValues);
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      Uri localUri = EsProvider.buildLocationQueryUri(paramEsAccount, paramLocationQuery.getKey());
      paramContext.getContentResolver().notifyChange(localUri, null);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void insertMultiStreamActivities(Context paramContext, EsAccount paramEsAccount, List<String> paramList, List<Update> paramList1, String paramString)
    throws IOException
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      insertActivitiesInTransaction(paramContext, paramEsAccount, localSQLiteDatabase, paramList1, paramString, false);
      Iterator localIterator1 = paramList.iterator();
      while (localIterator1.hasNext())
        insertActivitiesIntoStreamInTransaction$400325ad(localSQLiteDatabase, (String)localIterator1.next(), paramList1);
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    Iterator localIterator2 = paramList.iterator();
    while (localIterator2.hasNext())
    {
      Uri localUri = EsProvider.buildStreamUri(paramEsAccount, (String)localIterator2.next());
      paramContext.getContentResolver().notifyChange(localUri, null);
    }
  }

  public static boolean isActivityPlusOnedByViewer(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("activities", new String[] { "plus_one_data" }, "activity_id=?", new String[] { paramString }, null, null, null);
    try
    {
      if ((localCursor.moveToFirst()) && (!localCursor.isNull(0)))
      {
        DbPlusOneData localDbPlusOneData = DbPlusOneData.deserialize(localCursor.getBlob(0));
        if (localDbPlusOneData != null)
        {
          boolean bool2 = localDbPlusOneData.isPlusOnedByMe();
          bool1 = bool2;
          return bool1;
        }
      }
      localCursor.close();
      boolean bool1 = false;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static void markActivitiesAsRead(Context paramContext, EsAccount paramEsAccount, List<String> paramList)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
    {
      Log.d("EsPostsData", ">>>>> markActivitiesAsRead activity ids:");
      Iterator localIterator2 = paramList.iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        Log.d("EsPostsData", "\t" + str2);
      }
    }
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    ContentValues localContentValues;
    StringBuffer localStringBuffer;
    while (true)
    {
      try
      {
        localContentValues = new ContentValues(1);
        localContentValues.put("has_read", Integer.valueOf(1));
        localStringBuffer = new StringBuffer(256);
        localStringBuffer.append("activity_id IN(");
        int i = 1;
        Iterator localIterator1 = paramList.iterator();
        if (!localIterator1.hasNext())
          break;
        String str1 = (String)localIterator1.next();
        if (i != 0)
        {
          i = 0;
          localStringBuffer.append(DatabaseUtils.sqlEscapeString(str1));
          continue;
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      localStringBuffer.append(',');
    }
    localStringBuffer.append(')');
    localSQLiteDatabase.update("activities", localContentValues, localStringBuffer.toString(), null);
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
  }

  public static void muteActivity(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean)
  {
    int i = 1;
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>>> muteActivity id: " + paramString);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    ContentResolver localContentResolver;
    try
    {
      ContentValues localContentValues = new ContentValues(1);
      if (paramBoolean)
      {
        localContentValues.put("has_muted", Integer.valueOf(i));
        localSQLiteDatabase.update("activities", localContentValues, "activity_id=?", new String[] { paramString });
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        List localList = getActivityStreams(localSQLiteDatabase, paramString);
        localContentResolver = paramContext.getContentResolver();
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
          localContentResolver.notifyChange(EsProvider.buildStreamUri(paramEsAccount, (String)localIterator.next()), null);
      }
      i = 0;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void notifyActivityChange(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Uri.Builder localBuilder = EsProvider.ACTIVITY_VIEW_BY_ACTIVITY_ID_URI.buildUpon();
    localBuilder.appendPath(paramString);
    localContentResolver.notifyChange(localBuilder.build(), null);
    Iterator localIterator = getActivityStreams(paramSQLiteDatabase, paramString).iterator();
    while (localIterator.hasNext())
      localContentResolver.notifyChange(EsProvider.buildStreamUri(paramEsAccount, (String)localIterator.next()), null);
  }

  public static DbPlusOneData plusOneComment(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>>> plusOneComment activity id: " + paramString1 + ", commentId: " + paramString2 + " " + paramBoolean);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    DbPlusOneData localDbPlusOneData = getCommentPlusOneData(localSQLiteDatabase, paramString2);
    if (localDbPlusOneData == null)
      localDbPlusOneData = null;
    while (true)
    {
      return localDbPlusOneData;
      localDbPlusOneData.updatePlusOnedByMe(paramBoolean);
      replaceCommentPlusOneData(localSQLiteDatabase, paramString2, localDbPlusOneData);
      notifyActivityChange(localSQLiteDatabase, paramContext, paramEsAccount, paramString1);
    }
  }

  public static DbPlusOneData plusOnePost(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>>> plusOnePost activity id: " + paramString + " " + paramBoolean);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    DbPlusOneData localDbPlusOneData = getPostPlusOneData(localSQLiteDatabase, paramString);
    if (localDbPlusOneData == null)
      localDbPlusOneData = null;
    while (true)
    {
      return localDbPlusOneData;
      localDbPlusOneData.updatePlusOnedByMe(paramBoolean);
      replacePostPlusOneData(localSQLiteDatabase, paramString, localDbPlusOneData);
      notifyActivityChange(localSQLiteDatabase, paramContext, paramEsAccount, paramString);
    }
  }

  private static void replaceCommentPlusOneData(SQLiteDatabase paramSQLiteDatabase, String paramString, DbPlusOneData paramDbPlusOneData)
  {
    try
    {
      byte[] arrayOfByte = DbPlusOneData.serialize(paramDbPlusOneData);
      ContentValues localContentValues = new ContentValues(1);
      localContentValues.put("plus_one_data", arrayOfByte);
      paramSQLiteDatabase.update("activity_comments", localContentValues, "comment_id=?", new String[] { paramString });
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.e("EsPostsData", "Could not serialize DbPlusOneData " + localIOException);
    }
  }

  private static void replacePostPlusOneData(SQLiteDatabase paramSQLiteDatabase, String paramString, DbPlusOneData paramDbPlusOneData)
  {
    try
    {
      byte[] arrayOfByte = DbPlusOneData.serialize(paramDbPlusOneData);
      ContentValues localContentValues = new ContentValues(1);
      localContentValues.put("plus_one_data", arrayOfByte);
      paramSQLiteDatabase.update("activities", localContentValues, "activity_id=?", new String[] { paramString });
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.e("EsPostsData", "Could not serialize DbPlusOneData " + localIOException);
    }
  }

  public static void setSyncEnabled(boolean paramBoolean)
  {
    sSyncEnabled = paramBoolean;
  }

  public static void syncActivities(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, HttpOperation.OperationListener paramOperationListener)
    throws Exception
  {
    String str1;
    int i;
    String str2;
    while (true)
    {
      int k;
      synchronized (sSyncLock)
      {
        if ((!paramSyncState.isCanceled()) && (!sSyncEnabled))
          break label275;
        paramSyncState.onStart("Activities:Sync");
        ArrayList localArrayList = new ArrayList();
        HashSet localHashSet = new HashSet();
        int[] arrayOfInt = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(new ComponentName(paramContext, EsWidgetProvider.class));
        if (arrayOfInt.length > 0)
        {
          int j = arrayOfInt.length;
          k = 0;
          if (k < j)
          {
            String str3 = EsWidgetUtils.loadCircleId(paramContext, arrayOfInt[k]);
            if ((str3 == null) || (localHashSet.contains(str3)))
              break label276;
            localHashSet.add(str3);
            localArrayList.add(str3);
            break label276;
          }
        }
        if (localArrayList.size() <= 0)
          break label268;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext())
          break label268;
        str1 = (String)localIterator.next();
        if ("v.whatshot".equals(str1))
        {
          i = 1;
          str2 = null;
          doActivityStreamSync(paramContext, paramEsAccount, i, str2, null, null, true, null, 20, paramOperationListener, paramSyncState);
        }
      }
      if ("v.nearby".equals(str1))
      {
        i = 2;
        str2 = null;
      }
      else
      {
        if (!"v.all.circles".equals(str1))
          break label288;
        str2 = null;
        break;
        label268: paramSyncState.onFinish();
        label275: return;
        label276: k++;
      }
    }
    while (true)
    {
      i = 0;
      break;
      label288: str2 = str1;
    }
  }

  public static void updateComment(Context paramContext, EsAccount paramEsAccount, String paramString, Comment paramComment)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>> editComment: " + paramComment.commentId + " for activity: " + paramString);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      createCommentValues(paramComment, paramString, localContentValues);
      StringBuffer localStringBuffer = new StringBuffer(256);
      localStringBuffer.append("comment_id IN(");
      localStringBuffer.append(DatabaseUtils.sqlEscapeString(paramComment.commentId));
      localStringBuffer.append(')');
      localSQLiteDatabase.update("activity_comments", localContentValues, localStringBuffer.toString(), null);
      EsPeopleData.replaceUserInTransaction(localSQLiteDatabase, paramComment.obfuscatedId, paramComment.authorName, paramComment.authorPhotoUrl);
      localSQLiteDatabase.setTransactionSuccessful();
      notifyActivityChange(localSQLiteDatabase, paramContext, paramEsAccount, paramString);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void updateCommentPlusOneId(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>>> updateCommentPlusOneId activity id: " + paramString1 + ", comment id: " + paramString2);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    DbPlusOneData localDbPlusOneData = getCommentPlusOneData(localSQLiteDatabase, paramString2);
    if (localDbPlusOneData == null);
    while (true)
    {
      return;
      if (!TextUtils.equals(localDbPlusOneData.getId(), paramString3))
      {
        localDbPlusOneData.setId(paramString3);
        replaceCommentPlusOneData(localSQLiteDatabase, paramString2, localDbPlusOneData);
      }
    }
  }

  private static void updateCommentsInTransaction(SQLiteDatabase paramSQLiteDatabase, String paramString, List<Comment> paramList, boolean paramBoolean)
  {
    if (!paramBoolean)
      paramSQLiteDatabase.delete("activity_comments", "activity_id=?", new String[] { paramString });
    if (paramList.isEmpty());
    while (true)
    {
      return;
      ContentValues localContentValues = new ContentValues();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Comment localComment = (Comment)localIterator.next();
        if (EsLog.isLoggable("EsPostsData", 3))
          Log.d("EsPostsData", "    >>>>> insertComments comment id: " + localComment.commentId + ", author id: " + localComment.obfuscatedId + ", content: " + localComment.text + ", created: " + localComment.timestamp);
        if ((PrimitiveUtils.safeBoolean(localComment.isSpam)) && (!PrimitiveUtils.safeBoolean(localComment.isOwnedByViewer)))
        {
          if (EsLog.isLoggable("EsPostsData", 3))
            Log.d("EsPostsData", "    >>>>> skipping! isSpam=true");
        }
        else
        {
          createCommentValues(localComment, paramString, localContentValues);
          paramSQLiteDatabase.insertWithOnConflict("activity_comments", "activity_id", localContentValues, 5);
          EsPeopleData.replaceUserInTransaction(paramSQLiteDatabase, localComment.obfuscatedId, localComment.authorName, localComment.authorPhotoUrl);
        }
      }
    }
  }

  private static void updateMediaInTransaction(SQLiteDatabase paramSQLiteDatabase, String paramString, HashSet<String> paramHashSet, boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet();
    if (!paramBoolean)
    {
      Cursor localCursor = paramSQLiteDatabase.query("media", new String[] { "thumbnail_url" }, "activity_id=?", new String[] { paramString }, null, null, null);
      try
      {
        if (localCursor.moveToNext())
          localHashSet.add(localCursor.getString(0));
      }
      finally
      {
        localCursor.close();
      }
    }
    if (!paramHashSet.isEmpty())
    {
      ContentValues localContentValues = new ContentValues(2);
      Iterator localIterator1 = paramHashSet.iterator();
      if (localIterator1.hasNext())
      {
        String str = (String)localIterator1.next();
        int i = 0;
        label139: if (i < 2)
        {
          if (i == 1)
            str = str + "&google_plus:card_type=nonsquare";
          if (EsLog.isLoggable("EsPostsData", 3))
            Log.d("EsPostsData", "    >>>>> insertMedia: " + str);
          if (!localHashSet.contains(str))
            break label231;
          localHashSet.remove(str);
        }
        while (true)
        {
          i++;
          break label139;
          break;
          label231: localContentValues.put("activity_id", paramString);
          localContentValues.put("thumbnail_url", str);
          paramSQLiteDatabase.insertWithOnConflict("media", "activity_id", localContentValues, 4);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
      paramSQLiteDatabase.delete("media", "activity_id=? AND thumbnail_url=?", new String[] { paramString, (String)localIterator2.next() });
  }

  public static void updatePostPlusOneId(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", ">>>>> update post plusone id: " + paramString1 + " " + paramString2);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    DbPlusOneData localDbPlusOneData = getPostPlusOneData(localSQLiteDatabase, paramString1);
    if (localDbPlusOneData == null);
    while (true)
    {
      return;
      if (!TextUtils.equals(localDbPlusOneData.getId(), paramString2))
      {
        localDbPlusOneData.setId(paramString2);
        replacePostPlusOneData(localSQLiteDatabase, paramString1, localDbPlusOneData);
      }
    }
  }

  public static void updateStreamActivities(Context paramContext, EsAccount paramEsAccount, String paramString1, List<Update> paramList, String paramString2, String paramString3, String paramString4, EsSyncAdapterService.SyncState paramSyncState)
    throws IOException
  {
    if (paramList == null)
      paramList = new ArrayList();
    int i = paramList.size();
    if (TextUtils.equals(paramString3, paramString4))
      paramString4 = null;
    if (EsLog.isLoggable("EsPostsData", 3))
      Log.d("EsPostsData", "updateStreamActivities: " + paramString1 + " received activities: " + i + " ,new token: " + paramString4 + " ,old token: " + paramString3);
    if (paramSyncState != null)
      paramSyncState.incrementCount(i);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      int j;
      if (TextUtils.isEmpty(paramString3))
      {
        j = 0;
        localSQLiteDatabase.delete("activity_streams", "stream_key=?", new String[] { paramString1 });
      }
      ContentValues localContentValues;
      int k;
      while (true)
      {
        localContentValues = new ContentValues(5);
        k = -1 + (j + i);
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Update localUpdate = (Update)localIterator.next();
          localContentValues.put("stream_key", paramString1);
          localContentValues.put("activity_id", localUpdate.updateId);
          localContentValues.put("sort_index", Integer.valueOf(j));
          localSQLiteDatabase.insertWithOnConflict("activity_streams", "activity_id", localContentValues, 5);
          j++;
        }
        j = (int)DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT count(*) FROM activity_streams WHERE stream_key=?", new String[] { paramString1 });
      }
      localContentValues.clear();
      localContentValues.put("token", paramString4);
      localSQLiteDatabase.update("activity_streams", localContentValues, "stream_key=? AND sort_index=0", new String[] { paramString1 });
      if (TextUtils.isEmpty(paramString4))
      {
        localContentValues.clear();
        localContentValues.put("last_activity", Integer.valueOf(1));
        String[] arrayOfString = new String[2];
        arrayOfString[0] = paramString1;
        arrayOfString[1] = String.valueOf(k);
        localSQLiteDatabase.update("activity_streams", localContentValues, "stream_key=? AND sort_index=?", arrayOfString);
      }
      if (i > 0)
      {
        if (EsLog.isLoggable("EsPostsData", 3))
          Log.d("EsPostsData", "updateStreamActivities: " + paramString1 + " inserting activities:" + paramList.size());
        insertActivitiesInTransaction(paramContext, paramEsAccount, localSQLiteDatabase, paramList, paramString2, false);
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      Uri localUri = EsProvider.buildStreamUri(paramEsAccount, paramString1);
      paramContext.getContentResolver().notifyChange(localUri, null);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void updateTotalCommentCountInTransaction(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    Cursor localCursor = paramSQLiteDatabase.query("activities", new String[] { "total_comment_count" }, "activity_id=?", new String[] { paramString }, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      i = 0;
      if (bool)
        i = localCursor.getInt(0);
    }
    localContentValues.put("total_comment_count", Integer.valueOf(i + paramInt));
    paramSQLiteDatabase.update("activities", localContentValues, "activity_id=?", new String[] { paramString });
  }

  private static final class ActivityStatus
  {
    int dataStatus;
    long timestamp;
  }

  private static abstract interface ActivityStreamKeyQuery
  {
    public static final String[] PROJECTION = { "stream_key" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsPostsData
 * JD-Core Version:    0.6.2
 */