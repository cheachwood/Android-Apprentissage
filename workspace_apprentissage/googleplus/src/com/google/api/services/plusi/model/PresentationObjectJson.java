package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PresentationObjectJson extends EsJson<PresentationObject>
{
  static final PresentationObjectJson INSTANCE = new PresentationObjectJson();

  private PresentationObjectJson()
  {
    super(PresentationObject.class, new Object[] { "description", "embedUrl", "faviconUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "url" });
  }

  public static PresentationObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PresentationObjectJson
 * JD-Core Version:    0.6.2
 */