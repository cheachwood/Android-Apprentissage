package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeScheduleProtoJson extends EsJson<TimeScheduleProto>
{
  static final TimeScheduleProtoJson INSTANCE = new TimeScheduleProtoJson();

  private TimeScheduleProtoJson()
  {
    super(TimeScheduleProto.class, new Object[] { TimeComponentProtoJson.class, "component" });
  }

  public static TimeScheduleProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeScheduleProtoJson
 * JD-Core Version:    0.6.2
 */