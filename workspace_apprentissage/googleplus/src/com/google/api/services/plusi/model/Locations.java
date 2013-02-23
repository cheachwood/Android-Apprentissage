package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Locations extends GenericJson
{
  public GeoLocation currentGeoLocation;
  public String currentLocation;
  public String locationMapUrl;
  public Metadata metadata;
  public List<GeoLocation> otherGeoLocation;
  public List<String> otherLocation;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Locations
 * JD-Core Version:    0.6.2
 */