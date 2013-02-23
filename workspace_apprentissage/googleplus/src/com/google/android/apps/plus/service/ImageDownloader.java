package com.google.android.apps.plus.service;

import android.content.Context;
import android.os.Process;
import com.google.android.apps.plus.api.DownloadImageOperation;
import com.google.android.apps.plus.content.CachedImageRequest;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.util.ImageLoadingMetrics;
import com.google.android.apps.plus.util.Property;
import java.util.HashSet;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageDownloader
{
  private static EsAccount sAccount;
  private static Context sContext;
  private static Stack<CachedImageRequest> sDownloadRequests = new Stack();
  private static ExecutorService sExecutorService;
  private static DownloadProcessor sProcessor = new DownloadProcessor((byte)0);
  private static HashSet<CachedImageRequest> sQueuedRequest = new HashSet();

  public static void downloadImage(Context paramContext, EsAccount paramEsAccount, CachedImageRequest paramCachedImageRequest)
  {
    if (Property.ENABLE_VOLLEY_IMAGE_DOWNLOAD.getBoolean())
      VolleyImageDownloader.downloadImage(paramContext, paramEsAccount, paramCachedImageRequest);
    while (true)
    {
      return;
      init(paramContext);
      synchronized (sDownloadRequests)
      {
        if (!paramEsAccount.equals(sAccount))
          sDownloadRequests.clear();
        sAccount = paramEsAccount;
        if (!sQueuedRequest.contains(paramCachedImageRequest))
        {
          if (ImageLoadingMetrics.areImageLoadingMetricsEnabled())
            ImageLoadingMetrics.recordImageDownloadQueued(paramCachedImageRequest.getUriForLogging());
          sQueuedRequest.add(paramCachedImageRequest);
          sDownloadRequests.push(paramCachedImageRequest);
        }
        sExecutorService.execute(sProcessor);
      }
    }
  }

  private static void init(Context paramContext)
  {
    try
    {
      if (sExecutorService == null)
      {
        sContext = paramContext.getApplicationContext();
        sExecutorService = Executors.newFixedThreadPool(2);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static final class DownloadProcessor
    implements Runnable
  {
    public final void run()
    {
      Process.setThreadPriority(19);
      synchronized (ImageDownloader.sDownloadRequests)
      {
        while (true)
        {
          if (ImageDownloader.sDownloadRequests.isEmpty())
            return;
          CachedImageRequest localCachedImageRequest = (CachedImageRequest)ImageDownloader.sDownloadRequests.pop();
          DownloadImageOperation localDownloadImageOperation = new DownloadImageOperation(ImageDownloader.sContext, ImageDownloader.sAccount, localCachedImageRequest, null, null);
          localDownloadImageOperation.start();
          synchronized (ImageDownloader.sDownloadRequests)
          {
            ImageDownloader.sQueuedRequest.remove(localCachedImageRequest);
          }
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ImageDownloader
 * JD-Core Version:    0.6.2
 */