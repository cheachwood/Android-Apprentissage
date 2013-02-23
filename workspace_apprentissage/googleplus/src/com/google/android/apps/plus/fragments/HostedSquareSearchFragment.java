package com.google.android.apps.plus.fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.Recyclable;
import com.google.android.apps.plus.views.SearchViewAdapter;
import com.google.android.apps.plus.views.SearchViewAdapter.OnQueryChangeListener;

public class HostedSquareSearchFragment extends HostedEsFragment
  implements SquareSearchAdapter.SearchListAdapterListener, SearchViewAdapter.OnQueryChangeListener
{
  protected static ScreenMetrics sScreenMetrics;
  private SquareSearchAdapter mAdapter;
  private ColumnGridView mGridView;

  private void updateView(View paramView)
  {
    this.mGridView.setVisibility(0);
    showContent(paramView);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.SQUARE_SEARCH;
  }

  protected final boolean isEmpty()
  {
    return false;
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    LoaderManager localLoaderManager = getLoaderManager();
    this.mAdapter = new SquareSearchAdapter(getActivity(), getFragmentManager(), localLoaderManager, this.mAccount);
    this.mAdapter.onCreate(paramBundle);
    this.mAdapter.setListener(this);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.hosted_square_search_fragment, paramViewGroup, false);
    this.mGridView = ((ColumnGridView)localView.findViewById(R.id.grid));
    this.mGridView.setAdapter(this.mAdapter);
    if (sScreenMetrics == null)
      sScreenMetrics = ScreenMetrics.getInstance(getActivity());
    Resources localResources = getActivity().getResources();
    int i = localResources.getConfiguration().orientation;
    int j = 0;
    if (i == 2)
      j = 1;
    if (j != 0)
    {
      this.mGridView.setOrientation(1);
      this.mGridView.setColumnCount(-1);
      this.mGridView.setMinColumnWidth(localResources.getDimensionPixelSize(R.dimen.square_card_min_height));
      this.mGridView.setItemMargin(sScreenMetrics.itemMargin);
      this.mGridView.setPadding(sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin);
      this.mGridView.setRecyclerListener(new ColumnGridView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          if ((paramAnonymousView instanceof Recyclable))
            ((Recyclable)paramAnonymousView).onRecycle();
        }
      });
      updateView(localView);
      return localView;
    }
    this.mGridView.setOrientation(2);
    ColumnGridView localColumnGridView = this.mGridView;
    if (sScreenMetrics.screenDisplayType == 0);
    for (int k = 1; ; k = 2)
    {
      localColumnGridView.setColumnCount(k);
      break;
    }
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showSearchView();
    paramHostActionBar.getSearchViewAdapter().setQueryHint(R.string.search_squares_hint_text);
    paramHostActionBar.getSearchViewAdapter().addOnChangeListener(this);
  }

  public final void onQueryClose()
  {
  }

  public final void onQueryTextChanged(CharSequence paramCharSequence)
  {
    SquareSearchAdapter localSquareSearchAdapter;
    if (this.mAdapter != null)
    {
      localSquareSearchAdapter = this.mAdapter;
      if (paramCharSequence != null)
        break label24;
    }
    label24: for (String str = null; ; str = paramCharSequence.toString().trim())
    {
      localSquareSearchAdapter.setQueryString(str);
      return;
    }
  }

  public final void onQueryTextSubmitted(CharSequence paramCharSequence)
  {
  }

  public final void onResume()
  {
    super.onResume();
    updateView(getView());
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAdapter != null)
      this.mAdapter.onSaveInstanceState(paramBundle);
  }

  public final void onSearchListAdapterStateChange$6c57bada()
  {
    View localView = getView();
    if (localView != null)
      updateView(localView);
  }

  public final void onSquareSelected(String paramString)
  {
    startActivity(Intents.getSquareStreamActivityIntent(getActivity(), this.mAccount, paramString, null, null));
  }

  public final void onStart()
  {
    super.onStart();
    this.mAdapter.onStart();
  }

  public final void onStop()
  {
    super.onStart();
    this.mAdapter.onStop();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedSquareSearchFragment
 * JD-Core Version:    0.6.2
 */