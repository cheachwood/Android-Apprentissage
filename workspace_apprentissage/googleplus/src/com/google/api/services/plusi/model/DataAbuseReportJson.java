package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataAbuseReportJson extends EsJson<DataAbuseReport>
{
  static final DataAbuseReportJson INSTANCE = new DataAbuseReportJson();

  private DataAbuseReportJson()
  {
    super(DataAbuseReport.class, new Object[] { "abuseSubtype", "abuseType", "comment", "destinationStreamId" });
  }

  public static DataAbuseReportJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataAbuseReportJson
 * JD-Core Version:    0.6.2
 */