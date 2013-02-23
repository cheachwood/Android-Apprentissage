package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventRespondRequestJson extends EsJson<EventRespondRequest>
{
  static final EventRespondRequestJson INSTANCE = new EventRespondRequestJson();

  private EventRespondRequestJson()
  {
    super(EventRespondRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "deprecated1", "enableTracing", "eventId", EventSelectorJson.class, "eventSelector", "invitationToken", "location", "numAdditionalGuests", "offNetworkDisplayName", "response", "rsvpToken", "squareId" });
  }

  public static EventRespondRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventRespondRequestJson
 * JD-Core Version:    0.6.2
 */