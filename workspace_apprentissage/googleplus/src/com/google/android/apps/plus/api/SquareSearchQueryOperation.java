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
import com.google.api.services.plusi.model.SquareResult;
import java.util.List;

public final class SquareSearchQueryOperation extends PlusiOperation<SearchQueryRequest, SearchQueryResponse>
{
  private final String mQuery;
  private List<SquareResult> mSquareResults;

  public SquareSearchQueryOperation(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "searchquery", SearchQueryRequestJson.getInstance(), SearchQueryResponseJson.getInstance(), null, null);
    this.mQuery = paramString;
  }

  public final String getContinuationToken()
  {
    return null;
  }

  public final List<SquareResult> getSquareSearchResults()
  {
    return this.mSquareResults;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SquareSearchQueryOperation
 * JD-Core Version:    0.6.2
 */