package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.apps.plus.R.styleable;

public class ExactLayout extends ViewGroup
  implements Recyclable
{
  private Drawable mBackground;

  public ExactLayout(Context paramContext)
  {
    super(paramContext);
  }

  public ExactLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ExactLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public static int getMaxHeight(View[] paramArrayOfView)
  {
    int i = 0;
    for (int j = Math.max(-1 + paramArrayOfView.length, 0); j >= 0; j--)
    {
      View localView = paramArrayOfView[j];
      if (localView != null)
        i = Math.max(i, localView.getMeasuredHeight());
    }
    return i;
  }

  public static void measure(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = Math.max(paramInt1, 0);
    int j = Math.max(paramInt3, 0);
    paramView.measure(View.MeasureSpec.makeMeasureSpec(i, paramInt2), View.MeasureSpec.makeMeasureSpec(j, paramInt4));
  }

  public static void setCenterBounds(View paramView, int paramInt1, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    localLayoutParams.verticalBound = paramInt2;
    localLayoutParams.horizontalBound = paramInt1;
    paramView.setLayoutParams(localLayoutParams);
  }

  public static void setCorner(View paramView, int paramInt1, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (localLayoutParams == null)
      localLayoutParams = new LayoutParams(paramInt1, paramInt2);
    while (true)
    {
      paramView.setLayoutParams(localLayoutParams);
      return;
      localLayoutParams.x = paramInt1;
      localLayoutParams.y = paramInt2;
    }
  }

  public static void verticallyCenter(int paramInt, View[] paramArrayOfView)
  {
    int i = Math.max(-1 + paramArrayOfView.length, 0);
    int j = 2147483647;
    for (int k = i; k >= 0; k--)
    {
      View localView2 = paramArrayOfView[k];
      if (localView2 != null)
        j = Math.min(j, ((LayoutParams)localView2.getLayoutParams()).y);
    }
    for (int m = i; m >= 0; m--)
    {
      View localView1 = paramArrayOfView[m];
      if (localView1 != null)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView1.getLayoutParams();
        setCorner(localView1, localLayoutParams.x, j);
        setCenterBounds(localView1, localLayoutParams.horizontalBound, paramInt);
      }
    }
  }

  public final void addPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setPadding(paramInt1 + getPaddingLeft(), paramInt2 + getPaddingTop(), paramInt3 + getPaddingRight(), paramInt4 + getPaddingBottom());
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    if (this.mBackground != null)
    {
      this.mBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
      this.mBackground.draw(paramCanvas);
    }
    super.dispatchDraw(paramCanvas);
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        int k = localView.getMeasuredWidth();
        int m = localView.getMeasuredHeight();
        int n = getPaddingLeft() + Math.max(0, (localLayoutParams.horizontalBound - k) / 2);
        int i1 = getPaddingTop() + Math.max(0, (localLayoutParams.verticalBound - m) / 2);
        localView.layout(n + localLayoutParams.x, i1 + localLayoutParams.y, k + (n + localLayoutParams.x), i1 + (m + localLayoutParams.y));
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    int k = getPaddingLeft() + getPaddingRight();
    int m = getPaddingTop() + getPaddingBottom();
    int n = View.MeasureSpec.getMode(paramInt1);
    int i1 = View.MeasureSpec.getMode(paramInt2);
    int i2 = View.MeasureSpec.getSize(paramInt1);
    int i3 = View.MeasureSpec.getSize(paramInt2);
    measureChildren(View.MeasureSpec.makeMeasureSpec(i2 - k, n), View.MeasureSpec.makeMeasureSpec(i3 - m, i1));
    int i4 = getChildCount();
    for (int i5 = 0; i5 < i4; i5++)
    {
      View localView = getChildAt(i5);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        int i6 = localLayoutParams.x + localView.getMeasuredWidth();
        int i7 = localLayoutParams.y + localView.getMeasuredHeight();
        j = Math.max(j, i6);
        i = Math.max(i, i7);
      }
    }
    setMeasuredDimension(resolveSize(j + k, paramInt1), resolveSize(i + m, paramInt2));
  }

  public void onRecycle()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if ((localView instanceof Recyclable))
        ((Recyclable)localView).onRecycle();
    }
  }

  public void setBackground(Drawable paramDrawable)
  {
    this.mBackground = paramDrawable;
  }

  public static class LayoutParams extends ViewGroup.LayoutParams
  {
    public int horizontalBound;
    public int verticalBound;
    public int x;
    public int y;

    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ExactLayout_Layout);
      this.x = localTypedArray.getDimensionPixelOffset(0, 0);
      this.y = localTypedArray.getDimensionPixelOffset(1, 0);
      this.horizontalBound = localTypedArray.getDimensionPixelOffset(2, 0);
      this.verticalBound = localTypedArray.getDimensionPixelOffset(3, 0);
      localTypedArray.recycle();
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ExactLayout
 * JD-Core Version:    0.6.2
 */