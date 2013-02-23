package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleMemberPropertiesAddressJson extends EsJson<DataCircleMemberPropertiesAddress>
{
  static final DataCircleMemberPropertiesAddressJson INSTANCE = new DataCircleMemberPropertiesAddressJson();

  private DataCircleMemberPropertiesAddressJson()
  {
    super(DataCircleMemberPropertiesAddress.class, new Object[] { "country", "customTag", "extendedAddress", "locality", "poBox", "postalCode", "region", "standardTag", "streetAddress", "value" });
  }

  public static DataCircleMemberPropertiesAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleMemberPropertiesAddressJson
 * JD-Core Version:    0.6.2
 */