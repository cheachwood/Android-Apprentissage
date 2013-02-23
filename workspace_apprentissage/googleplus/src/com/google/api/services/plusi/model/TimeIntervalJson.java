package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeIntervalJson extends EsJson<TimeInterval>
{
  static final TimeIntervalJson INSTANCE = new TimeIntervalJson();

  private TimeIntervalJson()
  {
    super(TimeInterval.class, new Object[] { TimeEndpointJson.class, "begin", "current", TimeEndpointJson.class, "end" });
  }

  public static TimeIntervalJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeIntervalJson
 * JD-Core Version:    0.6.2
 */