package com.google.android.apps.plus.util;

import java.util.ArrayList;
import java.util.HashMap;

public final class ImageLoadingMetrics
{
  private static boolean sImageLoadingMetricsEnabled = false;
  public static ArrayList<ImageRequestMetrics> sMetrics;
  public static HashMap<String, ImageRequestMetrics> sMetricsMap;

  public static boolean areImageLoadingMetricsEnabled()
  {
    return false;
  }

  private static ImageRequestMetrics getRequestMetrics(String paramString)
  {
    try
    {
      if (sMetricsMap == null)
      {
        sMetrics = new ArrayList();
        sMetricsMap = new HashMap();
      }
      ImageRequestMetrics localImageRequestMetrics = (ImageRequestMetrics)sMetricsMap.get(paramString);
      if (localImageRequestMetrics == null)
      {
        localImageRequestMetrics = new ImageRequestMetrics();
        localImageRequestMetrics.url = paramString;
        sMetrics.add(localImageRequestMetrics);
        sMetricsMap.put(paramString, localImageRequestMetrics);
      }
      return localImageRequestMetrics;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void recordImageDelivered(String paramString, int paramInt1, int paramInt2)
  {
    ImageRequestMetrics localImageRequestMetrics = getRequestMetrics(paramString);
    if (localImageRequestMetrics.deliveredTimestamp == 0L)
    {
      localImageRequestMetrics.deliveredTimestamp = System.currentTimeMillis();
      localImageRequestMetrics.compressedByteCount = paramInt1;
      localImageRequestMetrics.uncompressedByteCount = paramInt2;
    }
  }

  public static void recordImageDownloadFinished(String paramString)
  {
    getRequestMetrics(paramString).downloadFinishedTimestamp = System.currentTimeMillis();
  }

  public static void recordImageDownloadQueued(String paramString)
  {
    getRequestMetrics(paramString).downloadQueuedTimestamp = System.currentTimeMillis();
  }

  public static void recordImageDownloadStarted(String paramString)
  {
    getRequestMetrics(paramString).downloadStartedTimestamp = System.currentTimeMillis();
  }

  public static void recordLoadImageRequest(String paramString)
  {
    try
    {
      ImageRequestMetrics localImageRequestMetrics = getRequestMetrics(paramString);
      if ((localImageRequestMetrics.downloadFinishedTimestamp != 0L) && (System.currentTimeMillis() - localImageRequestMetrics.downloadFinishedTimestamp > 1000L))
      {
        sMetricsMap.remove(paramString);
        localImageRequestMetrics = getRequestMetrics(paramString);
      }
      localImageRequestMetrics.requestCount = (1 + localImageRequestMetrics.requestCount);
      if (localImageRequestMetrics.requestTimestamp == 0L)
        localImageRequestMetrics.requestTimestamp = System.currentTimeMillis();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static final class ImageRequestMetrics
  {
    public int compressedByteCount;
    public long deliveredTimestamp;
    public long downloadFinishedTimestamp;
    public long downloadQueuedTimestamp;
    public long downloadStartedTimestamp;
    public int requestCount;
    public long requestTimestamp;
    public int uncompressedByteCount;
    public String url;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.ImageLoadingMetrics
 * JD-Core Version:    0.6.2
 */