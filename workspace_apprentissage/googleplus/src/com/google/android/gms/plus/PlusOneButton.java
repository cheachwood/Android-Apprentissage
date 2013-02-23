package com.google.android.gms.plus;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.internal.ad;

public final class PlusOneButton extends ViewGroup
{
  private final ad dd;

  public PlusOneButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public PlusOneButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.dd = new ad(paramContext, paramAttributeSet);
    addView(this.dd);
    if (isInEditMode());
    while (true)
    {
      return;
      setOnPlusOneClickListener(null);
    }
  }

  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.dd.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
  }

  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    ad localad = this.dd;
    measureChild(localad, paramInt1, paramInt2);
    setMeasuredDimension(localad.getMeasuredWidth(), localad.getMeasuredHeight());
  }

  public final void setAnnotation(int paramInt)
  {
    this.dd.setAnnotation(paramInt);
  }

  public final void setOnPlusOneClickListener(OnPlusOneClickListener paramOnPlusOneClickListener)
  {
    this.dd.setOnPlusOneClickListener(paramOnPlusOneClickListener);
  }

  public final void setSize(int paramInt)
  {
    this.dd.setSize(paramInt);
  }

  public static abstract interface OnPlusOneClickListener
  {
    public abstract void onPlusOneClick(Intent paramIntent);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.plus.PlusOneButton
 * JD-Core Version:    0.6.2
 */