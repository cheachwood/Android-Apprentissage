package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.google.android.apps.plus.R.array;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.EsSquaresData;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.SquareCardAdapter;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.SquareListItemView.OnItemClickListener;

public class HostedSquareListFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AlertFragmentDialog.AlertDialogListener, SquareListItemView.OnItemClickListener
{
  private SquareCardAdapter mAdapter;
  private Context mContext;
  private int mCurrentMode = 0;
  private int mCurrentSpinnerPosition;
  private boolean mDataPresent;
  private EsMatrixCursor mDescriptionHeaderCursor;
  private String mErrorText;
  private ColumnGridView mGridView;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private final EsServiceListener mListener = new EsServiceListener()
  {
    public final void onGetSquaresComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((HostedSquareListFragment.this.mNewerReqId == null) || (paramAnonymousInt != HostedSquareListFragment.this.mNewerReqId.intValue()))
        return;
      HostedSquareListFragment.this.mNewerReqId = null;
      HostedSquareListFragment.access$002(HostedSquareListFragment.this, false);
      if (paramAnonymousServiceResult.hasError())
        HostedSquareListFragment.this.setError(HostedSquareListFragment.this.getString(R.string.squares_load_error));
      while (true)
      {
        HostedSquareListFragment.this.updateSpinner();
        HostedSquareListFragment.this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            if ((HostedSquareListFragment.this.getActivity() != null) && (!HostedSquareListFragment.this.getActivity().isFinishing()))
              HostedSquareListFragment.this.getLoaderManager().restartLoader(0, null, HostedSquareListFragment.this);
          }
        });
        break;
        HostedSquareListFragment.this.clearError();
      }
    }
  };
  private boolean mRefreshNeeded;
  private ArrayAdapter<String> mSpinnerAdapter;
  private boolean mSquaresLoaderActive = true;

  private void fetchData()
  {
    if ((this.mNewerReqId == null) && (this.mContext != null))
    {
      if (!this.mDataPresent)
        showEmptyViewProgress(getView(), getString(R.string.loading));
      this.mNewerReqId = Integer.valueOf(EsService.getSquares(this.mContext, this.mAccount));
    }
    updateSpinner();
  }

  private boolean hasError()
  {
    if (this.mErrorText != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final void clearError()
  {
    this.mErrorText = null;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.SQUARE_HOME;
  }

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((super.isProgressIndicatorVisible()) || (this.mSquaresLoaderActive));
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
      startActivity(Intents.getSquareSearchActivityIntent(this.mContext, this.mAccount));
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mContext = paramActivity;
  }

  public final void onClick(String paramString)
  {
    startActivity(Intents.getSquareStreamActivityIntent(getActivity(), this.mAccount, paramString, null, null));
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mRefreshNeeded = paramBundle.getBoolean("squares_refresh", false);
      this.mCurrentMode = paramBundle.getInt("squares_currentmode", 0);
      this.mDataPresent = paramBundle.getBoolean("squares_datapresent", false);
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
    this.mSquaresLoaderActive = true;
    return new SquaresLoader(this.mContext, this.mAccount, this.mCurrentMode);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.hosted_squares_fragment, paramViewGroup, false);
    this.mGridView = ((ColumnGridView)localView.findViewById(R.id.grid));
    this.mAdapter = new SquareCardAdapter(this.mContext, this.mAccount, this, this.mGridView);
    this.mGridView.setAdapter(this.mAdapter);
    setupEmptyView(localView, R.string.no_squares);
    return localView;
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if ("dismiss_invitation".equals(paramString))
    {
      String str = paramBundle.getString("square_id");
      FragmentActivity localFragmentActivity = getActivity();
      EsService.declineSquareInvitation(localFragmentActivity, this.mAccount, str);
      Bundle localBundle = EsAnalyticsData.createExtras("extra_square_id", str);
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.SQUARE_DECLINE_INVITATION, OzViews.SQUARE_HOME, localBundle);
    }
  }

  public final void onInvitationDismissed(String paramString)
  {
    AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(null, getString(R.string.square_dismiss_invitation_text), getString(R.string.square_dialog_decline_button), getString(R.string.cancel));
    localAlertFragmentDialog.setTargetFragment(this, 0);
    Bundle localBundle = localAlertFragmentDialog.getArguments();
    if (localBundle == null)
      localBundle = new Bundle();
    localBundle.putString("square_id", paramString);
    localAlertFragmentDialog.setArguments(localBundle);
    localAlertFragmentDialog.show(getFragmentManager(), "dismiss_invitation");
  }

  public final void onInviterImageClick(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      String str = "g:" + paramString;
      startActivity(Intents.getProfileActivityIntent(this.mContext, this.mAccount, str, null, 0));
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    int i = paramMenuItem.getItemId();
    if (i == R.id.refresh)
      refresh();
    while (true)
    {
      return bool;
      if (i == R.id.help)
      {
        String str = getResources().getString(R.string.url_param_help_squares);
        startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(this.mContext, str)));
      }
      else
      {
        bool = super.onOptionsItemSelected(paramMenuItem);
      }
    }
  }

  public final void onPause()
  {
    EsService.unregisterListener(this.mListener);
    super.onPause();
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    this.mSpinnerAdapter = new ArrayAdapter(this.mContext, R.layout.simple_spinner_item);
    this.mSpinnerAdapter.setDropDownViewResource(17367049);
    CharSequence[] arrayOfCharSequence = getResources().getTextArray(R.array.square_list_spinner_items);
    int i = 0;
    int j = arrayOfCharSequence.length;
    while (i < j)
    {
      this.mSpinnerAdapter.add(arrayOfCharSequence[i].toString());
      i++;
    }
    if ((this.mCurrentMode != 0) || (this.mDataPresent))
      paramHostActionBar.showPrimarySpinner(this.mSpinnerAdapter, this.mCurrentSpinnerPosition);
    paramHostActionBar.showRefreshButtonIfRoom();
    paramHostActionBar.showActionButton(0, R.drawable.ic_menu_search_holo_light, R.string.menu_search);
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    boolean bool1 = getActionBar().isRefreshButtonVisible();
    MenuItem localMenuItem = paramMenu.findItem(R.id.refresh);
    if (!bool1);
    for (boolean bool2 = true; ; bool2 = false)
    {
      localMenuItem.setVisible(bool2);
      return;
    }
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if (this.mCurrentSpinnerPosition != paramInt)
    {
      this.mCurrentSpinnerPosition = paramInt;
      switch (paramInt)
      {
      default:
        this.mCurrentMode = 3;
      case 1:
      case 0:
      }
    }
    while (true)
    {
      this.mGridView.setSelectionToTop();
      getLoaderManager().restartLoader(0, null, this);
      return;
      this.mCurrentMode = 2;
      continue;
      this.mCurrentMode = 1;
    }
  }

  public final void onResume()
  {
    EsService.registerListener(this.mListener);
    super.onResume();
    if (!Property.ENABLE_SQUARES.getBoolean())
      getActivity().finish();
    if (this.mRefreshNeeded)
      fetchData();
    updateSpinner();
  }

  protected final void onResumeContentFetched(View paramView)
  {
    super.onResumeContentFetched(paramView);
    this.mRefreshNeeded = false;
    this.mErrorText = null;
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putBoolean("squares_refresh", this.mRefreshNeeded);
    paramBundle.putInt("squares_currentmode", this.mCurrentMode);
    paramBundle.putBoolean("squares_datapresent", this.mDataPresent);
    super.onSaveInstanceState(paramBundle);
  }

  public final void refresh()
  {
    super.refresh();
    fetchData();
  }

  protected final void setError(String paramString)
  {
    this.mErrorText = paramString;
    if ((this.mDataPresent) && (this.mErrorText != null))
      Toast.makeText(this.mContext, this.mErrorText, 0).show();
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

  public static abstract interface Query
  {
    public static final String[] INVITATION_PROJECTION = { "_id", "square_id", "square_name", "photo_url", "post_visibility", "member_count", "membership_status", "unread_count", "inviter_gaia_id", "inviter_name", "inviter_photo_url" };
    public static final String[] PROJECTION = { "_id", "square_id", "square_name", "photo_url", "post_visibility", "member_count", "membership_status", "unread_count" };
  }

  static final class SquaresLoader extends EsCursorLoader
  {
    private final EsAccount mAccount;
    private boolean mIsDataStale;
    private int mMode;

    public SquaresLoader(Context paramContext, EsAccount paramEsAccount, int paramInt)
    {
      super(EsProvider.SQUARES_URI);
      this.mMode = paramInt;
      this.mAccount = paramEsAccount;
    }

    public final Cursor esLoadInBackground()
    {
      long l = EsSquaresData.queryLastSquaresSyncTimestamp(getContext(), this.mAccount);
      boolean bool;
      int j;
      Object localObject;
      if (System.currentTimeMillis() - l > 900000L)
      {
        bool = true;
        this.mIsDataStale = bool;
        int i = this.mMode;
        j = 0;
        switch (i)
        {
        default:
          localObject = null;
        case 0:
        case 1:
        case 2:
        case 3:
        }
      }
      while (true)
      {
        return localObject;
        bool = false;
        break;
        j = 1;
        localObject = EsSquaresData.getInvitedSquares(getContext(), this.mAccount, HostedSquareListFragment.Query.INVITATION_PROJECTION, null);
        if ((j == 0) || ((localObject != null) && (((Cursor)localObject).getCount() > 0)))
        {
          this.mMode = 1;
        }
        else
        {
          Cursor localCursor2 = EsSquaresData.getJoinedSquares(getContext(), this.mAccount, HostedSquareListFragment.Query.PROJECTION, null);
          if ((j == 0) || ((localCursor2 != null) && (localCursor2.getCount() > 0)))
          {
            this.mMode = 2;
            localObject = localCursor2;
          }
          else
          {
            Cursor localCursor1 = EsSquaresData.getSuggestedSquares(getContext(), this.mAccount, HostedSquareListFragment.Query.PROJECTION, null);
            if ((j == 0) || ((localCursor1 != null) && (localCursor1.getCount() > 0)))
              this.mMode = 3;
            localObject = localCursor1;
          }
        }
      }
    }

    public final int getCurrentMode()
    {
      return this.mMode;
    }

    public final boolean isDataStale()
    {
      return this.mIsDataStale;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedSquareListFragment
 * JD-Core Version:    0.6.2
 */