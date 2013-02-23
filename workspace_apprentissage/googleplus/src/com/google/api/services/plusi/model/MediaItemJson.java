package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MediaItemJson extends EsJson<MediaItem>
{
  static final MediaItemJson INSTANCE = new MediaItemJson();

  private MediaItemJson()
  {
    super(MediaItem.class, new Object[] { "albumArtistHtml", "albumHtml", "albumId", "caption", "containerUrl", "contentUrl", "durationMinutes", "durationSeconds", "explicitType", "isFifeThumbnail", "mimeType", "offsetX", "offsetY", "ownerObfuscatedId", "photoId", "playerHeight", "playerUrl", "playerWidth", "provider", "purchaseUrl", "rating", SafeMobileUrlJson.class, "safeMobileUrl", "shareType", "thumbnailHeight", "thumbnailUrl", "thumbnailWidth" });
  }

  public static MediaItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MediaItemJson
 * JD-Core Version:    0.6.2
 */