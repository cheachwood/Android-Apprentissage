package com.google.android.apps.plus.views;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.View.OnTouchListener;

public class TwoPointerGestureDetector extends GestureDetector.SimpleOnGestureListener
  implements View.OnTouchListener
{
  private long mEndTime;
  private final GestureDetector mGestureDetector;
  private MotionEvent.PointerCoords[] mPointerEnd;
  private MotionEvent.PointerCoords[] mPointerStart;
  private long mStartTime;
  private TwoPointerSwipeState mTwoSwipeState = TwoPointerSwipeState.INITIAL;

  public TwoPointerGestureDetector(Context paramContext)
  {
    MotionEvent.PointerCoords[] arrayOfPointerCoords1 = new MotionEvent.PointerCoords[2];
    arrayOfPointerCoords1[0] = new MotionEvent.PointerCoords();
    arrayOfPointerCoords1[1] = new MotionEvent.PointerCoords();
    this.mPointerStart = arrayOfPointerCoords1;
    MotionEvent.PointerCoords[] arrayOfPointerCoords2 = new MotionEvent.PointerCoords[2];
    arrayOfPointerCoords2[0] = new MotionEvent.PointerCoords();
    arrayOfPointerCoords2[1] = new MotionEvent.PointerCoords();
    this.mPointerEnd = arrayOfPointerCoords2;
    this.mGestureDetector = new GestureDetector(paramContext, this);
  }

  private static boolean verifyPointerDistance(MotionEvent.PointerCoords[] paramArrayOfPointerCoords)
  {
    boolean bool = true;
    float f1 = Math.abs(paramArrayOfPointerCoords[0].x - paramArrayOfPointerCoords[bool].x);
    float f2 = Math.abs(paramArrayOfPointerCoords[0].y - paramArrayOfPointerCoords[bool].y);
    if ((f1 <= 100.0F) && (f2 <= 250.0F));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return true;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    int j = paramMotionEvent.getPointerCount();
    switch (1.$SwitchMap$com$google$android$apps$plus$views$TwoPointerGestureDetector$TwoPointerSwipeState[this.mTwoSwipeState.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return this.mGestureDetector.onTouchEvent(paramMotionEvent);
      if ((i == 0) && (j == 1))
      {
        this.mTwoSwipeState = TwoPointerSwipeState.ONE_DOWN;
        continue;
        if (i == 5)
        {
          if (j == 2)
          {
            this.mTwoSwipeState = TwoPointerSwipeState.TWO_DOWN;
            paramMotionEvent.getPointerCoords(0, this.mPointerStart[0]);
            paramMotionEvent.getPointerCoords(1, this.mPointerStart[1]);
            this.mStartTime = paramMotionEvent.getEventTime();
            if (verifyPointerDistance(this.mPointerStart))
              this.mTwoSwipeState = TwoPointerSwipeState.TWO_DOWN;
            else
              this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
          }
          else
          {
            this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
          }
        }
        else if ((i == 1) || (i == 6))
        {
          this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
          continue;
          if (j != 2)
          {
            this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
          }
          else if (i == 6)
          {
            paramMotionEvent.getPointerCoords(0, this.mPointerEnd[0]);
            paramMotionEvent.getPointerCoords(1, this.mPointerEnd[1]);
            this.mEndTime = paramMotionEvent.getEventTime();
            if (verifyPointerDistance(this.mPointerEnd))
            {
              this.mTwoSwipeState = TwoPointerSwipeState.ONE_UP;
            }
            else
            {
              this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
              continue;
              if (j != 1)
                this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
              else if ((i == 5) || (i == 0))
                this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
              else if (i == 1)
                if (paramMotionEvent.getEventTime() - this.mEndTime > 100L)
                {
                  this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
                }
                else
                {
                  this.mTwoSwipeState = TwoPointerSwipeState.INITIAL;
                  long l = this.mEndTime - this.mStartTime;
                  if ((l > 0L) && (l <= 500L))
                  {
                    float f1 = 1000.0F * (this.mPointerEnd[0].x - this.mPointerStart[0].x) / (float)l;
                    float f2 = 1000.0F * (this.mPointerEnd[0].y - this.mPointerStart[0].y) / (float)l;
                    onTwoPointerSwipe(this.mPointerStart[0], this.mPointerEnd[0], f1, f2);
                  }
                }
            }
          }
        }
      }
    }
  }

  public boolean onTwoPointerSwipe(MotionEvent.PointerCoords paramPointerCoords1, MotionEvent.PointerCoords paramPointerCoords2, float paramFloat1, float paramFloat2)
  {
    return true;
  }

  private static enum TwoPointerSwipeState
  {
    static
    {
      ONE_UP = new TwoPointerSwipeState("ONE_UP", 3);
      TwoPointerSwipeState[] arrayOfTwoPointerSwipeState = new TwoPointerSwipeState[4];
      arrayOfTwoPointerSwipeState[0] = INITIAL;
      arrayOfTwoPointerSwipeState[1] = ONE_DOWN;
      arrayOfTwoPointerSwipeState[2] = TWO_DOWN;
      arrayOfTwoPointerSwipeState[3] = ONE_UP;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TwoPointerGestureDetector
 * JD-Core Version:    0.6.2
 */