package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetCelebritySuggestionsRequest;
import com.google.api.services.plusi.model.GetCelebritySuggestionsRequestJson;
import com.google.api.services.plusi.model.GetCelebritySuggestionsResponse;
import com.google.api.services.plusi.model.GetCelebritySuggestionsResponseJson;

public final class GetCelebritySuggestionsOperation extends PlusiOperation<GetCelebritySuggestionsRequest, GetCelebritySuggestionsResponse>
{
  public GetCelebritySuggestionsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getcelebritysuggestions", GetCelebritySuggestionsRequestJson.getInstance(), GetCelebritySuggestionsResponseJson.getInstance(), null, null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetCelebritySuggestionsOperation
 * JD-Core Version:    0.6.2
 */