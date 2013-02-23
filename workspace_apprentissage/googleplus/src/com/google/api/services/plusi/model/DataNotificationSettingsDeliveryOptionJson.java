package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataNotificationSettingsDeliveryOptionJson extends EsJson<DataNotificationSettingsDeliveryOption>
{
  static final DataNotificationSettingsDeliveryOptionJson INSTANCE = new DataNotificationSettingsDeliveryOptionJson();

  private DataNotificationSettingsDeliveryOptionJson()
  {
    super(DataNotificationSettingsDeliveryOption.class, new Object[] { "bucketId", "category", "description", "enabled", "enabledForEmail", "enabledForPhone", "offnetworkBucketId" });
  }

  public static DataNotificationSettingsDeliveryOptionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettingsDeliveryOptionJson
 * JD-Core Version:    0.6.2
 */