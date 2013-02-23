package com.google.android.gms.plus.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.gms.internal.g;

public final class PlusOneButtonWithPopup extends ViewGroup
{
  private g dl;

  public PlusOneButtonWithPopup(Context paramContext)
  {
    this(paramContext, null);
  }

  public PlusOneButtonWithPopup(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.dl = new g(paramContext, paramAttributeSet);
    addView(this.dl);
    setOnClickListener(null);
  }

  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.dl.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
  }

  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    measureChild(this.dl, paramInt1, paramInt2);
    setMeasuredDimension(this.dl.getMeasuredWidth(), this.dl.getMeasuredHeight());
  }

  public final void setAnnotation(int paramInt)
  {
    this.dl.setAnnotation(paramInt);
  }

  public final void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.dl.setOnClickListener(paramOnClickListener);
  }

  public final void setSize(int paramInt)
  {
    this.dl.setSize(paramInt);
  }

  public final void setType(int paramInt)
  {
    this.dl.setType(paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.plus.internal.PlusOneButtonWithPopup
 * JD-Core Version:    0.6.2
 */