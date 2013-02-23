package com.google.android.apps.plus.network;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsNetworkData;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.EsLog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.cookie.Cookie;

public class HttpOperation
  implements HttpTransaction.HttpTransactionListener
{
  private static final Vector<byte[]> sBufferCache = new Vector(1);
  private static final ExecutorService sExecutorService = Executors.newCachedThreadPool(localSimpleThreadFactory);
  protected static final Handler sHandler;
  private static Exception sSimulateException;
  private static final ThreadFactory sThreadFactory;
  private boolean mAborted;
  protected final EsAccount mAccount;
  protected final Context mContext;
  private volatile HttpTransaction mCurrentTransaction;
  private int mErrorCode = -1;
  private Exception mEx;
  private final HttpRequestConfiguration mHttpRequestConfig;
  private final Intent mIntent;
  private final OperationListener mListener;
  private final String mMethod;
  private OutputStream mOutputStream;
  private String mReasonPhrase;
  private int mRetriesRemaining = 2;
  private boolean mThreaded;
  private final String mUrl;

  static
  {
    for (int i = 0; i <= 0; i++)
      sBufferCache.add(new byte[2048]);
    sSimulateException = null;
    sHandler = new Handler(Looper.getMainLooper());
    SimpleThreadFactory localSimpleThreadFactory = new SimpleThreadFactory((byte)0);
    sThreadFactory = localSimpleThreadFactory;
  }

  public HttpOperation(Context paramContext, String paramString1, String paramString2, EsAccount paramEsAccount, OutputStream paramOutputStream, Intent paramIntent, OperationListener paramOperationListener)
  {
    this(paramContext, paramString1, paramString2, new DefaultHttpRequestConfiguration(paramContext, paramEsAccount), paramEsAccount, paramOutputStream, paramIntent, paramOperationListener);
  }

  public HttpOperation(Context paramContext, String paramString1, String paramString2, HttpRequestConfiguration paramHttpRequestConfiguration, EsAccount paramEsAccount, OutputStream paramOutputStream, Intent paramIntent, OperationListener paramOperationListener)
  {
    this.mContext = paramContext;
    this.mMethod = paramString1;
    this.mUrl = paramString2;
    this.mHttpRequestConfig = paramHttpRequestConfiguration;
    this.mAccount = paramEsAccount;
    this.mOutputStream = paramOutputStream;
    this.mIntent = paramIntent;
    this.mListener = paramOperationListener;
  }

  protected static InputStream captureResponse(InputStream paramInputStream, int paramInt, StringBuilder paramStringBuilder)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    readFromStream(paramInputStream, paramInt, localByteArrayOutputStream);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    paramStringBuilder.append(new String(arrayOfByte));
    return new ByteArrayInputStream(arrayOfByte);
  }

  private void completeOperation(int paramInt, String paramString, Exception paramException)
  {
    setErrorInfo(paramInt, paramString, paramException);
    if (this.mListener != null)
      this.mListener.onOperationComplete(this);
  }

  // ERROR //
  private static void readFromStream(InputStream paramInputStream, int paramInt, OutputStream paramOutputStream)
    throws NetworkException, MemoryException
  {
    // Byte code:
    //   0: getstatic 52	com/google/android/apps/plus/network/HttpOperation:sBufferCache	Ljava/util/Vector;
    //   3: iconst_0
    //   4: invokevirtual 173	java/util/Vector:remove	(I)Ljava/lang/Object;
    //   7: checkcast 175	[B
    //   10: astore 4
    //   12: iconst_1
    //   13: istore 5
    //   15: iload_1
    //   16: iconst_m1
    //   17: if_icmpne +97 -> 114
    //   20: aload_0
    //   21: aload 4
    //   23: iconst_0
    //   24: aload 4
    //   26: arraylength
    //   27: invokevirtual 181	java/io/InputStream:read	([BII)I
    //   30: istore 16
    //   32: iload 16
    //   34: iconst_m1
    //   35: if_icmpeq +204 -> 239
    //   38: aload_2
    //   39: aload 4
    //   41: iconst_0
    //   42: iload 16
    //   44: invokevirtual 187	java/io/OutputStream:write	([BII)V
    //   47: goto -27 -> 20
    //   50: astore 14
    //   52: new 163	com/google/android/apps/plus/network/NetworkException
    //   55: dup
    //   56: aload 14
    //   58: invokevirtual 191	java/io/IOException:getMessage	()Ljava/lang/String;
    //   61: invokespecial 194	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   64: athrow
    //   65: astore 11
    //   67: iload 5
    //   69: ifeq +12 -> 81
    //   72: getstatic 52	com/google/android/apps/plus/network/HttpOperation:sBufferCache	Ljava/util/Vector;
    //   75: aload 4
    //   77: invokevirtual 56	java/util/Vector:add	(Ljava/lang/Object;)Z
    //   80: pop
    //   81: aload_0
    //   82: ifnull +7 -> 89
    //   85: aload_0
    //   86: invokevirtual 197	java/io/InputStream:close	()V
    //   89: aload_2
    //   90: invokevirtual 200	java/io/OutputStream:flush	()V
    //   93: aload_2
    //   94: invokevirtual 201	java/io/OutputStream:close	()V
    //   97: aload 11
    //   99: athrow
    //   100: astore_3
    //   101: sipush 2048
    //   104: newarray byte
    //   106: astore 4
    //   108: iconst_0
    //   109: istore 5
    //   111: goto -96 -> 15
    //   114: iload_1
    //   115: istore 6
    //   117: iload 6
    //   119: ifle +90 -> 209
    //   122: aload_0
    //   123: aload 4
    //   125: iconst_0
    //   126: iload 6
    //   128: aload 4
    //   130: arraylength
    //   131: invokestatic 207	java/lang/Math:min	(II)I
    //   134: invokevirtual 181	java/io/InputStream:read	([BII)I
    //   137: istore 15
    //   139: iload 15
    //   141: iconst_m1
    //   142: if_icmpne +43 -> 185
    //   145: new 163	com/google/android/apps/plus/network/NetworkException
    //   148: dup
    //   149: new 145	java/lang/StringBuilder
    //   152: dup
    //   153: ldc 209
    //   155: invokespecial 210	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   158: iload 6
    //   160: invokevirtual 213	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   163: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokespecial 194	com/google/android/apps/plus/network/NetworkException:<init>	(Ljava/lang/String;)V
    //   169: athrow
    //   170: astore 10
    //   172: new 165	com/google/android/apps/plus/network/MemoryException
    //   175: dup
    //   176: aload 10
    //   178: invokevirtual 217	java/lang/OutOfMemoryError:getMessage	()Ljava/lang/String;
    //   181: invokespecial 218	com/google/android/apps/plus/network/MemoryException:<init>	(Ljava/lang/String;)V
    //   184: athrow
    //   185: iload 15
    //   187: ifle -70 -> 117
    //   190: iload 6
    //   192: iload 15
    //   194: isub
    //   195: istore 6
    //   197: aload_2
    //   198: aload 4
    //   200: iconst_0
    //   201: iload 15
    //   203: invokevirtual 187	java/io/OutputStream:write	([BII)V
    //   206: goto -89 -> 117
    //   209: aload_0
    //   210: aload 4
    //   212: iconst_0
    //   213: aload 4
    //   215: arraylength
    //   216: invokevirtual 181	java/io/InputStream:read	([BII)I
    //   219: istore 7
    //   221: iload 7
    //   223: iconst_m1
    //   224: if_icmpeq +15 -> 239
    //   227: aload_2
    //   228: aload 4
    //   230: iconst_0
    //   231: iload 7
    //   233: invokevirtual 187	java/io/OutputStream:write	([BII)V
    //   236: goto -27 -> 209
    //   239: iload 5
    //   241: ifeq +12 -> 253
    //   244: getstatic 52	com/google/android/apps/plus/network/HttpOperation:sBufferCache	Ljava/util/Vector;
    //   247: aload 4
    //   249: invokevirtual 56	java/util/Vector:add	(Ljava/lang/Object;)Z
    //   252: pop
    //   253: aload_0
    //   254: ifnull +7 -> 261
    //   257: aload_0
    //   258: invokevirtual 197	java/io/InputStream:close	()V
    //   261: aload_2
    //   262: invokevirtual 200	java/io/OutputStream:flush	()V
    //   265: aload_2
    //   266: invokevirtual 201	java/io/OutputStream:close	()V
    //   269: return
    //   270: astore 12
    //   272: goto -175 -> 97
    //   275: astore 8
    //   277: goto -8 -> 269
    //
    // Exception table:
    //   from	to	target	type
    //   20	47	50	java/io/IOException
    //   122	170	50	java/io/IOException
    //   197	236	50	java/io/IOException
    //   20	47	65	finally
    //   52	65	65	finally
    //   122	170	65	finally
    //   172	185	65	finally
    //   197	236	65	finally
    //   0	12	100	java/lang/IndexOutOfBoundsException
    //   20	47	170	java/lang/OutOfMemoryError
    //   122	170	170	java/lang/OutOfMemoryError
    //   197	236	170	java/lang/OutOfMemoryError
    //   85	97	270	java/io/IOException
    //   257	269	275	java/io/IOException
  }

  public final void abort()
  {
    this.mAborted = true;
    HttpTransaction localHttpTransaction = this.mCurrentTransaction;
    if (localHttpTransaction != null)
      localHttpTransaction.abort();
  }

  protected HttpEntity createPostData()
    throws IOException
  {
    return null;
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final int getErrorCode()
  {
    return this.mErrorCode;
  }

  public final Exception getException()
  {
    return this.mEx;
  }

  public final Intent getIntent()
  {
    return this.mIntent;
  }

  public final String getMethod()
  {
    return this.mMethod;
  }

  public String getName()
  {
    return getClass().getSimpleName();
  }

  public final OutputStream getOutputStream()
  {
    return this.mOutputStream;
  }

  public final String getReasonPhrase()
  {
    return this.mReasonPhrase;
  }

  public final String getUrl()
  {
    return this.mUrl;
  }

  public boolean hasError()
  {
    if (this.mErrorCode != 200);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isAborted()
  {
    return this.mAborted;
  }

  protected boolean isAuthenticationError(Exception paramException)
  {
    if ((paramException instanceof HttpResponseException))
      switch (((HttpResponseException)paramException).getStatusCode())
      {
      default:
      case 401:
      }
    for (boolean bool = false; ; bool = true)
      return bool;
  }

  protected boolean isImmediatelyRetryableError(Exception paramException)
  {
    return isAuthenticationError(paramException);
  }

  public final void logAndThrowExceptionIfFailed(String paramString)
    throws IOException
  {
    if (hasError())
    {
      logError(paramString);
      if (hasError())
      {
        if (this.mEx != null)
        {
          if (Build.VERSION.SDK_INT < 9)
            throw new IOException(getName() + " operation failed " + this.mEx.getMessage());
          throw new IOException(getName() + " operation failed", this.mEx);
        }
        if (hasError())
          throw new IOException(getName() + " operation failed, error: " + this.mErrorCode + " [" + this.mReasonPhrase + "]");
      }
    }
  }

  public void logError(String paramString)
  {
    if (this.mEx != null)
      Log.e(paramString, "[" + getName() + "] failed due to exception: " + this.mEx, this.mEx);
    while (true)
    {
      return;
      if ((hasError()) && (EsLog.isLoggable(paramString, 4)))
        Log.i(paramString, "[" + getName() + "] failed due to error: " + this.mErrorCode + " [" + this.mReasonPhrase + "]");
    }
  }

  public void onHttpCookie(Cookie paramCookie)
  {
  }

  protected void onHttpHandleContentFromStream$6508b088(InputStream paramInputStream)
    throws IOException
  {
  }

  protected void onHttpOperationComplete(int paramInt, String paramString, Exception paramException)
  {
  }

  public void onHttpReadErrorFromStream(InputStream paramInputStream, String paramString, int paramInt1, Header[] paramArrayOfHeader, int paramInt2)
    throws IOException
  {
  }

  public void onHttpReadFromStream(InputStream paramInputStream, String paramString, int paramInt, Header[] paramArrayOfHeader)
    throws IOException
  {
    OutputStream localOutputStream = this.mOutputStream;
    if (localOutputStream != null)
    {
      readFromStream(paramInputStream, paramInt, localOutputStream);
      onHttpHandleContentFromStream$6508b088(null);
    }
    while (true)
    {
      return;
      if (paramString != null)
        onHttpHandleContentFromStream$6508b088(paramInputStream);
      else
        Log.e("HttpTransaction", "Content type not specified");
    }
  }

  public final void onHttpTransactionComplete(final int paramInt, final String paramString, Exception paramException)
  {
    if ((isImmediatelyRetryableError(paramException)) && (this.mRetriesRemaining > 0));
    while (true)
    {
      try
      {
        if (isAuthenticationError(paramException))
          this.mHttpRequestConfig.invalidateAuthToken();
        Log.i("HttpTransaction", "====> Restarting operation...");
        this.mRetriesRemaining = (-1 + this.mRetriesRemaining);
        start();
        return;
      }
      catch (Exception localException2)
      {
        Log.e("HttpTransaction", "====> Retry failed");
        localException2.printStackTrace();
        paramException = localException2;
      }
      onHttpOperationComplete(paramInt, paramString, paramException);
      final Exception localException1 = paramException;
      if (this.mThreaded)
        sHandler.post(new Runnable()
        {
          public final void run()
          {
            HttpOperation.this.completeOperation(paramInt, paramString, localException1);
          }
        });
      else
        completeOperation(paramInt, paramString, localException1);
    }
  }

  public final void onStartResultProcessing()
  {
    if (this.mCurrentTransaction != null)
      this.mCurrentTransaction.onStartResultProcessing();
  }

  public void setErrorInfo(int paramInt, String paramString, Exception paramException)
  {
    this.mErrorCode = paramInt;
    this.mReasonPhrase = paramString;
    this.mEx = paramException;
  }

  public final void setOutputStream(OutputStream paramOutputStream)
  {
    this.mOutputStream = paramOutputStream;
  }

  public final void start()
  {
    if (EsLog.ENABLE_DOGFOOD_FEATURES)
    {
      HttpTransactionMetrics localHttpTransactionMetrics = new HttpTransactionMetrics();
      start(null, localHttpTransactionMetrics);
      localHttpTransactionMetrics.log("HttpTransaction", "");
    }
    while (true)
    {
      return;
      start(null, null);
    }
  }

  public void start(EsSyncAdapterService.SyncState paramSyncState, HttpTransactionMetrics paramHttpTransactionMetrics)
  {
    try
    {
      boolean bool = this.mAborted;
      if (bool);
      while (true)
      {
        return;
        if (EsLog.isLoggable("HttpTransaction", 3))
          Log.d("HttpTransaction", "Starting op: " + this.mUrl);
        HttpEntity localHttpEntity = createPostData();
        HttpTransaction localHttpTransaction;
        if (localHttpEntity != null)
        {
          localHttpTransaction = new HttpTransaction(this.mMethod, this.mUrl, this.mHttpRequestConfig, localHttpEntity, this);
          label100: if (EsLog.isLoggable("HttpTransaction", 3))
            localHttpTransaction.printHeaders();
          if (paramHttpTransactionMetrics != null)
            paramHttpTransactionMetrics.onBeginTransaction(getName());
          localHttpTransaction.setHttpTransactionMetrics(paramHttpTransactionMetrics);
          this.mCurrentTransaction = localHttpTransaction;
        }
        try
        {
          Exception localException3 = localHttpTransaction.execute();
          EsNetworkData.insertData(this.mContext, this.mAccount, paramHttpTransactionMetrics, localException3);
          this.mCurrentTransaction = null;
          if ((paramSyncState == null) || (paramHttpTransactionMetrics == null))
            continue;
          paramSyncState.getHttpTransactionMetrics().accumulateFrom(paramHttpTransactionMetrics);
          continue;
          localHttpTransaction = new HttpTransaction(this.mMethod, this.mUrl, this.mHttpRequestConfig, this);
          break label100;
        }
        catch (Exception localException2)
        {
          EsNetworkData.insertData(this.mContext, this.mAccount, paramHttpTransactionMetrics, localException2);
          if (!localHttpTransaction.isAborted())
            throw localException2;
        }
        finally
        {
          this.mCurrentTransaction = null;
        }
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        if (EsLog.isLoggable("HttpTransaction", 6))
          Log.e("HttpTransaction", "HttpOperation failed " + this, localException1);
        onHttpTransactionComplete(0, null, localException1);
        if ((paramSyncState != null) && (paramHttpTransactionMetrics != null))
        {
          paramSyncState.getHttpTransactionMetrics().accumulateFrom(paramHttpTransactionMetrics);
          continue;
          this.mCurrentTransaction = null;
        }
      }
    }
    finally
    {
      if ((paramSyncState != null) && (paramHttpTransactionMetrics != null))
        paramSyncState.getHttpTransactionMetrics().accumulateFrom(paramHttpTransactionMetrics);
    }
  }

  public final void startThreaded()
  {
    this.mThreaded = true;
    sExecutorService.execute(new Runnable()
    {
      public final void run()
      {
        HttpOperation.this.start();
      }
    });
  }

  public static abstract interface OperationListener
  {
    public abstract void onOperationComplete(HttpOperation paramHttpOperation);
  }

  private static final class SimpleThreadFactory
    implements ThreadFactory
  {
    public final Thread newThread(Runnable paramRunnable)
    {
      Thread localThread = new Thread(paramRunnable);
      localThread.setPriority(1);
      return localThread;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.HttpOperation
 * JD-Core Version:    0.6.2
 */