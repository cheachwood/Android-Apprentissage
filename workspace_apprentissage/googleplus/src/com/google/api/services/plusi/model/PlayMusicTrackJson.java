package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlayMusicTrackJson extends EsJson<PlayMusicTrack>
{
  static final PlayMusicTrackJson INSTANCE = new PlayMusicTrackJson();

  private PlayMusicTrackJson()
  {
    super(PlayMusicTrack.class, new Object[] { AudioObjectJson.class, "audio", "audioEmbedUrlWithSessionIndex", "audioUrlWithSessionIndex", MusicGroupJson.class, "byArtist", "description", "explicitType", "imageUrl", PlayMusicAlbumJson.class, "inAlbum", "name", "offerUrlWithSessionIndex", OfferJson.class, "offers", "previewToken", "purchaseStatus", "storeId", "url", "urlWithSessionIndex" });
  }

  public static PlayMusicTrackJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlayMusicTrackJson
 * JD-Core Version:    0.6.2
 */