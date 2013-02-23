package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeComponentProtoJson extends EsJson<TimeComponentProto>
{
  static final TimeComponentProtoJson INSTANCE = new TimeComponentProtoJson();

  private TimeComponentProtoJson()
  {
    super(TimeComponentProto.class, new Object[] { "componentType", TimeIntervalProtoJson.class, "interval" });
  }

  public static TimeComponentProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeComponentProtoJson
 * JD-Core Version:    0.6.2
 */