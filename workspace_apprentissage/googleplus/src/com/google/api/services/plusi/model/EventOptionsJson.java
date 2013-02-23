package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventOptionsJson extends EsJson<EventOptions>
{
  static final EventOptionsJson INSTANCE = new EventOptionsJson();

  private EventOptionsJson()
  {
    super(EventOptions.class, new Object[] { "broadcast", "hangout", "hideGuestList", "openEventAcl", "openPhotoAcl" });
  }

  public static EventOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventOptionsJson
 * JD-Core Version:    0.6.2
 */