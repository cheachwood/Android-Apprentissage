package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class CircleNameAndCountView extends ViewGroup
{
  private View mCountTextView;
  private View mIconView;
  private View mNameTextView;

  public CircleNameAndCountView(Context paramContext)
  {
    super(paramContext);
  }

  public CircleNameAndCountView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public CircleNameAndCountView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mNameTextView = findViewById(16908308);
    this.mCountTextView = findViewById(16908309);
    this.mIconView = findViewById(16908294);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mNameTextView.getMeasuredWidth();
    int j = this.mNameTextView.getMeasuredHeight();
    int k = (paramInt4 - paramInt2 - j) / 2;
    int m = getPaddingLeft();
    this.mNameTextView.layout(m, k, m + i, k + j);
    if (this.mCountTextView.getVisibility() == 0)
      this.mCountTextView.layout(m + i, k, m + i + this.mCountTextView.getMeasuredWidth(), k + this.mCountTextView.getMeasuredHeight());
    if (this.mIconView.getVisibility() == 0)
      this.mIconView.layout(paramInt3 - this.mIconView.getLayoutParams().width, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = resolveSize(0, paramInt1);
    int j = resolveSize(0, paramInt2);
    int k;
    int m;
    label39: int n;
    int i1;
    int i2;
    int i3;
    int i4;
    int i6;
    int i7;
    int i8;
    if (this.mCountTextView.getVisibility() == 0)
    {
      k = 1;
      if (this.mIconView.getVisibility() != 0)
        break label282;
      m = 1;
      this.mNameTextView.measure(0, 0);
      n = this.mNameTextView.getMeasuredWidth();
      i1 = this.mNameTextView.getMeasuredHeight();
      i2 = 0;
      i3 = 0;
      if (k != 0)
      {
        this.mCountTextView.measure(0, 0);
        i3 = this.mCountTextView.getMeasuredWidth();
        i2 = this.mCountTextView.getMeasuredHeight();
      }
      i4 = 0;
      if (m != 0)
        i4 = this.mIconView.getLayoutParams().width;
      int i5 = n + i3;
      i6 = getPaddingLeft();
      i7 = getPaddingRight();
      i8 = i7 + (i5 + i6);
      switch (View.MeasureSpec.getMode(paramInt1))
      {
      default:
        label192: this.mNameTextView.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), View.MeasureSpec.makeMeasureSpec(i1, 1073741824));
        if (k != 0)
          this.mCountTextView.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        switch (View.MeasureSpec.getMode(paramInt2))
        {
        default:
        case 0:
        case -2147483648:
        }
        break;
      case 0:
      case -2147483648:
      case 1073741824:
      }
    }
    while (true)
    {
      setMeasuredDimension(i, j);
      return;
      k = 0;
      break;
      label282: m = 0;
      break label39;
      i = i8 + i4;
      break label192;
      if ((i != 0) && (i8 + i4 >= i))
        break label192;
      i = i8 + i4;
      break label192;
      n = Math.min(n, Math.max(i - i6 - i7 - i3 - i4, 0));
      break label192;
      j = Math.max(i1, i2) + getPaddingTop() + getPaddingBottom();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CircleNameAndCountView
 * JD-Core Version:    0.6.2
 */