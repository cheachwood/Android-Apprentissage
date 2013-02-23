package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ImageObjectJson extends EsJson<ImageObject>
{
  static final ImageObjectJson INSTANCE = new ImageObjectJson();

  private ImageObjectJson()
  {
    super(ImageObject.class, new Object[] { EmbedClientItemJson.class, "about", PlaceJson.class, "contentLocation", "contentUrl", "description", "height", "heightPx", "imageUrl", "name", ThumbnailJson.class, "proxiedImage", "sourceDomain", "thumbnailUrl", "url", "width", "widthPx" });
  }

  public static ImageObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ImageObjectJson
 * JD-Core Version:    0.6.2
 */