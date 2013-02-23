package com.google.android.apps.plus.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.google.android.apps.plus.content.ImageRequest;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.DrawableConsumer;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.service.ImageCache.OnMediaImageChangeListener;

public class EsImageView extends ImageView
  implements ImageCache.DrawableConsumer, ImageCache.ImageConsumer, ImageCache.OnMediaImageChangeListener, Recyclable
{
  private static Interpolator sAccelerateInterpolator;
  private static Interpolator sDecelerateInterpolator;
  private static ImageCache sImageCache;
  private int mDefaultResourceId;
  private Uri mDefaultResourceUri;
  private boolean mFadeIn;
  private boolean mInvalidated;
  private boolean mLayoutBlocked;
  private OnImageLoadedListener mListener;
  private boolean mLoaded;
  private ImageRequest mRequest;
  private long mRequestTime;
  private boolean mResizeable;

  public EsImageView(Context paramContext)
  {
    super(paramContext);
    if (sImageCache == null)
      sImageCache = ImageCache.getInstance(getContext());
  }

  public EsImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sImageCache == null)
      sImageCache = ImageCache.getInstance(getContext());
    updateDefaultResourceId(paramAttributeSet);
  }

  public EsImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sImageCache == null)
      sImageCache = ImageCache.getInstance(getContext());
    updateDefaultResourceId(paramAttributeSet);
  }

  private void onImageLoaded()
  {
    this.mLoaded = true;
    if ((System.currentTimeMillis() - this.mRequestTime > 100L) && (this.mFadeIn) && (Build.VERSION.SDK_INT >= 12))
    {
      if (sDecelerateInterpolator == null)
        sDecelerateInterpolator = new DecelerateInterpolator();
      setAlpha(0.01F);
      animate().alpha(1.0F).setDuration(500L).setInterpolator(sDecelerateInterpolator);
    }
    if (this.mListener != null)
      this.mListener.onImageLoaded$7ad36aad();
  }

  private void updateDefaultResourceId(AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      this.mDefaultResourceId = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
      this.mDefaultResourceUri = null;
    }
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerMediaImageChangeListener(this);
    this.mInvalidated = true;
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ImageCache.unregisterMediaImageChangeListener(this);
    this.mInvalidated = false;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mInvalidated)
    {
      this.mInvalidated = false;
      if (this.mRequest != null)
        sImageCache.refreshImage(this, this.mRequest);
    }
    super.onDraw(paramCanvas);
  }

  public final void onMediaImageChanged(String paramString)
  {
    if ((this.mRequest == null) || (!(this.mRequest instanceof MediaImageRequest)));
    while (true)
    {
      return;
      if (MediaImageRequest.areCanonicallyEqual((MediaImageRequest)this.mRequest, paramString))
      {
        this.mInvalidated = true;
        invalidate();
      }
    }
  }

  public void onRecycle()
  {
    setRequest(null);
    this.mListener = null;
    this.mLoaded = false;
  }

  public void requestLayout()
  {
    if ((this.mLayoutBlocked) && (!this.mResizeable))
      forceLayout();
    while (true)
    {
      return;
      super.requestLayout();
    }
  }

  public void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (!paramBoolean);
    try
    {
      this.mLayoutBlocked = true;
      setImageBitmap(paramBitmap);
      this.mLayoutBlocked = false;
      onImageLoaded();
      return;
    }
    finally
    {
      this.mLayoutBlocked = false;
    }
  }

  public void setDefaultImageUri(Uri paramUri)
  {
    this.mDefaultResourceUri = paramUri;
    this.mDefaultResourceId = 0;
    if ((!this.mLoaded) && (this.mDefaultResourceUri != null))
      setImageURI(this.mDefaultResourceUri);
  }

  public void setDrawable(Drawable paramDrawable, boolean paramBoolean)
  {
    if (!paramBoolean);
    try
    {
      this.mLayoutBlocked = true;
      setImageDrawable(paramDrawable);
      this.mLayoutBlocked = false;
      onImageLoaded();
      return;
    }
    finally
    {
      this.mLayoutBlocked = false;
    }
  }

  public void setFadeIn(boolean paramBoolean)
  {
    this.mFadeIn = paramBoolean;
  }

  public void setOnImageLoadedListener(OnImageLoadedListener paramOnImageLoadedListener)
  {
    this.mListener = paramOnImageLoadedListener;
    if (this.mLoaded)
      onImageLoaded();
  }

  public void setRequest(ImageRequest paramImageRequest)
  {
    this.mRequestTime = System.currentTimeMillis();
    if ((this.mRequest == null) || (!this.mRequest.equals(paramImageRequest)))
    {
      this.mRequest = paramImageRequest;
      this.mInvalidated = false;
      if (this.mRequest == null)
        break label54;
      sImageCache.loadImage(this, this.mRequest);
    }
    while (true)
    {
      return;
      label54: sImageCache.cancel(this);
      if (this.mDefaultResourceId != 0)
        setImageResource(this.mDefaultResourceId);
      else
        setImageDrawable(null);
    }
  }

  protected final void setResizeable(boolean paramBoolean)
  {
    this.mResizeable = true;
  }

  public void setUrl(String paramString)
  {
    if (paramString != null)
      setRequest(new MediaImageRequest(paramString, 3, getLayoutParams().height));
    while (true)
    {
      return;
      setRequest(null);
    }
  }

  public final void startFadeOut(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 12)
    {
      if (sAccelerateInterpolator == null)
        sAccelerateInterpolator = new AccelerateInterpolator();
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = getAlpha();
      arrayOfFloat[1] = 0.01F;
      ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, "alpha", arrayOfFloat).setDuration(paramInt);
      localObjectAnimator.setInterpolator(sAccelerateInterpolator);
      localObjectAnimator.start();
    }
  }

  public static abstract interface OnImageLoadedListener
  {
    public abstract void onImageLoaded$7ad36aad();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EsImageView
 * JD-Core Version:    0.6.2
 */