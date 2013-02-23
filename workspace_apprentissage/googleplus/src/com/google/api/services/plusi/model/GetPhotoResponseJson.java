package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetPhotoResponseJson extends EsJson<GetPhotoResponse>
{
  static final GetPhotoResponseJson INSTANCE = new GetPhotoResponseJson();

  private GetPhotoResponseJson()
  {
    super(GetPhotoResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "isDownloadable", DataPhotoJson.class, "photo" });
  }

  public static GetPhotoResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetPhotoResponseJson
 * JD-Core Version:    0.6.2
 */