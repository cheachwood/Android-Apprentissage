package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MusicAlbumJson extends EsJson<MusicAlbum>
{
  static final MusicAlbumJson INSTANCE = new MusicAlbumJson();

  private MusicAlbumJson()
  {
    super(MusicAlbum.class, new Object[] { EmbedClientItemJson.class, "about", "imageUrl", "name", "url" });
  }

  public static MusicAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MusicAlbumJson
 * JD-Core Version:    0.6.2
 */