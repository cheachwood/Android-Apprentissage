package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataNotificationSettingsEmailJson extends EsJson<DataNotificationSettingsEmail>
{
  static final DataNotificationSettingsEmailJson INSTANCE = new DataNotificationSettingsEmailJson();

  private DataNotificationSettingsEmailJson()
  {
    super(DataNotificationSettingsEmail.class, new Object[] { "address", "isVerified", "type" });
  }

  public static DataNotificationSettingsEmailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettingsEmailJson
 * JD-Core Version:    0.6.2
 */