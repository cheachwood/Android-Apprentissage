package com.google.android.apps.plus.hangout;

import android.app.ActionBar;
import android.app.ActionBar.OnMenuVisibilityListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.AlertFragmentDialog.AlertDialogListener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.ProgressFragmentDialog;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.util.AccessibilityUtils;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.views.HangoutInviteesView;
import com.google.android.apps.plus.views.ScaledLayout;
import com.google.android.apps.plus.views.Tile.ParticipantPresenceListener;
import com.google.android.apps.plus.views.TwoPointerGestureDetector;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class HangoutTabletTile extends HangoutTile
  implements ViewGroup.OnHierarchyChangeListener
{
  private RelativeLayout bradyLayoutContainer;
  private final EventHandler eventHandler = new EventHandler();
  private ViewGroup hangoutLaunchJoinPanel;
  private View instructionsView;
  private Runnable instructionsViewFadeOutRunnable;
  private boolean isRegistered;
  private boolean isTileStarted;
  private ActionBar mActionBar;
  private CountDownTimer mActionBarDismissalTimer;
  private EsFragmentActivity mActivity;
  private RelativeLayout mCenterStageContainer;
  private RemoteVideoView.CenterStageVideoView mCenterStageVideo;
  private StageViewMode mCurrentStageMode;
  private boolean mEnableStageIcons;
  private Animation mFilmStripAnimOut;
  private View mFilmStripContainer;
  private boolean mFilmStripIsPaused = true;
  private CountDownTimer mFilmStripPauseTimer;
  private TabletFilmStripView mFilmStripView;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private Hangout.SupportStatus mHangoutSupportStatus;
  private ScaledLayout mInset;
  private FrameLayout mInsetVideo;
  private View mInviteesContainer;
  private TextView mInviteesMessageView;
  private HangoutInviteesView mInviteesView;
  private boolean mIsAudioEnabled = true;
  private boolean mIsAudioMuted;
  private boolean mIsHangoutLite = true;
  private boolean mIsVideoMuted;
  private Button mJoinButton;
  private LocalVideoView mLocalVideoView;
  private View mMessageContainer;
  private TextView mMessageView;
  private boolean mNeedToToastForInvite;
  private Animation mSlideInUp;
  private ToastsView mToastsView;
  private ViewMode mViewMode;
  private ProgressBar progressBar;
  private TextView progressText;
  private RelativeLayout stageLayoutContainer;
  private HangoutTile.State state;
  private HangoutTile.State stateBeforeStop;

  static
  {
    if (!HangoutTabletTile.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public HangoutTabletTile(Context paramContext)
  {
    this(paramContext, null);
  }

  public HangoutTabletTile(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public HangoutTabletTile(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = paramContext;
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile(): this=%s context=%s eventHandler=%s", arrayOfObject);
  }

  private void addVideoToCenterStage(HangoutVideoView paramHangoutVideoView)
  {
    ViewGroup localViewGroup = (ViewGroup)paramHangoutVideoView.getParent();
    if (localViewGroup == this.mCenterStageContainer);
    while (true)
    {
      return;
      if (localViewGroup != null)
        localViewGroup.removeView(paramHangoutVideoView);
      this.mCenterStageContainer.addView(paramHangoutVideoView);
      paramHangoutVideoView.setLayoutMode(HangoutVideoView.LayoutMode.FIT);
      this.mCenterStageContainer.invalidate();
      this.mCenterStageContainer.requestLayout();
    }
  }

  private void checkAndDismissCallgrokLogUploadProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)this.mActivity.getSupportFragmentManager().findFragmentByTag("log_upload");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
  }

  private void fadeOutInstructionsView()
  {
    if ((this.isRegistered) && (this.instructionsView.getVisibility() != 8))
    {
      Animation localAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
      localAnimation.setAnimationListener(new HideViewAnimationListener(this.instructionsView));
      this.instructionsView.startAnimation(localAnimation);
    }
  }

  private void hideActionBar()
  {
    if (this.mActionBar == null);
    while (true)
    {
      return;
      this.mActionBarDismissalTimer.cancel();
      if ((!AccessibilityUtils.isAccessibilityEnabled(getContext())) && (GCommApp.getInstance(getContext()).getGCommNativeWrapper().getHadSomeConnectedParticipantInPast()))
        this.mActionBar.hide();
    }
  }

  private void hideFilmStrip()
  {
    if (this.mFilmStripContainer.getVisibility() == 0)
      this.mFilmStripContainer.startAnimation(this.mFilmStripAnimOut);
  }

  private void pauseFilmStrip()
  {
    this.mFilmStripPauseTimer.cancel();
    if (!this.mFilmStripIsPaused)
    {
      this.mFilmStripIsPaused = true;
      this.mFilmStripView.onPause();
    }
  }

  private void resumeFilmStrip()
  {
    this.mFilmStripPauseTimer.cancel();
    if (this.mFilmStripIsPaused)
    {
      this.mFilmStripIsPaused = false;
      this.mFilmStripView.onResume();
    }
  }

  private void setStageViewMode(StageViewMode paramStageViewMode)
  {
    if ((this.mViewMode != ViewMode.MODE_STAGE_VIEW) || (paramStageViewMode == this.mCurrentStageMode));
    while (true)
    {
      return;
      switch (12.$SwitchMap$com$google$android$apps$plus$hangout$HangoutTabletTile$StageViewMode[paramStageViewMode.ordinal()])
      {
      default:
        Log.error("Unknown stage layout mode: " + paramStageViewMode);
      case 1:
      case 2:
      }
    }
    this.mCenterStageContainer.removeAllViews();
    addVideoToCenterStage(this.mLocalVideoView);
    this.mInset.setVisibility(8);
    this.mCenterStageContainer.setVisibility(0);
    while (true)
    {
      this.mCurrentStageMode = paramStageViewMode;
      break;
      this.mCenterStageContainer.removeAllViews();
      addVideoToCenterStage(this.mCenterStageVideo);
      LocalVideoView localLocalVideoView = this.mLocalVideoView;
      ViewGroup localViewGroup = (ViewGroup)localLocalVideoView.getParent();
      if (localViewGroup != this.mInsetVideo)
      {
        if (localViewGroup != null)
          localViewGroup.removeView(localLocalVideoView);
        this.mInsetVideo.removeAllViews();
        this.mInsetVideo.addView(localLocalVideoView);
        localLocalVideoView.setLayoutMode(HangoutVideoView.LayoutMode.FIT);
        this.mInsetVideo.invalidate();
        this.mInsetVideo.requestLayout();
      }
      this.mInset.setVisibility(0);
      this.mCenterStageContainer.setVisibility(0);
    }
  }

  private void setState(HangoutTile.State paramState)
  {
    Log.debug("Setting state to " + paramState);
    HangoutTile.State localState = this.state;
    this.state = paramState;
    if (!paramState.isInMeeting())
    {
      this.mToastsView.setVisibility(8);
      this.mInset.setVisibility(8);
      this.bradyLayoutContainer.setVisibility(8);
      if (this.mViewMode == ViewMode.MODE_STAGE_VIEW)
      {
        setStageViewMode(StageViewMode.STAGE_MODE_LOCAL_ONLY);
        this.mMessageContainer.setVisibility(8);
        this.mInviteesContainer.setVisibility(8);
        this.hangoutLaunchJoinPanel.setVisibility(0);
        switch (12.$SwitchMap$com$google$android$apps$plus$hangout$HangoutTile$State[paramState.ordinal()])
        {
        default:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        }
      }
    }
    label522: 
    while (true)
    {
      return;
      break;
      if (!this.skipGreenRoom)
      {
        this.instructionsView.setVisibility(0);
        this.mHandler.postDelayed(this.instructionsViewFadeOutRunnable, 5000L);
      }
      this.mJoinButton.setVisibility(8);
      this.progressBar.setVisibility(0);
      this.progressText.setVisibility(0);
      this.progressText.setText(R.string.hangout_launch_signing_in);
      continue;
      this.mJoinButton.setVisibility(0);
      Button localButton = this.mJoinButton;
      boolean bool1 = StressMode.isEnabled();
      boolean bool2 = false;
      if (!bool1)
        bool2 = true;
      localButton.setEnabled(bool2);
      this.progressBar.setVisibility(8);
      this.progressText.setVisibility(8);
      continue;
      fadeOutInstructionsView();
      this.mJoinButton.setVisibility(8);
      this.progressBar.setVisibility(0);
      this.progressText.setVisibility(0);
      this.progressText.setText(R.string.hangout_launch_joining);
      continue;
      this.instructionsView.setVisibility(8);
      this.hangoutLaunchJoinPanel.setVisibility(8);
      this.mJoinButton.setVisibility(8);
      this.progressBar.setVisibility(8);
      this.progressText.setVisibility(8);
      assert (this.state.isInMeeting());
      if (this.mViewMode == ViewMode.MODE_STAGE_VIEW)
      {
        this.stageLayoutContainer.setVisibility(0);
        this.bradyLayoutContainer.setVisibility(8);
        this.mLocalVideoView.setVisibility(0);
        this.mCenterStageVideo.setVisibility(0);
        showFilmStrip();
      }
      while (true)
      {
        updateHangoutViews();
        if (!this.isTileStarted)
          break;
        if (!localState.isInMeeting())
          break label522;
        if (this.mViewMode != ViewMode.MODE_STAGE_VIEW)
          break label509;
        this.mToastsView.onResume();
        resumeFilmStrip();
        break;
        if (this.mViewMode == ViewMode.MODE_BRADY_VIEW)
          this.stageLayoutContainer.setVisibility(8);
      }
      label509: if (this.mViewMode == ViewMode.MODE_BRADY_VIEW)
      {
        continue;
        if (this.mViewMode == ViewMode.MODE_STAGE_VIEW)
        {
          this.mCenterStageVideo.onResume();
          this.mLocalVideoView.onResume();
          resumeFilmStrip();
          this.mToastsView.onResume();
        }
      }
    }
  }

  private void showActionBar()
  {
    if (this.mActionBar == null);
    while (true)
    {
      return;
      this.mActionBar.show();
      this.mActionBarDismissalTimer.start();
    }
  }

  private void showError(GCommNativeWrapper.Error paramError, boolean paramBoolean)
  {
    switch (12.$SwitchMap$com$google$android$apps$plus$hangout$GCommNativeWrapper$Error[paramError.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      return;
      showError(R.string.hangout_authentication_error, paramBoolean);
      continue;
      showError(R.string.hangout_fatal_error, paramBoolean);
      continue;
      showError(R.string.hangout_fatal_error, paramBoolean);
      continue;
      showError(R.string.hangout_network_error, paramBoolean);
      continue;
      showError(R.string.hangout_audio_video_error, paramBoolean);
      continue;
      showError(R.string.hangout_unknown_error, paramBoolean);
    }
  }

  private void showFilmStrip()
  {
    int i = GCommApp.getInstance(getContext()).getGCommNativeWrapper().getMeetingMemberCount();
    if ((!this.mIsHangoutLite) && (i > 1) && (this.mFilmStripContainer.getVisibility() != 0))
      this.mFilmStripContainer.startAnimation(this.mSlideInUp);
  }

  private void updateAudioMuteMenuButtonState(Boolean paramBoolean)
  {
    boolean bool1;
    if (paramBoolean == null)
    {
      bool1 = GCommApp.getInstance(getContext()).isAudioMute();
      if (this.isRegistered)
        break label31;
    }
    label142: label152: 
    while (true)
    {
      return;
      bool1 = paramBoolean.booleanValue();
      break;
      label31: if ((!GCommApp.getInstance(getContext()).isInAHangoutWithMedia()) || (GCommApp.getInstance(getContext()).hasAudioFocus()));
      for (boolean bool2 = true; ; bool2 = false)
      {
        boolean bool3 = this.mIsAudioMuted;
        int i = 0;
        if (bool3 != bool1)
        {
          i = 1;
          this.mIsAudioMuted = bool1;
        }
        if (this.mIsAudioEnabled != bool2)
        {
          i = 1;
          this.mIsAudioEnabled = bool2;
        }
        if (i != 0)
          this.mActivity.invalidateOptionsMenu();
        if (this.mViewMode != ViewMode.MODE_STAGE_VIEW)
          break label152;
        if (!this.mIsAudioMuted)
          break label142;
        this.mLocalVideoView.showAudioMutedStatus();
        break;
      }
      this.mLocalVideoView.hideAudioMutedStatus();
    }
  }

  private void updateHangoutViews()
  {
    int i = 8;
    GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(getContext()).getGCommNativeWrapper();
    this.mIsHangoutLite = GCommApp.getInstance(getContext()).getGCommNativeWrapper().getIsHangoutLite();
    this.mActivity.invalidateOptionsMenu();
    if ((localGCommNativeWrapper == null) || (this.hangoutInfo == null))
      setStageViewMode(StageViewMode.STAGE_MODE_LOCAL_ONLY);
    while (true)
    {
      return;
      int j = localGCommNativeWrapper.getMeetingMemberCount();
      boolean bool = localGCommNativeWrapper.getHadSomeConnectedParticipantInPast();
      int k;
      label94: int m;
      if ((this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.Creation) && (this.hangoutInfo.getRingInvitees()))
      {
        k = 1;
        if ((this.state == null) || (!this.state.isInMeeting()))
          break label174;
        m = 1;
        label114: if (!bool)
          break label180;
        this.mInviteesContainer.setVisibility(i);
      }
      label287: 
      while (true)
      {
        this.mActionBarDismissalTimer.start();
        if (!localGCommNativeWrapper.getHasSomeConnectedParticipant())
          break label289;
        setStageViewMode(StageViewMode.STAGE_MODE_LOCAL_AND_REMOTE);
        this.mMessageContainer.setVisibility(i);
        this.mInviteesContainer.setVisibility(i);
        break;
        k = 0;
        break label94;
        label174: m = 0;
        break label114;
        label180: if ((k != 0) && (m != 0))
        {
          Intent localIntent = ((Activity)getContext()).getIntent();
          if ((localIntent.hasExtra("audience")) && (!localGCommNativeWrapper.getHadSomeConnectedParticipantInPast()))
          {
            AudienceData localAudienceData = (AudienceData)localIntent.getParcelableExtra("audience");
            this.mInviteesView.setInvitees(localAudienceData, getAccount());
            if (this.mInviteesView.getAvatarCount() > 0)
              this.mInviteesView.setVisibility(0);
          }
          for (k = 1; ; k = 0)
          {
            if (k == 0)
              break label287;
            this.mInviteesContainer.setVisibility(0);
            break;
          }
        }
      }
      label289: setStageViewMode(StageViewMode.STAGE_MODE_LOCAL_ONLY);
      if (this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.Ring)
      {
        if ((j == 1) && (!bool))
        {
          this.mMessageView.setText(getResources().getString(R.string.hangout_already_ended));
          this.mMessageContainer.setVisibility(0);
        }
      }
      else if ((bool) && ((this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.Ring) || (this.hangoutInfo.getRingInvitees())))
      {
        if ((j == 1) && (!bool))
        {
          this.mMessageView.setText(getResources().getString(R.string.hangout_no_one_joined));
          this.mMessageContainer.setVisibility(0);
        }
      }
      else
      {
        String str = getWaitingMessage(bool);
        if ((this.state != null) && (this.state.isInMeeting()))
          i = 0;
        if (m != 0)
          if (k != 0)
          {
            this.mInviteesMessageView.setText(str);
          }
          else
          {
            this.mMessageView.setText(str);
            this.mMessageContainer.setVisibility(i);
          }
      }
    }
  }

  private void updateVideoMuteMenuButtonState(Boolean paramBoolean)
  {
    boolean bool;
    if (paramBoolean == null)
    {
      bool = GCommApp.getInstance(getContext()).isOutgoingVideoMute();
      if (this.isRegistered)
        break label31;
    }
    while (true)
    {
      return;
      bool = paramBoolean.booleanValue();
      break;
      label31: if (this.mIsVideoMuted != bool)
      {
        this.mIsVideoMuted = bool;
        this.mActivity.invalidateOptionsMenu();
      }
    }
  }

  public final void hideChild(View paramView)
  {
    if (paramView == this.mFilmStripView)
      hideFilmStrip();
  }

  public final boolean isTileStarted()
  {
    return this.isTileStarted;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 0) && (paramInt2 == -1) && (paramIntent != null))
      this.mNeedToToastForInvite = true;
  }

  public void onChildViewAdded(View paramView1, View paramView2)
  {
    if (paramView1 == this.mFilmStripView)
      showFilmStrip();
  }

  public void onChildViewRemoved(View paramView1, View paramView2)
  {
    if ((paramView1 == this.mFilmStripView) && (this.mFilmStripView.getChildCount() <= 0))
      hideFilmStrip();
  }

  public final void onCreate(Bundle paramBundle)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onCreate: this=%s context=%s eventHandler=%s", arrayOfObject);
    if (paramBundle != null)
      this.stateBeforeStop = HangoutTile.State.values()[paramBundle.getInt("HangoutTile_state")];
    this.mEnableStageIcons = Property.ENABLE_HANGOUT_STAGE_STATUS.getBoolean();
    this.mActivity = ((EsFragmentActivity)getContext());
    this.mActionBar = this.mActivity.getActionBar();
    ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(R.layout.hangout_tablet_tile, this, true);
    this.mViewMode = ViewMode.MODE_STAGE_VIEW;
    this.mCurrentStageMode = StageViewMode.STAGE_MODE_INVALID;
    this.stageLayoutContainer = ((RelativeLayout)findViewById(R.id.stage_container));
    this.bradyLayoutContainer = ((RelativeLayout)findViewById(R.id.brady_container));
    this.mInset = ((ScaledLayout)findViewById(R.id.inset));
    this.mInsetVideo = ((FrameLayout)findViewById(R.id.inset_video_container));
    this.mLocalVideoView = new LocalVideoView(getContext(), null);
    this.mLocalVideoView.setHangoutTile(this);
    this.mFilmStripContainer = findViewById(R.id.filmstrip_container);
    updateAudioMuteMenuButtonState(null);
    if (Cameras.isAnyCameraAvailable())
      updateVideoMuteMenuButtonState(null);
    this.instructionsView = findViewById(R.id.hangout_green_room_instructions);
    this.instructionsViewFadeOutRunnable = new Runnable()
    {
      public final void run()
      {
        HangoutTabletTile.this.fadeOutInstructionsView();
      }
    };
    this.hangoutLaunchJoinPanel = ((ViewGroup)findViewById(R.id.hangout_launch_join_panel));
    this.mJoinButton = ((Button)findViewById(R.id.hangout_launch_join_button));
    this.mJoinButton.setOnClickListener(new View.OnClickListener()
    {
      static
      {
        if (!HangoutTabletTile.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      public final void onClick(View paramAnonymousView)
      {
        assert (HangoutTabletTile.this.state == HangoutTile.State.READY_TO_LAUNCH_MEETING) : HangoutTabletTile.this.state;
        HangoutTabletTile.this.setState(HangoutTile.State.ENTERING_MEETING);
        if (HangoutTabletTile.this.hangoutInfo == null)
        {
          Intent localIntent = ((Activity)HangoutTabletTile.this.getContext()).getIntent();
          GCommApp.getInstance(HangoutTabletTile.this.getContext()).createHangout(localIntent.getBooleanExtra("hangout_ring_invitees", false));
          return;
        }
        GCommApp localGCommApp = GCommApp.getInstance(HangoutTabletTile.this.getContext());
        Hangout.Info localInfo = HangoutTabletTile.this.hangoutInfo;
        if (HangoutTabletTile.this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.MissedCall);
        for (boolean bool = true; ; bool = false)
        {
          localGCommApp.enterHangout(localInfo, bool, HangoutTabletTile.this.greenRoomParticipants, HangoutTabletTile.this.mHoaConsented);
          break;
        }
      }
    });
    this.progressBar = ((ProgressBar)findViewById(R.id.hangout_launch_progress_bar));
    this.progressText = ((TextView)findViewById(R.id.hangout_launch_progress_text));
    this.mToastsView = ((ToastsView)findViewById(R.id.toasts_view));
    this.mCenterStageContainer = ((RelativeLayout)findViewById(R.id.center_stage_video_container));
    this.mCenterStageVideo = new RemoteVideoView.CenterStageVideoView(getContext(), null);
    this.mCenterStageVideo.setHangoutTile(this);
    this.mFilmStripView = ((TabletFilmStripView)findViewById(R.id.film_strip));
    this.mFilmStripView.setHangoutTile(this);
    this.mFilmStripView.setOnHierarchyChangeListener(this);
    this.mMessageContainer = findViewById(R.id.message_container);
    this.mMessageView = ((TextView)findViewById(R.id.message));
    this.mInviteesContainer = findViewById(R.id.invitees_container);
    this.mInviteesMessageView = ((TextView)findViewById(R.id.invitees_message));
    this.mInviteesView = ((HangoutInviteesView)findViewById(R.id.invitees_view));
    this.mSlideInUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_up_self);
    this.mFilmStripAnimOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down_self);
    this.mActionBarDismissalTimer = new CountDownTimer(5000L, 5000L)
    {
      public final void onFinish()
      {
        HangoutTabletTile.this.hideActionBar();
      }

      public final void onTick(long paramAnonymousLong)
      {
      }
    };
    this.mFilmStripPauseTimer = new CountDownTimer(30000L, 30000L)
    {
      public final void onFinish()
      {
        HangoutTabletTile.this.pauseFilmStrip();
      }

      public final void onTick(long paramAnonymousLong)
      {
      }
    };
    if (this.mActionBar != null)
      this.mActionBar.addOnMenuVisibilityListener(new ActionBar.OnMenuVisibilityListener()
      {
        public final void onMenuVisibilityChanged(boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
            HangoutTabletTile.this.mActionBarDismissalTimer.cancel();
          while (true)
          {
            return;
            HangoutTabletTile.this.mActionBarDismissalTimer.start();
          }
        }
      });
    this.mFilmStripAnimOut.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (HangoutTabletTile.this.mFilmStripContainer != null)
        {
          HangoutTabletTile.this.mFilmStripContainer.setVisibility(8);
          HangoutTabletTile.this.mFilmStripPauseTimer.start();
        }
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
    this.mSlideInUp.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
        HangoutTabletTile.this.resumeFilmStrip();
        if (HangoutTabletTile.this.mFilmStripContainer != null)
          HangoutTabletTile.this.mFilmStripContainer.setVisibility(0);
      }
    });
    this.mInsetVideo.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (HangoutTabletTile.this.mViewMode == HangoutTabletTile.ViewMode.MODE_STAGE_VIEW)
          HangoutTabletTile.access$1600(HangoutTabletTile.this);
      }
    });
    this.mCenterStageContainer.setOnTouchListener(new CenterStageTouchListener(getContext()));
    this.mCenterStageVideo.setOnDragListener(new View.OnDragListener()
    {
      private boolean onTarget(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = HangoutTabletTile.this.mCenterStageVideo.getWidth();
        int j = HangoutTabletTile.this.mCenterStageVideo.getHeight();
        int k = j / 4;
        if ((paramAnonymousInt1 >= 0) && (paramAnonymousInt1 + 0 <= i) && (paramAnonymousInt2 >= 0) && (paramAnonymousInt2 + k <= j));
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean onDrag(View paramAnonymousView, DragEvent paramAnonymousDragEvent)
      {
        float f = 1.0F;
        Object localObject = paramAnonymousDragEvent.getLocalState();
        boolean bool1 = localObject instanceof RemoteVideoView.ParticipantVideoView;
        RemoteVideoView.ParticipantVideoView localParticipantVideoView = null;
        if (bool1)
          localParticipantVideoView = (RemoteVideoView.ParticipantVideoView)localObject;
        boolean bool2;
        switch (paramAnonymousDragEvent.getAction())
        {
        default:
          bool2 = false;
        case 1:
        case 6:
        case 2:
        case 5:
        case 3:
        case 4:
        }
        while (true)
        {
          return bool2;
          if (localParticipantVideoView == null)
          {
            bool2 = false;
          }
          else
          {
            int j = HangoutTabletTile.this.getResources().getColor(R.color.hangout_drag_drop_off_target);
            HangoutTabletTile.this.mCenterStageVideo.setBackgroundColor(j);
            RemoteVideoView.CenterStageVideoView localCenterStageVideoView2 = HangoutTabletTile.this.mCenterStageVideo;
            if (j == 0);
            while (true)
            {
              localCenterStageVideoView2.setAlpha(f);
              HangoutTabletTile.this.mCenterStageVideo.invalidate();
              bool2 = true;
              break;
              f = 0.85F;
            }
            int i;
            label190: RemoteVideoView.CenterStageVideoView localCenterStageVideoView1;
            if (onTarget((int)paramAnonymousDragEvent.getX(), (int)paramAnonymousDragEvent.getY()))
            {
              i = HangoutTabletTile.this.getResources().getColor(R.color.hangout_drag_drop_on_target);
              HangoutTabletTile.this.mCenterStageVideo.setBackgroundColor(i);
              localCenterStageVideoView1 = HangoutTabletTile.this.mCenterStageVideo;
              if (i != 0)
                break label256;
            }
            while (true)
            {
              localCenterStageVideoView1.setAlpha(f);
              HangoutTabletTile.this.mCenterStageVideo.invalidate();
              bool2 = true;
              break;
              i = HangoutTabletTile.this.getResources().getColor(R.color.hangout_drag_drop_off_target);
              break label190;
              label256: f = 0.85F;
            }
            bool2 = onTarget((int)paramAnonymousDragEvent.getX(), (int)paramAnonymousDragEvent.getY());
            continue;
            HangoutTabletTile.this.mCenterStageVideo.setBackgroundColor(0);
            HangoutTabletTile.this.mCenterStageVideo.setAlpha(f);
            HangoutTabletTile.this.mCenterStageVideo.invalidate();
            if (paramAnonymousDragEvent.getResult())
              HangoutTabletTile.this.mFilmStripView.requestPinVideo(localParticipantVideoView);
            bool2 = true;
          }
        }
      }
    });
    final Animation localAnimation1 = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
    final Animation localAnimation2 = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
    localAnimation1.setDuration(500L);
    localAnimation2.setDuration(500L);
    this.mCenterStageVideo.setVideoChangeListener(new RemoteVideoView.VideoChangeListener()
    {
      private volatile boolean mIsActive;
      private boolean mSkipFirst = true;

      public final void onVideoSourceAboutToChange$1385ff()
      {
        if (this.mSkipFirst)
          this.mSkipFirst = false;
        while (true)
        {
          return;
          if (!this.mIsActive)
          {
            this.mIsActive = true;
            final ImageView localImageView = HangoutTabletTile.this.mCenterStageVideo.getSnapshotView();
            localImageView.setImageBitmap(HangoutTabletTile.this.mCenterStageVideo.getBitmap());
            localImageView.setVisibility(0);
            HangoutTabletTile.this.mCenterStageVideo.hideVideoSurface();
            HangoutTabletTile.this.mCenterStageVideo.hidePinnedStatus();
            HangoutTabletTile.this.mCenterStageVideo.hideAudioMutedStatus();
            localAnimation1.setAnimationListener(new Animation.AnimationListener()
            {
              public final void onAnimationEnd(Animation paramAnonymous2Animation)
              {
                localImageView.setVisibility(8);
                HangoutTabletTile.this.mCenterStageVideo.startAnimation(HangoutTabletTile.10.this.val$animIn);
                HangoutTabletTile.this.mCenterStageVideo.showVideoSurface();
                HangoutTabletTile.10.access$2902(HangoutTabletTile.10.this, false);
                HangoutTabletTile.access$3000(HangoutTabletTile.this, HangoutTabletTile.this.mCenterStageVideo.getCurrentVideoSource());
              }

              public final void onAnimationRepeat(Animation paramAnonymous2Animation)
              {
              }

              public final void onAnimationStart(Animation paramAnonymous2Animation)
              {
              }
            });
            localImageView.startAnimation(localAnimation1);
          }
        }
      }
    });
  }

  public final void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenuInflater.inflate(R.menu.hangout_exit_menu, paramMenu);
    paramMenuInflater.inflate(R.menu.hangout_camera_switch_menu, paramMenu);
    paramMenuInflater.inflate(R.menu.hangout_audio_toggle_menu, paramMenu);
    paramMenuInflater.inflate(R.menu.hangout_video_toggle_menu, paramMenu);
    paramMenuInflater.inflate(R.menu.hangout_invite_people_menu, paramMenu);
    paramMenu.findItem(R.id.hangout_video_toggle_menu_item).setVisible(Cameras.isAnyCameraAvailable());
    MenuItem localMenuItem = paramMenu.findItem(R.id.hangout_menu_switch_camera);
    if ((Cameras.isFrontFacingCameraAvailable()) && (Cameras.isRearFacingCameraAvailable()));
    for (boolean bool = true; ; bool = false)
    {
      localMenuItem.setVisible(bool);
      return;
    }
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool1 = true;
    int i = paramMenuItem.getItemId();
    if (i == R.id.hangout_exit_menu_item)
    {
      hideActionBar();
      Log.debug("HangoutTabletTile onExit with state:" + this.state);
      if (this.state != null)
      {
        if (!this.state.isInMeeting())
          break label78;
        Log.debug("Setting userRequestedMeetingExit to true");
        GCommApp.getInstance(getContext()).exitMeeting();
      }
    }
    while (true)
    {
      return bool1;
      label78: ((HangoutTile.HangoutTileActivity)getContext()).stopHangoutTile();
      Log.debug("Did not set userRequestedMeetingExit");
      continue;
      if (i == R.id.hangout_menu_switch_camera)
      {
        GCommApp.sendEmptyMessage(getContext(), 201);
        hideActionBar();
      }
      else
      {
        if (i == R.id.hangout_audio_toggle_menu_item)
        {
          GCommApp localGCommApp = GCommApp.getInstance(getContext());
          if (!localGCommApp.isAudioMute());
          for (boolean bool2 = bool1; ; bool2 = false)
          {
            localGCommApp.setAudioMute(bool2);
            hideActionBar();
            break;
          }
        }
        if (i == R.id.hangout_video_toggle_menu_item)
        {
          GCommApp.sendEmptyMessage(getContext(), 202);
          hideActionBar();
        }
        else if (i == R.id.hangout_invite_menu_item)
        {
          hideActionBar();
          inviteMoreParticipants();
        }
        else
        {
          bool1 = super.onOptionsItemSelected(paramMenuItem);
        }
      }
    }
  }

  public final void onPause()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onPause: this=%s context=%s eventHandler=%s", arrayOfObject);
    this.stateBeforeStop = this.state;
    this.state = null;
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    MenuItem localMenuItem1 = paramMenu.findItem(R.id.hangout_video_toggle_menu_item);
    int k;
    int m;
    int i;
    int j;
    label123: MenuItem localMenuItem3;
    if (localMenuItem1.isVisible())
    {
      this.mIsVideoMuted = GCommApp.getInstance(getContext()).isOutgoingVideoMute();
      if (this.mIsVideoMuted)
      {
        k = R.drawable.hangout_ic_menu_video_unmute;
        m = R.string.hangout_menu_video_unmute;
        localMenuItem1.setIcon(k);
        localMenuItem1.setTitle(m);
      }
    }
    else
    {
      MenuItem localMenuItem2 = paramMenu.findItem(R.id.hangout_audio_toggle_menu_item);
      if (localMenuItem2.isVisible())
      {
        this.mIsAudioMuted = GCommApp.getInstance(getContext()).isAudioMute();
        if (!this.mIsAudioMuted)
          break label197;
        i = R.drawable.hangout_ic_menu_audio_unmute;
        j = R.string.hangout_menu_audio_unmute;
        localMenuItem2.setIcon(i);
        localMenuItem2.setTitle(j);
        localMenuItem2.setEnabled(this.mIsAudioEnabled);
      }
      localMenuItem3 = paramMenu.findItem(R.id.hangout_invite_menu_item);
      if (this.mIsHangoutLite)
        break label210;
    }
    label197: label210: for (boolean bool = true; ; bool = false)
    {
      localMenuItem3.setVisible(bool);
      return;
      k = R.drawable.hangout_ic_menu_video_mute;
      m = R.string.hangout_menu_video_mute;
      break;
      i = R.drawable.hangout_ic_menu_audio_mute;
      j = R.string.hangout_menu_audio_mute;
      break label123;
    }
  }

  public final void onResume()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onResume: this=%s context=%s eventHandler=%s", arrayOfObject);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.state == null);
    for (HangoutTile.State localState = this.stateBeforeStop; ; localState = this.state)
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = this;
      arrayOfObject[1] = getContext();
      arrayOfObject[2] = this.eventHandler;
      arrayOfObject[3] = localState;
      Log.debug("HangoutTabletTile.onSaveInstanceState: this=%s context=%s eventHandler=%s stateToSave:%s", arrayOfObject);
      paramBundle.putInt("HangoutTile_state", localState.ordinal());
      return;
    }
  }

  public final void onStart()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onStart: this=%s context=%s eventHandler=%s", arrayOfObject);
    GCommApp.getInstance(getContext()).startingHangoutActivity(this.mActivity);
  }

  public final void onStop()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onStop: this=%s context=%s eventHandler=%s", arrayOfObject);
    GCommApp.getInstance(getContext()).stoppingHangoutActivity();
  }

  public final void onTilePause()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onTilePause: this=%s context=%s eventHandler=%s", arrayOfObject);
    if (this.mHangoutSupportStatus != Hangout.SupportStatus.SUPPORTED)
      return;
    if ((this.state != null) && (this.state.isInMeeting()))
      if (this.mViewMode == ViewMode.MODE_STAGE_VIEW)
      {
        this.mCenterStageVideo.onPause();
        this.mToastsView.onPause();
        pauseFilmStrip();
        label86: if (this.mViewMode != ViewMode.MODE_STAGE_VIEW)
          break label156;
        this.mLocalVideoView.onPause();
        this.mFilmStripView.dismissParticipantMenuDialog();
        this.mCurrentStageMode = StageViewMode.STAGE_MODE_INVALID;
      }
    label156: 
    while (true)
    {
      checkAndDismissCallgrokLogUploadProgressDialog();
      this.isTileStarted = false;
      break;
      if (this.mViewMode != ViewMode.MODE_BRADY_VIEW)
        break label86;
      break label86;
      this.mHandler.removeCallbacks(this.instructionsViewFadeOutRunnable);
      break label86;
    }
  }

  public final void onTileResume()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    arrayOfObject[3] = this.hangoutInfo;
    Log.debug("HangoutTabletTile.onTileResume: this=%s context=%s eventHandler=%s hangoutInfo=%s", arrayOfObject);
    assert (this.mAccount != null);
    showActionBar();
    this.mHangoutSupportStatus = Hangout.getSupportedStatus(getContext(), this.mAccount);
    if (this.mHangoutSupportStatus != Hangout.SupportStatus.SUPPORTED)
      showError(this.mHangoutSupportStatus.getErrorMessage(getContext()), true);
    while (true)
    {
      return;
      this.isTileStarted = true;
      setState(HangoutTile.State.START);
      if ((this.hangoutInfo != null) && (this.hangoutInfo.getRoomType() == Hangout.RoomType.UNKNOWN))
      {
        showError(R.string.hangout_not_supported_type, true);
      }
      else
      {
        if (this.mNeedToToastForInvite)
        {
          this.mToastsView.addToast(R.string.hangout_invites_sent);
          this.mNeedToToastForInvite = false;
        }
        if (GCommApp.getInstance(getContext()).hasAudioFocus())
          ((Activity)getContext()).setVolumeControlStream(0);
        while (true)
        {
          if (!GCommApp.getInstance(getContext()).isInHangout(this.hangoutInfo))
            break label246;
          setState(HangoutTile.State.IN_MEETING);
          break;
          if (GCommApp.getInstance(getContext()).isInAHangoutWithMedia())
            this.mToastsView.addToast(R.string.hangout_audiofocus_loss_warning);
        }
        label246: if ((this.stateBeforeStop != null) && (this.stateBeforeStop.isInMeeting()))
        {
          this.stateBeforeStop = null;
          if (ExitHistory.exitReported(getContext(), this.hangoutInfo))
          {
            ((HangoutTile.HangoutTileActivity)getContext()).stopHangoutTile();
            Log.debug("Stopping hangout tile. Exit from hangout already reported.");
          }
          else
          {
            if (ExitHistory.exitedNormally(getContext(), this.hangoutInfo))
              showError(R.string.hangout_exited, true);
            while (true)
            {
              ExitHistory.recordExitReported(getContext(), this.hangoutInfo);
              break;
              GCommNativeWrapper.Error localError = ExitHistory.getError(getContext(), this.hangoutInfo);
              if (localError != null)
                showError(localError, true);
              else
                showError(R.string.hangout_exit_generic, true);
            }
          }
        }
        else if (GCommApp.getInstance(getContext()).isInAHangout())
        {
          showError(R.string.hangout_launch_already_in_hangout, true);
        }
        else if (getGCommNativeWrapper().getCurrentState() == GCommNativeWrapper.GCommAppState.SIGNED_IN)
        {
          this.eventHandler.onSignedIn(GCommApp.getInstance(getContext()).getGCommNativeWrapper().getUserJid());
        }
        else
        {
          GCommApp.getInstance(getContext()).disconnect();
          setState(HangoutTile.State.SIGNING_IN);
          GCommApp.getInstance(getContext()).signinUser(getAccount());
        }
      }
    }
  }

  public final void onTileStart()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onTileStart: this=%s context=%s eventHandler=%s", arrayOfObject);
    if (this.mAccount == null)
      throw new RuntimeException("setHangoutInfo must be called before switching to HangoutTabletTile");
    GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
    this.isRegistered = true;
  }

  public final void onTileStop()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.eventHandler;
    Log.debug("HangoutTabletTile.onTileStop: this=%s context=%s eventHandler=%s", arrayOfObject);
    this.mActionBarDismissalTimer.cancel();
    this.isRegistered = false;
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.eventHandler, false);
  }

  public void setParticipants(HashMap<String, Data.Participant> paramHashMap, HashSet<String> paramHashSet)
  {
  }

  public final void showChild(View paramView)
  {
    if (paramView == this.mFilmStripView)
      showFilmStrip();
  }

  public final void transfer()
  {
    AudienceData localAudienceData = new AudienceData(null, null);
    Log.debug("Transfer hangout");
    GCommApp.getInstance(getContext()).getGCommNativeWrapper().inviteToMeeting(localAudienceData, "TRANSFER", true, false);
    this.mToastsView.addToast(R.string.hangout_transfer_sent);
  }

  public final void updateMainVideoStreaming()
  {
    if ((this.mViewMode == ViewMode.MODE_STAGE_VIEW) && (this.state != null) && (this.state.isInMeeting()))
      this.mCenterStageVideo.updateVideoStreaming();
  }

  private final class CenterStageTouchListener extends TwoPointerGestureDetector
  {
    CenterStageTouchListener(Context arg2)
    {
      super();
    }

    public final boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      boolean bool = true;
      int i = HangoutTabletTile.this.getHeight() / 2;
      int j = (int)paramMotionEvent1.getY();
      if ((paramFloat2 > 0.0F) && (paramFloat2 > Math.abs(paramFloat1)))
        if (j >= i)
          HangoutTabletTile.this.hideChild(HangoutTabletTile.this.mFilmStripView);
      while (true)
      {
        return bool;
        if (j <= i)
          HangoutTabletTile.this.showActionBar();
        else if ((paramFloat2 < 0.0F) && (paramFloat2 < -Math.abs(paramFloat1)))
        {
          if (j >= i)
            HangoutTabletTile.this.showChild(HangoutTabletTile.this.mFilmStripView);
          else if (j <= i)
            HangoutTabletTile.this.hideActionBar();
        }
        else
          bool = super.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
      }
    }

    public final boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      HangoutTabletTile.access$1600(HangoutTabletTile.this);
      return true;
    }

    public final boolean onTwoPointerSwipe(MotionEvent.PointerCoords paramPointerCoords1, MotionEvent.PointerCoords paramPointerCoords2, float paramFloat1, float paramFloat2)
    {
      boolean bool = true;
      int i;
      int j;
      int k;
      if (Math.abs(paramPointerCoords1.y - paramPointerCoords2.y) < 250.0F)
      {
        i = HangoutTabletTile.this.getWidth();
        j = (int)paramPointerCoords1.x;
        k = (int)paramPointerCoords2.x;
        if (j < k)
        {
          if ((j >= i / 2) || (k <= i * 7 / 8))
            break label115;
          HangoutTabletTile.access$1700(HangoutTabletTile.this, bool);
        }
      }
      while (true)
      {
        return bool;
        if ((j > i / 2) && (k < i / 8))
          HangoutTabletTile.access$1700(HangoutTabletTile.this, false);
        else
          label115: bool = super.onTwoPointerSwipe(paramPointerCoords1, paramPointerCoords2, paramFloat1, paramFloat2);
      }
    }
  }

  final class EventHandler extends GCommEventHandler
  {
    static
    {
      if (!HangoutTabletTile.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    EventHandler()
    {
    }

    private void notifyListeners()
    {
      if (HangoutTabletTile.this.listeners == null);
      while (true)
      {
        return;
        Iterator localIterator = HangoutTabletTile.this.listeners.iterator();
        while (localIterator.hasNext())
          ((Tile.ParticipantPresenceListener)localIterator.next()).onParticipantPresenceChanged();
      }
    }

    public final void onAudioMuteStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onAudioMuteStateChanged(paramMeetingMember, paramBoolean);
      if ((paramMeetingMember == null) || (paramMeetingMember.isSelf()))
        HangoutTabletTile.this.updateAudioMuteMenuButtonState(Boolean.valueOf(paramBoolean));
      while (true)
      {
        return;
        HangoutTabletTile.access$1400(HangoutTabletTile.this, paramMeetingMember, paramBoolean);
      }
    }

    public final void onCallgrokLogUploadCompleted$4f708078()
    {
      HangoutTabletTile.this.checkAndDismissCallgrokLogUploadProgressDialog();
      HangoutTabletTile.this.getHangoutTileActivity().stopHangoutTile();
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      Log.info("HangoutTabletTile$EventHandler.onError(%s) %s", new Object[] { paramError, this });
      if (paramError == GCommNativeWrapper.Error.AUTHENTICATION)
      {
        assert (HangoutTabletTile.this.state.isSigningIn()) : HangoutTabletTile.this.state;
        HangoutTabletTile.access$500(HangoutTabletTile.this);
      }
      while (true)
      {
        if (HangoutTabletTile.this.hangoutInfo != null)
          ExitHistory.recordErrorExit(HangoutTabletTile.this.getContext(), HangoutTabletTile.this.hangoutInfo, paramError, true);
        return;
        HangoutTabletTile.this.showError(paramError, true);
      }
    }

    public final void onHangoutCreated(Hangout.Info paramInfo)
    {
      super.onHangoutCreated(paramInfo);
      HangoutTabletTile.this.hangoutInfo = paramInfo;
      Log.debug("HangoutTabletTile.onHangoutCreated: " + paramInfo);
      HangoutTabletTile.this.updateHangoutViews();
      GCommApp.getInstance(HangoutTabletTile.this.getContext()).enterHangout(paramInfo, true, HangoutTabletTile.this.greenRoomParticipants, HangoutTabletTile.this.mHoaConsented);
    }

    public final void onHangoutWaitTimeout(Hangout.Info paramInfo)
    {
      super.onHangoutWaitTimeout(paramInfo);
      HangoutTabletTile.this.mMessageView.setText(HangoutTabletTile.this.getResources().getString(R.string.hangout_no_one_joined));
    }

    public final void onMeetingEnterError(GCommNativeWrapper.MeetingEnterError paramMeetingEnterError)
    {
      super.onMeetingEnterError(paramMeetingEnterError);
      if (!HangoutTabletTile.this.isRegistered);
      while (true)
      {
        return;
        HangoutTabletTile.this.setState(HangoutTile.State.READY_TO_LAUNCH_MEETING);
        if (paramMeetingEnterError == GCommNativeWrapper.MeetingEnterError.HANGOUT_ON_AIR)
        {
          HangoutTabletTile.this.showHoaNotification(HangoutTabletTile.this.mJoinButton);
        }
        else
        {
          if (paramMeetingEnterError != GCommNativeWrapper.MeetingEnterError.OUTDATED_CLIENT)
            break;
          HangoutTabletTile.access$800(HangoutTabletTile.this);
        }
      }
      boolean bool;
      int i;
      switch (HangoutTabletTile.12.$SwitchMap$com$google$android$apps$plus$hangout$GCommNativeWrapper$MeetingEnterError[paramMeetingEnterError.ordinal()])
      {
      default:
        bool = false;
        i = R.string.hangout_enter_unknown_error;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      }
      while (true)
      {
        Log.debug("HangoutTabletTile.onMeetingEnterError: " + paramMeetingEnterError);
        HangoutTabletTile.this.showError(i, bool);
        break;
        i = R.string.hangout_enter_timeout_error;
        bool = false;
        continue;
        i = R.string.hangout_enter_blocked_error;
        bool = false;
        continue;
        i = R.string.hangout_enter_blocking_error;
        bool = false;
        continue;
        i = R.string.hangout_enter_max_users_error;
        bool = false;
        continue;
        i = R.string.hangout_enter_server_error;
        bool = false;
        continue;
        i = R.string.hangout_enter_green_room_info_error;
        bool = false;
        continue;
        bool = true;
        i = R.string.hangout_enter_hangout_over;
      }
    }

    public final void onMeetingExited(boolean paramBoolean)
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this;
      arrayOfObject[1] = HangoutTabletTile.this;
      arrayOfObject[2] = Boolean.valueOf(paramBoolean);
      Log.debug("HangoutTabletTile$EventHandler.onMeetingExited: this=%s, tile=%s clientInitiated=%s", arrayOfObject);
      super.onMeetingExited(paramBoolean);
      if ((!HangoutTabletTile.this.isRegistered) || (HangoutTabletTile.this.state == null))
        return;
      if (paramBoolean)
        if (StringUtils.getDomain(HangoutTabletTile.this.mAccount.getName()).equals("google.com"))
        {
          HangoutTabletTile.this.getGCommNativeWrapper().uploadCallgrokLog();
          ProgressFragmentDialog.newInstance(HangoutTabletTile.this.getResources().getString(R.string.hangout_log_upload_title), HangoutTabletTile.this.getResources().getString(R.string.hangout_log_upload_message)).show(HangoutTabletTile.this.mActivity.getSupportFragmentManager(), "log_upload");
        }
      while (true)
      {
        ExitHistory.recordNormalExit(HangoutTabletTile.this.getContext(), HangoutTabletTile.this.hangoutInfo, true);
        break;
        HangoutTabletTile.this.getHangoutTileActivity().stopHangoutTile();
        continue;
        HangoutTabletTile.this.showError(R.string.hangout_exited, true);
      }
    }

    public final void onMeetingMediaStarted()
    {
      super.onMeetingMediaStarted();
      if (!HangoutTabletTile.this.isRegistered);
      while (true)
      {
        return;
        HangoutTabletTile.this.setState(HangoutTile.State.IN_MEETING);
        HangoutTabletTile.this.updateHangoutViews();
        HangoutTabletTile.this.updateAudioMuteMenuButtonState(null);
        HangoutTabletTile.this.showFilmStrip();
        GCommApp.getInstance(HangoutTabletTile.this.getContext()).getGCommService().showHangoutNotification(HangoutTabletTile.this.getHangoutTileActivity().getHangoutNotificationIntent());
        HangoutTabletTile.this.getHangoutTileActivity().onMeetingMediaStarted();
      }
    }

    public final void onMeetingMemberEntered(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberEntered(paramMeetingMember);
      HangoutTabletTile.this.updateHangoutViews();
      notifyListeners();
    }

    public final void onMeetingMemberExited(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberExited(paramMeetingMember);
      HangoutTabletTile.this.updateHangoutViews();
      notifyListeners();
      HangoutTabletTile.access$100(HangoutTabletTile.this, paramMeetingMember);
    }

    public final void onMeetingMemberPresenceConnectionStatusChanged(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberPresenceConnectionStatusChanged(paramMeetingMember);
      HangoutTabletTile.this.updateHangoutViews();
      notifyListeners();
    }

    public final void onMucEntered(MeetingMember paramMeetingMember)
    {
      super.onMucEntered(paramMeetingMember);
      HangoutTabletTile.this.sendInvites();
    }

    public final void onRemoteMute(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2)
    {
      if (paramMeetingMember1.isSelf())
        HangoutTabletTile.this.updateAudioMuteMenuButtonState(Boolean.valueOf(true));
      while (true)
      {
        return;
        HangoutTabletTile.access$1400(HangoutTabletTile.this, paramMeetingMember1, true);
      }
    }

    public final void onSignedIn(String paramString)
    {
      boolean bool = true;
      super.onSignedIn(paramString);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this;
      arrayOfObject[bool] = HangoutTabletTile.this;
      Log.debug("HangoutTabletTile$EventHandler.onSignedIn: this=%s, tile=%s", arrayOfObject);
      assert (HangoutTabletTile.this.state.isSigningIn()) : HangoutTabletTile.this.state;
      if (!HangoutTabletTile.this.isRegistered);
      while (true)
      {
        return;
        if (HangoutTabletTile.this.skipGreenRoom)
        {
          HangoutTabletTile.this.setState(HangoutTile.State.ENTERING_MEETING);
          if (HangoutTabletTile.this.hangoutInfo == null)
          {
            Intent localIntent = ((Activity)HangoutTabletTile.this.getContext()).getIntent();
            GCommApp.getInstance(HangoutTabletTile.this.getContext()).createHangout(localIntent.getBooleanExtra("hangout_ring_invitees", false));
          }
          else
          {
            GCommApp localGCommApp = GCommApp.getInstance(HangoutTabletTile.this.getContext());
            Hangout.Info localInfo = HangoutTabletTile.this.hangoutInfo;
            if (HangoutTabletTile.this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.MissedCall);
            while (true)
            {
              localGCommApp.enterHangout(localInfo, bool, HangoutTabletTile.this.greenRoomParticipants, HangoutTabletTile.this.mHoaConsented);
              break;
              bool = false;
            }
          }
        }
        else
        {
          HangoutTabletTile.this.setState(HangoutTile.State.READY_TO_LAUNCH_MEETING);
        }
      }
    }

    public final void onSignedOut()
    {
      super.onSignedOut();
      Log.info("HangoutTabletTile$EventHandler.onSignedOut");
      if (!HangoutTabletTile.this.isRegistered);
      while (true)
      {
        return;
        HangoutTabletTile.this.showError(R.string.hangout_signin_timeout_error, true);
        HangoutTabletTile.this.setState(HangoutTile.State.SIGNIN_ERROR);
      }
    }

    public final void onSigninTimeOutError()
    {
      super.onSigninTimeOutError();
      Log.info("HangoutTabletTile$EventHandler.onSigninTimeOutError: this=" + this);
      if (!HangoutTabletTile.this.isRegistered);
      while (true)
      {
        return;
        HangoutTabletTile.this.showError(R.string.hangout_signin_timeout_error, true);
        HangoutTabletTile.this.setState(HangoutTile.State.SIGNIN_ERROR);
      }
    }

    public final void onVideoMuteChanged(boolean paramBoolean)
    {
      HangoutTabletTile.this.updateVideoMuteMenuButtonState(Boolean.valueOf(paramBoolean));
    }

    public final void onVolumeChanged(MeetingMember paramMeetingMember, int paramInt)
    {
      if ((paramMeetingMember == null) || (paramMeetingMember.isSelf()))
        HangoutTabletTile.this.updateAudioMuteMenuButtonState(Boolean.valueOf(false));
      while (true)
      {
        return;
        HangoutTabletTile.access$1400(HangoutTabletTile.this, paramMeetingMember, false);
      }
    }
  }

  private static enum StageViewMode
  {
    static
    {
      STAGE_MODE_LOCAL_AND_REMOTE = new StageViewMode("STAGE_MODE_LOCAL_AND_REMOTE", 2);
      StageViewMode[] arrayOfStageViewMode = new StageViewMode[3];
      arrayOfStageViewMode[0] = STAGE_MODE_INVALID;
      arrayOfStageViewMode[1] = STAGE_MODE_LOCAL_ONLY;
      arrayOfStageViewMode[2] = STAGE_MODE_LOCAL_AND_REMOTE;
    }
  }

  private static enum ViewMode
  {
    static
    {
      MODE_BRADY_VIEW = new ViewMode("MODE_BRADY_VIEW", 1);
      ViewMode[] arrayOfViewMode = new ViewMode[2];
      arrayOfViewMode[0] = MODE_STAGE_VIEW;
      arrayOfViewMode[1] = MODE_BRADY_VIEW;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutTabletTile
 * JD-Core Version:    0.6.2
 */