package com.google.android.picasastore;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public final class Config
{
  public static int sScreenNailSize = 640;
  public static int sThumbNailSize = 200;

  public static void init(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i = Math.max(localDisplayMetrics.heightPixels, localDisplayMetrics.widthPixels);
    sScreenNailSize = i / 2;
    sThumbNailSize = i / 5;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.Config
 * JD-Core Version:    0.6.2
 */