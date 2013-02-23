package com.google.android.apps.plus.iu;

import java.io.InputStream;
import org.apache.http.client.HttpClient;

final class GDataClient
{
  private String mAuthToken;
  private HttpClient mHttpClient = HttpUtils.createHttpClient("GData/1.0; gzip");

  // ERROR //
  public final void get(String paramString, Operation paramOperation)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 28
    //   3: invokevirtual 34	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   6: ifeq +24 -> 30
    //   9: new 36	java/lang/StringBuilder
    //   12: dup
    //   13: ldc 38
    //   15: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   18: aload_1
    //   19: iconst_5
    //   20: invokevirtual 45	java/lang/String:substring	(I)Ljava/lang/String;
    //   23: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   29: astore_1
    //   30: new 55	org/apache/http/client/methods/HttpGet
    //   33: dup
    //   34: aload_1
    //   35: invokespecial 56	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   38: astore_3
    //   39: aload_3
    //   40: ldc 58
    //   42: ldc 60
    //   44: invokeinterface 66 3 0
    //   49: aload_3
    //   50: ldc 68
    //   52: ldc 70
    //   54: invokeinterface 66 3 0
    //   59: aload_0
    //   60: getfield 72	com/google/android/apps/plus/iu/GDataClient:mAuthToken	Ljava/lang/String;
    //   63: astore 4
    //   65: aload 4
    //   67: invokestatic 78	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   70: ifne +28 -> 98
    //   73: aload_3
    //   74: ldc 80
    //   76: new 36	java/lang/StringBuilder
    //   79: dup
    //   80: ldc 82
    //   82: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   85: aload 4
    //   87: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   93: invokeinterface 66 3 0
    //   98: aload_2
    //   99: getfield 87	com/google/android/apps/plus/iu/GDataClient$Operation:inOutEtag	Ljava/lang/String;
    //   102: astore 5
    //   104: aload 5
    //   106: ifnull +13 -> 119
    //   109: aload_3
    //   110: ldc 89
    //   112: aload 5
    //   114: invokeinterface 66 3 0
    //   119: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   122: lstore 24
    //   124: aload_0
    //   125: getfield 22	com/google/android/apps/plus/iu/GDataClient:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   128: aload_3
    //   129: invokeinterface 101 2 0
    //   134: astore 12
    //   136: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   139: lload 24
    //   141: lsub
    //   142: invokestatic 107	com/google/android/apps/plus/iu/MetricsUtils:incrementNetworkOpDuration	(J)V
    //   145: lconst_1
    //   146: invokestatic 110	com/google/android/apps/plus/iu/MetricsUtils:incrementNetworkOpCount	(J)V
    //   149: aload_2
    //   150: aconst_null
    //   151: putfield 114	com/google/android/apps/plus/iu/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   154: aload 12
    //   156: invokeinterface 120 1 0
    //   161: invokeinterface 126 1 0
    //   166: istore 15
    //   168: aload 12
    //   170: invokeinterface 130 1 0
    //   175: astore 16
    //   177: aload 16
    //   179: ifnull +280 -> 459
    //   182: aload 16
    //   184: invokeinterface 136 1 0
    //   189: astore 21
    //   191: aload 21
    //   193: ifnull +259 -> 452
    //   196: aload 16
    //   198: invokeinterface 140 1 0
    //   203: astore 22
    //   205: aload 22
    //   207: ifnull +245 -> 452
    //   210: aload 22
    //   212: invokeinterface 145 1 0
    //   217: ldc 70
    //   219: invokevirtual 148	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   222: ifeq +230 -> 452
    //   225: new 150	java/util/zip/GZIPInputStream
    //   228: dup
    //   229: aload 21
    //   231: invokespecial 153	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   234: astore 17
    //   236: aload 12
    //   238: ldc 155
    //   240: invokeinterface 159 2 0
    //   245: astore 18
    //   247: aload_2
    //   248: iload 15
    //   250: putfield 163	com/google/android/apps/plus/iu/GDataClient$Operation:outStatus	I
    //   253: aconst_null
    //   254: astore 19
    //   256: aload 18
    //   258: ifnull +12 -> 270
    //   261: aload 18
    //   263: invokeinterface 145 1 0
    //   268: astore 19
    //   270: aload_2
    //   271: aload 19
    //   273: putfield 87	com/google/android/apps/plus/iu/GDataClient$Operation:inOutEtag	Ljava/lang/String;
    //   276: aload_2
    //   277: aload 17
    //   279: putfield 114	com/google/android/apps/plus/iu/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   282: aload_2
    //   283: getfield 114	com/google/android/apps/plus/iu/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   286: ifnonnull +24 -> 310
    //   289: aload 12
    //   291: invokeinterface 130 1 0
    //   296: astore 20
    //   298: aload 20
    //   300: ifnull +10 -> 310
    //   303: aload 20
    //   305: invokeinterface 166 1 0
    //   310: return
    //   311: astore 7
    //   313: ldc 168
    //   315: iconst_5
    //   316: invokestatic 174	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   319: ifeq +36 -> 355
    //   322: ldc 168
    //   324: new 36	java/lang/StringBuilder
    //   327: dup
    //   328: ldc 176
    //   330: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   333: aload_3
    //   334: invokeinterface 180 1 0
    //   339: invokevirtual 183	java/net/URI:toString	()Ljava/lang/String;
    //   342: invokestatic 189	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
    //   345: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   351: invokestatic 195	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   354: pop
    //   355: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   358: lstore 10
    //   360: aload_0
    //   361: getfield 22	com/google/android/apps/plus/iu/GDataClient:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   364: aload_3
    //   365: invokeinterface 101 2 0
    //   370: astore 12
    //   372: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   375: lload 10
    //   377: lsub
    //   378: invokestatic 107	com/google/android/apps/plus/iu/MetricsUtils:incrementNetworkOpDuration	(J)V
    //   381: lconst_1
    //   382: invokestatic 110	com/google/android/apps/plus/iu/MetricsUtils:incrementNetworkOpCount	(J)V
    //   385: goto -236 -> 149
    //   388: astore 8
    //   390: ldc 168
    //   392: iconst_5
    //   393: invokestatic 174	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   396: ifeq +11 -> 407
    //   399: ldc 168
    //   401: ldc 197
    //   403: invokestatic 195	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   406: pop
    //   407: aload 8
    //   409: athrow
    //   410: astore 6
    //   412: lconst_1
    //   413: invokestatic 110	com/google/android/apps/plus/iu/MetricsUtils:incrementNetworkOpCount	(J)V
    //   416: aload 6
    //   418: athrow
    //   419: astore 13
    //   421: aload_2
    //   422: getfield 114	com/google/android/apps/plus/iu/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   425: ifnonnull +24 -> 449
    //   428: aload 12
    //   430: invokeinterface 130 1 0
    //   435: astore 14
    //   437: aload 14
    //   439: ifnull +10 -> 449
    //   442: aload 14
    //   444: invokeinterface 166 1 0
    //   449: aload 13
    //   451: athrow
    //   452: aload 21
    //   454: astore 17
    //   456: goto -220 -> 236
    //   459: aconst_null
    //   460: astore 17
    //   462: goto -226 -> 236
    //
    // Exception table:
    //   from	to	target	type
    //   119	145	311	java/io/IOException
    //   355	381	388	java/io/IOException
    //   119	145	410	finally
    //   313	355	410	finally
    //   355	381	410	finally
    //   390	410	410	finally
    //   154	282	419	finally
  }

  public final void setAuthToken(String paramString)
  {
    this.mAuthToken = paramString;
  }

  public static final class Operation
  {
    public String inOutEtag;
    public InputStream outBody;
    public int outStatus;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.GDataClient
 * JD-Core Version:    0.6.2
 */