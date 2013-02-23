package com.google.android.apps.plus.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;

public class SlidingTab extends ViewGroup
{
  private Slider currentSlider;
  private float density = getResources().getDisplayMetrics().density;
  private Slider leftSlider = new Slider(this, R.drawable.ic_sliding_tab_jog_left, R.drawable.sliding_tab_jog_tab_target_green, R.drawable.sliding_tab_jog_tab_bar_left, R.drawable.sliding_tab_jog_tab_left);
  private Vibrator mVibrator;
  private OnTriggerListener onTriggerListener;
  private Slider rightSlider = new Slider(this, R.drawable.ic_sliding_tab_jog_right, R.drawable.sliding_tab_jog_tab_target_red, R.drawable.sliding_tab_jog_tab_bar_right, R.drawable.sliding_tab_jog_tab_right);
  private float targetZone;
  private boolean tracking;
  private boolean triggered = false;

  public SlidingTab(Context paramContext)
  {
    this(paramContext, null);
  }

  public SlidingTab(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setRightHintText(R.string.decline_call);
    setLeftHintText(R.string.take_call);
  }

  private void vibrate(long paramLong)
  {
    try
    {
      if (this.mVibrator == null)
        this.mVibrator = ((Vibrator)getContext().getSystemService("vibrator"));
      this.mVibrator.vibrate(paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    Rect localRect = new Rect();
    this.leftSlider.tab.getHitRect(localRect);
    boolean bool1 = localRect.contains((int)f1, (int)f2);
    this.rightSlider.tab.getHitRect(localRect);
    boolean bool2 = localRect.contains((int)f1, (int)f2);
    boolean bool3;
    if ((!this.tracking) && (!bool1))
    {
      bool3 = false;
      if (bool2);
    }
    while (true)
    {
      return bool3;
      switch (i)
      {
      default:
        bool3 = true;
      case 0:
      }
    }
    this.tracking = true;
    this.triggered = false;
    vibrate(30L);
    if (bool1)
    {
      this.currentSlider = this.leftSlider;
      this.targetZone = 0.6666667F;
      this.rightSlider.hide();
    }
    while (true)
    {
      this.currentSlider.setState(1);
      this.currentSlider.showTarget();
      break;
      this.currentSlider = this.rightSlider;
      this.targetZone = 0.3333333F;
      this.leftSlider.hide();
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!paramBoolean);
    while (true)
    {
      return;
      this.leftSlider.layout(paramInt1, paramInt2, paramInt3, paramInt4, 0);
      this.rightSlider.layout(paramInt1, paramInt2, paramInt3, paramInt4, 1);
      invalidate();
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = (int)(0.5F + this.density * this.leftSlider.getTabWidth());
    int k = (int)(0.5F + this.density * this.leftSlider.getTabHeight());
    int m = (int)(0.5F + this.density * this.rightSlider.getTabWidth());
    int n = (int)(0.5F + this.density * this.rightSlider.getTabHeight());
    setMeasuredDimension(Math.min(i, j + m), Math.max(k, n));
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    float f1;
    float f2;
    ImageView localImageView1;
    if (this.tracking)
    {
      int i = paramMotionEvent.getAction();
      f1 = paramMotionEvent.getX();
      f2 = paramMotionEvent.getY();
      localImageView1 = this.currentSlider.tab;
      switch (i)
      {
      default:
      case 2:
      case 1:
      case 3:
      }
    }
    while (true)
    {
      label64: boolean bool1;
      if (!this.tracking)
      {
        boolean bool2 = super.onTouchEvent(paramMotionEvent);
        bool1 = false;
        if (!bool2);
      }
      else
      {
        bool1 = true;
      }
      return bool1;
      ImageView localImageView2 = this.currentSlider.tab;
      TextView localTextView = this.currentSlider.text;
      int j = (int)f1 - localImageView2.getLeft() - localImageView2.getWidth() / 2;
      localImageView2.offsetLeftAndRight(j);
      localTextView.offsetLeftAndRight(j);
      invalidate();
      float f3 = this.targetZone * getWidth();
      int k;
      if (this.currentSlider == this.leftSlider)
        if (f1 > f3)
        {
          k = 1;
          label176: if ((!this.triggered) && (k != 0))
          {
            this.triggered = true;
            this.tracking = false;
            this.currentSlider.setState(2);
            if (this.currentSlider != this.leftSlider)
              break label319;
          }
        }
      label319: for (int m = 0; ; m = 1)
      {
        vibrate(40L);
        switch (m)
        {
        default:
          if ((f2 <= localImageView1.getBottom()) && (f2 >= localImageView1.getTop()))
            break label64;
          this.tracking = false;
          this.triggered = false;
          resetView();
          break label64;
          k = 0;
          break label176;
          if (f1 < f3)
          {
            k = 1;
            break label176;
          }
          k = 0;
          break label176;
        case 0:
        case 1:
        }
      }
      for (Animation localAnimation = AnimationUtils.loadAnimation(getContext(), 17432579); ; localAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left))
      {
        localAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public final void onAnimationEnd(Animation paramAnonymousAnimation)
          {
            SlidingTab.this.setVisibility(4);
            SlidingTab.this.resetView();
          }

          public final void onAnimationRepeat(Animation paramAnonymousAnimation)
          {
          }

          public final void onAnimationStart(Animation paramAnonymousAnimation)
          {
          }
        });
        startAnimation(localAnimation);
        if (this.onTriggerListener == null)
          break;
        break;
      }
      this.tracking = false;
      this.triggered = false;
      resetView();
    }
  }

  public final void resetView()
  {
    this.leftSlider.reset();
    this.rightSlider.reset();
    onLayout(true, getLeft(), getTop(), getLeft() + getWidth(), getTop() + getHeight());
  }

  public void setLeftHintText(int paramInt)
  {
    this.leftSlider.setHintText(paramInt);
  }

  public void setLeftTabDrawables(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    this.leftSlider.setDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }

  public void setLeftTabResources(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.leftSlider.setResources(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setOnTriggerListener(OnTriggerListener paramOnTriggerListener)
  {
    this.onTriggerListener = paramOnTriggerListener;
  }

  public void setRightHintText(int paramInt)
  {
    this.rightSlider.setHintText(paramInt);
  }

  public void setRightTabDrawables(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    this.rightSlider.setDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }

  public void setRightTabResources(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.rightSlider.setResources(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static abstract interface OnTriggerListener
  {
  }

  private static final class Slider
  {
    private final ImageView tab;
    private final ImageView target;
    private final TextView text;

    Slider(ViewGroup paramViewGroup, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.tab = new ImageView(paramViewGroup.getContext());
      this.tab.setBackgroundResource(paramInt4);
      this.tab.setImageResource(paramInt1);
      this.tab.setScaleType(ImageView.ScaleType.CENTER);
      this.tab.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      this.text = new TextView(paramViewGroup.getContext());
      this.text.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
      this.text.setBackgroundResource(paramInt3);
      this.text.setTextAppearance(paramViewGroup.getContext(), R.style.TextAppearance_SlidingTabNormal);
      this.target = new ImageView(paramViewGroup.getContext());
      this.target.setImageResource(paramInt2);
      this.target.setScaleType(ImageView.ScaleType.CENTER);
      this.target.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      this.target.setVisibility(4);
      paramViewGroup.addView(this.target);
      paramViewGroup.addView(this.tab);
      paramViewGroup.addView(this.text);
    }

    public final int getTabHeight()
    {
      return this.tab.getBackground().getIntrinsicHeight();
    }

    public final int getTabWidth()
    {
      return this.tab.getBackground().getIntrinsicWidth();
    }

    final void hide()
    {
      this.text.setVisibility(4);
      this.tab.setVisibility(4);
      this.target.setVisibility(4);
    }

    final void layout(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      int i = this.tab.getBackground().getIntrinsicWidth();
      int j = this.tab.getBackground().getIntrinsicHeight();
      int k = this.target.getDrawable().getIntrinsicWidth();
      int m = this.target.getDrawable().getIntrinsicHeight();
      int n = paramInt3 - paramInt1;
      int i1 = paramInt4 - paramInt2;
      int i2 = (int)(0.6666667F * n) - k + i / 2;
      int i3 = (int)(0.3333333F * n) - i / 2;
      int i4 = (j - m) / 2;
      int i5 = (i1 - j) / 2;
      int i6 = i5 + j;
      if (paramInt5 == 0)
      {
        this.tab.layout(0, i5, i, i6);
        this.text.layout(0 - n, i5, 0, i6);
        this.text.setGravity(5);
        this.target.layout(i2, i5 + i4, i2 + k, m + (i5 + i4));
      }
      while (true)
      {
        return;
        this.tab.layout(n - i, i5, n, i6);
        this.text.layout(n, i5, n + n, i6);
        this.target.layout(i3, i5 + i4, i3 + k, m + (i5 + i4));
        this.text.setGravity(48);
      }
    }

    final void reset()
    {
      setState(0);
      this.text.setVisibility(0);
      this.text.setTextAppearance(this.text.getContext(), R.style.TextAppearance_SlidingTabNormal);
      this.tab.setVisibility(0);
      this.target.setVisibility(4);
    }

    final void setDrawables(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      if (paramDrawable1 != null)
        this.tab.setImageDrawable(paramDrawable1);
      if (paramDrawable4 != null)
        this.tab.setBackgroundDrawable(paramDrawable4);
      if (paramDrawable3 != null)
        this.text.setBackgroundDrawable(paramDrawable3);
      if (paramDrawable4 != null)
        this.target.setImageDrawable(paramDrawable2);
    }

    final void setHintText(int paramInt)
    {
      this.text.setText(paramInt);
    }

    final void setResources(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.tab.setImageResource(paramInt1);
      this.tab.setBackgroundResource(paramInt4);
      this.text.setBackgroundResource(paramInt3);
      this.target.setImageResource(paramInt2);
    }

    final void setState(int paramInt)
    {
      TextView localTextView = this.text;
      boolean bool1;
      boolean bool2;
      if (paramInt == 1)
      {
        bool1 = true;
        localTextView.setPressed(bool1);
        ImageView localImageView = this.tab;
        if (paramInt != 1)
          break label128;
        bool2 = true;
        label31: localImageView.setPressed(bool2);
        if (paramInt != 2)
          break label134;
        int[] arrayOfInt = { 16842914 };
        if (this.text.getBackground().isStateful())
          this.text.getBackground().setState(arrayOfInt);
        if (this.tab.getBackground().isStateful())
          this.tab.getBackground().setState(arrayOfInt);
        this.text.setTextAppearance(this.text.getContext(), R.style.TextAppearance_SlidingTabActive);
      }
      while (true)
      {
        return;
        bool1 = false;
        break;
        label128: bool2 = false;
        break label31;
        label134: this.text.setTextAppearance(this.text.getContext(), R.style.TextAppearance_SlidingTabNormal);
      }
    }

    final void showTarget()
    {
      this.target.setVisibility(0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.SlidingTab
 * JD-Core Version:    0.6.2
 */