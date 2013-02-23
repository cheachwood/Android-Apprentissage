package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoView extends GLSurfaceView
{
  private final GCommNativeWrapper gcommNativeWrapper = GCommApp.getInstance(getContext()).getGCommNativeWrapper();
  private volatile boolean reinitializeRenderer;
  private volatile int requestID = 0;

  public VideoView(Context paramContext)
  {
    this(paramContext, null);
  }

  public VideoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setEGLContextClientVersion(2);
    setRenderer(new Renderer((byte)0));
    setRenderMode(0);
    onPause();
  }

  public void setRequestID(int paramInt)
  {
    if (paramInt != this.requestID)
    {
      this.requestID = paramInt;
      this.reinitializeRenderer = true;
    }
  }

  private final class Renderer
    implements GLSurfaceView.Renderer
  {
    private boolean disabled;

    private Renderer()
    {
    }

    public final void onDrawFrame(GL10 paramGL10)
    {
      boolean bool;
      if (VideoView.this.reinitializeRenderer)
      {
        if (!VideoView.this.gcommNativeWrapper.initializeIncomingVideoRenderer(VideoView.this.requestID))
        {
          bool = true;
          this.disabled = bool;
          if (this.disabled)
            Log.debug("initializeIncomingVideoRenderer failed. Rendering disabled");
          VideoView.access$102(VideoView.this, false);
        }
      }
      else
        if (!this.disabled)
          break label71;
      while (true)
      {
        return;
        bool = false;
        break;
        label71: VideoView.this.gcommNativeWrapper.renderIncomingVideoFrame(VideoView.this.requestID);
      }
    }

    public final void onSurfaceChanged(GL10 paramGL10, int paramInt1, int paramInt2)
    {
      boolean bool1 = true;
      if (VideoView.this.reinitializeRenderer)
        if (VideoView.this.gcommNativeWrapper.initializeIncomingVideoRenderer(VideoView.this.requestID))
          break label72;
      label72: for (boolean bool2 = bool1; ; bool2 = false)
      {
        this.disabled = bool2;
        if (this.disabled)
          Log.debug("initializeIncomingVideoRenderer failed. Rendering disabled");
        VideoView.access$102(VideoView.this, false);
        if (!this.disabled)
          break;
        return;
      }
      if (!VideoView.this.gcommNativeWrapper.setIncomingVideoRendererSurfaceSize(VideoView.this.requestID, paramInt1, paramInt2));
      while (true)
      {
        this.disabled = bool1;
        if (!this.disabled)
          break;
        Log.debug("setIncomingVideoRendererSurfaceSize failed. Rendering disabled");
        break;
        bool1 = false;
      }
    }

    public final void onSurfaceCreated(GL10 paramGL10, EGLConfig paramEGLConfig)
    {
      if (!VideoView.this.gcommNativeWrapper.initializeIncomingVideoRenderer(VideoView.this.requestID));
      for (boolean bool = true; ; bool = false)
      {
        this.disabled = bool;
        if (this.disabled)
          Log.debug("initializeIncomingVideoRenderer failed. Rendering disabled");
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.VideoView
 * JD-Core Version:    0.6.2
 */