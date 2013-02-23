package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import com.google.android.apps.plus.R.color;

public final class ClickableImageButton
  implements ClickableItem
{
  private static Paint sImageSelectedPaint;
  private boolean mClicked;
  private Bitmap mClickedBitmap;
  private CharSequence mContentDescription;
  private Bitmap mDefaultBitmap;
  private ClickableImageButtonListener mListener;
  private Rect mRect;

  public ClickableImageButton(Context paramContext, Bitmap paramBitmap1, Bitmap paramBitmap2, ClickableImageButtonListener paramClickableImageButtonListener, CharSequence paramCharSequence)
  {
    this.mDefaultBitmap = paramBitmap1;
    if (this.mRect != null)
      this.mRect = new Rect(this.mRect.left, this.mRect.top, this.mRect.left + this.mDefaultBitmap.getWidth(), this.mRect.top + this.mDefaultBitmap.getHeight());
    this.mClickedBitmap = null;
    this.mListener = paramClickableImageButtonListener;
    this.mContentDescription = paramCharSequence;
    if (sImageSelectedPaint == null)
    {
      Paint localPaint = new Paint();
      sImageSelectedPaint = localPaint;
      localPaint.setStrokeWidth(4.0F);
      sImageSelectedPaint.setColor(paramContext.getApplicationContext().getResources().getColor(R.color.image_selected_stroke));
      sImageSelectedPaint.setStyle(Paint.Style.STROKE);
    }
  }

  public final void draw(Canvas paramCanvas)
  {
    int i;
    if ((this.mClicked) && (this.mClickedBitmap == null) && (this.mListener != null))
    {
      i = 1;
      if ((!this.mClicked) || (this.mClickedBitmap == null) || (this.mListener == null))
        break label119;
    }
    label119: for (Bitmap localBitmap = this.mClickedBitmap; ; localBitmap = this.mDefaultBitmap)
    {
      paramCanvas.drawBitmap(localBitmap, null, this.mRect, null);
      if (i != 0)
        paramCanvas.drawRect(2 + this.mRect.left, 2 + this.mRect.top, -2 + this.mRect.right, -2 + this.mRect.bottom, sImageSelectedPaint);
      return;
      i = 0;
      break;
    }
  }

  public final CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }

  public final Rect getRect()
  {
    return this.mRect;
  }

  public final boolean handleEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 1;
    if (paramInt3 == 3)
      this.mClicked = false;
    while (true)
    {
      return i;
      if (!this.mRect.contains(paramInt1, paramInt2))
      {
        if (paramInt3 == i)
          this.mClicked = false;
        i = 0;
      }
      else
      {
        switch (paramInt3)
        {
        default:
          break;
        case 0:
          this.mClicked = i;
          break;
        case 1:
          if ((this.mClicked) && (this.mListener != null))
            this.mListener.onClickableImageButtonClick(this);
          this.mClicked = false;
        }
      }
    }
  }

  public final void setPosition(int paramInt1, int paramInt2)
  {
    this.mRect = new Rect(paramInt1, paramInt2, paramInt1 + this.mDefaultBitmap.getWidth(), paramInt2 + this.mDefaultBitmap.getHeight());
  }

  public static abstract interface ClickableImageButtonListener
  {
    public abstract void onClickableImageButtonClick(ClickableImageButton paramClickableImageButton);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableImageButton
 * JD-Core Version:    0.6.2
 */