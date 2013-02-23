package com.google.android.apps.plus.network;

import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

final class HttpTransaction
  implements HttpRequestInterceptor, HttpResponseInterceptor
{
  private static final HttpParams sHttpParams;
  private static final SchemeRegistry sSupportedSchemes = new SchemeRegistry();
  private boolean mAborted;
  private final HttpRequestBase mHttpMethod;
  private final HttpTransactionListener mListener;
  private HttpTransactionMetrics mMetrics;

  static
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    sHttpParams = localBasicHttpParams;
    localBasicHttpParams.setParameter("http.socket.timeout", Integer.valueOf(90000));
    sHttpParams.setParameter("http.connection.timeout", Integer.valueOf(3000));
    sSupportedSchemes.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    sSupportedSchemes.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
  }

  public HttpTransaction(String paramString1, String paramString2, HttpRequestConfiguration paramHttpRequestConfiguration, HttpTransactionListener paramHttpTransactionListener)
    throws IOException
  {
    if (paramString1.equals("GET"))
      this.mHttpMethod = new HttpGet(paramString2);
    while (paramHttpTransactionListener == null)
    {
      throw new NetworkException("The listener cannot be null");
      if (paramString1.equals("POST"))
        this.mHttpMethod = new HttpPost(paramString2);
      else if (paramString1.equals("DELETE"))
        this.mHttpMethod = new HttpDelete(paramString2);
      else
        throw new NetworkException("Unsupported method: " + paramString1);
    }
    this.mListener = paramHttpTransactionListener;
    paramHttpRequestConfiguration.addHeaders(this.mHttpMethod);
  }

  public HttpTransaction(String paramString1, String paramString2, HttpRequestConfiguration paramHttpRequestConfiguration, HttpEntity paramHttpEntity, HttpTransactionListener paramHttpTransactionListener)
    throws IOException
  {
    if (paramString1.equals("POST"))
      this.mHttpMethod = new HttpPost(paramString2);
    while (paramHttpTransactionListener == null)
    {
      throw new NetworkException("The listener cannot be null");
      if (paramString1.equals("PUT"))
        this.mHttpMethod = new HttpPut(paramString2);
      else
        throw new NetworkException("Unsupported method: " + paramString1);
    }
    this.mListener = paramHttpTransactionListener;
    paramHttpRequestConfiguration.addHeaders(this.mHttpMethod);
    if (paramHttpEntity != null)
      ((HttpPost)this.mHttpMethod).setEntity(new MyInputStreamEntity(this, paramHttpEntity));
  }

  private void processCookies(HeaderIterator paramHeaderIterator, CookieSpec paramCookieSpec, CookieOrigin paramCookieOrigin)
  {
    while (paramHeaderIterator.hasNext())
    {
      Header localHeader = paramHeaderIterator.nextHeader();
      try
      {
        Iterator localIterator = paramCookieSpec.parse(localHeader, paramCookieOrigin).iterator();
        while (localIterator.hasNext())
        {
          Cookie localCookie = (Cookie)localIterator.next();
          this.mListener.onHttpCookie(localCookie);
        }
      }
      catch (MalformedCookieException localMalformedCookieException)
      {
        Log.e("HttpTransaction", "Malformed cookie", localMalformedCookieException);
      }
    }
  }

  public final void abort()
  {
    if (this.mAborted);
    while (true)
    {
      return;
      this.mAborted = true;
      if (this.mHttpMethod != null)
      {
        StrictMode.ThreadPolicy localThreadPolicy;
        if (Build.VERSION.SDK_INT >= 9)
        {
          localThreadPolicy = StrictMode.getThreadPolicy();
          StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        }
        try
        {
          this.mHttpMethod.abort();
          StrictMode.setThreadPolicy(localThreadPolicy);
        }
        finally
        {
          StrictMode.setThreadPolicy(localThreadPolicy);
        }
      }
    }
  }

  // ERROR //
  public final Exception execute()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 201	com/google/android/apps/plus/network/HttpTransaction:mAborted	Z
    //   6: istore 7
    //   8: aconst_null
    //   9: astore_1
    //   10: iload 7
    //   12: ifeq +29 -> 41
    //   15: new 96	com/google/android/apps/plus/network/NetworkException
    //   18: dup
    //   19: ldc 245
    //   21: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   24: astore_3
    //   25: aload_0
    //   26: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   29: ifnull +10 -> 39
    //   32: aload_0
    //   33: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   36: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   39: aload_3
    //   40: areturn
    //   41: aconst_null
    //   42: astore 8
    //   44: aconst_null
    //   45: astore 9
    //   47: iconst_0
    //   48: istore 10
    //   50: aconst_null
    //   51: astore 11
    //   53: iload 10
    //   55: iconst_2
    //   56: if_icmpge +1258 -> 1314
    //   59: new 254	org/apache/http/impl/client/DefaultHttpClient
    //   62: dup
    //   63: new 256	org/apache/http/impl/conn/SingleClientConnManager
    //   66: dup
    //   67: getstatic 34	com/google/android/apps/plus/network/HttpTransaction:sHttpParams	Lorg/apache/http/params/HttpParams;
    //   70: getstatic 29	com/google/android/apps/plus/network/HttpTransaction:sSupportedSchemes	Lorg/apache/http/conn/scheme/SchemeRegistry;
    //   73: invokespecial 259	org/apache/http/impl/conn/SingleClientConnManager:<init>	(Lorg/apache/http/params/HttpParams;Lorg/apache/http/conn/scheme/SchemeRegistry;)V
    //   76: getstatic 34	com/google/android/apps/plus/network/HttpTransaction:sHttpParams	Lorg/apache/http/params/HttpParams;
    //   79: invokespecial 262	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;Lorg/apache/http/params/HttpParams;)V
    //   82: astore_1
    //   83: aload_1
    //   84: ldc_w 264
    //   87: invokevirtual 268	org/apache/http/impl/client/DefaultHttpClient:removeRequestInterceptorByClass	(Ljava/lang/Class;)V
    //   90: aload_1
    //   91: ldc_w 270
    //   94: invokevirtual 273	org/apache/http/impl/client/DefaultHttpClient:removeResponseInterceptorByClass	(Ljava/lang/Class;)V
    //   97: aload_1
    //   98: aload_0
    //   99: invokevirtual 277	org/apache/http/impl/client/DefaultHttpClient:addRequestInterceptor	(Lorg/apache/http/HttpRequestInterceptor;)V
    //   102: aload_1
    //   103: aload_0
    //   104: invokevirtual 281	org/apache/http/impl/client/DefaultHttpClient:addResponseInterceptor	(Lorg/apache/http/HttpResponseInterceptor;)V
    //   107: aload_1
    //   108: aload_0
    //   109: getfield 94	com/google/android/apps/plus/network/HttpTransaction:mHttpMethod	Lorg/apache/http/client/methods/HttpRequestBase;
    //   112: invokeinterface 286 2 0
    //   117: astore 9
    //   119: aload_0
    //   120: getfield 201	com/google/android/apps/plus/network/HttpTransaction:mAborted	Z
    //   123: istore 42
    //   125: aconst_null
    //   126: astore 8
    //   128: iload 42
    //   130: ifeq +137 -> 267
    //   133: new 96	com/google/android/apps/plus/network/NetworkException
    //   136: dup
    //   137: ldc 245
    //   139: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   142: astore_3
    //   143: aload_0
    //   144: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   147: ifnull +10 -> 157
    //   150: aload_0
    //   151: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   154: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   157: aload_1
    //   158: ifnull -119 -> 39
    //   161: aload_1
    //   162: invokeinterface 290 1 0
    //   167: invokeinterface 295 1 0
    //   172: goto -133 -> 39
    //   175: astore 41
    //   177: aload 41
    //   179: astore 8
    //   181: ldc 190
    //   183: iconst_3
    //   184: invokestatic 301	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   187: ifeq +30 -> 217
    //   190: ldc 190
    //   192: new 111	java/lang/StringBuilder
    //   195: dup
    //   196: ldc_w 303
    //   199: invokespecial 114	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   202: aload 8
    //   204: invokevirtual 306	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   207: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   213: invokestatic 310	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   216: pop
    //   217: aload_1
    //   218: invokeinterface 290 1 0
    //   223: invokeinterface 295 1 0
    //   228: iinc 10 1
    //   231: aload_1
    //   232: astore 11
    //   234: goto -181 -> 53
    //   237: astore 35
    //   239: aload 35
    //   241: astore 8
    //   243: ldc 190
    //   245: new 111	java/lang/StringBuilder
    //   248: dup
    //   249: ldc_w 312
    //   252: invokespecial 114	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   255: aload 35
    //   257: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   260: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   263: invokestatic 318	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   266: pop
    //   267: aload 8
    //   269: ifnull +56 -> 325
    //   272: aload 8
    //   274: athrow
    //   275: astore_3
    //   276: aload_0
    //   277: getfield 201	com/google/android/apps/plus/network/HttpTransaction:mAborted	Z
    //   280: ifeq +690 -> 970
    //   283: new 96	com/google/android/apps/plus/network/NetworkException
    //   286: dup
    //   287: ldc 245
    //   289: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   292: astore_3
    //   293: aload_0
    //   294: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   297: ifnull +10 -> 307
    //   300: aload_0
    //   301: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   304: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   307: aload_1
    //   308: ifnull -269 -> 39
    //   311: aload_1
    //   312: invokeinterface 290 1 0
    //   317: invokeinterface 295 1 0
    //   322: goto -283 -> 39
    //   325: aload 9
    //   327: invokeinterface 324 1 0
    //   332: invokeinterface 330 1 0
    //   337: istore 12
    //   339: aload 9
    //   341: invokeinterface 324 1 0
    //   346: invokeinterface 333 1 0
    //   351: astore 13
    //   353: aload_0
    //   354: getfield 201	com/google/android/apps/plus/network/HttpTransaction:mAborted	Z
    //   357: ifeq +45 -> 402
    //   360: new 96	com/google/android/apps/plus/network/NetworkException
    //   363: dup
    //   364: ldc 245
    //   366: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   369: astore_3
    //   370: aload_0
    //   371: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   374: ifnull +10 -> 384
    //   377: aload_0
    //   378: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   381: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   384: aload_1
    //   385: ifnull -346 -> 39
    //   388: aload_1
    //   389: invokeinterface 290 1 0
    //   394: invokeinterface 295 1 0
    //   399: goto -360 -> 39
    //   402: aconst_null
    //   403: astore 14
    //   405: iload 12
    //   407: sipush 200
    //   410: if_icmpeq +69 -> 479
    //   413: ldc 190
    //   415: iconst_3
    //   416: invokestatic 301	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   419: ifeq +60 -> 479
    //   422: ldc 190
    //   424: new 111	java/lang/StringBuilder
    //   427: dup
    //   428: ldc_w 335
    //   431: invokespecial 114	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   434: iload 12
    //   436: invokevirtual 338	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   439: ldc_w 340
    //   442: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   445: aload 13
    //   447: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   450: ldc_w 342
    //   453: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: aload_0
    //   457: getfield 94	com/google/android/apps/plus/network/HttpTransaction:mHttpMethod	Lorg/apache/http/client/methods/HttpRequestBase;
    //   460: invokevirtual 346	org/apache/http/client/methods/HttpRequestBase:getURI	()Ljava/net/URI;
    //   463: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   466: ldc_w 348
    //   469: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   472: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   475: invokestatic 310	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   478: pop
    //   479: aload 9
    //   481: invokeinterface 352 1 0
    //   486: astore 18
    //   488: aload 9
    //   490: invokeinterface 356 1 0
    //   495: astore 19
    //   497: aload 18
    //   499: invokeinterface 361 1 0
    //   504: ifnull +268 -> 772
    //   507: aload 18
    //   509: invokeinterface 361 1 0
    //   514: invokeinterface 366 1 0
    //   519: astore 20
    //   521: aload 18
    //   523: invokeinterface 369 1 0
    //   528: invokeinterface 366 1 0
    //   533: astore 21
    //   535: aload 21
    //   537: bipush 59
    //   539: invokevirtual 373	java/lang/String:indexOf	(I)I
    //   542: istore 22
    //   544: iload 22
    //   546: iconst_m1
    //   547: if_icmpeq +13 -> 560
    //   550: aload 21
    //   552: iconst_0
    //   553: iload 22
    //   555: invokevirtual 377	java/lang/String:substring	(II)Ljava/lang/String;
    //   558: astore 21
    //   560: aload 18
    //   562: invokeinterface 381 1 0
    //   567: l2i
    //   568: istore 23
    //   570: ldc 190
    //   572: iconst_3
    //   573: invokestatic 301	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   576: ifeq +49 -> 625
    //   579: ldc 190
    //   581: new 111	java/lang/StringBuilder
    //   584: dup
    //   585: ldc_w 383
    //   588: invokespecial 114	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   591: aload 20
    //   593: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: ldc_w 385
    //   599: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   602: aload 21
    //   604: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   607: ldc_w 387
    //   610: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   613: iload 23
    //   615: invokevirtual 338	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   618: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   621: invokestatic 310	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   624: pop
    //   625: aconst_null
    //   626: astore 24
    //   628: aload 18
    //   630: invokeinterface 391 1 0
    //   635: astore 27
    //   637: aload 20
    //   639: ifnull +668 -> 1307
    //   642: aload 20
    //   644: ldc_w 393
    //   647: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   650: ifeq +657 -> 1307
    //   653: new 395	java/util/zip/GZIPInputStream
    //   656: dup
    //   657: aload 27
    //   659: invokespecial 398	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   662: astore 28
    //   664: iconst_m1
    //   665: istore 23
    //   667: iload 12
    //   669: sipush 200
    //   672: if_icmpne +135 -> 807
    //   675: aload_0
    //   676: getfield 124	com/google/android/apps/plus/network/HttpTransaction:mListener	Lcom/google/android/apps/plus/network/HttpTransaction$HttpTransactionListener;
    //   679: aload 28
    //   681: aload 21
    //   683: iload 23
    //   685: aload 19
    //   687: invokeinterface 402 5 0
    //   692: aload 28
    //   694: invokevirtual 407	java/io/InputStream:close	()V
    //   697: iload 12
    //   699: sipush 200
    //   702: if_icmpeq +21 -> 723
    //   705: aload 14
    //   707: ifnonnull +16 -> 723
    //   710: new 231	org/apache/http/client/HttpResponseException
    //   713: dup
    //   714: iload 12
    //   716: aload 13
    //   718: invokespecial 410	org/apache/http/client/HttpResponseException:<init>	(ILjava/lang/String;)V
    //   721: astore 14
    //   723: aload_0
    //   724: getfield 201	com/google/android/apps/plus/network/HttpTransaction:mAborted	Z
    //   727: ifeq +194 -> 921
    //   730: new 96	com/google/android/apps/plus/network/NetworkException
    //   733: dup
    //   734: ldc 245
    //   736: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   739: astore_3
    //   740: aload_0
    //   741: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   744: ifnull +10 -> 754
    //   747: aload_0
    //   748: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   751: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   754: aload_1
    //   755: ifnull -716 -> 39
    //   758: aload_1
    //   759: invokeinterface 290 1 0
    //   764: invokeinterface 295 1 0
    //   769: goto -730 -> 39
    //   772: aconst_null
    //   773: astore 20
    //   775: goto -254 -> 521
    //   778: astore 25
    //   780: aload 24
    //   782: invokevirtual 407	java/io/InputStream:close	()V
    //   785: new 96	com/google/android/apps/plus/network/NetworkException
    //   788: dup
    //   789: aload 25
    //   791: invokevirtual 411	java/io/IOException:getMessage	()Ljava/lang/String;
    //   794: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   797: athrow
    //   798: astore 17
    //   800: aload 17
    //   802: astore 14
    //   804: goto -107 -> 697
    //   807: aload_0
    //   808: getfield 124	com/google/android/apps/plus/network/HttpTransaction:mListener	Lcom/google/android/apps/plus/network/HttpTransaction$HttpTransactionListener;
    //   811: aload 28
    //   813: aload 21
    //   815: iload 23
    //   817: aload 19
    //   819: iload 12
    //   821: invokeinterface 415 6 0
    //   826: goto -134 -> 692
    //   829: astore 29
    //   831: aload 28
    //   833: invokevirtual 407	java/io/InputStream:close	()V
    //   836: aload 29
    //   838: athrow
    //   839: astore 15
    //   841: ldc 190
    //   843: new 111	java/lang/StringBuilder
    //   846: dup
    //   847: ldc_w 417
    //   850: invokespecial 114	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   853: aload 15
    //   855: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   858: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   861: invokestatic 419	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   864: pop
    //   865: aconst_null
    //   866: astore 14
    //   868: goto -171 -> 697
    //   871: astore_3
    //   872: aload_0
    //   873: getfield 201	com/google/android/apps/plus/network/HttpTransaction:mAborted	Z
    //   876: ifeq +212 -> 1088
    //   879: new 96	com/google/android/apps/plus/network/NetworkException
    //   882: dup
    //   883: ldc 245
    //   885: invokespecial 99	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   888: astore_3
    //   889: aload_0
    //   890: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   893: ifnull +10 -> 903
    //   896: aload_0
    //   897: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   900: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   903: aload_1
    //   904: ifnull -865 -> 39
    //   907: aload_1
    //   908: invokeinterface 290 1 0
    //   913: invokeinterface 295 1 0
    //   918: goto -879 -> 39
    //   921: aload_0
    //   922: getfield 124	com/google/android/apps/plus/network/HttpTransaction:mListener	Lcom/google/android/apps/plus/network/HttpTransaction$HttpTransactionListener;
    //   925: iload 12
    //   927: aload 13
    //   929: aload 14
    //   931: invokeinterface 423 4 0
    //   936: aload_0
    //   937: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   940: ifnull +10 -> 950
    //   943: aload_0
    //   944: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   947: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   950: aload_1
    //   951: ifnull +14 -> 965
    //   954: aload_1
    //   955: invokeinterface 290 1 0
    //   960: invokeinterface 295 1 0
    //   965: aconst_null
    //   966: astore_3
    //   967: goto -928 -> 39
    //   970: ldc 190
    //   972: iconst_3
    //   973: invokestatic 301	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   976: ifeq +62 -> 1038
    //   979: aload_3
    //   980: invokevirtual 426	org/apache/http/client/HttpResponseException:printStackTrace	()V
    //   983: ldc 190
    //   985: new 111	java/lang/StringBuilder
    //   988: dup
    //   989: ldc_w 428
    //   992: invokespecial 114	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   995: aload_3
    //   996: invokevirtual 429	org/apache/http/client/HttpResponseException:getStatusCode	()I
    //   999: invokevirtual 338	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1002: aload_3
    //   1003: invokevirtual 430	org/apache/http/client/HttpResponseException:getMessage	()Ljava/lang/String;
    //   1006: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1009: ldc_w 342
    //   1012: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1015: aload_0
    //   1016: getfield 94	com/google/android/apps/plus/network/HttpTransaction:mHttpMethod	Lorg/apache/http/client/methods/HttpRequestBase;
    //   1019: invokevirtual 346	org/apache/http/client/methods/HttpRequestBase:getURI	()Ljava/net/URI;
    //   1022: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1025: ldc_w 348
    //   1028: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1031: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1034: invokestatic 310	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1037: pop
    //   1038: aload_0
    //   1039: getfield 124	com/google/android/apps/plus/network/HttpTransaction:mListener	Lcom/google/android/apps/plus/network/HttpTransaction$HttpTransactionListener;
    //   1042: aload_3
    //   1043: invokevirtual 429	org/apache/http/client/HttpResponseException:getStatusCode	()I
    //   1046: aload_3
    //   1047: invokevirtual 430	org/apache/http/client/HttpResponseException:getMessage	()Ljava/lang/String;
    //   1050: aload_3
    //   1051: invokeinterface 423 4 0
    //   1056: aload_0
    //   1057: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   1060: ifnull +10 -> 1070
    //   1063: aload_0
    //   1064: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   1067: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   1070: aload_1
    //   1071: ifnull -1032 -> 39
    //   1074: aload_1
    //   1075: invokeinterface 290 1 0
    //   1080: invokeinterface 295 1 0
    //   1085: goto -1046 -> 39
    //   1088: aload_3
    //   1089: instanceof 432
    //   1092: ifeq +57 -> 1149
    //   1095: ldc 190
    //   1097: ldc_w 434
    //   1100: aload_3
    //   1101: invokestatic 198	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   1104: pop
    //   1105: aload_0
    //   1106: getfield 124	com/google/android/apps/plus/network/HttpTransaction:mListener	Lcom/google/android/apps/plus/network/HttpTransaction$HttpTransactionListener;
    //   1109: iconst_0
    //   1110: aconst_null
    //   1111: aload_3
    //   1112: invokeinterface 423 4 0
    //   1117: aload_0
    //   1118: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   1121: ifnull +10 -> 1131
    //   1124: aload_0
    //   1125: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   1128: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   1131: aload_1
    //   1132: ifnull -1093 -> 39
    //   1135: aload_1
    //   1136: invokeinterface 290 1 0
    //   1141: invokeinterface 295 1 0
    //   1146: goto -1107 -> 39
    //   1149: ldc 190
    //   1151: iconst_3
    //   1152: invokestatic 301	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1155: ifeq -50 -> 1105
    //   1158: aload_3
    //   1159: invokevirtual 435	java/lang/Exception:printStackTrace	()V
    //   1162: ldc 190
    //   1164: new 111	java/lang/StringBuilder
    //   1167: dup
    //   1168: invokespecial 436	java/lang/StringBuilder:<init>	()V
    //   1171: aload_3
    //   1172: invokevirtual 440	java/lang/Object:getClass	()Ljava/lang/Class;
    //   1175: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1178: ldc_w 442
    //   1181: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1184: aload_3
    //   1185: invokevirtual 306	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   1188: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1191: ldc_w 342
    //   1194: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1197: aload_0
    //   1198: getfield 94	com/google/android/apps/plus/network/HttpTransaction:mHttpMethod	Lorg/apache/http/client/methods/HttpRequestBase;
    //   1201: invokevirtual 346	org/apache/http/client/methods/HttpRequestBase:getURI	()Ljava/net/URI;
    //   1204: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1207: ldc_w 348
    //   1210: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1213: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1216: invokestatic 310	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1219: pop
    //   1220: goto -115 -> 1105
    //   1223: astore_2
    //   1224: aload_0
    //   1225: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   1228: ifnull +10 -> 1238
    //   1231: aload_0
    //   1232: getfield 247	com/google/android/apps/plus/network/HttpTransaction:mMetrics	Lcom/google/android/apps/plus/network/HttpTransactionMetrics;
    //   1235: invokevirtual 252	com/google/android/apps/plus/network/HttpTransactionMetrics:onEndTransaction	()V
    //   1238: aload_1
    //   1239: ifnull +14 -> 1253
    //   1242: aload_1
    //   1243: invokeinterface 290 1 0
    //   1248: invokeinterface 295 1 0
    //   1253: aload_2
    //   1254: athrow
    //   1255: astore 26
    //   1257: goto -472 -> 785
    //   1260: astore 31
    //   1262: aconst_null
    //   1263: astore 14
    //   1265: goto -568 -> 697
    //   1268: astore 30
    //   1270: goto -434 -> 836
    //   1273: astore_2
    //   1274: aload 11
    //   1276: astore_1
    //   1277: goto -53 -> 1224
    //   1280: astore_3
    //   1281: aload 11
    //   1283: astore_1
    //   1284: goto -412 -> 872
    //   1287: astore_3
    //   1288: aload 11
    //   1290: astore_1
    //   1291: goto -1015 -> 276
    //   1294: astore 32
    //   1296: aload 27
    //   1298: astore 24
    //   1300: aload 32
    //   1302: astore 25
    //   1304: goto -524 -> 780
    //   1307: aload 27
    //   1309: astore 28
    //   1311: goto -644 -> 667
    //   1314: aload 11
    //   1316: astore_1
    //   1317: goto -1050 -> 267
    //   1320: astore 40
    //   1322: aload 40
    //   1324: astore 8
    //   1326: goto -1145 -> 181
    //   1329: astore 39
    //   1331: aload 39
    //   1333: astore 8
    //   1335: goto -1154 -> 181
    //   1338: astore 37
    //   1340: aload 37
    //   1342: astore 8
    //   1344: goto -1163 -> 181
    //
    // Exception table:
    //   from	to	target	type
    //   107	143	175	org/apache/http/conn/ConnectTimeoutException
    //   107	143	237	java/lang/Exception
    //   2	25	275	org/apache/http/client/HttpResponseException
    //   107	143	275	org/apache/http/client/HttpResponseException
    //   181	275	275	org/apache/http/client/HttpResponseException
    //   325	370	275	org/apache/http/client/HttpResponseException
    //   413	625	275	org/apache/http/client/HttpResponseException
    //   628	637	275	org/apache/http/client/HttpResponseException
    //   642	664	275	org/apache/http/client/HttpResponseException
    //   692	697	275	org/apache/http/client/HttpResponseException
    //   710	740	275	org/apache/http/client/HttpResponseException
    //   780	785	275	org/apache/http/client/HttpResponseException
    //   785	798	275	org/apache/http/client/HttpResponseException
    //   831	836	275	org/apache/http/client/HttpResponseException
    //   836	839	275	org/apache/http/client/HttpResponseException
    //   841	865	275	org/apache/http/client/HttpResponseException
    //   921	936	275	org/apache/http/client/HttpResponseException
    //   628	637	778	java/io/IOException
    //   413	625	798	com/google/android/apps/plus/api/ProtocolException
    //   628	637	798	com/google/android/apps/plus/api/ProtocolException
    //   642	664	798	com/google/android/apps/plus/api/ProtocolException
    //   692	697	798	com/google/android/apps/plus/api/ProtocolException
    //   780	785	798	com/google/android/apps/plus/api/ProtocolException
    //   785	798	798	com/google/android/apps/plus/api/ProtocolException
    //   831	836	798	com/google/android/apps/plus/api/ProtocolException
    //   836	839	798	com/google/android/apps/plus/api/ProtocolException
    //   675	692	829	finally
    //   807	826	829	finally
    //   413	625	839	java/lang/Exception
    //   628	637	839	java/lang/Exception
    //   642	664	839	java/lang/Exception
    //   692	697	839	java/lang/Exception
    //   780	785	839	java/lang/Exception
    //   785	798	839	java/lang/Exception
    //   831	836	839	java/lang/Exception
    //   836	839	839	java/lang/Exception
    //   2	25	871	java/lang/Exception
    //   181	275	871	java/lang/Exception
    //   325	370	871	java/lang/Exception
    //   710	740	871	java/lang/Exception
    //   841	865	871	java/lang/Exception
    //   921	936	871	java/lang/Exception
    //   2	25	1223	finally
    //   107	143	1223	finally
    //   181	275	1223	finally
    //   276	293	1223	finally
    //   325	370	1223	finally
    //   413	625	1223	finally
    //   628	637	1223	finally
    //   642	664	1223	finally
    //   692	697	1223	finally
    //   710	740	1223	finally
    //   780	785	1223	finally
    //   785	798	1223	finally
    //   831	836	1223	finally
    //   836	839	1223	finally
    //   841	865	1223	finally
    //   872	889	1223	finally
    //   921	936	1223	finally
    //   970	1056	1223	finally
    //   1088	1117	1223	finally
    //   1149	1220	1223	finally
    //   780	785	1255	java/io/IOException
    //   692	697	1260	java/io/IOException
    //   831	836	1268	java/io/IOException
    //   59	107	1273	finally
    //   59	107	1280	java/lang/Exception
    //   59	107	1287	org/apache/http/client/HttpResponseException
    //   642	664	1294	java/io/IOException
    //   107	143	1320	javax/net/ssl/SSLException
    //   107	143	1329	java/net/UnknownHostException
    //   107	143	1338	java/net/SocketException
  }

  public final boolean isAborted()
  {
    boolean bool;
    if (this.mAborted)
      bool = true;
    while (true)
    {
      return bool;
      if (this.mHttpMethod != null)
        bool = this.mHttpMethod.isAborted();
      else
        bool = false;
    }
  }

  public final void onStartResultProcessing()
  {
    if (this.mMetrics != null)
      this.mMetrics.onStartResultProcessing();
  }

  final void printHeaders()
  {
    if (EsLog.isLoggable("HttpTransaction", 3))
    {
      StringBuilder localStringBuilder = new StringBuilder("HTTP headers:\n");
      Header[] arrayOfHeader = this.mHttpMethod.getAllHeaders();
      int i = arrayOfHeader.length;
      int j = 0;
      if (j < i)
      {
        Header localHeader = arrayOfHeader[j];
        if ("Authorization".equals(localHeader.getName()))
          localStringBuilder.append("Authorization: <removed>");
        while (true)
        {
          localStringBuilder.append("\n");
          j++;
          break;
          localStringBuilder.append(localHeader.toString());
        }
      }
      Log.d("HttpTransaction", localStringBuilder.toString());
    }
  }

  public final void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
  {
    CookieSpecRegistry localCookieSpecRegistry = (CookieSpecRegistry)paramHttpContext.getAttribute("http.cookiespec-registry");
    HttpHost localHttpHost = (HttpHost)paramHttpContext.getAttribute("http.target_host");
    ManagedClientConnection localManagedClientConnection = (ManagedClientConnection)paramHttpContext.getAttribute("http.connection");
    String str1 = HttpClientParams.getCookiePolicy(paramHttpRequest.getParams());
    URI localURI = ((HttpUriRequest)paramHttpRequest).getURI();
    String str2 = localHttpHost.getHostName();
    int i = localHttpHost.getPort();
    if (i < 0)
      i = localManagedClientConnection.getRemotePort();
    CookieOrigin localCookieOrigin = new CookieOrigin(str2, i, localURI.getPath(), localManagedClientConnection.isSecure());
    paramHttpContext.setAttribute("http.cookie-spec", localCookieSpecRegistry.getCookieSpec(str1, paramHttpRequest.getParams()));
    paramHttpContext.setAttribute("http.cookie-origin", localCookieOrigin);
    if (this.mMetrics != null)
      this.mMetrics.setConnectionMetrics(localManagedClientConnection.getMetrics());
  }

  public final void process(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
  {
    CookieSpec localCookieSpec = (CookieSpec)paramHttpContext.getAttribute("http.cookie-spec");
    CookieOrigin localCookieOrigin = (CookieOrigin)paramHttpContext.getAttribute("http.cookie-origin");
    processCookies(paramHttpResponse.headerIterator("Set-Cookie"), localCookieSpec, localCookieOrigin);
    if (localCookieSpec.getVersion() > 0)
      processCookies(paramHttpResponse.headerIterator("Set-Cookie2"), localCookieSpec, localCookieOrigin);
  }

  public final void setHttpTransactionMetrics(HttpTransactionMetrics paramHttpTransactionMetrics)
  {
    this.mMetrics = paramHttpTransactionMetrics;
  }

  private static final class CountingOutputStream extends FilterOutputStream
  {
    private final long mChunk;
    private final long mLength;
    private long mNext;
    private final HttpTransaction mTransaction;
    private long mTransferred;

    public CountingOutputStream(HttpTransaction paramHttpTransaction, OutputStream paramOutputStream, long paramLong)
    {
      super();
      this.mTransaction = paramHttpTransaction;
      this.mLength = (2L * paramLong);
      this.mTransferred = 0L;
      this.mChunk = (this.mLength / 5L);
      this.mNext = this.mChunk;
    }

    public final void write(int paramInt)
      throws IOException
    {
      super.write(paramInt);
      this.mTransferred = (1L + this.mTransferred);
      if (this.mTransferred >= this.mNext)
      {
        super.flush();
        this.mNext += this.mChunk;
      }
    }

    public final void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      super.write(paramArrayOfByte, paramInt1, paramInt2);
      this.mTransferred += paramInt2;
      if (this.mTransferred >= this.mNext)
      {
        super.flush();
        this.mNext += this.mChunk;
      }
    }
  }

  public static abstract interface HttpTransactionListener
  {
    public abstract void onHttpCookie(Cookie paramCookie);

    public abstract void onHttpReadErrorFromStream(InputStream paramInputStream, String paramString, int paramInt1, Header[] paramArrayOfHeader, int paramInt2)
      throws IOException;

    public abstract void onHttpReadFromStream(InputStream paramInputStream, String paramString, int paramInt, Header[] paramArrayOfHeader)
      throws IOException;

    public abstract void onHttpTransactionComplete(int paramInt, String paramString, Exception paramException);
  }

  private static final class MyInputStreamEntity extends HttpEntityWrapper
  {
    private final HttpTransaction mTransaction;

    public MyInputStreamEntity(HttpTransaction paramHttpTransaction, HttpEntity paramHttpEntity)
    {
      super();
      this.mTransaction = paramHttpTransaction;
    }

    public final void writeTo(OutputStream paramOutputStream)
      throws IOException
    {
      super.writeTo(new HttpTransaction.CountingOutputStream(this.mTransaction, paramOutputStream, getContentLength()));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.HttpTransaction
 * JD-Core Version:    0.6.2
 */