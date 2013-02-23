package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReportAbusePhotoRequestJson extends EsJson<ReportAbusePhotoRequest>
{
  static final ReportAbusePhotoRequestJson INSTANCE = new ReportAbusePhotoRequestJson();

  private ReportAbusePhotoRequestJson()
  {
    super(ReportAbusePhotoRequest.class, arrayOfObject);
  }

  public static ReportAbusePhotoRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportAbusePhotoRequestJson
 * JD-Core Version:    0.6.2
 */