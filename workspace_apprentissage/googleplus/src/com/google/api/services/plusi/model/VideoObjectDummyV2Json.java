package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VideoObjectDummyV2Json extends EsJson<VideoObjectDummyV2>
{
  static final VideoObjectDummyV2Json INSTANCE = new VideoObjectDummyV2Json();

  private VideoObjectDummyV2Json()
  {
    super(VideoObjectDummyV2.class, new Object[] { EmbedClientItemJson.class, "about", EmbedsPersonJson.class, "author", "caption", PlaceJson.class, "contentLocation", "contentUrl", "description", "descriptionTruncated", "duration", "embedUrl", "height", "heightPx", "imageUrl", "isFamilyFriendly", "name", "paid", "playerType", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "unlisted", "url", "width", "widthPx" });
  }

  public static VideoObjectDummyV2Json getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VideoObjectDummyV2Json
 * JD-Core Version:    0.6.2
 */