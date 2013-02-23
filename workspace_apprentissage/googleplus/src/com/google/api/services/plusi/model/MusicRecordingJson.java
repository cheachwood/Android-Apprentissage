package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MusicRecordingJson extends EsJson<MusicRecording>
{
  static final MusicRecordingJson INSTANCE = new MusicRecordingJson();

  private MusicRecordingJson()
  {
    super(MusicRecording.class, new Object[] { EmbedClientItemJson.class, "about", AudioObjectJson.class, "audio", "buyLinkImageUrl", MusicGroupJson.class, "byArtist", "description", "imageUrl", MusicAlbumJson.class, "inAlbum", "name", "url" });
  }

  public static MusicRecordingJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MusicRecordingJson
 * JD-Core Version:    0.6.2
 */