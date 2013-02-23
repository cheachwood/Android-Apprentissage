package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosPlusOneResponseJson extends EsJson<PhotosPlusOneResponse>
{
  static final PhotosPlusOneResponseJson INSTANCE = new PhotosPlusOneResponseJson();

  private PhotosPlusOneResponseJson()
  {
    super(PhotosPlusOneResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataPlusOneJson.class, "plusOne", "success" });
  }

  public static PhotosPlusOneResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosPlusOneResponseJson
 * JD-Core Version:    0.6.2
 */