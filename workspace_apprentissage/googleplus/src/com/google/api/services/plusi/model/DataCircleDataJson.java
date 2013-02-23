package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleDataJson extends EsJson<DataCircleData>
{
  static final DataCircleDataJson INSTANCE = new DataCircleDataJson();

  private DataCircleDataJson()
  {
    super(DataCircleData.class, new Object[] { DataCircleIdJson.class, "circleId", DataCirclePropertiesJson.class, "circleProperties", DataContinuationTokenJson.class, "continuationToken", "deleted" });
  }

  public static DataCircleDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleDataJson
 * JD-Core Version:    0.6.2
 */