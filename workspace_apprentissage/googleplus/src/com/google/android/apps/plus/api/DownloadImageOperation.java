package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import com.google.android.apps.plus.content.CachedImageRequest;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsMediaCache;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.phone.FIFEUtil;
import com.google.android.apps.plus.phone.ImageProxyUtil;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.GifImage;
import com.google.android.apps.plus.util.ImageLoadingMetrics;
import com.google.android.apps.plus.util.ImageUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class DownloadImageOperation extends HttpOperation
{
  private byte[] mImageBytes;
  private final CachedImageRequest mRequest;
  private final boolean mSaveToCache;

  public DownloadImageOperation(Context paramContext, EsAccount paramEsAccount, CachedImageRequest paramCachedImageRequest, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    this(paramContext, paramEsAccount, paramCachedImageRequest, true, null, null);
  }

  public DownloadImageOperation(Context paramContext, EsAccount paramEsAccount, CachedImageRequest paramCachedImageRequest, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, "GET", paramCachedImageRequest.getDownloadUrl(), paramEsAccount, new ByteArrayOutputStream(15000), paramIntent, paramOperationListener);
    this.mRequest = paramCachedImageRequest;
    this.mSaveToCache = paramBoolean;
  }

  public final byte[] getImageBytes()
  {
    return this.mImageBytes;
  }

  protected final void onHttpHandleContentFromStream$6508b088(InputStream paramInputStream)
    throws IOException
  {
    onStartResultProcessing();
    ByteArrayOutputStream localByteArrayOutputStream = (ByteArrayOutputStream)getOutputStream();
    while (true)
    {
      String str;
      byte[] arrayOfByte1;
      Bitmap localBitmap;
      try
      {
        if (!(this.mRequest instanceof MediaImageRequest))
          break label296;
        MediaImageRequest localMediaImageRequest = (MediaImageRequest)this.mRequest;
        int i = localMediaImageRequest.getMediaType();
        if ((i == 3) || (i == 2) || (i == 1))
        {
          str = localMediaImageRequest.getUrl();
          arrayOfByte1 = localByteArrayOutputStream.toByteArray();
          if (GifImage.isGif(arrayOfByte1))
            this.mImageBytes = arrayOfByte1;
        }
        else
        {
          if (ImageLoadingMetrics.areImageLoadingMetricsEnabled())
            ImageLoadingMetrics.recordImageDownloadFinished(this.mRequest.getUriForLogging());
          if ((!this.mSaveToCache) || (this.mImageBytes == null))
            break;
          EsMediaCache.insertMedia(this.mContext, this.mRequest, this.mImageBytes);
          break;
        }
        int j = localMediaImageRequest.getWidth();
        int k = localMediaImageRequest.getHeight();
        if ((j <= 0) && (k <= 0))
        {
          if (FIFEUtil.isFifeHostedUrl(str))
          {
            localPoint = FIFEUtil.getImageUrlSize(str);
            j = localPoint.x;
            k = localPoint.y;
          }
        }
        else
        {
          if ((j <= 0) || (k <= 0))
            break label279;
          localBitmap = ImageUtils.resizeBitmap(arrayOfByte1, j, k);
          if (localBitmap != null)
            break label269;
          arrayOfByte2 = null;
          this.mImageBytes = arrayOfByte2;
          continue;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Log.w("HttpTransaction", "DownloadImageOperation OutOfMemoryError on image bytes: " + localByteArrayOutputStream.size(), localOutOfMemoryError);
        throw new ProtocolException("Cannot handle downloaded image");
      }
      Point localPoint = ImageProxyUtil.getImageUrlSize(str);
      continue;
      label269: byte[] arrayOfByte2 = ImageUtils.compressBitmap(localBitmap);
      continue;
      label279: Log.w("HttpTransaction", "DownloadImageOperation could not resize image; width or height were not specified");
      this.mImageBytes = arrayOfByte1;
      continue;
      label296: this.mImageBytes = localByteArrayOutputStream.toByteArray();
    }
  }

  public final void start(EsSyncAdapterService.SyncState paramSyncState, HttpTransactionMetrics paramHttpTransactionMetrics)
  {
    if (ImageLoadingMetrics.areImageLoadingMetricsEnabled())
      ImageLoadingMetrics.recordImageDownloadStarted(this.mRequest.getUriForLogging());
    super.start(paramSyncState, paramHttpTransactionMetrics);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DownloadImageOperation
 * JD-Core Version:    0.6.2
 */