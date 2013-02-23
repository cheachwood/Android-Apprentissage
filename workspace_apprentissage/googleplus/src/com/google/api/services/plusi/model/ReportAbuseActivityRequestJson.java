package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReportAbuseActivityRequestJson extends EsJson<ReportAbuseActivityRequest>
{
  static final ReportAbuseActivityRequestJson INSTANCE = new ReportAbuseActivityRequestJson();

  private ReportAbuseActivityRequestJson()
  {
    super(ReportAbuseActivityRequest.class, new Object[] { DataAbuseReportJson.class, "abuseReport", ApiaryFieldsJson.class, "commonFields", "enableTracing", "isUndo", "itemId" });
  }

  public static ReportAbuseActivityRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportAbuseActivityRequestJson
 * JD-Core Version:    0.6.2
 */