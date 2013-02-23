package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocationFeatureIdJson extends EsJson<LocationFeatureId>
{
  static final LocationFeatureIdJson INSTANCE = new LocationFeatureIdJson();

  private LocationFeatureIdJson()
  {
    super(LocationFeatureId.class, new Object[] { "cellId", "fprint" });
  }

  public static LocationFeatureIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocationFeatureIdJson
 * JD-Core Version:    0.6.2
 */