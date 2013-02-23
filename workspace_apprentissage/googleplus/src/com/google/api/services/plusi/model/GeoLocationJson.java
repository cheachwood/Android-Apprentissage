package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GeoLocationJson extends EsJson<GeoLocation>
{
  static final GeoLocationJson INSTANCE = new GeoLocationJson();

  private GeoLocationJson()
  {
    super(GeoLocation.class, new Object[] { GeoPointJson.class, "geoPoint", "name" });
  }

  public static GeoLocationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GeoLocationJson
 * JD-Core Version:    0.6.2
 */