package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class OneUpBaseView extends View
{
  OnMeasuredListener mOnMeasuredListener;

  public OneUpBaseView(Context paramContext)
  {
    super(paramContext);
  }

  public OneUpBaseView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public OneUpBaseView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public static void onStart()
  {
  }

  public static void onStop()
  {
  }

  public void setOnMeasureListener(OnMeasuredListener paramOnMeasuredListener)
  {
    this.mOnMeasuredListener = paramOnMeasuredListener;
  }

  public static abstract interface OnMeasuredListener
  {
    public abstract void onMeasured(View paramView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.OneUpBaseView
 * JD-Core Version:    0.6.2
 */