package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventTargetIdJson extends EsJson<EventTargetId>
{
  static final EventTargetIdJson INSTANCE = new EventTargetIdJson();

  private EventTargetIdJson()
  {
    super(EventTargetId.class, new Object[] { "id" });
  }

  public static EventTargetIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventTargetIdJson
 * JD-Core Version:    0.6.2
 */