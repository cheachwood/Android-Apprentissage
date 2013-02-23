package com.android.gallery3d.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.apps.plus.util.Property;
import java.io.Closeable;
import java.io.InterruptedIOException;

public final class Utils
{
  private static final boolean IS_DEBUG_BUILD;
  private static long[] sCrcTable = new long[256];

  static
  {
    boolean bool;
    if ((Property.ENABLE_DOGFOOD_FEATURES.getBoolean()) || (Build.TYPE.equals("eng")) || (Build.TYPE.equals("userdebug")))
    {
      bool = true;
      IS_DEBUG_BUILD = bool;
    }
    for (int i = 0; ; i++)
    {
      if (i >= 256)
        return;
      long l1 = i;
      int j = 0;
      label60: if (j < 8)
      {
        if ((0x1 & (int)l1) != 0);
        for (long l2 = -7661587058870466123L; ; l2 = 0L)
        {
          l1 = l2 ^ l1 >> 1;
          j++;
          break label60;
          bool = false;
          break;
        }
      }
      sCrcTable[i] = l1;
    }
  }

  public static void assertTrue(boolean paramBoolean)
  {
    if (!paramBoolean)
      throw new AssertionError();
  }

  public static <T> T checkNotNull(T paramT)
  {
    if (paramT == null)
      throw new NullPointerException();
    return paramT;
  }

  public static void closeSilently(Cursor paramCursor)
  {
    if (paramCursor != null);
    try
    {
      paramCursor.close();
      return;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        Log.w("Utils", "fail to close", localThrowable);
    }
  }

  public static void closeSilently(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (paramParcelFileDescriptor != null);
    try
    {
      paramParcelFileDescriptor.close();
      return;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        Log.w("Utils", "fail to close", localThrowable);
    }
  }

  public static void closeSilently(Closeable paramCloseable)
  {
    if (paramCloseable == null);
    while (true)
    {
      return;
      try
      {
        paramCloseable.close();
      }
      catch (Throwable localThrowable)
      {
        Log.w("Utils", "close fail", localThrowable);
      }
    }
  }

  public static int compare(long paramLong1, long paramLong2)
  {
    int i;
    if (paramLong1 < paramLong2)
      i = -1;
    while (true)
    {
      return i;
      if (paramLong1 == paramLong2)
        i = 0;
      else
        i = 1;
    }
  }

  public static final long crc64Long(String paramString)
  {
    int i = 0;
    if ((paramString == null) || (paramString.length() == 0));
    byte[] arrayOfByte;
    for (long l = 0L; ; l = crc64Long(arrayOfByte))
    {
      return l;
      arrayOfByte = new byte[2 * paramString.length()];
      char[] arrayOfChar = paramString.toCharArray();
      int j = arrayOfChar.length;
      int k = 0;
      while (i < j)
      {
        int m = arrayOfChar[i];
        int n = k + 1;
        arrayOfByte[k] = ((byte)(m & 0xFF));
        k = n + 1;
        arrayOfByte[n] = ((byte)(m >> 8));
        i++;
      }
    }
  }

  public static final long crc64Long(byte[] paramArrayOfByte)
  {
    long l = -1L;
    int i = 0;
    int j = paramArrayOfByte.length;
    while (i < j)
    {
      l = sCrcTable[(0xFF & ((int)l ^ paramArrayOfByte[i]))] ^ l >> 8;
      i++;
    }
    return l;
  }

  public static boolean equals(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2))));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static String escapeXml(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int j = paramString.length();
    if (i < j)
    {
      char c = paramString.charAt(i);
      switch (c)
      {
      default:
        localStringBuilder.append(c);
      case '<':
      case '>':
      case '"':
      case '\'':
      case '&':
      }
      while (true)
      {
        i++;
        break;
        localStringBuilder.append("&lt;");
        continue;
        localStringBuilder.append("&gt;");
        continue;
        localStringBuilder.append("&quot;");
        continue;
        localStringBuilder.append("&#039;");
        continue;
        localStringBuilder.append("&amp;");
      }
    }
    return localStringBuilder.toString();
  }

  public static String getUserAgent(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      Object[] arrayOfObject = new Object[9];
      arrayOfObject[0] = localPackageInfo.packageName;
      arrayOfObject[1] = localPackageInfo.versionName;
      arrayOfObject[2] = Build.BRAND;
      arrayOfObject[3] = Build.DEVICE;
      arrayOfObject[4] = Build.MODEL;
      arrayOfObject[5] = Build.ID;
      arrayOfObject[6] = Build.VERSION.SDK;
      arrayOfObject[7] = Build.VERSION.RELEASE;
      arrayOfObject[8] = Build.VERSION.INCREMENTAL;
      return String.format("%s/%s; %s/%s/%s/%s; %s/%s/%s", arrayOfObject);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    throw new IllegalStateException("getPackageInfo failed");
  }

  public static boolean handleInterrruptedException(Throwable paramThrowable)
  {
    if (((paramThrowable instanceof InterruptedIOException)) || ((paramThrowable instanceof InterruptedException)))
      Thread.currentThread().interrupt();
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static String maskDebugInfo(Object paramObject)
  {
    String str;
    if (paramObject == null)
      str = null;
    while (true)
    {
      return str;
      str = paramObject.toString();
      int i = Math.min(str.length(), 32);
      if (!IS_DEBUG_BUILD)
        str = "********************************".substring(0, i);
    }
  }

  public static int prevPowerOf2(int paramInt)
  {
    if (paramInt <= 0)
      throw new IllegalArgumentException();
    return Integer.highestOneBit(paramInt);
  }

  public static void waitWithoutInterrupt(Object paramObject)
  {
    try
    {
      paramObject.wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        Log.w("Utils", "unexpected interrupt: " + paramObject);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.common.Utils
 * JD-Core Version:    0.6.2
 */