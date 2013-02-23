package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

public class LinearLayoutWithLayoutNotifications extends LinearLayout
{
  private LayoutListener mLayoutListener;
  private int mMaxWidth = -1;

  public LinearLayoutWithLayoutNotifications(Context paramContext)
  {
    super(paramContext);
  }

  public LinearLayoutWithLayoutNotifications(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public LinearLayoutWithLayoutNotifications(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mLayoutListener != null);
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mMaxWidth > 0) && (getMeasuredWidth() > this.mMaxWidth))
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824), paramInt2);
    if (this.mLayoutListener != null)
      this.mLayoutListener.onMeasured(this);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mLayoutListener != null);
  }

  public void setLayoutListener(LayoutListener paramLayoutListener)
  {
    this.mLayoutListener = paramLayoutListener;
  }

  public void setMaxWidth(int paramInt)
  {
    this.mMaxWidth = paramInt;
  }

  public static abstract interface LayoutListener
  {
    public abstract void onMeasured(View paramView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.LinearLayoutWithLayoutNotifications
 * JD-Core Version:    0.6.2
 */