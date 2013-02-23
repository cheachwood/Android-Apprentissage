package com.google.android.apps.plus.util;

public final class PrimitiveUtils
{
  public static boolean safeBoolean(Boolean paramBoolean)
  {
    if (paramBoolean == null);
    for (boolean bool = false; ; bool = paramBoolean.booleanValue())
      return bool;
  }

  public static double safeDouble(Double paramDouble)
  {
    if (paramDouble == null);
    for (double d = 0.0D; ; d = paramDouble.doubleValue())
      return d;
  }

  public static int safeInt(Integer paramInteger)
  {
    if (paramInteger == null);
    for (int i = 0; ; i = paramInteger.intValue())
      return i;
  }

  public static long safeLong(Long paramLong)
  {
    if (paramLong == null);
    for (long l = 0L; ; l = paramLong.longValue())
      return l;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.PrimitiveUtils
 * JD-Core Version:    0.6.2
 */