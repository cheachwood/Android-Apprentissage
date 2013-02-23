package com.google.android.apps.plus.iu;

import java.util.concurrent.TimeUnit;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

final class HttpUtils
{
  private static final ClientConnectionManager sConnectionManager;
  private static final HttpParams sHttpClientParams = localBasicHttpParams2;

  static
  {
    BasicHttpParams localBasicHttpParams1 = new BasicHttpParams();
    ConnManagerParams.setTimeout(localBasicHttpParams1, 20000L);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    sConnectionManager = new MetricsTrackingConnectionManager(localBasicHttpParams1, localSchemeRegistry);
    BasicHttpParams localBasicHttpParams2 = new BasicHttpParams();
    HttpConnectionParams.setStaleCheckingEnabled(localBasicHttpParams2, false);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams2, 20000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams2, 20000);
    HttpClientParams.setRedirecting(localBasicHttpParams2, true);
    HttpProtocolParams.setUserAgent(localBasicHttpParams2, "InstantUploadSync/1.0");
  }

  public static HttpClient createHttpClient(String paramString)
  {
    HttpParams localHttpParams = sHttpClientParams.copy();
    HttpProtocolParams.setUserAgent(localHttpParams, paramString);
    return new DefaultHttpClient(sConnectionManager, localHttpParams);
  }

  private static final class MetricsTrackingConnectionManager extends ThreadSafeClientConnManager
  {
    public MetricsTrackingConnectionManager(HttpParams paramHttpParams, SchemeRegistry paramSchemeRegistry)
    {
      super(paramSchemeRegistry);
    }

    public final void releaseConnection(ManagedClientConnection paramManagedClientConnection, long paramLong, TimeUnit paramTimeUnit)
    {
      HttpConnectionMetrics localHttpConnectionMetrics = paramManagedClientConnection.getMetrics();
      if (localHttpConnectionMetrics != null)
      {
        MetricsUtils.incrementInBytes(localHttpConnectionMetrics.getReceivedBytesCount());
        MetricsUtils.incrementOutBytes(localHttpConnectionMetrics.getSentBytesCount());
      }
      super.releaseConnection(paramManagedClientConnection, paramLong, paramTimeUnit);
    }

    public final ClientConnectionRequest requestConnection(HttpRoute paramHttpRoute, Object paramObject)
    {
      return new ClientConnectionRequest()
      {
        public final void abortRequest()
        {
          this.val$r.abortRequest();
        }

        public final ManagedClientConnection getConnection(long paramAnonymousLong, TimeUnit paramAnonymousTimeUnit)
          throws InterruptedException, ConnectionPoolTimeoutException
        {
          ManagedClientConnection localManagedClientConnection = this.val$r.getConnection(paramAnonymousLong, paramAnonymousTimeUnit);
          HttpConnectionMetrics localHttpConnectionMetrics = localManagedClientConnection.getMetrics();
          if (localHttpConnectionMetrics != null)
            localHttpConnectionMetrics.reset();
          return localManagedClientConnection;
        }
      };
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.HttpUtils
 * JD-Core Version:    0.6.2
 */