package com.google.android.apps.plus.service;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsMediaCache;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.GifImage;
import com.google.android.apps.plus.util.ImageUtils;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public final class ImageResourceManager extends ResourceManager
{
  private static ImageResourceManager sInstance;
  private final HashMap<Object, Resource> mActiveResources = new HashMap();
  private AvatarIdentifier mAvatarIdPool = null;
  private final boolean mBitmapPackingEnabled;
  private final LruCache<ImageResourceIdentifier, ImageResource> mImageCache;
  private int mLargeImageThreshold;
  private MediaIdentifier mMediaIdPool = null;
  private ResourceDownloader mResourceDownloader;

  private ImageResourceManager(Context paramContext)
  {
    super(paramContext);
    Resources localResources = paramContext.getApplicationContext().getResources();
    int i;
    if (EsApplication.sMemoryClass >= 48)
    {
      i = Math.max(localResources.getInteger(R.integer.config_image_cache_max_bytes_decoded_large), 1024 * (1024 * (EsApplication.sMemoryClass / 4)));
      this.mLargeImageThreshold = (i / 3);
      this.mImageCache = new LruCache(i)
      {
      };
      if (Build.VERSION.SDK_INT >= 11)
        break label115;
    }
    label115: for (boolean bool = true; ; bool = false)
    {
      this.mBitmapPackingEnabled = bool;
      return;
      i = localResources.getInteger(R.integer.config_image_cache_max_bytes_decoded_small);
      break;
    }
  }

  static String buildShortFileName(String paramString)
  {
    long l = 1125899906842597L;
    int i = paramString.length();
    for (int j = 0; j < i; j++)
      l = 31L * l + paramString.charAt(j);
    return Long.toHexString(0xFFFFFFFF & l >> 4);
  }

  public static ImageResourceManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new ImageResourceManager(paramContext.getApplicationContext());
      ImageResourceManager localImageResourceManager = sInstance;
      return localImageResourceManager;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private Resource getMedia(MediaRef paramMediaRef, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ResourceConsumer paramResourceConsumer)
  {
    MediaIdentifier localMediaIdentifier;
    MediaResource localMediaResource;
    if (this.mMediaIdPool != null)
    {
      localMediaIdentifier = this.mMediaIdPool;
      this.mMediaIdPool = this.mMediaIdPool.getNextInPool();
      localMediaIdentifier.setNextInPool(null);
      localMediaIdentifier.init(paramInt4, paramMediaRef, paramInt1, paramInt2, paramInt3);
      localMediaResource = (MediaResource)this.mActiveResources.get(localMediaIdentifier);
      if (localMediaResource != null)
        break label201;
      localMediaResource = (MediaResource)this.mImageCache.get(localMediaIdentifier);
      if (localMediaResource == null)
        break label156;
      if (localMediaResource.isDebugLogEnabled())
        localMediaResource.logDebug("getMedia [CACHED]: " + localMediaIdentifier);
      this.mImageCache.remove(localMediaIdentifier);
      label120: this.mActiveResources.put(localMediaIdentifier, localMediaResource);
    }
    while (true)
    {
      localMediaResource.register(paramResourceConsumer);
      return localMediaResource;
      localMediaIdentifier = new MediaIdentifier((byte)0);
      break;
      label156: localMediaResource = new MediaResource(this, localMediaIdentifier);
      if (!localMediaResource.isDebugLogEnabled())
        break label120;
      localMediaResource.logDebug("getMedia [NOT CACHED]: " + localMediaIdentifier);
      break label120;
      label201: if (localMediaResource.isDebugLogEnabled())
        localMediaResource.logDebug("getMedia [ACTIVE]: " + localMediaIdentifier);
      localMediaIdentifier.setNextInPool(this.mMediaIdPool);
      this.mMediaIdPool = localMediaIdentifier;
    }
  }

  public final Resource getAvatar(String paramString, int paramInt, boolean paramBoolean, ResourceConsumer paramResourceConsumer)
  {
    AvatarIdentifier localAvatarIdentifier;
    Object localObject;
    if (this.mAvatarIdPool != null)
    {
      localAvatarIdentifier = this.mAvatarIdPool;
      this.mAvatarIdPool = this.mAvatarIdPool.getNextInPool();
      localAvatarIdentifier.setNextInPool(null);
      localAvatarIdentifier.init(paramString, paramInt, true);
      localObject = (ImageResource)this.mActiveResources.get(localAvatarIdentifier);
      if (localObject != null)
        break label209;
      localObject = (ImageResource)this.mImageCache.get(localAvatarIdentifier);
      if (localObject == null)
        break label152;
      if (((ImageResource)localObject).isDebugLogEnabled())
        ((ImageResource)localObject).logDebug("getAvatar [CACHED]: " + localAvatarIdentifier);
      this.mImageCache.remove(localAvatarIdentifier);
      label116: this.mActiveResources.put(localAvatarIdentifier, localObject);
    }
    while (true)
    {
      ((ImageResource)localObject).register(paramResourceConsumer);
      return localObject;
      localAvatarIdentifier = new AvatarIdentifier((byte)0);
      break;
      label152: localObject = new UrlImageResource(this, localAvatarIdentifier, ImageUtils.getCroppedAndResizedUrl(EsAvatarData.getAvatarSizeInPx(getContext(), paramInt), paramString));
      if (!((ImageResource)localObject).isDebugLogEnabled())
        break label116;
      ((ImageResource)localObject).logDebug("getAvatar [NOT CACHED]: " + localAvatarIdentifier);
      break label116;
      label209: if (((ImageResource)localObject).isDebugLogEnabled())
        ((ImageResource)localObject).logDebug("getAvatar [ACTIVE]: " + localAvatarIdentifier);
      localAvatarIdentifier.setNextInPool(this.mAvatarIdPool);
      this.mAvatarIdPool = localAvatarIdentifier;
    }
  }

  public final Resource getMedia(MediaRef paramMediaRef, int paramInt1, int paramInt2, int paramInt3, ResourceConsumer paramResourceConsumer)
  {
    return getMedia(paramMediaRef, 0, paramInt1, paramInt2, paramInt3, paramResourceConsumer);
  }

  public final Resource getMedia(MediaRef paramMediaRef, int paramInt1, int paramInt2, ResourceConsumer paramResourceConsumer)
  {
    return getMedia(paramMediaRef, paramInt1, 0, 0, paramInt2, paramResourceConsumer);
  }

  public final Resource getMedia(MediaRef paramMediaRef, int paramInt, ResourceConsumer paramResourceConsumer)
  {
    return getMedia(paramMediaRef, paramInt, 0, paramResourceConsumer);
  }

  protected final ResourceDownloader getResourceDownloader()
  {
    if (this.mResourceDownloader == null)
      this.mResourceDownloader = new ResourceDownloader(getContext(), new Handler());
    return this.mResourceDownloader;
  }

  public final void onEnvironmentChanged()
  {
    if (this.mActiveResources.size() == 0);
    while (true)
    {
      return;
      NetworkInfo localNetworkInfo = ((ConnectivityManager)getContext().getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
      {
        Iterator localIterator = this.mActiveResources.values().iterator();
        while (localIterator.hasNext())
        {
          Resource localResource = (Resource)localIterator.next();
          if (localResource.getStatus() == 5)
          {
            deliverResourceStatus(localResource, 2);
            loadResource(localResource);
          }
        }
      }
    }
  }

  public final void onFirstConsumerRegistered(Resource paramResource)
  {
    if (!this.mActiveResources.containsKey(paramResource.getIdentifier()))
      throw new IllegalStateException("Resource is not active: " + paramResource.getIdentifier());
    ImageResource localImageResource = (ImageResource)paramResource;
    switch (localImageResource.getStatus())
    {
    case 2:
    case 3:
    case 4:
    case 6:
    default:
      throw new IllegalStateException("Illegal resource state: " + paramResource.getStatusAsString());
    case 9:
      if (this.mBitmapPackingEnabled)
        localImageResource.unpack();
      break;
    case 1:
    case 7:
    case 0:
    case 5:
    case 8:
    }
    while (true)
    {
      return;
      paramResource.mStatus = 1;
      continue;
      if (localImageResource.isDebugLogEnabled())
        localImageResource.logDebug("Requesting image load: " + localImageResource.mId);
      localImageResource.mStatus = 2;
      loadResource(paramResource);
    }
  }

  public final void onLastConsumerUnregistered(Resource paramResource)
  {
    ImageResource localImageResource = (ImageResource)paramResource;
    ImageResourceIdentifier localImageResourceIdentifier = (ImageResourceIdentifier)localImageResource.mId;
    if (localImageResource.isDebugLogEnabled())
      localImageResource.logDebug("Deactivating image resource: " + localImageResourceIdentifier);
    int i = localImageResource.getStatus();
    if ((i == 2) || (i == 3))
    {
      localImageResource.mStatus = 8;
      if (this.mResourceDownloader != null)
        this.mResourceDownloader.cancelDownload(localImageResource);
    }
    this.mActiveResources.remove(localImageResourceIdentifier);
    int j;
    if ((i == 1) && ((0x2 & localImageResourceIdentifier.mFlags) == 0))
    {
      if (((localImageResource.mResource instanceof Bitmap)) && (this.mBitmapPackingEnabled))
        break label180;
      j = 1;
      if (j != 0)
      {
        int k = localImageResource.getSizeInBytes();
        if ((k != -1) && (k < this.mLargeImageThreshold))
          break label205;
      }
    }
    label180: label205: for (int m = 1; ; m = 0)
    {
      if (m == 0)
      {
        if (this.mBitmapPackingEnabled)
          localImageResource.pack();
        this.mImageCache.put(localImageResourceIdentifier, localImageResource);
      }
      return;
      if (((Bitmap)localImageResource.mResource).getConfig() != null)
      {
        j = 1;
        break;
      }
      j = 0;
      break;
    }
  }

  public final void verifyEmpty()
  {
    if (!this.mActiveResources.isEmpty())
    {
      Log.i("ImageResourceManager", "ImageResourceManager contains " + this.mActiveResources.size() + " resources");
      Iterator localIterator = this.mActiveResources.values().iterator();
      while (localIterator.hasNext())
      {
        Resource localResource = (Resource)localIterator.next();
        EsLog.writeToLog(4, "ImageResourceManager", localResource.toString() + "\n");
      }
    }
  }

  private final class AvatarIdentifier extends ImageResourceManager.UrlImageResourceIdentifier
  {
    private int mHashCode;
    private AvatarIdentifier mNextInPool;
    private boolean mRounded;
    private int mSizeCategory;

    private AvatarIdentifier()
    {
      super((byte)0);
    }

    public final boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (paramObject == this);
      while (true)
      {
        return bool;
        if (!(paramObject instanceof AvatarIdentifier))
        {
          bool = false;
        }
        else
        {
          AvatarIdentifier localAvatarIdentifier = (AvatarIdentifier)paramObject;
          if ((this.mSizeCategory != localAvatarIdentifier.mSizeCategory) || (this.mRounded != localAvatarIdentifier.mRounded) || (!this.mUrl.equals(localAvatarIdentifier.mUrl)))
            bool = false;
        }
      }
    }

    public final AvatarIdentifier getNextInPool()
    {
      return this.mNextInPool;
    }

    public final String getShortFileName()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(ImageResourceManager.buildShortFileName(this.mUrl));
      switch (this.mSizeCategory)
      {
      default:
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        return localStringBuilder.toString();
        localStringBuilder.append("-at");
        continue;
        localStringBuilder.append("-as");
        continue;
        localStringBuilder.append("-am");
      }
    }

    public final int hashCode()
    {
      if (this.mHashCode == 0)
      {
        this.mHashCode = (this.mUrl.hashCode() ^ this.mSizeCategory);
        if (this.mRounded)
          this.mHashCode = (1 + this.mHashCode);
      }
      return this.mHashCode;
    }

    public final void init(String paramString, int paramInt, boolean paramBoolean)
    {
      super.init(0, paramString);
      this.mSizeCategory = paramInt;
      this.mRounded = paramBoolean;
      this.mHashCode = 0;
    }

    public final void setNextInPool(AvatarIdentifier paramAvatarIdentifier)
    {
      this.mNextInPool = paramAvatarIdentifier;
    }

    public final String toString()
    {
      int i = this.mSizeCategory;
      String str1 = null;
      StringBuilder localStringBuilder;
      switch (i)
      {
      default:
        localStringBuilder = new StringBuilder("{").append(this.mUrl).append(" (").append(str1).append(")");
        if (!this.mRounded)
          break;
      case 0:
      case 1:
      case 2:
      }
      for (String str2 = " (rounded)"; ; str2 = "")
      {
        return str2 + "}";
        str1 = "tiny";
        break;
        str1 = "small";
        break;
        str1 = "medium";
        break;
      }
    }
  }

  private static abstract class ImageResource extends Resource
  {
    protected final String mCacheDir;
    protected final String mCacheFile;

    protected ImageResource(ImageResourceManager paramImageResourceManager, ImageResourceManager.ImageResourceIdentifier paramImageResourceIdentifier)
    {
      super(paramImageResourceIdentifier);
      this.mCacheFile = paramImageResourceIdentifier.getShortFileName();
      this.mCacheDir = this.mCacheFile.substring(0, 1);
    }

    public final void deliverData(byte[] paramArrayOfByte, boolean paramBoolean)
    {
      ImageResourceManager.ImageResourceIdentifier localImageResourceIdentifier = (ImageResourceManager.ImageResourceIdentifier)this.mId;
      boolean bool;
      if ((0x2 & localImageResourceIdentifier.mFlags) != 0)
      {
        bool = true;
        if (bool)
        {
          if (isDebugLogEnabled())
            logDebug("Saving image in file cache: " + this.mId);
          EsMediaCache.write(this.mManager.getContext(), this.mCacheDir, this.mCacheFile, paramArrayOfByte);
        }
        if ((this.mStatus == 2) || (this.mStatus == 3))
          break label154;
        if (isDebugLogEnabled())
          logDebug("Request no longer needed, not delivering: " + this.mId + ", status: " + getStatusAsString());
      }
      while (true)
      {
        return;
        if ((0x1 & localImageResourceIdentifier.mFlags) != 0)
        {
          bool = false;
          break;
        }
        bool = paramBoolean;
        break;
        label154: if ((0x2 & localImageResourceIdentifier.mFlags) != 0)
        {
          if (isDebugLogEnabled())
            logDebug("Image decoding disabled. Delivering null to consumers: " + this.mId);
          this.mManager.deliverResourceContent(this, 1, null);
        }
        else
        {
          try
          {
            if (((0x4 & localImageResourceIdentifier.mFlags) == 0) || (!GifImage.isGif(paramArrayOfByte)))
              break label288;
            this.mManager.deliverResourceContent(this, 1, new GifImage(paramArrayOfByte));
          }
          catch (OutOfMemoryError localOutOfMemoryError)
          {
            if (isDebugLogEnabled())
              logDebug("Out of memory while decoding image: " + this.mId);
            this.mManager.deliverResourceContent(this, 7, null);
          }
          continue;
          label288: Object localObject = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
          if ((localObject != null) && ((this.mId instanceof ImageResourceManager.AvatarIdentifier)) && (((ImageResourceManager.AvatarIdentifier)this.mId).mRounded))
          {
            Bitmap localBitmap = ImageUtils.getRoundedBitmap(this.mManager.getContext(), (Bitmap)localObject);
            if (localObject != localBitmap)
            {
              ((Bitmap)localObject).recycle();
              localObject = localBitmap;
            }
          }
          if (localObject != null)
          {
            if (isDebugLogEnabled())
              logDebug("Delivering image to consumers: " + this.mId);
            this.mManager.deliverResourceContent(this, 1, localObject);
          }
          else
          {
            if (isDebugLogEnabled())
              logDebug("Bad image; cannot decode: " + this.mId);
            this.mManager.deliverResourceStatus(this, 6);
          }
        }
      }
    }

    protected abstract void downloadResource();

    public final File getCacheFileName()
    {
      if ((0x1 & ((ImageResourceManager.ImageResourceIdentifier)this.mId).mFlags) != 0);
      for (File localFile = null; ; localFile = EsMediaCache.getMediaCacheFile(this.mManager.getContext(), this.mCacheDir, this.mCacheFile))
        return localFile;
    }

    public final int getSizeInBytes()
    {
      int i = -1;
      if (this.mResource == null);
      while (true)
      {
        return i;
        if ((this.mResource instanceof Bitmap))
        {
          Bitmap localBitmap = (Bitmap)this.mResource;
          i = localBitmap.getRowBytes() * localBitmap.getHeight();
        }
        else if ((this.mResource instanceof GifImage))
        {
          i = ((GifImage)this.mResource).getSizeEstimate();
        }
        else if ((this.mResource instanceof ImageResourceManager.PackedBitmap))
        {
          i = ((ImageResourceManager.PackedBitmap)this.mResource).sizeInBytes;
        }
      }
    }

    public final void load()
    {
      ImageResourceManager.ImageResourceIdentifier localImageResourceIdentifier = (ImageResourceManager.ImageResourceIdentifier)this.mId;
      if (((0x2 & localImageResourceIdentifier.mFlags) != 0) && (EsMediaCache.exists(this.mManager.getContext(), this.mCacheDir, this.mCacheFile)))
        this.mManager.deliverResourceContent(this, 1, null);
      while (true)
      {
        return;
        int i = 0x1 & localImageResourceIdentifier.mFlags;
        byte[] arrayOfByte = null;
        if (i == 0)
          arrayOfByte = EsMediaCache.read(this.mManager.getContext(), this.mCacheDir, this.mCacheFile);
        if (arrayOfByte != null)
        {
          if (isDebugLogEnabled())
            logDebug("Loaded image from file: " + this.mId);
          deliverData(arrayOfByte, false);
        }
        else
        {
          if (isDebugLogEnabled())
            logDebug("Requesting network download: " + this.mId);
          this.mManager.deliverResourceStatus(this, 3);
          downloadResource();
        }
      }
    }

    public final void pack()
    {
      Bitmap localBitmap;
      Bitmap.Config localConfig;
      if ((this.mStatus == 1) && ((this.mResource instanceof Bitmap)))
      {
        localBitmap = (Bitmap)this.mResource;
        localConfig = localBitmap.getConfig();
        if (localConfig != null)
          break label36;
      }
      while (true)
      {
        return;
        try
        {
          label36: ImageResourceManager.PackedBitmap localPackedBitmap = new ImageResourceManager.PackedBitmap((byte)0);
          localPackedBitmap.config = localConfig;
          localPackedBitmap.width = localBitmap.getWidth();
          localPackedBitmap.height = localBitmap.getHeight();
          localPackedBitmap.sizeInBytes = (localBitmap.getRowBytes() * localBitmap.getHeight());
          localPackedBitmap.buffer = ByteBuffer.allocate(localPackedBitmap.sizeInBytes);
          localBitmap.copyPixelsToBuffer(localPackedBitmap.buffer);
          localBitmap.recycle();
          this.mResource = localPackedBitmap;
          this.mStatus = 9;
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          this.mStatus = 7;
        }
      }
    }

    public final void recycle()
    {
      if ((this.mResource instanceof Bitmap))
        ((Bitmap)this.mResource).recycle();
      super.recycle();
    }

    public final void unpack()
    {
      ImageResourceManager.PackedBitmap localPackedBitmap;
      if ((this.mStatus == 9) && ((this.mResource instanceof ImageResourceManager.PackedBitmap)))
        localPackedBitmap = (ImageResourceManager.PackedBitmap)this.mResource;
      try
      {
        Bitmap localBitmap = Bitmap.createBitmap(localPackedBitmap.width, localPackedBitmap.height, localPackedBitmap.config);
        localPackedBitmap.buffer.rewind();
        localBitmap.copyPixelsFromBuffer(localPackedBitmap.buffer);
        this.mResource = localBitmap;
        this.mStatus = 1;
        return;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        while (true)
        {
          this.mResource = null;
          this.mStatus = 7;
        }
      }
    }
  }

  private static abstract class ImageResourceIdentifier extends Resource.ResourceIdentifier
  {
    protected int mFlags;

    public abstract String getShortFileName();

    public final void init(int paramInt)
    {
      this.mFlags = paramInt;
    }
  }

  private final class MediaIdentifier extends ImageResourceManager.ImageResourceIdentifier
  {
    private int mHashCode;
    private int mHeight;
    private MediaRef mMediaRef;
    private MediaIdentifier mNextInPool;
    private int mSizeCategory;
    private int mWidth;

    private MediaIdentifier()
    {
      super();
    }

    public final boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (paramObject == this);
      while (true)
      {
        return bool;
        if (!(paramObject instanceof MediaIdentifier))
        {
          bool = false;
        }
        else
        {
          MediaIdentifier localMediaIdentifier = (MediaIdentifier)paramObject;
          if ((this.mSizeCategory != localMediaIdentifier.mSizeCategory) || (this.mFlags != localMediaIdentifier.mFlags) || (!this.mMediaRef.equals(localMediaIdentifier.mMediaRef)) || (this.mWidth != localMediaIdentifier.mWidth) || (this.mHeight != localMediaIdentifier.mHeight))
            bool = false;
        }
      }
    }

    public final MediaIdentifier getNextInPool()
    {
      return this.mNextInPool;
    }

    public final String getShortFileName()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      if (this.mMediaRef.hasUrl())
      {
        localStringBuilder.append(ImageResourceManager.buildShortFileName(this.mMediaRef.getUrl()));
        switch (this.mSizeCategory)
        {
        case 1:
        default:
        case 0:
        case 2:
        case 3:
        case 4:
        case 5:
        }
      }
      while (true)
      {
        if ((0x4 & this.mFlags) != 0)
          localStringBuilder.append("-a");
        return localStringBuilder.toString();
        if (this.mMediaRef.hasLocalUri())
        {
          localStringBuilder.append(ImageResourceManager.buildShortFileName(this.mMediaRef.getLocalUri().toString()));
          break;
        }
        throw new IllegalStateException("A media ref should have a URI");
        localStringBuilder.append('-').append(this.mWidth).append('x').append(this.mHeight);
        continue;
        localStringBuilder.append("-t");
        continue;
        localStringBuilder.append("-b");
        continue;
        localStringBuilder.append("-p");
        continue;
        localStringBuilder.append("-l");
      }
    }

    public final int hashCode()
    {
      if (this.mHashCode == 0)
        this.mHashCode = (this.mMediaRef.hashCode() + this.mSizeCategory + this.mFlags);
      return this.mHashCode;
    }

    public final void init(int paramInt1, MediaRef paramMediaRef, int paramInt2, int paramInt3, int paramInt4)
    {
      init(paramInt1);
      this.mMediaRef = paramMediaRef;
      this.mSizeCategory = paramInt2;
      this.mWidth = paramInt3;
      this.mHeight = paramInt4;
      this.mHashCode = 0;
    }

    public final void setNextInPool(MediaIdentifier paramMediaIdentifier)
    {
      this.mNextInPool = paramMediaIdentifier;
    }

    public final String toString()
    {
      int i = this.mSizeCategory;
      String str1 = null;
      switch (i)
      {
      default:
      case 0:
      case 2:
      case 3:
      case 4:
      case 5:
      case 1:
      }
      while (true)
      {
        String str2 = "";
        if ((0x1 & this.mFlags) != 0)
          str2 = str2 + " no-disk-cache";
        return "{" + this.mMediaRef + " (" + str1 + ")" + str2 + "}";
        str1 = this.mWidth + "x" + this.mHeight;
        continue;
        str1 = "thumbnail";
        continue;
        str1 = "large";
        continue;
        str1 = "portrait";
        continue;
        str1 = "landscape";
        continue;
        str1 = "full";
      }
    }
  }

  private static final class MediaResource extends ImageResourceManager.ImageResource
  {
    public MediaResource(ImageResourceManager paramImageResourceManager, ImageResourceManager.MediaIdentifier paramMediaIdentifier)
    {
      super(paramMediaIdentifier);
    }

    protected final void downloadResource()
    {
      ImageResourceManager.MediaIdentifier localMediaIdentifier = (ImageResourceManager.MediaIdentifier)this.mId;
      int i = localMediaIdentifier.mSizeCategory;
      int j = localMediaIdentifier.mWidth;
      int k = localMediaIdentifier.mHeight;
      MediaRef localMediaRef = localMediaIdentifier.mMediaRef;
      if (localMediaRef.hasLocalUri())
      {
        Uri localUri = localMediaRef.getLocalUri();
        String str = localUri.getScheme();
        if ((str != null) && (!str.startsWith("http")))
        {
          ResourceDownloader.loadLocalResource(this.mManager.getContext(), this, localUri, i, j, k);
          return;
        }
      }
      ResourceDownloader localResourceDownloader = ((ImageResourceManager)this.mManager).getResourceDownloader();
      if ((0x4 & localMediaIdentifier.mFlags) != 0);
      for (boolean bool = true; ; bool = false)
      {
        localResourceDownloader.downloadResource(this, localMediaRef, i, j, k, bool);
        break;
      }
    }
  }

  private static final class PackedBitmap
  {
    public ByteBuffer buffer;
    public Bitmap.Config config;
    public int height;
    public int sizeInBytes;
    public int width;
  }

  private static final class UrlImageResource extends ImageResourceManager.ImageResource
  {
    private String mDownloadUrl;

    public UrlImageResource(ImageResourceManager paramImageResourceManager, ImageResourceManager.UrlImageResourceIdentifier paramUrlImageResourceIdentifier, String paramString)
    {
      super(paramUrlImageResourceIdentifier);
      this.mDownloadUrl = paramString;
    }

    protected final void downloadResource()
    {
      ((ImageResourceManager)this.mManager).getResourceDownloader().downloadResource(this, this.mDownloadUrl);
    }
  }

  private abstract class UrlImageResourceIdentifier extends ImageResourceManager.ImageResourceIdentifier
  {
    protected String mUrl;

    private UrlImageResourceIdentifier()
    {
      super();
    }

    protected final void init(int paramInt, String paramString)
    {
      super.init(0);
      this.mUrl = paramString;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ImageResourceManager
 * JD-Core Version:    0.6.2
 */