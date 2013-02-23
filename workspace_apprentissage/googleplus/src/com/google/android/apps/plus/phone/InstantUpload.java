package com.google.android.apps.plus.phone;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.service.AndroidNotification;
import com.google.android.apps.plus.service.CameraMonitor;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;

public final class InstantUpload
{
  private static final String[] INSTANT_SHARE_PROJECTION = { "instant_share_eventid" };
  private static final String[] INSTANT_UPLOAD_PROJECTION;
  private static final String[] PROJECTION_UPLOAD_SIZE = { "upload_full_resolution", "full_size_disabled", "quota_limit", "quota_used" };
  private static Handler sHandler = new Handler(Looper.getMainLooper());

  static
  {
    INSTANT_UPLOAD_PROJECTION = new String[] { "auto_upload_enabled" };
  }

  public static void cancelAllUploads(Context paramContext, EsAccount paramEsAccount)
  {
    Uri localUri = InstantUploadFacade.UPLOAD_ALL_URI.buildUpon().appendQueryParameter("account", paramEsAccount.getName()).build();
    paramContext.getContentResolver().delete(localUri, null, null);
  }

  public static void enableInstantUpload(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    ContentValues localContentValues = new ContentValues();
    if (paramBoolean)
    {
      localContentValues.put("auto_upload_account_name", paramEsAccount.getName());
      localContentValues.put("auto_upload_account_type", "com.google");
    }
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      localContentValues.put("auto_upload_enabled", Integer.valueOf(i));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      if (paramBoolean)
        ensureSyncEnabled$1f9c1b47(paramEsAccount);
      startMonitoring(paramContext);
      return;
    }
  }

  public static void ensureSyncEnabled$1f9c1b47(EsAccount paramEsAccount)
  {
    ContentResolver.setSyncAutomatically(AccountsUtil.newAccount(paramEsAccount.getName()), "com.google.android.apps.plus.iu.EsGoogleIuProvider", true);
  }

  public static String getInstantShareEventId(Context paramContext)
  {
    Uri localUri = InstantUploadFacade.SETTINGS_URI;
    Cursor localCursor = paramContext.getContentResolver().query(localUri, INSTANT_SHARE_PROJECTION, null, null, null);
    Object localObject1 = null;
    if (localCursor != null);
    try
    {
      if (localCursor.moveToFirst())
      {
        String str = localCursor.getString(0);
        localObject1 = str;
        return localObject1;
      }
      localCursor.close();
      localObject1 = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static String getSizeText(Context paramContext, int paramInt)
  {
    String str;
    if (paramInt < 900)
    {
      int k = R.string.full_size_megabyte;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(paramInt);
      str = paramContext.getString(k, arrayOfObject3);
    }
    while (true)
    {
      return str;
      if (paramInt < 921600)
      {
        int j = R.string.full_size_gigabyte;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Double.valueOf(Math.max(paramInt / 1024.0D, 1.0D));
        str = paramContext.getString(j, arrayOfObject2);
      }
      else
      {
        int i = R.string.full_size_terabyte;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Double.valueOf(Math.max(paramInt / 1048576.0D, 1.0D));
        str = paramContext.getString(i, arrayOfObject1);
      }
    }
  }

  public static boolean isEnabled(Context paramContext)
  {
    Uri localUri = InstantUploadFacade.SETTINGS_URI;
    Cursor localCursor = paramContext.getContentResolver().query(localUri, INSTANT_UPLOAD_PROJECTION, null, null, null);
    int i = 0;
    if (localCursor != null);
    while (true)
    {
      try
      {
        boolean bool2 = localCursor.moveToFirst();
        i = 0;
        if (bool2)
        {
          int j = localCursor.getInt(0);
          i = j;
        }
        localCursor.close();
        if (i != 0)
        {
          bool1 = true;
          return bool1;
        }
      }
      finally
      {
        localCursor.close();
      }
      boolean bool1 = false;
    }
  }

  public static boolean isInstantShareEnabled(Context paramContext)
  {
    if (getInstantShareEventId(paramContext) != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isSyncEnabled$1f9c1b43(EsAccount paramEsAccount)
  {
    return ContentResolver.getSyncAutomatically(AccountsUtil.newAccount(paramEsAccount.getName()), "com.google.android.apps.plus.iu.EsGoogleIuProvider");
  }

  public static void setFullResolutionSetting(Context paramContext, boolean paramBoolean)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("upload_full_resolution", Integer.valueOf(i));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      return;
    }
  }

  public static void setOnBatterySetting(Context paramContext, boolean paramBoolean)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("sync_on_battery", Integer.valueOf(i));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      return;
    }
  }

  public static void setPhotoWiFiOnlySetting(Context paramContext, boolean paramBoolean)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("sync_on_wifi_only", Integer.valueOf(i));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      return;
    }
  }

  public static void setRoamingUploadSetting(Context paramContext, boolean paramBoolean)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("sync_on_roaming", Integer.valueOf(i));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      return;
    }
  }

  public static void setVideoWiFiOnlySetting(Context paramContext, boolean paramBoolean)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("video_upload_wifi_only", Integer.valueOf(i));
      localContentResolver.update(InstantUploadFacade.SETTINGS_URI, localContentValues, null, null);
      return;
    }
  }

  public static void showFirstTimeFullSizeNotification(Context paramContext, EsAccount paramEsAccount)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if ((localSharedPreferences.getBoolean("iu.first_time_full_size_shown", false)) || (paramEsAccount == null))
      if (EsLog.isLoggable("iu.InstantUpload", 4))
      {
        if (paramEsAccount != null)
          break label43;
        Log.i("iu.InstantUpload", "No first time; account is null, retry");
      }
    while (true)
    {
      return;
      label43: Log.i("iu.InstantUpload", "First time already shown");
      continue;
      String str = paramEsAccount.getName();
      Uri localUri = InstantUploadFacade.SETTINGS_URI.buildUpon().appendQueryParameter("account", str).build();
      Cursor localCursor = paramContext.getContentResolver().query(localUri, PROJECTION_UPLOAD_SIZE, null, null, null);
      if (localCursor != null);
      try
      {
        if ((!localCursor.moveToFirst()) || (localCursor.getInt(0) != 1))
        {
          localSharedPreferences.edit().putBoolean("iu.first_time_full_size_shown", true).commit();
          if (EsLog.isLoggable("iu.InstantUpload", 4))
          {
            if (localCursor.getInt(0) == 1)
              break label188;
            Log.i("iu.InstantUpload", "No first time; full size uploads disabled");
          }
          while (localCursor != null)
          {
            localCursor.close();
            break;
            label188: Log.i("iu.InstantUpload", "No first time; couldn't get settings");
          }
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      if (localCursor.getInt(1) == 1);
      int i;
      int j;
      for (boolean bool = true; ; bool = false)
      {
        i = localCursor.getInt(2);
        j = localCursor.getInt(3);
        if (localCursor != null)
          localCursor.close();
        if ((i != -1) && (j != -1))
          break label303;
        if (!EsLog.isLoggable("iu.InstantUpload", 4))
          break;
        Log.i("iu.InstantUpload", "No first time; quota unknown, retry");
        break;
      }
      label303: localSharedPreferences.edit().putBoolean("iu.first_time_full_size_shown", true).commit();
      if ((!isEnabled(paramContext)) || (bool) || (InstantUploadFacade.isOutOfQuota(i, j)))
      {
        if (EsLog.isLoggable("iu.InstantUpload", 4))
          Log.i("iu.InstantUpload", "No first time; IU enabled? " + isEnabled(paramContext) + ", low quota? " + bool + ", no quota? " + InstantUploadFacade.isOutOfQuota(i, j));
      }
      else
        AndroidNotification.showFullSizeFirstTimeNotification(paramContext, paramEsAccount);
    }
  }

  public static void showOutOfQuotaNotification(Context paramContext, EsAccount paramEsAccount, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    boolean bool1 = InstantUploadFacade.isOutOfQuota(paramInt1, paramInt2);
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (!paramBoolean)
    {
      localSharedPreferences.edit().putBoolean("iu.received_low_quota", false).putBoolean("iu.received_no_quota", false).commit();
      showFirstTimeFullSizeNotification(paramContext, paramEsAccount);
      AndroidNotification.cancelQuotaNotification(paramContext, paramEsAccount);
    }
    while (true)
    {
      return;
      label79: Cursor localCursor;
      if (bool1)
      {
        if (localSharedPreferences.getBoolean("iu.received_no_quota", false))
          continue;
        String str = paramEsAccount.getName();
        Uri localUri = InstantUploadFacade.SETTINGS_URI.buildUpon().appendQueryParameter("account", str).build();
        localCursor = paramContext.getContentResolver().query(localUri, PROJECTION_UPLOAD_SIZE, null, null, null);
        if (localCursor == null);
      }
      try
      {
        if (localCursor.moveToFirst())
        {
          int i = localCursor.getInt(0);
          if (i == 1);
        }
        else
        {
          if (localCursor == null)
            continue;
          localCursor.close();
          continue;
          if (!localSharedPreferences.getBoolean("iu.received_low_quota", false))
            break label79;
          if (!localSharedPreferences.contains("iu.received_no_quota"))
            continue;
          localSharedPreferences.edit().putBoolean("iu.received_no_quota", false).commit();
          continue;
        }
        if (localCursor != null)
          localCursor.close();
        boolean bool2 = localSharedPreferences.contains("iu.first_time_full_size_shown");
        if (!bool2)
          localSharedPreferences.edit().putBoolean("iu.first_time_full_size_shown", true).commit();
        if (bool1)
        {
          localSharedPreferences.edit().putBoolean("iu.received_no_quota", true).commit();
          setFullResolutionSetting(paramContext, false);
          if (!bool2)
            continue;
          AndroidNotification.showQuotaNotification(paramContext, paramEsAccount, paramInt2, paramInt1, bool1);
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
  }

  public static void startMonitoring(Context paramContext)
  {
    sHandler.post(new Runnable()
    {
      public final void run()
      {
        PackageManager localPackageManager = this.val$context.getPackageManager();
        ComponentName localComponentName = new ComponentName(this.val$context.getPackageName(), CameraMonitor.class.getName());
        if (localPackageManager.getComponentEnabledSetting(localComponentName) != 1)
          localPackageManager.setComponentEnabledSetting(localComponentName, 1, 1);
        CameraMonitor.registerObservers(this.val$context.getApplicationContext());
      }
    });
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.InstantUpload
 * JD-Core Version:    0.6.2
 */