package com.google.android.apps.plus.views;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.DbEmbedEmotishare;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.util.GifDrawable;
import com.google.android.apps.plus.util.GifImage;
import com.google.android.apps.plus.util.Property;

public class EmotiShareCardView extends StreamCardView
{
  private static boolean sImageCardViewInitialized;
  private static ImageResourceManager sImageResourceManager;
  private Drawable mAnimatedDrawable;
  protected Resource mAnimatedImageResource;
  protected DbEmbedEmotishare mDbEmbedEmotiShare;
  protected Rect mDestRect = new Rect();
  private RectF mDestRectF = new RectF();
  private boolean mIsShowingBitmap;
  private boolean mIsShowingDrawable;
  private Matrix mMatrix = new Matrix();
  private Matrix mMatrixInverse = new Matrix();
  protected MediaRef mMediaRef;
  protected Rect mSrcRect = new Rect();
  private RectF mSrcRectF = new RectF();
  protected Resource mStaticImageResource;

  public EmotiShareCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public EmotiShareCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sImageCardViewInitialized)
    {
      sImageCardViewInitialized = true;
      sImageResourceManager = ImageResourceManager.getInstance(paramContext);
    }
  }

  private boolean hasBitmap(Resource paramResource)
  {
    if ((hasImage(paramResource)) && ((paramResource.getResource() instanceof Bitmap)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static boolean hasImage(Resource paramResource)
  {
    int i = 1;
    if ((paramResource != null) && (paramResource.getStatus() == i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  private static boolean isAnimationSupported()
  {
    if ((Property.ENABLE_STREAM_GIF_ANIMATION.getBoolean()) && (EsApplication.sMemoryClass >= 64));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = hasBitmap(this.mStaticImageResource);
    int j;
    if (this.mAnimatedDrawable == null)
    {
      Resource localResource = this.mAnimatedImageResource;
      if ((!hasImage(localResource)) || (!(localResource.getResource() instanceof GifImage)))
        break label362;
      j = 1;
      if (j != 0)
      {
        this.mSrcRect.setEmpty();
        this.mAnimatedDrawable = new GifDrawable((GifImage)this.mAnimatedImageResource.getResource());
        ((GifDrawable)this.mAnimatedDrawable).setAnimationEnabled(isAnimationSupported());
        this.mAnimatedDrawable.setCallback(this);
      }
    }
    Drawable localDrawable = this.mAnimatedDrawable;
    int i;
    label132: boolean bool2;
    if ((localDrawable != null) && ((!(localDrawable instanceof GifDrawable)) || (((GifDrawable)localDrawable).isValid())))
    {
      i = 1;
      if ((!bool1) && (i == 0))
        break label374;
      bool2 = true;
      label145: drawMediaTopAreaStage(paramCanvas, paramInt3, paramInt4, bool2, this.mDestRect, sMediaTopAreaBackgroundPaint);
      if (i == 0)
        break label380;
      if (localDrawable != null)
      {
        if (this.mIsShowingBitmap)
          this.mSrcRect.setEmpty();
        if (this.mSrcRect.isEmpty())
        {
          createSourceRectForMediaImage(this.mSrcRect, localDrawable, paramInt3, paramInt4);
          this.mSrcRectF.set(this.mSrcRect);
          this.mDestRectF.set(this.mDestRect);
          this.mMatrix.setRectToRect(this.mSrcRectF, this.mDestRectF, Matrix.ScaleToFit.CENTER);
          if (!this.mMatrix.invert(this.mMatrixInverse))
            this.mMatrixInverse.reset();
        }
        localDrawable.setBounds(0, 0, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight());
        paramCanvas.concat(this.mMatrix);
        localDrawable.draw(paramCanvas);
        paramCanvas.concat(this.mMatrixInverse);
        this.mIsShowingBitmap = false;
        this.mIsShowingDrawable = true;
      }
    }
    label362: label492: 
    while (true)
    {
      drawMediaTopAreaShadow(paramCanvas, paramInt3, paramInt4);
      drawTagBarIconAndBackground(paramCanvas, paramInt1, paramInt2);
      drawPlusOneBar(paramCanvas);
      drawMediaBottomArea$1be95c43(paramCanvas, paramInt1, paramInt3, paramInt4);
      drawCornerIcon(paramCanvas);
      return paramInt4;
      j = 0;
      break;
      i = 0;
      break label132;
      label374: bool2 = false;
      break label145;
      label380: if (bool1)
      {
        if (hasBitmap(this.mStaticImageResource))
          this.mSrcRect.setEmpty();
        for (Bitmap localBitmap = (Bitmap)this.mStaticImageResource.getResource(); ; localBitmap = null)
        {
          if (localBitmap == null)
            break label492;
          if (this.mIsShowingDrawable)
            this.mSrcRect.setEmpty();
          if (this.mSrcRect.isEmpty())
            createSourceRectForMediaImage(this.mSrcRect, localBitmap, paramInt3, paramInt4);
          paramCanvas.drawBitmap(localBitmap, this.mSrcRect, this.mDestRect, sResizePaint);
          this.mIsShowingBitmap = true;
          this.mIsShowingDrawable = false;
          break;
        }
      }
    }
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    byte[] arrayOfByte = paramCursor.getBlob(28);
    if (arrayOfByte != null)
    {
      this.mDbEmbedEmotiShare = DbEmbedEmotishare.deserialize(arrayOfByte);
      if (this.mDbEmbedEmotiShare != null)
        this.mMediaRef = this.mDbEmbedEmotiShare.getImageRef();
    }
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mAnimatedDrawable)
      invalidate();
    while (true)
    {
      return;
      super.invalidateDrawable(paramDrawable);
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 + sXDoublePadding;
    int j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    this.mBackgroundRect.set(0, j, getMeasuredWidth(), getMeasuredHeight());
    createTagBar(paramInt1, paramInt2, paramInt3);
    createPlusOneBar(paramInt1, j + sTopBorderPadding - sYPadding, paramInt3);
    createMediaBottomArea(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mSrcRect.setEmpty();
    this.mDestRect.set(sLeftBorderPadding, sTopBorderPadding, i + sLeftBorderPadding, j + sTopBorderPadding);
    return paramInt4;
  }

  protected final void onBindResources()
  {
    super.onBindResources();
    if (this.mMediaRef != null)
    {
      if (isAnimationSupported())
        this.mAnimatedImageResource = sImageResourceManager.getMedia(this.mMediaRef, 1, 4, this);
      this.mStaticImageResource = sImageResourceManager.getMedia(this.mMediaRef, 3, 0, this);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mDbEmbedEmotiShare = null;
    this.mMediaRef = null;
    this.mSrcRect.setEmpty();
    this.mDestRect.setEmpty();
    this.mSrcRectF.setEmpty();
    this.mDestRectF.setEmpty();
    this.mMatrix.reset();
  }

  protected final void onUnbindResources()
  {
    super.onUnbindResources();
    if (this.mStaticImageResource != null)
    {
      this.mStaticImageResource.unregister(this);
      this.mStaticImageResource = null;
    }
    if (this.mAnimatedImageResource != null)
    {
      this.mAnimatedImageResource.unregister(this);
      this.mAnimatedImageResource = null;
    }
    if (this.mAnimatedDrawable != null)
    {
      this.mAnimatedDrawable.setCallback(null);
      if ((this.mAnimatedDrawable instanceof Recyclable))
        ((Recyclable)this.mAnimatedDrawable).onRecycle();
    }
    this.mAnimatedDrawable = null;
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mAnimatedDrawable);
    for (boolean bool = true; ; bool = super.verifyDrawable(paramDrawable))
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EmotiShareCardView
 * JD-Core Version:    0.6.2
 */