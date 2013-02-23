package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationResultJson extends EsJson<LocationResult>
{
  static final LocationResultJson INSTANCE = new LocationResultJson();

  private LocationResultJson()
  {
    super(LocationResult.class, new Object[] { LocationJson.class, "location" });
  }

  public static LocationResultJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationResultJson
 * JD-Core Version:    0.6.2
 */