package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReportAbusePhotoResponseJson extends EsJson<ReportAbusePhotoResponse>
{
  static final ReportAbusePhotoResponseJson INSTANCE = new ReportAbusePhotoResponseJson();

  private ReportAbusePhotoResponseJson()
  {
    super(ReportAbusePhotoResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace" });
  }

  public static ReportAbusePhotoResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportAbusePhotoResponseJson
 * JD-Core Version:    0.6.2
 */