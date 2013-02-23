package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataHovercardBannerInfoJson extends EsJson<DataHovercardBannerInfo>
{
  static final DataHovercardBannerInfoJson INSTANCE = new DataHovercardBannerInfoJson();

  private DataHovercardBannerInfoJson()
  {
    super(DataHovercardBannerInfo.class, new Object[] { "fullBleedPhotoUrl", DataHovercardBannerInfoImageJson.class, "origFullBleedImage", DataScrapbookInfoJson.class, "scrapbookInfo", "scrapbookPhotoUrl" });
  }

  public static DataHovercardBannerInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataHovercardBannerInfoJson
 * JD-Core Version:    0.6.2
 */