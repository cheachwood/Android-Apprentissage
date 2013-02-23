package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataScrapbookInfoFullBleedPhotoJson extends EsJson<DataScrapbookInfoFullBleedPhoto>
{
  static final DataScrapbookInfoFullBleedPhotoJson INSTANCE = new DataScrapbookInfoFullBleedPhotoJson();

  private DataScrapbookInfoFullBleedPhotoJson()
  {
    super(DataScrapbookInfoFullBleedPhoto.class, new Object[] { "id", DataScrapbookInfoOffsetJson.class, "offset", "photoOwnerType" });
  }

  public static DataScrapbookInfoFullBleedPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataScrapbookInfoFullBleedPhotoJson
 * JD-Core Version:    0.6.2
 */