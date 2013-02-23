package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RegisterDeviceResponseJson extends EsJson<RegisterDeviceResponse>
{
  static final RegisterDeviceResponseJson INSTANCE = new RegisterDeviceResponseJson();

  private RegisterDeviceResponseJson()
  {
    super(RegisterDeviceResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace" });
  }

  public static RegisterDeviceResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RegisterDeviceResponseJson
 * JD-Core Version:    0.6.2
 */