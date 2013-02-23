package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataGadgetsSettingsJson extends EsJson<DataGadgetsSettings>
{
  static final DataGadgetsSettingsJson INSTANCE = new DataGadgetsSettingsJson();

  private DataGadgetsSettingsJson()
  {
    super(DataGadgetsSettings.class, new Object[] { "clientData", "isBookmarksVisible", "isSandbarNotificationsMuted", "serverData" });
  }

  public static DataGadgetsSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataGadgetsSettingsJson
 * JD-Core Version:    0.6.2
 */