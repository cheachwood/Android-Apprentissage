package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleIdsJson extends EsJson<DataCircleIds>
{
  static final DataCircleIdsJson INSTANCE = new DataCircleIdsJson();

  private DataCircleIdsJson()
  {
    super(DataCircleIds.class, new Object[] { DataCircleIdJson.class, "circleId" });
  }

  public static DataCircleIdsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleIdsJson
 * JD-Core Version:    0.6.2
 */