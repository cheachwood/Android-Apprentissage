package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.LoadBlockedPeopleRequest;
import com.google.api.services.plusi.model.LoadBlockedPeopleRequestJson;
import com.google.api.services.plusi.model.LoadBlockedPeopleResponse;
import com.google.api.services.plusi.model.LoadBlockedPeopleResponseJson;

public final class GetBlockedPeopleOperation extends PlusiOperation<LoadBlockedPeopleRequest, LoadBlockedPeopleResponse>
{
  public GetBlockedPeopleOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "loadblockedpeople", LoadBlockedPeopleRequestJson.getInstance(), LoadBlockedPeopleResponseJson.getInstance(), paramIntent, paramOperationListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetBlockedPeopleOperation
 * JD-Core Version:    0.6.2
 */