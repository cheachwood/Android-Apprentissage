package com.google.android.picasasync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import com.google.android.picasastore.FIFEUtil;
import com.google.android.picasastore.ImageProxyUtil;
import com.google.android.picasastore.MetricsUtils;

public final class PicasaApi
{
  private final String mBaseUrl;
  private final GDataClient mClient = new GDataClient();
  private final GDataClient.Operation mOperation = new GDataClient.Operation();

  public PicasaApi(ContentResolver paramContentResolver)
  {
    String str = Settings.Secure.getString(paramContentResolver, "picasa_gdata_base_url");
    if (str == null)
      str = "https://picasaweb.google.com/data/feed/api/";
    this.mBaseUrl = str;
  }

  public static String convertImageUrl(String paramString, int paramInt, boolean paramBoolean)
  {
    StringBuilder localStringBuilder;
    if (FIFEUtil.isFifeHostedUrl(paramString))
    {
      boolean bool = FIFEUtil.getImageUrlOptions(paramString).contains("I");
      localStringBuilder = new StringBuilder();
      localStringBuilder.append('s').append(paramInt);
      if (paramBoolean)
        localStringBuilder.append("-c");
      if (bool)
        localStringBuilder.append("-I");
    }
    for (String str = FIFEUtil.setImageUrlOptions(localStringBuilder.toString(), paramString).toString(); ; str = ImageProxyUtil.setImageUrlSize(paramInt, paramString))
    {
      return str;
      if (paramBoolean)
        Log.w("gp.PicasaAPI", "not a FIFE url, ignore the crop option");
    }
  }

  private static String encodeUsername(String paramString)
  {
    String str = paramString.toLowerCase();
    if ((str.contains("@gmail.")) || (str.contains("@googlemail.")))
      str = str.substring(0, str.indexOf('@'));
    return Uri.encode(str);
  }

  private int getAlbumPhotos$6e9461d9(AlbumEntry paramAlbumEntry, String paramString1, String paramString2, PhotoCollectorJson paramPhotoCollectorJson)
  {
    int i = MetricsUtils.begin("PicasaApi." + paramString2);
    if (Log.isLoggable("gp.PicasaAPI", 2))
    {
      String str = paramString2 + " for %s / %s, etag: %s";
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Utils.maskDebugInfo(paramAlbumEntry.user);
      arrayOfObject[1] = Utils.maskDebugInfo(Long.valueOf(paramAlbumEntry.id));
      arrayOfObject[2] = paramAlbumEntry.photosEtag;
      Log.v("gp.PicasaAPI", String.format(str, arrayOfObject));
    }
    try
    {
      GDataClient.Operation localOperation = this.mOperation;
      localOperation.inOutEtag = paramAlbumEntry.photosEtag;
      this.mClient.get(paramString1, localOperation);
      try
      {
        switch (localOperation.outStatus)
        {
        default:
          Log.e("gp.PicasaAPI", paramString2 + " fail: " + localOperation.outStatus);
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 3;
        case 200:
        case 304:
        case 401:
        case 403:
        }
        while (true)
        {
          return j;
          paramAlbumEntry.photosEtag = localOperation.inOutEtag;
          paramPhotoCollectorJson.parse(localOperation.outBody);
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 0;
          continue;
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 1;
          continue;
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 2;
        }
      }
      finally
      {
        Utils.closeSilently(localOperation.outBody);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Utils.handleInterrruptedException(localException);
        Log.e("gp.PicasaAPI", paramString2 + " fail", localException);
        MetricsUtils.end(i);
        int j = 3;
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
  }

  public final int getAlbumPhotos(AlbumEntry paramAlbumEntry, EntryHandler paramEntryHandler)
  {
    return getAlbumPhotos$6e9461d9(paramAlbumEntry, this.mBaseUrl + "user/" + encodeUsername(paramAlbumEntry.user) + "/albumid/" + paramAlbumEntry.id + "?max-results=1000&imgmax=d&thumbsize=640u&visibility=visible&v=4&alt=json&fd=shapes" + "&kind=photo", "getAlbumPhotos", new PhotoCollectorJson(paramEntryHandler));
  }

  public final int getAlbums(UserEntry paramUserEntry, EntryHandler paramEntryHandler)
  {
    int i = MetricsUtils.begin("PicasaApi.getAlbums");
    StringBuilder localStringBuilder = new StringBuilder(this.mBaseUrl).append("user/").append(encodeUsername(paramUserEntry.account)).append("?max-results=1000&imgmax=d&thumbsize=640u&visibility=visible&v=4&alt=json&fd=shapes").append("&kind=album");
    try
    {
      GDataClient.Operation localOperation = this.mOperation;
      localOperation.inOutEtag = paramUserEntry.albumsEtag;
      if (Log.isLoggable("gp.PicasaAPI", 2))
      {
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Utils.maskDebugInfo(paramUserEntry.account);
        arrayOfObject3[1] = paramUserEntry.albumsEtag;
        Log.v("gp.PicasaAPI", String.format("getAlbums for %s, etag = %s", arrayOfObject3));
      }
      this.mClient.get(localStringBuilder.toString(), localOperation);
      try
      {
        switch (localOperation.outStatus)
        {
        default:
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = Utils.maskDebugInfo(localStringBuilder.toString());
          arrayOfObject2[1] = Integer.valueOf(localOperation.outStatus);
          Log.e("gp.PicasaAPI", String.format("    getAlbums fail - uri: %s, status code: %s", arrayOfObject2));
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 3;
        case 200:
        case 304:
        case 401:
        case 403:
        }
        while (true)
        {
          return j;
          paramUserEntry.albumsEtag = localOperation.inOutEtag;
          new AlbumCollectorJson(paramEntryHandler).parse(localOperation.outBody);
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 0;
          continue;
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 1;
          continue;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Utils.maskDebugInfo(localStringBuilder.toString());
          arrayOfObject1[1] = Integer.valueOf(localOperation.outStatus);
          Log.e("gp.PicasaAPI", String.format("    getAlbums fail - uri: %s, status code: %s", arrayOfObject1));
          Utils.closeSilently(localOperation.outBody);
          MetricsUtils.end(i);
          j = 2;
        }
      }
      finally
      {
        Utils.closeSilently(localOperation.outBody);
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Utils.handleInterrruptedException(localThrowable);
        Log.e("gp.PicasaAPI", "getAlbums fail", localThrowable);
        MetricsUtils.end(i);
        int j = 3;
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
  }

  public final void setAuthToken(String paramString)
  {
    this.mClient.setAuthToken(paramString);
  }

  public static abstract interface EntryHandler
  {
    public abstract void handleEntry(ContentValues paramContentValues);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PicasaApi
 * JD-Core Version:    0.6.2
 */