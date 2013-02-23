package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

public final class FullSizeLinearLayout extends LinearLayout
{
  private int mMaxHeight;

  public FullSizeLinearLayout(Context paramContext)
  {
    this(paramContext, null);
    init(paramContext, null);
  }

  public FullSizeLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public FullSizeLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    this.mMaxHeight = 2147483647;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16843040 });
    this.mMaxHeight = localTypedArray.getDimensionPixelSize(0, 0);
    localTypedArray.recycle();
  }

  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mMaxHeight > 0)
    {
      if (View.MeasureSpec.getMode(paramInt1) == -2147483648)
        paramInt1 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt1), 1073741824);
      if (View.MeasureSpec.getMode(paramInt2) != -2147483648);
    }
    for (paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt2), this.mMaxHeight), 1073741824); ; paramInt2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824))
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
      paramInt1 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt1), 1073741824);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.FullSizeLinearLayout
 * JD-Core Version:    0.6.2
 */