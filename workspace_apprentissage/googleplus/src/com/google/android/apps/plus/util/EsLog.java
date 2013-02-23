package com.google.android.apps.plus.util;

import android.util.Log;

public final class EsLog
{
  public static final boolean ENABLE_DOGFOOD_FEATURES = Boolean.parseBoolean(Property.ENABLE_DOGFOOD_FEATURES.get());

  public static void doWriteToLog(int paramInt, String paramString1, String paramString2)
  {
    int i = 0;
    for (int j = paramString2.indexOf('\n', 0); j != -1; j = paramString2.indexOf('\n', i))
    {
      Log.println(paramInt, paramString1, paramString2.substring(i, j));
      i = j + 1;
    }
    Log.println(paramInt, paramString1, paramString2.substring(i));
  }

  public static boolean isLoggable(String paramString, int paramInt)
  {
    if (((ENABLE_DOGFOOD_FEATURES) || (paramInt == 6)) && (Log.isLoggable(paramString, paramInt)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static void writeToLog(int paramInt, String paramString1, String paramString2)
  {
    if (!isLoggable(paramString1, paramInt));
    while (true)
    {
      return;
      doWriteToLog(paramInt, paramString1, paramString2);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.EsLog
 * JD-Core Version:    0.6.2
 */