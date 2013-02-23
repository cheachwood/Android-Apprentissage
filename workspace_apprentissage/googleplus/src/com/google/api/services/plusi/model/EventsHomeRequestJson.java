package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventsHomeRequestJson extends EsJson<EventsHomeRequest>
{
  static final EventsHomeRequestJson INSTANCE = new EventsHomeRequestJson();

  private EventsHomeRequestJson()
  {
    super(EventsHomeRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxDeclinedUpcomingEvents", "maxPastEvents", "maxUpcomingEvents", "pastResumeToken" });
  }

  public static EventsHomeRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventsHomeRequestJson
 * JD-Core Version:    0.6.2
 */