package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.google.android.apps.plus.R.array;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EventCardAdapter;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.EventDestinationCardView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.api.services.plusi.model.PlusEvent;

public class HostedEventListFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, ItemClickListener
{
  private EventCardAdapter mAdapter;
  private int mCurrentMode = 0;
  private int mCurrentSpinnerPosition;
  private boolean mDataPresent;
  private ColumnGridView mGridView;
  private final Handler mHandler = new Handler();
  private boolean mInitialLoadDone;
  private final EsServiceListener mListener = new EsServiceListener()
  {
    public final void onEventHomeRequestComplete$b5e9bbb(int paramAnonymousInt)
    {
      if ((HostedEventListFragment.this.mNewerReqId == null) || (paramAnonymousInt != HostedEventListFragment.this.mNewerReqId.intValue()));
      while (true)
      {
        return;
        HostedEventListFragment.this.mNewerReqId = null;
        HostedEventListFragment.access$002(HostedEventListFragment.this, false);
        HostedEventListFragment.this.getActionBar().hideProgressIndicator();
        HostedEventListFragment.this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            if ((HostedEventListFragment.this.getActivity() != null) && (!HostedEventListFragment.this.getActivity().isFinishing()))
              HostedEventListFragment.this.getLoaderManager().restartLoader(0, null, HostedEventListFragment.this);
          }
        });
      }
    }
  };
  private boolean mRefreshNeeded;
  private ArrayAdapter<String> mSpinnerAdapter;

  private void fetchData()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((this.mNewerReqId == null) && (localFragmentActivity != null))
    {
      if (!this.mDataPresent)
        showEmptyViewProgress(getView(), getString(R.string.loading));
      getActionBar().showProgressIndicator();
      this.mNewerReqId = EsService.getEventHome(localFragmentActivity, this.mAccount);
    }
  }

  private void setCreationVisibility(int paramInt)
  {
    getView().findViewById(R.id.createButton).setVisibility(paramInt);
    getView().findViewById(R.id.createText).setVisibility(paramInt);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.MY_EVENTS;
  }

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    }
    while (true)
    {
      return;
      startActivity(Intents.getCreateEventActivityIntent(getActivity().getApplicationContext(), this.mAccount));
    }
  }

  public void onClick(View paramView)
  {
    if ((paramView instanceof EventDestinationCardView))
    {
      PlusEvent localPlusEvent = ((EventDestinationCardView)paramView).getEvent();
      if (localPlusEvent != null)
      {
        String str1 = localPlusEvent.id;
        String str2 = localPlusEvent.creatorObfuscatedId;
        startActivity(Intents.getHostedEventIntent(getActivity(), this.mAccount, str1, str2, null));
      }
    }
    while (true)
    {
      return;
      if (paramView.getId() == R.id.createButton)
        startActivity(Intents.getCreateEventActivityIntent(getActivity().getApplicationContext(), this.mAccount));
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mRefreshNeeded = paramBundle.getBoolean("events_refresh", false);
      this.mInitialLoadDone = paramBundle.getBoolean("events_initialload", false);
      this.mCurrentMode = paramBundle.getInt("events_currentmode", 0);
      this.mDataPresent = paramBundle.getBoolean("events_datapresent", false);
    }
    while (true)
    {
      getLoaderManager().initLoader(0, null, this);
      return;
      this.mRefreshNeeded = getArguments().getBoolean("refresh", false);
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new EventsLoader(getActivity(), this.mAccount, this.mCurrentMode);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.hosted_events_fragment, paramViewGroup, false);
    this.mGridView = ((ColumnGridView)localView.findViewById(R.id.grid));
    this.mAdapter = new EventCardAdapter(getActivity(), this.mAccount, null, this, this, this.mGridView);
    this.mGridView.setAdapter(this.mAdapter);
    setupEmptyView(localView, R.string.no_events);
    Button localButton = (Button)localView.findViewById(R.id.createButton);
    localButton.setClickable(true);
    localButton.setOnClickListener(this);
    return localView;
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.help)
    {
      String str = getResources().getString(R.string.url_param_help_events);
      startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(getActivity(), str)));
    }
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  public final void onPause()
  {
    EsService.unregisterListener(this.mListener);
    super.onPause();
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    this.mSpinnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item);
    this.mSpinnerAdapter.setDropDownViewResource(17367049);
    CharSequence[] arrayOfCharSequence = getResources().getTextArray(R.array.event_spinner_items);
    int i = 0;
    int j = arrayOfCharSequence.length;
    while (i < j)
    {
      this.mSpinnerAdapter.add(arrayOfCharSequence[i].toString());
      i++;
    }
    if ((this.mCurrentMode != 0) || (this.mDataPresent))
      paramHostActionBar.showPrimarySpinner(this.mSpinnerAdapter, this.mCurrentSpinnerPosition);
    paramHostActionBar.showActionButton(0, R.drawable.icn_events_create_event, R.string.event_button_add_event_label);
    paramHostActionBar.showRefreshButton();
    if (this.mNewerReqId != null)
      paramHostActionBar.showProgressIndicator();
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if (this.mCurrentSpinnerPosition != paramInt)
    {
      this.mCurrentSpinnerPosition = paramInt;
      if (this.mCurrentSpinnerPosition != 0)
        break label46;
    }
    label46: for (int i = 2; ; i = 1)
    {
      this.mCurrentMode = i;
      this.mGridView.setSelectionToTop();
      getLoaderManager().restartLoader(0, null, this);
      return;
    }
  }

  public final void onResume()
  {
    EsService.registerListener(this.mListener);
    super.onResume();
    if (this.mRefreshNeeded)
      fetchData();
  }

  protected final void onResumeContentFetched(View paramView)
  {
    super.onResumeContentFetched(paramView);
    this.mRefreshNeeded = false;
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putBoolean("events_refresh", this.mRefreshNeeded);
    paramBundle.putBoolean("events_initialload", this.mInitialLoadDone);
    paramBundle.putInt("events_currentmode", this.mCurrentMode);
    paramBundle.putBoolean("events_datapresent", this.mDataPresent);
    super.onSaveInstanceState(paramBundle);
  }

  public final void onSpanClick(URLSpan paramURLSpan)
  {
  }

  public final void onUserImageClick(String paramString1, String paramString2)
  {
    startActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), this.mAccount, paramString1, null));
  }

  public final void refresh()
  {
    super.refresh();
    setCreationVisibility(8);
    fetchData();
  }

  protected final void showContent(View paramView)
  {
    super.showContent(paramView);
    this.mGridView.setVisibility(0);
  }

  protected final void showEmptyView(View paramView, String paramString)
  {
    super.showEmptyView(paramView, paramString);
    this.mGridView.setVisibility(8);
  }

  static final class EventsLoader extends EsCursorLoader
  {
    private final EsAccount mAccount;
    private int mMode;

    public EventsLoader(Context paramContext, EsAccount paramEsAccount, int paramInt)
    {
      super(EsProvider.EVENTS_ALL_URI);
      this.mMode = paramInt;
      this.mAccount = paramEsAccount;
    }

    public final Cursor esLoadInBackground()
    {
      int i = 1;
      Object localObject;
      switch (this.mMode)
      {
      default:
        localObject = null;
      case 2:
      case 0:
      case 1:
      }
      while (true)
      {
        return localObject;
        i = 0;
        localObject = EsEventData.getMyCurrentEvents(getContext(), this.mAccount, System.currentTimeMillis(), HostedEventListFragment.Query.PROJECTION);
        if ((i == 0) || ((localObject != null) && (((Cursor)localObject).getCount() > 0)))
        {
          this.mMode = 2;
        }
        else
        {
          Cursor localCursor = EsEventData.getMyPastEvents(getContext(), this.mAccount, System.currentTimeMillis(), HostedEventListFragment.Query.PROJECTION);
          if ((localCursor != null) && (localCursor.getCount() > 0))
            this.mMode = 1;
          localObject = localCursor;
        }
      }
    }

    public final int getCurrentMode()
    {
      return this.mMode;
    }
  }

  public static abstract interface Query
  {
    public static final String[] PROJECTION = { "_id", "event_data" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedEventListFragment
 * JD-Core Version:    0.6.2
 */