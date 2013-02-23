package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataPeopleInAlbumJson extends EsJson<PhotoDataPeopleInAlbum>
{
  static final PhotoDataPeopleInAlbumJson INSTANCE = new PhotoDataPeopleInAlbumJson();

  private PhotoDataPeopleInAlbumJson()
  {
    super(PhotoDataPeopleInAlbum.class, new Object[] { PhotoDataAlbumJson.class, "album", CommonPersonJson.class, "ownerperson", CommonPersonJson.class, "taggeeperson" });
  }

  public static PhotoDataPeopleInAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataPeopleInAlbumJson
 * JD-Core Version:    0.6.2
 */