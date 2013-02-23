package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SettingsFetchRequestJson extends EsJson<SettingsFetchRequest>
{
  static final SettingsFetchRequestJson INSTANCE = new SettingsFetchRequestJson();

  private SettingsFetchRequestJson()
  {
    super(SettingsFetchRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", DataNotificationSettingsFetchParamsJson.class, "notificationSettingsFetchParams" });
  }

  public static SettingsFetchRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SettingsFetchRequestJson
 * JD-Core Version:    0.6.2
 */