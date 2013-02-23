package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventPhotoJson extends EsJson<EventPhoto>
{
  static final EventPhotoJson INSTANCE = new EventPhotoJson();

  private EventPhotoJson()
  {
    super(EventPhoto.class, new Object[] { "eventId", "eventOwnerObfuscatedId" });
  }

  public static EventPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventPhotoJson
 * JD-Core Version:    0.6.2
 */