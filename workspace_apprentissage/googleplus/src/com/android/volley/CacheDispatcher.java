package com.android.volley;

import java.util.concurrent.BlockingQueue;

public final class CacheDispatcher extends Thread
{
  private static final boolean DEBUG = VolleyLog.DEBUG;
  private final Cache mCache;
  private final BlockingQueue<Request> mCacheQueue;
  private final ResponseDelivery mDelivery;
  private final BlockingQueue<Request> mNetworkQueue;
  private volatile boolean mQuit = false;

  public CacheDispatcher(BlockingQueue<Request> paramBlockingQueue1, BlockingQueue<Request> paramBlockingQueue2, Cache paramCache, ResponseDelivery paramResponseDelivery)
  {
    this.mCacheQueue = paramBlockingQueue1;
    this.mNetworkQueue = paramBlockingQueue2;
    this.mCache = paramCache;
    this.mDelivery = paramResponseDelivery;
  }

  public final void quit()
  {
    this.mQuit = true;
    interrupt();
  }

  public final void run()
  {
    if (DEBUG)
      VolleyLog.v("start new dispatcher", new Object[0]);
    while (true)
    {
      Request localRequest;
      try
      {
        localRequest = (Request)this.mCacheQueue.take();
        localRequest.addMarker("cache-queue-take");
        if (!localRequest.isCanceled())
          break label64;
        localRequest.finish("cache-discard-canceled");
        continue;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
      if (this.mQuit)
      {
        return;
        label64: localRequest.getCacheKey();
        localRequest.addMarker("cache-miss");
        this.mNetworkQueue.put(localRequest);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.CacheDispatcher
 * JD-Core Version:    0.6.2
 */