package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeEndpointProtoJson extends EsJson<TimeEndpointProto>
{
  static final TimeEndpointProtoJson INSTANCE = new TimeEndpointProtoJson();

  private TimeEndpointProtoJson()
  {
    super(TimeEndpointProto.class, new Object[] { "day", "dayType", "hour", "minute", "month", "second", "week", "weekType", "year" });
  }

  public static TimeEndpointProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeEndpointProtoJson
 * JD-Core Version:    0.6.2
 */