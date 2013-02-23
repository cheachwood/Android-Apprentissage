package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteEventResponseJson extends EsJson<DeleteEventResponse>
{
  static final DeleteEventResponseJson INSTANCE = new DeleteEventResponseJson();

  private DeleteEventResponseJson()
  {
    super(DeleteEventResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "eventId" });
  }

  public static DeleteEventResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteEventResponseJson
 * JD-Core Version:    0.6.2
 */