package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScrapbookInfoOffsetJson extends EsJson<ScrapbookInfoOffset>
{
  static final ScrapbookInfoOffsetJson INSTANCE = new ScrapbookInfoOffsetJson();

  private ScrapbookInfoOffsetJson()
  {
    super(ScrapbookInfoOffset.class, new Object[] { "left", "top" });
  }

  public static ScrapbookInfoOffsetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScrapbookInfoOffsetJson
 * JD-Core Version:    0.6.2
 */