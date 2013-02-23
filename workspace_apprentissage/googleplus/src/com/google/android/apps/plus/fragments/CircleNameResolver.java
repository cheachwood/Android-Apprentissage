package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import java.util.ArrayList;
import java.util.Map;

public final class CircleNameResolver
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  private final EsAccount mAccount;
  private Map<String, String> mCircleNames;
  private final Context mContext;
  private final DataSetObservable mDataSetObservable = new DataSetObservable();
  private boolean mLoaded;
  private final int mLoaderId;
  private final LoaderManager mLoaderManager;
  private final StringBuilder mStringBuilder = new StringBuilder();

  public CircleNameResolver(Context paramContext, LoaderManager paramLoaderManager, EsAccount paramEsAccount)
  {
    this(paramContext, paramLoaderManager, paramEsAccount, 0);
  }

  public CircleNameResolver(Context paramContext, LoaderManager paramLoaderManager, EsAccount paramEsAccount, int paramInt)
  {
    this.mContext = paramContext;
    this.mLoaderManager = paramLoaderManager;
    this.mAccount = paramEsAccount;
    this.mLoaderId = (paramInt + 2048);
  }

  public final ArrayList<String> getCircleNameListForPackedIds(String paramString)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      if (this.mLoaded)
      {
        boolean bool = TextUtils.isEmpty(paramString);
        if (!bool)
          break label32;
      }
      while (true)
      {
        return localArrayList;
        label32: int j;
        for (int i = 0; i < paramString.length(); i = j + 1)
        {
          j = paramString.indexOf('|', i);
          if (j == -1)
            j = paramString.length();
          String str = (String)this.mCircleNames.get(paramString.substring(i, j));
          if (str != null)
            localArrayList.add(str);
        }
      }
    }
    finally
    {
    }
  }

  public final CharSequence getCircleNamesForPackedIds(String paramString)
  {
    while (true)
    {
      int j;
      try
      {
        Object localObject2;
        if ((!this.mLoaded) || (TextUtils.isEmpty(paramString)))
        {
          localObject2 = "";
          return localObject2;
        }
        this.mStringBuilder.setLength(0);
        i = 0;
        if (i < paramString.length())
        {
          j = paramString.indexOf('|', i);
          if (j == -1)
            j = paramString.length();
          String str2 = (String)this.mCircleNames.get(paramString.substring(i, j));
          if (str2 != null)
          {
            if (this.mStringBuilder.length() != 0)
              this.mStringBuilder.append(", ");
            this.mStringBuilder.append(str2);
          }
        }
        else
        {
          String str1 = this.mStringBuilder.toString();
          localObject2 = str1;
          continue;
        }
      }
      finally
      {
      }
      int i = j + 1;
    }
  }

  public final void initLoader()
  {
    this.mLoaderManager.initLoader(this.mLoaderId, null, this);
  }

  public final boolean isLoaded()
  {
    return this.mLoaded;
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new CircleListLoader(this.mContext, this.mAccount, 0, new String[] { "circle_id", "circle_name" });
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
    this.mDataSetObservable.notifyInvalidated();
  }

  public final void registerObserver(DataSetObserver paramDataSetObserver)
  {
    this.mDataSetObservable.registerObserver(paramDataSetObserver);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CircleNameResolver
 * JD-Core Version:    0.6.2
 */