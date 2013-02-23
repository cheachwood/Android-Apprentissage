package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.NetworkTransactionsAdapter;
import com.google.android.apps.plus.phone.NetworkTransactionsAdapter.NetworkTransactionsQuery;
import com.google.android.apps.plus.service.EsService;

public class NetworkTransactionsListFragment extends EsListFragment<ListView, NetworkTransactionsAdapter>
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  private EsAccount mAccount;
  private ProgressBar mProgressView;

  public final void clear()
  {
    EsService.clearNetworkTransactionsData(getActivity(), this.mAccount);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccount = ((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
    setHasOptionsMenu(true);
    getLoaderManager().initLoader(0, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Uri localUri = EsProvider.appendAccountParameter(EsProvider.NETWORK_DATA_TRANSACTIONS_URI, this.mAccount);
    return new EsCursorLoader(getActivity(), localUri, NetworkTransactionsAdapter.NetworkTransactionsQuery.PROJECTION, null, null, "time DESC");
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, R.layout.list_layout);
    this.mAdapter = new NetworkTransactionsAdapter(getActivity(), null);
    ((ListView)this.mListView).setAdapter(this.mAdapter);
    setupEmptyView(localView, R.string.no_network_transactions);
    showEmptyViewProgress(localView);
    return localView;
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void setProgressBar(ProgressBar paramProgressBar)
  {
    this.mProgressView = paramProgressBar;
    updateSpinner(this.mProgressView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.NetworkTransactionsListFragment
 * JD-Core Version:    0.6.2
 */