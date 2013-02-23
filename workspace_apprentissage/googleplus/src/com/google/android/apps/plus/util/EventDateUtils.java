package com.google.android.apps.plus.util;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.apps.plus.R.string;
import com.google.api.services.plusi.model.EventTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class EventDateUtils
{
  private static String sAbsoluteDateFormat;
  private static String sDateTimeZoneFormat;
  private static String sEndDateFormat;
  private static java.text.DateFormat sLongDateFormatter;
  private static java.text.DateFormat sMediumDateFormatter;
  private static String sRelativeBeginDateFormat;
  private static String sRelativeEndDateFormat;
  private static String sStartDateFormat;
  private static java.text.DateFormat sTimeFormatter;
  private static String sToday;
  private static long sTodayMsec;
  private static String sTomorrow;
  private static long sTomorrowMsec;
  private static String sYesterday;
  private static long sYesterdayMsec;

  private static String format(java.text.DateFormat paramDateFormat, Date paramDate, TimeZone paramTimeZone)
  {
    TimeZone localTimeZone = paramDateFormat.getTimeZone();
    if (paramTimeZone != null)
      paramDateFormat.setTimeZone(paramTimeZone);
    String str = paramDateFormat.format(paramDate);
    paramDateFormat.setTimeZone(localTimeZone);
    return str;
  }

  public static String getDateRange(Context paramContext, EventTime paramEventTime1, EventTime paramEventTime2, boolean paramBoolean)
  {
    initializeStrings(paramContext);
    long l = paramEventTime1.timeMs.longValue();
    return String.format("%s %s", new Object[] { getSingleDateDisplayLine(paramContext, l), getDisplayTime(paramContext, l) });
  }

  private static String getDisplayTime(Context paramContext, long paramLong)
  {
    initializeFormats(paramContext);
    synchronized (sTimeFormatter)
    {
      String str = sTimeFormatter.format(Long.valueOf(paramLong));
      return str;
    }
  }

  public static String getDisplayTime(Context paramContext, long paramLong, TimeZone paramTimeZone)
  {
    initializeFormats(paramContext);
    synchronized (sTimeFormatter)
    {
      TimeZone localTimeZone = sTimeFormatter.getTimeZone();
      sTimeFormatter.setTimeZone(paramTimeZone);
      String str = sTimeFormatter.format(Long.valueOf(paramLong));
      sTimeFormatter.setTimeZone(localTimeZone);
      return str;
    }
  }

  private static String getSingleDateDisplayLine(Context paramContext, long paramLong)
  {
    initializeFormats(paramContext);
    Date localDate = new Date(paramLong);
    synchronized (sLongDateFormatter)
    {
      String str = sLongDateFormatter.format(localDate);
      return str;
    }
  }

  public static String getSingleDateDisplayLine(Context paramContext, long paramLong, TimeZone paramTimeZone)
  {
    initializeFormats(paramContext);
    Date localDate = new Date(paramLong);
    synchronized (sLongDateFormatter)
    {
      TimeZone localTimeZone = sLongDateFormatter.getTimeZone();
      sLongDateFormatter.setTimeZone(paramTimeZone);
      String str = sLongDateFormatter.format(localDate);
      sLongDateFormatter.setTimeZone(localTimeZone);
      return str;
    }
  }

  public static String getSingleDisplayLine(Context paramContext, EventTime paramEventTime, String paramString, boolean paramBoolean, TimeZone paramTimeZone)
  {
    long l = paramEventTime.timeMs.longValue();
    Date localDate = new Date(l);
    initializeFormats(paramContext);
    initializeStrings(paramContext);
    String str1;
    if ((l > sTodayMsec) && (l < 86400000L + sTodayMsec))
    {
      str1 = sToday;
      if ((paramTimeZone == null) && (paramEventTime != null) && (!TextUtils.isEmpty(paramEventTime.timezone)))
      {
        paramTimeZone = TimeZoneHelper.getSystemTimeZone(paramEventTime.timezone);
        if (!TimeZoneHelper.areTimeZoneIdsEquivalent(paramEventTime.timezone, paramTimeZone.getID()))
          paramTimeZone = null;
      }
      if (str1 == null)
        break label263;
      if (!paramBoolean)
        break label255;
    }
    String str5;
    label255: for (String str6 = sRelativeEndDateFormat; ; str6 = sRelativeBeginDateFormat)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = str1;
      arrayOfObject2[1] = format(sTimeFormatter, localDate, paramTimeZone);
      str5 = String.format(str6, arrayOfObject2);
      if (!TextUtils.isEmpty(null))
        str5 = String.format(sDateTimeZoneFormat, new Object[] { str5, null });
      return str5;
      if ((l > sTomorrowMsec) && (l < 86400000L + sTomorrowMsec))
      {
        str1 = sTomorrow;
        break;
      }
      boolean bool1 = l < sYesterdayMsec;
      str1 = null;
      if (!bool1)
        break;
      boolean bool2 = l < 86400000L + sYesterdayMsec;
      str1 = null;
      if (!bool2)
        break;
      str1 = sYesterday;
      break;
    }
    label263: String str2 = sAbsoluteDateFormat;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = format(sMediumDateFormatter, localDate, paramTimeZone);
    arrayOfObject1[1] = format(sTimeFormatter, localDate, paramTimeZone);
    String str3 = String.format(str2, arrayOfObject1);
    if (paramBoolean);
    for (String str4 = sEndDateFormat; ; str4 = sStartDateFormat)
    {
      str5 = String.format(str4, new Object[] { str3 });
      break;
    }
  }

  private static void initializeFormats(Context paramContext)
  {
    if (sMediumDateFormatter == null)
    {
      sMediumDateFormatter = android.text.format.DateFormat.getMediumDateFormat(paramContext);
      sLongDateFormatter = android.text.format.DateFormat.getLongDateFormat(paramContext);
      sTimeFormatter = android.text.format.DateFormat.getTimeFormat(paramContext);
    }
  }

  private static void initializeStrings(Context paramContext)
  {
    if (sToday == null)
    {
      sToday = paramContext.getString(R.string.today);
      sTomorrow = paramContext.getString(R.string.tomorrow);
      sYesterday = paramContext.getString(R.string.yesterday);
      sRelativeBeginDateFormat = paramContext.getString(R.string.event_relative_start_date_format);
      sRelativeEndDateFormat = paramContext.getString(R.string.event_relative_end_date_format);
      sAbsoluteDateFormat = paramContext.getString(R.string.event_absolute_date_format);
      sEndDateFormat = paramContext.getString(R.string.event_end_date_format);
      sStartDateFormat = paramContext.getString(R.string.event_start_date_format);
      sDateTimeZoneFormat = paramContext.getString(R.string.event_card_start_time);
    }
    Calendar localCalendar = Calendar.getInstance();
    if (localCalendar.getTimeInMillis() > sTomorrowMsec)
    {
      localCalendar.set(11, 0);
      localCalendar.set(12, 0);
      localCalendar.set(13, 0);
      localCalendar.set(14, 0);
      long l = localCalendar.getTimeInMillis();
      sTodayMsec = l;
      sTomorrowMsec = l + 86400000L;
      sYesterdayMsec = sTodayMsec - 86400000L;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.EventDateUtils
 * JD-Core Version:    0.6.2
 */