package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.phone.ScreenMetrics;

public class CardLayout extends RelativeLayout
  implements Recyclable
{
  private static boolean sInitialized;
  private static int sPaddingBottom;
  private static int sPaddingLeft;
  private static int sPaddingRight;
  private static int sPaddingTop;
  protected static ScreenMetrics sScreenMetrics;

  public CardLayout(Context paramContext)
  {
    super(paramContext, null);
    init(paramContext);
  }

  public CardLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
    init(paramContext);
  }

  public CardLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext);
  }

  private void init(Context paramContext)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sPaddingLeft = (int)localResources.getDimension(R.dimen.card_border_left_padding);
      sPaddingTop = (int)localResources.getDimension(R.dimen.card_border_top_padding);
      sPaddingRight = (int)localResources.getDimension(R.dimen.card_border_right_padding);
      sPaddingBottom = (int)localResources.getDimension(R.dimen.card_border_bottom_padding);
      sScreenMetrics = ScreenMetrics.getInstance(paramContext);
      sInitialized = true;
    }
    setBackgroundResource(R.drawable.bg_tacos);
    setPadding(sPaddingLeft, sPaddingTop, sPaddingRight, sPaddingBottom);
  }

  public void onRecycle()
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CardLayout
 * JD-Core Version:    0.6.2
 */