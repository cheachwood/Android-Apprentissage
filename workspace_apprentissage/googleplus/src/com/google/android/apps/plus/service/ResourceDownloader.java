package com.google.android.apps.plus.service;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.VolleyError;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PanoramaDetector;
import com.google.android.apps.plus.phone.FIFEUtil;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.android.picasastore.Config;
import com.google.android.picasastore.PicasaStoreFacade;
import com.google.android.picasasync.PicasaFacade;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class ResourceDownloader
{
  private static int sLandscapeHeight;
  private static int sLandscapeWidth;
  private static int sPortraitHeight;
  private static int sPortraitWidth;
  private static ResourceRequestFilter sRequestFilter = new ResourceRequestFilter((byte)0);
  private Context mContext;
  private VolleyRequestQueue mRequestQueue;

  public ResourceDownloader(Context paramContext, Handler paramHandler)
  {
    this.mContext = paramContext.getApplicationContext();
    int i = ScreenMetrics.getInstance(paramContext).longDimension;
    int j = i / 2;
    sPortraitHeight = j;
    sPortraitWidth = j / paramContext.getResources().getDimensionPixelSize(R.dimen.media_max_portrait_aspect_ratio);
    int k = i / 2;
    sLandscapeWidth = k;
    sLandscapeHeight = k / paramContext.getResources().getDimensionPixelSize(R.dimen.media_min_landscape_aspect_ratio);
    this.mRequestQueue = new VolleyRequestQueue(paramContext, paramHandler);
  }

  public static void loadLocalResource(Context paramContext, Resource paramResource, Uri paramUri, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramResource.isDebugLogEnabled())
      paramResource.logDebug("Loading local resource " + paramUri);
    boolean bool1 = ImageUtils.isVideoMimeType(ImageUtils.getMimeType(paramContext.getContentResolver(), paramUri));
    boolean bool2 = MediaStoreUtils.isMediaStoreUri(paramUri);
    if (!bool1)
      PanoramaDetector.detectPanorama(paramContext, paramResource, paramUri);
    if (paramInt1 == 2);
    while (true)
    {
      int k;
      int i;
      try
      {
        paramInt3 = Config.sThumbNailSize;
        paramInt2 = paramInt3;
        break label265;
        localBitmap = MediaStoreUtils.getThumbnail(paramContext, paramUri, paramInt2, paramInt3);
        if (localBitmap != null)
        {
          paramResource.deliverResource(localBitmap);
          break label283;
          throw new UnsupportedOperationException();
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        paramResource.deliverDownloadError(7);
        break label283;
        paramInt3 = Config.sScreenNailSize;
        paramInt2 = paramInt3;
        break label265;
        if (paramInt1 == 1)
        {
          localBitmap = MediaStore.Images.Media.getBitmap(paramContext.getContentResolver(), paramUri);
          continue;
        }
        Bitmap localBitmap = ImageUtils.createLocalBitmap(paramContext.getContentResolver(), paramUri, Math.max(paramInt2, paramInt3));
        continue;
        if (!bool1)
          break label336;
        int j = ImageUtils.getMaxThumbnailDimension(paramContext, 3);
        if (paramInt2 > j)
          break label324;
        if (paramInt3 <= j)
          break label330;
        break label324;
        localBitmap = ImageUtils.createVideoThumbnail(paramContext, paramUri, k);
        continue;
        localBitmap = ImageUtils.createLocalBitmap(paramContext.getContentResolver(), paramUri, i);
        continue;
        i = Math.max(paramInt2, paramInt3);
        continue;
        paramResource.deliverDownloadError(4);
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        paramResource.deliverDownloadError(4);
      }
      catch (IOException localIOException)
      {
        paramResource.deliverDownloadError(6);
      }
      label265: if (bool2)
        if (paramInt1 != 2)
          if (bool1)
          {
            continue;
            label283: return;
            switch (paramInt1)
            {
            case 2:
            default:
            case 3:
            case 4:
            case 5:
            case 0:
            case 1:
            }
            label324: k = 1;
            continue;
            label330: k = 3;
            continue;
            label336: if (paramInt1 == 1)
              i = 0;
          }
    }
  }

  public final void cancelDownload(Resource paramResource)
  {
    synchronized (sRequestFilter)
    {
      sRequestFilter.resource = paramResource;
      this.mRequestQueue.cancelAll(sRequestFilter);
      return;
    }
  }

  public final void downloadResource(Resource paramResource, MediaRef paramMediaRef, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    String str1 = paramMediaRef.getUrl();
    String str4;
    String str5;
    if ((str1 != null) && (!paramBoolean))
    {
      if (str1.startsWith("//"))
        str1 = "http:" + str1;
      if (FIFEUtil.isFifeHostedUrl(str1))
      {
        Uri localUri2 = Uri.parse(str1);
        str4 = FIFEUtil.getImageUriOptions(localUri2);
        if (!str4.contains("k"))
        {
          if (!TextUtils.isEmpty(str4))
            break label223;
          str5 = "k";
          str1 = FIFEUtil.setImageUriOptions(str5, localUri2).toString();
        }
      }
    }
    Uri localUri1 = paramMediaRef.getLocalUri();
    long l = paramMediaRef.getPhotoId();
    Context localContext;
    String str2;
    boolean bool;
    label140: String str3;
    if (l != 0L)
    {
      localContext = this.mContext;
      str2 = paramMediaRef.getOwnerGaiaId();
      if (str2 != null)
        break label249;
      bool = false;
      if ((bool) && (paramInt1 != 0) && (paramInt1 != 1))
      {
        if (paramInt1 != 2)
          break label279;
        str3 = "thumbnail";
        label164: if ((str1 == null) || (str1.startsWith("content:")))
          break label287;
      }
    }
    label279: label287: for (localUri1 = PicasaStoreFacade.get(this.mContext).getPhotoUri(l, str3, str1); ; localUri1 = PicasaFacade.get(this.mContext).getPhotoUri(l).buildUpon().appendQueryParameter("type", str3).build())
    {
      this.mRequestQueue.add(new DownloadRequest(paramResource, localUri1, str1, paramInt1, paramInt2, paramInt3));
      return;
      label223: str5 = str4 + "-k";
      break;
      label249: EsAccount localEsAccount = EsService.getActiveAccount(localContext);
      if (localEsAccount == null)
      {
        bool = false;
        break label140;
      }
      bool = localEsAccount.isMyGaiaId(str2);
      break label140;
      str3 = "screennail";
      break label164;
    }
  }

  public final void downloadResource(Resource paramResource, String paramString)
  {
    this.mRequestQueue.add(new DownloadRequest(paramResource, paramString));
  }

  private static final class DownloadRequest extends VolleyRequest
  {
    private final boolean mConstructDownloadUrl;
    private String mDownloadUrl;
    private final int mHeight;
    private final Resource mResource;
    private final int mSizeCategory;
    private final String mUrl;
    private final int mWidth;

    public DownloadRequest(Resource paramResource, Uri paramUri, String paramString, int paramInt1, int paramInt2, int paramInt3)
    {
      this(paramResource, paramUri, paramString, paramInt1, paramInt2, paramInt3, true);
    }

    private DownloadRequest(Resource paramResource, Uri paramUri, String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      super(paramUri);
      this.mUrl = paramString;
      this.mResource = paramResource;
      this.mSizeCategory = paramInt1;
      this.mWidth = paramInt2;
      this.mHeight = paramInt3;
      this.mConstructDownloadUrl = paramBoolean;
      setShouldCache(false);
    }

    public DownloadRequest(Resource paramResource, String paramString)
    {
      this(paramResource, null, paramString, -1, 0, 0, false);
    }

    public final void deliverError(VolleyError paramVolleyError)
    {
      if (this.mResource.isDebugLogEnabled())
        Log.e("EsResource", "Failed to download " + this.mUrl, paramVolleyError);
      if ((paramVolleyError instanceof VolleyOutOfMemoryError))
        this.mResource.deliverDownloadError(7);
      while (true)
      {
        return;
        if (!(paramVolleyError instanceof NoConnectionError))
          break;
        this.mResource.deliverDownloadError(5);
      }
      if (paramVolleyError.networkResponse != null);
      for (int i = paramVolleyError.networkResponse.statusCode; ; i = 0)
      {
        this.mResource.deliverHttpError$4f708078(i);
        break;
      }
    }

    public final void deliverResponse(byte[] paramArrayOfByte)
    {
      this.mResource.deliverData(paramArrayOfByte, true);
    }

    public final String getUrl()
    {
      String str;
      if ((this.mConstructDownloadUrl) && (this.mDownloadUrl == null))
      {
        str = this.mUrl;
        if (str.startsWith("//"))
          str = "http:" + str;
        switch (this.mSizeCategory)
        {
        default:
        case 0:
        case 2:
        case 3:
        case 4:
        case 5:
        case 1:
        }
      }
      while (true)
      {
        return this.mDownloadUrl;
        if ((this.mWidth == 0) || (this.mHeight == 0))
        {
          this.mDownloadUrl = ImageUtils.getResizedUrl(this.mWidth, this.mHeight, str);
        }
        else
        {
          this.mDownloadUrl = ImageUtils.getCenterCroppedAndResizedUrl(this.mWidth, this.mHeight, str);
          continue;
          this.mDownloadUrl = PicasaStoreFacade.convertImageUrl(str, Config.sThumbNailSize, true);
          continue;
          this.mDownloadUrl = PicasaStoreFacade.convertImageUrl(str, Config.sScreenNailSize, false);
          continue;
          this.mDownloadUrl = ImageUtils.getCenterCroppedAndResizedUrl(ResourceDownloader.sPortraitWidth, ResourceDownloader.sPortraitHeight, str);
          continue;
          this.mDownloadUrl = ImageUtils.getCenterCroppedAndResizedUrl(ResourceDownloader.sLandscapeWidth, ResourceDownloader.sLandscapeHeight, str);
          continue;
          this.mDownloadUrl = PicasaStoreFacade.convertImageUrl(str, 0, false);
          continue;
          this.mDownloadUrl = this.mUrl;
        }
      }
    }
  }

  private static final class ResourceRequestFilter
    implements RequestQueue.RequestFilter
  {
    public Resource resource;

    public final boolean apply(Request<?> paramRequest)
    {
      if (((ResourceDownloader.DownloadRequest)paramRequest).mResource == this.resource);
      for (boolean bool = true; ; bool = false)
        return bool;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ResourceDownloader
 * JD-Core Version:    0.6.2
 */