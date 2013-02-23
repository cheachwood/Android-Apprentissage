package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SearchQueryRequest;
import com.google.api.services.plusi.model.SearchQueryRequestJson;
import com.google.api.services.plusi.model.SearchQueryResponse;
import com.google.api.services.plusi.model.SearchQueryResponseJson;

public final class SearchActivitiesOperation extends PlusiOperation<SearchQueryRequest, SearchQueryResponse>
{
  private final String mContinuationToken;
  private final String mQuery;

  public SearchActivitiesOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "searchquery", SearchQueryRequestJson.getInstance(), SearchQueryResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mQuery = paramString1;
    this.mContinuationToken = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SearchActivitiesOperation
 * JD-Core Version:    0.6.2
 */