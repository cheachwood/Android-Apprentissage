package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Rfc822Token;
import android.text.util.Rfc822Tokenizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsPeopleData.ProfileAndContactData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.ComposeBarController;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.phone.Intents.PhotosIntentBuilder;
import com.google.android.apps.plus.phone.ProfileActivity;
import com.google.android.apps.plus.phone.ProfileStreamAdapter;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.phone.StreamAdapter;
import com.google.android.apps.plus.phone.StreamAdapter.StreamQuery;
import com.google.android.apps.plus.phone.StreamAdapter.ViewUseListener;
import com.google.android.apps.plus.phone.StreamTranslationAdapter;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.android.apps.plus.util.MapUtils;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.ProfileAboutView.OnClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HostedProfileFragment extends HostedStreamFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, BlockFragment.Listener, BlockPersonDialog.PersonBlocker, ChoosePhotoDialog.PhotoHandler, UnblockPersonDialog.PersonUnblocker, ProfileAboutView.OnClickListener
{
  private ProfileMergeCursor mActiveProfileCursor;
  private boolean mBlockInProgress;
  private int mChoosePhotoTarget;
  private final DataSetObserver mCircleContentObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      HostedProfileFragment.this.mProfileAdapter.updateCircleList();
    }
  };
  private CircleNameResolver mCircleNameResolver;
  private Context mContext;
  private boolean mControlPrimarySpinner = true;
  private int mCurrentSpinnerPosition = -1;
  private final Handler mHandler = new Handler();
  private boolean mHasGaiaId;
  private Integer mInsertCameraPhotoRequestId;
  private boolean mIsBlocked;
  private boolean mIsMute;
  private boolean mIsMyProfile;
  private boolean mIsPlusPage;
  private boolean mLandscape;
  private Integer mMuteRequestId;
  private boolean mMuteRequestIsMuted;
  private String mPersonId;
  private Integer mPlusOneRequestId;
  private ProfileStreamAdapter mProfileAdapter;
  private final LoaderManager.LoaderCallbacks<EsPeopleData.ProfileAndContactData> mProfileAndContactDataLoader = new LoaderManager.LoaderCallbacks()
  {
    public final Loader<EsPeopleData.ProfileAndContactData> onCreateLoader(int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "Loader<ProfileAndContactData> onCreateLoader()");
      return new ProfileLoader(HostedProfileFragment.this.getActivity(), HostedProfileFragment.this.mAccount, paramAnonymousBundle.getString("person_id"), true);
    }

    public final void onLoaderReset(Loader<EsPeopleData.ProfileAndContactData> paramAnonymousLoader)
    {
    }
  };
  private boolean mProfileAndContactLoaderActive = true;
  private boolean mProfileIsExpanded;
  private Integer mProfilePendingRequestId;
  private final EsServiceListener mProfileServiceListener = new EsServiceListener()
  {
    public final void onCreateProfilePlusOneRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedProfileFragment.this.handlePlusOneCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onDeleteProfilePlusOneRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedProfileFragment.this.handlePlusOneCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onGetProfileAndContactComplete$63505a2b(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onGetProfileAndContactComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handleProfileServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onInsertCameraPhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onInsertCameraPhotoComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handlerInsertCameraPhoto$b5e9bbb(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onReportAbuseRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HostedProfileFragment.this.handleReportAbuseCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSetCircleMembershipComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onSetCircleMembershipComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handleProfileServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSetCoverPhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onSetCoverPhotoComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handleCoverPhotoCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSetMutedRequestComplete$4cb07f77(int paramAnonymousInt, boolean paramAnonymousBoolean, ServiceResult paramAnonymousServiceResult)
    {
      HostedProfileFragment.this.handleSetMutedCallback(paramAnonymousInt, paramAnonymousBoolean, paramAnonymousServiceResult);
    }

    public final void onSetScrapbookInfoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onSetCoverPhotoComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handleCoverPhotoCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onUploadCoverPhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onUploadCoverPhotoComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handleCoverPhotoCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onUploadProfilePhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "onUploadProfilePhotoComplete(); requestId=" + paramAnonymousInt);
      HostedProfileFragment.this.handleProfileServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
      if ((paramAnonymousServiceResult != null) && (!paramAnonymousServiceResult.hasError()) && (paramAnonymousServiceResult.getException() == null))
      {
        HostedProfileFragment.access$002(HostedProfileFragment.this, EsService.getProfileAndContact(HostedProfileFragment.this.getActivity(), HostedProfileFragment.this.mAccount, HostedProfileFragment.this.mPersonId, true));
        HostedProfileFragment.this.updateSpinner();
      }
    }
  };
  private Integer mReportAbuseRequestId;
  private Integer mSetCoverPhotoRequestId;

  private boolean canShowConversationActions()
  {
    if ((!this.mProfileAndContactLoaderActive) && (!this.mIsMyProfile) && (!this.mIsPlusPage) && (!this.mAccount.isPlusPage()) && (!this.mIsBlocked) && (!this.mBlockInProgress) && (!this.mError));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private boolean canShowRefreshInActionBar()
  {
    if ((ScreenMetrics.getInstance(this.mContext).screenDisplayType != 0) || (this.mLandscape));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void handleCoverPhotoCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mSetCoverPhotoRequestId == null) || (this.mSetCoverPhotoRequestId.intValue() != paramInt))
      return;
    DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
    if ((paramServiceResult == null) || (paramServiceResult.hasError()) || (paramServiceResult.getException() != null))
      Toast.makeText(this.mContext, R.string.transient_server_error, 0).show();
    while (true)
    {
      this.mSetCoverPhotoRequestId = null;
      updateSpinner();
      break;
      this.mProfilePendingRequestId = EsService.getProfileAndContact(getActivity(), this.mAccount, this.mPersonId, true);
      updateSpinner();
    }
  }

  private void handlerInsertCameraPhoto$b5e9bbb(int paramInt)
  {
    int i = 2;
    if ((this.mInsertCameraPhotoRequestId == null) || (this.mInsertCameraPhotoRequestId.intValue() != paramInt));
    String str;
    FragmentActivity localFragmentActivity;
    while (true)
    {
      return;
      str = EsService.getLastCameraMediaLocation();
      localFragmentActivity = getActivity();
      if (str != null)
        break;
      Toast.makeText(getActivity(), getString(R.string.camera_photo_error), 1).show();
      if ((localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
        ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).hideInsertCameraPhotoDialog();
      this.mInsertCameraPhotoRequestId = null;
      updateSpinner();
    }
    MediaRef localMediaRef = new MediaRef(null, 0L, null, Uri.parse(str), MediaRef.MediaType.IMAGE);
    int j;
    switch (this.mChoosePhotoTarget)
    {
    default:
      j = i;
      i = 1;
    case 2:
    }
    while (true)
    {
      startActivityForResult(Intents.getPhotoPickerIntent(localFragmentActivity, this.mAccount, localFragmentActivity.getString(R.string.change_photo_crop_title), localMediaRef, i), j);
      break;
      j = 5;
    }
  }

  private boolean isDialogVisible(String paramString)
  {
    if ((DialogFragment)getFragmentManager().findFragmentByTag(paramString) != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void launchEditActivity(int paramInt, String paramString1, String paramString2)
  {
    startActivityForResult(Intents.getProfileEditActivityIntent(getActivity(), getAccount(), paramInt, paramString1, paramString2), 7);
    EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.PROFILE_EDIT_START, getViewForLogging());
  }

  private void safeStartActivity(Intent paramIntent)
  {
    try
    {
      startActivity(paramIntent);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      while (true)
        if (EsLog.isLoggable("HostedProfileFragment", 6))
          Log.e("HostedProfileFragment", "Cannot launch activity: " + paramIntent, localActivityNotFoundException);
    }
  }

  private void setPersonBlocked(boolean paramBoolean)
  {
    BlockFragment localBlockFragment = BlockFragment.getInstance(this.mContext, this.mAccount, this.mPersonId, this.mProfileAdapter.getFullName(), this.mProfileAdapter.isPlusPage(), paramBoolean);
    localBlockFragment.setTargetFragment(this, 0);
    localBlockFragment.show(getActivity());
    this.mBlockInProgress = true;
    this.mProfileAdapter.beginBlockInProgress();
  }

  private void showChooseCoverPhotoDialog()
  {
    if (isDialogVisible("change_photo"))
      return;
    ChoosePhotoDialog localChoosePhotoDialog = new ChoosePhotoDialog(R.string.change_cover_photo_dialog_title);
    localChoosePhotoDialog.setIsCameraSupported(Intents.isCameraIntentRegistered(this.mContext));
    boolean bool;
    if (this.mProfileAdapter.getScrapbookAlbumId() != null)
    {
      bool = true;
      label45: if (!this.mProfileAdapter.hasCoverPhotoUpgrade())
        break label95;
    }
    label95: for (Long localLong = this.mProfileAdapter.getScrapbookCoverPhotoId(); ; localLong = null)
    {
      localChoosePhotoDialog.setIsForCoverPhoto(true, bool, localLong);
      localChoosePhotoDialog.setTargetFragment(this, 0);
      localChoosePhotoDialog.show(getFragmentManager(), "change_photo");
      break;
      bool = false;
      break label45;
    }
  }

  private void updateCoverPhoto(String paramString, int paramInt)
  {
    String str = this.mProfileAdapter.getScrapbookLayout();
    this.mSetCoverPhotoRequestId = Integer.valueOf(EsService.setScrapbookInfo(getActivity(), this.mAccount, paramString, paramInt, str));
    showProgressDialog(R.string.setting_cover_photo);
  }

  public final void blockPerson(Serializable paramSerializable)
  {
    setPersonBlocked(true);
  }

  protected final StreamAdapter createStreamAdapter(Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, final ItemClickListener paramItemClickListener, StreamAdapter.ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    return new ProfileStreamAdapter(paramContext, paramColumnGridView, paramEsAccount, paramOnClickListener, new ItemClickListener()
    {
      public final void onSpanClick(URLSpan paramAnonymousURLSpan)
      {
        paramItemClickListener.onSpanClick(paramAnonymousURLSpan);
      }

      public final void onUserImageClick(String paramAnonymousString1, String paramAnonymousString2)
      {
      }
    }
    , paramViewUseListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener, null);
  }

  public final void doPickPhotoFromAlbums(int paramInt)
  {
    if (this.mChoosePhotoTarget == 1)
    {
      Intents.PhotosIntentBuilder localPhotosIntentBuilder4 = Intents.newAlbumsActivityIntentBuilder(getActivity());
      localPhotosIntentBuilder4.setAccount(this.mAccount).setPersonId(this.mAccount.getPersonId()).setPhotosHome(Boolean.valueOf(true)).setShowCameraAlbum(Boolean.valueOf(true)).setPhotoPickerMode(Integer.valueOf(1)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_profile)).setCropMode(Integer.valueOf(1));
      startActivityForResult(localPhotosIntentBuilder4.build(), 3);
    }
    while (true)
    {
      return;
      if (this.mChoosePhotoTarget == 2)
        switch (paramInt)
        {
        default:
          break;
        case 0:
          Intents.PhotosIntentBuilder localPhotosIntentBuilder3 = Intents.newAlbumsActivityIntentBuilder(getActivity());
          localPhotosIntentBuilder3.setAccount(this.mAccount).setPersonId(this.mAccount.getPersonId()).setPhotosHome(Boolean.valueOf(true)).setShowCameraAlbum(Boolean.valueOf(true)).setPhotoPickerMode(Integer.valueOf(1)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_cover_photo)).setCropMode(Integer.valueOf(2));
          startActivityForResult(localPhotosIntentBuilder3.build(), 5);
          break;
        case 1:
          Intents.PhotosIntentBuilder localPhotosIntentBuilder2 = Intents.newPhotosActivityIntentBuilder(getActivity());
          localPhotosIntentBuilder2.setAccount(this.mAccount).setGaiaId("115239603441691718952").setAlbumId("5745127577944303633").setPhotoPickerMode(Integer.valueOf(1)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_cover_photo)).setCropMode(Integer.valueOf(2));
          startActivityForResult(localPhotosIntentBuilder2.build(), 5);
          break;
        case 2:
          Intents.PhotosIntentBuilder localPhotosIntentBuilder1 = Intents.newPhotosActivityIntentBuilder(getActivity());
          localPhotosIntentBuilder1.setAccount(this.mAccount).setGaiaId(this.mAccount.getGaiaId()).setPersonId(this.mAccount.getPersonId()).setAlbumId(this.mProfileAdapter.getScrapbookAlbumId()).setPhotoPickerMode(Integer.valueOf(1)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_cover_photo)).setCropMode(Integer.valueOf(2));
          startActivityForResult(localPhotosIntentBuilder1.build(), 5);
        }
    }
  }

  public final void doRepositionCoverPhoto()
  {
    MediaRef localMediaRef = new MediaRef(this.mProfileAdapter.getScrapbookCoverPhotoOwnerId(), this.mProfileAdapter.getScrapbookCoverPhotoId().longValue(), this.mProfileAdapter.getScrapbookCoverPhotoUrl(), null, MediaRef.MediaType.IMAGE);
    Intent localIntent = Intents.getPhotoPickerIntent(getActivity(), this.mAccount, null, localMediaRef, 2);
    localIntent.putExtra("top_offset", this.mProfileAdapter.getScrapbookCoverPhotoOffset());
    startActivityForResult(localIntent, 6);
  }

  protected final void doShowEmptyView(View paramView, String paramString)
  {
  }

  protected final void doShowEmptyViewProgress(View paramView)
  {
  }

  public final void doTakePhoto()
  {
    try
    {
      if (this.mChoosePhotoTarget == 1);
      for (int i = 1; ; i = 4)
      {
        getActivity();
        startActivityForResult(Intents.getCameraIntentPhoto$3a35108a("camera-profile.jpg"), i);
        return;
      }
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      while (true)
        Toast.makeText(getActivity(), R.string.change_photo_no_camera, 1).show();
    }
  }

  public final Bundle getExtrasForLogging()
  {
    if (!TextUtils.isEmpty(this.mGaiaId));
    for (Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", this.mGaiaId); ; localBundle = null)
      return localBundle;
  }

  public final OzViews getViewForLogging()
  {
    if ((this.mProfileAdapter != null) && (this.mProfileAdapter.getViewIsExpanded()));
    for (OzViews localOzViews = OzViews.PROFILE; ; localOzViews = OzViews.LOOP_USER)
      return localOzViews;
  }

  protected final void handlePlusOneCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPlusOneRequestId == null) || (this.mPlusOneRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPlusOneRequestId = null;
      updateSpinner();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(this.mContext, R.string.transient_server_error, 0).show();
    }
  }

  protected final void handleProfileServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mProfilePendingRequestId == null) || (this.mProfilePendingRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if ((paramServiceResult == null) || (paramServiceResult.hasError()) || (paramServiceResult.getException() != null))
        Toast.makeText(this.mContext, R.string.transient_server_error, 0).show();
      this.mProfilePendingRequestId = null;
      updateSpinner();
    }
  }

  protected final void handleReportAbuseCallback(int paramInt, ServiceResult paramServiceResult)
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
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(this.mContext, R.string.transient_server_error, 0).show();
      else
        Toast.makeText(this.mContext, R.string.report_abuse_completed_toast, 0).show();
    }
  }

  protected final void handleSetMutedCallback(int paramInt, boolean paramBoolean, ServiceResult paramServiceResult)
  {
    if ((this.mMuteRequestId == null) || (this.mMuteRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mMuteRequestId = null;
      updateSpinner();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(this.mContext, R.string.transient_server_error, 0).show();
      else if (paramBoolean)
        Toast.makeText(this.mContext, R.string.report_mute_completed_toast, 0).show();
      else
        Toast.makeText(this.mContext, R.string.report_unmute_completed_toast, 0).show();
    }
  }

  protected final void initCirclesLoader()
  {
  }

  protected final boolean isAdapterEmpty()
  {
    int i = 1;
    if (this.mAdapter.getCount() == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  protected final boolean isLocalDataAvailable(Cursor paramCursor)
  {
    int i = 1;
    if ((paramCursor != null) && (paramCursor.getCount() > i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((super.isProgressIndicatorVisible()) || (this.mProfileAndContactLoaderActive) || (this.mProfilePendingRequestId != null) || (this.mPlusOneRequestId != null) || (this.mReportAbuseRequestId != null) || (this.mInsertCameraPhotoRequestId != null) || (this.mMuteRequestId != null) || (this.mSetCoverPhotoRequestId != null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean needsAsyncData()
  {
    return true;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    Intent localIntent = null;
    switch (paramInt)
    {
    default:
      if (localIntent != null)
        safeStartActivity(localIntent);
      return;
    case 0:
      String str4 = this.mProfileAdapter.getFullName();
      String str5 = null;
      if (this.mPersonId.startsWith("e:"));
      for (String str6 = this.mPersonId.substring(2); ; str6 = null)
      {
        AudienceData localAudienceData2 = new AudienceData(new PersonData(str5, str4, str6));
        localIntent = Intents.getNewConversationActivityIntent(this.mContext, getAccount(), localAudienceData2);
        break;
        boolean bool2 = this.mHasGaiaId;
        localIntent = null;
        if (!bool2)
          break;
        str5 = this.mGaiaId;
      }
    case 1:
    }
    String str1 = this.mProfileAdapter.getFullName();
    String str2 = null;
    if (this.mPersonId.startsWith("e:"));
    for (String str3 = this.mPersonId.substring(2); ; str3 = null)
    {
      AudienceData localAudienceData1 = new AudienceData(new PersonData(str2, str1, str3));
      localIntent = Intents.getNewHangoutActivityIntent(this.mContext, getAccount(), true, localAudienceData1);
      break;
      boolean bool1 = this.mHasGaiaId;
      localIntent = null;
      if (!bool1)
        break;
      str2 = this.mGaiaId;
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1)
      if (paramInt1 == 7)
        EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.PROFILE_EDIT_CANCEL, getViewForLogging());
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 0:
        final ArrayList localArrayList1 = paramIntent.getExtras().getStringArrayList("original_circle_ids");
        final ArrayList localArrayList2 = paramIntent.getExtras().getStringArrayList("selected_circle_ids");
        this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            HostedProfileFragment.this.setCircleMembership(localArrayList1, localArrayList2);
          }
        });
        break;
      case 1:
      case 4:
        FragmentActivity localFragmentActivity = getActivity();
        if ((localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
          ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).showInsertCameraPhotoDialog();
        this.mInsertCameraPhotoRequestId = EsService.insertCameraPhoto(localFragmentActivity, this.mAccount, "camera-profile.jpg");
        break;
      case 2:
      case 3:
        if (paramIntent != null)
        {
          final byte[] arrayOfByte2 = paramIntent.getByteArrayExtra("data");
          if (arrayOfByte2 != null)
            this.mHandler.post(new Runnable()
            {
              public final void run()
              {
                HostedProfileFragment.this.setProfilePhoto(arrayOfByte2);
              }
            });
        }
        break;
      case 5:
        if (paramIntent != null)
        {
          final int j = paramIntent.getIntExtra("top_offset", 0);
          final long l = paramIntent.getLongExtra("photo_id", 0L);
          if (l != 0L)
          {
            final boolean bool = paramIntent.getBooleanExtra("is_gallery_photo", false);
            this.mHandler.post(new Runnable()
            {
              public final void run()
              {
                HostedProfileFragment.this.setCoverPhoto(Long.toString(l), bool, this.val$isGalleryPhoto);
              }
            });
            if (bool)
              EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.COVER_PHOTO_CHOOSE_GALLERY, getViewForLogging());
            else
              EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.COVER_PHOTO_CHOOSE_OWN_PHOTO, getViewForLogging());
          }
          else
          {
            final byte[] arrayOfByte1 = paramIntent.getByteArrayExtra("data");
            if (arrayOfByte1 != null)
              this.mHandler.post(new Runnable()
              {
                public final void run()
                {
                  HostedProfileFragment.this.setCoverPhoto(arrayOfByte1, j);
                }
              });
            EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.COVER_PHOTO_CHOOSE_OWN_PHOTO, getViewForLogging());
          }
        }
        break;
      case 6:
        int i = paramIntent.getIntExtra("top_offset", 0);
        updateCoverPhoto(Long.toString(paramIntent.getLongExtra("photo_id", 0L)), i);
        EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.COVER_PHOTO_REPOSITION, getViewForLogging());
        break;
      case 7:
        this.mProfilePendingRequestId = EsService.getProfileAndContact(getActivity(), this.mAccount, this.mPersonId, true);
        updateSpinner();
        EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.PROFILE_EDIT_SAVE, getViewForLogging());
      }
    }
  }

  public final void onAddressClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Uri localUri = Uri.parse("geo:0,0?q=" + Uri.encode(paramString));
      MapUtils.launchMapsActivity(getActivity(), localUri);
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mContext = paramActivity;
    if (paramActivity.getResources().getConfiguration().orientation == 2);
    for (boolean bool = true; ; bool = false)
    {
      this.mLandscape = bool;
      return;
    }
  }

  public final void onAvatarClicked()
  {
    if (this.mIsMyProfile)
    {
      this.mChoosePhotoTarget = 1;
      if (!isDialogVisible("change_photo"))
      {
        ChoosePhotoDialog localChoosePhotoDialog = new ChoosePhotoDialog(R.string.change_photo_dialog_title);
        localChoosePhotoDialog.setIsCameraSupported(Intents.isCameraIntentRegistered(this.mContext));
        localChoosePhotoDialog.setTargetFragment(this, 0);
        localChoosePhotoDialog.show(getFragmentManager(), "change_photo");
      }
    }
    while (true)
    {
      return;
      startActivity(Intents.newPhotosActivityIntentBuilder(this.mContext).setAccount(this.mAccount).setGaiaId(EsPeopleData.extractGaiaId(this.mPersonId)).setAlbumName(getString(R.string.profile_photos_stream_title)).setStreamId("profile").build());
    }
  }

  public final void onBlockCompleted(boolean paramBoolean)
  {
    this.mBlockInProgress = false;
    this.mProfileAdapter.endBlockInProgress(paramBoolean);
    invalidateActionBar();
  }

  public final void onCirclesButtonClicked()
  {
    startActivityForResult(Intents.getCircleMembershipActivityIntent(getActivity(), this.mAccount, this.mPersonId, null, true), 0);
  }

  public final void onCoverPhotoClicked(int paramInt)
  {
    if (this.mIsMyProfile)
    {
      this.mChoosePhotoTarget = 2;
      if (this.mProfileAdapter.hasCoverPhotoUpgrade())
      {
        EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.COVER_PHOTO_CHANGE, getViewForLogging());
        showChooseCoverPhotoDialog();
      }
    }
    while (true)
    {
      return;
      EsAnalytics.recordActionEvent(getActivity(), this.mAccount, OzActions.COVER_PHOTO_UPGRADE_START, getViewForLogging());
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.upgrade_to_cover_photo_dialog_title), getString(R.string.upgrade_to_cover_photo_dialog_content), getString(R.string.upgrade_to_cover_photo_dialog_confirm), getString(R.string.cancel));
      localAlertFragmentDialog.setListener(new AlertFragmentDialog.AlertDialogListener()
      {
        public final void onDialogCanceled$20f9a4b7(String paramAnonymousString)
        {
        }

        public final void onDialogListClick$12e92030(int paramAnonymousInt, Bundle paramAnonymousBundle)
        {
        }

        public final void onDialogNegativeClick$20f9a4b7(String paramAnonymousString)
        {
        }

        public final void onDialogPositiveClick(Bundle paramAnonymousBundle, String paramAnonymousString)
        {
          HostedProfileFragment.this.showChooseCoverPhotoDialog();
        }
      });
      localAlertFragmentDialog.show(getFragmentManager(), "cover_photo_upgrade");
      continue;
      startActivity(Intents.newPhotoViewActivityIntentBuilder(this.mContext).setAccount(this.mAccount).setGaiaId(this.mGaiaId).setAlbumName(getString(R.string.profile_cover_photos_stream_title)).setAlbumId(this.mProfileAdapter.getScrapbookAlbumId()).setPhotoId(this.mProfileAdapter.getScrapbookPhotoId(paramInt)).build());
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("profile_request_id"))
        this.mProfilePendingRequestId = Integer.valueOf(paramBundle.getInt("profile_request_id"));
      if (paramBundle.containsKey("plusone_request_id"))
        this.mPlusOneRequestId = Integer.valueOf(paramBundle.getInt("plusone_request_id"));
      if (paramBundle.containsKey("abuse_request_id"))
        this.mReportAbuseRequestId = Integer.valueOf(paramBundle.getInt("abuse_request_id"));
      if (paramBundle.containsKey("mute_request_id"))
      {
        this.mMuteRequestId = Integer.valueOf(paramBundle.getInt("mute_request_id"));
        this.mMuteRequestIsMuted = paramBundle.getBoolean("mute_state");
      }
      if (paramBundle.containsKey("camera_request_id"))
        this.mInsertCameraPhotoRequestId = Integer.valueOf(paramBundle.getInt("camera_request_id"));
      if (paramBundle.containsKey("cover_photo_request_id"))
        this.mSetCoverPhotoRequestId = Integer.valueOf(paramBundle.getInt("cover_photo_request_id"));
      if (paramBundle.containsKey("block_in_progress"))
        this.mBlockInProgress = paramBundle.getBoolean("block_in_progress");
      if (paramBundle.containsKey("profile_is_expanded"))
        this.mProfileIsExpanded = paramBundle.getBoolean("profile_is_expanded");
      if (paramBundle.containsKey("choose_photo_target"))
        this.mChoosePhotoTarget = paramBundle.getInt("choose_photo_target");
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("person_id", this.mPersonId);
    getLoaderManager().initLoader(100, localBundle, this.mProfileAndContactDataLoader);
    this.mCircleNameResolver = new CircleNameResolver(this.mContext, getLoaderManager(), this.mAccount);
    this.mCircleNameResolver.registerObserver(this.mCircleContentObserver);
    this.mCircleNameResolver.initLoader();
    getLoaderManager().initLoader(2, null, this);
    fetchStreamContent(true);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    StringBuilder localStringBuilder;
    if (Log.isLoggable("HostedProfileFragment", 3))
    {
      localStringBuilder = new StringBuilder("Loader<Cursor> onCreateLoader() -- ");
      if (paramInt != 3)
        break label52;
    }
    label52: for (Object localObject = "POSTS_LOADER_ID"; ; localObject = Integer.valueOf(paramInt))
    {
      Log.d("HostedProfileFragment", localObject);
      return super.onCreateLoader(paramInt, paramBundle);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mProfileAdapter = ((ProfileStreamAdapter)this.mInnerAdapter);
    ProfileStreamAdapter localProfileStreamAdapter = this.mProfileAdapter;
    String str = this.mPersonId;
    boolean bool1 = this.mIsMyProfile;
    boolean bool2 = this.mHasGaiaId;
    Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.fromParts("sms", "", null));
    if (getActivity().getPackageManager().queryIntentActivities(localIntent, 65536).size() > 0);
    for (boolean bool3 = true; ; bool3 = false)
    {
      localProfileStreamAdapter.init(str, bool1, bool2, bool3, this.mCircleNameResolver);
      this.mProfileAdapter.setOnClickListener(this);
      if (this.mBlockInProgress)
        this.mProfileAdapter.beginBlockInProgress();
      this.mProfileAdapter.setViewIsExpanded(this.mProfileIsExpanded);
      return localView;
    }
  }

  public final void onEditEducationClicked()
  {
    launchEditActivity(2, this.mProfileAdapter.getEducationList(), this.mProfileAdapter.getSharingRosterData());
  }

  public final void onEditEmploymentClicked()
  {
    launchEditActivity(1, this.mProfileAdapter.getEmploymentList(), this.mProfileAdapter.getSharingRosterData());
  }

  public final void onEditPlacesLivedClicked()
  {
    launchEditActivity(3, this.mProfileAdapter.getPlacesLivedList(), this.mProfileAdapter.getSharingRosterData());
  }

  public final void onEmailClicked(String paramString)
  {
    Rfc822Token[] arrayOfRfc822Token = null;
    if (paramString != null)
      arrayOfRfc822Token = Rfc822Tokenizer.tokenize(paramString);
    if ((arrayOfRfc822Token == null) || (arrayOfRfc822Token.length == 0));
    while (true)
    {
      return;
      Rfc822Token localRfc822Token = arrayOfRfc822Token[0];
      if ((TextUtils.isEmpty(localRfc822Token.getName())) && (!TextUtils.isEmpty(this.mProfileAdapter.getFullName())))
        localRfc822Token.setName(this.mProfileAdapter.getFullName());
      safeStartActivity(new Intent("android.intent.action.SENDTO", Uri.parse("mailto:" + Uri.encode(localRfc822Token.toString()))));
    }
  }

  public final void onExpandClicked(boolean paramBoolean)
  {
    this.mProfileIsExpanded = paramBoolean;
    this.mProfileAdapter.setViewIsExpanded(paramBoolean);
    OzViews localOzViews1;
    if (paramBoolean)
      localOzViews1 = OzViews.LOOP_USER;
    for (OzViews localOzViews2 = OzViews.PROFILE; ; localOzViews2 = OzViews.LOOP_USER)
    {
      Bundle localBundle = getExtrasForLogging();
      EsAnalytics.recordNavigationEvent(getActivity(), getAccount(), localOzViews1, localOzViews2, null, null, localBundle, localBundle);
      return;
      localOzViews1 = OzViews.PROFILE;
    }
  }

  public final void onLinkClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      localIntent.addFlags(524288);
      safeStartActivity(localIntent);
    }
  }

  public final void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    switch (paramLoader.getId())
    {
    default:
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "Loader<Cursor> onLoadFinished() -- " + paramLoader.getId());
      super.onLoadFinished(paramLoader, paramCursor);
    case 3:
    }
    while (true)
    {
      return;
      if ((this.mActiveProfileCursor == null) || (!this.mActiveProfileCursor.wrapsStreamCursor(paramCursor)))
      {
        EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(paramCursor.getColumnNames(), 1);
        Object[] arrayOfObject = new Object[StreamAdapter.StreamQuery.PROJECTION_STREAM.length];
        arrayOfObject[15] = Long.valueOf(512L);
        localEsMatrixCursor.addRow(arrayOfObject);
        this.mActiveProfileCursor = new ProfileMergeCursor(new Cursor[] { localEsMatrixCursor, paramCursor });
      }
      if (Log.isLoggable("HostedProfileFragment", 3))
        Log.d("HostedProfileFragment", "Loader<Cursor> onLoadFinished() -- POSTS_LOADER_ID, " + this.mActiveProfileCursor.getCount() + " rows");
      super.onLoadFinished(paramLoader, this.mActiveProfileCursor);
      if (this.mActiveProfileCursor.getCount() > 0)
      {
        this.mProfileAdapter.notifyDataSetChanged();
        showContent(getView());
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onLocalCallClicked(String paramString)
  {
    startExternalActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + Uri.encode(paramString))));
  }

  public final void onLocalDirectionsClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      MapUtils.launchMapsActivity(getActivity(), Uri.parse(paramString));
  }

  public final void onLocalMapClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      MapUtils.launchMapsActivity(getActivity(), Uri.parse(paramString));
  }

  public final void onLocalReviewClicked(int paramInt1, int paramInt2)
  {
    safeStartActivity(Intents.getLocalReviewActivityIntent(getActivity(), this.mAccount, this.mPersonId, paramInt1, paramInt2));
  }

  public final void onLocationClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Uri localUri = Uri.parse("geo:0,0?q=" + Uri.encode(paramString));
      MapUtils.launchMapsActivity(getActivity(), localUri);
    }
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
      if (i == R.id.mute)
      {
        if (this.mIsPlusPage);
        for (String str3 = this.mProfileAdapter.getFullName(); ; str3 = this.mProfileAdapter.getGivenName())
        {
          MuteProfileDialog localMuteProfileDialog2 = new MuteProfileDialog();
          Bundle localBundle2 = new Bundle();
          localBundle2.putString("name", str3);
          localBundle2.putString("gender", this.mProfileAdapter.getGender());
          localBundle2.putBoolean("target_mute", bool);
          localMuteProfileDialog2.setArguments(localBundle2);
          localMuteProfileDialog2.setTargetFragment(this, 0);
          localMuteProfileDialog2.show(getFragmentManager(), "mute_profile");
          break;
        }
      }
      if (i == R.id.unmute)
      {
        if (this.mIsPlusPage);
        for (String str2 = this.mProfileAdapter.getFullName(); ; str2 = this.mProfileAdapter.getGivenName())
        {
          MuteProfileDialog localMuteProfileDialog1 = new MuteProfileDialog();
          Bundle localBundle1 = new Bundle();
          localBundle1.putString("name", str2);
          localBundle1.putString("gender", this.mProfileAdapter.getGender());
          localBundle1.putBoolean("target_mute", false);
          localMuteProfileDialog1.setArguments(localBundle1);
          localMuteProfileDialog1.setTargetFragment(this, 0);
          localMuteProfileDialog1.show(getFragmentManager(), "unmute_profile");
          break;
        }
      }
      if (i == R.id.block)
      {
        BlockPersonDialog localBlockPersonDialog = new BlockPersonDialog(this.mProfileAdapter.isPlusPage());
        localBlockPersonDialog.setTargetFragment(this, 0);
        localBlockPersonDialog.show(getFragmentManager(), "block_person");
      }
      else if (i == R.id.unblock)
      {
        UnblockPersonDialog localUnblockPersonDialog = new UnblockPersonDialog(this.mPersonId, this.mProfileAdapter.isPlusPage());
        localUnblockPersonDialog.setTargetFragment(this, 0);
        localUnblockPersonDialog.show(getFragmentManager(), "unblock_person");
      }
      else if (i == R.id.report_abuse)
      {
        ReportAbuseDialog localReportAbuseDialog = new ReportAbuseDialog();
        localReportAbuseDialog.setTargetFragment(this, 0);
        localReportAbuseDialog.show(getFragmentManager(), "report_abuse");
      }
      else if (i == R.id.help)
      {
        String str1 = getResources().getString(R.string.url_param_help_profile);
        startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(getActivity(), str1)));
      }
      else
      {
        bool = super.onOptionsItemSelected(paramMenuItem);
      }
    }
  }

  public final void onPause()
  {
    super.onPause();
    this.mInnerAdapter.onPause();
    this.mGridView.onPause();
    EsService.unregisterListener(this.mProfileServiceListener);
  }

  public final void onPhoneNumberClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      safeStartActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + Uri.encode(paramString))));
  }

  public final void onPlusOneClicked()
  {
    if (this.mProfileAdapter.isPlusOnedByMe())
    {
      String str2 = EsPeopleData.extractGaiaId(this.mPersonId);
      if (!EsService.isProfilePlusOnePending(str2))
        this.mPlusOneRequestId = Integer.valueOf(EsService.deleteProfilePlusOne(this.mContext, this.mAccount, str2));
    }
    while (true)
    {
      return;
      String str1 = EsPeopleData.extractGaiaId(this.mPersonId);
      if (!EsService.isProfilePlusOnePending(str1))
        this.mPlusOneRequestId = Integer.valueOf(EsService.createProfilePlusOne(this.mContext, this.mAccount, str1));
    }
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    int j;
    boolean bool2;
    int i;
    if (this.mControlPrimarySpinner)
    {
      ArrayAdapter localArrayAdapter = ProfileActivity.createSpinnerAdapter(this.mContext);
      if (this.mCurrentSpinnerPosition < 0)
      {
        j = 0;
        paramHostActionBar.showPrimarySpinner(localArrayAdapter, j);
      }
    }
    else
    {
      boolean bool1 = canShowRefreshInActionBar();
      if (!bool1)
        super.updateSpinner();
      if ((bool1) || (!canShowConversationActions()))
        paramHostActionBar.showRefreshButton();
      bool2 = isProgressIndicatorVisible();
      if (canShowConversationActions())
        break label128;
      i = 0;
    }
    while (true)
    {
      if (i != 0)
      {
        paramHostActionBar.showActionButton(0, R.drawable.icn_startmessenger, R.string.start_conversation_action_label);
        if (Hangout.isHangoutCreationSupported(this.mContext, this.mAccount))
          paramHostActionBar.showActionButton(1, R.drawable.icn_starthangout, R.string.start_hangout_action_label);
      }
      return;
      j = this.mCurrentSpinnerPosition;
      break;
      label128: if ((ScreenMetrics.getInstance(this.mContext).screenDisplayType == 0) && (!this.mLandscape) && (bool2))
        i = 0;
      else
        i = 1;
    }
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    boolean bool1;
    boolean bool2;
    label108: boolean bool3;
    label166: boolean bool4;
    label204: boolean bool5;
    label228: boolean bool6;
    if ((this.mHasGaiaId) && (!this.mIsMyProfile) && (this.mProfileAdapter != null) && (!TextUtils.isEmpty(this.mProfileAdapter.getFullName())) && (this.mMuteRequestId == null) && (!this.mBlockInProgress) && (this.mReportAbuseRequestId == null) && (!this.mIsMute))
    {
      bool1 = true;
      if ((!this.mHasGaiaId) || (this.mIsMyProfile) || (this.mMuteRequestId != null) || (this.mBlockInProgress) || (this.mReportAbuseRequestId != null) || (!this.mIsMute))
        break label394;
      bool2 = true;
      if ((!this.mHasGaiaId) || (this.mIsMyProfile) || (this.mProfileAdapter == null) || (TextUtils.isEmpty(this.mProfileAdapter.getFullName())) || (this.mBlockInProgress) || (this.mReportAbuseRequestId != null) || (this.mIsBlocked))
        break label399;
      bool3 = true;
      if ((!this.mHasGaiaId) || (this.mIsMyProfile) || (this.mBlockInProgress) || (this.mReportAbuseRequestId != null) || (!this.mIsBlocked))
        break label405;
      bool4 = true;
      if ((!this.mHasGaiaId) || (this.mIsMyProfile) || (this.mReportAbuseRequestId != null))
        break label411;
      bool5 = true;
      if ((canShowRefreshInActionBar()) || (!canShowConversationActions()))
        break label417;
      bool6 = true;
      label245: paramMenu.findItem(R.id.refresh).setVisible(bool6);
      paramMenu.findItem(R.id.mute).setVisible(bool1);
      paramMenu.findItem(R.id.unmute).setVisible(bool2);
      paramMenu.findItem(R.id.block).setVisible(bool3);
      paramMenu.findItem(R.id.unblock).setVisible(bool4);
      paramMenu.findItem(R.id.report_abuse).setVisible(bool5);
      if (!this.mIsPlusPage)
        break label423;
      paramMenu.findItem(R.id.block).setTitle(R.string.menu_item_block_profile);
      paramMenu.findItem(R.id.unblock).setTitle(R.string.menu_item_unblock_profile);
    }
    while (true)
    {
      return;
      bool1 = false;
      break;
      label394: bool2 = false;
      break label108;
      label399: bool3 = false;
      break label166;
      label405: bool4 = false;
      break label204;
      label411: bool5 = false;
      break label228;
      label417: bool6 = false;
      break label245;
      label423: paramMenu.findItem(R.id.block).setTitle(R.string.menu_item_block_person);
      paramMenu.findItem(R.id.unblock).setTitle(R.string.menu_item_unblock_person);
    }
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if ((this.mControlPrimarySpinner) && (this.mCurrentSpinnerPosition != paramInt))
      switch (paramInt)
      {
      default:
      case 0:
      case 1:
      }
    while (true)
    {
      this.mCurrentSpinnerPosition = paramInt;
      return;
      super.refresh();
      continue;
      startActivity(Intents.getHostedProfileAlbumsIntent(this.mContext, this.mAccount, this.mPersonId, null));
    }
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mProfileServiceListener);
    if ((this.mProfilePendingRequestId != null) && (!EsService.isRequestPending(this.mProfilePendingRequestId.intValue())))
    {
      ServiceResult localServiceResult5 = EsService.removeResult(this.mProfilePendingRequestId.intValue());
      handleProfileServiceCallback(this.mProfilePendingRequestId.intValue(), localServiceResult5);
      this.mProfilePendingRequestId = null;
    }
    if ((this.mReportAbuseRequestId != null) && (!EsService.isRequestPending(this.mReportAbuseRequestId.intValue())))
    {
      ServiceResult localServiceResult4 = EsService.removeResult(this.mReportAbuseRequestId.intValue());
      handleReportAbuseCallback(this.mReportAbuseRequestId.intValue(), localServiceResult4);
      this.mReportAbuseRequestId = null;
    }
    if ((this.mMuteRequestId != null) && (!EsService.isRequestPending(this.mMuteRequestId.intValue())))
    {
      ServiceResult localServiceResult3 = EsService.removeResult(this.mMuteRequestId.intValue());
      handleSetMutedCallback(this.mMuteRequestId.intValue(), this.mMuteRequestIsMuted, localServiceResult3);
      this.mMuteRequestId = null;
    }
    if ((this.mPlusOneRequestId != null) && (!EsService.isRequestPending(this.mPlusOneRequestId.intValue())))
    {
      ServiceResult localServiceResult2 = EsService.removeResult(this.mPlusOneRequestId.intValue());
      handlePlusOneCallback(this.mPlusOneRequestId.intValue(), localServiceResult2);
      this.mPlusOneRequestId = null;
    }
    if ((this.mInsertCameraPhotoRequestId != null) && (!EsService.isRequestPending(this.mInsertCameraPhotoRequestId.intValue())))
    {
      EsService.removeResult(this.mInsertCameraPhotoRequestId.intValue());
      handlerInsertCameraPhoto$b5e9bbb(this.mInsertCameraPhotoRequestId.intValue());
      this.mInsertCameraPhotoRequestId = null;
    }
    if ((this.mSetCoverPhotoRequestId != null) && (!EsService.isRequestPending(this.mSetCoverPhotoRequestId.intValue())))
    {
      ServiceResult localServiceResult1 = EsService.removeResult(this.mSetCoverPhotoRequestId.intValue());
      handleCoverPhotoCallback(this.mSetCoverPhotoRequestId.intValue(), localServiceResult1);
    }
    updateSpinner();
  }

  public final void onReviewAuthorAvatarClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      safeStartActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), this.mAccount, paramString, null));
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mProfilePendingRequestId != null)
      paramBundle.putInt("profile_request_id", this.mProfilePendingRequestId.intValue());
    if (this.mPlusOneRequestId != null)
      paramBundle.putInt("plusone_request_id", this.mPlusOneRequestId.intValue());
    if (this.mReportAbuseRequestId != null)
      paramBundle.putInt("abuse_request_id", this.mReportAbuseRequestId.intValue());
    if (this.mMuteRequestId != null)
    {
      paramBundle.putInt("mute_request_id", this.mMuteRequestId.intValue());
      paramBundle.putBoolean("mute_state", this.mMuteRequestIsMuted);
    }
    if (this.mInsertCameraPhotoRequestId != null)
      paramBundle.putInt("camera_request_id", this.mInsertCameraPhotoRequestId.intValue());
    if (this.mSetCoverPhotoRequestId != null)
      paramBundle.putInt("cover_photo_request_id", this.mSetCoverPhotoRequestId.intValue());
    paramBundle.putBoolean("block_in_progress", this.mBlockInProgress);
    paramBundle.putBoolean("profile_is_expanded", this.mProfileIsExpanded);
    paramBundle.putInt("choose_photo_target", this.mChoosePhotoTarget);
  }

  public final void onSendTextClicked(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      startExternalActivity(new Intent("android.intent.action.VIEW", Uri.parse("sms:" + Uri.encode(paramString))));
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mPersonId = paramBundle.getString("person_id");
    this.mIsMyProfile = this.mPersonId.equals(this.mAccount.getPersonId());
    if (EsPeopleData.extractGaiaId(this.mPersonId) != null);
    for (boolean bool = true; ; bool = false)
    {
      this.mHasGaiaId = bool;
      this.mGaiaId = EsPeopleData.extractGaiaId(this.mPersonId);
      if (this.mGaiaId == null)
        paramBundle.putBoolean("show_empty_stream", true);
      return;
    }
  }

  public final void onZagatExplanationClicked()
  {
    new ProfileZagatExplanationDialog().show(getFragmentManager(), "zagat_explanation");
  }

  public final void refresh()
  {
    super.refresh();
    refreshProfile();
  }

  public final void refreshProfile()
  {
    this.mProfilePendingRequestId = EsService.getProfileAndContact(getActivity(), this.mAccount, this.mPersonId, true);
    updateSpinner();
  }

  public final void relinquishPrimarySpinner()
  {
    this.mControlPrimarySpinner = false;
  }

  public final void reportAbuse(String paramString)
  {
    if ("IMPERSONATION".equals(paramString))
      AlertFragmentDialog.newInstance(getString(R.string.report_user_dialog_title), getString(R.string.report_impersonation_dialog_message), getString(17039370), null).show(getFragmentManager(), "dialog_warning");
    while (true)
    {
      return;
      this.mReportAbuseRequestId = EsService.reportProfileAbuse(this.mContext, this.mAccount, this.mGaiaId, paramString);
      showProgressDialog(R.string.report_abuse_operation_pending);
    }
  }

  protected final void setCircleMembership(ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
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
    this.mProfilePendingRequestId = EsService.setCircleMembership(getActivity(), this.mAccount, this.mPersonId, this.mProfileAdapter.getFullName(), (String[])localArrayList1.toArray(new String[localArrayList1.size()]), (String[])localArrayList2.toArray(new String[localArrayList2.size()]));
    if ((!localArrayList1.isEmpty()) && (localArrayList2.isEmpty()))
      showProgressDialog(R.string.add_to_circle_operation_pending);
    while (true)
    {
      return;
      if ((localArrayList1.isEmpty()) && (!localArrayList2.isEmpty()))
        showProgressDialog(R.string.remove_from_circle_operation_pending);
      else
        showProgressDialog(R.string.moving_between_circles_operation_pending);
    }
  }

  protected final void setCoverPhoto(String paramString, int paramInt, boolean paramBoolean)
  {
    Long localLong = this.mProfileAdapter.getScrapbookCoverPhotoId();
    if ((localLong != null) && (Long.toString(localLong.longValue()).equals(paramString)))
      updateCoverPhoto(paramString, paramInt);
    while (true)
    {
      return;
      this.mSetCoverPhotoRequestId = Integer.valueOf(EsService.setCoverPhoto(getActivity(), this.mAccount, paramString, paramInt, paramBoolean));
      showProgressDialog(R.string.setting_cover_photo);
    }
  }

  protected final void setCoverPhoto(byte[] paramArrayOfByte, int paramInt)
  {
    this.mSetCoverPhotoRequestId = Integer.valueOf(EsService.uploadCoverPhoto(getActivity(), this.mAccount, paramArrayOfByte, paramInt));
    showProgressDialog(R.string.setting_cover_photo);
  }

  public final void setPersonMuted(boolean paramBoolean)
  {
    this.mMuteRequestId = EsService.setPersonMuted(this.mContext, this.mAccount, this.mGaiaId, paramBoolean);
    this.mMuteRequestIsMuted = paramBoolean;
    showProgressDialog(R.string.mute_operation_pending);
  }

  protected final void setProfilePhoto(byte[] paramArrayOfByte)
  {
    this.mProfilePendingRequestId = Integer.valueOf(EsService.uploadProfilePhoto(getActivity(), this.mAccount, paramArrayOfByte));
    showProgressDialog(R.string.setting_profile_photo);
  }

  protected final void showProgressDialog(int paramInt)
  {
    ProgressFragmentDialog.newInstance(null, getString(paramInt), false).show(getFragmentManager(), "req_pending");
  }

  public final void unblockPerson(String paramString)
  {
    setPersonBlocked(false);
  }

  protected final void updateSpinner()
  {
    if (canShowRefreshInActionBar())
      super.updateSpinner();
    while (true)
    {
      return;
      invalidateActionBar();
    }
  }

  private static final class ProfileMergeCursor extends MergeCursor
  {
    private EsMatrixCursor mProfileCursor = (EsMatrixCursor)paramArrayOfCursor[0];
    private Cursor mStreamCursor = paramArrayOfCursor[1];

    public ProfileMergeCursor(Cursor[] paramArrayOfCursor)
    {
      super();
    }

    public final boolean wrapsStreamCursor(Cursor paramCursor)
    {
      if (this.mStreamCursor == paramCursor);
      for (boolean bool = true; ; bool = false)
        return bool;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedProfileFragment
 * JD-Core Version:    0.6.2
 */