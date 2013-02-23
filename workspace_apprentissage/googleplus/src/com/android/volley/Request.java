package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import java.util.Collections;
import java.util.Map;

public abstract class Request<T>
  implements Comparable<Request<T>>
{
  private Cache.Entry mCacheEntry;
  private boolean mCanceled;
  private final Response.ErrorListener mErrorListener;
  private final VolleyLog.MarkerLog mEventLog;
  private long mRequestBirthTime;
  private RequestQueue mRequestQueue;
  private boolean mResponseDelivered;
  private RetryPolicy mRetryPolicy;
  private Integer mSequence;
  private boolean mShouldCache;
  private Object mTag;
  private final String mUrl;

  public Request(String paramString, Response.ErrorListener paramErrorListener)
  {
    if (VolleyLog.MarkerLog.ENABLED);
    for (VolleyLog.MarkerLog localMarkerLog = new VolleyLog.MarkerLog(); ; localMarkerLog = null)
    {
      this.mEventLog = localMarkerLog;
      this.mShouldCache = true;
      this.mCanceled = false;
      this.mResponseDelivered = false;
      this.mRequestBirthTime = 0L;
      this.mCacheEntry = null;
      this.mUrl = paramString;
      this.mErrorListener = null;
      this.mRetryPolicy = new DefaultRetryPolicy();
      return;
    }
  }

  public static Map<String, String> getHeaders()
    throws AuthFailureError
  {
    return Collections.emptyMap();
  }

  protected static VolleyError parseNetworkError(VolleyError paramVolleyError)
  {
    return paramVolleyError;
  }

  public final void addMarker(String paramString)
  {
    if (VolleyLog.MarkerLog.ENABLED)
      this.mEventLog.add(paramString, Thread.currentThread().getId());
    while (true)
    {
      return;
      if (this.mRequestBirthTime == 0L)
        this.mRequestBirthTime = SystemClock.elapsedRealtime();
    }
  }

  public final void cancel()
  {
    this.mCanceled = true;
  }

  public int compareTo(Request<T> paramRequest)
  {
    Priority localPriority1 = Priority.NORMAL;
    Priority localPriority2 = Priority.NORMAL;
    if (localPriority1 == localPriority2);
    for (int i = this.mSequence.intValue() - paramRequest.mSequence.intValue(); ; i = localPriority2.ordinal() - localPriority1.ordinal())
      return i;
  }

  public void deliverError(VolleyError paramVolleyError)
  {
    if (this.mErrorListener != null);
  }

  protected abstract void deliverResponse(T paramT);

  final void finish(final String paramString)
  {
    if (this.mRequestQueue != null)
      this.mRequestQueue.finish(this);
    final long l2;
    if (VolleyLog.MarkerLog.ENABLED)
    {
      l2 = Thread.currentThread().getId();
      if (Looper.myLooper() != Looper.getMainLooper())
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public final void run()
          {
            Request.this.mEventLog.add(paramString, l2);
            Request.this.mEventLog.finish(toString());
          }
        });
    }
    while (true)
    {
      return;
      this.mEventLog.add(paramString, l2);
      this.mEventLog.finish(toString());
      continue;
      long l1 = SystemClock.elapsedRealtime() - this.mRequestBirthTime;
      if (l1 >= 3000L)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Long.valueOf(l1);
        arrayOfObject[1] = toString();
        VolleyLog.d("%d ms: %s", arrayOfObject);
      }
    }
  }

  public final Cache.Entry getCacheEntry()
  {
    return this.mCacheEntry;
  }

  public final String getCacheKey()
  {
    return getUrl();
  }

  public final byte[] getPostBody()
    throws AuthFailureError
  {
    return null;
  }

  public final String getPostBodyContentType()
  {
    return "application/x-www-form-urlencoded; charset=" + "UTF-8";
  }

  public final RetryPolicy getRetryPolicy()
  {
    return this.mRetryPolicy;
  }

  public final int getSequence()
  {
    if (this.mSequence == null)
      throw new IllegalStateException("getSequence called before setSequence");
    return this.mSequence.intValue();
  }

  public final Object getTag()
  {
    return this.mTag;
  }

  public final int getTimeoutMs()
  {
    return this.mRetryPolicy.getCurrentTimeout();
  }

  public String getUrl()
  {
    return this.mUrl;
  }

  public final boolean hasHadResponseDelivered()
  {
    return this.mResponseDelivered;
  }

  public final boolean isCanceled()
  {
    return this.mCanceled;
  }

  public final void markDelivered()
  {
    this.mResponseDelivered = true;
  }

  protected abstract Response<T> parseNetworkResponse(NetworkResponse paramNetworkResponse);

  public final void setRequestQueue(RequestQueue paramRequestQueue)
  {
    this.mRequestQueue = paramRequestQueue;
  }

  public final void setSequence(int paramInt)
  {
    this.mSequence = Integer.valueOf(paramInt);
  }

  public final void setShouldCache(boolean paramBoolean)
  {
    this.mShouldCache = false;
  }

  public final void setTag(Object paramObject)
  {
    this.mTag = paramObject;
  }

  public final boolean shouldCache()
  {
    return this.mShouldCache;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.mCanceled);
    for (String str = "[X] "; ; str = "[ ] ")
      return str + getUrl() + " " + Priority.NORMAL + " " + this.mSequence;
  }

  public static enum Priority
  {
    static
    {
      HIGH = new Priority("HIGH", 2);
      IMMEDIATE = new Priority("IMMEDIATE", 3);
      Priority[] arrayOfPriority = new Priority[4];
      arrayOfPriority[0] = LOW;
      arrayOfPriority[1] = NORMAL;
      arrayOfPriority[2] = HIGH;
      arrayOfPriority[3] = IMMEDIATE;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.Request
 * JD-Core Version:    0.6.2
 */