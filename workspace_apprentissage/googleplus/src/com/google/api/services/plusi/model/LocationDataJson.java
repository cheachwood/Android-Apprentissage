package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationDataJson extends EsJson<LocationData>
{
  static final LocationDataJson INSTANCE = new LocationDataJson();

  private LocationDataJson()
  {
    super(LocationData.class, new Object[] { "displayAddress", ViewportDataJson.class, "viewport" });
  }

  public static LocationDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationDataJson
 * JD-Core Version:    0.6.2
 */