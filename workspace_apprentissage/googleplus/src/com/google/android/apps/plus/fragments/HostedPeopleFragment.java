package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.service.CircleMembershipManager;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.SearchViewAdapter;
import com.google.android.apps.plus.views.SearchViewAdapter.OnQueryChangeListener;
import com.google.android.apps.plus.views.SuggestionGridView;
import com.google.android.apps.plus.views.SuggestionGridView.ScrollPositions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HostedPeopleFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AlertFragmentDialog.AlertDialogListener, CirclePropertiesFragmentDialog.CirclePropertiesListener, PeopleSearchAdapter.SearchListAdapterListener, SuggestionGridAdapter.SuggestionGridAdapterListener, UnblockPersonDialog.PersonUnblocker, SearchViewAdapter.OnQueryChangeListener
{
  private static final String[] CIRCLES_PROJECTION = { "circle_id", "circle_name", "contact_count", "type", "semantic_hints" };
  protected static ScreenMetrics sScreenMetrics;
  private PeopleSearchGridAdapter mAdapter;
  private Cursor mCircleMembers;
  protected String mCircleName;
  private CircleSpinnerAdapter mCircleSpinnerAdapter;
  private Cursor mCirclesCursor;
  private int mCurrentSpinnerPosition = -1;
  private boolean mDataLoaded;
  protected Integer mDeleteCircleRequestId;
  private ColumnGridView mGridView;
  private final Handler mHandler = new Handler();
  private boolean mIsNew;
  protected Integer mNewCircleRequestId;
  protected Integer mPendingRequestId;
  private CircleSpinnerAdapter mPrimarySpinnerAdapter;
  private boolean mRefreshSuggestedPeople;
  private int mScrollPosition = -1;
  private boolean mSearchMode;
  private String mSelectedCircleId;
  private int mSelectedViewType = 0;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onCreateCircleRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPeopleFragment.this.handleNewCircleCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onDeleteCirclesRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPeopleFragment.this.handleDeleteCircleCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onModifyCirclePropertiesRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPeopleFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSetBlockedRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPeopleFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSetCircleMembershipComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedPeopleFragment.access$000(HostedPeopleFragment.this, paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private ArrayList<String> mShownPersonIds = new ArrayList();
  private SuggestionGridAdapter mSuggestionAdapter;
  private SuggestionGridView mSuggestionGridView;
  private SuggestionGridView.ScrollPositions mSuggestionScrollPositions;
  private ScrollView mSuggestionScrollView;
  private boolean mViewingAsPlusPage;

  public HostedPeopleFragment()
  {
    this(false);
  }

  public HostedPeopleFragment(boolean paramBoolean)
  {
    this.mIsNew = paramBoolean;
  }

  private void changeCircleMembers(Cursor paramCursor)
  {
    int i = 1;
    if (this.mSelectedViewType == 0)
    {
      this.mSuggestionAdapter.swapCursor(paramCursor);
      if ((paramCursor != null) && (this.mSuggestionGridView != null) && (this.mSuggestionScrollPositions != null))
      {
        this.mSuggestionGridView.setScrollPositions(this.mSuggestionScrollPositions);
        this.mSuggestionScrollPositions = null;
      }
      return;
    }
    PeopleSearchGridAdapter localPeopleSearchGridAdapter = this.mAdapter;
    if ((this.mSelectedViewType == i) || (this.mSelectedViewType == 2));
    while (true)
    {
      localPeopleSearchGridAdapter.changeCircleMembers$2c8bde3e(paramCursor, i);
      break;
      int j = 0;
    }
  }

  private void dismissProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
  }

  private static OzViews getLoggingViewFromType(int paramInt)
  {
    OzViews localOzViews;
    switch (paramInt)
    {
    default:
      localOzViews = OzViews.UNKNOWN;
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return localOzViews;
      localOzViews = OzViews.PEOPLE_SEARCH;
      continue;
      localOzViews = OzViews.PEOPLE_IN_CIRCLES;
      continue;
      localOzViews = OzViews.PEOPLE_BLOCKED;
    }
  }

  private void handleDeleteCircleCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mDeleteCircleRequestId == null) || (paramInt != this.mDeleteCircleRequestId.intValue()));
    while (true)
    {
      return;
      dismissProgressDialog();
      this.mDeleteCircleRequestId = null;
      if ((paramServiceResult == null) || (!paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.toast_circle_deleted, 0).show();
      else
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
    }
  }

  private void handleNewCircleCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mNewCircleRequestId == null) || (paramInt != this.mNewCircleRequestId.intValue()));
    while (true)
    {
      return;
      dismissProgressDialog();
      this.mNewCircleRequestId = null;
      if ((paramServiceResult == null) || (!paramServiceResult.hasError()))
      {
        int i = R.string.toast_new_circle_created;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.mCircleName;
        String str = getString(i, arrayOfObject);
        Toast.makeText(getActivity(), str, 0).show();
      }
      else
      {
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
      }
    }
  }

  private void setSearchMode(boolean paramBoolean)
  {
    if (this.mSearchMode == paramBoolean)
      return;
    this.mSearchMode = paramBoolean;
    this.mAdapter.setQueryString(null);
    if (paramBoolean)
    {
      this.mAdapter.changeCircleMembers$2c8bde3e(null, true);
      getActionBar().getSearchViewAdapter().setQueryText(null);
    }
    while (true)
    {
      invalidateActionBar();
      updateView(getView());
      break;
      changeCircleMembers(this.mCircleMembers);
    }
  }

  private void showCircleMembershipDialog(String paramString1, String paramString2)
  {
    startActivityForResult(Intents.getCircleMembershipActivityIntent(getActivity(), this.mAccount, paramString1, paramString2, true), 0);
  }

  private void showProgressDialog(int paramInt)
  {
    ProgressFragmentDialog.newInstance(null, getString(paramInt), false).show(getFragmentManager(), "req_pending");
  }

  private void updateView(View paramView)
  {
    if (this.mSearchMode)
    {
      this.mGridView.setVisibility(0);
      this.mSuggestionScrollView.setVisibility(8);
      showContent(paramView);
    }
    while (true)
    {
      return;
      switch (this.mSelectedViewType)
      {
      default:
        this.mSuggestionScrollView.setVisibility(8);
        if (!this.mDataLoaded)
        {
          this.mGridView.setVisibility(8);
          showEmptyViewProgress(paramView, getString(R.string.loading));
        }
        break;
      case 0:
        if (!this.mDataLoaded)
        {
          this.mGridView.setVisibility(8);
          this.mSuggestionScrollView.setVisibility(8);
          showEmptyViewProgress(paramView, getString(R.string.loading_friend_suggestions));
        }
        else
        {
          this.mGridView.setVisibility(8);
          this.mSuggestionScrollView.setVisibility(0);
          showContent(paramView);
          continue;
          if (this.mCircleMembers == null)
          {
            this.mGridView.setVisibility(8);
          }
          else if (this.mCircleMembers.getCount() == 0)
          {
            this.mGridView.setVisibility(8);
            setupEmptyView(paramView, R.string.empty_circle);
            showEmptyView(paramView, getString(R.string.empty_circle));
          }
          else
          {
            this.mGridView.setVisibility(0);
            showContent(paramView);
          }
        }
        break;
      }
    }
  }

  public final void doDeleteCircle()
  {
    showProgressDialog(R.string.delete_circle_operation_pending);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.mSelectedCircleId);
    this.mDeleteCircleRequestId = EsService.deleteCircles(getActivity(), this.mAccount, localArrayList);
  }

  public final OzViews getViewForLogging()
  {
    return getLoggingViewFromType(this.mSelectedViewType);
  }

  protected final void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (paramInt != this.mPendingRequestId.intValue()));
    while (true)
    {
      return;
      dismissProgressDialog();
      this.mPendingRequestId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
    }
  }

  protected final boolean isEmpty()
  {
    boolean bool1;
    if ((!this.mDataLoaded) || (this.mCirclesCursor == null))
      bool1 = true;
    while (true)
    {
      return bool1;
      boolean bool2 = this.mSearchMode;
      bool1 = false;
      if (!bool2)
        switch (this.mSelectedViewType)
        {
        default:
          int i = this.mAdapter.getCount();
          bool1 = false;
          if (i == 0)
            bool1 = true;
          break;
        case 0:
          bool1 = this.mSuggestionAdapter.isEmpty();
        }
    }
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
      setSearchMode(true);
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 0))
    {
      final String str1 = paramIntent.getStringExtra("person_id");
      final String str2 = paramIntent.getStringExtra("display_name");
      final ArrayList localArrayList1 = paramIntent.getExtras().getStringArrayList("original_circle_ids");
      final ArrayList localArrayList2 = paramIntent.getExtras().getStringArrayList("selected_circle_ids");
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          HostedPeopleFragment.this.setCircleMembership(str1, str2, localArrayList1, localArrayList2);
        }
      });
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public final void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (this.mSelectedViewType != 2)
    {
      String str = EsPeopleData.getDefaultCircleId(getActivity(), this.mCirclesCursor, paramBoolean);
      if (str != null)
      {
        EsService.addPersonToCircle(getActivity(), this.mAccount, paramString1, paramString2, str);
        if (!paramString1.startsWith("g:"))
          setSearchMode(false);
      }
    }
    while (true)
    {
      return;
      showCircleMembershipDialog(paramString1, paramString2);
    }
  }

  public final boolean onBackPressed()
  {
    boolean bool1 = this.mSearchMode;
    boolean bool2 = false;
    if (bool1)
    {
      setSearchMode(false);
      bool2 = true;
    }
    return bool2;
  }

  public final void onChangeCirclesAction(String paramString1, String paramString2)
  {
    showCircleMembershipDialog(paramString1, paramString2);
  }

  public final void onCirclePropertiesChange(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString2));
    while (true)
    {
      return;
      String str = paramString2.trim();
      if (this.mAdapter != null)
      {
        int i = this.mPrimarySpinnerAdapter.getCount();
        for (int j = 0; ; j++)
        {
          int k = 0;
          if (j < i)
          {
            CircleSpinnerAdapter.CircleSpinnerInfo localCircleSpinnerInfo = (CircleSpinnerAdapter.CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(j);
            if ((localCircleSpinnerInfo.id != null) && (localCircleSpinnerInfo.circleType != 10) && (str.equalsIgnoreCase(localCircleSpinnerInfo.title)) && (!TextUtils.equals(paramString1, localCircleSpinnerInfo.id)))
              k = 1;
          }
          else
          {
            if (k == 0)
              break label131;
            Toast.makeText(getActivity(), R.string.toast_circle_already_exists, 0).show();
            break;
          }
        }
      }
      label131: this.mCircleName = str;
      if (paramString1 == null)
      {
        showProgressDialog(R.string.new_circle_operation_pending);
        this.mNewCircleRequestId = EsService.createCircle(getActivity(), this.mAccount, str, paramBoolean);
      }
      else
      {
        showProgressDialog(R.string.circle_properties_operation_pending);
        this.mPendingRequestId = EsService.modifyCircleProperties(getActivity(), this.mAccount, paramString1, str, paramBoolean);
      }
    }
  }

  public final void onCircleSelected(String paramString, CircleData paramCircleData)
  {
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mRefreshSuggestedPeople = this.mIsNew;
    this.mViewingAsPlusPage = this.mAccount.isPlusPage();
    if ((this.mViewingAsPlusPage) && (this.mSelectedViewType == 0))
      this.mSelectedViewType = 1;
    if (paramBundle != null)
    {
      this.mSelectedCircleId = paramBundle.getString("selected_circle_id");
      this.mSelectedViewType = paramBundle.getInt("selected_view_type");
      if (!this.mIsNew)
        this.mSearchMode = paramBundle.getBoolean("search_mode");
      this.mShownPersonIds = paramBundle.getStringArrayList("shown_persons");
      if (paramBundle.containsKey("request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
      if (paramBundle.containsKey("new_circle_request_id"))
        this.mNewCircleRequestId = Integer.valueOf(paramBundle.getInt("new_circle_request_id"));
      if (paramBundle.containsKey("delete_circle_request_id"))
        this.mDeleteCircleRequestId = Integer.valueOf(paramBundle.getInt("delete_circle_request_id"));
      this.mCircleName = paramBundle.getString("new_circle_name");
      this.mScrollPosition = paramBundle.getInt("scrollPos");
      this.mSuggestionScrollPositions = ((SuggestionGridView.ScrollPositions)paramBundle.getParcelable("scrollPositions"));
    }
    this.mCircleSpinnerAdapter = new CircleSpinnerAdapter(getActivity());
    this.mCircleSpinnerAdapter.setNotifyOnChange(false);
    LoaderManager localLoaderManager = getLoaderManager();
    this.mAdapter = new PeopleSearchGridAdapter(getActivity(), getFragmentManager(), localLoaderManager, this.mAccount);
    this.mAdapter.setCircleUsageType(-1);
    this.mAdapter.setPublicProfileSearchEnabled(true);
    this.mAdapter.setIncludePeopleInCircles(true);
    this.mAdapter.setIncludePlusPages(true);
    PeopleSearchGridAdapter localPeopleSearchGridAdapter = this.mAdapter;
    if (!this.mViewingAsPlusPage);
    for (boolean bool = true; ; bool = false)
    {
      localPeopleSearchGridAdapter.setAddToCirclesActionEnabled(bool);
      this.mAdapter.setCircleSpinnerAdapter(this.mCircleSpinnerAdapter);
      this.mAdapter.setListener(this);
      this.mSuggestionAdapter = new SuggestionGridAdapter(getActivity(), localLoaderManager, this.mAccount, 777);
      this.mSuggestionAdapter.setCircleSpinnerAdapter(this.mCircleSpinnerAdapter);
      this.mSuggestionAdapter.setListener(this);
      getLoaderManager().initLoader(1, null, this);
      getLoaderManager().initLoader(2, null, this);
      return;
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 1:
    case 2:
    }
    while (true)
    {
      return localObject;
      if (this.mSelectedViewType == 2);
      for (int i = 12; ; i = 1)
      {
        localObject = new CircleListLoader(getActivity(), this.mAccount, i, CIRCLES_PROJECTION);
        break;
      }
      switch (this.mSelectedViewType)
      {
      default:
        break;
      case 0:
        localObject = new SuggestedPeopleListLoader(getActivity(), this.mAccount, SuggestionGridAdapter.PROJECTION, this.mRefreshSuggestedPeople);
        break;
      case 1:
        localObject = new PeopleListLoader(getActivity(), this.mAccount, PeopleSearchGridAdapter.PEOPLE_PROJECTION, this.mSelectedCircleId);
        break;
      case 2:
        localObject = new BlockedPeopleListLoader(getActivity(), this.mAccount, PeopleSearchGridAdapter.PEOPLE_PROJECTION, this.mShownPersonIds);
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.hosted_people_fragment, paramViewGroup, false);
    this.mGridView = ((ColumnGridView)localView.findViewById(R.id.grid));
    this.mGridView.setAdapter(this.mAdapter);
    this.mSuggestionScrollView = ((ScrollView)localView.findViewById(R.id.suggestion_scroll_view));
    this.mSuggestionGridView = ((SuggestionGridView)localView.findViewById(R.id.suggestion_grid));
    this.mSuggestionGridView.setAdapter(this.mSuggestionAdapter);
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
      this.mGridView.setMinColumnWidth(localResources.getDimensionPixelSize(R.dimen.person_card_min_height));
      this.mGridView.setItemMargin(sScreenMetrics.itemMargin);
      this.mGridView.setPadding(sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin);
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
  }

  public final void onDismissSuggestionAction(String paramString1, String paramString2)
  {
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(paramString1);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(paramString2);
    EsService.dismissSuggestedPeople(getActivity(), this.mAccount, "ANDROID_PEOPLE_SUGGESTIONS_PAGE", localArrayList1, localArrayList2);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = 1;
    int k = paramMenuItem.getItemId();
    if (k == R.id.delete_circle)
      if (this.mSelectedCircleId != null)
      {
        DeleteCircleConfirmationDialog localDeleteCircleConfirmationDialog = new DeleteCircleConfirmationDialog();
        localDeleteCircleConfirmationDialog.setTargetFragment(this, i);
        localDeleteCircleConfirmationDialog.show(getFragmentManager(), "delete_circle_conf");
      }
    while (true)
    {
      return i;
      if (k == R.id.circle_settings)
      {
        if ((this.mCirclesCursor != null) && (!this.mCirclesCursor.isClosed()) && (this.mSelectedCircleId != null))
        {
          label98: int m;
          label144: String str2;
          String str3;
          if (this.mCirclesCursor.moveToFirst())
            if (TextUtils.equals(this.mCirclesCursor.getString(0), this.mSelectedCircleId))
            {
              this.mCircleName = this.mCirclesCursor.getString(i);
              m = this.mCirclesCursor.getInt(4);
              if (m == -1)
                break label227;
              getActivity();
              str2 = this.mSelectedCircleId;
              str3 = this.mCircleName;
              if ((m & 0x40) != 0)
                break label229;
            }
          label227: label229: int i1;
          for (int n = i; ; i1 = 0)
          {
            CirclePropertiesFragmentDialog localCirclePropertiesFragmentDialog = CirclePropertiesFragmentDialog.newInstance$50fd8769(str2, str3, n);
            localCirclePropertiesFragmentDialog.setTargetFragment(this, 0);
            localCirclePropertiesFragmentDialog.show(getFragmentManager(), "circle_settings");
            break;
            if (this.mCirclesCursor.moveToNext())
              break label98;
            m = -1;
            break label144;
            break;
          }
        }
      }
      else if (k == R.id.help)
      {
        String str1 = getResources().getString(R.string.url_param_help_circles);
        startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(getActivity(), str1)));
      }
      else
      {
        int j = 0;
      }
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
    CircleMembershipManager.onPeopleListVisibilityChange(false);
  }

  public final void onPersonSelected(String paramString)
  {
    startActivity(Intents.getProfileActivityIntent(getActivity(), this.mAccount, paramString, null));
  }

  public final void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData)
  {
    if (paramString2 != null)
      startExternalActivity(new Intent("android.intent.action.VIEW", Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, paramString2)));
    while (true)
    {
      return;
      startActivity(Intents.getProfileActivityIntent(getActivity(), this.mAccount, paramString1, null));
    }
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    if (this.mSearchMode)
    {
      paramHostActionBar.showSearchView();
      paramHostActionBar.getSearchViewAdapter().addOnChangeListener(this);
      return;
    }
    if (this.mPrimarySpinnerAdapter == null)
      this.mPrimarySpinnerAdapter = new CircleSpinnerAdapter(getActivity());
    this.mPrimarySpinnerAdapter.clear();
    int i;
    label93: int k;
    label108: String str1;
    int m;
    if (this.mCirclesCursor != null)
    {
      if (this.mAccount.isPlusPage())
        break label297;
      this.mPrimarySpinnerAdapter.add(new CircleSpinnerAdapter.CircleSpinnerInfo(null, getString(R.string.suggested_people_spinner_item), 0, 0, 0));
      i = 1;
      if (!this.mCirclesCursor.moveToFirst())
        break label353;
      k = 0;
      str1 = this.mCirclesCursor.getString(0);
      String str2 = this.mCirclesCursor.getString(1);
      m = this.mCirclesCursor.getInt(3);
      int n = this.mCirclesCursor.getInt(2);
      this.mPrimarySpinnerAdapter.add(new CircleSpinnerAdapter.CircleSpinnerInfo(str1, str2, m, n, 0));
      switch (this.mSelectedViewType)
      {
      case 0:
      default:
        label208: if (this.mCirclesCursor.moveToNext())
          break;
      case 1:
      case 2:
      }
    }
    label297: label353: for (int j = k; ; j = 0)
    {
      this.mPrimarySpinnerAdapter.add(new CircleSpinnerAdapter.CircleSpinnerInfo(null, getString(R.string.create_new_circle), 0, 0, R.drawable.ic_add_circles));
      if (this.mCurrentSpinnerPosition != j)
      {
        this.mCurrentSpinnerPosition = -1;
        onPrimarySpinnerSelectionChange(j);
      }
      getActionBar().showPrimarySpinner(this.mPrimarySpinnerAdapter, this.mCurrentSpinnerPosition);
      paramHostActionBar.showActionButton(0, R.drawable.ic_menu_search_holo_light, R.string.menu_search);
      break;
      i = 0;
      break label93;
      if (!TextUtils.equals(this.mSelectedCircleId, str1))
        break label208;
      k = i + this.mCirclesCursor.getPosition();
      break label208;
      if (m != 10)
        break label208;
      k = i + this.mCirclesCursor.getPosition();
      break label208;
      break label108;
    }
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if ((!this.mSearchMode) && (this.mSelectedViewType == 1) && (this.mSelectedCircleId != null))
    {
      paramMenu.findItem(R.id.delete_circle).setVisible(true);
      paramMenu.findItem(R.id.circle_settings).setVisible(true);
    }
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if (paramInt == -1 + this.mPrimarySpinnerAdapter.getCount())
    {
      getActionBar().setPrimarySpinnerSelection(this.mCurrentSpinnerPosition);
      getActivity();
      CirclePropertiesFragmentDialog localCirclePropertiesFragmentDialog = CirclePropertiesFragmentDialog.newInstance$47e87423();
      localCirclePropertiesFragmentDialog.setTargetFragment(this, 0);
      localCirclePropertiesFragmentDialog.show(getFragmentManager(), "new_circle_input");
      return;
    }
    int i;
    label77: CircleSpinnerAdapter.CircleSpinnerInfo localCircleSpinnerInfo;
    int j;
    if (this.mCurrentSpinnerPosition != paramInt)
    {
      this.mCurrentSpinnerPosition = paramInt;
      if (this.mSelectedViewType != 2)
        break label241;
      i = 1;
      localCircleSpinnerInfo = (CircleSpinnerAdapter.CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(paramInt);
      if (localCircleSpinnerInfo.id != null)
        break label246;
      j = 0;
      label99: if (this.mSelectedViewType != j)
      {
        clearNavigationAction();
        recordNavigationAction(getLoggingViewFromType(this.mSelectedViewType), getLoggingViewFromType(j), null, null, null);
        this.mSelectedViewType = j;
        if (this.mSelectedViewType != 2)
          break label267;
      }
    }
    label267: for (int k = 1; ; k = 0)
    {
      i |= k;
      this.mScrollPosition = 0;
      String str = null;
      if (j != 0)
        str = localCircleSpinnerInfo.id;
      if (!TextUtils.equals(str, this.mSelectedCircleId))
      {
        this.mSelectedCircleId = str;
        this.mScrollPosition = 0;
      }
      this.mDataLoaded = false;
      getLoaderManager().restartLoader(2, null, this);
      if (i != 0)
        getLoaderManager().restartLoader(1, null, this);
      invalidateActionBar();
      updateView(getView());
      break;
      break;
      label241: i = 0;
      break label77;
      label246: if (localCircleSpinnerInfo.circleType == 10)
      {
        j = 2;
        break label99;
      }
      j = 1;
      break label99;
    }
  }

  public final void onQueryClose()
  {
    setSearchMode(false);
  }

  public final void onQueryTextChanged(CharSequence paramCharSequence)
  {
    PeopleSearchGridAdapter localPeopleSearchGridAdapter;
    if ((this.mAdapter != null) && (this.mSearchMode))
    {
      localPeopleSearchGridAdapter = this.mAdapter;
      if (paramCharSequence != null)
        break label31;
    }
    label31: for (String str = null; ; str = paramCharSequence.toString().trim())
    {
      localPeopleSearchGridAdapter.setQueryString(str);
      return;
    }
  }

  public final void onQueryTextSubmitted(CharSequence paramCharSequence)
  {
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    CircleMembershipManager.onPeopleListVisibilityChange(true);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult3 = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult3);
      this.mPendingRequestId = null;
    }
    if ((this.mNewCircleRequestId != null) && (!EsService.isRequestPending(this.mNewCircleRequestId.intValue())))
    {
      ServiceResult localServiceResult2 = EsService.removeResult(this.mNewCircleRequestId.intValue());
      handleNewCircleCallback(this.mNewCircleRequestId.intValue(), localServiceResult2);
      this.mNewCircleRequestId = null;
    }
    if ((this.mDeleteCircleRequestId != null) && (!EsService.isRequestPending(this.mDeleteCircleRequestId.intValue())))
    {
      ServiceResult localServiceResult1 = EsService.removeResult(this.mDeleteCircleRequestId.intValue());
      handleDeleteCircleCallback(this.mDeleteCircleRequestId.intValue(), localServiceResult1);
      this.mDeleteCircleRequestId = null;
    }
    updateView(getView());
    EsService.syncPeople(getActivity(), getAccount(), false);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAdapter != null)
      this.mAdapter.onSaveInstanceState(paramBundle);
    paramBundle.putString("selected_circle_id", this.mSelectedCircleId);
    paramBundle.putInt("selected_view_type", this.mSelectedViewType);
    paramBundle.putBoolean("search_mode", this.mSearchMode);
    paramBundle.putStringArrayList("shown_persons", this.mShownPersonIds);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
    if (this.mNewCircleRequestId != null)
      paramBundle.putInt("new_circle_request_id", this.mNewCircleRequestId.intValue());
    if (this.mDeleteCircleRequestId != null)
      paramBundle.putInt("delete_circle_request_id", this.mDeleteCircleRequestId.intValue());
    paramBundle.putString("new_circle_name", this.mCircleName);
    if (this.mGridView != null);
    for (int i = this.mGridView.getFirstVisiblePosition(); ; i = -1)
    {
      paramBundle.putInt("scrollPos", i);
      if (this.mSuggestionGridView != null)
        paramBundle.putParcelable("scrollPositions", this.mSuggestionGridView.getScrollPositions());
      return;
    }
  }

  public final void onSearchListAdapterStateChange(PeopleSearchAdapter paramPeopleSearchAdapter)
  {
    View localView = getView();
    if (localView != null)
      updateView(localView);
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mSelectedViewType = paramBundle.getInt("people_view_type", 0);
    this.mSelectedCircleId = paramBundle.getString("circle_id");
  }

  public final void onStart()
  {
    super.onStart();
    this.mAdapter.onStart();
    this.mSuggestionAdapter.onStart();
  }

  public final void onStop()
  {
    super.onStart();
    this.mAdapter.onStop();
  }

  public final void onUnblockPersonAction(String paramString, boolean paramBoolean)
  {
    UnblockPersonDialog localUnblockPersonDialog = new UnblockPersonDialog(paramString, false);
    localUnblockPersonDialog.setTargetFragment(this, 0);
    localUnblockPersonDialog.show(getFragmentManager(), "unblock_person");
  }

  public final boolean onUpButtonClicked()
  {
    boolean bool1 = this.mSearchMode;
    boolean bool2 = false;
    if (bool1)
    {
      setSearchMode(false);
      bool2 = true;
    }
    return bool2;
  }

  protected final void setCircleMembership(String paramString1, String paramString2, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator1 = paramArrayList2.iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      if (!paramArrayList1.contains(str2))
        localArrayList1.add(str2);
    }
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = paramArrayList1.iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      if (!paramArrayList2.contains(str1))
        localArrayList2.add(str1);
    }
    showProgressDialog(EsPeopleData.getMembershipChangeMessageId(localArrayList1, localArrayList2));
    this.mPendingRequestId = EsService.setCircleMembership(getActivity(), this.mAccount, paramString1, paramString2, (String[])localArrayList1.toArray(new String[localArrayList1.size()]), (String[])localArrayList2.toArray(new String[localArrayList2.size()]));
    if (!paramString1.startsWith("g:"))
      setSearchMode(false);
  }

  public final void unblockPerson(String paramString)
  {
    this.mPendingRequestId = EsService.setPersonBlocked(getActivity(), this.mAccount, paramString, null, false);
    showProgressDialog(R.string.unblock_person_operation_pending);
  }

  public static class DeleteCircleConfirmationDialog extends DialogFragment
    implements DialogInterface.OnClickListener
  {
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
      case -1:
      case -2:
      }
      while (true)
      {
        return;
        ((HostedPeopleFragment)getTargetFragment()).doDeleteCircle();
        continue;
        paramDialogInterface.dismiss();
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      Resources localResources = getResources();
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
      int i = R.plurals.delete_circles_dialog_title;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(1);
      localBuilder.setTitle(localResources.getQuantityString(i, 1, arrayOfObject1));
      int j = R.plurals.delete_circles_dialog_message;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(1);
      localBuilder.setMessage(localResources.getQuantityString(j, 1, arrayOfObject2));
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      return localBuilder.create();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedPeopleFragment
 * JD-Core Version:    0.6.2
 */