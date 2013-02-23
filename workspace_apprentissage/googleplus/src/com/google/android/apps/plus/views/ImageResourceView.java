package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.GifDrawable;
import com.google.android.apps.plus.util.GifImage;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ViewUtils;

public class ImageResourceView extends View
  implements ResourceConsumer, Recyclable
{
  private static Interpolator sDecelerateInterpolator;
  private static Bitmap sDefaultIcon;
  private static ImageResourceManager sImageManager;
  private static Bitmap sPanoramaIcon;
  private static final Paint sScalePaint;
  private static Bitmap sVideoIcon;
  private boolean mAnimationEnabled = true;
  private Bitmap mBitmap;
  private Matrix mBitmapMatrix;
  private boolean mClearCurrentContent;
  private int mCustomImageHeight;
  private int mCustomImageWidth;
  private boolean mDefaultIconEnabled;
  private int mDefaultIconX;
  private int mDefaultIconY;
  private Rect mDestinationRect = new Rect();
  private RectF mDestinationRectF = new RectF();
  private Drawable mDrawable;
  private boolean mFadeIn;
  private int mImageResourceFlags;
  private int mLastRequestedHeight;
  private int mLastRequestedWidth;
  private Matrix mMatrix = new Matrix();
  private MediaRef mMediaRef;
  private Drawable mOverlayDrawable;
  private int mPanoramaIconX;
  private int mPanoramaIconY;
  private boolean mPaused;
  private boolean mReleaseImageWhenPaused;
  private long mRequestTime;
  private Resource mResource;
  private Drawable mResourceBrokenDrawable;
  private Drawable mResourceLoadingDrawable;
  private boolean mResourceMissing;
  private Drawable mResourceMissingDrawable;
  private int mScaleMode = 1;
  private Drawable mSelectorDrawable;
  private int mSizeCategory = 1;
  private Rect mSourceRect = new Rect();
  private RectF mSourceRectF = new RectF();
  private int mVideoIconX;
  private int mVideoIconY;

  static
  {
    Paint localPaint = new Paint();
    sScalePaint = localPaint;
    localPaint.setFilterBitmap(true);
  }

  public ImageResourceView(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }

  public ImageResourceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public ImageResourceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private void clearDrawable()
  {
    if (this.mDrawable != null)
      this.mDrawable.setCallback(null);
    if ((this.mDrawable instanceof Recyclable))
      ((Recyclable)this.mDrawable).onRecycle();
    this.mDrawable = null;
  }

  private void computeRects(int paramInt1, int paramInt2)
  {
    int i = getPaddingTop();
    int j = getPaddingRight();
    int k = getPaddingBottom();
    int m = getPaddingLeft();
    int n = getWidth() - m - j;
    int i1 = getHeight() - i - k;
    float f1 = paramInt1 / paramInt2;
    float f2 = n / i1;
    switch (this.mScaleMode)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      this.mSourceRectF.set(this.mSourceRect);
      this.mDestinationRectF.set(this.mDestinationRect);
      this.mMatrix.setRectToRect(this.mSourceRectF, this.mDestinationRectF, Matrix.ScaleToFit.FILL);
      return;
      this.mSourceRect.set(0, 0, paramInt1, paramInt2);
      if (f1 > f2)
      {
        int i6 = (i1 - (int)(n / f1)) / 2;
        this.mDestinationRect.set(m, i + i6, m + n, i + i1 - i6);
      }
      else
      {
        int i5 = (n - (int)(f1 * i1)) / 2;
        this.mDestinationRect.set(m + i5, i, m + n - i5, i + i1);
        continue;
        if (f1 > f2)
        {
          int i4 = (paramInt1 - (int)(f2 * paramInt2)) / 2;
          this.mSourceRect.set(i4, 0, paramInt1 - i4, paramInt2);
        }
        while (true)
        {
          this.mDestinationRect.set(m, i, m + n, i + i1);
          break;
          int i2 = (int)(paramInt1 / f2);
          int i3 = Math.max((int)(0.4F * paramInt2) - i2 / 2, 0);
          this.mSourceRect.set(0, i3, paramInt1, i3 + i2);
        }
        this.mSourceRect.set(0, 0, paramInt1, paramInt2);
        this.mDestinationRect.set(0, 0, n, i1);
      }
    }
  }

  private void drawResourceStatus(Canvas paramCanvas, Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      paramDrawable.setBounds(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
      paramDrawable.draw(paramCanvas);
    }
  }

  private Drawable getDrawable(int paramInt)
  {
    if (paramInt == 0);
    for (Drawable localDrawable = null; ; localDrawable = getResources().getDrawable(paramInt))
      return localDrawable;
  }

  private boolean hasDrawable()
  {
    if ((hasImage()) && ((this.mResource.getResource() instanceof GifImage)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (sImageManager == null)
    {
      sImageManager = ImageResourceManager.getInstance(paramContext);
      sVideoIcon = ImageUtils.decodeResource(paramContext.getResources(), R.drawable.ic_video_play);
      sPanoramaIcon = ImageUtils.decodeResource(paramContext.getResources(), R.drawable.overlay_lightcycle);
      sDefaultIcon = ImageUtils.decodeResource(paramContext.getResources(), R.drawable.ic_missing_photo);
    }
    this.mSelectorDrawable = paramContext.getResources().getDrawable(R.drawable.stream_list_selector);
    this.mSelectorDrawable.setCallback(this);
    String str1;
    String str2;
    if (paramAttributeSet != null)
    {
      str1 = paramAttributeSet.getAttributeValue(null, "size");
      if (str1 != null)
      {
        if (!"custom".equals(str1))
          break label140;
        this.mSizeCategory = 0;
      }
      str2 = paramAttributeSet.getAttributeValue(null, "scale");
      if (str2 != null)
        if (!"zoom".equals(str2))
          break label255;
    }
    for (this.mScaleMode = 1; ; this.mScaleMode = 0)
    {
      return;
      label140: if ("thumbnail".equals(str1))
      {
        this.mSizeCategory = 2;
        break;
      }
      if ("large".equals(str1))
      {
        this.mSizeCategory = 3;
        break;
      }
      if ("portrait".equals(str1))
      {
        this.mSizeCategory = 4;
        break;
      }
      if ("landscape".equals(str1))
      {
        this.mSizeCategory = 5;
        break;
      }
      if ("full".equals(str1))
      {
        this.mSizeCategory = 1;
        break;
      }
      throw new IllegalArgumentException("Invalid size category: " + str1);
      label255: if (!"fit".equals(str2))
        break label274;
    }
    label274: throw new IllegalArgumentException("Invalid scale mode: " + str2);
  }

  public final void bindResources()
  {
    int i;
    int j;
    if ((ViewUtils.isViewAttached(this)) && (!this.mPaused))
    {
      this.mRequestTime = System.currentTimeMillis();
      if (this.mMediaRef == null)
        break label154;
      if (this.mSizeCategory != 0)
        break label128;
      if ((this.mCustomImageWidth == 0) && (this.mCustomImageHeight == 0))
        break label115;
      i = this.mCustomImageWidth;
      j = this.mCustomImageHeight;
      if (((i != 0) || (j != 0)) && ((this.mLastRequestedHeight != j) || (this.mLastRequestedWidth != i)))
      {
        this.mResource = sImageManager.getMedia(this.mMediaRef, i, j, this.mImageResourceFlags, this);
        this.mLastRequestedWidth = i;
        this.mLastRequestedHeight = j;
      }
    }
    while (true)
    {
      return;
      label115: i = getWidth();
      j = getHeight();
      break;
      label128: this.mResource = sImageManager.getMedia(this.mMediaRef, this.mSizeCategory, this.mImageResourceFlags, this);
      continue;
      label154: this.mBitmap = null;
      clearDrawable();
    }
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if ((isPressed()) || (isFocused()))
    {
      this.mSelectorDrawable.setBounds(0, 0, getWidth(), getHeight());
      this.mSelectorDrawable.draw(paramCanvas);
    }
    while (true)
    {
      return;
      if (isSelected())
      {
        this.mSelectorDrawable.setBounds(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        this.mSelectorDrawable.draw(paramCanvas);
      }
    }
  }

  protected void drawableStateChanged()
  {
    this.mSelectorDrawable.setState(getDrawableState());
    invalidate();
    super.drawableStateChanged();
  }

  protected final Bitmap getBitmap()
  {
    if (hasBitmap());
    for (Bitmap localBitmap = (Bitmap)this.mResource.getResource(); ; localBitmap = this.mBitmap)
      return localBitmap;
  }

  public final MediaRef getMediaRef()
  {
    return this.mMediaRef;
  }

  protected final boolean hasBitmap()
  {
    if ((hasImage()) && ((this.mResource.getResource() instanceof Bitmap)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasImage()
  {
    int i = 1;
    if ((this.mResource != null) && (this.mResource.getStatus() == i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mDrawable)
      invalidate();
    while (true)
    {
      return;
      super.invalidateDrawable(paramDrawable);
    }
  }

  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    if (this.mDrawable != null)
      this.mDrawable.jumpToCurrentState();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mPaused = false;
    bindResources();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    onUnbindResources();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    boolean bool = hasBitmap();
    Bitmap localBitmap;
    int k;
    if ((bool) || (hasDrawable()))
      if (bool)
      {
        localBitmap = getBitmap();
        if ((localBitmap == null) || (localBitmap.isRecycled()))
        {
          if ((this.mMediaRef == null) || (MediaRef.MediaType.VIDEO != this.mMediaRef.getType()))
            break label393;
          k = 1;
          label67: if (k == 0)
            break label399;
          paramCanvas.drawBitmap(sVideoIcon, this.mVideoIconX, this.mVideoIconY, null);
        }
      }
    while (true)
    {
      if (this.mOverlayDrawable != null)
      {
        this.mOverlayDrawable.setBounds(0, 0, getWidth(), getHeight());
        this.mOverlayDrawable.draw(paramCanvas);
      }
      return;
      if (this.mSourceRect.isEmpty())
        computeRects(localBitmap.getWidth(), localBitmap.getHeight());
      if ((this.mScaleMode == 2) && (this.mBitmapMatrix != null))
      {
        paramCanvas.drawBitmap(localBitmap, this.mBitmapMatrix, sScalePaint);
        break;
      }
      paramCanvas.drawBitmap(localBitmap, this.mSourceRect, this.mDestinationRect, sScalePaint);
      break;
      if ((this.mDrawable == null) && (hasDrawable()))
      {
        this.mSourceRect.setEmpty();
        this.mDrawable = new GifDrawable((GifImage)this.mResource.getResource());
        ((GifDrawable)this.mDrawable).setAnimationEnabled(this.mAnimationEnabled);
        this.mDrawable.setCallback(this);
      }
      Drawable localDrawable = this.mDrawable;
      if (localDrawable == null)
        break;
      if (((localDrawable instanceof GifDrawable)) && (!((GifDrawable)localDrawable).isValid()))
      {
        if (this.mDefaultIconEnabled)
        {
          paramCanvas.drawBitmap(sDefaultIcon, this.mDefaultIconX, this.mDefaultIconY, null);
          break;
        }
        drawResourceStatus(paramCanvas, this.mResourceBrokenDrawable);
        break;
      }
      int i = localDrawable.getIntrinsicWidth();
      int j = localDrawable.getIntrinsicHeight();
      if (this.mSourceRect.isEmpty())
        computeRects(i, j);
      paramCanvas.save();
      localDrawable.setBounds(0, 0, i, j);
      paramCanvas.concat(this.mMatrix);
      localDrawable.draw(paramCanvas);
      paramCanvas.restore();
      break;
      label393: k = 0;
      break label67;
      label399: int m;
      if ((this.mMediaRef != null) && (MediaRef.MediaType.PANORAMA == this.mMediaRef.getType()))
        m = 1;
      while (true)
      {
        if (m == 0)
          break label476;
        paramCanvas.drawBitmap(sPanoramaIcon, this.mPanoramaIconX, this.mPanoramaIconY, null);
        break;
        if ((this.mResource != null) && (this.mResource.getResourceType() == 2))
          m = 1;
        else
          m = 0;
      }
      label476: continue;
      if (this.mDefaultIconEnabled)
        paramCanvas.drawBitmap(sDefaultIcon, this.mDefaultIconX, this.mDefaultIconY, null);
      else if (this.mResource != null)
        switch (this.mResource.getStatus())
        {
        case 1:
        default:
          break;
        case 0:
        case 2:
        case 3:
          drawResourceStatus(paramCanvas, this.mResourceLoadingDrawable);
          break;
        case 4:
          drawResourceStatus(paramCanvas, this.mResourceMissingDrawable);
          break;
        case 5:
        case 6:
        case 7:
          drawResourceStatus(paramCanvas, this.mResourceBrokenDrawable);
          break;
        }
      else if (this.mResourceMissing)
        drawResourceStatus(paramCanvas, this.mResourceMissingDrawable);
      else
        drawResourceStatus(paramCanvas, this.mResourceLoadingDrawable);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    this.mVideoIconX = ((i - sVideoIcon.getWidth()) / 2);
    this.mVideoIconY = ((j - sVideoIcon.getHeight()) / 2);
    this.mPanoramaIconX = ((i - sPanoramaIcon.getWidth()) / 2);
    this.mPanoramaIconY = ((j - sPanoramaIcon.getHeight()) / 2);
    this.mDefaultIconX = ((i - sDefaultIcon.getWidth()) / 2);
    this.mDefaultIconY = ((j - sDefaultIcon.getHeight()) / 2);
    if ((paramBoolean) && (this.mSizeCategory == 0))
    {
      onUnbindResources();
      bindResources();
    }
  }

  public void onRecycle()
  {
    onUnbindResources();
    setMediaRef(null);
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    switch (paramResource.getStatus())
    {
    default:
    case 1:
    }
    while (true)
    {
      invalidate();
      return;
      if ((System.currentTimeMillis() - this.mRequestTime > 100L) && (this.mFadeIn) && (Build.VERSION.SDK_INT >= 12))
      {
        if (sDecelerateInterpolator == null)
          sDecelerateInterpolator = new DecelerateInterpolator();
        setAlpha(0.01F);
        animate().alpha(1.0F).setDuration(500L).setInterpolator(sDecelerateInterpolator);
      }
    }
  }

  public final void onResume()
  {
    if (this.mReleaseImageWhenPaused)
    {
      this.mPaused = false;
      bindResources();
    }
  }

  public final void onStop()
  {
    if (this.mReleaseImageWhenPaused)
    {
      this.mPaused = true;
      onUnbindResources();
    }
  }

  protected void onUnbindResources()
  {
    if (this.mResource != null)
    {
      this.mResource.unregister(this);
      this.mResource = null;
    }
    if (!this.mClearCurrentContent)
      this.mBitmap = getBitmap();
    clearDrawable();
    this.mOverlayDrawable = null;
    this.mSourceRect.setEmpty();
    this.mLastRequestedWidth = 0;
    this.mLastRequestedHeight = 0;
  }

  public void setCustomImageSize(int paramInt1, int paramInt2)
  {
    this.mCustomImageWidth = paramInt1;
    this.mCustomImageHeight = paramInt2;
  }

  public void setDefaultIconEnabled(boolean paramBoolean)
  {
    if (paramBoolean != this.mDefaultIconEnabled)
    {
      this.mDefaultIconEnabled = paramBoolean;
      invalidate();
    }
  }

  public void setFadeIn(boolean paramBoolean)
  {
    this.mFadeIn = paramBoolean;
  }

  public void setImageMatrix(Matrix paramMatrix)
  {
    this.mBitmapMatrix = paramMatrix;
  }

  public void setImageResourceFlags(int paramInt)
  {
    this.mImageResourceFlags = paramInt;
  }

  public void setMediaRef(MediaRef paramMediaRef)
  {
    setMediaRef(paramMediaRef, true);
  }

  public void setMediaRef(MediaRef paramMediaRef, boolean paramBoolean)
  {
    if ((this.mMediaRef != null) && (this.mMediaRef.equals(paramMediaRef)));
    while (true)
    {
      return;
      this.mClearCurrentContent = paramBoolean;
      onUnbindResources();
      this.mMediaRef = paramMediaRef;
      if (this.mMediaRef != null)
        this.mResourceMissing = false;
      bindResources();
      invalidate();
    }
  }

  public void setOverlay(Drawable paramDrawable)
  {
    if (this.mOverlayDrawable != paramDrawable)
    {
      this.mOverlayDrawable = paramDrawable;
      invalidate();
    }
  }

  public void setReleaseImageWhenPaused(boolean paramBoolean)
  {
    this.mReleaseImageWhenPaused = paramBoolean;
  }

  public void setResourceBrokenDrawable(int paramInt)
  {
    this.mResourceBrokenDrawable = getDrawable(paramInt);
  }

  public void setResourceBrokenDrawable(Drawable paramDrawable)
  {
    this.mResourceBrokenDrawable = paramDrawable;
  }

  public void setResourceLoadingDrawable(int paramInt)
  {
    this.mResourceLoadingDrawable = getDrawable(paramInt);
  }

  public void setResourceLoadingDrawable(Drawable paramDrawable)
  {
    this.mResourceLoadingDrawable = paramDrawable;
  }

  public void setResourceMissing(boolean paramBoolean)
  {
    this.mResourceMissing = paramBoolean;
  }

  public void setResourceMissingDrawable(int paramInt)
  {
    this.mResourceMissingDrawable = getDrawable(paramInt);
  }

  public void setResourceMissingDrawable(Drawable paramDrawable)
  {
    this.mResourceMissingDrawable = paramDrawable;
  }

  public void setScaleMode(int paramInt)
  {
    if (paramInt != this.mScaleMode)
    {
      this.mScaleMode = paramInt;
      this.mSourceRect.setEmpty();
      invalidate();
    }
  }

  public void setSelected(boolean paramBoolean)
  {
    if (paramBoolean != isSelected())
    {
      super.setSelected(paramBoolean);
      if (this.mSelectorDrawable != null)
        invalidate();
    }
  }

  public void setSelector(Drawable paramDrawable)
  {
    this.mSelectorDrawable = paramDrawable;
    if (this.mSelectorDrawable != null)
      this.mSelectorDrawable.setCallback(this);
  }

  public void setSizeCategory(int paramInt)
  {
    this.mSizeCategory = paramInt;
  }

  public final void unbindResources()
  {
    onUnbindResources();
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if ((paramDrawable == this.mSelectorDrawable) || (paramDrawable == this.mDrawable));
    for (boolean bool = true; ; bool = super.verifyDrawable(paramDrawable))
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ImageResourceView
 * JD-Core Version:    0.6.2
 */