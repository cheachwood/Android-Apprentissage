package com.google.android.apps.plus.network;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.apache.http.HttpConnectionMetrics;

public final class HttpTransactionMetrics
{
  private long mBaseReceivedBytes;
  private long mBaseRequestCount;
  private long mBaseSentBytes;
  private HttpConnectionMetrics mConnectionMetrics;
  private final HashMap<String, HttpTransactionMetricsHolder> mMap = new HashMap();
  private long mProcessingStartMillis;
  private HttpTransactionMetricsHolder mTransaction;
  private long mTransactionStartMillis;

  public final void accumulateFrom(HttpTransactionMetrics paramHttpTransactionMetrics)
  {
    if (this.mTransaction == null)
      onBeginTransaction(paramHttpTransactionMetrics.getName());
    HttpTransactionMetricsHolder.access$314(this.mTransaction, paramHttpTransactionMetrics.getDuration());
    HttpTransactionMetricsHolder.access$214(this.mTransaction, paramHttpTransactionMetrics.getProcessingDuration());
    HttpTransactionMetricsHolder.access$414(this.mTransaction, paramHttpTransactionMetrics.getRequestCount());
    HttpTransactionMetricsHolder.access$514(this.mTransaction, paramHttpTransactionMetrics.getReceivedBytes());
    HttpTransactionMetricsHolder.access$614(this.mTransaction, paramHttpTransactionMetrics.getSentBytes());
  }

  public final long getDuration()
  {
    ArrayList localArrayList = new ArrayList(this.mMap.keySet());
    long l = 0L;
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      l += ((HttpTransactionMetricsHolder)this.mMap.get(str)).duration;
    }
    return l;
  }

  public final String getName()
  {
    if (!this.mMap.isEmpty());
    for (String str = (String)this.mMap.keySet().iterator().next(); ; str = "Unknown")
      return str;
  }

  public final long getProcessingDuration()
  {
    ArrayList localArrayList = new ArrayList(this.mMap.keySet());
    long l = 0L;
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      l += ((HttpTransactionMetricsHolder)this.mMap.get(str)).processingDuration;
    }
    return l;
  }

  public final long getReceivedBytes()
  {
    ArrayList localArrayList = new ArrayList(this.mMap.keySet());
    long l = 0L;
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      l += ((HttpTransactionMetricsHolder)this.mMap.get(str)).receivedBytes;
    }
    return l;
  }

  public final long getRequestCount()
  {
    ArrayList localArrayList = new ArrayList(this.mMap.keySet());
    long l = 0L;
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      l += ((HttpTransactionMetricsHolder)this.mMap.get(str)).requestCount;
    }
    return l;
  }

  public final long getSentBytes()
  {
    ArrayList localArrayList = new ArrayList(this.mMap.keySet());
    long l = 0L;
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      l += ((HttpTransactionMetricsHolder)this.mMap.get(str)).sentBytes;
    }
    return l;
  }

  public final void log(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList(this.mMap.keySet());
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Log.i(paramString1, paramString2 + this.mMap.get(str));
    }
  }

  public final void onBeginTransaction(String paramString)
  {
    this.mTransaction = ((HttpTransactionMetricsHolder)this.mMap.get(paramString));
    if (this.mTransaction == null)
    {
      this.mTransaction = new HttpTransactionMetricsHolder((byte)0);
      HttpTransactionMetricsHolder.access$102(this.mTransaction, paramString);
      this.mMap.put(paramString, this.mTransaction);
    }
    this.mTransactionStartMillis = System.currentTimeMillis();
    this.mProcessingStartMillis = 0L;
  }

  public final void onEndResultProcessing()
  {
    if (this.mProcessingStartMillis != 0L)
    {
      HttpTransactionMetricsHolder.access$214(this.mTransaction, System.currentTimeMillis() - this.mProcessingStartMillis);
      this.mProcessingStartMillis = 0L;
    }
  }

  public final void onEndTransaction()
  {
    onEndResultProcessing();
    HttpTransactionMetricsHolder.access$314(this.mTransaction, System.currentTimeMillis() - this.mTransactionStartMillis);
    if (this.mConnectionMetrics != null)
    {
      HttpTransactionMetricsHolder.access$414(this.mTransaction, this.mConnectionMetrics.getRequestCount() - 0L);
      HttpTransactionMetricsHolder.access$514(this.mTransaction, this.mConnectionMetrics.getReceivedBytesCount() - 0L);
      HttpTransactionMetricsHolder.access$614(this.mTransaction, this.mConnectionMetrics.getSentBytesCount() - 0L);
    }
    this.mConnectionMetrics = null;
  }

  public final void onEndTransaction(long paramLong1, long paramLong2)
  {
    HttpTransactionMetricsHolder.access$302(this.mTransaction, paramLong1);
    HttpTransactionMetricsHolder.access$202(this.mTransaction, paramLong2);
    if (this.mConnectionMetrics != null)
    {
      HttpTransactionMetricsHolder.access$414(this.mTransaction, this.mConnectionMetrics.getRequestCount() - 0L);
      HttpTransactionMetricsHolder.access$514(this.mTransaction, this.mConnectionMetrics.getReceivedBytesCount() - 0L);
      HttpTransactionMetricsHolder.access$614(this.mTransaction, this.mConnectionMetrics.getSentBytesCount() - 0L);
    }
    this.mConnectionMetrics = null;
  }

  public final void onStartResultProcessing()
  {
    this.mProcessingStartMillis = System.currentTimeMillis();
  }

  public final void setConnectionMetrics(HttpConnectionMetrics paramHttpConnectionMetrics)
  {
    this.mConnectionMetrics = paramHttpConnectionMetrics;
    this.mBaseRequestCount = 0L;
    this.mBaseReceivedBytes = 0L;
    this.mBaseSentBytes = 0L;
  }

  private static final class HttpTransactionMetricsHolder
  {
    private long duration;
    private String name;
    private long processingDuration;
    private long receivedBytes;
    private long requestCount;
    private long sentBytes;

    public final String toString()
    {
      return "[" + this.name + "], duration: " + this.duration + "ms, network: " + (this.duration - this.processingDuration) + "ms, processing: " + this.processingDuration + "ms, requests: " + this.requestCount + ", sent: " + this.sentBytes + ", received: " + this.receivedBytes;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.HttpTransactionMetrics
 * JD-Core Version:    0.6.2
 */