package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventActivityDataJson extends EsJson<EventActivityData>
{
  static final EventActivityDataJson INSTANCE = new EventActivityDataJson();

  private EventActivityDataJson()
  {
    super(EventActivityData.class, new Object[] { "broadcastEventActivity", "recipientRsvpType" });
  }

  public static EventActivityDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventActivityDataJson
 * JD-Core Version:    0.6.2
 */