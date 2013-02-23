package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.AnalyticsInfo;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.GetNotificationsOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.realtimechat.RealTimeChatNotifications;
import com.google.android.apps.plus.service.AndroidNotification;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.DataAction;
import com.google.api.services.plusi.model.DataActor;
import com.google.api.services.plusi.model.DataAlbum;
import com.google.api.services.plusi.model.DataCoalescedItem;
import com.google.api.services.plusi.model.DataItem;
import com.google.api.services.plusi.model.DataKvPair;
import com.google.api.services.plusi.model.DataPhoto;
import com.google.api.services.plusi.model.DataUser;
import com.google.api.services.plusi.model.EmbedClientItem;
import com.google.api.services.plusi.model.EntityEntityData;
import com.google.api.services.plusi.model.EntityPhotosData;
import com.google.api.services.plusi.model.EntityPhotosDataJson;
import com.google.api.services.plusi.model.EntitySquaresData;
import com.google.api.services.plusi.model.EntitySquaresDataJson;
import com.google.api.services.plusi.model.EntitySquaresDataNewModerator;
import com.google.api.services.plusi.model.EntitySquaresDataRenderSquaresData;
import com.google.api.services.plusi.model.EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionData;
import com.google.api.services.plusi.model.EntitySquaresDataSquare;
import com.google.api.services.plusi.model.EntitySquaresDataSquareInvite;
import com.google.api.services.plusi.model.EntitySquaresDataSquareMembershipApproved;
import com.google.api.services.plusi.model.EntitySquaresDataSquareMembershipRequest;
import com.google.api.services.plusi.model.EntitySquaresDataSquareNameChange;
import com.google.api.services.plusi.model.EntitySquaresDataSquareSubscription;
import com.google.api.services.plusi.model.EntitySummaryData;
import com.google.api.services.plusi.model.EntityUpdateData;
import com.google.api.services.plusi.model.EntityUpdateDataSummarySnippet;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusPhoto;
import com.google.api.services.plusi.model.Update;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EsNotificationData
{
  private static final Map<String, Integer> MAP_CATEGORY = new HashMap();
  private static final Map<String, Integer> MAP_ENTITY_TYPE = new HashMap();
  private static final Map<String, Integer> MAP_NOTIFICATION_TYPE = new HashMap();
  private static final Object mSyncLock = new Object();

  static
  {
    MAP_CATEGORY.put("CIRCLE", Integer.valueOf(2));
    MAP_CATEGORY.put("ENTITYPROFILE", Integer.valueOf(9));
    MAP_CATEGORY.put("EVENTS", Integer.valueOf(10));
    MAP_CATEGORY.put("GAMES", Integer.valueOf(4));
    MAP_CATEGORY.put("GENERIC_CATEGORY", Integer.valueOf(65535));
    MAP_CATEGORY.put("HANGOUT", Integer.valueOf(8));
    MAP_CATEGORY.put("MOBILE", Integer.valueOf(7));
    MAP_CATEGORY.put("PHOTOS", Integer.valueOf(3));
    MAP_CATEGORY.put("QUESTIONS", Integer.valueOf(6));
    MAP_CATEGORY.put("SQUARE", Integer.valueOf(11));
    MAP_CATEGORY.put("STREAM", Integer.valueOf(1));
    MAP_CATEGORY.put("SYSTEM", Integer.valueOf(5));
    MAP_CATEGORY.put("TARGET", Integer.valueOf(12));
    MAP_ENTITY_TYPE.put("ACTIVITY", Integer.valueOf(1));
    MAP_ENTITY_TYPE.put("ALBUM", Integer.valueOf(7));
    MAP_ENTITY_TYPE.put("CIRCLE_SHARE", Integer.valueOf(8));
    MAP_ENTITY_TYPE.put("DEPRECATED_SYSTEM_TACO", Integer.valueOf(4));
    MAP_ENTITY_TYPE.put("EVENT", Integer.valueOf(9));
    MAP_ENTITY_TYPE.put("MATERIALIZED_TORTILLA", Integer.valueOf(5));
    MAP_ENTITY_TYPE.put("PHOTO", Integer.valueOf(2));
    MAP_ENTITY_TYPE.put("QUESTION", Integer.valueOf(3));
    MAP_ENTITY_TYPE.put("RESHARED", Integer.valueOf(6));
    MAP_ENTITY_TYPE.put("UNKNOWN_ENTITY_TYPE", Integer.valueOf(65535));
    MAP_NOTIFICATION_TYPE.put("ASPEN_INVITE", Integer.valueOf(74));
    MAP_NOTIFICATION_TYPE.put("BIRTHDAY_WISH", Integer.valueOf(63));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_CONTACT_JOINED", Integer.valueOf(69));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_DIGESTED_ADD", Integer.valueOf(40));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_EXPLICIT_INVITE", Integer.valueOf(32));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_INVITE_REQUEST", Integer.valueOf(8));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_INVITEE_JOINED_ES", Integer.valueOf(38));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_MEMBER_JOINED_ES", Integer.valueOf(9));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_PERSONAL_ADD", Integer.valueOf(6));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_RECIPROCATING_ADD", Integer.valueOf(39));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_RECOMMEND_PEOPLE", Integer.valueOf(66));
    MAP_NOTIFICATION_TYPE.put("CIRCLE_STATUS_CHANGE", Integer.valueOf(7));
    MAP_NOTIFICATION_TYPE.put("DIGEST_SWEEP", Integer.valueOf(70));
    MAP_NOTIFICATION_TYPE.put("ENTITYPROFILE_ADD_ADMIN", Integer.valueOf(34));
    MAP_NOTIFICATION_TYPE.put("ENTITYPROFILE_REMOVE_ADMIN", Integer.valueOf(35));
    MAP_NOTIFICATION_TYPE.put("ENTITYPROFILE_TRANSFER_OWNERSHIP", Integer.valueOf(36));
    MAP_NOTIFICATION_TYPE.put("EVENTS_BEFORE_REMINDER", Integer.valueOf(59));
    MAP_NOTIFICATION_TYPE.put("EVENTS_CHANGE", Integer.valueOf(53));
    MAP_NOTIFICATION_TYPE.put("EVENTS_CHECKIN", Integer.valueOf(58));
    MAP_NOTIFICATION_TYPE.put("EVENTS_INVITE", Integer.valueOf(47));
    MAP_NOTIFICATION_TYPE.put("EVENTS_INVITEE_CHANGE", Integer.valueOf(57));
    MAP_NOTIFICATION_TYPE.put("EVENTS_PHOTOS_ADDED", Integer.valueOf(62));
    MAP_NOTIFICATION_TYPE.put("EVENTS_PHOTOS_COLLECTION", Integer.valueOf(56));
    MAP_NOTIFICATION_TYPE.put("EVENTS_PHOTOS_REMINDER", Integer.valueOf(55));
    MAP_NOTIFICATION_TYPE.put("EVENTS_RSVP_CONFIRMATION", Integer.valueOf(67));
    MAP_NOTIFICATION_TYPE.put("EVENTS_STARTING", Integer.valueOf(54));
    MAP_NOTIFICATION_TYPE.put("GAMES_APPLICATION_MESSAGE", Integer.valueOf(12));
    MAP_NOTIFICATION_TYPE.put("GAMES_INVITE_REQUEST", Integer.valueOf(11));
    MAP_NOTIFICATION_TYPE.put("GAMES_ONEUP_NOTIFICATION", Integer.valueOf(73));
    MAP_NOTIFICATION_TYPE.put("GAMES_PERSONAL_MESSAGE", Integer.valueOf(17));
    MAP_NOTIFICATION_TYPE.put("HANGOUT_INVITE", Integer.valueOf(33));
    MAP_NOTIFICATION_TYPE.put("MOBILE_NEW_CONVERSATION", Integer.valueOf(29));
    MAP_NOTIFICATION_TYPE.put("PHOTOS_CAMERASYNC_UPLOADED", Integer.valueOf(18));
    MAP_NOTIFICATION_TYPE.put("PHOTOS_FACE_SUGGESTED", Integer.valueOf(41));
    MAP_NOTIFICATION_TYPE.put("PHOTOS_PROFILE_PHOTO_SUGGESTED", Integer.valueOf(68));
    MAP_NOTIFICATION_TYPE.put("PHOTOS_PROFILE_PHOTO_SUGGESTION_ACCEPTED", Integer.valueOf(71));
    MAP_NOTIFICATION_TYPE.put("PHOTOS_TAG_ADDED_ON_PHOTO", Integer.valueOf(13));
    MAP_NOTIFICATION_TYPE.put("PHOTOS_TAGGED_IN_PHOTO", Integer.valueOf(10));
    MAP_NOTIFICATION_TYPE.put("QUESTIONS_ANSWERER_FOLLOWUP", Integer.valueOf(30));
    MAP_NOTIFICATION_TYPE.put("QUESTIONS_ASKER_FOLLOWUP", Integer.valueOf(31));
    MAP_NOTIFICATION_TYPE.put("QUESTIONS_DASHER_WELCOME", Integer.valueOf(27));
    MAP_NOTIFICATION_TYPE.put("QUESTIONS_REFERRAL", Integer.valueOf(19));
    MAP_NOTIFICATION_TYPE.put("QUESTIONS_REPLY", Integer.valueOf(22));
    MAP_NOTIFICATION_TYPE.put("QUESTIONS_UNANSWERED_QUESTION", Integer.valueOf(28));
    MAP_NOTIFICATION_TYPE.put("SQUARE_ABUSE", Integer.valueOf(79));
    MAP_NOTIFICATION_TYPE.put("SQUARE_INVITE", Integer.valueOf(48));
    MAP_NOTIFICATION_TYPE.put("SQUARE_MEMBERSHIP_APPROVED", Integer.valueOf(51));
    MAP_NOTIFICATION_TYPE.put("SQUARE_MEMBERSHIP_REQUEST", Integer.valueOf(52));
    MAP_NOTIFICATION_TYPE.put("SQUARE_NAME_CHANGE", Integer.valueOf(72));
    MAP_NOTIFICATION_TYPE.put("SQUARE_NEW_MODERATOR", Integer.valueOf(65));
    MAP_NOTIFICATION_TYPE.put("SQUARE_SUBSCRIPTION", Integer.valueOf(49));
    MAP_NOTIFICATION_TYPE.put("STREAM_COMMENT_AT_REPLY", Integer.valueOf(15));
    MAP_NOTIFICATION_TYPE.put("STREAM_COMMENT_FOLLOWUP", Integer.valueOf(3));
    MAP_NOTIFICATION_TYPE.put("STREAM_COMMENT_FOR_PHOTO_TAGGED", Integer.valueOf(25));
    MAP_NOTIFICATION_TYPE.put("STREAM_COMMENT_FOR_PHOTO_TAGGER", Integer.valueOf(26));
    MAP_NOTIFICATION_TYPE.put("STREAM_COMMENT_NEW", Integer.valueOf(2));
    MAP_NOTIFICATION_TYPE.put("STREAM_COMMENT_ON_MENTION", Integer.valueOf(14));
    MAP_NOTIFICATION_TYPE.put("STREAM_LIKE", Integer.valueOf(4));
    MAP_NOTIFICATION_TYPE.put("STREAM_PLUSONE_COMMENT", Integer.valueOf(21));
    MAP_NOTIFICATION_TYPE.put("STREAM_PLUSONE_POST", Integer.valueOf(20));
    MAP_NOTIFICATION_TYPE.put("STREAM_POST_AT_REPLY", Integer.valueOf(16));
    MAP_NOTIFICATION_TYPE.put("STREAM_POST_FROM_UNCIRCLED", Integer.valueOf(61));
    MAP_NOTIFICATION_TYPE.put("STREAM_POST_SHARED", Integer.valueOf(24));
    MAP_NOTIFICATION_TYPE.put("STREAM_POST", Integer.valueOf(1));
    MAP_NOTIFICATION_TYPE.put("STREAM_POST_SUBSCRIBED", Integer.valueOf(64));
    MAP_NOTIFICATION_TYPE.put("STREAM_RESHARE", Integer.valueOf(5));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_CELEBRITY_SUGGESTIONS", Integer.valueOf(45));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_CONNECTED_SITES", Integer.valueOf(46));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_DO_NOT_USE", Integer.valueOf(50));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_FRIEND_SUGGESTIONS", Integer.valueOf(44));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_INVITE", Integer.valueOf(37));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_TOOLTIP", Integer.valueOf(43));
    MAP_NOTIFICATION_TYPE.put("SYSTEM_WELCOME", Integer.valueOf(42));
    MAP_NOTIFICATION_TYPE.put("TARGET_SHARED", Integer.valueOf(60));
    MAP_NOTIFICATION_TYPE.put("UNKNOWN_NOTIFICATION_TYPE", Integer.valueOf(0));
  }

  static void cleanupData$3105fef4(SQLiteDatabase paramSQLiteDatabase)
  {
    long l = EsDatabaseHelper.getRowsCount(paramSQLiteDatabase, "notifications", null, null);
    if (EsLog.isLoggable("EsNotificationData", 4))
      Log.i("EsNotificationData", "deleteOldNotifications, keep count: " + 200L + ", have: " + l);
    if (l - 200L > 0L)
    {
      Cursor localCursor = paramSQLiteDatabase.query("notifications", NotificationIdsQuery.PROJECTION, null, null, null, null, "timestamp ASC", Long.toString(l - 200L));
      if (localCursor != null)
      {
        StringBuffer localStringBuffer = new StringBuffer(256);
        while (true)
        {
          try
          {
            localStringBuffer.append("notif_id IN(");
            int i = 1;
            if (!localCursor.moveToNext())
              break;
            if (i != 0)
            {
              i = 0;
              localStringBuffer.append('\'');
              localStringBuffer.append(localCursor.getString(0));
              localStringBuffer.append('\'');
              continue;
            }
          }
          finally
          {
            localCursor.close();
          }
          localStringBuffer.append(',');
        }
        localStringBuffer.append(')');
        localCursor.close();
        paramSQLiteDatabase.delete("notifications", localStringBuffer.toString(), null);
      }
    }
  }

  public static void deactivateAccount(Context paramContext, EsAccount paramEsAccount)
  {
    AndroidNotification.cancelAll(paramContext, paramEsAccount);
    AndroidNotification.cancelQuotaNotification(paramContext, paramEsAccount);
    AndroidNotification.cancelFirstTimeFullSizeNotification(paramContext, paramEsAccount);
    RealTimeChatNotifications.cancel(paramContext, paramEsAccount);
  }

  public static double getLatestNotificationTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("notifications", NotificationTimestampsQuery.PROJECTION, null, null, null, null, "timestamp DESC", "1");
    double d1;
    if (localCursor == null)
      d1 = -1.0D;
    while (true)
    {
      return d1;
      try
      {
        if (localCursor.moveToNext())
        {
          double d2 = localCursor.getDouble(0);
          d1 = d2;
          localCursor.close();
          continue;
        }
        localCursor.close();
        d1 = -1.0D;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static int getNotificationType(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (MAP_NOTIFICATION_TYPE.containsKey(paramString)));
    for (int i = ((Integer)MAP_NOTIFICATION_TYPE.get(paramString)).intValue(); ; i = 0)
      return i;
  }

  public static Cursor getNotificationsToDisplay(Context paramContext, EsAccount paramEsAccount)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("notifications", NotificationQuery.PROJECTION, "read=0 AND seen=0 AND enabled=1", null, null, null, "timestamp DESC");
    if ((localCursor != null) && (EsLog.isLoggable("EsNotificationData", 4)))
    {
      while (localCursor.moveToNext())
        Log.i("EsNotificationData", "getNotificationsToDisplay: unread notification id: " + localCursor.getString(1) + ", coalescingCode: " + localCursor.getString(2) + ", message: " + localCursor.getString(4) + ", timestamp: " + localCursor.getLong(5));
      localCursor.moveToPosition(-1);
    }
    return localCursor;
  }

  public static int getNumSquarePosts(EntitySquaresData paramEntitySquaresData)
  {
    if (paramEntitySquaresData.subscription != null);
    for (int i = paramEntitySquaresData.subscription.size(); ; i = 0)
      return i;
  }

  private static double getOldestUnreadNotificationTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("notifications", NotificationTimestampsQuery.PROJECTION, "read=0", null, null, null, "timestamp ASC", "1");
    double d1;
    if (localCursor == null)
      d1 = -1.0D;
    while (true)
    {
      return d1;
      try
      {
        if (localCursor.moveToNext())
        {
          double d2 = localCursor.getDouble(0);
          d1 = d2;
          localCursor.close();
          continue;
        }
        localCursor.close();
        d1 = -1.0D;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static String getSquarePostActivityId(EntitySquaresData paramEntitySquaresData, boolean paramBoolean)
  {
    int i;
    EntitySquaresDataSquareSubscription localEntitySquaresDataSquareSubscription;
    if (paramEntitySquaresData.subscription != null)
    {
      i = 0;
      int j = paramEntitySquaresData.subscription.size();
      if (i < j)
      {
        localEntitySquaresDataSquareSubscription = (EntitySquaresDataSquareSubscription)paramEntitySquaresData.subscription.get(i);
        if (((localEntitySquaresDataSquareSubscription == null) || (paramBoolean)) && (PrimitiveUtils.safeBoolean(localEntitySquaresDataSquareSubscription.isRead)));
      }
    }
    for (String str = localEntitySquaresDataSquareSubscription.activityId; ; str = null)
    {
      return str;
      i++;
      break;
    }
  }

  private static long getUnreadCount(SQLiteDatabase paramSQLiteDatabase)
  {
    return paramSQLiteDatabase.compileStatement(String.format("SELECT COUNT(*) FROM %s WHERE %s", new Object[] { "notifications", "read=0 AND seen=0 AND enabled=1" })).simpleQueryForLong();
  }

  public static int getUnreadSquarePosts(EntitySquaresData paramEntitySquaresData)
  {
    if ((paramEntitySquaresData.renderSquaresData != null) && (paramEntitySquaresData.renderSquaresData.renderSubscriptionData != null));
    for (int i = PrimitiveUtils.safeInt(paramEntitySquaresData.renderSquaresData.renderSubscriptionData.numUnread); ; i = 0)
      return i;
  }

  private static void insertNotifications(Context paramContext, EsAccount paramEsAccount, List<DataCoalescedItem> paramList, double paramDouble1, double paramDouble2, boolean paramBoolean, Map<String, DataActor> paramMap)
    throws IOException
  {
    OzActions localOzActions;
    if (paramBoolean)
      localOzActions = OzActions.NOTIFICATION_FETCHED_FROM_TICKLE;
    OzViews localOzViews;
    label20: SQLiteDatabase localSQLiteDatabase;
    long l1;
    HashMap localHashMap;
    Cursor localCursor;
    while (true)
    {
      if (paramBoolean)
      {
        localOzViews = OzViews.NOTIFICATIONS_SYSTEM;
        localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      }
      try
      {
        localSQLiteDatabase.beginTransaction();
        l1 = getUnreadCount(localSQLiteDatabase);
        if (paramList.isEmpty())
        {
          if (paramDouble1 <= 0.0D)
            paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.NOTIFICATIONS_URI, paramEsAccount), null);
          localSQLiteDatabase.setTransactionSuccessful();
          Bundle localBundle2 = new Bundle();
          localBundle2.putInt("extra_num_unread_notifi", (int)l1);
          localBundle2.putInt("extra_prev_num_unread_noti", (int)l1);
          EsAnalytics.postRecordEvent(paramContext, paramEsAccount, new AnalyticsInfo(localOzViews), localOzActions, localBundle2);
          localSQLiteDatabase.endTransaction();
          return;
          localOzActions = OzActions.NOTIFICATION_FETCHED_FROM_USER_REFRESH;
          continue;
          localOzViews = OzViews.NOTIFICATIONS_WIDGET;
          break label20;
        }
        else
        {
          localHashMap = new HashMap();
          localCursor = localSQLiteDatabase.query("notifications", IdAndTimestampQuery.PROJECTION, null, null, null, null, null);
        }
      }
      finally
      {
        try
        {
          if (!localCursor.moveToNext())
            break;
          localHashMap.put(localCursor.getString(0), Double.valueOf(localCursor.getDouble(1)));
        }
        finally
        {
          localCursor.close();
        }
        localSQLiteDatabase.endTransaction();
      }
    }
    localCursor.close();
    long l2 = localSQLiteDatabase.compileStatement(String.format("SELECT MAX(%s) FROM %s", new Object[] { "timestamp", "notifications" })).simpleQueryForLong();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ContentValues localContentValues = new ContentValues();
    Iterator localIterator1 = paramList.iterator();
    label307: DataCoalescedItem localDataCoalescedItem;
    String str2;
    double d;
    boolean bool1;
    int i;
    label431: int j;
    label477: label499: boolean bool2;
    int k;
    label1184: label1353: String str5;
    String str6;
    String str7;
    Object localObject3;
    EntityUpdateData localEntityUpdateData2;
    String str9;
    label1493: PlusPhoto localPlusPhoto2;
    while (true)
      if (localIterator1.hasNext())
      {
        localDataCoalescedItem = (DataCoalescedItem)localIterator1.next();
        if ((!TextUtils.isEmpty(localDataCoalescedItem.id)) && (!TextUtils.isEmpty(localDataCoalescedItem.coalescingCode)))
        {
          String str1 = localDataCoalescedItem.id;
          str2 = localDataCoalescedItem.coalescingCode;
          d = PrimitiveUtils.safeDouble(localDataCoalescedItem.timestamp);
          bool1 = PrimitiveUtils.safeBoolean(localDataCoalescedItem.isEntityDeleted);
          String str3 = localDataCoalescedItem.category;
          if ((TextUtils.isEmpty(str3)) || (!MAP_CATEGORY.containsKey(str3)))
            break label3162;
          i = ((Integer)MAP_CATEGORY.get(str3)).intValue();
          String str4 = localDataCoalescedItem.entityReferenceType;
          if ((TextUtils.isEmpty(str4)) || (!MAP_ENTITY_TYPE.containsKey(str4)))
            break label3169;
          j = ((Integer)MAP_ENTITY_TYPE.get(str4)).intValue();
          if (PrimitiveUtils.safeBoolean(localDataCoalescedItem.isRead))
            break label3156;
          if (d > paramDouble2)
            break label3176;
          break label3156;
          if (EsLog.isLoggable("EsNotificationData", 4))
          {
            String str10 = "Notification id: " + str1 + ", coalescingCode: " + str2 + ", category: " + localDataCoalescedItem.category + ", filterType: " + localDataCoalescedItem.filterType + ", timestamp: " + d + ", read: " + bool2 + ", entityDeleted: " + bool1 + ", pushEnabled: " + localDataCoalescedItem.pushEnabled;
            if ((localDataCoalescedItem.entityData != null) && (localDataCoalescedItem.entityData.summarySnippet != null))
              str10 = str10 + ", snippet: " + localDataCoalescedItem.entityData.summarySnippet.summaryPlaintext;
            Log.i("EsNotificationData", str10);
          }
          Double localDouble = (Double)localHashMap.get(str1);
          if ((localDouble != null) && (localDouble.doubleValue() == d) && (!bool2))
          {
            if (EsLog.isLoggable("EsNotificationData", 4))
              Log.i("EsNotificationData", "Ignore notification with same timestamp and not read. Id: " + str1);
          }
          else
          {
            localContentValues.clear();
            localContentValues.put("notif_id", str1);
            localContentValues.put("coalescing_code", str2);
            localContentValues.put("timestamp", Double.valueOf(d));
            localContentValues.put("entity_type", Integer.valueOf(j));
            if ((i == 1) && (localDataCoalescedItem.entityReference != null))
              localContentValues.put("activity_id", localDataCoalescedItem.entityReference);
            List localList1 = localDataCoalescedItem.action;
            k = 0;
            if (localList1 != null)
            {
              int i2 = localDataCoalescedItem.action.size();
              k = 0;
              if (i2 > 0)
              {
                Iterator localIterator3 = localDataCoalescedItem.action.iterator();
                while (localIterator3.hasNext())
                {
                  DataAction localDataAction = (DataAction)localIterator3.next();
                  if ((localDataAction != null) && (localDataAction.item != null) && (!localDataAction.item.isEmpty()))
                  {
                    ArrayList localArrayList4 = new ArrayList();
                    Iterator localIterator4 = localDataAction.item.iterator();
                    while (localIterator4.hasNext())
                    {
                      DataActor localDataActor2 = ((DataItem)localIterator4.next()).actor;
                      if (localDataActor2 != null)
                      {
                        if (localDataActor2.photoUrl != null)
                          localDataActor2.photoUrl = EsAvatarData.compressAvatarUrl(localDataActor2.photoUrl);
                        localArrayList4.add(localDataActor2);
                        if (EsLog.isLoggable("EsNotificationData", 4))
                          Log.i("EsNotificationData", "- Actor name: " + localDataActor2.name + " gaiaId: " + localDataActor2.obfuscatedGaiaId + " photoUrl: " + localDataActor2.photoUrl);
                      }
                    }
                    k = getNotificationType(((DataItem)localDataAction.item.get(0)).notificationType);
                  }
                }
                localContentValues.put("circle_data", DbDataAction.serializeDataActionList(localDataCoalescedItem.action));
              }
            }
            localContentValues.put("notification_type", Integer.valueOf(k));
            localContentValues.put("read", Boolean.valueOf(bool2));
            localContentValues.put("seen", Integer.valueOf(0));
            Iterator localIterator2;
            if (localDataCoalescedItem.pushEnabled != null)
            {
              localContentValues.put("enabled", localDataCoalescedItem.pushEnabled);
              if (!isEventNotificationType(k))
                break label3182;
              i = 10;
              break label3182;
              if ((k == 18) && (localDataCoalescedItem.opaqueClientFields != null))
                localIterator2 = localDataCoalescedItem.opaqueClientFields.iterator();
            }
            else
            {
              while (true)
              {
                if (!localIterator2.hasNext())
                  break label1353;
                DataKvPair localDataKvPair = (DataKvPair)localIterator2.next();
                if ((TextUtils.equals("TAGGEE_OGIDS", localDataKvPair.key)) && (!TextUtils.isEmpty(localDataKvPair.value)))
                {
                  List localList2 = PhotoTaggeeData.createDataActorList(paramMap, localDataKvPair.value);
                  if (localList2.isEmpty())
                    continue;
                  localContentValues.put("taggee_data_actors", DbDataAction.serializeDataActorList(localList2));
                  continue;
                  localContentValues.put("enabled", Boolean.valueOf(bool2));
                  break;
                }
                if ((TextUtils.equals("TAGGEE_PHOTO_IDS", localDataKvPair.key)) && (!TextUtils.isEmpty(localDataKvPair.value)))
                  localContentValues.put("taggee_photo_ids", localDataKvPair.value);
              }
            }
            EntityEntityData localEntityEntityData = localDataCoalescedItem.entityData;
            str5 = null;
            if (localEntityEntityData != null)
            {
              EntitySummaryData localEntitySummaryData = localDataCoalescedItem.entityData.summarySnippet;
              str5 = null;
              if (localEntitySummaryData != null)
                str5 = localDataCoalescedItem.entityData.summarySnippet.summaryPlaintext;
              EntityUpdateData localEntityUpdateData1 = localDataCoalescedItem.entityData.update;
              str6 = null;
              str7 = null;
              localObject3 = null;
              if (localEntityUpdateData1 != null)
              {
                localEntityUpdateData2 = localDataCoalescedItem.entityData.update;
                if (localEntityUpdateData2.activity != null)
                {
                  ArrayList localArrayList3 = new ArrayList(1);
                  localArrayList3.add(localEntityUpdateData2.activity);
                  EsPostsData.insertActivitiesAndOverwrite(paramContext, paramEsAccount, null, localArrayList3, "DEFAULT");
                }
                if (TextUtils.isEmpty(localEntityUpdateData2.safeAnnotationHtml))
                  break label2482;
                str9 = localEntityUpdateData2.safeAnnotationHtml;
                if (!TextUtils.isEmpty(str9))
                  localContentValues.put("entity_snippet", str9);
                Update localUpdate = localDataCoalescedItem.entityData.update.activity;
                str6 = null;
                str7 = null;
                localObject3 = null;
                if (localUpdate != null)
                {
                  EmbedClientItem localEmbedClientItem = localDataCoalescedItem.entityData.update.activity.embed;
                  str6 = null;
                  str7 = null;
                  localObject3 = null;
                  if (localEmbedClientItem != null)
                  {
                    PlusPhoto localPlusPhoto1 = localDataCoalescedItem.entityData.update.activity.embed.plusPhoto;
                    str6 = null;
                    str7 = null;
                    localObject3 = null;
                    if (localPlusPhoto1 != null)
                    {
                      localPlusPhoto2 = localDataCoalescedItem.entityData.update.activity.embed.plusPhoto;
                      str6 = localPlusPhoto2.ownerObfuscatedId;
                      str7 = localPlusPhoto2.albumId;
                    }
                  }
                }
              }
            }
          }
        }
      }
    label3201: label3211: label3217: label3223: label3229: 
    while (true)
    {
      EntitySquaresData localEntitySquaresData;
      String str8;
      int m;
      int n;
      try
      {
        boolean bool4 = TextUtils.isEmpty(localPlusPhoto2.photoId);
        localObject3 = null;
        if (!bool4)
        {
          Long localLong2 = Long.valueOf(Long.parseLong(localPlusPhoto2.photoId));
          localObject3 = localLong2;
        }
        localEntityPhotosData = localDataCoalescedItem.entityData.photos;
        if ((i == 3) && (localEntityPhotosData != null))
        {
          localContentValues.put("entity_photos_data", EntityPhotosDataJson.getInstance().toByteArray(localEntityPhotosData));
          if ((localEntityPhotosData.photo != null) && (!localEntityPhotosData.photo.isEmpty()))
          {
            localDataPhoto = (DataPhoto)localEntityPhotosData.photo.get(0);
            if (localDataPhoto == null);
          }
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        try
        {
          EntityPhotosData localEntityPhotosData;
          DataPhoto localDataPhoto;
          if (!TextUtils.isEmpty(localDataPhoto.id))
          {
            Long localLong1 = Long.valueOf(Long.parseLong(localDataPhoto.id));
            localObject3 = localLong1;
          }
          if (localDataPhoto.album != null)
            str7 = localDataPhoto.album.id;
          if (localDataPhoto.owner != null)
            str6 = localDataPhoto.owner.id;
          if ((k == 18) && (!bool2))
          {
            if (EsLog.isLoggable("EsNotificationData", 3))
            {
              int i1 = localEntityPhotosData.numPhotos.intValue() + localEntityPhotosData.numVideos.intValue();
              Log.d("EsNotificationData", "Insert " + i1 + " IU photos into the photo table!");
            }
            EsPhotosDataApiary.insertStreamPhotos(paramContext, paramEsAccount, null, "camerasync", str6, localEntityPhotosData.photo, true);
          }
          if ((EsLog.isLoggable("EsNotificationData", 4)) && ((!TextUtils.isEmpty(str6)) || (!TextUtils.isEmpty(str7)) || (localObject3 != null)))
            Log.i("EsNotificationData", "- Photo ownerId: " + str6 + " albumId: " + str7 + " photoId: " + localObject3);
          if (!TextUtils.isEmpty(str6))
            localContentValues.put("pd_gaia_id", str6);
          if (!TextUtils.isEmpty(str7))
            localContentValues.put("pd_album_id", str7);
          if (localObject3 != null)
            localContentValues.put("pd_photo_id", localObject3);
          if (localDataCoalescedItem.entityData.squares != null)
          {
            localEntitySquaresData = localDataCoalescedItem.entityData.squares;
            localContentValues.put("entity_squares_data", EntitySquaresDataJson.getInstance().toByteArray(localEntitySquaresData));
            if ((localEntitySquaresData.invite == null) || (localEntitySquaresData.invite.size() <= 0))
              continue;
            EntitySquaresDataSquareInvite localEntitySquaresDataSquareInvite = (EntitySquaresDataSquareInvite)localEntitySquaresData.invite.get(0);
            if ((localEntitySquaresDataSquareInvite == null) || (localEntitySquaresDataSquareInvite.square == null))
              continue;
            str8 = localEntitySquaresDataSquareInvite.square.oid;
            if (EsLog.isLoggable("EsNotificationData", 3))
              Log.d("EsNotificationData", "- squareId: " + str8);
            if (!TextUtils.isEmpty(str8))
            {
              localContentValues.put("square_id", str8);
              DataActor localDataActor1 = (DataActor)paramMap.get(str8);
              if (localDataActor1 != null)
              {
                localContentValues.put("square_name", localDataActor1.name);
                localContentValues.put("square_photo_url", EsAvatarData.compressAvatarUrl(localDataActor1.photoUrl));
              }
            }
          }
          if ((localDataCoalescedItem.entityData.update != null) && (localDataCoalescedItem.entityData.update.activity != null) && (localDataCoalescedItem.entityData.update.activity.embed != null) && (localDataCoalescedItem.entityData.update.activity.embed.plusEvent != null))
          {
            PlusEvent localPlusEvent = localDataCoalescedItem.entityData.update.activity.embed.plusEvent;
            localContentValues.put("ed_event_id", localPlusEvent.id);
            localContentValues.put("ed_creator_id", localPlusEvent.creatorObfuscatedId);
            if ((58 != k) || (!TextUtils.equals(localPlusEvent.id, InstantUpload.getInstantShareEventId(paramContext))))
              break label3201;
            localContentValues.put("read", Boolean.valueOf(true));
            break label3201;
          }
          if ((TextUtils.isEmpty(str5)) || (bool1))
          {
            if (m == 0)
              break label3005;
            str5 = paramContext.getString(R.string.notification_event_deleted);
          }
          localContentValues.put("message", str5);
          if (m == 0)
            break label3229;
          n = 1;
          localContentValues.put("ed_event", Integer.valueOf(n));
          localContentValues.put("category", Integer.valueOf(i));
          localSQLiteDatabase.insertWithOnConflict("notifications", "coalescing_code", localContentValues, 5);
          if (d <= l2)
            break label307;
          localArrayList1.add(Integer.valueOf(k));
          localArrayList2.add(str2);
          break label307;
          label2482: if (!TextUtils.isEmpty(localEntityUpdateData2.safeTitleHtml))
          {
            str9 = localEntityUpdateData2.safeTitleHtml;
            break label1493;
          }
          if (localEntityUpdateData2.summary == null)
            break label3217;
          EntityUpdateDataSummarySnippet localEntityUpdateDataSummarySnippet = localEntityUpdateData2.summary;
          if (!TextUtils.isEmpty(localEntityUpdateDataSummarySnippet.bodySanitizedHtml))
          {
            str9 = localEntityUpdateDataSummarySnippet.bodySanitizedHtml;
            break label1493;
          }
          if (!TextUtils.isEmpty(localEntityUpdateDataSummarySnippet.activityContentSanitizedHtml))
          {
            str9 = localEntityUpdateDataSummarySnippet.activityContentSanitizedHtml;
            break label1493;
          }
          if (TextUtils.isEmpty(localEntityUpdateDataSummarySnippet.headerSanitizedHtml))
            break label3211;
          str9 = localEntityUpdateDataSummarySnippet.activityContentSanitizedHtml;
          break label1493;
          localNumberFormatException2 = localNumberFormatException2;
          boolean bool3 = EsLog.isLoggable("EsNotificationData", 6);
          localObject3 = null;
          if (!bool3)
            continue;
          Log.e("EsNotificationData", "Invalid photoId " + localNumberFormatException2);
          localObject3 = null;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          if (!EsLog.isLoggable("EsNotificationData", 6))
            continue;
          Log.e("EsNotificationData", "Invalid photoId " + localNumberFormatException1);
          continue;
          if ((localEntitySquaresData.membershipApproved != null) && (localEntitySquaresData.membershipApproved.size() > 0))
          {
            EntitySquaresDataSquareMembershipApproved localEntitySquaresDataSquareMembershipApproved = (EntitySquaresDataSquareMembershipApproved)localEntitySquaresData.membershipApproved.get(0);
            if ((localEntitySquaresDataSquareMembershipApproved != null) && (localEntitySquaresDataSquareMembershipApproved.square != null))
            {
              str8 = localEntitySquaresDataSquareMembershipApproved.square.oid;
              continue;
            }
          }
          if ((localEntitySquaresData.membershipRequest != null) && (localEntitySquaresData.membershipRequest.size() > 0))
          {
            EntitySquaresDataSquareMembershipRequest localEntitySquaresDataSquareMembershipRequest = (EntitySquaresDataSquareMembershipRequest)localEntitySquaresData.membershipRequest.get(0);
            if ((localEntitySquaresDataSquareMembershipRequest != null) && (localEntitySquaresDataSquareMembershipRequest.square != null))
            {
              str8 = localEntitySquaresDataSquareMembershipRequest.square.oid;
              continue;
            }
          }
          if ((localEntitySquaresData.newModerator != null) && (localEntitySquaresData.newModerator.size() > 0))
          {
            EntitySquaresDataNewModerator localEntitySquaresDataNewModerator = (EntitySquaresDataNewModerator)localEntitySquaresData.newModerator.get(0);
            if (localEntitySquaresDataNewModerator != null)
            {
              str8 = localEntitySquaresDataNewModerator.squareOid;
              continue;
            }
          }
          if ((localEntitySquaresData.squareNameChange != null) && (localEntitySquaresData.squareNameChange.size() > 0))
          {
            EntitySquaresDataSquareNameChange localEntitySquaresDataSquareNameChange = (EntitySquaresDataSquareNameChange)localEntitySquaresData.squareNameChange.get(0);
            if (localEntitySquaresDataSquareNameChange != null)
            {
              str8 = localEntitySquaresDataSquareNameChange.squareOid;
              continue;
            }
          }
          if ((localEntitySquaresData.subscription != null) && (localEntitySquaresData.subscription.size() > 0))
          {
            EntitySquaresDataSquareSubscription localEntitySquaresDataSquareSubscription = (EntitySquaresDataSquareSubscription)localEntitySquaresData.subscription.get(0);
            if ((localEntitySquaresDataSquareSubscription != null) && (localEntitySquaresDataSquareSubscription.square != null))
            {
              str8 = localEntitySquaresDataSquareSubscription.square.oid;
              continue;
            }
          }
          if (!EsLog.isLoggable("EsNotificationData", 6))
            break label3223;
        }
      }
      Log.e("EsNotificationData", "No Square ID in notification:\n" + EntitySquaresDataJson.getInstance().toPrettyString(localEntitySquaresData));
      label3005: if (i == 3)
      {
        str5 = paramContext.getString(R.string.notification_photo_deleted);
      }
      else
      {
        str5 = paramContext.getString(R.string.notification_post_deleted);
        continue;
        Bundle localBundle1 = new Bundle();
        if ((!localArrayList1.isEmpty()) && (localArrayList1.size() == localArrayList2.size()))
        {
          localBundle1.putIntegerArrayList("extra_notification_types", localArrayList1);
          localBundle1.putStringArrayList("extra_coalescing_codes", localArrayList2);
        }
        localBundle1.putInt("extra_num_unread_notifi", (int)getUnreadCount(localSQLiteDatabase));
        localBundle1.putInt("extra_prev_num_unread_noti", (int)l1);
        EsAnalytics.postRecordEvent(paramContext, paramEsAccount, new AnalyticsInfo(localOzViews), localOzActions, localBundle1);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.NOTIFICATIONS_URI, paramEsAccount), null);
        break;
        label3156: bool2 = true;
        break label499;
        label3162: i = 65535;
        break label431;
        label3169: j = 65535;
        break label477;
        label3176: bool2 = false;
        break label499;
        label3182: if (i == 10)
        {
          m = 1;
          break label1184;
        }
        m = 0;
        break label1184;
        i = 10;
        m = 1;
        continue;
        str9 = null;
        break label1493;
        str9 = null;
        break label1493;
        str8 = null;
        continue;
        n = 0;
      }
    }
  }

  public static boolean isCommentNotificationType(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 2:
    case 3:
    case 14:
    case 15:
    case 25:
    case 26:
    }
    for (boolean bool = false; ; bool = true)
      return bool;
  }

  public static boolean isEventNotificationType(int paramInt)
  {
    switch (paramInt)
    {
    case 48:
    case 49:
    case 50:
    case 51:
    case 52:
    case 60:
    case 61:
    case 63:
    case 64:
    case 65:
    case 66:
    default:
    case 47:
    case 53:
    case 54:
    case 55:
    case 56:
    case 57:
    case 58:
    case 59:
    case 62:
    case 67:
    }
    for (boolean bool = false; ; bool = true)
      return bool;
  }

  public static void markAllNotificationsAsRead(Context paramContext, EsAccount paramEsAccount)
  {
    if (EsLog.isLoggable("EsNotificationData", 4))
      Log.i("EsNotificationData", "markAllNotificationsAsRead");
    if (paramEsAccount == null)
      Log.e("EsNotificationData", "markAllNotificationsAsRead: The account cannot be null");
    while (true)
    {
      return;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      ContentValues localContentValues = new ContentValues(1);
      localContentValues.put("read", Integer.valueOf(1));
      localSQLiteDatabase.update("notifications", localContentValues, null, null);
      paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.NOTIFICATIONS_URI, paramEsAccount), null);
      AndroidNotification.cancel(paramContext, paramEsAccount, 1);
    }
  }

  public static void markAllNotificationsAsSeen(Context paramContext, EsAccount paramEsAccount)
  {
    if (EsLog.isLoggable("EsNotificationData", 4))
      Log.i("EsNotificationData", "markAllNotificationsAsSeen");
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues(1);
    localContentValues.put("seen", Integer.valueOf(1));
    localSQLiteDatabase.update("notifications", localContentValues, null, null);
  }

  public static void markNotificationAsRead(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    if (EsLog.isLoggable("EsNotificationData", 4))
      Log.i("EsNotificationData", "markNotificationAsRead: " + paramString);
    if (paramEsAccount == null)
      Log.e("EsNotificationData", "markNotificationAsRead: The account cannot be null");
    while (true)
    {
      return;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      ContentValues localContentValues = new ContentValues(2);
      localContentValues.put("read", Integer.valueOf(1));
      localSQLiteDatabase.update("notifications", localContentValues, "notif_id=?", new String[] { paramString });
      paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.NOTIFICATIONS_URI, paramEsAccount), null);
    }
  }

  public static void syncNotifications(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, HttpOperation.OperationListener paramOperationListener, boolean paramBoolean)
    throws IOException
  {
    while (true)
    {
      double d1;
      Object localObject3;
      HashMap localHashMap1;
      HashMap localHashMap2;
      double d2;
      int i;
      int k;
      GetNotificationsOperation localGetNotificationsOperation;
      double d3;
      synchronized (mSyncLock)
      {
        if (paramSyncState.isCanceled())
          break label802;
        if (EsLog.isLoggable("EsNotificationData", 4))
          Log.i("EsNotificationData", "syncNotifications starting sync stream");
        paramSyncState.onStart("Notifications");
        d1 = getOldestUnreadNotificationTimestamp(paramContext, paramEsAccount);
        if (d1 < 0.0D)
        {
          d1 = getLatestNotificationTimestamp(paramContext, paramEsAccount);
          if (!EsLog.isLoggable("EsNotificationData", 4))
            break label803;
          Log.i("EsNotificationData", "syncNotifications latest notification: " + d1);
          break label803;
          localObject3 = null;
          localHashMap1 = new HashMap();
          localHashMap2 = new HashMap();
          d2 = 0.0D;
          i = 0;
          int j = i;
          k = 0;
          if (j < 3)
          {
            if (EsLog.isLoggable("EsNotificationData", 4))
              Log.i("EsNotificationData", "syncNotifications continuation token: " + (String)localObject3 + ", chunk: " + i);
            localGetNotificationsOperation = new GetNotificationsOperation(paramContext, paramEsAccount, null, paramOperationListener);
            if (i != 0)
              break label825;
            d3 = d1;
            localGetNotificationsOperation.getNotifications(d3, (String)localObject3);
            localGetNotificationsOperation.start(paramSyncState, new HttpTransactionMetrics());
            if (!localGetNotificationsOperation.hasError())
              break label403;
            if (!EsLog.isLoggable("EsNotificationData", 4))
              break label819;
            Log.i("EsNotificationData", "syncNotifications error in chunk: " + i + " with error code " + localGetNotificationsOperation.getErrorCode());
            break label819;
          }
          if (k == 0)
          {
            ArrayList localArrayList = new ArrayList(localHashMap1.values());
            Collections.sort(localArrayList, new Comparator()
            {
            });
            insertNotifications(paramContext, paramEsAccount, localArrayList, d1, d2, paramBoolean, localHashMap2);
            AndroidNotification.update(paramContext, paramEsAccount);
          }
          paramSyncState.onFinish(localHashMap1.size());
        }
      }
      if (EsLog.isLoggable("EsNotificationData", 4))
      {
        Log.i("EsNotificationData", "syncNotifications oldest unread time: " + d1);
        continue;
        label403: d2 = PrimitiveUtils.safeDouble(localGetNotificationsOperation.getLastReadTime());
        List localList1 = localGetNotificationsOperation.getDataActors();
        if (localList1 != null)
        {
          Iterator localIterator2 = localList1.iterator();
          while (localIterator2.hasNext())
          {
            DataActor localDataActor = (DataActor)localIterator2.next();
            if ((localDataActor != null) && (localDataActor.obfuscatedGaiaId != null))
              localHashMap2.put(localDataActor.obfuscatedGaiaId, localDataActor);
          }
        }
        List localList2 = localGetNotificationsOperation.getNotifications();
        k = 0;
        if (localList2 != null)
        {
          int m = localList2.size();
          k = 0;
          if (m != 0)
          {
            if (EsLog.isLoggable("EsNotificationData", 4))
              Log.i("EsNotificationData", "syncNotifications retrieved: " + localList2.size());
            Iterator localIterator1 = localList2.iterator();
            while (localIterator1.hasNext())
            {
              DataCoalescedItem localDataCoalescedItem1 = (DataCoalescedItem)localIterator1.next();
              String str2 = localDataCoalescedItem1.id;
              DataCoalescedItem localDataCoalescedItem2 = (DataCoalescedItem)localHashMap1.get(str2);
              if ((localDataCoalescedItem2 != null) && (localDataCoalescedItem1.timestamp != null) && (localDataCoalescedItem2.timestamp != null))
              {
                if (localDataCoalescedItem1.timestamp.doubleValue() > localDataCoalescedItem2.timestamp.doubleValue())
                {
                  localHashMap1.put(str2, localDataCoalescedItem1);
                  if (EsLog.isLoggable("EsNotificationData", 4))
                    Log.i("EsNotificationData", "syncNotifications replacing notification: " + str2 + " is newer: " + localDataCoalescedItem1.timestamp);
                }
                else if (EsLog.isLoggable("EsNotificationData", 4))
                {
                  Log.i("EsNotificationData", "syncNotifications ignoring notification: " + str2 + " is older: " + localDataCoalescedItem1.timestamp);
                }
              }
              else
                localHashMap1.put(str2, localDataCoalescedItem1);
            }
            String str1 = localGetNotificationsOperation.getContinuationToken();
            localObject3 = str1;
            k = 0;
            if (localObject3 != null)
            {
              i++;
              continue;
              label802: return;
              label803: if (d1 > 0.0D)
              {
                d1 += 1.0D;
                continue;
                label819: k = 1;
                continue;
                label825: d3 = 0.0D;
              }
            }
          }
        }
      }
    }
  }

  private static abstract interface IdAndTimestampQuery
  {
    public static final String[] PROJECTION = { "notif_id", "timestamp" };
  }

  private static abstract interface NotificationIdsQuery
  {
    public static final String[] PROJECTION = { "notif_id" };
  }

  public static abstract interface NotificationQuery
  {
    public static final String[] PROJECTION = { "_id", "notif_id", "coalescing_code", "category", "message", "timestamp", "circle_data", "pd_gaia_id", "pd_album_id", "pd_photo_id", "activity_id", "read", "ed_event", "ed_event_id", "ed_creator_id", "notification_type", "entity_type", "entity_snippet", "entity_photos_data", "entity_squares_data", "square_id", "square_name", "square_photo_url", "taggee_photo_ids", "taggee_data_actors" };
  }

  private static abstract interface NotificationTimestampsQuery
  {
    public static final String[] PROJECTION = { "timestamp" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsNotificationData
 * JD-Core Version:    0.6.2
 */