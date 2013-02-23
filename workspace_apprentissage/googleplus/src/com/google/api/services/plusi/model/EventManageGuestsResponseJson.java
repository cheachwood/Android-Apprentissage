package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventManageGuestsResponseJson extends EsJson<EventManageGuestsResponse>
{
  static final EventManageGuestsResponseJson INSTANCE = new EventManageGuestsResponseJson();

  private EventManageGuestsResponseJson()
  {
    super(EventManageGuestsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "success" });
  }

  public static EventManageGuestsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventManageGuestsResponseJson
 * JD-Core Version:    0.6.2
 */