package com.android.volley;

import java.util.concurrent.BlockingQueue;

public final class NetworkDispatcher extends Thread
{
  private final Cache mCache;
  private final ResponseDelivery mDelivery;
  private final Network mNetwork;
  private final BlockingQueue<Request> mQueue;
  private volatile boolean mQuit = false;

  public NetworkDispatcher(BlockingQueue<Request> paramBlockingQueue, Network paramNetwork, Cache paramCache, ResponseDelivery paramResponseDelivery)
  {
    this.mQueue = paramBlockingQueue;
    this.mNetwork = paramNetwork;
    this.mCache = paramCache;
    this.mDelivery = paramResponseDelivery;
  }

  public final void quit()
  {
    this.mQuit = true;
    interrupt();
  }

  // ERROR //
  public final void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 24	com/android/volley/NetworkDispatcher:mQueue	Ljava/util/concurrent/BlockingQueue;
    //   4: invokeinterface 47 1 0
    //   9: checkcast 49	com/android/volley/Request
    //   12: astore_2
    //   13: aload_2
    //   14: ldc 51
    //   16: invokevirtual 55	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   19: aload_2
    //   20: invokevirtual 59	com/android/volley/Request:isCanceled	()Z
    //   23: ifeq +45 -> 68
    //   26: aload_2
    //   27: ldc 61
    //   29: invokevirtual 64	com/android/volley/Request:finish	(Ljava/lang/String;)V
    //   32: goto -32 -> 0
    //   35: astore 5
    //   37: aload 5
    //   39: invokestatic 68	com/android/volley/Request:parseNetworkError	(Lcom/android/volley/VolleyError;)Lcom/android/volley/VolleyError;
    //   42: astore 6
    //   44: aload_0
    //   45: getfield 30	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   48: aload_2
    //   49: aload 6
    //   51: invokeinterface 74 3 0
    //   56: goto -56 -> 0
    //   59: astore_1
    //   60: aload_0
    //   61: getfield 22	com/android/volley/NetworkDispatcher:mQuit	Z
    //   64: ifeq -64 -> 0
    //   67: return
    //   68: aload_0
    //   69: getfield 26	com/android/volley/NetworkDispatcher:mNetwork	Lcom/android/volley/Network;
    //   72: aload_2
    //   73: invokeinterface 80 2 0
    //   78: astore 7
    //   80: aload_2
    //   81: ldc 82
    //   83: invokevirtual 55	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   86: aload 7
    //   88: getfield 87	com/android/volley/NetworkResponse:notModified	Z
    //   91: ifeq +62 -> 153
    //   94: aload_2
    //   95: invokevirtual 90	com/android/volley/Request:hasHadResponseDelivered	()Z
    //   98: ifeq +55 -> 153
    //   101: aload_2
    //   102: ldc 92
    //   104: invokevirtual 64	com/android/volley/Request:finish	(Ljava/lang/String;)V
    //   107: goto -107 -> 0
    //   110: astore_3
    //   111: iconst_1
    //   112: anewarray 94	java/lang/Object
    //   115: astore 4
    //   117: aload 4
    //   119: iconst_0
    //   120: aload_3
    //   121: invokevirtual 98	java/lang/Exception:toString	()Ljava/lang/String;
    //   124: aastore
    //   125: ldc 100
    //   127: aload 4
    //   129: invokestatic 106	com/android/volley/VolleyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   132: aload_0
    //   133: getfield 30	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   136: aload_2
    //   137: new 39	com/android/volley/VolleyError
    //   140: dup
    //   141: aload_3
    //   142: invokespecial 109	com/android/volley/VolleyError:<init>	(Ljava/lang/Throwable;)V
    //   145: invokeinterface 74 3 0
    //   150: goto -150 -> 0
    //   153: aload_2
    //   154: aload 7
    //   156: invokevirtual 113	com/android/volley/Request:parseNetworkResponse	(Lcom/android/volley/NetworkResponse;)Lcom/android/volley/Response;
    //   159: astore 8
    //   161: aload_2
    //   162: ldc 115
    //   164: invokevirtual 55	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   167: aload_2
    //   168: invokevirtual 118	com/android/volley/Request:shouldCache	()Z
    //   171: ifeq +33 -> 204
    //   174: aload 8
    //   176: getfield 124	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
    //   179: ifnull +25 -> 204
    //   182: aload_0
    //   183: getfield 28	com/android/volley/NetworkDispatcher:mCache	Lcom/android/volley/Cache;
    //   186: pop
    //   187: aload_2
    //   188: invokevirtual 127	com/android/volley/Request:getCacheKey	()Ljava/lang/String;
    //   191: pop
    //   192: aload 8
    //   194: getfield 124	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
    //   197: pop
    //   198: aload_2
    //   199: ldc 129
    //   201: invokevirtual 55	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   204: aload_2
    //   205: invokevirtual 132	com/android/volley/Request:markDelivered	()V
    //   208: aload_0
    //   209: getfield 30	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   212: aload_2
    //   213: aload 8
    //   215: invokeinterface 136 3 0
    //   220: goto -220 -> 0
    //
    // Exception table:
    //   from	to	target	type
    //   13	32	35	com/android/volley/VolleyError
    //   68	107	35	com/android/volley/VolleyError
    //   153	220	35	com/android/volley/VolleyError
    //   0	13	59	java/lang/InterruptedException
    //   13	32	110	java/lang/Exception
    //   68	107	110	java/lang/Exception
    //   153	220	110	java/lang/Exception
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.NetworkDispatcher
 * JD-Core Version:    0.6.2
 */