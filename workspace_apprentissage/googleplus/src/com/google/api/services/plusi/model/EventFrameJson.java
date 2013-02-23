package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventFrameJson extends EsJson<EventFrame>
{
  static final EventFrameJson INSTANCE = new EventFrameJson();

  private EventFrameJson()
  {
    super(EventFrame.class, arrayOfObject);
  }

  public static EventFrameJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventFrameJson
 * JD-Core Version:    0.6.2
 */