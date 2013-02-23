package com.google.android.apps.plus.hangout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.fragments.BlockPersonDialog;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.QuickActions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TabletFilmStripView extends LinearLayout
{
  private Dialog mContextMenuDialog;
  private int mCurrentOrientation = 0;
  private CountDownTimer mDismissMenuTimer;
  private final EventHandler mEventHandler = new EventHandler((byte)0);
  private final int mFilmStripMargin;
  private GCommApp mGCommAppInstance;
  private HangoutTile mHangoutTile;
  private HashMap<HangoutVideoView, MeetingMember> mMeetingMembersByVideoView = new HashMap();
  private MeetingMember mPinnedVideoMember;
  private boolean mShouldShowStatusIcons;
  private Boolean mShouldShowStatusIconsOverride;
  private HashMap<MeetingMember, CountDownTimer> mTimersByMeetingMember = new HashMap();
  private HashMap<MeetingMember, RemoteVideoView.ParticipantVideoView> mVideoViewsByMeetingMember = new HashMap();
  private boolean msResumed;

  public TabletFilmStripView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mFilmStripMargin = paramContext.getResources().getDimensionPixelSize(R.dimen.hangout_filmstrip_margin);
    this.mShouldShowStatusIcons = Property.ENABLE_HANGOUT_FILMSTRIP_STATUS.getBoolean();
    this.mDismissMenuTimer = new CountDownTimer(5000L, 5000L)
    {
      public final void onFinish()
      {
        TabletFilmStripView.this.dismissParticipantMenuDialog();
      }

      public final void onTick(long paramAnonymousLong)
      {
      }
    };
  }

  private void addParticipantVideo(MeetingMember paramMeetingMember)
  {
    if (paramMeetingMember.isSelf())
      return;
    if ((this.mPinnedVideoMember != null) && (paramMeetingMember == this.mPinnedVideoMember));
    for (boolean bool = true; ; bool = false)
    {
      RemoteVideoView.ParticipantVideoView localParticipantVideoView1 = (RemoteVideoView.ParticipantVideoView)this.mVideoViewsByMeetingMember.get(paramMeetingMember);
      if (localParticipantVideoView1 != null)
        removeParticipantVideo(localParticipantVideoView1, bool);
      RemoteVideoView.ParticipantVideoView localParticipantVideoView2 = new RemoteVideoView.ParticipantVideoView(getContext(), null, paramMeetingMember);
      if (getChildCount() > 0)
      {
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
        localLayoutParams.leftMargin = this.mFilmStripMargin;
        localParticipantVideoView2.setLayoutParams(localLayoutParams);
      }
      localParticipantVideoView2.setOnTouchListener(new TouchListener(localParticipantVideoView2));
      localParticipantVideoView2.setTag(paramMeetingMember);
      addView(localParticipantVideoView2);
      localParticipantVideoView2.setHangoutTile(this.mHangoutTile);
      localParticipantVideoView2.onResume();
      CountDownTimer local2 = new CountDownTimer(2000L, 2000L)
      {
        public final void onFinish()
        {
          this.val$participantVideoView.hideVolumeBar();
        }

        public final void onTick(long paramAnonymousLong)
        {
        }
      };
      this.mTimersByMeetingMember.put(paramMeetingMember, local2);
      if (bool)
        pinVideo(paramMeetingMember);
      this.mVideoViewsByMeetingMember.put(paramMeetingMember, localParticipantVideoView2);
      this.mMeetingMembersByVideoView.put(localParticipantVideoView2, paramMeetingMember);
      updateStatusOverlay(paramMeetingMember);
      break;
    }
  }

  private GCommApp getGCommAppInstance()
  {
    if (this.mGCommAppInstance != null);
    for (GCommApp localGCommApp = this.mGCommAppInstance; ; localGCommApp = GCommApp.getInstance(getContext()))
      return localGCommApp;
  }

  private void onVideoClicked(HangoutVideoView paramHangoutVideoView, MeetingMember paramMeetingMember)
  {
    ParticipantContextMenuHelper localParticipantContextMenuHelper = new ParticipantContextMenuHelper(paramMeetingMember);
    this.mContextMenuDialog = QuickActions.show(paramHangoutVideoView, null, null, localParticipantContextMenuHelper, localParticipantContextMenuHelper, true, true);
    this.mDismissMenuTimer.start();
  }

  private void pinVideo(MeetingMember paramMeetingMember)
  {
    MeetingMember localMeetingMember = this.mPinnedVideoMember;
    getGCommAppInstance().setSelectedVideoSource(paramMeetingMember);
    this.mHangoutTile.updateMainVideoStreaming();
    this.mPinnedVideoMember = paramMeetingMember;
    updateStatusOverlay(localMeetingMember);
    updateStatusOverlay(this.mPinnedVideoMember);
  }

  private void removeParticipantVideo(RemoteVideoView.ParticipantVideoView paramParticipantVideoView, boolean paramBoolean)
  {
    paramParticipantVideoView.onPause();
    MeetingMember localMeetingMember = (MeetingMember)this.mMeetingMembersByVideoView.remove(paramParticipantVideoView);
    if (localMeetingMember != null)
    {
      this.mVideoViewsByMeetingMember.remove(localMeetingMember);
      this.mTimersByMeetingMember.remove(localMeetingMember);
      if ((paramBoolean) && (localMeetingMember == this.mPinnedVideoMember))
        unpinVideo();
    }
    removeView(paramParticipantVideoView);
  }

  private void unpinVideo()
  {
    getGCommAppInstance().setSelectedVideoSource(null);
    this.mHangoutTile.updateMainVideoStreaming();
    MeetingMember localMeetingMember = this.mPinnedVideoMember;
    this.mPinnedVideoMember = null;
    updateStatusOverlay(localMeetingMember);
  }

  private void updateStatusOverlay(MeetingMember paramMeetingMember)
  {
    if ((paramMeetingMember == null) || (paramMeetingMember.isSelf()));
    while (true)
    {
      return;
      HangoutVideoView localHangoutVideoView = (HangoutVideoView)this.mVideoViewsByMeetingMember.get(paramMeetingMember);
      if (localHangoutVideoView != null)
        if (paramMeetingMember == this.mPinnedVideoMember)
          localHangoutVideoView.showPinnedStatus();
        else
          localHangoutVideoView.hidePinnedStatus();
    }
  }

  public final void dismissParticipantMenuDialog()
  {
    this.mDismissMenuTimer.cancel();
    if (this.mContextMenuDialog != null)
    {
      this.mContextMenuDialog.dismiss();
      this.mContextMenuDialog = null;
    }
  }

  public final boolean isAudioMuted(MeetingMember paramMeetingMember)
  {
    HangoutVideoView localHangoutVideoView = (HangoutVideoView)this.mVideoViewsByMeetingMember.get(paramMeetingMember);
    if ((localHangoutVideoView != null) && (localHangoutVideoView.isAudioMuteStatusShowing()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (paramConfiguration.orientation != this.mCurrentOrientation)
    {
      this.mCurrentOrientation = paramConfiguration.orientation;
      dismissParticipantMenuDialog();
    }
  }

  public final void onPause()
  {
    if (!this.msResumed);
    while (true)
    {
      return;
      this.msResumed = false;
      getGCommAppInstance().unregisterForEvents(getContext(), this.mEventHandler, false);
      for (int i = -1 + getChildCount(); i >= 0; i--)
      {
        View localView = getChildAt(i);
        if ((localView instanceof RemoteVideoView.ParticipantVideoView))
          removeParticipantVideo((RemoteVideoView.ParticipantVideoView)localView, false);
      }
      removeAllViews();
      dismissParticipantMenuDialog();
    }
  }

  public final void onResume()
  {
    if (this.msResumed);
    while (true)
    {
      return;
      if (this.mHangoutTile != null)
      {
        this.msResumed = true;
        this.mCurrentOrientation = 0;
        Iterator localIterator = getGCommAppInstance().getGCommNativeWrapper().getMeetingMembersOrderedByEntry().iterator();
        while (localIterator.hasNext())
          addParticipantVideo((MeetingMember)localIterator.next());
        getGCommAppInstance().registerForEvents(getContext(), this.mEventHandler, false);
      }
    }
  }

  public final void requestPinVideo(RemoteVideoView.ParticipantVideoView paramParticipantVideoView)
  {
    if (this.msResumed)
    {
      MeetingMember localMeetingMember = paramParticipantVideoView.getMember();
      if ((localMeetingMember != null) && (!localMeetingMember.isSelf()))
        pinVideo(localMeetingMember);
    }
  }

  public void setGCommAppInstanceOverride(GCommApp paramGCommApp)
  {
    this.mGCommAppInstance = paramGCommApp;
  }

  public void setHangoutTile(HangoutTile paramHangoutTile)
  {
    this.mHangoutTile = paramHangoutTile;
  }

  public void setShouldShowStatusIconsOverride(boolean paramBoolean)
  {
    this.mShouldShowStatusIconsOverride = Boolean.valueOf(paramBoolean);
  }

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onAudioMuteStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onAudioMuteStateChanged(paramMeetingMember, paramBoolean);
      TabletFilmStripView.access$500(TabletFilmStripView.this, paramMeetingMember, paramBoolean);
    }

    public final void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
    {
      super.onMediaBlock(paramMeetingMember1, paramMeetingMember2, paramBoolean);
      TabletFilmStripView.this.updateStatusOverlay(paramMeetingMember1);
      TabletFilmStripView.this.updateStatusOverlay(paramMeetingMember2);
      TabletFilmStripView.this.mHangoutTile.updateMainVideoStreaming();
    }

    public final void onMeetingMemberEntered(MeetingMember paramMeetingMember)
    {
      if (TabletFilmStripView.this.msResumed)
        TabletFilmStripView.this.addParticipantVideo(paramMeetingMember);
    }

    public final void onMeetingMemberExited(MeetingMember paramMeetingMember)
    {
      if (TabletFilmStripView.this.msResumed)
        for (int i = -1 + TabletFilmStripView.this.getChildCount(); i >= 0; i--)
        {
          View localView = TabletFilmStripView.this.getChildAt(i);
          if ((localView != null) && ((localView instanceof RemoteVideoView.ParticipantVideoView)) && (((RemoteVideoView.ParticipantVideoView)localView).getMember() == paramMeetingMember))
            TabletFilmStripView.this.removeParticipantVideo((RemoteVideoView.ParticipantVideoView)localView, true);
        }
    }

    public final void onRemoteMute(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2)
    {
      super.onRemoteMute(paramMeetingMember1, paramMeetingMember2);
      TabletFilmStripView.access$500(TabletFilmStripView.this, paramMeetingMember1, true);
    }

    public final void onVideoPauseStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoPauseStateChanged(paramMeetingMember, paramBoolean);
      TabletFilmStripView.this.updateStatusOverlay(paramMeetingMember);
    }

    public final void onVolumeChanged(MeetingMember paramMeetingMember, int paramInt)
    {
      super.onVolumeChanged(paramMeetingMember, paramInt);
      TabletFilmStripView.access$600(TabletFilmStripView.this, paramMeetingMember, paramInt);
    }
  }

  private final class ParticipantContextMenuHelper
    implements MenuItem.OnMenuItemClickListener, View.OnCreateContextMenuListener
  {
    private final MeetingMember mMeetingMember;

    ParticipantContextMenuHelper(MeetingMember arg2)
    {
      Object localObject;
      this.mMeetingMember = localObject;
    }

    public final void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
    {
      new MenuInflater(TabletFilmStripView.this.getContext()).inflate(R.menu.hangout_participant_menu, paramContextMenu);
      MenuItem localMenuItem1 = paramContextMenu.findItem(R.id.menu_hangout_participant_profile);
      MenuItem localMenuItem2 = paramContextMenu.findItem(R.id.menu_hangout_participant_pin_video);
      MenuItem localMenuItem3 = paramContextMenu.findItem(R.id.menu_hangout_participant_unpin_video);
      MenuItem localMenuItem4 = paramContextMenu.findItem(R.id.menu_hangout_participant_remote_mute);
      MenuItem localMenuItem5 = paramContextMenu.findItem(R.id.menu_hangout_participant_remote_mute_disabled);
      MenuItem localMenuItem6 = paramContextMenu.findItem(R.id.menu_hangout_participant_block);
      MenuItem localMenuItem7 = paramContextMenu.findItem(R.id.menu_hangout_participant_block_disabled);
      localMenuItem1.setTitle(this.mMeetingMember.getName(TabletFilmStripView.this.getContext()));
      if (this.mMeetingMember != TabletFilmStripView.this.mPinnedVideoMember)
      {
        localMenuItem2.setVisible(true);
        if ((this.mMeetingMember.isMediaBlocked()) || (!(paramView instanceof HangoutVideoView)) || (((HangoutVideoView)paramView).isAudioMuteStatusShowing()))
          break label221;
        localMenuItem4.setVisible(true);
        label179: if ((!this.mMeetingMember.isSelfProfile()) && (!this.mMeetingMember.isMediaBlocked()))
          break label233;
        localMenuItem7.setVisible(true);
      }
      while (true)
      {
        return;
        localMenuItem3.setVisible(true);
        break;
        label221: localMenuItem5.setVisible(true);
        break label179;
        label233: localMenuItem6.setVisible(true);
      }
    }

    public final boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      int i = paramMenuItem.getItemId();
      if (i == R.id.menu_hangout_participant_profile)
      {
        Intent localIntent = Intents.getProfileActivityIntent(TabletFilmStripView.this.getContext(), TabletFilmStripView.this.mHangoutTile.getAccount(), this.mMeetingMember.getId(), null);
        TabletFilmStripView.this.getContext().startActivity(localIntent);
      }
      while (true)
      {
        TabletFilmStripView.this.mDismissMenuTimer.cancel();
        TabletFilmStripView.access$1502(TabletFilmStripView.this, null);
        boolean bool = true;
        int j;
        do
        {
          return bool;
          if (i == R.id.menu_hangout_participant_pin_video)
          {
            TabletFilmStripView.this.pinVideo(this.mMeetingMember);
            break;
          }
          if (i == R.id.menu_hangout_participant_unpin_video)
          {
            TabletFilmStripView.this.unpinVideo();
            break;
          }
          if (i == R.id.menu_hangout_participant_remote_mute)
          {
            TabletFilmStripView.this.getGCommAppInstance().getGCommNativeWrapper().remoteMute(this.mMeetingMember);
            break;
          }
          j = R.id.menu_hangout_participant_block;
          bool = false;
        }
        while (i != j);
        new BlockPersonDialog(false, this.mMeetingMember).show(((EsFragmentActivity)TabletFilmStripView.this.getContext()).getSupportFragmentManager(), null);
      }
    }
  }

  private final class TouchListener extends GestureDetector.SimpleOnGestureListener
    implements View.OnTouchListener
  {
    private final GestureDetector mGestureDetector = new GestureDetector(TabletFilmStripView.this.getContext(), this);
    private final HangoutVideoView mVideoView;

    TouchListener(HangoutVideoView arg2)
    {
      Object localObject;
      this.mVideoView = localObject;
      this.mGestureDetector.setOnDoubleTapListener(this);
    }

    public final boolean onDoubleTap(MotionEvent paramMotionEvent)
    {
      MeetingMember localMeetingMember = (MeetingMember)this.mVideoView.getTag();
      TabletFilmStripView.access$800(TabletFilmStripView.this, this.mVideoView, localMeetingMember);
      return true;
    }

    public final boolean onDown(MotionEvent paramMotionEvent)
    {
      return true;
    }

    public final boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      if ((TabletFilmStripView.this.mHangoutTile != null) && (paramFloat2 > 0.0F) && (paramFloat2 > Math.abs(paramFloat1)))
      {
        ViewParent localViewParent = this.mVideoView.getParent();
        if ((localViewParent instanceof TabletFilmStripView))
          TabletFilmStripView.this.mHangoutTile.hideChild((TabletFilmStripView)localViewParent);
      }
      for (boolean bool = true; ; bool = super.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2))
        return bool;
    }

    public final boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      if (TabletFilmStripView.this.getY() > TabletFilmStripView.this.getHeight() / 2)
      {
        if (paramFloat2 < 0.0F);
        for (i = 1; ; i = 0)
        {
          if (i != 0)
          {
            View.DragShadowBuilder local1 = new View.DragShadowBuilder(this.mVideoView)
            {
              public final void onDrawShadow(Canvas paramAnonymousCanvas)
              {
                Bitmap localBitmap = TabletFilmStripView.TouchListener.this.mVideoView.getBitmap();
                if (localBitmap != null)
                  paramAnonymousCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, null);
              }
            };
            this.mVideoView.startDrag(null, local1, this.mVideoView, 0);
          }
          return true;
        }
      }
      if (paramFloat2 > 0.0F);
      for (int i = 1; ; i = 0)
        break;
    }

    public final boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      if ((TabletFilmStripView.this.getParent() != null) && (TabletFilmStripView.this.getVisibility() == 0) && (this.mVideoView.getParent() != null))
      {
        MeetingMember localMeetingMember = (MeetingMember)this.mVideoView.getTag();
        TabletFilmStripView.this.onVideoClicked(this.mVideoView, localMeetingMember);
      }
      return true;
    }

    public final boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      return this.mGestureDetector.onTouchEvent(paramMotionEvent);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.TabletFilmStripView
 * JD-Core Version:    0.6.2
 */