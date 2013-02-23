package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusEventAlbumJson extends EsJson<PlusEventAlbum>
{
  static final PlusEventAlbumJson INSTANCE = new PlusEventAlbumJson();

  private PlusEventAlbumJson()
  {
    super(PlusEventAlbum.class, new Object[] { "albumId", "unnamedShapeCount" });
  }

  public static PlusEventAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusEventAlbumJson
 * JD-Core Version:    0.6.2
 */