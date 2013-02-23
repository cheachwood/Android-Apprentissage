package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataNotificationSettingsNotificationsSettingsCategoryInfoJson extends EsJson<DataNotificationSettingsNotificationsSettingsCategoryInfo>
{
  static final DataNotificationSettingsNotificationsSettingsCategoryInfoJson INSTANCE = new DataNotificationSettingsNotificationsSettingsCategoryInfoJson();

  private DataNotificationSettingsNotificationsSettingsCategoryInfoJson()
  {
    super(DataNotificationSettingsNotificationsSettingsCategoryInfo.class, new Object[] { "category", "description" });
  }

  public static DataNotificationSettingsNotificationsSettingsCategoryInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettingsNotificationsSettingsCategoryInfoJson
 * JD-Core Version:    0.6.2
 */