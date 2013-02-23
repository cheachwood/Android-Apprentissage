package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataTaggerOfPhotoJson extends EsJson<PhotoDataTaggerOfPhoto>
{
  static final PhotoDataTaggerOfPhotoJson INSTANCE = new PhotoDataTaggerOfPhotoJson();

  private PhotoDataTaggerOfPhotoJson()
  {
    super(PhotoDataTaggerOfPhoto.class, new Object[] { PhotoDataAlbumJson.class, "album", "isVideo", CommonPersonJson.class, "ownerperson", CommonPersonJson.class, "taggeeperson", CommonPersonJson.class, "taggerperson" });
  }

  public static PhotoDataTaggerOfPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataTaggerOfPhotoJson
 * JD-Core Version:    0.6.2
 */