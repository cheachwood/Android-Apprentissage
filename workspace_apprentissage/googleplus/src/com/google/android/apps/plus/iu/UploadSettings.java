package com.google.android.apps.plus.iu;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;

public final class UploadSettings
{
  private static final String[] PROJECTION_ENABLE_ACCOUNT_WIFI = { "auto_upload_enabled", "auto_upload_account_name", "sync_on_wifi_only", "video_upload_wifi_only", "sync_on_roaming", "sync_on_battery", "instant_share_eventid", "instant_share_starttime", "instant_share_endtime", "upload_full_resolution" };
  private static UploadSettings sInstance;
  private boolean mAutoUploadEnabled;
  private final Context mContext;
  private long mEventEndTime;
  private String mEventId;
  private long mEventStartTime;
  private boolean mSettingsValid;
  private String mSyncAccount;
  private boolean mSyncOnBattery;
  private boolean mSyncOnRoaming;
  private boolean mUploadFullRes;
  private boolean mWifiOnlyPhoto;
  private boolean mWifiOnlyVideo;

  private UploadSettings(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static UploadSettings getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
      {
        UploadSettings localUploadSettings1 = new UploadSettings(paramContext);
        sInstance = localUploadSettings1;
        localUploadSettings1.reloadSettings(null);
      }
      UploadSettings localUploadSettings2 = sInstance;
      return localUploadSettings2;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final boolean getAutoUploadEnabled()
  {
    return this.mAutoUploadEnabled;
  }

  public final long getEventEndTime()
  {
    return this.mEventEndTime;
  }

  public final String getEventId()
  {
    return this.mEventId;
  }

  public final long getEventStartTime()
  {
    return this.mEventStartTime;
  }

  public final String getSyncAccount()
  {
    return this.mSyncAccount;
  }

  public final boolean getSyncOnBattery()
  {
    return this.mSyncOnBattery;
  }

  public final boolean getSyncOnRoaming()
  {
    return this.mSyncOnRoaming;
  }

  final Cursor getSystemSettingsCursor()
  {
    return this.mContext.getContentResolver().query(InstantUploadFacade.SETTINGS_URI, PROJECTION_ENABLE_ACCOUNT_WIFI, null, null, null);
  }

  public final boolean getUploadFullRes()
  {
    return this.mUploadFullRes;
  }

  public final boolean getWifiOnlyPhoto()
  {
    return this.mWifiOnlyPhoto;
  }

  public final boolean getWifiOnlyVideo()
  {
    return this.mWifiOnlyVideo;
  }

  public final void reloadSettings()
  {
    reloadSettings(null);
  }

  final void reloadSettings(Cursor paramCursor)
  {
    if (paramCursor == null)
      paramCursor = getSystemSettingsCursor();
    if (paramCursor != null);
    while (true)
    {
      String str2;
      try
      {
        if (!paramCursor.moveToFirst())
        {
          if (EsLog.isLoggable("iu.UploadsManager", 5))
            Log.w("iu.UploadsManager", "failed to query system settings");
          return;
        }
        if (paramCursor.getInt(0) != 0)
        {
          bool1 = true;
          String str1 = paramCursor.getString(1);
          if (paramCursor.getInt(2) == 0)
            continue;
          bool2 = true;
          if (paramCursor.getInt(3) == 0)
            continue;
          bool3 = true;
          if (paramCursor.getInt(4) == 0)
            continue;
          bool4 = true;
          if (paramCursor.getInt(5) == 0)
            continue;
          bool5 = true;
          str2 = paramCursor.getString(6);
          long l1 = paramCursor.getLong(7);
          long l2 = paramCursor.getLong(8);
          int i = paramCursor.getInt(9);
          if (i == 0)
            continue;
          bool6 = true;
          Utils.closeSilently(paramCursor);
          if (TextUtils.isEmpty(str1))
          {
            bool1 = false;
            str2 = null;
          }
          if (EsLog.isLoggable("iu.UploadsManager", 4))
          {
            StringBuilder localStringBuilder3 = new StringBuilder("#reloadSettings(); account: ").append(str1).append("; IU: ").append(bool1).append("; IS: ");
            if (str2 != null)
              break label750;
            str5 = "<< NULL >>";
            StringBuilder localStringBuilder4 = localStringBuilder3.append(str5).append("; photoWiFi: ").append(bool2).append("; videoWiFi: ").append(bool3).append("; roam: ").append(bool4).append("; battery: ").append(bool5).append("; size: ");
            if (!bool6)
              break label757;
            str6 = "FULL";
            Log.i("iu.UploadsManager", str6);
          }
          if (EsLog.isLoggable("iu.UploadsManager", 3))
          {
            if (!TextUtils.equals(str1, this.mSyncAccount))
              Log.d("iu.UploadsManager", "   account changed from: " + Utils.maskDebugInfo(this.mSyncAccount) + " --> " + Utils.maskDebugInfo(str1));
            if (bool1 != this.mAutoUploadEnabled)
              Log.d("iu.UploadsManager", "   auto upload changed to " + bool1);
            if (!TextUtils.equals(str2, this.mEventId))
            {
              StringBuilder localStringBuilder1 = new StringBuilder("   event ID changed from ");
              if (this.mEventId != null)
                break label764;
              str3 = "<< NULL >>";
              StringBuilder localStringBuilder2 = localStringBuilder1.append(str3).append(" --> ");
              if (str2 != null)
                break label773;
              str4 = "<< NULL >>";
              Log.d("iu.UploadsManager", str4);
            }
            if (bool2 != this.mWifiOnlyPhoto)
              Log.d("iu.UploadsManager", "   wifiOnlyPhoto changed to " + bool2);
            if (bool3 != this.mWifiOnlyVideo)
              Log.d("iu.UploadsManager", "   wifiOnlyVideo changed to " + bool3);
            if (bool4 != this.mSyncOnRoaming)
              Log.d("iu.UploadsManager", "   syncOnRoaming changed to " + bool4);
            if (bool5 != this.mSyncOnBattery)
              Log.d("iu.UploadsManager", "   syncOnBattery changed to " + bool5);
            if (bool6 != this.mUploadFullRes)
              Log.d("iu.UploadsManager", "   uploadFullRes changed to " + bool6);
          }
          this.mAutoUploadEnabled = bool1;
          this.mSyncAccount = str1;
          this.mWifiOnlyPhoto = bool2;
          this.mWifiOnlyVideo = bool3;
          this.mSyncOnRoaming = bool4;
          this.mSyncOnBattery = bool5;
          this.mEventId = str2;
          this.mEventStartTime = l1;
          this.mEventEndTime = l2;
          this.mUploadFullRes = bool6;
          this.mSettingsValid = true;
          continue;
        }
        boolean bool1 = false;
        continue;
        boolean bool2 = false;
        continue;
        boolean bool3 = false;
        continue;
        boolean bool4 = false;
        continue;
        boolean bool5 = false;
        continue;
        boolean bool6 = false;
        continue;
      }
      finally
      {
        Utils.closeSilently(paramCursor);
      }
      label750: String str5 = str2;
      continue;
      label757: String str6 = "STANDARD";
      continue;
      label764: String str3 = this.mEventId;
      continue;
      label773: String str4 = str2;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.UploadSettings
 * JD-Core Version:    0.6.2
 */