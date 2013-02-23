package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventsHomeResponseJson extends EsJson<EventsHomeResponse>
{
  static final EventsHomeResponseJson INSTANCE = new EventsHomeResponseJson();

  private EventsHomeResponseJson()
  {
    super(EventsHomeResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", PlusEventJson.class, "declinedUpcoming", PlusEventJson.class, "deprecated1", PlusEventJson.class, "past", "pastResumeToken", EmbedsPersonJson.class, "resolvedPerson", PlusEventJson.class, "upcoming" });
  }

  public static EventsHomeResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventsHomeResponseJson
 * JD-Core Version:    0.6.2
 */