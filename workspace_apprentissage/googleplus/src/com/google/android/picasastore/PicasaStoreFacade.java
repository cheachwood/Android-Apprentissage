package com.google.android.picasastore;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public final class PicasaStoreFacade
{
  private static File sCacheDir;
  private static PicasaStoreFacade sInstance;
  private static Class<?> sNetworkReceiver;
  private Uri mAlbumCoversUri;
  private String mAuthority;
  private Uri mCachedFingerprintUri;
  private final Context mContext;
  private Uri mFingerprintUri;
  private PicasaStoreInfo mLocalInfo;
  private PicasaStoreInfo mMasterInfo;
  private Uri mPhotosUri;
  private Uri mRecalculateFingerprintUri;

  private PicasaStoreFacade(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    updatePicasaSyncInfo(true);
  }

  public static void broadcastOperationReport(String paramString, long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4)
  {
    if ((sInstance == null) || (sNetworkReceiver == null));
    while (true)
    {
      return;
      Context localContext = sInstance.mContext;
      Intent localIntent = new Intent(localContext, sNetworkReceiver);
      localIntent.setAction("com.google.android.picasastore.op_report");
      localIntent.putExtra("op_name", paramString);
      localIntent.putExtra("total_time", paramLong1);
      localIntent.putExtra("net_duration", paramLong2);
      localIntent.putExtra("transaction_count", paramInt);
      localIntent.putExtra("sent_bytes", paramLong3);
      localIntent.putExtra("received_bytes", paramLong4);
      localContext.sendBroadcast(localIntent);
    }
  }

  public static String convertImageUrl(String paramString, int paramInt, boolean paramBoolean)
  {
    StringBuilder localStringBuilder;
    if (FIFEUtil.isFifeHostedUrl(paramString))
    {
      String str2 = FIFEUtil.getImageUrlOptions(paramString);
      boolean bool1 = str2.contains("I");
      boolean bool2 = str2.contains("k");
      localStringBuilder = new StringBuilder();
      localStringBuilder.append('s').append(paramInt);
      localStringBuilder.append("-no");
      if (paramBoolean)
        localStringBuilder.append("-c");
      if (bool1)
        localStringBuilder.append("-I");
      if (bool2)
        localStringBuilder.append("-k");
    }
    for (String str1 = FIFEUtil.setImageUrlOptions(localStringBuilder.toString(), paramString).toString(); ; str1 = ImageProxyUtil.setImageUrlSize(paramInt, paramString))
    {
      return str1;
      if (paramBoolean)
        Log.w("gp.PicasaStore", "not a FIFE url, ignore the crop option");
    }
  }

  public static File createCacheFile(long paramLong, String paramString)
  {
    File localFile1 = getCacheDirectory();
    if (localFile1 == null);
    for (File localFile3 = null; ; localFile3 = null)
      while (true)
      {
        return localFile3;
        String str1 = paramLong + paramString;
        int i = (int)(paramLong % 10L);
        String str2 = "picasa--" + i;
        int j = 0;
        if (j < 5)
        {
          File localFile2 = new File(localFile1, str2);
          if ((localFile2.isDirectory()) || (localFile2.mkdirs()))
            localFile3 = new File(localFile2, str1);
          try
          {
            localFile3.createNewFile();
            boolean bool = localFile3.exists();
            if (!bool)
            {
              str2 = str2 + "e";
              j++;
            }
          }
          catch (IOException localIOException)
          {
            while (true)
              Log.d("gp.PicasaStore", str2 + " is full: " + localIOException);
          }
        }
      }
  }

  public static PicasaStoreFacade get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new PicasaStoreFacade(paramContext);
      PicasaStoreFacade localPicasaStoreFacade = sInstance;
      return localPicasaStoreFacade;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static File getAlbumCoverCacheFile(long paramLong, String paramString1, String paramString2)
  {
    File localFile1 = getCacheDirectory();
    if (localFile1 == null);
    for (File localFile2 = null; ; localFile2 = new File(localFile1, "picasa_covers/" + getAlbumCoverKey(paramLong, paramString1) + paramString2))
      return localFile2;
  }

  public static String getAlbumCoverKey(long paramLong, String paramString)
  {
    return paramLong + '_' + Utils.crc64Long(paramString);
  }

  public static File getCacheDirectory()
  {
    while (true)
    {
      try
      {
        if (sCacheDir == null)
        {
          File localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "cache/com.google.android.googlephotos");
          sCacheDir = localFile1;
          if (!localFile1.isDirectory())
          {
            boolean bool = sCacheDir.mkdirs();
            if (!bool)
              break label123;
          }
        }
        try
        {
          File localFile2 = new File(sCacheDir, ".nomedia");
          if (!localFile2.exists())
            localFile2.createNewFile();
          File localFile3 = sCacheDir;
          return localFile3;
        }
        catch (IOException localIOException)
        {
          Log.w("gp.PicasaStore", "fail to create '.nomedia' in " + sCacheDir);
          sCacheDir = null;
          continue;
        }
      }
      finally
      {
      }
      label123: Log.w("gp.PicasaStore", "fail to create cache dir in external storage");
      sCacheDir = null;
    }
  }

  public static File getCacheFile(long paramLong, String paramString)
  {
    File localFile1 = getCacheDirectory();
    File localFile3;
    if (localFile1 == null)
      localFile3 = null;
    while (true)
    {
      return localFile3;
      String str1 = paramLong + paramString;
      int i = (int)(paramLong % 10L);
      String str2 = "picasa--" + i;
      for (int j = 0; ; j++)
      {
        if (j >= 5)
          break label153;
        File localFile2 = new File(localFile1, str2);
        if (!localFile2.exists())
        {
          localFile3 = null;
          break;
        }
        if (localFile2.isDirectory())
        {
          localFile3 = new File(localFile2, str1);
          if (localFile3.exists())
            break;
        }
        str2 = str2 + "e";
      }
      label153: localFile3 = null;
    }
  }

  public static void setNetworkReceiver(Class<?> paramClass)
  {
    sNetworkReceiver = paramClass;
  }

  private void updatePicasaSyncInfo(boolean paramBoolean)
  {
    while (true)
    {
      Object localObject2;
      ServiceInfo localServiceInfo;
      PicasaStoreInfo localPicasaStoreInfo;
      try
      {
        List localList = this.mContext.getPackageManager().queryIntentServices(new Intent("com.google.android.picasastore.PACKAGE_METADATA_LOOKUP"), 132);
        String str1 = this.mContext.getPackageName();
        localObject2 = null;
        Iterator localIterator = localList.iterator();
        if (!localIterator.hasNext())
          break label297;
        localServiceInfo = ((ResolveInfo)localIterator.next()).serviceInfo;
        if ((!localServiceInfo.enabled) || (!localServiceInfo.applicationInfo.enabled))
        {
          Log.w("gp.PicasaStore", "ignore disabled picasa sync adapter: " + localServiceInfo);
          localPicasaStoreInfo = null;
          if (localPicasaStoreInfo == null)
            continue;
          if ((localObject2 == null) || (localObject2.priority < localPicasaStoreInfo.priority))
            break label456;
          if (!localPicasaStoreInfo.packageName.equals(str1))
            continue;
          this.mLocalInfo = localPicasaStoreInfo;
          continue;
        }
      }
      finally
      {
      }
      Bundle localBundle = localServiceInfo.metaData;
      if (localBundle == null)
      {
        Log.w("gp.PicasaStore", "missing metadata: " + localServiceInfo);
        localPicasaStoreInfo = null;
      }
      else
      {
        int i = localBundle.getInt("com.google.android.picasastore.priority", -1);
        String str3 = localBundle.getString("com.google.android.picasastore.authority");
        if ((i == -1) || (TextUtils.isEmpty(str3)))
        {
          Log.w("gp.PicasaStore", "missing required metadata info: " + localServiceInfo);
          localPicasaStoreInfo = null;
        }
        else
        {
          localPicasaStoreInfo = new PicasaStoreInfo(localServiceInfo.packageName, str3, i);
          continue;
          label297: this.mMasterInfo = localObject2;
          Utils.checkNotNull(this.mLocalInfo);
          Utils.checkNotNull(this.mMasterInfo);
          String str2 = this.mMasterInfo.authority;
          if (!str2.equals(this.mAuthority))
          {
            this.mAuthority = str2;
            Uri localUri = Uri.parse("content://" + this.mAuthority);
            this.mPhotosUri = Uri.withAppendedPath(localUri, "photos");
            this.mFingerprintUri = Uri.withAppendedPath(localUri, "fingerprint");
            this.mRecalculateFingerprintUri = this.mFingerprintUri.buildUpon().appendQueryParameter("force_recalculate", "1").build();
            this.mCachedFingerprintUri = this.mFingerprintUri.buildUpon().appendQueryParameter("cache_only", "1").build();
            this.mAlbumCoversUri = Uri.withAppendedPath(localUri, "albumcovers");
          }
          return;
          label456: localObject2 = localPicasaStoreInfo;
        }
      }
    }
  }

  public final String getAuthority()
  {
    return this.mAuthority;
  }

  public final Uri getFingerprintUri()
  {
    return this.mFingerprintUri;
  }

  public final Uri getFingerprintUri(boolean paramBoolean1, boolean paramBoolean2)
  {
    Uri localUri;
    if (paramBoolean1)
      localUri = this.mRecalculateFingerprintUri;
    while (true)
    {
      return localUri;
      if (paramBoolean2)
        localUri = this.mCachedFingerprintUri;
      else
        localUri = this.mFingerprintUri;
    }
  }

  public final Uri getPhotoUri(long paramLong, String paramString1, String paramString2)
  {
    return this.mPhotosUri.buildUpon().appendPath(String.valueOf(paramLong)).appendQueryParameter("type", paramString1).appendQueryParameter("content_url", paramString2).build();
  }

  public final boolean isMaster()
  {
    if (this.mMasterInfo == this.mLocalInfo);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onPackageAdded$552c4e01()
  {
    updatePicasaSyncInfo(false);
  }

  public final void onPackageRemoved$552c4e01()
  {
    updatePicasaSyncInfo(false);
  }

  public static class DummyService extends Service
  {
    public IBinder onBind(Intent paramIntent)
    {
      return null;
    }
  }

  static final class PicasaStoreInfo
  {
    public final String authority;
    public final String packageName;
    public final int priority;

    public PicasaStoreInfo(String paramString1, String paramString2, int paramInt)
    {
      this.packageName = paramString1;
      this.authority = paramString2;
      this.priority = paramInt;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.PicasaStoreFacade
 * JD-Core Version:    0.6.2
 */