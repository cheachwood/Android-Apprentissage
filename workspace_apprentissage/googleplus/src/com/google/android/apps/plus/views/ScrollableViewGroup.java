package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.google.android.apps.plus.R.id;

public abstract class ScrollableViewGroup extends ViewGroup
{
  private static final Interpolator sInterpolator = new Interpolator()
  {
    public final float getInterpolation(float paramAnonymousFloat)
    {
      float f = paramAnonymousFloat - 1.0F;
      return 1.0F + f * (f * (f * (f * f)));
    }
  };
  private float mFlingVelocity = 0.0F;
  private boolean mFlingable = true;
  private boolean mIsBeingDragged = false;
  private float[] mLastPosition = { 0.0F, 0.0F };
  private final int[] mLimits = { -2147483647, 2147483647 };
  private int mMaximumVelocity;
  private int mMinimumVelocity;
  private boolean mReceivedDown = false;
  private int mScrollDirection = 0;
  private boolean mScrollEnabled = true;
  protected Scroller mScroller;
  private int mTouchSlop;
  private VelocityTracker mVelocityTracker;
  private boolean mVertical = true;

  public ScrollableViewGroup(Context paramContext)
  {
    super(paramContext);
    Context localContext = getContext();
    setFocusable(false);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(localContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mScroller = new Scroller(localContext, sInterpolator);
  }

  public ScrollableViewGroup(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Context localContext = getContext();
    setFocusable(false);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(localContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mScroller = new Scroller(localContext, sInterpolator);
  }

  public ScrollableViewGroup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Context localContext = getContext();
    setFocusable(false);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(localContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mScroller = new Scroller(localContext, sInterpolator);
  }

  private int clampToScrollLimits(int paramInt)
  {
    if (paramInt < this.mLimits[0])
      paramInt = this.mLimits[0];
    while (true)
    {
      return paramInt;
      if (paramInt > this.mLimits[1])
        paramInt = this.mLimits[1];
    }
  }

  private boolean shouldStartDrag(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mScrollEnabled;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      if (this.mIsBeingDragged)
      {
        this.mIsBeingDragged = false;
        bool2 = false;
      }
      else
      {
        switch (paramMotionEvent.getAction())
        {
        case 1:
        default:
          bool2 = false;
          break;
        case 0:
          updatePosition(paramMotionEvent);
          if (!this.mScroller.isFinished())
          {
            startDrag();
            bool2 = true;
          }
          else
          {
            this.mReceivedDown = true;
            bool2 = false;
          }
          break;
        case 2:
        }
      }
    }
    float f1 = paramMotionEvent.getX() - this.mLastPosition[0];
    float f2 = paramMotionEvent.getY() - this.mLastPosition[1];
    int i;
    label151: int j;
    label177: int k;
    if ((f1 > this.mTouchSlop) || (f1 < -this.mTouchSlop))
    {
      i = 1;
      if ((f2 <= this.mTouchSlop) && (f2 >= -this.mTouchSlop))
        break label224;
      j = 1;
      if (!this.mVertical)
        break label236;
      if ((j == 0) || (i != 0))
        break label230;
      k = 1;
    }
    while (true)
    {
      bool2 = false;
      if (k == 0)
        break;
      updatePosition(paramMotionEvent);
      startDrag();
      bool2 = true;
      break;
      i = 0;
      break label151;
      label224: j = 0;
      break label177;
      label230: k = 0;
      continue;
      label236: if ((i != 0) && (j == 0))
        k = 1;
      else
        k = 0;
    }
  }

  private void startDrag()
  {
    this.mIsBeingDragged = true;
    this.mFlingVelocity = 0.0F;
    this.mScrollDirection = 0;
    this.mScroller.abortAnimation();
  }

  public void addView(View paramView)
  {
    View localView = paramView.findViewById(R.id.list_layout_parent);
    int k;
    if (localView != null)
    {
      int i = ((Integer)localView.getTag()).intValue();
      int j = getChildCount();
      k = 0;
      int m = 0;
      if (k < j)
      {
        if (((Integer)getChildAt(k).findViewById(R.id.list_layout_parent).getTag()).intValue() > i)
        {
          addView(paramView, k);
          m = 1;
        }
      }
      else if (m == 0)
        super.addView(paramView);
    }
    while (true)
    {
      return;
      k++;
      break;
      super.addView(paramView);
    }
  }

  public void computeScroll()
  {
    int i;
    int j;
    if (this.mScroller.computeScrollOffset())
    {
      if (!this.mVertical)
        break label92;
      i = this.mScroller.getCurrY();
      scrollTo(i);
      invalidate();
      if (!this.mVertical)
        break label103;
      j = this.mScroller.getFinalY();
      label49: if (i == j)
        this.mScroller.abortAnimation();
      if (this.mFlingVelocity != 0.0F)
        if (this.mFlingVelocity <= 0.0F)
          break label114;
    }
    label92: label103: label114: for (int k = 1; ; k = -1)
    {
      this.mFlingVelocity = 0.0F;
      onScrollFinished(k);
      return;
      i = this.mScroller.getCurrX();
      break;
      j = this.mScroller.getFinalX();
      break label49;
    }
  }

  public final int getScroll()
  {
    if (this.mVertical);
    for (int i = getScrollY(); ; i = getScrollX())
      return i;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return shouldStartDrag(paramMotionEvent);
  }

  protected void onScrollFinished(int paramInt)
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if (this.mFlingable)
    {
      if (this.mVelocityTracker == null)
        this.mVelocityTracker = VelocityTracker.obtain();
      this.mVelocityTracker.addMovement(paramMotionEvent);
    }
    boolean bool;
    if (!this.mIsBeingDragged)
      if (shouldStartDrag(paramMotionEvent))
        bool = true;
    while (true)
    {
      return bool;
      if ((i == 1) && (this.mReceivedDown))
      {
        this.mReceivedDown = false;
        bool = performClick();
      }
      else
      {
        bool = true;
        continue;
        switch (i)
        {
        default:
          bool = true;
        case 2:
        case 1:
        case 3:
        }
      }
    }
    int n;
    label132: float f4;
    if (this.mVertical)
    {
      n = 1;
      float f3 = this.mLastPosition[n];
      updatePosition(paramMotionEvent);
      f4 = f3 - this.mLastPosition[n];
      if (f4 >= -1.0F)
        break label197;
      this.mScrollDirection = -1;
      label171: scrollTo(getScroll() + (int)f4);
    }
    label197: label457: 
    while (true)
    {
      this.mReceivedDown = false;
      break;
      n = 0;
      break label132;
      if (f4 <= 1.0F)
        break label171;
      this.mScrollDirection = 1;
      break label171;
      int j;
      label219: float f1;
      float f2;
      int k;
      int m;
      if (i == 3)
      {
        j = 1;
        this.mIsBeingDragged = false;
        if ((j != 0) || (!this.mFlingable) || (getChildCount() <= 0))
          break label448;
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        if (!this.mVertical)
          break label393;
        f1 = this.mVelocityTracker.getYVelocity();
        if ((f1 <= this.mMinimumVelocity) && (f1 >= -this.mMinimumVelocity))
          break label437;
        f2 = -f1;
        this.mFlingVelocity = f2;
        k = getScrollX();
        m = getScrollY();
        if (!this.mVertical)
          break label405;
        this.mScroller.fling(k, m, 0, (int)f2, 0, 0, this.mLimits[0], this.mLimits[1]);
        label355: invalidate();
      }
      while (true)
      {
        if ((!this.mFlingable) || (this.mVelocityTracker == null))
          break label457;
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        break;
        j = 0;
        break label219;
        f1 = this.mVelocityTracker.getXVelocity();
        break label273;
        this.mScroller.fling(k, m, (int)f2, 0, this.mLimits[0], this.mLimits[1], 0, 0);
        break label355;
        onScrollFinished(this.mScrollDirection);
        continue;
        onScrollFinished(this.mScrollDirection);
      }
    }
  }

  protected final void scrollTo(int paramInt)
  {
    if (this.mVertical)
      scrollTo(0, clampToScrollLimits(paramInt));
    while (true)
    {
      return;
      scrollTo(clampToScrollLimits(paramInt), 0);
    }
  }

  public void setFlingable(boolean paramBoolean)
  {
    this.mFlingable = paramBoolean;
  }

  public void setScrollEnabled(boolean paramBoolean)
  {
    this.mScrollEnabled = paramBoolean;
  }

  public void setScrollLimits(int paramInt1, int paramInt2)
  {
    this.mLimits[0] = paramInt1;
    this.mLimits[1] = paramInt2;
  }

  public void setVertical(boolean paramBoolean)
  {
    this.mVertical = paramBoolean;
  }

  public boolean showContextMenuForChild(View paramView)
  {
    requestDisallowInterceptTouchEvent(true);
    return super.showContextMenuForChild(paramView);
  }

  public final void smoothScrollTo(int paramInt)
  {
    int i = clampToScrollLimits(paramInt) - getScroll();
    if (this.mVertical)
      this.mScroller.startScroll(0, getScrollY(), 0, i, 500);
    while (true)
    {
      invalidate();
      return;
      this.mScroller.startScroll(getScrollX(), 0, i, 0, 500);
    }
  }

  protected final void updatePosition(MotionEvent paramMotionEvent)
  {
    this.mLastPosition[0] = paramMotionEvent.getX();
    this.mLastPosition[1] = paramMotionEvent.getY();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ScrollableViewGroup
 * JD-Core Version:    0.6.2
 */