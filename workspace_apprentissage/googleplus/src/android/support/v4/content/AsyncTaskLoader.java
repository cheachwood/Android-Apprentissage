package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

public abstract class AsyncTaskLoader<D> extends Loader<D>
{
  volatile AsyncTaskLoader<D>.LoadTask mCancellingTask;
  long mLastLoadCompleteTime = -10000L;
  volatile AsyncTaskLoader<D>.LoadTask mTask;

  public AsyncTaskLoader(Context paramContext)
  {
    super(paramContext);
  }

  public boolean cancelLoad()
  {
    LoadTask localLoadTask = this.mTask;
    boolean bool = false;
    if (localLoadTask != null)
    {
      if (this.mCancellingTask == null)
        break label51;
      if (this.mTask.waiting)
      {
        this.mTask.waiting = false;
        null.removeCallbacks(this.mTask);
      }
      this.mTask = null;
    }
    while (true)
    {
      return bool;
      label51: if (this.mTask.waiting)
      {
        this.mTask.waiting = false;
        null.removeCallbacks(this.mTask);
        this.mTask = null;
        bool = false;
      }
      else
      {
        bool = this.mTask.cancel(false);
        if (bool)
          this.mCancellingTask = this.mTask;
        this.mTask = null;
      }
    }
  }

  final void dispatchOnCancelled(AsyncTaskLoader<D>.LoadTask paramAsyncTaskLoader, D paramD)
  {
    onCanceled(paramD);
    if (this.mCancellingTask == paramAsyncTaskLoader)
    {
      this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
      this.mCancellingTask = null;
      executePendingTask();
    }
  }

  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    if (this.mTask != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTask=");
      paramPrintWriter.print(this.mTask);
      paramPrintWriter.print(" waiting=");
      paramPrintWriter.println(this.mTask.waiting);
    }
    if (this.mCancellingTask != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mCancellingTask=");
      paramPrintWriter.print(this.mCancellingTask);
      paramPrintWriter.print(" waiting=");
      paramPrintWriter.println(this.mCancellingTask.waiting);
    }
    if (0L != 0L)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mUpdateThrottle=");
      TimeUtils.formatDuration(0L, paramPrintWriter);
      paramPrintWriter.print(" mLastLoadCompleteTime=");
      TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), paramPrintWriter);
      paramPrintWriter.println();
    }
  }

  final void executePendingTask()
  {
    if ((this.mCancellingTask == null) && (this.mTask != null))
    {
      if (this.mTask.waiting)
      {
        this.mTask.waiting = false;
        null.removeCallbacks(this.mTask);
      }
      if ((0L <= 0L) || (SystemClock.uptimeMillis() >= 0L + this.mLastLoadCompleteTime))
        break label83;
      this.mTask.waiting = true;
      null.postAtTime(this.mTask, 0L + this.mLastLoadCompleteTime);
    }
    while (true)
    {
      return;
      label83: this.mTask.executeOnExecutor(ModernAsyncTask.THREAD_POOL_EXECUTOR, null);
    }
  }

  public abstract D loadInBackground();

  public void onCanceled(D paramD)
  {
  }

  protected final void onForceLoad()
  {
    super.onForceLoad();
    cancelLoad();
    this.mTask = new LoadTask();
    executePendingTask();
  }

  final class LoadTask extends ModernAsyncTask<Void, Void, D>
    implements Runnable
  {
    private CountDownLatch done = new CountDownLatch(1);
    D result;
    boolean waiting;

    LoadTask()
    {
    }

    protected final void onCancelled()
    {
      try
      {
        AsyncTaskLoader.this.dispatchOnCancelled(this, this.result);
        return;
      }
      finally
      {
        this.done.countDown();
      }
    }

    protected final void onPostExecute(D paramD)
    {
      while (true)
      {
        AsyncTaskLoader localAsyncTaskLoader;
        try
        {
          localAsyncTaskLoader = AsyncTaskLoader.this;
          if (localAsyncTaskLoader.mTask != this)
          {
            localAsyncTaskLoader.dispatchOnCancelled(this, paramD);
            return;
          }
          if (localAsyncTaskLoader.mAbandoned)
          {
            localAsyncTaskLoader.onCanceled(paramD);
            continue;
          }
        }
        finally
        {
          this.done.countDown();
        }
        localAsyncTaskLoader.mLastLoadCompleteTime = SystemClock.uptimeMillis();
        localAsyncTaskLoader.mTask = null;
        localAsyncTaskLoader.deliverResult(paramD);
      }
    }

    public final void run()
    {
      this.waiting = false;
      AsyncTaskLoader.this.executePendingTask();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.content.AsyncTaskLoader
 * JD-Core Version:    0.6.2
 */