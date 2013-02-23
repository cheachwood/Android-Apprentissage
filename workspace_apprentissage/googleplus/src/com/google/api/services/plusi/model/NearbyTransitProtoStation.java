package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class NearbyTransitProtoStation extends GenericJson
{
  public String distance;
  public Long latE6;
  public List<TransitStationScheduleLineGroup> lineGroup;
  public PlacePageLink link;
  public Long lngE6;
  public String markerId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyTransitProtoStation
 * JD-Core Version:    0.6.2
 */