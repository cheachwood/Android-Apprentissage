package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbSquareStream;
import com.google.android.apps.plus.content.SquareTargetData;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.HostActionBar;

public class SelectSquareCategoryFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<DbSquareStream[]>, AdapterView.OnItemClickListener
{
  private ArrayAdapter<SquareStreamInfo> mAdapter;
  private ListView mListView;
  private boolean mLoaderError;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onGetSquareComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((SelectSquareCategoryFragment.this.mNewerReqId == null) || (paramAnonymousInt != SelectSquareCategoryFragment.this.mNewerReqId.intValue()))
        return;
      SelectSquareCategoryFragment.this.mNewerReqId = null;
      if ((paramAnonymousServiceResult.hasError()) && (!SelectSquareCategoryFragment.this.mLoaderError))
        Toast.makeText(SelectSquareCategoryFragment.this.getActivity(), SelectSquareCategoryFragment.this.getString(R.string.people_list_error), 0).show();
      while (true)
      {
        SelectSquareCategoryFragment.this.updateSpinner();
        break;
        if ((SelectSquareCategoryFragment.this.getActivity() != null) && (!SelectSquareCategoryFragment.this.getActivity().isFinishing()))
          SelectSquareCategoryFragment.this.getLoaderManager().restartLoader(0, null, SelectSquareCategoryFragment.this);
      }
    }
  };
  private String mSquareId;
  private String mSquareName;
  private boolean mSquareStreamLoaderActive = true;

  private boolean isLoading()
  {
    if ((this.mAdapter == null) || (this.mAdapter.getCount() == 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void updateView(View paramView)
  {
    View localView1 = paramView.findViewById(16908298);
    View localView2 = paramView.findViewById(R.id.server_error);
    if (this.mLoaderError)
    {
      localView1.setVisibility(8);
      localView2.setVisibility(0);
      showContent(paramView);
    }
    while (true)
    {
      return;
      if (isLoading())
      {
        localView1.setVisibility(8);
        localView2.setVisibility(8);
        showEmptyViewProgress(paramView);
      }
      else if (isEmpty())
      {
        localView1.setVisibility(8);
        localView2.setVisibility(8);
        showEmptyView(paramView, getString(R.string.no_squares));
      }
      else
      {
        localView1.setVisibility(0);
        localView2.setVisibility(8);
        showContent(paramView);
      }
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PEOPLE_PICKER;
  }

  protected final boolean isEmpty()
  {
    if ((isLoading()) || (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((super.isProgressIndicatorVisible()) || (this.mSquareStreamLoaderActive));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new ArrayAdapter(paramActivity, 17367043);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getActivity().getIntent();
    this.mSquareId = localIntent.getStringExtra("square_id");
    this.mSquareName = localIntent.getStringExtra("square_name");
    getLoaderManager().initLoader(0, null, this);
  }

  public final Loader<DbSquareStream[]> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 0:
    }
    for (Object localObject = null; ; localObject = new SquareCategoryLoader(getActivity(), getAccount(), this.mSquareId))
      return localObject;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.edit_audience_fragment, paramViewGroup, false);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setFastScrollEnabled(false);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    SquareStreamInfo localSquareStreamInfo = (SquareStreamInfo)this.mAdapter.getItem(paramInt);
    String str1 = localSquareStreamInfo.getStreamId();
    String str2 = localSquareStreamInfo.getStreamName();
    AudienceData localAudienceData = new AudienceData(new SquareTargetData(this.mSquareId, this.mSquareName, str1, str2));
    Intent localIntent = new Intent();
    localIntent.putExtra("audience", localAudienceData);
    FragmentActivity localFragmentActivity = getActivity();
    localFragmentActivity.setResult(-1, localIntent);
    localFragmentActivity.finish();
  }

  public final void onLoaderReset(Loader<DbSquareStream[]> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.refresh)
      refresh();
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showTitle(getActivity().getIntent().getStringExtra("title"));
    paramHostActionBar.showRefreshButton();
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    updateView(getView());
  }

  public final void refresh()
  {
    super.refresh();
    if ((this.mNewerReqId == null) && (getActivity() != null))
      this.mNewerReqId = Integer.valueOf(EsService.getSquare(getActivity(), this.mAccount, this.mSquareId));
    updateSpinner();
  }

  private static final class SquareStreamInfo
  {
    private final String mStreamId;
    private final String mStreamName;

    public SquareStreamInfo(String paramString1, String paramString2)
    {
      this.mStreamId = paramString2;
      this.mStreamName = paramString1;
    }

    public final String getStreamId()
    {
      return this.mStreamId;
    }

    public final String getStreamName()
    {
      return this.mStreamName;
    }

    public final String toString()
    {
      return this.mStreamName;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SelectSquareCategoryFragment
 * JD-Core Version:    0.6.2
 */