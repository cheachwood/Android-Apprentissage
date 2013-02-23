package com.google.android.apps.plus.iu;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;

public final class MetricsUtils
{
  private static final long LOG_DURATION_LIMIT = SystemProperties.getLong("picasasync.metrics.time", 100L);
  static Metrics sFreeMetrics = null;
  private static final ThreadLocal<ArrayList<Metrics>> sMetricsStack = new ThreadLocal()
  {
  };

  public static int begin(String paramString)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    localArrayList.add(Metrics.obtain(paramString));
    return localArrayList.size();
  }

  public static void end(int paramInt)
  {
    endWithReport(null, paramInt, null);
  }

  public static void endWithReport(Context paramContext, int paramInt, String paramString)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    if ((paramInt > localArrayList.size()) || (paramInt <= 0))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(localArrayList.size());
      arrayOfObject[1] = Integer.valueOf(paramInt);
      throw new IllegalArgumentException(String.format("size: %s, id: %s", arrayOfObject));
    }
    while (paramInt < localArrayList.size())
    {
      Metrics localMetrics2 = (Metrics)localArrayList.remove(-1 + localArrayList.size());
      if (EsLog.isLoggable("MetricsUtils", 5))
        Log.w("MetricsUtils", "WARNING: unclosed metrics: " + localMetrics2.toString());
      if (!localArrayList.isEmpty())
        ((Metrics)localArrayList.get(-1 + localArrayList.size())).merge(localMetrics2);
      localMetrics2.recycle();
    }
    Metrics localMetrics1 = (Metrics)localArrayList.remove(-1 + localArrayList.size());
    localMetrics1.endTimestamp = SystemClock.elapsedRealtime();
    if ((LOG_DURATION_LIMIT >= 0L) && (localMetrics1.endTimestamp - localMetrics1.startTimestamp >= LOG_DURATION_LIMIT) && (EsLog.isLoggable("MetricsUtils", 3)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[").append(localMetrics1.name);
      if (localMetrics1.queryResultCount != 0)
        localStringBuilder.append(" query-result:").append(localMetrics1.queryResultCount);
      if (localMetrics1.updateCount != 0)
        localStringBuilder.append(" update:").append(localMetrics1.updateCount);
      if (localMetrics1.inBytes != 0L)
        localStringBuilder.append(" in:").append(localMetrics1.inBytes);
      if (localMetrics1.outBytes != 0L)
        localStringBuilder.append(" out:").append(localMetrics1.outBytes);
      if (localMetrics1.networkOpDuration > 0L)
        localStringBuilder.append(" net-time:").append(localMetrics1.networkOpDuration);
      if (localMetrics1.networkOpCount > 1)
        localStringBuilder.append(" net-op:").append(localMetrics1.networkOpCount);
      long l = localMetrics1.endTimestamp - localMetrics1.startTimestamp;
      if (l > 0L)
        localStringBuilder.append(" time:").append(l);
      if (paramString != null)
        localStringBuilder.append(" report:" + paramString);
      Log.d("MetricsUtils", ']');
    }
    if (!localArrayList.isEmpty())
      ((Metrics)localArrayList.get(-1 + localArrayList.size())).merge(localMetrics1);
    if ((paramContext != null) && (paramString != null) && (localMetrics1.networkOpCount > 0))
      InstantUploadFacade.broadcastOperationReport(paramContext, paramString, localMetrics1.endTimestamp - localMetrics1.startTimestamp, localMetrics1.networkOpDuration, localMetrics1.networkOpCount, localMetrics1.outBytes, localMetrics1.inBytes);
    localMetrics1.recycle();
  }

  public static void incrementInBytes(long paramLong)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    int i = localArrayList.size();
    if (i > 0)
    {
      Metrics localMetrics = (Metrics)localArrayList.get(i - 1);
      localMetrics.inBytes = (paramLong + localMetrics.inBytes);
    }
  }

  public static void incrementNetworkOpCount(long paramLong)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    int i = localArrayList.size();
    if (i > 0)
    {
      Metrics localMetrics = (Metrics)localArrayList.get(i - 1);
      localMetrics.networkOpCount = ((int)(1L + localMetrics.networkOpCount));
    }
  }

  public static void incrementNetworkOpDuration(long paramLong)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    int i = localArrayList.size();
    if (i > 0)
    {
      Metrics localMetrics = (Metrics)localArrayList.get(i - 1);
      localMetrics.networkOpDuration = (paramLong + localMetrics.networkOpDuration);
    }
  }

  public static void incrementOutBytes(long paramLong)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    int i = localArrayList.size();
    if (i > 0)
    {
      Metrics localMetrics = (Metrics)localArrayList.get(i - 1);
      localMetrics.outBytes = (paramLong + localMetrics.outBytes);
    }
  }

  public static void incrementQueryResultCount(int paramInt)
  {
    ArrayList localArrayList = (ArrayList)sMetricsStack.get();
    int i = localArrayList.size();
    if (i > 0)
    {
      Metrics localMetrics = (Metrics)localArrayList.get(i - 1);
      localMetrics.queryResultCount = (paramInt + localMetrics.queryResultCount);
    }
  }

  private static final class Metrics
  {
    public long endTimestamp;
    public long inBytes;
    public String name;
    public int networkOpCount;
    public long networkOpDuration;
    public Metrics nextFree;
    public long outBytes;
    public int queryResultCount;
    public long startTimestamp;
    public int updateCount;

    public static Metrics obtain(String paramString)
    {
      try
      {
        Metrics localMetrics = MetricsUtils.sFreeMetrics;
        if (localMetrics == null)
          localMetrics = new Metrics();
        while (true)
        {
          localMetrics.name = paramString;
          localMetrics.startTimestamp = SystemClock.elapsedRealtime();
          return localMetrics;
          MetricsUtils.sFreeMetrics = localMetrics.nextFree;
        }
      }
      finally
      {
      }
    }

    private static void recycle(Metrics paramMetrics)
    {
      try
      {
        paramMetrics.nextFree = MetricsUtils.sFreeMetrics;
        MetricsUtils.sFreeMetrics = paramMetrics;
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void merge(Metrics paramMetrics)
    {
      this.queryResultCount += paramMetrics.queryResultCount;
      this.updateCount += paramMetrics.updateCount;
      this.inBytes += paramMetrics.inBytes;
      this.outBytes += paramMetrics.outBytes;
      this.networkOpDuration += paramMetrics.networkOpDuration;
      this.networkOpCount += paramMetrics.networkOpCount;
    }

    public final void recycle()
    {
      this.name = null;
      this.queryResultCount = 0;
      this.updateCount = 0;
      this.inBytes = 0L;
      this.outBytes = 0L;
      this.networkOpDuration = 0L;
      this.networkOpCount = 0;
      recycle(this);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.MetricsUtils
 * JD-Core Version:    0.6.2
 */