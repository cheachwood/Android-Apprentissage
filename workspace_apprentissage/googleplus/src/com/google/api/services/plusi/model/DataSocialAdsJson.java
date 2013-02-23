package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSocialAdsJson extends EsJson<DataSocialAds>
{
  static final DataSocialAdsJson INSTANCE = new DataSocialAdsJson();

  private DataSocialAdsJson()
  {
    super(DataSocialAds.class, new Object[] { "cookieRefreshUrl", "enabled", "optoutXsrfToken", "url" });
  }

  public static DataSocialAdsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSocialAdsJson
 * JD-Core Version:    0.6.2
 */