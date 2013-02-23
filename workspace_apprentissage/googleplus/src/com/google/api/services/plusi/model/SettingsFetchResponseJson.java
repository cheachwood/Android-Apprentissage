package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SettingsFetchResponseJson extends EsJson<SettingsFetchResponse>
{
  static final SettingsFetchResponseJson INSTANCE = new SettingsFetchResponseJson();

  private SettingsFetchResponseJson()
  {
    super(SettingsFetchResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", OzDataSettingsJson.class, "settings" });
  }

  public static SettingsFetchResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SettingsFetchResponseJson
 * JD-Core Version:    0.6.2
 */