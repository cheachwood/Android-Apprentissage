package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class HostActionBarWidgetLayout extends ViewGroup
{
  private int mMaxWidth;

  public HostActionBarWidgetLayout(Context paramContext)
  {
    super(paramContext);
  }

  public HostActionBarWidgetLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    parseAttr(paramAttributeSet);
  }

  public HostActionBarWidgetLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    parseAttr(paramAttributeSet);
  }

  private void parseAttr(AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, new int[] { 16843039 });
    this.mMaxWidth = localTypedArray.getDimensionPixelSize(0, 0);
    localTypedArray.recycle();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (getChildCount() != 1);
    while (true)
    {
      return;
      View localView = getChildAt(0);
      int i = localView.getMeasuredHeight();
      int j = (paramInt4 - paramInt2 - i) / 2;
      localView.layout(0, j, localView.getMeasuredWidth(), j + i);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (getChildCount() != 1)
    {
      setMeasuredDimension(0, 0);
      return;
    }
    View localView = getChildAt(0);
    int i;
    label44: int j;
    if (localView.getLayoutParams().height == -2)
    {
      i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), -2147483648);
      j = getLayoutParams().width;
      if (j != -2)
        break label136;
      localView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), i);
    }
    while (true)
    {
      int k = resolveSize(this.mMaxWidth, paramInt1);
      if (localView.getMeasuredWidth() > k)
        localView.measure(View.MeasureSpec.makeMeasureSpec(k, 1073741824), i);
      setMeasuredDimension(localView.getMeasuredWidth(), resolveSize(localView.getMeasuredHeight(), paramInt2));
      break;
      i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824);
      break label44;
      label136: localView.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), i);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.HostActionBarWidgetLayout
 * JD-Core Version:    0.6.2
 */