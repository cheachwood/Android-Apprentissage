package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GeoPointJson extends EsJson<GeoPoint>
{
  static final GeoPointJson INSTANCE = new GeoPointJson();

  private GeoPointJson()
  {
    super(GeoPoint.class, arrayOfObject);
  }

  public static GeoPointJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GeoPointJson
 * JD-Core Version:    0.6.2
 */