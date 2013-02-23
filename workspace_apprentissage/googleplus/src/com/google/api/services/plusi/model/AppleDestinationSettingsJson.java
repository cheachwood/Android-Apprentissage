package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AppleDestinationSettingsJson extends EsJson<AppleDestinationSettings>
{
  static final AppleDestinationSettingsJson INSTANCE = new AppleDestinationSettingsJson();

  private AppleDestinationSettingsJson()
  {
    super(AppleDestinationSettings.class, new Object[] { "languageTag", "payloadStyle", "typeGroupToPush" });
  }

  public static AppleDestinationSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AppleDestinationSettingsJson
 * JD-Core Version:    0.6.2
 */