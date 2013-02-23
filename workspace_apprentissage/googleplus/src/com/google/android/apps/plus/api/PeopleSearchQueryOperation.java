package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PeopleResult;
import com.google.api.services.plusi.model.SearchQueryRequest;
import com.google.api.services.plusi.model.SearchQueryRequestJson;
import com.google.api.services.plusi.model.SearchQueryResponse;
import com.google.api.services.plusi.model.SearchQueryResponseJson;
import java.util.List;

public final class PeopleSearchQueryOperation extends PlusiOperation<SearchQueryRequest, SearchQueryResponse>
{
  private final String mContinuationToken;
  private boolean mInludePlusPages;
  private String mNewContinuationToken;
  private List<PeopleResult> mPeopleResults;
  private final String mQuery;

  public PeopleSearchQueryOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "searchquery", SearchQueryRequestJson.getInstance(), SearchQueryResponseJson.getInstance(), null, null);
    this.mQuery = paramString1;
    this.mContinuationToken = paramString2;
    this.mInludePlusPages = paramBoolean;
  }

  public final String getContinuationToken()
  {
    return this.mNewContinuationToken;
  }

  public final List<PeopleResult> getPeopleSearchResults()
  {
    return this.mPeopleResults;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PeopleSearchQueryOperation
 * JD-Core Version:    0.6.2
 */