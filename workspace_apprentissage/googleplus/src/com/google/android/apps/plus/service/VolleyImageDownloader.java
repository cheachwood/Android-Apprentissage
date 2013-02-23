package com.google.android.apps.plus.service;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.google.android.apps.plus.content.CachedImageRequest;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsMediaCache;
import com.google.android.apps.plus.content.EsNetworkData;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.util.EsLog;
import java.util.HashSet;
import org.apache.http.HttpConnectionMetrics;

public final class VolleyImageDownloader
{
  private static EsAccount sAccount;
  private static Context sContext;
  private static final Object sImageDownloadTag = new Object();
  private static BasicNetwork sNetwork;
  private static HashSet<CachedImageRequest> sQueuedRequest = new HashSet();
  private static RequestQueue sRequestQueue;

  public static void downloadImage(Context paramContext, EsAccount paramEsAccount, CachedImageRequest paramCachedImageRequest)
  {
    init(paramContext);
    synchronized (sQueuedRequest)
    {
      if (!paramEsAccount.equals(sAccount))
      {
        sRequestQueue.cancelAll(sImageDownloadTag);
        sQueuedRequest.clear();
      }
      sAccount = paramEsAccount;
      if (!sQueuedRequest.contains(paramCachedImageRequest))
      {
        sQueuedRequest.add(paramCachedImageRequest);
        DownloadImageRequest localDownloadImageRequest = new DownloadImageRequest(paramCachedImageRequest, true);
        localDownloadImageRequest.setShouldCache(false);
        localDownloadImageRequest.setTag(sImageDownloadTag);
        sRequestQueue.add(localDownloadImageRequest);
      }
      return;
    }
  }

  private static void init(Context paramContext)
  {
    try
    {
      if (sNetwork == null)
      {
        sContext = paramContext.getApplicationContext();
        sNetwork = new BasicNetwork(new HurlStack())
        {
          public final NetworkResponse performRequest(Request<?> paramAnonymousRequest)
            throws VolleyError
          {
            ((VolleyImageDownloader.DownloadImageRequest)paramAnonymousRequest).getMetrics().onBeginTransaction("VolleyImageDownload");
            Process.setThreadPriority(19);
            return super.performRequest(paramAnonymousRequest);
          }
        };
        RequestQueue localRequestQueue = new RequestQueue(new NoCache(), sNetwork, 2, new NoResponseDelivery((byte)0));
        sRequestQueue = localRequestQueue;
        localRequestQueue.start();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static final class ConnectionMetrics
    implements HttpConnectionMetrics
  {
    long receivedBytes;

    public final Object getMetric(String paramString)
    {
      return null;
    }

    public final long getReceivedBytesCount()
    {
      return this.receivedBytes;
    }

    public final long getRequestCount()
    {
      return 1L;
    }

    public final long getResponseCount()
    {
      return 1L;
    }

    public final long getSentBytesCount()
    {
      return 0L;
    }

    public final void reset()
    {
    }
  }

  private static final class DownloadImageRequest extends Request<byte[]>
  {
    private final VolleyImageDownloader.ConnectionMetrics mConnectionMetrics;
    private CachedImageRequest mImageRequest;
    public final HttpTransactionMetrics mMetrics;
    private boolean mSaveToCache;

    public DownloadImageRequest(CachedImageRequest paramCachedImageRequest, boolean paramBoolean)
    {
      super(null);
      this.mImageRequest = paramCachedImageRequest;
      this.mSaveToCache = paramBoolean;
      this.mConnectionMetrics = new VolleyImageDownloader.ConnectionMetrics((byte)0);
      this.mMetrics = new HttpTransactionMetrics();
      this.mMetrics.setConnectionMetrics(this.mConnectionMetrics);
    }

    public final int compareTo(Request<byte[]> paramRequest)
    {
      Request.Priority localPriority1 = Request.Priority.NORMAL;
      Request.Priority localPriority2 = Request.Priority.NORMAL;
      if (localPriority1 == localPriority2);
      for (int i = paramRequest.getSequence() - getSequence(); ; i = localPriority2.ordinal() - localPriority1.ordinal())
        return i;
    }

    public final CachedImageRequest getImageRequest()
    {
      return this.mImageRequest;
    }

    public final HttpTransactionMetrics getMetrics()
    {
      return this.mMetrics;
    }

    protected final Response<byte[]> parseNetworkResponse(NetworkResponse paramNetworkResponse)
    {
      this.mMetrics.onStartResultProcessing();
      this.mConnectionMetrics.receivedBytes = paramNetworkResponse.data.length;
      Response localResponse;
      try
      {
        if ((this.mImageRequest instanceof MediaImageRequest));
        for (byte[] arrayOfByte = VolleyImageDownloader.access$500((MediaImageRequest)this.mImageRequest, paramNetworkResponse.data); ; arrayOfByte = paramNetworkResponse.data)
        {
          if ((this.mSaveToCache) && (arrayOfByte != null))
            EsMediaCache.insertMedia(VolleyImageDownloader.sContext, this.mImageRequest, arrayOfByte);
          localResponse = Response.success(arrayOfByte, null);
          break;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Log.w("VolleyImageDownloader", "DownloadImageOperation OutOfMemoryError on image bytes: " + paramNetworkResponse.data.length, localOutOfMemoryError);
        localResponse = Response.error(new VolleyError(localOutOfMemoryError));
      }
      return localResponse;
    }
  }

  private static final class NoResponseDelivery
    implements ResponseDelivery
  {
    private static void finishRequest(VolleyImageDownloader.DownloadImageRequest paramDownloadImageRequest, VolleyError paramVolleyError)
    {
      HttpTransactionMetrics localHttpTransactionMetrics = paramDownloadImageRequest.getMetrics();
      localHttpTransactionMetrics.onEndTransaction();
      EsNetworkData.insertData(VolleyImageDownloader.sContext, VolleyImageDownloader.sAccount, localHttpTransactionMetrics, paramVolleyError);
      synchronized (VolleyImageDownloader.sQueuedRequest)
      {
        VolleyImageDownloader.sQueuedRequest.remove(paramDownloadImageRequest.getImageRequest());
        return;
      }
    }

    public final void postError(Request<?> paramRequest, VolleyError paramVolleyError)
    {
      if (EsLog.isLoggable("VolleyImageDownloader", 5))
        Log.w("VolleyImageDownloader", "ERROR: " + paramRequest.getSequence(), paramVolleyError);
      finishRequest((VolleyImageDownloader.DownloadImageRequest)paramRequest, paramVolleyError);
    }

    public final void postResponse(Request<?> paramRequest, Response<?> paramResponse)
    {
      if (EsLog.isLoggable("VolleyImageDownloader", 3))
        Log.d("VolleyImageDownloader", "RESPONSE: " + paramRequest.getSequence());
      finishRequest((VolleyImageDownloader.DownloadImageRequest)paramRequest, null);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.VolleyImageDownloader
 * JD-Core Version:    0.6.2
 */