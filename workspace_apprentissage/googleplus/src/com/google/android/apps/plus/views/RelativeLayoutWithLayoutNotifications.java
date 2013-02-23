package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

public class RelativeLayoutWithLayoutNotifications extends RelativeLayout
{
  private LayoutListener layoutListener;

  public RelativeLayoutWithLayoutNotifications(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.layoutListener != null);
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1) - (getPaddingLeft() + getPaddingRight());
    int j = View.MeasureSpec.getSize(paramInt2) - (getPaddingTop() + getPaddingBottom());
    if (this.layoutListener != null);
    onMeasure$3b4dfe4b(i, j);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onMeasure$3b4dfe4b(int paramInt1, int paramInt2)
  {
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.layoutListener != null);
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setLayoutListener(LayoutListener paramLayoutListener)
  {
    this.layoutListener = paramLayoutListener;
  }

  public static abstract interface LayoutListener
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.RelativeLayoutWithLayoutNotifications
 * JD-Core Version:    0.6.2
 */