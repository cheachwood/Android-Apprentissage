package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SpreadsheetObjectJson extends EsJson<SpreadsheetObject>
{
  static final SpreadsheetObjectJson INSTANCE = new SpreadsheetObjectJson();

  private SpreadsheetObjectJson()
  {
    super(SpreadsheetObject.class, new Object[] { "description", "embedUrl", "faviconUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "url" });
  }

  public static SpreadsheetObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SpreadsheetObjectJson
 * JD-Core Version:    0.6.2
 */