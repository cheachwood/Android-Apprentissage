package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DrawingObjectJson extends EsJson<DrawingObject>
{
  static final DrawingObjectJson INSTANCE = new DrawingObjectJson();

  private DrawingObjectJson()
  {
    super(DrawingObject.class, new Object[] { "description", "embedUrl", "faviconUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "url" });
  }

  public static DrawingObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DrawingObjectJson
 * JD-Core Version:    0.6.2
 */