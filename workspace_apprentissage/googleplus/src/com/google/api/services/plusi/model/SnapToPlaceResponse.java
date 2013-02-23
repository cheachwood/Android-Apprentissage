package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SnapToPlaceResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public LocationResult cityLocation;
  public List<LocationResult> localPlace;
  public LocationResult preciseLocation;
  public Boolean userIsAtFirstPlace;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SnapToPlaceResponse
 * JD-Core Version:    0.6.2
 */