package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateEventRequestJson extends EsJson<UpdateEventRequest>
{
  static final UpdateEventRequestJson INSTANCE = new UpdateEventRequestJson();

  private UpdateEventRequestJson()
  {
    super(UpdateEventRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", PlusEventJson.class, "event" });
  }

  public static UpdateEventRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateEventRequestJson
 * JD-Core Version:    0.6.2
 */