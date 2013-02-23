package com.google.android.apps.plus.hangout.multiwaveview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.google.android.apps.plus.R.styleable;
import java.util.ArrayList;
import java.util.Iterator;

public class MultiWaveView extends View
{
  private int mActiveTarget = -1;
  private boolean mAnimatingTargets;
  private TimeInterpolator mChevronAnimationInterpolator;
  private ArrayList<Tweener> mChevronAnimations;
  private ArrayList<TargetDrawable> mChevronDrawables = new ArrayList();
  private ArrayList<String> mDirectionDescriptions;
  private int mDirectionDescriptionsResourceId;
  private boolean mDragging;
  private int mFeedbackCount = 3;
  private int mGrabbedState;
  private Tweener mHandleAnimation;
  private TargetDrawable mHandleDrawable;
  private float mHitRadius = 0.0F;
  private float mHorizontalOffset;
  private int mNewTargetResources;
  private OnTriggerListener mOnTriggerListener;
  private float mOuterRadius = 0.0F;
  private TargetDrawable mOuterRing;
  private GradientDrawable mOuterRingDrawable;
  private Animator.AnimatorListener mResetListener;
  private Animator.AnimatorListener mResetListenerWithPing;
  private int mScreenHeight;
  private int mScreenWidth;
  private float mSnapMargin = 0.0F;
  private float mTapRadius;
  private ArrayList<Tweener> mTargetAnimations;
  private ArrayList<String> mTargetDescriptions;
  private int mTargetDescriptionsResourceId;
  private ArrayList<TargetDrawable> mTargetDrawables = new ArrayList();
  private int mTargetResourceId;
  private Animator.AnimatorListener mTargetUpdateListener;
  private ValueAnimator.AnimatorUpdateListener mUpdateListener;
  private float mVerticalOffset;
  private int mVibrationDuration = 0;
  private Vibrator mVibrator;
  private float mWaveCenterX;
  private float mWaveCenterY;

  public MultiWaveView(Context paramContext)
  {
    this(paramContext, null);
  }

  public MultiWaveView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.mChevronAnimationInterpolator = Ease.Quad.easeOut;
      this.mChevronAnimations = new ArrayList();
      this.mTargetAnimations = new ArrayList();
      this.mResetListener = new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          MultiWaveView.this.switchToState$48676aae(0, MultiWaveView.this.mWaveCenterX, MultiWaveView.this.mWaveCenterY);
        }
      };
      this.mResetListenerWithPing = new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          MultiWaveView.this.ping();
          MultiWaveView.this.switchToState$48676aae(0, MultiWaveView.this.mWaveCenterX, MultiWaveView.this.mWaveCenterY);
        }
      };
      this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener()
      {
        public final void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          MultiWaveView.this.invalidateGlobalRegion(MultiWaveView.this.mHandleDrawable);
          MultiWaveView.this.invalidate();
        }
      };
      this.mTargetUpdateListener = new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          if (MultiWaveView.this.mNewTargetResources != 0)
          {
            MultiWaveView.this.internalSetTargetResources(MultiWaveView.this.mNewTargetResources);
            MultiWaveView.access$402(MultiWaveView.this, 0);
            MultiWaveView.this.hideTargets(false);
          }
          MultiWaveView.access$702(MultiWaveView.this, false);
        }
      };
    }
    paramContext.getResources();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MultiWaveView);
    this.mHorizontalOffset = localTypedArray.getDimension(14, this.mHorizontalOffset);
    this.mVerticalOffset = localTypedArray.getDimension(13, this.mVerticalOffset);
    this.mHitRadius = localTypedArray.getDimension(9, this.mHitRadius);
    this.mSnapMargin = localTypedArray.getDimension(11, this.mSnapMargin);
    this.mVibrationDuration = localTypedArray.getInt(10, this.mVibrationDuration);
    this.mFeedbackCount = localTypedArray.getInt(12, this.mFeedbackCount);
    this.mHandleDrawable = new TargetDrawable(localTypedArray.getDrawable(3));
    this.mTapRadius = (this.mHandleDrawable.getWidth() / 2);
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.mScreenWidth = localDisplayMetrics.widthPixels;
    this.mScreenHeight = localDisplayMetrics.heightPixels;
    float f = 0.9F * Math.min(this.mScreenWidth, this.mScreenHeight) / 2.0F;
    int i;
    int[] arrayOfInt;
    int k;
    if (this.mTargetDrawables.size() > 0)
    {
      i = ((TargetDrawable)this.mTargetDrawables.get(0)).getWidth() / 2;
      this.mOuterRadius = (f - i);
      int j = (int)(2.0F * this.mOuterRadius);
      this.mOuterRingDrawable = ((GradientDrawable)localTypedArray.getDrawable(8));
      ((GradientDrawable)this.mOuterRingDrawable.mutate()).setSize(j, j);
      this.mOuterRing = new TargetDrawable(this.mOuterRingDrawable);
      arrayOfInt = new int[] { 4, 5, 6, 7 };
      k = arrayOfInt.length;
    }
    for (int m = 0; ; m++)
    {
      if (m >= k)
        break label557;
      Drawable localDrawable = localTypedArray.getDrawable(arrayOfInt[m]);
      int i2 = 0;
      label494: if (i2 < this.mFeedbackCount)
      {
        ArrayList localArrayList = this.mChevronDrawables;
        if (localDrawable != null);
        for (TargetDrawable localTargetDrawable = new TargetDrawable(localDrawable); ; localTargetDrawable = null)
        {
          localArrayList.add(localTargetDrawable);
          i2++;
          break label494;
          i = 0;
          break;
        }
      }
    }
    label557: TypedValue localTypedValue = new TypedValue();
    if (localTypedArray.getValue(0, localTypedValue))
      internalSetTargetResources(localTypedValue.resourceId);
    if ((this.mTargetDrawables == null) || (this.mTargetDrawables.size() == 0))
      throw new IllegalStateException("Must specify at least one target drawable");
    if (localTypedArray.getValue(1, localTypedValue))
    {
      int i1 = localTypedValue.resourceId;
      if (i1 == 0)
        throw new IllegalStateException("Must specify target descriptions");
      setTargetDescriptionsResourceId(i1);
    }
    if (localTypedArray.getValue(2, localTypedValue))
    {
      int n = localTypedValue.resourceId;
      if (n == 0)
        throw new IllegalStateException("Must specify direction descriptions");
      setDirectionDescriptionsResourceId(n);
    }
    localTypedArray.recycle();
    if (this.mVibrationDuration > 0);
    for (boolean bool = true; ; bool = false)
    {
      setVibrateEnabled(bool);
      return;
    }
  }

  private void announceText(String paramString)
  {
    setContentDescription(paramString);
    sendAccessibilityEvent(8);
    setContentDescription(null);
  }

  private void deactivateTargets()
  {
    Iterator localIterator = this.mTargetDrawables.iterator();
    while (localIterator.hasNext())
      ((TargetDrawable)localIterator.next()).setState(TargetDrawable.STATE_INACTIVE);
    this.mActiveTarget = -1;
  }

  private String getTargetDescription(int paramInt)
  {
    if ((this.mTargetDescriptions == null) || (this.mTargetDescriptions.isEmpty()))
    {
      this.mTargetDescriptions = loadDescriptions(this.mTargetDescriptionsResourceId);
      if (this.mTargetDrawables.size() != this.mTargetDescriptions.size())
        Log.w("MultiWaveView", "The number of target drawables must be equal to the number of target descriptions.");
    }
    for (String str = null; ; str = (String)this.mTargetDescriptions.get(paramInt))
      return str;
  }

  private void handleMove(MotionEvent paramMotionEvent)
  {
    if (!this.mDragging)
      trySwitchToFirstTouchState(paramMotionEvent);
    while (true)
    {
      return;
      int i = -1;
      int j = paramMotionEvent.getHistorySize();
      int k = 0;
      if (k < j + 1)
      {
        float f1;
        label46: float f2;
        label60: float f6;
        label115: float f7;
        float f8;
        int m;
        label153: label183: float f11;
        label202: float f12;
        if (k < j)
        {
          f1 = paramMotionEvent.getHistoricalX(k);
          if (k >= j)
            break label257;
          f2 = paramMotionEvent.getHistoricalY(k);
          float f3 = f1 - this.mWaveCenterX;
          float f4 = f2 - this.mWaveCenterY;
          float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
          if (f5 <= this.mOuterRadius)
            break label266;
          f6 = this.mOuterRadius / f5;
          f7 = this.mWaveCenterX + f3 * f6;
          f8 = this.mWaveCenterY + f4 * f6;
          if (this.mTargetDrawables.size() != 1)
            break label272;
          m = 1;
          if (m == 0)
            break label278;
          if (f5 > this.mOuterRadius - this.mSnapMargin)
          {
            i = 0;
            f1 = f7;
            f2 = f8;
          }
          if (i == -1)
            break label442;
          switchToState$48676aae(3);
          if (m == 0)
            break label404;
          f11 = f7;
          if (m == 0)
            break label423;
          f12 = f8;
          label211: moveHandleTo$483d6f3f(f11, f12);
          TargetDrawable localTargetDrawable1 = (TargetDrawable)this.mTargetDrawables.get(i);
          localTargetDrawable1.hasState$25e2147();
        }
        while (true)
        {
          k++;
          break;
          f1 = paramMotionEvent.getX();
          break label46;
          label257: f2 = paramMotionEvent.getY();
          break label60;
          label266: f6 = 1.0F;
          break label115;
          label272: m = 0;
          break label153;
          label278: float f9 = 3.4028235E+38F;
          float f10 = this.mHitRadius * this.mHitRadius;
          for (int n = 0; n < this.mTargetDrawables.size(); n++)
          {
            TargetDrawable localTargetDrawable2 = (TargetDrawable)this.mTargetDrawables.get(n);
            float f13 = f7 - localTargetDrawable2.getX();
            float f14 = f8 - localTargetDrawable2.getY();
            float f15 = f13 * f13 + f14 * f14;
            if ((localTargetDrawable2.isValid()) && (f15 < f10) && (f15 < f9))
            {
              i = n;
              f9 = f15;
            }
          }
          f1 = f7;
          f2 = f8;
          break label183;
          label404: f11 = ((TargetDrawable)this.mTargetDrawables.get(i)).getX();
          break label202;
          label423: f12 = ((TargetDrawable)this.mTargetDrawables.get(i)).getY();
          break label211;
          label442: switchToState$48676aae(2);
          moveHandleTo$483d6f3f(f1, f2);
          this.mHandleDrawable.setAlpha(1.0F);
        }
      }
      invalidateGlobalRegion(this.mHandleDrawable);
      if ((this.mActiveTarget != i) && (i != -1))
      {
        vibrate();
        if ((this.mOnTriggerListener == null) || (((AccessibilityManager)getContext().getSystemService("accessibility")).isEnabled()))
          announceText(getTargetDescription(i));
      }
      this.mActiveTarget = i;
    }
  }

  private void hideChevrons()
  {
    Iterator localIterator = this.mChevronDrawables.iterator();
    while (localIterator.hasNext())
    {
      TargetDrawable localTargetDrawable = (TargetDrawable)localIterator.next();
      if (localTargetDrawable != null)
        localTargetDrawable.setAlpha(0.0F);
    }
  }

  private void hideTargets(boolean paramBoolean)
  {
    if ((this.mTargetAnimations != null) && (this.mTargetAnimations.size() > 0))
      stopTargetAnimation();
    this.mAnimatingTargets = paramBoolean;
    if ((paramBoolean) && (Build.VERSION.SDK_INT >= 11))
    {
      if (paramBoolean);
      for (int i = 1200; ; i = 0)
      {
        Iterator localIterator2 = this.mTargetDrawables.iterator();
        while (localIterator2.hasNext())
        {
          TargetDrawable localTargetDrawable3 = (TargetDrawable)localIterator2.next();
          localTargetDrawable3.setState(TargetDrawable.STATE_INACTIVE);
          ArrayList localArrayList2 = this.mTargetAnimations;
          long l2 = i;
          Object[] arrayOfObject2 = new Object[6];
          arrayOfObject2[0] = "alpha";
          arrayOfObject2[1] = Float.valueOf(0.0F);
          arrayOfObject2[2] = "delay";
          arrayOfObject2[3] = Integer.valueOf(200);
          arrayOfObject2[4] = "onUpdate";
          arrayOfObject2[5] = this.mUpdateListener;
          localArrayList2.add(Tweener.to(localTargetDrawable3, l2, arrayOfObject2));
        }
      }
      ArrayList localArrayList1 = this.mTargetAnimations;
      TargetDrawable localTargetDrawable2 = this.mOuterRing;
      long l1 = i;
      Object[] arrayOfObject1 = new Object[8];
      arrayOfObject1[0] = "alpha";
      arrayOfObject1[1] = Float.valueOf(0.0F);
      arrayOfObject1[2] = "delay";
      arrayOfObject1[3] = Integer.valueOf(200);
      arrayOfObject1[4] = "onUpdate";
      arrayOfObject1[5] = this.mUpdateListener;
      arrayOfObject1[6] = "onComplete";
      arrayOfObject1[7] = this.mTargetUpdateListener;
      localArrayList1.add(Tweener.to(localTargetDrawable2, l1, arrayOfObject1));
    }
    while (true)
    {
      return;
      Iterator localIterator1 = this.mTargetDrawables.iterator();
      while (localIterator1.hasNext())
      {
        TargetDrawable localTargetDrawable1 = (TargetDrawable)localIterator1.next();
        localTargetDrawable1.setState(TargetDrawable.STATE_INACTIVE);
        localTargetDrawable1.setAlpha(0.0F);
      }
      this.mOuterRing.setAlpha(0.0F);
    }
  }

  private void hideUnselected(int paramInt)
  {
    for (int i = 0; i < this.mTargetDrawables.size(); i++)
      if (i != paramInt)
        ((TargetDrawable)this.mTargetDrawables.get(i)).setAlpha(0.0F);
    this.mOuterRing.setAlpha(0.0F);
  }

  private void internalSetTargetResources(int paramInt)
  {
    TypedArray localTypedArray = getContext().getResources().obtainTypedArray(paramInt);
    int i = localTypedArray.length();
    int j = 0;
    ArrayList localArrayList = new ArrayList(i);
    for (int k = 0; k < i; k++)
    {
      Drawable localDrawable = localTypedArray.getDrawable(k);
      localArrayList.add(new TargetDrawable(localDrawable));
      if ((localDrawable != null) && (localDrawable.getIntrinsicWidth() > j))
        j = localDrawable.getIntrinsicWidth();
    }
    this.mOuterRadius = (0.9F * Math.min(getWidth(), getHeight()) / 2.0F);
    int m = 2 * (int)this.mOuterRadius;
    ((GradientDrawable)this.mOuterRingDrawable.mutate()).setSize(m, m);
    this.mOuterRing = new TargetDrawable(this.mOuterRingDrawable);
    localTypedArray.recycle();
    this.mTargetResourceId = paramInt;
    this.mTargetDrawables = localArrayList;
    updateTargetPositions();
  }

  private ArrayList<String> loadDescriptions(int paramInt)
  {
    TypedArray localTypedArray = getContext().getResources().obtainTypedArray(paramInt);
    int i = localTypedArray.length();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
      localArrayList.add(localTypedArray.getString(j));
    localTypedArray.recycle();
    return localArrayList;
  }

  private void moveHandleTo$483d6f3f(float paramFloat1, float paramFloat2)
  {
    this.mHandleDrawable.setX(paramFloat1);
    this.mHandleDrawable.setY(paramFloat2);
  }

  private static int resolveMeasured(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j;
    switch (View.MeasureSpec.getMode(paramInt1))
    {
    default:
      j = i;
    case 0:
    case -2147483648:
    }
    while (true)
    {
      return j;
      j = paramInt2;
      continue;
      j = Math.min(i, paramInt2);
    }
  }

  private void setGrabbedState(int paramInt)
  {
    if (paramInt != this.mGrabbedState)
    {
      if (paramInt != 0)
        vibrate();
      this.mGrabbedState = paramInt;
      if (this.mOnTriggerListener == null);
    }
  }

  private void stopChevronAnimation()
  {
    if (Build.VERSION.SDK_INT < 11);
    while (true)
    {
      return;
      Iterator localIterator = this.mChevronAnimations.iterator();
      while (localIterator.hasNext())
        ((Tweener)localIterator.next()).animator.end();
      this.mChevronAnimations.clear();
    }
  }

  private void stopHandleAnimation()
  {
    if (Build.VERSION.SDK_INT < 11);
    while (true)
    {
      return;
      if (this.mHandleAnimation != null)
      {
        this.mHandleAnimation.animator.end();
        this.mHandleAnimation = null;
      }
    }
  }

  private void stopTargetAnimation()
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      Iterator localIterator = this.mTargetAnimations.iterator();
      while (localIterator.hasNext())
        ((Tweener)localIterator.next()).animator.end();
      this.mTargetAnimations.clear();
    }
  }

  private void switchToState$48676aae(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
    case 3:
    default:
    case 0:
    case 1:
      StringBuilder localStringBuilder;
      int m;
      int n;
      do
      {
        do
        {
          while (true)
          {
            return;
            deactivateTargets();
            this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
          }
          stopHandleAnimation();
          deactivateTargets();
          if ((this.mTargetAnimations != null) && (this.mTargetAnimations.size() > 0))
            stopTargetAnimation();
          this.mAnimatingTargets = false;
          Iterator localIterator = this.mTargetDrawables.iterator();
          while (localIterator.hasNext())
          {
            TargetDrawable localTargetDrawable4 = (TargetDrawable)localIterator.next();
            localTargetDrawable4.setState(TargetDrawable.STATE_INACTIVE);
            localTargetDrawable4.setAlpha(1.0F);
          }
          this.mOuterRing.setAlpha(1.0F);
          this.mHandleDrawable.setState(TargetDrawable.STATE_ACTIVE);
          setGrabbedState(1);
        }
        while (!((AccessibilityManager)getContext().getSystemService("accessibility")).isEnabled());
        localStringBuilder = new StringBuilder();
        m = this.mTargetDrawables.size();
        n = 0;
      }
      while (n >= m);
      String str = getTargetDescription(n);
      if ((this.mDirectionDescriptions == null) || (this.mDirectionDescriptions.isEmpty()))
      {
        this.mDirectionDescriptions = loadDescriptions(this.mDirectionDescriptionsResourceId);
        if (this.mTargetDrawables.size() != this.mDirectionDescriptions.size())
          Log.w("MultiWaveView", "The number of target drawables must be euqal to the number of direction descriptions.");
      }
      for (Object localObject = null; ; localObject = (String)this.mDirectionDescriptions.get(n))
      {
        if ((!TextUtils.isEmpty(str)) && (!TextUtils.isEmpty((CharSequence)localObject)))
          localStringBuilder.append(String.format((String)localObject, new Object[] { str }));
        if (localStringBuilder.length() > 0)
          announceText(localStringBuilder.toString());
        n++;
        break;
      }
    case 4:
    }
    int i = this.mActiveTarget;
    int j;
    label361: float f;
    if (i != -1)
    {
      j = 1;
      hideTargets(true);
      TargetDrawable localTargetDrawable1 = this.mHandleDrawable;
      if (j == 0)
        break label630;
      f = 0.0F;
      label379: localTargetDrawable1.setAlpha(f);
      if (j == 0)
        break label679;
      ((TargetDrawable)this.mTargetDrawables.get(i)).setState(TargetDrawable.STATE_ACTIVE);
      hideUnselected(i);
      Log.v("MultiWaveView", "Finish with target hit = " + true);
      int k = this.mActiveTarget;
      vibrate();
      if (this.mOnTriggerListener != null)
        this.mOnTriggerListener.onTrigger$5359dc9a(k);
      if (Build.VERSION.SDK_INT < 11)
        break label636;
      TargetDrawable localTargetDrawable3 = this.mHandleDrawable;
      Object[] arrayOfObject2 = new Object[14];
      arrayOfObject2[0] = "ease";
      arrayOfObject2[1] = Ease.Quart.easeOut;
      arrayOfObject2[2] = "delay";
      arrayOfObject2[3] = Integer.valueOf(1200);
      arrayOfObject2[4] = "alpha";
      arrayOfObject2[5] = Float.valueOf(1.0F);
      arrayOfObject2[6] = "x";
      arrayOfObject2[7] = Float.valueOf(this.mWaveCenterX);
      arrayOfObject2[8] = "y";
      arrayOfObject2[9] = Float.valueOf(this.mWaveCenterY);
      arrayOfObject2[10] = "onUpdate";
      arrayOfObject2[11] = this.mUpdateListener;
      arrayOfObject2[12] = "onComplete";
      arrayOfObject2[13] = this.mResetListener;
      this.mHandleAnimation = Tweener.to(localTargetDrawable3, 0L, arrayOfObject2);
    }
    while (true)
    {
      setGrabbedState(0);
      break;
      j = 0;
      break label361;
      label630: f = 1.0F;
      break label379;
      label636: this.mHandleDrawable.setAlpha(0.0F);
      this.mHandleDrawable.setX(this.mWaveCenterX);
      this.mHandleDrawable.setY(this.mWaveCenterY);
      this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
      continue;
      label679: if (Build.VERSION.SDK_INT >= 11)
      {
        TargetDrawable localTargetDrawable2 = this.mHandleDrawable;
        Object[] arrayOfObject1 = new Object[14];
        arrayOfObject1[0] = "ease";
        arrayOfObject1[1] = Ease.Quart.easeOut;
        arrayOfObject1[2] = "delay";
        arrayOfObject1[3] = Integer.valueOf(0);
        arrayOfObject1[4] = "alpha";
        arrayOfObject1[5] = Float.valueOf(1.0F);
        arrayOfObject1[6] = "x";
        arrayOfObject1[7] = Float.valueOf(this.mWaveCenterX);
        arrayOfObject1[8] = "y";
        arrayOfObject1[9] = Float.valueOf(this.mWaveCenterY);
        arrayOfObject1[10] = "onUpdate";
        arrayOfObject1[11] = this.mUpdateListener;
        arrayOfObject1[12] = "onComplete";
        if (this.mDragging);
        for (Animator.AnimatorListener localAnimatorListener = this.mResetListenerWithPing; ; localAnimatorListener = this.mResetListener)
        {
          arrayOfObject1[13] = localAnimatorListener;
          this.mHandleAnimation = Tweener.to(localTargetDrawable2, 300L, arrayOfObject1);
          break;
        }
      }
      this.mHandleDrawable.setX(this.mWaveCenterX);
      this.mHandleDrawable.setY(this.mWaveCenterY);
      this.mHandleDrawable.setAlpha(1.0F);
      this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
    }
  }

  private boolean trySwitchToFirstTouchState(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    float f3 = f1 - this.mWaveCenterX;
    float f4 = f2 - this.mWaveCenterY;
    float f5 = f3 * f3 + f4 * f4;
    float f6;
    if (((AccessibilityManager)getContext().getSystemService("accessibility")).isEnabled())
    {
      f6 = 1.3F * this.mTapRadius;
      if (f5 > f6 * f6)
        break label120;
      Log.v("MultiWaveView", "** Handle HIT");
      switchToState$48676aae(1);
      moveHandleTo$483d6f3f(f1, f2);
      this.mDragging = true;
    }
    label120: for (boolean bool = true; ; bool = false)
    {
      return bool;
      f6 = this.mTapRadius;
      break;
    }
  }

  private void updateTargetPositions()
  {
    for (int i = 0; i < this.mTargetDrawables.size(); i++)
    {
      TargetDrawable localTargetDrawable = (TargetDrawable)this.mTargetDrawables.get(i);
      double d = -6.283185307179586D * i / this.mTargetDrawables.size();
      float f1 = this.mWaveCenterX + this.mOuterRadius * (float)Math.cos(d);
      float f2 = this.mWaveCenterY + this.mOuterRadius * (float)Math.sin(d);
      localTargetDrawable.setX(f1);
      localTargetDrawable.setY(f2);
    }
  }

  private void vibrate()
  {
    if (this.mVibrator != null)
      this.mVibrator.vibrate(this.mVibrationDuration);
  }

  protected int getSuggestedMinimumHeight()
  {
    int i = this.mOuterRing.getHeight();
    int j = this.mTargetDrawables.size();
    int k = 0;
    if (j > 0)
      k = ((TargetDrawable)this.mTargetDrawables.get(0)).getHeight() / 2;
    return k + i;
  }

  protected int getSuggestedMinimumWidth()
  {
    int i = this.mOuterRing.getWidth();
    int j = this.mTargetDrawables.size();
    int k = 0;
    if (j > 0)
      k = ((TargetDrawable)this.mTargetDrawables.get(0)).getWidth() / 2;
    return k + i;
  }

  final void invalidateGlobalRegion(TargetDrawable paramTargetDrawable)
  {
    int i = paramTargetDrawable.getWidth();
    int j = paramTargetDrawable.getHeight();
    RectF localRectF = new RectF(0.0F, 0.0F, i, j);
    localRectF.offset(paramTargetDrawable.getX() - i / 2, paramTargetDrawable.getY() - j / 2);
    Object localObject = this;
    while ((((View)localObject).getParent() != null) && ((((View)localObject).getParent() instanceof View)))
    {
      localObject = (View)((View)localObject).getParent();
      if (Build.VERSION.SDK_INT >= 11)
        ((View)localObject).getMatrix().mapRect(localRectF);
      ((View)localObject).invalidate((int)Math.floor(localRectF.left), (int)Math.floor(localRectF.top), (int)Math.ceil(localRectF.right), (int)Math.ceil(localRectF.bottom));
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    this.mOuterRing.draw(paramCanvas);
    Iterator localIterator1 = this.mTargetDrawables.iterator();
    while (localIterator1.hasNext())
    {
      TargetDrawable localTargetDrawable2 = (TargetDrawable)localIterator1.next();
      if (localTargetDrawable2 != null)
        localTargetDrawable2.draw(paramCanvas);
    }
    Iterator localIterator2 = this.mChevronDrawables.iterator();
    while (localIterator2.hasNext())
    {
      TargetDrawable localTargetDrawable1 = (TargetDrawable)localIterator2.next();
      if (localTargetDrawable1 != null)
        localTargetDrawable1.draw(paramCanvas);
    }
    this.mHandleDrawable.draw(paramCanvas);
  }

  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    int i;
    if (((AccessibilityManager)getContext().getSystemService("accessibility")).isTouchExplorationEnabled())
    {
      i = paramMotionEvent.getAction();
      switch (i)
      {
      case 8:
      default:
      case 9:
      case 7:
      case 10:
      }
    }
    while (true)
    {
      onTouchEvent(paramMotionEvent);
      paramMotionEvent.setAction(i);
      return super.onHoverEvent(paramMotionEvent);
      paramMotionEvent.setAction(0);
      continue;
      paramMotionEvent.setAction(2);
      continue;
      paramMotionEvent.setAction(1);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    float f1 = this.mHorizontalOffset + Math.max(i, this.mOuterRing.getWidth()) / 2;
    float f2 = this.mVerticalOffset + Math.max(j, this.mOuterRing.getHeight()) / 2;
    if ((f1 != this.mWaveCenterX) || (f2 != this.mWaveCenterY))
    {
      if ((this.mWaveCenterX == 0.0F) && (this.mWaveCenterY == 0.0F))
      {
        if (this.mOuterRadius == 0.0F)
          this.mOuterRadius = (0.5F * (float)Math.sqrt(f1 * f1 + f2 * f2));
        if (this.mHitRadius == 0.0F)
          this.mHitRadius = (((TargetDrawable)this.mTargetDrawables.get(0)).getWidth() / 2.0F);
        if (this.mSnapMargin == 0.0F)
          this.mSnapMargin = TypedValue.applyDimension(1, 20.0F, getContext().getResources().getDisplayMetrics());
        hideChevrons();
        hideTargets(false);
        moveHandleTo$483d6f3f(f1, f2);
      }
      this.mWaveCenterX = f1;
      this.mWaveCenterY = f2;
      this.mOuterRing.setX(this.mWaveCenterX);
      this.mOuterRing.setY(this.mWaveCenterY);
      updateTargetPositions();
    }
    Log.v("MultiWaveView", "Outer Radius = " + this.mOuterRadius);
    Log.v("MultiWaveView", "HitRadius = " + this.mHitRadius);
    Log.v("MultiWaveView", "SnapMargin = " + this.mSnapMargin);
    Log.v("MultiWaveView", "FeedbackCount = " + this.mFeedbackCount);
    Log.v("MultiWaveView", "VibrationDuration = " + this.mVibrationDuration);
    Log.v("MultiWaveView", "TapRadius = " + this.mTapRadius);
    Log.v("MultiWaveView", "WaveCenterX = " + this.mWaveCenterX);
    Log.v("MultiWaveView", "WaveCenterY = " + this.mWaveCenterY);
    Log.v("MultiWaveView", "HorizontalOffset = " + this.mHorizontalOffset);
    Log.v("MultiWaveView", "VerticalOffset = " + this.mVerticalOffset);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getSuggestedMinimumWidth();
    int j = getSuggestedMinimumHeight();
    int k = resolveMeasured(paramInt1, i);
    int m = resolveMeasured(paramInt2, j);
    this.mOuterRadius = (0.9F * Math.min(k - getPaddingLeft() - getPaddingRight(), m - getPaddingTop() - getPaddingBottom()) / 2.0F);
    int n = (int)(2.0F * this.mOuterRadius);
    ((GradientDrawable)this.mOuterRingDrawable.mutate()).setSize(n, n);
    getResources();
    this.mOuterRing = new TargetDrawable(this.mOuterRingDrawable);
    this.mOuterRing.setX(getWidth() / 2);
    this.mOuterRing.setY(getHeight() / 2);
    setMeasuredDimension(k, m);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    int j = 0;
    switch (i)
    {
    default:
      invalidate();
      if (j == 0)
        break;
    case 0:
    case 2:
    case 1:
    case 3:
    }
    for (boolean bool = true; ; bool = super.onTouchEvent(paramMotionEvent))
    {
      return bool;
      if (!trySwitchToFirstTouchState(paramMotionEvent))
      {
        this.mDragging = false;
        stopTargetAnimation();
        ping();
      }
      j = 1;
      break;
      handleMove(paramMotionEvent);
      j = 1;
      break;
      handleMove(paramMotionEvent);
      if (this.mDragging)
        Log.v("MultiWaveView", "** Handle RELEASE");
      paramMotionEvent.getX();
      paramMotionEvent.getY();
      switchToState$48676aae(4);
      j = 1;
      break;
      handleMove(paramMotionEvent);
      j = 1;
      break;
    }
  }

  public final void ping()
  {
    stopChevronAnimation();
    if (Build.VERSION.SDK_INT >= 11)
    {
      float f1 = 0.4F * this.mHandleDrawable.getWidth();
      float f2 = 0.9F * this.mOuterRadius;
      float[][] arrayOfFloat1 = new float[4][];
      float[] arrayOfFloat2 = new float[2];
      arrayOfFloat2[0] = (this.mWaveCenterX - f1);
      arrayOfFloat2[1] = this.mWaveCenterY;
      arrayOfFloat1[0] = arrayOfFloat2;
      float[] arrayOfFloat3 = new float[2];
      arrayOfFloat3[0] = (f1 + this.mWaveCenterX);
      arrayOfFloat3[1] = this.mWaveCenterY;
      arrayOfFloat1[1] = arrayOfFloat3;
      float[] arrayOfFloat4 = new float[2];
      arrayOfFloat4[0] = this.mWaveCenterX;
      arrayOfFloat4[1] = (this.mWaveCenterY - f1);
      arrayOfFloat1[2] = arrayOfFloat4;
      float[] arrayOfFloat5 = new float[2];
      arrayOfFloat5[0] = this.mWaveCenterX;
      arrayOfFloat5[1] = (f1 + this.mWaveCenterY);
      arrayOfFloat1[3] = arrayOfFloat5;
      float[][] arrayOfFloat6 = new float[4][];
      float[] arrayOfFloat7 = new float[2];
      arrayOfFloat7[0] = (this.mWaveCenterX - f2);
      arrayOfFloat7[1] = this.mWaveCenterY;
      arrayOfFloat6[0] = arrayOfFloat7;
      float[] arrayOfFloat8 = new float[2];
      arrayOfFloat8[0] = (f2 + this.mWaveCenterX);
      arrayOfFloat8[1] = this.mWaveCenterY;
      arrayOfFloat6[1] = arrayOfFloat8;
      float[] arrayOfFloat9 = new float[2];
      arrayOfFloat9[0] = this.mWaveCenterX;
      arrayOfFloat9[1] = (this.mWaveCenterY - f2);
      arrayOfFloat6[2] = arrayOfFloat9;
      float[] arrayOfFloat10 = new float[2];
      arrayOfFloat10[0] = this.mWaveCenterX;
      arrayOfFloat10[1] = (f2 + this.mWaveCenterY);
      arrayOfFloat6[3] = arrayOfFloat10;
      this.mChevronAnimations.clear();
      for (int i = 0; i < 4; i++)
        for (int j = 0; j < this.mFeedbackCount; j++)
        {
          int k = j * 160;
          TargetDrawable localTargetDrawable = (TargetDrawable)this.mChevronDrawables.get(j + i * this.mFeedbackCount);
          if (localTargetDrawable != null)
          {
            ArrayList localArrayList = this.mChevronAnimations;
            Object[] arrayOfObject = new Object[16];
            arrayOfObject[0] = "ease";
            arrayOfObject[1] = this.mChevronAnimationInterpolator;
            arrayOfObject[2] = "delay";
            arrayOfObject[3] = Integer.valueOf(k);
            arrayOfObject[4] = "x";
            float[] arrayOfFloat11 = new float[2];
            arrayOfFloat11[0] = arrayOfFloat1[i][0];
            arrayOfFloat11[1] = arrayOfFloat6[i][0];
            arrayOfObject[5] = arrayOfFloat11;
            arrayOfObject[6] = "y";
            float[] arrayOfFloat12 = new float[2];
            arrayOfFloat12[0] = arrayOfFloat1[i][1];
            arrayOfFloat12[1] = arrayOfFloat6[i][1];
            arrayOfObject[7] = arrayOfFloat12;
            arrayOfObject[8] = "alpha";
            arrayOfObject[9] = { 1.0F, 0.0F };
            arrayOfObject[10] = "scaleX";
            arrayOfObject[11] = { 0.5F, 2.0F };
            arrayOfObject[12] = "scaleY";
            arrayOfObject[13] = { 0.5F, 2.0F };
            arrayOfObject[14] = "onUpdate";
            arrayOfObject[15] = this.mUpdateListener;
            localArrayList.add(Tweener.to(localTargetDrawable, 850L, arrayOfObject));
          }
        }
    }
  }

  public final void reset(boolean paramBoolean)
  {
    stopChevronAnimation();
    stopHandleAnimation();
    stopTargetAnimation();
    hideChevrons();
    hideTargets(false);
    this.mHandleDrawable.setX(this.mWaveCenterX);
    this.mHandleDrawable.setY(this.mWaveCenterY);
    this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
    this.mHandleDrawable.setAlpha(1.0F);
    if (Build.VERSION.SDK_INT >= 11)
      Tweener.reset();
  }

  public void setDirectionDescriptionsResourceId(int paramInt)
  {
    this.mDirectionDescriptionsResourceId = paramInt;
    if (this.mDirectionDescriptions != null)
      this.mDirectionDescriptions.clear();
  }

  public void setOnTriggerListener(OnTriggerListener paramOnTriggerListener)
  {
    this.mOnTriggerListener = paramOnTriggerListener;
  }

  public void setTargetDescriptionsResourceId(int paramInt)
  {
    this.mTargetDescriptionsResourceId = paramInt;
    if (this.mTargetDescriptions != null)
      this.mTargetDescriptions.clear();
  }

  public void setTargetResources(int paramInt)
  {
    if (this.mAnimatingTargets)
      this.mNewTargetResources = paramInt;
    while (true)
    {
      return;
      internalSetTargetResources(paramInt);
    }
  }

  public void setVibrateEnabled(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.mVibrator == null));
    try
    {
      for (this.mVibrator = ((Vibrator)getContext().getSystemService("vibrator")); ; this.mVibrator = null)
        label28: return;
    }
    catch (Exception localException)
    {
      break label28;
    }
  }

  public static abstract interface OnTriggerListener
  {
    public abstract void onTrigger$5359dc9a(int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.multiwaveview.MultiWaveView
 * JD-Core Version:    0.6.2
 */