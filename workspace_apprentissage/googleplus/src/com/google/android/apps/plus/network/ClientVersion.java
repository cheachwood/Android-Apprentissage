package com.google.android.apps.plus.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public final class ClientVersion
{
  private static Integer sCachedValue;

  public static int from(Context paramContext)
  {
    try
    {
      if (sCachedValue == null)
        sCachedValue = Integer.valueOf(getVersionCode(paramContext));
      int i = sCachedValue.intValue();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static int getVersionCode(Context paramContext)
  {
    int i = 0;
    try
    {
      i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      label18: return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label18;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ClientVersion
 * JD-Core Version:    0.6.2
 */