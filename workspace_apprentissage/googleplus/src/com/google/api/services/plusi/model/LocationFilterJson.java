package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationFilterJson extends EsJson<LocationFilter>
{
  static final LocationFilterJson INSTANCE = new LocationFilterJson();

  private LocationFilterJson()
  {
    super(LocationFilter.class, new Object[] { LocationFilterLatLongE7Json.class, "latLongE7", "location", "searchRadiusMeters" });
  }

  public static LocationFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationFilterJson
 * JD-Core Version:    0.6.2
 */