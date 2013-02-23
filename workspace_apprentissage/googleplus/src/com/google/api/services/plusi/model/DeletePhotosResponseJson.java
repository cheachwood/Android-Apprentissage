package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeletePhotosResponseJson extends EsJson<DeletePhotosResponse>
{
  static final DeletePhotosResponseJson INSTANCE = new DeletePhotosResponseJson();

  private DeletePhotosResponseJson()
  {
    super(DeletePhotosResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "kansasVersionInfo", "success" });
  }

  public static DeletePhotosResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeletePhotosResponseJson
 * JD-Core Version:    0.6.2
 */