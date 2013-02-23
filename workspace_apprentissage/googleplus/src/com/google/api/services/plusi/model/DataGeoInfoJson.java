package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataGeoInfoJson extends EsJson<DataGeoInfo>
{
  static final DataGeoInfoJson INSTANCE = new DataGeoInfoJson();

  private DataGeoInfoJson()
  {
    super(DataGeoInfo.class, new Object[] { "altitude", "fromExif", "latitude", "latitudeSpan", "longitude", "longitudeSpan" });
  }

  public static DataGeoInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataGeoInfoJson
 * JD-Core Version:    0.6.2
 */