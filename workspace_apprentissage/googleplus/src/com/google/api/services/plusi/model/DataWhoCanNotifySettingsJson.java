package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataWhoCanNotifySettingsJson extends EsJson<DataWhoCanNotifySettings>
{
  static final DataWhoCanNotifySettingsJson INSTANCE = new DataWhoCanNotifySettingsJson();

  private DataWhoCanNotifySettingsJson()
  {
    super(DataWhoCanNotifySettings.class, new Object[] { "aclJson" });
  }

  public static DataWhoCanNotifySettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataWhoCanNotifySettingsJson
 * JD-Core Version:    0.6.2
 */