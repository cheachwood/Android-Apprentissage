package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeIntervalProtoJson extends EsJson<TimeIntervalProto>
{
  static final TimeIntervalProtoJson INSTANCE = new TimeIntervalProtoJson();

  private TimeIntervalProtoJson()
  {
    super(TimeIntervalProto.class, new Object[] { TimeEndpointProtoJson.class, "begin", TimeEndpointProtoJson.class, "end", "inverted", "occasion", "type" });
  }

  public static TimeIntervalProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeIntervalProtoJson
 * JD-Core Version:    0.6.2
 */