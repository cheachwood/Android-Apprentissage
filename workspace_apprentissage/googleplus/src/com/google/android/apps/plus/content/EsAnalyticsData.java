package com.google.android.apps.plus.content;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.ActionTarget;
import com.google.api.services.plusi.model.ActivityDetails;
import com.google.api.services.plusi.model.ClientActionData;
import com.google.api.services.plusi.model.ClientLoggedCircle;
import com.google.api.services.plusi.model.ClientLoggedCircleMember;
import com.google.api.services.plusi.model.ClientLoggedShareboxInfo;
import com.google.api.services.plusi.model.ClientLoggedSquare;
import com.google.api.services.plusi.model.ClientOutputData;
import com.google.api.services.plusi.model.ClientOzEvent;
import com.google.api.services.plusi.model.ClientOzEventJson;
import com.google.api.services.plusi.model.ClientOzExtension;
import com.google.api.services.plusi.model.ClientUserInfo;
import com.google.api.services.plusi.model.FavaDiagnostics;
import com.google.api.services.plusi.model.FavaDiagnosticsMemoryStats;
import com.google.api.services.plusi.model.FavaDiagnosticsNamespacedType;
import com.google.api.services.plusi.model.NotificationTypes;
import com.google.api.services.plusi.model.OutputData;
import com.google.api.services.plusi.model.OzEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class EsAnalyticsData
{
  private static Boolean mIsTabletDevice;
  private static final Runtime runtime = Runtime.getRuntime();

  public static void bulkInsert(Context paramContext, EsAccount paramEsAccount, List<ClientOzEvent> paramList)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
      try
      {
        ContentValues localContentValues = new ContentValues();
        ClientOzEventJson localClientOzEventJson = ClientOzEventJson.getInstance();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          localContentValues.put("event_data", localClientOzEventJson.toByteArray((ClientOzEvent)localIterator.next()));
          localSQLiteDatabase.insert("analytics_events", null, localContentValues);
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      label103: return;
    }
    catch (Exception localException)
    {
      break label103;
    }
  }

  public static ClientOzEvent createClientOzEvent(OzActions paramOzActions, OzViews paramOzViews1, OzViews paramOzViews2, long paramLong1, long paramLong2, Bundle paramBundle)
  {
    ClientOzEvent localClientOzEvent = new ClientOzEvent();
    localClientOzEvent.clientTimeMsec = Long.valueOf(paramLong1);
    OzEvent localOzEvent = new OzEvent();
    FavaDiagnostics localFavaDiagnostics = new FavaDiagnostics();
    String str1;
    String str2;
    String str4;
    if ((paramLong1 > 0L) && (paramLong2 >= paramLong1))
    {
      localFavaDiagnostics.totalTimeMs = Integer.valueOf((int)(paramLong2 - paramLong1));
      str1 = null;
      str2 = null;
      if (paramOzActions != null)
      {
        localFavaDiagnostics.actionType = paramOzActions.getFavaDiagnosticsNamespacedType();
        FavaDiagnosticsNamespacedType localFavaDiagnosticsNamespacedType3 = localFavaDiagnostics.actionType;
        str1 = null;
        str2 = null;
        if (localFavaDiagnosticsNamespacedType3 != null)
        {
          str2 = localFavaDiagnostics.actionType.namespace;
          boolean bool6 = EsLog.isLoggable("EsAnalyticsData", 3);
          str1 = null;
          if (bool6)
          {
            Log.d("EsAnalyticsData", "Action name: " + paramOzActions + " namespace: " + localFavaDiagnostics.actionType.namespace + " typeNum: " + localFavaDiagnostics.actionType.typeNum);
            str1 = paramOzActions.name();
          }
        }
      }
      str3 = null;
      if (paramOzViews1 != null)
      {
        OutputData localOutputData2 = paramOzViews1.getViewData();
        if (localOutputData2 != null)
          localOzEvent.startViewData = localOutputData2;
        localFavaDiagnostics.startView = paramOzViews1.getFavaDiagnosticsNamespacedType();
        FavaDiagnosticsNamespacedType localFavaDiagnosticsNamespacedType2 = localFavaDiagnostics.startView;
        str3 = null;
        if (localFavaDiagnosticsNamespacedType2 != null)
        {
          boolean bool5 = EsLog.isLoggable("EsAnalyticsData", 3);
          str3 = null;
          if (bool5)
          {
            Log.d("EsAnalyticsData", "StartView name: " + paramOzViews1 + " namespace: " + localFavaDiagnostics.startView.namespace + " typeNum: " + localFavaDiagnostics.startView.typeNum + " filterType: " + getFilterType(localOutputData2) + " tab: " + getTab(localOutputData2));
            str3 = paramOzViews1.name();
          }
        }
      }
      str4 = null;
      if (paramOzViews2 != null)
      {
        OutputData localOutputData1 = paramOzViews2.getViewData();
        if (localOutputData1 != null)
          localOzEvent.endViewData = localOutputData1;
        localFavaDiagnostics.endView = paramOzViews2.getFavaDiagnosticsNamespacedType();
        FavaDiagnosticsNamespacedType localFavaDiagnosticsNamespacedType1 = localFavaDiagnostics.endView;
        str4 = null;
        if (localFavaDiagnosticsNamespacedType1 != null)
        {
          str2 = localFavaDiagnostics.endView.namespace;
          boolean bool4 = EsLog.isLoggable("EsAnalyticsData", 3);
          str4 = null;
          if (bool4)
          {
            Log.d("EsAnalyticsData", "EndView name: " + paramOzViews2 + " namespace: " + localFavaDiagnostics.endView.namespace + " typeNum: " + localFavaDiagnostics.endView.typeNum + " filterType: " + getFilterType(localOutputData1) + " tab: " + getTab(localOutputData1));
            str4 = paramOzViews2.name();
          }
        }
      }
      if (((localFavaDiagnostics.actionType != null) || (localFavaDiagnostics.endView != null)) && (localFavaDiagnostics.startView == null))
      {
        if (str2 != null)
          break label1153;
        localFavaDiagnostics.startView = OzViews.UNKNOWN.getFavaDiagnosticsNamespacedType();
      }
    }
    ArrayList localArrayList8;
    StringBuilder localStringBuilder4;
    for (String str3 = OzViews.UNKNOWN.name(); ; str3 = "UNKNOWN:" + str2)
    {
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "StartView name: " + paramOzViews1 + " namespace: " + localFavaDiagnostics.startView.namespace + " typeNum: " + localFavaDiagnostics.startView.typeNum);
      if (paramBundle == null)
        break label2733;
      if (paramBundle.containsKey("extra_start_view_extras"))
      {
        String str14 = getGaiaId(paramBundle.getBundle("extra_start_view_extras"));
        if (!TextUtils.isEmpty(str14))
        {
          localClientOzEvent.startViewData = new ClientOutputData();
          setUserInfo(localClientOzEvent.startViewData, str14);
          if (EsLog.isLoggable("EsAnalyticsData", 3))
            Log.d("EsAnalyticsData", "createClientOzEvent: start view target gaiaId: " + str14);
        }
      }
      if (paramBundle.containsKey("extra_end_view_extras"))
      {
        String str13 = getGaiaId(paramBundle.getBundle("extra_end_view_extras"));
        if (!TextUtils.isEmpty(str13))
        {
          localClientOzEvent.endViewData = new ClientOutputData();
          setUserInfo(localClientOzEvent.endViewData, str13);
          if (EsLog.isLoggable("EsAnalyticsData", 3))
            Log.d("EsAnalyticsData", "createClientOzEvent: end view target gaiaId: " + str13);
        }
      }
      if (paramBundle.containsKey("extra_platform_event"))
      {
        localOzEvent.isNativePlatformEvent = Boolean.valueOf(paramBundle.getBoolean("extra_platform_event", false));
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: isPlatform: " + localOzEvent.isNativePlatformEvent);
      }
      if ((paramBundle.containsKey("extra_gaia_id")) || (paramBundle.containsKey("extra_participant_ids")) || (paramBundle.containsKey("extra_circle_ids")) || (paramBundle.containsKey("extra_square_id")) || (paramBundle.containsKey("extra_posting_mode")))
        localClientOzEvent.actionData = new ClientActionData();
      if (paramBundle.containsKey("extra_gaia_id"))
      {
        String str12 = paramBundle.getString("extra_gaia_id");
        if (!TextUtils.isEmpty(str12))
        {
          ArrayList localArrayList9 = new ArrayList(1);
          localArrayList9.add(str12);
          localClientOzEvent.actionData.obfuscatedGaiaId = localArrayList9;
          if (EsLog.isLoggable("EsAnalyticsData", 3))
            Log.d("EsAnalyticsData", "createClientOzEvent: target gaiaId: " + str12);
        }
      }
      if (!paramBundle.containsKey("extra_participant_ids"))
        break label1256;
      ArrayList localArrayList7 = paramBundle.getStringArrayList("extra_participant_ids");
      if ((localArrayList7 == null) || (localArrayList7.isEmpty()))
        break label1256;
      localArrayList8 = new ArrayList();
      localStringBuilder4 = new StringBuilder();
      Iterator localIterator2 = localArrayList7.iterator();
      while (localIterator2.hasNext())
      {
        String str11 = (String)localIterator2.next();
        ClientLoggedCircleMember localClientLoggedCircleMember = new ClientLoggedCircleMember();
        localClientLoggedCircleMember.obfuscatedGaiaId = str11;
        localArrayList8.add(localClientLoggedCircleMember);
        localStringBuilder4.append(str11 + " ");
      }
      localFavaDiagnostics.totalTimeMs = Integer.valueOf(0);
      break;
      label1153: localFavaDiagnostics.startView = new FavaDiagnosticsNamespacedType();
      localFavaDiagnostics.startView.namespace = str2;
      localFavaDiagnostics.startView.typeNum = Integer.valueOf(0);
    }
    localClientOzEvent.actionData.circleMember = localArrayList8;
    if (EsLog.isLoggable("EsAnalyticsData", 3))
      Log.d("EsAnalyticsData", "createClientOzEvent: participants: " + localStringBuilder4.toString());
    label1256: if (paramBundle.containsKey("extra_circle_ids"))
    {
      ArrayList localArrayList5 = paramBundle.getStringArrayList("extra_circle_ids");
      if ((localArrayList5 != null) && (!localArrayList5.isEmpty()))
      {
        ArrayList localArrayList6 = new ArrayList();
        StringBuilder localStringBuilder3 = new StringBuilder();
        Iterator localIterator1 = localArrayList5.iterator();
        while (localIterator1.hasNext())
        {
          String str10 = (String)localIterator1.next();
          ClientLoggedCircle localClientLoggedCircle = new ClientLoggedCircle();
          localClientLoggedCircle.circleId = str10;
          localArrayList6.add(localClientLoggedCircle);
          localStringBuilder3.append(str10 + " ");
        }
        localClientOzEvent.actionData.circle = localArrayList6;
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: circleIds: " + localStringBuilder3.toString());
      }
    }
    if (paramBundle.containsKey("extra_square_id"))
    {
      String str9 = paramBundle.getString("extra_square_id");
      if (!TextUtils.isEmpty(str9))
      {
        localClientOzEvent.actionData.square = new ClientLoggedSquare();
        localClientOzEvent.actionData.square.obfuscatedGaiaId = str9;
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: target squareId: " + str9);
      }
    }
    if (paramBundle.containsKey("extra_posting_mode"))
    {
      String str8 = paramBundle.getString("extra_posting_mode");
      if (!TextUtils.isEmpty(str8))
      {
        localClientOzEvent.actionData.shareboxInfo = new ClientLoggedShareboxInfo();
        localClientOzEvent.actionData.shareboxInfo.postingMode = str8;
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: postingMode: " + str8);
      }
    }
    if ((paramBundle.containsKey("extra_activity_id")) || (paramBundle.containsKey("extra_comment_id")) || (paramBundle.containsKey("extra_notification_read")) || (paramBundle.containsKey("extra_notification_types")) || (paramBundle.containsKey("extra_coalescing_codes")) || (paramBundle.containsKey("extra_num_unread_notifi")) || (paramBundle.containsKey("extra_media_url")) || (paramBundle.containsKey("extra_has_emotishare")) || (paramBundle.containsKey("extra_external_url")) || (paramBundle.containsKey("extra_prev_num_unread_noti")) || (paramBundle.containsKey("extra_creation_source_id")))
      localOzEvent.actionTarget = new ActionTarget();
    if (paramBundle.containsKey("extra_activity_id"))
    {
      String str7 = paramBundle.getString("extra_activity_id");
      if (!TextUtils.isEmpty(str7))
      {
        localOzEvent.actionTarget.activityId = str7;
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: activityId: " + str7);
      }
    }
    if (paramBundle.containsKey("extra_comment_id"))
    {
      String str6 = paramBundle.getString("extra_comment_id");
      if (!TextUtils.isEmpty(str6))
      {
        localOzEvent.actionTarget.commentId = str6;
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: commentId: " + str6);
      }
    }
    boolean bool2;
    boolean bool3;
    if (paramBundle.containsKey("extra_notification_read"))
    {
      boolean bool1 = paramBundle.getBoolean("extra_notification_read", false);
      ActionTarget localActionTarget = localOzEvent.actionTarget;
      if (bool1)
        break label2379;
      bool2 = true;
      localActionTarget.isUnreadNotification = Boolean.valueOf(bool2);
      if (EsLog.isLoggable("EsAnalyticsData", 3))
      {
        StringBuilder localStringBuilder2 = new StringBuilder("createClientOzEvent: isUnreadNotification: ");
        if (bool1)
          break label2385;
        bool3 = true;
        label1972: Log.d("EsAnalyticsData", bool3);
      }
    }
    if (paramBundle.containsKey("extra_num_unread_notifi"))
    {
      int n = paramBundle.getInt("extra_num_unread_notifi");
      localOzEvent.actionTarget.numUnreadNotifications = Integer.valueOf(n);
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "createClientOzEvent: numUnreadNotifications: " + n);
    }
    if (paramBundle.containsKey("extra_prev_num_unread_noti"))
    {
      int m = paramBundle.getInt("extra_prev_num_unread_noti");
      localOzEvent.actionTarget.previousNumUnreadNotifications = Integer.valueOf(m);
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "createClientOzEvent: previousNumUnreadNotifications: " + m);
    }
    if ((paramBundle.containsKey("extra_notification_types")) && (paramBundle.containsKey("extra_coalescing_codes")))
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      ArrayList localArrayList1 = paramBundle.getIntegerArrayList("extra_notification_types");
      ArrayList localArrayList2 = paramBundle.getStringArrayList("extra_coalescing_codes");
      if ((localArrayList1 != null) && (localArrayList2 != null) && (!localArrayList1.isEmpty()) && (localArrayList1.size() == localArrayList2.size()))
      {
        ArrayList localArrayList3 = new ArrayList();
        int i = 0;
        label2216: int j = localArrayList1.size();
        if (i < j)
        {
          NotificationTypes localNotificationTypes = new NotificationTypes();
          ArrayList localArrayList4 = new ArrayList(1);
          Integer localInteger = (Integer)localArrayList1.get(i);
          if (localInteger == null);
          for (int k = 0; ; k = localInteger.intValue())
          {
            localArrayList4.add(Integer.valueOf(k));
            localNotificationTypes.type = localArrayList4;
            String str5 = (String)localArrayList2.get(i);
            if (!TextUtils.isEmpty(str5))
              localNotificationTypes.coalescingCode = str5;
            localStringBuilder1.append("(" + localArrayList4.get(0) + ":" + str5 + ") ");
            localArrayList3.add(localNotificationTypes);
            i++;
            break label2216;
            label2379: bool2 = false;
            break;
            label2385: bool3 = false;
            break label1972;
          }
        }
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "createClientOzEvent: notificationTypes: " + localStringBuilder1.toString());
        localOzEvent.actionTarget.notificationTypes = localArrayList3;
      }
    }
    if (paramBundle.containsKey("extra_external_url"))
    {
      localOzEvent.actionTarget.externalUrl = paramBundle.getString("extra_external_url");
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "createClientOzEvent: externalUrl: " + localOzEvent.actionTarget.externalUrl);
    }
    if ((paramBundle.containsKey("extra_has_emotishare")) || (paramBundle.containsKey("extra_media_url")) || (paramBundle.containsKey("extra_creation_source_id")))
      localOzEvent.actionTarget.activityDetails = new ActivityDetails();
    if (paramBundle.containsKey("extra_has_emotishare"))
    {
      localOzEvent.actionTarget.activityDetails.embedType = Integer.valueOf(334);
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "createClientOzEvent: embedType: " + localOzEvent.actionTarget.activityDetails.embedType);
    }
    if (paramBundle.containsKey("extra_media_url"))
    {
      localOzEvent.actionTarget.activityDetails.mediaUrl = paramBundle.getString("extra_media_url");
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "createClientOzEvent: mediaUrl: " + localOzEvent.actionTarget.activityDetails.mediaUrl);
    }
    if (paramBundle.containsKey("extra_creation_source_id"))
      localOzEvent.actionTarget.activityDetails.sourceStreamId = paramBundle.getString("extra_creation_source_id");
    label2733: if (EsLog.ENABLE_DOGFOOD_FEATURES)
    {
      FavaDiagnosticsMemoryStats localFavaDiagnosticsMemoryStats = new FavaDiagnosticsMemoryStats();
      localFavaDiagnosticsMemoryStats.jsHeapSizeLimit = Long.valueOf(runtime.maxMemory());
      localFavaDiagnosticsMemoryStats.totalJsHeapSize = Long.valueOf(runtime.totalMemory());
      long l = runtime.freeMemory();
      localFavaDiagnosticsMemoryStats.usedJsHeapSize = Long.valueOf(localFavaDiagnosticsMemoryStats.totalJsHeapSize.longValue() - l);
      if (EsLog.isLoggable("EsAnalyticsData", 3))
        Log.d("EsAnalyticsData", "MemoryStats Max: " + localFavaDiagnosticsMemoryStats.jsHeapSizeLimit + " Total: " + localFavaDiagnosticsMemoryStats.totalJsHeapSize + " Used: " + localFavaDiagnosticsMemoryStats.usedJsHeapSize + " Free: " + l);
      localFavaDiagnostics.memoryStats = localFavaDiagnosticsMemoryStats;
    }
    if (EsLog.isLoggable("EsAnalyticsData", 3))
    {
      if (str1 != null)
        break label2942;
      Log.d("EsAnalyticsData", String.format("EVENT SUMMARY: %s -> %s", new Object[] { str3, str4 }));
    }
    while (true)
    {
      localOzEvent.favaDiagnostics = localFavaDiagnostics;
      localClientOzEvent.ozEvent = localOzEvent;
      return localClientOzEvent;
      label2942: if (str4 == null)
        Log.d("EsAnalyticsData", String.format("EVENT SUMMARY: %s in %s", new Object[] { str1, str3 }));
      else
        Log.d("EsAnalyticsData", String.format("EVENT SUMMARY: %s in %s (unexpected endView: %s)", new Object[] { str1, str3, paramOzViews2 }));
    }
  }

  public static ClientOzExtension createClientOzExtension(Context paramContext)
  {
    ClientOzExtension localClientOzExtension = new ClientOzExtension();
    localClientOzExtension.sendTimeMsec = Long.valueOf(System.currentTimeMillis());
    boolean bool1;
    if (mIsTabletDevice != null)
      bool1 = mIsTabletDevice.booleanValue();
    while (true)
    {
      ClientId localClientId;
      if (bool1)
      {
        localClientId = ClientId.ANDROID_TABLET;
        localClientOzExtension.clientId = localClientId.value();
        if (EsLog.isLoggable("EsAnalyticsData", 3))
          Log.d("EsAnalyticsData", "Set the client id to " + localClientId.name() + " " + localClientId.value());
      }
      try
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        String str = paramContext.getPackageName();
        localClientOzExtension.callingApplication = str;
        localClientOzExtension.clientVersion = localPackageManager.getPackageInfo(str, 0).versionName;
        return localClientOzExtension;
        mIsTabletDevice = Boolean.valueOf(false);
        if (Build.VERSION.SDK_INT < 11)
        {
          bool1 = mIsTabletDevice.booleanValue();
          continue;
        }
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
        if (localWindowManager == null)
        {
          bool1 = mIsTabletDevice.booleanValue();
          continue;
        }
        Display localDisplay = localWindowManager.getDefaultDisplay();
        if (localDisplay == null)
        {
          bool1 = mIsTabletDevice.booleanValue();
          continue;
        }
        localDisplay.getMetrics(localDisplayMetrics);
        if ((localDisplayMetrics.xdpi == 0.0D) || (localDisplayMetrics.ydpi == 0.0D))
        {
          bool1 = mIsTabletDevice.booleanValue();
          continue;
        }
        double d1 = localDisplayMetrics.widthPixels / localDisplayMetrics.xdpi;
        double d2 = localDisplayMetrics.heightPixels / localDisplayMetrics.ydpi;
        if (Math.sqrt(d1 * d1 + d2 * d2) >= 5.0D);
        for (boolean bool2 = true; ; bool2 = false)
        {
          Boolean localBoolean = Boolean.valueOf(bool2);
          mIsTabletDevice = localBoolean;
          bool1 = localBoolean.booleanValue();
          break;
        }
        localClientId = ClientId.ANDROID_OS;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        while (true)
          localNameNotFoundException.printStackTrace();
      }
    }
  }

  public static Bundle createExtras(String paramString1, String paramString2)
  {
    Bundle localBundle;
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      localBundle = null;
    while (true)
    {
      return localBundle;
      localBundle = new Bundle();
      localBundle.putString(paramString1, paramString2);
    }
  }

  private static Integer getFilterType(OutputData paramOutputData)
  {
    if ((paramOutputData == null) || (paramOutputData.filterType == null));
    for (Integer localInteger = null; ; localInteger = paramOutputData.filterType)
      return localInteger;
  }

  private static String getGaiaId(Bundle paramBundle)
  {
    if (paramBundle == null);
    for (String str = null; ; str = paramBundle.getString("extra_gaia_id"))
      return str;
  }

  private static Integer getTab(OutputData paramOutputData)
  {
    if ((paramOutputData == null) || (paramOutputData.tab == null));
    for (Integer localInteger = null; ; localInteger = paramOutputData.tab)
      return localInteger;
  }

  public static void insert(Context paramContext, EsAccount paramEsAccount, byte[] paramArrayOfByte)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("event_data", paramArrayOfByte);
      localSQLiteDatabase.insert("analytics_events", null, localContentValues);
      label38: return;
    }
    catch (Exception localException)
    {
      break label38;
    }
  }

  public static long queryLastAnalyticsSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_analytics_sync_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  public static List<ClientOzEvent> removeAll(Context paramContext, EsAccount paramEsAccount)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
      try
      {
        localCursor = localSQLiteDatabase.query("analytics_events", new String[] { "event_data" }, null, null, null, null, null);
        if (localCursor == null)
        {
          localSQLiteDatabase.endTransaction();
          label56: return localArrayList;
        }
      }
      finally
      {
        try
        {
          int i = localCursor.getColumnIndexOrThrow("event_data");
          for (int j = 0; localCursor.moveToPosition(j); j++)
          {
            byte[] arrayOfByte = localCursor.getBlob(i);
            ClientOzEvent localClientOzEvent = (ClientOzEvent)ClientOzEventJson.getInstance().fromByteArray(arrayOfByte);
            if (localClientOzEvent != null)
              localArrayList.add(localClientOzEvent);
          }
          localCursor.close();
          localSQLiteDatabase.delete("analytics_events", null, null);
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
        }
        finally
        {
          Cursor localCursor;
          localCursor.close();
        }
        localSQLiteDatabase.endTransaction();
      }
    }
    catch (Exception localException)
    {
      break label56;
    }
  }

  public static void saveLastAnalyticsSyncTimestamp(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("last_analytics_sync_time", Long.valueOf(paramLong));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
  }

  private static void setUserInfo(ClientOutputData paramClientOutputData, String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramClientOutputData != null))
    {
      ClientUserInfo localClientUserInfo = new ClientUserInfo();
      localClientUserInfo.obfuscatedGaiaId = paramString;
      paramClientOutputData.userInfo = new ArrayList(1);
      paramClientOutputData.userInfo.add(localClientUserInfo);
    }
  }

  static enum ClientId
  {
    private final String mValue;

    static
    {
      ClientId[] arrayOfClientId = new ClientId[2];
      arrayOfClientId[0] = ANDROID_OS;
      arrayOfClientId[1] = ANDROID_TABLET;
    }

    private ClientId(String paramString)
    {
      this.mValue = paramString;
    }

    public final String value()
    {
      return this.mValue;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsAnalyticsData
 * JD-Core Version:    0.6.2
 */