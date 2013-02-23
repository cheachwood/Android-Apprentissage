package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewPager extends ViewGroup
{
  private static final Comparator<ItemInfo> COMPARATOR = new Comparator()
  {
  };
  private static final int[] LAYOUT_ATTRS = { 16842931 };
  private static final Interpolator sInterpolator = new Interpolator()
  {
    public final float getInterpolation(float paramAnonymousFloat)
    {
      float f = paramAnonymousFloat - 1.0F;
      return 1.0F + f * (f * (f * (f * f)));
    }
  };
  private int mActivePointerId = -1;
  private PagerAdapter mAdapter;
  private OnAdapterChangeListener mAdapterChangeListener;
  private int mBottomPageBounds;
  private boolean mCalledSuper;
  private int mChildHeightMeasureSpec;
  private int mChildWidthMeasureSpec;
  private int mCloseEnough;
  private int mCurItem;
  private int mDecorChildCount;
  private int mDefaultGutterSize;
  private boolean mFirstLayout = true;
  private float mFirstOffset = -3.402824E+038F;
  private int mFlingDistance;
  private int mGutterSize;
  private boolean mInLayout;
  private float mInitialMotionX;
  private OnPageChangeListener mInternalPageChangeListener;
  private boolean mIsBeingDragged;
  private boolean mIsUnableToDrag;
  private final ArrayList<ItemInfo> mItems = new ArrayList();
  private float mLastMotionX;
  private float mLastMotionY;
  private float mLastOffset = 3.4028235E+38F;
  private EdgeEffectCompat mLeftEdge;
  private Drawable mMarginDrawable;
  private int mMaximumVelocity;
  private int mMinimumVelocity;
  private boolean mNeedCalculatePageOffsets = false;
  private PagerObserver mObserver;
  private int mOffscreenPageLimit = 1;
  private OnPageChangeListener mOnPageChangeListener;
  private int mPageMargin;
  private boolean mPopulatePending;
  private Parcelable mRestoredAdapterState = null;
  private ClassLoader mRestoredClassLoader = null;
  private int mRestoredCurItem = -1;
  private EdgeEffectCompat mRightEdge;
  private int mScrollState = 0;
  private Scroller mScroller;
  private boolean mScrollingCacheEnabled;
  private final ItemInfo mTempItem = new ItemInfo();
  private final Rect mTempRect = new Rect();
  private int mTopPageBounds;
  private int mTouchSlop;
  private VelocityTracker mVelocityTracker;

  public ViewPager(Context paramContext)
  {
    super(paramContext);
    initViewPager();
  }

  public ViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initViewPager();
  }

  private ItemInfo addNewItem(int paramInt1, int paramInt2)
  {
    ItemInfo localItemInfo = new ItemInfo();
    localItemInfo.position = paramInt1;
    localItemInfo.object = this.mAdapter.instantiateItem(this, paramInt1);
    localItemInfo.widthFactor = 1.0F;
    if ((paramInt2 < 0) || (paramInt2 >= this.mItems.size()))
      this.mItems.add(localItemInfo);
    while (true)
    {
      return localItemInfo;
      this.mItems.add(paramInt2, localItemInfo);
    }
  }

  private boolean arrowScroll(int paramInt)
  {
    View localView1 = findFocus();
    if (localView1 == this)
      localView1 = null;
    View localView2 = FocusFinder.getInstance().findNextFocus(this, localView1, paramInt);
    boolean bool;
    if ((localView2 != null) && (localView2 != localView1))
      if (paramInt == 17)
      {
        int k = getChildRectInPagerCoordinates(this.mTempRect, localView2).left;
        int m = getChildRectInPagerCoordinates(this.mTempRect, localView1).left;
        if ((localView1 != null) && (k >= m))
          bool = pageLeft();
      }
    while (true)
    {
      if (bool)
        playSoundEffect(SoundEffectConstants.getContantForFocusDirection(paramInt));
      return bool;
      bool = localView2.requestFocus();
      continue;
      bool = false;
      if (paramInt == 66)
      {
        int i = getChildRectInPagerCoordinates(this.mTempRect, localView2).left;
        int j = getChildRectInPagerCoordinates(this.mTempRect, localView1).left;
        if ((localView1 != null) && (i <= j))
        {
          bool = pageRight();
        }
        else
        {
          bool = localView2.requestFocus();
          continue;
          if ((paramInt == 17) || (paramInt == 1))
          {
            bool = pageLeft();
          }
          else if (paramInt != 66)
          {
            bool = false;
            if (paramInt != 2);
          }
          else
          {
            bool = pageRight();
          }
        }
      }
    }
  }

  private boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    int k;
    boolean bool;
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int i = paramView.getScrollX();
      int j = paramView.getScrollY();
      k = -1 + localViewGroup.getChildCount();
      if (k >= 0)
      {
        View localView = localViewGroup.getChildAt(k);
        if ((paramInt2 + i >= localView.getLeft()) && (paramInt2 + i < localView.getRight()) && (paramInt3 + j >= localView.getTop()) && (paramInt3 + j < localView.getBottom()) && (canScroll(localView, true, paramInt1, paramInt2 + i - localView.getLeft(), paramInt3 + j - localView.getTop())))
          bool = true;
      }
    }
    while (true)
    {
      return bool;
      k--;
      break;
      if ((paramBoolean) && (ViewCompat.canScrollHorizontally(paramView, -paramInt1)))
        bool = true;
      else
        bool = false;
    }
  }

  private void completeScroll()
  {
    if (this.mScrollState == 2);
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
      {
        setScrollingCacheEnabled(false);
        this.mScroller.abortAnimation();
        int k = getScrollX();
        int m = getScrollY();
        int n = this.mScroller.getCurrX();
        int i1 = this.mScroller.getCurrY();
        if ((k != n) || (m != i1))
          scrollTo(n, i1);
        setScrollState(0);
      }
      this.mPopulatePending = false;
      for (int j = 0; j < this.mItems.size(); j++)
      {
        ItemInfo localItemInfo = (ItemInfo)this.mItems.get(j);
        if (localItemInfo.scrolling)
        {
          i = 1;
          localItemInfo.scrolling = false;
        }
      }
    }
    if (i != 0)
      populate();
  }

  private void endDrag()
  {
    this.mIsBeingDragged = false;
    this.mIsUnableToDrag = false;
    if (this.mVelocityTracker != null)
    {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }

  private Rect getChildRectInPagerCoordinates(Rect paramRect, View paramView)
  {
    if (paramRect == null)
      paramRect = new Rect();
    if (paramView == null)
      paramRect.set(0, 0, 0, 0);
    while (true)
    {
      return paramRect;
      paramRect.left = paramView.getLeft();
      paramRect.right = paramView.getRight();
      paramRect.top = paramView.getTop();
      paramRect.bottom = paramView.getBottom();
      ViewGroup localViewGroup;
      for (ViewParent localViewParent = paramView.getParent(); ((localViewParent instanceof ViewGroup)) && (localViewParent != this); localViewParent = localViewGroup.getParent())
      {
        localViewGroup = (ViewGroup)localViewParent;
        paramRect.left += localViewGroup.getLeft();
        paramRect.right += localViewGroup.getRight();
        paramRect.top += localViewGroup.getTop();
        paramRect.bottom += localViewGroup.getBottom();
      }
    }
  }

  private ItemInfo infoForChild(View paramView)
  {
    int i = 0;
    ItemInfo localItemInfo;
    if (i < this.mItems.size())
    {
      localItemInfo = (ItemInfo)this.mItems.get(i);
      if (!this.mAdapter.isViewFromObject(paramView, localItemInfo.object));
    }
    while (true)
    {
      return localItemInfo;
      i++;
      break;
      localItemInfo = null;
    }
  }

  private ItemInfo infoForCurrentScrollPosition()
  {
    int i = getWidth();
    float f1;
    float f2;
    int j;
    float f3;
    float f4;
    int k;
    Object localObject;
    int m;
    if (i > 0)
    {
      f1 = getScrollX() / i;
      f2 = 0.0F;
      if (i > 0)
        f2 = this.mPageMargin / i;
      j = -1;
      f3 = 0.0F;
      f4 = 0.0F;
      k = 1;
      localObject = null;
      m = 0;
    }
    while (true)
    {
      ItemInfo localItemInfo;
      float f5;
      if (m < this.mItems.size())
      {
        localItemInfo = (ItemInfo)this.mItems.get(m);
        if ((k == 0) && (localItemInfo.position != j + 1))
        {
          localItemInfo = this.mTempItem;
          localItemInfo.offset = (f2 + (f3 + f4));
          localItemInfo.position = (j + 1);
          localItemInfo.widthFactor = 1.0F;
          m--;
        }
        f5 = localItemInfo.offset;
        float f6 = f2 + (f5 + localItemInfo.widthFactor);
        if ((k != 0) || (f1 >= f5))
        {
          if ((f1 >= f6) && (m != -1 + this.mItems.size()))
            break label205;
          localObject = localItemInfo;
        }
      }
      return localObject;
      f1 = 0.0F;
      break;
      label205: j = localItemInfo.position;
      f3 = f5;
      f4 = localItemInfo.widthFactor;
      localObject = localItemInfo;
      m++;
      k = 0;
    }
  }

  private ItemInfo infoForPosition(int paramInt)
  {
    int i = 0;
    ItemInfo localItemInfo;
    if (i < this.mItems.size())
    {
      localItemInfo = (ItemInfo)this.mItems.get(i);
      if (localItemInfo.position != paramInt);
    }
    while (true)
    {
      return localItemInfo;
      i++;
      break;
      localItemInfo = null;
    }
  }

  private void initViewPager()
  {
    setWillNotDraw(false);
    setDescendantFocusability(262144);
    setFocusable(true);
    Context localContext = getContext();
    this.mScroller = new Scroller(localContext, sInterpolator);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(localContext);
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(localViewConfiguration);
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mLeftEdge = new EdgeEffectCompat(localContext);
    this.mRightEdge = new EdgeEffectCompat(localContext);
    float f = localContext.getResources().getDisplayMetrics().density;
    this.mFlingDistance = ((int)(25.0F * f));
    this.mCloseEnough = ((int)(2.0F * f));
    this.mDefaultGutterSize = ((int)(16.0F * f));
    ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
    if (ViewCompat.getImportantForAccessibility(this) == 0)
      ViewCompat.setImportantForAccessibility(this, 1);
  }

  private void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (this.mDecorChildCount > 0)
    {
      int i = getScrollX();
      int j = getPaddingLeft();
      int k = getPaddingRight();
      int m = getWidth();
      int n = getChildCount();
      int i1 = 0;
      if (i1 < n)
      {
        View localView = getChildAt(i1);
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        int i2;
        if (localLayoutParams.isDecor)
          switch (0x7 & localLayoutParams.gravity)
          {
          case 2:
          case 4:
          default:
            i2 = j;
          case 3:
          case 1:
          case 5:
          }
        while (true)
        {
          int i3 = i2 + i - localView.getLeft();
          if (i3 != 0)
            localView.offsetLeftAndRight(i3);
          i1++;
          break;
          i2 = j;
          j += localView.getWidth();
          continue;
          i2 = Math.max((m - localView.getMeasuredWidth()) / 2, j);
          continue;
          i2 = m - k - localView.getMeasuredWidth();
          k += localView.getMeasuredWidth();
        }
      }
    }
    if (this.mOnPageChangeListener != null)
      this.mOnPageChangeListener.onPageScrolled$486775f1(paramInt1, paramFloat);
    if (this.mInternalPageChangeListener != null)
      this.mInternalPageChangeListener.onPageScrolled$486775f1(paramInt1, paramFloat);
    this.mCalledSuper = true;
  }

  private void onSecondaryPointerUp(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, i) == this.mActivePointerId)
      if (i != 0)
        break label56;
    label56: for (int j = 1; ; j = 0)
    {
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, j);
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, j);
      if (this.mVelocityTracker != null)
        this.mVelocityTracker.clear();
      return;
    }
  }

  private boolean pageLeft()
  {
    boolean bool = true;
    if (this.mCurItem > 0)
      setCurrentItem(-1 + this.mCurItem, bool);
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  private boolean pageRight()
  {
    boolean bool = true;
    if ((this.mAdapter != null) && (this.mCurItem < -1 + this.mAdapter.getCount()))
      setCurrentItem(1 + this.mCurItem, bool);
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  private boolean pageScrolled(int paramInt)
  {
    boolean bool1;
    if (this.mItems.size() == 0)
    {
      this.mCalledSuper = false;
      onPageScrolled(0, 0.0F, 0);
      boolean bool2 = this.mCalledSuper;
      bool1 = false;
      if (!bool2)
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }
    else
    {
      ItemInfo localItemInfo = infoForCurrentScrollPosition();
      int i = getWidth();
      int j = i + this.mPageMargin;
      float f1 = this.mPageMargin / i;
      int k = localItemInfo.position;
      float f2 = (paramInt / i - localItemInfo.offset) / (f1 + localItemInfo.widthFactor);
      int m = (int)(f2 * j);
      this.mCalledSuper = false;
      onPageScrolled(k, f2, m);
      if (!this.mCalledSuper)
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
      bool1 = true;
    }
    return bool1;
  }

  private boolean performDrag(float paramFloat)
  {
    float f1 = this.mLastMotionX - paramFloat;
    this.mLastMotionX = paramFloat;
    float f2 = f1 + getScrollX();
    int i = getWidth();
    float f3 = i * this.mFirstOffset;
    float f4 = i * this.mLastOffset;
    int j = 1;
    int k = 1;
    ItemInfo localItemInfo1 = (ItemInfo)this.mItems.get(0);
    ItemInfo localItemInfo2 = (ItemInfo)this.mItems.get(-1 + this.mItems.size());
    if (localItemInfo1.position != 0)
    {
      j = 0;
      f3 = localItemInfo1.offset * i;
    }
    if (localItemInfo2.position != -1 + this.mAdapter.getCount())
    {
      k = 0;
      f4 = localItemInfo2.offset * i;
    }
    boolean bool1;
    if (f2 < f3)
    {
      bool1 = false;
      if (j != 0)
      {
        float f5 = f3 - f2;
        bool1 = this.mLeftEdge.onPull(Math.abs(f5) / i);
      }
      f2 = f3;
    }
    while (true)
    {
      this.mLastMotionX += f2 - (int)f2;
      scrollTo((int)f2, getScrollY());
      pageScrolled((int)f2);
      return bool1;
      boolean bool2 = f2 < f4;
      bool1 = false;
      if (bool2)
      {
        bool1 = false;
        if (k != 0)
        {
          float f6 = f2 - f4;
          bool1 = this.mRightEdge.onPull(Math.abs(f6) / i);
        }
        f2 = f4;
      }
    }
  }

  private void populate()
  {
    populate(this.mCurItem);
  }

  private void populate(int paramInt)
  {
    int i = this.mCurItem;
    ItemInfo localItemInfo1 = null;
    if (i != paramInt)
    {
      localItemInfo1 = infoForPosition(this.mCurItem);
      this.mCurItem = paramInt;
    }
    if (this.mAdapter == null)
      break label33;
    label33: label1701: label1832: 
    while (true)
    {
      return;
      if ((!this.mPopulatePending) && (getWindowToken() != null))
      {
        int j = this.mOffscreenPageLimit;
        int k = Math.max(0, this.mCurItem - j);
        int m = this.mAdapter.getCount();
        int n = Math.min(m - 1, j + this.mCurItem);
        int i1 = 0;
        int i2 = this.mItems.size();
        Object localObject1 = null;
        if (i1 < i2)
        {
          ItemInfo localItemInfo12 = (ItemInfo)this.mItems.get(i1);
          if (localItemInfo12.position < this.mCurItem)
            break label355;
          int i40 = localItemInfo12.position;
          int i41 = this.mCurItem;
          localObject1 = null;
          if (i40 == i41)
            localObject1 = localItemInfo12;
        }
        if ((localObject1 == null) && (m > 0))
          localObject1 = addNewItem(this.mCurItem, i1);
        if (localObject1 != null)
        {
          float f1 = 0.0F;
          int i7 = i1 - 1;
          label227: int i8;
          if (i7 >= 0)
          {
            localItemInfo5 = (ItemInfo)this.mItems.get(i7);
            float f2 = 2.0F - ((ItemInfo)localObject1).widthFactor;
            i8 = -1 + this.mCurItem;
            label244: if (i8 < 0)
              break label486;
            if ((f1 < f2) || (i8 >= k))
              break label373;
            if (localItemInfo5 == null)
              break label486;
            int i39 = localItemInfo5.position;
            if ((i8 == i39) && (!localItemInfo5.scrolling))
            {
              this.mItems.remove(i7);
              PagerAdapter localPagerAdapter3 = this.mAdapter;
              Object localObject4 = localItemInfo5.object;
              localPagerAdapter3.destroyItem(this, i8, localObject4);
              i7--;
              i1--;
              if (i7 < 0)
                break label367;
            }
          }
          label355: label367: for (ItemInfo localItemInfo5 = (ItemInfo)this.mItems.get(i7); ; localItemInfo5 = null)
          {
            i8--;
            break label244;
            i1++;
            break;
            localItemInfo5 = null;
            break label227;
          }
          label373: if (localItemInfo5 != null)
          {
            int i38 = localItemInfo5.position;
            if (i8 == i38)
            {
              f1 += localItemInfo5.widthFactor;
              i7--;
              if (i7 >= 0);
              for (localItemInfo5 = (ItemInfo)this.mItems.get(i7); ; localItemInfo5 = null)
                break;
            }
          }
          int i37 = i7 + 1;
          f1 += addNewItem(i8, i37).widthFactor;
          i1++;
          if (i7 >= 0);
          for (localItemInfo5 = (ItemInfo)this.mItems.get(i7); ; localItemInfo5 = null)
            break;
          label486: float f3 = ((ItemInfo)localObject1).widthFactor;
          int i9 = i1 + 1;
          if (f3 < 2.0F)
          {
            int i34;
            if (i9 < this.mItems.size())
            {
              localItemInfo10 = (ItemInfo)this.mItems.get(i9);
              i34 = 1 + this.mCurItem;
              if (i34 >= m)
                break label790;
              if ((f3 < 2.0F) || (i34 <= n))
                break label665;
              if (localItemInfo10 == null)
                break label790;
              int i36 = localItemInfo10.position;
              if ((i34 == i36) && (!localItemInfo10.scrolling))
              {
                this.mItems.remove(i9);
                PagerAdapter localPagerAdapter2 = this.mAdapter;
                Object localObject3 = localItemInfo10.object;
                localPagerAdapter2.destroyItem(this, i34, localObject3);
                if (i9 >= this.mItems.size())
                  break label659;
              }
            }
            for (ItemInfo localItemInfo10 = (ItemInfo)this.mItems.get(i9); ; localItemInfo10 = null)
            {
              i34++;
              break label540;
              localItemInfo10 = null;
              break;
            }
            if (localItemInfo10 != null)
            {
              int i35 = localItemInfo10.position;
              if (i34 == i35)
              {
                f3 += localItemInfo10.widthFactor;
                i9++;
                if (i9 < this.mItems.size());
                for (localItemInfo10 = (ItemInfo)this.mItems.get(i9); ; localItemInfo10 = null)
                  break;
              }
            }
            ItemInfo localItemInfo11 = addNewItem(i34, i9);
            i9++;
            f3 += localItemInfo11.widthFactor;
            if (i9 < this.mItems.size());
            for (localItemInfo10 = (ItemInfo)this.mItems.get(i9); ; localItemInfo10 = null)
              break;
          }
          int i10 = this.mAdapter.getCount();
          int i11 = getWidth();
          float f4;
          int i21;
          int i27;
          float f11;
          if (i11 > 0)
          {
            f4 = this.mPageMargin / i11;
            if (localItemInfo1 == null)
              break label1199;
            i21 = localItemInfo1.position;
            if (i21 < ((ItemInfo)localObject1).position)
            {
              i27 = 0;
              f11 = f4 + (localItemInfo1.offset + localItemInfo1.widthFactor);
            }
          }
          else
          {
            for (int i28 = i21 + 1; ; i28++)
            {
              int i29 = ((ItemInfo)localObject1).position;
              if (i28 > i29)
                break label1199;
              int i30 = this.mItems.size();
              if (i27 >= i30)
                break label1199;
              for (ItemInfo localItemInfo9 = (ItemInfo)this.mItems.get(i27); ; localItemInfo9 = (ItemInfo)this.mItems.get(i27))
              {
                int i31 = localItemInfo9.position;
                if (i28 <= i31)
                  break;
                int i33 = -1 + this.mItems.size();
                if (i27 >= i33)
                  break;
                i27++;
              }
              f4 = 0.0F;
              break;
              while (true)
              {
                int i32 = localItemInfo9.position;
                if (i28 >= i32)
                  break;
                f11 += 1.0F + f4;
                i28++;
              }
              float f12 = f11;
              localItemInfo9.offset = f12;
              f11 += f4 + localItemInfo9.widthFactor;
            }
          }
          if (i21 > ((ItemInfo)localObject1).position)
          {
            int i22 = -1 + this.mItems.size();
            float f9 = localItemInfo1.offset;
            for (int i23 = i21 - 1; ; i23--)
            {
              int i24 = ((ItemInfo)localObject1).position;
              if ((i23 < i24) || (i22 < 0))
                break;
              for (ItemInfo localItemInfo8 = (ItemInfo)this.mItems.get(i22); ; localItemInfo8 = (ItemInfo)this.mItems.get(i22))
              {
                int i25 = localItemInfo8.position;
                if ((i23 >= i25) || (i22 <= 0))
                  break;
                i22--;
              }
              while (true)
              {
                int i26 = localItemInfo8.position;
                if (i23 <= i26)
                  break;
                f9 -= 1.0F + f4;
                i23--;
              }
              f9 -= f4 + localItemInfo8.widthFactor;
              float f10 = f9;
              localItemInfo8.offset = f10;
            }
          }
          label1199: int i12 = this.mItems.size();
          float f5 = ((ItemInfo)localObject1).offset;
          int i13 = -1 + ((ItemInfo)localObject1).position;
          float f6;
          float f7;
          if (((ItemInfo)localObject1).position == 0)
          {
            f6 = ((ItemInfo)localObject1).offset;
            this.mFirstOffset = f6;
            if (((ItemInfo)localObject1).position != i10 - 1)
              break label1344;
            f7 = ((ItemInfo)localObject1).offset + ((ItemInfo)localObject1).widthFactor - 1.0F;
            label1272: this.mLastOffset = f7;
          }
          label1344: int i20;
          for (int i14 = i1 - 1; ; i14 = i20)
          {
            if (i14 < 0)
              break label1401;
            ItemInfo localItemInfo7 = (ItemInfo)this.mItems.get(i14);
            while (true)
            {
              int i19 = localItemInfo7.position;
              if (i13 <= i19)
                break;
              i13--;
              f5 -= 1.0F + f4;
            }
            f6 = -3.402824E+038F;
            break;
            f7 = 3.4028235E+38F;
            break label1272;
            f5 -= f4 + localItemInfo7.widthFactor;
            localItemInfo7.offset = f5;
            if (localItemInfo7.position == 0)
              this.mFirstOffset = f5;
            i20 = i14 - 1;
            i13--;
          }
          label1401: float f8 = f4 + (((ItemInfo)localObject1).offset + ((ItemInfo)localObject1).widthFactor);
          int i15 = 1 + ((ItemInfo)localObject1).position;
          int i18;
          for (int i16 = i1 + 1; i16 < i12; i16 = i18)
          {
            ItemInfo localItemInfo6 = (ItemInfo)this.mItems.get(i16);
            while (true)
            {
              int i17 = localItemInfo6.position;
              if (i15 >= i17)
                break;
              i15++;
              f8 += 1.0F + f4;
            }
            if (localItemInfo6.position == i10 - 1)
              this.mLastOffset = (f8 + localItemInfo6.widthFactor - 1.0F);
            localItemInfo6.offset = f8;
            f8 += f4 + localItemInfo6.widthFactor;
            i18 = i16 + 1;
            i15++;
          }
          this.mNeedCalculatePageOffsets = false;
        }
        PagerAdapter localPagerAdapter1 = this.mAdapter;
        int i3 = this.mCurItem;
        if (localObject1 != null);
        for (Object localObject2 = ((ItemInfo)localObject1).object; ; localObject2 = null)
        {
          localPagerAdapter1.setPrimaryItem(this, i3, localObject2);
          this.mAdapter.finishUpdate(this);
          int i4 = getChildCount();
          for (int i5 = 0; i5 < i4; i5++)
          {
            View localView3 = getChildAt(i5);
            LayoutParams localLayoutParams = (LayoutParams)localView3.getLayoutParams();
            if ((!localLayoutParams.isDecor) && (localLayoutParams.widthFactor == 0.0F))
            {
              ItemInfo localItemInfo4 = infoForChild(localView3);
              if (localItemInfo4 != null)
                localLayoutParams.widthFactor = localItemInfo4.widthFactor;
            }
          }
        }
        if (!hasFocus())
          break;
        View localView1 = findFocus();
        ViewParent localViewParent;
        ItemInfo localItemInfo2;
        if (localView1 != null)
        {
          localViewParent = localView1.getParent();
          if (localViewParent != this)
            if ((localViewParent == null) || (!(localViewParent instanceof View)))
              localItemInfo2 = null;
        }
        while (true)
        {
          if ((localItemInfo2 != null) && (localItemInfo2.position == this.mCurItem))
            break label1832;
          for (int i6 = 0; ; i6++)
          {
            if (i6 >= getChildCount())
              break label1805;
            View localView2 = getChildAt(i6);
            ItemInfo localItemInfo3 = infoForChild(localView2);
            if ((localItemInfo3 != null) && (localItemInfo3.position == this.mCurItem) && (localView2.requestFocus(2)))
              break;
          }
          break label33;
          localView1 = (View)localViewParent;
          break label1701;
          localItemInfo2 = infoForChild(localView1);
          continue;
          localItemInfo2 = null;
        }
      }
    }
  }

  private void recomputeScrollPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt2 > 0) && (!this.mItems.isEmpty()))
    {
      int j = paramInt1 + paramInt3;
      int k = paramInt2 + paramInt4;
      int m = (int)(getScrollX() / k * j);
      scrollTo(m, getScrollY());
      if (!this.mScroller.isFinished())
      {
        int n = this.mScroller.getDuration() - this.mScroller.timePassed();
        ItemInfo localItemInfo2 = infoForPosition(this.mCurItem);
        this.mScroller.startScroll(m, 0, (int)(localItemInfo2.offset * paramInt1), 0, n);
      }
      return;
    }
    ItemInfo localItemInfo1 = infoForPosition(this.mCurItem);
    if (localItemInfo1 != null);
    for (float f = Math.min(localItemInfo1.offset, this.mLastOffset); ; f = 0.0F)
    {
      int i = (int)(f * paramInt1);
      if (i == getScrollX())
        break;
      completeScroll();
      scrollTo(i, getScrollY());
      break;
    }
  }

  private void setCurrentItemInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    setCurrentItemInternal(paramInt, paramBoolean1, paramBoolean2, 0);
  }

  private void setCurrentItemInternal(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2)
  {
    if ((this.mAdapter == null) || (this.mAdapter.getCount() <= 0))
      setScrollingCacheEnabled(false);
    while (true)
    {
      return;
      if ((!paramBoolean2) && (this.mCurItem == paramInt1) && (this.mItems.size() != 0))
      {
        setScrollingCacheEnabled(false);
      }
      else
      {
        if (paramInt1 < 0)
          paramInt1 = 0;
        while (true)
        {
          int j = this.mOffscreenPageLimit;
          int k = j + this.mCurItem;
          if (paramInt1 <= k)
          {
            int i11 = this.mCurItem - j;
            if (paramInt1 >= i11)
              break;
          }
          for (int m = 0; m < this.mItems.size(); m++)
            ((ItemInfo)this.mItems.get(m)).scrolling = true;
          int i = this.mAdapter.getCount();
          if (paramInt1 >= i)
            paramInt1 = -1 + this.mAdapter.getCount();
        }
        int n;
        label171: int i1;
        if (this.mCurItem != paramInt1)
        {
          n = 1;
          populate(paramInt1);
          ItemInfo localItemInfo = infoForPosition(paramInt1);
          i1 = 0;
          if (localItemInfo != null)
            i1 = (int)(getWidth() * Math.max(this.mFirstOffset, Math.min(localItemInfo.offset, this.mLastOffset)));
          if (!paramBoolean1)
            break label520;
          if (getChildCount() != 0)
            break label288;
          setScrollingCacheEnabled(false);
        }
        label288: int i2;
        int i3;
        int i4;
        int i5;
        while (true)
        {
          if ((n != 0) && (this.mOnPageChangeListener != null))
            this.mOnPageChangeListener.onPageSelected(paramInt1);
          if ((n == 0) || (this.mInternalPageChangeListener == null))
            break;
          this.mInternalPageChangeListener.onPageSelected(paramInt1);
          break;
          n = 0;
          break label171;
          i2 = getScrollX();
          i3 = getScrollY();
          i4 = i1 - i2;
          i5 = 0 - i3;
          if ((i4 != 0) || (i5 != 0))
            break label339;
          completeScroll();
          populate();
          setScrollState(0);
        }
        label339: setScrollingCacheEnabled(true);
        setScrollState(2);
        int i6 = getWidth();
        int i7 = i6 / 2;
        float f1 = Math.min(1.0F, 1.0F * Math.abs(i4) / i6);
        float f2 = i7 + i7 * (float)Math.sin((float)(0.47123891676382D * (f1 - 0.5F)));
        int i8 = Math.abs(paramInt2);
        if (i8 > 0);
        float f4;
        for (int i9 = 4 * Math.round(1000.0F * Math.abs(f2 / i8)); ; i9 = (int)(100.0F * (1.0F + Math.abs(i4) / (f4 + this.mPageMargin))))
        {
          int i10 = Math.min(i9, 600);
          this.mScroller.startScroll(i2, i3, i4, i5, i10);
          ViewCompat.postInvalidateOnAnimation(this);
          break;
          float f3 = i6;
          f4 = f3 * 1.0F;
        }
        label520: if ((n != 0) && (this.mOnPageChangeListener != null))
          this.mOnPageChangeListener.onPageSelected(paramInt1);
        if ((n != 0) && (this.mInternalPageChangeListener != null))
          this.mInternalPageChangeListener.onPageSelected(paramInt1);
        completeScroll();
        scrollTo(i1, 0);
      }
    }
  }

  private void setScrollState(int paramInt)
  {
    if (this.mScrollState == paramInt);
    while (true)
    {
      return;
      this.mScrollState = paramInt;
      if (this.mOnPageChangeListener != null)
        this.mOnPageChangeListener.onPageScrollStateChanged(paramInt);
    }
  }

  private void setScrollingCacheEnabled(boolean paramBoolean)
  {
    if (this.mScrollingCacheEnabled != paramBoolean)
      this.mScrollingCacheEnabled = paramBoolean;
  }

  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    int i = paramArrayList.size();
    int j = getDescendantFocusability();
    if (j != 393216)
      for (int k = 0; k < getChildCount(); k++)
      {
        View localView = getChildAt(k);
        if (localView.getVisibility() == 0)
        {
          ItemInfo localItemInfo = infoForChild(localView);
          if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem))
            localView.addFocusables(paramArrayList, paramInt1, paramInt2);
        }
      }
    if (((j == 262144) && (i != paramArrayList.size())) || (!isFocusable()));
    while (true)
    {
      return;
      if ((((paramInt2 & 0x1) != 1) || (!isInTouchMode()) || (isFocusableInTouchMode())) && (paramArrayList != null))
        paramArrayList.add(this);
    }
  }

  public void addTouchables(ArrayList<View> paramArrayList)
  {
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem))
          localView.addTouchables(paramArrayList);
      }
    }
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (!checkLayoutParams(paramLayoutParams))
      paramLayoutParams = generateLayoutParams(paramLayoutParams);
    LayoutParams localLayoutParams = (LayoutParams)paramLayoutParams;
    localLayoutParams.isDecor |= paramView instanceof Decor;
    if (this.mInLayout)
    {
      if ((localLayoutParams != null) && (localLayoutParams.isDecor))
        throw new IllegalStateException("Cannot add pager decor view during layout");
      localLayoutParams.needsMeasure = true;
      addViewInLayout(paramView, paramInt, paramLayoutParams);
    }
    while (true)
    {
      return;
      super.addView(paramView, paramInt, paramLayoutParams);
    }
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if (((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void computeScroll()
  {
    if ((!this.mScroller.isFinished()) && (this.mScroller.computeScrollOffset()))
    {
      int i = getScrollX();
      int j = getScrollY();
      int k = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      if ((i != k) || (j != m))
      {
        scrollTo(k, m);
        if (!pageScrolled(k))
        {
          this.mScroller.abortAnimation();
          scrollTo(0, m);
        }
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
    while (true)
    {
      return;
      completeScroll();
    }
  }

  final void dataSetChanged()
  {
    int i;
    int j;
    int k;
    int m;
    label47: ItemInfo localItemInfo;
    int i2;
    if ((this.mItems.size() < 1 + 2 * this.mOffscreenPageLimit) && (this.mItems.size() < this.mAdapter.getCount()))
    {
      i = 1;
      j = this.mCurItem;
      k = 0;
      m = 0;
      if (m >= this.mItems.size())
        break label227;
      localItemInfo = (ItemInfo)this.mItems.get(m);
      i2 = this.mAdapter.getItemPosition(localItemInfo.object);
      if (i2 != -1)
      {
        if (i2 != -2)
          break label190;
        this.mItems.remove(m);
        m--;
        if (k == 0)
          k = 1;
        this.mAdapter.destroyItem(this, localItemInfo.position, localItemInfo.object);
        i = 1;
        if (this.mCurItem == localItemInfo.position)
        {
          j = Math.max(0, Math.min(this.mCurItem, -1 + this.mAdapter.getCount()));
          i = 1;
        }
      }
    }
    while (true)
    {
      m++;
      break label47;
      i = 0;
      break;
      label190: if (localItemInfo.position != i2)
      {
        if (localItemInfo.position == this.mCurItem)
          j = i2;
        localItemInfo.position = i2;
        i = 1;
      }
    }
    label227: if (k != 0)
      this.mAdapter.finishUpdate(this);
    Collections.sort(this.mItems, COMPARATOR);
    if (i != 0)
    {
      int n = getChildCount();
      for (int i1 = 0; i1 < n; i1++)
      {
        LayoutParams localLayoutParams = (LayoutParams)getChildAt(i1).getLayoutParams();
        if (!localLayoutParams.isDecor)
          localLayoutParams.widthFactor = 0.0F;
      }
      setCurrentItemInternal(j, false, true);
      requestLayout();
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool2;
    if (!super.dispatchKeyEvent(paramKeyEvent))
    {
      if (paramKeyEvent.getAction() == 0);
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        bool2 = false;
      case 21:
      case 22:
      case 61:
      }
    }
    while (true)
    {
      boolean bool1 = false;
      if (bool2)
        bool1 = true;
      return bool1;
      bool2 = arrowScroll(17);
      continue;
      bool2 = arrowScroll(66);
      continue;
      if (Build.VERSION.SDK_INT < 11)
        break;
      if (KeyEventCompat.hasNoModifiers(paramKeyEvent))
      {
        bool2 = arrowScroll(2);
      }
      else
      {
        if (!KeyEventCompat.hasModifiers(paramKeyEvent, 1))
          break;
        bool2 = arrowScroll(1);
      }
    }
  }

  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo == null) || (localItemInfo.position != this.mCurItem) || (!localView.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent)));
      }
    }
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      j++;
      break;
    }
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i = ViewCompat.getOverScrollMode(this);
    boolean bool2;
    if ((i == 0) || ((i == 1) && (this.mAdapter != null) && (this.mAdapter.getCount() > 1)))
    {
      boolean bool1 = this.mLeftEdge.isFinished();
      bool2 = false;
      if (!bool1)
      {
        int n = paramCanvas.save();
        int i1 = getHeight() - getPaddingTop() - getPaddingBottom();
        int i2 = getWidth();
        paramCanvas.rotate(270.0F);
        paramCanvas.translate(-i1 + getPaddingTop(), this.mFirstOffset * i2);
        this.mLeftEdge.setSize(i1, i2);
        bool2 = false | this.mLeftEdge.draw(paramCanvas);
        paramCanvas.restoreToCount(n);
      }
      if (!this.mRightEdge.isFinished())
      {
        int j = paramCanvas.save();
        int k = getWidth();
        int m = getHeight() - getPaddingTop() - getPaddingBottom();
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-getPaddingTop(), -(1.0F + this.mLastOffset) * k);
        this.mRightEdge.setSize(m, k);
        bool2 |= this.mRightEdge.draw(paramCanvas);
        paramCanvas.restoreToCount(j);
      }
    }
    while (true)
    {
      if (bool2)
        ViewCompat.postInvalidateOnAnimation(this);
      return;
      this.mLeftEdge.finish();
      this.mRightEdge.finish();
      bool2 = false;
    }
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Drawable localDrawable = this.mMarginDrawable;
    if ((localDrawable != null) && (localDrawable.isStateful()))
      localDrawable.setState(getDrawableState());
  }

  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return generateDefaultLayoutParams();
  }

  public final PagerAdapter getAdapter()
  {
    return this.mAdapter;
  }

  public final int getCurrentItem()
  {
    return this.mCurItem;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mPageMargin > 0) && (this.mMarginDrawable != null) && (this.mItems.size() > 0) && (this.mAdapter != null))
    {
      int i = getScrollX();
      int j = getWidth();
      float f1 = this.mPageMargin / j;
      int k = 0;
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(0);
      float f2 = localItemInfo.offset;
      int m = this.mItems.size();
      int n = localItemInfo.position;
      int i1 = ((ItemInfo)this.mItems.get(m - 1)).position;
      int i2 = n;
      if (i2 < i1)
      {
        while ((i2 > localItemInfo.position) && (k < m))
        {
          ArrayList localArrayList = this.mItems;
          k++;
          localItemInfo = (ItemInfo)localArrayList.get(k);
        }
        float f3;
        if (i2 == localItemInfo.position)
          f3 = (localItemInfo.offset + localItemInfo.widthFactor) * j;
        for (f2 = f1 + (localItemInfo.offset + localItemInfo.widthFactor); ; f2 += 1.0F + f1)
        {
          if (f3 + this.mPageMargin > i)
          {
            this.mMarginDrawable.setBounds((int)f3, this.mTopPageBounds, (int)(0.5F + (f3 + this.mPageMargin)), this.mBottomPageBounds);
            this.mMarginDrawable.draw(paramCanvas);
          }
          if (f3 > i + j)
            return;
          i2++;
          break;
          f3 = (1.0F + f2) * j;
        }
      }
    }
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 0xFF & paramMotionEvent.getAction();
    boolean bool;
    if ((i == 3) || (i == 1))
    {
      this.mIsBeingDragged = false;
      this.mIsUnableToDrag = false;
      this.mActivePointerId = -1;
      if (this.mVelocityTracker != null)
      {
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
      }
      bool = false;
    }
    while (true)
    {
      return bool;
      if (i == 0)
        break;
      if (this.mIsBeingDragged)
      {
        bool = true;
      }
      else
      {
        if (!this.mIsUnableToDrag)
          break;
        bool = false;
      }
    }
    switch (i)
    {
    default:
    case 2:
    case 0:
    case 6:
    }
    while (true)
    {
      if (this.mVelocityTracker == null)
        this.mVelocityTracker = VelocityTracker.obtain();
      this.mVelocityTracker.addMovement(paramMotionEvent);
      bool = this.mIsBeingDragged;
      break;
      int j = this.mActivePointerId;
      if (j != -1)
      {
        int k = MotionEventCompat.findPointerIndex(paramMotionEvent, j);
        float f2 = MotionEventCompat.getX(paramMotionEvent, k);
        float f3 = f2 - this.mLastMotionX;
        float f4 = Math.abs(f3);
        float f5 = MotionEventCompat.getY(paramMotionEvent, k);
        float f6 = Math.abs(f5 - this.mLastMotionY);
        if (f3 != 0.0F)
        {
          float f8 = this.mLastMotionX;
          if (((f8 < this.mGutterSize) && (f3 > 0.0F)) || ((f8 > getWidth() - this.mGutterSize) && (f3 < 0.0F)));
          for (int m = 1; ; m = 0)
          {
            if ((m != 0) || (!canScroll(this, false, (int)f3, (int)f2, (int)f5)))
              break label328;
            this.mLastMotionX = f2;
            this.mInitialMotionX = f2;
            this.mLastMotionY = f5;
            this.mIsUnableToDrag = true;
            bool = false;
            break;
          }
        }
        label328: float f7;
        if ((f4 > this.mTouchSlop) && (f4 > f6))
        {
          this.mIsBeingDragged = true;
          setScrollState(1);
          if (f3 > 0.0F)
          {
            f7 = this.mInitialMotionX + this.mTouchSlop;
            label376: this.mLastMotionX = f7;
            setScrollingCacheEnabled(true);
          }
        }
        while ((this.mIsBeingDragged) && (performDrag(f2)))
        {
          ViewCompat.postInvalidateOnAnimation(this);
          break;
          f7 = this.mInitialMotionX - this.mTouchSlop;
          break label376;
          if (f6 > this.mTouchSlop)
            this.mIsUnableToDrag = true;
        }
        float f1 = paramMotionEvent.getX();
        this.mInitialMotionX = f1;
        this.mLastMotionX = f1;
        this.mLastMotionY = paramMotionEvent.getY();
        this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
        this.mIsUnableToDrag = false;
        this.mScroller.computeScrollOffset();
        if ((this.mScrollState == 2) && (Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough))
        {
          this.mScroller.abortAnimation();
          this.mPopulatePending = false;
          populate();
          this.mIsBeingDragged = true;
          setScrollState(1);
        }
        else
        {
          completeScroll();
          this.mIsBeingDragged = false;
          continue;
          onSecondaryPointerUp(paramMotionEvent);
        }
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mInLayout = true;
    populate();
    this.mInLayout = false;
    int i = getChildCount();
    int j = paramInt3 - paramInt1;
    int k = paramInt4 - paramInt2;
    int m = getPaddingLeft();
    int n = getPaddingTop();
    int i1 = getPaddingRight();
    int i2 = getPaddingBottom();
    int i3 = getScrollX();
    int i4 = 0;
    int i5 = 0;
    if (i5 < i)
    {
      View localView2 = getChildAt(i5);
      int i11;
      label172: int i12;
      if (localView2.getVisibility() != 8)
      {
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        if (localLayoutParams2.isDecor)
        {
          int i9 = 0x7 & localLayoutParams2.gravity;
          int i10 = 0x70 & localLayoutParams2.gravity;
          switch (i9)
          {
          case 2:
          case 4:
          default:
            i11 = m;
            switch (i10)
            {
            default:
              i12 = n;
            case 48:
            case 16:
            case 80:
            }
            break;
          case 3:
          case 1:
          case 5:
          }
        }
      }
      while (true)
      {
        int i13 = i11 + i3;
        localView2.layout(i13, i12, i13 + localView2.getMeasuredWidth(), i12 + localView2.getMeasuredHeight());
        i4++;
        i5++;
        break;
        i11 = m;
        m += localView2.getMeasuredWidth();
        break label172;
        i11 = Math.max((j - localView2.getMeasuredWidth()) / 2, m);
        break label172;
        i11 = j - i1 - localView2.getMeasuredWidth();
        i1 += localView2.getMeasuredWidth();
        break label172;
        i12 = n;
        n += localView2.getMeasuredHeight();
        continue;
        i12 = Math.max((k - localView2.getMeasuredHeight()) / 2, n);
        continue;
        i12 = k - i2 - localView2.getMeasuredHeight();
        i2 += localView2.getMeasuredHeight();
      }
    }
    for (int i6 = 0; i6 < i; i6++)
    {
      View localView1 = getChildAt(i6);
      if (localView1.getVisibility() != 8)
      {
        LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
        if (!localLayoutParams1.isDecor)
        {
          ItemInfo localItemInfo = infoForChild(localView1);
          if (localItemInfo != null)
          {
            int i7 = m + (int)(j * localItemInfo.offset);
            int i8 = n;
            if (localLayoutParams1.needsMeasure)
            {
              localLayoutParams1.needsMeasure = false;
              localView1.measure(View.MeasureSpec.makeMeasureSpec((int)((j - m - i1) * localLayoutParams1.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec(k - n - i2, 1073741824));
            }
            localView1.layout(i7, i8, i7 + localView1.getMeasuredWidth(), i8 + localView1.getMeasuredHeight());
          }
        }
      }
    }
    this.mTopPageBounds = n;
    this.mBottomPageBounds = (k - i2);
    this.mDecorChildCount = i4;
    this.mFirstLayout = false;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(getDefaultSize(0, paramInt1), getDefaultSize(0, paramInt2));
    int i = getMeasuredWidth();
    this.mGutterSize = Math.min(i / 10, this.mDefaultGutterSize);
    int j = i - getPaddingLeft() - getPaddingRight();
    int k = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    int m = getChildCount();
    int n = 0;
    if (n < m)
    {
      View localView2 = getChildAt(n);
      int i6;
      int i7;
      label167: int i8;
      if (localView2.getVisibility() != 8)
      {
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        if ((localLayoutParams2 != null) && (localLayoutParams2.isDecor))
        {
          int i3 = 0x7 & localLayoutParams2.gravity;
          int i4 = 0x70 & localLayoutParams2.gravity;
          int i5 = -2147483648;
          i6 = -2147483648;
          if ((i4 != 48) && (i4 != 80))
            break label302;
          i7 = 1;
          if ((i3 != 3) && (i3 != 5))
            break label308;
          i8 = 1;
          label182: if (i7 == 0)
            break label314;
          i5 = 1073741824;
          label192: int i9 = j;
          int i10 = k;
          if (localLayoutParams2.width != -2)
          {
            i5 = 1073741824;
            if (localLayoutParams2.width != -1)
              i9 = localLayoutParams2.width;
          }
          if (localLayoutParams2.height != -2)
          {
            i6 = 1073741824;
            if (localLayoutParams2.height != -1)
              i10 = localLayoutParams2.height;
          }
          localView2.measure(View.MeasureSpec.makeMeasureSpec(i9, i5), View.MeasureSpec.makeMeasureSpec(i10, i6));
          if (i7 == 0)
            break label327;
          k -= localView2.getMeasuredHeight();
        }
      }
      while (true)
      {
        n++;
        break;
        label302: i7 = 0;
        break label167;
        label308: i8 = 0;
        break label182;
        label314: if (i8 == 0)
          break label192;
        i6 = 1073741824;
        break label192;
        label327: if (i8 != 0)
          j -= localView2.getMeasuredWidth();
      }
    }
    this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
    this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(k, 1073741824);
    this.mInLayout = true;
    populate();
    this.mInLayout = false;
    int i1 = getChildCount();
    for (int i2 = 0; i2 < i1; i2++)
    {
      View localView1 = getChildAt(i2);
      if (localView1.getVisibility() != 8)
      {
        LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
        if ((localLayoutParams1 == null) || (!localLayoutParams1.isDecor))
          localView1.measure(View.MeasureSpec.makeMeasureSpec((int)(j * localLayoutParams1.widthFactor), 1073741824), this.mChildHeightMeasureSpec);
      }
    }
  }

  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    int i = getChildCount();
    int j;
    int k;
    int m;
    int n;
    if ((paramInt & 0x2) != 0)
    {
      j = 0;
      k = 1;
      m = i;
      n = j;
      label24: if (n == m)
        break label112;
      View localView = getChildAt(n);
      if (localView.getVisibility() != 0)
        break label102;
      ItemInfo localItemInfo = infoForChild(localView);
      if ((localItemInfo == null) || (localItemInfo.position != this.mCurItem) || (!localView.requestFocus(paramInt, paramRect)))
        break label102;
    }
    label102: label112: for (boolean bool = true; ; bool = false)
    {
      return bool;
      j = i - 1;
      k = -1;
      m = -1;
      break;
      n += k;
      break label24;
    }
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
      super.onRestoreInstanceState(paramParcelable);
    while (true)
    {
      return;
      SavedState localSavedState = (SavedState)paramParcelable;
      super.onRestoreInstanceState(localSavedState.getSuperState());
      if (this.mAdapter != null)
      {
        this.mAdapter.restoreState(localSavedState.adapterState, localSavedState.loader);
        setCurrentItemInternal(localSavedState.position, false, true);
      }
      else
      {
        this.mRestoredCurItem = localSavedState.position;
        this.mRestoredAdapterState = localSavedState.adapterState;
        this.mRestoredClassLoader = localSavedState.loader;
      }
    }
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.position = this.mCurItem;
    if (this.mAdapter != null)
      localSavedState.adapterState = this.mAdapter.saveState();
    return localSavedState;
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3)
      recomputeScrollPosition(paramInt1, paramInt3, this.mPageMargin, this.mPageMargin);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (paramMotionEvent.getEdgeFlags() != 0));
    for (boolean bool1 = false; ; bool1 = false)
    {
      return bool1;
      if ((this.mAdapter != null) && (this.mAdapter.getCount() != 0))
        break;
    }
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain();
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = 0xFF & paramMotionEvent.getAction();
    boolean bool2 = false;
    label343: int k;
    int i1;
    float f1;
    label499: ItemInfo localItemInfo2;
    ItemInfo localItemInfo3;
    switch (i)
    {
    case 4:
    default:
    case 0:
    case 2:
    case 1:
      boolean bool4;
      do
      {
        while (true)
        {
          if (bool2)
            ViewCompat.postInvalidateOnAnimation(this);
          bool1 = true;
          break;
          this.mScroller.abortAnimation();
          this.mPopulatePending = false;
          populate();
          this.mIsBeingDragged = true;
          setScrollState(1);
          float f6 = paramMotionEvent.getX();
          this.mInitialMotionX = f6;
          this.mLastMotionX = f6;
          this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
          bool2 = false;
        }
        if (!this.mIsBeingDragged)
        {
          int i3 = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId);
          float f2 = MotionEventCompat.getX(paramMotionEvent, i3);
          float f3 = Math.abs(f2 - this.mLastMotionX);
          float f4 = Math.abs(MotionEventCompat.getY(paramMotionEvent, i3) - this.mLastMotionY);
          if ((f3 > this.mTouchSlop) && (f3 > f4))
          {
            this.mIsBeingDragged = true;
            if (f2 - this.mInitialMotionX <= 0.0F)
              break label343;
          }
        }
        for (float f5 = this.mInitialMotionX + this.mTouchSlop; ; f5 = this.mInitialMotionX - this.mTouchSlop)
        {
          this.mLastMotionX = f5;
          setScrollState(1);
          setScrollingCacheEnabled(true);
          boolean bool5 = this.mIsBeingDragged;
          bool2 = false;
          if (!bool5)
            break;
          bool2 = false | performDrag(MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId)));
          break;
        }
        bool4 = this.mIsBeingDragged;
        bool2 = false;
      }
      while (!bool4);
      VelocityTracker localVelocityTracker = this.mVelocityTracker;
      localVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
      k = (int)VelocityTrackerCompat.getXVelocity(localVelocityTracker, this.mActivePointerId);
      this.mPopulatePending = true;
      int m = getWidth();
      int n = getScrollX();
      ItemInfo localItemInfo1 = infoForCurrentScrollPosition();
      i1 = localItemInfo1.position;
      f1 = (n / m - localItemInfo1.offset) / localItemInfo1.widthFactor;
      if ((Math.abs((int)(MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId)) - this.mInitialMotionX)) > this.mFlingDistance) && (Math.abs(k) > this.mMinimumVelocity))
        if (k > 0)
        {
          if (this.mItems.size() <= 0)
            break label736;
          localItemInfo2 = (ItemInfo)this.mItems.get(0);
          localItemInfo3 = (ItemInfo)this.mItems.get(-1 + this.mItems.size());
        }
      break;
    case 3:
    case 5:
    case 6:
    }
    label736: for (int i2 = Math.max(localItemInfo2.position, Math.min(i1, localItemInfo3.position)); ; i2 = i1)
    {
      setCurrentItemInternal(i2, true, true, k);
      this.mActivePointerId = -1;
      endDrag();
      bool2 = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
      break;
      i1++;
      break label499;
      i1 = (int)(0.5F + (f1 + i1));
      break label499;
      boolean bool3 = this.mIsBeingDragged;
      bool2 = false;
      if (!bool3)
        break;
      setCurrentItemInternal(this.mCurItem, true, true);
      this.mActivePointerId = -1;
      endDrag();
      bool2 = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
      break;
      int j = MotionEventCompat.getActionIndex(paramMotionEvent);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, j);
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, j);
      bool2 = false;
      break;
      onSecondaryPointerUp(paramMotionEvent);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
      bool2 = false;
      break;
    }
  }

  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    if (this.mAdapter != null)
    {
      this.mAdapter.unregisterDataSetObserver(this.mObserver);
      for (int i = 0; i < this.mItems.size(); i++)
      {
        ItemInfo localItemInfo = (ItemInfo)this.mItems.get(i);
        this.mAdapter.destroyItem(this, localItemInfo.position, localItemInfo.object);
      }
      this.mAdapter.finishUpdate(this);
      this.mItems.clear();
      for (int j = 0; j < getChildCount(); j++)
        if (!((LayoutParams)getChildAt(j).getLayoutParams()).isDecor)
        {
          removeViewAt(j);
          j--;
        }
      this.mCurItem = 0;
      scrollTo(0, 0);
    }
    PagerAdapter localPagerAdapter = this.mAdapter;
    this.mAdapter = paramPagerAdapter;
    if (this.mAdapter != null)
    {
      if (this.mObserver == null)
        this.mObserver = new PagerObserver((byte)0);
      this.mAdapter.registerDataSetObserver(this.mObserver);
      this.mPopulatePending = false;
      this.mFirstLayout = true;
      if (this.mRestoredCurItem < 0)
        break label276;
      this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
      setCurrentItemInternal(this.mRestoredCurItem, false, true);
      this.mRestoredCurItem = -1;
      this.mRestoredAdapterState = null;
      this.mRestoredClassLoader = null;
    }
    while (true)
    {
      if ((this.mAdapterChangeListener != null) && (localPagerAdapter != paramPagerAdapter))
        this.mAdapterChangeListener.onAdapterChanged(localPagerAdapter, paramPagerAdapter);
      return;
      label276: populate();
    }
  }

  public void setCurrentItem(int paramInt)
  {
    this.mPopulatePending = false;
    if (!this.mFirstLayout);
    for (boolean bool = true; ; bool = false)
    {
      setCurrentItemInternal(paramInt, bool, false);
      return;
    }
  }

  public void setCurrentItem(int paramInt, boolean paramBoolean)
  {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, paramBoolean, false);
  }

  final OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    OnPageChangeListener localOnPageChangeListener = this.mInternalPageChangeListener;
    this.mInternalPageChangeListener = paramOnPageChangeListener;
    return localOnPageChangeListener;
  }

  public void setOffscreenPageLimit(int paramInt)
  {
    if (paramInt <= 0)
    {
      Log.w("ViewPager", "Requested offscreen page limit " + paramInt + " too small; defaulting to 1");
      paramInt = 1;
    }
    if (paramInt != this.mOffscreenPageLimit)
    {
      this.mOffscreenPageLimit = paramInt;
      populate();
    }
  }

  final void setOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener)
  {
    this.mAdapterChangeListener = paramOnAdapterChangeListener;
  }

  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    this.mOnPageChangeListener = paramOnPageChangeListener;
  }

  public void setPageMargin(int paramInt)
  {
    int i = this.mPageMargin;
    this.mPageMargin = paramInt;
    int j = getWidth();
    recomputeScrollPosition(j, j, paramInt, i);
    requestLayout();
  }

  public void setPageMarginDrawable(int paramInt)
  {
    setPageMarginDrawable(getContext().getResources().getDrawable(paramInt));
  }

  public void setPageMarginDrawable(Drawable paramDrawable)
  {
    this.mMarginDrawable = paramDrawable;
    if (paramDrawable != null)
      refreshDrawableState();
    if (paramDrawable == null);
    for (boolean bool = true; ; bool = false)
    {
      setWillNotDraw(bool);
      invalidate();
      return;
    }
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if ((super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mMarginDrawable));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  static abstract interface Decor
  {
  }

  static final class ItemInfo
  {
    Object object;
    float offset;
    int position;
    boolean scrolling;
    float widthFactor;
  }

  public static class LayoutParams extends ViewGroup.LayoutParams
  {
    public int gravity;
    public boolean isDecor;
    public boolean needsMeasure;
    public float widthFactor = 0.0F;

    public LayoutParams()
    {
      super(-1);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, ViewPager.LAYOUT_ATTRS);
      this.gravity = localTypedArray.getInteger(0, 48);
      localTypedArray.recycle();
    }
  }

  final class MyAccessibilityDelegate extends AccessibilityDelegateCompat
  {
    MyAccessibilityDelegate()
    {
    }

    public final void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(ViewPager.class.getName());
    }

    public final void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      int i = 1;
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramAccessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
      if ((ViewPager.this.mAdapter != null) && (ViewPager.this.mAdapter.getCount() > i));
      while (true)
      {
        paramAccessibilityNodeInfoCompat.setScrollable(i);
        if ((ViewPager.this.mAdapter != null) && (ViewPager.this.mCurItem >= 0) && (ViewPager.this.mCurItem < -1 + ViewPager.this.mAdapter.getCount()))
          paramAccessibilityNodeInfoCompat.addAction(4096);
        if ((ViewPager.this.mAdapter != null) && (ViewPager.this.mCurItem > 0) && (ViewPager.this.mCurItem < ViewPager.this.mAdapter.getCount()))
          paramAccessibilityNodeInfoCompat.addAction(8192);
        return;
        int j = 0;
      }
    }

    public final boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
    {
      boolean bool = true;
      if (super.performAccessibilityAction(paramView, paramInt, paramBundle));
      while (true)
      {
        return bool;
        switch (paramInt)
        {
        default:
          bool = false;
          break;
        case 4096:
          if ((ViewPager.this.mAdapter != null) && (ViewPager.this.mCurItem >= 0) && (ViewPager.this.mCurItem < -1 + ViewPager.this.mAdapter.getCount()))
            ViewPager.this.setCurrentItem(1 + ViewPager.this.mCurItem);
          else
            bool = false;
          break;
        case 8192:
          if ((ViewPager.this.mAdapter != null) && (ViewPager.this.mCurItem > 0) && (ViewPager.this.mCurItem < ViewPager.this.mAdapter.getCount()))
            ViewPager.this.setCurrentItem(-1 + ViewPager.this.mCurItem);
          else
            bool = false;
          break;
        }
      }
    }
  }

  static abstract interface OnAdapterChangeListener
  {
    public abstract void onAdapterChanged(PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2);
  }

  public static abstract interface OnPageChangeListener
  {
    public abstract void onPageScrollStateChanged(int paramInt);

    public abstract void onPageScrolled$486775f1(int paramInt, float paramFloat);

    public abstract void onPageSelected(int paramInt);
  }

  private final class PagerObserver extends DataSetObserver
  {
    private PagerObserver()
    {
    }

    public final void onChanged()
    {
      ViewPager.this.dataSetChanged();
    }

    public final void onInvalidated()
    {
      ViewPager.this.dataSetChanged();
    }
  }

  public static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks()
    {
    });
    Parcelable adapterState;
    ClassLoader loader;
    int position;

    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super();
      if (paramClassLoader == null)
        paramClassLoader = getClass().getClassLoader();
      this.position = paramParcel.readInt();
      this.adapterState = paramParcel.readParcelable(paramClassLoader);
      this.loader = paramClassLoader;
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.position);
      paramParcel.writeParcelable(this.adapterState, paramInt);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewPager
 * JD-Core Version:    0.6.2
 */