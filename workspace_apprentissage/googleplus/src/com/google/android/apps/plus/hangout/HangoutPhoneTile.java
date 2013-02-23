package com.google.android.apps.plus.hangout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
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
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.views.HangoutInviteesView;
import com.google.android.apps.plus.views.ParticipantsGalleryView;
import com.google.android.apps.plus.views.ParticipantsGalleryView.SimpleCommandListener;
import com.google.android.apps.plus.views.Tile.ParticipantPresenceListener;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class HangoutPhoneTile extends HangoutTile
{
  private TextView mEmptyHangoutMessageView;
  private final EventHandler mEventHandler = new EventHandler();
  private FilmStripView mFilmStripView;
  private ParticipantsGalleryView mGreenRoomParticipantsGalleryView;
  private boolean mHadConnectedParticipant;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private ViewGroup mHangoutLaunchJoinPanel;
  private HangoutParticipantsGalleryView mHangoutParticipantsGalleryView;
  private Hangout.SupportStatus mHangoutSupportStatus;
  private ImageButton mHangoutSwitchMenuButton;
  private boolean mInnerActionBarEnabled = true;
  private View mInstructionsView;
  private Runnable mInstructionsViewFadeOutRunnable;
  private ImageButton mInviteParticipantsMenuButton;
  private HangoutInviteesView mInviteesView;
  private boolean mIsHangoutLite = true;
  private boolean mIsTileStarted;
  private Button mJoinButton;
  private IncomingVideoView.MainVideoView mMainVideoView;
  private View mMessageContainer;
  private TextView mMessageView;
  private boolean mNeedToToastForInvite;
  private View mParticipantsView;
  private ViewGroup mRootView;
  private SelfVideoView mSelfVideoView;
  private FrameLayout mSelfVideoViewContainer;
  private boolean mShowOverlayMenu;
  private HangoutTile.State mState;
  private HangoutTile.State mStateBeforeStop;
  private ImageButton mSwitchCameraMenuItem;
  private View mTitleBarView;
  private ToastsView mToastsView;
  private ImageButton mToggleAudioMuteMenuButton;
  private ImageButton mToggleVideoMuteMenuButton;
  private View mTopMenuView;
  private View mTouchSensorView;
  private View mUpButton;

  static
  {
    if (!HangoutPhoneTile.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public HangoutPhoneTile(Context paramContext)
  {
    this(paramContext, null);
  }

  public HangoutPhoneTile(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public HangoutPhoneTile(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = paramContext;
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile(): this=%s context=%s eventHandler=%s", arrayOfObject);
  }

  private void addSelfVideoViewToRootView()
  {
    ViewGroup localViewGroup = (ViewGroup)this.mSelfVideoView.getParent();
    if (localViewGroup == this.mSelfVideoViewContainer);
    while (true)
    {
      return;
      if (localViewGroup != null)
        localViewGroup.removeView(this.mSelfVideoView);
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
      this.mSelfVideoView.setLayoutParams(localLayoutParams);
      this.mSelfVideoViewContainer.addView(this.mSelfVideoView);
    }
  }

  private void checkAndDismissCallgrokLogUploadProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)((EsFragmentActivity)getContext()).getSupportFragmentManager().findFragmentByTag("log_upload");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
  }

  private void fadeOutInstructionsView()
  {
    if ((this.mInstructionsView != null) && (this.mInstructionsView.getVisibility() != 8))
    {
      Animation localAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
      localAnimation.setAnimationListener(new HideViewAnimationListener(this.mInstructionsView));
      this.mInstructionsView.startAnimation(localAnimation);
    }
  }

  private void setState(HangoutTile.State paramState)
  {
    boolean bool = true;
    Log.debug("Setting state to " + paramState);
    HangoutTile.State localState = this.mState;
    this.mState = paramState;
    if (!paramState.isInMeeting())
    {
      this.mHangoutParticipantsGalleryView.setVisibility(8);
      this.mToastsView.setVisibility(8);
      this.mMainVideoView.setVisibility(8);
      this.mFilmStripView.setVisibility(8);
      addSelfVideoViewToRootView();
      this.mTopMenuView.setVisibility(8);
      this.mInviteesView.setVisibility(8);
      this.mEmptyHangoutMessageView.setVisibility(0);
      this.mSelfVideoView.setLayoutMode(SelfVideoView.LayoutMode.FIT);
      switch (14.$SwitchMap$com$google$android$apps$plus$hangout$HangoutTile$State[paramState.ordinal()])
      {
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
    }
    while (true)
    {
      return;
      if (!this.skipGreenRoom)
      {
        this.mGreenRoomParticipantsGalleryView.removeAllParticipants();
        this.mGreenRoomParticipantsGalleryView.setVisibility(0);
        if (this.greenRoomParticipants != null)
          this.mGreenRoomParticipantsGalleryView.addParticipants(this.greenRoomParticipants);
        this.mGreenRoomParticipantsGalleryView.setCommandListener(new ParticipantsGalleryView.SimpleCommandListener(this.mGreenRoomParticipantsGalleryView, getAccount())
        {
          public final void onShowParticipantList()
          {
            Intent localIntent = HangoutPhoneTile.this.getHangoutTileActivity().getGreenRoomParticipantListActivityIntent(HangoutPhoneTile.this.greenRoomParticipants);
            HangoutPhoneTile.this.getContext().startActivity(localIntent);
          }
        });
        this.mInstructionsView.setVisibility(0);
        this.mHandler.postDelayed(this.mInstructionsViewFadeOutRunnable, 5000L);
      }
      this.mJoinButton.setVisibility(8);
      this.mMessageContainer.setVisibility(0);
      this.mMessageView.setText(R.string.hangout_launch_signing_in);
      continue;
      this.mHangoutLaunchJoinPanel.setVisibility(0);
      this.mJoinButton.setVisibility(0);
      Button localButton = this.mJoinButton;
      if (!StressMode.isEnabled());
      while (true)
      {
        localButton.setEnabled(bool);
        this.mMessageContainer.setVisibility(8);
        break;
        bool = false;
      }
      fadeOutInstructionsView();
      this.mJoinButton.setVisibility(8);
      this.mMessageContainer.setVisibility(0);
      this.mMessageView.setText(R.string.hangout_launch_joining);
      continue;
      this.mGreenRoomParticipantsGalleryView.setVisibility(8);
      this.mInstructionsView.setVisibility(8);
      this.mHangoutLaunchJoinPanel.setVisibility(8);
      this.mJoinButton.setVisibility(8);
      this.mMainVideoView.setVisibility(0);
      assert (this.mState.isInMeeting());
      this.mIsHangoutLite = GCommApp.getInstance(getContext()).getGCommNativeWrapper().getIsHangoutLite();
      if (!this.mIsHangoutLite)
        this.mInviteParticipantsMenuButton.setVisibility(0);
      if (this.mState == HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET)
      {
        if (!this.mIsHangoutLite)
        {
          this.mTopMenuView.setVisibility(0);
          this.mParticipantsView.setVisibility(0);
          this.mHangoutParticipantsGalleryView.setVisibility(0);
        }
        assert (this.mState == HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET);
        final Animation localAnimation1 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_down_self);
        final Animation localAnimation2 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_up_self);
        final Animation localAnimation3 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_up_self);
        final Animation localAnimation4 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down_self);
        final View localView = findViewById(R.id.overlay_menu);
        if (localView.getVisibility() == 0)
        {
          label588: this.mShowOverlayMenu = bool;
          this.mTouchSensorView.setOnTouchListener(new View.OnTouchListener()
          {
            public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
            {
              if (paramAnonymousMotionEvent.getAction() == 1)
              {
                if (!HangoutPhoneTile.this.mShowOverlayMenu)
                  break label137;
                if ((!HangoutPhoneTile.this.mIsHangoutLite) && (HangoutPhoneTile.this.mHadConnectedParticipant))
                {
                  HangoutPhoneTile.this.mTopMenuView.startAnimation(localAnimation2);
                  HangoutPhoneTile.this.mTopMenuView.setVisibility(8);
                }
                localAnimation4.setAnimationListener(new HangoutPhoneTile.OverlayMenuSlideOutAnimationListener(HangoutPhoneTile.this, (byte)0));
                localView.startAnimation(localAnimation4);
                localView.setVisibility(8);
              }
              while (true)
              {
                HangoutPhoneTile localHangoutPhoneTile = HangoutPhoneTile.this;
                boolean bool1 = HangoutPhoneTile.this.mShowOverlayMenu;
                boolean bool2 = false;
                if (!bool1)
                  bool2 = true;
                HangoutPhoneTile.access$1602(localHangoutPhoneTile, bool2);
                return true;
                label137: if ((!HangoutPhoneTile.this.mIsHangoutLite) && (HangoutPhoneTile.this.mHadConnectedParticipant))
                {
                  HangoutPhoneTile.this.mTopMenuView.startAnimation(localAnimation1);
                  HangoutPhoneTile.this.mTopMenuView.setVisibility(0);
                }
                localAnimation4.setAnimationListener(null);
                localView.startAnimation(localAnimation3);
                localView.setVisibility(0);
                HangoutPhoneTile.this.mSelfVideoView.setExtraBottomOffset(localView.getHeight());
              }
            }
          });
          int i = getResources().getDimensionPixelOffset(R.dimen.hangout_overlay_menu_height);
          this.mSelfVideoView.setExtraBottomOffset(i);
          this.mSelfVideoView.setVisibility(0);
          addSelfVideoViewToRootView();
          this.mFilmStripView.setVisibility(8);
        }
      }
      while (true)
      {
        updateOverlayMenuAndMessageViews();
        this.mSelfVideoView.setVisibleViewOnTouchListener(new View.OnTouchListener()
        {
          public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
          {
            if (!Property.NATIVE_HANGOUT_LOG.getBoolean());
            while (paramAnonymousMotionEvent.getAction() != 1)
              return true;
            HangoutPhoneTile localHangoutPhoneTile = HangoutPhoneTile.this;
            HangoutTile.State localState;
            label42: SharedPreferences.Editor localEditor;
            if (HangoutPhoneTile.this.mState == HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET)
            {
              localState = HangoutTile.State.IN_MEETING_WITH_FILM_STRIP;
              localHangoutPhoneTile.setState(localState);
              localEditor = HangoutPhoneTile.this.getContext().getSharedPreferences("com.google.android.apps.plus.hangout.HangoutTile", 0).edit();
              if (HangoutPhoneTile.this.mState != HangoutTile.State.IN_MEETING_WITH_FILM_STRIP)
                break label115;
            }
            label115: for (boolean bool = true; ; bool = false)
            {
              localEditor.putBoolean("filmStrip_", bool);
              localEditor.commit();
              break;
              localState = HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET;
              break label42;
            }
          }
        });
        if (!this.mIsTileStarted)
          break;
        if (!localState.isInMeeting())
          break label797;
        if (paramState != HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET)
          break label776;
        this.mToastsView.onResume();
        this.mFilmStripView.onPause();
        break;
        bool = false;
        break label588;
        this.mTopMenuView.setVisibility(8);
        this.mParticipantsView.setVisibility(8);
        this.mHangoutParticipantsGalleryView.setVisibility(8);
        this.mTouchSensorView.setOnTouchListener(null);
        this.mSelfVideoView.setVisibility(0);
        this.mFilmStripView.setVisibility(0);
      }
      label776: this.mToastsView.onPause();
      label797: 
      do
      {
        this.mFilmStripView.onResume(this.mSelfVideoView);
        break;
        this.mMainVideoView.onResume();
        this.mHangoutParticipantsGalleryView.setMainVideoRequestId(this.mMainVideoView.getRequestId());
        this.mSelfVideoView.startCapturing();
      }
      while (paramState != HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET);
      this.mToastsView.onResume();
    }
  }

  private void showError(GCommNativeWrapper.Error paramError, boolean paramBoolean)
  {
    switch (14.$SwitchMap$com$google$android$apps$plus$hangout$GCommNativeWrapper$Error[paramError.ordinal()])
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

  private void updateAudioMuteMenuButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mToggleAudioMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_audio_unmute);
      this.mToggleAudioMuteMenuButton.setContentDescription(getResources().getString(R.string.hangout_menu_audio_unmute));
    }
    while (true)
    {
      return;
      this.mToggleAudioMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_audio_mute);
      this.mToggleAudioMuteMenuButton.setContentDescription(getResources().getString(R.string.hangout_menu_audio_mute));
    }
  }

  private void updateAudioMuteMenuButtonState(Boolean paramBoolean)
  {
    if (paramBoolean == null);
    for (boolean bool1 = GCommApp.getInstance(getContext()).isAudioMute(); this.mToggleAudioMuteMenuButton == null; bool1 = paramBoolean.booleanValue())
      return;
    updateAudioMuteMenuButton(bool1);
    ImageButton localImageButton = this.mToggleAudioMuteMenuButton;
    if ((!GCommApp.getInstance(getContext()).isInAHangoutWithMedia()) || (GCommApp.getInstance(getContext()).hasAudioFocus()));
    for (boolean bool2 = true; ; bool2 = false)
    {
      localImageButton.setEnabled(bool2);
      break;
    }
  }

  private void updateOverlayMenuAndMessageViews()
  {
    GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(getContext()).getGCommNativeWrapper();
    if ((localGCommNativeWrapper == null) || (this.hangoutInfo == null));
    while (true)
    {
      return;
      this.mHadConnectedParticipant = localGCommNativeWrapper.getHadSomeConnectedParticipantInPast();
      HangoutInviteesView localHangoutInviteesView = this.mInviteesView;
      int i;
      label46: label73: SelfVideoView localSelfVideoView;
      if (this.mHadConnectedParticipant)
      {
        i = 8;
        localHangoutInviteesView.setVisibility(i);
        if (!this.mHadConnectedParticipant)
          break label146;
        if (this.mShowOverlayMenu)
          this.mTopMenuView.setVisibility(0);
        localSelfVideoView = this.mSelfVideoView;
        if (!localGCommNativeWrapper.getHasSomeConnectedParticipant())
          break label253;
      }
      label146: label251: label253: for (SelfVideoView.LayoutMode localLayoutMode = SelfVideoView.LayoutMode.INSET; ; localLayoutMode = SelfVideoView.LayoutMode.FIT)
      {
        localSelfVideoView.setLayoutMode(localLayoutMode);
        this.mSelfVideoView.requestLayout();
        if (localGCommNativeWrapper.getMeetingMemberCount() > 1)
          this.mEmptyHangoutMessageView.setVisibility(8);
        if (!localGCommNativeWrapper.getHasSomeConnectedParticipant())
          break label261;
        this.mMessageContainer.setVisibility(8);
        break;
        i = 0;
        break label46;
        this.mTopMenuView.setVisibility(8);
        Intent localIntent = ((Activity)getContext()).getIntent();
        if ((localIntent.hasExtra("audience")) && (!localGCommNativeWrapper.getHadSomeConnectedParticipantInPast()))
        {
          AudienceData localAudienceData = (AudienceData)localIntent.getParcelableExtra("audience");
          if (localAudienceData.getUserCount() > 0)
          {
            this.mInviteesView.setInvitees(localAudienceData, getAccount());
            this.mInviteesView.setVisibility(0);
          }
        }
        for (int j = 1; ; j = 0)
        {
          if (j != 0)
            break label251;
          this.mInviteesView.setVisibility(8);
          break;
        }
        break label73;
      }
      label261: if (this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.Ring)
      {
        if ((localGCommNativeWrapper.getMeetingMemberCount() == 1) && (!localGCommNativeWrapper.getHadSomeConnectedParticipantInPast()))
        {
          this.mMessageContainer.setVisibility(0);
          this.mMessageView.setText(getResources().getString(R.string.hangout_already_ended));
          this.mEmptyHangoutMessageView.setVisibility(8);
        }
      }
      else if (!localGCommNativeWrapper.getHadSomeConnectedParticipantInPast())
      {
        if ((this.mState != null) && (this.mState.isInMeeting()))
        {
          this.mMessageContainer.setVisibility(0);
          this.mMessageView.setText(getWaitingMessage(false));
        }
        this.mEmptyHangoutMessageView.setVisibility(8);
      }
      else if (((this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.Ring) || (this.hangoutInfo.getRingInvitees())) && (localGCommNativeWrapper.getMeetingMemberCount() == 1) && (!localGCommNativeWrapper.getHadSomeConnectedParticipantInPast()))
      {
        this.mMessageContainer.setVisibility(0);
        this.mMessageView.setText(getResources().getString(R.string.hangout_no_one_joined));
        this.mEmptyHangoutMessageView.setVisibility(8);
      }
    }
  }

  private void updateVideoMuteMenuButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mToggleVideoMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_video_unmute);
      this.mToggleVideoMuteMenuButton.setContentDescription(getResources().getString(R.string.hangout_menu_video_unmute));
    }
    while (true)
    {
      return;
      this.mToggleVideoMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_video_mute);
      this.mToggleVideoMuteMenuButton.setContentDescription(getResources().getString(R.string.hangout_menu_video_mute));
    }
  }

  private void updateVideoMuteMenuButtonState(Boolean paramBoolean)
  {
    boolean bool;
    if (paramBoolean == null)
    {
      bool = GCommApp.getInstance(getContext()).isOutgoingVideoMute();
      if (this.mToggleVideoMuteMenuButton != null)
        break label31;
    }
    while (true)
    {
      return;
      bool = paramBoolean.booleanValue();
      break;
      label31: updateVideoMuteMenuButton(bool);
    }
  }

  public final boolean isTileStarted()
  {
    return this.mIsTileStarted;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 0) && (paramInt2 == -1) && (paramIntent != null))
      this.mNeedToToastForInvite = true;
  }

  public final void onCreate(Bundle paramBundle)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onCreate: this=%s context=%s eventHandler=%s", arrayOfObject);
    if (paramBundle != null)
      this.mStateBeforeStop = HangoutTile.State.values()[paramBundle.getInt("HangoutTile_state")];
  }

  public final void onPause()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onPause: this=%s context=%s eventHandler=%s", arrayOfObject);
    this.mStateBeforeStop = this.mState;
    this.mState = null;
  }

  public final void onResume()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onResume: this=%s context=%s eventHandler=%s", arrayOfObject);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mState == null);
    for (HangoutTile.State localState = this.mStateBeforeStop; ; localState = this.mState)
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = this;
      arrayOfObject[1] = getContext();
      arrayOfObject[2] = this.mEventHandler;
      arrayOfObject[3] = localState;
      Log.debug("HangoutPhoneTile.onSaveInstanceState: this=%s context=%s eventHandler=%s stateToSave:%s", arrayOfObject);
      paramBundle.putInt("HangoutTile_state", localState.ordinal());
      return;
    }
  }

  public final void onStart()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onStart: this=%s context=%s eventHandler=%s", arrayOfObject);
    GCommApp.getInstance(getContext()).startingHangoutActivity((EsFragmentActivity)getContext());
  }

  public final void onStop()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onStop: this=%s context=%s eventHandler=%s", arrayOfObject);
    GCommApp.getInstance(getContext()).stoppingHangoutActivity();
  }

  public final void onTilePause()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onTilePause: this=%s context=%s eventHandler=%s", arrayOfObject);
    if (this.mHangoutSupportStatus != Hangout.SupportStatus.SUPPORTED)
      return;
    if ((this.mState != null) && (this.mState.isInMeeting()))
    {
      this.mMainVideoView.onPause();
      if (this.mState == HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET)
        this.mToastsView.onPause();
    }
    while (true)
    {
      this.mSelfVideoView.onPause();
      this.mGreenRoomParticipantsGalleryView.dismissAvatarMenuDialog();
      this.mHangoutParticipantsGalleryView.onPause();
      checkAndDismissCallgrokLogUploadProgressDialog();
      this.mIsTileStarted = false;
      break;
      if (this.mState == HangoutTile.State.IN_MEETING_WITH_FILM_STRIP)
      {
        this.mFilmStripView.onPause();
        continue;
        this.mHandler.removeCallbacks(this.mInstructionsViewFadeOutRunnable);
      }
    }
  }

  public final void onTileResume()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    arrayOfObject[3] = this.hangoutInfo;
    Log.debug("HangoutPhoneTile.onTileResume: this=%s context=%s eventHandler=%s hangoutInfo=%s", arrayOfObject);
    assert (this.mAccount != null);
    this.mHangoutSupportStatus = Hangout.getSupportedStatus(getContext(), this.mAccount);
    if (this.mHangoutSupportStatus != Hangout.SupportStatus.SUPPORTED)
      showError(this.mHangoutSupportStatus.getErrorMessage(getContext()), true);
    while (true)
    {
      return;
      this.mIsTileStarted = true;
      setState(HangoutTile.State.START);
      this.mSelfVideoView.onResume();
      this.mHangoutParticipantsGalleryView.onResume();
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
        {
          ((Activity)getContext()).setVolumeControlStream(0);
          label203: if (!GCommApp.getInstance(getContext()).isInHangout(this.hangoutInfo))
            break label288;
          if (!getContext().getSharedPreferences("com.google.android.apps.plus.hangout.HangoutTile", 0).getBoolean("filmStrip_", false))
            break label281;
        }
        label281: for (HangoutTile.State localState = HangoutTile.State.IN_MEETING_WITH_FILM_STRIP; ; localState = HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET)
        {
          setState(localState);
          break;
          if (!GCommApp.getInstance(getContext()).isInAHangoutWithMedia())
            break label203;
          this.mToastsView.addToast(R.string.hangout_audiofocus_loss_warning);
          break label203;
        }
        label288: if ((this.mStateBeforeStop != null) && (this.mStateBeforeStop.isInMeeting()))
        {
          this.mStateBeforeStop = null;
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
          this.mEventHandler.onSignedIn(GCommApp.getInstance(getContext()).getGCommNativeWrapper().getUserJid());
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
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onTileStart: this=%s context=%s eventHandler=%s", arrayOfObject);
    assert (this.mAccount != null);
    addView(((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(R.layout.hangout_tile, null));
    this.mRootView = ((ViewGroup)findViewById(R.id.hangout_tile_root_view));
    this.mTopMenuView = findViewById(R.id.hangout_top_menu);
    this.mTitleBarView = findViewById(R.id.title_bar);
    View localView = this.mTitleBarView;
    if (this.mInnerActionBarEnabled);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      this.mParticipantsView = findViewById(R.id.hangout_participants_info);
      this.mTouchSensorView = findViewById(R.id.touch_sensor_view);
      this.mSelfVideoViewContainer = ((FrameLayout)findViewById(R.id.self_video_container));
      this.mSelfVideoView = new SelfVideoView(getContext(), null);
      this.mSelfVideoView.setHangoutTile(this);
      this.mUpButton = findViewById(R.id.up);
      this.mUpButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          ((Activity)HangoutPhoneTile.this.getContext()).onBackPressed();
        }
      });
      this.mInviteParticipantsMenuButton = ((ImageButton)findViewById(R.id.invite_participants));
      this.mInviteParticipantsMenuButton.setVisibility(8);
      this.mInviteParticipantsMenuButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          HangoutPhoneTile.this.inviteMoreParticipants();
        }
      });
      final GCommApp localGCommApp = GCommApp.getInstance(getContext());
      this.mToggleAudioMuteMenuButton = ((ImageButton)findViewById(R.id.hangout_menu_common_toggle_audio_mute));
      updateAudioMuteMenuButton(localGCommApp.isAudioMute());
      this.mToggleAudioMuteMenuButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          GCommApp localGCommApp = localGCommApp;
          if (!localGCommApp.isAudioMute());
          for (boolean bool = true; ; bool = false)
          {
            localGCommApp.setAudioMute(bool);
            return;
          }
        }
      });
      this.mToggleVideoMuteMenuButton = ((ImageButton)findViewById(R.id.hangout_menu_common_toggle_video_mute));
      updateVideoMuteMenuButton(localGCommApp.isOutgoingVideoMute());
      this.mToggleVideoMuteMenuButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          GCommApp.sendEmptyMessage(HangoutPhoneTile.this.getContext(), 202);
        }
      });
      this.mHangoutSwitchMenuButton = ((ImageButton)findViewById(R.id.hangout_menu_common_hangout_switch));
      if ((this.mHangoutSwitchMenuButton != null) && (Property.ENABLE_HANGOUT_SWITCH.getBoolean()))
      {
        this.mHangoutSwitchMenuButton.setVisibility(0);
        this.mHangoutSwitchMenuButton.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            HangoutPhoneTile.this.transfer();
          }
        });
      }
      this.mSwitchCameraMenuItem = ((ImageButton)findViewById(R.id.hangout_menu_common_switch_camera));
      this.mSwitchCameraMenuItem.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          GCommApp.sendEmptyMessage(HangoutPhoneTile.this.getContext(), 201);
        }
      });
      findViewById(R.id.hangout_menu_common_exit).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          HangoutPhoneTile.access$1400(HangoutPhoneTile.this, paramAnonymousView);
        }
      });
      updateAudioMuteMenuButtonState(null);
      if (Cameras.isAnyCameraAvailable())
      {
        this.mToggleVideoMuteMenuButton.setVisibility(0);
        updateVideoMuteMenuButtonState(null);
        if ((Cameras.isFrontFacingCameraAvailable()) && (Cameras.isRearFacingCameraAvailable()))
          this.mSwitchCameraMenuItem.setVisibility(0);
      }
      this.mInstructionsView = findViewById(R.id.hangout_green_room_instructions);
      this.mInstructionsViewFadeOutRunnable = new Runnable()
      {
        public final void run()
        {
          HangoutPhoneTile.this.fadeOutInstructionsView();
        }
      };
      this.mHangoutLaunchJoinPanel = ((ViewGroup)findViewById(R.id.hangout_launch_join_panel));
      this.mJoinButton = ((Button)findViewById(R.id.hangout_launch_join_button));
      this.mJoinButton.setOnClickListener(new View.OnClickListener()
      {
        static
        {
          if (!HangoutPhoneTile.class.desiredAssertionStatus());
          for (boolean bool = true; ; bool = false)
          {
            $assertionsDisabled = bool;
            return;
          }
        }

        public final void onClick(View paramAnonymousView)
        {
          assert (HangoutPhoneTile.this.mState == HangoutTile.State.READY_TO_LAUNCH_MEETING) : HangoutPhoneTile.this.mState;
          HangoutPhoneTile.this.setState(HangoutTile.State.ENTERING_MEETING);
          if (HangoutPhoneTile.this.hangoutInfo == null)
          {
            Intent localIntent = ((Activity)HangoutPhoneTile.this.getContext()).getIntent();
            GCommApp.getInstance(HangoutPhoneTile.this.getContext()).createHangout(localIntent.getBooleanExtra("hangout_ring_invitees", false));
            return;
          }
          GCommApp localGCommApp = GCommApp.getInstance(HangoutPhoneTile.this.getContext());
          Hangout.Info localInfo = HangoutPhoneTile.this.hangoutInfo;
          if (HangoutPhoneTile.this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.MissedCall);
          for (boolean bool = true; ; bool = false)
          {
            localGCommApp.enterHangout(localInfo, bool, HangoutPhoneTile.this.greenRoomParticipants, HangoutPhoneTile.this.mHoaConsented);
            break;
          }
        }
      });
      this.mGreenRoomParticipantsGalleryView = ((ParticipantsGalleryView)findViewById(R.id.green_room_participants_view));
      this.mToastsView = ((ToastsView)findViewById(R.id.toasts_view));
      this.mMainVideoView = ((IncomingVideoView.MainVideoView)findViewById(R.id.main_video));
      this.mMainVideoView.setHangoutTile(this);
      this.mFilmStripView = ((FilmStripView)findViewById(R.id.film_strip));
      this.mFilmStripView.setHangoutTile(this);
      this.mHangoutParticipantsGalleryView = ((HangoutParticipantsGalleryView)findViewById(R.id.hangout_participants_view));
      this.mHangoutParticipantsGalleryView.setHangoutTile(this);
      this.mMessageView = ((TextView)findViewById(R.id.message));
      this.mMessageContainer = findViewById(R.id.message_container);
      this.mInviteesView = ((HangoutInviteesView)findViewById(R.id.invitees_view));
      this.mEmptyHangoutMessageView = ((TextView)this.mHangoutParticipantsGalleryView.findViewById(R.id.empty_message));
      this.mHangoutParticipantsGalleryView.setAccount(this.mAccount);
      this.mGreenRoomParticipantsGalleryView.setAccount(this.mAccount);
      GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.mEventHandler, false);
      return;
    }
  }

  public final void onTileStop()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this;
    arrayOfObject[1] = getContext();
    arrayOfObject[2] = this.mEventHandler;
    Log.debug("HangoutPhoneTile.onTileStop: this=%s context=%s eventHandler=%s", arrayOfObject);
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.mEventHandler, false);
    this.mTouchSensorView.setOnTouchListener(null);
    removeAllViews();
    this.mRootView = null;
    this.mTopMenuView = null;
    this.mTitleBarView = null;
    this.mParticipantsView = null;
    this.mTouchSensorView = null;
    this.mToggleAudioMuteMenuButton = null;
    this.mToggleVideoMuteMenuButton = null;
    this.mSwitchCameraMenuItem = null;
    this.mSelfVideoViewContainer = null;
    this.mSelfVideoView = null;
    this.mGreenRoomParticipantsGalleryView = null;
    this.mInstructionsView = null;
    this.mHangoutLaunchJoinPanel = null;
    this.mJoinButton = null;
    this.mHangoutParticipantsGalleryView = null;
    this.mToastsView = null;
    this.mMainVideoView = null;
    this.mFilmStripView = null;
    this.mMessageView = null;
    this.mMessageContainer = null;
    this.mInviteesView = null;
    this.mEmptyHangoutMessageView = null;
  }

  public final HangoutPhoneTile setInnerActionBarEnabled(boolean paramBoolean)
  {
    this.mInnerActionBarEnabled = false;
    if (this.mTitleBarView != null)
      this.mTitleBarView.setVisibility(8);
    return this;
  }

  public void setParticipants(HashMap<String, Data.Participant> paramHashMap, HashSet<String> paramHashSet)
  {
    if (this.mHangoutParticipantsGalleryView != null)
      this.mHangoutParticipantsGalleryView.setParticipants(paramHashMap, paramHashSet);
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
    this.mMainVideoView.updateVideoStreaming();
  }

  final class EventHandler extends GCommEventHandler
  {
    static
    {
      if (!HangoutPhoneTile.class.desiredAssertionStatus());
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
      if (HangoutPhoneTile.this.listeners == null);
      while (true)
      {
        return;
        Iterator localIterator = HangoutPhoneTile.this.listeners.iterator();
        while (localIterator.hasNext())
          ((Tile.ParticipantPresenceListener)localIterator.next()).onParticipantPresenceChanged();
      }
    }

    public final void onAudioMuteStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      if ((paramMeetingMember == null) || (paramMeetingMember.isSelf()))
        HangoutPhoneTile.this.updateAudioMuteMenuButtonState(Boolean.valueOf(paramBoolean));
    }

    public final void onCallgrokLogUploadCompleted$4f708078()
    {
      HangoutPhoneTile.this.checkAndDismissCallgrokLogUploadProgressDialog();
      HangoutPhoneTile.this.getHangoutTileActivity().stopHangoutTile();
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      Log.info("HangoutPhoneTile$EventHandler.onError(%s) %s", new Object[] { paramError, this });
      if (paramError == GCommNativeWrapper.Error.AUTHENTICATION)
      {
        assert (HangoutPhoneTile.this.mState.isSigningIn()) : HangoutPhoneTile.this.mState;
        HangoutPhoneTile.access$500(HangoutPhoneTile.this);
      }
      while (true)
      {
        if (HangoutPhoneTile.this.hangoutInfo != null)
          ExitHistory.recordErrorExit(HangoutPhoneTile.this.getContext(), HangoutPhoneTile.this.hangoutInfo, paramError, true);
        return;
        HangoutPhoneTile.this.showError(paramError, true);
      }
    }

    public final void onHangoutCreated(Hangout.Info paramInfo)
    {
      super.onHangoutCreated(paramInfo);
      HangoutPhoneTile.this.hangoutInfo = paramInfo;
      Log.debug("HangoutPhoneTile.onHangoutCreated: " + paramInfo);
      HangoutPhoneTile.this.updateOverlayMenuAndMessageViews();
      GCommApp.getInstance(HangoutPhoneTile.this.getContext()).enterHangout(paramInfo, true, HangoutPhoneTile.this.greenRoomParticipants, HangoutPhoneTile.this.mHoaConsented);
    }

    public final void onHangoutWaitTimeout(Hangout.Info paramInfo)
    {
      super.onHangoutWaitTimeout(paramInfo);
      HangoutPhoneTile.this.mMessageContainer.setVisibility(0);
      HangoutPhoneTile.this.mMessageView.setText(HangoutPhoneTile.this.getResources().getString(R.string.hangout_no_one_joined));
    }

    public final void onMeetingEnterError(GCommNativeWrapper.MeetingEnterError paramMeetingEnterError)
    {
      super.onMeetingEnterError(paramMeetingEnterError);
      if (HangoutPhoneTile.this.mRootView == null);
      while (true)
      {
        return;
        HangoutPhoneTile.this.setState(HangoutTile.State.READY_TO_LAUNCH_MEETING);
        if (paramMeetingEnterError == GCommNativeWrapper.MeetingEnterError.HANGOUT_ON_AIR)
        {
          HangoutPhoneTile.this.showHoaNotification(HangoutPhoneTile.this.mJoinButton);
        }
        else
        {
          if (paramMeetingEnterError != GCommNativeWrapper.MeetingEnterError.OUTDATED_CLIENT)
            break;
          HangoutPhoneTile.access$800(HangoutPhoneTile.this);
        }
      }
      boolean bool;
      int i;
      switch (HangoutPhoneTile.14.$SwitchMap$com$google$android$apps$plus$hangout$GCommNativeWrapper$MeetingEnterError[paramMeetingEnterError.ordinal()])
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
        Log.debug("HangoutPhoneTile.onMeetingEnterError: " + paramMeetingEnterError);
        HangoutPhoneTile.this.showError(i, bool);
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
      arrayOfObject[1] = HangoutPhoneTile.this;
      arrayOfObject[2] = Boolean.valueOf(paramBoolean);
      Log.debug("HangoutPhoneTile$EventHandler.onMeetingExited: this=%s, tile=%s clientInitiated=%s", arrayOfObject);
      super.onMeetingExited(paramBoolean);
      if ((HangoutPhoneTile.this.mRootView == null) || (HangoutPhoneTile.this.mState == null))
        return;
      if (paramBoolean)
        if (StringUtils.getDomain(HangoutPhoneTile.this.mAccount.getName()).equals("google.com"))
        {
          HangoutPhoneTile.this.getGCommNativeWrapper().uploadCallgrokLog();
          ProgressFragmentDialog.newInstance(HangoutPhoneTile.this.getResources().getString(R.string.hangout_log_upload_title), HangoutPhoneTile.this.getResources().getString(R.string.hangout_log_upload_message)).show(HangoutPhoneTile.this.getEsFragmentActivity().getSupportFragmentManager(), "log_upload");
        }
      while (true)
      {
        ExitHistory.recordNormalExit(HangoutPhoneTile.this.getContext(), HangoutPhoneTile.this.hangoutInfo, true);
        break;
        HangoutPhoneTile.this.getHangoutTileActivity().stopHangoutTile();
        continue;
        HangoutPhoneTile.this.showError(R.string.hangout_exited, true);
      }
    }

    public final void onMeetingMediaStarted()
    {
      super.onMeetingMediaStarted();
      if (HangoutPhoneTile.this.mRootView == null)
        return;
      boolean bool = HangoutPhoneTile.this.getContext().getSharedPreferences("com.google.android.apps.plus.hangout.HangoutTile", 0).getBoolean("filmStrip_", false);
      HangoutPhoneTile localHangoutPhoneTile = HangoutPhoneTile.this;
      if (bool);
      for (HangoutTile.State localState = HangoutTile.State.IN_MEETING_WITH_FILM_STRIP; ; localState = HangoutTile.State.IN_MEETING_WITH_SELF_VIDEO_INSET)
      {
        localHangoutPhoneTile.setState(localState);
        HangoutPhoneTile.this.updateOverlayMenuAndMessageViews();
        GCommApp.getInstance(HangoutPhoneTile.this.getContext()).getGCommService().showHangoutNotification(HangoutPhoneTile.this.getHangoutTileActivity().getHangoutNotificationIntent());
        HangoutPhoneTile.this.getHangoutTileActivity().onMeetingMediaStarted();
        break;
      }
    }

    public final void onMeetingMemberEntered(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberEntered(paramMeetingMember);
      HangoutPhoneTile.this.updateOverlayMenuAndMessageViews();
      notifyListeners();
    }

    public final void onMeetingMemberExited(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberExited(paramMeetingMember);
      HangoutPhoneTile.this.updateOverlayMenuAndMessageViews();
      notifyListeners();
    }

    public final void onMeetingMemberPresenceConnectionStatusChanged(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberPresenceConnectionStatusChanged(paramMeetingMember);
      HangoutPhoneTile.this.updateOverlayMenuAndMessageViews();
      notifyListeners();
    }

    public final void onMucEntered(MeetingMember paramMeetingMember)
    {
      super.onMucEntered(paramMeetingMember);
      HangoutPhoneTile.this.sendInvites();
    }

    public final void onRemoteMute(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2)
    {
      if (paramMeetingMember1.isSelf())
        HangoutPhoneTile.this.updateAudioMuteMenuButtonState(Boolean.valueOf(true));
    }

    public final void onSignedIn(String paramString)
    {
      boolean bool = true;
      super.onSignedIn(paramString);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this;
      arrayOfObject[bool] = HangoutPhoneTile.this;
      Log.debug("HangoutPhoneTile$EventHandler.onSignedIn: this=%s, tile=%s", arrayOfObject);
      assert (HangoutPhoneTile.this.mState.isSigningIn()) : HangoutPhoneTile.this.mState;
      if (HangoutPhoneTile.this.mRootView == null);
      while (true)
      {
        return;
        if (HangoutPhoneTile.this.skipGreenRoom)
        {
          HangoutPhoneTile.this.setState(HangoutTile.State.ENTERING_MEETING);
          if (HangoutPhoneTile.this.hangoutInfo == null)
          {
            Intent localIntent = ((Activity)HangoutPhoneTile.this.getContext()).getIntent();
            GCommApp.getInstance(HangoutPhoneTile.this.getContext()).createHangout(localIntent.getBooleanExtra("hangout_ring_invitees", false));
          }
          else
          {
            GCommApp localGCommApp = GCommApp.getInstance(HangoutPhoneTile.this.getContext());
            Hangout.Info localInfo = HangoutPhoneTile.this.hangoutInfo;
            if (HangoutPhoneTile.this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.MissedCall);
            while (true)
            {
              localGCommApp.enterHangout(localInfo, bool, HangoutPhoneTile.this.greenRoomParticipants, HangoutPhoneTile.this.mHoaConsented);
              break;
              bool = false;
            }
          }
        }
        else
        {
          HangoutPhoneTile.this.setState(HangoutTile.State.READY_TO_LAUNCH_MEETING);
        }
      }
    }

    public final void onSignedOut()
    {
      super.onSignedOut();
      Log.info("HangoutPhoneTile$EventHandler.onSignedOut");
      if (HangoutPhoneTile.this.mRootView == null);
      while (true)
      {
        return;
        HangoutPhoneTile.this.showError(R.string.hangout_signin_timeout_error, true);
        HangoutPhoneTile.this.setState(HangoutTile.State.SIGNIN_ERROR);
      }
    }

    public final void onSigninTimeOutError()
    {
      super.onSigninTimeOutError();
      Log.info("HangoutPhoneTile$EventHandler.onSigninTimeOutError: this=" + this);
      if (HangoutPhoneTile.this.mRootView == null);
      while (true)
      {
        return;
        HangoutPhoneTile.this.showError(R.string.hangout_signin_timeout_error, true);
        HangoutPhoneTile.this.setState(HangoutTile.State.SIGNIN_ERROR);
      }
    }

    public final void onVideoMuteChanged(boolean paramBoolean)
    {
      HangoutPhoneTile.this.updateVideoMuteMenuButtonState(Boolean.valueOf(paramBoolean));
    }
  }

  private final class OverlayMenuSlideOutAnimationListener
    implements Animation.AnimationListener
  {
    private OverlayMenuSlideOutAnimationListener()
    {
    }

    public final void onAnimationEnd(Animation paramAnimation)
    {
      HangoutPhoneTile.this.mSelfVideoView.setExtraBottomOffset(0);
    }

    public final void onAnimationRepeat(Animation paramAnimation)
    {
    }

    public final void onAnimationStart(Animation paramAnimation)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutPhoneTile
 * JD-Core Version:    0.6.2
 */