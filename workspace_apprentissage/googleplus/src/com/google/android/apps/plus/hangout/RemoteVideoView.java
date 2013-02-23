package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public abstract class RemoteVideoView extends HangoutVideoView
{
  private IncomingContentType currentContent = IncomingContentType.NONE;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  protected int incomingVideoContainerHeight;
  protected int incomingVideoContainerWidth;
  protected int incomingVideoFrameHeight = 20;
  protected int incomingVideoFrameWidth = 10;
  private boolean isRegistered;
  protected MeetingMember mCurrentVideoSource;
  protected VideoChangeListener mListener;
  private final VideoTextureView mVideoSurface;
  protected int requestID = 0;
  private boolean showingUnknownAvatar;

  public RemoteVideoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mVideoSurface = new VideoTextureView(paramContext, paramAttributeSet);
    setVideoSurface(this.mVideoSurface);
    setLayoutMode(HangoutVideoView.LayoutMode.FIT);
  }

  public final Bitmap getBitmap()
  {
    return this.mVideoSurface.getBitmap();
  }

  public final MeetingMember getCurrentVideoSource()
  {
    return this.mCurrentVideoSource;
  }

  public final boolean isVideoShowing()
  {
    if ((this.mVideoSurface != null) && (this.mVideoSurface.isDecoding()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onMeasure$3b4dfe4b(int paramInt1, int paramInt2)
  {
    layoutVideo(this.incomingVideoFrameWidth, this.incomingVideoFrameHeight, paramInt1, paramInt2);
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mVideoSurface.getLayoutParams();
    if ((this.incomingVideoContainerWidth != localLayoutParams.width) || (this.incomingVideoContainerHeight != localLayoutParams.height))
    {
      this.incomingVideoContainerWidth = localLayoutParams.width;
      this.incomingVideoContainerHeight = localLayoutParams.height;
      if (this.requestID != 0)
        GCommApp.getInstance(getContext()).getGCommNativeWrapper().setIncomingVideoParameters(this.requestID, this.incomingVideoContainerWidth, this.incomingVideoContainerHeight, GCommNativeWrapper.ScalingMode.AUTO_ZOOM, 15);
    }
  }

  public final void onPause()
  {
    GCommApp localGCommApp = GCommApp.getInstance(getContext());
    if (this.isRegistered)
    {
      localGCommApp.unregisterForEvents(getContext(), this.eventHandler, false);
      this.isRegistered = false;
    }
    this.mVideoSurface.onPause();
    if (this.requestID != 0)
    {
      localGCommApp.getGCommNativeWrapper().stopIncomingVideo(this.requestID);
      this.requestID = 0;
    }
  }

  public final void onResume()
  {
    if (!this.isRegistered)
    {
      GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
      this.isRegistered = true;
    }
    setIncomingContent(IncomingContentType.VIDEO);
    startVideo();
  }

  public void setAlpha(float paramFloat)
  {
    if (this.mVideoSurface != null)
      this.mVideoSurface.setAlpha(paramFloat);
    while (true)
    {
      return;
      super.setAlpha(paramFloat);
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

  protected final void setIncomingContent(IncomingContentType paramIncomingContentType)
  {
    if (paramIncomingContentType == this.currentContent)
      return;
    label20: ImageView localImageView;
    if (paramIncomingContentType == IncomingContentType.VIDEO)
    {
      showVideoSurface();
      localImageView = getSnapshotView();
      if ((paramIncomingContentType != IncomingContentType.AVATAR) && (paramIncomingContentType != IncomingContentType.BLOCKED) && (paramIncomingContentType != IncomingContentType.VIDEO_PAUSED))
        break label148;
      Bitmap localBitmap = this.mVideoSurface.getBitmap();
      if (localBitmap == null)
        break label139;
      localImageView.setImageBitmap(localBitmap);
      localImageView.setVisibility(0);
      label68: if ((paramIncomingContentType != IncomingContentType.AVATAR) && (paramIncomingContentType != IncomingContentType.BLOCKED) && (paramIncomingContentType != IncomingContentType.VIDEO_PAUSED))
        break label157;
      showAvatar();
      label93: if (paramIncomingContentType != IncomingContentType.BLOCKED)
        break label164;
      showBlocked();
      label104: if (paramIncomingContentType != IncomingContentType.VIDEO_PAUSED)
        break label171;
      showPaused();
    }
    while (true)
    {
      hideLogo();
      this.showingUnknownAvatar = false;
      this.currentContent = paramIncomingContentType;
      break;
      hideVideoSurface();
      break label20;
      label139: localImageView.setVisibility(8);
      break label68;
      label148: localImageView.setVisibility(8);
      break label68;
      label157: hideAvatar();
      break label93;
      label164: hideBlocked();
      break label104;
      label171: hidePaused();
    }
  }

  public void setVideoChangeListener(VideoChangeListener paramVideoChangeListener)
  {
    this.mListener = paramVideoChangeListener;
  }

  protected abstract void startVideo();

  public static class CenterStageVideoView extends RemoteVideoView
  {
    public CenterStageVideoView(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    protected final void startVideo()
    {
      MeetingMember localMeetingMember = GCommApp.getInstance(getContext()).getSelectedVideoSource();
      if (localMeetingMember == null)
        this.requestID = GCommApp.getInstance(getContext()).getGCommNativeWrapper().startIncomingVideoForSpeakerIndex(0, this.incomingVideoContainerWidth, this.incomingVideoContainerHeight, 15);
      while (true)
      {
        return;
        this.requestID = GCommApp.getInstance(getContext()).getGCommNativeWrapper().startIncomingVideoForUser(localMeetingMember.getMucJid(), this.incomingVideoContainerWidth, this.incomingVideoContainerHeight, 15);
        setIncomingContent(localMeetingMember);
      }
    }

    public final void updateVideoStreaming()
    {
      if (this.requestID == 0);
      while (true)
      {
        return;
        GCommApp localGCommApp = GCommApp.getInstance(getContext());
        GCommNativeWrapper localGCommNativeWrapper = localGCommApp.getGCommNativeWrapper();
        MeetingMember localMeetingMember = localGCommApp.getSelectedVideoSource();
        if ((this.mCurrentVideoSource != null) && (this.mCurrentVideoSource == localMeetingMember) && ((this.mCurrentVideoSource.isMediaBlocked()) || (this.mCurrentVideoSource.isVideoPaused())))
        {
          if (this.mCurrentVideoSource.isMediaBlocked());
          for (RemoteVideoView.IncomingContentType localIncomingContentType = RemoteVideoView.IncomingContentType.BLOCKED; ; localIncomingContentType = RemoteVideoView.IncomingContentType.VIDEO_PAUSED)
          {
            setIncomingContent(localIncomingContentType);
            break;
          }
        }
        if (localMeetingMember == null)
        {
          if (this.mListener != null)
            this.mListener.onVideoSourceAboutToChange$1385ff();
          setIncomingContent(RemoteVideoView.IncomingContentType.VIDEO);
          localGCommNativeWrapper.setIncomingVideoSourceToSpeakerIndex(this.requestID, 0);
        }
        else
        {
          setIncomingContent(RemoteVideoView.IncomingContentType.VIDEO);
          localGCommNativeWrapper.setIncomingVideoSourceToUser(this.requestID, localMeetingMember.getMucJid());
          if (this.mListener != null)
            this.mListener.onVideoSourceAboutToChange$1385ff();
          onPause();
          setIncomingContent(localMeetingMember);
          onResume();
        }
      }
    }
  }

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      if (paramError == GCommNativeWrapper.Error.AUDIO_VIDEO_SESSION)
        RemoteVideoView.this.setIncomingContent(RemoteVideoView.IncomingContentType.NONE);
    }

    public final void onIncomingVideoFrameDimensionsChanged(int paramInt1, int paramInt2, int paramInt3)
    {
      super.onIncomingVideoFrameDimensionsChanged(paramInt1, paramInt2, paramInt3);
      if (paramInt1 != RemoteVideoView.this.requestID);
      while (true)
      {
        return;
        if ((paramInt2 != RemoteVideoView.this.incomingVideoFrameWidth) || (paramInt3 != RemoteVideoView.this.incomingVideoFrameHeight))
        {
          RemoteVideoView.this.incomingVideoFrameWidth = paramInt2;
          RemoteVideoView.this.incomingVideoFrameHeight = paramInt3;
          RemoteVideoView.this.requestLayout();
        }
      }
    }

    public final void onIncomingVideoFrameReceived(int paramInt)
    {
      super.onIncomingVideoFrameReceived(paramInt);
      if (paramInt == RemoteVideoView.this.requestID)
        RemoteVideoView.this.mVideoSurface.requestRender();
    }

    public final void onIncomingVideoStarted(int paramInt)
    {
      super.onIncomingVideoStarted(paramInt);
      if ((paramInt == RemoteVideoView.this.requestID) && (!GCommApp.getInstance(RemoteVideoView.this.getContext()).isExitingHangout()) && (RemoteVideoView.this.isHangoutTileStarted()))
      {
        RemoteVideoView.this.mVideoSurface.setRequestID(paramInt);
        RemoteVideoView.this.mVideoSurface.onResume();
      }
    }

    public final void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
    {
      super.onMediaBlock(paramMeetingMember1, paramMeetingMember2, paramBoolean);
      if ((paramMeetingMember1 != null) && (paramMeetingMember1 == RemoteVideoView.this.mCurrentVideoSource))
        RemoteVideoView.this.setIncomingContent(RemoteVideoView.this.mCurrentVideoSource);
    }

    public final void onVCardResponse(MeetingMember paramMeetingMember)
    {
      super.onVCardResponse(paramMeetingMember);
      if ((RemoteVideoView.this.mCurrentVideoSource == paramMeetingMember) && (RemoteVideoView.this.currentContent == RemoteVideoView.IncomingContentType.AVATAR) && (RemoteVideoView.this.showingUnknownAvatar) && (paramMeetingMember.getVCard() != null) && (paramMeetingMember.getVCard().getAvatarData() != null))
      {
        Avatars.renderAvatar(RemoteVideoView.this.getContext(), paramMeetingMember, RemoteVideoView.this.getAvatarView());
        RemoteVideoView.access$102(RemoteVideoView.this, false);
      }
    }

    public final void onVideoPauseStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoPauseStateChanged(paramMeetingMember, paramBoolean);
      if (paramMeetingMember == RemoteVideoView.this.mCurrentVideoSource)
        RemoteVideoView.this.setIncomingContent(RemoteVideoView.this.mCurrentVideoSource);
    }

    public final void onVideoSourceChanged(int paramInt, MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoSourceChanged(paramInt, paramMeetingMember, paramBoolean);
      if (paramInt != RemoteVideoView.this.requestID)
        return;
      if ((paramMeetingMember == null) || (paramMeetingMember.getCurrentStatus() != MeetingMember.Status.CONNECTED))
        RemoteVideoView.this.setIncomingContent(RemoteVideoView.IncomingContentType.NONE);
      while (true)
      {
        if (RemoteVideoView.this.mCurrentVideoSource != paramMeetingMember)
        {
          if (RemoteVideoView.this.mListener != null)
            RemoteVideoView.this.mListener.onVideoSourceAboutToChange$1385ff();
          RemoteVideoView.this.mCurrentVideoSource = paramMeetingMember;
          RemoteVideoView.this.onPause();
          RemoteVideoView.this.onResume();
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = RemoteVideoView.this.currentContent.toString();
        Log.info("Now showing %s on video activity", arrayOfObject);
        break;
        if ((paramMeetingMember.isVideoPaused()) || (paramMeetingMember.isMediaBlocked()))
        {
          RemoteVideoView.this.setIncomingContent(paramMeetingMember);
        }
        else if (paramBoolean)
        {
          RemoteVideoView.this.setIncomingContent(RemoteVideoView.IncomingContentType.VIDEO);
        }
        else
        {
          Avatars.renderAvatar(RemoteVideoView.this.getContext(), paramMeetingMember, RemoteVideoView.this.getAvatarView());
          RemoteVideoView.this.setIncomingContent(RemoteVideoView.IncomingContentType.AVATAR);
          if ((paramMeetingMember.getVCard() == null) || (paramMeetingMember.getVCard().getAvatarData() == null))
            RemoteVideoView.access$102(RemoteVideoView.this, true);
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

  public static class ParticipantVideoView extends RemoteVideoView
  {
    private final MeetingMember member;

    static
    {
      if (!RemoteVideoView.class.desiredAssertionStatus());
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
    }

    public final MeetingMember getMember()
    {
      return this.member;
    }

    protected final void startVideo()
    {
      assert ((this.mCurrentVideoSource == null) || (this.member == this.mCurrentVideoSource));
      this.requestID = GCommApp.getInstance(getContext()).getGCommNativeWrapper().startIncomingVideoForUser(this.member.getMucJid(), this.incomingVideoContainerWidth, this.incomingVideoContainerHeight, 15);
      setIncomingContent(this.member);
    }
  }

  public static abstract interface VideoChangeListener
  {
    public abstract void onVideoSourceAboutToChange$1385ff();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.RemoteVideoView
 * JD-Core Version:    0.6.2
 */