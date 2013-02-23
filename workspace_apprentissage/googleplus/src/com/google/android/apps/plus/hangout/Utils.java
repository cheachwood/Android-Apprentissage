package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Looper;

public final class Utils
{
  private static boolean debuggable;
  private static String version;
  private static String versionName;

  public static RectangleDimensions fitContentInContainer(double paramDouble, int paramInt1, int paramInt2)
  {
    int i;
    if (paramDouble < paramInt1 / paramInt2)
      i = (int)(paramDouble * paramInt2);
    for (int j = paramInt2; ; j = (int)(paramInt1 * Math.pow(paramDouble, -1.0D)))
    {
      if (j <= 0)
        j = 1;
      return new RectangleDimensions(i, j);
      i = paramInt1;
    }
  }

  public static String getVersion()
  {
    return version;
  }

  static void initialize(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    String str = paramContext.getPackageName();
    try
    {
      if ((0x2 & localPackageManager.getApplicationInfo(str, 128).flags) != 0);
      for (boolean bool = true; ; bool = false)
      {
        debuggable = bool;
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(str, 0);
        versionName = localPackageInfo.versionName;
        version = localPackageInfo.versionName + "-" + localPackageInfo.versionCode;
        return;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        debuggable = false;
        versionName = "Error reading version";
      }
    }
  }

  public static boolean isAppInstalled(String paramString, Context paramContext)
  {
    boolean bool = true;
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      localPackageManager.getPackageInfo(paramString, 1);
      return bool;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        bool = false;
    }
  }

  public static boolean isOnMainThread(Context paramContext)
  {
    Looper localLooper = Looper.myLooper();
    if ((localLooper != null) && (localLooper == paramContext.getMainLooper()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.Utils
 * JD-Core Version:    0.6.2
 */