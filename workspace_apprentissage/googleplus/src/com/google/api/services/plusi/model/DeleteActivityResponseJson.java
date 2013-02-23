package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteActivityResponseJson extends EsJson<DeleteActivityResponse>
{
  static final DeleteActivityResponseJson INSTANCE = new DeleteActivityResponseJson();

  private DeleteActivityResponseJson()
  {
    super(DeleteActivityResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "itemId" });
  }

  public static DeleteActivityResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteActivityResponseJson
 * JD-Core Version:    0.6.2
 */