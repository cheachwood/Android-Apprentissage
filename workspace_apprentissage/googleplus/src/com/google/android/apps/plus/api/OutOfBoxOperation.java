package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.MobileOutOfBoxRequest;
import com.google.api.services.plusi.model.MobileOutOfBoxRequestJson;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;
import com.google.api.services.plusi.model.MobileOutOfBoxResponseJson;

public final class OutOfBoxOperation extends PlusiOperation<MobileOutOfBoxRequest, MobileOutOfBoxResponse>
{
  private MobileOutOfBoxRequest mRequest;
  private MobileOutOfBoxResponse mResponse;

  public OutOfBoxOperation(Context paramContext, EsAccount paramEsAccount, MobileOutOfBoxRequest paramMobileOutOfBoxRequest, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "mobileoutofboxflow", MobileOutOfBoxRequestJson.getInstance(), MobileOutOfBoxResponseJson.getInstance(), null, null);
    this.mRequest = paramMobileOutOfBoxRequest;
  }

  public final MobileOutOfBoxResponse getResponse()
  {
    return this.mResponse;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.OutOfBoxOperation
 * JD-Core Version:    0.6.2
 */