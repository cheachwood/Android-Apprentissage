package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class NearbyStreamRequest extends GenericJson
{
  public ActivityFilters activityFilters;
  public ApiaryFields commonFields;
  public String contentFormat;
  public String continuationToken;
  public ClientEmbedOptions embedOptions;
  public Boolean enableTracing;
  public FieldRequestOptions fieldRequestOptions;
  public NearbyStreamRequestLatLongE7 latLongE7;
  public Integer maxResults;
  public UpdateFilter updateFilter;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyStreamRequest
 * JD-Core Version:    0.6.2
 */