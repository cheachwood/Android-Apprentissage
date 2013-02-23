package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPlusActionsSettingsJson extends EsJson<DataPlusActionsSettings>
{
  static final DataPlusActionsSettingsJson INSTANCE = new DataPlusActionsSettingsJson();

  private DataPlusActionsSettingsJson()
  {
    super(DataPlusActionsSettings.class, new Object[] { "enablePlusPageAutoFollow" });
  }

  public static DataPlusActionsSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPlusActionsSettingsJson
 * JD-Core Version:    0.6.2
 */