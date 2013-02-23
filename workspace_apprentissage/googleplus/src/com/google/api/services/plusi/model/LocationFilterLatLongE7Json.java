package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationFilterLatLongE7Json extends EsJson<LocationFilterLatLongE7>
{
  static final LocationFilterLatLongE7Json INSTANCE = new LocationFilterLatLongE7Json();

  private LocationFilterLatLongE7Json()
  {
    super(LocationFilterLatLongE7.class, new Object[] { "latitude", "longitude" });
  }

  public static LocationFilterLatLongE7Json getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationFilterLatLongE7Json
 * JD-Core Version:    0.6.2
 */