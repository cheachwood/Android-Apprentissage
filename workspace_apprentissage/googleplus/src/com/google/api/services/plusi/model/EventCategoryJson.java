package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventCategoryJson extends EsJson<EventCategory>
{
  static final EventCategoryJson INSTANCE = new EventCategoryJson();

  private EventCategoryJson()
  {
    super(EventCategory.class, new Object[] { "category" });
  }

  public static EventCategoryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventCategoryJson
 * JD-Core Version:    0.6.2
 */