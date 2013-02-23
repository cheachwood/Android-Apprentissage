package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class SnapToPlaceRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Boolean includeGaiaIdForDisplay;
  public Integer latitudeE7;
  public Integer longitudeE7;
  public Double precisionMeters;
  public String searchQuery;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SnapToPlaceRequest
 * JD-Core Version:    0.6.2
 */