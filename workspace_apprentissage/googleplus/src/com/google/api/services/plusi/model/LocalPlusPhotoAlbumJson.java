package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalPlusPhotoAlbumJson extends EsJson<LocalPlusPhotoAlbum>
{
  static final LocalPlusPhotoAlbumJson INSTANCE = new LocalPlusPhotoAlbumJson();

  private LocalPlusPhotoAlbumJson()
  {
    super(LocalPlusPhotoAlbum.class, new Object[] { "albumId", PlusPhotoJson.class, "associatedMedia", PlaceJson.class, "contentLocation", "description", "name", "ownerObfuscatedId", "photoCount", "url" });
  }

  public static LocalPlusPhotoAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalPlusPhotoAlbumJson
 * JD-Core Version:    0.6.2
 */