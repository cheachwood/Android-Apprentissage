package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.apps.plus.R.styleable;

public class Thermometer extends View
{
  private static Rect sBounds = new Rect();
  private Drawable mBackground;
  private double mFillLevel;
  private Drawable mForeground;
  private Orientation mOrientation = Orientation.HORIZONTAL;

  public Thermometer(Context paramContext)
  {
    this(paramContext, null);
  }

  public Thermometer(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public Thermometer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (paramAttributeSet == null);
    TypedArray localTypedArray;
    do
    {
      return;
      localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Thermometer, paramInt, 0);
    }
    while (localTypedArray == null);
    if (localTypedArray.hasValue(0))
      this.mBackground = localTypedArray.getDrawable(0);
    if (localTypedArray.hasValue(1))
      this.mForeground = localTypedArray.getDrawable(1);
    int i = localTypedArray.getInt(2, 0);
    if ((i != 0) && (i == 1));
    for (this.mOrientation = Orientation.VERTICAL; ; this.mOrientation = Orientation.HORIZONTAL)
    {
      localTypedArray.recycle();
      break;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mBackground == null) && (this.mForeground == null))
      return;
    sBounds.set(0, 0, getWidth(), getHeight());
    paramCanvas.clipRect(sBounds, Region.Op.REPLACE);
    if (this.mForeground != null)
      this.mForeground.setBounds(sBounds);
    if (this.mBackground != null)
    {
      this.mBackground.setBounds(sBounds);
      this.mBackground.draw(paramCanvas);
    }
    paramCanvas.save();
    if (this.mOrientation == Orientation.HORIZONTAL)
    {
      int j = (int)(sBounds.width() * this.mFillLevel);
      sBounds.right = (j + sBounds.left);
    }
    while (true)
    {
      paramCanvas.clipRect(sBounds, Region.Op.REPLACE);
      if (this.mForeground != null)
        this.mForeground.draw(paramCanvas);
      paramCanvas.restore();
      break;
      if (this.mOrientation == Orientation.VERTICAL)
      {
        int i = (int)(sBounds.height() * this.mFillLevel);
        sBounds.bottom = (i + sBounds.top);
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Drawable localDrawable = this.mBackground;
    int i = 0;
    int j = 0;
    if (localDrawable != null)
    {
      int n = this.mBackground.getIntrinsicWidth();
      j = 0;
      if (n > 0)
        j = n;
      int i1 = this.mBackground.getIntrinsicHeight();
      i = 0;
      if (i1 > 0)
        i = i1;
    }
    if (this.mForeground != null)
    {
      int k = this.mForeground.getIntrinsicWidth();
      if (k > j)
        j = k;
      int m = this.mForeground.getIntrinsicHeight();
      if (m > i)
        i = m;
    }
    if ((j > 0) && (i > 0))
      setMeasuredDimension(resolveSize(j, paramInt1), resolveSize(i, paramInt2));
    while (true)
    {
      return;
      super.onMeasure(paramInt1, paramInt2);
    }
  }

  public void setBackgroundImage(Drawable paramDrawable)
  {
    this.mBackground = paramDrawable;
    invalidate();
  }

  public void setFillLevel(double paramDouble)
  {
    if (paramDouble < 0.0D)
      paramDouble = 0.0D;
    if (paramDouble > 1.0D)
      paramDouble = 1.0D;
    this.mFillLevel = paramDouble;
    invalidate();
  }

  public void setForegroundImage(Drawable paramDrawable)
  {
    this.mForeground = paramDrawable;
    invalidate();
  }

  public void setOrientation(Orientation paramOrientation)
  {
    this.mOrientation = paramOrientation;
    invalidate();
  }

  public static enum Orientation
  {
    static
    {
      Orientation[] arrayOfOrientation = new Orientation[2];
      arrayOfOrientation[0] = HORIZONTAL;
      arrayOfOrientation[1] = VERTICAL;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.Thermometer
 * JD-Core Version:    0.6.2
 */