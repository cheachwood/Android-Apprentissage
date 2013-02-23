package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.ApiaryBatchOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;

public final class GetSuggestedPeopleOperation extends ApiaryBatchOperation
{
  public GetSuggestedPeopleOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, null, null);
    add(new FindMorePeopleOperation(paramContext, paramEsAccount, null, null));
    add(new GetCelebritySuggestionsOperation(paramContext, paramEsAccount, null, null));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetSuggestedPeopleOperation
 * JD-Core Version:    0.6.2
 */