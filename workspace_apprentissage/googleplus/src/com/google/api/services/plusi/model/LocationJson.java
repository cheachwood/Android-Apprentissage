package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationJson extends EsJson<Location>
{
  static final LocationJson INSTANCE = new LocationJson();

  private LocationJson()
  {
    super(Location.class, new Object[] { "bestAddress", "clusterId", LocationFeatureIdJson.class, "featureId", "isAddressOnly", "language", "latitude", "latitudeE7", "locationTag", "longitude", "longitudeE7", "mapThumbUrl", "mapUrl", "mapsPageUrl", "placePageUrl", "plusPageObfuscatedId", "precisionMeters", "seenIncludeLocation" });
  }

  public static LocationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationJson
 * JD-Core Version:    0.6.2
 */