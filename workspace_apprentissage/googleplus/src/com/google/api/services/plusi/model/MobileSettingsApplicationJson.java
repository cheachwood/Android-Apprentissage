package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileSettingsApplicationJson extends EsJson<MobileSettingsApplication>
{
  static final MobileSettingsApplicationJson INSTANCE = new MobileSettingsApplicationJson();

  private MobileSettingsApplicationJson()
  {
    super(MobileSettingsApplication.class, new Object[] { "latestAppVersion", "mandatoryAppVersion" });
  }

  public static MobileSettingsApplicationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileSettingsApplicationJson
 * JD-Core Version:    0.6.2
 */