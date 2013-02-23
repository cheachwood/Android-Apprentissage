package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScrapBookEntryJson extends EsJson<ScrapBookEntry>
{
  static final ScrapBookEntryJson INSTANCE = new ScrapBookEntryJson();

  private ScrapBookEntryJson()
  {
    super(ScrapBookEntry.class, new Object[] { "cropUrl", "photoHeight", "photoId", "photoWidth", "url" });
  }

  public static ScrapBookEntryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScrapBookEntryJson
 * JD-Core Version:    0.6.2
 */