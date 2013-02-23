package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateEventResponseJson extends EsJson<UpdateEventResponse>
{
  static final UpdateEventResponseJson INSTANCE = new UpdateEventResponseJson();

  private UpdateEventResponseJson()
  {
    super(UpdateEventResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", PlusEventJson.class, "event" });
  }

  public static UpdateEventResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateEventResponseJson
 * JD-Core Version:    0.6.2
 */