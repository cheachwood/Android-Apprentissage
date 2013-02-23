package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SnapToPlaceResponseJson extends EsJson<SnapToPlaceResponse>
{
  static final SnapToPlaceResponseJson INSTANCE = new SnapToPlaceResponseJson();

  private SnapToPlaceResponseJson()
  {
    super(SnapToPlaceResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", LocationResultJson.class, "cityLocation", LocationResultJson.class, "localPlace", LocationResultJson.class, "preciseLocation", "userIsAtFirstPlace" });
  }

  public static SnapToPlaceResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SnapToPlaceResponseJson
 * JD-Core Version:    0.6.2
 */