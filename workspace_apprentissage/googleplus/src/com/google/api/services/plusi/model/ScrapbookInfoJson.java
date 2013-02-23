package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScrapbookInfoJson extends EsJson<ScrapbookInfo>
{
  static final ScrapbookInfoJson INSTANCE = new ScrapbookInfoJson();

  private ScrapbookInfoJson()
  {
    super(ScrapbookInfo.class, new Object[] { ScrapbookInfoFullBleedPhotoJson.class, "fullBleedPhoto", "fullBleedPhotoId", "layout" });
  }

  public static ScrapbookInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScrapbookInfoJson
 * JD-Core Version:    0.6.2
 */