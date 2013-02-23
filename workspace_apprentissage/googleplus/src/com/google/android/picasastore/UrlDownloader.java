package com.google.android.picasastore;

import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

final class UrlDownloader
{
  private final Controller mController;
  private final Executor mExecutor = new ThreadPoolExecutor(0, 3, 60L, TimeUnit.SECONDS, this.mQueue, new PriorityThreadFactory("download-manager", 10));
  private final LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue();
  private final HashMap<String, DownloadTask> mTaskMap = new HashMap();

  public UrlDownloader(Controller paramController)
  {
    this.mController = ((Controller)Utils.checkNotNull(paramController));
  }

  public final InputStream openInputStream(String paramString)
  {
    try
    {
      DownloadTask localDownloadTask = (DownloadTask)this.mTaskMap.get(paramString);
      if (localDownloadTask == null)
      {
        localDownloadTask = new DownloadTask(paramString);
        this.mTaskMap.put(paramString, localDownloadTask);
        this.mExecutor.execute(localDownloadTask);
      }
      SharedInputStream localSharedInputStream = new SharedInputStream(localDownloadTask);
      return localSharedInputStream;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static abstract interface Controller
  {
    public abstract File createTempFile();

    public abstract void onDownloadComplete(String paramString, File paramFile);
  }

  private final class DownloadTask
    implements Runnable
  {
    public long cancelTimeout;
    public File downloadFile;
    public long downloadSize = 0L;
    public final String downloadUrl;
    public RandomAccessFile randomAccessFile;
    public int requestCount;
    public int state = 1;

    public DownloadTask(String arg2)
    {
      Object localObject;
      this.downloadUrl = localObject;
    }

    private void setFinalState(int paramInt)
    {
      this.state = paramInt;
      UrlDownloader.this.mTaskMap.remove(this.downloadUrl);
      UrlDownloader.this.notifyAll();
    }

    public final void releaseReadRequest()
    {
      synchronized (UrlDownloader.this)
      {
        this.requestCount = (-1 + this.requestCount);
        if (this.requestCount == 0)
        {
          this.cancelTimeout = (3000L + SystemClock.elapsedRealtime());
          if ((0x1C & this.state) != 0)
          {
            Utils.closeSilently(this.randomAccessFile);
            if (this.state != 4)
              UrlDownloader.access$000(this.downloadFile);
          }
        }
        return;
      }
    }

    public final void requestRead()
    {
      synchronized (UrlDownloader.this)
      {
        this.requestCount = (1 + this.requestCount);
        return;
      }
    }

    // ERROR //
    public final void run()
    {
      // Byte code:
      //   0: iconst_1
      //   1: istore_1
      //   2: aload_0
      //   3: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   6: astore_2
      //   7: aload_2
      //   8: monitorenter
      //   9: aload_0
      //   10: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   13: iload_1
      //   14: if_icmpne +711 -> 725
      //   17: iload_1
      //   18: invokestatic 85	com/android/gallery3d/common/Utils:assertTrue	(Z)V
      //   21: aload_0
      //   22: getfield 53	com/google/android/picasastore/UrlDownloader$DownloadTask:requestCount	I
      //   25: ifne +14 -> 39
      //   28: aload_0
      //   29: bipush 16
      //   31: invokespecial 87	com/google/android/picasastore/UrlDownloader$DownloadTask:setFinalState	(I)V
      //   34: aload_2
      //   35: monitorexit
      //   36: goto +688 -> 724
      //   39: aload_0
      //   40: iconst_2
      //   41: putfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   44: aload_2
      //   45: monitorexit
      //   46: new 89	java/lang/StringBuilder
      //   49: dup
      //   50: ldc 91
      //   52: invokespecial 94	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   55: aload_0
      //   56: getfield 33	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadUrl	Ljava/lang/String;
      //   59: invokestatic 98	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
      //   62: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   65: invokevirtual 106	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   68: invokestatic 112	com/google/android/picasastore/MetricsUtils:begin	(Ljava/lang/String;)I
      //   71: istore 4
      //   73: aconst_null
      //   74: astore 5
      //   76: aload_0
      //   77: aload_0
      //   78: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   81: invokestatic 116	com/google/android/picasastore/UrlDownloader:access$200	(Lcom/google/android/picasastore/UrlDownloader;)Lcom/google/android/picasastore/UrlDownloader$Controller;
      //   84: invokeinterface 122 1 0
      //   89: putfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   92: aload_0
      //   93: new 124	java/io/RandomAccessFile
      //   96: dup
      //   97: aload_0
      //   98: getfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   101: ldc 126
      //   103: invokespecial 129	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   106: putfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   109: aload_0
      //   110: getfield 33	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadUrl	Ljava/lang/String;
      //   113: invokestatic 135	com/google/android/picasastore/HttpUtils:openInputStream	(Ljava/lang/String;)Ljava/io/InputStream;
      //   116: astore 5
      //   118: sipush 2048
      //   121: newarray byte
      //   123: astore 15
      //   125: invokestatic 61	android/os/SystemClock:elapsedRealtime	()J
      //   128: lstore 16
      //   130: aload 5
      //   132: aload 15
      //   134: invokevirtual 141	java/io/InputStream:read	([B)I
      //   137: istore 19
      //   139: iload 19
      //   141: ifle +332 -> 473
      //   144: aload_0
      //   145: monitorenter
      //   146: aload_0
      //   147: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   150: aload_0
      //   151: getfield 29	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadSize	J
      //   154: invokevirtual 145	java/io/RandomAccessFile:seek	(J)V
      //   157: aload_0
      //   158: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   161: aload 15
      //   163: iconst_0
      //   164: iload 19
      //   166: invokevirtual 149	java/io/RandomAccessFile:write	([BII)V
      //   169: aload_0
      //   170: monitorexit
      //   171: aload_0
      //   172: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   175: astore 27
      //   177: aload 27
      //   179: monitorenter
      //   180: aload_0
      //   181: getfield 53	com/google/android/picasastore/UrlDownloader$DownloadTask:requestCount	I
      //   184: ifne +247 -> 431
      //   187: aload_0
      //   188: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   191: invokestatic 153	com/google/android/picasastore/UrlDownloader:access$300	(Lcom/google/android/picasastore/UrlDownloader;)Ljava/util/concurrent/LinkedBlockingQueue;
      //   194: invokevirtual 159	java/util/concurrent/LinkedBlockingQueue:isEmpty	()Z
      //   197: ifeq +14 -> 211
      //   200: invokestatic 61	android/os/SystemClock:elapsedRealtime	()J
      //   203: aload_0
      //   204: getfield 63	com/google/android/picasastore/UrlDownloader$DownloadTask:cancelTimeout	J
      //   207: lcmp
      //   208: ifle +223 -> 431
      //   211: aload_0
      //   212: bipush 16
      //   214: invokespecial 87	com/google/android/picasastore/UrlDownloader$DownloadTask:setFinalState	(I)V
      //   217: aload 27
      //   219: monitorexit
      //   220: invokestatic 61	android/os/SystemClock:elapsedRealtime	()J
      //   223: lload 16
      //   225: lsub
      //   226: invokestatic 162	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
      //   229: aload_0
      //   230: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   233: iconst_4
      //   234: if_icmpeq +8 -> 242
      //   237: aload 5
      //   239: invokestatic 166	com/google/android/picasastore/HttpUtils:abortConnectionSilently	(Ljava/io/InputStream;)V
      //   242: aload 5
      //   244: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   247: aload_0
      //   248: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   251: astore 29
      //   253: aload 29
      //   255: monitorenter
      //   256: aload_0
      //   257: getfield 53	com/google/android/picasastore/UrlDownloader$DownloadTask:requestCount	I
      //   260: ifne +25 -> 285
      //   263: aload_0
      //   264: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   267: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   270: aload_0
      //   271: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   274: iconst_4
      //   275: if_icmpeq +10 -> 285
      //   278: aload_0
      //   279: getfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   282: invokestatic 77	com/google/android/picasastore/UrlDownloader:access$000	(Ljava/io/File;)V
      //   285: aload 29
      //   287: monitorexit
      //   288: iload 4
      //   290: ldc 168
      //   292: invokestatic 172	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
      //   295: goto +429 -> 724
      //   298: astore_3
      //   299: aload_2
      //   300: monitorexit
      //   301: aload_3
      //   302: athrow
      //   303: astore 26
      //   305: aload_0
      //   306: monitorexit
      //   307: aload 26
      //   309: athrow
      //   310: astore 18
      //   312: invokestatic 61	android/os/SystemClock:elapsedRealtime	()J
      //   315: lload 16
      //   317: lsub
      //   318: invokestatic 162	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
      //   321: aload 18
      //   323: athrow
      //   324: astore 9
      //   326: ldc 174
      //   328: ldc 176
      //   330: aload 9
      //   332: invokestatic 182	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   335: pop
      //   336: aload_0
      //   337: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   340: astore 11
      //   342: aload 11
      //   344: monitorenter
      //   345: aload_0
      //   346: bipush 8
      //   348: invokespecial 87	com/google/android/picasastore/UrlDownloader$DownloadTask:setFinalState	(I)V
      //   351: aload 11
      //   353: monitorexit
      //   354: aload_0
      //   355: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   358: iconst_4
      //   359: if_icmpeq +8 -> 367
      //   362: aload 5
      //   364: invokestatic 166	com/google/android/picasastore/HttpUtils:abortConnectionSilently	(Ljava/io/InputStream;)V
      //   367: aload 5
      //   369: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   372: aload_0
      //   373: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   376: astore 13
      //   378: aload 13
      //   380: monitorenter
      //   381: aload_0
      //   382: getfield 53	com/google/android/picasastore/UrlDownloader$DownloadTask:requestCount	I
      //   385: ifne +25 -> 410
      //   388: aload_0
      //   389: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   392: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   395: aload_0
      //   396: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   399: iconst_4
      //   400: if_icmpeq +10 -> 410
      //   403: aload_0
      //   404: getfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   407: invokestatic 77	com/google/android/picasastore/UrlDownloader:access$000	(Ljava/io/File;)V
      //   410: aload 13
      //   412: monitorexit
      //   413: iload 4
      //   415: ldc 168
      //   417: invokestatic 172	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
      //   420: goto +304 -> 724
      //   423: astore 30
      //   425: aload 29
      //   427: monitorexit
      //   428: aload 30
      //   430: athrow
      //   431: aload_0
      //   432: aload_0
      //   433: getfield 29	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadSize	J
      //   436: iload 19
      //   438: i2l
      //   439: ladd
      //   440: putfield 29	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadSize	J
      //   443: aload_0
      //   444: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   447: invokevirtual 50	java/lang/Object:notifyAll	()V
      //   450: aload 27
      //   452: monitorexit
      //   453: aload 5
      //   455: aload 15
      //   457: invokevirtual 141	java/io/InputStream:read	([B)I
      //   460: istore 19
      //   462: goto -323 -> 139
      //   465: astore 28
      //   467: aload 27
      //   469: monitorexit
      //   470: aload 28
      //   472: athrow
      //   473: invokestatic 61	android/os/SystemClock:elapsedRealtime	()J
      //   476: lload 16
      //   478: lsub
      //   479: invokestatic 162	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
      //   482: aload_0
      //   483: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   486: invokevirtual 186	java/io/RandomAccessFile:getFD	()Ljava/io/FileDescriptor;
      //   489: invokevirtual 191	java/io/FileDescriptor:sync	()V
      //   492: aload_0
      //   493: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   496: invokestatic 116	com/google/android/picasastore/UrlDownloader:access$200	(Lcom/google/android/picasastore/UrlDownloader;)Lcom/google/android/picasastore/UrlDownloader$Controller;
      //   499: aload_0
      //   500: getfield 33	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadUrl	Ljava/lang/String;
      //   503: aload_0
      //   504: getfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   507: invokeinterface 195 3 0
      //   512: aload_0
      //   513: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   516: astore 22
      //   518: aload 22
      //   520: monitorenter
      //   521: aload_0
      //   522: iconst_4
      //   523: invokespecial 87	com/google/android/picasastore/UrlDownloader$DownloadTask:setFinalState	(I)V
      //   526: aload 22
      //   528: monitorexit
      //   529: aload_0
      //   530: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   533: iconst_4
      //   534: if_icmpeq +8 -> 542
      //   537: aload 5
      //   539: invokestatic 166	com/google/android/picasastore/HttpUtils:abortConnectionSilently	(Ljava/io/InputStream;)V
      //   542: aload 5
      //   544: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   547: aload_0
      //   548: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   551: astore 24
      //   553: aload 24
      //   555: monitorenter
      //   556: aload_0
      //   557: getfield 53	com/google/android/picasastore/UrlDownloader$DownloadTask:requestCount	I
      //   560: ifne +25 -> 585
      //   563: aload_0
      //   564: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   567: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   570: aload_0
      //   571: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   574: iconst_4
      //   575: if_icmpeq +10 -> 585
      //   578: aload_0
      //   579: getfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   582: invokestatic 77	com/google/android/picasastore/UrlDownloader:access$000	(Ljava/io/File;)V
      //   585: aload 24
      //   587: monitorexit
      //   588: iload 4
      //   590: ldc 168
      //   592: invokestatic 172	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
      //   595: goto +129 -> 724
      //   598: astore 20
      //   600: ldc 174
      //   602: ldc 197
      //   604: aload 20
      //   606: invokestatic 182	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   609: pop
      //   610: goto -98 -> 512
      //   613: astore 6
      //   615: aload_0
      //   616: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   619: iconst_4
      //   620: if_icmpeq +8 -> 628
      //   623: aload 5
      //   625: invokestatic 166	com/google/android/picasastore/HttpUtils:abortConnectionSilently	(Ljava/io/InputStream;)V
      //   628: aload 5
      //   630: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   633: aload_0
      //   634: getfield 24	com/google/android/picasastore/UrlDownloader$DownloadTask:this$0	Lcom/google/android/picasastore/UrlDownloader;
      //   637: astore 7
      //   639: aload 7
      //   641: monitorenter
      //   642: aload_0
      //   643: getfield 53	com/google/android/picasastore/UrlDownloader$DownloadTask:requestCount	I
      //   646: ifne +25 -> 671
      //   649: aload_0
      //   650: getfield 65	com/google/android/picasastore/UrlDownloader$DownloadTask:randomAccessFile	Ljava/io/RandomAccessFile;
      //   653: invokestatic 71	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
      //   656: aload_0
      //   657: getfield 31	com/google/android/picasastore/UrlDownloader$DownloadTask:state	I
      //   660: iconst_4
      //   661: if_icmpeq +10 -> 671
      //   664: aload_0
      //   665: getfield 73	com/google/android/picasastore/UrlDownloader$DownloadTask:downloadFile	Ljava/io/File;
      //   668: invokestatic 77	com/google/android/picasastore/UrlDownloader:access$000	(Ljava/io/File;)V
      //   671: aload 7
      //   673: monitorexit
      //   674: iload 4
      //   676: ldc 168
      //   678: invokestatic 172	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
      //   681: aload 6
      //   683: athrow
      //   684: astore 23
      //   686: aload 22
      //   688: monitorexit
      //   689: aload 23
      //   691: athrow
      //   692: astore 25
      //   694: aload 24
      //   696: monitorexit
      //   697: aload 25
      //   699: athrow
      //   700: astore 12
      //   702: aload 11
      //   704: monitorexit
      //   705: aload 12
      //   707: athrow
      //   708: astore 14
      //   710: aload 13
      //   712: monitorexit
      //   713: aload 14
      //   715: athrow
      //   716: astore 8
      //   718: aload 7
      //   720: monitorexit
      //   721: aload 8
      //   723: athrow
      //   724: return
      //   725: iconst_0
      //   726: istore_1
      //   727: goto -710 -> 17
      //
      // Exception table:
      //   from	to	target	type
      //   9	46	298	finally
      //   146	171	303	finally
      //   130	146	310	finally
      //   171	180	310	finally
      //   305	310	310	finally
      //   453	473	310	finally
      //   76	130	324	java/lang/Throwable
      //   220	229	324	java/lang/Throwable
      //   312	324	324	java/lang/Throwable
      //   473	482	324	java/lang/Throwable
      //   512	521	324	java/lang/Throwable
      //   600	610	324	java/lang/Throwable
      //   686	692	324	java/lang/Throwable
      //   256	288	423	finally
      //   180	220	465	finally
      //   431	453	465	finally
      //   482	512	598	java/lang/Throwable
      //   76	130	613	finally
      //   220	229	613	finally
      //   312	324	613	finally
      //   326	345	613	finally
      //   473	482	613	finally
      //   482	512	613	finally
      //   512	521	613	finally
      //   600	610	613	finally
      //   686	692	613	finally
      //   702	708	613	finally
      //   521	529	684	finally
      //   556	588	692	finally
      //   345	354	700	finally
      //   381	413	708	finally
      //   642	674	716	finally
    }
  }

  private static final class PriorityThreadFactory
    implements ThreadFactory
  {
    private final String mName;
    private final AtomicInteger mNumber = new AtomicInteger();
    private final int mPriority;

    public PriorityThreadFactory(String paramString, int paramInt)
    {
      this.mName = paramString;
      this.mPriority = 10;
    }

    public final Thread newThread(Runnable paramRunnable)
    {
      return new Thread(paramRunnable, this.mName + '-' + this.mNumber.getAndIncrement())
      {
        public final void run()
        {
          Process.setThreadPriority(UrlDownloader.PriorityThreadFactory.this.mPriority);
          super.run();
        }
      };
    }
  }

  private final class SharedInputStream extends InputStream
  {
    private long mOffset = 0L;
    private UrlDownloader.DownloadTask mTask;

    public SharedInputStream(UrlDownloader.DownloadTask arg2)
    {
      Object localObject;
      this.mTask = localObject;
      this.mTask.requestRead();
    }

    public final void close()
    {
      try
      {
        if (this.mTask == null)
          return;
        UrlDownloader.DownloadTask localDownloadTask = this.mTask;
        this.mTask = null;
        localDownloadTask.releaseReadRequest();
      }
      finally
      {
      }
    }

    protected final void finalize()
      throws Throwable
    {
      try
      {
        super.finalize();
        return;
      }
      finally
      {
        if (this.mTask != null)
          Log.w("UrlDownloader", "unclosed input stream");
        Utils.closeSilently(this);
      }
    }

    public final int read()
      throws IOException
    {
      byte[] arrayOfByte = new byte[1];
      if (read(arrayOfByte, 0, 1) > 0);
      for (int i = 0xFF & arrayOfByte[1]; ; i = -1)
        return i;
    }

    public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = 0;
      if (paramInt2 == 0)
        return i;
      UrlDownloader.DownloadTask localDownloadTask = this.mTask;
      while (true)
      {
        while (true)
        {
          long l;
          synchronized (UrlDownloader.this)
          {
            l = localDownloadTask.downloadSize;
            if (l <= this.mOffset)
            {
              if ((0x3 & localDownloadTask.state) == 0)
                break label189;
              k = 1;
              if (k != 0)
              {
                Utils.waitWithoutInterrupt(UrlDownloader.this);
                l = localDownloadTask.downloadSize;
                continue;
              }
            }
            if (localDownloadTask.state == 8)
              throw new IOException("download fail!");
          }
          int j = (int)Math.min(paramInt2, l - this.mOffset);
          if (j == 0)
          {
            i = -1;
            break;
          }
          try
          {
            localDownloadTask.randomAccessFile.seek(this.mOffset);
            i = localDownloadTask.randomAccessFile.read(paramArrayOfByte, paramInt1, j);
            this.mOffset += i;
            break;
          }
          finally
          {
            localObject2 = finally;
            throw localObject2;
          }
        }
        label189: int k = 0;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.UrlDownloader
 * JD-Core Version:    0.6.2
 */