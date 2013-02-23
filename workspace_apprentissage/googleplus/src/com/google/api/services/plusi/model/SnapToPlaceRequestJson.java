package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SnapToPlaceRequestJson extends EsJson<SnapToPlaceRequest>
{
  static final SnapToPlaceRequestJson INSTANCE = new SnapToPlaceRequestJson();

  private SnapToPlaceRequestJson()
  {
    super(SnapToPlaceRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "includeGaiaIdForDisplay", "latitudeE7", "longitudeE7", "precisionMeters", "searchQuery" });
  }

  public static SnapToPlaceRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SnapToPlaceRequestJson
 * JD-Core Version:    0.6.2
 */