package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventRespondResponseJson extends EsJson<EventRespondResponse>
{
  static final EventRespondResponseJson INSTANCE = new EventRespondResponseJson();

  private EventRespondResponseJson()
  {
    super(EventRespondResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "result", "success" });
  }

  public static EventRespondResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventRespondResponseJson
 * JD-Core Version:    0.6.2
 */