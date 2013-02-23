package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UploadMediaResponseJson extends EsJson<UploadMediaResponse>
{
  static final UploadMediaResponseJson INSTANCE = new UploadMediaResponseJson();

  private UploadMediaResponseJson()
  {
    super(UploadMediaResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataPhotoJson.class, "photo", ScottyInfoJson.class, "scottyInfo", "setProfilePhotoSucceeded" });
  }

  public static UploadMediaResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UploadMediaResponseJson
 * JD-Core Version:    0.6.2
 */