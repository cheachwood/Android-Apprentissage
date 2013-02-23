package com.google.android.apps.plus.settings;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.xml;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.util.AccountsUtil;
import java.util.Map;

public class InstantUploadSettingsActivity extends BaseSettingsActivity
  implements DialogInterface.OnClickListener
{
  private static final Uri BUY_QUTOA_URI = Uri.parse("https://www.google.com/settings/storage/");
  private static final Uri LEARN_MORE_URI = Uri.parse("https://support.google.com/plus/?p=full_size_upload");
  private static final String[] PROJECTION_PICASA_SETTINGS = { "auto_upload_enabled", "sync_on_wifi_only", "sync_on_roaming", "sync_on_battery", "video_upload_wifi_only", "upload_full_resolution", "quota_limit", "quota_used" };
  private static String sBuyQuotaKey;
  private static String sConnectionPhotoKey;
  private static String sConnectionVideoKey;
  private static String sInstantUploadKey;
  private static String sLearnMoreKey;
  private static IntentFilter sMatchFilter;
  private static String sOnBatteryKey;
  private static String sRoamingUploadKey;
  private static String sSyncNowKey;
  private static String sUploadSizeKey;
  private static boolean sWifiOnly;
  private boolean mIsReceiverRegistered;
  private boolean mIsUploading;
  private boolean mMasterSyncEnabled;
  private boolean mPhotoSyncEnabled;
  private int mQuotaLimit = -1;
  private int mQuotaUsed = -1;
  private AsyncTaskLoader<Map<String, Integer>> mSettingsLoader;
  private final BroadcastReceiver mUploadsProgressReceiver = new BroadcastReceiver()
  {
    public final void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent == null);
      while (true)
      {
        return;
        if ("com.google.android.apps.plus.iu.upload_all_progress".equals(paramAnonymousIntent.getAction()))
        {
          final int i = paramAnonymousIntent.getIntExtra("upload_all_progress", -1);
          final int j = paramAnonymousIntent.getIntExtra("upload_all_count", -1);
          final int k = paramAnonymousIntent.getIntExtra("upload_all_state", -1);
          InstantUploadSettingsActivity.this.mHandler.post(new Runnable()
          {
            public final void run()
            {
              InstantUploadSettingsActivity localInstantUploadSettingsActivity1 = InstantUploadSettingsActivity.this;
              boolean bool;
              String str1;
              String str2;
              if (j != i)
              {
                bool = true;
                InstantUploadSettingsActivity.access$002(localInstantUploadSettingsActivity1, bool);
                if (InstantUploadSettingsActivity.this.mIsUploading)
                  break label142;
                str1 = InstantUploadSettingsActivity.this.getString(R.string.photo_sync_preference_title);
                str2 = InstantUploadSettingsActivity.this.getString(R.string.photo_sync_preference_summary);
                Toast.makeText(InstantUploadSettingsActivity.this, R.string.photo_upload_finished, 0).show();
                InstantUploadSettingsActivity.this.unregisterReceiver(InstantUploadSettingsActivity.this.mUploadsProgressReceiver);
              }
              while (true)
              {
                Preference localPreference = InstantUploadSettingsActivity.this.findPreference(InstantUploadSettingsActivity.sSyncNowKey);
                localPreference.setTitle(str1);
                localPreference.setSummary(str2);
                return;
                bool = false;
                break;
                label142: str1 = InstantUploadSettingsActivity.this.getString(R.string.photo_sync_preference_cancel_title);
                if ((k == 0) || (k == 1))
                {
                  InstantUploadSettingsActivity localInstantUploadSettingsActivity2 = InstantUploadSettingsActivity.this;
                  int i = R.string.photo_upload_now_inprogress_summary;
                  Object[] arrayOfObject = new Object[2];
                  arrayOfObject[0] = Integer.valueOf(i);
                  arrayOfObject[1] = Integer.valueOf(j);
                  str2 = localInstantUploadSettingsActivity2.getString(i, arrayOfObject);
                }
                else
                {
                  String str3 = InstantUploadSettingsActivity.access$200(InstantUploadSettingsActivity.this, k);
                  str2 = InstantUploadSettingsActivity.this.getString(R.string.photo_upload_now_paused_summary, new Object[] { str3 });
                }
              }
            }
          });
        }
      }
    }
  };

  private void recordUserAction(OzActions paramOzActions)
  {
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      EsAnalytics.recordActionEvent(this, localEsAccount, paramOzActions, OzViews.getViewForLogging(this));
  }

  private void updateEnabledStates(boolean paramBoolean)
  {
    boolean bool1 = true;
    boolean bool2 = InstantUploadFacade.isOutOfQuota(this.mQuotaLimit, this.mQuotaUsed);
    Preference localPreference1 = findPreference(sConnectionPhotoKey);
    boolean bool3;
    boolean bool4;
    label65: boolean bool5;
    label94: boolean bool6;
    label123: boolean bool7;
    label150: boolean bool8;
    label201: Preference localPreference7;
    if ((!paramBoolean) || (!sWifiOnly))
    {
      bool3 = bool1;
      localPreference1.setShouldDisableView(bool3);
      Preference localPreference2 = findPreference(sConnectionPhotoKey);
      if ((!paramBoolean) || (sWifiOnly))
        break label262;
      bool4 = bool1;
      localPreference2.setEnabled(bool4);
      Preference localPreference3 = findPreference(sConnectionVideoKey);
      if ((paramBoolean) && (sWifiOnly))
        break label268;
      bool5 = bool1;
      localPreference3.setShouldDisableView(bool5);
      Preference localPreference4 = findPreference(sConnectionVideoKey);
      if ((!paramBoolean) || (sWifiOnly))
        break label274;
      bool6 = bool1;
      localPreference4.setEnabled(bool6);
      Preference localPreference5 = findPreference(sUploadSizeKey);
      if ((!paramBoolean) || (bool2))
        break label280;
      bool7 = bool1;
      localPreference5.setEnabled(bool7);
      findPreference(sBuyQuotaKey).setEnabled(paramBoolean);
      findPreference(sLearnMoreKey).setEnabled(bool1);
      Preference localPreference6 = findPreference(sRoamingUploadKey);
      if ((paramBoolean) && (sWifiOnly))
        break label286;
      bool8 = bool1;
      localPreference6.setShouldDisableView(bool8);
      localPreference7 = findPreference(sRoamingUploadKey);
      if ((!paramBoolean) || (sWifiOnly))
        break label292;
    }
    while (true)
    {
      localPreference7.setEnabled(bool1);
      findPreference(sOnBatteryKey).setEnabled(paramBoolean);
      findPreference(sSyncNowKey).setEnabled(paramBoolean);
      return;
      bool3 = false;
      break;
      label262: bool4 = false;
      break label65;
      label268: bool5 = false;
      break label94;
      label274: bool6 = false;
      break label123;
      label280: bool7 = false;
      break label150;
      label286: bool8 = false;
      break label201;
      label292: bool1 = false;
    }
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    }
    while (true)
    {
      dismissDialog(0);
      return;
      this.mIsUploading = true;
      recordUserAction(OzActions.CS_SETTINGS_SYNC_ALL);
      Preference localPreference = findPreference(sSyncNowKey);
      localPreference.setTitle(R.string.photo_sync_preference_cancel_title);
      localPreference.setSummary(R.string.photo_upload_starting_summary);
      AsyncTask local5 = new AsyncTask()
      {
      };
      registerReceiver(this.mUploadsProgressReceiver, sMatchFilter);
      local5.execute(new Void[] { null });
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (sInstantUploadKey == null)
    {
      sInstantUploadKey = getString(R.string.photo_instant_upload_preference_key);
      sRoamingUploadKey = getString(R.string.photo_roaming_upload_preference_key);
      sOnBatteryKey = getString(R.string.photo_on_battery_preference_key);
      sConnectionPhotoKey = getString(R.string.photo_connection_preference_key);
      sConnectionVideoKey = getString(R.string.video_connection_preference_key);
      sSyncNowKey = getString(R.string.photo_sync_preference_key);
      sUploadSizeKey = getString(R.string.photo_upload_size_preference_key);
      sBuyQuotaKey = getString(R.string.photo_buy_quota_preference_key);
      sLearnMoreKey = getString(R.string.photo_learn_more_preference_key);
      IntentFilter localIntentFilter = new IntentFilter();
      sMatchFilter = localIntentFilter;
      localIntentFilter.addAction("com.google.android.apps.plus.iu.upload_all_progress");
      NetworkInfo localNetworkInfo = ((ConnectivityManager)getSystemService("connectivity")).getNetworkInfo(0);
      boolean bool = false;
      if (localNetworkInfo == null)
        bool = true;
      sWifiOnly = bool;
    }
    addPreferencesFromResource(R.xml.photo_preferences);
    PhotoPreferenceChangeListener localPhotoPreferenceChangeListener = new PhotoPreferenceChangeListener();
    Preference localPreference = findPreference(sInstantUploadKey);
    localPreference.setOnPreferenceChangeListener(localPhotoPreferenceChangeListener);
    hookMasterSwitch(null, (CheckBoxPreference)localPreference);
    findPreference(sConnectionPhotoKey).setOnPreferenceChangeListener(localPhotoPreferenceChangeListener);
    findPreference(sConnectionVideoKey).setOnPreferenceChangeListener(localPhotoPreferenceChangeListener);
    findPreference(sRoamingUploadKey).setOnPreferenceChangeListener(localPhotoPreferenceChangeListener);
    findPreference(sOnBatteryKey).setOnPreferenceChangeListener(localPhotoPreferenceChangeListener);
    findPreference(sUploadSizeKey).setOnPreferenceChangeListener(localPhotoPreferenceChangeListener);
    findPreference(sBuyQuotaKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public final boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(InstantUploadSettingsActivity.BUY_QUTOA_URI);
        InstantUploadSettingsActivity.this.startActivity(localIntent);
        return true;
      }
    });
    findPreference(sLearnMoreKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public final boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(InstantUploadSettingsActivity.LEARN_MORE_URI);
        InstantUploadSettingsActivity.this.startActivity(localIntent);
        return true;
      }
    });
    findPreference(sSyncNowKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public final boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        if (!InstantUploadSettingsActivity.this.mIsUploading)
          InstantUploadSettingsActivity.this.showDialog(0);
        while (true)
        {
          return true;
          InstantUploadSettingsActivity.access$002(InstantUploadSettingsActivity.this, false);
          InstantUploadSettingsActivity.this.unregisterReceiver(InstantUploadSettingsActivity.this.mUploadsProgressReceiver);
          InstantUploadSettingsActivity.this.getContentResolver().delete(InstantUploadSettingsActivity.access$600(InstantUploadSettingsActivity.this), null, null);
          paramAnonymousPreference.setTitle(InstantUploadSettingsActivity.this.getString(R.string.photo_sync_preference_title));
          paramAnonymousPreference.setSummary(InstantUploadSettingsActivity.this.getString(R.string.photo_sync_preference_summary));
        }
      }
    });
    updateEnabledStates(((CheckBoxPreference)localPreference).isChecked());
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    AlertDialog.Builder localBuilder;
    if (paramInt == 0)
    {
      localBuilder = new AlertDialog.Builder(this);
      localBuilder.setMessage(R.string.photo_upload_confirmation);
      localBuilder.setPositiveButton(R.string.yes, this);
      localBuilder.setNegativeButton(R.string.no, this);
    }
    for (AlertDialog localAlertDialog = localBuilder.create(); ; localAlertDialog = null)
      return localAlertDialog;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mSettingsLoader != null)
      this.mSettingsLoader.reset();
  }

  public void onPause()
  {
    super.onPause();
    if (this.mSettingsLoader != null)
      this.mSettingsLoader.stopLoading();
    unregisterReceiver(this.mUploadsProgressReceiver);
  }

  public void onResume()
  {
    super.onResume();
    this.mPhotoSyncEnabled = ContentResolver.getSyncAutomatically(AccountsUtil.newAccount(getAccount().getName()), "com.google.android.apps.plus.iu.EsGoogleIuProvider");
    this.mMasterSyncEnabled = ContentResolver.getMasterSyncAutomatically();
    if ((this.mMasterSyncEnabled) && (this.mPhotoSyncEnabled))
    {
      if (this.mSettingsLoader == null)
        this.mSettingsLoader = new SystemSettingLoader(this);
      this.mSettingsLoader.startLoading();
    }
    while (true)
    {
      return;
      finish();
    }
  }

  public Intent registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    boolean bool = this.mIsReceiverRegistered;
    Intent localIntent = null;
    if (!bool)
    {
      localIntent = super.registerReceiver(paramBroadcastReceiver, paramIntentFilter);
      this.mIsReceiverRegistered = true;
    }
    return localIntent;
  }

  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    if (this.mIsReceiverRegistered)
    {
      super.unregisterReceiver(paramBroadcastReceiver);
      this.mIsReceiverRegistered = false;
    }
  }

  final class PhotoPreferenceChangeListener
    implements Preference.OnPreferenceChangeListener
  {
    PhotoPreferenceChangeListener()
    {
    }

    public final boolean onPreferenceChange(Preference paramPreference, Object paramObject)
    {
      String str1 = paramPreference.getKey();
      OzActions localOzActions2;
      if (TextUtils.equals(InstantUploadSettingsActivity.sInstantUploadKey, str1))
      {
        final EsAccount localEsAccount = InstantUploadSettingsActivity.this.getAccount();
        final Boolean localBoolean5 = (Boolean)paramObject;
        InstantUploadSettingsActivity.this.updateEnabledStates(localBoolean5.booleanValue());
        InstantUploadSettingsActivity localInstantUploadSettingsActivity2 = InstantUploadSettingsActivity.this;
        if (localBoolean5.booleanValue())
        {
          localOzActions2 = OzActions.CS_SETTINGS_OPTED_IN;
          localInstantUploadSettingsActivity2.recordUserAction(localOzActions2);
          new AsyncTask()
          {
          }
          .execute(null);
        }
      }
      label312: 
      do
        while (true)
        {
          return true;
          localOzActions2 = OzActions.CS_SETTINGS_OPTED_OUT;
          break;
          if (TextUtils.equals(InstantUploadSettingsActivity.sRoamingUploadKey, str1))
          {
            final Boolean localBoolean4 = (Boolean)paramObject;
            InstantUploadSettingsActivity localInstantUploadSettingsActivity1 = InstantUploadSettingsActivity.this;
            if (localBoolean4.booleanValue());
            for (OzActions localOzActions1 = OzActions.CS_SETTINGS_ROAMING_ENABLED; ; localOzActions1 = OzActions.CS_SETTINGS_ROAMING_DISABLED)
            {
              localInstantUploadSettingsActivity1.recordUserAction(localOzActions1);
              new AsyncTask()
              {
              }
              .execute(null);
              break;
            }
          }
          if (TextUtils.equals(InstantUploadSettingsActivity.sOnBatteryKey, str1))
          {
            new AsyncTask()
            {
            }
            .execute(null);
          }
          else if (TextUtils.equals(InstantUploadSettingsActivity.sConnectionPhotoKey, str1))
          {
            String str7 = (String)paramObject;
            final Boolean localBoolean3;
            if (TextUtils.equals(str7, "WIFI_ONLY"))
            {
              InstantUploadSettingsActivity.this.recordUserAction(OzActions.CS_SETTINGS_UPLOAD_VIA_PHOTOS_AND_VIDEOS_VIA_WIFI_ONLY);
              paramPreference.setSummary(R.string.photo_connection_preference_summary_wifi);
              localBoolean3 = Boolean.valueOf(true);
            }
            while (true)
            {
              if (localBoolean3 == null)
                break label312;
              new AsyncTask()
              {
              }
              .execute(null);
              break;
              if (TextUtils.equals(str7, "WIFI_OR_MOBILE"))
              {
                InstantUploadSettingsActivity.this.recordUserAction(OzActions.CS_SETTINGS_UPLOAD_VIA_PHOTOS_AND_VIDEOS_VIA_MOBILE);
                paramPreference.setSummary(R.string.photo_connection_preference_summary_mobile);
                localBoolean3 = Boolean.valueOf(false);
              }
              else
              {
                localBoolean3 = null;
              }
            }
          }
          else
          {
            if (!TextUtils.equals(InstantUploadSettingsActivity.sConnectionVideoKey, str1))
              break label435;
            String str6 = (String)paramObject;
            final Boolean localBoolean2;
            if (TextUtils.equals(str6, "WIFI_ONLY"))
            {
              InstantUploadSettingsActivity.this.recordUserAction(OzActions.CS_SETTINGS_UPLOAD_VIA_VIDEOS_VIA_WIFI_ONLY);
              paramPreference.setSummary(R.string.video_connection_preference_summary_wifi);
              localBoolean2 = Boolean.valueOf(true);
            }
            while (true)
            {
              if (localBoolean2 == null)
                break label433;
              new AsyncTask()
              {
              }
              .execute(null);
              break;
              if (TextUtils.equals(str6, "WIFI_OR_MOBILE"))
              {
                InstantUploadSettingsActivity.this.recordUserAction(OzActions.CS_SETTINGS_UPLOAD_VIA_VIDEOS_VIA_MOBILE);
                paramPreference.setSummary(R.string.video_connection_preference_summary_mobile);
                localBoolean2 = Boolean.valueOf(false);
              }
              else
              {
                localBoolean2 = null;
              }
            }
          }
        }
      while (!TextUtils.equals(InstantUploadSettingsActivity.sUploadSizeKey, str1));
      label433: label435: String str2 = (String)paramObject;
      int i;
      label476: String str5;
      if ((InstantUploadSettingsActivity.this.mQuotaLimit != -1) && (InstantUploadSettingsActivity.this.mQuotaUsed != -1))
      {
        i = 1;
        String str3 = InstantUpload.getSizeText(InstantUploadSettingsActivity.this, InstantUploadSettingsActivity.this.mQuotaLimit);
        String str4 = InstantUpload.getSizeText(InstantUploadSettingsActivity.this, InstantUploadSettingsActivity.this.mQuotaLimit - InstantUploadSettingsActivity.this.mQuotaUsed);
        if (i == 0)
          break label620;
        str5 = InstantUploadSettingsActivity.this.getString(R.string.photo_upload_size_quota_available, new Object[] { str4, str3 });
        label547: if (!TextUtils.equals(str2, "FULL"))
          break label635;
        InstantUploadSettingsActivity.this.recordUserAction(OzActions.ENABLE_FULL_SIZE_PHOTO_UPLOAD);
        paramPreference.setSummary(InstantUploadSettingsActivity.this.getString(R.string.photo_upload_size_preference_summary_full, new Object[] { str5 }));
      }
      for (final Boolean localBoolean1 = Boolean.valueOf(true); ; localBoolean1 = Boolean.valueOf(false))
      {
        new AsyncTask()
        {
        }
        .execute(null);
        break;
        i = 0;
        break label476;
        label620: str5 = InstantUploadSettingsActivity.this.getString(R.string.photo_upload_size_quota_unknown);
        break label547;
        label635: InstantUploadSettingsActivity.this.recordUserAction(OzActions.ENABLE_STANDARD_SIZE_PHOTO_UPLOAD);
        paramPreference.setSummary(R.string.photo_upload_size_preference_summary_standard);
      }
    }
  }

  static final class SystemSettingLoader extends AsyncTaskLoader<Map<String, Integer>>
  {
    private final InstantUploadSettingsActivity mActivity;
    private boolean mLoaderException;
    private final Loader<Map<String, Integer>>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
    private boolean mObserverRegistered;

    public SystemSettingLoader(InstantUploadSettingsActivity paramInstantUploadSettingsActivity)
    {
      super();
      this.mActivity = paramInstantUploadSettingsActivity;
    }

    // ERROR //
    private Map<String, Integer> esLoadInBackground()
    {
      // Byte code:
      //   0: new 33	java/util/HashMap
      //   3: dup
      //   4: invokespecial 36	java/util/HashMap:<init>	()V
      //   7: astore_1
      //   8: aload_0
      //   9: invokevirtual 40	com/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader:getContext	()Landroid/content/Context;
      //   12: invokevirtual 46	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
      //   15: astore_2
      //   16: aload_0
      //   17: getfield 27	com/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader:mActivity	Lcom/google/android/apps/plus/settings/InstantUploadSettingsActivity;
      //   20: invokevirtual 52	com/google/android/apps/plus/settings/InstantUploadSettingsActivity:getAccount	()Lcom/google/android/apps/plus/content/EsAccount;
      //   23: astore_3
      //   24: iconst_1
      //   25: istore 4
      //   27: iconst_1
      //   28: istore 5
      //   30: iconst_1
      //   31: istore 6
      //   33: iconst_1
      //   34: istore 7
      //   36: iconst_m1
      //   37: istore 8
      //   39: iconst_m1
      //   40: istore 9
      //   42: aload_2
      //   43: getstatic 58	com/google/android/apps/plus/iu/InstantUploadFacade:SETTINGS_URI	Landroid/net/Uri;
      //   46: invokevirtual 64	android/net/Uri:buildUpon	()Landroid/net/Uri$Builder;
      //   49: ldc 66
      //   51: aload_3
      //   52: invokevirtual 72	com/google/android/apps/plus/content/EsAccount:getName	()Ljava/lang/String;
      //   55: invokevirtual 78	android/net/Uri$Builder:appendQueryParameter	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
      //   58: invokevirtual 82	android/net/Uri$Builder:build	()Landroid/net/Uri;
      //   61: invokestatic 86	com/google/android/apps/plus/settings/InstantUploadSettingsActivity:access$1700	()[Ljava/lang/String;
      //   64: aconst_null
      //   65: aconst_null
      //   66: aconst_null
      //   67: invokevirtual 92	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   70: astore 10
      //   72: iconst_0
      //   73: istore 11
      //   75: iconst_0
      //   76: istore 12
      //   78: aload 10
      //   80: ifnull +116 -> 196
      //   83: aload 10
      //   85: invokeinterface 98 1 0
      //   90: istore 29
      //   92: iconst_0
      //   93: istore 11
      //   95: iconst_0
      //   96: istore 12
      //   98: iload 29
      //   100: ifeq +89 -> 189
      //   103: aload 10
      //   105: iconst_0
      //   106: invokeinterface 102 2 0
      //   111: istore 11
      //   113: aload 10
      //   115: iconst_1
      //   116: invokeinterface 102 2 0
      //   121: istore 4
      //   123: aload 10
      //   125: iconst_2
      //   126: invokeinterface 102 2 0
      //   131: istore 12
      //   133: aload 10
      //   135: iconst_3
      //   136: invokeinterface 102 2 0
      //   141: istore 6
      //   143: aload 10
      //   145: iconst_4
      //   146: invokeinterface 102 2 0
      //   151: istore 5
      //   153: aload 10
      //   155: iconst_5
      //   156: invokeinterface 102 2 0
      //   161: istore 7
      //   163: aload 10
      //   165: bipush 6
      //   167: invokeinterface 102 2 0
      //   172: istore 8
      //   174: aload 10
      //   176: bipush 7
      //   178: invokeinterface 102 2 0
      //   183: istore 30
      //   185: iload 30
      //   187: istore 9
      //   189: aload 10
      //   191: invokeinterface 105 1 0
      //   196: aload_1
      //   197: ldc 107
      //   199: iload 11
      //   201: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   204: invokeinterface 119 3 0
      //   209: pop
      //   210: aload_1
      //   211: ldc 121
      //   213: iload 4
      //   215: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   218: invokeinterface 119 3 0
      //   223: pop
      //   224: aload_1
      //   225: ldc 123
      //   227: iload 12
      //   229: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   232: invokeinterface 119 3 0
      //   237: pop
      //   238: aload_1
      //   239: ldc 125
      //   241: iload 6
      //   243: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   246: invokeinterface 119 3 0
      //   251: pop
      //   252: aload_1
      //   253: ldc 127
      //   255: iload 5
      //   257: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   260: invokeinterface 119 3 0
      //   265: pop
      //   266: aload_1
      //   267: ldc 129
      //   269: iload 7
      //   271: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   274: invokeinterface 119 3 0
      //   279: pop
      //   280: aload_1
      //   281: ldc 131
      //   283: iload 8
      //   285: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   288: invokeinterface 119 3 0
      //   293: pop
      //   294: aload_1
      //   295: ldc 133
      //   297: iload 9
      //   299: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   302: invokeinterface 119 3 0
      //   307: pop
      //   308: aload_2
      //   309: aload_0
      //   310: getfield 27	com/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader:mActivity	Lcom/google/android/apps/plus/settings/InstantUploadSettingsActivity;
      //   313: invokestatic 137	com/google/android/apps/plus/settings/InstantUploadSettingsActivity:access$600	(Lcom/google/android/apps/plus/settings/InstantUploadSettingsActivity;)Landroid/net/Uri;
      //   316: aconst_null
      //   317: aconst_null
      //   318: aconst_null
      //   319: aconst_null
      //   320: invokevirtual 92	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   323: astore 21
      //   325: aload 21
      //   327: invokeinterface 141 1 0
      //   332: ifeq +93 -> 425
      //   335: aload 21
      //   337: invokeinterface 98 1 0
      //   342: ifeq +83 -> 425
      //   345: aload 21
      //   347: iconst_1
      //   348: invokeinterface 102 2 0
      //   353: istore 23
      //   355: aload 21
      //   357: iconst_2
      //   358: invokeinterface 102 2 0
      //   363: istore 24
      //   365: aload 21
      //   367: iconst_3
      //   368: invokeinterface 102 2 0
      //   373: istore 27
      //   375: iload 27
      //   377: istore 25
      //   379: aload 21
      //   381: invokeinterface 105 1 0
      //   386: aload_0
      //   387: getfield 27	com/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader:mActivity	Lcom/google/android/apps/plus/settings/InstantUploadSettingsActivity;
      //   390: getfield 145	com/google/android/apps/plus/settings/InstantUploadSettingsActivity:mHandler	Landroid/os/Handler;
      //   393: new 147	com/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader$1
      //   396: dup
      //   397: aload_0
      //   398: iload 25
      //   400: iload 24
      //   402: iload 23
      //   404: invokespecial 150	com/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader$1:<init>	(Lcom/google/android/apps/plus/settings/InstantUploadSettingsActivity$SystemSettingLoader;III)V
      //   407: invokevirtual 156	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   410: pop
      //   411: aload_1
      //   412: areturn
      //   413: astore 28
      //   415: aload 10
      //   417: invokeinterface 105 1 0
      //   422: aload 28
      //   424: athrow
      //   425: iconst_m1
      //   426: istore 23
      //   428: iconst_m1
      //   429: istore 24
      //   431: iconst_m1
      //   432: istore 25
      //   434: goto -55 -> 379
      //   437: astore 22
      //   439: aload 21
      //   441: invokeinterface 105 1 0
      //   446: aload 22
      //   448: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   83	185	413	finally
      //   325	375	437	finally
    }

    private Map<String, Integer> loadInBackground()
    {
      if (!this.mLoaderException);
      while (true)
      {
        try
        {
          Map localMap2 = esLoadInBackground();
          localMap1 = localMap2;
          return localMap1;
        }
        catch (SQLiteException localSQLiteException)
        {
          Log.w("EsAsyncTaskLoader", "loadInBackground failed", localSQLiteException);
          this.mLoaderException = true;
        }
        Map localMap1 = null;
      }
    }

    protected final void onAbandon()
    {
      if (this.mObserverRegistered)
      {
        getContext().getContentResolver().unregisterContentObserver(this.mObserver);
        this.mObserverRegistered = false;
      }
    }

    protected final void onReset()
    {
      onAbandon();
    }

    protected final void onStartLoading()
    {
      if (!this.mObserverRegistered)
      {
        getContext().getContentResolver().registerContentObserver(InstantUploadFacade.SETTINGS_URI, true, this.mObserver);
        this.mObserverRegistered = true;
      }
      forceLoad();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.InstantUploadSettingsActivity
 * JD-Core Version:    0.6.2
 */