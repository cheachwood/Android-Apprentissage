package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NearbyStreamRequestJson extends EsJson<NearbyStreamRequest>
{
  static final NearbyStreamRequestJson INSTANCE = new NearbyStreamRequestJson();

  private NearbyStreamRequestJson()
  {
    super(NearbyStreamRequest.class, new Object[] { ActivityFiltersJson.class, "activityFilters", ApiaryFieldsJson.class, "commonFields", "contentFormat", "continuationToken", ClientEmbedOptionsJson.class, "embedOptions", "enableTracing", FieldRequestOptionsJson.class, "fieldRequestOptions", NearbyStreamRequestLatLongE7Json.class, "latLongE7", "maxResults", UpdateFilterJson.class, "updateFilter" });
  }

  public static NearbyStreamRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyStreamRequestJson
 * JD-Core Version:    0.6.2
 */