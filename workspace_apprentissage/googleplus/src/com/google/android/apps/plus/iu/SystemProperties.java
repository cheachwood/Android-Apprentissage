package com.google.android.apps.plus.iu;

import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import java.lang.reflect.Method;

public final class SystemProperties
{
  private static final Method sGetLongMethod;

  static
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = String.class;
      arrayOfClass[1] = Long.TYPE;
      Method localMethod2 = localClass.getMethod("getLong", arrayOfClass);
      localMethod1 = localMethod2;
      sGetLongMethod = localMethod1;
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        boolean bool = EsLog.isLoggable("SystemProperties", 6);
        Method localMethod1 = null;
        if (bool)
        {
          Log.e("SystemProperties", "initialize error", localException);
          localMethod1 = null;
        }
      }
    }
  }

  public static long getLong(String paramString, long paramLong)
  {
    try
    {
      if (sGetLongMethod != null)
      {
        Method localMethod = sGetLongMethod;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = Long.valueOf(100L);
        long l2 = ((Long)localMethod.invoke(null, arrayOfObject)).longValue();
        l1 = l2;
        return l1;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        if (EsLog.isLoggable("SystemProperties", 6))
          Log.e("SystemProperties", "get error", localException);
        long l1 = 100L;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.SystemProperties
 * JD-Core Version:    0.6.2
 */