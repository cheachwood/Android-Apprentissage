package com.google.android.apps.plus.views;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PhotoViewPager extends ViewPager
{
  private float mActivatedX;
  private float mActivatedY;
  private int mActivePointerId;
  private float mLastMotionX;
  private OnInterceptTouchListener mListener;

  public PhotoViewPager(Context paramContext)
  {
    super(paramContext);
  }

  public PhotoViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    InterceptType localInterceptType;
    int i;
    label41: int j;
    if (this.mListener != null)
    {
      localInterceptType = this.mListener.onTouchIntercept(this.mActivatedX, this.mActivatedY);
      if ((localInterceptType != InterceptType.BOTH) && (localInterceptType != InterceptType.LEFT))
        break label137;
      i = 1;
      if ((localInterceptType != InterceptType.BOTH) && (localInterceptType != InterceptType.RIGHT))
        break label142;
      j = 1;
      label58: int k = 0xFF & paramMotionEvent.getAction();
      if ((k == 3) || (k == 1))
        this.mActivePointerId = -1;
      switch (k)
      {
      default:
      case 2:
      case 0:
      case 6:
      }
    }
    label120: int m;
    label137: label142: 
    do
    {
      while (true)
      {
        boolean bool = super.onInterceptTouchEvent(paramMotionEvent);
        while (true)
        {
          return bool;
          localInterceptType = InterceptType.NONE;
          break;
          i = 0;
          break label41;
          j = 0;
          break label58;
          if ((i == 0) && (j == 0))
            break label120;
          int i1 = this.mActivePointerId;
          if (i1 == -1)
            break label120;
          float f = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, i1));
          if ((i != 0) && (j != 0))
          {
            this.mLastMotionX = f;
            bool = false;
          }
          else if ((i != 0) && (f > this.mLastMotionX))
          {
            this.mLastMotionX = f;
            bool = false;
          }
          else
          {
            if ((j == 0) || (f >= this.mLastMotionX))
              break label120;
            this.mLastMotionX = f;
            bool = false;
          }
        }
        this.mLastMotionX = paramMotionEvent.getX();
        this.mActivatedX = paramMotionEvent.getRawX();
        this.mActivatedY = paramMotionEvent.getRawY();
        this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
      }
      m = MotionEventCompat.getActionIndex(paramMotionEvent);
    }
    while (MotionEventCompat.getPointerId(paramMotionEvent, m) != this.mActivePointerId);
    if (m == 0);
    for (int n = 1; ; n = 0)
    {
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, n);
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, n);
      break;
    }
  }

  public void setOnInterceptTouchListener(OnInterceptTouchListener paramOnInterceptTouchListener)
  {
    this.mListener = paramOnInterceptTouchListener;
  }

  public static enum InterceptType
  {
    static
    {
      LEFT = new InterceptType("LEFT", 1);
      RIGHT = new InterceptType("RIGHT", 2);
      BOTH = new InterceptType("BOTH", 3);
      InterceptType[] arrayOfInterceptType = new InterceptType[4];
      arrayOfInterceptType[0] = NONE;
      arrayOfInterceptType[1] = LEFT;
      arrayOfInterceptType[2] = RIGHT;
      arrayOfInterceptType[3] = BOTH;
    }
  }

  public static abstract interface OnInterceptTouchListener
  {
    public abstract PhotoViewPager.InterceptType onTouchIntercept(float paramFloat1, float paramFloat2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoViewPager
 * JD-Core Version:    0.6.2
 */