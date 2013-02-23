package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataWhoCanInterruptSettingsJson extends EsJson<DataWhoCanInterruptSettings>
{
  static final DataWhoCanInterruptSettingsJson INSTANCE = new DataWhoCanInterruptSettingsJson();

  private DataWhoCanInterruptSettingsJson()
  {
    super(DataWhoCanInterruptSettings.class, new Object[] { "aclJson" });
  }

  public static DataWhoCanInterruptSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataWhoCanInterruptSettingsJson
 * JD-Core Version:    0.6.2
 */