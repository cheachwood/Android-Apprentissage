package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ThumbnailJson extends EsJson<Thumbnail>
{
  static final ThumbnailJson INSTANCE = new ThumbnailJson();

  private ThumbnailJson()
  {
    super(Thumbnail.class, new Object[] { "boxHeightPx", "boxWidthPx", "exactHeight", "exactWidth", "imageHeightPx", "imageUrl", "recenterVertically", SafeMobileUrlJson.class, "safeMobileUrl", "uncroppedImageUrl" });
  }

  public static ThumbnailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ThumbnailJson
 * JD-Core Version:    0.6.2
 */