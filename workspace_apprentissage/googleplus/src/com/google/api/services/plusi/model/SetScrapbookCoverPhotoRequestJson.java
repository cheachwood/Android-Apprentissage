package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetScrapbookCoverPhotoRequestJson extends EsJson<SetScrapbookCoverPhotoRequest>
{
  static final SetScrapbookCoverPhotoRequestJson INSTANCE = new SetScrapbookCoverPhotoRequestJson();

  private SetScrapbookCoverPhotoRequestJson()
  {
    super(SetScrapbookCoverPhotoRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "galleryPhoto", "offset", "ownerId", "photoId" });
  }

  public static SetScrapbookCoverPhotoRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetScrapbookCoverPhotoRequestJson
 * JD-Core Version:    0.6.2
 */