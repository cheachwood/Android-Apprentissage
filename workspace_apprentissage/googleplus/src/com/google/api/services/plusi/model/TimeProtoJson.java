package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeProtoJson extends EsJson<TimeProto>
{
  static final TimeProtoJson INSTANCE = new TimeProtoJson();

  private TimeProtoJson()
  {
    super(TimeProto.class, new Object[] { "timeCount", "timeUnit" });
  }

  public static TimeProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeProtoJson
 * JD-Core Version:    0.6.2
 */