package com.android.volley;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class RequestQueue
{
  private final Cache mCache;
  private CacheDispatcher mCacheDispatcher;
  private final PriorityBlockingQueue<Request> mCacheQueue = new PriorityBlockingQueue();
  private final Set<Request> mCurrentRequests = new HashSet();
  private final ResponseDelivery mDelivery;
  private NetworkDispatcher[] mDispatchers;
  private final Network mNetwork;
  private final PriorityBlockingQueue<Request> mNetworkQueue = new PriorityBlockingQueue();
  private AtomicInteger mSequenceGenerator = new AtomicInteger();
  private final Map<String, Queue<Request>> mWaitingRequests = new HashMap();

  public RequestQueue(Cache paramCache, Network paramNetwork, int paramInt, ResponseDelivery paramResponseDelivery)
  {
    this.mCache = paramCache;
    this.mNetwork = paramNetwork;
    this.mDispatchers = new NetworkDispatcher[2];
    this.mDelivery = paramResponseDelivery;
  }

  public final Request add(Request paramRequest)
  {
    paramRequest.setRequestQueue(this);
    synchronized (this.mCurrentRequests)
    {
      this.mCurrentRequests.add(paramRequest);
      paramRequest.setSequence(this.mSequenceGenerator.incrementAndGet());
      paramRequest.addMarker("add-to-queue");
      if (!paramRequest.shouldCache())
      {
        this.mNetworkQueue.add(paramRequest);
        return paramRequest;
      }
    }
    while (true)
    {
      String str;
      synchronized (this.mWaitingRequests)
      {
        str = paramRequest.getCacheKey();
        if (this.mWaitingRequests.containsKey(str))
        {
          Object localObject3 = (Queue)this.mWaitingRequests.get(str);
          if (localObject3 == null)
            localObject3 = new LinkedList();
          ((Queue)localObject3).add(paramRequest);
          this.mWaitingRequests.put(str, localObject3);
          if (VolleyLog.DEBUG)
            VolleyLog.v("Request for cacheKey=%s is in flight, putting on hold.", new Object[] { str });
        }
      }
      this.mWaitingRequests.put(str, null);
      this.mCacheQueue.add(paramRequest);
    }
  }

  public final void cancelAll(RequestFilter paramRequestFilter)
  {
    synchronized (this.mCurrentRequests)
    {
      Iterator localIterator = this.mCurrentRequests.iterator();
      while (localIterator.hasNext())
      {
        Request localRequest = (Request)localIterator.next();
        if (paramRequestFilter.apply(localRequest))
          localRequest.cancel();
      }
    }
  }

  public final void cancelAll(final Object paramObject)
  {
    if (paramObject == null)
      throw new IllegalArgumentException("Cannot cancelAll with a null tag");
    cancelAll(new RequestFilter()
    {
      public final boolean apply(Request<?> paramAnonymousRequest)
      {
        if (paramAnonymousRequest.getTag() == paramObject);
        for (boolean bool = true; ; bool = false)
          return bool;
      }
    });
  }

  final void finish(Request paramRequest)
  {
    synchronized (this.mCurrentRequests)
    {
      this.mCurrentRequests.remove(paramRequest);
      if (!paramRequest.shouldCache());
    }
    synchronized (this.mWaitingRequests)
    {
      String str = paramRequest.getCacheKey();
      Queue localQueue = (Queue)this.mWaitingRequests.remove(str);
      if (localQueue != null)
      {
        if (VolleyLog.DEBUG)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(localQueue.size());
          arrayOfObject[1] = str;
          VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", arrayOfObject);
        }
        this.mCacheQueue.addAll(localQueue);
      }
      return;
      localObject1 = finally;
      throw localObject1;
    }
  }

  public final void start()
  {
    if (this.mCacheDispatcher != null)
      this.mCacheDispatcher.quit();
    for (int i = 0; i < this.mDispatchers.length; i++)
      if (this.mDispatchers[i] != null)
        this.mDispatchers[i].quit();
    this.mCacheDispatcher = new CacheDispatcher(this.mCacheQueue, this.mNetworkQueue, this.mCache, this.mDelivery);
    this.mCacheDispatcher.start();
    for (int j = 0; j < this.mDispatchers.length; j++)
    {
      NetworkDispatcher localNetworkDispatcher = new NetworkDispatcher(this.mNetworkQueue, this.mNetwork, this.mCache, this.mDelivery);
      this.mDispatchers[j] = localNetworkDispatcher;
      localNetworkDispatcher.start();
    }
  }

  public static abstract interface RequestFilter
  {
    public abstract boolean apply(Request<?> paramRequest);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.RequestQueue
 * JD-Core Version:    0.6.2
 */