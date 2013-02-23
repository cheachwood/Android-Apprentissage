package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataBirthdaySettingsJson extends EsJson<DataBirthdaySettings>
{
  static final DataBirthdaySettingsJson INSTANCE = new DataBirthdaySettingsJson();

  private DataBirthdaySettingsJson()
  {
    super(DataBirthdaySettings.class, new Object[] { "aclJson", SharingRosterJson.class, "sharingRoster" });
  }

  public static DataBirthdaySettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataBirthdaySettingsJson
 * JD-Core Version:    0.6.2
 */