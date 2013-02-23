package com.google.android.picasasync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import java.util.Iterator;
import java.util.List;

public final class PicasaFacade
{
  private static PicasaFacade sInstance;
  private Uri mAlbumCoversUri;
  private Uri mAlbumsUri;
  private String mAuthority;
  private final Context mContext;
  private PicasaSyncInfo mLocalInfo;
  private PicasaSyncInfo mMasterInfo;
  private Uri mPhotosUri;
  private Uri mPostAlbumsUri;
  private Uri mPostPhotosUri;
  private Uri mSettingsUri;
  private Uri mSyncRequestUri;
  private Uri mUsersUri;

  private PicasaFacade(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    PackageManager localPackageManager = this.mContext.getPackageManager();
    ComponentName localComponentName1 = new ComponentName(this.mContext, PicasaContentProvider.class);
    if (localPackageManager.getComponentEnabledSetting(localComponentName1) != 0)
      localPackageManager.setComponentEnabledSetting(localComponentName1, 0, 1);
    ComponentName localComponentName2 = new ComponentName(this.mContext, PicasaSyncService.class);
    if (localPackageManager.getComponentEnabledSetting(localComponentName2) != 0)
      localPackageManager.setComponentEnabledSetting(localComponentName2, 0, 1);
    updatePicasaSyncInfo(true);
  }

  public static PicasaFacade get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new PicasaFacade(paramContext);
      PicasaFacade localPicasaFacade = sInstance;
      return localPicasaFacade;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void updatePicasaSyncInfo(boolean paramBoolean)
  {
    while (true)
    {
      boolean bool1;
      Object localObject2;
      ServiceInfo localServiceInfo;
      PicasaSyncInfo localPicasaSyncInfo;
      try
      {
        List localList = this.mContext.getPackageManager().queryIntentServices(new Intent("com.google.android.picasasync.SyncAdapter"), 132);
        bool1 = false;
        localObject2 = null;
        String str1 = this.mContext.getPackageName();
        Iterator localIterator = localList.iterator();
        if (!localIterator.hasNext())
          break label318;
        localServiceInfo = ((ResolveInfo)localIterator.next()).serviceInfo;
        if ((!localServiceInfo.enabled) || (!localServiceInfo.applicationInfo.enabled))
        {
          Log.w("gp.PicasaFacade", "ignore disabled picasa sync adapter: " + localServiceInfo);
          localPicasaSyncInfo = null;
          if (localPicasaSyncInfo == null)
            continue;
          if (localPicasaSyncInfo.enableDownSync)
            bool1 = true;
          if ((localObject2 == null) || (localObject2.priority < localPicasaSyncInfo.priority))
            break label546;
          if (!localPicasaSyncInfo.packageName.equals(str1))
            continue;
          this.mLocalInfo = localPicasaSyncInfo;
          continue;
        }
      }
      finally
      {
      }
      Bundle localBundle = localServiceInfo.metaData;
      if (localBundle == null)
      {
        Log.w("gp.PicasaFacade", "missing metadata: " + localServiceInfo);
        localPicasaSyncInfo = null;
      }
      else
      {
        int i = localBundle.getInt("com.google.android.picasasync.priority", -1);
        boolean bool3 = localBundle.getBoolean("com.google.android.picasasync.enable-down-sync", true);
        String str3 = localBundle.getString("com.google.android.picasasync.authority");
        if ((i == -1) || (TextUtils.isEmpty(str3)))
        {
          Log.w("gp.PicasaFacade", "missing required metadata info: " + localServiceInfo);
          localPicasaSyncInfo = null;
        }
        else
        {
          localPicasaSyncInfo = new PicasaSyncInfo(localServiceInfo.packageName, str3, i, bool3);
          continue;
          label318: localObject2.enableDownSync = bool1;
          this.mMasterInfo = localObject2;
          Utils.checkNotNull(this.mLocalInfo);
          Utils.checkNotNull(this.mMasterInfo);
          if (this.mLocalInfo == this.mMasterInfo);
          for (boolean bool2 = true; ; bool2 = false)
          {
            updateSyncableState(bool2);
            String str2 = this.mMasterInfo.authority;
            if (!str2.equals(this.mAuthority))
            {
              this.mAuthority = str2;
              Uri localUri = Uri.parse("content://" + this.mAuthority);
              this.mPhotosUri = Uri.withAppendedPath(localUri, "photos");
              this.mAlbumsUri = Uri.withAppendedPath(localUri, "albums");
              this.mPostAlbumsUri = Uri.withAppendedPath(localUri, "posts_album");
              this.mPostPhotosUri = Uri.withAppendedPath(localUri, "posts");
              this.mUsersUri = Uri.withAppendedPath(localUri, "users");
              this.mSettingsUri = Uri.withAppendedPath(localUri, "settings");
              this.mSyncRequestUri = Uri.withAppendedPath(localUri, "sync_request");
              this.mAlbumCoversUri = Uri.withAppendedPath(localUri, "albumcovers");
            }
            if ((!paramBoolean) && (isMaster()))
              PicasaSyncManager.get(this.mContext).updateTasks(0L);
            return;
          }
          label546: localObject2 = localPicasaSyncInfo;
        }
      }
    }
  }

  private void updateSyncableState(boolean paramBoolean)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    if (paramBoolean);
    String str;
    AccountManager localAccountManager;
    for (int i = 1; ; i = 2)
    {
      ComponentName localComponentName1 = new ComponentName(this.mContext, ConnectivityReceiver.class);
      if (localPackageManager.getComponentEnabledSetting(localComponentName1) != i)
        localPackageManager.setComponentEnabledSetting(localComponentName1, i, 1);
      ComponentName localComponentName2 = new ComponentName(this.mContext, BatteryReceiver.class);
      if (localPackageManager.getComponentEnabledSetting(localComponentName2) != i)
        localPackageManager.setComponentEnabledSetting(localComponentName2, i, 1);
      str = this.mLocalInfo.authority;
      localAccountManager = AccountManager.get(this.mContext);
      if (!paramBoolean)
        break;
      for (Account localAccount2 : localAccountManager.getAccountsByType("com.google"))
        if (ContentResolver.getIsSyncable(localAccount2, str) == 0)
        {
          ContentResolver.setIsSyncable(localAccount2, str, -1);
          ContentResolver.requestSync(localAccount2, str, new Bundle());
        }
    }
    for (Account localAccount1 : localAccountManager.getAccountsByType("com.google"))
    {
      ContentResolver.setIsSyncable(localAccount1, str, 0);
      ContentResolver.cancelSync(localAccount1, str);
    }
  }

  public final Uri getAlbumsUri()
  {
    return this.mAlbumsUri;
  }

  public final String getAuthority()
  {
    return this.mAuthority;
  }

  public final PicasaSyncInfo getMasterInfo()
  {
    return this.mMasterInfo;
  }

  public final Uri getPhotoUri(long paramLong)
  {
    return this.mPhotosUri.buildUpon().appendPath(String.valueOf(paramLong)).build();
  }

  public final Uri getPhotosUri()
  {
    return this.mPhotosUri;
  }

  public final Uri getSettingsUri()
  {
    return this.mSettingsUri;
  }

  public final Uri getSyncRequestUri()
  {
    return this.mSyncRequestUri;
  }

  public final Uri getUsersUri()
  {
    return this.mUsersUri;
  }

  public final boolean isMaster()
  {
    if (this.mMasterInfo == this.mLocalInfo);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onMediaMounted()
  {
    PicasaSyncManager.get(this.mContext).updateTasks(0L);
  }

  public final void onPackageAdded$552c4e01()
  {
    updatePicasaSyncInfo(false);
  }

  public final void onPackageRemoved$552c4e01()
  {
    updatePicasaSyncInfo(false);
  }

  static final class PicasaSyncInfo
  {
    public final String authority;
    public boolean enableDownSync;
    public final String packageName;
    public final int priority;

    public PicasaSyncInfo(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
    {
      this.packageName = paramString1;
      this.authority = paramString2;
      this.priority = paramInt;
      this.enableDownSync = paramBoolean;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PicasaFacade
 * JD-Core Version:    0.6.2
 */