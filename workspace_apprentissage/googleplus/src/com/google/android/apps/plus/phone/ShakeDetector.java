package com.google.android.apps.plus.phone;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
import com.google.android.apps.plus.util.Property;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ShakeDetector
  implements SensorEventListener
{
  private static volatile ShakeDetector mShakeDetector;
  private float mCurrentAcceleration;
  private ConcurrentLinkedQueue<ShakeEventListener> mEventListeners = new ConcurrentLinkedQueue();
  private boolean mIsSensorRegistered;
  private SensorManager mSensorManager;
  private long mShakeStartTime;

  private ShakeDetector(Context paramContext)
  {
    this.mSensorManager = ((SensorManager)paramContext.getSystemService("sensor"));
    this.mCurrentAcceleration = 0.0F;
  }

  public static ShakeDetector getInstance(Context paramContext)
  {
    try
    {
      if (!Property.ENABLE_SHAKE_GLOBAL_ACTION.getBoolean())
        mShakeDetector = null;
      while (true)
      {
        ShakeDetector localShakeDetector = mShakeDetector;
        return localShakeDetector;
        if (mShakeDetector == null)
          mShakeDetector = new ShakeDetector(paramContext);
      }
    }
    finally
    {
    }
  }

  public final void addEventListener(ShakeEventListener paramShakeEventListener)
  {
    if (Looper.getMainLooper().getThread() != Thread.currentThread())
      throw new RuntimeException("startCommand must be called on the UI thread");
    this.mEventListeners.add(paramShakeEventListener);
  }

  public final void onAccuracyChanged(Sensor paramSensor, int paramInt)
  {
  }

  public final void onSensorChanged(SensorEvent paramSensorEvent)
  {
    float f1 = paramSensorEvent.values[0];
    float f2 = paramSensorEvent.values[1];
    float f3 = paramSensorEvent.values[2];
    this.mCurrentAcceleration = (0.2F * Math.abs((float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3) - 9.80665F) + 0.8F * this.mCurrentAcceleration);
    if (this.mCurrentAcceleration < 8.0F)
      this.mShakeStartTime = 0L;
    while (true)
    {
      return;
      long l = System.currentTimeMillis();
      if (this.mShakeStartTime == 0L)
      {
        this.mShakeStartTime = l;
      }
      else if (l - this.mShakeStartTime >= 250L)
      {
        stop();
        Iterator localIterator = this.mEventListeners.iterator();
        while (localIterator.hasNext())
          ((ShakeEventListener)localIterator.next()).onShakeDetected();
        start();
      }
    }
  }

  public final void removeEventListener(ShakeEventListener paramShakeEventListener)
  {
    if (Looper.getMainLooper().getThread() != Thread.currentThread())
      throw new RuntimeException("startCommand must be called on the UI thread");
    this.mEventListeners.remove(paramShakeEventListener);
  }

  public final boolean start()
  {
    if (Looper.getMainLooper().getThread() != Thread.currentThread())
      throw new RuntimeException("startCommand must be called on the UI thread");
    if ((this.mSensorManager != null) && (!this.mIsSensorRegistered))
      this.mIsSensorRegistered = this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(1), 3);
    return this.mIsSensorRegistered;
  }

  public final boolean stop()
  {
    if (Looper.getMainLooper().getThread() != Thread.currentThread())
      throw new RuntimeException("startCommand must be called on the UI thread");
    SensorManager localSensorManager = this.mSensorManager;
    boolean bool1 = false;
    if (localSensorManager != null)
    {
      boolean bool2 = this.mIsSensorRegistered;
      bool1 = false;
      if (bool2)
      {
        this.mSensorManager.unregisterListener(this);
        this.mIsSensorRegistered = false;
        this.mCurrentAcceleration = 0.0F;
        bool1 = true;
      }
    }
    return bool1;
  }

  public static abstract interface ShakeEventListener
  {
    public abstract void onShakeDetected();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ShakeDetector
 * JD-Core Version:    0.6.2
 */