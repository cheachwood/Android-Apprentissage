package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataActivityOnPhotoJson extends EsJson<PhotoDataActivityOnPhoto>
{
  static final PhotoDataActivityOnPhotoJson INSTANCE = new PhotoDataActivityOnPhotoJson();

  private PhotoDataActivityOnPhotoJson()
  {
    super(PhotoDataActivityOnPhoto.class, new Object[] { PhotoDataAlbumJson.class, "album", CommonPersonJson.class, "commenter", CommonPersonJson.class, "ownerperson" });
  }

  public static PhotoDataActivityOnPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataActivityOnPhotoJson
 * JD-Core Version:    0.6.2
 */