package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MediaLayoutJson extends EsJson<MediaLayout>
{
  static final MediaLayoutJson INSTANCE = new MediaLayoutJson();

  private MediaLayoutJson()
  {
    super(MediaLayout.class, new Object[] { "contentUrl", "description", "faviconUrl", "layoutType", MediaItemJson.class, "media", "source", "title", "totalMediaCount", "uploader" });
  }

  public static MediaLayoutJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MediaLayoutJson
 * JD-Core Version:    0.6.2
 */