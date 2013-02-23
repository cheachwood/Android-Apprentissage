package com.google.android.apps.plus.hangout;

import com.google.android.apps.plus.service.Hangout.Info;

abstract class GCommEventHandler
{
  public void onAudioMuteStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
  {
  }

  public void onCallgrokLogUploadCompleted$4f708078()
  {
  }

  public void onCameraPreviewFrameDimensionsChanged(int paramInt1, int paramInt2)
  {
  }

  public void onCameraSwitchRequested()
  {
  }

  public void onCurrentSpeakerChanged(MeetingMember paramMeetingMember)
  {
  }

  public void onError(GCommNativeWrapper.Error paramError)
  {
  }

  public void onHangoutCreated(Hangout.Info paramInfo)
  {
    Log.debug(this + " onHangoutCreated:" + paramInfo);
  }

  public void onHangoutWaitTimeout(Hangout.Info paramInfo)
  {
    Log.debug(this + " onHangoutWaitTimeout:" + paramInfo);
  }

  public void onIncomingVideoFrameDimensionsChanged(int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onIncomingVideoFrameReceived(int paramInt)
  {
  }

  public void onIncomingVideoStarted(int paramInt)
  {
  }

  public void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
  {
  }

  public void onMeetingEnterError(GCommNativeWrapper.MeetingEnterError paramMeetingEnterError)
  {
  }

  public void onMeetingExited(boolean paramBoolean)
  {
  }

  public void onMeetingMediaStarted()
  {
  }

  public void onMeetingMemberEntered(MeetingMember paramMeetingMember)
  {
    Log.debug(this + " onMeetingMemberEntered Id:" + paramMeetingMember.getMucJid() + " currentStatus: " + paramMeetingMember.getCurrentStatus() + " prevStatus: " + paramMeetingMember.getPreviousStatus());
  }

  public void onMeetingMemberExited(MeetingMember paramMeetingMember)
  {
    Log.debug(this + " onMeetingMemberExited Id:" + paramMeetingMember.getMucJid() + " currentStatus: " + paramMeetingMember.getCurrentStatus() + " prevStatus: " + paramMeetingMember.getPreviousStatus());
  }

  public void onMeetingMemberPresenceConnectionStatusChanged(MeetingMember paramMeetingMember)
  {
    Log.debug(this + " onMeetingMemberPresenceConnectionStatusChanged Id:" + paramMeetingMember.getMucJid() + " currentStatus: " + paramMeetingMember.getCurrentStatus() + " prevStatus: " + paramMeetingMember.getPreviousStatus());
  }

  public void onMucEntered(MeetingMember paramMeetingMember)
  {
  }

  public void onOutgoingVideoStarted()
  {
  }

  public void onRemoteMute(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2)
  {
  }

  public void onSignedIn(String paramString)
  {
  }

  public void onSignedOut()
  {
  }

  public void onSigninTimeOutError()
  {
  }

  public void onVCardResponse(MeetingMember paramMeetingMember)
  {
    Log.debug(this + " onVCardResponse Id:" + paramMeetingMember.getMucJid() + " currentStatus: " + paramMeetingMember.getCurrentStatus() + " prevStatus: " + paramMeetingMember.getPreviousStatus());
  }

  public void onVideoMuteChanged(boolean paramBoolean)
  {
  }

  public void onVideoMuteToggleRequested()
  {
  }

  public void onVideoPauseStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
  {
  }

  public void onVideoSourceChanged(int paramInt, MeetingMember paramMeetingMember, boolean paramBoolean)
  {
  }

  public void onVolumeChanged(MeetingMember paramMeetingMember, int paramInt)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.GCommEventHandler
 * JD-Core Version:    0.6.2
 */