package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.api.services.plusi.model.GetSquaresOzRequest;
import com.google.api.services.plusi.model.GetSquaresOzRequestJson;
import com.google.api.services.plusi.model.GetSquaresOzResponse;
import com.google.api.services.plusi.model.GetSquaresOzResponseJson;

public final class GetSquaresOperation extends PlusiOperation<GetSquaresOzRequest, GetSquaresOzResponse>
{
  private final EsSyncAdapterService.SyncState mSyncState = null;

  public GetSquaresOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, EsSyncAdapterService.SyncState paramSyncState)
  {
    super(paramContext, paramEsAccount, "getsquares", GetSquaresOzRequestJson.getInstance(), GetSquaresOzResponseJson.getInstance(), paramIntent, paramOperationListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetSquaresOperation
 * JD-Core Version:    0.6.2
 */