package com.google.android.apps.plus.service;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.content.AvatarImageRequest;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.CachedImageRequest;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsMediaCache;
import com.google.android.apps.plus.content.ImageRequest;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.GifDrawable;
import com.google.android.apps.plus.util.ImageLoadingMetrics;
import com.google.android.apps.plus.util.ImageUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ImageCache
  implements Handler.Callback
{
  private static final byte[] EMPTY_ARRAY = new byte[0];
  private static final Object FAILED_IMAGE = new Object();
  private static HashSet<OnAvatarChangeListener> mAvatarListeners = new HashSet();
  private static HashSet<OnMediaImageChangeListener> mMediaImageListeners = new HashSet();
  private static HashSet<ImageCache.OnImageRequestCompleteListener> mRequestCompleteListeners = new HashSet();
  private static ImageCache sInstance;
  private static int sMediumAvatarEstimatedSize;
  private static int sSmallAvatarEstimatedSize;
  private static int sTinyAvatarEstimatedSize;
  private int mBackgroundThreadBitmapCount;
  private final Context mContext;
  private final LruCache<ImageRequest, Object> mImageCache;
  private final LruCache<ImageRequest, ImageHolder> mImageHolderCache;
  private final int mImageHolderCacheRedZoneBytes;
  private final ArrayList<ImageHolder> mImageHolderQueue = new ArrayList();
  private LoaderThread mLoaderThread;
  private boolean mLoadingRequested;
  private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), this);
  private boolean mPaused;
  private final ConcurrentHashMap<ImageConsumer, ImageRequest> mPendingRequests = new ConcurrentHashMap();

  private ImageCache(Context paramContext)
  {
    this.mContext = paramContext;
    Resources localResources = paramContext.getApplicationContext().getResources();
    int i;
    if (EsApplication.sMemoryClass >= 48)
    {
      i = Math.max(localResources.getInteger(R.integer.config_image_cache_max_bytes_decoded_large), 1024 * (1024 * (EsApplication.sMemoryClass / 4)));
      this.mImageCache = new LruCache(i)
      {
      };
      if (EsApplication.sMemoryClass < 48)
        break label234;
    }
    label234: for (int j = 1024 * (1024 * (EsApplication.sMemoryClass / 4)); ; j = 0)
    {
      int k = Math.max(j, localResources.getInteger(R.integer.config_image_cache_max_bytes_encoded));
      this.mImageHolderCache = new LruCache(k)
      {
      };
      this.mImageHolderCacheRedZoneBytes = ((int)(0.9D * k));
      if (sTinyAvatarEstimatedSize == 0)
      {
        sTinyAvatarEstimatedSize = (int)(0.3F * EsAvatarData.getTinyAvatarSize(paramContext) * EsAvatarData.getTinyAvatarSize(paramContext));
        sSmallAvatarEstimatedSize = (int)(0.3F * EsAvatarData.getSmallAvatarSize(paramContext) * EsAvatarData.getSmallAvatarSize(paramContext));
        sMediumAvatarEstimatedSize = (int)(0.3F * EsAvatarData.getMediumAvatarSize(paramContext) * EsAvatarData.getMediumAvatarSize(paramContext));
      }
      return;
      i = localResources.getInteger(R.integer.config_image_cache_max_bytes_decoded_small);
      break;
    }
  }

  private void clearTemporaryImageReferences()
  {
    synchronized (this.mImageHolderQueue)
    {
      Iterator localIterator = this.mImageHolderQueue.iterator();
      if (localIterator.hasNext())
        ((ImageHolder)localIterator.next()).image = null;
    }
    this.mImageHolderQueue.clear();
  }

  private void decodeImage(ImageRequest paramImageRequest, ImageHolder paramImageHolder)
  {
    if (paramImageHolder.image != null);
    while (true)
    {
      return;
      byte[] arrayOfByte = paramImageHolder.bytes;
      if ((arrayOfByte != null) && (arrayOfByte.length != 0))
      {
        paramImageHolder.image = this.mImageCache.get(paramImageRequest);
        if (paramImageHolder.image == null)
        {
          paramImageHolder.image = ImageUtils.decodeMedia(arrayOfByte);
          if (paramImageHolder.image == null)
            paramImageHolder.image = FAILED_IMAGE;
          this.mImageCache.put(paramImageRequest, paramImageHolder.image);
          if (this.mImageCache.get(paramImageRequest) == null)
          {
            paramImageHolder.image = FAILED_IMAGE;
            this.mImageCache.put(paramImageRequest, FAILED_IMAGE);
          }
        }
      }
    }
  }

  private void ensureLoaderThread()
  {
    if (this.mLoaderThread == null)
    {
      this.mContext.getContentResolver();
      this.mLoaderThread = new LoaderThread();
      this.mLoaderThread.start();
    }
  }

  private void evictImage(ImageRequest paramImageRequest)
  {
    this.mImageCache.remove(paramImageRequest);
    ImageHolder localImageHolder = (ImageHolder)this.mImageHolderCache.get(paramImageRequest);
    if (localImageHolder != null)
      localImageHolder.fresh = false;
  }

  public static ImageCache getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new ImageCache(paramContext.getApplicationContext());
      ImageCache localImageCache = sInstance;
      return localImageCache;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private boolean loadCachedImage(ImageConsumer paramImageConsumer, ImageRequest paramImageRequest, boolean paramBoolean)
  {
    boolean bool = false;
    ImageHolder localImageHolder = (ImageHolder)this.mImageHolderCache.get(paramImageRequest);
    if ((localImageHolder != null) && (localImageHolder.fresh) && (localImageHolder.complete) && (localImageHolder.image == null) && (localImageHolder.bytes != null) && (localImageHolder.bytes.length > 4000) && (this.mImageCache.get(paramImageRequest) == null))
      localImageHolder.decodeInBackground = true;
    while (true)
    {
      return bool;
      if (localImageHolder != null)
        break;
      bool = false;
      if (paramBoolean)
      {
        paramImageConsumer.setBitmap(null, true);
        bool = false;
      }
    }
    this.mImageHolderCache.put(paramImageRequest, localImageHolder);
    if (this.mImageHolderCache.get(paramImageRequest) == null)
    {
      localImageHolder = new ImageHolder(null, true);
      this.mImageHolderCache.put(paramImageRequest, localImageHolder);
    }
    if (localImageHolder.bytes == null)
    {
      if (localImageHolder.complete)
      {
        paramImageConsumer.setBitmap(null, false);
        if (ImageLoadingMetrics.areImageLoadingMetricsEnabled())
          ImageLoadingMetrics.recordImageDelivered(paramImageRequest.getUriForLogging(), 0, 0);
        notifyRequestComplete(paramImageRequest);
      }
      while (true)
      {
        bool = localImageHolder.fresh;
        break;
        paramImageConsumer.setBitmap(null, true);
      }
    }
    decodeImage(paramImageRequest, localImageHolder);
    Object localObject = localImageHolder.image;
    int i;
    if ((localObject instanceof Bitmap))
    {
      paramImageConsumer.setBitmap((Bitmap)localObject, false);
      if (ImageLoadingMetrics.areImageLoadingMetricsEnabled())
      {
        Bitmap localBitmap = (Bitmap)localObject;
        String str = paramImageRequest.getUriForLogging();
        if (localImageHolder.bytes != null)
          break label315;
        i = 0;
        label283: ImageLoadingMetrics.recordImageDelivered(str, i, localBitmap.getByteCount());
      }
    }
    label315: 
    do
      while (true)
      {
        notifyRequestComplete(paramImageRequest);
        localImageHolder.image = null;
        bool = localImageHolder.fresh;
        break;
        i = localImageHolder.bytes.length;
        break label283;
        if (((localObject instanceof Drawable)) && ((paramImageConsumer instanceof DrawableConsumer)))
        {
          ((DrawableConsumer)paramImageConsumer).setDrawable((Drawable)localObject, false);
        }
        else if ((localObject instanceof GifDrawable))
        {
          paramImageConsumer.setBitmap(null, false);
        }
        else
        {
          if (localObject != FAILED_IMAGE)
            break label397;
          paramImageConsumer.setBitmap(null, false);
        }
      }
    while (localObject == null);
    label397: throw new UnsupportedOperationException("Cannot handle drawables of type " + localObject.getClass());
  }

  private void loadImage(ImageConsumer paramImageConsumer, ImageRequest paramImageRequest, boolean paramBoolean)
  {
    if (ImageLoadingMetrics.areImageLoadingMetricsEnabled())
      ImageLoadingMetrics.recordLoadImageRequest(paramImageRequest.getUriForLogging());
    if (paramImageRequest.isEmpty())
    {
      paramImageConsumer.setBitmap(null, false);
      notifyRequestComplete(paramImageRequest);
    }
    while (true)
    {
      return;
      this.mPendingRequests.remove(paramImageConsumer);
      if (loadCachedImage(paramImageConsumer, paramImageRequest, paramBoolean))
      {
        this.mPendingRequests.remove(paramImageConsumer);
      }
      else
      {
        this.mPendingRequests.put(paramImageConsumer, paramImageRequest);
        if (!this.mPaused)
          requestLoading();
      }
    }
  }

  private static void notifyRequestComplete(ImageRequest paramImageRequest)
  {
    Iterator localIterator = mRequestCompleteListeners.iterator();
    while (localIterator.hasNext())
      localIterator.next();
  }

  public static void registerAvatarChangeListener(OnAvatarChangeListener paramOnAvatarChangeListener)
  {
    mAvatarListeners.add(paramOnAvatarChangeListener);
  }

  public static void registerMediaImageChangeListener(OnMediaImageChangeListener paramOnMediaImageChangeListener)
  {
    mMediaImageListeners.add(paramOnMediaImageChangeListener);
  }

  private void requestLoading()
  {
    if (!this.mLoadingRequested)
    {
      this.mLoadingRequested = true;
      this.mMainThreadHandler.sendEmptyMessage(1);
    }
  }

  public static void unregisterAvatarChangeListener(OnAvatarChangeListener paramOnAvatarChangeListener)
  {
    mAvatarListeners.remove(paramOnAvatarChangeListener);
  }

  public static void unregisterMediaImageChangeListener(OnMediaImageChangeListener paramOnMediaImageChangeListener)
  {
    mMediaImageListeners.remove(paramOnMediaImageChangeListener);
  }

  public final void cancel(ImageConsumer paramImageConsumer)
  {
    this.mPendingRequests.remove(paramImageConsumer);
  }

  public final void clear()
  {
    this.mImageHolderCache.evictAll();
    this.mImageCache.evictAll();
    this.mPendingRequests.clear();
  }

  public final void clearFailedRequests()
  {
    Iterator localIterator = this.mImageHolderCache.snapshot().keySet().iterator();
    while (localIterator.hasNext())
    {
      ImageRequest localImageRequest = (ImageRequest)localIterator.next();
      ImageHolder localImageHolder = (ImageHolder)this.mImageHolderCache.get(localImageRequest);
      if ((localImageHolder != null) && (localImageHolder.fresh) && (localImageHolder.bytes == null))
        localImageHolder.fresh = false;
    }
  }

  public final boolean handleMessage(Message paramMessage)
  {
    boolean bool;
    switch (paramMessage.what)
    {
    default:
      bool = false;
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return bool;
      this.mLoadingRequested = false;
      if (!this.mPaused)
      {
        ensureLoaderThread();
        this.mLoaderThread.requestLoading();
      }
      bool = true;
      continue;
      if (!this.mPaused)
      {
        Iterator localIterator4 = this.mPendingRequests.keySet().iterator();
        while (localIterator4.hasNext())
        {
          ImageConsumer localImageConsumer = (ImageConsumer)localIterator4.next();
          ImageRequest localImageRequest2 = (ImageRequest)this.mPendingRequests.get(localImageConsumer);
          if ((localImageRequest2 != null) && (loadCachedImage(localImageConsumer, localImageRequest2, false)))
            localIterator4.remove();
        }
        clearTemporaryImageReferences();
        this.mBackgroundThreadBitmapCount = 0;
        if (!this.mPendingRequests.isEmpty())
          requestLoading();
      }
      bool = true;
      continue;
      String str = (String)paramMessage.obj;
      evictImage(new AvatarRequest(str, 0));
      evictImage(new AvatarRequest(str, 0, true));
      evictImage(new AvatarRequest(str, 1));
      evictImage(new AvatarRequest(str, 1, true));
      evictImage(new AvatarRequest(str, 2));
      evictImage(new AvatarRequest(str, 2, true));
      Iterator localIterator3 = mAvatarListeners.iterator();
      while (localIterator3.hasNext())
        ((OnAvatarChangeListener)localIterator3.next()).onAvatarChanged(str);
      bool = true;
      continue;
      MediaImageChangeNotification localMediaImageChangeNotification = (MediaImageChangeNotification)paramMessage.obj;
      Iterator localIterator1 = this.mImageHolderCache.snapshot().keySet().iterator();
      while (localIterator1.hasNext())
      {
        ImageRequest localImageRequest1 = (ImageRequest)localIterator1.next();
        if ((!localImageRequest1.equals(localMediaImageChangeNotification.request)) && ((localImageRequest1 instanceof MediaImageRequest)) && (MediaImageRequest.areCanonicallyEqual((MediaImageRequest)localImageRequest1, localMediaImageChangeNotification.request)))
          evictImage(localImageRequest1);
      }
      Iterator localIterator2 = mMediaImageListeners.iterator();
      while (localIterator2.hasNext())
        ((OnMediaImageChangeListener)localIterator2.next()).onMediaImageChanged(localMediaImageChangeNotification.request.getUrl());
      bool = true;
    }
  }

  public final void loadImage(ImageConsumer paramImageConsumer, ImageRequest paramImageRequest)
  {
    loadImage(paramImageConsumer, paramImageRequest, true);
  }

  public final void notifyAvatarChange(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return;
      ensureLoaderThread();
      this.mLoaderThread.notifyAvatarChange(paramString);
    }
  }

  public final void notifyMediaImageChange(CachedImageRequest paramCachedImageRequest, byte[] paramArrayOfByte)
  {
    ensureLoaderThread();
    if ((paramCachedImageRequest instanceof MediaImageRequest))
    {
      MediaImageChangeNotification localMediaImageChangeNotification = new MediaImageChangeNotification((byte)0);
      localMediaImageChangeNotification.request = ((MediaImageRequest)paramCachedImageRequest);
      localMediaImageChangeNotification.imageBytes = paramArrayOfByte;
      this.mLoaderThread.notifyMediaImageChange(localMediaImageChangeNotification);
    }
    while (true)
    {
      return;
      if ((paramCachedImageRequest instanceof AvatarImageRequest))
      {
        ensureLoaderThread();
        this.mLoaderThread.notifyAvatarChange(((AvatarImageRequest)paramCachedImageRequest).getGaiaId());
      }
    }
  }

  public final void pause()
  {
    this.mPaused = true;
  }

  public final void refreshImage(ImageConsumer paramImageConsumer, ImageRequest paramImageRequest)
  {
    loadImage(paramImageConsumer, paramImageRequest, false);
  }

  public final void resume()
  {
    this.mPaused = false;
    if (!this.mPendingRequests.isEmpty())
      requestLoading();
  }

  public static abstract interface DrawableConsumer extends ImageCache.ImageConsumer
  {
    public abstract void setDrawable(Drawable paramDrawable, boolean paramBoolean);
  }

  public static abstract interface ImageConsumer
  {
    public abstract void setBitmap(Bitmap paramBitmap, boolean paramBoolean);
  }

  private static final class ImageHolder
  {
    final byte[] bytes;
    final boolean complete;
    boolean decodeInBackground;
    volatile boolean fresh;
    Object image;

    public ImageHolder(byte[] paramArrayOfByte, boolean paramBoolean)
    {
      this.bytes = paramArrayOfByte;
      this.fresh = true;
      this.complete = paramBoolean;
    }
  }

  private final class LoaderThread extends HandlerThread
    implements Handler.Callback
  {
    private Handler mLoaderThreadHandler;
    private List<AvatarRequest> mPreloadRequests = new ArrayList();
    private int mPreloadStatus = 0;
    private final HashSet<ImageRequest> mRequests = new HashSet();

    public LoaderThread()
    {
      super(1);
    }

    private void continuePreloading()
    {
      if (this.mPreloadStatus == 2);
      while (true)
      {
        return;
        ensureHandler();
        if (!this.mLoaderThreadHandler.hasMessages(2))
          this.mLoaderThreadHandler.sendEmptyMessageDelayed(1, 50L);
      }
    }

    private void ensureHandler()
    {
      if (this.mLoaderThreadHandler == null)
        this.mLoaderThreadHandler = new Handler(getLooper(), this);
    }

    private void loadImagesFromDatabase(boolean paramBoolean)
    {
      if (this.mRequests.size() == 0);
      while (true)
      {
        return;
        if ((!paramBoolean) && (this.mPreloadStatus == 1))
        {
          this.mPreloadRequests.removeAll(this.mRequests);
          if (this.mPreloadRequests.isEmpty())
            this.mPreloadStatus = 2;
        }
        ArrayList localArrayList1 = null;
        ArrayList localArrayList2 = null;
        ArrayList localArrayList3 = null;
        Iterator localIterator1 = this.mRequests.iterator();
        while (localIterator1.hasNext())
        {
          ImageRequest localImageRequest2 = (ImageRequest)localIterator1.next();
          if ((localImageRequest2 instanceof AvatarRequest))
          {
            if (localArrayList1 == null)
              localArrayList1 = new ArrayList();
            localArrayList1.add((AvatarRequest)localImageRequest2);
          }
          else if ((localImageRequest2 instanceof MediaImageRequest))
          {
            if (localArrayList2 == null)
              localArrayList2 = new ArrayList();
            localArrayList2.add((MediaImageRequest)localImageRequest2);
          }
          else
          {
            if (localArrayList3 == null)
              localArrayList3 = new ArrayList();
            localArrayList3.add(localImageRequest2);
          }
        }
        if (localArrayList2 != null)
        {
          Iterator localIterator4 = EsMediaCache.loadMedia(ImageCache.this.mContext, localArrayList2).entrySet().iterator();
          while (localIterator4.hasNext())
          {
            Map.Entry localEntry2 = (Map.Entry)localIterator4.next();
            CachedImageRequest localCachedImageRequest = (CachedImageRequest)localEntry2.getKey();
            ImageCache.access$400(ImageCache.this, localCachedImageRequest, (byte[])localEntry2.getValue(), true, paramBoolean);
            this.mRequests.remove(localCachedImageRequest);
          }
        }
        if (localArrayList1 != null)
        {
          Iterator localIterator3 = EsAvatarData.loadAvatars(ImageCache.this.mContext, localArrayList1).entrySet().iterator();
          while (localIterator3.hasNext())
          {
            Map.Entry localEntry1 = (Map.Entry)localIterator3.next();
            AvatarRequest localAvatarRequest = (AvatarRequest)localEntry1.getKey();
            ImageCache.access$400(ImageCache.this, localAvatarRequest, (byte[])localEntry1.getValue(), true, paramBoolean);
            this.mRequests.remove(localAvatarRequest);
          }
        }
        Iterator localIterator2 = this.mRequests.iterator();
        while (localIterator2.hasNext())
        {
          ImageRequest localImageRequest1 = (ImageRequest)localIterator2.next();
          ImageCache.access$400(ImageCache.this, localImageRequest1, null, false, paramBoolean);
        }
        ImageCache.this.mMainThreadHandler.sendEmptyMessage(2);
      }
    }

    private void preloadAvatarsInBackground()
    {
      if (this.mPreloadStatus == 2);
      while (true)
      {
        return;
        if (this.mPreloadStatus == 0)
        {
          if (this.mPreloadRequests.isEmpty());
          for (this.mPreloadStatus = 2; ; this.mPreloadStatus = 1)
          {
            continuePreloading();
            break;
          }
        }
        if (ImageCache.this.mImageHolderCache.size() > ImageCache.this.mImageHolderCacheRedZoneBytes)
        {
          this.mPreloadStatus = 2;
        }
        else
        {
          this.mRequests.clear();
          int i = 0;
          int j = this.mPreloadRequests.size();
          while ((j > 0) && (this.mRequests.size() < 25))
          {
            j--;
            AvatarRequest localAvatarRequest = (AvatarRequest)this.mPreloadRequests.get(j);
            this.mPreloadRequests.remove(j);
            if (ImageCache.this.mImageHolderCache.get(localAvatarRequest) == null)
            {
              this.mRequests.add(localAvatarRequest);
              i++;
            }
          }
          loadImagesFromDatabase(true);
          if (j == 0)
            this.mPreloadStatus = 2;
          if (EsLog.isLoggable("ImageCache", 4))
            Log.v("ImageCache", "Preloaded " + i + " avatars. Cache size (bytes): " + ImageCache.this.mImageHolderCache.size());
          continuePreloading();
        }
      }
    }

    public final boolean handleMessage(Message paramMessage)
    {
      try
      {
        switch (paramMessage.what)
        {
        case 0:
          List localList = (List)paramMessage.obj;
          this.mPreloadRequests.clear();
          this.mPreloadRequests.addAll(localList);
          this.mPreloadStatus = 0;
          preloadAvatarsInBackground();
        case 1:
        case 2:
        case 3:
        case 4:
        }
      }
      catch (Throwable localThrowable)
      {
        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), localThrowable);
        bool = false;
        break label363;
      }
      preloadAvatarsInBackground();
      break label361;
      ImageCache.access$500(ImageCache.this, this.mRequests);
      if (!this.mRequests.isEmpty())
      {
        if (!this.mRequests.isEmpty())
        {
          Iterator localIterator = this.mRequests.iterator();
          while (localIterator.hasNext())
          {
            ImageRequest localImageRequest = (ImageRequest)localIterator.next();
            ImageCache.ImageHolder localImageHolder = (ImageCache.ImageHolder)ImageCache.this.mImageHolderCache.get(localImageRequest);
            if (localImageHolder != null)
            {
              ImageCache.access$400(ImageCache.this, localImageRequest, localImageHolder.bytes, true, false);
              localIterator.remove();
            }
          }
          ImageCache.this.mMainThreadHandler.sendEmptyMessage(2);
        }
      }
      else
      {
        ImageCache.access$600(ImageCache.this, this.mRequests);
        if (!this.mRequests.isEmpty())
          loadImagesFromDatabase(false);
        continuePreloading();
        break label361;
        String str = (String)paramMessage.obj;
        Message localMessage2 = ImageCache.this.mMainThreadHandler.obtainMessage(3, str);
        ImageCache.this.mMainThreadHandler.sendMessage(localMessage2);
        break label361;
        ImageCache.MediaImageChangeNotification localMediaImageChangeNotification = (ImageCache.MediaImageChangeNotification)paramMessage.obj;
        ImageCache.access$400(ImageCache.this, localMediaImageChangeNotification.request, localMediaImageChangeNotification.imageBytes, true, false);
        Message localMessage1 = ImageCache.this.mMainThreadHandler.obtainMessage(4, localMediaImageChangeNotification);
        ImageCache.this.mMainThreadHandler.sendMessage(localMessage1);
      }
      label361: boolean bool = true;
      label363: return bool;
    }

    public final void notifyAvatarChange(String paramString)
    {
      ensureHandler();
      Message localMessage = this.mLoaderThreadHandler.obtainMessage(3, paramString);
      this.mLoaderThreadHandler.sendMessage(localMessage);
    }

    public final void notifyMediaImageChange(ImageCache.MediaImageChangeNotification paramMediaImageChangeNotification)
    {
      ensureHandler();
      Message localMessage = this.mLoaderThreadHandler.obtainMessage(4, paramMediaImageChangeNotification);
      this.mLoaderThreadHandler.sendMessage(localMessage);
    }

    public final void requestLoading()
    {
      ensureHandler();
      this.mLoaderThreadHandler.removeMessages(1);
      this.mLoaderThreadHandler.sendEmptyMessage(2);
    }
  }

  private static final class MediaImageChangeNotification
  {
    byte[] imageBytes;
    MediaImageRequest request;
  }

  public static abstract interface OnAvatarChangeListener
  {
    public abstract void onAvatarChanged(String paramString);
  }

  public static abstract interface OnMediaImageChangeListener
  {
    public abstract void onMediaImageChanged(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ImageCache
 * JD-Core Version:    0.6.2
 */