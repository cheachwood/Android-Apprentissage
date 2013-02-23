package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataActivityOnAlbumJson extends EsJson<PhotoDataActivityOnAlbum>
{
  static final PhotoDataActivityOnAlbumJson INSTANCE = new PhotoDataActivityOnAlbumJson();

  private PhotoDataActivityOnAlbumJson()
  {
    super(PhotoDataActivityOnAlbum.class, new Object[] { PhotoDataAlbumJson.class, "album", CommonPersonJson.class, "ownerperson" });
  }

  public static PhotoDataActivityOnAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataActivityOnAlbumJson
 * JD-Core Version:    0.6.2
 */