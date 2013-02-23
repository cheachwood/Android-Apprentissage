package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlayMusicAlbumJson extends EsJson<PlayMusicAlbum>
{
  static final PlayMusicAlbumJson INSTANCE = new PlayMusicAlbumJson();

  private PlayMusicAlbumJson()
  {
    super(PlayMusicAlbum.class, new Object[] { AudioObjectJson.class, "audio", "audioUrlWithSessionIndex", MusicGroupJson.class, "byArtist", "description", "explicitType", "imageUrl", "name", "offerUrlWithSessionIndex", OfferJson.class, "offers", "previewToken", ThumbnailJson.class, "proxiedThumbnail", "purchaseStatus", "storeId", "storeUrlWithSessionIndex", "url", "urlWithSessionIndex" });
  }

  public static PlayMusicAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlayMusicAlbumJson
 * JD-Core Version:    0.6.2
 */