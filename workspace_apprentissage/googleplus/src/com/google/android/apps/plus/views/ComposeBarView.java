package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ComposeBarView extends FrameLayout
  implements ColumnGridView.PressedHighlightable
{
  private OnComposeBarMeasuredListener mOnComposeBarMeasuredListener;

  public ComposeBarView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ComposeBarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public ComposeBarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mOnComposeBarMeasuredListener != null)
      this.mOnComposeBarMeasuredListener.onComposeBarMeasured(getMeasuredWidth(), getMeasuredHeight());
  }

  public void setOnComposeBarMeasuredListener(OnComposeBarMeasuredListener paramOnComposeBarMeasuredListener)
  {
    this.mOnComposeBarMeasuredListener = paramOnComposeBarMeasuredListener;
  }

  public final boolean shouldHighlightOnPress()
  {
    return false;
  }

  public static abstract interface OnComposeBarMeasuredListener
  {
    public abstract void onComposeBarMeasured(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ComposeBarView
 * JD-Core Version:    0.6.2
 */