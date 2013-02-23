package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MediaProtoJson extends EsJson<MediaProto>
{
  static final MediaProtoJson INSTANCE = new MediaProtoJson();

  private MediaProtoJson()
  {
    super(MediaProto.class, new Object[] { "authorName", LatLngProtoJson.class, "latLng", "originalIndex", "reviewId", "sourceId", "sourceName", MediaProtoThumbnailJson.class, "thumbnail", MediaProtoThumbnailJson.class, "thumbnails", "title", "type", UserMediaProtoJson.class, "userMedia" });
  }

  public static MediaProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MediaProtoJson
 * JD-Core Version:    0.6.2
 */