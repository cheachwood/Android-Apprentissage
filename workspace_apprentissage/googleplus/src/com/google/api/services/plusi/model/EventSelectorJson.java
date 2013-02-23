package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventSelectorJson extends EsJson<EventSelector>
{
  static final EventSelectorJson INSTANCE = new EventSelectorJson();

  private EventSelectorJson()
  {
    super(EventSelector.class, new Object[] { "authKey", "eventId", "ownerId" });
  }

  public static EventSelectorJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventSelectorJson
 * JD-Core Version:    0.6.2
 */