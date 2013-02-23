package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class TabContainer extends ScrollableViewGroup
{
  private int mFirstVisiblePanel = -1;
  private int mLastVisiblePanel = -1;
  private OnTabChangeListener mListener;
  private int mPanelWidth;
  private int mSelectedPanel;

  public TabContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setVertical(false);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mPanelWidth == 0);
    for (int i = 1; ; i = 0)
    {
      this.mPanelWidth = (paramInt3 - paramInt1);
      int j = paramInt4 - paramInt2;
      for (int k = 0; k < getChildCount(); k++)
      {
        View localView = getChildAt(k);
        int m = k * this.mPanelWidth;
        localView.layout(m, 0, m + this.mPanelWidth, j);
      }
    }
    setScrollLimits(0, this.mPanelWidth * (-1 + getChildCount()));
    if (i != 0)
      scrollTo(this.mSelectedPanel * this.mPanelWidth);
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    setMeasuredDimension(i, j);
    int k = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    int m = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
    for (int n = 0; n < getChildCount(); n++)
      getChildAt(n).measure(k, m);
  }

  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1 / this.mPanelWidth;
    int j;
    int m;
    label60: OnTabChangeListener localOnTabChangeListener;
    if (paramInt1 % this.mPanelWidth == 0)
    {
      j = 0;
      int k = i + j;
      if ((i == this.mFirstVisiblePanel) && (k == this.mLastVisiblePanel))
        return;
      this.mFirstVisiblePanel = i;
      this.mLastVisiblePanel = k;
      m = 0;
      if (m >= getChildCount())
        return;
      localOnTabChangeListener = this.mListener;
      if ((m < this.mFirstVisiblePanel) || (m > this.mLastVisiblePanel))
        break label119;
    }
    label119: for (boolean bool = true; ; bool = false)
    {
      localOnTabChangeListener.onTabVisibilityChange(m, bool);
      m++;
      break label60;
      j = 1;
      break;
    }
  }

  protected final void onScrollFinished(int paramInt)
  {
    if (this.mPanelWidth == 0)
      return;
    int i = getScrollX();
    if (paramInt < 0);
    for (this.mSelectedPanel = (i / this.mPanelWidth); ; this.mSelectedPanel = (1 + i / this.mPanelWidth))
    {
      if (this.mSelectedPanel >= getChildCount())
        this.mSelectedPanel = (-1 + getChildCount());
      smoothScrollTo(this.mSelectedPanel * this.mPanelWidth);
      this.mListener.onTabSelected(this.mSelectedPanel);
      break;
    }
  }

  public void setOnTabChangeListener(OnTabChangeListener paramOnTabChangeListener)
  {
    this.mListener = paramOnTabChangeListener;
  }

  public void setSelectedPanel(int paramInt)
  {
    if (this.mSelectedPanel != paramInt)
    {
      this.mSelectedPanel = paramInt;
      if (this.mPanelWidth != 0)
        smoothScrollTo(this.mPanelWidth * this.mSelectedPanel);
    }
  }

  public static abstract interface OnTabChangeListener
  {
    public abstract void onTabSelected(int paramInt);

    public abstract void onTabVisibilityChange(int paramInt, boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TabContainer
 * JD-Core Version:    0.6.2
 */