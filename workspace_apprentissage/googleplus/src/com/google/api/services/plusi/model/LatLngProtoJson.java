package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LatLngProtoJson extends EsJson<LatLngProto>
{
  static final LatLngProtoJson INSTANCE = new LatLngProtoJson();

  private LatLngProtoJson()
  {
    super(LatLngProto.class, new Object[] { "latitudeE7", "longitudeE7" });
  }

  public static LatLngProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LatLngProtoJson
 * JD-Core Version:    0.6.2
 */