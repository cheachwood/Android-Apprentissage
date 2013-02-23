package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.Scroller;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.styleable;

public class ExpandingScrollView extends ScrollableViewGroup
{
  private static final Interpolator sBigBounceInterpolator = new OvershootInterpolator(15.0F);
  private static final Interpolator sBounceInterpolator = new OvershootInterpolator();
  private static boolean sInitialized;
  private static int sMinExposureLand;
  private static int sMinExposurePort;
  private boolean mAlwaysExpanded = true;
  private Runnable mAnimateInRunnable;
  private boolean mBigBounce;
  private boolean mCanAnimate;
  private boolean mHasPlayedAnimation;
  private MotionEvent mLastTouchEvent;
  private int mLastTouchY;
  private int mMaxScroll;
  private int mMinExposure;
  private int mMinExposureLand;
  private int mMinExposurePort;
  private int mOriginalTranslationY;
  private Boolean mRestoreExpandedScrollPosition;
  private int mTouchSlop;

  public ExpandingScrollView(Context paramContext)
  {
    super(paramContext);
    Context localContext = getContext();
    if (!sInitialized)
    {
      Resources localResources = localContext.getResources();
      sMinExposureLand = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_land);
      sMinExposurePort = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_port);
      sInitialized = true;
    }
    this.mTouchSlop = ViewConfiguration.get(localContext).getScaledTouchSlop();
  }

  public ExpandingScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Context localContext = getContext();
    if (!sInitialized)
    {
      Resources localResources = localContext.getResources();
      sMinExposureLand = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_land);
      sMinExposurePort = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_port);
      sInitialized = true;
    }
    this.mTouchSlop = ViewConfiguration.get(localContext).getScaledTouchSlop();
    setAttributeValues(paramContext, paramAttributeSet);
  }

  public ExpandingScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Context localContext = getContext();
    if (!sInitialized)
    {
      Resources localResources = localContext.getResources();
      sMinExposureLand = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_land);
      sMinExposurePort = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_port);
      sInitialized = true;
    }
    this.mTouchSlop = ViewConfiguration.get(localContext).getScaledTouchSlop();
    setAttributeValues(paramContext, paramAttributeSet);
  }

  private void setAttributeValues(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ExpandingScrollView);
    this.mMinExposureLand = localTypedArray.getDimensionPixelOffset(0, sMinExposureLand);
    this.mMinExposurePort = localTypedArray.getDimensionPixelOffset(1, sMinExposurePort);
    this.mBigBounce = localTypedArray.getBoolean(2, false);
  }

  public final MotionEvent getLastTouchEvent()
  {
    return this.mLastTouchEvent;
  }

  protected void onDetachedFromWindow()
  {
    if (this.mAnimateInRunnable != null)
    {
      removeCallbacks(this.mAnimateInRunnable);
      this.mAnimateInRunnable = null;
    }
    clearAnimation();
    super.onDetachedFromWindow();
  }

  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mAlwaysExpanded;
    boolean bool2 = false;
    if (bool1);
    while (true)
    {
      return bool2;
      if (!this.mScroller.isFinished())
      {
        bool2 = true;
      }
      else
      {
        int i = (int)paramMotionEvent.getY();
        switch (paramMotionEvent.getAction())
        {
        case 1:
        default:
          bool2 = false;
          break;
        case 0:
          updatePosition(paramMotionEvent);
          this.mLastTouchY = i;
          bool2 = false;
          break;
        case 2:
          int j = getScrollY();
          boolean bool3;
          label103: int k;
          int m;
          label129: AbsListView localAbsListView;
          if (j == this.mMaxScroll)
          {
            bool3 = true;
            this.mRestoreExpandedScrollPosition = Boolean.valueOf(bool3);
            k = i - this.mLastTouchY;
            if (k >= 0)
              break label297;
            m = 1;
            localAbsListView = (AbsListView)getChildAt(0);
            if ((localAbsListView.getChildCount() != 0) && ((localAbsListView.getFirstVisiblePosition() != 0) || (localAbsListView.getChildAt(0).getTop() != 0) || (localAbsListView.getScrollY() != 0)))
              break label303;
          }
          label297: label303: for (int n = 1; ; n = 0)
          {
            if (j != 0)
            {
              bool2 = false;
              if (m != 0)
                break;
              bool2 = false;
              if (n == 0)
                break;
              int i1 = this.mMaxScroll;
              bool2 = false;
              if (j != i1)
                break;
            }
            super.onInterceptTouchEvent(paramMotionEvent);
            if (k <= this.mTouchSlop)
            {
              int i4 = -this.mTouchSlop;
              bool2 = false;
              if (k >= i4)
                break;
            }
            int i2 = 0;
            int i3 = localAbsListView.getChildCount();
            while (i2 < i3)
            {
              View localView = localAbsListView.getChildAt(i2);
              if ((localView instanceof StreamOneUpCommentView))
                ((StreamOneUpCommentView)localView).cancelPressedState();
              i2++;
            }
            bool3 = false;
            break label103;
            m = 0;
            break label129;
          }
          bool2 = true;
        }
      }
    }
  }

  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mAlwaysExpanded);
    for (int i = 0; ; i = this.mMaxScroll)
      for (int j = 0; j < getChildCount(); j++)
      {
        View localView = getChildAt(j);
        int i2 = i + localView.getMeasuredHeight();
        localView.layout(paramInt1, i, paramInt3, i2);
        i = i2;
      }
    int k;
    int i1;
    label119: int m;
    if (this.mAlwaysExpanded)
    {
      k = 0;
      setScrollLimits(0, k);
      if ((this.mRestoreExpandedScrollPosition != null) && (this.mRestoreExpandedScrollPosition.booleanValue()))
      {
        boolean bool = this.mAlwaysExpanded;
        i1 = 0;
        if (!bool)
          break label242;
        scrollTo(i1);
      }
      if ((Build.VERSION.SDK_INT >= 12) && (!this.mHasPlayedAnimation) && (this.mCanAnimate) && (this.mRestoreExpandedScrollPosition == null))
        if (this.mAnimateInRunnable == null)
        {
          this.mOriginalTranslationY = ((int)getTranslationY());
          m = this.mOriginalTranslationY;
          if (!this.mAlwaysExpanded)
            break label251;
        }
    }
    label242: label251: for (int n = this.mMaxScroll; ; n = this.mMinExposure)
    {
      setTranslationY(n + m);
      this.mAnimateInRunnable = new Runnable()
      {
        public final void run()
        {
          int i;
          if (ExpandingScrollView.this.mBigBounce)
          {
            i = 1000;
            if (!ExpandingScrollView.this.mBigBounce)
              break label66;
          }
          label66: for (Interpolator localInterpolator = ExpandingScrollView.sBigBounceInterpolator; ; localInterpolator = ExpandingScrollView.sBounceInterpolator)
          {
            for (int j = 0; j < ExpandingScrollView.this.getChildCount(); j++)
              ExpandingScrollView.this.getChildAt(j).setVerticalScrollBarEnabled(false);
            i = 750;
            break;
          }
          ExpandingScrollView.this.animate().translationY(ExpandingScrollView.this.mOriginalTranslationY).setInterpolator(localInterpolator).setDuration(i);
          ExpandingScrollView.access$402(ExpandingScrollView.this, null);
          ExpandingScrollView.access$502(ExpandingScrollView.this, true);
          ExpandingScrollView.this.postDelayed(new Runnable()
          {
            public final void run()
            {
              for (int i = 0; i < ExpandingScrollView.this.getChildCount(); i++)
                ExpandingScrollView.this.getChildAt(i).setVerticalScrollBarEnabled(true);
            }
          }
          , i + 200);
        }
      };
      removeCallbacks(this.mAnimateInRunnable);
      postDelayed(this.mAnimateInRunnable, 250L);
      return;
      k = this.mMaxScroll;
      break;
      i1 = this.mMaxScroll;
      break label119;
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt2);
    if (getContext().getResources().getConfiguration().orientation == 2);
    int j;
    for (this.mMinExposure = this.mMinExposureLand; ; this.mMinExposure = this.mMinExposurePort)
    {
      this.mMaxScroll = (i - this.mMinExposure);
      j = 0;
      int k = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
      for (int m = 0; m < getChildCount(); m++)
      {
        getChildAt(m).measure(paramInt1, k);
        j = Math.max(j, getChildAt(m).getMeasuredWidth());
      }
    }
    setMeasuredDimension(j, i + this.mMaxScroll);
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mRestoreExpandedScrollPosition = Boolean.valueOf(localSavedState.mExpanded);
    this.mMinExposureLand = localSavedState.mExposureLand;
    this.mMinExposurePort = localSavedState.mExposurePort;
    this.mBigBounce = localSavedState.mBigBounce;
  }

  protected Parcelable onSaveInstanceState()
  {
    Parcelable localParcelable = super.onSaveInstanceState();
    if ((this.mMaxScroll != 0) && (getScrollY() == this.mMaxScroll));
    for (boolean bool = true; ; bool = false)
      return new SavedState(localParcelable, bool, this.mMinExposureLand, this.mMinExposurePort, this.mBigBounce);
  }

  protected final void onScrollFinished(int paramInt)
  {
    if (paramInt < 0);
    for (int i = 0; ; i = this.mMaxScroll)
    {
      smoothScrollTo(i);
      boolean bool = false;
      if (paramInt >= 0)
        bool = true;
      this.mRestoreExpandedScrollPosition = Boolean.valueOf(bool);
      return;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mLastTouchEvent = paramMotionEvent;
    return super.onTouchEvent(paramMotionEvent);
  }

  public void setAlwaysExpanded(boolean paramBoolean)
  {
    this.mAlwaysExpanded = paramBoolean;
  }

  public void setBigBounce(boolean paramBoolean)
  {
    this.mBigBounce = paramBoolean;
  }

  public void setCanAnimate(boolean paramBoolean)
  {
    this.mCanAnimate = paramBoolean;
    if ((this.mCanAnimate) && (!this.mHasPlayedAnimation))
      requestLayout();
  }

  public void setMinimumExposure(int paramInt1, int paramInt2)
  {
    this.mMinExposureLand = paramInt1;
    this.mMinExposurePort = paramInt2;
  }

  private static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    final boolean mBigBounce;
    final boolean mExpanded;
    final int mExposureLand;
    final int mExposurePort;

    private SavedState(Parcel paramParcel)
    {
      super();
      int j;
      if (paramParcel.readInt() == i)
      {
        j = i;
        this.mExpanded = j;
        this.mExposureLand = paramParcel.readInt();
        this.mExposurePort = paramParcel.readInt();
        if (paramParcel.readInt() != i)
          break label57;
      }
      while (true)
      {
        this.mBigBounce = i;
        return;
        j = 0;
        break;
        label57: i = 0;
      }
    }

    public SavedState(Parcelable paramParcelable, boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2)
    {
      super();
      this.mExpanded = paramBoolean1;
      this.mExposureLand = paramInt1;
      this.mExposurePort = paramInt2;
      this.mBigBounce = paramBoolean2;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      int i = 1;
      super.writeToParcel(paramParcel, paramInt);
      int j;
      if (this.mExpanded)
      {
        j = i;
        paramParcel.writeInt(j);
        paramParcel.writeInt(this.mExposureLand);
        paramParcel.writeInt(this.mExposurePort);
        if (!this.mBigBounce)
          break label59;
      }
      while (true)
      {
        paramParcel.writeInt(i);
        return;
        j = 0;
        break;
        label59: i = 0;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ExpandingScrollView
 * JD-Core Version:    0.6.2
 */