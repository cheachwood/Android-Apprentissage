package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReportProfileRequestJson extends EsJson<ReportProfileRequest>
{
  static final ReportProfileRequestJson INSTANCE = new ReportProfileRequestJson();

  private ReportProfileRequestJson()
  {
    super(ReportProfileRequest.class, new Object[] { DataAbuseReportJson.class, "abuseReport", ApiaryFieldsJson.class, "commonFields", "enableTracing", "ownerId" });
  }

  public static ReportProfileRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportProfileRequestJson
 * JD-Core Version:    0.6.2
 */