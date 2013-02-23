package com.google.android.apps.plus.views;

import android.graphics.Bitmap;
import android.view.View;
import com.google.android.apps.plus.content.ImageRequest;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;

public final class RemoteImage
  implements ImageCache.ImageConsumer
{
  private static ImageCache sImageCache;
  private Bitmap mBitmap;
  private boolean mInvalidated;
  private boolean mLoaded;
  private final ImageRequest mRequest;
  private final View mView;

  public RemoteImage(View paramView, ImageRequest paramImageRequest)
  {
    this.mView = paramView;
    this.mRequest = paramImageRequest;
    if (sImageCache == null)
      sImageCache = ImageCache.getInstance(paramView.getContext());
  }

  public final Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  public final ImageRequest getRequest()
  {
    return this.mRequest;
  }

  public final void invalidate()
  {
    this.mInvalidated = true;
    this.mView.invalidate();
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
    this.mBitmap = paramBitmap;
    if (!paramBoolean);
    for (boolean bool = true; ; bool = false)
    {
      this.mLoaded = bool;
      this.mView.invalidate();
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.RemoteImage
 * JD-Core Version:    0.6.2
 */