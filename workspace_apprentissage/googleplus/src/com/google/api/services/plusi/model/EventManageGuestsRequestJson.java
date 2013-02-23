package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventManageGuestsRequestJson extends EsJson<EventManageGuestsRequest>
{
  static final EventManageGuestsRequestJson INSTANCE = new EventManageGuestsRequestJson();

  private EventManageGuestsRequestJson()
  {
    super(EventManageGuestsRequest.class, new Object[] { "actionType", ApiaryFieldsJson.class, "commonFields", "enableTracing", "eventId", EventSelectorJson.class, "eventSelector", EmbedsPersonJson.class, "invitee", EmbedsSquareJson.class, "square" });
  }

  public static EventManageGuestsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventManageGuestsRequestJson
 * JD-Core Version:    0.6.2
 */