package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReportProfileResponseJson extends EsJson<ReportProfileResponse>
{
  static final ReportProfileResponseJson INSTANCE = new ReportProfileResponseJson();

  private ReportProfileResponseJson()
  {
    super(ReportProfileResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "profileId" });
  }

  public static ReportProfileResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportProfileResponseJson
 * JD-Core Version:    0.6.2
 */