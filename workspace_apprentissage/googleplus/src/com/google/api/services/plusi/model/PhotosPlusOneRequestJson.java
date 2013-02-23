package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosPlusOneRequestJson extends EsJson<PhotosPlusOneRequest>
{
  static final PhotosPlusOneRequestJson INSTANCE = new PhotosPlusOneRequestJson();

  private PhotosPlusOneRequestJson()
  {
    super(PhotosPlusOneRequest.class, arrayOfObject);
  }

  public static PhotosPlusOneRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosPlusOneRequestJson
 * JD-Core Version:    0.6.2
 */