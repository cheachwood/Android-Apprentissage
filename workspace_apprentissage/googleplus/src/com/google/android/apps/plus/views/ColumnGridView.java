package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.Scroller;
import com.google.android.apps.plus.R.styleable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class ColumnGridView extends ViewGroup
{
  private int mActivePointerId;
  private ListAdapter mAdapter;
  private Bug6713624LinkedHashMap<String, Integer> mBug6713624LinkedHashMap = new Bug6713624LinkedHashMap((byte)0);
  private int mColCount = 2;
  private int mColCountSetting = 2;
  private final Point mCurrentTouchPoint = new Point(-1, -1);
  private boolean mDataChanged;
  private final EdgeEffectCompat mEndEdge;
  private int mFirstPosition;
  private final int mFlingVelocity;
  private boolean mHasStableIds;
  private boolean mHorizontalOrientation;
  private boolean mInLayout;
  private int mItemCount;
  private int[] mItemEnd;
  private int mItemMargin;
  private int[] mItemStart;
  private int mLastScrollState;
  private float mLastTouch;
  private final SparseArrayCompat<LayoutRecord> mLayoutRecords = new SparseArrayCompat();
  private int[] mLocation = new int[2];
  private final int mMaximumVelocity;
  private int mMinColWidth = 0;
  private final AdapterDataSetObserver mObserver = new AdapterDataSetObserver((byte)0);
  private OnScrollListener mOnScrollListener;
  private boolean mPopulating;
  private boolean mPressed;
  private float mRatio = 1.0F;
  private final RecycleBin mRecycler = new RecycleBin((byte)0);
  private int mRestoreOffset;
  private int mScrollState;
  private final Scroller mScroller;
  private final SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
  private ItemSelectionListener mSelectionListener;
  private boolean mSelectionMode;
  private final Point mSelectionStartPoint = new Point();
  private Drawable mSelector;
  private Runnable mSetPressedRunnable = new Runnable()
  {
    public final void run()
    {
      ColumnGridView.access$302(ColumnGridView.this, true);
      ColumnGridView.this.invalidate();
    }
  };
  private final EdgeEffectCompat mStartEdge;
  private float mTouchRemainder;
  private final int mTouchSlop;
  private final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
  private int mVisibleOffset;

  public ColumnGridView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ColumnGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public ColumnGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mScroller = new Scroller(paramContext);
    this.mStartEdge = new EdgeEffectCompat(paramContext);
    this.mEndEdge = new EdgeEffectCompat(paramContext);
    setWillNotDraw(false);
    setClipToPadding(false);
  }

  private void checkForSelection(int paramInt1, int paramInt2)
  {
    if (!this.mSelectionMode);
    while (true)
    {
      return;
      int i = this.mSelectionStartPoint.x - paramInt1;
      int j = this.mSelectionStartPoint.y - paramInt2;
      if (i * i + j * j < this.mTouchSlop * this.mTouchSlop)
      {
        int k = this.mFirstPosition;
        int m = 0;
        int n = -1 + getChildCount();
        if (n >= 0)
        {
          View localView = getChildAt(n);
          localView.getLocationOnScreen(this.mLocation);
          int i1;
          if ((paramInt1 >= this.mLocation[0]) && (paramInt1 <= this.mLocation[0] + localView.getWidth()) && (paramInt2 >= this.mLocation[1]) && (paramInt2 <= this.mLocation[1] + localView.getHeight()))
          {
            i1 = n + k;
            if (!isSelected(i1))
              break label172;
            deselect(i1);
          }
          while (true)
          {
            m = 1;
            n--;
            break;
            label172: select(i1);
          }
        }
        if (m != 0)
          invalidate();
      }
    }
  }

  private void clearAllState()
  {
    this.mBug6713624LinkedHashMap.put("clearallstate - clear", Integer.valueOf(0));
    this.mLayoutRecords.clear();
    removeAllViews();
    resetStateForGridTop();
    this.mRecycler.clear();
  }

  private void clearPressedState()
  {
    if (this.mPressed)
      invalidate();
    this.mPressed = false;
    removeCallbacks(this.mSetPressedRunnable);
  }

  private int fillDown(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    int k;
    int m;
    label42: int n;
    int i1;
    if (this.mHorizontalOrientation)
    {
      i = getPaddingTop();
      j = this.mItemMargin;
      k = getColumnSize();
      if (!this.mHorizontalOrientation)
        break label462;
      m = getWidth() - getPaddingRight();
      n = m + paramInt2;
      i1 = getNextColumnDown(this.mItemEnd);
    }
    for (int i2 = paramInt1; ; i2++)
    {
      if ((i1 < 0) || (this.mItemEnd[i1] >= n))
        break label916;
      int i6 = this.mItemCount;
      if (i2 >= i6)
        break label916;
      View localView = obtainView(i2, null);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      label136: int i7;
      int i8;
      LayoutRecord localLayoutRecord;
      label196: int i9;
      label259: int i12;
      int i11;
      if (localView.getParent() != this)
      {
        if (this.mInLayout)
          addViewInLayout(localView, -1, localLayoutParams);
      }
      else
      {
        i7 = Math.min(this.mColCount, localLayoutParams.minorSpan);
        i8 = k * i7 + j * (i7 - 1);
        if (i7 <= 1)
          break label485;
        int[] arrayOfInt2 = this.mItemEnd;
        localLayoutRecord = getNextRecordDown(i2, i7, arrayOfInt2);
        i1 = localLayoutRecord.column;
        i9 = 0;
        if (localLayoutRecord != null)
          break label518;
        localLayoutRecord = new LayoutRecord((byte)0);
        this.mBug6713624LinkedHashMap.put("filldown - put", Integer.valueOf(i2));
        this.mLayoutRecords.put(i2, localLayoutRecord);
        int i10 = i1;
        localLayoutRecord.column = i10;
        localLayoutRecord.span = i7;
        if (this.mHasStableIds)
        {
          long l = this.mAdapter.getItemId(i2);
          localLayoutRecord.id = l;
          localLayoutParams.id = l;
        }
        localLayoutParams.column = i1;
        if (!this.mHorizontalOrientation)
          break label627;
        i12 = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
        if (localLayoutParams.width != -2)
          break label565;
        i11 = View.MeasureSpec.makeMeasureSpec(0, 0);
        label334: localView.measure(i11, i12);
        if (!this.mHorizontalOrientation)
          break label719;
      }
      int i23;
      label462: label719: for (int i13 = localView.getMeasuredWidth(); ; i13 = localView.getMeasuredHeight())
      {
        if ((i9 != 0) || ((i13 != localLayoutRecord.size) && (localLayoutRecord.size > 0)))
          invalidateLayoutRecordsAfterPosition(i2);
        localLayoutRecord.size = i13;
        if (i7 <= 1)
          break label845;
        i23 = this.mItemEnd[i1];
        for (int i24 = i1 + 1; ; i24++)
        {
          int i25 = i1 + i7;
          if (i24 >= i25)
            break;
          int i26 = this.mItemEnd[i24];
          if (i26 > i23)
            i23 = i26;
        }
        i = getPaddingLeft();
        break;
        m = getHeight() - getPaddingBottom();
        break label42;
        addView(localView);
        break label136;
        label485: this.mBug6713624LinkedHashMap.put("filldown - get", Integer.valueOf(i2));
        localLayoutRecord = (LayoutRecord)this.mLayoutRecords.get(i2);
        break label196;
        if (i7 != localLayoutRecord.span)
        {
          localLayoutRecord.span = i7;
          int i27 = i1;
          localLayoutRecord.column = i27;
          i9 = 1;
          break label259;
        }
        i1 = localLayoutRecord.column;
        i9 = 0;
        break label259;
        if (localLayoutParams.width == -1)
        {
          i11 = View.MeasureSpec.makeMeasureSpec(j * (-1 + localLayoutParams.majorSpan) + (int)(k * localLayoutParams.majorSpan * this.mRatio), 1073741824);
          break label334;
        }
        i11 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
        break label334;
        label627: i11 = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
        if (localLayoutParams.height == -2)
        {
          i12 = View.MeasureSpec.makeMeasureSpec(0, 0);
          break label334;
        }
        if (localLayoutParams.height == -1)
        {
          i12 = View.MeasureSpec.makeMeasureSpec(j * (-1 + localLayoutParams.majorSpan) + (int)(k * localLayoutParams.majorSpan * this.mRatio), 1073741824);
          break label334;
        }
        i12 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
        break label334;
      }
      label518: label565: int i14 = i23;
      int i15;
      int i16;
      int i17;
      int i18;
      if (this.mHorizontalOrientation)
      {
        i15 = i14 + j;
        i16 = i15 + i13;
        i17 = i + i1 * (k + j);
        i18 = i17 + localView.getMeasuredHeight();
      }
      for (int i19 = i16; ; i19 = i18)
      {
        localView.layout(i15, i17, i16, i18);
        for (int i20 = i1; ; i20++)
        {
          int i21 = i1 + i7;
          if (i20 >= i21)
            break;
          int[] arrayOfInt1 = this.mItemEnd;
          int i22 = i20 - i1;
          arrayOfInt1[i20] = (i19 + localLayoutRecord.getMarginAfter(i22));
        }
        label845: i14 = this.mItemEnd[i1];
        break;
        i15 = i + i1 * (k + j);
        i16 = i15 + localView.getMeasuredWidth();
        i17 = i14 + j;
        i18 = i17 + i13;
      }
      i1 = getNextColumnDown(this.mItemEnd);
    }
    label916: int i3 = 0;
    for (int i4 = 0; ; i4++)
    {
      int i5 = this.mColCount;
      if (i4 >= i5)
        break;
      if (this.mItemEnd[i4] > i3)
        i3 = this.mItemEnd[i4];
    }
    return i3 - m;
  }

  private int fillUp(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    int k;
    int m;
    label37: int i1;
    boolean bool;
    int i2;
    View localView;
    LayoutParams localLayoutParams;
    label140: int i7;
    int i8;
    LayoutRecord localLayoutRecord2;
    LayoutRecord localLayoutRecord1;
    if (this.mHorizontalOrientation)
    {
      i = getPaddingTop();
      j = this.mItemMargin;
      k = getColumnSize();
      if (!this.mHorizontalOrientation)
        break label333;
      m = getPaddingLeft();
      int n = m - paramInt2;
      i1 = getNextColumnUp();
      bool = true;
      i2 = paramInt1;
      if ((i1 < 0) || ((this.mItemStart[i1] <= n) && (bool)) || (i2 < 0))
        break label1252;
      int i6 = this.mItemCount;
      if (i2 >= i6)
        break label1252;
      localView = obtainView(i2, null);
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if (localView.getParent() != this)
      {
        if (!this.mInLayout)
          break label342;
        addViewInLayout(localView, 0, localLayoutParams);
      }
      i7 = Math.min(this.mColCount, localLayoutParams.minorSpan);
      i8 = k * i7 + j * (i7 - 1);
      if (i7 <= 1)
        break label758;
      this.mBug6713624LinkedHashMap.put("getnextrecordup - get", Integer.valueOf(i2));
      localLayoutRecord2 = (LayoutRecord)this.mLayoutRecords.get(i2);
      if (localLayoutRecord2 != null)
        break label352;
      localLayoutRecord1 = new LayoutRecord((byte)0);
      localLayoutRecord1.span = i7;
      this.mBug6713624LinkedHashMap.put("getnextrecordup - put", Integer.valueOf(i2));
      this.mLayoutRecords.put(i2, localLayoutRecord1);
    }
    while (true)
    {
      int i32 = -1;
      int i33 = -2147483648;
      int i34 = this.mColCount - i7;
      label271: int i38;
      int i39;
      label285: int i42;
      if (i34 >= 0)
      {
        i38 = 2147483647;
        i39 = i34;
        int i40 = i34 + i7;
        if (i39 < i40)
        {
          i42 = this.mItemStart[i39];
          if (i42 >= i38)
            break label1318;
        }
      }
      while (true)
      {
        i39++;
        i38 = i42;
        break label285;
        i = getPaddingLeft();
        break;
        label333: m = getPaddingTop();
        break label37;
        label342: addView(localView, 0);
        break label140;
        label352: if (localLayoutRecord2.span == i7)
          break label1325;
        IllegalStateException localIllegalStateException = new IllegalStateException("Invalid LayoutRecord! Record had span=" + localLayoutRecord2.span + " but caller requested span=" + i7 + " for position=" + i2);
        throw localIllegalStateException;
        if (i38 > i33);
        for (int i41 = i34; ; i41 = i32)
        {
          i34--;
          i33 = i38;
          i32 = i41;
          break label271;
          int i35 = i32;
          localLayoutRecord1.column = i35;
          for (int i36 = 0; i36 < i7; i36++)
          {
            int i37 = this.mItemStart[(i36 + i32)] - i33;
            localLayoutRecord1.setMarginAfter(i36, i37);
          }
          i1 = localLayoutRecord1.column;
          int i9 = 0;
          label563: int i12;
          int i11;
          if (localLayoutRecord1 == null)
          {
            localLayoutRecord1 = new LayoutRecord((byte)0);
            this.mBug6713624LinkedHashMap.put("fillup - put", Integer.valueOf(i2));
            this.mLayoutRecords.put(i2, localLayoutRecord1);
            int i10 = i1;
            localLayoutRecord1.column = i10;
            localLayoutRecord1.span = i7;
            if (this.mHasStableIds)
            {
              long l = this.mAdapter.getItemId(i2);
              localLayoutRecord1.id = l;
              localLayoutParams.id = l;
            }
            localLayoutParams.column = i1;
            if (!this.mHorizontalOrientation)
              break label900;
            i12 = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
            if (localLayoutParams.width != -2)
              break label838;
            i11 = View.MeasureSpec.makeMeasureSpec(0, 0);
            label638: localView.measure(i11, i12);
            if (!this.mHorizontalOrientation)
              break label992;
          }
          int i27;
          label838: label992: for (int i13 = localView.getMeasuredWidth(); ; i13 = localView.getMeasuredHeight())
          {
            if ((i9 != 0) || ((i13 != localLayoutRecord1.size) && (localLayoutRecord1.size > 0)))
              invalidateLayoutRecordsBeforePosition(i2);
            localLayoutRecord1.size = i13;
            if (i7 <= 1)
              break label1118;
            i27 = this.mItemStart[i1];
            for (int i28 = i1 + 1; ; i28++)
            {
              int i29 = i1 + i7;
              if (i28 >= i29)
                break;
              int i30 = this.mItemStart[i28];
              if (i30 < i27)
                i27 = i30;
            }
            label758: this.mBug6713624LinkedHashMap.put("fillup - get", Integer.valueOf(i2));
            localLayoutRecord1 = (LayoutRecord)this.mLayoutRecords.get(i2);
            break;
            if (i7 != localLayoutRecord1.span)
            {
              localLayoutRecord1.span = i7;
              int i31 = i1;
              localLayoutRecord1.column = i31;
              i9 = 1;
              break label563;
            }
            i1 = localLayoutRecord1.column;
            i9 = 0;
            break label563;
            if (localLayoutParams.width == -1)
            {
              i11 = View.MeasureSpec.makeMeasureSpec(j * (-1 + localLayoutParams.majorSpan) + (int)(k * localLayoutParams.majorSpan * this.mRatio), 1073741824);
              break label638;
            }
            i11 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
            break label638;
            i11 = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
            if (localLayoutParams.height == -2)
            {
              i12 = View.MeasureSpec.makeMeasureSpec(0, 0);
              break label638;
            }
            if (localLayoutParams.height == -1)
            {
              i12 = View.MeasureSpec.makeMeasureSpec(j * (-1 + localLayoutParams.majorSpan) + (int)(k * localLayoutParams.majorSpan * this.mRatio), 1073741824);
              break label638;
            }
            i12 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
            break label638;
          }
          label900: int i14 = i27;
          int i18;
          int i17;
          int i16;
          int i15;
          if (this.mHorizontalOrientation)
          {
            i18 = i14;
            i17 = i14 - i13;
            i16 = i + i1 * (k + j);
            i15 = i16 + localView.getMeasuredHeight();
          }
          for (int i19 = i17; ; i19 = i16)
          {
            localView.layout(i17, i16, i18, i15);
            for (int i20 = i1; ; i20++)
            {
              int i21 = i1 + i7;
              if (i20 >= i21)
                break;
              int[] arrayOfInt = this.mItemStart;
              int i26 = i20 - i1;
              arrayOfInt[i20] = (i19 - localLayoutRecord1.getMarginBefore(i26) - j);
            }
            label1118: i14 = this.mItemStart[i1];
            break;
            i15 = i14;
            i16 = i14 - i13;
            i17 = i + i1 * (k + j);
            i18 = i17 + localView.getMeasuredWidth();
          }
          bool = localLayoutParams.isBoxStart;
          int i22 = this.mItemStart[0];
          for (int i23 = 1; ; i23++)
          {
            int i24 = this.mColCount;
            if ((i23 >= i24) || (!bool))
              break;
            if (this.mItemStart[i23] != i22)
              bool = false;
          }
          i1 = getNextColumnUp();
          int i25 = i2 - 1;
          this.mFirstPosition = i2;
          i2 = i25;
          break;
          label1252: int i3 = getHeight();
          for (int i4 = 0; ; i4++)
          {
            int i5 = this.mColCount;
            if (i4 >= i5)
              break;
            if (this.mItemStart[i4] < i3)
              i3 = this.mItemStart[i4];
          }
          return m - i3;
          i38 = i33;
        }
        label1318: i42 = i38;
      }
      label1325: localLayoutRecord1 = localLayoutRecord2;
    }
  }

  private LayoutParams generateDefaultLayoutParams()
  {
    if (this.mHorizontalOrientation);
    for (int i = 1; ; i = 2)
      return new LayoutParams(i, -2, 1, 1);
  }

  private LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    LayoutParams localLayoutParams = new LayoutParams(paramLayoutParams);
    if (this.mHorizontalOrientation);
    for (int i = 1; ; i = 2)
    {
      localLayoutParams.orientation = i;
      return localLayoutParams;
    }
  }

  private int getNextColumnDown(int[] paramArrayOfInt)
  {
    int i = -1;
    int j = 2147483647;
    int k = this.mColCount;
    for (int m = 0; m < k; m++)
    {
      int n = paramArrayOfInt[m];
      if (n < j)
      {
        j = n;
        i = m;
      }
    }
    return i;
  }

  private int getNextColumnUp()
  {
    int i = -1;
    int j = -2147483648;
    for (int k = -1 + this.mColCount; k >= 0; k--)
    {
      int m = this.mItemStart[k];
      if (m > j)
      {
        j = m;
        i = k;
      }
    }
    return i;
  }

  private LayoutRecord getNextRecordDown(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    this.mBug6713624LinkedHashMap.put("getnextrecorddown - get", Integer.valueOf(paramInt1));
    LayoutRecord localLayoutRecord = (LayoutRecord)this.mLayoutRecords.get(paramInt1);
    int i;
    int j;
    int k;
    if (localLayoutRecord == null)
    {
      localLayoutRecord = new LayoutRecord((byte)0);
      localLayoutRecord.span = paramInt2;
      this.mBug6713624LinkedHashMap.put("getnextrecorddown - put", Integer.valueOf(paramInt1));
      this.mLayoutRecords.put(paramInt1, localLayoutRecord);
      i = -1;
      j = 2147483647;
      k = this.mColCount;
    }
    for (int m = 0; ; m++)
    {
      if (m > k - paramInt2)
        break label220;
      int i1 = -2147483648;
      int i2 = m;
      while (true)
        if (i2 < m + paramInt2)
        {
          int i3 = paramArrayOfInt[i2];
          if (i3 > i1)
            i1 = i3;
          i2++;
          continue;
          if (localLayoutRecord.span == paramInt2)
            break;
          throw new IllegalStateException("Invalid LayoutRecord! Record had span=" + localLayoutRecord.span + " but caller requested span=" + paramInt2 + " for position=" + paramInt1);
        }
      if (i1 < j)
      {
        j = i1;
        i = m;
      }
    }
    label220: localLayoutRecord.column = i;
    for (int n = 0; n < paramInt2; n++)
      localLayoutRecord.setMarginBefore(n, j - paramArrayOfInt[(n + i)]);
    return localLayoutRecord;
  }

  private void invalidateLayoutRecordsAfterPosition(int paramInt)
  {
    for (int i = -1 + this.mLayoutRecords.size(); (i >= 0) && (this.mLayoutRecords.keyAt(i) > paramInt); i--);
    int j = i + 1;
    this.mBug6713624LinkedHashMap.put("invalidateafter - removeatrange", Integer.valueOf(this.mLayoutRecords.size() - j));
    this.mLayoutRecords.removeAtRange(j + 1, this.mLayoutRecords.size() - j);
  }

  private void invalidateLayoutRecordsBeforePosition(int paramInt)
  {
    for (int i = 0; (i < this.mLayoutRecords.size()) && (this.mLayoutRecords.keyAt(i) < paramInt); i++);
    this.mBug6713624LinkedHashMap.put("invalidatebefore - removeatrange", Integer.valueOf(i));
    this.mLayoutRecords.removeAtRange(0, i);
  }

  private void invokeOnItemScrollListener(int paramInt)
  {
    if (this.mOnScrollListener != null)
      this.mOnScrollListener.onScroll(this, this.mFirstPosition, this.mVisibleOffset, getChildCount(), this.mItemCount, paramInt);
    onScrollChanged(0, 0, 0, 0);
  }

  private boolean isSelected(int paramInt)
  {
    return this.mSelectedPositions.get(paramInt, false);
  }

  private View obtainView(int paramInt, View paramView)
  {
    View localView1 = this.mRecycler.getTransientStateView(paramInt);
    Object localObject2;
    if (localView1 != null)
    {
      localObject2 = localView1;
      return localObject2;
    }
    int i;
    label35: int j;
    View localView2;
    label57: View localView3;
    Object localObject1;
    if (paramView != null)
    {
      i = ((LayoutParams)paramView.getLayoutParams()).viewType;
      j = this.mAdapter.getItemViewType(paramInt);
      if (i != j)
        break label194;
      localView2 = paramView;
      localView3 = this.mAdapter.getView(paramInt, localView2, this);
      if ((localView3 != localView2) && (localView2 != null))
        this.mRecycler.addScrap(localView2, getChildCount());
      localObject1 = localView3.getLayoutParams();
      if (localView3.getParent() != this)
      {
        if (localObject1 != null)
          break label208;
        Log.e("ColumnGridView", "view at position " + paramInt + " doesn't have layout parameters;using default layout paramters");
        localObject1 = generateDefaultLayoutParams();
      }
    }
    while (true)
    {
      localView3.setLayoutParams((ViewGroup.LayoutParams)localObject1);
      LayoutParams localLayoutParams = (LayoutParams)localObject1;
      localLayoutParams.position = paramInt;
      localLayoutParams.viewType = j;
      localObject2 = localView3;
      break;
      i = -1;
      break label35;
      label194: localView2 = this.mRecycler.getScrapView(j);
      break label57;
      label208: if (!checkLayoutParams((ViewGroup.LayoutParams)localObject1))
      {
        Log.e("ColumnGridView", "view at position " + paramInt + " doesn't have layout parameters of type ColumnGridView.LayoutParams; wrapping parameters");
        localObject1 = generateLayoutParams((ViewGroup.LayoutParams)localObject1);
      }
    }
  }

  private void populate()
  {
    if ((getWidth() == 0) || (getHeight() == 0))
      return;
    int i54;
    if (this.mColCount == -1)
    {
      if (!this.mHorizontalOrientation)
        break label519;
      i54 = getHeight() / this.mMinColWidth;
      label41: if (i54 != this.mColCount)
        this.mColCount = i54;
    }
    int i = this.mColCount;
    int j;
    label103: int m;
    label159: label188: int n;
    int i1;
    int i2;
    int i3;
    int i4;
    int i5;
    label225: View localView3;
    LayoutParams localLayoutParams3;
    int i32;
    int i33;
    int i34;
    label282: View localView4;
    if ((this.mItemStart == null) || (this.mItemStart.length != i))
    {
      this.mItemStart = new int[i];
      this.mItemEnd = new int[i];
      if (this.mHorizontalOrientation)
      {
        j = getPaddingLeft();
        int k = j + this.mRestoreOffset;
        Arrays.fill(this.mItemStart, k);
        Arrays.fill(this.mItemEnd, k);
        this.mBug6713624LinkedHashMap.put("populate - clear", Integer.valueOf(0));
        this.mLayoutRecords.clear();
        if (!this.mInLayout)
          break label541;
        removeAllViewsInLayout();
        this.mRestoreOffset = 0;
      }
    }
    else
    {
      this.mPopulating = true;
      boolean bool = this.mDataChanged;
      if (!this.mHorizontalOrientation)
        break label548;
      m = getPaddingTop();
      n = this.mItemMargin;
      i1 = getColumnSize();
      i2 = -1;
      i3 = -1;
      Arrays.fill(this.mItemEnd, -2147483648);
      i4 = getChildCount();
      i5 = 0;
      if (i5 >= i4)
        break label1004;
      localView3 = getChildAt(i5);
      localLayoutParams3 = (LayoutParams)localView3.getLayoutParams();
      i32 = localLayoutParams3.column;
      i33 = i5 + this.mFirstPosition;
      if ((!bool) && (!localView3.isLayoutRequested()))
        break label557;
      i34 = 1;
      if (!bool)
        break label2083;
      localView4 = obtainView(i33, localView3);
      if (localView4 == localView3)
        break label2076;
      removeViewAt(i5);
      addView(localView4, i5);
      label318: int i53 = localLayoutParams3.minorSpan;
      localLayoutParams3 = (LayoutParams)localView4.getLayoutParams();
      if (localLayoutParams3.minorSpan != i53)
        Log.e("ColumnGridView", "Span changed!");
      localLayoutParams3.column = i32;
    }
    while (true)
    {
      int i35 = Math.min(this.mColCount, localLayoutParams3.minorSpan);
      int i36 = i1 * i35 + n * (i35 - 1);
      int i52;
      int i50;
      int i51;
      label434: int i37;
      label468: int i48;
      label480: int i49;
      if (i34 != 0)
      {
        if (!this.mHorizontalOrientation)
          break label633;
        i52 = View.MeasureSpec.makeMeasureSpec(i36, 1073741824);
        if (localLayoutParams3.width == -2)
        {
          i50 = View.MeasureSpec.makeMeasureSpec(0, 0);
          i51 = i52;
          localView4.measure(i50, i51);
        }
      }
      else
      {
        if (this.mItemEnd[i32] <= -2147483648)
          break label725;
        i37 = n + this.mItemEnd[i32];
        if (i35 <= 1)
          break label752;
        i48 = i32 + 1;
        if (i48 >= i32 + i35)
          break label752;
        i49 = n + this.mItemEnd[i48];
        if (i49 <= i37)
          break label2069;
      }
      while (true)
      {
        i48++;
        i37 = i49;
        break label480;
        label519: i54 = getWidth() / this.mMinColWidth;
        break label41;
        j = getPaddingTop();
        break label103;
        label541: removeAllViews();
        break label159;
        label548: m = getPaddingLeft();
        break label188;
        label557: i34 = 0;
        break label282;
        if (localLayoutParams3.width == -1)
        {
          i50 = View.MeasureSpec.makeMeasureSpec(n * (-1 + localLayoutParams3.majorSpan) + (int)(i1 * localLayoutParams3.majorSpan * this.mRatio), 1073741824);
          i51 = i52;
          break label434;
        }
        i50 = View.MeasureSpec.makeMeasureSpec(localLayoutParams3.width, 1073741824);
        i51 = i52;
        break label434;
        label633: i50 = View.MeasureSpec.makeMeasureSpec(i36, 1073741824);
        if (localLayoutParams3.height == -2)
        {
          i51 = View.MeasureSpec.makeMeasureSpec(0, 0);
          break label434;
        }
        if (localLayoutParams3.height == -1)
        {
          i51 = View.MeasureSpec.makeMeasureSpec(n * (-1 + localLayoutParams3.majorSpan) + (int)(i1 * localLayoutParams3.majorSpan * this.mRatio), 1073741824);
          break label434;
        }
        i51 = View.MeasureSpec.makeMeasureSpec(localLayoutParams3.height, 1073741824);
        break label434;
        label725: if (this.mHorizontalOrientation)
        {
          i37 = localView4.getLeft();
          break label468;
        }
        i37 = localView4.getTop();
        break label468;
        label752: int i38;
        int i40;
        int i44;
        int i41;
        int i42;
        if (this.mHorizontalOrientation)
        {
          i38 = localView4.getMeasuredWidth();
          if (!this.mHorizontalOrientation)
            break label859;
          i40 = i37 + i38;
          i44 = m + i32 * (i1 + n);
          i41 = i44 + localView4.getMeasuredHeight();
          i42 = i40;
        }
        while (true)
        {
          localView4.layout(i37, i44, i40, i41);
          for (int i45 = i32; i45 < i32 + i35; i45++)
            this.mItemEnd[i45] = i42;
          i38 = localView4.getMeasuredHeight();
          break;
          label859: int i39 = m + i32 * (i1 + n);
          i40 = i39 + localView4.getMeasuredWidth();
          i41 = i37 + i38;
          i42 = i41;
          int i43 = i37;
          i37 = i39;
          i44 = i43;
        }
        this.mBug6713624LinkedHashMap.put("layoutchildren - get", Integer.valueOf(i33));
        LayoutRecord localLayoutRecord3 = (LayoutRecord)this.mLayoutRecords.get(i33);
        if ((localLayoutRecord3 != null) && (localLayoutRecord3.size != i38))
          localLayoutRecord3.size = i38;
        for (int i46 = i33; ; i46 = i2)
        {
          if ((localLayoutRecord3 != null) && (localLayoutRecord3.span != i35))
            localLayoutRecord3.span = i35;
          for (int i47 = i33; ; i47 = i3)
          {
            i5++;
            i3 = i47;
            i2 = i46;
            break label225;
            label1004: for (int i6 = 0; i6 < this.mColCount; i6++)
              if (this.mItemEnd[i6] == -2147483648)
                this.mItemEnd[i6] = this.mItemStart[i6];
            if ((i2 >= 0) || (i3 >= 0))
            {
              if (i2 >= 0)
                invalidateLayoutRecordsBeforePosition(i2);
              if (i3 >= 0)
                invalidateLayoutRecordsAfterPosition(i3);
              int i7 = 0;
              if (i7 < i4)
              {
                int i30 = i7 + this.mFirstPosition;
                View localView2 = getChildAt(i7);
                LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
                this.mBug6713624LinkedHashMap.put("layoutchildren - get2", Integer.valueOf(i30));
                LayoutRecord localLayoutRecord2 = (LayoutRecord)this.mLayoutRecords.get(i30);
                if (localLayoutRecord2 == null)
                {
                  localLayoutRecord2 = new LayoutRecord((byte)0);
                  this.mBug6713624LinkedHashMap.put("layoutchildren - put2", Integer.valueOf(i30));
                  this.mLayoutRecords.put(i30, localLayoutRecord2);
                }
                localLayoutRecord2.column = localLayoutParams2.column;
                if (this.mHorizontalOrientation);
                for (int i31 = localView2.getWidth(); ; i31 = localView2.getHeight())
                {
                  localLayoutRecord2.size = i31;
                  localLayoutRecord2.id = localLayoutParams2.id;
                  localLayoutRecord2.span = Math.min(this.mColCount, localLayoutParams2.minorSpan);
                  i7++;
                  break;
                }
              }
            }
            int i8 = this.mFirstPosition;
            int[] arrayOfInt;
            int i11;
            int i12;
            int i14;
            int i15;
            label1312: View localView1;
            LayoutParams localLayoutParams1;
            int i16;
            int i17;
            Object localObject;
            int i18;
            label1401: int i19;
            label1460: int i29;
            int i20;
            int i21;
            label1539: int i22;
            label1562: int i23;
            int i27;
            label1617: int i28;
            if (this.mInLayout)
            {
              arrayOfInt = new int[this.mColCount];
              i11 = this.mItemMargin;
              i12 = getColumnSize();
              int i13 = getNextColumnDown(arrayOfInt);
              i14 = 0;
              i15 = i13;
              if ((i14 < i8) && (i14 < this.mItemCount))
              {
                localView1 = obtainView(i14, null);
                localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
                i16 = Math.min(this.mColCount, localLayoutParams1.minorSpan);
                i17 = i12 * i16 + i11 * (i16 - 1);
                if (i16 > 1)
                {
                  localObject = getNextRecordDown(i14, i16, arrayOfInt);
                  i18 = ((LayoutRecord)localObject).column;
                  i19 = 0;
                  if (localObject != null)
                    break label1692;
                  localObject = new LayoutRecord((byte)0);
                  this.mBug6713624LinkedHashMap.put("prefilldown - put", Integer.valueOf(i14));
                  this.mLayoutRecords.put(i14, localObject);
                  ((LayoutRecord)localObject).column = i18;
                  ((LayoutRecord)localObject).span = i16;
                  if (this.mHasStableIds)
                  {
                    long l = this.mAdapter.getItemId(i14);
                    ((LayoutRecord)localObject).id = l;
                    localLayoutParams1.id = l;
                  }
                  localLayoutParams1.column = i18;
                  if (!this.mHorizontalOrientation)
                    break label1805;
                  i29 = View.MeasureSpec.makeMeasureSpec(i17, 1073741824);
                  if (localLayoutParams1.width != -2)
                    break label1735;
                  i20 = View.MeasureSpec.makeMeasureSpec(0, 0);
                  i21 = i29;
                  localView1.measure(i20, i21);
                  if (!this.mHorizontalOrientation)
                    break label1897;
                  i22 = localView1.getMeasuredWidth();
                  if ((i19 != 0) || ((i22 != ((LayoutRecord)localObject).size) && (((LayoutRecord)localObject).size > 0)))
                    invalidateLayoutRecordsAfterPosition(i14);
                  ((LayoutRecord)localObject).size = i22;
                  if (i16 <= 1)
                    break label1907;
                  i23 = arrayOfInt[i18];
                  i27 = i18 + 1;
                  if (i27 >= i18 + i16)
                    break label1914;
                  i28 = arrayOfInt[i27];
                  if (i28 <= i23)
                    break label2048;
                }
              }
            }
            while (true)
            {
              i27++;
              i23 = i28;
              break label1617;
              this.mBug6713624LinkedHashMap.put("prefilldown - get", Integer.valueOf(i14));
              LayoutRecord localLayoutRecord1 = (LayoutRecord)this.mLayoutRecords.get(i14);
              i18 = i15;
              localObject = localLayoutRecord1;
              break label1401;
              label1692: if (i16 != ((LayoutRecord)localObject).span)
              {
                ((LayoutRecord)localObject).span = i16;
                ((LayoutRecord)localObject).column = i18;
                i19 = 1;
                break label1460;
              }
              i18 = ((LayoutRecord)localObject).column;
              i19 = 0;
              break label1460;
              label1735: if (localLayoutParams1.width == -1)
              {
                i20 = View.MeasureSpec.makeMeasureSpec(i11 * (-1 + localLayoutParams1.majorSpan) + (int)(i12 * localLayoutParams1.majorSpan * this.mRatio), 1073741824);
                i21 = i29;
                break label1539;
              }
              i20 = View.MeasureSpec.makeMeasureSpec(localLayoutParams1.width, 1073741824);
              i21 = i29;
              break label1539;
              label1805: i20 = View.MeasureSpec.makeMeasureSpec(i17, 1073741824);
              if (localLayoutParams1.height == -2)
              {
                i21 = View.MeasureSpec.makeMeasureSpec(0, 0);
                break label1539;
              }
              if (localLayoutParams1.height == -1)
              {
                i21 = View.MeasureSpec.makeMeasureSpec(i11 * (-1 + localLayoutParams1.majorSpan) + (int)(i12 * localLayoutParams1.majorSpan * this.mRatio), 1073741824);
                break label1539;
              }
              i21 = View.MeasureSpec.makeMeasureSpec(localLayoutParams1.height, 1073741824);
              break label1539;
              label1897: i22 = localView1.getMeasuredHeight();
              break label1562;
              label1907: i23 = arrayOfInt[i18];
              label1914: int i24 = i11 + (i23 + i22);
              for (int i25 = i18; i25 < i18 + i16; i25++)
                arrayOfInt[i25] = (i24 + ((LayoutRecord)localObject).getMarginAfter(i25 - i18));
              int i26 = getNextColumnDown(arrayOfInt);
              i14++;
              i15 = i26;
              break label1312;
              fillDown(this.mFirstPosition + getChildCount(), 0);
              int i9 = -1 + this.mFirstPosition;
              if (this.mRestoreOffset > 0);
              for (int i10 = this.mRestoreOffset; ; i10 = 0)
              {
                fillUp(i9, i10);
                setVisibleOffset();
                this.mPopulating = false;
                this.mDataChanged = false;
                break;
              }
              label2048: i28 = i23;
            }
          }
        }
        label2069: i49 = i37;
      }
      label2076: localView4 = localView3;
      break label318;
      label2083: localView4 = localView3;
    }
  }

  private void reportScrollStateChange(int paramInt)
  {
    if (paramInt != this.mLastScrollState)
    {
      this.mLastScrollState = paramInt;
      if (this.mOnScrollListener != null)
        this.mOnScrollListener.onScrollStateChanged(this, paramInt);
    }
  }

  private void resetStateForGridTop()
  {
    int i = this.mColCount;
    if (i != -1)
    {
      if ((this.mItemStart == null) || (this.mItemStart.length != i))
      {
        this.mItemStart = new int[i];
        this.mItemEnd = new int[i];
      }
      int j = getPaddingTop();
      Arrays.fill(this.mItemStart, j);
      Arrays.fill(this.mItemEnd, j);
    }
    this.mFirstPosition = 0;
    this.mVisibleOffset = 0;
    this.mRestoreOffset = 0;
  }

  private void setVisibleOffset()
  {
    int i = -this.mItemMargin;
    this.mVisibleOffset = 0;
    int j = 0;
    int k = getChildCount();
    if (j < k)
    {
      View localView = getChildAt(j);
      if (this.mHorizontalOrientation);
      for (int m = localView.getRight(); ; m = localView.getBottom())
      {
        if (m >= i)
          return;
        this.mVisibleOffset = (1 + this.mVisibleOffset);
        j++;
        break;
      }
    }
  }

  private boolean trackMotionScroll(int paramInt, boolean paramBoolean)
  {
    int i;
    int j;
    int i9;
    int i10;
    label56: int m;
    int i11;
    label74: int i12;
    if ((this.mFirstPosition != 0) || (getChildCount() != this.mItemCount))
    {
      i = 0;
      j = Math.abs(paramInt);
      if (i != 0)
        break label1519;
      this.mPopulating = true;
      if (paramInt <= 0)
        break label313;
      i9 = fillUp(-1 + this.mFirstPosition, j);
      i10 = 1;
      m = Math.min(i9, j);
      if (i10 == 0)
        break label341;
      i11 = m;
      if (!this.mHorizontalOrientation)
        break label349;
      i12 = i11;
      label85: if (!this.mHorizontalOrientation)
        break label355;
    }
    label308: label313: label341: label349: label355: for (int i13 = 0; ; i13 = i11)
    {
      int i14 = getChildCount();
      for (int i15 = 0; i15 < i14; i15++)
      {
        View localView7 = getChildAt(i15);
        localView7.layout(i12 + localView7.getLeft(), i13 + localView7.getTop(), i12 + localView7.getRight(), i13 + localView7.getBottom());
      }
      int i47 = 2147483647;
      int i48 = -2147483648;
      for (int i49 = 0; ; i49++)
      {
        int i50 = this.mColCount;
        if (i49 >= i50)
          break;
        if (this.mItemStart[i49] < i47)
          i47 = this.mItemStart[i49];
        if (this.mItemEnd[i49] > i48)
          i48 = this.mItemEnd[i49];
      }
      int i51;
      int i52;
      if (this.mHorizontalOrientation)
      {
        i51 = getPaddingLeft();
        i52 = getPaddingRight();
      }
      for (int i53 = getWidth(); ; i53 = getHeight())
      {
        if (i47 < i51)
          break label308;
        int i54 = i53 - i52;
        if (i48 > i54)
          break label308;
        i = 1;
        break;
        i51 = getPaddingTop();
        i52 = getPaddingBottom();
      }
      i = 0;
      break;
      i9 = fillDown(this.mFirstPosition + getChildCount(), j) + this.mItemMargin;
      i10 = 0;
      break label56;
      i11 = -m;
      break label74;
      i12 = 0;
      break label85;
    }
    int i16 = this.mColCount;
    for (int i17 = 0; i17 < i16; i17++)
    {
      int[] arrayOfInt1 = this.mItemStart;
      arrayOfInt1[i17] = (i11 + arrayOfInt1[i17]);
      int[] arrayOfInt2 = this.mItemEnd;
      arrayOfInt2[i17] = (i11 + arrayOfInt2[i17]);
    }
    int i18;
    int i21;
    int i23;
    int i24;
    int i25;
    label494: View localView6;
    int i43;
    label521: int i44;
    label535: int i46;
    label584: int i45;
    if (this.mHorizontalOrientation)
    {
      i18 = getWidth();
      int i19 = this.mItemMargin;
      int i20 = (int)(getColumnSize() * this.mRatio) + i19 * this.mColCount;
      i21 = -i19;
      int i22 = i18 + i19;
      i23 = -i20;
      i24 = i18 + i20;
      i25 = -1 + getChildCount();
      if (i25 < 0)
        break label665;
      localView6 = getChildAt(i25);
      if (!this.mHorizontalOrientation)
        break label614;
      i43 = localView6.getRight();
      if (!this.mHorizontalOrientation)
        break label624;
      i44 = localView6.getLeft();
      if (((i44 > i21) && (i43 < i22)) || (i43 < i23) || (i44 > i24) || (i43 >= i21))
        break label1609;
      if (!this.mHorizontalOrientation)
        break label634;
      i46 = localView6.getWidth();
      if (i46 <= i20)
        break label644;
      i45 = i21;
    }
    label1408: label1538: 
    while (true)
    {
      i25--;
      i23 = i45;
      break label494;
      i18 = getHeight();
      break;
      label614: i43 = localView6.getBottom();
      break label521;
      label624: i44 = localView6.getTop();
      break label535;
      label634: i46 = localView6.getHeight();
      break label584;
      label644: if (!((LayoutParams)localView6.getLayoutParams()).isBoxStart)
      {
        i45 = i21;
        continue;
        label665: int i26 = -1 + getChildCount();
        if (i26 >= 0)
        {
          View localView5 = getChildAt(i26);
          int i42;
          if (this.mHorizontalOrientation)
          {
            i42 = localView5.getLeft();
            label700: if (i42 <= i24)
              break label759;
            if (!this.mInLayout)
              break label750;
            removeViewsInLayout(i26, 1);
          }
          while (true)
          {
            this.mRecycler.addScrap(localView5, getChildCount());
            i26--;
            break;
            i42 = localView5.getTop();
            break label700;
            label750: removeViewAt(i26);
          }
        }
        label759: this.mVisibleOffset = 0;
        View localView4;
        int i41;
        label792: int i27;
        if (getChildCount() > 0)
        {
          localView4 = getChildAt(0);
          if (this.mHorizontalOrientation)
          {
            i41 = localView4.getRight();
            if (i41 < i23)
              break label1086;
            setVisibleOffset();
          }
        }
        else
        {
          i27 = getChildCount();
          if (i27 <= 0)
            break label1271;
          Arrays.fill(this.mItemStart, 2147483647);
          Arrays.fill(this.mItemEnd, -2147483648);
        }
        for (int i28 = 0; ; i28++)
        {
          if (i28 >= i27)
            break label1159;
          View localView3 = getChildAt(i28);
          LayoutParams localLayoutParams = (LayoutParams)localView3.getLayoutParams();
          int i34;
          label876: int i35;
          if (this.mHorizontalOrientation)
          {
            i34 = localView3.getLeft();
            i35 = i34 - this.mItemMargin;
            if (!this.mHorizontalOrientation)
              break label1143;
          }
          label1086: label1143: for (int i36 = localView3.getRight(); ; i36 = localView3.getBottom())
          {
            this.mBug6713624LinkedHashMap.put("recycleoffscreenveiws - get", Integer.valueOf(i28 + this.mFirstPosition));
            LayoutRecord localLayoutRecord = (LayoutRecord)this.mLayoutRecords.get(i28 + this.mFirstPosition);
            if (localLayoutRecord == null)
              Log.e("ColumnGridView", this.mBug6713624LinkedHashMap.toString());
            int i37 = localLayoutParams.column + Math.min(this.mColCount, localLayoutParams.minorSpan);
            for (int i38 = localLayoutParams.column; i38 < i37; i38++)
            {
              int i39 = i35 - localLayoutRecord.getMarginBefore(i38 - localLayoutParams.column);
              int i40 = i36 + localLayoutRecord.getMarginAfter(i38 - localLayoutParams.column);
              if (i39 < this.mItemStart[i38])
                this.mItemStart[i38] = i39;
              if (i40 > this.mItemEnd[i38])
                this.mItemEnd[i38] = i40;
            }
            i41 = localView4.getBottom();
            break label792;
            if (this.mInLayout)
              removeViewsInLayout(0, 1);
            while (true)
            {
              this.mRecycler.addScrap(localView4, getChildCount());
              this.mFirstPosition = (1 + this.mFirstPosition);
              break;
              removeViewAt(0);
            }
            i34 = localView3.getTop();
            break label876;
          }
        }
        label1159: int i29 = 2147483647;
        for (int i30 = 0; ; i30++)
        {
          int i31 = this.mColCount;
          if (i30 >= i31)
            break;
          if (this.mItemStart[i30] < i29)
            i29 = this.mItemStart[i30];
        }
        if (i29 == 2147483647)
          i29 = 0;
        for (int i32 = 0; ; i32++)
        {
          int i33 = this.mColCount;
          if (i32 >= i33)
            break;
          if (this.mItemStart[i32] == 2147483647)
          {
            this.mItemStart[i32] = i29;
            this.mItemEnd[i32] = i29;
          }
        }
        label1271: this.mPopulating = false;
        int k = j - i9;
        EdgeEffectCompat localEdgeEffectCompat;
        label1323: int i8;
        if (paramBoolean)
        {
          int i7 = ViewCompat.getOverScrollMode(this);
          if (((i7 == 0) || ((i7 == 1) && (i == 0))) && (k > 0))
          {
            if (paramInt <= 0)
              break label1529;
            localEdgeEffectCompat = this.mStartEdge;
            if (!this.mHorizontalOrientation)
              break label1538;
            i8 = getWidth();
            label1336: localEdgeEffectCompat.onPull(Math.abs(paramInt) / i8);
            ViewCompat.postInvalidateOnAnimation(this);
          }
        }
        int n = getChildCount();
        int i1;
        label1369: View localView2;
        int i5;
        int i6;
        label1422: View localView1;
        int i2;
        int i3;
        int i4;
        if (n > 0)
        {
          i1 = 1;
          if ((i1 != 0) && (this.mFirstPosition == 0))
          {
            localView2 = getChildAt(0);
            if (!this.mHorizontalOrientation)
              break label1553;
            i5 = localView2.getLeft();
            i6 = getPaddingLeft();
            if ((i5 >= i6) && (paramInt >= 0))
              break label1569;
            i1 = 1;
          }
          if ((i1 != 0) && (n + this.mFirstPosition == this.mItemCount))
          {
            localView1 = getChildAt(n - 1);
            if (!this.mHorizontalOrientation)
              break label1575;
            i2 = localView1.getRight();
            i3 = getPaddingRight();
            i4 = getWidth();
            label1477: if ((i2 <= i4 - i3) && (paramInt <= 0))
              break label1597;
            i1 = 1;
          }
          label1494: if (i1 != 0)
            invokeOnItemScrollListener(paramInt);
          if ((paramInt != 0) && (m == 0))
            break label1603;
        }
        label1553: label1569: label1575: label1597: label1603: for (boolean bool = true; ; bool = false)
        {
          return bool;
          label1519: k = j;
          m = 0;
          break;
          label1529: localEdgeEffectCompat = this.mEndEdge;
          break label1323;
          i8 = getHeight();
          break label1336;
          i1 = 0;
          break label1369;
          i5 = localView2.getTop();
          i6 = getPaddingTop();
          break label1408;
          i1 = 0;
          break label1422;
          i2 = localView1.getBottom();
          i3 = getPaddingBottom();
          i4 = getHeight();
          break label1477;
          i1 = 0;
          break label1494;
        }
      }
      else
      {
        label1609: i45 = i23;
      }
    }
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  public void computeScroll()
  {
    int i;
    int j;
    if (this.mScroller.computeScrollOffset())
    {
      if (!this.mHorizontalOrientation)
        break label78;
      i = this.mScroller.getCurrX();
      j = (int)(i - this.mLastTouch);
      this.mLastTouch = i;
      if (trackMotionScroll(j, false))
        break label89;
    }
    label78: label89: for (int k = 1; ; k = 0)
    {
      if ((k != 0) || (this.mScroller.isFinished()))
        break label94;
      ViewCompat.postInvalidateOnAnimation(this);
      reportScrollStateChange(this.mScrollState);
      return;
      i = this.mScroller.getCurrY();
      break;
    }
    label94: if (k != 0)
      if (ViewCompat.getOverScrollMode(this) != 2)
        if (j <= 0)
          break label171;
    label171: for (EdgeEffectCompat localEdgeEffectCompat = this.mStartEdge; ; localEdgeEffectCompat = this.mEndEdge)
    {
      int m = Build.VERSION.SDK_INT;
      int n = 0;
      if (m >= 14)
        n = (int)this.mScroller.getCurrVelocity();
      localEdgeEffectCompat.onAbsorb(Math.abs(n));
      ViewCompat.postInvalidateOnAnimation(this);
      this.mScroller.abortAnimation();
      this.mScrollState = 0;
      break;
    }
  }

  public final void deselect(int paramInt)
  {
    if ((this.mSelectionMode) && (this.mSelectedPositions.get(paramInt)))
    {
      this.mSelectedPositions.put(paramInt, false);
      View localView = getChildAt(paramInt - this.mFirstPosition);
      if (this.mSelectionListener != null)
        this.mSelectionListener.onItemDeselected(localView, paramInt);
    }
  }

  public void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (this.mSelector == null);
    while (true)
    {
      return;
      int i = getPaddingLeft();
      int j = getRight() - getPaddingRight();
      int k = getPaddingTop();
      int m = getBottom() - getPaddingBottom();
      for (int n = -1 + getChildCount(); n >= 0; n--)
      {
        View localView = getChildAt(n);
        if (!isSelected(n + this.mFirstPosition))
        {
          if ((this.mPressed) && (this.mCurrentTouchPoint.x >= 0) && (this.mCurrentTouchPoint.y >= 0) && ((!(localView instanceof PressedHighlightable)) || (((PressedHighlightable)localView).shouldHighlightOnPress())))
          {
            localView.getLocationOnScreen(this.mLocation);
            if ((this.mCurrentTouchPoint.x < this.mLocation[0]) || (this.mCurrentTouchPoint.x > this.mLocation[0] + localView.getWidth()) || (this.mCurrentTouchPoint.y < this.mLocation[1]) || (this.mCurrentTouchPoint.y > this.mLocation[1] + localView.getHeight()));
          }
        }
        else
        {
          int i1 = localView.getLeft();
          int i2 = localView.getRight();
          int i3 = localView.getTop();
          int i4 = localView.getBottom();
          if ((i1 <= j) && (i2 >= i) && (i3 <= m) && (i4 >= k))
          {
            this.mSelector.setBounds(i1, i3, i2, i4);
            this.mSelector.draw(paramCanvas);
          }
        }
      }
    }
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i;
    if (this.mStartEdge != null)
    {
      boolean bool = this.mStartEdge.isFinished();
      i = 0;
      if (!bool)
      {
        if (!this.mHorizontalOrientation)
          break label141;
        int n = paramCanvas.save();
        paramCanvas.rotate(270.0F);
        paramCanvas.translate(-getHeight(), 0.0F);
        this.mStartEdge.draw(paramCanvas);
        paramCanvas.restoreToCount(n);
        i = 1;
      }
      if (!this.mEndEdge.isFinished())
      {
        if (!this.mHorizontalOrientation)
          break label153;
        int m = paramCanvas.save();
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(0.0F, -getWidth());
        this.mEndEdge.draw(paramCanvas);
        paramCanvas.restoreToCount(m);
      }
    }
    while (true)
    {
      i = 1;
      if (i != 0)
        ViewCompat.postInvalidateOnAnimation(this);
      return;
      label141: this.mStartEdge.draw(paramCanvas);
      break;
      label153: int j = paramCanvas.save();
      int k = getWidth();
      paramCanvas.translate(-k, getHeight());
      paramCanvas.rotate(180.0F, k, 0.0F);
      this.mEndEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(j);
    }
  }

  public final void endSelectionMode()
  {
    if (!this.mSelectionMode)
      throw new IllegalStateException("Not in selection mode!");
    this.mSelectionMode = false;
    if (this.mSelectedPositions.size() > 0)
      invalidate();
    this.mSelectedPositions.clear();
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  public final ListAdapter getAdapter()
  {
    return this.mAdapter;
  }

  public final int getColumnCount()
  {
    return this.mColCount;
  }

  public final int getColumnSize()
  {
    int i;
    int j;
    if (this.mHorizontalOrientation)
    {
      i = getPaddingTop();
      if (!this.mHorizontalOrientation)
        break label67;
      j = getPaddingBottom();
      label24: if (!this.mHorizontalOrientation)
        break label75;
    }
    label67: label75: for (int k = getHeight(); ; k = getWidth())
    {
      return (k - i - j - this.mItemMargin * (-1 + this.mColCount)) / this.mColCount;
      i = getPaddingLeft();
      break;
      j = getPaddingRight();
      break label24;
    }
  }

  public final int getFirstVisiblePosition()
  {
    return this.mFirstPosition;
  }

  public final int getLastVisiblePosition()
  {
    return -1 + (this.mFirstPosition + getChildCount());
  }

  public final void invalidateViews()
  {
    this.mDataChanged = true;
    requestLayout();
    invalidate();
  }

  public final boolean isInSelectionMode()
  {
    return this.mSelectionMode;
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    removeCallbacks(this.mSetPressedRunnable);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (0xFF & paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      i = 0;
      label50: int j;
      while (true)
      {
        return i;
        this.mCurrentTouchPoint.set((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY());
        postDelayed(this.mSetPressedRunnable, ViewConfiguration.getTapTimeout());
        this.mVelocityTracker.clear();
        this.mScroller.abortAnimation();
        if (this.mHorizontalOrientation);
        for (this.mLastTouch = paramMotionEvent.getX(); ; this.mLastTouch = paramMotionEvent.getY())
        {
          this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
          this.mTouchRemainder = 0.0F;
          if (this.mScrollState != 2)
            break label152;
          this.mScrollState = i;
          break;
        }
        label152: if (!this.mSelectionMode)
          break;
        continue;
        j = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId);
        if (j >= 0)
          break label213;
        Log.e("ColumnGridView", "onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did we receive an inconsistent event stream?");
        i = 0;
      }
      label213: if (this.mHorizontalOrientation);
      for (float f1 = MotionEventCompat.getX(paramMotionEvent, j); ; f1 = MotionEventCompat.getY(paramMotionEvent, j))
      {
        float f2 = f1 - this.mLastTouch + this.mTouchRemainder;
        this.mTouchRemainder = (f2 - (int)f2);
        if (Math.abs(f2) <= this.mTouchSlop)
          break;
        this.mScrollState = i;
        break label50;
      }
      this.mCurrentTouchPoint.set(-1, -1);
      clearPressedState();
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mInLayout = true;
    populate();
    this.mInLayout = false;
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    if (this.mHorizontalOrientation)
    {
      this.mStartEdge.setSize(j, i);
      this.mEndEdge.setSize(j, i);
    }
    while (true)
    {
      invokeOnItemScrollListener(0);
      return;
      this.mStartEdge.setSize(i, j);
      this.mEndEdge.setSize(i, j);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    if (i != 1073741824)
      Log.e("ColumnGridView", "onMeasure: must have an exact width or match_parent! Using fallback spec of EXACTLY " + k);
    if (j != 1073741824)
      Log.e("ColumnGridView", "onMeasure: must have an exact height or match_parent! Using fallback spec of EXACTLY " + m);
    setMeasuredDimension(k, m);
    if ((this.mColCountSetting == -1) && (m > 0) && (k > 0))
      if (!this.mHorizontalOrientation)
        break label137;
    label137: for (int n = m / this.mMinColWidth; ; n = k / this.mMinColWidth)
    {
      this.mColCount = n;
      return;
    }
  }

  public final void onPause()
  {
    clearPressedState();
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mDataChanged = true;
    this.mFirstPosition = localSavedState.position;
    this.mVisibleOffset = localSavedState.visibleOffset;
    this.mRestoreOffset = localSavedState.topOffset;
    this.mSelectedPositions.clear();
    for (int i = -1 + localSavedState.selectedPositions.size(); i >= 0; i--)
      this.mSelectedPositions.put(localSavedState.selectedPositions.keyAt(i), localSavedState.selectedPositions.valueAt(i));
    this.mSelectionMode = localSavedState.selectionMode;
    requestLayout();
  }

  public final void onResume()
  {
    clearPressedState();
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    int i = this.mFirstPosition;
    int j = this.mVisibleOffset;
    localSavedState.position = i;
    localSavedState.visibleOffset = j;
    if ((i >= 0) && (this.mAdapter != null) && (i < this.mAdapter.getCount()))
      localSavedState.firstId = this.mAdapter.getItemId(i);
    int k = this.mSelectedPositions.size();
    SparseBooleanArray localSparseBooleanArray = new SparseBooleanArray(k);
    for (int m = k - 1; m >= 0; m--)
      localSparseBooleanArray.put(this.mSelectedPositions.keyAt(m), this.mSelectedPositions.valueAt(m));
    localSavedState.selectedPositions = localSparseBooleanArray;
    localSavedState.selectionMode = this.mSelectionMode;
    int n = getChildCount();
    for (int i1 = 0; ; i1++)
    {
      View localView;
      int i2;
      if (i1 < n)
      {
        localView = getChildAt(i1);
        if (!((LayoutParams)localView.getLayoutParams()).isBoxStart)
          continue;
        if (!this.mHorizontalOrientation)
          break label278;
        i2 = getPaddingLeft();
      }
      for (int i3 = localView.getLeft(); ; i3 = localView.getTop())
      {
        localSavedState.topOffset = (i3 - this.mItemMargin - i2);
        if (i1 != 0)
        {
          localSavedState.position = (i + i1);
          if ((localSavedState.position >= 0) && (this.mAdapter != null) && (localSavedState.position < this.mAdapter.getCount()))
            localSavedState.firstId = this.mAdapter.getItemId(localSavedState.position);
        }
        return localSavedState;
        label278: i2 = getPaddingTop();
      }
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (0xFF & paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 2:
    case 3:
      while (true)
      {
        reportScrollStateChange(this.mScrollState);
        int k;
        for (boolean bool = true; ; bool = false)
        {
          return bool;
          this.mCurrentTouchPoint.set((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY());
          this.mSelectionStartPoint.set((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY());
          postDelayed(this.mSetPressedRunnable, ViewConfiguration.getTapTimeout());
          this.mVelocityTracker.clear();
          this.mScroller.abortAnimation();
          if (this.mHorizontalOrientation);
          for (this.mLastTouch = paramMotionEvent.getX(); ; this.mLastTouch = paramMotionEvent.getY())
          {
            this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
            this.mTouchRemainder = 0.0F;
            break;
          }
          this.mCurrentTouchPoint.set((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY());
          clearPressedState();
          k = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId);
          if (k >= 0)
            break label238;
          Log.e("ColumnGridView", "onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did we receive an inconsistent event stream?");
        }
        label238: if (this.mHorizontalOrientation);
        for (float f2 = MotionEventCompat.getX(paramMotionEvent, k); ; f2 = MotionEventCompat.getY(paramMotionEvent, k))
        {
          float f3 = f2 - this.mLastTouch + this.mTouchRemainder;
          int m = (int)f3;
          this.mTouchRemainder = (f3 - m);
          if (Math.abs(f3) > this.mTouchSlop)
            this.mScrollState = 1;
          if (this.mScrollState != 1)
            break;
          this.mLastTouch = f2;
          if (trackMotionScroll(m, true))
            break;
          this.mVelocityTracker.clear();
          break;
        }
        this.mCurrentTouchPoint.set(-1, -1);
        clearPressedState();
        this.mScrollState = 0;
      }
    case 1:
    }
    this.mCurrentTouchPoint.set(-1, -1);
    clearPressedState();
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
    float f1;
    label414: int i;
    label443: int j;
    if (this.mHorizontalOrientation)
    {
      f1 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
      if (Math.abs(f1) <= this.mFlingVelocity)
        break label532;
      this.mScrollState = 2;
      if (!this.mHorizontalOrientation)
        break label519;
      i = (int)f1;
      if (!this.mHorizontalOrientation)
        break label525;
      j = 0;
      label453: this.mScroller.fling(0, 0, i, j, -2147483648, 2147483647, -2147483648, 2147483647);
      this.mLastTouch = 0.0F;
      ViewCompat.postInvalidateOnAnimation(this);
    }
    while (true)
    {
      checkForSelection((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY());
      break;
      f1 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
      break label414;
      label519: i = 0;
      break label443;
      label525: j = (int)f1;
      break label453;
      label532: this.mScrollState = 0;
    }
  }

  public final void registerSelectionListener(ItemSelectionListener paramItemSelectionListener)
  {
    this.mSelectionListener = paramItemSelectionListener;
  }

  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mCurrentTouchPoint.set(-1, -1);
      clearPressedState();
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }

  public void requestLayout()
  {
    if (!this.mPopulating)
      super.requestLayout();
  }

  public final void select(int paramInt)
  {
    if ((this.mSelectionMode) && (!this.mSelectedPositions.get(paramInt)))
    {
      this.mSelectedPositions.put(paramInt, true);
      View localView = getChildAt(paramInt - this.mFirstPosition);
      if (this.mSelectionListener != null)
        this.mSelectionListener.onItemSelected(localView, paramInt);
    }
  }

  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (this.mAdapter != null)
    {
      this.mAdapter.unregisterDataSetObserver(this.mObserver);
      clearAllState();
    }
    this.mAdapter = paramListAdapter;
    this.mDataChanged = true;
    int i;
    if (paramListAdapter != null)
    {
      i = paramListAdapter.getCount();
      this.mItemCount = i;
      if (paramListAdapter == null)
        break label108;
      paramListAdapter.registerDataSetObserver(this.mObserver);
      this.mRecycler.setViewTypeCount(paramListAdapter.getViewTypeCount());
    }
    label108: for (this.mHasStableIds = paramListAdapter.hasStableIds(); ; this.mHasStableIds = false)
    {
      if (this.mSelectionMode)
        endSelectionMode();
      populate();
      return;
      i = 0;
      break;
    }
  }

  public void setColumnCount(int paramInt)
  {
    if ((paramInt <= 0) && (paramInt != -1))
      throw new IllegalArgumentException("colCount must be at least 1 - received " + paramInt);
    if (paramInt != this.mColCount);
    for (int i = 1; ; i = 0)
    {
      this.mColCountSetting = paramInt;
      this.mColCount = paramInt;
      if (i != 0)
        populate();
      return;
    }
  }

  public void setItemMargin(int paramInt)
  {
    if (paramInt != this.mItemMargin);
    for (int i = 1; ; i = 0)
    {
      this.mItemMargin = paramInt;
      if (i != 0)
        populate();
      return;
    }
  }

  public void setMinColumnWidth(int paramInt)
  {
    this.mMinColWidth = paramInt;
    setColumnCount(-1);
  }

  public void setOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    this.mOnScrollListener = paramOnScrollListener;
    invokeOnItemScrollListener(0);
  }

  public void setOrientation(int paramInt)
  {
    int i = 1;
    if (paramInt == i);
    while (true)
    {
      this.mHorizontalOrientation = i;
      return;
      i = 0;
    }
  }

  public void setRatio(float paramFloat)
  {
    this.mRatio = paramFloat;
  }

  public void setRecyclerListener(RecyclerListener paramRecyclerListener)
  {
    RecycleBin.access$402(this.mRecycler, paramRecyclerListener);
  }

  public void setSelection(int paramInt)
  {
    setSelectionFromTop(paramInt, 0);
  }

  public void setSelectionFromTop(int paramInt1, int paramInt2)
  {
    if (this.mAdapter == null);
    while (true)
    {
      return;
      this.mFirstPosition = Math.max(0, paramInt1);
      this.mVisibleOffset = 0;
      this.mRestoreOffset = paramInt2;
      requestLayout();
    }
  }

  public void setSelectionToTop()
  {
    removeAllViews();
    resetStateForGridTop();
    populate();
  }

  public void setSelector(int paramInt)
  {
    if (paramInt == 0);
    for (this.mSelector = null; ; this.mSelector = getResources().getDrawable(paramInt))
      return;
  }

  public void setSelector(Drawable paramDrawable)
  {
    this.mSelector = paramDrawable;
  }

  public final void startSelectionMode()
  {
    if (this.mSelectionMode)
      throw new IllegalStateException("Already in selection mode!");
    this.mSelectionMode = true;
  }

  public final void unregisterSelectionListener()
  {
    this.mSelectionListener = null;
  }

  private final class AdapterDataSetObserver extends DataSetObserver
  {
    private AdapterDataSetObserver()
    {
    }

    public final void onChanged()
    {
      ColumnGridView.access$602(ColumnGridView.this, true);
      int i = ColumnGridView.this.mItemCount;
      ColumnGridView.access$702(ColumnGridView.this, ColumnGridView.this.mAdapter.getCount());
      if (ColumnGridView.this.mItemCount < i)
        for (int m = -1 + ColumnGridView.this.mSelectedPositions.size(); m >= 0; m--)
        {
          int n = ColumnGridView.this.mSelectedPositions.keyAt(m);
          if ((n >= ColumnGridView.this.mItemCount) && (ColumnGridView.this.mSelectedPositions.valueAt(m)))
            ColumnGridView.this.deselect(n);
        }
      ColumnGridView.this.mRecycler.clearTransientViews();
      if (ColumnGridView.this.mItemCount == 0)
        ColumnGridView.this.clearAllState();
      while (true)
      {
        ColumnGridView.this.requestLayout();
        return;
        if ((!ColumnGridView.this.mHasStableIds) || (ColumnGridView.this.mItemCount < i))
        {
          ColumnGridView.this.mBug6713624LinkedHashMap.put("onchanged - clear", Integer.valueOf(0));
          ColumnGridView.this.mLayoutRecords.clear();
          ColumnGridView.access$1500(ColumnGridView.this);
          if (ColumnGridView.this.mItemStart != null)
          {
            int j = ColumnGridView.this.mColCount;
            for (int k = 0; k < j; k++)
              ColumnGridView.this.mItemEnd[k] = ColumnGridView.this.mItemStart[k];
          }
        }
      }
    }

    public final void onInvalidated()
    {
    }
  }

  private static final class Bug6713624LinkedHashMap<K, V> extends LinkedHashMap<K, V>
  {
    private static final long serialVersionUID = -1633416006298915877L;

    protected final boolean removeEldestEntry(Map.Entry<K, V> paramEntry)
    {
      if (size() > 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }
  }

  public static abstract interface ItemSelectionListener
  {
    public abstract void onItemDeselected(View paramView, int paramInt);

    public abstract void onItemSelected(View paramView, int paramInt);
  }

  public static class LayoutParams extends ViewGroup.LayoutParams
  {
    int column;
    long id = -1L;
    public boolean isBoxStart = true;
    public int majorSpan = 1;
    public int minorSpan = 1;
    int orientation = 2;
    int position;
    int viewType;

    public LayoutParams(int paramInt1, int paramInt2)
    {
    }

    public LayoutParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this(paramInt1, paramInt2);
      this.minorSpan = paramInt3;
      this.majorSpan = paramInt4;
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ColumnGridView_Layout);
      this.minorSpan = localTypedArray.getInteger(1, 1);
      this.majorSpan = localTypedArray.getInteger(2, 1);
      this.orientation = localTypedArray.getInteger(0, 2);
      localTypedArray.recycle();
      if (this.orientation == 1)
        if (this.height != -1)
        {
          Log.w("ColumnGridView", "Inflation setting LayoutParams height to " + this.height + " - must be MATCH_PARENT");
          this.height = -1;
        }
      while (true)
      {
        return;
        if (this.width != -1)
        {
          Log.w("ColumnGridView", "Inflation setting LayoutParams width to " + this.width + " - must be MATCH_PARENT");
          this.width = -1;
        }
      }
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
      if (this.orientation == 1)
        if (this.height != -1)
        {
          Log.w("ColumnGridView", "Constructing LayoutParams with height " + this.height + " - must be MATCH_PARENT");
          this.height = -1;
        }
      while (true)
      {
        return;
        if (this.width != -1)
        {
          Log.w("ColumnGridView", "Constructing LayoutParams with width " + this.width + " - must be MATCH_PARENT");
          this.width = -1;
        }
      }
    }

    public String toString()
    {
      return "ColumnGridView.LayoutParams: id=" + this.id + " major=" + this.majorSpan + " minor=" + this.minorSpan + " pos=" + this.position + " type=" + this.viewType + " col=" + this.column + " boxstart=" + this.isBoxStart + " orient=" + this.orientation;
    }
  }

  private static final class LayoutRecord
  {
    public int column;
    public long id = -1L;
    private int[] mMargins;
    public int size;
    public int span;

    private final void ensureMargins()
    {
      if (this.mMargins == null)
        this.mMargins = new int[2 * this.span];
    }

    public final int getMarginAfter(int paramInt)
    {
      if (this.mMargins == null);
      for (int i = 0; ; i = this.mMargins[(1 + paramInt * 2)])
        return i;
    }

    public final int getMarginBefore(int paramInt)
    {
      if (this.mMargins == null);
      for (int i = 0; ; i = this.mMargins[(paramInt * 2)])
        return i;
    }

    public final void setMarginAfter(int paramInt1, int paramInt2)
    {
      if ((this.mMargins == null) && (paramInt2 == 0));
      while (true)
      {
        return;
        ensureMargins();
        this.mMargins[(1 + paramInt1 * 2)] = paramInt2;
      }
    }

    public final void setMarginBefore(int paramInt1, int paramInt2)
    {
      if ((this.mMargins == null) && (paramInt2 == 0));
      while (true)
      {
        return;
        ensureMargins();
        this.mMargins[(paramInt1 * 2)] = paramInt2;
      }
    }

    public final String toString()
    {
      String str1 = "LayoutRecord{c=" + this.column + ", id=" + this.id + " sz=" + this.size + " sp=" + this.span;
      if (this.mMargins != null)
      {
        String str2 = str1 + " margins[before, after](";
        for (int i = 0; i < this.mMargins.length; i += 2)
          str2 = str2 + "[" + this.mMargins[i] + ", " + this.mMargins[(i + 1)] + "]";
        str1 = str2 + ")";
      }
      return str1 + "}";
    }
  }

  public static abstract interface OnScrollListener
  {
    public abstract void onScroll(ColumnGridView paramColumnGridView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

    public abstract void onScrollStateChanged(ColumnGridView paramColumnGridView, int paramInt);
  }

  public static abstract interface PressedHighlightable
  {
    public abstract boolean shouldHighlightOnPress();
  }

  private static final class RecycleBin
  {
    private int mMaxScrap;
    private ColumnGridView.RecyclerListener mRecyclerListener;
    private ArrayList<View>[] mScrapViews;
    private SparseArray<View> mTransientStateViews;
    private int mViewTypeCount;

    public final void addScrap(View paramView, int paramInt)
    {
      ColumnGridView.LayoutParams localLayoutParams = (ColumnGridView.LayoutParams)paramView.getLayoutParams();
      if (ViewCompat.hasTransientState(paramView))
      {
        if (this.mTransientStateViews == null)
          this.mTransientStateViews = new SparseArray();
        this.mTransientStateViews.put(localLayoutParams.position, paramView);
      }
      while (true)
      {
        return;
        if (paramInt > this.mMaxScrap)
          this.mMaxScrap = paramInt;
        ArrayList localArrayList = this.mScrapViews[localLayoutParams.viewType];
        if (localArrayList.size() < this.mMaxScrap)
          localArrayList.add(paramView);
        if (this.mRecyclerListener != null)
          this.mRecyclerListener.onMovedToScrapHeap(paramView);
      }
    }

    public final void clear()
    {
      int i = this.mViewTypeCount;
      for (int j = 0; j < i; j++)
        this.mScrapViews[j].clear();
      if (this.mTransientStateViews != null)
        this.mTransientStateViews.clear();
    }

    public final void clearTransientViews()
    {
      if (this.mTransientStateViews != null)
        this.mTransientStateViews.clear();
    }

    public final View getScrapView(int paramInt)
    {
      ArrayList localArrayList = this.mScrapViews[paramInt];
      View localView;
      if (localArrayList.isEmpty())
        localView = null;
      while (true)
      {
        return localView;
        int i = -1 + localArrayList.size();
        localView = (View)localArrayList.get(i);
        localArrayList.remove(i);
      }
    }

    public final View getTransientStateView(int paramInt)
    {
      View localView;
      if (this.mTransientStateViews == null)
        localView = null;
      while (true)
      {
        return localView;
        localView = (View)this.mTransientStateViews.get(paramInt);
        if (localView != null)
          this.mTransientStateViews.remove(paramInt);
      }
    }

    public final void setViewTypeCount(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("Must have at least one view type (" + paramInt + " types reported)");
      if (paramInt == this.mViewTypeCount);
      while (true)
      {
        return;
        ArrayList[] arrayOfArrayList = new ArrayList[paramInt];
        for (int i = 0; i < paramInt; i++)
          arrayOfArrayList[i] = new ArrayList();
        this.mViewTypeCount = paramInt;
        this.mScrapViews = arrayOfArrayList;
      }
    }
  }

  public static abstract interface RecyclerListener
  {
    public abstract void onMovedToScrapHeap(View paramView);
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    long firstId = -1L;
    int position;
    SparseBooleanArray selectedPositions;
    boolean selectionMode;
    int topOffset;
    int visibleOffset;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.firstId = paramParcel.readLong();
      this.position = paramParcel.readInt();
      this.visibleOffset = paramParcel.readInt();
      this.topOffset = paramParcel.readInt();
      this.selectedPositions = paramParcel.readSparseBooleanArray();
      if (paramParcel.readInt() == i);
      while (true)
      {
        this.selectionMode = i;
        return;
        i = 0;
      }
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      return "StaggereGridView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " firstId=" + this.firstId + " position=" + this.position + " selected=" + this.selectedPositions + " selectionMode=" + this.selectionMode + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeLong(this.firstId);
      paramParcel.writeInt(this.position);
      paramParcel.writeInt(this.visibleOffset);
      paramParcel.writeInt(this.topOffset);
      paramParcel.writeSparseBooleanArray(this.selectedPositions);
      if (this.selectionMode);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ColumnGridView
 * JD-Core Version:    0.6.2
 */