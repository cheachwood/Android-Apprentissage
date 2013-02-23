package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetMobileExperimentsRequest;
import com.google.api.services.plusi.model.GetMobileExperimentsRequestJson;
import com.google.api.services.plusi.model.GetMobileExperimentsResponse;
import com.google.api.services.plusi.model.GetMobileExperimentsResponseJson;

public final class GetMobileExperimentsOperation extends PlusiOperation<GetMobileExperimentsRequest, GetMobileExperimentsResponse>
{
  public GetMobileExperimentsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getmobileexperiments", GetMobileExperimentsRequestJson.getInstance(), GetMobileExperimentsResponseJson.getInstance(), null, null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetMobileExperimentsOperation
 * JD-Core Version:    0.6.2
 */