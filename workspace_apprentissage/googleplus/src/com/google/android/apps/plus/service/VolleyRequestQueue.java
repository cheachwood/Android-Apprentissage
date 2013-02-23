package com.google.android.apps.plus.service;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import com.android.volley.ExecutorDelivery;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

public final class VolleyRequestQueue
{
  private EsNetwork mNetwork;
  private RequestQueue mRequestQueue;

  public VolleyRequestQueue(Context paramContext, Handler paramHandler)
  {
    this.mNetwork = new EsNetwork(paramContext.getApplicationContext(), new HurlStack(), SharedByteArrayPool.getInstance());
    this.mRequestQueue = new RequestQueue(new NoCache(), this.mNetwork, 2, new ExecutorDelivery(paramHandler));
    this.mRequestQueue.start();
  }

  public final void add(VolleyRequest paramVolleyRequest)
  {
    this.mRequestQueue.add(paramVolleyRequest);
  }

  public final void cancelAll(RequestQueue.RequestFilter paramRequestFilter)
  {
    this.mRequestQueue.cancelAll(paramRequestFilter);
  }

  private static final class EsNetwork extends BasicNetwork
  {
    private Context mContext;

    public EsNetwork(Context paramContext, HttpStack paramHttpStack, ByteArrayPool paramByteArrayPool)
    {
      super(paramByteArrayPool);
      this.mContext = paramContext;
    }

    // ERROR //
    private byte[] tryContentUri(Uri paramUri)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_2
      //   2: new 21	com/android/volley/toolbox/PoolingByteArrayOutputStream
      //   5: dup
      //   6: aload_0
      //   7: getfield 25	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
      //   10: invokespecial 28	com/android/volley/toolbox/PoolingByteArrayOutputStream:<init>	(Lcom/android/volley/toolbox/ByteArrayPool;)V
      //   13: astore_3
      //   14: aload_0
      //   15: getfield 25	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
      //   18: sipush 1024
      //   21: invokevirtual 34	com/android/volley/toolbox/ByteArrayPool:getBuf	(I)[B
      //   24: astore 4
      //   26: aload_0
      //   27: getfield 13	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mContext	Landroid/content/Context;
      //   30: invokevirtual 40	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
      //   33: aload_1
      //   34: invokevirtual 46	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
      //   37: astore 11
      //   39: aload 11
      //   41: aload 4
      //   43: invokevirtual 52	java/io/InputStream:read	([B)I
      //   46: istore 13
      //   48: iload 13
      //   50: iconst_m1
      //   51: if_icmpeq +42 -> 93
      //   54: aload_3
      //   55: aload 4
      //   57: iconst_0
      //   58: iload 13
      //   60: invokevirtual 56	com/android/volley/toolbox/PoolingByteArrayOutputStream:write	([BII)V
      //   63: goto -24 -> 39
      //   66: astore 12
      //   68: aload 11
      //   70: invokevirtual 60	java/io/InputStream:close	()V
      //   73: aload 12
      //   75: athrow
      //   76: astore 9
      //   78: aload_0
      //   79: getfield 25	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
      //   82: aload 4
      //   84: invokevirtual 64	com/android/volley/toolbox/ByteArrayPool:returnBuf	([B)V
      //   87: aload_3
      //   88: invokevirtual 65	com/android/volley/toolbox/PoolingByteArrayOutputStream:close	()V
      //   91: aload_2
      //   92: areturn
      //   93: aload 11
      //   95: invokevirtual 60	java/io/InputStream:close	()V
      //   98: aload_3
      //   99: invokevirtual 69	com/android/volley/toolbox/PoolingByteArrayOutputStream:toByteArray	()[B
      //   102: astore 14
      //   104: aload 14
      //   106: astore_2
      //   107: aload_0
      //   108: getfield 25	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
      //   111: aload 4
      //   113: invokevirtual 64	com/android/volley/toolbox/ByteArrayPool:returnBuf	([B)V
      //   116: aload_3
      //   117: invokevirtual 65	com/android/volley/toolbox/PoolingByteArrayOutputStream:close	()V
      //   120: goto -29 -> 91
      //   123: astore 15
      //   125: goto -34 -> 91
      //   128: astore 7
      //   130: aload_0
      //   131: getfield 25	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
      //   134: aload 4
      //   136: invokevirtual 64	com/android/volley/toolbox/ByteArrayPool:returnBuf	([B)V
      //   139: aload_3
      //   140: invokevirtual 65	com/android/volley/toolbox/PoolingByteArrayOutputStream:close	()V
      //   143: aconst_null
      //   144: astore_2
      //   145: goto -54 -> 91
      //   148: astore 8
      //   150: aconst_null
      //   151: astore_2
      //   152: goto -61 -> 91
      //   155: astore 5
      //   157: aload_0
      //   158: getfield 25	com/google/android/apps/plus/service/VolleyRequestQueue$EsNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
      //   161: aload 4
      //   163: invokevirtual 64	com/android/volley/toolbox/ByteArrayPool:returnBuf	([B)V
      //   166: aload_3
      //   167: invokevirtual 65	com/android/volley/toolbox/PoolingByteArrayOutputStream:close	()V
      //   170: aload 5
      //   172: athrow
      //   173: astore 10
      //   175: aconst_null
      //   176: astore_2
      //   177: goto -86 -> 91
      //   180: astore 6
      //   182: goto -12 -> 170
      //
      // Exception table:
      //   from	to	target	type
      //   39	63	66	finally
      //   26	39	76	java/io/IOException
      //   68	76	76	java/io/IOException
      //   93	104	76	java/io/IOException
      //   116	120	123	java/io/IOException
      //   26	39	128	java/lang/OutOfMemoryError
      //   68	76	128	java/lang/OutOfMemoryError
      //   93	104	128	java/lang/OutOfMemoryError
      //   139	143	148	java/io/IOException
      //   26	39	155	finally
      //   68	76	155	finally
      //   93	104	155	finally
      //   87	91	173	java/io/IOException
      //   166	170	180	java/io/IOException
    }

    public final NetworkResponse performRequest(Request<?> paramRequest)
      throws VolleyError
    {
      Process.setThreadPriority(19);
      Object localObject;
      try
      {
        if ((paramRequest instanceof VolleyRequest))
        {
          Uri localUri = ((VolleyRequest)paramRequest).getContentUri();
          if (localUri != null)
          {
            byte[] arrayOfByte = tryContentUri(localUri);
            if ((arrayOfByte != null) && (arrayOfByte.length != 0))
            {
              localObject = new NetworkResponse(arrayOfByte);
              break label81;
            }
          }
        }
        NetworkResponse localNetworkResponse = super.performRequest(paramRequest);
        localObject = localNetworkResponse;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        throw new VolleyOutOfMemoryError(localOutOfMemoryError);
      }
      label81: return localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.VolleyRequestQueue
 * JD-Core Version:    0.6.2
 */