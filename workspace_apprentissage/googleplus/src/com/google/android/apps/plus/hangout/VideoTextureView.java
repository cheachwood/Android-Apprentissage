package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.apps.plus.views.GLTextureView;
import com.google.android.apps.plus.views.GLTextureView.Renderer;

public class VideoTextureView extends GLTextureView
{
  private final GCommNativeWrapper mGcommNativeWrapper = GCommApp.getInstance(getContext()).getGCommNativeWrapper();
  private volatile boolean mIsDecoding;
  private final Renderer mRenderer;
  private volatile int mRequestID = 0;

  public VideoTextureView(Context paramContext)
  {
    this(paramContext, null);
  }

  public VideoTextureView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setEGLContextClientVersion(2);
    this.mRenderer = new Renderer((byte)0);
    setRenderer(this.mRenderer);
    setRenderMode(0);
    onPause();
  }

  public final boolean isDecoding()
  {
    return this.mIsDecoding;
  }

  public final void setRequestID(int paramInt)
  {
    if (this.mRequestID != paramInt)
    {
      this.mRequestID = paramInt;
      this.mRenderer.reinitialize();
    }
  }

  private final class Renderer
    implements GLTextureView.Renderer
  {
    private volatile int mAttempt;
    private volatile boolean mEnabled;
    private volatile boolean mInitializeRendererPending;
    private int mPendingHeight;
    private int mPendingWidth;
    private boolean mSurfaceSizePending;

    private Renderer()
    {
    }

    private boolean handleFailure()
    {
      try
      {
        this.mAttempt = (1 + this.mAttempt);
        int i = this.mAttempt;
        boolean bool = false;
        if (i >= 30)
        {
          VideoTextureView.access$302(VideoTextureView.this, false);
          this.mEnabled = false;
          this.mInitializeRendererPending = false;
          this.mSurfaceSizePending = false;
          bool = true;
        }
        if (bool)
          Log.debug("Configuring native video renderer failed after " + this.mAttempt + " attempts");
        return bool;
      }
      finally
      {
      }
    }

    // ERROR //
    private void initializeRenderer()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 40	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mInitializeRendererPending	Z
      //   4: ifeq +37 -> 41
      //   7: aload_0
      //   8: getfield 20	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:this$0	Lcom/google/android/apps/plus/hangout/VideoTextureView;
      //   11: invokestatic 72	com/google/android/apps/plus/hangout/VideoTextureView:access$200	(Lcom/google/android/apps/plus/hangout/VideoTextureView;)Lcom/google/android/apps/plus/hangout/GCommNativeWrapper;
      //   14: aload_0
      //   15: getfield 20	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:this$0	Lcom/google/android/apps/plus/hangout/VideoTextureView;
      //   18: invokestatic 76	com/google/android/apps/plus/hangout/VideoTextureView:access$100	(Lcom/google/android/apps/plus/hangout/VideoTextureView;)I
      //   21: invokevirtual 82	com/google/android/apps/plus/hangout/GCommNativeWrapper:initializeIncomingVideoRenderer	(I)Z
      //   24: ifeq +74 -> 98
      //   27: aload_0
      //   28: monitorenter
      //   29: aload_0
      //   30: iconst_1
      //   31: putfield 38	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mEnabled	Z
      //   34: aload_0
      //   35: iconst_0
      //   36: putfield 40	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mInitializeRendererPending	Z
      //   39: aload_0
      //   40: monitorexit
      //   41: aload_0
      //   42: getfield 38	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mEnabled	Z
      //   45: ifeq +47 -> 92
      //   48: aload_0
      //   49: getfield 42	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mSurfaceSizePending	Z
      //   52: ifeq +40 -> 92
      //   55: aload_0
      //   56: getfield 20	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:this$0	Lcom/google/android/apps/plus/hangout/VideoTextureView;
      //   59: invokestatic 72	com/google/android/apps/plus/hangout/VideoTextureView:access$200	(Lcom/google/android/apps/plus/hangout/VideoTextureView;)Lcom/google/android/apps/plus/hangout/GCommNativeWrapper;
      //   62: aload_0
      //   63: getfield 20	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:this$0	Lcom/google/android/apps/plus/hangout/VideoTextureView;
      //   66: invokestatic 76	com/google/android/apps/plus/hangout/VideoTextureView:access$100	(Lcom/google/android/apps/plus/hangout/VideoTextureView;)I
      //   69: aload_0
      //   70: getfield 84	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mPendingWidth	I
      //   73: aload_0
      //   74: getfield 86	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mPendingHeight	I
      //   77: invokevirtual 90	com/google/android/apps/plus/hangout/GCommNativeWrapper:setIncomingVideoRendererSurfaceSize	(III)Z
      //   80: ifeq +38 -> 118
      //   83: aload_0
      //   84: monitorenter
      //   85: aload_0
      //   86: iconst_0
      //   87: putfield 42	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:mSurfaceSizePending	Z
      //   90: aload_0
      //   91: monitorexit
      //   92: return
      //   93: astore_2
      //   94: aload_0
      //   95: monitorexit
      //   96: aload_2
      //   97: athrow
      //   98: aload_0
      //   99: invokespecial 92	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:handleFailure	()Z
      //   102: ifeq -61 -> 41
      //   105: ldc 94
      //   107: invokestatic 67	com/google/android/apps/plus/hangout/Log:debug	(Ljava/lang/String;)V
      //   110: goto -18 -> 92
      //   113: astore_1
      //   114: aload_0
      //   115: monitorexit
      //   116: aload_1
      //   117: athrow
      //   118: aload_0
      //   119: invokespecial 92	com/google/android/apps/plus/hangout/VideoTextureView$Renderer:handleFailure	()Z
      //   122: ifeq -30 -> 92
      //   125: ldc 96
      //   127: invokestatic 67	com/google/android/apps/plus/hangout/Log:debug	(Ljava/lang/String;)V
      //   130: goto -38 -> 92
      //
      // Exception table:
      //   from	to	target	type
      //   29	41	93	finally
      //   85	92	113	finally
    }

    public final void onDrawFrame$62c01aa1()
    {
      initializeRenderer();
      if (this.mEnabled)
        VideoTextureView.this.mGcommNativeWrapper.renderIncomingVideoFrame(VideoTextureView.this.mRequestID);
      try
      {
        VideoTextureView.access$302(VideoTextureView.this, true);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void onSurfaceChanged$4ccda93f(int paramInt1, int paramInt2)
    {
      try
      {
        this.mSurfaceSizePending = true;
        this.mPendingHeight = paramInt2;
        this.mPendingWidth = paramInt1;
        this.mAttempt = 0;
        initializeRenderer();
        return;
      }
      finally
      {
      }
    }

    public final void onSurfaceCreated$4a9c201c()
    {
      initializeRenderer();
    }

    public final void reinitialize()
    {
      try
      {
        this.mInitializeRendererPending = true;
        this.mAttempt = 0;
        VideoTextureView.access$302(VideoTextureView.this, false);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.VideoTextureView
 * JD-Core Version:    0.6.2
 */