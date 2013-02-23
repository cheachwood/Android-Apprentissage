package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataMobileSettingsCountryJson extends EsJson<DataMobileSettingsCountry>
{
  static final DataMobileSettingsCountryJson INSTANCE = new DataMobileSettingsCountryJson();

  private DataMobileSettingsCountryJson()
  {
    super(DataMobileSettingsCountry.class, new Object[] { "countryCode", "displayName" });
  }

  public static DataMobileSettingsCountryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataMobileSettingsCountryJson
 * JD-Core Version:    0.6.2
 */