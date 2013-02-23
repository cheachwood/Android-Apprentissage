package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.ComposeBarController;
import com.google.android.apps.plus.phone.ComposeBarController.ComposeBarListener;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotosIntentBuilder;
import com.google.android.apps.plus.phone.LocationController;
import com.google.android.apps.plus.phone.ProfileActivity;
import com.google.android.apps.plus.phone.RecentImagesLoader;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.phone.StreamAdapter;
import com.google.android.apps.plus.phone.StreamAdapter.StreamQuery;
import com.google.android.apps.plus.phone.StreamAdapter.ViewUseListener;
import com.google.android.apps.plus.phone.StreamTranslationAdapter;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.ResourceRedirector;
import com.google.android.apps.plus.views.CardView;
import com.google.android.apps.plus.views.ClickableButton;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.PlusOneAnimatorView;
import com.google.android.apps.plus.views.PlusOneAnimatorView.PlusOneAnimListener;
import com.google.android.apps.plus.views.StreamCardView;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;
import java.util.ArrayList;
import java.util.HashMap;

public class HostedStreamFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, ComposeBarController.ComposeBarListener, StreamAdapter.ViewUseListener, PlusOneAnimatorView.PlusOneAnimListener, StreamCardView.StreamMediaClickListener, StreamCardView.StreamPlusBarClickListener
{
  private static final String[] CIRCLES_PROJECTION = { "circle_id", "circle_name", "contact_count", "volume" };
  private static int sNextPagePreloadTriggerRows;
  private static boolean sUsePhotoOneUp = false;
  protected StreamTranslationAdapter mAdapter;
  protected PlusOneInfo mAnimatingPlusOneInfo;
  protected String mCircleId;
  protected ComposeBarController mComposeBarController;
  protected String mContinuationToken;
  private int mCurrentSpinnerPosition;
  protected boolean mEndOfStream;
  protected boolean mError;
  protected boolean mFirstLoad = true;
  private boolean mFragmentCreated;
  private long mFragmentStartTime;
  protected String mGaiaId;
  protected ColumnGridView mGridView;
  protected StreamAdapter mInnerAdapter;
  private long mLastDeactivationTime;
  protected Integer mLoaderHash;
  protected Location mLocation;
  protected LocationController mLocationController;
  protected View mLocationDisabledView;
  protected Button mLocationSettingsButton;
  protected boolean mNearby;
  private boolean mOptionsMenuIsSubscribeVisible;
  private int mOptionsMenuSubscribeIcon;
  private int mOptionsMenuSubscribeText;
  protected Uri mPostsUri;
  protected boolean mPreloadRequested;
  private ArrayAdapter<CircleSpinnerInfo> mPrimarySpinnerAdapter;
  private long mRecentImagesSyncTimestamp;
  private boolean mRefreshDisabled;
  protected boolean mResetAnimationState;
  protected boolean mResetPosition;
  private int mScrollOffset;
  private int mScrollPos;
  private View mServerErrorRetryButton;
  private View mServerErrorView;
  protected final EsServiceListener mServiceListener = new ServiceListener();
  private String mSetVolumeRequestCircleName;
  private Integer mSetVolumeRequestId;
  private int mSetVolumeRequestVolume;
  private long mStreamChangeLastCheckTimeMs;
  private boolean mStreamHasChanged;
  private int mStreamLength = -1;
  protected String mStreamOwnerUserId;
  protected int mView;

  private void addLocationListener(Location paramLocation)
  {
    if (this.mLocationController == null)
      this.mLocationController = new LocationController(getActivity(), this.mAccount, true, 3000L, paramLocation, new StreamLocationListener((byte)0));
    View localView = getView();
    localView.findViewById(R.id.stream_location_layout).setVisibility(0);
    updateLocationHeader(localView);
    if (!this.mLocationController.isProviderEnabled())
    {
      removeProgressViewMessages();
      localView.findViewById(16908292).setVisibility(8);
      localView.findViewById(R.id.list_empty_text).setVisibility(8);
      localView.findViewById(R.id.list_empty_progress).setVisibility(8);
      localView.findViewById(R.id.stream_location_layout).setVisibility(8);
      this.mGridView.setVisibility(8);
      this.mLocationDisabledView.setVisibility(0);
      removeLocationListener();
    }
    while (true)
    {
      return;
      this.mLocationController.init();
      if (this.mLocation == null)
        showEmptyViewProgress(localView, getString(R.string.finding_your_location));
    }
  }

  private static OzViews getViewForLogging(String paramString)
  {
    OzViews localOzViews;
    if ("v.all.circles".equals(paramString))
      localOzViews = OzViews.LOOP_EVERYONE;
    while (true)
    {
      return localOzViews;
      if ("v.whatshot".equals(paramString))
        localOzViews = OzViews.LOOP_WHATS_HOT;
      else if ("v.nearby".equals(paramString))
        localOzViews = OzViews.LOOP_NEARBY;
      else if (paramString.startsWith("f."))
        localOzViews = OzViews.LOOP_CIRCLES;
      else if (paramString.startsWith("g."))
        localOzViews = OzViews.LOOP_USER;
      else
        localOzViews = OzViews.HOME;
    }
  }

  private void handleOnSetVolumeControlCallback(ServiceResult paramServiceResult)
  {
    DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
    this.mSetVolumeRequestId = null;
    String str;
    if (paramServiceResult != null)
    {
      if (!paramServiceResult.hasError())
        break label59;
      str = getString(R.string.transient_server_error);
    }
    while (true)
    {
      Toast.makeText(getActivity(), str, 0).show();
      return;
      label59: if (this.mSetVolumeRequestVolume == 2)
      {
        int j = R.string.toast_circle_unsubscribed;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mSetVolumeRequestCircleName;
        str = getString(j, arrayOfObject2);
      }
      else if (this.mSetVolumeRequestVolume == 4)
      {
        int i = R.string.toast_circle_subscribed;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mSetVolumeRequestCircleName;
        str = getString(i, arrayOfObject1);
      }
      else
      {
        str = getString(R.string.report_set_volume_completed_toast);
      }
    }
  }

  private void initRecentImagesLoader()
  {
    getLoaderManager().restartLoader(6, null, this);
  }

  private void onShakeAnimFinished()
  {
    if ((isPaused()) || (getActivity() == null));
    while (true)
    {
      return;
      View localView1 = getView();
      if (localView1 != null)
      {
        View localView2 = localView1.findViewById(R.id.plus_one_glass);
        if (localView2 != null)
          localView2.setVisibility(8);
      }
      if (this.mAnimatingPlusOneInfo != null)
      {
        togglePlusOne(this.mAnimatingPlusOneInfo.activityId, this.mAnimatingPlusOneInfo.plusOneData);
        this.mAnimatingPlusOneInfo = null;
      }
    }
  }

  private void prefetchContent()
  {
    this.mPreloadRequested = true;
    if (EsLog.isLoggable("HostedStreamFrag", 4))
      Log.i("HostedStreamFrag", "prefetchContent - mPreloadRequested=true");
    if ((!this.mGridView.post(new Runnable()
    {
      public final void run()
      {
        if (!HostedStreamFragment.this.isPaused())
          HostedStreamFragment.this.fetchContent(false);
        while (true)
        {
          return;
          if (EsLog.isLoggable("HostedStreamFrag", 4))
            Log.i("HostedStreamFrag", "prefetchContent - paused!");
        }
      }
    })) && (EsLog.isLoggable("HostedStreamFrag", 4)))
      Log.i("HostedStreamFrag", "prefetchContent - posting the runnable returned false!");
  }

  private void removeLocationListener()
  {
    if (this.mLocationController != null)
    {
      this.mLocationController.release();
      this.mLocationController = null;
    }
  }

  private void startStreamOneUp(StreamCardView paramStreamCardView, boolean paramBoolean)
  {
    String str1 = paramStreamCardView.getAlbumId();
    MediaRef localMediaRef = paramStreamCardView.getMediaRef();
    String str2 = paramStreamCardView.getMediaLinkUrl();
    int i = paramStreamCardView.getDesiredWidth();
    int j = paramStreamCardView.getDesiredHeight();
    boolean bool = paramStreamCardView.isAlbum();
    String str3 = paramStreamCardView.getLinkTitle();
    String str4 = paramStreamCardView.getDeepLinkLabel();
    String str5 = paramStreamCardView.getLinkUrl();
    String str6 = paramStreamCardView.getSquareIdForOneUp();
    if (localMediaRef != null)
    {
      Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", localMediaRef.getOwnerGaiaId());
      recordUserAction(OzActions.STREAM_SELECT_ACTIVITY, localBundle);
    }
    String str7 = paramStreamCardView.getActivityId();
    if (str7 == null);
    while (true)
    {
      return;
      Intent localIntent = Intents.getStreamOneUpActivityIntent(getActivity().getApplicationContext(), this.mAccount, str7);
      if ((i > 0) && (j > 0))
      {
        localIntent.putExtra("photo_width", i);
        localIntent.putExtra("photo_height", j);
      }
      if (localMediaRef != null)
      {
        localIntent.putExtra("photo_ref", localMediaRef);
        localIntent.putExtra("is_album", bool);
      }
      if (!TextUtils.isEmpty(str2))
        localIntent.putExtra("photo_link_url", str2);
      if (!TextUtils.isEmpty(str1))
        localIntent.putExtra("album_id", str1);
      if (!TextUtils.isEmpty(str3))
        localIntent.putExtra("link_title", str3);
      if (!TextUtils.isEmpty(str4))
        localIntent.putExtra("deep_link_label", str4);
      if (!TextUtils.isEmpty(str5))
        localIntent.putExtra("link_url", str5);
      if (!TextUtils.isEmpty(str6))
        localIntent.putExtra("square_id", str6);
      localIntent.putExtra("show_keyboard", paramBoolean);
      startStreamOneUp(localIntent);
    }
  }

  private void togglePlusOne(String paramString, DbPlusOneData paramDbPlusOneData)
  {
    FragmentActivity localFragmentActivity;
    if (!EsService.isPostPlusOnePending(paramString))
    {
      localFragmentActivity = getActivity();
      if ((paramDbPlusOneData == null) || (!paramDbPlusOneData.isPlusOnedByMe()))
        break label34;
      EsService.deletePostPlusOne(localFragmentActivity, this.mAccount, paramString);
    }
    while (true)
    {
      return;
      label34: EsService.createPostPlusOne(localFragmentActivity, this.mAccount, paramString);
    }
  }

  private void updateEmptyViewProgressText()
  {
    View localView = getView();
    if (localView != null)
      if (!this.mNearby)
        break label41;
    label41: for (int i = R.string.finding_your_location; ; i = R.string.loading)
    {
      String str = getString(i);
      ((TextView)localView.findViewById(R.id.list_empty_progress_text)).setText(str);
      return;
    }
  }

  private void updateLocationHeader(View paramView)
  {
    TextView localTextView = (TextView)paramView.findViewById(R.id.stream_location_text);
    if (this.mLocation == null)
      localTextView.setText(R.string.finding_your_location);
    while (true)
    {
      return;
      String str = LocationController.getFormattedAddress(this.mLocation);
      if (str != null)
        localTextView.setText(str);
      else
        localTextView.setText(R.string.unknown_address);
    }
  }

  private void updateOptionsMenuInfo(CircleSpinnerInfo paramCircleSpinnerInfo)
  {
    String str = paramCircleSpinnerInfo.getRealCircleId();
    if ("v.all.circles".equals(str))
    {
      this.mOptionsMenuIsSubscribeVisible = true;
      this.mOptionsMenuSubscribeText = R.string.menu_subscribe_to_circles;
      this.mOptionsMenuSubscribeIcon = R.drawable.ic_menu_unmute_conversation;
    }
    while (true)
    {
      return;
      if (("v.whatshot".equals(str)) || ("v.nearby".equals(str)))
      {
        this.mOptionsMenuIsSubscribeVisible = false;
        this.mOptionsMenuSubscribeText = 0;
        this.mOptionsMenuSubscribeIcon = 0;
      }
      else
      {
        this.mOptionsMenuIsSubscribeVisible = true;
        if (paramCircleSpinnerInfo.getVolume() == 4)
        {
          this.mOptionsMenuSubscribeText = R.string.menu_unsubscribe;
          this.mOptionsMenuSubscribeIcon = R.drawable.ic_menu_mute_conversation;
        }
        else
        {
          this.mOptionsMenuSubscribeText = R.string.menu_subscribe;
          this.mOptionsMenuSubscribeIcon = R.drawable.ic_menu_unmute_conversation;
        }
      }
    }
  }

  private void updateRefreshButton(boolean paramBoolean)
  {
    if (EsLog.isLoggable("HostedStreamFrag", 3))
      Log.d("HostedStreamFrag", "Stream has changed: " + paramBoolean);
    this.mStreamHasChanged = paramBoolean;
    HostActionBar localHostActionBar = getActionBar();
    if (localHostActionBar != null)
      localHostActionBar.updateRefreshButtonIcon(this.mStreamHasChanged);
  }

  protected final void checkResetAnimationState()
  {
    if (this.mResetAnimationState)
    {
      if (this.mResetPosition)
      {
        this.mScrollPos = 0;
        this.mScrollOffset = 0;
        this.mGridView.setSelectionToTop();
      }
      this.mResetPosition = true;
      this.mInnerAdapter.resetAnimationState();
      this.mResetAnimationState = false;
    }
  }

  protected StreamAdapter createStreamAdapter(Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamAdapter.ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    return new StreamAdapter(paramContext, paramColumnGridView, paramEsAccount, paramOnClickListener, paramItemClickListener, paramViewUseListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener, paramComposeBarController);
  }

  public final void doCircleSubscribe(String paramString1, String paramString2)
  {
    this.mSetVolumeRequestId = EsService.setVolumeControl(getActivity(), this.mAccount, paramString1, 4);
    this.mSetVolumeRequestVolume = 4;
    this.mSetVolumeRequestCircleName = paramString2;
    showProgressDialog(R.string.dialog_pending_circle_subscribe);
  }

  public final void doCircleSubscriptions(HashMap<String, Integer> paramHashMap)
  {
    this.mSetVolumeRequestId = EsService.setVolumeControl(getActivity(), this.mAccount, paramHashMap);
    this.mSetVolumeRequestVolume = -1;
    this.mSetVolumeRequestCircleName = null;
    showProgressDialog(R.string.set_volume_multiple_pending);
  }

  public final void doCircleUnsubscribe(String paramString1, String paramString2)
  {
    this.mSetVolumeRequestId = EsService.setVolumeControl(getActivity(), this.mAccount, paramString1, 2);
    this.mSetVolumeRequestVolume = 2;
    this.mSetVolumeRequestCircleName = paramString2;
    showProgressDialog(R.string.dialog_pending_circle_unsubscribe);
  }

  protected void doShowEmptyViewProgress(View paramView)
  {
    super.doShowEmptyViewProgress(paramView);
    this.mLocationDisabledView.setVisibility(8);
  }

  protected void fetchContent(boolean paramBoolean)
  {
    if ((this.mPrimarySpinnerAdapter != null) && (this.mPrimarySpinnerAdapter.getCount() == 0))
    {
      if (EsLog.isLoggable("HostedStreamFrag", 4))
        Log.i("HostedStreamFrag", "fetchContent: No circles... reloading: " + isEmpty());
      showEmptyViewProgress(getView(), getString(R.string.loading));
      updateSpinner();
      getLoaderManager().restartLoader(1, null, this);
    }
    while (true)
    {
      return;
      if ((!showEmptyStream()) && ((paramBoolean) || (!this.mEndOfStream)) && (!fetchStreamContent(paramBoolean)))
        if (this.mNearby)
          showEmptyViewProgress(getView(), getString(R.string.loading));
        else
          showEmptyViewProgress(getView());
    }
  }

  protected final boolean fetchStreamContent(boolean paramBoolean)
  {
    boolean bool;
    label31: Integer localInteger;
    if (this.mNearby)
    {
      Location localLocation = this.mLocation;
      bool = false;
      if (localLocation == null)
        return bool;
      if (paramBoolean)
      {
        this.mContinuationToken = null;
        localInteger = Integer.valueOf(EsService.getNearbyActivities(getActivity(), this.mAccount, this.mView, new DbLocation(0, this.mLocation), this.mContinuationToken));
        if (!paramBoolean)
          break label153;
        this.mNewerReqId = localInteger;
      }
    }
    while (true)
    {
      updateSpinner();
      bool = true;
      break;
      if (this.mContinuationToken != null)
        break label31;
      bool = false;
      break;
      if (paramBoolean)
        this.mContinuationToken = null;
      while (this.mContinuationToken != null)
      {
        localInteger = Integer.valueOf(EsService.getActivityStream(getActivity(), this.mAccount, this.mView, this.mCircleId, this.mGaiaId, null, this.mContinuationToken, false));
        break;
      }
      bool = false;
      break;
      label153: this.mOlderReqId = localInteger;
    }
  }

  public OzViews getViewForLogging()
  {
    return OzViews.HOME;
  }

  protected void initCirclesLoader()
  {
    getLoaderManager().initLoader(1, null, this);
  }

  protected boolean isAdapterEmpty()
  {
    return this.mAdapter.isEmpty();
  }

  protected boolean isEmpty()
  {
    if ((isAdapterEmpty()) || (this.mFirstLoad));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected boolean isLocalDataAvailable(Cursor paramCursor)
  {
    if ((paramCursor != null) && (paramCursor.getCount() > 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public boolean isSquareStream()
  {
    return false;
  }

  protected boolean needsAsyncData()
  {
    return true;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    }
    while (true)
    {
      return;
      if ((paramInt2 == -1) && (paramIntent != null))
      {
        ArrayList localArrayList = paramIntent.getParcelableArrayListExtra("mediarefs");
        Intent localIntent = Intents.getPostActivityIntent(getActivity(), null, localArrayList);
        if (paramIntent.hasExtra("insert_photo_request_id"))
          localIntent.putExtra("insert_photo_request_id", paramIntent.getIntExtra("insert_photo_request_id", 0));
        localIntent.removeExtra("account");
        startActivityForCompose(localIntent);
      }
    }
  }

  protected final void onAsyncData()
  {
    super.onAsyncData();
    FragmentActivity localFragmentActivity = getActivity();
    if ((this.mFragmentStartTime > 0L) && ((localFragmentActivity instanceof ProfileActivity)))
      this.mFragmentStartTime = 0L;
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mLocationSettingsButton)
      startActivity(Intents.getLocationSettingActivityIntent());
    while (true)
    {
      return;
      if (paramView == this.mServerErrorRetryButton)
      {
        this.mError = false;
        if (EsLog.isLoggable("HostedStreamFrag", 4))
          Log.i("HostedStreamFrag", "onClick - mError=false");
        if (isAdapterEmpty())
        {
          refresh();
        }
        else
        {
          prefetchContent();
          updateServerErrorView();
        }
      }
      else if ((paramView instanceof StreamCardView))
      {
        StreamCardView localStreamCardView = (StreamCardView)paramView;
        String str1 = localStreamCardView.getEventId();
        String str2 = localStreamCardView.getEventOwnerId();
        if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
        {
          startActivity(Intents.getHostedEventIntent(getActivity(), this.mAccount, str1, str2, null));
        }
        else
        {
          String str3 = localStreamCardView.getSquareId();
          if ((!TextUtils.isEmpty(str3)) && (!isSquareStream()))
            startActivity(Intents.getSquareStreamActivityIntent(getActivity(), this.mAccount, str3, null, null));
          else
            startStreamOneUp(localStreamCardView, false);
        }
      }
      else
      {
        int i = paramView.getId();
        FragmentActivity localFragmentActivity = getActivity();
        if (i == R.id.compose_post)
        {
          startActivityForCompose(Intents.getPostTextActivityIntent(localFragmentActivity, this.mAccount));
        }
        else if (i == R.id.compose_photos)
        {
          recordUserAction(OzActions.COMPOSE_CHOOSE_PHOTO);
          startActivityForResult(Intents.newPhotosActivityIntentBuilder(getActivity()).setAccount(this.mAccount).setAlbumType("camera_photos").setPhotoPickerMode(Integer.valueOf(2)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_share)).setTakePhoto(true).setTakeVideo(true).build(), 1);
        }
        else if (i == R.id.compose_location)
        {
          recordUserAction(OzActions.LOOP_CHECKIN);
          startActivityForCompose(Intents.getCheckinActivityIntent(localFragmentActivity, this.mAccount));
        }
        else if (i == R.id.compose_custom)
        {
          ResourceRedirector.getInstance();
          if (Property.ENABLE_EMOTISHARE.getBoolean())
          {
            recordUserAction(OzActions.EMOTISHARE_INSERT_CLICKED);
            startActivityForCompose(Intents.getEmotiShareActivityIntent(localFragmentActivity, this.mAccount, null));
          }
        }
      }
    }
  }

  public final void onCommentsClicked$1b4287ec(StreamCardView paramStreamCardView)
  {
    startStreamOneUp(paramStreamCardView, true);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mScrollPos = paramBundle.getInt("scroll_pos");
      this.mScrollOffset = paramBundle.getInt("scroll_off");
      this.mLocation = ((Location)paramBundle.getParcelable("location"));
      if (paramBundle.containsKey("loader_hash"))
        this.mLoaderHash = Integer.valueOf(paramBundle.getInt("loader_hash"));
      this.mStreamLength = paramBundle.getInt("stream_length");
      this.mLastDeactivationTime = paramBundle.getLong("last_deactivation");
      this.mError = paramBundle.getBoolean("error");
      this.mResetAnimationState = paramBundle.getBoolean("reset_animation", false);
      this.mResetPosition = this.mResetAnimationState;
      this.mStreamChangeLastCheckTimeMs = paramBundle.getLong("stream_change");
      this.mStreamHasChanged = paramBundle.getBoolean("stream_change_flag");
      this.mRefreshDisabled = true;
      this.mFragmentCreated = false;
      if (paramBundle.containsKey("set_volume_id"))
      {
        this.mSetVolumeRequestId = Integer.valueOf(paramBundle.getInt("set_volume_id"));
        this.mSetVolumeRequestVolume = paramBundle.getInt("set_volume_value");
        this.mSetVolumeRequestCircleName = paramBundle.getString("set_volume_circle");
      }
      this.mOptionsMenuIsSubscribeVisible = paramBundle.getBoolean("subscribe_visible", false);
      this.mOptionsMenuSubscribeText = paramBundle.getInt("subscribe_text");
      this.mOptionsMenuSubscribeIcon = paramBundle.getInt("subscribe_icon");
    }
    while (true)
    {
      prepareLoaderUri();
      initCirclesLoader();
      initRecentImagesLoader();
      return;
      this.mScrollPos = 0;
      this.mScrollOffset = 0;
      this.mFragmentStartTime = System.currentTimeMillis();
      this.mFragmentCreated = true;
    }
  }

  public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      return localObject;
      localObject = new CircleListLoader(getActivity(), this.mAccount, 3, CIRCLES_PROJECTION);
      continue;
      if (showEmptyStream())
        this.mStreamLength = 0;
      int i;
      label87: String str1;
      int j;
      label141: String[] arrayOfString;
      label151: FragmentActivity localFragmentActivity;
      Uri localUri1;
      String str2;
      if (paramInt == 2)
      {
        i = 1;
        str1 = "sort_index ASC";
        if (i != -1)
          str1 = str1 + " LIMIT " + i;
        if ((paramInt == 2) || (this.mGaiaId != null))
          break label216;
        j = 1;
        if (paramInt != 2)
          break label222;
        arrayOfString = ContinuationTokenQuery.PROJECTION;
        localFragmentActivity = getActivity();
        localUri1 = this.mPostsUri;
        if (j == 0)
          break label230;
        str2 = "has_muted=0";
        label173: if (paramInt != 3)
          break label236;
      }
      label216: label222: label230: label236: for (Uri localUri2 = EsProvider.EVENTS_ALL_URI; ; localUri2 = null)
      {
        localObject = new EsCursorLoader(localFragmentActivity, localUri1, arrayOfString, str2, null, str1, localUri2);
        break;
        i = this.mStreamLength;
        break label87;
        j = 0;
        break label141;
        arrayOfString = StreamAdapter.StreamQuery.PROJECTION_STREAM;
        break label151;
        str2 = null;
        break label173;
      }
      localObject = new StreamChangeLoader(getActivity(), this.mAccount, this.mView, this.mCircleId, this.mGaiaId, null, false);
      continue;
      localObject = new NearbyStreamChangeLoader(getActivity(), this.mAccount, new DbLocation(0, this.mLocation));
      continue;
      localObject = new RecentImagesLoader(getActivity(), this.mAccount);
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.stream, paramViewGroup, false);
    int i;
    label129: boolean bool;
    if (sNextPagePreloadTriggerRows == 0)
    {
      if (ScreenMetrics.getInstance(localView1.getContext()).screenDisplayType == 0)
        sNextPagePreloadTriggerRows = 6;
    }
    else
    {
      this.mGridView = ((ColumnGridView)localView1.findViewById(R.id.grid));
      View localView2 = localView1.findViewById(R.id.floating_compose_bar);
      localView2.findViewById(R.id.compose_post).setOnClickListener(this);
      localView2.findViewById(R.id.compose_photos).setOnClickListener(this);
      localView2.findViewById(R.id.compose_location).setOnClickListener(this);
      View localView3 = localView2.findViewById(R.id.compose_custom);
      localView3.setOnClickListener(this);
      ResourceRedirector.getInstance();
      if (!Property.ENABLE_EMOTISHARE.getBoolean())
        break label360;
      i = 0;
      localView3.setVisibility(i);
      if (getResources().getConfiguration().orientation != 2)
        break label367;
      bool = true;
      label153: this.mComposeBarController = new ComposeBarController(localView2, bool, this);
      this.mInnerAdapter = createStreamAdapter(getActivity(), this.mGridView, this.mAccount, this, new PostClickListener(), this, this, this, this.mComposeBarController);
      this.mAdapter = new StreamTranslationAdapter(this.mInnerAdapter);
      this.mGridView.setAdapter(this.mAdapter);
      this.mGridView.setSelector(R.drawable.list_selected_holo);
      setupEmptyView(localView1, R.string.no_posts);
      this.mLocationDisabledView = localView1.findViewById(R.id.location_off);
      this.mLocationSettingsButton = ((Button)localView1.findViewById(R.id.location_off_settings));
      this.mLocationSettingsButton.setOnClickListener(this);
      this.mServerErrorView = localView1.findViewById(R.id.transient_server_error);
      this.mServerErrorRetryButton = localView1.findViewById(R.id.error_retry_button);
      this.mServerErrorRetryButton.setOnClickListener(this);
      if (showEmptyStream())
        showEmptyView(localView1, getString(R.string.no_posts));
      if (paramBundle != null)
        break label373;
    }
    label360: label367: label373: for (this.mRecentImagesSyncTimestamp = 0L; ; this.mRecentImagesSyncTimestamp = paramBundle.getLong("recent_images_sync_timestamp"))
    {
      updateServerErrorView();
      return localView1;
      sNextPagePreloadTriggerRows = 8;
      break;
      i = 8;
      break label129;
      bool = false;
      break label153;
    }
  }

  public final void onDestroyView()
  {
    super.onDestroyView();
    if (this.mGridView != null)
    {
      this.mGridView.setOnScrollListener(null);
      this.mGridView = null;
    }
  }

  public final void onDismissRecentImages(boolean paramBoolean)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
    {
      EsAccountsData.saveRecentImagesTimestamp(localFragmentActivity, this.mRecentImagesSyncTimestamp);
      initRecentImagesLoader();
      if (paramBoolean)
        EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.STREAM_DISMISS_INSTANT_UPLOAD_PHOTOS, getViewForLogging());
    }
  }

  public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    String str4;
    label94: int i2;
    switch (paramLoader.getId())
    {
    default:
    case 1:
      while (true)
      {
        return;
        if (paramCursor != null)
          break;
        if (EsLog.isLoggable("HostedStreamFrag", 4))
          Log.i("HostedStreamFrag", "populatePrimarySpinner: No circles loaded");
        this.mError = true;
        updateServerErrorView();
      }
      if (this.mCircleId != null)
      {
        str4 = this.mCircleId;
        if (this.mPrimarySpinnerAdapter.getCount() == paramCursor.getCount())
          break label360;
        i2 = 1;
        label113: if ((i2 != 0) || (!paramCursor.moveToFirst()))
          break label388;
      }
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    int i8;
    label179: label360: label1259: label1261: for (int i7 = 0; ; i7 = i8)
    {
      String str6 = paramCursor.getString(0);
      ArrayAdapter localArrayAdapter = this.mPrimarySpinnerAdapter;
      i8 = i7 + 1;
      CircleSpinnerInfo localCircleSpinnerInfo = (CircleSpinnerInfo)localArrayAdapter.getItem(i7);
      int i3;
      int i4;
      if (!TextUtils.equals(str6, localCircleSpinnerInfo.getRealCircleId()))
      {
        i3 = 1;
        if ((i3 != 0) && (paramCursor.moveToFirst()))
        {
          i4 = 0;
          this.mPrimarySpinnerAdapter.clear();
        }
      }
      while (true)
      {
        int i5 = i4;
        String str5 = paramCursor.getString(0);
        int i6 = paramCursor.getInt(3);
        this.mPrimarySpinnerAdapter.add(new CircleSpinnerInfo(getActivity(), paramCursor.getString(1), str5, paramCursor.getInt(2), i6));
        if (TextUtils.equals(str4, str5));
        for (i4 = paramCursor.getPosition(); ; i4 = i5)
        {
          if (paramCursor.moveToNext())
            break label1259;
          this.mCurrentSpinnerPosition = -1;
          getActionBar().showPrimarySpinner(this.mPrimarySpinnerAdapter, i4);
          if (this.mCurrentSpinnerPosition <= 0)
            break;
          updateOptionsMenuInfo((CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(this.mCurrentSpinnerPosition));
          break;
          str4 = getActivity().getSharedPreferences("streams", 0).getString("circle", "v.all.circles");
          break label94;
          i2 = 0;
          break label113;
          localCircleSpinnerInfo.setVolume(paramCursor.getInt(3));
          if (paramCursor.moveToNext())
            break label1261;
          i3 = i2;
          break label179;
          if ((paramCursor != null) && (!paramCursor.isClosed()) && (paramCursor.moveToFirst()));
          for (this.mContinuationToken = paramCursor.getString(0); ; this.mContinuationToken = null)
          {
            getLoaderManager().restartLoader(3, null, this);
            break;
          }
          int i = this.mAdapter.getCount();
          if (i != 0)
            saveScrollPosition();
          StreamAdapter localStreamAdapter = this.mInnerAdapter;
          int i1;
          boolean bool1;
          int j;
          int k;
          switch (this.mView)
          {
          default:
            if ((this.mGaiaId == null) && (this.mCircleId != null))
            {
              String str3 = this.mCircleId;
              if ((str3 != null) && (str3.startsWith("com.google.android.apps.plus.search_key-")))
              {
                i1 = 1;
                if (i1 == 0)
                  break label742;
                bool1 = false;
                localStreamAdapter.setMarkPostsAsRead(bool1);
                this.mInnerAdapter.changeStreamCursor(paramCursor);
                checkResetAnimationState();
                j = this.mInnerAdapter.getCount();
                this.mEndOfStream = false;
                this.mPreloadRequested = false;
                if (EsLog.isLoggable("HostedStreamFrag", 4))
                  Log.i("HostedStreamFrag", "onLoadFinished - mPreloadRequested=false");
                if ((!this.mError) || (j != 0))
                  break label754;
                showEmptyView(getView(), getString(R.string.people_list_error));
                k = 0;
              }
            }
            break;
          case 1:
          case 2:
          }
          while (true)
          {
            this.mFirstLoad = false;
            if ((paramCursor != null) && (paramCursor.getCount() != 0))
              break label891;
            this.mLoaderHash = null;
            restoreScrollPosition();
            if (EsLog.isLoggable("HostedStreamFrag", 4))
              Log.i("HostedStreamFrag", "onLoadFinished - mEndOfStream=" + this.mEndOfStream);
            if ((i != 0) && (j == 0))
            {
              this.mRefreshDisabled = false;
              refresh();
            }
            if (k == 0)
              break;
            onAsyncData();
            break;
            bool1 = false;
            break label547;
            i1 = 0;
            break label539;
            bool1 = true;
            break label547;
            bool1 = true;
            break label547;
            label754: if (isLocalDataAvailable(paramCursor))
            {
              showContent(getView());
              k = 1;
              paramCursor.moveToLast();
              if (paramCursor.getInt(20) == 1);
              for (boolean bool2 = true; ; bool2 = false)
              {
                this.mEndOfStream = bool2;
                updateSpinner();
                break;
              }
            }
            if (this.mFirstLoad)
            {
              if (!EsProvider.buildStreamUri(this.mAccount, "no_location_stream_key").equals(this.mPostsUri))
                fetchContent(true);
              k = 0;
            }
            else
            {
              if ((!this.mNearby) || (this.mLocation != null))
              {
                showEmptyView(getView(), getString(R.string.no_posts));
                updateSpinner();
              }
              k = 1;
            }
          }
          label891: StringBuilder localStringBuilder = new StringBuilder();
          if (paramCursor.moveToFirst())
            localStringBuilder.append(paramCursor.getString(1));
          int m = localStringBuilder.toString().hashCode();
          if ((this.mLoaderHash != null) && (this.mLoaderHash.intValue() != m));
          for (int n = 1; ; n = 0)
          {
            this.mLoaderHash = Integer.valueOf(m);
            if ((n == 0) && (j < i) && (this.mGridView.getLastVisiblePosition() >= j - 1))
              n = 1;
            if (n == 0)
              break;
            this.mGridView.setSelectionToTop();
            break label662;
          }
          if (this.mNewerReqId != null)
            break;
          long l3 = System.currentTimeMillis();
          if (l3 - this.mStreamChangeLastCheckTimeMs <= 30000L)
            break;
          StreamChangeLoader localStreamChangeLoader = (StreamChangeLoader)paramLoader;
          if (localStreamChangeLoader.hasError())
            break;
          this.mStreamChangeLastCheckTimeMs = l3;
          updateRefreshButton(localStreamChangeLoader.hasStreamChanged());
          break;
          if (this.mNewerReqId != null)
            break;
          long l2 = System.currentTimeMillis();
          if (l2 - this.mStreamChangeLastCheckTimeMs <= 30000L)
            break;
          NearbyStreamChangeLoader localNearbyStreamChangeLoader = (NearbyStreamChangeLoader)paramLoader;
          if (localNearbyStreamChangeLoader.hasError())
            break;
          this.mStreamChangeLastCheckTimeMs = l2;
          updateRefreshButton(localNearbyStreamChangeLoader.hasStreamChanged());
          break;
          if (paramCursor == null)
            break;
          ArrayList localArrayList = new ArrayList(paramCursor.getCount());
          String str1;
          long l1;
          String str2;
          if (paramCursor.moveToFirst())
          {
            this.mRecentImagesSyncTimestamp = System.currentTimeMillis();
            str1 = this.mAccount.getGaiaId();
            l1 = paramCursor.getLong(0);
            str2 = paramCursor.getString(1);
            if (!paramCursor.isNull(3))
              break label1246;
          }
          for (MediaRef.MediaType localMediaType = MediaRef.MediaType.IMAGE; ; localMediaType = MediaRef.MediaType.VIDEO)
          {
            localArrayList.add(new MediaRef(str1, l1, str2, null, localMediaType));
            if (paramCursor.moveToNext())
              break label1163;
            this.mComposeBarController.setRecentImageRefs(localArrayList);
            break;
          }
        }
      }
    }
  }

  public void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onMediaClicked(String paramString1, String paramString2, MediaRef paramMediaRef, boolean paramBoolean, StreamCardView paramStreamCardView)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (paramBoolean)
    {
      String str = paramMediaRef.getLocalUri().toString();
      Intents.viewContent(localFragmentActivity, this.mAccount, str);
    }
    while (true)
    {
      return;
      startStreamOneUp(paramStreamCardView, false);
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    CircleSpinnerInfo localCircleSpinnerInfo1;
    if (paramMenuItem.getItemId() == R.id.subscribe)
    {
      localCircleSpinnerInfo1 = (CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(this.mCurrentSpinnerPosition);
      if (localCircleSpinnerInfo1.getRealCircleId().equals("v.all.circles"))
      {
        ArrayList localArrayList = new ArrayList();
        int i = this.mPrimarySpinnerAdapter.getCount();
        for (int j = 0; j < i; j++)
        {
          CircleSpinnerInfo localCircleSpinnerInfo2 = (CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(j);
          String str = localCircleSpinnerInfo2.getRealCircleId();
          if ((!"v.all.circles".equals(str)) && (!"15".equals(str)) && (!"1c".equals(str)) && (!"v.nearby".equals(str)) && (!"v.whatshot".equals(str)))
            localArrayList.add(new CircleSubscriptionsDialog.CircleInfo(localCircleSpinnerInfo2.getCircleId(), localCircleSpinnerInfo2.getCircleName(), localCircleSpinnerInfo2.getMemberCount(), localCircleSpinnerInfo2.getVolume()));
        }
        getActivity();
        CircleSubscriptionsDialog localCircleSubscriptionsDialog = CircleSubscriptionsDialog.newInstance$51fb5134(localArrayList);
        localCircleSubscriptionsDialog.setTargetFragment(this, 0);
        localCircleSubscriptionsDialog.show(getFragmentManager(), "circle_subscriptions");
      }
    }
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
    {
      return bool;
      if (localCircleSpinnerInfo1.getVolume() == 4);
      for (int k = 1; ; k = 2)
      {
        CircleSubscribeDialog localCircleSubscribeDialog = new CircleSubscribeDialog();
        Bundle localBundle = new Bundle();
        localBundle.putInt("do_subscribe", k);
        localBundle.putString("circle_id", localCircleSpinnerInfo1.getCircleId());
        localBundle.putString("circle_name", localCircleSpinnerInfo1.getCircleName());
        localCircleSubscribeDialog.setArguments(localBundle);
        localCircleSubscribeDialog.setTargetFragment(this, 0);
        localCircleSubscribeDialog.show(getFragmentManager(), "circle_subscribe");
        break;
      }
    }
  }

  public void onPause()
  {
    super.onPause();
    this.mInnerAdapter.onPause();
    this.mGridView.onPause();
    EsService.unregisterListener(this.mServiceListener);
    removeLocationListener();
  }

  public final void onPlusOneAnimFinished()
  {
    if ((isPaused()) || (getActivity() == null))
      return;
    int i;
    int j;
    if ((this.mAnimatingPlusOneInfo != null) && (this.mGridView != null))
    {
      i = 0;
      j = this.mGridView.getChildCount();
    }
    while (true)
    {
      int k = 0;
      if (i < j)
      {
        View localView = this.mGridView.getChildAt(i);
        if ((localView instanceof StreamCardView))
        {
          StreamCardView localStreamCardView = (StreamCardView)localView;
          if (TextUtils.equals(localStreamCardView.getActivityId(), this.mAnimatingPlusOneInfo.activityId))
          {
            localStreamCardView.overridePlusOnedButtonDisplay(true, this.mAnimatingPlusOneInfo.overrideCount);
            k = 1;
          }
        }
      }
      else
      {
        if (k != 0)
          break;
        onShakeAnimFinished();
        break;
        break;
      }
      i++;
    }
  }

  public final void onPlusOneClicked(String paramString, DbPlusOneData paramDbPlusOneData, StreamCardView paramStreamCardView)
  {
    if (this.mAnimatingPlusOneInfo == null)
    {
      if ((Build.VERSION.SDK_INT < 12) || ((paramDbPlusOneData != null) && (paramDbPlusOneData.isPlusOnedByMe())))
      {
        togglePlusOne(paramString, paramDbPlusOneData);
        paramStreamCardView.overridePlusOnedButtonDisplay(false, 0);
      }
    }
    else
      return;
    View localView = getView();
    if (paramDbPlusOneData == null);
    for (int i = 1; ; i = 1 + paramDbPlusOneData.getCount())
    {
      this.mAnimatingPlusOneInfo = new PlusOneInfo(paramString, paramDbPlusOneData, i);
      PlusOneAnimatorView localPlusOneAnimatorView = (PlusOneAnimatorView)localView.findViewById(R.id.plus_one_animator);
      Pair localPair = paramStreamCardView.getPlusOneButtonAnimationCopies();
      localPlusOneAnimatorView.startPlusOneAnim(this, (ClickableButton)localPair.first, (ClickableButton)localPair.second);
      paramStreamCardView.startDelayedShakeAnimation();
      localView.findViewById(R.id.plus_one_glass).setVisibility(0);
      getView().postDelayed(new Runnable()
      {
        public final void run()
        {
          HostedStreamFragment.this.onShakeAnimFinished();
        }
      }
      , 915L);
      break;
    }
  }

  protected void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    if (this.mPrimarySpinnerAdapter == null)
    {
      this.mPrimarySpinnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item);
      this.mPrimarySpinnerAdapter.setDropDownViewResource(17367049);
    }
    paramHostActionBar.showPrimarySpinner(this.mPrimarySpinnerAdapter, this.mCurrentSpinnerPosition);
    paramHostActionBar.showRefreshButton();
    paramHostActionBar.updateRefreshButtonIcon(this.mStreamHasChanged);
    updateSpinner();
  }

  public void onPrepareOptionsMenu(Menu paramMenu)
  {
    MenuItem localMenuItem = paramMenu.findItem(R.id.subscribe);
    localMenuItem.setVisible(this.mOptionsMenuIsSubscribeVisible);
    if (this.mOptionsMenuIsSubscribeVisible)
    {
      localMenuItem.setTitle(this.mOptionsMenuSubscribeText);
      localMenuItem.setIcon(this.mOptionsMenuSubscribeIcon);
    }
  }

  public void onPrimarySpinnerSelectionChange(int paramInt)
  {
    CircleSpinnerInfo localCircleSpinnerInfo;
    OzViews localOzViews1;
    boolean bool;
    label111: View localView;
    label186: SharedPreferences.Editor localEditor;
    if (this.mCurrentSpinnerPosition != paramInt)
    {
      localCircleSpinnerInfo = (CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(paramInt);
      if (this.mAccount != null)
      {
        if (this.mCurrentSpinnerPosition < 0)
          break label318;
        localOzViews1 = getViewForLogging(((CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(this.mCurrentSpinnerPosition)).getRealCircleId());
        OzViews localOzViews2 = getViewForLogging(localCircleSpinnerInfo.getRealCircleId());
        clearNavigationAction();
        recordNavigationAction(localOzViews1, localOzViews2, null, null, null);
      }
      this.mCurrentSpinnerPosition = paramInt;
      this.mCircleId = localCircleSpinnerInfo.getCircleId();
      this.mView = localCircleSpinnerInfo.getView();
      if (this.mView != 2)
        break label326;
      bool = true;
      this.mNearby = bool;
      updateEmptyViewProgressText();
      this.mFirstLoad = true;
      this.mContinuationToken = null;
      localView = getView();
      if (!this.mNearby)
        break label331;
      addLocationListener(null);
      localView.findViewById(R.id.stream_location_layout).setVisibility(0);
      updateLocationHeader(localView);
      if (this.mLocation == null)
        showEmptyViewProgress(localView, getString(R.string.finding_your_location));
      prepareLoaderUri();
      getArguments().putString("circle_id", this.mCircleId);
      getArguments().putInt("view", this.mView);
      getLoaderManager().restartLoader(2, null, this);
      localEditor = getActivity().getSharedPreferences("streams", 0).edit();
      localEditor.putString("circle", ((CircleSpinnerInfo)this.mPrimarySpinnerAdapter.getItem(this.mCurrentSpinnerPosition)).getRealCircleId());
      if (Build.VERSION.SDK_INT < 9)
        break label356;
      localEditor.apply();
    }
    while (true)
    {
      this.mResetAnimationState = true;
      updateOptionsMenuInfo(localCircleSpinnerInfo);
      if (this.mComposeBarController != null)
        this.mComposeBarController.forceShow();
      refresh();
      return;
      label318: localOzViews1 = OzViews.HOME;
      break;
      label326: bool = false;
      break label111;
      label331: removeLocationListener();
      this.mLocation = null;
      localView.findViewById(R.id.stream_location_layout).setVisibility(8);
      break label186;
      label356: localEditor.commit();
    }
  }

  public final void onReshareClicked(String paramString, boolean paramBoolean)
  {
    Intent localIntent = Intents.getReshareActivityIntent(getActivity(), this.mAccount, paramString, paramBoolean);
    if (paramBoolean)
      ConfirmIntentDialog.newInstance(getString(R.string.reshare_dialog_title), getString(R.string.reshare_dialog_message), getString(R.string.reshare_dialog_positive_button), localIntent).show(getFragmentManager(), "confirm_reshare");
    while (true)
    {
      return;
      startActivity(localIntent);
    }
  }

  public void onResume()
  {
    if (this.mNewerReqId != null);
    for (int i = 1; ; i = 0)
    {
      super.onResume();
      EsService.registerListener(this.mServiceListener);
      if (this.mGridView == null)
        break label82;
      int j = 0;
      int k = this.mGridView.getChildCount();
      while (j < k)
      {
        if ((this.mGridView.getChildAt(j) instanceof CardView))
          CardView.onStart();
        j++;
      }
    }
    this.mGridView.onResume();
    label82: if (this.mNearby)
      addLocationListener(null);
    if ((i != 0) && (this.mNewerReqId == null))
      updateRefreshButton(false);
    updateSpinner();
    if (EsLog.isLoggable("HostedStreamFrag", 3))
      Log.d("HostedStreamFrag", "onResume mFragmentCreated: " + this.mFragmentCreated + ", mNewerReqId: " + this.mNewerReqId + ", gaia id: " + this.mGaiaId + ", time diff (ms): " + (System.currentTimeMillis() - this.mStreamChangeLastCheckTimeMs));
    if ((!this.mFragmentCreated) && (this.mNewerReqId == null) && (this.mGaiaId == null) && (System.currentTimeMillis() - this.mStreamChangeLastCheckTimeMs > 30000L))
    {
      if (!this.mNearby)
        break label300;
      if (this.mLocation != null)
        getLoaderManager().restartLoader(5, null, this);
    }
    while (true)
    {
      if ((this.mSetVolumeRequestId != null) && (!EsService.isRequestPending(this.mSetVolumeRequestId.intValue())))
      {
        handleOnSetVolumeControlCallback(EsService.removeResult(this.mSetVolumeRequestId.intValue()));
        this.mSetVolumeRequestId = null;
      }
      this.mFragmentCreated = false;
      return;
      label300: getLoaderManager().restartLoader(4, null, this);
    }
  }

  protected final void onResumeContentFetched(View paramView)
  {
    updateSpinner();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if ((!getActivity().isFinishing()) && (this.mGridView != null))
    {
      saveScrollPosition();
      paramBundle.putInt("scroll_pos", this.mScrollPos);
      paramBundle.putInt("scroll_off", this.mScrollOffset);
    }
    if (this.mLocation != null)
      paramBundle.putParcelable("location", this.mLocation);
    if (this.mLoaderHash != null)
      paramBundle.putInt("loader_hash", this.mLoaderHash.intValue());
    paramBundle.putInt("stream_length", this.mStreamLength);
    paramBundle.putLong("last_deactivation", this.mLastDeactivationTime);
    paramBundle.putBoolean("error", this.mError);
    paramBundle.putBoolean("reset_animation", this.mResetAnimationState);
    paramBundle.putLong("stream_change", this.mStreamChangeLastCheckTimeMs);
    paramBundle.putBoolean("stream_change_flag", this.mStreamHasChanged);
    paramBundle.putLong("recent_images_sync_timestamp", this.mRecentImagesSyncTimestamp);
    paramBundle.putBoolean("subscribe_visible", this.mOptionsMenuIsSubscribeVisible);
    paramBundle.putInt("subscribe_text", this.mOptionsMenuSubscribeText);
    paramBundle.putInt("subscribe_icon", this.mOptionsMenuSubscribeIcon);
    if (this.mSetVolumeRequestId != null)
    {
      paramBundle.putInt("set_volume_id", this.mSetVolumeRequestId.intValue());
      paramBundle.putInt("set_volume_value", this.mSetVolumeRequestVolume);
      paramBundle.putString("set_volume_circle", this.mSetVolumeRequestCircleName);
    }
  }

  protected void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mGaiaId = paramBundle.getString("gaia_id");
    if (this.mGaiaId == null)
    {
      this.mStreamOwnerUserId = this.mAccount.getGaiaId();
      this.mCircleId = paramBundle.getString("circle_id");
      if (!paramBundle.containsKey("view"))
        break label100;
    }
    label100: for (this.mView = paramBundle.getInt("view"); ; this.mView = 0)
    {
      int i = this.mView;
      boolean bool = false;
      if (i == 2)
        bool = true;
      this.mNearby = bool;
      updateEmptyViewProgressText();
      return;
      this.mStreamOwnerUserId = this.mGaiaId;
      break;
    }
  }

  public final void onShareRecentImages(ArrayList<MediaRef> paramArrayList)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
    {
      startActivityForCompose(Intents.getPostActivityIntent(localFragmentActivity, EsAccountsData.getActiveAccount(localFragmentActivity), paramArrayList));
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.STREAM_SHARE_INSTANT_UPLOAD_PHOTOS, getViewForLogging());
    }
  }

  public final void onStop()
  {
    super.onStop();
    int i = 0;
    int j = this.mGridView.getChildCount();
    while (i < j)
    {
      if ((this.mGridView.getChildAt(i) instanceof CardView))
        CardView.onStop();
      i++;
    }
    this.mGridView.invalidateViews();
  }

  public final void onViewUsed(int paramInt)
  {
    if ((this.mPreloadRequested) || (this.mEndOfStream) || (this.mError) || (this.mGridView == null));
    while (true)
    {
      return;
      if (paramInt >= this.mInnerAdapter.getCount() - sNextPagePreloadTriggerRows)
        prefetchContent();
    }
  }

  protected void prepareLoaderUri()
  {
    if ((this.mNearby) && (this.mLocation == null));
    DbLocation localDbLocation;
    for (this.mPostsUri = EsProvider.buildStreamUri(this.mAccount, "no_location_stream_key"); ; this.mPostsUri = EsProvider.buildStreamUri(this.mAccount, EsPostsData.buildActivitiesStreamKey(this.mGaiaId, this.mCircleId, localDbLocation, false, this.mView)))
    {
      return;
      Location localLocation = this.mLocation;
      localDbLocation = null;
      if (localLocation != null)
        localDbLocation = new DbLocation(0, this.mLocation);
    }
  }

  public void refresh()
  {
    if (this.mRefreshDisabled)
      this.mRefreshDisabled = false;
    while (true)
    {
      return;
      super.refresh();
      if (this.mNearby)
      {
        Location localLocation = this.mLocation;
        this.mLocation = null;
        addLocationListener(localLocation);
        updateSpinner();
      }
      else
      {
        fetchContent(true);
      }
    }
  }

  protected final void restoreScrollPosition()
  {
    if (this.mGridView == null);
    while (true)
    {
      return;
      if ((this.mScrollOffset != 0) || (this.mScrollPos != 0))
      {
        this.mGridView.setSelectionFromTop(this.mScrollPos, this.mScrollOffset);
        this.mScrollPos = 0;
        this.mScrollOffset = 0;
      }
    }
  }

  protected final void saveScrollPosition()
  {
    if (this.mGridView == null);
    while (true)
    {
      return;
      this.mScrollPos = this.mGridView.getFirstVisiblePosition();
      if (this.mAdapter != null)
      {
        View localView = this.mGridView.getChildAt(0);
        if (localView != null)
          this.mScrollOffset = localView.getTop();
        else
          this.mScrollOffset = 0;
      }
      else
      {
        this.mScrollOffset = 0;
      }
    }
  }

  protected final void showContent(View paramView)
  {
    super.showContent(paramView);
    if (this.mNearby)
    {
      paramView.findViewById(R.id.stream_location_layout).setVisibility(0);
      updateLocationHeader(paramView);
    }
    this.mGridView.setVisibility(0);
    this.mLocationDisabledView.setVisibility(8);
  }

  protected boolean showEmptyStream()
  {
    return getArguments().getBoolean("show_empty_stream", false);
  }

  protected final void showEmptyView(View paramView, String paramString)
  {
    super.showEmptyView(paramView, paramString);
    if (this.mNearby)
    {
      paramView.findViewById(R.id.stream_location_layout).setVisibility(0);
      updateLocationHeader(paramView);
    }
    this.mLocationDisabledView.setVisibility(8);
  }

  protected final void showEmptyViewProgress(View paramView)
  {
    super.showEmptyViewProgress(paramView);
    this.mLocationDisabledView.setVisibility(8);
  }

  protected final void showEmptyViewProgress(View paramView, String paramString)
  {
    super.showEmptyViewProgress(paramView, paramString);
    this.mLocationDisabledView.setVisibility(8);
  }

  protected void showProgressDialog(int paramInt)
  {
    ProgressFragmentDialog.newInstance(null, getString(paramInt), false).show(getFragmentManager(), "req_pending");
  }

  protected void startActivityForCompose(Intent paramIntent)
  {
    startActivity(paramIntent);
  }

  protected void startStreamOneUp(Intent paramIntent)
  {
    startActivity(paramIntent);
  }

  protected final void updateServerErrorView()
  {
    View localView = this.mServerErrorView;
    if (this.mError);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  private static final class CircleSpinnerInfo
  {
    private final String mCircleId;
    private final String mCircleName;
    private int mMemberCount;
    private final String mRealCircleId;
    private final int mView;
    private int mVolume;

    public CircleSpinnerInfo(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
      this.mRealCircleId = paramString2;
      this.mMemberCount = paramInt1;
      this.mVolume = paramInt2;
      if (paramString2.equals("v.all.circles"))
      {
        this.mView = 0;
        this.mCircleName = paramContext.getString(R.string.stream_circles);
        this.mCircleId = null;
      }
      while (true)
      {
        return;
        if (paramString2.equals("v.whatshot"))
        {
          this.mView = 1;
          this.mCircleName = paramContext.getString(R.string.stream_whats_hot);
          this.mCircleId = null;
        }
        else if (paramString2.equals("v.nearby"))
        {
          this.mView = 2;
          this.mCircleName = paramContext.getString(R.string.stream_nearby);
          this.mCircleId = null;
        }
        else
        {
          this.mView = 3;
          this.mCircleName = paramString1;
          this.mCircleId = paramString2;
        }
      }
    }

    public final String getCircleId()
    {
      return this.mCircleId;
    }

    public final String getCircleName()
    {
      return this.mCircleName;
    }

    public final int getMemberCount()
    {
      return this.mMemberCount;
    }

    public final String getRealCircleId()
    {
      return this.mRealCircleId;
    }

    public final int getView()
    {
      return this.mView;
    }

    public final int getVolume()
    {
      return this.mVolume;
    }

    public final int setVolume(int paramInt)
    {
      this.mVolume = paramInt;
      return paramInt;
    }

    public final String toString()
    {
      return this.mCircleName;
    }
  }

  private static abstract interface ContinuationTokenQuery
  {
    public static final String[] PROJECTION = { "token" };
  }

  private static final class PlusOneInfo
  {
    public String activityId;
    public int overrideCount;
    public DbPlusOneData plusOneData;

    public PlusOneInfo(String paramString, DbPlusOneData paramDbPlusOneData, int paramInt)
    {
      this.activityId = paramString;
      this.plusOneData = paramDbPlusOneData;
      this.overrideCount = paramInt;
    }
  }

  protected final class PostClickListener
    implements ItemClickListener
  {
    protected PostClickListener()
    {
    }

    public final void onSpanClick(URLSpan paramURLSpan)
    {
    }

    public final void onUserImageClick(String paramString1, String paramString2)
    {
      if (HostedStreamFragment.this.getArguments().getBoolean("view_as_plus_page", false));
      while (true)
      {
        return;
        String str = HostedStreamFragment.this.mGaiaId;
        int i = 0;
        if (str == null)
          i = 1;
        if ((!TextUtils.equals(HostedStreamFragment.this.mStreamOwnerUserId, paramString1)) || (i != 0))
        {
          Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", HostedStreamFragment.this.mStreamOwnerUserId);
          OzActions localOzActions = OzActions.STREAM_SELECT_AUTHOR;
          EsAnalytics.recordActionEvent(HostedStreamFragment.this.getActivity(), HostedStreamFragment.this.mAccount, localOzActions, HostedStreamFragment.this.getViewForLogging(), localBundle);
          HostedStreamFragment.this.startActivity(Intents.getProfileActivityByGaiaIdIntent(HostedStreamFragment.this.getActivity(), HostedStreamFragment.this.mAccount, paramString1, null));
        }
      }
    }
  }

  protected final class ServiceListener extends EsServiceListener
  {
    protected ServiceListener()
    {
    }

    public final void onCreatePostPlusOne$63505a2b(ServiceResult paramServiceResult)
    {
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(HostedStreamFragment.this.getActivity(), R.string.plusone_error, 0).show();
    }

    public final void onDeletePostPlusOne$63505a2b(ServiceResult paramServiceResult)
    {
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(HostedStreamFragment.this.getActivity(), R.string.delete_plusone_error, 0).show();
    }

    public final void onGetActivities$35a362dd(int paramInt1, boolean paramBoolean, int paramInt2, ServiceResult paramServiceResult)
    {
      HostedStreamFragment localHostedStreamFragment = HostedStreamFragment.this;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()));
      for (boolean bool = true; ; bool = false)
      {
        localHostedStreamFragment.mError = bool;
        if (EsLog.isLoggable("HostedStreamFrag", 4))
          Log.i("HostedStreamFrag", "onGetActivities - mError=" + HostedStreamFragment.this.mError);
        if (!paramBoolean)
          break label215;
        if ((HostedStreamFragment.this.mNewerReqId != null) && (HostedStreamFragment.this.mNewerReqId.equals(Integer.valueOf(paramInt1))))
          break;
        return;
      }
      HostedStreamFragment.this.mNewerReqId = null;
      if (!HostedStreamFragment.this.mError)
      {
        HostedStreamFragment.access$002(HostedStreamFragment.this, System.currentTimeMillis());
        HostedStreamFragment.this.updateRefreshButton(false);
      }
      while (true)
      {
        if ((HostedStreamFragment.this.mStreamLength != -1) || (HostedStreamFragment.this.mError))
        {
          if (!HostedStreamFragment.this.mError)
            HostedStreamFragment.access$202(HostedStreamFragment.this, paramInt2);
          HostedStreamFragment.this.getLoaderManager().restartLoader(2, null, HostedStreamFragment.this);
        }
        HostedStreamFragment.this.updateSpinner();
        HostedStreamFragment.this.updateServerErrorView();
        break;
        label215: if ((HostedStreamFragment.this.mOlderReqId == null) || (!HostedStreamFragment.this.mOlderReqId.equals(Integer.valueOf(paramInt1))))
          break;
        HostedStreamFragment.this.mOlderReqId = null;
        if (HostedStreamFragment.this.mError)
        {
          HostedStreamFragment.this.mPreloadRequested = false;
          if (EsLog.isLoggable("HostedStreamFrag", 4))
            Log.i("HostedStreamFrag", "onGetActivities - mPreloadRequested=false");
        }
      }
    }

    public final void onSetVolumeControlsRequestComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      if ((HostedStreamFragment.this.mSetVolumeRequestId == null) || (HostedStreamFragment.this.mSetVolumeRequestId.intValue() != paramInt));
      while (true)
      {
        return;
        HostedStreamFragment.this.handleOnSetVolumeControlCallback(paramServiceResult);
      }
    }
  }

  private final class StreamLocationListener
    implements LocationListener
  {
    private StreamLocationListener()
    {
    }

    public final void onLocationChanged(Location paramLocation)
    {
      if (HostedStreamFragment.this.mLocation != null);
      while (true)
      {
        return;
        HostedStreamFragment.this.mLocation = paramLocation;
        HostedStreamFragment.this.prepareLoaderUri();
        HostedStreamFragment.this.mFirstLoad = true;
        HostedStreamFragment.this.getLoaderManager().restartLoader(2, null, HostedStreamFragment.this);
        HostedStreamFragment.this.updateLocationHeader(HostedStreamFragment.this.getView());
        HostedStreamFragment.this.fetchContent(true);
      }
    }

    public final void onProviderDisabled(String paramString)
    {
    }

    public final void onProviderEnabled(String paramString)
    {
    }

    public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedStreamFragment
 * JD-Core Version:    0.6.2
 */