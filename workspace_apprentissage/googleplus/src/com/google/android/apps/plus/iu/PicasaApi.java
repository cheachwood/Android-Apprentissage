package com.google.android.apps.plus.iu;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;

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

  private int getUploadedPhotos$67a1bd15(String paramString1, String[] paramArrayOfString, String paramString2, PhotoCollectorJson paramPhotoCollectorJson)
  {
    int i = MetricsUtils.begin("PicasaApi.getUploadedPhotos");
    if (EsLog.isLoggable("iu.PicasaAPI", 3))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Utils.maskDebugInfo(paramString1);
      arrayOfObject[1] = paramArrayOfString[0];
      Log.d("iu.PicasaAPI", String.format("get uploaded photos for %s etag %s", arrayOfObject));
    }
    try
    {
      GDataClient.Operation localOperation = this.mOperation;
      localOperation.inOutEtag = paramArrayOfString[0];
      this.mClient.get(paramString2, localOperation);
      try
      {
        switch (localOperation.outStatus)
        {
        default:
          if (EsLog.isLoggable("iu.PicasaAPI", 6))
            Log.e("iu.PicasaAPI", "getUploadedPhotos failed: " + localOperation.outStatus);
          Utils.closeSilently(localOperation.outBody);
          if (EsLog.isLoggable("iu.PicasaAPI", 3))
            Log.d("iu.PicasaAPI", "   done");
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
          paramArrayOfString[0] = localOperation.inOutEtag;
          paramPhotoCollectorJson.parse(localOperation.outBody);
          Utils.closeSilently(localOperation.outBody);
          if (EsLog.isLoggable("iu.PicasaAPI", 3))
            Log.d("iu.PicasaAPI", "   done");
          MetricsUtils.end(i);
          j = 0;
          continue;
          Utils.closeSilently(localOperation.outBody);
          if (EsLog.isLoggable("iu.PicasaAPI", 3))
            Log.d("iu.PicasaAPI", "   done");
          MetricsUtils.end(i);
          j = 1;
          continue;
          Utils.closeSilently(localOperation.outBody);
          if (EsLog.isLoggable("iu.PicasaAPI", 3))
            Log.d("iu.PicasaAPI", "   done");
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
        if (EsLog.isLoggable("iu.PicasaAPI", 6))
          Log.e("iu.PicasaAPI", "getUploadedPhotos failed", localException);
        if (EsLog.isLoggable("iu.PicasaAPI", 3))
          Log.d("iu.PicasaAPI", "   done");
        MetricsUtils.end(i);
        int j = 3;
      }
    }
    finally
    {
      if (EsLog.isLoggable("iu.PicasaAPI", 3))
        Log.d("iu.PicasaAPI", "   done");
      MetricsUtils.end(i);
    }
  }

  public final int getUploadedPhotos(String paramString, String[] paramArrayOfString, EntryHandler paramEntryHandler)
  {
    StringBuilder localStringBuilder = new StringBuilder(this.mBaseUrl).append("user/");
    String str1 = paramString.toLowerCase();
    if ((str1.contains("@gmail.")) || (str1.contains("@googlemail.")))
      str1 = str1.substring(0, str1.indexOf('@'));
    String str2 = Uri.encode(str1) + "?max-results=1000&imgmax=d&thumbsize=640u&visibility=visible&v=4&alt=json&kind=photo" + "&streamid=camera_sync_created";
    PhotoCollectorJson localPhotoCollectorJson = new PhotoCollectorJson(paramEntryHandler);
    int i = 1;
    int j = getUploadedPhotos$67a1bd15(paramString, paramArrayOfString, str2 + "&start-index=" + i, localPhotoCollectorJson);
    if (j != 0);
    while (true)
    {
      return j;
      if (localPhotoCollectorJson.entryCount != 0)
      {
        i += localPhotoCollectorJson.entryCount;
        paramArrayOfString[0] = null;
        break;
      }
      if (EsLog.isLoggable("iu.PicasaAPI", 3))
        Log.d("iu.PicasaAPI", "total entry count=" + (i - 1));
      j = 0;
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
 * Qualified Name:     com.google.android.apps.plus.iu.PicasaApi
 * JD-Core Version:    0.6.2
 */