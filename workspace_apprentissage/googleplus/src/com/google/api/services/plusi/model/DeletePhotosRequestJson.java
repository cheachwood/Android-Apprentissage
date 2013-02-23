package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeletePhotosRequestJson extends EsJson<DeletePhotosRequest>
{
  static final DeletePhotosRequestJson INSTANCE = new DeletePhotosRequestJson();

  private DeletePhotosRequestJson()
  {
    super(DeletePhotosRequest.class, arrayOfObject);
  }

  public static DeletePhotosRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeletePhotosRequestJson
 * JD-Core Version:    0.6.2
 */