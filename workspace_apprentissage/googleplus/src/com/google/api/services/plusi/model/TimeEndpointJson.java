package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TimeEndpointJson extends EsJson<TimeEndpoint>
{
  static final TimeEndpointJson INSTANCE = new TimeEndpointJson();

  private TimeEndpointJson()
  {
    super(TimeEndpoint.class, new Object[] { "hour", "localizedstring", "minute" });
  }

  public static TimeEndpointJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TimeEndpointJson
 * JD-Core Version:    0.6.2
 */