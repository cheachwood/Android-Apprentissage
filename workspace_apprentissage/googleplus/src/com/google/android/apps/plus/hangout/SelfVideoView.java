package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.views.RelativeLayoutWithLayoutNotifications;

public class SelfVideoView extends RelativeLayoutWithLayoutNotifications
  implements VideoCapturer.Host
{
  private boolean audioVideoFailed;
  private View cameraErrorView;
  private boolean disableFlashLightSupport = false;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  private int extraBottomOffset;
  private ViewGroup insetViewGroup;
  private LayoutMode layoutMode = LayoutMode.FIT;
  private HangoutTile mHangoutTile;
  private float mVerticalGravity = 0.0F;
  private int numPendingStartOutgoingVideoRequests;
  private Cameras.CameraType selectedCameraType;
  private int selfFrameHeight = 240;
  private int selfFrameWidth = 320;
  private SurfaceView surfaceView;
  private ImageButton toggleFlashLightButton;
  private VideoCapturer videoCapturer;

  public SelfVideoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    LayoutInflater.from(paramContext).inflate(R.layout.hangout_self_video_view, this, true);
    this.insetViewGroup = ((ViewGroup)findViewById(R.id.inset));
    this.surfaceView = ((SurfaceView)this.insetViewGroup.findViewById(R.id.surface_view));
    this.cameraErrorView = this.insetViewGroup.findViewById(R.id.self_video_error);
    this.toggleFlashLightButton = ((ImageButton)this.insetViewGroup.findViewById(R.id.hangout_toggle_flash_light_button));
    this.toggleFlashLightButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        SelfVideoView.this.videoCapturer.toggleFlashLightEnabled();
        SelfVideoView.this.updateFlashLightButtonState();
      }
    });
    this.videoCapturer = new VideoCapturer(paramContext, GCommApp.getInstance(paramContext).getGCommNativeWrapper(), this.surfaceView.getHolder(), this);
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
    if ((this.disableFlashLightSupport) || (!this.videoCapturer.supportsFlashLight()) || (!this.videoCapturer.isCapturing()))
      this.toggleFlashLightButton.setVisibility(8);
    while (true)
    {
      return;
      this.toggleFlashLightButton.setVisibility(0);
      if (this.videoCapturer.flashLightEnabled())
        this.toggleFlashLightButton.setImageResource(R.drawable.ic_flash_off_holo_light);
      else
        this.toggleFlashLightButton.setImageResource(R.drawable.ic_flash_on_holo_light);
    }
  }

  public final void layout(int paramInt1, int paramInt2)
  {
    int n;
    int i2;
    int i1;
    RectangleDimensions localRectangleDimensions;
    int j;
    int i;
    label87: RelativeLayout.LayoutParams localLayoutParams;
    if (this.layoutMode == LayoutMode.INSET)
    {
      n = Math.max(paramInt1, paramInt2);
      if (this.selfFrameWidth > this.selfFrameHeight)
      {
        i2 = (int)(0.2D * n);
        i1 = (int)(i2 * this.selfFrameHeight / this.selfFrameWidth);
        localRectangleDimensions = new RectangleDimensions(i2, i1);
        j = (int)(0.02D * n);
        i = j + this.extraBottomOffset;
        localLayoutParams = new RelativeLayout.LayoutParams(localRectangleDimensions.width, localRectangleDimensions.height);
        localLayoutParams.setMargins(0, 0, j, i);
        if (this.layoutMode != LayoutMode.INSET)
          break label332;
        localLayoutParams.addRule(12);
        localLayoutParams.addRule(11);
      }
    }
    while (true)
    {
      this.insetViewGroup.setLayoutParams(localLayoutParams);
      Object[] arrayOfObject = new Object[6];
      arrayOfObject[0] = Integer.valueOf(this.selfFrameWidth);
      arrayOfObject[1] = Integer.valueOf(this.selfFrameHeight);
      arrayOfObject[2] = Integer.valueOf(paramInt1);
      arrayOfObject[3] = Integer.valueOf(paramInt2);
      arrayOfObject[4] = Integer.valueOf(localRectangleDimensions.width);
      arrayOfObject[5] = Integer.valueOf(localRectangleDimensions.height);
      Log.debug("SelfView.layout: frame=%d,%d root=%d,%d self=%d,%d", arrayOfObject);
      setPadding(0, (int)(paramInt2 * this.mVerticalGravity), 0, 0);
      return;
      i1 = (int)(0.2D * n);
      i2 = (int)(i1 * this.selfFrameWidth / this.selfFrameHeight);
      break;
      if (this.selfFrameHeight == 0)
      {
        localRectangleDimensions = new RectangleDimensions(100, 100);
        i = 0;
        j = 0;
        break label87;
      }
      int k = this.selfFrameWidth;
      int m = this.selfFrameHeight;
      localRectangleDimensions = Utils.fitContentInContainer(k / m, paramInt1, paramInt2);
      i = 0;
      j = 0;
      break label87;
      label332: localLayoutParams.addRule(13);
    }
  }

  public final void onCameraOpenError(RuntimeException paramRuntimeException)
  {
    Log.warn("Video capturer failed to start");
    Log.warn(android.util.Log.getStackTraceString(paramRuntimeException));
    this.surfaceView.setVisibility(8);
    this.cameraErrorView.setVisibility(0);
  }

  public final void onCapturingStateChanged$1385ff()
  {
    updateFlashLightButtonState();
  }

  public final void onMeasure$3b4dfe4b(int paramInt1, int paramInt2)
  {
    layout(paramInt1, paramInt2);
  }

  public final void onPause()
  {
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.eventHandler, false);
    if (GCommApp.getInstance(getContext()).getGCommNativeWrapper().isOutgoingVideoStarted())
      GCommApp.getInstance(getContext()).getGCommNativeWrapper().stopOutgoingVideo();
    this.videoCapturer.stop();
  }

  public final void onResume()
  {
    GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
    startCapturing();
  }

  public void setExtraBottomOffset(int paramInt)
  {
    this.extraBottomOffset = paramInt;
    this.surfaceView.requestLayout();
  }

  public void setHangoutTile(HangoutTile paramHangoutTile)
  {
    this.mHangoutTile = paramHangoutTile;
  }

  public void setLayoutMode(LayoutMode paramLayoutMode)
  {
    this.layoutMode = paramLayoutMode;
  }

  public void setVerticalGravity(float paramFloat)
  {
    this.mVerticalGravity = paramFloat;
  }

  public void setVisibleViewOnTouchListener(View.OnTouchListener paramOnTouchListener)
  {
    if (this.layoutMode == LayoutMode.FIT)
    {
      setOnTouchListener(paramOnTouchListener);
      this.surfaceView.setOnTouchListener(null);
    }
    while (true)
    {
      return;
      setOnTouchListener(null);
      this.surfaceView.setOnTouchListener(paramOnTouchListener);
    }
  }

  public final void startCapturing()
  {
    if (!GCommApp.getInstance(getContext()).isOutgoingVideoMute())
    {
      if ((!GCommApp.getInstance(getContext()).getGCommNativeWrapper().isOutgoingVideoStarted()) && (GCommApp.getInstance(getContext()).isInAHangoutWithMedia()))
        break label70;
      Cameras.CameraType localCameraType1 = GCommApp.getInstance(getContext()).getLastUsedCameraType();
      this.surfaceView.setVisibility(0);
      this.videoCapturer.start(localCameraType1);
    }
    while (true)
    {
      return;
      label70: if (Cameras.isAnyCameraAvailable())
      {
        Cameras.CameraType localCameraType2 = GCommApp.getInstance(getContext()).getLastUsedCameraType();
        if (localCameraType2 == null)
          if (!Cameras.isFrontFacingCameraAvailable())
            break label109;
        label109: for (localCameraType2 = Cameras.CameraType.FrontFacing; ; localCameraType2 = Cameras.CameraType.RearFacing)
        {
          restartOutgoingVideo(localCameraType2);
          break;
        }
      }
      Log.info("Not starting outgoing video because device is not capable.");
    }
  }

  public final void turnOffFlashLightSupport()
  {
    this.disableFlashLightSupport = true;
    this.toggleFlashLightButton.setVisibility(8);
  }

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onCameraPreviewFrameDimensionsChanged(int paramInt1, int paramInt2)
    {
      super.onCameraPreviewFrameDimensionsChanged(paramInt1, paramInt2);
      SelfVideoView.access$602(SelfVideoView.this, paramInt1);
      SelfVideoView.access$702(SelfVideoView.this, paramInt2);
      SelfVideoView.this.layout(SelfVideoView.this.getWidth(), SelfVideoView.this.getHeight());
    }

    public final void onCameraSwitchRequested()
    {
      Cameras.CameraType localCameraType1 = GCommApp.getInstance(SelfVideoView.this.getContext()).getLastUsedCameraType();
      if ((localCameraType1 == null) || (localCameraType1 == Cameras.CameraType.FrontFacing));
      for (Cameras.CameraType localCameraType2 = Cameras.CameraType.RearFacing; ; localCameraType2 = Cameras.CameraType.FrontFacing)
      {
        GCommApp.getInstance(SelfVideoView.this.getContext()).setLastUsedCameraType(localCameraType2);
        if (!GCommApp.getInstance(SelfVideoView.this.getContext()).isOutgoingVideoMute())
          SelfVideoView.this.restartOutgoingVideo(localCameraType2);
        return;
      }
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      if (paramError == GCommNativeWrapper.Error.AUDIO_VIDEO_SESSION)
      {
        SelfVideoView.this.videoCapturer.stop();
        SelfVideoView.access$802(SelfVideoView.this, true);
      }
    }

    public final void onOutgoingVideoStarted()
    {
      super.onOutgoingVideoStarted();
      Log.info("Outgoing video started");
      SelfVideoView.access$210(SelfVideoView.this);
      if ((SelfVideoView.this.mHangoutTile != null) && (SelfVideoView.this.mHangoutTile.isTileStarted()) && (SelfVideoView.this.numPendingStartOutgoingVideoRequests == 0) && (!GCommApp.getInstance(SelfVideoView.this.getContext()).isOutgoingVideoMute()))
      {
        SelfVideoView.this.surfaceView.setVisibility(0);
        SelfVideoView.this.videoCapturer.start(SelfVideoView.this.selectedCameraType);
      }
    }

    public final void onVideoMuteToggleRequested()
    {
      boolean bool1 = true;
      boolean bool2 = GCommApp.getInstance(SelfVideoView.this.getContext()).isOutgoingVideoMute();
      boolean bool3;
      label58: Context localContext;
      if (bool2)
      {
        SelfVideoView.this.restartOutgoingVideo(GCommApp.getInstance(SelfVideoView.this.getContext()).getLastUsedCameraType());
        GCommApp localGCommApp = GCommApp.getInstance(SelfVideoView.this.getContext());
        if (bool2)
          break label119;
        bool3 = bool1;
        localGCommApp.setOutgoingVideoMute(bool3);
        localContext = SelfVideoView.this.getContext();
        if (bool2)
          break label125;
      }
      while (true)
      {
        GCommApp.sendObjectMessage(localContext, 203, Boolean.valueOf(bool1));
        return;
        SelfVideoView.this.videoCapturer.stop();
        GCommApp.getInstance(SelfVideoView.this.getContext()).getGCommNativeWrapper().stopOutgoingVideo();
        break;
        label119: bool3 = false;
        break label58;
        label125: bool1 = false;
      }
    }
  }

  static enum LayoutMode
  {
    static
    {
      FIT = new LayoutMode("FIT", 1);
      LayoutMode[] arrayOfLayoutMode = new LayoutMode[2];
      arrayOfLayoutMode[0] = INSET;
      arrayOfLayoutMode[1] = FIT;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.SelfVideoView
 * JD-Core Version:    0.6.2
 */