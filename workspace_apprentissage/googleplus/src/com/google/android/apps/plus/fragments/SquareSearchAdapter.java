package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.SquareListItemView;
import com.google.android.apps.plus.views.SquareListItemView.OnItemClickListener;

public final class SquareSearchAdapter extends EsCompositeCursorAdapter
  implements LoaderManager.LoaderCallbacks<SquareSearchLoaderResults>, SquareListItemView.OnItemClickListener
{
  private static int sMinWidth;
  protected final EsAccount mAccount;
  private boolean mError;
  private final Handler mHandler;
  private boolean mLandscape;
  protected SearchListAdapterListener mListener;
  private final LoaderManager mLoaderManager;
  private boolean mLoading;
  private boolean mNotFound;
  protected String mQuery;
  private SquareSearchResults mResults;
  private boolean mResultsPreserved;
  private boolean mShowProgressWhenEmpty;
  private final int mSquaresLoaderId;

  public SquareSearchAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount)
  {
    this(paramContext, paramFragmentManager, paramLoaderManager, paramEsAccount, 0);
  }

  private SquareSearchAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount, int paramInt)
  {
    super(paramContext, (byte)0);
    this.mShowProgressWhenEmpty = bool;
    this.mResults = new SquareSearchResults(HostedSquareListFragment.Query.PROJECTION);
    this.mHandler = new Handler()
    {
      public final void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default:
        case 0:
        case 1:
        }
        while (true)
        {
          return;
          SquareSearchAdapter.this.showEmptySearchResults();
          continue;
          SquareSearchAdapter.access$000(SquareSearchAdapter.this);
        }
      }
    };
    for (int i = 0; i < 2; i++)
      addPartition(false, false);
    this.mSquaresLoaderId = 1024;
    SearchResultsFragment localSearchResultsFragment = (SearchResultsFragment)paramFragmentManager.findFragmentByTag("square_search_results");
    if (localSearchResultsFragment == null)
    {
      localSearchResultsFragment = new SearchResultsFragment();
      paramFragmentManager.beginTransaction().add(localSearchResultsFragment, "square_search_results").commitAllowingStateLoss();
      localSearchResultsFragment.setSquareSearchResults(this.mResults);
      this.mLoaderManager = paramLoaderManager;
      this.mAccount = paramEsAccount;
      if (paramContext.getResources().getConfiguration().orientation != 2)
        break label207;
    }
    while (true)
    {
      this.mLandscape = bool;
      if (sMinWidth == 0)
        sMinWidth = paramContext.getResources().getDimensionPixelSize(R.dimen.square_card_min_width);
      return;
      SquareSearchResults localSquareSearchResults = localSearchResultsFragment.getSquareSearchResults();
      if (localSquareSearchResults == null)
        break;
      this.mResults = localSquareSearchResults;
      this.mQuery = this.mResults.getQuery();
      this.mResultsPreserved = bool;
      break;
      label207: bool = false;
    }
  }

  private void updateSearchStatus()
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(new String[] { "_id" });
    if ((!TextUtils.isEmpty(this.mQuery)) && (this.mQuery.trim().length() >= 2))
    {
      if (!this.mError)
        break label86;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(3);
      localEsMatrixCursor.addRow(arrayOfObject3);
    }
    while (true)
    {
      if (localEsMatrixCursor.getCount() != 0)
        showEmptySearchResults();
      changeCursor(1, localEsMatrixCursor);
      return;
      label86: if (this.mNotFound)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(2);
        localEsMatrixCursor.addRow(arrayOfObject2);
      }
      else if ((this.mLoading) && ((this.mShowProgressWhenEmpty) || (this.mResults.getCount() > 0)))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(1);
        localEsMatrixCursor.addRow(arrayOfObject1);
      }
    }
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    int m;
    switch (paramInt1)
    {
    default:
      if (this.mLandscape)
      {
        m = 1;
        label34: if (!this.mLandscape)
          break label227;
      }
      break;
    case 0:
    case 1:
    }
    label227: for (int n = sMinWidth; ; n = -2)
    {
      paramView.setLayoutParams(new ColumnGridView.LayoutParams(m, n, 1, 1));
      return;
      ((SquareListItemView)paramView).init(paramCursor, this, true, false);
      if ((paramInt2 != -1 + paramCursor.getCount()) || (!this.mResults.hasMoreResults()))
        break;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          SquareSearchAdapter.access$100(SquareSearchAdapter.this);
        }
      });
      break;
      int i = 8;
      int j = 8;
      int k = 8;
      switch (paramCursor.getInt(0))
      {
      default:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        paramView.findViewById(R.id.loading).setVisibility(i);
        paramView.findViewById(R.id.not_found).setVisibility(j);
        paramView.findViewById(R.id.error).setVisibility(k);
        break;
        i = 0;
        continue;
        j = 0;
        continue;
        k = 0;
      }
      m = 2;
      break label34;
    }
  }

  protected final int getItemViewType(int paramInt1, int paramInt2)
  {
    return paramInt1;
  }

  public final int getItemViewTypeCount()
  {
    return 2;
  }

  public final boolean isEmpty()
  {
    return TextUtils.isEmpty(this.mQuery);
  }

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(paramContext);
    switch (paramInt)
    {
    default:
    case 1:
    }
    for (View localView = localLayoutInflater.inflate(R.layout.square_list_item_view, paramViewGroup, false); ; localView = localLayoutInflater.inflate(R.layout.square_search_status_card, paramViewGroup, false))
      return localView;
  }

  public final void onClick(String paramString)
  {
    if (this.mListener != null)
      this.mListener.onSquareSelected(paramString);
  }

  public final void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      paramBundle.setClassLoader(getClass().getClassLoader());
      this.mQuery = paramBundle.getString("search_list_adapter.query");
      if ((paramBundle.containsKey("search_list_adapter.results")) && (!this.mResultsPreserved))
        this.mResults = ((SquareSearchResults)paramBundle.getParcelable("search_list_adapter.results"));
    }
  }

  public final Loader<SquareSearchLoaderResults> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Context localContext;
    EsAccount localEsAccount;
    if (paramInt == this.mSquaresLoaderId)
    {
      localContext = getContext();
      localEsAccount = this.mAccount;
    }
    for (SquareSearchLoader localSquareSearchLoader = new SquareSearchLoader(localContext, localEsAccount, this.mQuery, 2, this.mResults.getContinuationToken()); ; localSquareSearchLoader = null)
      return localSquareSearchLoader;
  }

  public final void onInvitationDismissed(String paramString)
  {
  }

  public final void onInviterImageClick(String paramString)
  {
  }

  public final void onLoaderReset(Loader<SquareSearchLoaderResults> paramLoader)
  {
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putString("search_list_adapter.query", this.mQuery);
    if (this.mResults.isParcelable())
      paramBundle.putParcelable("search_list_adapter.results", this.mResults);
  }

  public final void onStart()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("query", this.mQuery);
    this.mLoaderManager.initLoader(this.mSquaresLoaderId, localBundle, this);
    updateSearchStatus();
  }

  public final void onStop()
  {
    this.mHandler.removeMessages(0);
  }

  public final void setListener(SearchListAdapterListener paramSearchListAdapterListener)
  {
    this.mListener = paramSearchListAdapterListener;
  }

  public final void setQueryString(String paramString)
  {
    if (TextUtils.equals(this.mQuery, paramString));
    while (true)
    {
      return;
      this.mResults.setQueryString(paramString);
      this.mHandler.removeMessages(0);
      this.mHandler.removeMessages(1);
      this.mQuery = paramString;
      if (TextUtils.isEmpty(paramString))
      {
        this.mLoaderManager.destroyLoader(this.mSquaresLoaderId);
        clearPartitions();
        if (this.mListener != null)
          this.mListener.onSearchListAdapterStateChange$6c57bada();
      }
      else
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("query", this.mQuery);
        this.mError = false;
        this.mNotFound = false;
        this.mLoading = false;
        this.mHandler.sendEmptyMessageDelayed(1, 300L);
        this.mLoaderManager.destroyLoader(this.mSquaresLoaderId);
        this.mLoaderManager.initLoader(this.mSquaresLoaderId, localBundle, this);
        updateSearchStatus();
      }
    }
  }

  protected final void showEmptySearchResults()
  {
    this.mHandler.removeMessages(0);
    Cursor localCursor = this.mResults.getCursor();
    if (localCursor.getCount() == 0)
      changeCursor(0, localCursor);
  }

  public static abstract interface SearchListAdapterListener
  {
    public abstract void onSearchListAdapterStateChange$6c57bada();

    public abstract void onSquareSelected(String paramString);
  }

  public static class SearchResultsFragment extends Fragment
  {
    private SquareSearchResults mResults;

    public SearchResultsFragment()
    {
      setRetainInstance(true);
    }

    public final SquareSearchResults getSquareSearchResults()
    {
      return this.mResults;
    }

    public final void setSquareSearchResults(SquareSearchResults paramSquareSearchResults)
    {
      this.mResults = paramSquareSearchResults;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SquareSearchAdapter
 * JD-Core Version:    0.6.2
 */