package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SettingsNotificationTypeJson extends EsJson<SettingsNotificationType>
{
  static final SettingsNotificationTypeJson INSTANCE = new SettingsNotificationTypeJson();

  private SettingsNotificationTypeJson()
  {
    super(SettingsNotificationType.class, new Object[] { "bucket", "channel" });
  }

  public static SettingsNotificationTypeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SettingsNotificationTypeJson
 * JD-Core Version:    0.6.2
 */