package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PicasaAlbumJson extends EsJson<PicasaAlbum>
{
  static final PicasaAlbumJson INSTANCE = new PicasaAlbumJson();

  private PicasaAlbumJson()
  {
    super(PicasaAlbum.class, new Object[] { "albumId", "ownerId" });
  }

  public static PicasaAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PicasaAlbumJson
 * JD-Core Version:    0.6.2
 */