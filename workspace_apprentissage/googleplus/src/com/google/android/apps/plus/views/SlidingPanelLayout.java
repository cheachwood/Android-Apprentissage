package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.integer;

public class SlidingPanelLayout extends ScrollableViewGroup
{
  private OnSlidingPanelStateChange mListener;
  private int mMaxNavigationBarWidth;
  private int mMinNavigationBarWidth;
  private int mNavigationBarWidth;
  private int mNavigationBarWidthPercent;
  private boolean mOpen;
  private View mPanel;
  private Drawable mShadow;
  private int mShadowWidth;

  public SlidingPanelLayout(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(0);
    setScrollEnabled(true);
    setVertical(false);
  }

  public SlidingPanelLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBackgroundColor(0);
    setScrollEnabled(true);
    setVertical(false);
  }

  public SlidingPanelLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setBackgroundColor(0);
    setScrollEnabled(true);
    setVertical(false);
  }

  private boolean isScrolling()
  {
    int i = getScroll();
    if ((i != 0) && (i != -this.mNavigationBarWidth));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void close()
  {
    if (!this.mOpen);
    while (true)
    {
      return;
      setScrollEnabled(false);
      smoothScrollTo(0);
    }
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (getScrollX() < 0)
    {
      this.mShadow.setBounds(-this.mShadowWidth, 0, 0, getHeight());
      this.mShadow.draw(paramCanvas);
    }
  }

  public final int getNavigationBarWidth()
  {
    return this.mNavigationBarWidth;
  }

  public final boolean isOpen()
  {
    return this.mOpen;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    if (getChildCount() != 1)
      throw new IllegalStateException(getClass().getName() + " should have exactly one child");
    this.mPanel = getChildAt(0);
    Resources localResources = getResources();
    this.mShadowWidth = localResources.getDimensionPixelSize(R.dimen.host_shadow_width);
    this.mMinNavigationBarWidth = localResources.getDimensionPixelSize(R.dimen.host_min_navigation_bar_width);
    this.mMaxNavigationBarWidth = localResources.getDimensionPixelSize(R.dimen.host_max_navigation_bar_width);
    this.mNavigationBarWidthPercent = localResources.getInteger(R.integer.host_navigation_bar_width_percent);
    this.mShadow = localResources.getDrawable(R.drawable.navigation_shadow);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mOpen;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      boolean bool3 = paramMotionEvent.getX() < this.mNavigationBarWidth;
      bool2 = false;
      if (!bool3)
      {
        super.onInterceptTouchEvent(paramMotionEvent);
        bool2 = true;
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mPanel.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    if (!isScrolling())
    {
      if (this.mOpen)
        break label38;
      scrollTo(0, 0);
    }
    while (true)
    {
      return;
      label38: scrollTo(-this.mNavigationBarWidth, 0);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    super.onMeasure(paramInt1, paramInt2);
    this.mPanel.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 1073741824));
    this.mNavigationBarWidth = Math.min(Math.max(i * this.mNavigationBarWidthPercent / 100, this.mMinNavigationBarWidth), this.mMaxNavigationBarWidth);
    setScrollLimits(-this.mNavigationBarWidth, 0);
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mOpen = localSavedState.open;
    setScrollEnabled(this.mOpen);
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.open = this.mOpen;
    return localSavedState;
  }

  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 == 0)
    {
      this.mOpen = false;
      if (this.mListener != null)
        this.mListener.onPanelClosed();
    }
    while (true)
    {
      return;
      if ((paramInt1 == -this.mNavigationBarWidth) && (this.mListener != null));
    }
  }

  protected final void onScrollFinished(int paramInt)
  {
    if (paramInt < 0)
      smoothScrollTo(-this.mNavigationBarWidth);
    while (true)
    {
      return;
      this.mOpen = false;
      smoothScrollTo(0);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mOpen;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      if (paramMotionEvent.getX() < this.mNavigationBarWidth)
      {
        boolean bool3 = isScrolling();
        bool2 = false;
        if (!bool3);
      }
      else
      {
        bool2 = super.onTouchEvent(paramMotionEvent);
      }
    }
  }

  public final void open()
  {
    if (this.mOpen);
    while (true)
    {
      return;
      this.mOpen = true;
      setScrollEnabled(true);
      smoothScrollTo(-this.mNavigationBarWidth);
    }
  }

  public boolean performClick()
  {
    super.performClick();
    close();
    return true;
  }

  public void setOnSlidingPanelStateChange(OnSlidingPanelStateChange paramOnSlidingPanelStateChange)
  {
    this.mListener = paramOnSlidingPanelStateChange;
  }

  public static abstract interface OnSlidingPanelStateChange
  {
    public abstract void onPanelClosed();
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    boolean open;

    private SavedState(Parcel paramParcel)
    {
      super();
      if (paramParcel.readInt() != 0);
      for (boolean bool = true; ; bool = false)
      {
        this.open = bool;
        return;
      }
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      String str = Integer.toHexString(System.identityHashCode(this));
      return "HostLayout.SavedState{" + str + " open=" + this.open + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      if (this.open);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SlidingPanelLayout
 * JD-Core Version:    0.6.2
 */