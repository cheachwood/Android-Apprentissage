package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.apps.plus.R.drawable;

public class LocalVideoView extends HangoutVideoView
  implements VideoCapturer.Host
{
  private boolean audioVideoFailed;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  private boolean isRegistered;
  private int numPendingStartOutgoingVideoRequests;
  private Cameras.CameraType selectedCameraType;
  private int selfFrameHeight = 240;
  private int selfFrameWidth = 320;
  private final TextureView textureView;
  private final ImageButton toggleFlashButton;
  private final VideoCapturer videoCapturer;

  public LocalVideoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.textureView = new TextureView(paramContext, paramAttributeSet);
    setVideoSurface(this.textureView);
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.textureView.getLayoutParams();
    localLayoutParams.width = -2;
    localLayoutParams.height = -2;
    this.textureView.setLayoutParams(localLayoutParams);
    setLayoutMode(HangoutVideoView.LayoutMode.FIT);
    this.videoCapturer = new VideoCapturer.TextureViewVideoCapturer(paramContext, GCommApp.getInstance(paramContext).getGCommNativeWrapper(), this.textureView, this);
    this.toggleFlashButton = getFlashToggleButton();
    this.toggleFlashButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        LocalVideoView.this.videoCapturer.toggleFlashLightEnabled();
        LocalVideoView.this.updateFlashLightButtonState();
      }
    });
  }

  private void restartOutgoingVideo(Cameras.CameraType paramCameraType)
  {
    if (this.audioVideoFailed);
    while (true)
    {
      return;
      this.videoCapturer.stop();
      if (GCommApp.getInstance(getContext()).isInAHangoutWithMedia())
      {
        GCommApp.getInstance(getContext()).getGCommNativeWrapper().stopOutgoingVideo();
        GCommApp.getInstance(getContext()).setLastUsedCameraType(paramCameraType);
        Camera.Size localSize = VideoCapturer.getSizeOfCapturedFrames(paramCameraType);
        if (localSize == null)
        {
          onCameraOpenError(null);
        }
        else
        {
          Log.info("Starting outgoing video");
          GCommApp.getInstance(getContext()).getGCommNativeWrapper().startOutgoingVideo(localSize.width, localSize.height);
          this.selectedCameraType = paramCameraType;
          this.numPendingStartOutgoingVideoRequests = (1 + this.numPendingStartOutgoingVideoRequests);
        }
      }
      else
      {
        this.videoCapturer.start(paramCameraType);
      }
    }
  }

  private void updateFlashLightButtonState()
  {
    if ((!this.videoCapturer.supportsFlashLight()) || (!this.videoCapturer.isCapturing()))
      this.toggleFlashButton.setVisibility(8);
    while (true)
    {
      return;
      this.toggleFlashButton.setVisibility(0);
      if (this.videoCapturer.flashLightEnabled())
        this.toggleFlashButton.setImageResource(R.drawable.ic_flash_off_holo_light);
      else
        this.toggleFlashButton.setImageResource(R.drawable.ic_flash_on_holo_light);
    }
  }

  public final boolean isVideoShowing()
  {
    return this.videoCapturer.isCapturing();
  }

  public final void onCameraOpenError(RuntimeException paramRuntimeException)
  {
    Log.warn("Video capturer failed to start");
    Log.warn(android.util.Log.getStackTraceString(paramRuntimeException));
    hideVideoSurface();
    hideLogo();
    showCameraError();
  }

  public final void onCapturingStateChanged$1385ff()
  {
    updateFlashLightButtonState();
  }

  public final void onMeasure$3b4dfe4b(int paramInt1, int paramInt2)
  {
    layoutVideo(this.selfFrameWidth, this.selfFrameHeight, paramInt1, paramInt2);
  }

  public final void onPause()
  {
    if (this.isRegistered)
    {
      Context localContext = getContext();
      GCommApp.getInstance(localContext).unregisterForEvents(localContext, this.eventHandler, false);
      this.isRegistered = false;
    }
    GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(getContext()).getGCommNativeWrapper();
    if (localGCommNativeWrapper.isOutgoingVideoStarted())
      localGCommNativeWrapper.stopOutgoingVideo();
    this.videoCapturer.stop();
  }

  public final void onResume()
  {
    if (!this.isRegistered)
    {
      Context localContext = getContext();
      GCommApp.getInstance(localContext).registerForEvents(localContext, this.eventHandler, false);
      this.isRegistered = true;
    }
    if (!GCommApp.getInstance(getContext()).isOutgoingVideoMute())
    {
      if ((!GCommApp.getInstance(getContext()).getGCommNativeWrapper().isOutgoingVideoStarted()) && (GCommApp.getInstance(getContext()).isInAHangoutWithMedia()))
        break label100;
      Cameras.CameraType localCameraType1 = GCommApp.getInstance(getContext()).getLastUsedCameraType();
      hideLogo();
      showVideoSurface();
      this.videoCapturer.start(localCameraType1);
    }
    while (true)
    {
      return;
      label100: if (Cameras.isAnyCameraAvailable())
      {
        Cameras.CameraType localCameraType2 = GCommApp.getInstance(getContext()).getLastUsedCameraType();
        if (localCameraType2 == null)
          if (!Cameras.isFrontFacingCameraAvailable())
            break label139;
        for (localCameraType2 = Cameras.CameraType.FrontFacing; ; localCameraType2 = Cameras.CameraType.RearFacing)
        {
          restartOutgoingVideo(localCameraType2);
          break;
        }
      }
      label139: Log.info("Not starting outgoing video because device is not capable.");
    }
  }

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onCameraPreviewFrameDimensionsChanged(int paramInt1, int paramInt2)
    {
      super.onCameraPreviewFrameDimensionsChanged(paramInt1, paramInt2);
      LocalVideoView.access$402(LocalVideoView.this, paramInt1);
      LocalVideoView.access$502(LocalVideoView.this, paramInt2);
      LocalVideoView.this.layoutVideo(LocalVideoView.this.selfFrameWidth, LocalVideoView.this.selfFrameHeight, LocalVideoView.this.getWidth(), LocalVideoView.this.getHeight());
    }

    public final void onCameraSwitchRequested()
    {
      Cameras.CameraType localCameraType1 = GCommApp.getInstance(LocalVideoView.this.getContext()).getLastUsedCameraType();
      if ((localCameraType1 == null) || (localCameraType1 == Cameras.CameraType.FrontFacing));
      for (Cameras.CameraType localCameraType2 = Cameras.CameraType.RearFacing; ; localCameraType2 = Cameras.CameraType.FrontFacing)
      {
        GCommApp.getInstance(LocalVideoView.this.getContext()).setLastUsedCameraType(localCameraType2);
        if (!GCommApp.getInstance(LocalVideoView.this.getContext()).isOutgoingVideoMute())
          LocalVideoView.this.restartOutgoingVideo(localCameraType2);
        return;
      }
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      if (paramError == GCommNativeWrapper.Error.AUDIO_VIDEO_SESSION)
      {
        LocalVideoView.this.videoCapturer.stop();
        LocalVideoView.access$602(LocalVideoView.this, true);
      }
    }

    public final void onOutgoingVideoStarted()
    {
      super.onOutgoingVideoStarted();
      Log.info("Outgoing video started");
      LocalVideoView.access$210(LocalVideoView.this);
      if ((LocalVideoView.this.isHangoutTileStarted()) && (LocalVideoView.this.numPendingStartOutgoingVideoRequests == 0) && (!GCommApp.getInstance(LocalVideoView.this.getContext()).isOutgoingVideoMute()))
      {
        LocalVideoView.this.hideLogo();
        LocalVideoView.this.showVideoSurface();
        LocalVideoView.this.videoCapturer.start(LocalVideoView.this.selectedCameraType);
      }
    }

    public final void onVideoMuteToggleRequested()
    {
      boolean bool1 = true;
      boolean bool2 = GCommApp.getInstance(LocalVideoView.this.getContext()).isOutgoingVideoMute();
      boolean bool3;
      label58: Context localContext;
      if (bool2)
      {
        LocalVideoView.this.restartOutgoingVideo(GCommApp.getInstance(LocalVideoView.this.getContext()).getLastUsedCameraType());
        GCommApp localGCommApp = GCommApp.getInstance(LocalVideoView.this.getContext());
        if (bool2)
          break label119;
        bool3 = bool1;
        localGCommApp.setOutgoingVideoMute(bool3);
        localContext = LocalVideoView.this.getContext();
        if (bool2)
          break label125;
      }
      while (true)
      {
        GCommApp.sendObjectMessage(localContext, 203, Boolean.valueOf(bool1));
        return;
        LocalVideoView.this.videoCapturer.stop();
        GCommApp.getInstance(LocalVideoView.this.getContext()).getGCommNativeWrapper().stopOutgoingVideo();
        break;
        label119: bool3 = false;
        break label58;
        label125: bool1 = false;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.LocalVideoView
 * JD-Core Version:    0.6.2
 */