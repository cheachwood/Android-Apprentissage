package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLDebugHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.google.android.apps.plus.util.SystemProperties;
import java.io.Writer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLTextureView extends TextureView
  implements TextureView.SurfaceTextureListener
{
  private static final GLThreadManager sGLThreadManager = new GLThreadManager((byte)0);
  private int mDebugFlags;
  private boolean mDetached;
  private EGLConfigChooser mEGLConfigChooser;
  private int mEGLContextClientVersion;
  private EGLContextFactory mEGLContextFactory;
  private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
  private GLThread mGLThread;
  private GLWrapper mGLWrapper;
  private boolean mPreserveEGLContextOnPause;
  private boolean mSizeChanged = true;
  private Renderer mViewRenderer;

  public GLTextureView(Context paramContext)
  {
    super(paramContext);
    setSurfaceTextureListener(this);
  }

  public GLTextureView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setSurfaceTextureListener(this);
  }

  private void checkRenderThreadState()
  {
    if (this.mGLThread != null)
      throw new IllegalStateException("setRenderer has already been called for this instance.");
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((this.mDetached) && (this.mViewRenderer != null))
    {
      int i = 1;
      if (this.mGLThread != null)
        i = this.mGLThread.getRenderMode();
      this.mGLThread = new GLThread(this.mViewRenderer);
      if (i != 1)
        this.mGLThread.setRenderMode(i);
      this.mGLThread.start();
    }
    this.mDetached = false;
  }

  protected void onDetachedFromWindow()
  {
    if (this.mGLThread != null)
      this.mGLThread.requestExitAndWait();
    this.mDetached = true;
    super.onDetachedFromWindow();
  }

  public final void onPause()
  {
    this.mGLThread.onPause();
  }

  public final void onResume()
  {
    this.mGLThread.onResume();
  }

  public void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
  {
    this.mGLThread.surfaceCreated();
    this.mGLThread.onWindowResize(paramInt1, paramInt2);
  }

  public boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture)
  {
    this.mGLThread.surfaceDestroyed();
    return true;
  }

  public void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
  {
    this.mGLThread.onWindowResize(paramInt1, paramInt2);
  }

  public void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture)
  {
  }

  public final void requestRender()
  {
    this.mGLThread.requestRender();
  }

  public void setDebugFlags(int paramInt)
  {
    this.mDebugFlags = paramInt;
  }

  public void setEGLConfigChooser(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setEGLConfigChooser(new ComponentSizeChooser(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
  }

  public void setEGLConfigChooser(EGLConfigChooser paramEGLConfigChooser)
  {
    checkRenderThreadState();
    this.mEGLConfigChooser = paramEGLConfigChooser;
  }

  public void setEGLConfigChooser(boolean paramBoolean)
  {
    setEGLConfigChooser(new SimpleEGLConfigChooser(paramBoolean));
  }

  public void setEGLContextClientVersion(int paramInt)
  {
    checkRenderThreadState();
    this.mEGLContextClientVersion = paramInt;
  }

  public void setEGLContextFactory(EGLContextFactory paramEGLContextFactory)
  {
    checkRenderThreadState();
    this.mEGLContextFactory = paramEGLContextFactory;
  }

  public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory paramEGLWindowSurfaceFactory)
  {
    checkRenderThreadState();
    this.mEGLWindowSurfaceFactory = paramEGLWindowSurfaceFactory;
  }

  public void setGLWrapper(GLWrapper paramGLWrapper)
  {
    this.mGLWrapper = paramGLWrapper;
  }

  public void setPreserveEGLContextOnPause(boolean paramBoolean)
  {
    this.mPreserveEGLContextOnPause = paramBoolean;
  }

  public void setRenderMode(int paramInt)
  {
    this.mGLThread.setRenderMode(paramInt);
  }

  public void setRenderer(Renderer paramRenderer)
  {
    checkRenderThreadState();
    if (this.mEGLConfigChooser == null)
      this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
    if (this.mEGLContextFactory == null)
      this.mEGLContextFactory = new DefaultContextFactory((byte)0);
    if (this.mEGLWindowSurfaceFactory == null)
      this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory((byte)0);
    this.mViewRenderer = paramRenderer;
    this.mGLThread = new GLThread(paramRenderer);
    this.mGLThread.start();
  }

  private abstract class BaseConfigChooser
    implements GLTextureView.EGLConfigChooser
  {
    protected int[] mConfigSpec;

    public BaseConfigChooser(int[] arg2)
    {
      if (GLTextureView.this.mEGLContextClientVersion != 2);
      while (true)
      {
        this.mConfigSpec = localObject;
        return;
        int i = localObject.length;
        int[] arrayOfInt = new int[i + 2];
        System.arraycopy(localObject, 0, arrayOfInt, 0, i - 1);
        arrayOfInt[(i - 1)] = 12352;
        arrayOfInt[i] = 4;
        arrayOfInt[(i + 1)] = 12344;
        Object localObject = arrayOfInt;
      }
    }

    public final EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay)
    {
      int[] arrayOfInt = new int[1];
      if (!paramEGL10.eglChooseConfig(paramEGLDisplay, this.mConfigSpec, null, 0, arrayOfInt))
        throw new IllegalArgumentException("eglChooseConfig failed");
      int i = arrayOfInt[0];
      if (i <= 0)
        throw new IllegalArgumentException("No configs match configSpec");
      EGLConfig[] arrayOfEGLConfig = new EGLConfig[i];
      if (!paramEGL10.eglChooseConfig(paramEGLDisplay, this.mConfigSpec, arrayOfEGLConfig, i, arrayOfInt))
        throw new IllegalArgumentException("eglChooseConfig#2 failed");
      EGLConfig localEGLConfig = chooseConfig(paramEGL10, paramEGLDisplay, arrayOfEGLConfig);
      if (localEGLConfig == null)
        throw new IllegalArgumentException("No config chosen");
      return localEGLConfig;
    }

    abstract EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig[] paramArrayOfEGLConfig);
  }

  private class ComponentSizeChooser extends GLTextureView.BaseConfigChooser
  {
    protected int mAlphaSize;
    protected int mBlueSize;
    protected int mDepthSize;
    protected int mGreenSize;
    protected int mRedSize;
    protected int mStencilSize;
    private int[] mValue = new int[1];

    public ComponentSizeChooser(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int arg7)
    {
      super(new int[] { 12324, paramInt1, 12323, paramInt2, 12322, paramInt3, 12321, paramInt4, 12325, paramInt5, 12326, i, 12344 });
      this.mRedSize = paramInt1;
      this.mGreenSize = paramInt2;
      this.mBlueSize = paramInt3;
      this.mAlphaSize = paramInt4;
      this.mDepthSize = paramInt5;
      this.mStencilSize = i;
    }

    private int findConfigAttrib(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig, int paramInt1, int paramInt2)
    {
      boolean bool = paramEGL10.eglGetConfigAttrib(paramEGLDisplay, paramEGLConfig, paramInt1, this.mValue);
      int i = 0;
      if (bool)
        i = this.mValue[0];
      return i;
    }

    public final EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig[] paramArrayOfEGLConfig)
    {
      int i = paramArrayOfEGLConfig.length;
      int j = 0;
      EGLConfig localEGLConfig;
      if (j < i)
      {
        localEGLConfig = paramArrayOfEGLConfig[j];
        int k = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12325, 0);
        int m = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12326, 0);
        if ((k >= this.mDepthSize) && (m >= this.mStencilSize))
        {
          int n = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12324, 0);
          int i1 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12323, 0);
          int i2 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12322, 0);
          int i3 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12321, 0);
          if ((n != this.mRedSize) || (i1 != this.mGreenSize) || (i2 != this.mBlueSize) || (i3 != this.mAlphaSize));
        }
      }
      while (true)
      {
        return localEGLConfig;
        j++;
        break;
        localEGLConfig = null;
      }
    }
  }

  private final class DefaultContextFactory
    implements GLTextureView.EGLContextFactory
  {
    private DefaultContextFactory()
    {
    }

    public final EGLContext createContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig)
    {
      int[] arrayOfInt = new int[3];
      arrayOfInt[0] = 12440;
      arrayOfInt[1] = GLTextureView.this.mEGLContextClientVersion;
      arrayOfInt[2] = 12344;
      EGLContext localEGLContext = EGL10.EGL_NO_CONTEXT;
      if (GLTextureView.this.mEGLContextClientVersion != 0);
      while (true)
      {
        return paramEGL10.eglCreateContext(paramEGLDisplay, paramEGLConfig, localEGLContext, arrayOfInt);
        arrayOfInt = null;
      }
    }

    public final void destroyContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLContext paramEGLContext)
    {
      if (!paramEGL10.eglDestroyContext(paramEGLDisplay, paramEGLContext))
      {
        Log.e("DefaultContextFactory", "display:" + paramEGLDisplay + " context: " + paramEGLContext);
        throw new RuntimeException("eglDestroyContext failed: " + paramEGL10.eglGetError());
      }
    }
  }

  private static final class DefaultWindowSurfaceFactory
    implements GLTextureView.EGLWindowSurfaceFactory
  {
    public final EGLSurface createWindowSurface(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig, Object paramObject)
    {
      try
      {
        EGLSurface localEGLSurface2 = paramEGL10.eglCreateWindowSurface(paramEGLDisplay, paramEGLConfig, paramObject, null);
        localEGLSurface1 = localEGLSurface2;
        return localEGLSurface1;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        while (true)
        {
          Log.e("GLTextureView", "eglCreateWindowSurface", localIllegalArgumentException);
          EGLSurface localEGLSurface1 = null;
        }
      }
    }

    public final void destroySurface(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLSurface paramEGLSurface)
    {
      paramEGL10.eglDestroySurface(paramEGLDisplay, paramEGLSurface);
    }
  }

  public static abstract interface EGLConfigChooser
  {
    public abstract EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay);
  }

  public static abstract interface EGLContextFactory
  {
    public abstract EGLContext createContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig);

    public abstract void destroyContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLContext paramEGLContext);
  }

  public static abstract interface EGLWindowSurfaceFactory
  {
    public abstract EGLSurface createWindowSurface(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig, Object paramObject);

    public abstract void destroySurface(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLSurface paramEGLSurface);
  }

  private final class EglHelper
  {
    EGL10 mEgl;
    EGLConfig mEglConfig;
    EGLContext mEglContext;
    EGLDisplay mEglDisplay;
    EGLSurface mEglSurface;

    public EglHelper()
    {
    }

    private void throwEglException(String paramString)
    {
      throwEglException(paramString, this.mEgl.eglGetError());
    }

    private static void throwEglException(String paramString, int paramInt)
    {
      throw new RuntimeException(paramString + " failed: " + paramInt);
    }

    public final GL createSurface(SurfaceTexture paramSurfaceTexture)
    {
      if (this.mEgl == null)
        throw new RuntimeException("egl not initialized");
      if (this.mEglDisplay == null)
        throw new RuntimeException("eglDisplay not initialized");
      if (this.mEglConfig == null)
        throw new RuntimeException("mEglConfig not initialized");
      if ((this.mEglSurface != null) && (this.mEglSurface != EGL10.EGL_NO_SURFACE))
      {
        this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        GLTextureView.this.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
      }
      this.mEglSurface = GLTextureView.this.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, paramSurfaceTexture);
      GL localGL;
      if ((this.mEglSurface == null) || (this.mEglSurface == EGL10.EGL_NO_SURFACE))
      {
        if (this.mEgl.eglGetError() == 12299)
          Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
        localGL = null;
      }
      while (true)
      {
        return localGL;
        if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext))
          throwEglException("eglMakeCurrent");
        localGL = this.mEglContext.getGL();
        if (GLTextureView.this.mGLWrapper != null)
          localGL = GLTextureView.this.mGLWrapper.wrap$40ce139f();
        if ((0x3 & GLTextureView.this.mDebugFlags) != 0)
        {
          int i = 0x1 & GLTextureView.this.mDebugFlags;
          int j = 0;
          if (i != 0)
            j = 1;
          int k = 0x2 & GLTextureView.this.mDebugFlags;
          GLTextureView.LogWriter localLogWriter = null;
          if (k != 0)
            localLogWriter = new GLTextureView.LogWriter();
          localGL = GLDebugHelper.wrap(localGL, j, localLogWriter);
        }
      }
    }

    public final void finish()
    {
      if (this.mEglContext != null)
      {
        GLTextureView.this.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
        this.mEglContext = null;
      }
      if (this.mEglDisplay != null)
      {
        this.mEgl.eglTerminate(this.mEglDisplay);
        this.mEglDisplay = null;
      }
    }

    public final void start()
    {
      this.mEgl = ((EGL10)EGLContext.getEGL());
      this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
      if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY)
        throw new RuntimeException("eglGetDisplay failed");
      int[] arrayOfInt = new int[2];
      if (!this.mEgl.eglInitialize(this.mEglDisplay, arrayOfInt))
        throw new RuntimeException("eglInitialize failed");
      this.mEglConfig = GLTextureView.this.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
      this.mEglContext = GLTextureView.this.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
      if ((this.mEglContext == null) || (this.mEglContext == EGL10.EGL_NO_CONTEXT))
      {
        this.mEglContext = null;
        throwEglException("createContext");
      }
      this.mEglSurface = null;
    }

    public final boolean swap()
    {
      if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface))
      {
        int i = this.mEgl.eglGetError();
        switch (i)
        {
        case 12300:
        case 12301:
        default:
          throwEglException("eglSwapBuffers", i);
        case 12302:
        case 12299:
        }
      }
      while (true)
      {
        for (boolean bool = true; ; bool = false)
          return bool;
        Log.e("EglHelper", "eglSwapBuffers returned EGL_BAD_NATIVE_WINDOW. tid=" + Thread.currentThread().getId());
      }
    }
  }

  final class GLThread extends Thread
  {
    private GLTextureView.EglHelper mEglHelper;
    private ArrayList<Runnable> mEventQueue = new ArrayList();
    private boolean mExited;
    private boolean mHasSurface;
    private boolean mHaveEglContext;
    private boolean mHaveEglSurface;
    private int mHeight = 0;
    private boolean mPaused;
    private boolean mRenderComplete;
    private int mRenderMode = 1;
    private GLTextureView.Renderer mRenderer;
    private boolean mRequestPaused;
    private boolean mRequestRender = true;
    private boolean mShouldExit;
    private boolean mShouldReleaseEglContext;
    private boolean mWaitingForSurface;
    private int mWidth = 0;

    GLThread(GLTextureView.Renderer arg2)
    {
      Object localObject;
      this.mRenderer = localObject;
    }

    private void guardedRun()
      throws InterruptedException
    {
      this.mEglHelper = new GLTextureView.EglHelper(GLTextureView.this);
      this.mHaveEglContext = false;
      this.mHaveEglSurface = false;
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      int i3 = 0;
      int i4 = 0;
      Runnable localRunnable = null;
      try
      {
        synchronized (GLTextureView.sGLThreadManager)
        {
          while (true)
          {
            if (this.mShouldExit)
              synchronized (GLTextureView.sGLThreadManager)
              {
                stopEglSurfaceLocked();
                stopEglContextLocked();
                return;
              }
            if (this.mEventQueue.isEmpty())
              break;
            localRunnable = (Runnable)this.mEventQueue.remove(0);
            if (localRunnable == null)
              break label541;
            localRunnable.run();
            localRunnable = null;
          }
          if (this.mPaused != this.mRequestPaused)
          {
            this.mPaused = this.mRequestPaused;
            GLTextureView.sGLThreadManager.notifyAll();
          }
          if (this.mShouldReleaseEglContext)
          {
            stopEglSurfaceLocked();
            stopEglContextLocked();
            this.mShouldReleaseEglContext = false;
            i2 = 1;
          }
          if (k != 0)
          {
            stopEglSurfaceLocked();
            stopEglContextLocked();
            k = 0;
          }
          if ((this.mHaveEglSurface) && (this.mPaused))
          {
            stopEglSurfaceLocked();
            if ((!GLTextureView.this.mPreserveEGLContextOnPause) || (GLTextureView.sGLThreadManager.shouldReleaseEGLContextWhenPausing()))
              stopEglContextLocked();
            if (GLTextureView.sGLThreadManager.shouldTerminateEGLWhenPausing())
              this.mEglHelper.finish();
          }
          if ((!this.mHasSurface) && (!this.mWaitingForSurface))
          {
            if (this.mHaveEglSurface)
              stopEglSurfaceLocked();
            this.mWaitingForSurface = true;
            GLTextureView.sGLThreadManager.notifyAll();
          }
          if ((this.mHasSurface) && (this.mWaitingForSurface))
          {
            this.mWaitingForSurface = false;
            GLTextureView.sGLThreadManager.notifyAll();
          }
          if (i1 != 0)
          {
            n = 0;
            i1 = 0;
            this.mRenderComplete = true;
            GLTextureView.sGLThreadManager.notifyAll();
          }
          if (readyToDraw())
            if (!this.mHaveEglContext)
            {
              if (i2 != 0)
                i2 = 0;
            }
            else
            {
              if ((this.mHaveEglContext) && (!this.mHaveEglSurface))
              {
                this.mHaveEglSurface = true;
                j = 1;
                m = 1;
              }
              if (!this.mHaveEglSurface)
                break label532;
              if (!GLTextureView.this.mSizeChanged)
                break label524;
              m = 1;
              i3 = this.mWidth;
              i4 = this.mHeight;
              n = 1;
              GLTextureView.access$1002(GLTextureView.this, false);
              GLTextureView.sGLThreadManager.notifyAll();
            }
        }
      }
      finally
      {
        synchronized (GLTextureView.sGLThreadManager)
        {
          while (true)
          {
            stopEglSurfaceLocked();
            stopEglContextLocked();
            throw localObject1;
            boolean bool2 = GLTextureView.sGLThreadManager.tryAcquireEglContextLocked(this);
            if (!bool2)
              continue;
            try
            {
              this.mEglHelper.start();
              this.mHaveEglContext = true;
              i = 1;
              GLTextureView.sGLThreadManager.notifyAll();
            }
            catch (RuntimeException localRuntimeException)
            {
              GLTextureView.sGLThreadManager.releaseEglContextLocked(this);
              throw localRuntimeException;
            }
            label524: this.mRequestRender = false;
            continue;
            label532: GLTextureView.sGLThreadManager.wait();
            continue;
            label541: if (j != 0)
            {
              GL10 localGL10 = (GL10)this.mEglHelper.createSurface(GLTextureView.this.getSurfaceTexture());
              if (localGL10 != null)
              {
                GLTextureView.sGLThreadManager.checkGLDriver(localGL10);
                j = 0;
              }
            }
            else
            {
              if (i != 0)
              {
                GLTextureView.Renderer localRenderer = this.mRenderer;
                localRenderer.onSurfaceCreated$4a9c201c();
                i = 0;
              }
              if (m != 0)
              {
                GLTextureView.EglHelper localEglHelper = this.mEglHelper;
                localEglHelper.mEgl.eglMakeCurrent(localEglHelper.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                localEglHelper.mEgl.eglMakeCurrent(localEglHelper.mEglDisplay, localEglHelper.mEglSurface, localEglHelper.mEglSurface, localEglHelper.mEglContext);
                this.mRenderer.onSurfaceChanged$4ccda93f(i3, i4);
                m = 0;
              }
              this.mRenderer.onDrawFrame$62c01aa1();
              boolean bool1 = this.mEglHelper.swap();
              if (!bool1)
                k = 1;
              if (n == 0)
                continue;
              i1 = 1;
              continue;
            }
            synchronized (GLTextureView.sGLThreadManager)
            {
              stopEglSurfaceLocked();
              stopEglContextLocked();
            }
          }
        }
      }
    }

    private boolean readyToDraw()
    {
      int i = 1;
      if ((!this.mPaused) && (this.mHasSurface) && (this.mWidth > 0) && (this.mHeight > 0) && ((this.mRequestRender) || (this.mRenderMode == i)));
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    private void stopEglContextLocked()
    {
      if (this.mHaveEglContext)
      {
        this.mEglHelper.finish();
        this.mHaveEglContext = false;
        GLTextureView.sGLThreadManager.releaseEglContextLocked(this);
      }
    }

    private void stopEglSurfaceLocked()
    {
      if (this.mHaveEglSurface)
      {
        this.mHaveEglSurface = false;
        GLTextureView.EglHelper localEglHelper = this.mEglHelper;
        if ((localEglHelper.mEglSurface != null) && (localEglHelper.mEglSurface != EGL10.EGL_NO_SURFACE))
        {
          localEglHelper.mEgl.eglMakeCurrent(localEglHelper.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
          localEglHelper.this$0.mEGLWindowSurfaceFactory.destroySurface(localEglHelper.mEgl, localEglHelper.mEglDisplay, localEglHelper.mEglSurface);
          localEglHelper.mEglSurface = null;
        }
      }
    }

    public final int getRenderMode()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        int i = this.mRenderMode;
        return i;
      }
    }

    public final void onPause()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mRequestPaused = true;
        GLTextureView.sGLThreadManager.notifyAll();
        while (true)
          if (!this.mExited)
          {
            boolean bool = this.mPaused;
            if (!bool)
              try
              {
                GLTextureView.sGLThreadManager.wait();
              }
              catch (InterruptedException localInterruptedException)
              {
                Thread.currentThread().interrupt();
              }
          }
      }
    }

    public final void onResume()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mRequestPaused = false;
        this.mRequestRender = true;
        this.mRenderComplete = false;
        GLTextureView.sGLThreadManager.notifyAll();
        while (true)
          if ((!this.mExited) && (this.mPaused))
          {
            boolean bool = this.mRenderComplete;
            if (!bool)
              try
              {
                GLTextureView.sGLThreadManager.wait();
              }
              catch (InterruptedException localInterruptedException)
              {
                Thread.currentThread().interrupt();
              }
          }
      }
    }

    public final void onWindowResize(int paramInt1, int paramInt2)
    {
      while (true)
      {
        synchronized (GLTextureView.sGLThreadManager)
        {
          this.mWidth = paramInt1;
          this.mHeight = paramInt2;
          GLTextureView.access$1002(GLTextureView.this, true);
          this.mRequestRender = true;
          this.mRenderComplete = false;
          GLTextureView.sGLThreadManager.notifyAll();
          if ((this.mExited) || (this.mPaused) || (this.mRenderComplete) || (GLTextureView.this.mGLThread == null))
            break;
          GLThread localGLThread = GLTextureView.this.mGLThread;
          if ((localGLThread.mHaveEglContext) && (localGLThread.mHaveEglSurface))
          {
            boolean bool = localGLThread.readyToDraw();
            if (bool)
            {
              i = 1;
              if (i == 0)
                break;
              try
              {
                GLTextureView.sGLThreadManager.wait();
              }
              catch (InterruptedException localInterruptedException)
              {
                Thread.currentThread().interrupt();
              }
            }
          }
        }
        int i = 0;
      }
    }

    public final void requestExitAndWait()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mShouldExit = true;
        GLTextureView.sGLThreadManager.notifyAll();
        while (true)
        {
          boolean bool = this.mExited;
          if (!bool)
            try
            {
              GLTextureView.sGLThreadManager.wait();
            }
            catch (InterruptedException localInterruptedException)
            {
              Thread.currentThread().interrupt();
            }
        }
      }
    }

    public final void requestReleaseEglContextLocked()
    {
      this.mShouldReleaseEglContext = true;
      GLTextureView.sGLThreadManager.notifyAll();
    }

    public final void requestRender()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mRequestRender = true;
        GLTextureView.sGLThreadManager.notifyAll();
        return;
      }
    }

    // ERROR //
    public final void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: new 247	java/lang/StringBuilder
      //   4: dup
      //   5: ldc 249
      //   7: invokespecial 252	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   10: aload_0
      //   11: invokevirtual 256	com/google/android/apps/plus/views/GLTextureView$GLThread:getId	()J
      //   14: invokevirtual 260	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
      //   17: invokevirtual 264	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   20: invokevirtual 267	com/google/android/apps/plus/views/GLTextureView$GLThread:setName	(Ljava/lang/String;)V
      //   23: aload_0
      //   24: invokespecial 269	com/google/android/apps/plus/views/GLTextureView$GLThread:guardedRun	()V
      //   27: invokestatic 77	com/google/android/apps/plus/views/GLTextureView:access$800	()Lcom/google/android/apps/plus/views/GLTextureView$GLThreadManager;
      //   30: aload_0
      //   31: invokevirtual 272	com/google/android/apps/plus/views/GLTextureView$GLThreadManager:threadExiting	(Lcom/google/android/apps/plus/views/GLTextureView$GLThread;)V
      //   34: return
      //   35: astore_2
      //   36: invokestatic 77	com/google/android/apps/plus/views/GLTextureView:access$800	()Lcom/google/android/apps/plus/views/GLTextureView$GLThreadManager;
      //   39: aload_0
      //   40: invokevirtual 272	com/google/android/apps/plus/views/GLTextureView$GLThreadManager:threadExiting	(Lcom/google/android/apps/plus/views/GLTextureView$GLThread;)V
      //   43: goto -9 -> 34
      //   46: astore_1
      //   47: invokestatic 77	com/google/android/apps/plus/views/GLTextureView:access$800	()Lcom/google/android/apps/plus/views/GLTextureView$GLThreadManager;
      //   50: aload_0
      //   51: invokevirtual 272	com/google/android/apps/plus/views/GLTextureView$GLThreadManager:threadExiting	(Lcom/google/android/apps/plus/views/GLTextureView$GLThread;)V
      //   54: aload_1
      //   55: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   23	27	35	java/lang/InterruptedException
      //   23	27	46	finally
    }

    public final void setRenderMode(int paramInt)
    {
      if ((paramInt < 0) || (paramInt > 1))
        throw new IllegalArgumentException("renderMode");
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mRenderMode = paramInt;
        GLTextureView.sGLThreadManager.notifyAll();
        return;
      }
    }

    public final void surfaceCreated()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mHasSurface = true;
        GLTextureView.sGLThreadManager.notifyAll();
        while (true)
          if (this.mWaitingForSurface)
          {
            boolean bool = this.mExited;
            if (!bool)
              try
              {
                GLTextureView.sGLThreadManager.wait();
              }
              catch (InterruptedException localInterruptedException)
              {
                Thread.currentThread().interrupt();
              }
          }
      }
    }

    public final void surfaceDestroyed()
    {
      synchronized (GLTextureView.sGLThreadManager)
      {
        this.mHasSurface = false;
        GLTextureView.sGLThreadManager.notifyAll();
        while (true)
          if (!this.mWaitingForSurface)
          {
            boolean bool = this.mExited;
            if (!bool)
              try
              {
                GLTextureView.sGLThreadManager.wait();
              }
              catch (InterruptedException localInterruptedException)
              {
                Thread.currentThread().interrupt();
              }
          }
      }
    }
  }

  private static final class GLThreadManager
  {
    private GLTextureView.GLThread mEglOwner;
    private boolean mGLESDriverCheckComplete;
    private int mGLESVersion;
    private boolean mGLESVersionCheckComplete;
    private boolean mLimitedGLESContexts;
    private boolean mMultipleGLESContextsAllowed;

    private void checkGLESVersion()
    {
      if (!this.mGLESVersionCheckComplete)
      {
        this.mGLESVersion = SystemProperties.getInt("ro.opengles.version", 0);
        if (this.mGLESVersion >= 131072)
          this.mMultipleGLESContextsAllowed = true;
        this.mGLESVersionCheckComplete = true;
      }
    }

    public final void checkGLDriver(GL10 paramGL10)
    {
      while (true)
      {
        boolean bool1;
        try
        {
          if (!this.mGLESDriverCheckComplete)
          {
            checkGLESVersion();
            String str = paramGL10.glGetString(7937);
            if (this.mGLESVersion < 131072)
            {
              if (!str.startsWith("Q3Dimension MSM7500 "))
              {
                bool3 = true;
                this.mMultipleGLESContextsAllowed = bool3;
                notifyAll();
              }
            }
            else
            {
              if (!this.mMultipleGLESContextsAllowed)
                continue;
              boolean bool2 = str.startsWith("Adreno");
              bool1 = false;
              if (bool2)
                continue;
              this.mLimitedGLESContexts = bool1;
              this.mGLESDriverCheckComplete = true;
            }
          }
          else
          {
            return;
          }
          boolean bool3 = false;
        }
        finally
        {
        }
      }
    }

    public final void releaseEglContextLocked(GLTextureView.GLThread paramGLThread)
    {
      if (this.mEglOwner == paramGLThread)
        this.mEglOwner = null;
      notifyAll();
    }

    public final boolean shouldReleaseEGLContextWhenPausing()
    {
      try
      {
        boolean bool = this.mLimitedGLESContexts;
        return bool;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final boolean shouldTerminateEGLWhenPausing()
    {
      try
      {
        checkGLESVersion();
        boolean bool1 = this.mMultipleGLESContextsAllowed;
        if (!bool1)
        {
          bool2 = true;
          return bool2;
        }
        boolean bool2 = false;
      }
      finally
      {
      }
    }

    public final void threadExiting(GLTextureView.GLThread paramGLThread)
    {
      try
      {
        GLTextureView.GLThread.access$1202(paramGLThread, true);
        if (this.mEglOwner == paramGLThread)
          this.mEglOwner = null;
        notifyAll();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final boolean tryAcquireEglContextLocked(GLTextureView.GLThread paramGLThread)
    {
      boolean bool = true;
      if ((this.mEglOwner == paramGLThread) || (this.mEglOwner == null))
      {
        this.mEglOwner = paramGLThread;
        notifyAll();
      }
      while (true)
      {
        return bool;
        checkGLESVersion();
        if (!this.mMultipleGLESContextsAllowed)
        {
          if (this.mEglOwner != null)
            this.mEglOwner.requestReleaseEglContextLocked();
          bool = false;
        }
      }
    }
  }

  public static abstract interface GLWrapper
  {
    public abstract GL wrap$40ce139f();
  }

  static final class LogWriter extends Writer
  {
    private StringBuilder mBuilder = new StringBuilder();

    private void flushBuilder()
    {
      if (this.mBuilder.length() > 0)
      {
        Log.v("GLTextureView", this.mBuilder.toString());
        this.mBuilder.delete(0, this.mBuilder.length());
      }
    }

    public final void close()
    {
      flushBuilder();
    }

    public final void flush()
    {
      flushBuilder();
    }

    public final void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      int i = 0;
      if (i < paramInt2)
      {
        char c = paramArrayOfChar[(paramInt1 + i)];
        if (c == '\n')
          flushBuilder();
        while (true)
        {
          i++;
          break;
          this.mBuilder.append(c);
        }
      }
    }
  }

  public static abstract interface Renderer
  {
    public abstract void onDrawFrame$62c01aa1();

    public abstract void onSurfaceChanged$4ccda93f(int paramInt1, int paramInt2);

    public abstract void onSurfaceCreated$4a9c201c();
  }

  private final class SimpleEGLConfigChooser extends GLTextureView.ComponentSizeChooser
  {
    public SimpleEGLConfigChooser(boolean arg2)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.GLTextureView
 * JD-Core Version:    0.6.2
 */