package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.cookie.DateUtils;

public class BasicNetwork
  implements Network
{
  protected static final boolean DEBUG = VolleyLog.DEBUG;
  private static int DEFAULT_POOL_SIZE = 4096;
  private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
  protected final HttpStack mHttpStack;
  protected final ByteArrayPool mPool;

  public BasicNetwork(HttpStack paramHttpStack)
  {
    this(paramHttpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
  }

  public BasicNetwork(HttpStack paramHttpStack, ByteArrayPool paramByteArrayPool)
  {
    this.mHttpStack = paramHttpStack;
    this.mPool = paramByteArrayPool;
  }

  private static void attemptRetryOnException(String paramString, Request<?> paramRequest, VolleyError paramVolleyError)
    throws VolleyError
  {
    RetryPolicy localRetryPolicy = paramRequest.getRetryPolicy();
    int i = paramRequest.getTimeoutMs();
    try
    {
      localRetryPolicy.retry(paramVolleyError);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramString;
      arrayOfObject2[1] = Integer.valueOf(i);
      paramRequest.addMarker(String.format("%s-retry [timeout=%s]", arrayOfObject2));
      return;
    }
    catch (VolleyError localVolleyError)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = Integer.valueOf(i);
      paramRequest.addMarker(String.format("%s-timeout-giveup [timeout=%s]", arrayOfObject1));
      throw localVolleyError;
    }
  }

  private static Map<String, String> convertHeaders(Header[] paramArrayOfHeader)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < paramArrayOfHeader.length; i++)
      localHashMap.put(paramArrayOfHeader[i].getName(), paramArrayOfHeader[i].getValue());
    return localHashMap;
  }

  private byte[] entityToBytes(HttpEntity paramHttpEntity)
    throws IOException, ServerError
  {
    PoolingByteArrayOutputStream localPoolingByteArrayOutputStream = new PoolingByteArrayOutputStream(this.mPool, (int)paramHttpEntity.getContentLength());
    byte[] arrayOfByte1 = null;
    InputStream localInputStream;
    try
    {
      localInputStream = paramHttpEntity.getContent();
      arrayOfByte1 = null;
      if (localInputStream == null)
        throw new ServerError();
    }
    finally
    {
    }
    try
    {
      paramHttpEntity.consumeContent();
      this.mPool.returnBuf(arrayOfByte1);
      localPoolingByteArrayOutputStream.close();
      throw localObject;
      arrayOfByte1 = this.mPool.getBuf(1024);
      while (true)
      {
        int i = localInputStream.read(arrayOfByte1);
        if (i == -1)
          break;
        localPoolingByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
      byte[] arrayOfByte2 = localPoolingByteArrayOutputStream.toByteArray();
      try
      {
        paramHttpEntity.consumeContent();
        this.mPool.returnBuf(arrayOfByte1);
        localPoolingByteArrayOutputStream.close();
        return arrayOfByte2;
      }
      catch (IOException localIOException2)
      {
        while (true)
          VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
      }
    }
    catch (IOException localIOException1)
    {
      while (true)
        VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
    }
  }

  public NetworkResponse performRequest(Request<?> paramRequest)
    throws VolleyError
  {
    long l1 = SystemClock.elapsedRealtime();
    NetworkResponse localNetworkResponse2;
    label319: NetworkResponse localNetworkResponse1;
    while (true)
    {
      HttpResponse localHttpResponse = null;
      byte[] arrayOfByte = null;
      Object localObject1 = new HashMap();
      try
      {
        HashMap localHashMap = new HashMap();
        Cache.Entry localEntry = paramRequest.getCacheEntry();
        localHttpResponse = null;
        arrayOfByte = null;
        if (localEntry != null)
        {
          boolean bool = 0L < 0L;
          localHttpResponse = null;
          arrayOfByte = null;
          if (bool)
            localHashMap.put("If-Modified-Since", DateUtils.formatDate(new Date(0L)));
        }
        localHttpResponse = this.mHttpStack.performRequest(paramRequest, localHashMap);
        StatusLine localStatusLine = localHttpResponse.getStatusLine();
        localObject1 = convertHeaders(localHttpResponse.getAllHeaders());
        int j = localStatusLine.getStatusCode();
        arrayOfByte = null;
        if (j == 304)
        {
          paramRequest.getCacheEntry();
          localNetworkResponse2 = new NetworkResponse(304, null, (Map)localObject1, true);
          break label525;
        }
        arrayOfByte = entityToBytes(localHttpResponse.getEntity());
        long l2 = SystemClock.elapsedRealtime() - l1;
        if ((DEBUG) || (l2 > SLOW_REQUEST_THRESHOLD_MS))
        {
          Object[] arrayOfObject2 = new Object[5];
          arrayOfObject2[0] = paramRequest;
          arrayOfObject2[1] = Long.valueOf(l2);
          if (arrayOfByte != null)
          {
            localObject2 = Integer.valueOf(arrayOfByte.length);
            arrayOfObject2[2] = localObject2;
            arrayOfObject2[3] = Integer.valueOf(localStatusLine.getStatusCode());
            arrayOfObject2[4] = Integer.valueOf(paramRequest.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", arrayOfObject2);
          }
        }
        else
        {
          if (localStatusLine.getStatusCode() == 200)
            break label319;
          throw new IOException();
        }
      }
      catch (SocketTimeoutException localSocketTimeoutException)
      {
        while (true)
        {
          attemptRetryOnException("socket", paramRequest, new TimeoutError());
          break;
          Object localObject2 = "null";
        }
        localNetworkResponse2 = new NetworkResponse(200, arrayOfByte, (Map)localObject1, false);
      }
      catch (ConnectTimeoutException localConnectTimeoutException)
      {
        attemptRetryOnException("connection", paramRequest, new TimeoutError());
      }
      catch (MalformedURLException localMalformedURLException)
      {
        throw new RuntimeException("Bad URL " + paramRequest.getUrl(), localMalformedURLException);
      }
      catch (IOException localIOException)
      {
        if (localHttpResponse != null)
        {
          int i = localHttpResponse.getStatusLine().getStatusCode();
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Integer.valueOf(i);
          arrayOfObject1[1] = paramRequest.getUrl();
          VolleyLog.e("Unexpected response code %d for %s", arrayOfObject1);
          if (arrayOfByte == null)
            break label516;
          localNetworkResponse1 = new NetworkResponse(i, arrayOfByte, (Map)localObject1, false);
          if ((i == 401) || (i == 403))
            attemptRetryOnException("auth", paramRequest, new AuthFailureError(localNetworkResponse1));
        }
        else
        {
          throw new NoConnectionError(localIOException);
        }
      }
    }
    throw new ServerError(localNetworkResponse1);
    label516: throw new NetworkError(null);
    label525: return localNetworkResponse2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.BasicNetwork
 * JD-Core Version:    0.6.2
 */