package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsDatabaseHelper;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.ComposeBarController;
import com.google.android.apps.plus.phone.StreamAdapter;
import com.google.android.apps.plus.phone.StreamAdapter.ViewUseListener;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.SearchUtils;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.SearchViewAdapter;
import com.google.android.apps.plus.views.SearchViewAdapter.OnQueryChangeListener;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;

public class HostedPostSearchFragment extends HostedStreamFragment
  implements SearchViewAdapter.OnQueryChangeListener
{
  private String mDelayedQuery;
  private final EsServiceListener mPostsSearchServiceListener = new EsServiceListener()
  {
    public final void onSearchActivitiesComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPostSearchFragment localHostedPostSearchFragment;
      if (((HostedPostSearchFragment.this.mNewerReqId != null) && (paramAnonymousInt == HostedPostSearchFragment.this.mNewerReqId.intValue())) || ((HostedPostSearchFragment.this.mOlderReqId != null) && (paramAnonymousInt == HostedPostSearchFragment.this.mOlderReqId.intValue())))
      {
        HostedPostSearchFragment.this.mNewerReqId = null;
        HostedPostSearchFragment.this.mOlderReqId = null;
        localHostedPostSearchFragment = HostedPostSearchFragment.this;
        if ((paramAnonymousServiceResult == null) || (!paramAnonymousServiceResult.hasError()))
          break label104;
      }
      label104: for (boolean bool = true; ; bool = false)
      {
        localHostedPostSearchFragment.mError = bool;
        HostedPostSearchFragment.this.updateServerErrorView();
        HostedPostSearchFragment.this.loadContent();
        return;
      }
    }
  };
  private String mQuery;
  private SearchViewAdapter mSearchViewAdapter;

  private void createAndRunDbCleanup(final Context paramContext, final EsAccount paramEsAccount, final Runnable paramRunnable)
  {
    new Thread(new Runnable()
    {
      public final void run()
      {
        SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
        localSQLiteDatabase.delete("search", null, null);
        String str = DatabaseUtils.sqlEscapeString(SearchUtils.getSearchKey("") + '%');
        localSQLiteDatabase.delete("activity_streams", "stream_key LIKE " + str, null);
        if (paramRunnable != null)
          new Handler(Looper.getMainLooper()).post(paramRunnable);
      }
    }).start();
  }

  private void doSearch()
  {
    this.mFirstLoad = true;
    prepareLoaderUri();
    getLoaderManager().restartLoader(2, null, this);
    fetchContent(true);
  }

  protected final StreamAdapter createStreamAdapter(Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamAdapter.ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    return super.createStreamAdapter(paramContext, paramColumnGridView, paramEsAccount, paramOnClickListener, paramItemClickListener, paramViewUseListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener, null);
  }

  protected final void fetchContent(final boolean paramBoolean)
  {
    if (TextUtils.isEmpty(this.mQuery));
    while (true)
    {
      return;
      if (paramBoolean)
        showEmptyViewProgress(getView());
      Runnable local2 = new Runnable()
      {
        public final void run()
        {
          if (HostedPostSearchFragment.this.isPaused())
            return;
          if (paramBoolean)
            HostedPostSearchFragment.this.mNewerReqId = Integer.valueOf(EsService.searchActivities(HostedPostSearchFragment.this.getActivity(), HostedPostSearchFragment.this.mAccount, HostedPostSearchFragment.this.mQuery, false));
          for (HostedPostSearchFragment.this.mOlderReqId = null; ; HostedPostSearchFragment.this.mOlderReqId = Integer.valueOf(EsService.searchActivities(HostedPostSearchFragment.this.getActivity(), HostedPostSearchFragment.this.mAccount, HostedPostSearchFragment.this.mQuery, false)))
          {
            HostedPostSearchFragment.this.updateSpinner();
            break;
            HostedPostSearchFragment.this.mNewerReqId = null;
          }
        }
      };
      if (paramBoolean)
        createAndRunDbCleanup(getActivity().getApplicationContext(), this.mAccount, local2);
      else
        local2.run();
    }
  }

  public final void loadContent()
  {
    getLoaderManager().restartLoader(2, null, this);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mQuery = paramBundle.getString("query");
      this.mDelayedQuery = paramBundle.getString("delayed_query");
      prepareLoaderUri();
      getLoaderManager().initLoader(2, null, this);
    }
    while (true)
    {
      return;
      this.mDelayedQuery = getArguments().getString("query");
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 1:
    }
    for (Loader localLoader = super.onCreateLoader(paramInt, paramBundle); ; localLoader = null)
      return localLoader;
  }

  public final void onDestroy()
  {
    super.onDestroy();
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity.isFinishing())
      createAndRunDbCleanup(localFragmentActivity.getApplicationContext(), this.mAccount, null);
  }

  public final void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    switch (paramLoader.getId())
    {
    default:
    case 2:
    case 3:
    }
    View localView;
    while (true)
    {
      return;
      super.onLoadFinished(paramLoader, paramCursor);
      if (TextUtils.equals(this.mQuery, getArguments().getString("query")))
      {
        this.mSearchViewAdapter.hideSoftInput();
        continue;
        saveScrollPosition();
        this.mInnerAdapter.setMarkPostsAsRead(false);
        this.mInnerAdapter.changeStreamCursor(paramCursor);
        checkResetAnimationState();
        this.mEndOfStream = false;
        this.mPreloadRequested = false;
        localView = getView();
        if (!this.mError)
          break;
        showEmptyView(getView(), getString(R.string.people_list_error));
        restoreScrollPosition();
        updateSpinner();
      }
    }
    if ((paramCursor != null) && (paramCursor.getCount() > 0))
    {
      showContent(localView);
      this.mEndOfStream = TextUtils.isEmpty(this.mContinuationToken);
    }
    while (true)
    {
      this.mFirstLoad = false;
      break;
      if ((this.mNewerReqId != null) || (this.mOlderReqId != null))
        showEmptyViewProgress(localView);
      else if (!TextUtils.isEmpty(this.mQuery))
      {
        if (this.mFirstLoad)
          fetchContent(true);
        else
          showEmptyView(localView, getString(R.string.no_posts));
      }
      else
        showContent(localView);
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mPostsSearchServiceListener);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showSearchView();
    this.mSearchViewAdapter = paramHostActionBar.getSearchViewAdapter();
    this.mSearchViewAdapter.setQueryHint(R.string.search_posts_hint_text);
    this.mSearchViewAdapter.addOnChangeListener(this);
    this.mSearchViewAdapter.requestFocus(true);
  }

  public final void onQueryClose()
  {
  }

  public final void onQueryTextChanged(CharSequence paramCharSequence)
  {
  }

  public final void onQueryTextSubmitted(CharSequence paramCharSequence)
  {
    String str = paramCharSequence.toString().trim();
    if (!TextUtils.equals(str, this.mQuery))
      this.mResetAnimationState = true;
    this.mQuery = str;
    doSearch();
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mPostsSearchServiceListener);
    if (this.mNewerReqId != null)
      if (!EsService.isRequestPending(this.mNewerReqId.intValue()))
      {
        ServiceResult localServiceResult2 = EsService.removeResult(this.mNewerReqId.intValue());
        this.mNewerReqId = null;
        if (!localServiceResult2.hasError())
          loadContent();
      }
    while (true)
    {
      if (this.mDelayedQuery != null)
      {
        this.mQuery = this.mDelayedQuery;
        this.mDelayedQuery = null;
        this.mSearchViewAdapter.setQueryText(this.mQuery);
        doSearch();
      }
      return;
      if ((this.mOlderReqId != null) && (!EsService.isRequestPending(this.mOlderReqId.intValue())))
      {
        ServiceResult localServiceResult1 = EsService.removeResult(this.mOlderReqId.intValue());
        this.mOlderReqId = null;
        if (!localServiceResult1.hasError())
          loadContent();
      }
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("query", this.mQuery);
    paramBundle.putString("delayed_query", this.mDelayedQuery);
  }

  protected final void prepareLoaderUri()
  {
    if (TextUtils.isEmpty(this.mQuery));
    for (this.mPostsUri = EsProvider.buildStreamUri(this.mAccount, "com.google.android.apps.plus.INVALID_SEARCH_QUERY"); ; this.mPostsUri = EsProvider.buildStreamUri(this.mAccount, SearchUtils.getSearchKey(this.mQuery)))
      return;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedPostSearchFragment
 * JD-Core Version:    0.6.2
 */