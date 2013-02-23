package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class StaticMapProto extends GenericJson
{
  public List<StaticMapProtoImage> additionalMaps;
  public PlacePageLink directionsLink;
  public StaticMapProtoImage image;
  public Long latitudeE6;
  public PlacePageLink link;
  public Long longitudeE6;
  public Long zoomLevel;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StaticMapProto
 * JD-Core Version:    0.6.2
 */