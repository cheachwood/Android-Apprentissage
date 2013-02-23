package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class NearbyTransitProto extends GenericJson
{
  public Boolean hasMoreStations;
  public Boolean isStation;
  public Integer startIndex;
  public List<NearbyTransitProtoStation> station;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyTransitProto
 * JD-Core Version:    0.6.2
 */