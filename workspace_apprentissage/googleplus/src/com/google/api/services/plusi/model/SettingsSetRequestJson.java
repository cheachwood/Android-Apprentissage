package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SettingsSetRequestJson extends EsJson<SettingsSetRequest>
{
  static final SettingsSetRequestJson INSTANCE = new SettingsSetRequestJson();

  private SettingsSetRequestJson()
  {
    super(SettingsSetRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", OzDataSettingsJson.class, "settings" });
  }

  public static SettingsSetRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SettingsSetRequestJson
 * JD-Core Version:    0.6.2
 */