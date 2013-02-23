package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RegisterDeviceParamsDestinationSettingsJson extends EsJson<RegisterDeviceParamsDestinationSettings>
{
  static final RegisterDeviceParamsDestinationSettingsJson INSTANCE = new RegisterDeviceParamsDestinationSettingsJson();

  private RegisterDeviceParamsDestinationSettingsJson()
  {
    super(RegisterDeviceParamsDestinationSettings.class, new Object[] { AppleDestinationSettingsJson.class, "appleDestinationSettings" });
  }

  public static RegisterDeviceParamsDestinationSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RegisterDeviceParamsDestinationSettingsJson
 * JD-Core Version:    0.6.2
 */