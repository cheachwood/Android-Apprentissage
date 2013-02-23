package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.views.Thermometer;

public abstract class HangoutVideoView extends RelativeLayout
{
  private final View mAudiodMutedStatusView;
  private final ImageView mAvatarView;
  private final ImageView mBackgoundLogo;
  private final View mBlockedView;
  private final View mCameraErrorView;
  private final Rect mDispSize = new Rect();
  private final Display mDisplay = ((WindowManager)getContext().getSystemService("window")).getDefaultDisplay();
  private final ImageButton mFlashToggleButton;
  private HangoutTile mHangoutTile;
  private LayoutMode mLayoutMode;
  private final View mPausedView;
  private final View mPinnedStatusView;
  private RelativeLayout mRootView;
  private final ImageView mSnapshotView;
  private View mVideoSurface;
  private final Thermometer mVolumeBar;

  public HangoutVideoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    LayoutInflater.from(paramContext).inflate(R.layout.hangout_video_view, this, true);
    this.mAvatarView = ((ImageView)findViewById(R.id.hangout_video_avatar));
    this.mBlockedView = findViewById(R.id.hangout_video_blocked);
    this.mPausedView = findViewById(R.id.hangout_video_paused);
    this.mCameraErrorView = findViewById(R.id.hangout_camera_error);
    this.mFlashToggleButton = ((ImageButton)findViewById(R.id.hangout_toggle_flash_light_button));
    this.mBackgoundLogo = ((ImageView)findViewById(R.id.hangout_background_logo));
    this.mSnapshotView = ((ImageView)findViewById(R.id.hangout_video_snapshot));
    this.mRootView = ((RelativeLayout)findViewById(R.id.hangout_video_view));
    this.mAudiodMutedStatusView = findViewById(R.id.hangout_audio_muted_status);
    this.mPinnedStatusView = findViewById(R.id.hangout_pinned_status);
    this.mVolumeBar = ((Thermometer)findViewById(R.id.hangout_volume));
    this.mLayoutMode = LayoutMode.FIT;
    setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
  }

  public final ImageView getAvatarView()
  {
    return this.mAvatarView;
  }

  public Bitmap getBitmap()
  {
    return null;
  }

  public MeetingMember getCurrentVideoSource()
  {
    return null;
  }

  public final ImageButton getFlashToggleButton()
  {
    return this.mFlashToggleButton;
  }

  public final ImageView getSnapshotView()
  {
    return this.mSnapshotView;
  }

  public final void hideAudioMutedStatus()
  {
    this.mAudiodMutedStatusView.setVisibility(8);
  }

  public final void hideAvatar()
  {
    this.mAvatarView.setVisibility(8);
  }

  public final void hideBlocked()
  {
    this.mBlockedView.setVisibility(8);
  }

  public final void hideLogo()
  {
    this.mBackgoundLogo.setVisibility(8);
  }

  public final void hidePaused()
  {
    this.mPausedView.setVisibility(8);
  }

  public final void hidePinnedStatus()
  {
    this.mPinnedStatusView.setVisibility(8);
  }

  public final void hideVideoSurface()
  {
    this.mVideoSurface.setVisibility(8);
  }

  public final void hideVolumeBar()
  {
    this.mVolumeBar.setVisibility(8);
  }

  public final boolean isAudioMuteStatusShowing()
  {
    if (this.mAudiodMutedStatusView.getVisibility() == 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isHangoutTileStarted()
  {
    if ((this.mHangoutTile != null) && (this.mHangoutTile.isTileStarted()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public boolean isVideoShowing()
  {
    return false;
  }

  protected final void layoutVideo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RectangleDimensions localRectangleDimensions;
    switch (1.$SwitchMap$com$google$android$apps$plus$hangout$HangoutVideoView$LayoutMode[this.mLayoutMode.ordinal()])
    {
    default:
      Log.error("Unknown layout mode: " + this.mLayoutMode);
      return;
    case 1:
      localRectangleDimensions = new RectangleDimensions(paramInt3, paramInt4);
    case 2:
    }
    while (true)
    {
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mVideoSurface.getLayoutParams();
      localLayoutParams.width = localRectangleDimensions.width;
      localLayoutParams.height = localRectangleDimensions.height;
      this.mVideoSurface.setLayoutParams(localLayoutParams);
      Object[] arrayOfObject = new Object[8];
      arrayOfObject[0] = this.mLayoutMode.toString();
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(paramInt2);
      arrayOfObject[3] = Integer.valueOf(paramInt3);
      arrayOfObject[4] = Integer.valueOf(paramInt4);
      arrayOfObject[5] = Integer.valueOf(localRectangleDimensions.width);
      arrayOfObject[6] = Integer.valueOf(localRectangleDimensions.height);
      arrayOfObject[7] = toString();
      Log.debug("HangoutVideo.layout: mode=%s  video=%d,%d  parent=%d,%d   new dimensions=%d,%d  self=%s", arrayOfObject);
      break;
      if (paramInt2 == 0)
        localRectangleDimensions = new RectangleDimensions(paramInt3, paramInt4);
      else
        localRectangleDimensions = Utils.fitContentInContainer(paramInt1 / paramInt2, paramInt3, paramInt4);
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getPaddingLeft() + getPaddingRight();
    int j = getPaddingTop() + getPaddingBottom();
    int k = View.MeasureSpec.getSize(paramInt1) - i;
    int m = View.MeasureSpec.getSize(paramInt2) - j;
    if ((k <= 0) || (m <= 0))
    {
      this.mDisplay.getRectSize(this.mDispSize);
      if (k <= 0)
        k = this.mDispSize.width() - i;
      if (m <= 0)
        m = this.mDispSize.height() - j;
    }
    onMeasure$3b4dfe4b(k, m);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onMeasure$3b4dfe4b(int paramInt1, int paramInt2)
  {
  }

  public void setBackgroundViewColor(int paramInt)
  {
    this.mRootView.setBackgroundColor(paramInt);
  }

  public final void setHangoutTile(HangoutTile paramHangoutTile)
  {
    this.mHangoutTile = paramHangoutTile;
  }

  public final void setLayoutMode(LayoutMode paramLayoutMode)
  {
    this.mLayoutMode = paramLayoutMode;
    requestLayout();
  }

  public final void setVideoSurface(View paramView)
  {
    if (this.mVideoSurface != null)
      this.mRootView.removeView(this.mVideoSurface);
    this.mVideoSurface = paramView;
    if (this.mRootView.getChildCount() > 0);
    for (int i = 1; ; i = 0)
    {
      this.mRootView.addView(this.mVideoSurface, i);
      invalidate();
      requestLayout();
      return;
    }
  }

  public void setVolume(int paramInt)
  {
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt > 9)
      paramInt = 9;
    this.mVolumeBar.setFillLevel(paramInt / 9.0D);
  }

  public final void showAudioMutedStatus()
  {
    this.mAudiodMutedStatusView.setVisibility(0);
  }

  public final void showAvatar()
  {
    if (!this.mSnapshotView.isShown())
      this.mAvatarView.setVisibility(0);
  }

  public final void showBlocked()
  {
    this.mBlockedView.setVisibility(0);
  }

  public final void showCameraError()
  {
    this.mCameraErrorView.setVisibility(0);
  }

  public final void showPaused()
  {
    this.mPausedView.setVisibility(0);
  }

  public final void showPinnedStatus()
  {
    this.mPinnedStatusView.setVisibility(0);
  }

  public final void showVideoSurface()
  {
    this.mVideoSurface.setVisibility(0);
  }

  public final void showVolumeBar()
  {
    this.mVolumeBar.setVisibility(0);
  }

  public static enum LayoutMode
  {
    static
    {
      LayoutMode[] arrayOfLayoutMode = new LayoutMode[2];
      arrayOfLayoutMode[0] = FILL;
      arrayOfLayoutMode[1] = FIT;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutVideoView
 * JD-Core Version:    0.6.2
 */