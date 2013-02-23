package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class RegisterDeviceRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public RegisterDeviceParamsDestinationSettings destinationSettings;
  public Boolean enableTracing;
  public PushAddress pushAddress;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RegisterDeviceRequest
 * JD-Core Version:    0.6.2
 */