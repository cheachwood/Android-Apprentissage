package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VideoObjectJson extends EsJson<VideoObject>
{
  static final VideoObjectJson INSTANCE = new VideoObjectJson();

  private VideoObjectJson()
  {
    super(VideoObject.class, new Object[] { EmbedClientItemJson.class, "about", EmbedsPersonJson.class, "author", "canonicalFountainStream", "caption", PlaceJson.class, "contentLocation", "contentUrl", "description", "descriptionTruncated", "duration", "embedUrl", "height", "heightPx", "imageUrl", "inboxFountainStream", "isFamilyFriendly", "name", "paid", "playerType", "postmodFountainStream", "premodFountainStream", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "unfilteredFountainStream", "unlisted", "url", "width", "widthPx" });
  }

  public static VideoObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VideoObjectJson
 * JD-Core Version:    0.6.2
 */