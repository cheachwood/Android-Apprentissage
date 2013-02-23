package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.apps.plus.R.dimen;

public final class ScreenMetrics
{
  private static ScreenMetrics sInstance;
  public final int itemMargin;
  public final int longDimension;
  public final int screenDisplayType;
  public final int shortDimension;

  private ScreenMetrics(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.shortDimension = paramInt1;
    this.longDimension = paramInt2;
    this.screenDisplayType = paramInt3;
    this.itemMargin = paramInt4;
  }

  public static ScreenMetrics getInstance(Context paramContext)
  {
    ScreenMetrics localScreenMetrics;
    if (sInstance != null)
    {
      localScreenMetrics = sInstance;
      return localScreenMetrics;
    }
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i;
    label58: int j;
    int k;
    int m;
    label92: int n;
    int i1;
    if (paramContext.getResources().getConfiguration().orientation == 2)
    {
      i = 1;
      j = localDisplayMetrics.widthPixels;
      k = localDisplayMetrics.heightPixels;
      if ((j != 0) && (k != 0) && (localDisplayMetrics.density != 0.0F))
        break label156;
      m = 0;
      n = (int)(paramContext.getResources().getDimension(R.dimen.card_margin_percentage) * Math.min(j, k));
      if (i == 0)
        break label204;
      i1 = k;
      label122: if (i == 0)
        break label211;
    }
    while (true)
    {
      localScreenMetrics = new ScreenMetrics(i1, j, m, n);
      sInstance = localScreenMetrics;
      break;
      i = 0;
      break label58;
      label156: float f1 = j / localDisplayMetrics.density;
      float f2 = k / localDisplayMetrics.density;
      if ((f1 >= 550.0F) && (f2 >= 550.0F))
      {
        m = 1;
        break label92;
      }
      m = 0;
      break label92;
      label204: i1 = j;
      break label122;
      label211: j = k;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ScreenMetrics
 * JD-Core Version:    0.6.2
 */