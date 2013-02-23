package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.phone.EsAsyncTaskLoader;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EventDetailsActivityAdapter;
import com.google.android.apps.plus.phone.EventDetailsActivityAdapter.ViewUseListener;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.phone.Intents.PhotosIntentBuilder;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.android.apps.plus.util.MapUtils;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.EventActionListener;
import com.google.android.apps.plus.views.EventDetailsHeaderView;
import com.google.android.apps.plus.views.EventRsvpLayout;
import com.google.android.apps.plus.views.EventUpdate;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.api.services.plusi.model.EventOptions;
import com.google.api.services.plusi.model.PlusEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HostedEventFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AlertFragmentDialog.AlertDialogListener, CommentEditFragmentDialog.CommentEditDialogListener, EventDetailsActivityAdapter.ViewUseListener, EventActionListener
{
  private static int mNextPagePreloadTriggerRows;
  private String mActivityId;
  private EventDetailsActivityAdapter mAdapter;
  private String mAuthKey;
  private boolean mCanComment;
  private Integer mCommentReqId;
  private Integer mDeleteReqId;
  private boolean mError;
  private PlusEvent mEvent;
  private String mEventId;
  private boolean mEventLoaded;
  private EventActiveState mEventState = new EventActiveState();
  private Integer mFetchReqId;
  private long mFirstActivityTimestamp;
  private boolean mGhostEvent;
  private ColumnGridView mGridView;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private boolean mHasUserInteracted;
  private String mIncomingRsvpType;
  private String mInvitationToken;
  private Integer mInviteReqId;
  private final EsServiceListener mListener = new EsServiceListener()
  {
    public final void onCreateEventComment$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.this.handleCreateCommentComplete(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onDeleteEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.access$1000(HostedEventFragment.this, paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onEventInviteComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.this.handleInviteMoreComplete(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onInsertCameraPhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      String str = EsService.getLastCameraMediaLocation();
      HostedEventFragment.this.handleNewPhotoComplete(paramAnonymousInt, paramAnonymousServiceResult, str);
    }

    public final void onReadEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.access$302(HostedEventFragment.this, false);
      if ((paramAnonymousServiceResult != null) && (!paramAnonymousServiceResult.hasError()))
        HostedEventFragment.access$402(HostedEventFragment.this, null);
      HostedEventFragment.this.handleGetEventUpdatesComplete(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onReportActivity$63505a2b(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.this.handleReportEventCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSendEventRsvpComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.this.handleSendEventRsvpComplete(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSharePhotosToEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedEventFragment.this.handleSharePhotosToEventCallBack$b5e9bbb(paramAnonymousServiceResult);
    }
  };
  private boolean mNeedsRefresh;
  private Integer mNewPhotoReqId;
  private String mPollingToken;
  private boolean mPreloadRequested;
  private Runnable mRefreshRunnable;
  private Integer mReportAbuseRequestId;
  private String mResumeToken;
  private int mSavedScrollPos = -1;
  private Integer mSendRsvpReqId;
  private final SettingsLoaderCallbacks mSettingsCallbacks = new SettingsLoaderCallbacks((byte)0);
  private ContentObserver mSettingsObserver = new ContentObserver(this.mHandler)
  {
    public final void onChange(boolean paramAnonymousBoolean)
    {
      if (!HostedEventFragment.this.isPaused())
        HostedEventFragment.this.getLoaderManager().restartLoader(0, null, HostedEventFragment.this);
    }
  };
  private int mSource;
  private String mTemporalRsvpState;
  private int mTypeId;

  private void fetchData()
  {
    this.mError = false;
    this.mFetchReqId = Integer.valueOf(EsService.readEvent(getActivity(), this.mAccount, this.mEventId, this.mPollingToken, this.mResumeToken, this.mInvitationToken, this.mAuthKey, true));
    updateProgressIndicator();
  }

  private void handleCreateCommentComplete(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mCommentReqId == null) || (this.mCommentReqId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mCommentReqId = null;
      hideProgressDialog();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
      else
        fetchData();
    }
  }

  private void handleGetEventUpdatesComplete(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mFetchReqId == null) || (this.mFetchReqId.intValue() != paramInt));
    do
    {
      return;
      this.mFetchReqId = null;
      updateProgressIndicator();
      hideProgressDialog();
    }
    while ((paramServiceResult == null) || (!paramServiceResult.hasError()));
    int i = paramServiceResult.getErrorCode();
    if ((i >= 400) && (i < 500))
      this.mGhostEvent = true;
    while (true)
    {
      getLoaderManager().restartLoader(0, null, this);
      getLoaderManager().restartLoader(4, null, this);
      this.mAdapter.checkPartitions("HEF", "HGEUC");
      break;
      this.mError = true;
      if (this.mEvent != null)
        Toast.makeText(getActivity(), R.string.no_connection, 0).show();
    }
  }

  private void handleInviteMoreComplete(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mInviteReqId == null) || (paramInt != this.mInviteReqId.intValue()));
    while (true)
    {
      return;
      hideProgressDialog();
      this.mInviteReqId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
      else
        fetchData();
    }
  }

  private void handleNewPhotoComplete(int paramInt, ServiceResult paramServiceResult, String paramString)
  {
    if ((this.mNewPhotoReqId == null) || (this.mNewPhotoReqId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mNewPhotoReqId = null;
      hideProgressDialog();
      final FragmentActivity localFragmentActivity = getActivity();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        Toast.makeText(localFragmentActivity, R.string.transient_server_error, 0).show();
      }
      else
      {
        Toast.makeText(localFragmentActivity, R.string.event_post_photo, 1).show();
        AsyncTask local8 = new AsyncTask()
        {
        };
        String[] arrayOfString = new String[3];
        arrayOfString[0] = this.mEventId;
        arrayOfString[1] = getAccount().getName();
        arrayOfString[2] = paramString;
        local8.execute(arrayOfString);
      }
    }
  }

  private void handleSendEventRsvpComplete(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mSendRsvpReqId == null) || (this.mSendRsvpReqId.intValue() != paramInt));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("send_rsvp");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
      }
      else
      {
        this.mTemporalRsvpState = null;
        this.mSendRsvpReqId = null;
        if (this.mEvent != null)
        {
          updateActiveEventState();
          updateRsvpSection();
        }
      }
    }
  }

  private void hideProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
  }

  private void inviteMore()
  {
    startActivityForResult(Intents.getEditAudienceActivityIntent(getActivity(), getAccount(), getString(R.string.event_invite_activity_title), null, 11, false, false, true, false), 2);
  }

  private void processPendingPhotoRequest()
  {
    if ((this.mNewPhotoReqId != null) && (!EsService.isRequestPending(this.mNewPhotoReqId.intValue())))
    {
      EsService.removeResult(this.mNewPhotoReqId.intValue());
      ServiceResult localServiceResult = EsService.removeResult(this.mNewPhotoReqId.intValue());
      String str = EsService.getLastCameraMediaLocation();
      handleNewPhotoComplete(this.mNewPhotoReqId.intValue(), localServiceResult, str);
      this.mNewPhotoReqId = null;
    }
  }

  private void showPhotoDialog()
  {
    recordUserAction(OzActions.COMPOSE_CHOOSE_PHOTO);
    startActivityForResult(Intents.newPhotosActivityIntentBuilder(getActivity()).setAccount(this.mAccount).setAlbumType("camera_photos").setPhotoPickerMode(Integer.valueOf(2)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_share)).setTakePhoto(true).setTakeVideo(true).build(), 3);
  }

  private void showProgressDialog(int paramInt)
  {
    ProgressFragmentDialog.newInstance(null, getString(paramInt), false).show(getFragmentManager(), "req_pending");
  }

  private void toggleInstantShare(boolean paramBoolean)
  {
    if (paramBoolean == this.mEventState.isInstantShareEnabled);
    while (true)
    {
      return;
      FragmentActivity localFragmentActivity = getActivity();
      if (localFragmentActivity != null)
      {
        InstantShareToggle localInstantShareToggle = new InstantShareToggle();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Boolean.valueOf(paramBoolean);
        arrayOfObject[1] = localFragmentActivity;
        localInstantShareToggle.execute(arrayOfObject);
      }
    }
  }

  private void turnOnInstantShare(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2)
      recordUserAction(OzActions.COMPOSE_TAKE_PHOTO);
    try
    {
      getActivity();
      startActivityForResult(Intents.getCameraIntentPhoto$3a35108a("camera-event.jpg"), 0);
      if ((!EsEventData.isViewerCheckedIn(this.mEvent)) && (paramBoolean1))
        EsService.sendEventRsvp(getActivity(), getAccount(), this.mEventId, this.mAuthKey, "CHECKIN");
      toggleInstantShare(true);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      while (true)
        Toast.makeText(getActivity(), R.string.change_photo_no_camera, 1).show();
    }
  }

  private void updateActiveEventState()
  {
    boolean bool = true;
    long l1 = System.currentTimeMillis();
    this.mEventState.hasUserInteracted = this.mHasUserInteracted;
    String str = getAccount().getGaiaId();
    this.mEventState.isOwner = TextUtils.equals(str, this.mEvent.creatorObfuscatedId);
    this.mEventState.isInstantShareAvailable = false;
    this.mEventState.isInstantShareExpired = false;
    EventActiveState localEventActiveState;
    if (EsEventData.isInstantShareAllowed(this.mEvent, str, l1))
    {
      this.mEventState.isInstantShareAvailable = bool;
      this.mEventState.canInviteOthers = EsEventData.canInviteOthers(this.mEvent, this.mAccount);
      localEventActiveState = this.mEventState;
      if (this.mSendRsvpReqId != null)
        break label307;
    }
    while (true)
    {
      localEventActiveState.isRsvpEnabled = bool;
      this.mEventState.temporalRsvpValue = this.mTemporalRsvpState;
      this.mEventState.eventSource = this.mSource;
      if (getAccount().isPlusPage())
      {
        this.mEventState.isInstantShareAvailable = false;
        this.mEventState.isInstantShareExpired = false;
      }
      if ((this.mTypeId == 58) && (this.mEventState.isInstantShareAvailable) && (!this.mEventState.isInstantShareEnabled))
        this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            if (!HostedEventFragment.this.isPaused())
              HostedEventFragment.this.onInstantShareToggle(true);
          }
        });
      this.mTypeId = 0;
      return;
      if (EsEventData.isEventOver(this.mEvent, l1))
      {
        this.mEventState.isInstantShareExpired = bool;
        break;
      }
      if (this.mRefreshRunnable == null)
        this.mRefreshRunnable = new EventRefreshRunnable((byte)0);
      this.mHandler.removeCallbacks(this.mRefreshRunnable);
      long l2 = EsEventData.timeUntilInstantShareAllowed(this.mEvent, str, l1);
      if (l2 <= 0L)
        break;
      this.mHandler.postDelayed(this.mRefreshRunnable, l2);
      break;
      label307: bool = false;
    }
  }

  private void updateProgressIndicator()
  {
    HostActionBar localHostActionBar = getActionBar();
    if ((!this.mGhostEvent) && ((this.mFetchReqId != null) || (!this.mEventLoaded)))
      localHostActionBar.showProgressIndicator();
    while (true)
    {
      return;
      localHostActionBar.hideProgressIndicator();
    }
  }

  private void updateRsvpSection()
  {
    View localView = getView();
    if (localView == null);
    while (true)
    {
      return;
      EventRsvpLayout localEventRsvpLayout = (EventRsvpLayout)localView.findViewById(R.id.event_rsvp_section);
      if (localEventRsvpLayout != null)
      {
        localEventRsvpLayout.bind(this.mEvent, this.mEventState, this);
        localEventRsvpLayout.invalidate();
      }
    }
  }

  private void updateView(View paramView)
  {
    if (paramView == null)
      return;
    TextView localTextView = (TextView)paramView.findViewById(R.id.server_error);
    View localView = paramView.findViewById(R.id.grid);
    if (this.mGhostEvent)
    {
      localTextView.setVisibility(0);
      localTextView.setText(R.string.event_does_not_exist);
      localView.setVisibility(8);
      showContent(paramView);
    }
    while (true)
    {
      updateProgressIndicator();
      break;
      if (this.mEvent != null)
      {
        localTextView.setVisibility(8);
        localView.setVisibility(0);
        showContent(paramView);
      }
      else if ((!this.mEventLoaded) || (this.mFetchReqId != null))
      {
        localView.setVisibility(8);
        localTextView.setVisibility(8);
        showEmptyViewProgress(paramView);
      }
      else if (this.mError)
      {
        localTextView.setVisibility(0);
        localTextView.setText(R.string.event_details_error);
        localView.setVisibility(8);
        showContent(paramView);
      }
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.EVENT;
  }

  protected final void handleReportEventCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mReportAbuseRequestId == null) || (this.mReportAbuseRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mReportAbuseRequestId = null;
      updateSpinner();
      FragmentActivity localFragmentActivity = getActivity();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(localFragmentActivity, R.string.transient_server_error, 0).show();
      else
        Toast.makeText(localFragmentActivity, R.string.report_abuse_event_completed_toast, 0).show();
    }
  }

  protected final void handleSharePhotosToEventCallBack$b5e9bbb(ServiceResult paramServiceResult)
  {
    if (paramServiceResult.hasError())
      Toast.makeText(getSafeContext(), R.string.event_photo_share_failed_toast, 0).show();
  }

  protected final boolean isEmpty()
  {
    if (this.mEvent == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((super.isProgressIndicatorVisible()) || (this.mReportAbuseRequestId != null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      showPhotoDialog();
      continue;
      CommentEditFragmentDialog localCommentEditFragmentDialog = CommentEditFragmentDialog.newInstance(R.string.event_comment_dialog_title);
      localCommentEditFragmentDialog.setTargetFragment(this, 1);
      localCommentEditFragmentDialog.show(getFragmentManager(), "comment");
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
      case 1:
      default:
        break;
      case 0:
        FragmentActivity localFragmentActivity3 = getActivity();
        if ((localFragmentActivity3 instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
          ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity3).showInsertCameraPhotoDialog();
        this.mNewPhotoReqId = EsService.insertCameraPhoto(localFragmentActivity3, this.mAccount, "camera-event.jpg");
        break;
      case 3:
        if ((paramInt2 == -1) && (paramIntent != null) && ((!paramIntent.hasExtra("media_taken")) || (!this.mEventState.isInstantShareEnabled)))
          if (paramIntent.hasExtra("insert_photo_request_id"))
          {
            FragmentActivity localFragmentActivity2 = getActivity();
            if ((localFragmentActivity2 instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
              ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity2).showInsertCameraPhotoDialog();
            this.mNewPhotoReqId = Integer.valueOf(paramIntent.getIntExtra("insert_photo_request_id", 0));
            processPendingPhotoRequest();
          }
          else
          {
            ArrayList localArrayList = paramIntent.getParcelableArrayListExtra("mediarefs");
            if (localArrayList != null)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              Iterator localIterator = localArrayList.iterator();
              while (localIterator.hasNext())
              {
                Uri localUri = ((MediaRef)localIterator.next()).getLocalUri();
                if (localUri != null)
                  localStringBuilder.append(localUri.toString() + " ");
              }
              final FragmentActivity localFragmentActivity1 = getActivity();
              Toast.makeText(localFragmentActivity1, R.string.event_post_photo, 1).show();
              AsyncTask local9 = new AsyncTask()
              {
                EsAccount currentAccount = HostedEventFragment.this.getAccount();
                String currentEventId = HostedEventFragment.this.mEventId;
                List<Long> skippedPhotoIds = new ArrayList();

                // ERROR //
                private Void doInBackground(String[] paramAnonymousArrayOfString)
                {
                  // Byte code:
                  //   0: aload_0
                  //   1: getfield 38	com/google/android/apps/plus/fragments/HostedEventFragment$9:currentAccount	Lcom/google/android/apps/plus/content/EsAccount;
                  //   4: invokevirtual 54	com/google/android/apps/plus/content/EsAccount:getName	()Ljava/lang/String;
                  //   7: astore_2
                  //   8: aload_1
                  //   9: iconst_0
                  //   10: aaload
                  //   11: astore_3
                  //   12: aload_0
                  //   13: getfield 24	com/google/android/apps/plus/fragments/HostedEventFragment$9:val$context	Landroid/content/Context;
                  //   16: invokevirtual 60	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
                  //   19: astore 4
                  //   21: getstatic 66	com/google/android/apps/plus/iu/InstantUploadFacade:PHOTOS_URI	Landroid/net/Uri;
                  //   24: invokevirtual 72	android/net/Uri:buildUpon	()Landroid/net/Uri$Builder;
                  //   27: ldc 74
                  //   29: aload_0
                  //   30: getfield 22	com/google/android/apps/plus/fragments/HostedEventFragment$9:this$0	Lcom/google/android/apps/plus/fragments/HostedEventFragment;
                  //   33: getfield 77	com/google/android/apps/plus/fragments/HostedEventFragment:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
                  //   36: invokevirtual 54	com/google/android/apps/plus/content/EsAccount:getName	()Ljava/lang/String;
                  //   39: invokevirtual 83	android/net/Uri$Builder:appendQueryParameter	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
                  //   42: invokevirtual 87	android/net/Uri$Builder:build	()Landroid/net/Uri;
                  //   45: astore 5
                  //   47: new 89	java/util/StringTokenizer
                  //   50: dup
                  //   51: aload_3
                  //   52: invokespecial 92	java/util/StringTokenizer:<init>	(Ljava/lang/String;)V
                  //   55: astore 6
                  //   57: new 94	java/util/LinkedHashSet
                  //   60: dup
                  //   61: invokespecial 95	java/util/LinkedHashSet:<init>	()V
                  //   64: astore 7
                  //   66: aload 6
                  //   68: invokevirtual 99	java/util/StringTokenizer:hasMoreTokens	()Z
                  //   71: ifeq +17 -> 88
                  //   74: aload 7
                  //   76: aload 6
                  //   78: invokevirtual 102	java/util/StringTokenizer:nextToken	()Ljava/lang/String;
                  //   81: invokevirtual 106	java/util/LinkedHashSet:add	(Ljava/lang/Object;)Z
                  //   84: pop
                  //   85: goto -19 -> 66
                  //   88: aconst_null
                  //   89: astore 8
                  //   91: aload 4
                  //   93: aload 5
                  //   95: aload 7
                  //   97: aload 7
                  //   99: invokevirtual 110	java/util/LinkedHashSet:size	()I
                  //   102: anewarray 112	java/lang/String
                  //   105: invokevirtual 116	java/util/LinkedHashSet:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
                  //   108: checkcast 118	[Ljava/lang/String;
                  //   111: aconst_null
                  //   112: aconst_null
                  //   113: aconst_null
                  //   114: invokevirtual 124	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
                  //   117: astore 8
                  //   119: aload 8
                  //   121: invokeinterface 129 1 0
                  //   126: ifeq +89 -> 215
                  //   129: aload 7
                  //   131: aload 8
                  //   133: iconst_0
                  //   134: invokeinterface 133 2 0
                  //   139: invokevirtual 136	java/util/LinkedHashSet:remove	(Ljava/lang/Object;)Z
                  //   142: pop
                  //   143: aload_0
                  //   144: getfield 32	com/google/android/apps/plus/fragments/HostedEventFragment$9:skippedPhotoIds	Ljava/util/List;
                  //   147: aload 8
                  //   149: iconst_1
                  //   150: invokeinterface 140 2 0
                  //   155: invokestatic 146	java/lang/Long:valueOf	(J)Ljava/lang/Long;
                  //   158: invokeinterface 149 2 0
                  //   163: pop
                  //   164: goto -45 -> 119
                  //   167: astore 10
                  //   169: aload 8
                  //   171: ifnull +10 -> 181
                  //   174: aload 8
                  //   176: invokeinterface 152 1 0
                  //   181: aload_0
                  //   182: getfield 22	com/google/android/apps/plus/fragments/HostedEventFragment$9:this$0	Lcom/google/android/apps/plus/fragments/HostedEventFragment;
                  //   185: aload_0
                  //   186: getfield 24	com/google/android/apps/plus/fragments/HostedEventFragment$9:val$context	Landroid/content/Context;
                  //   189: aload_2
                  //   190: aload_0
                  //   191: getfield 44	com/google/android/apps/plus/fragments/HostedEventFragment$9:currentEventId	Ljava/lang/String;
                  //   194: aload 7
                  //   196: aload 7
                  //   198: invokevirtual 110	java/util/LinkedHashSet:size	()I
                  //   201: anewarray 112	java/lang/String
                  //   204: invokevirtual 116	java/util/LinkedHashSet:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
                  //   207: checkcast 118	[Ljava/lang/String;
                  //   210: invokestatic 156	com/google/android/apps/plus/fragments/HostedEventFragment:access$1500	(Lcom/google/android/apps/plus/fragments/HostedEventFragment;Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
                  //   213: aconst_null
                  //   214: areturn
                  //   215: aload 8
                  //   217: ifnull -36 -> 181
                  //   220: aload 8
                  //   222: invokeinterface 152 1 0
                  //   227: goto -46 -> 181
                  //   230: astore 9
                  //   232: aload 8
                  //   234: ifnull +10 -> 244
                  //   237: aload 8
                  //   239: invokeinterface 152 1 0
                  //   244: aload 9
                  //   246: athrow
                  //
                  // Exception table:
                  //   from	to	target	type
                  //   91	164	167	java/lang/Exception
                  //   91	164	230	finally
                }
              };
              String[] arrayOfString = new String[1];
              arrayOfString[0] = localStringBuilder.toString();
              local9.execute(arrayOfString);
            }
          }
        break;
      case 2:
        final AudienceData localAudienceData = (AudienceData)paramIntent.getParcelableExtra("audience");
        this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            HostedEventFragment.access$1400(HostedEventFragment.this, localAudienceData);
          }
        });
      }
    }
  }

  public final void onAddPhotosClicked()
  {
    showPhotoDialog();
  }

  public final void onAvatarClicked(String paramString)
  {
    startActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), getAccount(), paramString, null));
  }

  public final void onCommentEditComplete$42c1be52(Spannable paramSpannable)
  {
    if ((this.mActivityId == null) || (TextUtils.isEmpty(paramSpannable)));
    while (true)
    {
      return;
      getActivity();
      getAccount();
      String str = ApiUtils.buildPostableString$6d7f0b14(paramSpannable);
      showProgressDialog(R.string.event_comment_sending);
      this.mCommentReqId = Integer.valueOf(EsService.createEventComment(getActivity(), getAccount(), this.mActivityId, this.mEventId, this.mAuthKey, str));
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mEventState.expanded = true;
    if (paramBundle != null)
    {
      this.mEventId = paramBundle.getString("id");
      this.mTypeId = paramBundle.getInt("typeid");
      this.mInvitationToken = paramBundle.getString("invitation_token");
      this.mIncomingRsvpType = paramBundle.getString("incoming_rsvp_type");
      this.mNeedsRefresh = paramBundle.getBoolean("refresh", false);
      this.mSavedScrollPos = paramBundle.getInt("scroll_pos", -1);
      this.mFirstActivityTimestamp = paramBundle.getLong("first_timestamp");
      if (paramBundle.containsKey("fetch_req_id"))
        this.mFetchReqId = Integer.valueOf(paramBundle.getInt("fetch_req_id"));
      if (paramBundle.containsKey("comment_req_id"))
        this.mCommentReqId = Integer.valueOf(paramBundle.getInt("comment_req_id"));
      if (paramBundle.containsKey("new_photo_req_id"))
        this.mNewPhotoReqId = Integer.valueOf(paramBundle.getInt("new_photo_req_id"));
      if (paramBundle.containsKey("invite_more_req_id"))
        this.mInviteReqId = Integer.valueOf(paramBundle.getInt("invite_more_req_id"));
      if (paramBundle.containsKey("rsvp_req_id"))
        this.mSendRsvpReqId = Integer.valueOf(paramBundle.getInt("rsvp_req_id"));
      if (paramBundle.containsKey("temp_rsvp_state"))
        this.mTemporalRsvpState = paramBundle.getString("temp_rsvp_state");
      if (paramBundle.containsKey("delete_req_id"))
        this.mDeleteReqId = Integer.valueOf(paramBundle.getInt("delete_req_id"));
      if (paramBundle.containsKey("abuse_request_id"))
        this.mReportAbuseRequestId = Integer.valueOf(paramBundle.getInt("abuse_request_id"));
      this.mEventState.expanded = paramBundle.getBoolean("expanded", true);
      invalidateActionBar();
    }
    while (true)
    {
      if (!TextUtils.isEmpty(this.mIncomingRsvpType))
      {
        onRsvpChanged(this.mIncomingRsvpType);
        this.mIncomingRsvpType = null;
      }
      return;
      if (this.mEventId != null)
      {
        this.mNeedsRefresh = true;
      }
      else
      {
        this.mGhostEvent = true;
        updateView(getView());
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    final FragmentActivity localFragmentActivity = getActivity();
    Object localObject = null;
    switch (paramInt)
    {
    case 2:
    case 3:
    default:
    case 0:
    case 1:
    case 4:
    }
    while (true)
    {
      return localObject;
      localObject = new EsCursorLoader(localFragmentActivity, EsProvider.EVENTS_ALL_URI)
      {
        public final Cursor esLoadInBackground()
        {
          return EsEventData.getEvent(localFragmentActivity, HostedEventFragment.this.mAccount, HostedEventFragment.this.mEventId, HostedEventFragment.DetailsQuery.PROJECTION);
        }
      };
      continue;
      localObject = new EsCursorLoader(localFragmentActivity, EsProvider.EVENTS_ALL_URI)
      {
        public final Cursor esLoadInBackground()
        {
          return EsEventData.getEventActivities(localFragmentActivity, HostedEventFragment.this.mAccount, HostedEventFragment.this.mEventId, HostedEventFragment.ActivityQuery.PROJECTION);
        }
      };
      continue;
      localObject = new EsCursorLoader(localFragmentActivity, EsProvider.EVENTS_ALL_URI)
      {
        public final Cursor esLoadInBackground()
        {
          return EsEventData.getEventResolvedPeople(localFragmentActivity, HostedEventFragment.this.mAccount, HostedEventFragment.this.mEventId, HostedEventFragment.EventPeopleQuery.PROJECTION);
        }
      };
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.hosted_event_fragment, paramViewGroup, false);
    this.mGridView = ((ColumnGridView)localView.findViewById(R.id.grid));
    FragmentActivity localFragmentActivity = getActivity();
    this.mAdapter = new EventDetailsActivityAdapter(localFragmentActivity, this.mGridView, this, this);
    this.mGridView.setAdapter(this.mAdapter);
    getLoaderManager().initLoader(2, null, this.mSettingsCallbacks);
    getLoaderManager().initLoader(0, null, this);
    getLoaderManager().initLoader(1, null, this);
    getLoaderManager().initLoader(4, null, this);
    if (mNextPagePreloadTriggerRows == 0)
      if (ScreenMetrics.getInstance(localView.getContext()).screenDisplayType != 0)
        break label148;
    label148: for (mNextPagePreloadTriggerRows = 8; ; mNextPagePreloadTriggerRows = 16)
    {
      updateView(localView);
      return localView;
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
    if ("dialog_photo_sync".equals(paramString))
      turnOnInstantShare(false, false);
    while (true)
    {
      return;
      if ("report_event".equals(paramString))
      {
        this.mReportAbuseRequestId = Integer.valueOf(EsService.reportActivity(getActivity(), this.mAccount, this.mActivityId, null));
        showProgressDialog(R.string.report_abuse_operation_pending);
      }
    }
  }

  public final void onExpansionToggled(boolean paramBoolean)
  {
    this.mEventState.expanded = paramBoolean;
  }

  public final void onHangoutClicked()
  {
    if (this.mEvent.hangoutInfo != null)
      startActivity(Intents.getEventHangoutActivityIntent(getActivity(), this.mAccount, this.mEventId));
  }

  public final void onInstantShareToggle(boolean paramBoolean)
  {
    boolean bool2;
    boolean bool3;
    DialogType localDialogType;
    if (paramBoolean)
    {
      boolean bool1 = InstantUpload.isSyncEnabled$1f9c1b43((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
      bool2 = ContentResolver.getMasterSyncAutomatically();
      if ((bool2) && (bool1))
      {
        FragmentManager localFragmentManager3 = getFragmentManager();
        if (localFragmentManager3.findFragmentByTag("dialog_check_in") == null)
        {
          if (EsEventData.isViewerCheckedIn(this.mEvent))
            break label136;
          bool3 = true;
          if ((this.mEvent.eventOptions == null) || (!Boolean.TRUE.equals(this.mEvent.eventOptions.broadcast)))
            break label142;
          localDialogType = DialogType.ON_AIR;
          label100: InstantShareConfirmationDialog localInstantShareConfirmationDialog = new InstantShareConfirmationDialog(bool3, localDialogType);
          localInstantShareConfirmationDialog.show(localFragmentManager3, "dialog_check_in");
          localInstantShareConfirmationDialog.setTargetFragment(this, 0);
        }
      }
    }
    while (true)
    {
      this.mHasUserInteracted = true;
      return;
      label136: bool3 = false;
      break;
      label142: if (Boolean.TRUE.equals(this.mEvent.isPublic))
      {
        localDialogType = DialogType.PUBLIC;
        break label100;
      }
      localDialogType = DialogType.PRIVATE;
      break label100;
      if (!bool2)
      {
        FragmentManager localFragmentManager2 = getFragmentManager();
        if (localFragmentManager2.findFragmentByTag("dialog_master_sync") == null)
        {
          AlertFragmentDialog localAlertFragmentDialog2 = AlertFragmentDialog.newInstance(getString(R.string.event_instant_share_dialog_title), getString(R.string.event_master_sync_dialog_message), getString(R.string.ok), null);
          localAlertFragmentDialog2.setTargetFragment(this, 0);
          localAlertFragmentDialog2.show(localFragmentManager2, "dialog_master_sync");
        }
      }
      else
      {
        FragmentManager localFragmentManager1 = getFragmentManager();
        if (localFragmentManager1.findFragmentByTag("dialog_photo_sync") == null)
        {
          String str1 = getString(R.string.es_google_iu_provider);
          String str2 = getString(R.string.event_enable_sync_dialog_message, new Object[] { str1 });
          AlertFragmentDialog localAlertFragmentDialog1 = AlertFragmentDialog.newInstance(getString(R.string.event_instant_share_dialog_title), str2, getString(R.string.yes), getString(R.string.no));
          localAlertFragmentDialog1.setTargetFragment(this, 0);
          localAlertFragmentDialog1.show(localFragmentManager1, "dialog_photo_sync");
          continue;
          toggleInstantShare(false);
        }
      }
    }
  }

  public final void onInviteMoreClicked()
  {
    inviteMore();
  }

  public final void onLinkClicked(String paramString)
  {
    Context localContext = getSafeContext();
    if (paramString.startsWith("https://plus.google.com/s/%23"))
    {
      String str = "#" + paramString.substring(29);
      startActivity(Intents.getPostSearchActivityIntent(localContext, this.mAccount, str));
    }
    while (true)
    {
      return;
      if (Intents.isProfileUrl(paramString))
      {
        Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", Intents.getPersonIdFromProfileUrl(paramString));
        recordUserAction(OzActions.ONE_UP_SELECT_PERSON, localBundle);
      }
      Intents.viewContent(getActivity(), this.mAccount, paramString);
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onLocationClicked()
  {
    if (this.mEvent.location != null)
      MapUtils.showDrivingDirections(getActivity(), this.mEvent.location);
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    int i = paramMenuItem.getItemId();
    if (i == R.id.edit_event)
      startActivity(Intents.getEditEventActivityIntent(getActivity().getApplicationContext(), this.mAccount, this.mEventId, this.mAuthKey));
    while (true)
    {
      return bool;
      if (i == R.id.delete_event)
      {
        DeleteEventConfirmationDialog localDeleteEventConfirmationDialog = new DeleteEventConfirmationDialog();
        localDeleteEventConfirmationDialog.show(getFragmentManager(), "delete_event_conf");
        localDeleteEventConfirmationDialog.setTargetFragment(this, 0);
      }
      else if (i == R.id.invite_more)
      {
        inviteMore();
      }
      else if (i == R.id.report_abuse)
      {
        recordUserAction(OzActions.ONE_UP_REPORT_ABUSE_ACTIVITY);
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.menu_report_abuse), getString(R.string.event_report_question), getString(R.string.ok), getString(R.string.cancel));
        localAlertFragmentDialog.setTargetFragment(this, 0);
        localAlertFragmentDialog.getArguments().putString("activity_id", this.mActivityId);
        localAlertFragmentDialog.show(getFragmentManager(), "report_event");
      }
      else
      {
        bool = false;
      }
    }
  }

  public final void onPause()
  {
    getActivity().getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    EsService.unregisterListener(this.mListener);
    EventDetailsHeaderView localEventDetailsHeaderView = (EventDetailsHeaderView)getView().findViewById(R.id.event_header_view);
    if (localEventDetailsHeaderView != null)
      localEventDetailsHeaderView.pausePlayback();
    super.onPause();
  }

  public final void onPhotoClicked(String paramString1, String paramString2, String paramString3)
  {
    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoViewActivityIntentBuilder(getActivity());
    localPhotoViewIntentBuilder.setAccount(getAccount());
    if (paramString1 != null)
    {
      if (this.mEvent.name != null);
      for (String str2 = this.mEvent.name; ; str2 = getString(R.string.event_activity_title))
      {
        localPhotoViewIntentBuilder.setAlbumName(str2);
        localPhotoViewIntentBuilder.setPhotoId(Long.valueOf(Long.parseLong(paramString1)));
        localPhotoViewIntentBuilder.setGaiaId(paramString3);
        localPhotoViewIntentBuilder.setEventId(this.mEventId);
        startActivity(localPhotoViewIntentBuilder.build());
        return;
      }
    }
    if (this.mEvent.name != null);
    for (String str1 = this.mEvent.name; ; str1 = getString(R.string.event_activity_title))
    {
      localPhotoViewIntentBuilder.setAlbumName(str1);
      localPhotoViewIntentBuilder.setPhotoUrl(paramString2);
      break;
    }
  }

  public final void onPhotoUpdateNeeded(String paramString1, String paramString2, String paramString3)
  {
    EsService.updateEventPhoto(getSafeContext(), this.mAccount, this.mEventId, paramString1, paramString2, paramString3);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    super.onPrepareActionBar(paramHostActionBar);
    paramHostActionBar.showRefreshButton();
    if ((this.mAccount != null) && (this.mAccount.isPlusPage()));
    for (int i = 1; ; i = 0)
    {
      if ((this.mAccount != null) && (EsEventData.canAddPhotos(this.mEvent, this.mAccount.getGaiaId())) && (this.mActivityId != null) && (i == 0))
        paramHostActionBar.showActionButton(0, R.drawable.icn_events_add_photo, R.string.event_button_add_photo_label);
      if (this.mCanComment)
        paramHostActionBar.showActionButton(1, R.drawable.icn_events_add_comment, R.string.event_button_add_comment_label);
      updateProgressIndicator();
      return;
    }
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if ((this.mEvent != null) && (this.mAccount != null) && (TextUtils.equals(this.mEvent.creatorObfuscatedId, this.mAccount.getGaiaId())));
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
      {
        paramMenu.findItem(R.id.edit_event).setVisible(true);
        paramMenu.findItem(R.id.delete_event).setVisible(true);
      }
      int j = 0;
      if (i == 0)
      {
        String str = this.mActivityId;
        j = 0;
        if (str != null)
        {
          Integer localInteger = this.mReportAbuseRequestId;
          j = 0;
          if (localInteger == null)
            j = 1;
        }
      }
      if ((this.mEventState != null) && (this.mEventState.canInviteOthers))
        paramMenu.findItem(R.id.invite_more).setVisible(true);
      if (j != 0)
        paramMenu.findItem(R.id.report_abuse).setVisible(true);
      return;
    }
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mListener);
    this.mAdapter.checkPartitions("HEF", "OR");
    if ((this.mFetchReqId != null) && (!EsService.isRequestPending(this.mFetchReqId.intValue())))
    {
      ServiceResult localServiceResult5 = EsService.removeResult(this.mFetchReqId.intValue());
      handleGetEventUpdatesComplete(this.mFetchReqId.intValue(), localServiceResult5);
      this.mFetchReqId = null;
    }
    if ((this.mSendRsvpReqId != null) && (!EsService.isRequestPending(this.mSendRsvpReqId.intValue())))
    {
      ServiceResult localServiceResult4 = EsService.removeResult(this.mSendRsvpReqId.intValue());
      handleSendEventRsvpComplete(this.mSendRsvpReqId.intValue(), localServiceResult4);
      this.mSendRsvpReqId = null;
    }
    if ((this.mCommentReqId != null) && (!EsService.isRequestPending(this.mCommentReqId.intValue())))
    {
      ServiceResult localServiceResult3 = EsService.removeResult(this.mCommentReqId.intValue());
      handleCreateCommentComplete(this.mCommentReqId.intValue(), localServiceResult3);
      this.mCommentReqId = null;
    }
    processPendingPhotoRequest();
    if ((this.mInviteReqId != null) && (!EsService.isRequestPending(this.mInviteReqId.intValue())))
    {
      ServiceResult localServiceResult2 = EsService.removeResult(this.mInviteReqId.intValue());
      handleInviteMoreComplete(this.mInviteReqId.intValue(), localServiceResult2);
      this.mInviteReqId = null;
    }
    if ((this.mReportAbuseRequestId != null) && (!EsService.isRequestPending(this.mReportAbuseRequestId.intValue())))
    {
      ServiceResult localServiceResult1 = EsService.removeResult(this.mReportAbuseRequestId.intValue());
      handleReportEventCallback(this.mReportAbuseRequestId.intValue(), localServiceResult1);
      this.mReportAbuseRequestId = null;
    }
    getActivity().getContentResolver().registerContentObserver(InstantUploadFacade.SETTINGS_URI, false, this.mSettingsObserver);
  }

  public final void onRsvpChanged(String paramString)
  {
    if ((this.mEvent == null) || (!TextUtils.equals(paramString, EsEventData.getRsvpType(this.mEvent))))
    {
      this.mSendRsvpReqId = Integer.valueOf(EsService.sendEventRsvp(getActivity(), this.mAccount, this.mEventId, this.mAuthKey, paramString));
      this.mTemporalRsvpState = paramString;
      if (this.mEvent != null)
      {
        updateActiveEventState();
        updateRsvpSection();
      }
      if (!TextUtils.isEmpty(this.mIncomingRsvpType))
      {
        ProgressFragmentDialog.newInstance(null, getString(R.string.event_send_rsvp), false).show(getFragmentManager(), "send_rsvp");
        this.mIncomingRsvpType = null;
      }
    }
    this.mHasUserInteracted = true;
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("id", this.mEventId);
    paramBundle.putInt("typeid", this.mTypeId);
    paramBundle.putString("invitation_token", this.mInvitationToken);
    paramBundle.putString("incoming_rsvp_type", this.mIncomingRsvpType);
    paramBundle.putBoolean("refresh", this.mNeedsRefresh);
    paramBundle.putBoolean("expanded", this.mEventState.expanded);
    paramBundle.putLong("first_timestamp", this.mFirstActivityTimestamp);
    if (this.mGridView != null)
      paramBundle.putInt("scroll_pos", this.mGridView.getFirstVisiblePosition());
    if (this.mFetchReqId != null)
      paramBundle.putInt("fetch_req_id", this.mFetchReqId.intValue());
    if (this.mSendRsvpReqId != null)
      paramBundle.putInt("rsvp_req_id", this.mSendRsvpReqId.intValue());
    if (this.mTemporalRsvpState != null)
      paramBundle.putString("temp_rsvp_state", this.mTemporalRsvpState);
    if (this.mCommentReqId != null)
      paramBundle.putInt("comment_req_id", this.mCommentReqId.intValue());
    if (this.mNewPhotoReqId != null)
      paramBundle.putInt("new_photo_req_id", this.mNewPhotoReqId.intValue());
    if (this.mInviteReqId != null)
      paramBundle.putInt("invite_more_req_id", this.mInviteReqId.intValue());
    if (this.mDeleteReqId != null)
      paramBundle.putInt("delete_req_id", this.mDeleteReqId.intValue());
    if (this.mReportAbuseRequestId != null)
      paramBundle.putInt("abuse_request_id", this.mReportAbuseRequestId.intValue());
    this.mAdapter.checkPartitions("HEF", "ON");
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mEventId = paramBundle.getString("event_id");
    this.mInvitationToken = paramBundle.getString("invitation_token");
    this.mAuthKey = paramBundle.getString("auth_key");
    this.mIncomingRsvpType = paramBundle.getString("rsvp");
    this.mTypeId = paramBundle.getInt("notif_type");
  }

  public final void onUpdateCardClicked(EventUpdate paramEventUpdate)
  {
    FragmentManager localFragmentManager = getFragmentManager();
    if (localFragmentManager.findFragmentByTag("update_card") != null);
    while (true)
    {
      return;
      EventUpdateDialog localEventUpdateDialog = EventUpdateDialog.newInstance();
      localEventUpdateDialog.setUpdate(paramEventUpdate);
      localEventUpdateDialog.setTargetFragment(this, 0);
      localEventUpdateDialog.show(localFragmentManager, "update_card");
    }
  }

  public final void onViewAllInviteesClicked()
  {
    FragmentActivity localFragmentActivity = getActivity();
    EsAccount localEsAccount = getAccount();
    String str1 = this.mEventId;
    String str2 = this.mAuthKey;
    if (this.mEvent != null);
    for (String str3 = this.mEvent.creatorObfuscatedId; ; str3 = null)
    {
      startActivity(Intents.getEventInviteeListActivityIntent(localFragmentActivity, localEsAccount, str1, str2, str3));
      return;
    }
  }

  public final void onViewUsed(int paramInt)
  {
    if ((this.mPreloadRequested) || (this.mResumeToken == null) || (this.mError) || (this.mGridView == null));
    while (true)
    {
      return;
      if (paramInt >= this.mAdapter.getCount() - mNextPagePreloadTriggerRows)
      {
        this.mPreloadRequested = true;
        this.mGridView.post(new Runnable()
        {
          public final void run()
          {
            if (!HostedEventFragment.this.isPaused())
              HostedEventFragment.access$1300(HostedEventFragment.this);
          }
        });
      }
    }
  }

  public final void refresh()
  {
    super.refresh();
    fetchData();
  }

  public static abstract interface ActivityQuery
  {
    public static final String[] PROJECTION = { "_id", "type", "owner_gaia_id", "owner_name", "timestamp", "data", "url", "comment", "fingerprint" };
  }

  public static class DeleteEventConfirmationDialog extends DialogFragment
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
        if ((getTargetFragment() instanceof HostedEventFragment))
        {
          HostedEventFragment.access$1700((HostedEventFragment)getTargetFragment());
          continue;
          paramDialogInterface.dismiss();
        }
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      FragmentActivity localFragmentActivity = getActivity();
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
      localBuilder.setMessage(localFragmentActivity.getString(R.string.delete_event_dialog_message));
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      return localBuilder.create();
    }
  }

  public static abstract interface DetailsQuery
  {
    public static final String[] PROJECTION = { "_id", "event_data", "polling_token", "resume_token", "activity_id", "can_comment", "source" };
  }

  public static enum DialogType
  {
    static
    {
      ON_AIR = new DialogType("ON_AIR", 1);
      PUBLIC = new DialogType("PUBLIC", 2);
      DialogType[] arrayOfDialogType = new DialogType[3];
      arrayOfDialogType[0] = PRIVATE;
      arrayOfDialogType[1] = ON_AIR;
      arrayOfDialogType[2] = PUBLIC;
    }
  }

  public static abstract interface EventPeopleQuery
  {
    public static final String[] PROJECTION = { "_id", "gaia_id", "person_id", "name", "avatar" };
  }

  private final class EventRefreshRunnable
    implements Runnable
  {
    private EventRefreshRunnable()
    {
    }

    public final void run()
    {
      if (HostedEventFragment.this.getActivity() == null);
      while (true)
      {
        return;
        if (!HostedEventFragment.this.isPaused())
          HostedEventFragment.this.getLoaderManager().restartLoader(0, null, HostedEventFragment.this);
      }
    }
  }

  public static class InstantShareConfirmationDialog extends AlertFragmentDialog
  {
    private CheckBox mCheckinButton;
    private HostedEventFragment.DialogType mDialogType = HostedEventFragment.DialogType.PRIVATE;
    private boolean mFirstTime;
    private boolean mHasCheckedIn;

    public InstantShareConfirmationDialog()
    {
    }

    public InstantShareConfirmationDialog(boolean paramBoolean, HostedEventFragment.DialogType paramDialogType)
    {
      this.mHasCheckedIn = paramBoolean;
      this.mDialogType = paramDialogType;
    }

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
        Fragment localFragment = getTargetFragment();
        if ((localFragment instanceof HostedEventFragment))
        {
          ((HostedEventFragment)localFragment).turnOnInstantShare(this.mCheckinButton.isChecked(), this.mFirstTime);
          continue;
          dismiss();
        }
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      if (paramBundle != null)
      {
        this.mHasCheckedIn = paramBundle.getBoolean("has_checked_in_id", false);
        this.mFirstTime = paramBundle.getBoolean("first_time_id", false);
        this.mDialogType = HostedEventFragment.DialogType.valueOf(paramBundle.getString("dialog_type"));
      }
      Context localContext = getDialogContext();
      Resources localResources = localContext.getResources();
      View localView = LayoutInflater.from(localContext).inflate(R.layout.event_instant_share_dialog_view, null);
      this.mCheckinButton = ((CheckBox)localView.findViewById(R.id.checkin));
      CheckBox localCheckBox = this.mCheckinButton;
      int i;
      TextView localTextView1;
      int j;
      if (this.mHasCheckedIn)
      {
        i = 0;
        localCheckBox.setVisibility(i);
        localTextView1 = (TextView)localView.findViewById(R.id.dialog_content);
        switch (HostedEventFragment.11.$SwitchMap$com$google$android$apps$plus$fragments$HostedEventFragment$DialogType[this.mDialogType.ordinal()])
        {
        default:
          j = R.string.event_instant_share_dialog_content;
        case 1:
        case 2:
        }
      }
      while (true)
      {
        localTextView1.setText(j);
        TextView localTextView2 = (TextView)localView.findViewById(R.id.link);
        localTextView2.setText(Html.fromHtml(localResources.getString(R.string.event_instant_share_dialog_learn_more)));
        localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
        int k = R.string.event_instant_share_dialog_positive;
        SharedPreferences localSharedPreferences = localContext.getSharedPreferences("event", 0);
        boolean bool1 = localSharedPreferences.contains("hasUsedInstantShare");
        boolean bool2 = false;
        if (!bool1)
          bool2 = true;
        this.mFirstTime = bool2;
        if (this.mFirstTime)
        {
          localSharedPreferences.edit().putBoolean("hasUsedInstantShare", true).commit();
          k = R.string.event_instant_share_dialog_positive_first;
        }
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContext);
        localBuilder.setTitle(R.string.event_instant_share_dialog_title).setView(localView).setPositiveButton(k, this).setNegativeButton(R.string.cancel, this);
        return localBuilder.create();
        i = 8;
        break;
        j = R.string.event_instant_share_on_air_dialog_content;
        continue;
        j = R.string.event_instant_share_public_dialog_content;
      }
    }

    public final void onSaveInstanceState(Bundle paramBundle)
    {
      super.onSaveInstanceState(paramBundle);
      paramBundle.putBoolean("has_checked_in_id", this.mHasCheckedIn);
      paramBundle.putBoolean("first_time_id", this.mFirstTime);
      paramBundle.putString("dialog_type", this.mDialogType.name());
    }
  }

  final class InstantShareToggle extends AsyncTask<Object, Void, Void>
  {
    private Activity mActivity;
    private boolean mEnabled;

    InstantShareToggle()
    {
    }
  }

  private final class SettingsLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<String>
  {
    private SettingsLoaderCallbacks()
    {
    }

    public final Loader<String> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      return new EsAsyncTaskLoader(HostedEventFragment.this.getActivity())
      {
        protected final void onStartLoading()
        {
          forceLoad();
        }
      };
    }

    public final void onLoaderReset(Loader<String> paramLoader)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedEventFragment
 * JD-Core Version:    0.6.2
 */