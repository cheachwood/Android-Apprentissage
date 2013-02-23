package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.EsSquaresData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.content.SquareTargetData;
import com.google.android.apps.plus.phone.ComposeBarController;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.phone.SquareStreamAdapter;
import com.google.android.apps.plus.phone.StreamAdapter;
import com.google.android.apps.plus.phone.StreamAdapter.ViewUseListener;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.SquareLandingView.OnClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;

public class HostedSquareStreamFragment extends HostedStreamFragment
  implements AlertFragmentDialog.AlertDialogListener, SquareLandingView.OnClickListener
{
  private boolean mAutoSubscribe;
  private Boolean mCanSeePosts;
  private int mCurrentSpinnerPosition;
  private boolean mFirstStreamListLoad = true;
  private boolean mFragmentCreated;
  private Integer mGetSquareRequestId;
  private Boolean mIsMember;
  private int mOperationType = 0;
  private Integer mPendingRequestId;
  private ArrayAdapter<SquareStreamSpinnerInfo> mPrimarySpinnerAdapter;
  protected String mSquareId;
  private Boolean mSquareIsExpanded;
  private boolean mSquareLoaderActive = true;
  private final LoaderManager.LoaderCallbacks<Cursor> mSquareLoaderCallbacks = new LoaderManager.LoaderCallbacks()
  {
    public final Loader<Cursor> onCreateLoader(int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      Uri localUri = EsProvider.appendAccountParameter(EsProvider.SQUARES_URI.buildUpon().appendPath(HostedSquareStreamFragment.this.mSquareId), HostedSquareStreamFragment.this.mAccount).build();
      return new EsCursorLoader(HostedSquareStreamFragment.this.getActivity(), localUri, EsSquaresData.SQUARES_PROJECTION, null, null, null);
    }

    public final void onLoaderReset(Loader<Cursor> paramAnonymousLoader)
    {
    }
  };
  private AudienceData mSquareMembers;
  protected String mSquareName;
  private final EsServiceListener mSquareServiceListener = new EsServiceListener()
  {
    public final void onEditSquareMembershipComplete$4cb07f77(int paramAnonymousInt, boolean paramAnonymousBoolean, ServiceResult paramAnonymousServiceResult)
    {
      if (EsLog.isLoggable("HostedSquareStreamFrag", 3))
        Log.d("HostedSquareStreamFrag", "onEditSquareMembershipComplete() requestId=" + paramAnonymousInt);
      if ((paramAnonymousBoolean) && ((HostedSquareStreamFragment.this.mOperationType == 2) || (HostedSquareStreamFragment.this.mOperationType == 3)))
        AlertFragmentDialog.newInstance(null, HostedSquareStreamFragment.this.getString(R.string.square_blocking_moderator_text), HostedSquareStreamFragment.this.getString(R.string.ok), null).show(HostedSquareStreamFragment.this.getFragmentManager(), null);
      HostedSquareStreamFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetSquareComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (EsLog.isLoggable("HostedSquareStreamFrag", 3))
        Log.d("HostedSquareStreamFrag", "onGetSquareComplete() requestId=" + paramAnonymousInt);
      HostedSquareStreamFragment.this.handleGetSquareServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onReadSquareMembersComplete$71621a40(int paramAnonymousInt, AudienceData paramAnonymousAudienceData, ServiceResult paramAnonymousServiceResult)
    {
      if (EsLog.isLoggable("HostedSquareStreamFrag", 3))
        Log.d("HostedSquareStreamFrag", "onGetSquareComplete() requestId=" + paramAnonymousInt);
      if ((!paramAnonymousServiceResult.hasError()) && (paramAnonymousAudienceData != null))
      {
        HostedSquareStreamFragment.access$002(HostedSquareStreamFragment.this, paramAnonymousAudienceData);
        HostedSquareStreamFragment.this.showSquareMembers(paramAnonymousAudienceData);
      }
      HostedSquareStreamFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private SquareStreamAdapter mSquareStreamAdapter;
  protected String mStreamId;
  protected String mStreamName;

  private void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (this.mPendingRequestId.intValue() != paramInt))
      return;
    this.mPendingRequestId = null;
    DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
    int i;
    if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      switch (this.mOperationType)
      {
      default:
        i = R.string.operation_failed;
        label105: Toast.makeText(getSafeContext(), i, 0).show();
      case 2:
      case 3:
      case 4:
      case 5:
      case 1:
      case 6:
      }
    while (true)
    {
      this.mOperationType = 0;
      break;
      i = R.string.square_join_error;
      break label105;
      i = R.string.square_request_to_join_error;
      break label105;
      i = R.string.square_cancel_join_request_error;
      break label105;
      i = R.string.square_leave_error;
      break label105;
      i = R.string.square_get_members_error;
      break label105;
      this.mSquareStreamAdapter.notifyDataSetChanged();
      i = R.string.square_set_notifications_error;
      break label105;
      switch (this.mOperationType)
      {
      default:
        break;
      case 2:
        this.mFirstStreamListLoad = true;
        refresh();
      }
    }
  }

  private void showProgressDialog()
  {
    int i;
    switch (this.mOperationType)
    {
    default:
      i = R.string.loading;
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      ProgressFragmentDialog.newInstance(null, getString(i), false).show(getFragmentManager(), "req_pending");
      return;
      i = R.string.square_joining;
      continue;
      i = R.string.square_sending_join_request;
      continue;
      i = R.string.square_canceling_join_request;
      continue;
      i = R.string.square_leaving;
    }
  }

  private void showSquareMembers(AudienceData paramAudienceData)
  {
    if (EsLog.isLoggable("HostedSquareStreamFrag", 3))
    {
      Log.d("HostedSquareStreamFrag", "Hidden count: " + paramAudienceData.getHiddenUserCount());
      Log.d("HostedSquareStreamFrag", "Audience users: " + paramAudienceData.getUserCount());
      for (PersonData localPersonData : paramAudienceData.getUsers())
        Log.d("HostedSquareStreamFrag", "Users: " + localPersonData.getName());
    }
    PeopleListDialogFragment localPeopleListDialogFragment = new PeopleListDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("account", this.mAccount);
    localBundle.putParcelable("audience", paramAudienceData);
    localBundle.putString("people_list_title", getString(R.string.square_members));
    localPeopleListDialogFragment.setArguments(localBundle);
    localPeopleListDialogFragment.show(getFragmentManager(), "members");
  }

  private void updateComposeBar()
  {
    if (this.mComposeBarController != null)
    {
      if (!PrimitiveUtils.safeBoolean(this.mIsMember))
        break label25;
      this.mComposeBarController.forceShow();
    }
    while (true)
    {
      return;
      label25: this.mComposeBarController.forceHide();
    }
  }

  private void updateSelectedStream(String paramString1, String paramString2)
  {
    if (EsLog.isLoggable("HostedSquareStreamFrag", 4))
      Log.i("HostedSquareStreamFrag", "updateSelectedStream");
    this.mStreamId = paramString1;
    this.mStreamName = paramString2;
    this.mFirstLoad = true;
    this.mContinuationToken = null;
    prepareLoaderUri();
    getArguments().putString("stream_id", this.mStreamId);
    getLoaderManager().restartLoader(2, null, this);
    this.mResetAnimationState = true;
    updateComposeBar();
    super.refresh();
  }

  protected final StreamAdapter createStreamAdapter(Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamAdapter.ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    return new SquareStreamAdapter(paramContext, paramColumnGridView, paramEsAccount, paramOnClickListener, paramItemClickListener, paramViewUseListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener, paramComposeBarController);
  }

  protected final void fetchContent(boolean paramBoolean)
  {
    if (EsLog.isLoggable("HostedSquareStreamFrag", 4))
      Log.i("HostedSquareStreamFrag", "fetchContent - newer=" + paramBoolean);
    if (showEmptyStream())
      return;
    label60: Integer localInteger;
    if ((paramBoolean) || (!this.mEndOfStream))
      if (paramBoolean)
      {
        this.mContinuationToken = null;
        showEmptyViewProgress(getView(), getString(R.string.loading));
        localInteger = Integer.valueOf(EsService.getActivityStream(getActivity(), this.mAccount, 4, null, this.mSquareId, this.mStreamId, this.mContinuationToken, false));
        if (!paramBoolean)
          break label131;
        this.mNewerReqId = localInteger;
      }
    while (true)
    {
      updateSpinner();
      break;
      if (this.mContinuationToken != null)
        break label60;
      break;
      break;
      label131: this.mOlderReqId = localInteger;
    }
  }

  public final Bundle getExtrasForLogging()
  {
    return EsAnalyticsData.createExtras("extra_square_id", this.mSquareId);
  }

  protected final EsMatrixCursor getStreamHeaderCursor()
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(new String[] { "_id" }, 2);
    Integer[] arrayOfInteger1 = new Integer[1];
    arrayOfInteger1[0] = Integer.valueOf(0);
    localEsMatrixCursor.addRow(arrayOfInteger1);
    if (showEmptyStream())
    {
      Integer[] arrayOfInteger2 = new Integer[1];
      arrayOfInteger2[0] = Integer.valueOf(1);
      localEsMatrixCursor.addRow(arrayOfInteger2);
    }
    return localEsMatrixCursor;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.SQUARE_LANDING;
  }

  protected final void handleGetSquareServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mGetSquareRequestId == null) || (this.mGetSquareRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      if (paramServiceResult.hasError())
      {
        this.mError = true;
        updateServerErrorView();
        if (!this.mSquareStreamAdapter.hasSquareData())
          showEmptyView(getView(), getString(R.string.people_list_error));
      }
      this.mGetSquareRequestId = null;
      updateSpinner();
    }
  }

  protected final void initCirclesLoader()
  {
  }

  protected final boolean isEmpty()
  {
    if ((this.mSquareStreamAdapter.isEmpty()) && (super.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((super.isProgressIndicatorVisible()) || (this.mSquareLoaderActive) || (this.mGetSquareRequestId != null) || (this.mPendingRequestId != null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isSquareStream()
  {
    return true;
  }

  public final void onBlockingHelpLinkClicked(Uri paramUri)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(paramUri);
    startExternalActivity(localIntent);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("square_request_id"))
        this.mGetSquareRequestId = Integer.valueOf(paramBundle.getInt("square_request_id"));
      if (paramBundle.containsKey("pending_request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("pending_request_id"));
      if (paramBundle.containsKey("square_expanded"))
        this.mSquareIsExpanded = Boolean.valueOf(paramBundle.getBoolean("square_expanded"));
      if (paramBundle.containsKey("square_members"))
        this.mSquareMembers = ((AudienceData)paramBundle.getParcelable("square_members"));
      if (paramBundle.containsKey("square_name"))
        this.mSquareName = paramBundle.getString("square_name");
      if (paramBundle.containsKey("square_stream_name"))
        this.mStreamName = paramBundle.getString("square_stream_name");
      this.mOperationType = paramBundle.getInt("operation_type", 0);
    }
    for (this.mFragmentCreated = false; ; this.mFragmentCreated = true)
    {
      getLoaderManager().initLoader(1, null, this.mSquareLoaderCallbacks);
      return;
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 4:
    }
    for (Object localObject = super.onCreateLoader(paramInt, paramBundle); ; localObject = new StreamChangeLoader(getActivity(), this.mAccount, 4, null, this.mSquareId, this.mStreamId, false))
      return localObject;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mSquareStreamAdapter = ((SquareStreamAdapter)this.mInnerAdapter);
    this.mSquareStreamAdapter.setOnClickListener(this);
    this.mSquareStreamAdapter.setViewIsExpanded(this.mSquareIsExpanded);
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
    FragmentActivity localFragmentActivity = getActivity();
    if ("leave_square".equals(paramString))
    {
      this.mOperationType = 5;
      this.mPendingRequestId = Integer.valueOf(EsService.editSquareMembership(localFragmentActivity, this.mAccount, this.mSquareId, "LEAVE"));
      showProgressDialog();
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.SQUARE_LEAVE, OzViews.SQUARE_LANDING, getExtrasForLogging());
    }
  }

  public final void onExpandClicked(boolean paramBoolean)
  {
    this.mSquareIsExpanded = Boolean.valueOf(paramBoolean);
    this.mSquareStreamAdapter.setViewIsExpanded(Boolean.valueOf(paramBoolean));
  }

  public final void onJoinLeaveClicked(int paramInt)
  {
    FragmentActivity localFragmentActivity = getActivity();
    OzViews localOzViews = OzViews.SQUARE_LANDING;
    Bundle localBundle = getExtrasForLogging();
    switch (paramInt)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return;
      this.mOperationType = 2;
      EsAccount localEsAccount5 = this.mAccount;
      String str6 = this.mSquareId;
      String str7;
      label82: EsAccount localEsAccount6;
      if (this.mAutoSubscribe)
      {
        str7 = "JOIN_WITH_SUBSCRIPTION";
        this.mPendingRequestId = Integer.valueOf(EsService.editSquareMembership(localFragmentActivity, localEsAccount5, str6, str7));
        showProgressDialog();
        localEsAccount6 = this.mAccount;
        if (!this.mAutoSubscribe)
          break label143;
      }
      label143: for (OzActions localOzActions3 = OzActions.SQUARE_JOIN_WITH_SUBSCRIPTION; ; localOzActions3 = OzActions.SQUARE_JOIN)
      {
        EsAnalytics.recordActionEvent(localFragmentActivity, localEsAccount6, localOzActions3, localOzViews, localBundle);
        break;
        str7 = "JOIN";
        break label82;
      }
      this.mOperationType = 2;
      EsAccount localEsAccount3 = this.mAccount;
      String str4 = this.mSquareId;
      String str5;
      label180: EsAccount localEsAccount4;
      if (this.mAutoSubscribe)
      {
        str5 = "ACCEPT_INVITATION_WITH_SUBSCRIPTION";
        this.mPendingRequestId = Integer.valueOf(EsService.editSquareMembership(localFragmentActivity, localEsAccount3, str4, str5));
        showProgressDialog();
        localEsAccount4 = this.mAccount;
        if (!this.mAutoSubscribe)
          break label241;
      }
      label241: for (OzActions localOzActions2 = OzActions.SQUARE_ACCEPT_INVITATION_WITH_SUBSCRIPTION; ; localOzActions2 = OzActions.SQUARE_ACCEPT_INVITATION)
      {
        EsAnalytics.recordActionEvent(localFragmentActivity, localEsAccount4, localOzActions2, localOzViews, localBundle);
        break;
        str5 = "ACCEPT_INVITATION";
        break label180;
      }
      this.mOperationType = 3;
      EsAccount localEsAccount1 = this.mAccount;
      String str2 = this.mSquareId;
      String str3;
      label278: EsAccount localEsAccount2;
      if (this.mAutoSubscribe)
      {
        str3 = "APPLY_TO_JOIN_WITH_SUBSCRIPTION";
        this.mPendingRequestId = Integer.valueOf(EsService.editSquareMembership(localFragmentActivity, localEsAccount1, str2, str3));
        showProgressDialog();
        localEsAccount2 = this.mAccount;
        if (!this.mAutoSubscribe)
          break label339;
      }
      label339: for (OzActions localOzActions1 = OzActions.SQUARE_APPLY_TO_JOIN_WITH_SUBSCRIPTION; ; localOzActions1 = OzActions.SQUARE_APPLY_TO_JOIN)
      {
        EsAnalytics.recordActionEvent(localFragmentActivity, localEsAccount2, localOzActions1, localOzViews, localBundle);
        break;
        str3 = "APPLY_TO_JOIN";
        break label278;
      }
      String str1 = getString(R.string.square_confirm_leave_title);
      if (this.mSquareStreamAdapter.getVisibility() == 0);
      for (int i = R.string.square_confirm_leave_public; ; i = R.string.square_confirm_leave_private)
      {
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(str1, getString(i), getString(R.string.square_dialog_leave_button), getString(R.string.cancel));
        localAlertFragmentDialog.setTargetFragment(this, 0);
        localAlertFragmentDialog.show(getFragmentManager(), "leave_square");
        break;
      }
      this.mOperationType = 4;
      this.mPendingRequestId = Integer.valueOf(EsService.editSquareMembership(localFragmentActivity, this.mAccount, this.mSquareId, "CANCEL_JOIN_REQUEST"));
      showProgressDialog();
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.SQUARE_CANCEL_JOIN_REQUEST, localOzViews, localBundle);
    }
  }

  public final void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    super.onLoadFinished(paramLoader, paramCursor);
    switch (paramLoader.getId())
    {
    default:
    case 3:
    }
    while (true)
    {
      return;
      this.mSquareStreamAdapter.notifyDataSetChanged();
      if (this.mSquareStreamAdapter.hasSquareData())
        showContent(getView());
    }
  }

  public final void onMembersClicked()
  {
    if (this.mSquareMembers != null)
      showSquareMembers(this.mSquareMembers);
    while (true)
    {
      return;
      if (this.mPendingRequestId == null)
      {
        this.mOperationType = 1;
        this.mPendingRequestId = Integer.valueOf(EsService.readSquareMembers(getActivity(), this.mAccount, this.mSquareId, null));
        showProgressDialog();
      }
    }
  }

  public final void onNotificationSwitchChanged(boolean paramBoolean)
  {
    this.mOperationType = 6;
    FragmentActivity localFragmentActivity1 = getActivity();
    EsAccount localEsAccount1 = this.mAccount;
    String str1 = this.mSquareId;
    String str2;
    FragmentActivity localFragmentActivity2;
    EsAccount localEsAccount2;
    if (paramBoolean)
    {
      str2 = "SUBSCRIBE";
      this.mPendingRequestId = Integer.valueOf(EsService.editSquareMembership(localFragmentActivity1, localEsAccount1, str1, str2));
      localFragmentActivity2 = getActivity();
      localEsAccount2 = this.mAccount;
      if (!paramBoolean)
        break label93;
    }
    label93: for (OzActions localOzActions = OzActions.SQUARE_SUBSCRIBE; ; localOzActions = OzActions.SQUARE_UNSUBSCRIBE)
    {
      EsAnalytics.recordActionEvent(localFragmentActivity2, localEsAccount2, localOzActions, OzViews.SQUARE_LANDING, getExtrasForLogging());
      return;
      str2 = "UNSUBSCRIBE";
      break;
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mSquareServiceListener);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    this.mPrimarySpinnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item);
    this.mPrimarySpinnerAdapter.setDropDownViewResource(17367049);
    this.mPrimarySpinnerAdapter.clear();
    paramHostActionBar.showPrimarySpinner(this.mPrimarySpinnerAdapter, 0);
    paramHostActionBar.showRefreshButton();
    paramHostActionBar.showProgressIndicator();
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if (this.mCurrentSpinnerPosition != paramInt)
    {
      this.mCurrentSpinnerPosition = paramInt;
      SquareStreamSpinnerInfo localSquareStreamSpinnerInfo = (SquareStreamSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(paramInt);
      updateSelectedStream(localSquareStreamSpinnerInfo.getStreamId(), localSquareStreamSpinnerInfo.getStreamName());
    }
  }

  public final void onResume()
  {
    super.onResume();
    if (!Property.ENABLE_SQUARES.getBoolean())
      getActivity().finish();
    EsService.registerListener(this.mSquareServiceListener);
    if ((this.mGetSquareRequestId != null) && (!EsService.isRequestPending(this.mGetSquareRequestId.intValue())))
    {
      ServiceResult localServiceResult2 = EsService.removeResult(this.mGetSquareRequestId.intValue());
      handleGetSquareServiceCallback(this.mGetSquareRequestId.intValue(), localServiceResult2);
    }
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult1 = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult1);
    }
    if (this.mFragmentCreated)
    {
      this.mFragmentCreated = false;
      refreshSquare();
    }
    updateSpinner();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mGetSquareRequestId != null)
      paramBundle.putInt("square_request_id", this.mGetSquareRequestId.intValue());
    if (this.mPendingRequestId != null)
      paramBundle.putInt("pending_request_id", this.mPendingRequestId.intValue());
    if (this.mSquareMembers != null)
      paramBundle.putParcelable("square_members", this.mSquareMembers);
    if (this.mSquareName != null)
      paramBundle.putString("square_name", this.mSquareName);
    if (this.mStreamName != null)
      paramBundle.putString("square_stream_name", this.mStreamName);
    if (this.mSquareIsExpanded != null)
      paramBundle.putBoolean("square_expanded", this.mSquareIsExpanded.booleanValue());
    paramBundle.putInt("operation_type", this.mOperationType);
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mSquareId = paramBundle.getString("square_id");
    this.mStreamId = paramBundle.getString("stream_id");
  }

  protected final void prepareLoaderUri()
  {
    this.mPostsUri = EsProvider.buildStreamUri(this.mAccount, EsPostsData.buildSquareStreamKey(this.mSquareId, this.mStreamId, false));
  }

  public final void refresh()
  {
    super.refresh();
    refreshSquare();
  }

  public final void refreshSquare()
  {
    if (EsLog.isLoggable("HostedSquareStreamFrag", 4))
      Log.i("HostedSquareStreamFrag", "refreshSquare");
    this.mSquareMembers = null;
    this.mGetSquareRequestId = Integer.valueOf(EsService.getSquare(getActivity(), this.mAccount, this.mSquareId));
    updateSpinner();
  }

  protected final boolean showEmptyStream()
  {
    if ((this.mCanSeePosts != null) && (!this.mCanSeePosts.booleanValue()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final void startActivityForCompose(Intent paramIntent)
  {
    boolean bool = TextUtils.isEmpty(this.mSquareName);
    AudienceData localAudienceData = null;
    String str1;
    String str2;
    String str3;
    if (!bool)
    {
      str1 = this.mSquareId;
      str2 = this.mSquareName;
      str3 = this.mStreamId;
      if (this.mStreamId != null)
        break label82;
    }
    label82: for (String str4 = ""; ; str4 = this.mStreamName)
    {
      localAudienceData = new AudienceData(new SquareTargetData(str1, str2, str3, str4));
      paramIntent.putExtra("audience", localAudienceData);
      super.startActivityForCompose(paramIntent);
      return;
    }
  }

  protected final void startStreamOneUp(Intent paramIntent)
  {
    if (this.mSquareStreamAdapter.isSquareAdmin())
      paramIntent.putExtra("square_admin", true);
    paramIntent.putExtra("refresh", true);
    super.startStreamOneUp(paramIntent);
  }

  private static final class SquareStreamSpinnerInfo
  {
    private final String mStreamId;
    private final String mStreamName;

    public SquareStreamSpinnerInfo(String paramString1, String paramString2)
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
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedSquareStreamFragment
 * JD-Core Version:    0.6.2
 */