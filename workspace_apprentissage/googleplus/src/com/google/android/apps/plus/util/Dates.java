package com.google.android.apps.plus.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;

public final class Dates
{
  private static Time sThenTime;

  public static CharSequence getAbbreviatedRelativeTimeSpanString(Context paramContext, long paramLong)
  {
    long l = System.currentTimeMillis();
    Object localObject;
    if (l - paramLong < 60000L)
      localObject = paramContext.getResources().getText(R.string.posted_just_now);
    while (true)
    {
      return localObject;
      try
      {
        String str = DateUtils.getRelativeTimeSpanString(paramLong, l, 60000L, 327680).toString();
        localObject = str;
      }
      catch (Exception localException)
      {
        if (EsLog.isLoggable("Dates", 3))
          Log.d("Dates", "DateUtils.getRelativeTimeSpanString threw an exception! time=" + paramLong + ", now=" + l + "\n" + localException.getMessage());
        localObject = getShortRelativeTimeSpanString(paramContext, paramLong);
      }
    }
  }

  private static long getNumberOfDaysPassed(long paramLong1, long paramLong2)
  {
    try
    {
      if (sThenTime == null)
        sThenTime = new Time();
      sThenTime.set(paramLong1);
      int i = Time.getJulianDay(paramLong1, sThenTime.gmtoff);
      sThenTime.set(paramLong2);
      int j = Math.abs(Time.getJulianDay(paramLong2, sThenTime.gmtoff) - i);
      long l = j;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static CharSequence getRelativeTimeSpanString(Context paramContext, long paramLong)
  {
    long l = System.currentTimeMillis();
    Object localObject;
    if (l - paramLong < 60000L)
      localObject = paramContext.getResources().getText(R.string.posted_just_now);
    while (true)
    {
      return localObject;
      try
      {
        String str = DateUtils.getRelativeTimeSpanString(paramLong, l, 60000L, 262144).toString();
        localObject = str;
      }
      catch (Exception localException)
      {
        if (EsLog.isLoggable("Dates", 3))
          Log.d("Dates", "DateUtils.getRelativeTimeSpanString threw an exception! time=" + paramLong + ", now=" + l + "\n" + localException.getMessage());
        localObject = getShortRelativeTimeSpanString(paramContext, paramLong);
      }
    }
  }

  public static CharSequence getShortRelativeTimeSpanString(Context paramContext, long paramLong)
  {
    long l1 = System.currentTimeMillis();
    Object localObject;
    if (l1 - paramLong < 60000L)
      localObject = paramContext.getResources().getText(R.string.posted_just_now);
    while (true)
    {
      return localObject;
      long l2 = Math.abs(l1 - paramLong);
      long l3;
      int i;
      if (l2 < 3600000L)
      {
        l3 = l2 / 60000L;
        i = R.plurals.num_minutes_ago;
      }
      while (true)
      {
        String str = paramContext.getResources().getQuantityString(i, (int)l3);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(l3);
        localObject = String.format(str, arrayOfObject);
        break;
        if (l2 < 86400000L)
        {
          l3 = l2 / 3600000L;
          i = R.plurals.num_hours_ago;
        }
        else
        {
          if (l2 >= 604800000L)
            break label149;
          l3 = getNumberOfDaysPassed(paramLong, l1);
          i = R.plurals.num_days_ago;
        }
      }
      label149: localObject = DateUtils.formatDateRange(paramContext, paramLong, paramLong, 262144);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.Dates
 * JD-Core Version:    0.6.2
 */