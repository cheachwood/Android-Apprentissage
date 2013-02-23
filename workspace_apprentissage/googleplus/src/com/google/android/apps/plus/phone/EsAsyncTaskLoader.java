package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public abstract class EsAsyncTaskLoader<E> extends AsyncTaskLoader<E>
{
  private boolean mLoaderException;

  public EsAsyncTaskLoader(Context paramContext)
  {
    super(paramContext);
  }

  public void deliverResult(E paramE)
  {
    if (!this.mLoaderException)
      super.deliverResult(paramE);
  }

  public abstract E esLoadInBackground();

  public final E loadInBackground()
  {
    if (!this.mLoaderException);
    while (true)
    {
      try
      {
        Object localObject2 = esLoadInBackground();
        localObject1 = localObject2;
        return localObject1;
      }
      catch (SQLiteException localSQLiteException)
      {
        Log.w("EsAsyncTaskLoader", "loadInBackground failed", localSQLiteException);
        this.mLoaderException = true;
      }
      Object localObject1 = null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsAsyncTaskLoader
 * JD-Core Version:    0.6.2
 */