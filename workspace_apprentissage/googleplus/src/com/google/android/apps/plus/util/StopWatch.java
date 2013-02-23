package com.google.android.apps.plus.util;

public final class StopWatch
{
  private boolean mIsRunning = false;
  private final String mLabel = null;
  private long mStartNanos = 0L;
  private long mStopNanos = 0L;

  public final long getElapsedMsec()
  {
    if (this.mIsRunning);
    for (long l = System.nanoTime() - this.mStartNanos; ; l = 0L - this.mStartNanos)
      return l / 1000000L;
  }

  public final StopWatch start()
  {
    this.mStartNanos = System.nanoTime();
    this.mIsRunning = true;
    return this;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.StopWatch
 * JD-Core Version:    0.6.2
 */