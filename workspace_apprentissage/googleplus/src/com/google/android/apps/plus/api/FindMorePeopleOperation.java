package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.FindMorePeopleRequest;
import com.google.api.services.plusi.model.FindMorePeopleRequestJson;
import com.google.api.services.plusi.model.FindMorePeopleResponse;
import com.google.api.services.plusi.model.FindMorePeopleResponseJson;

public final class FindMorePeopleOperation extends PlusiOperation<FindMorePeopleRequest, FindMorePeopleResponse>
{
  public FindMorePeopleOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "findmorepeople", FindMorePeopleRequestJson.getInstance(), FindMorePeopleResponseJson.getInstance(), null, null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.FindMorePeopleOperation
 * JD-Core Version:    0.6.2
 */