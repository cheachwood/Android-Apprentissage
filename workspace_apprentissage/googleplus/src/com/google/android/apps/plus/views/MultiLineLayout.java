package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MultiLineLayout extends ViewGroup
{
  private int mChipHeight = 0;
  private int mNumLines = 0;

  public MultiLineLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public final int getHeightForNumLines(int paramInt)
  {
    return getPaddingTop() * (paramInt + 1) + paramInt * this.mChipHeight;
  }

  public final int getNumLines()
  {
    return this.mNumLines;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    // Byte code:
    //   0: new 44	com/google/android/apps/plus/views/MultiLineLayout$1
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 47	com/google/android/apps/plus/views/MultiLineLayout$1:<init>	(Lcom/google/android/apps/plus/views/MultiLineLayout;)V
    //   8: iload 4
    //   10: iload_2
    //   11: isub
    //   12: invokevirtual 51	com/google/android/apps/plus/views/MultiLineLayout$1:apply	(I)V
    //   15: return
  }

  protected void onMeasure(final int paramInt1, final int paramInt2)
  {
    new Rules(paramInt1, paramInt2)
    {
      private int mRequestedHeight = 0;
      private int mRequestedWidth = 0;

      public final void apply(int paramAnonymousInt)
      {
        super.apply(paramAnonymousInt);
        this.mRequestedWidth += MultiLineLayout.this.getPaddingRight();
        this.mRequestedHeight += MultiLineLayout.this.getPaddingBottom();
        MultiLineLayout.this.setMeasuredDimension(View.resolveSize(this.mRequestedWidth, paramInt1), View.resolveSize(this.mRequestedHeight, paramInt2));
      }

      protected final void layout(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        this.mRequestedWidth = Math.max(this.mRequestedWidth, paramAnonymousInt1 + paramAnonymousInt3);
        this.mRequestedHeight = Math.max(this.mRequestedHeight, paramAnonymousInt2 + paramAnonymousInt4);
      }

      protected final void measure(View paramAnonymousView)
      {
        MultiLineLayout.this.measureChild(paramAnonymousView, paramInt1, paramInt2);
      }
    }
    .apply(resolveSize(2147483647, paramInt1));
  }

  private class Rules
  {
    private Rules()
    {
    }

    public void apply(int paramInt)
    {
      int i = MultiLineLayout.this.getPaddingLeft();
      int j = MultiLineLayout.this.getPaddingTop();
      int k = 0;
      int m = MultiLineLayout.this.getPaddingLeft();
      int n = MultiLineLayout.this.getPaddingTop();
      int i1 = paramInt - MultiLineLayout.this.getPaddingLeft() - MultiLineLayout.this.getPaddingRight();
      int i2 = MultiLineLayout.this.getChildCount();
      MultiLineLayout.access$302(MultiLineLayout.this, 1);
      MultiLineLayout.access$402(MultiLineLayout.this, 0);
      for (int i3 = 0; i3 < i2; i3++)
      {
        View localView = MultiLineLayout.this.getChildAt(i3);
        if (localView.getVisibility() != 8)
        {
          measure(localView);
          int i4 = localView.getMeasuredWidth();
          int i5 = localView.getMeasuredHeight();
          if (MultiLineLayout.this.mChipHeight < i5)
            MultiLineLayout.access$402(MultiLineLayout.this, i5);
          if (m + i4 > i1)
          {
            MultiLineLayout.access$308(MultiLineLayout.this);
            m = MultiLineLayout.this.getPaddingLeft();
            n += k + j;
            k = 0;
          }
          layout(localView, m, n, i4, i5);
          m += i4 + i;
          k = Math.max(k, i5);
        }
      }
    }

    protected void layout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
    }

    protected void measure(View paramView)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.MultiLineLayout
 * JD-Core Version:    0.6.2
 */