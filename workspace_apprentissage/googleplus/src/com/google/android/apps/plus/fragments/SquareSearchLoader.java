package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import com.google.android.apps.plus.api.SquareSearchQueryOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation;

public final class SquareSearchLoader extends AsyncTaskLoader<SquareSearchLoaderResults>
{
  public static final SquareSearchLoaderResults ABORTED = new SquareSearchLoaderResults();
  private final EsAccount mAccount;
  private final String mContinuationToken;
  private SquareSearchLoaderResults mData;
  private final int mMinQueryLength;
  private volatile SquareSearchQueryOperation mOperation;
  private final String mQuery;

  public SquareSearchLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, int paramInt, String paramString2)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mQuery = paramString1;
    this.mMinQueryLength = 2;
    this.mContinuationToken = paramString2;
  }

  private void abort()
  {
    SquareSearchQueryOperation localSquareSearchQueryOperation = this.mOperation;
    if (localSquareSearchQueryOperation != null)
      localSquareSearchQueryOperation.abort();
    this.mOperation = null;
  }

  private SquareSearchLoaderResults loadInBackground()
  {
    Object localObject1;
    if ((TextUtils.isEmpty(this.mQuery)) || (this.mQuery.length() < this.mMinQueryLength))
      localObject1 = new SquareSearchLoaderResults();
    while (true)
    {
      return localObject1;
      Context localContext = getContext();
      EsAccount localEsAccount = this.mAccount;
      String str = this.mQuery;
      SquareSearchQueryOperation localSquareSearchQueryOperation = new SquareSearchQueryOperation(localContext, localEsAccount, str, null, null);
      this.mOperation = localSquareSearchQueryOperation;
      try
      {
        localSquareSearchQueryOperation.start();
        if (localSquareSearchQueryOperation.isAborted())
        {
          SquareSearchLoaderResults localSquareSearchLoaderResults = ABORTED;
          this.mOperation = null;
          localObject1 = localSquareSearchLoaderResults;
        }
        else
        {
          this.mOperation = null;
          if (localSquareSearchQueryOperation.hasError())
          {
            localSquareSearchQueryOperation.logError("SquareSearch");
            localObject1 = null;
          }
        }
      }
      finally
      {
        this.mOperation = null;
      }
    }
  }

  public final boolean cancelLoad()
  {
    abort();
    return super.cancelLoad();
  }

  public final String getContinuationToken()
  {
    return this.mContinuationToken;
  }

  public final void onAbandon()
  {
    abort();
  }

  protected final void onStartLoading()
  {
    if (this.mData == null)
      forceLoad();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SquareSearchLoader
 * JD-Core Version:    0.6.2
 */