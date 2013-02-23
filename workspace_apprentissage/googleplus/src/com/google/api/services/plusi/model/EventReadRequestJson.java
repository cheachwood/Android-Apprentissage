package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventReadRequestJson extends EsJson<EventReadRequest>
{
  static final EventReadRequestJson INSTANCE = new EventReadRequestJson();

  private EventReadRequestJson()
  {
    super(EventReadRequest.class, new Object[] { "authToken", ApiaryFieldsJson.class, "commonFields", "contentFormat", "enableTracing", EventSelectorJson.class, "eventSelector", "invitationToken", "pollingToken", ReadOptionsJson.class, "readOptions", "requestAnonymously", "resumeToken" });
  }

  public static EventReadRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventReadRequestJson
 * JD-Core Version:    0.6.2
 */