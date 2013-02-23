package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReportAbuseActivityResponseJson extends EsJson<ReportAbuseActivityResponse>
{
  static final ReportAbuseActivityResponseJson INSTANCE = new ReportAbuseActivityResponseJson();

  private ReportAbuseActivityResponseJson()
  {
    super(ReportAbuseActivityResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "isUndo", "itemIds" });
  }

  public static ReportAbuseActivityResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportAbuseActivityResponseJson
 * JD-Core Version:    0.6.2
 */