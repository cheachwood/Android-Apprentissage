package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventTimeJson extends EsJson<EventTime>
{
  static final EventTimeJson INSTANCE = new EventTimeJson();

  private EventTimeJson()
  {
    super(EventTime.class, arrayOfObject);
  }

  public static EventTimeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventTimeJson
 * JD-Core Version:    0.6.2
 */