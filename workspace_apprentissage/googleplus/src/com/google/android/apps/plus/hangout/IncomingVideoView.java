package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.views.RelativeLayoutWithLayoutNotifications;

public abstract class IncomingVideoView extends RelativeLayoutWithLayoutNotifications
{
  private final ImageView avatarView;
  private final View blockedView;
  private IncomingContentType currentContent = IncomingContentType.NONE;
  protected MeetingMember currentVideoSource;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  protected int incomingVideoHeight;
  protected int incomingVideoWidth;
  private HangoutTile mHangoutTile;
  protected int requestID = 0;
  private boolean showingUnknownAvatar;
  private final View videoPausedView;
  protected final VideoView videoView;

  public IncomingVideoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    LayoutInflater.from(paramContext).inflate(R.layout.hangout_incoming_video_view, this, true);
    this.videoView = ((VideoView)findViewById(R.id.video_view));
    this.avatarView = ((ImageView)findViewById(R.id.video_avatar));
    this.blockedView = findViewById(R.id.blocked);
    this.videoPausedView = findViewById(R.id.video_paused);
  }

  final int getRequestId()
  {
    return this.requestID;
  }

  public final void onMeasure$3b4dfe4b(int paramInt1, int paramInt2)
  {
    this.incomingVideoWidth = paramInt1;
    this.incomingVideoHeight = paramInt2;
    if (this.requestID != 0)
      GCommApp.getInstance(getContext()).getGCommNativeWrapper().setIncomingVideoParameters(this.requestID, this.incomingVideoWidth, this.incomingVideoHeight, GCommNativeWrapper.ScalingMode.AUTO_ZOOM, 15);
  }

  public final void onPause()
  {
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.eventHandler, false);
    this.videoView.onPause();
    if (this.requestID != 0)
    {
      GCommApp.getInstance(getContext()).getGCommNativeWrapper().stopIncomingVideo(this.requestID);
      this.requestID = 0;
    }
  }

  public final void onResume()
  {
    GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
    setIncomingContent(IncomingContentType.VIDEO);
    startVideo();
  }

  public void setHangoutTile(HangoutTile paramHangoutTile)
  {
    this.mHangoutTile = paramHangoutTile;
  }

  protected final void setIncomingContent(IncomingContentType paramIncomingContentType)
  {
    this.videoView.setVisibility(8);
    this.avatarView.setVisibility(8);
    this.blockedView.setVisibility(8);
    this.videoPausedView.setVisibility(8);
    this.showingUnknownAvatar = false;
    switch (1.$SwitchMap$com$google$android$apps$plus$hangout$IncomingVideoView$IncomingContentType[paramIncomingContentType.ordinal()])
    {
    case 1:
    default:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      this.currentContent = paramIncomingContentType;
      return;
      this.videoView.setVisibility(0);
      continue;
      this.avatarView.setVisibility(0);
      continue;
      this.blockedView.setVisibility(0);
      continue;
      this.videoPausedView.setVisibility(0);
    }
  }

  protected final void setIncomingContent(MeetingMember paramMeetingMember)
  {
    if (paramMeetingMember.isMediaBlocked())
      setIncomingContent(IncomingContentType.BLOCKED);
    while (true)
    {
      return;
      if (paramMeetingMember.isVideoPaused())
        setIncomingContent(IncomingContentType.VIDEO_PAUSED);
      else
        setIncomingContent(IncomingContentType.VIDEO);
    }
  }

  protected abstract void startVideo();

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      if (paramError == GCommNativeWrapper.Error.AUDIO_VIDEO_SESSION)
        IncomingVideoView.this.setIncomingContent(IncomingVideoView.IncomingContentType.NONE);
    }

    public final void onIncomingVideoFrameReceived(int paramInt)
    {
      super.onIncomingVideoFrameReceived(paramInt);
      if (paramInt == IncomingVideoView.this.requestID)
        IncomingVideoView.this.videoView.requestRender();
    }

    public final void onIncomingVideoStarted(int paramInt)
    {
      super.onIncomingVideoStarted(paramInt);
      if ((paramInt == IncomingVideoView.this.requestID) && (!GCommApp.getInstance(IncomingVideoView.this.getContext()).isExitingHangout()) && (IncomingVideoView.this.mHangoutTile.isTileStarted()))
      {
        IncomingVideoView.this.videoView.setRequestID(paramInt);
        IncomingVideoView.this.videoView.onResume();
      }
    }

    public final void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
    {
      super.onMediaBlock(paramMeetingMember1, paramMeetingMember2, paramBoolean);
      if ((paramMeetingMember1 != null) && (paramMeetingMember1 == IncomingVideoView.this.currentVideoSource))
        IncomingVideoView.this.setIncomingContent(IncomingVideoView.this.currentVideoSource);
    }

    public final void onVCardResponse(MeetingMember paramMeetingMember)
    {
      super.onVCardResponse(paramMeetingMember);
      if ((IncomingVideoView.this.currentVideoSource == paramMeetingMember) && (IncomingVideoView.this.currentContent == IncomingVideoView.IncomingContentType.AVATAR) && (IncomingVideoView.this.showingUnknownAvatar) && (paramMeetingMember.getVCard() != null) && (paramMeetingMember.getVCard().getAvatarData() != null))
      {
        Avatars.renderAvatar(IncomingVideoView.this.getContext(), paramMeetingMember, IncomingVideoView.this.avatarView);
        IncomingVideoView.access$202(IncomingVideoView.this, false);
      }
    }

    public final void onVideoPauseStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoPauseStateChanged(paramMeetingMember, paramBoolean);
      if (paramMeetingMember == IncomingVideoView.this.currentVideoSource)
        IncomingVideoView.this.setIncomingContent(IncomingVideoView.this.currentVideoSource);
    }

    public final void onVideoSourceChanged(int paramInt, MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoSourceChanged(paramInt, paramMeetingMember, paramBoolean);
      if (paramInt != IncomingVideoView.this.requestID)
        return;
      if ((paramMeetingMember == null) || (paramMeetingMember.getCurrentStatus() != MeetingMember.Status.CONNECTED))
        IncomingVideoView.this.setIncomingContent(IncomingVideoView.IncomingContentType.NONE);
      while (true)
      {
        IncomingVideoView.this.currentVideoSource = paramMeetingMember;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = IncomingVideoView.this.currentContent.toString();
        Log.info("Now showing %s on video activity", arrayOfObject);
        break;
        if ((paramMeetingMember.isVideoPaused()) || (paramMeetingMember.isMediaBlocked()))
        {
          IncomingVideoView.this.setIncomingContent(paramMeetingMember);
        }
        else if (paramBoolean)
        {
          IncomingVideoView.this.setIncomingContent(IncomingVideoView.IncomingContentType.VIDEO);
        }
        else
        {
          Avatars.renderAvatar(IncomingVideoView.this.getContext(), paramMeetingMember, IncomingVideoView.this.avatarView);
          IncomingVideoView.this.setIncomingContent(IncomingVideoView.IncomingContentType.AVATAR);
          if ((paramMeetingMember.getVCard() == null) || (paramMeetingMember.getVCard().getAvatarData() == null))
            IncomingVideoView.access$202(IncomingVideoView.this, true);
        }
      }
    }
  }

  private static enum IncomingContentType
  {
    static
    {
      AVATAR = new IncomingContentType("AVATAR", 2);
      BLOCKED = new IncomingContentType("BLOCKED", 3);
      VIDEO_PAUSED = new IncomingContentType("VIDEO_PAUSED", 4);
      IncomingContentType[] arrayOfIncomingContentType = new IncomingContentType[5];
      arrayOfIncomingContentType[0] = NONE;
      arrayOfIncomingContentType[1] = VIDEO;
      arrayOfIncomingContentType[2] = AVATAR;
      arrayOfIncomingContentType[3] = BLOCKED;
      arrayOfIncomingContentType[4] = VIDEO_PAUSED;
    }
  }

  public static class MainVideoView extends IncomingVideoView
  {
    public MainVideoView(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    protected final void startVideo()
    {
      MeetingMember localMeetingMember = GCommApp.getInstance(getContext()).getSelectedVideoSource();
      if (localMeetingMember == null)
        this.requestID = GCommApp.getInstance(getContext()).getGCommNativeWrapper().startIncomingVideoForSpeakerIndex(0, this.incomingVideoWidth, this.incomingVideoHeight, 15);
      while (true)
      {
        return;
        this.requestID = GCommApp.getInstance(getContext()).getGCommNativeWrapper().startIncomingVideoForUser(localMeetingMember.getMucJid(), this.incomingVideoWidth, this.incomingVideoHeight, 15);
        setIncomingContent(localMeetingMember);
      }
    }

    public final void updateVideoStreaming()
    {
      if (this.requestID == 0);
      while (true)
      {
        return;
        MeetingMember localMeetingMember = GCommApp.getInstance(getContext()).getSelectedVideoSource();
        if (localMeetingMember == null)
        {
          GCommApp.getInstance(getContext()).getGCommNativeWrapper().setIncomingVideoSourceToSpeakerIndex(this.requestID, 0);
        }
        else
        {
          GCommApp.getInstance(getContext()).getGCommNativeWrapper().setIncomingVideoSourceToUser(this.requestID, localMeetingMember.getMucJid());
          setIncomingContent(localMeetingMember);
        }
      }
    }
  }

  public static class ParticipantVideoView extends IncomingVideoView
  {
    private final MeetingMember member;

    static
    {
      if (!IncomingVideoView.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    public ParticipantVideoView(Context paramContext, AttributeSet paramAttributeSet, MeetingMember paramMeetingMember)
    {
      super(null);
      this.member = paramMeetingMember;
      this.videoView.setZOrderMediaOverlay(true);
    }

    public final MeetingMember getMember()
    {
      return this.member;
    }

    protected final void startVideo()
    {
      assert ((this.currentVideoSource == null) || (this.member == this.currentVideoSource));
      this.requestID = GCommApp.getInstance(getContext()).getGCommNativeWrapper().startIncomingVideoForUser(this.member.getMucJid(), this.incomingVideoWidth, this.incomingVideoHeight, 15);
      setIncomingContent(this.member);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.IncomingVideoView
 * JD-Core Version:    0.6.2
 */