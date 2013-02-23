package com.google.android.picasasync;

import com.google.android.picasastore.HttpUtils;
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
    //   60: getfield 72	com/google/android/picasasync/GDataClient:mAuthToken	Ljava/lang/String;
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
    //   99: getfield 87	com/google/android/picasasync/GDataClient$Operation:inOutEtag	Ljava/lang/String;
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
    //   125: getfield 22	com/google/android/picasasync/GDataClient:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   128: aload_3
    //   129: invokeinterface 101 2 0
    //   134: astore 13
    //   136: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   139: lload 24
    //   141: lsub
    //   142: invokestatic 107	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDuration	(J)V
    //   145: lconst_1
    //   146: invokestatic 110	com/google/android/picasastore/MetricsUtils:incrementNetworkOpCount	(J)V
    //   149: aload_2
    //   150: aconst_null
    //   151: putfield 114	com/google/android/picasasync/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   154: aload 13
    //   156: invokeinterface 120 1 0
    //   161: invokeinterface 126 1 0
    //   166: istore 16
    //   168: aload 13
    //   170: invokeinterface 130 1 0
    //   175: astore 17
    //   177: aload 17
    //   179: ifnull +259 -> 438
    //   182: aload 17
    //   184: invokeinterface 136 1 0
    //   189: astore 22
    //   191: aload 22
    //   193: ifnull +238 -> 431
    //   196: aload 17
    //   198: invokeinterface 140 1 0
    //   203: astore 23
    //   205: aload 23
    //   207: ifnull +224 -> 431
    //   210: aload 23
    //   212: invokeinterface 145 1 0
    //   217: ldc 70
    //   219: invokevirtual 148	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   222: ifeq +209 -> 431
    //   225: new 150	java/util/zip/GZIPInputStream
    //   228: dup
    //   229: aload 22
    //   231: invokespecial 153	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   234: astore 18
    //   236: aload 13
    //   238: ldc 155
    //   240: invokeinterface 159 2 0
    //   245: astore 19
    //   247: aload_2
    //   248: iload 16
    //   250: putfield 163	com/google/android/picasasync/GDataClient$Operation:outStatus	I
    //   253: aconst_null
    //   254: astore 20
    //   256: aload 19
    //   258: ifnull +12 -> 270
    //   261: aload 19
    //   263: invokeinterface 145 1 0
    //   268: astore 20
    //   270: aload_2
    //   271: aload 20
    //   273: putfield 87	com/google/android/picasasync/GDataClient$Operation:inOutEtag	Ljava/lang/String;
    //   276: aload_2
    //   277: aload 18
    //   279: putfield 114	com/google/android/picasasync/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   282: aload_2
    //   283: getfield 114	com/google/android/picasasync/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   286: ifnonnull +24 -> 310
    //   289: aload 13
    //   291: invokeinterface 130 1 0
    //   296: astore 21
    //   298: aload 21
    //   300: ifnull +10 -> 310
    //   303: aload 21
    //   305: invokeinterface 166 1 0
    //   310: return
    //   311: astore 7
    //   313: ldc 168
    //   315: new 36	java/lang/StringBuilder
    //   318: dup
    //   319: ldc 170
    //   321: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   324: aload_3
    //   325: invokeinterface 174 1 0
    //   330: invokestatic 180	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
    //   333: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   336: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   339: invokestatic 186	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   342: pop
    //   343: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   346: lstore 11
    //   348: aload_0
    //   349: getfield 22	com/google/android/picasasync/GDataClient:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   352: aload_3
    //   353: invokeinterface 101 2 0
    //   358: astore 13
    //   360: invokestatic 95	android/os/SystemClock:elapsedRealtime	()J
    //   363: lload 11
    //   365: lsub
    //   366: invokestatic 107	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDuration	(J)V
    //   369: lconst_1
    //   370: invokestatic 110	com/google/android/picasastore/MetricsUtils:incrementNetworkOpCount	(J)V
    //   373: goto -224 -> 149
    //   376: astore 9
    //   378: ldc 168
    //   380: ldc 188
    //   382: invokestatic 186	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   385: pop
    //   386: aload 9
    //   388: athrow
    //   389: astore 6
    //   391: lconst_1
    //   392: invokestatic 110	com/google/android/picasastore/MetricsUtils:incrementNetworkOpCount	(J)V
    //   395: aload 6
    //   397: athrow
    //   398: astore 14
    //   400: aload_2
    //   401: getfield 114	com/google/android/picasasync/GDataClient$Operation:outBody	Ljava/io/InputStream;
    //   404: ifnonnull +24 -> 428
    //   407: aload 13
    //   409: invokeinterface 130 1 0
    //   414: astore 15
    //   416: aload 15
    //   418: ifnull +10 -> 428
    //   421: aload 15
    //   423: invokeinterface 166 1 0
    //   428: aload 14
    //   430: athrow
    //   431: aload 22
    //   433: astore 18
    //   435: goto -199 -> 236
    //   438: aconst_null
    //   439: astore 18
    //   441: goto -205 -> 236
    //
    // Exception table:
    //   from	to	target	type
    //   119	145	311	java/io/IOException
    //   343	369	376	java/io/IOException
    //   119	145	389	finally
    //   313	343	389	finally
    //   343	369	389	finally
    //   378	389	389	finally
    //   154	282	398	finally
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
 * Qualified Name:     com.google.android.picasasync.GDataClient
 * JD-Core Version:    0.6.2
 */