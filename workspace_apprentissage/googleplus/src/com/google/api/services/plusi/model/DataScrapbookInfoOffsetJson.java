package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataScrapbookInfoOffsetJson extends EsJson<DataScrapbookInfoOffset>
{
  static final DataScrapbookInfoOffsetJson INSTANCE = new DataScrapbookInfoOffsetJson();

  private DataScrapbookInfoOffsetJson()
  {
    super(DataScrapbookInfoOffset.class, new Object[] { "left", "top" });
  }

  public static DataScrapbookInfoOffsetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataScrapbookInfoOffsetJson
 * JD-Core Version:    0.6.2
 */