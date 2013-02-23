package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AudioObjectJson extends EsJson<AudioObject>
{
  static final AudioObjectJson INSTANCE = new AudioObjectJson();

  private AudioObjectJson()
  {
    super(AudioObject.class, new Object[] { EmbedClientItemJson.class, "about", "embedUrl", "name", "url" });
  }

  public static AudioObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AudioObjectJson
 * JD-Core Version:    0.6.2
 */