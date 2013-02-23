package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NearbyTransitProtoJson extends EsJson<NearbyTransitProto>
{
  static final NearbyTransitProtoJson INSTANCE = new NearbyTransitProtoJson();

  private NearbyTransitProtoJson()
  {
    super(NearbyTransitProto.class, new Object[] { "hasMoreStations", "isStation", "startIndex", NearbyTransitProtoStationJson.class, "station" });
  }

  public static NearbyTransitProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyTransitProtoJson
 * JD-Core Version:    0.6.2
 */