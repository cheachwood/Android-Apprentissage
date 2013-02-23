package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class WeekdayTimeIntervalsJson extends EsJson<WeekdayTimeIntervals>
{
  static final WeekdayTimeIntervalsJson INSTANCE = new WeekdayTimeIntervalsJson();

  private WeekdayTimeIntervalsJson()
  {
    super(WeekdayTimeIntervals.class, new Object[] { "current", "dayOfWeek", TimeIntervalJson.class, "intervals" });
  }

  public static WeekdayTimeIntervalsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WeekdayTimeIntervalsJson
 * JD-Core Version:    0.6.2
 */