package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataMobileSettings extends GenericJson
{
  public List<DataMobileSettingsCountry> country;
  public DataMobileSettingsDeliveryWindow deliveryWindow;
  public String emailFrequency;
  public Boolean hasPushTarget;
  public Boolean isInternationalSmsServiceNumber;
  public Boolean isNumberAdded;
  public Boolean isSmsEnabled;
  public Boolean isSmsPostingDisabled;
  public String mobileNotificationType;
  public String mobileNumber;
  public String pin;
  public DataMobileSettingsCountry selectedCountry;
  public String smsFrequency;
  public String smsServiceNumber;
  public String userRegionCodeFromPhoneNumber;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataMobileSettings
 * JD-Core Version:    0.6.2
 */