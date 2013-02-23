package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteEventRequestJson extends EsJson<DeleteEventRequest>
{
  static final DeleteEventRequestJson INSTANCE = new DeleteEventRequestJson();

  private DeleteEventRequestJson()
  {
    super(DeleteEventRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "eventId", EventSelectorJson.class, "eventSelector" });
  }

  public static DeleteEventRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteEventRequestJson
 * JD-Core Version:    0.6.2
 */