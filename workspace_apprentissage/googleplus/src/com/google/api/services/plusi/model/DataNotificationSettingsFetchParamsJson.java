package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataNotificationSettingsFetchParamsJson extends EsJson<DataNotificationSettingsFetchParams>
{
  static final DataNotificationSettingsFetchParamsJson INSTANCE = new DataNotificationSettingsFetchParamsJson();

  private DataNotificationSettingsFetchParamsJson()
  {
    super(DataNotificationSettingsFetchParams.class, new Object[] { "fetchAlternateEmailAddress", "fetchPlusPageSettings", "fetchSettingsDescription", "fetchWhoCanNotifyMe", "settingsType", "typeGroupToFetch" });
  }

  public static DataNotificationSettingsFetchParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettingsFetchParamsJson
 * JD-Core Version:    0.6.2
 */