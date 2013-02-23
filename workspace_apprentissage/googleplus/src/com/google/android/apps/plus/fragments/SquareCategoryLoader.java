package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.google.android.apps.plus.content.DbSquareStream;
import com.google.android.apps.plus.content.EsAccount;

public final class SquareCategoryLoader extends AsyncTaskLoader<DbSquareStream[]>
{
  private static final String[] PROJECTION = { "square_streams", "last_sync" };
  private final EsAccount mAccount;
  private DbSquareStream[] mData;
  private boolean mIsDataStale;
  private final String mSquareId;

  public SquareCategoryLoader(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mSquareId = paramString;
  }

  public final boolean isDataStale()
  {
    return this.mIsDataStale;
  }

  protected final void onStartLoading()
  {
    if (this.mData == null)
      forceLoad();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SquareCategoryLoader
 * JD-Core Version:    0.6.2
 */