package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlaceInfoJson extends EsJson<PlaceInfo>
{
  static final PlaceInfoJson INSTANCE = new PlaceInfoJson();

  private PlaceInfoJson()
  {
    super(PlaceInfo.class, arrayOfObject);
  }

  public static PlaceInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlaceInfoJson
 * JD-Core Version:    0.6.2
 */