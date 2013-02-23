package com.google.android.apps.plus.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import com.google.android.apps.plus.R.drawable;

public final class BackgroundPatternUtils
{
  private static BackgroundPatternUtils sInstance;
  private static BitmapDrawable[] sTiledPlusStageDrawables;

  public static BitmapDrawable getBackgroundPattern(String paramString)
  {
    return sTiledPlusStageDrawables[(0x3 & paramString.hashCode())];
  }

  public static BackgroundPatternUtils getInstance(Context paramContext)
  {
    if (sTiledPlusStageDrawables == null)
    {
      Resources localResources = paramContext.getResources();
      BitmapDrawable[] arrayOfBitmapDrawable = new BitmapDrawable[4];
      arrayOfBitmapDrawable[0] = ((BitmapDrawable)localResources.getDrawable(R.drawable.bg_blue_tile));
      arrayOfBitmapDrawable[1] = ((BitmapDrawable)localResources.getDrawable(R.drawable.bg_green_tile));
      arrayOfBitmapDrawable[2] = ((BitmapDrawable)localResources.getDrawable(R.drawable.bg_red_tile));
      arrayOfBitmapDrawable[3] = ((BitmapDrawable)localResources.getDrawable(R.drawable.bg_yellow_tile));
      sTiledPlusStageDrawables = arrayOfBitmapDrawable;
      int i = 0;
      int j = sTiledPlusStageDrawables.length;
      while (i < j)
      {
        sTiledPlusStageDrawables[i].setTileModeX(Shader.TileMode.REPEAT);
        sTiledPlusStageDrawables[i].setTileModeY(Shader.TileMode.REPEAT);
        i++;
      }
      sInstance = new BackgroundPatternUtils();
    }
    return sInstance;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.BackgroundPatternUtils
 * JD-Core Version:    0.6.2
 */