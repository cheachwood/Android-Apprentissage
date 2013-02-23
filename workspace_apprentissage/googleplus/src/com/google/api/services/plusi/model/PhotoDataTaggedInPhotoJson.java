package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataTaggedInPhotoJson extends EsJson<PhotoDataTaggedInPhoto>
{
  static final PhotoDataTaggedInPhotoJson INSTANCE = new PhotoDataTaggedInPhotoJson();

  private PhotoDataTaggedInPhotoJson()
  {
    super(PhotoDataTaggedInPhoto.class, new Object[] { PhotoDataAlbumJson.class, "album", "isVideo", CommonPersonJson.class, "ownerperson", CommonPersonJson.class, "taggeeperson", CommonPersonJson.class, "taggerperson" });
  }

  public static PhotoDataTaggedInPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataTaggedInPhotoJson
 * JD-Core Version:    0.6.2
 */