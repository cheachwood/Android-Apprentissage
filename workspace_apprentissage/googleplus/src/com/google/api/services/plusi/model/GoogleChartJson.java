package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GoogleChartJson extends EsJson<GoogleChart>
{
  static final GoogleChartJson INSTANCE = new GoogleChartJson();

  private GoogleChartJson()
  {
    super(GoogleChart.class, new Object[] { "description", "name", "url" });
  }

  public static GoogleChartJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleChartJson
 * JD-Core Version:    0.6.2
 */