package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.RegisterDeviceRequest;
import com.google.api.services.plusi.model.RegisterDeviceRequestJson;
import com.google.api.services.plusi.model.RegisterDeviceResponse;
import com.google.api.services.plusi.model.RegisterDeviceResponseJson;

public final class RegisterDeviceOperation extends PlusiOperation<RegisterDeviceRequest, RegisterDeviceResponse>
{
  public RegisterDeviceOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "registerdevice", RegisterDeviceRequestJson.getInstance(), RegisterDeviceResponseJson.getInstance(), null, null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.RegisterDeviceOperation
 * JD-Core Version:    0.6.2
 */