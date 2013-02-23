package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlaceJson extends EsJson<Place>
{
  static final PlaceJson INSTANCE = new PlaceJson();

  private PlaceJson()
  {
    super(Place.class, new Object[] { EmbedsPostalAddressJson.class, "address", "clusterId", "description", GeoCoordinatesJson.class, "geo", "imageUrl", "mapUrl", "name", "ownerObfuscatedId", "referenceId", "url" });
  }

  public static PlaceJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlaceJson
 * JD-Core Version:    0.6.2
 */