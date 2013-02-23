package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationsJson extends EsJson<Locations>
{
  static final LocationsJson INSTANCE = new LocationsJson();

  private LocationsJson()
  {
    super(Locations.class, new Object[] { GeoLocationJson.class, "currentGeoLocation", "currentLocation", "locationMapUrl", MetadataJson.class, "metadata", GeoLocationJson.class, "otherGeoLocation", "otherLocation" });
  }

  public static LocationsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationsJson
 * JD-Core Version:    0.6.2
 */