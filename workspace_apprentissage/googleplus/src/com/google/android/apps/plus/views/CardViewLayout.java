package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;

public class CardViewLayout extends ExactLayout
  implements Recyclable
{
  private static Drawable sBackground;
  private static boolean sInitialized;
  private static int sPaddingBottom;
  private static int sPaddingLeft;
  private static int sPaddingRight;
  private static int sPaddingTop;

  public CardViewLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public CardViewLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public CardViewLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private static int adjustPadding(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 1; ; i = -1)
      return Math.max(paramInt1 + i * paramInt2, 0);
  }

  protected void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    boolean bool1 = true;
    Resources localResources = paramContext.getResources();
    if (!sInitialized)
    {
      sPaddingLeft = (int)localResources.getDimension(R.dimen.card_border_left_padding);
      sPaddingTop = (int)localResources.getDimension(R.dimen.card_border_top_padding);
      sPaddingRight = (int)localResources.getDimension(R.dimen.card_border_right_padding);
      sPaddingBottom = (int)localResources.getDimension(R.dimen.card_border_bottom_padding);
      sBackground = localResources.getDrawable(R.drawable.bg_tacos);
      sInitialized = bool1;
    }
    toggleCardBorderStyle(bool1);
    if (paramContext.getResources().getConfiguration().orientation == 2);
    for (boolean bool2 = bool1; ; bool2 = false)
    {
      int i;
      if (bool2)
        i = 2;
      setLayoutParams(new ColumnGridView.LayoutParams(i, -3));
      return;
    }
  }

  public final void toggleCardBorderStyle(boolean paramBoolean)
  {
    if (paramBoolean);
    for (Drawable localDrawable = sBackground; ; localDrawable = null)
    {
      setBackground(localDrawable);
      setPadding(adjustPadding(getPaddingLeft(), sPaddingLeft, paramBoolean), adjustPadding(getPaddingTop(), sPaddingTop, paramBoolean), adjustPadding(getPaddingRight(), sPaddingRight, paramBoolean), adjustPadding(getPaddingBottom(), sPaddingBottom, paramBoolean));
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CardViewLayout
 * JD-Core Version:    0.6.2
 */