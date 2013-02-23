package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataPhotoShareByOwnerJson extends EsJson<PhotoDataPhotoShareByOwner>
{
  static final PhotoDataPhotoShareByOwnerJson INSTANCE = new PhotoDataPhotoShareByOwnerJson();

  private PhotoDataPhotoShareByOwnerJson()
  {
    super(PhotoDataPhotoShareByOwner.class, new Object[] { "hasAlbum" });
  }

  public static PhotoDataPhotoShareByOwnerJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataPhotoShareByOwnerJson
 * JD-Core Version:    0.6.2
 */