package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataNotificationSettings extends GenericJson
{
  public List<DataNotificationSettingsEmail> alternateEmail;
  public List<DataNotificationSettingsNotificationsSettingsCategoryInfo> categoryInfo;
  public String defaultDestinationId;
  public List<DataNotificationSettingsDeliveryOption> deliveryOption;
  public String emailAddress;
  public List<DataNotificationSettingsDeliveryOption> emailDeliveryOption;
  public Boolean isEmailAddressEditable;
  public Boolean showEntitiesSettings;
  public List<DataNotificationSettingsDeliveryOption> smsDeliveryOption;
  public String smsDestinationId;
  public Boolean uncircledCanNotify;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettings
 * JD-Core Version:    0.6.2
 */