package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataViewerCirclesJson extends EsJson<DataViewerCircles>
{
  static final DataViewerCirclesJson INSTANCE = new DataViewerCirclesJson();

  private DataViewerCirclesJson()
  {
    super(DataViewerCircles.class, new Object[] { DataCircleDataJson.class, "circle" });
  }

  public static DataViewerCirclesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataViewerCirclesJson
 * JD-Core Version:    0.6.2
 */