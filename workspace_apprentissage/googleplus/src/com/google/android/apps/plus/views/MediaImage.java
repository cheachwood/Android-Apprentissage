package com.google.android.apps.plus.views;

import android.graphics.Bitmap;
import android.view.View;
import com.google.android.apps.plus.content.ImageRequest;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.util.ImageUtils;

public final class MediaImage
  implements ImageCache.ImageConsumer
{
  private static ImageCache sImageCache;
  private Bitmap mBitmap;
  private boolean mInvalidated;
  private final int mPostHeight;
  private final int mPostWidth;
  private final ImageRequest mRequest;
  private final View mView;

  public MediaImage(View paramView, ImageRequest paramImageRequest)
  {
    this(paramView, paramImageRequest, 0, 0);
  }

  private MediaImage(View paramView, ImageRequest paramImageRequest, int paramInt1, int paramInt2)
  {
    this.mView = paramView;
    this.mRequest = paramImageRequest;
    if (sImageCache == null)
      sImageCache = ImageCache.getInstance(paramView.getContext());
    this.mPostWidth = 0;
    this.mPostHeight = 0;
  }

  public final Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  public final void invalidate()
  {
    this.mInvalidated = true;
  }

  public final void load()
  {
    if (this.mRequest != null)
      sImageCache.loadImage(this, this.mRequest);
  }

  public final void refreshIfInvalidated()
  {
    if (this.mInvalidated)
    {
      this.mInvalidated = false;
      if (this.mRequest != null)
        sImageCache.refreshImage(this, this.mRequest);
    }
  }

  public final void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if ((paramBitmap != null) && (this.mPostWidth != 0) && (this.mPostHeight != 0))
    {
      this.mBitmap = ImageUtils.resizeAndCropBitmap(paramBitmap, this.mPostWidth, this.mPostHeight);
      if (this.mBitmap != null);
    }
    else
    {
      this.mBitmap = paramBitmap;
    }
    this.mView.invalidate();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.MediaImage
 * JD-Core Version:    0.6.2
 */