package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetScrapbookCoverPhotoResponseJson extends EsJson<SetScrapbookCoverPhotoResponse>
{
  static final SetScrapbookCoverPhotoResponseJson INSTANCE = new SetScrapbookCoverPhotoResponseJson();

  private SetScrapbookCoverPhotoResponseJson()
  {
    super(SetScrapbookCoverPhotoResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "success" });
  }

  public static SetScrapbookCoverPhotoResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetScrapbookCoverPhotoResponseJson
 * JD-Core Version:    0.6.2
 */