package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class OneUpTouchHandler extends ViewGroup
{
  private View mActionBar;
  private View mBackground;
  private int[] mLocation = new int[2];
  private View mScrollView;
  private View mTagView;
  private View mTargetView;

  public OneUpTouchHandler(Context paramContext)
  {
    super(paramContext);
  }

  public OneUpTouchHandler(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public OneUpTouchHandler(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getRawX();
    float f2 = paramMotionEvent.getRawY();
    if ((this.mTargetView == null) && (this.mScrollView != null) && (this.mScrollView.getVisibility() == 0))
    {
      View localView2 = this.mScrollView.findViewById(16908298);
      localView2.getLocationOnScreen(this.mLocation);
      if ((f1 >= this.mLocation[0]) && (f1 < this.mLocation[0] + localView2.getWidth()) && (f2 >= this.mLocation[1]) && (f2 < this.mLocation[1] + localView2.getHeight()))
        this.mTargetView = this.mScrollView;
    }
    if ((this.mTargetView == null) && (this.mTagView != null) && (this.mTagView.getVisibility() == 0))
    {
      this.mTagView.getLocationOnScreen(this.mLocation);
      if ((f1 >= this.mLocation[0]) && (f1 < this.mLocation[0] + this.mTagView.getWidth()) && (f2 >= this.mLocation[1]) && (f2 < this.mLocation[1] + this.mTagView.getHeight()))
        this.mTargetView = this.mTagView;
    }
    if ((this.mTargetView == null) && (this.mActionBar != null) && (this.mActionBar.getVisibility() == 0))
    {
      this.mActionBar.getLocationOnScreen(this.mLocation);
      if ((f1 >= this.mLocation[0]) && (f1 < this.mLocation[0] + this.mActionBar.getWidth()) && (f2 >= this.mLocation[1]) && (f2 < this.mLocation[1] + this.mActionBar.getHeight()))
        this.mTargetView = this.mActionBar;
    }
    if ((this.mTargetView == null) && (this.mBackground != null))
      this.mTargetView = this.mBackground;
    View localView1;
    if (this.mTargetView != null)
    {
      localView1 = this.mTargetView;
      int i = paramMotionEvent.getAction();
      if (i != 3)
        break label426;
      paramMotionEvent.setAction(3);
      localView1.dispatchTouchEvent(paramMotionEvent);
      paramMotionEvent.setAction(i);
    }
    while (true)
    {
      int j = paramMotionEvent.getAction();
      if ((j == 3) || (j == 1))
        this.mTargetView = null;
      return true;
      label426: MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
      localMotionEvent.offsetLocation(getScrollX() - localView1.getLeft(), getScrollY() - localView1.getTop());
      localView1.dispatchTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
    }
  }

  public final View getTargetView()
  {
    return this.mTargetView;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return true;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return true;
  }

  public void setActionBar(View paramView)
  {
    this.mActionBar = paramView;
  }

  public void setBackground(View paramView)
  {
    this.mBackground = paramView;
  }

  public void setScrollView(View paramView)
  {
    this.mScrollView = paramView;
  }

  public void setTagLayout(View paramView)
  {
    this.mTagView = paramView;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.OneUpTouchHandler
 * JD-Core Version:    0.6.2
 */