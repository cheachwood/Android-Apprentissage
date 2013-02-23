package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

public class CoverPhotoImageView extends ImageResourceView
{
  private Matrix mCoverPhotoMatrix;
  private int mLayoutWidth;
  private int mOffset;
  private int mRequiredWidth;

  public CoverPhotoImageView(Context paramContext)
  {
    super(paramContext);
    setSizeCategory(0);
    setScaleMode(2);
  }

  public CoverPhotoImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setSizeCategory(0);
    setScaleMode(2);
  }

  public CoverPhotoImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setSizeCategory(0);
    setScaleMode(2);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i;
    float f;
    int j;
    int k;
    if ((hasBitmap()) && (this.mCoverPhotoMatrix == null))
    {
      i = getBitmap().getWidth();
      this.mCoverPhotoMatrix = new Matrix();
      this.mCoverPhotoMatrix.reset();
      f = 1.0F;
      if (this.mLayoutWidth <= this.mRequiredWidth)
        break label137;
      f = this.mLayoutWidth / i;
      j = this.mLayoutWidth;
      this.mCoverPhotoMatrix.postScale(f, f);
      k = this.mOffset;
      if (j >= 940)
        break label163;
      k = Math.round(this.mRequiredWidth / 940.0F * this.mOffset);
    }
    while (true)
    {
      this.mCoverPhotoMatrix.postTranslate(0.0F, k);
      setImageMatrix(this.mCoverPhotoMatrix);
      super.onDraw(paramCanvas);
      return;
      label137: if (this.mRequiredWidth > i)
        f = this.mRequiredWidth / i;
      j = this.mRequiredWidth;
      break;
      label163: if (j > 940)
        k = Math.round(j / 940.0F * this.mOffset);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    this.mLayoutWidth = getMeasuredWidth();
    int i = getMeasuredHeight();
    if ((this.mLayoutWidth != 0) && (i != 0))
    {
      int j = this.mLayoutWidth;
      this.mRequiredWidth = Math.round(5.222222F * i);
      if (this.mRequiredWidth > j)
        j = this.mRequiredWidth;
      if (j > 940)
        j = 940;
      setCustomImageSize(j, 0);
      this.mCoverPhotoMatrix = null;
    }
  }

  protected final void onUnbindResources()
  {
    super.onUnbindResources();
    this.mCoverPhotoMatrix = null;
  }

  public void setTopOffset(int paramInt)
  {
    if (this.mOffset != paramInt)
    {
      this.mOffset = paramInt;
      this.mCoverPhotoMatrix = null;
      invalidate();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CoverPhotoImageView
 * JD-Core Version:    0.6.2
 */