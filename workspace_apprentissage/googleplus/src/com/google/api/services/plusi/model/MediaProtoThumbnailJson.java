package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MediaProtoThumbnailJson extends EsJson<MediaProtoThumbnail>
{
  static final MediaProtoThumbnailJson INSTANCE = new MediaProtoThumbnailJson();

  private MediaProtoThumbnailJson()
  {
    super(MediaProtoThumbnail.class, new Object[] { "altText", "height", "imageSourceUrl", PlacePageLinkJson.class, "targetLink", "width" });
  }

  public static MediaProtoThumbnailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MediaProtoThumbnailJson
 * JD-Core Version:    0.6.2
 */