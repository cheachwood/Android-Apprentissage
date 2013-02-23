package com.google.android.apps.plus.util;

import java.lang.reflect.Method;

public final class SystemProperties
{
  public static String get(String paramString1, String paramString2)
  {
    try
    {
      str = (String)Class.forName("android.os.SystemProperties").getMethod("get", new Class[] { String.class, String.class }).invoke(null, new Object[] { paramString1, paramString2 });
      return str;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        String str = paramString2;
      }
    }
  }

  public static int getInt(String paramString, int paramInt)
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = String.class;
      arrayOfClass[1] = Integer.TYPE;
      Method localMethod = localClass.getMethod("getInt", arrayOfClass);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(0);
      int j = ((Integer)localMethod.invoke(null, arrayOfObject)).intValue();
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        int i = 0;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.SystemProperties
 * JD-Core Version:    0.6.2
 */