package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteCircleResponseJson extends EsJson<DeleteCircleResponse>
{
  static final DeleteCircleResponseJson INSTANCE = new DeleteCircleResponseJson();

  private DeleteCircleResponseJson()
  {
    super(DeleteCircleResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataRevertCookieJson.class, "revertCookie" });
  }

  public static DeleteCircleResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteCircleResponseJson
 * JD-Core Version:    0.6.2
 */