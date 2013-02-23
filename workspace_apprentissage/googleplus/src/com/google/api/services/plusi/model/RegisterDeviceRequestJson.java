package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RegisterDeviceRequestJson extends EsJson<RegisterDeviceRequest>
{
  static final RegisterDeviceRequestJson INSTANCE = new RegisterDeviceRequestJson();

  private RegisterDeviceRequestJson()
  {
    super(RegisterDeviceRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", RegisterDeviceParamsDestinationSettingsJson.class, "destinationSettings", "enableTracing", PushAddressJson.class, "pushAddress" });
  }

  public static RegisterDeviceRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RegisterDeviceRequestJson
 * JD-Core Version:    0.6.2
 */