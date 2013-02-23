package com.android.gallery3d.util;

import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ThreadPool
{
  public static final JobContext JOB_CONTEXT_STUB = new JobContextStub((byte)0);
  ResourceCounter mCpuCounter = new ResourceCounter(2);
  private final Executor mExecutor = new ThreadPoolExecutor(4, 8, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new PriorityThreadFactory("thread-pool", 10));
  ResourceCounter mNetworkCounter = new ResourceCounter(2);

  public final <T> Future<T> submit(Job<T> paramJob)
  {
    Worker localWorker = new Worker(paramJob, null);
    this.mExecutor.execute(localWorker);
    return localWorker;
  }

  public static abstract interface Job<T>
  {
    public abstract T run(ThreadPool.JobContext paramJobContext);
  }

  public static abstract interface JobContext
  {
    public abstract boolean setMode(int paramInt);
  }

  private static final class JobContextStub
    implements ThreadPool.JobContext
  {
    public final boolean setMode(int paramInt)
    {
      return true;
    }
  }

  private static final class ResourceCounter
  {
    public int value = 2;

    public ResourceCounter(int paramInt)
    {
    }
  }

  private final class Worker<T>
    implements Future<T>, ThreadPool.JobContext, Runnable
  {
    private boolean mIsDone;
    private ThreadPool.Job<T> mJob;
    private FutureListener<T> mListener;
    private int mMode;
    private T mResult;
    private ThreadPool.ResourceCounter mWaitOnResource;

    public Worker(FutureListener<T> arg2)
    {
      Object localObject1;
      this.mJob = localObject1;
      Object localObject2;
      this.mListener = localObject2;
    }

    // ERROR //
    private boolean acquireResource(ThreadPool.ResourceCounter paramResourceCounter)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: aload_1
      //   4: putfield 45	com/android/gallery3d/util/ThreadPool$Worker:mWaitOnResource	Lcom/android/gallery3d/util/ThreadPool$ResourceCounter;
      //   7: aload_0
      //   8: monitorexit
      //   9: aload_1
      //   10: monitorenter
      //   11: aload_1
      //   12: getfield 50	com/android/gallery3d/util/ThreadPool$ResourceCounter:value	I
      //   15: ifle +31 -> 46
      //   18: aload_1
      //   19: iconst_m1
      //   20: aload_1
      //   21: getfield 50	com/android/gallery3d/util/ThreadPool$ResourceCounter:value	I
      //   24: iadd
      //   25: putfield 50	com/android/gallery3d/util/ThreadPool$ResourceCounter:value	I
      //   28: aload_1
      //   29: monitorexit
      //   30: aload_0
      //   31: monitorenter
      //   32: aload_0
      //   33: aconst_null
      //   34: putfield 45	com/android/gallery3d/util/ThreadPool$Worker:mWaitOnResource	Lcom/android/gallery3d/util/ThreadPool$ResourceCounter;
      //   37: aload_0
      //   38: monitorexit
      //   39: iconst_1
      //   40: ireturn
      //   41: astore_2
      //   42: aload_0
      //   43: monitorexit
      //   44: aload_2
      //   45: athrow
      //   46: aload_1
      //   47: invokevirtual 53	java/lang/Object:wait	()V
      //   50: aload_1
      //   51: monitorexit
      //   52: goto -52 -> 0
      //   55: astore_3
      //   56: aload_1
      //   57: monitorexit
      //   58: aload_3
      //   59: athrow
      //   60: astore 5
      //   62: aload_0
      //   63: monitorexit
      //   64: aload 5
      //   66: athrow
      //   67: astore 4
      //   69: goto -19 -> 50
      //
      // Exception table:
      //   from	to	target	type
      //   2	9	41	finally
      //   11	30	55	finally
      //   46	50	55	finally
      //   50	52	55	finally
      //   32	39	60	finally
      //   46	50	67	java/lang/InterruptedException
    }

    private ThreadPool.ResourceCounter modeToCounter(int paramInt)
    {
      ThreadPool.ResourceCounter localResourceCounter;
      if (paramInt == 1)
        localResourceCounter = ThreadPool.this.mCpuCounter;
      while (true)
      {
        return localResourceCounter;
        if (paramInt == 2)
          localResourceCounter = ThreadPool.this.mNetworkCounter;
        else
          localResourceCounter = null;
      }
    }

    public final void run()
    {
      boolean bool = setMode(1);
      Object localObject1 = null;
      if (bool);
      try
      {
        Object localObject3 = this.mJob.run(this);
        localObject1 = localObject3;
      }
      catch (Throwable localThrowable)
      {
        try
        {
          setMode(0);
          this.mResult = localObject1;
          this.mIsDone = true;
          notifyAll();
          return;
          localThrowable = localThrowable;
          Log.w("Worker", "Exception in running a job", localThrowable);
          localObject1 = null;
        }
        finally
        {
        }
      }
    }

    public final boolean setMode(int paramInt)
    {
      ThreadPool.ResourceCounter localResourceCounter1 = modeToCounter(this.mMode);
      if (localResourceCounter1 != null);
      while (true)
      {
        try
        {
          localResourceCounter1.value = (1 + localResourceCounter1.value);
          localResourceCounter1.notifyAll();
          this.mMode = 0;
          ThreadPool.ResourceCounter localResourceCounter2 = modeToCounter(paramInt);
          if (localResourceCounter2 == null)
            break label76;
          boolean bool2 = acquireResource(localResourceCounter2);
          bool1 = false;
          if (!bool2)
            return bool1;
        }
        finally
        {
        }
        this.mMode = paramInt;
        label76: boolean bool1 = true;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.util.ThreadPool
 * JD-Core Version:    0.6.2
 */