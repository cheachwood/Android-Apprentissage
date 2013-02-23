package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataScrapbookInfoJson extends EsJson<DataScrapbookInfo>
{
  static final DataScrapbookInfoJson INSTANCE = new DataScrapbookInfoJson();

  private DataScrapbookInfoJson()
  {
    super(DataScrapbookInfo.class, new Object[] { DataScrapbookInfoFullBleedPhotoJson.class, "fullBleedPhoto", "fullBleedPhotoId", "layout" });
  }

  public static DataScrapbookInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataScrapbookInfoJson
 * JD-Core Version:    0.6.2
 */