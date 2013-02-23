package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataHuddleSettingsJson extends EsJson<DataHuddleSettings>
{
  static final DataHuddleSettingsJson INSTANCE = new DataHuddleSettingsJson();

  private DataHuddleSettingsJson()
  {
    super(DataHuddleSettings.class, new Object[] { "acl", "isUsingHuddle" });
  }

  public static DataHuddleSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataHuddleSettingsJson
 * JD-Core Version:    0.6.2
 */