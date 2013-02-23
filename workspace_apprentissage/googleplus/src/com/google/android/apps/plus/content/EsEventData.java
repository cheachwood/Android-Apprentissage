package com.google.android.apps.plus.content;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.DownloadImageOperation;
import com.google.android.apps.plus.api.EventReadOperation;
import com.google.android.apps.plus.api.GetEventOperation;
import com.google.android.apps.plus.api.GetEventThemesOperation;
import com.google.android.apps.plus.api.GetPhotoOperation;
import com.google.android.apps.plus.api.SendEventRsvpOperation;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.NotificationUtils;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.DataImage;
import com.google.api.services.plusi.model.DataPhoto;
import com.google.api.services.plusi.model.DataPhotoJson;
import com.google.api.services.plusi.model.EmbedsPerson;
import com.google.api.services.plusi.model.EventCategory;
import com.google.api.services.plusi.model.EventOptions;
import com.google.api.services.plusi.model.EventTime;
import com.google.api.services.plusi.model.Invitee;
import com.google.api.services.plusi.model.InviteeJson;
import com.google.api.services.plusi.model.InviteeSummary;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;
import com.google.api.services.plusi.model.Theme;
import com.google.api.services.plusi.model.ThemeImage;
import com.google.api.services.plusi.model.Update;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EsEventData
{
  public static final EsJson<EventActivity> EVENT_ACTIVITY_JSON;
  public static final EsJson<EventCoalescedFrame> EVENT_COALESCED_FRAME_JSON = EsJson.buildJson(EventCoalescedFrame.class, arrayOfObject);
  public static final EsJson<EventComment> EVENT_COMMENT_JSON;
  public static final EsJson<EventPerson> EVENT_PERSON_JSON;
  private static final String[] EVENT_QUERY_PROJECTION;
  public static final EsJson<InviteeList> INVITEE_LIST_JSON;
  private static final String[] SYNC_QUERY_PROJECTION;
  private static final Object mSyncLock;
  private static final Object sEventOperationSyncObject = new Object();
  private static File sEventThemePlaceholderDir;
  private static Object sEventThemesLock = new Object();

  static
  {
    mSyncLock = new Object();
    SYNC_QUERY_PROJECTION = new String[] { "event_id", "polling_token", "resume_token", "event_data" };
    EVENT_QUERY_PROJECTION = new String[] { "event_data" };
    EVENT_ACTIVITY_JSON = EsJson.getSimpleJson(EventActivity.class);
    EVENT_COMMENT_JSON = EsJson.getSimpleJson(EventComment.class);
    EVENT_PERSON_JSON = EsJson.getSimpleJson(EventPerson.class);
    INVITEE_LIST_JSON = EsJson.buildJson(InviteeList.class, new Object[] { InviteeJson.class, "invitees" });
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = EVENT_PERSON_JSON;
    arrayOfObject[1] = "people";
  }

  public static boolean canAddPhotos(PlusEvent paramPlusEvent, String paramString)
  {
    boolean bool1 = false;
    if (paramPlusEvent == null);
    while (true)
    {
      return bool1;
      if ((!TextUtils.equals(paramString, paramPlusEvent.creatorObfuscatedId)) && (paramPlusEvent.eventOptions != null) && (paramPlusEvent.eventOptions.openPhotoAcl != null))
      {
        boolean bool2 = paramPlusEvent.eventOptions.openPhotoAcl.booleanValue();
        bool1 = false;
        if (!bool2);
      }
      else
      {
        bool1 = true;
      }
    }
  }

  public static boolean canInviteOthers(PlusEvent paramPlusEvent, EsAccount paramEsAccount)
  {
    boolean bool1 = true;
    if ((paramPlusEvent == null) || (paramEsAccount == null))
      bool1 = false;
    label47: label106: label110: 
    while (true)
    {
      return bool1;
      if (!TextUtils.equals(paramPlusEvent.creatorObfuscatedId, paramEsAccount.getGaiaId()))
      {
        boolean bool2;
        if ((paramPlusEvent.viewerInfo != null) && (paramPlusEvent.viewerInfo.inviter != null))
        {
          bool2 = bool1;
          if ((paramPlusEvent.eventOptions != null) && ((paramPlusEvent.eventOptions == null) || (paramPlusEvent.eventOptions.openEventAcl == null) || (!paramPlusEvent.eventOptions.openEventAcl.booleanValue())))
            break label106;
        }
        for (boolean bool3 = bool1; ; bool3 = false)
        {
          if ((bool2) && (bool3))
            break label110;
          bool1 = false;
          break;
          bool2 = false;
          break label47;
        }
      }
    }
  }

  public static boolean canRsvp(PlusEvent paramPlusEvent)
  {
    if ((paramPlusEvent.isBroadcastView == null) || (!paramPlusEvent.isBroadcastView.booleanValue()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean canViewerAddPhotos(PlusEvent paramPlusEvent)
  {
    if ((paramPlusEvent != null) && (paramPlusEvent.viewerInfo != null) && (paramPlusEvent.viewerInfo.canUploadPhotos != null) && (paramPlusEvent.viewerInfo.canUploadPhotos.booleanValue()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static void copyRsvpFromSummary(PlusEvent paramPlusEvent, EsAccount paramEsAccount)
  {
    if ((paramPlusEvent.inviteeSummary != null) && (paramPlusEvent.inviteeSummary.size() > 0))
    {
      Iterator localIterator = paramPlusEvent.inviteeSummary.iterator();
      while (localIterator.hasNext())
      {
        InviteeSummary localInviteeSummary = (InviteeSummary)localIterator.next();
        if ((localInviteeSummary.setByViewer != null) && (localInviteeSummary.setByViewer.booleanValue()) && (localInviteeSummary.rsvpType != null) && (!TextUtils.equals(localInviteeSummary.rsvpType, "INVITED")))
          setViewerInfoRsvp(paramPlusEvent, paramEsAccount, localInviteeSummary.rsvpType);
      }
    }
  }

  public static void deleteEvent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    int i = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase().delete("events", "event_id=?", new String[] { paramString });
    boolean bool = TextUtils.equals(paramString, InstantUpload.getInstantShareEventId(paramContext));
    int j = 0;
    if (bool)
    {
      j = 1;
      disableInstantShare(paramContext);
    }
    if (i > 0)
      paramContext.getContentResolver().notifyChange(EsProvider.EVENTS_ALL_URI, null);
    StringBuilder localStringBuilder;
    if (EsLog.isLoggable("EsEventData", 3))
    {
      localStringBuilder = new StringBuilder("[DELETE_EVENT], id: ").append(paramString);
      if (j == 0)
        break label114;
    }
    label114: for (String str = "; disable IS"; ; str = "")
    {
      Log.d("EsEventData", str);
      return;
    }
  }

  public static void disableInstantShare(Context paramContext)
  {
    if (EsLog.isLoggable("EsEventData", 4))
      Log.i("EsEventData", "#disableInstantShare; now: " + System.currentTimeMillis());
    enableInstantShareInternal(paramContext, null, null, null, null, 0L, 0L);
  }

  public static void enableInstantShare(Context paramContext, boolean paramBoolean, PlusEvent paramPlusEvent)
  {
    if (EsLog.isLoggable("EsEventData", 4))
      Log.i("EsEventData", "#enableInstantShare; event: " + paramPlusEvent.id);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    PendingIntent localPendingIntent = Intents.getEventFinishedIntent(paramContext, paramPlusEvent.id);
    long l1 = getEventEndTime(paramPlusEvent);
    long l2 = System.currentTimeMillis();
    localAlarmManager.cancel(localPendingIntent);
    if ((paramBoolean) && (5000L + l2 < l1))
    {
      if (EsLog.isLoggable("EsEventData", 4))
        Log.i("EsEventData", "#enableInstantShare; start IS; now: " + l2 + ", end: " + l1 + ", wake in: " + (l1 - l2));
      enableInstantShareInternal(paramContext, EsAccountsData.getActiveAccount(paramContext), paramPlusEvent.id, paramPlusEvent.creatorObfuscatedId, paramPlusEvent.name, l2, l1);
      localAlarmManager.set(0, l1, localPendingIntent);
    }
    while (true)
    {
      return;
      if (EsLog.isLoggable("EsEventData", 4))
        Log.i("EsEventData", "#enableInstantShare; event over; now: " + l2 + ", end: " + l1);
      disableInstantShare(paramContext);
    }
  }

  private static void enableInstantShareInternal(Context paramContext, final EsAccount paramEsAccount, final String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    ContentValues localContentValues = new ContentValues();
    int i;
    final String str;
    if (paramString1 != null)
    {
      i = 1;
      str = InstantUpload.getInstantShareEventId(paramContext);
      if (i != 0)
      {
        localContentValues.put("auto_upload_account_name", paramEsAccount.getName());
        localContentValues.put("auto_upload_account_type", "com.google");
      }
      localContentValues.put("instant_share_eventid", paramString1);
      localContentValues.put("instant_share_starttime", Long.valueOf(paramLong1));
      localContentValues.put("instant_share_endtime", Long.valueOf(paramLong2));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      if (i == 0)
        break label172;
      NotificationUtils.notifyInstantShareEnabled(paramContext, paramString3, Intents.getViewEventActivityNotificationIntent(paramContext, paramEsAccount, paramString1, paramString2));
      InstantUpload.ensureSyncEnabled$1f9c1b47(paramEsAccount);
    }
    while (true)
    {
      InstantUpload.startMonitoring(paramContext);
      if (!TextUtils.equals(paramString1, str))
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public final void run()
          {
            OzViews localOzViews = OzViews.getViewForLogging(this.val$context);
            if (!TextUtils.isEmpty(str))
              EsAnalytics.recordActionEvent(this.val$context, paramEsAccount, OzActions.EVENTS_PARTY_MODE_OFF, localOzViews);
            if (!TextUtils.isEmpty(paramString1))
              EsAnalytics.recordActionEvent(this.val$context, paramEsAccount, OzActions.EVENTS_PARTY_MODE_ON, localOzViews);
          }
        });
      return;
      i = 0;
      break;
      label172: NotificationUtils.cancelInstantShareEnabled(paramContext);
    }
  }

  private static void ensureFreshEventThemes(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    if ((paramSyncState == null) || (!paramSyncState.isCanceled()));
    synchronized (sEventThemesLock)
    {
      try
      {
        long l2 = DatabaseUtils.longForQuery(EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase(), "SELECT event_themes_sync_time  FROM account_status", null);
        l1 = l2;
        if (System.currentTimeMillis() - l1 > 86400000L)
        {
          GetEventThemesOperation localGetEventThemesOperation = new GetEventThemesOperation(paramContext, paramEsAccount, null, null);
          localGetEventThemesOperation.start();
          if (localGetEventThemesOperation.hasError())
            localGetEventThemesOperation.logError("EsEventData");
        }
        return;
      }
      catch (SQLiteDoneException localSQLiteDoneException)
      {
        while (true)
          long l1 = -1L;
      }
    }
  }

  public static long getDisplayTime(Context paramContext, EsAccount paramEsAccount, PlusEvent paramPlusEvent)
  {
    long l1;
    if (paramPlusEvent == null)
      l1 = 0L;
    while (true)
    {
      return l1;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramPlusEvent.id;
      l1 = 0L;
      Cursor localCursor = localSQLiteDatabase.query("events", new String[] { "display_time" }, "event_id=?", arrayOfString, null, null, null);
      try
      {
        if (localCursor.moveToFirst())
        {
          long l2 = localCursor.getLong(0);
          l1 = l2;
        }
        localCursor.close();
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static Cursor getEvent(Context paramContext, EsAccount paramEsAccount, String paramString, String[] paramArrayOfString)
  {
    return EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("events", paramArrayOfString, "event_id=?", new String[] { paramString }, null, null, null);
  }

  public static Cursor getEventActivities(Context paramContext, EsAccount paramEsAccount, String paramString, String[] paramArrayOfString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    long l1 = 0L;
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT display_time FROM events WHERE event_id = ?", new String[] { paramString });
      l1 = l2;
      label35: String[] arrayOfString = new String[2];
      arrayOfString[0] = paramString;
      arrayOfString[1] = Long.toString(l1);
      return localSQLiteDatabase.query("event_activities", paramArrayOfString, "event_id = ? AND timestamp >= ?", arrayOfString, null, null, "timestamp DESC");
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      break label35;
    }
  }

  public static long getEventEndTime(PlusEvent paramPlusEvent)
  {
    if ((paramPlusEvent.endTime == null) || (paramPlusEvent.endTime.timeMs == null));
    for (long l = 7200000L + paramPlusEvent.startTime.timeMs.longValue(); ; l = paramPlusEvent.endTime.timeMs.longValue())
      return l;
  }

  public static boolean getEventFromServer(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    synchronized (sEventOperationSyncObject)
    {
      GetEventOperation localGetEventOperation = new GetEventOperation(paramContext, paramEsAccount, paramString1, paramString2, null, null);
      localGetEventOperation.start();
      if (localGetEventOperation.hasError())
        localGetEventOperation.logError("EsEventData");
      if (!localGetEventOperation.hasError())
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
  }

  public static String getEventName(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      String str2 = DatabaseUtils.stringForQuery(localSQLiteDatabase, "SELECT name FROM events WHERE event_id = ?", new String[] { paramString });
      str1 = str2;
      return str1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        String str1 = null;
    }
  }

  public static Cursor getEventResolvedPeople(Context paramContext, EsAccount paramEsAccount, String paramString, String[] paramArrayOfString)
  {
    return EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("event_people_view", paramArrayOfString, "event_id = ?", new String[] { paramString }, null, null, null);
  }

  public static Cursor getEventTheme(Context paramContext, EsAccount paramEsAccount, int paramInt, String[] paramArrayOfString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    String str;
    String[] arrayOfString;
    Cursor localCursor;
    if (paramInt == -1)
    {
      str = "is_default!=0";
      arrayOfString = null;
      localCursor = localSQLiteDatabase.query("event_themes", paramArrayOfString, str, arrayOfString, null, null, "theme_id");
      if (localCursor.getCount() == 0)
        break label78;
    }
    while (true)
    {
      return localCursor;
      str = "theme_id=?";
      arrayOfString = new String[1];
      arrayOfString[0] = Integer.toString(paramInt);
      break;
      label78: localCursor.close();
      ensureFreshEventThemes(paramContext, paramEsAccount, null);
      localCursor = localSQLiteDatabase.query("event_themes", paramArrayOfString, str, arrayOfString, null, null, "theme_id");
    }
  }

  private static File getEventThemePlaceholderDir(Context paramContext)
  {
    if (sEventThemePlaceholderDir == null)
      sEventThemePlaceholderDir = new File(paramContext.getCacheDir(), "event_themes");
    if (!sEventThemePlaceholderDir.exists());
    try
    {
      sEventThemePlaceholderDir.mkdir();
      return sEventThemePlaceholderDir;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e("EsEventData", "Cannot create event theme placeholder directory: " + sEventThemePlaceholderDir, localException);
        sEventThemePlaceholderDir = null;
      }
    }
  }

  public static Cursor getEventThemes(Context paramContext, EsAccount paramEsAccount, int paramInt, String[] paramArrayOfString)
  {
    ensureFreshEventThemes(paramContext, paramEsAccount, null);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    StringBuilder localStringBuilder = new StringBuilder("is_featured=");
    if (paramInt == 0);
    for (int i = 1; ; i = 0)
      return localSQLiteDatabase.query("event_themes", paramArrayOfString, i, null, null, null, "sort_order ASC");
  }

  public static String getImageUrl(Theme paramTheme)
  {
    ThemeImage localThemeImage = getThemeImage(paramTheme);
    if (localThemeImage != null);
    for (String str = localThemeImage.url; ; str = null)
      return str;
  }

  public static InviteeSummary getInviteeSummary(PlusEvent paramPlusEvent, String paramString)
  {
    Object localObject;
    if (paramPlusEvent.inviteeSummary == null)
      localObject = null;
    while (true)
    {
      return localObject;
      Iterator localIterator = paramPlusEvent.inviteeSummary.iterator();
      InviteeSummary localInviteeSummary;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject = null;
        if (!bool)
          break;
        localInviteeSummary = (InviteeSummary)localIterator.next();
      }
      while ((localInviteeSummary.rsvpType == null) || ((localInviteeSummary.count.intValue() == 1) && (localInviteeSummary.setByViewer != null) && (localInviteeSummary.setByViewer.booleanValue())) || (!TextUtils.equals(paramString, localInviteeSummary.rsvpType)));
      localObject = localInviteeSummary;
    }
  }

  public static Cursor getMyCurrentEvents(Context paramContext, EsAccount paramEsAccount, long paramLong, String[] paramArrayOfString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Long.toString(paramLong);
    return localSQLiteDatabase.query("events", paramArrayOfString, "mine = 1 AND ? < end_time AND source = 1", arrayOfString, null, null, "end_time ASC");
  }

  private static Set<String> getMyEventIds(SQLiteDatabase paramSQLiteDatabase)
  {
    Cursor localCursor = paramSQLiteDatabase.query("events", new String[] { "event_id" }, "mine = 1", null, null, null, null);
    HashSet localHashSet = new HashSet();
    try
    {
      if (localCursor.moveToNext())
        localHashSet.add(localCursor.getString(0));
    }
    finally
    {
      localCursor.close();
    }
    return localHashSet;
  }

  public static Cursor getMyPastEvents(Context paramContext, EsAccount paramEsAccount, long paramLong, String[] paramArrayOfString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Long.toString(paramLong);
    return localSQLiteDatabase.query("events", paramArrayOfString, "mine = 1 AND ? > end_time AND source = 1", arrayOfString, null, null, "end_time DESC");
  }

  public static PlusEvent getPlusEvent(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    PlusEvent localPlusEvent = null;
    if (paramEsAccount != null)
    {
      boolean bool = TextUtils.isEmpty(paramString);
      localPlusEvent = null;
      if (!bool)
        break label21;
    }
    while (true)
    {
      return localPlusEvent;
      label21: Cursor localCursor = getEvent(paramContext, paramEsAccount, paramString, EVENT_QUERY_PROJECTION);
      if (localCursor != null);
      try
      {
        if (localCursor.moveToNext())
        {
          localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(localCursor.getBlob(0));
          if (localCursor == null)
            continue;
          localCursor.close();
          continue;
        }
        localPlusEvent = null;
        if (localCursor == null)
          continue;
        localCursor.close();
        localPlusEvent = null;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
  }

  public static int getRsvpStatus(PlusEvent paramPlusEvent)
  {
    String str = getRsvpType(paramPlusEvent);
    int i;
    if (("CHECKIN".equals(str)) || ("ATTENDING".equals(str)))
      i = 1;
    while (true)
    {
      return i;
      if ("MAYBE".equals(str))
        i = 2;
      else if (("NOT_ATTENDING".equals(str)) || ("NOT_ATTENDING_AND_REMOVE".equals(str)))
        i = 3;
      else
        i = 0;
    }
  }

  public static String getRsvpType(PlusEvent paramPlusEvent)
  {
    String str;
    if ((paramPlusEvent != null) && (paramPlusEvent.viewerInfo != null))
    {
      str = paramPlusEvent.viewerInfo.rsvpType;
      if ((!"CHECKIN".equals(str)) && (!"ATTENDING".equals(str)) && (!"MAYBE".equals(str)) && (!"NOT_ATTENDING".equals(str)) && (!"NOT_ATTENDING_AND_REMOVE".equals(str)));
    }
    while (true)
    {
      return str;
      if (EsLog.isLoggable("EsEventData", 3))
        Log.d("EsEventData", "[FILTER_RSVP_TYPE]; " + str + " not recognized");
      str = "NOT_RESPONDED";
      continue;
      str = "NOT_RESPONDED";
    }
  }

  public static ThemeImage getThemeImage(Theme paramTheme)
  {
    Object localObject = null;
    if (paramTheme != null)
    {
      List localList = paramTheme.image;
      localObject = null;
      if (localList != null)
      {
        Iterator localIterator = paramTheme.image.iterator();
        while (localIterator.hasNext())
        {
          ThemeImage localThemeImage = (ThemeImage)localIterator.next();
          if (localThemeImage != null)
            if ("LARGE".equals(localThemeImage.aspectRatio))
            {
              if ("JPG".equals(localThemeImage.format))
                localObject = localThemeImage;
            }
            else if ((localObject == null) && (!"MOV".equals(localThemeImage.format)))
              localObject = localThemeImage;
        }
      }
    }
    return localObject;
  }

  public static void insertEvent(Context paramContext, EsAccount paramEsAccount, String paramString, PlusEvent paramPlusEvent, Update paramUpdate)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ArrayList localArrayList = new ArrayList();
    String str = paramPlusEvent.id;
    localSQLiteDatabase.beginTransaction();
    try
    {
      insertEventInTransaction(paramContext, paramEsAccount.getGaiaId(), localSQLiteDatabase, str, paramString, paramPlusEvent, paramUpdate, localArrayList, null, null);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      if ((!TextUtils.isEmpty(str)) && (TextUtils.equals(str, InstantUpload.getInstantShareEventId(paramContext))) && (validateInstantShare(paramContext, paramEsAccount, paramPlusEvent)))
        enableInstantShare(paramContext, true, paramPlusEvent);
      Iterator localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
      {
        Uri localUri = (Uri)localIterator.next();
        paramContext.getContentResolver().notifyChange(localUri, null);
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void insertEventActivities(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, List<EventActivity> paramList, boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ArrayList localArrayList = new ArrayList();
    localSQLiteDatabase.beginTransaction();
    try
    {
      insertResumeTokenInTransaction(localSQLiteDatabase, paramString1, null);
      if (paramList != null)
        insertEventActivitiesInTransaction(paramContext, localSQLiteDatabase, paramString1, paramList, true, localArrayList);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      Iterator localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
      {
        Uri localUri = (Uri)localIterator.next();
        paramContext.getContentResolver().notifyChange(localUri, null);
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void insertEventActivitiesInTransaction(Context paramContext, SQLiteDatabase paramSQLiteDatabase, String paramString, List<EventActivity> paramList, boolean paramBoolean, List<Uri> paramList1)
  {
    String[] arrayOfString1 = { paramString };
    HashMap localHashMap1 = new HashMap();
    int i = 0;
    Cursor localCursor = paramSQLiteDatabase.query("event_activities", new String[] { "_id", "type", "owner_gaia_id", "timestamp", "fingerprint" }, "event_id=?", arrayOfString1, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        EventActivityKey localEventActivityKey1 = new EventActivityKey((byte)0);
        EventActivityStatus localEventActivityStatus1 = new EventActivityStatus((byte)0);
        localEventActivityStatus1.id = localCursor.getString(0);
        localEventActivityKey1.type = localCursor.getInt(1);
        localEventActivityKey1.ownerGaiaId = localCursor.getString(2);
        localEventActivityKey1.timestamp = localCursor.getLong(3);
        localEventActivityStatus1.fingerprint = localCursor.getInt(4);
        localHashMap1.put(localEventActivityKey1, localEventActivityStatus1);
      }
    }
    finally
    {
      localCursor.close();
    }
    ContentValues localContentValues = new ContentValues();
    EventActivityKey localEventActivityKey2 = new EventActivityKey((byte)0);
    HashMap localHashMap2 = new HashMap();
    Iterator localIterator1 = paramList.iterator();
    label314: label708: 
    while (localIterator1.hasNext())
    {
      EventActivity localEventActivity = (EventActivity)localIterator1.next();
      localEventActivityKey2.type = localEventActivity.activityType;
      localEventActivityKey2.ownerGaiaId = localEventActivity.ownerGaiaId;
      localEventActivityKey2.timestamp = localEventActivity.timestamp;
      EventActivityStatus localEventActivityStatus3 = (EventActivityStatus)localHashMap1.get(localEventActivityKey2);
      int j;
      DataPhoto localDataPhoto;
      String str1;
      String str2;
      if (localEventActivity.data == null)
      {
        j = 0;
        if (localEventActivity.activityType != 100)
          break label534;
        localDataPhoto = (DataPhoto)DataPhotoJson.getInstance().fromString(localEventActivity.data);
        str1 = null;
        str2 = null;
        if (localDataPhoto != null)
          str2 = localDataPhoto.original.url;
        label361: if (localEventActivityStatus3 != null)
          break label602;
        localContentValues.clear();
        localContentValues.put("event_id", paramString);
        localContentValues.put("type", Integer.valueOf(localEventActivity.activityType));
        localContentValues.put("timestamp", Long.valueOf(localEventActivity.timestamp));
        localContentValues.put("owner_gaia_id", localEventActivity.ownerGaiaId);
        localContentValues.put("owner_name", localEventActivity.ownerName);
        localContentValues.put("data", localEventActivity.data);
        localContentValues.put("url", str2);
        localContentValues.put("comment", str1);
        localContentValues.put("fingerprint", Integer.valueOf(j));
        paramSQLiteDatabase.insert("event_activities", null, localContentValues);
      }
      while (true)
      {
        if (localEventActivity.activityType != 100)
          break label708;
        EsPhotosDataApiary.insertEventPhotoInTransaction(paramSQLiteDatabase, localDataPhoto, paramString, localHashMap2, paramList1);
        i = 1;
        break;
        j = localEventActivity.data.hashCode();
        break label314;
        int k = localEventActivity.activityType;
        str1 = null;
        localDataPhoto = null;
        str2 = null;
        if (k != 5)
          break label361;
        EventComment localEventComment = (EventComment)EVENT_COMMENT_JSON.fromString(localEventActivity.data);
        str1 = null;
        localDataPhoto = null;
        str2 = null;
        if (localEventComment == null)
          break label361;
        str1 = localEventComment.text;
        localDataPhoto = null;
        str2 = null;
        break label361;
        label602: int m = localEventActivityStatus3.fingerprint;
        if (j != m)
        {
          localContentValues.clear();
          localContentValues.put("data", localEventActivity.data);
          localContentValues.put("url", str2);
          localContentValues.put("comment", str1);
          localContentValues.put("fingerprint", Integer.valueOf(j));
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = localEventActivityStatus3.id;
          paramSQLiteDatabase.update("event_activities", localContentValues, "_id=?", arrayOfString3);
        }
        localHashMap1.remove(localEventActivityKey2);
      }
    }
    label534: if (!paramBoolean)
    {
      Iterator localIterator2 = localHashMap1.values().iterator();
      while (localIterator2.hasNext())
      {
        EventActivityStatus localEventActivityStatus2 = (EventActivityStatus)localIterator2.next();
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = localEventActivityStatus2.id;
        paramSQLiteDatabase.delete("event_activities", "_id=?", arrayOfString2);
      }
    }
    localContentValues.clear();
    localContentValues.put("activity_refresh_timestamp", Long.valueOf(System.currentTimeMillis()));
    paramSQLiteDatabase.update("events", localContentValues, "event_id=?", arrayOfString1);
    Uri localUri;
    if (i != 0)
    {
      localUri = Uri.withAppendedPath(EsProvider.PHOTO_BY_EVENT_ID_URI, paramString);
      if (paramList1 == null)
        break label844;
      paramList1.add(localUri);
    }
    while (true)
    {
      return;
      label844: paramContext.getContentResolver().notifyChange(localUri, null);
    }
  }

  public static void insertEventHomeList(Context paramContext, EsAccount paramEsAccount, List<PlusEvent> paramList1, List<PlusEvent> paramList2, List<PlusEvent> paramList3, List<EmbedsPerson> paramList)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ArrayList localArrayList = new ArrayList();
    String str = paramEsAccount.getGaiaId();
    Set localSet = getMyEventIds(localSQLiteDatabase);
    int[] arrayOfInt = new int[5];
    localSQLiteDatabase.beginTransaction();
    try
    {
      insertEventListInTransaction(paramContext, localSQLiteDatabase, str, paramList1, localSet, arrayOfInt, localArrayList, paramList);
      insertEventListInTransaction(paramContext, localSQLiteDatabase, str, paramList2, localSet, arrayOfInt, localArrayList, paramList);
      insertEventListInTransaction(paramContext, localSQLiteDatabase, str, paramList3, localSet, arrayOfInt, localArrayList, paramList);
      StringBuilder localStringBuilder = new StringBuilder();
      String[] arrayOfString = (String[])localSet.toArray(new String[0]);
      localStringBuilder.append("event_id IN (");
      for (int i = 0; i < arrayOfString.length; i++)
      {
        if (i != 0)
          localStringBuilder.append(',');
        localStringBuilder.append('?');
      }
      localStringBuilder.append(')');
      localSQLiteDatabase.delete("events", localStringBuilder.toString(), arrayOfString);
      arrayOfInt[3] = arrayOfString.length;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("event_list_sync_time", Long.valueOf(System.currentTimeMillis()));
      localSQLiteDatabase.update("account_status", localContentValues, null, null);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsEventData", 3))
        Log.d("EsEventData", "[INSERT_EVENT_LIST]; " + arrayOfInt[0] + " inserted, " + arrayOfInt[1] + " changed, " + arrayOfInt[2] + " not changed, " + arrayOfInt[3] + " removed, " + arrayOfInt[4] + " ignored");
      PlusEvent localPlusEvent = getPlusEvent(paramContext, paramEsAccount, InstantUpload.getInstantShareEventId(paramContext));
      if (validateInstantShare(paramContext, paramEsAccount, localPlusEvent))
        enableInstantShare(paramContext, true, localPlusEvent);
      Iterator localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
      {
        Uri localUri = (Uri)localIterator.next();
        paramContext.getContentResolver().notifyChange(localUri, null);
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    paramContext.getContentResolver().notifyChange(EsProvider.EVENTS_ALL_URI, null);
  }

  private static boolean insertEventInTransaction(Context paramContext, String paramString1, SQLiteDatabase paramSQLiteDatabase, String paramString2, String paramString3, PlusEvent paramPlusEvent, Update paramUpdate, List<Uri> paramList, Long paramLong, List<EmbedsPerson> paramList1)
  {
    return insertEventInTransaction$6b5f16b7(paramContext, paramString1, paramSQLiteDatabase, paramString2, paramString3, paramPlusEvent, paramUpdate, paramLong, paramList1, 1);
  }

  static boolean insertEventInTransaction$6b5f16b7(Context paramContext, String paramString1, SQLiteDatabase paramSQLiteDatabase, String paramString2, String paramString3, PlusEvent paramPlusEvent, Update paramUpdate, Long paramLong, List<EmbedsPerson> paramList, int paramInt)
  {
    long l1 = System.currentTimeMillis();
    int i = 1;
    Cursor localCursor = paramSQLiteDatabase.query("events", new String[] { "fingerprint", "source", "can_comment" }, "event_id=?", new String[] { paramString2 }, null, null, null);
    while (true)
    {
      boolean bool2;
      ContentValues localContentValues;
      boolean bool3;
      String str2;
      try
      {
        boolean bool1 = localCursor.moveToFirst();
        int j = 0;
        bool2 = false;
        boolean bool7;
        if (bool1)
        {
          j = localCursor.getInt(0);
          int m = localCursor.getInt(1);
          int n = localCursor.getInt(2);
          if (n != 0)
          {
            bool2 = true;
            i = 0;
            if (m == 1)
            {
              i = 0;
              if (paramInt == 0)
              {
                localCursor.close();
                return bool7;
              }
            }
          }
          else
          {
            bool2 = false;
            continue;
          }
        }
        localCursor.close();
        byte[] arrayOfByte = PlusEventJson.getInstance().toByteArray(paramPlusEvent);
        int k = Arrays.hashCode(arrayOfByte);
        localContentValues = new ContentValues();
        localContentValues.put("source", Integer.valueOf(paramInt));
        if ((i == 0) && (j == k))
          break label885;
        localContentValues.put("refresh_timestamp", Long.valueOf(l1));
        localContentValues.put("name", paramPlusEvent.name);
        localContentValues.put("event_data", arrayOfByte);
        localContentValues.put("mine", Boolean.valueOf(isMine(paramPlusEvent, paramString1)));
        if ((paramString1.equals(paramPlusEvent.creatorObfuscatedId)) || ((paramPlusEvent.eventOptions != null) && (paramPlusEvent.eventOptions.openEventAcl != null) && (paramPlusEvent.eventOptions.openEventAcl.booleanValue())))
        {
          bool3 = true;
          localContentValues.put("can_invite_people", Boolean.valueOf(bool3));
          if ((!paramString1.equals(paramPlusEvent.creatorObfuscatedId)) && ((paramPlusEvent.eventOptions == null) || (paramPlusEvent.eventOptions.openPhotoAcl == null) || (!paramPlusEvent.eventOptions.openPhotoAcl.booleanValue())))
            break label843;
          bool4 = true;
          localContentValues.put("can_post_photos", Boolean.valueOf(bool4));
          if (paramUpdate == null)
            break label849;
          bool5 = PrimitiveUtils.safeBoolean(paramUpdate.canViewerComment);
          localContentValues.put("can_comment", Boolean.valueOf(bool5));
          if (paramPlusEvent.startTime == null)
            break label856;
          l2 = paramPlusEvent.startTime.timeMs.longValue();
          localContentValues.put("start_time", Long.valueOf(l2));
          localContentValues.put("end_time", Long.valueOf(getEventEndTime(paramPlusEvent)));
          localContentValues.put("fingerprint", Integer.valueOf(k));
          if ((i != 0) || (paramString3 != null))
            localContentValues.put("activity_id", paramString3);
          if (paramLong != null)
            localContentValues.put("display_time", paramLong);
          if (i == 0)
            break label863;
          localContentValues.put("event_id", paramString2);
          paramSQLiteDatabase.insert("events", null, localContentValues);
          bool6 = true;
          if (EsLog.isLoggable("EsEventData", 3))
          {
            Log.d("EsEventData", "[INSERT_EVENT], duration: " + (System.currentTimeMillis() - l1) + "ms");
            StringBuilder localStringBuilder1 = new StringBuilder();
            String str1 = localStringBuilder1.toString();
            localStringBuilder1.setLength(0);
            StringBuilder localStringBuilder2 = localStringBuilder1.append(str1).append("EVENT [id: ").append(paramPlusEvent.id).append(", owner: ");
            if (paramPlusEvent.creatorObfuscatedId != null)
              break label928;
            str2 = "N/A";
            localStringBuilder2.append(str2);
            CharSequence localCharSequence1 = DateFormat.format("MMM dd, yyyy h:mmaa", new Date(paramPlusEvent.startTime.timeMs.longValue()));
            localStringBuilder1.append(", start: ").append(localCharSequence1);
            if ((paramPlusEvent.endTime != null) && (paramPlusEvent.endTime.timeMs != null))
            {
              CharSequence localCharSequence2 = DateFormat.format("MMM dd, yyyy h:mmaa", new Date(paramPlusEvent.endTime.timeMs.longValue()));
              localStringBuilder1.append(", end: ").append(localCharSequence2);
            }
            localStringBuilder1.append(", \n").append(str1).append("      title: ").append(paramPlusEvent.name);
            localStringBuilder1.append("]");
            localStringBuilder1.append("\n");
            localStringBuilder1.append("\n");
            EsLog.writeToLog(3, "EsEventData", localStringBuilder1.toString());
          }
          insertReferencedPeopleInTransaction(paramContext, paramPlusEvent, paramList, paramSQLiteDatabase);
          bool7 = bool6;
        }
      }
      finally
      {
        localCursor.close();
      }
      continue;
      label843: boolean bool4 = false;
      continue;
      label849: boolean bool5 = bool2;
      continue;
      label856: long l2 = l1;
      continue;
      label863: paramSQLiteDatabase.update("events", localContentValues, "event_id=?", new String[] { paramString2 });
      continue;
      label885: boolean bool6 = false;
      if (paramString3 != null)
      {
        localContentValues.put("activity_id", paramString3);
        paramSQLiteDatabase.update("events", localContentValues, "event_id=?", new String[] { paramString2 });
        bool6 = false;
        continue;
        label928: str2 = paramPlusEvent.creatorObfuscatedId;
      }
    }
  }

  public static void insertEventInviteeList(Context paramContext, EsAccount paramEsAccount, String paramString, List<Invitee> paramList)
  {
    if (EsLog.isLoggable("EsEventData", 3))
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Invitee localInvitee = (Invitee)localIterator.next();
        Log.d("EsEventData", "[INSERT_EVENT_INVITEE]; " + paramString + " " + InviteeJson.getInstance().toPrettyString(localInvitee));
      }
    }
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    String[] arrayOfString = { paramString };
    ContentValues localContentValues = new ContentValues();
    InviteeList localInviteeList = new InviteeList();
    localInviteeList.invitees = paramList;
    localContentValues.put("invitee_roster_timestamp", Long.valueOf(System.currentTimeMillis()));
    localContentValues.put("invitee_roster", INVITEE_LIST_JSON.toByteArray(localInviteeList));
    insertPeopleInInviteeSummaries(paramContext, paramString, null, paramList, localSQLiteDatabase);
    localSQLiteDatabase.update("events", localContentValues, "event_id=?", arrayOfString);
    paramContext.getContentResolver().notifyChange(EsProvider.EVENTS_ALL_URI, null);
  }

  private static void insertEventListInTransaction(Context paramContext, SQLiteDatabase paramSQLiteDatabase, String paramString, List<PlusEvent> paramList, Set<String> paramSet, int[] paramArrayOfInt, List<Uri> paramList1, List<EmbedsPerson> paramList2)
  {
    if (paramList == null)
      return;
    Iterator localIterator = paramList.iterator();
    label13: int i;
    while (localIterator.hasNext())
    {
      PlusEvent localPlusEvent = (PlusEvent)localIterator.next();
      if (!isMine(localPlusEvent, paramString))
      {
        paramArrayOfInt[4] = (1 + paramArrayOfInt[4]);
      }
      else
      {
        boolean bool = paramSet.remove(localPlusEvent.id);
        if (!insertEventInTransaction(paramContext, paramString, paramSQLiteDatabase, localPlusEvent.id, null, localPlusEvent, null, paramList1, null, paramList2))
          break label123;
        if (!bool)
          break label117;
        i = 1;
      }
    }
    while (true)
    {
      paramArrayOfInt[i] = (1 + paramArrayOfInt[i]);
      break label13;
      break;
      label117: i = 0;
      continue;
      label123: i = 2;
    }
  }

  public static void insertEventThemes(Context paramContext, EsAccount paramEsAccount, List<Theme> paramList)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    HashMap localHashMap1;
    Cursor localCursor;
    while (true)
    {
      try
      {
        localHashMap1 = new HashMap();
        localCursor = localSQLiteDatabase.query("event_themes", new String[] { "theme_id", "is_default", "is_featured", "image_url", "sort_order", "placeholder_path" }, null, null, null, null, null);
      }
      finally
      {
        try
        {
          if (!localCursor.moveToNext())
            break;
          ThemeStatus localThemeStatus1 = new ThemeStatus((byte)0);
          int i = localCursor.getInt(0);
          if (localCursor.getInt(1) == 0)
            break label225;
          bool1 = true;
          localThemeStatus1.isDefault = bool1;
          if (localCursor.getInt(2) == 0)
            break label231;
          bool2 = true;
          localThemeStatus1.isFeatured = bool2;
          localThemeStatus1.imageUrl = localCursor.getString(3);
          localThemeStatus1.sortOrder = localCursor.getInt(4);
          localThemeStatus1.placeholderPath = localCursor.getString(5);
          localHashMap1.put(Integer.valueOf(i), localThemeStatus1);
        }
        finally
        {
          localCursor.close();
        }
        localSQLiteDatabase.endTransaction();
      }
      label225: boolean bool1 = false;
      continue;
      label231: boolean bool2 = false;
    }
    localCursor.close();
    HashMap localHashMap2 = new HashMap(localHashMap1);
    ContentValues localContentValues = new ContentValues();
    int j = -1 + paramList.size();
    Theme localTheme;
    String str;
    ThemeStatus localThemeStatus3;
    int n;
    if (j >= 0)
    {
      localTheme = (Theme)paramList.get(j);
      if (localTheme.themeId == null)
        break label742;
      str = getImageUrl(localTheme);
      if (str == null)
        break label742;
      localHashMap2.remove(localTheme.themeId);
      List localList = localTheme.category;
      int k = 0;
      int m = 0;
      if (localList != null)
      {
        Iterator localIterator2 = localTheme.category.iterator();
        while (localIterator2.hasNext())
        {
          EventCategory localEventCategory = (EventCategory)localIterator2.next();
          if ("FEATURED".equals(localEventCategory.category))
            m = 1;
          else if ("DEFAULT".equals(localEventCategory.category))
            k = 1;
        }
      }
      localThemeStatus3 = (ThemeStatus)localHashMap1.get(localTheme.themeId);
      if ((localThemeStatus3 != null) && (localThemeStatus3.isDefault == k) && (localThemeStatus3.isFeatured == m) && (TextUtils.equals(localThemeStatus3.imageUrl, str)) && (localThemeStatus3.sortOrder == j))
        break label742;
      localContentValues.clear();
      if (m == 0)
        break label748;
      n = 1;
      label491: localContentValues.put("is_featured", Integer.valueOf(n));
      if (k == 0)
        break label754;
    }
    label742: label748: label754: for (int i1 = 1; ; i1 = 0)
    {
      localContentValues.put("is_default", Integer.valueOf(i1));
      localContentValues.put("image_url", str);
      localContentValues.put("sort_order", Integer.valueOf(j));
      if (localThemeStatus3 != null)
      {
        localSQLiteDatabase.update("event_themes", localContentValues, "theme_id = " + localTheme.themeId, null);
      }
      else
      {
        localContentValues.put("theme_id", localTheme.themeId);
        localSQLiteDatabase.insert("event_themes", null, localContentValues);
        break label742;
        Iterator localIterator1 = localHashMap2.keySet().iterator();
        while (localIterator1.hasNext())
        {
          Integer localInteger = (Integer)localIterator1.next();
          ThemeStatus localThemeStatus2 = (ThemeStatus)localHashMap2.get(localInteger);
          String[] arrayOfString = new String[1];
          arrayOfString[0] = localInteger.toString();
          localSQLiteDatabase.delete("event_themes", "theme_id=?", arrayOfString);
          paramContext.deleteFile(localThemeStatus2.placeholderPath);
        }
        localContentValues.clear();
        localContentValues.put("event_themes_sync_time", Long.valueOf(System.currentTimeMillis()));
        localSQLiteDatabase.update("account_status", localContentValues, null, null);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return;
      }
      j--;
      break;
      n = 0;
      break label491;
    }
  }

  private static void insertMentionedPersonInTransaction(Context paramContext, String paramString, EmbedsPerson paramEmbedsPerson, List<EmbedsPerson> paramList, SQLiteDatabase paramSQLiteDatabase)
  {
    int i = 0;
    if (paramEmbedsPerson != null)
    {
      i = 0;
      if (paramList != null)
      {
        String str = paramEmbedsPerson.ownerObfuscatedId;
        i = 0;
        if (str != null)
          for (int j = 0; (j < paramList.size()) && (i == 0); j++)
          {
            EmbedsPerson localEmbedsPerson = (EmbedsPerson)paramList.get(j);
            if (TextUtils.equals(localEmbedsPerson.ownerObfuscatedId, paramEmbedsPerson.ownerObfuscatedId))
            {
              insertPersonInTransaction$5725cc2c(paramString, localEmbedsPerson.ownerObfuscatedId, localEmbedsPerson.name, localEmbedsPerson.imageUrl, paramSQLiteDatabase);
              i = 1;
            }
          }
      }
    }
    if ((i == 0) && (paramEmbedsPerson != null) && (paramEmbedsPerson.ownerObfuscatedId != null) && (paramEmbedsPerson.imageUrl != null))
      insertPersonInTransaction$5725cc2c(paramString, paramEmbedsPerson.ownerObfuscatedId, paramEmbedsPerson.name, paramEmbedsPerson.imageUrl, paramSQLiteDatabase);
  }

  private static void insertPeopleInInviteeSummaries(Context paramContext, String paramString, List<EmbedsPerson> paramList, List<Invitee> paramList1, SQLiteDatabase paramSQLiteDatabase)
  {
    if (paramList1 != null)
    {
      Iterator localIterator = paramList1.iterator();
      while (localIterator.hasNext())
      {
        Invitee localInvitee = (Invitee)localIterator.next();
        insertMentionedPersonInTransaction(paramContext, paramString, localInvitee.invitee, paramList, paramSQLiteDatabase);
        insertMentionedPersonInTransaction(paramContext, paramString, localInvitee.inviter, paramList, paramSQLiteDatabase);
      }
    }
  }

  private static void insertPersonInTransaction$5725cc2c(String paramString1, String paramString2, String paramString3, String paramString4, SQLiteDatabase paramSQLiteDatabase)
  {
    if ((paramString2 == null) || (paramString4 == null));
    while (true)
    {
      return;
      String[] arrayOfString = { paramString1, paramString2 };
      int i = 1;
      try
      {
        DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT event_id FROM event_people WHERE event_id=? AND gaia_id=?", arrayOfString);
        i = 0;
        label40: if (i != 0)
        {
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("event_id", paramString1);
          localContentValues.put("gaia_id", paramString2);
          paramSQLiteDatabase.insert("event_people", null, localContentValues);
        }
        EsPeopleData.replaceUserInTransaction(paramSQLiteDatabase, paramString2, paramString3, paramString4);
      }
      catch (SQLiteDoneException localSQLiteDoneException)
      {
        break label40;
      }
    }
  }

  private static void insertReferencedPeopleInTransaction(Context paramContext, PlusEvent paramPlusEvent, List<EmbedsPerson> paramList, SQLiteDatabase paramSQLiteDatabase)
  {
    String str = paramPlusEvent.id;
    insertMentionedPersonInTransaction(paramContext, str, paramPlusEvent.creator, paramList, paramSQLiteDatabase);
    if (paramPlusEvent.inviteeSummary != null)
    {
      Iterator localIterator = paramPlusEvent.inviteeSummary.iterator();
      while (localIterator.hasNext())
        insertPeopleInInviteeSummaries(paramContext, str, paramList, ((InviteeSummary)localIterator.next()).invitee, paramSQLiteDatabase);
    }
  }

  private static void insertResumeTokenInTransaction(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1 };
    int i = 1;
    Cursor localCursor = paramSQLiteDatabase.query("events", new String[] { "resume_token" }, "event_id=?", arrayOfString, null, null, null);
    while (true)
    {
      ContentValues localContentValues;
      try
      {
        boolean bool = localCursor.moveToFirst();
        Object localObject2 = null;
        if (bool)
        {
          String str = localCursor.getString(0);
          localObject2 = str;
          i = 0;
        }
        localCursor.close();
        if (!TextUtils.equals(localObject2, paramString2))
        {
          localContentValues = new ContentValues();
          localContentValues.put("resume_token", paramString2);
          if (i != 0)
            paramSQLiteDatabase.insert("events", null, localContentValues);
        }
        else
        {
          return;
        }
      }
      finally
      {
        localCursor.close();
      }
      paramSQLiteDatabase.update("events", localContentValues, "event_id=?", arrayOfString);
    }
  }

  public static boolean isEventHangout(PlusEvent paramPlusEvent)
  {
    if ((paramPlusEvent.eventOptions != null) && (paramPlusEvent.eventOptions.broadcast != null) && (paramPlusEvent.eventOptions.broadcast.booleanValue()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isEventOver(PlusEvent paramPlusEvent, long paramLong)
  {
    if (paramLong > getEventEndTime(paramPlusEvent));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isInstantShareAllowed(PlusEvent paramPlusEvent, String paramString, long paramLong)
  {
    boolean bool1 = true;
    boolean bool2 = canAddPhotos(paramPlusEvent, paramString);
    boolean bool3;
    if ((paramPlusEvent.viewerInfo != null) && (paramPlusEvent.viewerInfo.rsvpType != null))
    {
      bool3 = bool1;
      long l1 = paramPlusEvent.startTime.timeMs.longValue() - 10800000L;
      long l2 = getEventEndTime(paramPlusEvent) - 5000L;
      if ((!bool2) || (!bool3) || (paramLong <= l1) || (paramLong >= l2))
        break label90;
    }
    while (true)
    {
      return bool1;
      bool3 = false;
      break;
      label90: bool1 = false;
    }
  }

  private static boolean isMine(PlusEvent paramPlusEvent, String paramString)
  {
    boolean bool = true;
    if (paramString.equals(paramPlusEvent.creatorObfuscatedId));
    while (true)
    {
      return bool;
      if ((paramPlusEvent.viewerInfo == null) || (paramPlusEvent.viewerInfo.rsvpType == null))
      {
        if (paramPlusEvent.inviteeSummary != null)
        {
          Iterator localIterator = paramPlusEvent.inviteeSummary.iterator();
          while (true)
            if (localIterator.hasNext())
            {
              InviteeSummary localInviteeSummary = (InviteeSummary)localIterator.next();
              if ((localInviteeSummary.setByViewer != null) && (localInviteeSummary.setByViewer.booleanValue()))
                break;
            }
        }
        bool = false;
      }
    }
  }

  public static boolean isViewerCheckedIn(PlusEvent paramPlusEvent)
  {
    return "CHECKIN".equals(getRsvpType(paramPlusEvent));
  }

  private static void populateEventThemePlaceholders(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    if (paramSyncState.isCanceled());
    while (true)
    {
      return;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
      Cursor localCursor1 = null;
      Cursor localCursor2 = null;
      try
      {
        localCursor1 = localSQLiteDatabase.query("event_themes", new String[] { "theme_id", "image_url" }, "placeholder_path IS NULL", null, null, null, "sort_order ASC");
        localCursor2 = localSQLiteDatabase.query("event_themes", new String[] { "placeholder_path" }, "placeholder_path IS NOT NULL", null, null, null, "sort_order ASC");
        int i = localCursor2.getCount();
        long l1 = 0L;
        long l2 = 0L;
        if (localCursor2.moveToFirst())
        {
          File localFile1 = new File(localCursor2.getString(0));
          l1 = localFile1.length();
          if (l1 > 0L)
            l2 = 1048576L / l1;
        }
        if (localCursor1.getCount() > 0)
        {
          File localFile2 = getEventThemePlaceholderDir(paramContext);
          ContentValues localContentValues = new ContentValues();
          while ((!paramSyncState.isCanceled()) && (localCursor1.moveToNext()) && ((l1 == 0L) || (i < l2)))
          {
            String str1 = localCursor1.getString(0);
            EventThemePlaceholderRequest localEventThemePlaceholderRequest = new EventThemePlaceholderRequest(localCursor1.getString(1), paramContext);
            DownloadImageOperation localDownloadImageOperation = new DownloadImageOperation(paramContext, paramEsAccount, localEventThemePlaceholderRequest, false, null, null);
            localDownloadImageOperation.start();
            if (!localDownloadImageOperation.hasError())
            {
              File localFile3 = new File(localFile2, str1);
              byte[] arrayOfByte = localDownloadImageOperation.getImageBytes();
              if (l1 == 0L)
              {
                l1 = arrayOfByte.length;
                l2 = 1048576L / l1;
              }
              String str2 = localFile3.getAbsolutePath();
              try
              {
                FileOutputStream localFileOutputStream = new FileOutputStream(localFile3);
                localFileOutputStream.write(arrayOfByte);
                localFileOutputStream.close();
                i++;
                localContentValues.clear();
                localContentValues.put("placeholder_path", str2);
                if (localSQLiteDatabase.update("event_themes", localContentValues, "theme_id = " + str1, null) == 0)
                  paramContext.deleteFile(str2);
              }
              catch (IOException localIOException)
              {
                paramContext.deleteFile(str2);
                Log.e("EsEventData", "Cannot write event placeholder file: " + localEventThemePlaceholderRequest + " " + localFile3.getPath(), localIOException);
              }
            }
          }
        }
      }
      finally
      {
        if (localCursor2 != null)
          localCursor2.close();
        if (localCursor1 != null)
          localCursor1.close();
      }
      if (localCursor2 != null)
        localCursor2.close();
      if (localCursor1 != null)
        localCursor1.close();
    }
  }

  public static HttpOperation readEventFromServer(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean1, boolean paramBoolean2, EsSyncAdapterService.SyncState paramSyncState, HttpTransactionMetrics paramHttpTransactionMetrics)
  {
    Object localObject1 = sEventOperationSyncObject;
    if (paramBoolean2);
    try
    {
      for (EventReadOperation localEventReadOperation = new EventReadOperation(paramContext, paramEsAccount, paramString1, paramString4, paramBoolean1, null, null); (paramSyncState != null) && (paramHttpTransactionMetrics != null); localEventReadOperation = new EventReadOperation(paramContext, paramEsAccount, paramString1, paramString2, paramString3, paramString4, paramString5, paramBoolean1, null, null))
      {
        localEventReadOperation.start(paramSyncState, paramHttpTransactionMetrics);
        if (localEventReadOperation.hasError())
          localEventReadOperation.logError("EsEventData");
        return localEventReadOperation;
      }
      localEventReadOperation.start();
    }
    finally
    {
    }
  }

  public static void refreshEvent(Context paramContext, final EsAccount paramEsAccount, final String paramString)
  {
    EsService.postOnUiThread(new Runnable()
    {
      public final void run()
      {
        EsService.getEvent(this.val$context, paramEsAccount, paramString);
      }
    });
  }

  public static Cursor retrieveEvent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String[] paramArrayOfString)
  {
    Cursor localCursor = getEvent(paramContext, paramEsAccount, paramString1, paramArrayOfString);
    if (localCursor.getCount() > 0);
    while (true)
    {
      return localCursor;
      localCursor.close();
      if (getEventFromServer(paramContext, paramEsAccount, paramString1, paramString2))
        localCursor = getEvent(paramContext, paramEsAccount, paramString1, paramArrayOfString);
      else
        localCursor = null;
    }
  }

  public static boolean rsvpForEvent(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    boolean bool;
    while (true)
    {
      synchronized (sEventOperationSyncObject)
      {
        PlusEvent localPlusEvent = getPlusEvent(paramContext, paramEsAccount, paramString1);
        String str = setRsvpType(paramContext, paramEsAccount, paramString1, paramString2);
        if ((localPlusEvent != null) && (str == null))
        {
          bool = false;
          break;
        }
        SendEventRsvpOperation localSendEventRsvpOperation = new SendEventRsvpOperation(paramContext, paramEsAccount, paramString1, paramString3, paramString2, str, null, null);
        localSendEventRsvpOperation.start();
        if (!localSendEventRsvpOperation.hasError())
          bool = true;
      }
      bool = false;
    }
    return bool;
  }

  public static String setRsvpType(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    boolean bool1 = "CHECKIN".equals(paramString2);
    String str1;
    SQLiteDatabase localSQLiteDatabase;
    Cursor localCursor;
    if (bool1)
    {
      str1 = "UNDO_CHECKIN";
      localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      localCursor = localSQLiteDatabase.query("events", EVENT_QUERY_PROJECTION, "event_id=?", new String[] { paramString1 }, null, null, null);
    }
    while (true)
    {
      PlusEvent localPlusEvent;
      try
      {
        boolean bool2 = localCursor.moveToNext();
        localPlusEvent = null;
        if (bool2)
          localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(localCursor.getBlob(0));
        localCursor.close();
        if (localPlusEvent == null)
          break label550;
        if (((localPlusEvent.isPublic == null) || (!localPlusEvent.isPublic.booleanValue())) && (!isMine(localPlusEvent, paramEsAccount.getGaiaId())) && (TextUtils.isEmpty(localPlusEvent.authKey)))
        {
          return str2;
          str1 = "NOT_RESPONDED";
        }
      }
      finally
      {
        localCursor.close();
      }
      int i = 0;
      Iterator localIterator = localPlusEvent.inviteeSummary.iterator();
      while (localIterator.hasNext())
      {
        InviteeSummary localInviteeSummary2 = (InviteeSummary)localIterator.next();
        if (localInviteeSummary2.rsvpType != null)
        {
          int j;
          if ((localInviteeSummary2.setByViewer != null) && (localInviteeSummary2.setByViewer.booleanValue()))
          {
            j = 1;
            label245: if ((j != 0) && (!bool1))
              str1 = localInviteeSummary2.rsvpType;
            if (paramString2.equals(localInviteeSummary2.rsvpType))
            {
              i = 1;
              if (j != 0)
                continue;
              localInviteeSummary2.setByViewer = Boolean.valueOf(true);
              if (localInviteeSummary2.count != null);
            }
          }
          else
          {
            for (int k = 1; ; k = 1 + localInviteeSummary2.count.intValue())
            {
              localInviteeSummary2.count = Integer.valueOf(k);
              break;
              j = 0;
              break label245;
            }
            if (j != 0)
            {
              boolean bool3 = true;
              if (localInviteeSummary2.rsvpType.equals("CHECKIN"))
                bool3 = "UNDO_CHECKIN".equals(paramString2);
              if (bool3)
              {
                localInviteeSummary2.setByViewer = Boolean.valueOf(false);
                if (localInviteeSummary2.count != null)
                {
                  localInviteeSummary2.count = Integer.valueOf(-1 + localInviteeSummary2.count.intValue());
                  if (localInviteeSummary2.count.intValue() == 0)
                    localIterator.remove();
                }
              }
            }
          }
        }
      }
      if (i == 0)
      {
        InviteeSummary localInviteeSummary1 = new InviteeSummary();
        localInviteeSummary1.rsvpType = paramString2;
        localInviteeSummary1.count = Integer.valueOf(1);
        localInviteeSummary1.setByViewer = Boolean.valueOf(true);
        localPlusEvent.inviteeSummary.add(0, localInviteeSummary1);
      }
      setViewerInfoRsvp(localPlusEvent, paramEsAccount, paramString2);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("event_data", PlusEventJson.getInstance().toByteArray(localPlusEvent));
      localContentValues.put("refresh_timestamp", Long.valueOf(System.currentTimeMillis()));
      localSQLiteDatabase.update("events", localContentValues, "event_id=?", new String[] { paramString1 });
      label550: paramContext.getContentResolver().notifyChange(EsProvider.EVENTS_ALL_URI, null);
      String str2 = str1;
    }
  }

  private static void setViewerInfoRsvp(PlusEvent paramPlusEvent, EsAccount paramEsAccount, String paramString)
  {
    if (paramPlusEvent != null)
    {
      if (paramPlusEvent.viewerInfo == null)
      {
        paramPlusEvent.viewerInfo = new Invitee();
        paramPlusEvent.viewerInfo.invitee = new EmbedsPerson();
        paramPlusEvent.viewerInfo.invitee.ownerObfuscatedId = paramEsAccount.getGaiaId();
      }
      paramPlusEvent.viewerInfo.rsvpType = paramString;
    }
  }

  public static void syncCurrentEvents$1ef5a3b9(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    Cursor localCursor;
    int i;
    synchronized (mSyncLock)
    {
      if (paramSyncState.isCanceled())
        return;
      paramSyncState.onStart("Current events");
      long l1 = System.currentTimeMillis();
      long l2 = l1 - 18000000L;
      long l3 = l1 + 10800000L;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
      String[] arrayOfString1 = SYNC_QUERY_PROJECTION;
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = Long.toString(l2);
      arrayOfString2[1] = Long.toString(l3);
      localCursor = localSQLiteDatabase.query("events", arrayOfString1, "end_time > ? AND start_time < ?", arrayOfString2, null, null, null);
      i = 0;
    }
    try
    {
      while ((localCursor.moveToNext()) && (!paramSyncState.isCanceled()))
      {
        boolean bool = readEventFromServer(paramContext, paramEsAccount, localCursor.getString(0), localCursor.getString(1), localCursor.getString(2), ((PlusEvent)PlusEventJson.getInstance().fromByteArray(localCursor.getBlob(3))).authKey, null, true, false, paramSyncState, new HttpTransactionMetrics()).hasError();
        if (!bool)
          i++;
      }
      localCursor.close();
      paramSyncState.onFinish(i);
      return;
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static void syncEventThemes$1ef5a3b9(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    int i = 1;
    if (paramSyncState.isCanceled())
      return;
    paramSyncState.onStart("Event Themes");
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (EsLog.isLoggable("EsEventData", 3))
      Log.d("EsEventData", "active network: " + localNetworkInfo);
    int j;
    if (localNetworkInfo != null)
      switch (localNetworkInfo.getType())
      {
      case 1:
      default:
        j = i;
        label119: if (j == 0)
          break;
      case 0:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    while (true)
    {
      if ((i != 0) && (!paramSyncState.isCanceled()))
      {
        ensureFreshEventThemes(paramContext, paramEsAccount, paramSyncState);
        populateEventThemePlaceholders(paramContext, paramEsAccount, paramSyncState);
      }
      paramSyncState.onFinish(0);
      break;
      j = 0;
      break label119;
      i = 0;
    }
  }

  public static long timeUntilInstantShareAllowed(PlusEvent paramPlusEvent, String paramString, long paramLong)
  {
    long l2;
    if (isInstantShareAllowed(paramPlusEvent, paramString, paramLong))
      l2 = 0L;
    while (true)
    {
      return l2;
      long l1 = getEventEndTime(paramPlusEvent);
      if ((!canAddPhotos(paramPlusEvent, paramString)) || (paramLong > l1))
        l2 = -1L;
      else
        l2 = paramPlusEvent.startTime.timeMs.longValue() - 10800000L - paramLong;
    }
  }

  public static boolean updateDataPhoto(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, long paramLong, String paramString3)
  {
    GetPhotoOperation localGetPhotoOperation = new GetPhotoOperation(paramContext, paramEsAccount, null, null, paramLong, paramString3);
    localGetPhotoOperation.start();
    if (!localGetPhotoOperation.hasError())
    {
      DataPhoto localDataPhoto = localGetPhotoOperation.getDataPhoto();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("data", DataPhotoJson.getInstance().toString(localDataPhoto));
      EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().update("event_activities", localContentValues, "event_id=? AND fingerprint=?", new String[] { paramString1, paramString2 });
    }
    return true;
  }

  // ERROR //
  public static void updateEventActivities(Context paramContext, EsAccount paramEsAccount, String paramString1, PlusEvent paramPlusEvent, Update paramUpdate, String paramString2, String paramString3, ArrayList<EventActivity> paramArrayList, boolean paramBoolean, long paramLong, List<EmbedsPerson> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 192	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   5: invokevirtual 196	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   8: astore 12
    //   10: new 703	java/util/ArrayList
    //   13: dup
    //   14: invokespecial 704	java/util/ArrayList:<init>	()V
    //   17: astore 13
    //   19: aload_3
    //   20: getfield 291	com/google/api/services/plusi/model/PlusEvent:id	Ljava/lang/String;
    //   23: astore 14
    //   25: aload 12
    //   27: invokevirtual 707	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   30: aload_0
    //   31: aload_1
    //   32: invokevirtual 123	com/google/android/apps/plus/content/EsAccount:getGaiaId	()Ljava/lang/String;
    //   35: aload 12
    //   37: aload 14
    //   39: aload_2
    //   40: aload_3
    //   41: aload 4
    //   43: aload 13
    //   45: lload 9
    //   47: invokestatic 362	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   50: aconst_null
    //   51: invokestatic 711	com/google/android/apps/plus/content/EsEventData:insertEventInTransaction	(Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Lcom/google/api/services/plusi/model/PlusEvent;Lcom/google/api/services/plusi/model/Update;Ljava/util/List;Ljava/lang/Long;Ljava/util/List;)Z
    //   54: pop
    //   55: aload 11
    //   57: ifnull +69 -> 126
    //   60: aload 11
    //   62: invokeinterface 161 1 0
    //   67: astore 42
    //   69: aload 42
    //   71: invokeinterface 166 1 0
    //   76: ifeq +50 -> 126
    //   79: aload 42
    //   81: invokeinterface 170 1 0
    //   86: checkcast 1112	com/google/api/services/plusi/model/EmbedsPerson
    //   89: astore 43
    //   91: aload 14
    //   93: aload 43
    //   95: getfield 1115	com/google/api/services/plusi/model/EmbedsPerson:ownerObfuscatedId	Ljava/lang/String;
    //   98: aload 43
    //   100: getfield 1116	com/google/api/services/plusi/model/EmbedsPerson:name	Ljava/lang/String;
    //   103: aload 43
    //   105: getfield 1117	com/google/api/services/plusi/model/EmbedsPerson:imageUrl	Ljava/lang/String;
    //   108: aload 12
    //   110: invokestatic 1121	com/google/android/apps/plus/content/EsEventData:insertPersonInTransaction$5725cc2c	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase;)V
    //   113: goto -44 -> 69
    //   116: astore 15
    //   118: aload 12
    //   120: invokevirtual 717	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   123: aload 15
    //   125: athrow
    //   126: iconst_1
    //   127: anewarray 35	java/lang/String
    //   130: dup
    //   131: iconst_0
    //   132: aload 14
    //   134: aastore
    //   135: astore 17
    //   137: iconst_1
    //   138: istore 18
    //   140: aload 12
    //   142: ldc 198
    //   144: iconst_1
    //   145: anewarray 35	java/lang/String
    //   148: dup
    //   149: iconst_0
    //   150: ldc 39
    //   152: aastore
    //   153: ldc 200
    //   155: aload 17
    //   157: aconst_null
    //   158: aconst_null
    //   159: aconst_null
    //   160: invokevirtual 462	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   163: astore 19
    //   165: aload 19
    //   167: invokeinterface 467 1 0
    //   172: istore 21
    //   174: aconst_null
    //   175: astore 22
    //   177: iload 21
    //   179: ifeq +20 -> 199
    //   182: aload 19
    //   184: iconst_0
    //   185: invokeinterface 626 2 0
    //   190: astore 41
    //   192: aload 41
    //   194: astore 22
    //   196: iconst_0
    //   197: istore 18
    //   199: aload 19
    //   201: invokeinterface 474 1 0
    //   206: aload 22
    //   208: aload 5
    //   210: invokestatic 99	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   213: ifne +37 -> 250
    //   216: new 338	android/content/ContentValues
    //   219: dup
    //   220: invokespecial 339	android/content/ContentValues:<init>	()V
    //   223: astore 23
    //   225: aload 23
    //   227: ldc 39
    //   229: aload 5
    //   231: invokevirtual 348	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   234: iload 18
    //   236: ifeq +264 -> 500
    //   239: aload 12
    //   241: ldc 198
    //   243: aconst_null
    //   244: aload 23
    //   246: invokevirtual 829	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   249: pop2
    //   250: aload 12
    //   252: aload 14
    //   254: aload 6
    //   256: invokestatic 732	com/google/android/apps/plus/content/EsEventData:insertResumeTokenInTransaction	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;)V
    //   259: iconst_1
    //   260: anewarray 35	java/lang/String
    //   263: dup
    //   264: iconst_0
    //   265: aload 14
    //   267: aastore
    //   268: astore 25
    //   270: iconst_1
    //   271: istore 26
    //   273: lconst_0
    //   274: lstore 27
    //   276: aload 12
    //   278: ldc 198
    //   280: iconst_1
    //   281: anewarray 35	java/lang/String
    //   284: dup
    //   285: iconst_0
    //   286: ldc_w 458
    //   289: aastore
    //   290: ldc 200
    //   292: aload 25
    //   294: aconst_null
    //   295: aconst_null
    //   296: aconst_null
    //   297: invokevirtual 462	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   300: astore 29
    //   302: aload 29
    //   304: invokeinterface 467 1 0
    //   309: ifeq +20 -> 329
    //   312: aload 29
    //   314: iconst_0
    //   315: invokeinterface 471 2 0
    //   320: lstore 37
    //   322: lload 37
    //   324: lstore 27
    //   326: iconst_0
    //   327: istore 26
    //   329: aload 29
    //   331: invokeinterface 474 1 0
    //   336: lload 9
    //   338: lload 27
    //   340: lcmp
    //   341: ifeq +41 -> 382
    //   344: new 338	android/content/ContentValues
    //   347: dup
    //   348: invokespecial 339	android/content/ContentValues:<init>	()V
    //   351: astore 31
    //   353: aload 31
    //   355: ldc_w 458
    //   358: lload 9
    //   360: invokestatic 362	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   363: invokevirtual 365	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   366: iload 26
    //   368: ifeq +161 -> 529
    //   371: aload 12
    //   373: ldc 198
    //   375: aconst_null
    //   376: aload 31
    //   378: invokevirtual 829	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   381: pop2
    //   382: aload 7
    //   384: ifnull +17 -> 401
    //   387: aload_0
    //   388: aload 12
    //   390: aload 14
    //   392: aload 7
    //   394: iload 8
    //   396: aload 13
    //   398: invokestatic 736	com/google/android/apps/plus/content/EsEventData:insertEventActivitiesInTransaction	(Landroid/content/Context;Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/util/List;ZLjava/util/List;)V
    //   401: aload 12
    //   403: invokevirtual 714	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   406: aload 12
    //   408: invokevirtual 717	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   411: aload 14
    //   413: invokestatic 641	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   416: ifne +30 -> 446
    //   419: aload 14
    //   421: aload_0
    //   422: invokestatic 212	com/google/android/apps/plus/phone/InstantUpload:getInstantShareEventId	(Landroid/content/Context;)Ljava/lang/String;
    //   425: invokestatic 99	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   428: ifeq +18 -> 446
    //   431: aload_0
    //   432: aload_1
    //   433: aload_3
    //   434: invokestatic 721	com/google/android/apps/plus/content/EsEventData:validateInstantShare	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/api/services/plusi/model/PlusEvent;)Z
    //   437: ifeq +9 -> 446
    //   440: aload_0
    //   441: iconst_1
    //   442: aload_3
    //   443: invokestatic 723	com/google/android/apps/plus/content/EsEventData:enableInstantShare	(Landroid/content/Context;ZLcom/google/api/services/plusi/model/PlusEvent;)V
    //   446: aload 13
    //   448: invokevirtual 724	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   451: astore 33
    //   453: aload 33
    //   455: invokeinterface 166 1 0
    //   460: ifeq +86 -> 546
    //   463: aload 33
    //   465: invokeinterface 170 1 0
    //   470: checkcast 726	android/net/Uri
    //   473: astore 34
    //   475: aload_0
    //   476: invokevirtual 222	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   479: aload 34
    //   481: aconst_null
    //   482: invokevirtual 234	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   485: goto -32 -> 453
    //   488: astore 20
    //   490: aload 19
    //   492: invokeinterface 474 1 0
    //   497: aload 20
    //   499: athrow
    //   500: aload 12
    //   502: ldc 198
    //   504: aload 23
    //   506: ldc 200
    //   508: aload 17
    //   510: invokevirtual 847	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   513: pop
    //   514: goto -264 -> 250
    //   517: astore 30
    //   519: aload 29
    //   521: invokeinterface 474 1 0
    //   526: aload 30
    //   528: athrow
    //   529: aload 12
    //   531: ldc 198
    //   533: aload 31
    //   535: ldc 200
    //   537: aload 25
    //   539: invokevirtual 847	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   542: pop
    //   543: goto -161 -> 382
    //   546: aload_0
    //   547: invokevirtual 222	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   550: getstatic 228	com/google/android/apps/plus/content/EsProvider:EVENTS_ALL_URI	Landroid/net/Uri;
    //   553: aconst_null
    //   554: invokevirtual 234	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   557: return
    //
    // Exception table:
    //   from	to	target	type
    //   30	113	116	finally
    //   126	165	116	finally
    //   199	302	116	finally
    //   329	406	116	finally
    //   490	543	116	finally
    //   165	192	488	finally
    //   302	322	517	finally
  }

  public static void updateEventInviteeList(Context paramContext, EsAccount paramEsAccount, String paramString1, boolean paramBoolean, String paramString2, String paramString3)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("events", new String[] { "event_data", "invitee_roster" }, "event_id=?", new String[] { paramString1 }, null, null, null);
    while (true)
    {
      PlusEvent localPlusEvent;
      List localList;
      try
      {
        boolean bool1 = localCursor.moveToFirst();
        localPlusEvent = null;
        localList = null;
        if (bool1)
        {
          byte[] arrayOfByte2 = localCursor.getBlob(0);
          byte[] arrayOfByte3 = localCursor.getBlob(1);
          localPlusEvent = null;
          localList = null;
          if (arrayOfByte3 != null)
          {
            localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(arrayOfByte2);
            localList = ((InviteeList)INVITEE_LIST_JSON.fromByteArray(arrayOfByte3)).invitees;
          }
        }
        localCursor.close();
        if ((localList == null) || (localPlusEvent == null))
          return;
      }
      finally
      {
        localCursor.close();
      }
      Iterator localIterator1 = localList.iterator();
      Invitee localInvitee;
      do
      {
        boolean bool2 = localIterator1.hasNext();
        localObject2 = null;
        if (!bool2)
          break;
        localInvitee = (Invitee)localIterator1.next();
      }
      while ((localInvitee.invitee == null) || (!TextUtils.equals(paramString2, localInvitee.invitee.ownerObfuscatedId)) || (!TextUtils.equals(paramString3, localInvitee.invitee.email)));
      Object localObject2 = localInvitee;
      if ((localObject2 != null) && ((localObject2.isAdminBlacklisted == null) || (localObject2.isAdminBlacklisted.booleanValue() != paramBoolean)))
      {
        Boolean localBoolean = Boolean.valueOf(paramBoolean);
        localObject2.isAdminBlacklisted = localBoolean;
        insertEventInviteeList(paramContext, paramEsAccount, paramString1, localList);
        String str = localObject2.rsvpType;
        int i = 1 + localObject2.numAdditionalGuests.intValue();
        if ((localPlusEvent != null) && (localPlusEvent.inviteeSummary != null))
        {
          Iterator localIterator2 = localPlusEvent.inviteeSummary.iterator();
          label408: 
          while (localIterator2.hasNext())
          {
            InviteeSummary localInviteeSummary = (InviteeSummary)localIterator2.next();
            if (paramBoolean);
            for (int j = -1; ; j = 1)
            {
              if ((localInviteeSummary.count == null) || (!TextUtils.equals(localInviteeSummary.rsvpType, str)))
                break label408;
              localInviteeSummary.count = Integer.valueOf(Math.max(localInviteeSummary.count.intValue() + j * i, 0));
              break;
            }
          }
          SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
          String[] arrayOfString = new String[1];
          arrayOfString[0] = localPlusEvent.id;
          byte[] arrayOfByte1 = PlusEventJson.getInstance().toByteArray(localPlusEvent);
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("event_data", arrayOfByte1);
          localSQLiteDatabase.update("events", localContentValues, "event_id=?", arrayOfString);
          paramContext.getContentResolver().notifyChange(EsProvider.EVENTS_ALL_URI, null);
        }
      }
    }
  }

  public static boolean validateInstantShare(Context paramContext, EsAccount paramEsAccount)
  {
    return validateInstantShare(paramContext, paramEsAccount, getPlusEvent(paramContext, paramEsAccount, InstantUpload.getInstantShareEventId(paramContext)));
  }

  private static boolean validateInstantShare(Context paramContext, EsAccount paramEsAccount, PlusEvent paramPlusEvent)
  {
    if (EsLog.isLoggable("EsEventData", 4))
      Log.i("EsEventData", "#validateInstantShare; now: " + System.currentTimeMillis());
    boolean bool1 = false;
    if (paramEsAccount != null)
    {
      bool1 = false;
      if (paramPlusEvent == null);
    }
    try
    {
      String str1 = paramEsAccount.getGaiaId();
      boolean bool2 = paramEsAccount.isPlusPage();
      String str2 = paramPlusEvent.id;
      AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
      localAlarmManager.cancel(Intents.getEventFinishedIntent(paramContext, null));
      long l1 = System.currentTimeMillis();
      boolean bool3 = EsLog.isLoggable("EsEventData", 4);
      bool1 = false;
      if (bool3)
        Log.i("EsEventData", "#validateInstantShare; cur event: " + paramPlusEvent.id);
      bool1 = false;
      if (!bool2)
      {
        boolean bool4 = isInstantShareAllowed(paramPlusEvent, str1, l1);
        bool1 = false;
        if (bool4)
        {
          PendingIntent localPendingIntent = Intents.getViewEventActivityNotificationIntent(paramContext, paramEsAccount, str2, paramPlusEvent.creatorObfuscatedId);
          NotificationUtils.notifyInstantShareEnabled(paramContext, paramPlusEvent.name, localPendingIntent, false);
          long l2 = getEventEndTime(paramPlusEvent);
          localAlarmManager.set(0, l2, Intents.getEventFinishedIntent(paramContext, str2));
          bool1 = true;
          if (EsLog.isLoggable("EsEventData", 4))
            Log.i("EsEventData", "#validateInstantShare; keep IS; now: " + l1 + ", end: " + l2 + ", wake in: " + (l2 - l1));
          if (EsLog.isLoggable("EsEventData", 4))
          {
            StringBuilder localStringBuilder1 = new StringBuilder("Enable Instant Share; now: ");
            Date localDate1 = new Date(l1);
            StringBuilder localStringBuilder2 = localStringBuilder1.append(DateFormat.format("MMM dd, yyyy h:mmaa", localDate1)).append(", alarm: ");
            Date localDate2 = new Date(l2);
            Log.i("EsEventData", DateFormat.format("MMM dd, yyyy h:mmaa", localDate2));
          }
        }
      }
      return bool1;
    }
    finally
    {
      if (!bool1)
      {
        disableInstantShare(paramContext);
        NotificationUtils.cancelInstantShareEnabled(paramContext);
        if (EsLog.isLoggable("EsEventData", 4))
          Log.i("EsEventData", "Disable Instant Share");
      }
    }
  }

  public static class EventActivity
  {
    public int activityType;
    public String data;
    public String ownerGaiaId;
    public String ownerName;
    public long timestamp;
  }

  private static final class EventActivityKey
  {
    public String ownerGaiaId;
    public long timestamp;
    public int type;

    public final boolean equals(Object paramObject)
    {
      EventActivityKey localEventActivityKey = (EventActivityKey)paramObject;
      if ((this.type == localEventActivityKey.type) && (TextUtils.equals(this.ownerGaiaId, localEventActivityKey.ownerGaiaId)) && (this.timestamp == localEventActivityKey.timestamp));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final int hashCode()
    {
      int i = this.type;
      if (this.ownerGaiaId == null);
      for (int j = 0; ; j = this.ownerGaiaId.hashCode())
        return (int)(j + i + this.timestamp);
    }
  }

  private static final class EventActivityStatus
  {
    public int fingerprint;
    public String id;
  }

  public static class EventCoalescedFrame extends GenericJson
  {
    public List<EsEventData.EventPerson> people;
  }

  public static class EventComment extends GenericJson
  {
    public String commentId;
    public boolean ownedByViewer;
    public String text;
    public long totalPlusOnes;
  }

  public static class EventPerson extends GenericJson
  {
    public String gaiaId;
    public String name;
    public int numAdditionalGuests;
  }

  public static class InviteeList extends GenericJson
  {
    public List<Invitee> invitees;
  }

  public static final class ResolvedPerson
  {
    public String avatarUrl;
    public String gaiaId;
    public String name;

    public ResolvedPerson(String paramString1, String paramString2, String paramString3)
    {
      this.name = paramString1;
      this.gaiaId = paramString2;
      this.avatarUrl = paramString3;
    }
  }

  private static final class ThemeStatus
  {
    String imageUrl;
    boolean isDefault;
    boolean isFeatured;
    String placeholderPath;
    int sortOrder;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsEventData
 * JD-Core Version:    0.6.2
 */