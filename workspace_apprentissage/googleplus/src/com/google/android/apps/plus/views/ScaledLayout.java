package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.google.android.apps.plus.R.styleable;

public class ScaledLayout extends RelativeLayout
{
  private final Rect mDispSize;
  private final Display mDisplay;
  private MarginMode mMarginMode = MarginMode.SCALE_MARGIN_NONE;
  private float mScaleHeight = 1.0F;
  private float mScaleMarginBottom = 0.0F;
  private float mScaleMarginLeft = 0.0F;
  private float mScaleMarginRight = 0.0F;
  private float mScaleMarginTop = 0.0F;
  private float mScaleWidth = 1.0F;

  public ScaledLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public ScaledLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public ScaledLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray;
    label137: int i;
    if (paramAttributeSet != null)
    {
      localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ScaledLayout, paramInt, 0);
      if (localTypedArray != null)
      {
        if (!localTypedArray.hasValue(0))
          break label198;
        float f2 = localTypedArray.getFloat(0, 1.0F);
        this.mScaleWidth = f2;
        this.mScaleHeight = f2;
        if (!localTypedArray.hasValue(3))
          break label223;
        float f1 = localTypedArray.getFloat(3, 0.0F);
        this.mScaleMarginLeft = f1;
        this.mScaleMarginRight = f1;
        this.mScaleMarginTop = f1;
        this.mScaleMarginBottom = f1;
        i = localTypedArray.getInt(8, 0);
        if (i != 1)
          break label272;
        this.mMarginMode = MarginMode.SCALE_MARGIN_INDEPENDENT;
      }
    }
    while (true)
    {
      localTypedArray.recycle();
      this.mDisplay = ((WindowManager)getContext().getSystemService("window")).getDefaultDisplay();
      this.mDispSize = new Rect();
      return;
      label198: this.mScaleWidth = localTypedArray.getFloat(2, 1.0F);
      this.mScaleHeight = localTypedArray.getFloat(1, 1.0F);
      break;
      label223: this.mScaleMarginLeft = localTypedArray.getFloat(6, 0.0F);
      this.mScaleMarginRight = localTypedArray.getFloat(7, 0.0F);
      this.mScaleMarginTop = localTypedArray.getFloat(4, 0.0F);
      this.mScaleMarginBottom = localTypedArray.getFloat(5, 0.0F);
      break label137;
      label272: if (i == 2)
        this.mMarginMode = MarginMode.SCALE_MARGIN_LONG_EDGE;
      else if (i == 3)
        this.mMarginMode = MarginMode.SCALE_MARGIN_SHORT_EDGE;
      else
        this.mMarginMode = MarginMode.SCALE_MARGIN_NONE;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt2);
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
    int n;
    int i1;
    int i2;
    int i10;
    label122: label125: int i3;
    int i4;
    int i9;
    if ((this.mMarginMode == MarginMode.SCALE_MARGIN_INDEPENDENT) || (this.mMarginMode == MarginMode.SCALE_MARGIN_LONG_EDGE) || (this.mMarginMode == MarginMode.SCALE_MARGIN_SHORT_EDGE))
    {
      n = 1;
      i1 = j;
      i2 = getMeasuredWidth();
      if ((i != -2147483648) || (this.mScaleWidth <= 0.0F))
        break label241;
      i10 = localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin;
      if ((n == 0) || (j != i2 + i10))
        break label224;
      i1 = i2;
      i = 1073741824;
      i3 = m;
      i4 = getMeasuredHeight();
      if ((k != -2147483648) || (this.mScaleHeight <= 0.0F))
        break label274;
      i9 = localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin;
      if ((n == 0) || (m != i4 + i9))
        break label257;
      i3 = i4;
      label183: k = 1073741824;
    }
    while (true)
    {
      setMeasuredDimension(i1, i3);
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(i1, i), View.MeasureSpec.makeMeasureSpec(i3, k));
      if (n != 0)
        break label291;
      return;
      n = 0;
      break;
      label224: i1 = (int)((j + i10) * this.mScaleWidth);
      break label122;
      label241: if ((i != 0) || (j > 0))
        break label125;
      i1 = i2;
      break label125;
      label257: i3 = (int)((m + i9) * this.mScaleHeight);
      break label183;
      label274: if ((k == 0) && (m <= 0))
        i3 = i4;
    }
    label291: ViewParent localViewParent;
    do
      localViewParent = getParent();
    while ((localViewParent != null) && (!(localViewParent instanceof View)));
    int i5;
    int i6;
    label336: int i7;
    int i8;
    if (localViewParent != null)
    {
      View localView = (View)localViewParent;
      i5 = localView.getMeasuredWidth();
      i6 = localView.getMeasuredHeight();
      if (i5 >= i6)
        break label429;
      i7 = i5;
      i8 = i6;
      label351: switch (1.$SwitchMap$com$google$android$apps$plus$views$ScaledLayout$MarginMode[this.mMarginMode.ordinal()])
      {
      default:
      case 1:
      case 2:
      case 3:
      }
    }
    while (true)
    {
      setLayoutParams(localMarginLayoutParams);
      break;
      this.mDisplay.getRectSize(this.mDispSize);
      i5 = this.mDispSize.width();
      i6 = this.mDispSize.height();
      break label336;
      label429: i7 = i6;
      i8 = i5;
      break label351;
      localMarginLayoutParams.topMargin = ((int)(i6 * this.mScaleMarginTop));
      localMarginLayoutParams.bottomMargin = ((int)(i6 * this.mScaleMarginBottom));
      localMarginLayoutParams.leftMargin = ((int)(i5 * this.mScaleMarginLeft));
      localMarginLayoutParams.rightMargin = ((int)(i5 * this.mScaleMarginRight));
      continue;
      localMarginLayoutParams.topMargin = ((int)(i8 * this.mScaleMarginTop));
      localMarginLayoutParams.bottomMargin = ((int)(i8 * this.mScaleMarginBottom));
      localMarginLayoutParams.leftMargin = ((int)(i8 * this.mScaleMarginLeft));
      localMarginLayoutParams.rightMargin = ((int)(i8 * this.mScaleMarginRight));
      continue;
      localMarginLayoutParams.topMargin = ((int)(i7 * this.mScaleMarginTop));
      localMarginLayoutParams.bottomMargin = ((int)(i7 * this.mScaleMarginBottom));
      localMarginLayoutParams.leftMargin = ((int)(i7 * this.mScaleMarginLeft));
      localMarginLayoutParams.rightMargin = ((int)(i7 * this.mScaleMarginRight));
    }
  }

  public void setScale(float paramFloat)
  {
    this.mScaleWidth = paramFloat;
    this.mScaleHeight = paramFloat;
  }

  public void setScaleHeight(float paramFloat)
  {
    this.mScaleHeight = paramFloat;
  }

  public void setScaleMargin(float paramFloat)
  {
    this.mScaleMarginBottom = paramFloat;
    this.mScaleMarginRight = paramFloat;
    this.mScaleMarginLeft = paramFloat;
    this.mScaleMarginTop = paramFloat;
  }

  public void setScaleMarginBottom(float paramFloat)
  {
    this.mScaleMarginBottom = paramFloat;
  }

  public void setScaleMarginLeft(float paramFloat)
  {
    this.mScaleMarginLeft = paramFloat;
  }

  public void setScaleMarginMode(MarginMode paramMarginMode)
  {
    this.mMarginMode = paramMarginMode;
  }

  public void setScaleMarginRight(float paramFloat)
  {
    this.mScaleMarginRight = paramFloat;
  }

  public void setScaleMarginTop(float paramFloat)
  {
    this.mScaleMarginTop = paramFloat;
  }

  public void setScaleWidth(float paramFloat)
  {
    this.mScaleWidth = paramFloat;
  }

  public static enum MarginMode
  {
    static
    {
      SCALE_MARGIN_INDEPENDENT = new MarginMode("SCALE_MARGIN_INDEPENDENT", 1);
      SCALE_MARGIN_LONG_EDGE = new MarginMode("SCALE_MARGIN_LONG_EDGE", 2);
      SCALE_MARGIN_SHORT_EDGE = new MarginMode("SCALE_MARGIN_SHORT_EDGE", 3);
      MarginMode[] arrayOfMarginMode = new MarginMode[4];
      arrayOfMarginMode[0] = SCALE_MARGIN_NONE;
      arrayOfMarginMode[1] = SCALE_MARGIN_INDEPENDENT;
      arrayOfMarginMode[2] = SCALE_MARGIN_LONG_EDGE;
      arrayOfMarginMode[3] = SCALE_MARGIN_SHORT_EDGE;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ScaledLayout
 * JD-Core Version:    0.6.2
 */