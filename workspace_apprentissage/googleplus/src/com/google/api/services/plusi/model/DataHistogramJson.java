package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataHistogramJson extends EsJson<DataHistogram>
{
  static final DataHistogramJson INSTANCE = new DataHistogramJson();

  private DataHistogramJson()
  {
    super(DataHistogram.class, new Object[] { "blueData", "greenData", "redData" });
  }

  public static DataHistogramJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataHistogramJson
 * JD-Core Version:    0.6.2
 */