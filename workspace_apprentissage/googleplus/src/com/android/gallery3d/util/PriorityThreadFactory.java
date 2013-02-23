package com.android.gallery3d.util;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class PriorityThreadFactory
  implements ThreadFactory
{
  private final String mName;
  private final AtomicInteger mNumber = new AtomicInteger();
  private final int mPriority;

  public PriorityThreadFactory(String paramString, int paramInt)
  {
    this.mName = paramString;
    this.mPriority = 10;
  }

  public final Thread newThread(Runnable paramRunnable)
  {
    return new Thread(paramRunnable, this.mName + '-' + this.mNumber.getAndIncrement())
    {
      public final void run()
      {
        Process.setThreadPriority(PriorityThreadFactory.this.mPriority);
        super.run();
      }
    };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.util.PriorityThreadFactory
 * JD-Core Version:    0.6.2
 */