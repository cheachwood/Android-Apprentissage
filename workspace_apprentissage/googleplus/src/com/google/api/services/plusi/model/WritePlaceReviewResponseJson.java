package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class WritePlaceReviewResponseJson extends EsJson<WritePlaceReviewResponse>
{
  static final WritePlaceReviewResponseJson INSTANCE = new WritePlaceReviewResponseJson();

  private WritePlaceReviewResponseJson()
  {
    super(WritePlaceReviewResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "zipitVersionInfo" });
  }

  public static WritePlaceReviewResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WritePlaceReviewResponseJson
 * JD-Core Version:    0.6.2
 */