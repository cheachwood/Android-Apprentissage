package com.google.android.apps.plus.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class ServiceThread extends Thread
{
  private IntentProcessor mIntentProcessor;
  private final Handler mMainHandler;
  private final Runnable mProcessQueueRunnable = new Runnable()
  {
    public final void run()
    {
      while (true)
      {
        Intent localIntent = (Intent)ServiceThread.this.mQueue.poll();
        if (localIntent == null)
          break;
        try
        {
          if (ServiceThread.this.mIntentProcessor != null)
            ServiceThread.this.mIntentProcessor.processIntent(localIntent);
        }
        catch (Throwable localThrowable)
        {
          Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), localThrowable);
        }
      }
    }
  };
  private final Queue<Intent> mQueue;
  private Handler mThreadHandler;

  public ServiceThread(Handler paramHandler, String paramString, IntentProcessor paramIntentProcessor)
  {
    this.mMainHandler = paramHandler;
    setName(paramString + this);
    this.mQueue = new LinkedBlockingQueue();
    this.mIntentProcessor = paramIntentProcessor;
  }

  public final void put(Intent paramIntent)
  {
    this.mQueue.add(paramIntent);
    if (this.mThreadHandler != null)
      this.mThreadHandler.post(this.mProcessQueueRunnable);
  }

  public final void quit()
  {
    if (this.mThreadHandler != null)
      this.mThreadHandler.getLooper().quit();
    if (this.mQueue.size() > 0)
      this.mQueue.clear();
  }

  public final void run()
  {
    Looper.prepare();
    this.mThreadHandler = new Handler();
    this.mMainHandler.post(new Runnable()
    {
      public final void run()
      {
        ServiceThread.this.mThreadHandler.post(ServiceThread.this.mProcessQueueRunnable);
      }
    });
    Looper.loop();
    if (this.mIntentProcessor != null)
      this.mIntentProcessor.onServiceThreadEnd();
  }

  public static abstract interface IntentProcessor
  {
    public abstract void onServiceThreadEnd();

    public abstract void processIntent(Intent paramIntent);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ServiceThread
 * JD-Core Version:    0.6.2
 */