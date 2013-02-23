package com.google.android.apps.plus.util;

import android.content.Context;
import com.google.android.gsf.EsGservices;

public final class GservicesConfig
{
  public static boolean isInstantShareEnabled(Context paramContext)
  {
    boolean bool = true;
    if ((EsGservices.getBoolean(paramContext.getContentResolver(), "plusone:enable_instant_share", bool)) || (Property.ENABLE_INSTANT_SHARE.getBoolean()));
    while (true)
    {
      return bool;
      bool = false;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.GservicesConfig
 * JD-Core Version:    0.6.2
 */