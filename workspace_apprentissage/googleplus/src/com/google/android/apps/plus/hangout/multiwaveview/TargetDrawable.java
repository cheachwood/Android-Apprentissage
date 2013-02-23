package com.google.android.apps.plus.hangout.multiwaveview;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class TargetDrawable
{
  public static final int[] STATE_ACTIVE = { 16842910, 16842914 };
  public static final int[] STATE_FOCUSED = { 16842910, 16842908 };
  public static final int[] STATE_INACTIVE = { 16842910 };
  private float mAlpha = 1.0F;
  private Drawable mDrawable;
  private float mScaleX = 1.0F;
  private float mScaleY = 1.0F;
  private float mTranslationX = 0.0F;
  private float mTranslationY = 0.0F;

  public TargetDrawable(Drawable paramDrawable)
  {
    if (paramDrawable != null);
    for (Drawable localDrawable = paramDrawable.mutate(); ; localDrawable = null)
    {
      this.mDrawable = localDrawable;
      if (this.mDrawable != null)
        this.mDrawable.setBounds(0, 0, this.mDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
      setState(STATE_INACTIVE);
      return;
    }
  }

  public final void draw(Canvas paramCanvas)
  {
    if (this.mDrawable == null);
    while (true)
    {
      return;
      paramCanvas.save(1);
      paramCanvas.translate(this.mTranslationX, this.mTranslationY);
      paramCanvas.scale(this.mScaleX, this.mScaleY);
      paramCanvas.translate(-0.5F * getWidth(), -0.5F * getHeight());
      this.mDrawable.setAlpha(Math.round(255.0F * this.mAlpha));
      this.mDrawable.draw(paramCanvas);
      paramCanvas.restore();
    }
  }

  public float getAlpha()
  {
    return this.mAlpha;
  }

  public int getHeight()
  {
    if (this.mDrawable != null);
    for (int i = this.mDrawable.getIntrinsicHeight(); ; i = 0)
      return i;
  }

  public float getScaleX()
  {
    return this.mScaleX;
  }

  public float getScaleY()
  {
    return this.mScaleY;
  }

  public int getWidth()
  {
    if (this.mDrawable != null);
    for (int i = this.mDrawable.getIntrinsicWidth(); ; i = 0)
      return i;
  }

  public float getX()
  {
    return this.mTranslationX;
  }

  public float getY()
  {
    return this.mTranslationY;
  }

  public final boolean hasState$25e2147()
  {
    if ((this.mDrawable instanceof StateListDrawable));
    return false;
  }

  public final boolean isValid()
  {
    if (this.mDrawable != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void setAlpha(float paramFloat)
  {
    this.mAlpha = paramFloat;
  }

  public final void setState(int[] paramArrayOfInt)
  {
    if ((this.mDrawable instanceof StateListDrawable))
    {
      StateListDrawable localStateListDrawable = (StateListDrawable)this.mDrawable;
      localStateListDrawable.setState(paramArrayOfInt);
      localStateListDrawable.invalidateSelf();
    }
  }

  public final void setX(float paramFloat)
  {
    this.mTranslationX = paramFloat;
  }

  public final void setY(float paramFloat)
  {
    this.mTranslationY = paramFloat;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.multiwaveview.TargetDrawable
 * JD-Core Version:    0.6.2
 */