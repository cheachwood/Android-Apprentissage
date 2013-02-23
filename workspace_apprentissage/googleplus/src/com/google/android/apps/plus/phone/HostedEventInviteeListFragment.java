package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.EventInviteeListAdapter;
import com.google.android.apps.plus.fragments.EventInviteeListAdapter.OnActionListener;
import com.google.android.apps.plus.fragments.EventInviteeListLoader;
import com.google.android.apps.plus.fragments.HostedEsFragment;
import com.google.android.apps.plus.fragments.HostedEventFragment.DetailsQuery;
import com.google.android.apps.plus.fragments.ProgressFragmentDialog;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.Recyclable;
import com.google.api.services.plusi.model.PlusEvent;

public class HostedEventInviteeListFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.RecyclerListener, AdapterView.OnItemClickListener, EventInviteeListAdapter.OnActionListener
{
  private EventInviteeListAdapter mAdapter;
  private String mAuthKey;
  private Integer mDeleteAddRequestId;
  private String mEventId;
  private Integer mGetEventRequestId;
  private Integer mGetInviteesRequestId;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private Integer mInviteMoreRequestId;
  protected ListView mListView;
  private final EsServiceListener mListener = new EsServiceListener()
  {
    public final void onEventInviteComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventInviteeListFragment.this.handleInviteMoreComplete(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onEventManageGuestComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventInviteeListFragment.this.handleAddRemoveGuestCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventInviteeListFragment.this.handleGetEventCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetEventInviteesComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventInviteeListFragment.this.handleGetEventInviteesCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onRemovePeopleRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventInviteeListFragment.this.handleAddRemoveGuestCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private String mOwnerId;
  private PlusEvent mPlusEvent;
  private boolean mRefreshNeeded;

  private void handleGetEventCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if (this.mGetEventRequestId != null)
      this.mGetEventRequestId.intValue();
    if (!paramServiceResult.hasError())
      getLoaderManager().restartLoader(0, null, this);
    this.mGetEventRequestId = null;
    updateRefreshStatus();
  }

  private void handleInviteMoreComplete(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mInviteMoreRequestId == null) || (paramInt != this.mInviteMoreRequestId.intValue()));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mInviteMoreRequestId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
      else
        refresh(2);
    }
  }

  private void refresh(int paramInt)
  {
    if (((paramInt == 1) || (paramInt == 0)) && (this.mGetEventRequestId == null))
      this.mGetEventRequestId = Integer.valueOf(EsService.getEvent(getActivity(), getAccount(), this.mEventId));
    if (((paramInt == 2) || (paramInt == 0)) && (this.mGetInviteesRequestId == null))
      this.mGetInviteesRequestId = Integer.valueOf(EsService.getEventInvitees(getActivity(), getAccount(), this.mEventId, this.mAuthKey, true));
    updateRefreshStatus();
  }

  private void showProgressDialog(int paramInt)
  {
    ProgressFragmentDialog.newInstance(null, getString(paramInt), false).show(getFragmentManager(), "req_pending");
  }

  private void updateRefreshStatus()
  {
    if (getActionBar() != null)
    {
      if ((this.mGetEventRequestId == null) && (this.mGetInviteesRequestId == null))
        break label48;
      getActionBar().showProgressIndicator();
    }
    while (true)
    {
      if ((this.mGetEventRequestId == null) && (this.mGetInviteesRequestId == null))
        this.mRefreshNeeded = false;
      return;
      label48: getActionBar().hideProgressIndicator();
    }
  }

  public final OzViews getViewForLogging()
  {
    return null;
  }

  protected final void handleAddRemoveGuestCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mDeleteAddRequestId == null) || (paramInt != this.mDeleteAddRequestId.intValue()));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mDeleteAddRequestId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
    }
  }

  protected final void handleGetEventInviteesCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mGetInviteesRequestId == null) || (this.mGetInviteesRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      if (this.mGetEventRequestId == null)
        this.mRefreshNeeded = false;
      if (!paramServiceResult.hasError())
        getLoaderManager().restartLoader(1, null, this);
      this.mGetInviteesRequestId = null;
      updateRefreshStatus();
    }
  }

  protected final boolean isEmpty()
  {
    if (this.mPlusEvent == null);
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
      startActivityForResult(Intents.getEditAudienceActivityIntent(getActivity(), getAccount(), getString(R.string.event_invite_activity_title), null, 11, false, false, true, false), 0);
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1);
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 0:
        final AudienceData localAudienceData = (AudienceData)paramIntent.getParcelableExtra("audience");
        this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            HostedEventInviteeListFragment.access$400(HostedEventInviteeListFragment.this, localAudienceData);
          }
        });
      }
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new EventInviteeListAdapter(paramActivity);
    this.mAdapter.setOnActionListener(this);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mRefreshNeeded = true;
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("id"))
        this.mEventId = paramBundle.getString("id");
      if (paramBundle.containsKey("ownerid"))
        this.mOwnerId = paramBundle.getString("ownerid");
      if (paramBundle.containsKey("invitemoreid"))
        this.mInviteMoreRequestId = Integer.valueOf(paramBundle.getInt("invitemoreid"));
      if (paramBundle.containsKey("inviteesreq"))
        this.mGetInviteesRequestId = Integer.valueOf(paramBundle.getInt("inviteesreq"));
      if (paramBundle.containsKey("eventreq"))
        this.mGetEventRequestId = Integer.valueOf(paramBundle.getInt("eventreq"));
      if (paramBundle.containsKey("eventaddremovereq"))
        this.mDeleteAddRequestId = Integer.valueOf(paramBundle.getInt("eventaddremovereq"));
      if (paramBundle.containsKey("inviteesrefreshneeded"))
        this.mRefreshNeeded = paramBundle.getBoolean("inviteesrefreshneeded");
    }
    this.mAdapter.setViewerGaiaId(getAccount().getGaiaId());
    this.mAdapter.setEventOwnerId(this.mOwnerId);
    if (this.mRefreshNeeded)
      refresh();
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    final FragmentActivity localFragmentActivity = getActivity();
    Object localObject = null;
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return localObject;
      localObject = new EsCursorLoader(localFragmentActivity, EsProvider.EVENTS_ALL_URI)
      {
        public final Cursor esLoadInBackground()
        {
          return EsEventData.getEvent(localFragmentActivity, HostedEventInviteeListFragment.this.mAccount, HostedEventInviteeListFragment.this.mEventId, HostedEventFragment.DetailsQuery.PROJECTION);
        }
      };
      continue;
      localObject = new EventInviteeListLoader(getActivity(), getAccount(), this.mEventId, this.mOwnerId);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.people_list_fragment, paramViewGroup, false);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setRecyclerListener(this);
    getLoaderManager().restartLoader(0, null, this);
    getLoaderManager().restartLoader(1, null, this);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((paramView instanceof PeopleListItemView))
    {
      String str = ((PeopleListItemView)paramView).getGaiaId();
      if (str != null)
        startActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), getAccount(), str, null));
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public void onMovedToScrapHeap(View paramView)
  {
    if ((paramView instanceof Recyclable))
      ((Recyclable)paramView).onRecycle();
  }

  public final void onPause()
  {
    EsService.unregisterListener(this.mListener);
    super.onPause();
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    if (EsEventData.canInviteOthers(this.mPlusEvent, this.mAccount))
      paramHostActionBar.showActionButton(0, R.drawable.icn_events_rsvp_invite_more, R.string.event_button_invite_more_label);
    paramHostActionBar.showRefreshButton();
    if (this.mPlusEvent != null)
      paramHostActionBar.showTitle(this.mPlusEvent.name);
    updateRefreshStatus();
    super.onPrepareActionBar(paramHostActionBar);
  }

  public final void onReInviteInvitee(String paramString1, String paramString2)
  {
    showProgressDialog(R.string.event_reinviting_invitee);
    this.mDeleteAddRequestId = Integer.valueOf(EsService.manageEventGuest(getActivity(), getAccount(), this.mEventId, this.mAuthKey, false, paramString1, paramString2));
  }

  public final void onRemoveInvitee(String paramString1, String paramString2)
  {
    showProgressDialog(R.string.event_removing_invitee);
    this.mDeleteAddRequestId = Integer.valueOf(EsService.manageEventGuest(getActivity(), getAccount(), this.mEventId, this.mAuthKey, true, paramString1, paramString2));
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mListener);
    if ((this.mInviteMoreRequestId != null) && (!EsService.isRequestPending(this.mInviteMoreRequestId.intValue())))
    {
      ServiceResult localServiceResult4 = EsService.removeResult(this.mInviteMoreRequestId.intValue());
      handleInviteMoreComplete(this.mInviteMoreRequestId.intValue(), localServiceResult4);
      this.mInviteMoreRequestId = null;
    }
    if ((this.mGetInviteesRequestId != null) && (!EsService.isRequestPending(this.mGetInviteesRequestId.intValue())))
    {
      ServiceResult localServiceResult3 = EsService.removeResult(this.mGetInviteesRequestId.intValue());
      handleGetEventInviteesCallback(this.mGetInviteesRequestId.intValue(), localServiceResult3);
      this.mGetInviteesRequestId = null;
    }
    if ((this.mGetEventRequestId != null) && (!EsService.isRequestPending(this.mGetEventRequestId.intValue())))
    {
      ServiceResult localServiceResult2 = EsService.removeResult(this.mGetEventRequestId.intValue());
      handleGetEventCallback(this.mGetEventRequestId.intValue(), localServiceResult2);
      this.mGetEventRequestId = null;
    }
    if ((this.mDeleteAddRequestId != null) && (!EsService.isRequestPending(this.mDeleteAddRequestId.intValue())))
    {
      ServiceResult localServiceResult1 = EsService.removeResult(this.mDeleteAddRequestId.intValue());
      handleAddRemoveGuestCallback(this.mDeleteAddRequestId.intValue(), localServiceResult1);
      this.mDeleteAddRequestId = null;
    }
    if (this.mRefreshNeeded)
      refresh();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("id", this.mEventId);
    paramBundle.putString("ownerid", this.mOwnerId);
    if (this.mInviteMoreRequestId != null)
      paramBundle.putInt("invitemoreid", this.mInviteMoreRequestId.intValue());
    if (this.mGetInviteesRequestId != null)
      paramBundle.putInt("inviteesreq", this.mGetInviteesRequestId.intValue());
    if (this.mGetEventRequestId != null)
      paramBundle.putInt("eventreq", this.mGetEventRequestId.intValue());
    if (this.mDeleteAddRequestId != null)
      paramBundle.putInt("eventaddremovereq", this.mDeleteAddRequestId.intValue());
    paramBundle.putBoolean("inviteesrefreshneeded", this.mRefreshNeeded);
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mEventId = paramBundle.getString("event_id");
    this.mOwnerId = paramBundle.getString("owner_id");
    this.mAuthKey = paramBundle.getString("auth_key");
  }

  public final void refresh()
  {
    super.refresh();
    refresh(0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostedEventInviteeListFragment
 * JD-Core Version:    0.6.2
 */