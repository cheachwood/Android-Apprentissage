package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataHovercardBannerInfoImageJson extends EsJson<DataHovercardBannerInfoImage>
{
  static final DataHovercardBannerInfoImageJson INSTANCE = new DataHovercardBannerInfoImageJson();

  private DataHovercardBannerInfoImageJson()
  {
    super(DataHovercardBannerInfoImage.class, new Object[] { "height", "url", "width" });
  }

  public static DataHovercardBannerInfoImageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataHovercardBannerInfoImageJson
 * JD-Core Version:    0.6.2
 */