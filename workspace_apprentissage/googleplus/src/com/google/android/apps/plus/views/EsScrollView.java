package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.ScrollView;

public class EsScrollView extends ScrollView
{
  public EsScrollView(Context paramContext)
  {
    super(paramContext);
  }

  public EsScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EsScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if (super.onInterceptTouchEvent(paramMotionEvent))
    {
      ViewParent localViewParent = getParent();
      if (localViewParent != null)
        localViewParent.requestDisallowInterceptTouchEvent(bool);
    }
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = getScrollY();
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (getScrollY() != i)
    {
      ViewParent localViewParent = getParent();
      if (localViewParent != null)
        localViewParent.requestDisallowInterceptTouchEvent(true);
    }
    return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EsScrollView
 * JD-Core Version:    0.6.2
 */