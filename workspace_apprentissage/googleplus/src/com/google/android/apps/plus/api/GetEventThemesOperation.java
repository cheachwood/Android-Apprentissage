package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetEventThemesRequest;
import com.google.api.services.plusi.model.GetEventThemesRequestJson;
import com.google.api.services.plusi.model.GetEventThemesResponse;
import com.google.api.services.plusi.model.GetEventThemesResponseJson;

public final class GetEventThemesOperation extends PlusiOperation<GetEventThemesRequest, GetEventThemesResponse>
{
  public GetEventThemesOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "geteventthemes", GetEventThemesRequestJson.getInstance(), GetEventThemesResponseJson.getInstance(), paramIntent, paramOperationListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetEventThemesOperation
 * JD-Core Version:    0.6.2
 */