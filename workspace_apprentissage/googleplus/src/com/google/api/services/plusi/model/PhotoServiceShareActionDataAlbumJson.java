package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoServiceShareActionDataAlbumJson extends EsJson<PhotoServiceShareActionDataAlbum>
{
  static final PhotoServiceShareActionDataAlbumJson INSTANCE = new PhotoServiceShareActionDataAlbumJson();

  private PhotoServiceShareActionDataAlbumJson()
  {
    super(PhotoServiceShareActionDataAlbum.class, new Object[] { "albumId", "obfuscatedOwnerId" });
  }

  public static PhotoServiceShareActionDataAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoServiceShareActionDataAlbumJson
 * JD-Core Version:    0.6.2
 */