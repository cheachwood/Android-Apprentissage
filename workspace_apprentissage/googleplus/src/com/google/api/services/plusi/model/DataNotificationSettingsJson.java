package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataNotificationSettingsJson extends EsJson<DataNotificationSettings>
{
  static final DataNotificationSettingsJson INSTANCE = new DataNotificationSettingsJson();

  private DataNotificationSettingsJson()
  {
    super(DataNotificationSettings.class, new Object[] { DataNotificationSettingsEmailJson.class, "alternateEmail", DataNotificationSettingsNotificationsSettingsCategoryInfoJson.class, "categoryInfo", "defaultDestinationId", DataNotificationSettingsDeliveryOptionJson.class, "deliveryOption", "emailAddress", DataNotificationSettingsDeliveryOptionJson.class, "emailDeliveryOption", "isEmailAddressEditable", "showEntitiesSettings", DataNotificationSettingsDeliveryOptionJson.class, "smsDeliveryOption", "smsDestinationId", "uncircledCanNotify" });
  }

  public static DataNotificationSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettingsJson
 * JD-Core Version:    0.6.2
 */