package com.google.android.apps.plus.content;

import android.accounts.Account;
import android.app.AlarmManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.api.GetMobileExperimentsOperation;
import com.google.android.apps.plus.api.GetSettingsOperation;
import com.google.android.apps.plus.api.SetSettingsOperation;
import com.google.android.apps.plus.hangout.GCommApp;
import com.google.android.apps.plus.iu.InstantUploadSyncService;
import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.service.AndroidContactsSync;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsSyncAdapterService;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.SkyjamPlaybackService;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.Property;
import com.google.api.services.plusi.model.GetMobileExperimentsResponseExperiment;
import com.google.api.services.plusi.model.GetMobileExperimentsResponseExperimentJson;
import com.google.api.services.plusi.model.GetMobileExperimentsResponseExperimentValue;
import com.google.api.services.plusi.model.ShareboxSettings;
import com.google.api.services.plusi.model.SharingRoster;
import com.google.api.services.plusi.model.SharingTargetId;
import com.google.api.services.plusi.model.SharingTargetIdJson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class EsAccountsData
{
  private static final EsJson<ExperimentList> EXPERIMENT_LIST_JSON = EsJson.buildJson(ExperimentList.class, new Object[] { GetMobileExperimentsResponseExperimentJson.class, "flags" });
  private static ArrayList<ExperimentListener> sExperimentListeners;
  private static HashMap<String, String> sExperiments = new HashMap();
  private static boolean sHadSharingRoster;

  static
  {
    sExperimentListeners = new ArrayList();
  }

  public static List<String> accountsChanged(Context paramContext)
  {
    if (EsLog.isLoggable("EsAccountsData", 3))
      Log.d("EsAccountsData", "accountsChanged");
    List localList = filterRemovedAccounts(paramContext);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
      deactivateAccount(paramContext, (String)localIterator.next(), true);
    return localList;
  }

  public static void activateAccount(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    EsDatabaseHelper localEsDatabaseHelper = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount);
    localEsDatabaseHelper.createNewDatabase();
    SQLiteDatabase localSQLiteDatabase = localEsDatabaseHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("last_sync_time", Integer.valueOf(-1));
    localContentValues.put("last_stats_sync_time", Integer.valueOf(-1));
    localContentValues.put("last_contacted_time", Integer.valueOf(-1));
    localContentValues.put("wipeout_stats", Integer.valueOf(-1));
    localContentValues.put("circle_sync_time", Integer.valueOf(-1));
    localContentValues.put("people_sync_time", Integer.valueOf(-1));
    localContentValues.putNull("people_last_update_token");
    localContentValues.put("avatars_downloaded", Integer.valueOf(0));
    localContentValues.put("user_id", paramEsAccount.getGaiaId());
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
    EsPeopleData.activateAccount$1f9c1b47();
    EsSyncAdapterService.activateAccount(paramContext, paramEsAccount.getName());
    InstantUploadSyncService.activateAccount(paramContext, paramEsAccount.getName());
    InstantUpload.showFirstTimeFullSizeNotification(paramContext, paramEsAccount);
    EsPeopleData.insertSelf(paramContext, paramEsAccount, paramString);
  }

  private static boolean compareAudiences(AudienceData paramAudienceData1, AudienceData paramAudienceData2)
  {
    boolean bool = false;
    if (paramAudienceData1 != null)
    {
      bool = false;
      if (paramAudienceData2 != null)
        break label14;
    }
    while (true)
    {
      return bool;
      label14: SharingRoster localSharingRoster = EsPeopleData.convertAudienceToSharingRoster(paramAudienceData1);
      HashSet localHashSet = new HashSet(localSharingRoster.sharingTargetId.size());
      Iterator localIterator1 = localSharingRoster.sharingTargetId.iterator();
      while (localIterator1.hasNext())
      {
        SharingTargetId localSharingTargetId2 = (SharingTargetId)localIterator1.next();
        localHashSet.add(SharingTargetIdJson.getInstance().toString(localSharingTargetId2));
      }
      Iterator localIterator2 = EsPeopleData.convertAudienceToSharingRoster(paramAudienceData2).sharingTargetId.iterator();
      while (true)
        if (localIterator2.hasNext())
        {
          SharingTargetId localSharingTargetId1 = (SharingTargetId)localIterator2.next();
          if (!localHashSet.remove(SharingTargetIdJson.getInstance().toString(localSharingTargetId1)))
          {
            bool = false;
            break;
          }
        }
      bool = localHashSet.isEmpty();
    }
  }

  public static void deactivateAccount(Context paramContext, String paramString, boolean paramBoolean)
  {
    while (true)
    {
      SharedPreferences.Editor localEditor1;
      SharedPreferences.Editor localEditor2;
      try
      {
        if (EsLog.isLoggable("EsAccountsData", 3))
          Log.d("EsAccountsData", "deactivateAccount: " + paramString);
        final EsAccount localEsAccount1 = getAccountByName(paramContext, paramString);
        if (localEsAccount1 == null)
          return;
        EsSyncAdapterService.deactivateAccount(paramString);
        InstantUploadSyncService.deactivateAccount(paramContext, paramString);
        EsAccount localEsAccount2 = getActiveAccount(paramContext);
        if ((localEsAccount2 != null) && (localEsAccount2.getIndex() == localEsAccount1.getIndex()))
        {
          localEditor1 = paramContext.getSharedPreferences("accounts", 0).edit();
          localEditor1.remove("active_account");
          if (Build.VERSION.SDK_INT >= 9)
          {
            localEditor1.apply();
            localEditor2 = paramContext.getSharedPreferences("streams", 0).edit();
            localEditor2.remove("circle");
            if (Build.VERSION.SDK_INT < 9)
              break label344;
            localEditor2.apply();
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
              public final void run()
              {
                RealTimeChatService.logOut(this.val$context, localEsAccount1);
                SkyjamPlaybackService.logOut(this.val$context);
              }
            });
          }
        }
        else
        {
          InstantUpload.cancelAllUploads(paramContext, localEsAccount1);
          InstantUpload.enableInstantUpload(paramContext, null, false);
          EsEventData.disableInstantShare(paramContext);
          PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putBoolean("iu.received_low_quota", false).putBoolean("iu.received_no_quota", false).commit();
          ((AlarmManager)paramContext.getSystemService("alarm")).cancel(Intents.getEventFinishedIntent(paramContext, null));
          EsNotificationData.deactivateAccount(paramContext, localEsAccount1);
          EsProvider.deleteDatabase(paramContext, localEsAccount1);
          AndroidContactsSync.deactivateAccount(paramContext, localEsAccount1);
          deleteAccount(paramContext, paramString, paramBoolean);
          GCommApp.deactivateAccount(paramContext, localEsAccount1);
          ImageCache.getInstance(paramContext).clear();
          if (!EsLog.isLoggable("EsAccountsData", 3))
            continue;
          Log.d("EsAccountsData", "Account deactivated: " + paramString);
          continue;
        }
      }
      finally
      {
      }
      localEditor1.commit();
      continue;
      label344: localEditor2.commit();
    }
  }

  private static void deleteAccount(Context paramContext, String paramString, boolean paramBoolean)
  {
    try
    {
      if (EsLog.isLoggable("EsAccountsData", 3))
        Log.d("EsAccountsData", "deleteAccount: " + paramString);
      EsAccount localEsAccount = getAccountByName(paramContext, paramString);
      if (localEsAccount == null);
      while (true)
      {
        return;
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
        int i = localEsAccount.getIndex();
        localEditor.remove(i + ".gaia_id");
        localEditor.remove(i + ".display_name");
        localEditor.remove(i + ".is_child");
        localEditor.remove(i + ".is_plus_page");
        localEditor.remove(i + ".location_dialog");
        localEditor.remove(i + ".warm_welcome_ts");
        localEditor.remove(i + ".last_photo_notification_ts");
        localEditor.remove(i + ".seen_hangout_minor_warning");
        localEditor.remove(i + ".seen_hangout_abuse_warning");
        localEditor.remove(i + ".stream_views");
        localEditor.remove(i + ".settings_synced");
        localEditor.remove(i + ".minor_public_extended_dialog");
        localEditor.remove(i + ".one_click_tooltip_shown");
        localEditor.remove("contacts_clean");
        localEditor.remove("contacts_stats_clean");
        localEditor.remove("recent_images_timestamp");
        if (paramBoolean)
        {
          localEditor.remove(i + ".account_name");
          localEditor.remove(i + ".contacts_sync");
          localEditor.remove(i + ".contacts_stats_sync");
          localEditor.remove(i + ".iu_enabled");
          localEditor.remove(i + ".iu_photos_wifi_only");
          localEditor.remove(i + ".iu_videos_wifi_only");
          localEditor.remove(i + ".contacts_oob_completed");
          localEditor.remove(i + ".iu_oob_completed");
          localEditor.remove(i + ".find_people_promo_ts");
          localEditor.remove(i + ".flags");
        }
        localEditor.commit();
      }
    }
    finally
    {
    }
  }

  private static List<String> filterRemovedAccounts(Context paramContext)
  {
    ArrayList localArrayList1;
    try
    {
      List localList = AccountsUtil.getAccounts(paramContext);
      localArrayList1 = new ArrayList(localList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
        localArrayList1.add(((Account)localIterator.next()).name);
    }
    finally
    {
    }
    ArrayList localArrayList2 = new ArrayList();
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = localSharedPreferences.getInt("count", 0);
    for (int j = 0; j < i; j++)
    {
      String str = localSharedPreferences.getString(j + ".account_name", null);
      if ((str != null) && (!localArrayList1.contains(str)))
        localArrayList2.add(str);
    }
    return localArrayList2;
  }

  private static EsAccount getAccount(Context paramContext, int paramInt, boolean paramBoolean)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    String str1 = localSharedPreferences.getString(paramInt + ".gaia_id", null);
    String str2;
    String str3;
    if ((str1 != null) || (paramBoolean))
      if (EsLog.isLoggable("EsAccountsData", 3))
      {
        StringBuilder localStringBuilder = new StringBuilder("EsAccount.getAccount returning account for gaiaId: ");
        if (str1 == null)
        {
          str2 = "null";
          Log.d("EsAccountsData", str2);
        }
      }
      else
      {
        str3 = localSharedPreferences.getString(paramInt + ".account_name", null);
        if (str3 == null)
          str3 = localSharedPreferences.getString(paramInt + ".name", null);
      }
    for (EsAccount localEsAccount = new EsAccount(str3, str1, localSharedPreferences.getString(paramInt + ".display_name", null), localSharedPreferences.getBoolean(paramInt + ".is_child", false), localSharedPreferences.getBoolean(paramInt + ".is_plus_page", false), paramInt); ; localEsAccount = null)
    {
      return localEsAccount;
      str2 = str1;
      break;
      if (EsLog.isLoggable("EsAccountsData", 6))
        Log.e("EsAccountsData", "EsAccount.getAccount failed to get account " + paramInt);
    }
  }

  public static EsAccount getAccountByName(Context paramContext, String paramString)
  {
    try
    {
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = localSharedPreferences.getInt("count", 0);
      int j = 0;
      EsAccount localEsAccount = null;
      if (j < i)
      {
        String str = localSharedPreferences.getString(j + ".account_name", null);
        if (paramString.equals(str))
          localEsAccount = new EsAccount(str, localSharedPreferences.getString(j + ".gaia_id", null), localSharedPreferences.getString(j + ".display_name", null), localSharedPreferences.getBoolean(j + ".is_child", false), localSharedPreferences.getBoolean(j + ".is_plus_page", false), j);
      }
      else
      {
        return localEsAccount;
      }
      j++;
    }
    finally
    {
    }
  }

  public static EsAccount getActiveAccount(Context paramContext)
  {
    try
    {
      int i = paramContext.getSharedPreferences("accounts", 0).getInt("active_account", -1);
      if (i == -1);
      EsAccount localEsAccount;
      for (Object localObject2 = null; ; localObject2 = localEsAccount)
      {
        return localObject2;
        localEsAccount = getAccount(paramContext, i, false);
      }
    }
    finally
    {
    }
  }

  public static EsAccount getActiveAccountUnsafe(Context paramContext)
  {
    try
    {
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = localSharedPreferences.getInt("active_account", -1);
      if (i == -1)
      {
        int j = localSharedPreferences.getInt("active", -1);
        i = j;
        if (i != -1);
      }
      EsAccount localEsAccount;
      for (Object localObject2 = null; ; localObject2 = localEsAccount)
      {
        return localObject2;
        localEsAccount = getAccount(paramContext, i, true);
      }
    }
    finally
    {
    }
  }

  public static String getExperiment(String paramString1, String paramString2)
  {
    synchronized (sExperiments)
    {
      String str = (String)sExperiments.get(paramString1);
      if (str != null)
        return str;
      str = paramString2;
    }
  }

  public static long getLastDatabaseCleanupTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    return localSharedPreferences.getLong(i + ".cleanup_timestamp", 0L);
  }

  public static ArrayList<String> getStreamViewList(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    String[] arrayOfString = localSharedPreferences.getString(i + ".stream_views", "").split(",");
    ArrayList localArrayList = new ArrayList();
    int j = arrayOfString.length;
    for (int k = 0; k < j; k++)
    {
      String str = arrayOfString[k];
      if (!TextUtils.isEmpty(str))
        localArrayList.add(str);
    }
    if (localArrayList.isEmpty())
    {
      localArrayList.add("v.whatshot");
      localArrayList.add("v.all.circles");
      localArrayList.add("v.nearby");
    }
    return localArrayList;
  }

  public static boolean hadSharingRoster()
  {
    return sHadSharingRoster;
  }

  public static boolean hasLoggedInThePast(Context paramContext)
  {
    return paramContext.getSharedPreferences("accounts", 0).contains("last_active");
  }

  public static boolean hasOneClickAddTooltipBeenShown(Context paramContext, EsAccount paramEsAccount)
  {
    int i = paramEsAccount.getIndex();
    return paramContext.getSharedPreferences("accounts", 0).getBoolean(i + ".one_click_tooltip_shown", false);
  }

  public static boolean hasSeenLocationDialog(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    return localSharedPreferences.getBoolean(i + ".location_dialog", false);
  }

  public static boolean hasSeenMinorHangoutWarningDialog(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    return localSharedPreferences.getBoolean(i + ".seen_hangout_minor_warning", false);
  }

  public static boolean hasSeenMinorPublicExtendedDialog(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    return localSharedPreferences.getBoolean(i + ".minor_public_extended_dialog", false);
  }

  public static boolean hasSeenReportAbusetWarningDialog(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    return localSharedPreferences.getBoolean(i + ".seen_hangout_abuse_warning", false);
  }

  public static boolean hasSeenWarmWelcome(Context paramContext, EsAccount paramEsAccount)
  {
    boolean bool1 = Property.WARM_WELCOME_ON_LOGIN.get().equalsIgnoreCase("false");
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = paramEsAccount.getIndex();
      boolean bool3 = localSharedPreferences.getLong(i + ".warm_welcome_ts", 0L) < 0L;
      bool2 = false;
      if (bool3)
        bool2 = true;
    }
  }

  public static boolean hasVisitedOob(Context paramContext)
  {
    return paramContext.getSharedPreferences("accounts", 0).getBoolean("visited_oob", false);
  }

  public static EsAccount insertAccount(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      if (EsLog.isLoggable("EsAccountsData", 3))
        Log.d("EsAccountsData", "insertAccount: " + paramString2);
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = -1;
      int j = localSharedPreferences.getInt("count", 0);
      int k = 0;
      if (k < j)
      {
        String str = localSharedPreferences.getString(k + ".account_name", null);
        if ((str != null) && (str.equals(paramString2)))
          i = k;
      }
      else
      {
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        if (i == -1)
        {
          i = j;
          localEditor.putInt("count", j + 1);
        }
        localEditor.putString(i + ".gaia_id", paramString1);
        localEditor.putString(i + ".account_name", paramString2);
        localEditor.putString(i + ".display_name", paramString3);
        localEditor.putBoolean(i + ".is_child", paramBoolean1);
        localEditor.putBoolean(i + ".is_plus_page", paramBoolean2);
        localEditor.putInt("active_account", i);
        localEditor.putInt("last_active", i);
        localEditor.commit();
        EsAccount localEsAccount = new EsAccount(paramString2, paramString1, paramString3, paramBoolean1, paramBoolean2, i);
        loadExperiments(paramContext, localEsAccount);
        return localEsAccount;
      }
      k++;
    }
    finally
    {
    }
  }

  public static void insertExperiments(Context paramContext, EsAccount paramEsAccount, List<GetMobileExperimentsResponseExperiment> paramList)
  {
    ExperimentList localExperimentList = new ExperimentList();
    localExperimentList.flags = paramList;
    String str1 = paramEsAccount.getName() + ".flags";
    String str2 = EXPERIMENT_LIST_JSON.toString(localExperimentList);
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    if (!TextUtils.equals(localSharedPreferences.getString(str1, null), str2))
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString(str1, str2);
      localEditor.commit();
      loadExperiments(paramList);
      int i = sExperimentListeners.size();
      for (int j = 0; j < i; j++)
        ((ExperimentListener)sExperimentListeners.get(j)).onExperimentsChanged();
    }
  }

  public static boolean isAccountUpgradeRequired(Context paramContext, EsAccount paramEsAccount)
  {
    int i = paramEsAccount.getIndex();
    boolean bool1 = false;
    if (i == -1);
    while (true)
    {
      return bool1;
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      boolean bool2 = localSharedPreferences.contains(i + ".gaia_id");
      bool1 = false;
      if (!bool2)
      {
        boolean bool3 = localSharedPreferences.contains(i + ".user_id");
        bool1 = false;
        if (bool3)
          bool1 = true;
      }
    }
  }

  public static boolean isContactsStatsSyncClean(Context paramContext)
  {
    return paramContext.getSharedPreferences("accounts", 0).getBoolean("contacts_stats_clean", false);
  }

  public static boolean isContactsStatsSyncEnabled(Context paramContext, EsAccount paramEsAccount)
  {
    boolean bool1 = paramEsAccount.isPlusPage();
    boolean bool2 = false;
    if (bool1);
    while (true)
    {
      return bool2;
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = paramEsAccount.getIndex();
      bool2 = localSharedPreferences.getBoolean(i + ".contacts_stats_sync", false);
    }
  }

  public static boolean isContactsStatsSyncPreferenceSet(Context paramContext, EsAccount paramEsAccount)
  {
    int i = paramEsAccount.getIndex();
    if (i == -1);
    for (boolean bool = true; ; bool = paramContext.getSharedPreferences("accounts", 0).contains(i + ".contacts_stats_sync"))
      return bool;
  }

  public static boolean isContactsStatsWipeoutNeeded(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT wipeout_stats  FROM account_status", null);
      boolean bool2 = l < 1L;
      bool1 = false;
      if (!bool2)
        bool1 = true;
      return bool1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        boolean bool1 = false;
    }
  }

  public static boolean isContactsSyncClean(Context paramContext)
  {
    return paramContext.getSharedPreferences("accounts", 0).getBoolean("contacts_clean", false);
  }

  public static boolean isContactsSyncEnabled(Context paramContext, EsAccount paramEsAccount)
  {
    boolean bool1 = paramEsAccount.isPlusPage();
    boolean bool2 = false;
    if (bool1);
    while (true)
    {
      return bool2;
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = paramEsAccount.getIndex();
      bool2 = localSharedPreferences.getBoolean(i + ".contacts_sync", false);
    }
  }

  public static boolean isContactsSyncPreferenceSet(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    if (i == -1);
    for (boolean bool = true; ; bool = localSharedPreferences.contains(i + ".contacts_sync"))
      return bool;
  }

  private static boolean isValidCustomAudience(AudienceData paramAudienceData)
  {
    boolean bool1 = false;
    if (paramAudienceData != null)
    {
      boolean bool2 = paramAudienceData.isEmpty();
      bool1 = false;
      if (!bool2)
        break label19;
    }
    while (true)
    {
      return bool1;
      label19: int i = paramAudienceData.getCircleCount();
      int j = paramAudienceData.getUserCount();
      int k = paramAudienceData.getSquareTargetCount();
      if ((j == 0) && (k == 0) && (i == 1))
      {
        int m = paramAudienceData.getCircle(0).getType();
        bool1 = false;
        if (m != 5)
        {
          bool1 = false;
          if (m != 8)
          {
            bool1 = false;
            if (m == 9);
          }
        }
      }
      else
      {
        bool1 = true;
      }
    }
  }

  public static void loadExperiments(Context paramContext)
  {
    EsAccount localEsAccount = getActiveAccount(paramContext);
    if (localEsAccount != null)
      loadExperiments(paramContext, localEsAccount);
  }

  private static void loadExperiments(Context paramContext, EsAccount paramEsAccount)
  {
    String str = paramContext.getSharedPreferences("accounts", 0).getString(paramEsAccount.getName() + ".flags", null);
    List localList = null;
    if (str != null)
      localList = ((ExperimentList)EXPERIMENT_LIST_JSON.fromString(str)).flags;
    loadExperiments(localList);
  }

  private static void loadExperiments(List<GetMobileExperimentsResponseExperiment> paramList)
  {
    Object localObject2;
    while (true)
    {
      int j;
      synchronized (sExperiments)
      {
        sExperiments.clear();
        if (paramList != null)
        {
          int i = paramList.size();
          j = 0;
          if (j < i)
          {
            GetMobileExperimentsResponseExperiment localGetMobileExperimentsResponseExperiment = (GetMobileExperimentsResponseExperiment)paramList.get(j);
            String str = localGetMobileExperimentsResponseExperiment.flagType;
            if (str == null)
              break label274;
            if ("BOOLEAN".equals(str))
            {
              break label274;
              if (localObject2 == null)
                break label282;
              sExperiments.put(localGetMobileExperimentsResponseExperiment.flagId, localObject2);
              break label282;
            }
            if ("STRING".equals(str))
            {
              if (localGetMobileExperimentsResponseExperiment.value == null)
                break label291;
              localObject2 = localGetMobileExperimentsResponseExperiment.value.stringValue;
              break;
            }
            if ("DOUBLE".equals(str))
            {
              GetMobileExperimentsResponseExperimentValue localGetMobileExperimentsResponseExperimentValue2 = localGetMobileExperimentsResponseExperiment.value;
              localObject2 = null;
              if (localGetMobileExperimentsResponseExperimentValue2 == null)
                continue;
              Double localDouble = localGetMobileExperimentsResponseExperiment.value.doubleValue;
              localObject2 = null;
              if (localDouble == null)
                continue;
              localObject2 = Double.toString(localGetMobileExperimentsResponseExperiment.value.doubleValue.doubleValue());
              continue;
            }
            boolean bool = "LONG".equals(str);
            localObject2 = null;
            if (!bool)
              continue;
            GetMobileExperimentsResponseExperimentValue localGetMobileExperimentsResponseExperimentValue1 = localGetMobileExperimentsResponseExperiment.value;
            localObject2 = null;
            if (localGetMobileExperimentsResponseExperimentValue1 == null)
              continue;
            Long localLong = localGetMobileExperimentsResponseExperiment.value.longValue;
            localObject2 = null;
            if (localLong == null)
              continue;
            localObject2 = Long.toString(localGetMobileExperimentsResponseExperiment.value.longValue.longValue());
            continue;
          }
        }
      }
      return;
      label274: localObject2 = "TRUE";
      continue;
      label282: j++;
    }
    while (true)
    {
      break;
      label291: localObject2 = null;
    }
  }

  public static long loadRecentImagesTimestamp(Context paramContext)
  {
    return paramContext.getSharedPreferences("accounts", 0).getLong("recent_images_timestamp", 0L);
  }

  public static boolean needContactSyncOob(Context paramContext, EsAccount paramEsAccount)
  {
    boolean bool1 = AndroidContactsSync.isContactsProviderAvailable(paramContext);
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
      int i = paramEsAccount.getIndex();
      boolean bool3 = localSharedPreferences.getBoolean(i + ".contacts_oob_completed", false);
      bool2 = false;
      if (!bool3)
        bool2 = true;
    }
  }

  public static boolean needInstantUploadOob(Context paramContext, EsAccount paramEsAccount)
  {
    boolean bool1 = true;
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    if (!localSharedPreferences.getBoolean(i + ".iu_oob_completed", false));
    while (true)
    {
      return bool1;
      boolean bool2 = localSharedPreferences.getBoolean(i + ".iu_enabled", false);
      boolean bool3 = InstantUpload.isSyncEnabled$1f9c1b43(paramEsAccount);
      boolean bool4 = ContentResolver.getMasterSyncAutomatically();
      if ((!bool2) || ((bool3) && (bool4)))
        bool1 = false;
    }
  }

  public static void onAccountUpgradeRequired(Context paramContext, int paramInt)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    localEditor.remove(paramInt + ".gaia_id");
    localEditor.putLong(paramInt + ".user_id", 0L);
    localEditor.commit();
  }

  public static int queryContactsSyncVersion(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT contacts_sync_version  FROM account_status", null);
      i = (int)l;
      return i;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        int i = -1;
    }
  }

  public static long queryLastContactedTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_contacted_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  public static long queryLastPhotoNotificationTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    return localSharedPreferences.getLong(i + ".last_photo_notification_ts", -1L);
  }

  private static long queryLastSettingsSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_settings_sync_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  public static long queryLastStatsSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_stats_sync_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  public static long queryLastSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_sync_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  public static boolean queryNotificationPushEnabled(Context paramContext, EsAccount paramEsAccount)
  {
    int i = 1;
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT push_notifications  FROM account_status", null);
      if ((int)l == i);
      while (true)
      {
        return i;
        j = 0;
      }
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        int j = 0;
    }
  }

  public static void registerExperimentListener(ExperimentListener paramExperimentListener)
  {
    sExperimentListeners.add(paramExperimentListener);
  }

  public static void restoreAccountSettings(Context paramContext, final EsAccount paramEsAccount)
  {
    if (paramEsAccount.isPlusPage())
      new AsyncTask()
      {
      }
      .execute(null);
    label167: label174: 
    while (true)
    {
      return;
      if (!needContactSyncOob(paramContext, paramEsAccount))
      {
        if (!isContactsStatsSyncEnabled(paramContext, paramEsAccount))
          break label167;
        EsService.disableWipeoutStats(paramContext, paramEsAccount);
      }
      while (true)
      {
        if (needInstantUploadOob(paramContext, paramEsAccount))
          break label174;
        SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
        int i = paramEsAccount.getIndex();
        final boolean bool = localSharedPreferences.getBoolean(i + ".iu_enabled", false);
        new AsyncTask()
        {
        }
        .execute(null);
        break;
        EsService.enableAndPerformWipeoutStats(paramContext, paramEsAccount);
      }
    }
  }

  public static void saveAudience(Context paramContext, EsAccount paramEsAccount, byte[] paramArrayOfByte)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues(2);
    localContentValues.put("audience_data", paramArrayOfByte);
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
  }

  public static void saveContactsStatsSyncCleanupStatus(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    localEditor.putBoolean("contacts_stats_clean", paramBoolean);
    localEditor.commit();
  }

  public static void saveContactsStatsSyncPreference(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putBoolean(i + ".contacts_stats_sync", paramBoolean);
    localEditor.commit();
  }

  public static void saveContactsStatsWipeoutNeeded(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      localContentValues.put("wipeout_stats", Integer.valueOf(i));
      localSQLiteDatabase.update("account_status", localContentValues, null, null);
      return;
    }
  }

  public static void saveContactsSyncCleanupStatus(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    localEditor.putBoolean("contacts_clean", paramBoolean);
    localEditor.commit();
  }

  public static void saveContactsSyncPreference(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putBoolean(i + ".contacts_sync", paramBoolean);
    localEditor.commit();
  }

  public static void saveContactsSyncVersion(Context paramContext, EsAccount paramEsAccount, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("contacts_sync_version", Integer.valueOf(13));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
    paramContext.getContentResolver().notifyChange(EsProvider.ACCOUNT_STATUS_URI, null);
  }

  public static void saveInstantUploadEnabled(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    if (localSharedPreferences.getInt("active_account", -1) != i);
    while (true)
    {
      return;
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putBoolean(i + ".iu_enabled", paramBoolean);
      localEditor.commit();
    }
  }

  public static void saveInstantUploadPhotoWifiOnly(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putBoolean(i + ".iu_photos_wifi_only", paramBoolean);
    localEditor.commit();
  }

  public static void saveInstantUploadVideoWifiOnly(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putBoolean(i + ".iu_videos_wifi_only", paramBoolean);
    localEditor.commit();
  }

  public static void saveLastContactedTimestamp(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("last_contacted_time", Long.valueOf(paramLong));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
    paramContext.getContentResolver().notifyChange(EsProvider.ACCOUNT_STATUS_URI, null);
  }

  public static void saveLastDatabaseCleanupTimestamp(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putLong(i + ".cleanup_timestamp", paramLong);
    localEditor.commit();
  }

  public static void saveLastPhotoNotificationTimestamp(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putLong(i + ".last_photo_notification_ts", paramLong);
    localEditor.commit();
  }

  public static void saveLastStatsSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("last_stats_sync_time", Long.valueOf(System.currentTimeMillis()));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
    paramContext.getContentResolver().notifyChange(EsProvider.ACCOUNT_STATUS_URI, null);
  }

  public static void saveLastSyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("last_sync_time", Long.valueOf(System.currentTimeMillis()));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
    paramContext.getContentResolver().notifyChange(EsProvider.ACCOUNT_STATUS_URI, null);
  }

  public static void saveLocationDialogSeenPreference(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putBoolean(i + ".location_dialog", true);
    localEditor.commit();
  }

  public static void saveMinorHangoutWarningDialogSeenPreference(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putBoolean(i + ".seen_hangout_minor_warning", true);
    localEditor.commit();
  }

  public static void saveMinorPublicExtendedDialogSeenPreference(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putBoolean(i + ".minor_public_extended_dialog", true);
    localEditor.commit();
  }

  public static void savePostingPreferences(Context paramContext, EsAccount paramEsAccount, ShareboxSettings paramShareboxSettings)
  {
    AudienceData localAudienceData;
    if (paramShareboxSettings.defaultSharingRosters != null)
    {
      sHadSharingRoster = true;
      localAudienceData = EsPeopleData.convertSharingRosterToAudience(paramContext, paramEsAccount, paramShareboxSettings.defaultSharingRosters);
      if (localAudienceData == null);
    }
    while (true)
    {
      String str;
      try
      {
        saveAudience(paramContext, paramEsAccount, DbAudienceData.serialize(localAudienceData));
        boolean bool1 = true;
        str = paramShareboxSettings.lastLocationDisplayType;
        bool2 = false;
        if (str != null)
        {
          if (!str.equals("HIDE"))
            break label136;
          bool1 = false;
        }
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("streams", 0).edit();
        localEditor.putBoolean("want_locations", bool1);
        localEditor.putBoolean("city_level_location", bool2);
        localEditor.commit();
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("EsAccountsData", "Error saving default audience");
        continue;
      }
      sHadSharingRoster = false;
      continue;
      label136: boolean bool3 = str.equals("SHOW_CITY_LEVEL");
      boolean bool2 = false;
      if (bool3)
        bool2 = true;
    }
  }

  public static void saveRecentImagesTimestamp(Context paramContext, long paramLong)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    long l = localSharedPreferences.getLong("recent_images_timestamp", 0L);
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putLong("recent_images_timestamp", Math.max(l, paramLong));
    localEditor.commit();
  }

  public static void saveReportAbuseWarningDialogSeenPreference(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putBoolean(i + ".seen_hangout_abuse_warning", true);
    localEditor.commit();
  }

  public static void saveServerSettings(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData)
  {
    if (paramAccountSettingsData.getWarmWelcomeTimestamp() != null)
      setWarmWelcomeTimestamp(paramContext, paramEsAccount, paramAccountSettingsData.getWarmWelcomeTimestamp().longValue(), true);
    if (paramAccountSettingsData.getShareboxSettings() != null)
      savePostingPreferences(paramContext, paramEsAccount, paramAccountSettingsData.getShareboxSettings());
  }

  public static void setHasVisitedOob(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    localEditor.putBoolean("visited_oob", paramBoolean);
    localEditor.commit();
  }

  public static void setNotificationPushEnabled(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("push_notifications", Boolean.valueOf(true));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
  }

  public static void setOneClickAddTooltipShown(Context paramContext, EsAccount paramEsAccount)
  {
    int i = paramEsAccount.getIndex();
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    localEditor.putBoolean(i + ".one_click_tooltip_shown", true);
    localEditor.commit();
  }

  public static void setOobComplete(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    if (i != -1)
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putBoolean(i + ".contacts_oob_completed", true);
      localEditor.putBoolean(i + ".iu_oob_completed", true);
      localEditor.commit();
    }
  }

  public static void setWarmWelcomeTimestamp(Context paramContext, EsAccount paramEsAccount, long paramLong, boolean paramBoolean)
  {
    int i = paramEsAccount.getIndex();
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    if ((paramBoolean) && (localSharedPreferences.getLong(i + ".warm_welcome_ts", 0L) > paramLong));
    while (true)
    {
      return;
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putLong(i + ".warm_welcome_ts", paramLong);
      localEditor.putBoolean(i + ".settings_synced", paramBoolean);
      localEditor.commit();
      if (!paramBoolean)
        EsService.uploadChangedSettings(paramContext, paramEsAccount);
    }
  }

  public static void syncExperiments(Context paramContext, EsAccount paramEsAccount)
    throws IOException
  {
    GetMobileExperimentsOperation localGetMobileExperimentsOperation = new GetMobileExperimentsOperation(paramContext, paramEsAccount, null, null);
    localGetMobileExperimentsOperation.start();
    localGetMobileExperimentsOperation.logAndThrowExceptionIfFailed("EsAccountsData");
  }

  public static void syncSettings$41b2440d(Context paramContext, EsAccount paramEsAccount)
  {
    long l1 = queryLastSettingsSyncTimestamp(paramContext, paramEsAccount);
    if (System.currentTimeMillis() - l1 < 86400000L);
    while (true)
    {
      return;
      GetSettingsOperation localGetSettingsOperation = new GetSettingsOperation(paramContext, paramEsAccount, false, null, null);
      localGetSettingsOperation.start();
      if (!localGetSettingsOperation.hasError())
      {
        long l2 = System.currentTimeMillis();
        SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("last_settings_sync_time", Long.valueOf(l2));
        localSQLiteDatabase.update("account_status", localContentValues, null, null);
      }
      else
      {
        localGetSettingsOperation.logError("EsAccountsData");
      }
    }
  }

  public static void unregisterExperimentListener(ExperimentListener paramExperimentListener)
  {
    sExperimentListeners.remove(paramExperimentListener);
  }

  public static void updateAccount(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    try
    {
      if (EsLog.isLoggable("EsAccountsData", 3))
        Log.d("EsAccountsData", "updateAccount: " + paramEsAccount.getName());
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
      int i = paramEsAccount.getIndex();
      localEditor.putString(i + ".gaia_id", paramString1);
      localEditor.putString(i + ".account_name", paramEsAccount.getName());
      localEditor.putString(i + ".display_name", paramString2);
      localEditor.putBoolean(i + ".is_child", paramBoolean);
      localEditor.commit();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void updateAudienceHistory(Context paramContext, EsAccount paramEsAccount, AudienceData paramAudienceData)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    Cursor localCursor = localSQLiteDatabase.query("account_status", new String[] { "audience_history" }, null, null, null, null, null);
    ArrayList localArrayList1;
    while (true)
    {
      int k;
      try
      {
        boolean bool = localCursor.moveToFirst();
        Object localObject2 = null;
        if (bool)
        {
          byte[] arrayOfByte2 = localCursor.getBlob(0);
          localObject2 = null;
          if (arrayOfByte2 != null)
          {
            ArrayList localArrayList2 = DbAudienceData.deserializeList(arrayOfByte2);
            localObject2 = localArrayList2;
          }
        }
        localCursor.close();
        if (localObject2 == null)
          localObject2 = new ArrayList();
        localArrayList1 = new ArrayList();
        if (isValidCustomAudience(paramAudienceData))
          localArrayList1.add(paramAudienceData);
        int i = ((ArrayList)localObject2).size();
        int j = 0;
        if (j < i)
        {
          AudienceData localAudienceData = (AudienceData)((ArrayList)localObject2).get(j);
          if ((!compareAudiences(paramAudienceData, localAudienceData)) && (isValidCustomAudience(localAudienceData)))
            localArrayList1.add(localAudienceData);
          j++;
        }
      }
      finally
      {
        localCursor.close();
        new ArrayList();
      }
      localArrayList1.remove(k);
      k--;
    }
    try
    {
      byte[] arrayOfByte1 = DbAudienceData.serialize(localArrayList1);
      ContentValues localContentValues = new ContentValues(1);
      localContentValues.put("audience_history", arrayOfByte1);
      localSQLiteDatabase.update("account_status", localContentValues, null, null);
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        EsLog.writeToLog(6, "EsAccountsData", "Error saving audience history");
    }
  }

  public static void upgradeAccount(Context paramContext, EsAccount paramEsAccount)
    throws Exception
  {
    GetSettingsOperation localGetSettingsOperation = new GetSettingsOperation(paramContext, paramEsAccount, false, null, null);
    localGetSettingsOperation.start();
    if (localGetSettingsOperation.hasError())
      throw new Exception("Account upgrade failed", localGetSettingsOperation.getException());
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("accounts", 0).edit();
    int i = paramEsAccount.getIndex();
    localEditor.putInt("active_account", i);
    localEditor.remove("active");
    localEditor.remove(i + ".user_id");
    localEditor.remove(i + ".name");
    localEditor.commit();
    ContentResolver.requestSync(AccountsUtil.newAccount(paramEsAccount.getName()), "com.google.android.apps.plus.content.EsProvider", new Bundle());
  }

  public static void uploadChangedSettings(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("accounts", 0);
    int i = paramEsAccount.getIndex();
    if (localSharedPreferences.getBoolean(i + ".settings_synced", true));
    while (true)
    {
      return;
      long l = localSharedPreferences.getLong(i + ".warm_welcome_ts", 0L);
      if (l != 0L)
      {
        SetSettingsOperation localSetSettingsOperation = new SetSettingsOperation(paramContext, paramEsAccount, l, null, null);
        localSetSettingsOperation.start();
        if (localSetSettingsOperation.hasError())
          Log.e("EsAccountsData", "Could not upload settings: " + localSetSettingsOperation.getErrorCode(), localSetSettingsOperation.getException());
      }
    }
  }

  public static class ExperimentList extends GenericJson
  {
    public List<GetMobileExperimentsResponseExperiment> flags;
  }

  public static abstract interface ExperimentListener
  {
    public abstract void onExperimentsChanged();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsAccountsData
 * JD-Core Version:    0.6.2
 */