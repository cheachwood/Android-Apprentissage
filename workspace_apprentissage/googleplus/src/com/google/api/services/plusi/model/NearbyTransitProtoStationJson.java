package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NearbyTransitProtoStationJson extends EsJson<NearbyTransitProtoStation>
{
  static final NearbyTransitProtoStationJson INSTANCE = new NearbyTransitProtoStationJson();

  private NearbyTransitProtoStationJson()
  {
    super(NearbyTransitProtoStation.class, arrayOfObject);
  }

  public static NearbyTransitProtoStationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyTransitProtoStationJson
 * JD-Core Version:    0.6.2
 */