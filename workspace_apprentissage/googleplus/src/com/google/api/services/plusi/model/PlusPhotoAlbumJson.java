package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPhotoAlbumJson extends EsJson<PlusPhotoAlbum>
{
  static final PlusPhotoAlbumJson INSTANCE = new PlusPhotoAlbumJson();

  private PlusPhotoAlbumJson()
  {
    super(PlusPhotoAlbum.class, new Object[] { "albumId", "albumSummaryType", PlusPhotoJson.class, "associatedMedia", "authkey", PlaceJson.class, "contentLocation", "description", "name", "ownerObfuscatedId", "photoCount", "relativeUrl", "url" });
  }

  public static PlusPhotoAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPhotoAlbumJson
 * JD-Core Version:    0.6.2
 */