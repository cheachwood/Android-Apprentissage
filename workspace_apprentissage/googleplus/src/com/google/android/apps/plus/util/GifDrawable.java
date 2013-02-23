package com.google.android.apps.plus.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.views.Recyclable;

public final class GifDrawable extends Drawable
  implements Animatable, Handler.Callback, Recyclable, Runnable
{
  private static final byte[] NETSCAPE2_0 = "NETSCAPE2.0".getBytes();
  private static Handler sDecoderHandler;
  private static DecoderThread sDecoderThread;
  private static Paint sPaint;
  private static Paint sScalePaint;
  private int[] mActiveColorTable;
  private boolean mAnimationEnabled = true;
  private int mBackgroundColor;
  private int[] mBackup;
  private boolean mBackupSaved;
  private Bitmap mBitmap;
  private byte[] mBlock = new byte[256];
  private int[] mColors;
  private final byte[] mData;
  private int mDisposalMethod = 2;
  private boolean mDone;
  private volatile boolean mError;
  private boolean mFirstFrameReady;
  private int mFrameCount;
  private int mFrameDelay;
  private int mFrameHeight;
  private int mFrameWidth;
  private int mFrameX;
  private int mFrameY;
  private final GifImage mGifImage;
  private final Handler mHandler = new Handler(Looper.getMainLooper(), this);
  private int mHeight;
  private boolean mInterlace;
  private volatile int mIntrinsicHeight;
  private volatile int mIntrinsicWidth;
  private int[] mLocalColorTable;
  private int mLocalColorTableSize;
  private boolean mLocalColorTableUsed;
  private byte[] mPixelStack = new byte[4097];
  private byte[] mPixels;
  private volatile int mPosition;
  private short[] mPrefix = new short[4096];
  private boolean mRecycled;
  private boolean mRunning;
  private boolean mScale;
  private float mScaleFactor;
  private boolean mScheduled;
  private byte[] mSuffix = new byte[4096];
  private boolean mTransparency;
  private int mTransparentColorIndex;
  private int mWidth;

  public GifDrawable(GifImage paramGifImage)
  {
    if (sDecoderThread == null)
    {
      DecoderThread localDecoderThread = new DecoderThread();
      sDecoderThread = localDecoderThread;
      localDecoderThread.start();
      sDecoderHandler = new Handler(sDecoderThread.getLooper(), sDecoderThread);
    }
    if (sPaint == null)
    {
      sPaint = new Paint(2);
      Paint localPaint = new Paint(2);
      sScalePaint = localPaint;
      localPaint.setFilterBitmap(true);
    }
    this.mGifImage = paramGifImage;
    this.mData = paramGifImage.getData();
    this.mPosition = this.mGifImage.mHeaderSize;
    int i = paramGifImage.getWidth();
    this.mIntrinsicWidth = i;
    this.mFrameWidth = i;
    int j = paramGifImage.getHeight();
    this.mIntrinsicHeight = j;
    this.mFrameHeight = j;
    this.mBackgroundColor = this.mGifImage.mBackgroundColor;
    this.mError = this.mGifImage.mError;
    if (!this.mError)
      try
      {
        int k = this.mIntrinsicWidth;
        int m = this.mIntrinsicHeight;
        int n = EsApplication.sMemoryClass;
        int i1 = 0;
        if (n < 64)
          i1 = 1;
        if (i1 != 0);
        for (Bitmap.Config localConfig = Bitmap.Config.ARGB_4444; ; localConfig = Bitmap.Config.ARGB_8888)
        {
          this.mBitmap = Bitmap.createBitmap(k, m, localConfig);
          int i2 = this.mIntrinsicWidth * this.mIntrinsicHeight;
          this.mColors = new int[i2];
          this.mPixels = new byte[i2];
          this.mWidth = this.mIntrinsicHeight;
          this.mHeight = this.mIntrinsicHeight;
          sDecoderHandler.sendMessage(sDecoderHandler.obtainMessage(0, this));
          break;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        this.mError = true;
      }
  }

  private void backupFrame()
  {
    if (this.mBackupSaved);
    while (true)
    {
      return;
      if (this.mBackup == null)
        this.mBackup = null;
      try
      {
        this.mBackup = new int[this.mColors.length];
        if (this.mBackup == null)
          continue;
        System.arraycopy(this.mColors, 0, this.mBackup, 0, this.mColors.length);
        this.mBackupSaved = true;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        while (true)
          Log.e("GifDrawable", "GifDrawable.backupFrame threw an OOME", localOutOfMemoryError);
      }
    }
  }

  private int readBlock()
  {
    byte[] arrayOfByte = this.mData;
    int i = this.mPosition;
    this.mPosition = (i + 1);
    int j = 0xFF & arrayOfByte[i];
    if (j > 0)
    {
      System.arraycopy(this.mData, this.mPosition, this.mBlock, 0, j);
      this.mPosition = (j + this.mPosition);
    }
    return j;
  }

  private int readShort()
  {
    byte[] arrayOfByte1 = this.mData;
    int i = this.mPosition;
    this.mPosition = (i + 1);
    int j = 0xFF & arrayOfByte1[i];
    byte[] arrayOfByte2 = this.mData;
    int k = this.mPosition;
    this.mPosition = (k + 1);
    return j | (0xFF & arrayOfByte2[k]) << 8;
  }

  private void reset()
  {
    sDecoderHandler.sendMessage(sDecoderHandler.obtainMessage(7, this));
    this.mFrameCount = 0;
    this.mScheduled = false;
  }

  private void skip()
  {
    int j;
    do
    {
      byte[] arrayOfByte = this.mData;
      int i = this.mPosition;
      this.mPosition = (i + 1);
      j = 0xFF & arrayOfByte[i];
      this.mPosition = (j + this.mPosition);
    }
    while (j > 0);
  }

  public final void draw(Canvas paramCanvas)
  {
    if ((this.mError) || (this.mWidth == 0) || (this.mHeight == 0) || (this.mRecycled) || (!this.mFirstFrameReady));
    while (true)
    {
      return;
      if (this.mScale)
      {
        paramCanvas.save();
        paramCanvas.scale(this.mScaleFactor, this.mScaleFactor, 0.0F, 0.0F);
        paramCanvas.drawBitmap(this.mBitmap, 0.0F, 0.0F, sScalePaint);
        paramCanvas.restore();
      }
      while (true)
      {
        if (!this.mRunning)
          break label126;
        if (this.mScheduled)
          break;
        scheduleSelf(this, SystemClock.uptimeMillis() + this.mFrameDelay);
        break;
        paramCanvas.drawBitmap(this.mBitmap, 0.0F, 0.0F, sPaint);
      }
      label126: if (!this.mDone)
        start();
      else
        unscheduleSelf(this);
    }
  }

  public final int getIntrinsicHeight()
  {
    return this.mIntrinsicHeight;
  }

  public final int getIntrinsicWidth()
  {
    return this.mIntrinsicWidth;
  }

  public final int getOpacity()
  {
    return 0;
  }

  public final boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    boolean bool = false;
    switch (i)
    {
    case 2:
    default:
    case 1:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      return bool;
      this.mError = true;
      bool = true;
      continue;
      this.mDone = true;
      bool = true;
      continue;
      this.mScheduled = false;
      invalidateSelf();
      bool = true;
      continue;
      if (this.mBitmap != null)
      {
        this.mBitmap.setPixels(this.mColors, 0, this.mIntrinsicWidth, 0, 0, this.mIntrinsicWidth, this.mIntrinsicHeight);
        this.mFirstFrameReady = true;
        invalidateSelf();
      }
      bool = true;
      continue;
      this.mFrameCount = (1 + this.mFrameCount);
      bool = true;
    }
  }

  public final boolean isRunning()
  {
    return this.mRunning;
  }

  public final boolean isValid()
  {
    if ((!this.mError) && (this.mFirstFrameReady));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.mWidth = paramRect.width();
    this.mHeight = paramRect.height();
    if ((this.mWidth != this.mIntrinsicWidth) && (this.mHeight != this.mIntrinsicHeight));
    for (boolean bool = true; ; bool = false)
    {
      this.mScale = bool;
      if (this.mScale)
        this.mScaleFactor = Math.max(this.mWidth / this.mIntrinsicWidth, this.mHeight / this.mIntrinsicHeight);
      reset();
      return;
    }
  }

  public final void onRecycle()
  {
    if (this.mBitmap != null)
      this.mBitmap.recycle();
    this.mBitmap = null;
    this.mRecycled = true;
  }

  public final void run()
  {
    if (this.mRecycled);
    while (true)
    {
      return;
      if (this.mDone)
      {
        if (this.mFrameCount > 1)
        {
          this.mDone = false;
          reset();
        }
      }
      else
      {
        sDecoderHandler.sendMessage(sDecoderHandler.obtainMessage(2, this));
        continue;
      }
      stop();
    }
  }

  public final void scheduleSelf(Runnable paramRunnable, long paramLong)
  {
    if (this.mAnimationEnabled)
    {
      super.scheduleSelf(paramRunnable, paramLong);
      this.mScheduled = true;
    }
  }

  public final void setAlpha(int paramInt)
  {
  }

  public final void setAnimationEnabled(boolean paramBoolean)
  {
    if (this.mAnimationEnabled == paramBoolean);
    while (true)
    {
      return;
      this.mAnimationEnabled = paramBoolean;
      if (this.mAnimationEnabled)
        start();
      else
        stop();
    }
  }

  public final void setColorFilter(ColorFilter paramColorFilter)
  {
  }

  public final boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    if (paramBoolean1)
      if ((bool) || (paramBoolean2))
        start();
    while (true)
    {
      return bool;
      stop();
    }
  }

  public final void start()
  {
    if (!isRunning())
    {
      this.mRunning = true;
      run();
    }
  }

  public final void stop()
  {
    if (isRunning())
      unscheduleSelf(this);
  }

  public final void unscheduleSelf(Runnable paramRunnable)
  {
    super.unscheduleSelf(paramRunnable);
    this.mRunning = false;
  }

  private static final class DecoderThread extends HandlerThread
    implements Handler.Callback
  {
    public DecoderThread()
    {
      super();
    }

    public final boolean handleMessage(Message paramMessage)
    {
      int i = 1;
      GifDrawable localGifDrawable = (GifDrawable)paramMessage.obj;
      switch (paramMessage.what)
      {
      default:
        i = 0;
      case 0:
      case 2:
      case 7:
      }
      while (true)
      {
        return i;
        try
        {
          GifDrawable.access$000(localGifDrawable);
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2)
        {
          localGifDrawable.mHandler.sendEmptyMessage(i);
        }
        continue;
        try
        {
          GifDrawable.access$000(localGifDrawable);
          localGifDrawable.mHandler.sendEmptyMessage(4);
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1)
        {
          while (true)
            localGifDrawable.mHandler.sendEmptyMessage(3);
        }
        GifDrawable.access$202(localGifDrawable, localGifDrawable.mGifImage.mHeaderSize);
        GifDrawable.access$402(localGifDrawable, false);
        GifDrawable.access$502(localGifDrawable, 0);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.GifDrawable
 * JD-Core Version:    0.6.2
 */