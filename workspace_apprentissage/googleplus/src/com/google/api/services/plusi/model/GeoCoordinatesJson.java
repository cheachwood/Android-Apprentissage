package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GeoCoordinatesJson extends EsJson<GeoCoordinates>
{
  static final GeoCoordinatesJson INSTANCE = new GeoCoordinatesJson();

  private GeoCoordinatesJson()
  {
    super(GeoCoordinates.class, new Object[] { "imageUrl", "latitude", "longitude", "name", "url" });
  }

  public static GeoCoordinatesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GeoCoordinatesJson
 * JD-Core Version:    0.6.2
 */