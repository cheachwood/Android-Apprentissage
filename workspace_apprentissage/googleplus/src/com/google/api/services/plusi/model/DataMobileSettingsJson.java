package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataMobileSettingsJson extends EsJson<DataMobileSettings>
{
  static final DataMobileSettingsJson INSTANCE = new DataMobileSettingsJson();

  private DataMobileSettingsJson()
  {
    super(DataMobileSettings.class, new Object[] { DataMobileSettingsCountryJson.class, "country", DataMobileSettingsDeliveryWindowJson.class, "deliveryWindow", "emailFrequency", "hasPushTarget", "isInternationalSmsServiceNumber", "isNumberAdded", "isSmsEnabled", "isSmsPostingDisabled", "mobileNotificationType", "mobileNumber", "pin", DataMobileSettingsCountryJson.class, "selectedCountry", "smsFrequency", "smsServiceNumber", "userRegionCodeFromPhoneNumber" });
  }

  public static DataMobileSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataMobileSettingsJson
 * JD-Core Version:    0.6.2
 */