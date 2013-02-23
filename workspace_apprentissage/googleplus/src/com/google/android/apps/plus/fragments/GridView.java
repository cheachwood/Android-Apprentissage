package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.google.android.apps.plus.R.dimen;

public class GridView extends android.widget.GridView
{
  private final int[] attrsArray = { 16843028, 16843029 };
  private int mHorizontalSpacing = 0;
  private int mVerticalSpacing = 0;

  public GridView(Context paramContext)
  {
    super(paramContext);
  }

  public GridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public GridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, this.attrsArray);
    this.mHorizontalSpacing = localTypedArray.getDimensionPixelSize(0, 0);
    this.mVerticalSpacing = localTypedArray.getDimensionPixelSize(1, 0);
    localTypedArray.recycle();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Resources localResources = getContext().getResources();
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = localResources.getDimensionPixelSize(R.dimen.medium_avatar_dimension) + 2 * localResources.getDimensionPixelSize(R.dimen.medium_avatar_selected_padding);
    int k = (i + this.mHorizontalSpacing) / (j + this.mHorizontalSpacing);
    int m = k * (j + this.mHorizontalSpacing) - this.mHorizontalSpacing;
    if ((k > 0) && (getLayoutParams().height == -2))
    {
      int n = getCount();
      int i1 = localResources.getDimensionPixelSize(R.dimen.medium_avatar_selected_dimension) + localResources.getDimensionPixelSize(R.dimen.medium_avatar_name_height);
      int i2 = (-1 + (n + k)) / k;
      int i3 = i1 * i2 + getPaddingTop() + getPaddingBottom() + this.mVerticalSpacing * (i2 - 1);
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), View.MeasureSpec.makeMeasureSpec(i3, 1073741824));
    }
    while (true)
    {
      return;
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), paramInt2);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.GridView
 * JD-Core Version:    0.6.2
 */