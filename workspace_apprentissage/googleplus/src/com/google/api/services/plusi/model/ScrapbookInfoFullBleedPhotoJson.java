package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScrapbookInfoFullBleedPhotoJson extends EsJson<ScrapbookInfoFullBleedPhoto>
{
  static final ScrapbookInfoFullBleedPhotoJson INSTANCE = new ScrapbookInfoFullBleedPhotoJson();

  private ScrapbookInfoFullBleedPhotoJson()
  {
    super(ScrapbookInfoFullBleedPhoto.class, new Object[] { "id", ScrapbookInfoOffsetJson.class, "offset", "photoOwnerType" });
  }

  public static ScrapbookInfoFullBleedPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScrapbookInfoFullBleedPhotoJson
 * JD-Core Version:    0.6.2
 */