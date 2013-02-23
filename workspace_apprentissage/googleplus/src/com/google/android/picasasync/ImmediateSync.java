package com.google.android.picasasync;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import com.android.gallery3d.util.ThreadPool;
import com.android.gallery3d.util.ThreadPool.Job;
import com.google.android.picasastore.MetricsUtils;
import java.util.HashMap;

final class ImmediateSync
{
  private static ImmediateSync sInstance;
  private final HashMap<String, Task> mCompleteTaskMap = new HashMap();
  private final Context mContext;
  private final HashMap<String, Task> mPendingTaskMap = new HashMap();
  private final ThreadPool mThreadPool = new ThreadPool();

  private ImmediateSync(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private void completeTask(Task paramTask)
  {
    try
    {
      String str = paramTask.taskId;
      if (this.mPendingTaskMap.remove(str) != paramTask)
        Log.d("ImmediateSync", "new task added, ignored old:" + str);
      while (true)
      {
        return;
        this.mCompleteTaskMap.put(str, paramTask);
        Uri localUri = PicasaFacade.get(this.mContext).getSyncRequestUri().buildUpon().appendPath(str).build();
        this.mContext.getContentResolver().notifyChange(localUri, null, false);
        if (paramTask.syncResultCode != 0)
          Log.d("ImmediateSync", "sync " + str + " incomplete " + paramTask.syncResultCode);
      }
    }
    finally
    {
    }
  }

  public static ImmediateSync get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new ImmediateSync(paramContext);
      ImmediateSync localImmediateSync = sInstance;
      return localImmediateSync;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void requestSyncAlbumList(String paramString, final String[] paramArrayOfString)
  {
    Task local1 = new Task(paramString, paramArrayOfString)
    {
      // ERROR //
      protected final int doSync()
      {
        // Byte code:
        //   0: invokestatic 32	android/content/ContentResolver:getMasterSyncAutomatically	()Z
        //   3: ifne +17 -> 20
        //   6: ldc 34
        //   8: ldc 36
        //   10: invokestatic 42	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   13: pop
        //   14: iconst_0
        //   15: istore 14
        //   17: iload 14
        //   19: ireturn
        //   20: ldc 44
        //   22: invokestatic 50	com/google/android/picasastore/MetricsUtils:begin	(Ljava/lang/String;)I
        //   25: istore_1
        //   26: aload_0
        //   27: getfield 17	com/google/android/picasasync/ImmediateSync$1:this$0	Lcom/google/android/picasasync/ImmediateSync;
        //   30: invokestatic 54	com/google/android/picasasync/ImmediateSync:access$100	(Lcom/google/android/picasasync/ImmediateSync;)Landroid/content/Context;
        //   33: invokestatic 60	com/google/android/picasasync/PicasaSyncHelper:getInstance	(Landroid/content/Context;)Lcom/google/android/picasasync/PicasaSyncHelper;
        //   36: astore_2
        //   37: aload_0
        //   38: getfield 19	com/google/android/picasasync/ImmediateSync$1:val$accountArgs	[Ljava/lang/String;
        //   41: astore_3
        //   42: aload_3
        //   43: ifnonnull +123 -> 166
        //   46: aload_2
        //   47: invokevirtual 64	com/google/android/picasasync/PicasaSyncHelper:getUsers	()Ljava/util/ArrayList;
        //   50: astore 25
        //   52: new 66	java/util/ArrayList
        //   55: dup
        //   56: invokespecial 69	java/util/ArrayList:<init>	()V
        //   59: astore 26
        //   61: aload_0
        //   62: getfield 17	com/google/android/picasasync/ImmediateSync$1:this$0	Lcom/google/android/picasasync/ImmediateSync;
        //   65: invokestatic 54	com/google/android/picasasync/ImmediateSync:access$100	(Lcom/google/android/picasasync/ImmediateSync;)Landroid/content/Context;
        //   68: invokestatic 75	com/google/android/picasasync/PicasaFacade:get	(Landroid/content/Context;)Lcom/google/android/picasasync/PicasaFacade;
        //   71: invokevirtual 79	com/google/android/picasasync/PicasaFacade:getAuthority	()Ljava/lang/String;
        //   74: astore 27
        //   76: iconst_0
        //   77: istore 28
        //   79: aload 25
        //   81: invokeinterface 84 1 0
        //   86: istore 29
        //   88: iload 28
        //   90: iload 29
        //   92: if_icmpge +57 -> 149
        //   95: aload 25
        //   97: iload 28
        //   99: invokeinterface 87 2 0
        //   104: checkcast 89	com/google/android/picasasync/UserEntry
        //   107: getfield 93	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
        //   110: astore 30
        //   112: new 95	android/accounts/Account
        //   115: dup
        //   116: aload 30
        //   118: ldc 97
        //   120: invokespecial 100	android/accounts/Account:<init>	(Ljava/lang/String;Ljava/lang/String;)V
        //   123: astore 31
        //   125: aload 31
        //   127: aload 27
        //   129: invokestatic 104	android/content/ContentResolver:getSyncAutomatically	(Landroid/accounts/Account;Ljava/lang/String;)Z
        //   132: ifeq +11 -> 143
        //   135: aload 26
        //   137: aload 30
        //   139: invokevirtual 108	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   142: pop
        //   143: iinc 28 1
        //   146: goto -58 -> 88
        //   149: aload 26
        //   151: aload 26
        //   153: invokevirtual 109	java/util/ArrayList:size	()I
        //   156: anewarray 111	java/lang/String
        //   159: invokevirtual 115	java/util/ArrayList:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
        //   162: checkcast 116	[Ljava/lang/String;
        //   165: astore_3
        //   166: ldc 34
        //   168: new 118	java/lang/StringBuilder
        //   171: dup
        //   172: ldc 120
        //   174: invokespecial 123	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   177: aload_3
        //   178: arraylength
        //   179: invokevirtual 127	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   182: ldc 129
        //   184: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   187: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   190: invokestatic 42	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   193: pop
        //   194: new 137	android/content/SyncResult
        //   197: dup
        //   198: invokespecial 138	android/content/SyncResult:<init>	()V
        //   201: astore 6
        //   203: aload_0
        //   204: getfield 17	com/google/android/picasasync/ImmediateSync$1:this$0	Lcom/google/android/picasasync/ImmediateSync;
        //   207: astore 7
        //   209: aload 7
        //   211: monitorenter
        //   212: aload_0
        //   213: invokevirtual 141	com/google/android/picasasync/ImmediateSync$1:syncInterrupted	()Z
        //   216: ifeq +18 -> 234
        //   219: aload 7
        //   221: monitorexit
        //   222: iload_1
        //   223: ldc 143
        //   225: invokestatic 147	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
        //   228: iconst_1
        //   229: istore 14
        //   231: goto -214 -> 17
        //   234: aload_0
        //   235: aload_2
        //   236: aload 6
        //   238: invokestatic 153	java/lang/Thread:currentThread	()Ljava/lang/Thread;
        //   241: invokevirtual 157	com/google/android/picasasync/PicasaSyncHelper:createSyncContext	(Landroid/content/SyncResult;Ljava/lang/Thread;)Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
        //   244: putfield 161	com/google/android/picasasync/ImmediateSync$1:syncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
        //   247: aload 7
        //   249: monitorexit
        //   250: aload_3
        //   251: astore 9
        //   253: aload_3
        //   254: arraylength
        //   255: istore 10
        //   257: iconst_0
        //   258: istore 11
        //   260: iload 11
        //   262: iload 10
        //   264: if_icmpge +159 -> 423
        //   267: aload 9
        //   269: iload 11
        //   271: aaload
        //   272: astore 15
        //   274: aload_2
        //   275: aload 15
        //   277: invokevirtual 165	com/google/android/picasasync/PicasaSyncHelper:findUser	(Ljava/lang/String;)Lcom/google/android/picasasync/UserEntry;
        //   280: astore 16
        //   282: aload_2
        //   283: aload 15
        //   285: invokevirtual 169	com/google/android/picasasync/PicasaSyncHelper:isPicasaAccount	(Ljava/lang/String;)Z
        //   288: istore 24
        //   290: iload 24
        //   292: istore 19
        //   294: iload 19
        //   296: ifne +66 -> 362
        //   299: ldc 34
        //   301: new 118	java/lang/StringBuilder
        //   304: dup
        //   305: ldc 171
        //   307: invokespecial 123	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   310: aload 15
        //   312: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   315: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   318: invokestatic 174	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
        //   321: pop
        //   322: goto +180 -> 502
        //   325: astore 8
        //   327: aload 7
        //   329: monitorexit
        //   330: aload 8
        //   332: athrow
        //   333: astore 4
        //   335: iload_1
        //   336: ldc 143
        //   338: invokestatic 147	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
        //   341: aload 4
        //   343: athrow
        //   344: astore 17
        //   346: ldc 34
        //   348: ldc 176
        //   350: aload 17
        //   352: invokestatic 179	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   355: pop
        //   356: iconst_0
        //   357: istore 19
        //   359: goto -65 -> 294
        //   362: aload 16
        //   364: ifnull +85 -> 449
        //   367: ldc 34
        //   369: new 118	java/lang/StringBuilder
        //   372: dup
        //   373: ldc 181
        //   375: invokespecial 123	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   378: aload 15
        //   380: invokestatic 187	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
        //   383: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   386: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   389: invokestatic 42	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   392: pop
        //   393: aload_0
        //   394: getfield 161	com/google/android/picasasync/ImmediateSync$1:syncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
        //   397: aload 15
        //   399: invokevirtual 192	com/google/android/picasasync/PicasaSyncHelper$SyncContext:setAccount	(Ljava/lang/String;)Z
        //   402: pop
        //   403: aload_2
        //   404: aload_0
        //   405: getfield 161	com/google/android/picasasync/ImmediateSync$1:syncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
        //   408: aload 16
        //   410: invokevirtual 196	com/google/android/picasasync/PicasaSyncHelper:syncAlbumsForUser	(Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;Lcom/google/android/picasasync/UserEntry;)V
        //   413: aload_0
        //   414: getfield 161	com/google/android/picasasync/ImmediateSync$1:syncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
        //   417: invokevirtual 197	com/google/android/picasasync/PicasaSyncHelper$SyncContext:syncInterrupted	()Z
        //   420: ifeq +82 -> 502
        //   423: aload_0
        //   424: getfield 161	com/google/android/picasasync/ImmediateSync$1:syncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
        //   427: invokevirtual 197	com/google/android/picasasync/PicasaSyncHelper$SyncContext:syncInterrupted	()Z
        //   430: istore 12
        //   432: iload 12
        //   434: ifeq +44 -> 478
        //   437: iconst_1
        //   438: istore 14
        //   440: iload_1
        //   441: ldc 143
        //   443: invokestatic 147	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
        //   446: goto -429 -> 17
        //   449: ldc 34
        //   451: new 118	java/lang/StringBuilder
        //   454: dup
        //   455: ldc 199
        //   457: invokespecial 123	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   460: aload 15
        //   462: invokestatic 187	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
        //   465: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   468: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   471: invokestatic 174	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
        //   474: pop
        //   475: goto -62 -> 413
        //   478: aload 6
        //   480: invokevirtual 202	android/content/SyncResult:hasError	()Z
        //   483: istore 13
        //   485: iload 13
        //   487: ifeq +9 -> 496
        //   490: iconst_2
        //   491: istore 14
        //   493: goto -53 -> 440
        //   496: iconst_0
        //   497: istore 14
        //   499: goto -59 -> 440
        //   502: iinc 11 1
        //   505: goto -245 -> 260
        //
        // Exception table:
        //   from	to	target	type
        //   212	222	325	finally
        //   234	250	325	finally
        //   166	212	333	finally
        //   253	282	333	finally
        //   282	290	333	finally
        //   299	333	333	finally
        //   346	432	333	finally
        //   449	485	333	finally
        //   282	290	344	java/lang/Exception
      }
    };
    this.mPendingTaskMap.put(paramString, local1);
    this.mThreadPool.submit(local1);
  }

  public final boolean cancelTask(String paramString)
  {
    boolean bool = true;
    try
    {
      Log.d("ImmediateSync", "cancel sync " + paramString);
      Task localTask = (Task)this.mPendingTaskMap.get(paramString);
      if ((localTask != null) && (localTask.refCount > 0))
      {
        int i = -1 + localTask.refCount;
        localTask.refCount = i;
        if (i == 0)
        {
          localTask.syncResultCode = 1;
          if (localTask.syncContext != null)
            localTask.syncContext.stopSync();
        }
        return bool;
      }
      bool = false;
    }
    finally
    {
    }
  }

  public final int getResult(String paramString)
  {
    try
    {
      Task localTask = (Task)this.mCompleteTaskMap.get(paramString);
      if (localTask == null)
        localTask = (Task)this.mPendingTaskMap.get(paramString);
      if (localTask == null);
      for (int i = 3; ; i = localTask.syncResultCode)
        return i;
    }
    finally
    {
    }
  }

  public final String requestSyncAlbum(String paramString)
  {
    PicasaDatabaseHelper localPicasaDatabaseHelper;
    final AlbumEntry localAlbumEntry;
    try
    {
      localPicasaDatabaseHelper = PicasaDatabaseHelper.get(this.mContext);
      localAlbumEntry = localPicasaDatabaseHelper.getAlbumEntry$2582d372(paramString);
      if (localAlbumEntry == null)
        throw new IllegalArgumentException("album does not exist");
    }
    finally
    {
    }
    final String str1 = localPicasaDatabaseHelper.getUserAccount(localAlbumEntry.userId);
    String str2 = str1.hashCode() + "." + paramString;
    Task localTask = (Task)this.mPendingTaskMap.get(str2);
    if ((localTask != null) && (localTask.addRequester()))
      Log.d("ImmediateSync", "task already exists:" + str2);
    while (true)
    {
      return str2;
      this.mCompleteTaskMap.remove(str2);
      Task local2 = new Task(str2, str1)
      {
        protected final int doSync()
        {
          int i = 1;
          int j = 0;
          if (!ContentResolver.getMasterSyncAutomatically())
            Log.d("ImmediateSync", "master auto sync is off");
          while (true)
          {
            return j;
            if (ContentResolver.getSyncAutomatically(new Account(str1, "com.google"), PicasaFacade.get(ImmediateSync.this.mContext).getAuthority()))
              break;
            Log.d("ImmediateSync", "auto sync is off on " + Utils.maskDebugInfo(str1));
            j = 0;
          }
          int k = MetricsUtils.begin("ImmediateSync.album");
          while (true)
          {
            SyncResult localSyncResult;
            try
            {
              while (true)
              {
                PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(ImmediateSync.this.mContext);
                localSyncResult = new SyncResult();
                synchronized (ImmediateSync.this)
                {
                  if (syncInterrupted())
                  {
                    MetricsUtils.endWithReport(k, "picasa.sync.metadata");
                    j = i;
                    break;
                  }
                  this.syncContext = localPicasaSyncHelper.createSyncContext(localSyncResult, Thread.currentThread());
                  this.syncContext.setAccount(str1);
                  Log.d("ImmediateSync", "sync album for " + Utils.maskDebugInfo(str1) + "/" + localAlbumEntry.id);
                  localPicasaSyncHelper.syncPhotosForAlbum(this.syncContext, localAlbumEntry);
                  boolean bool1 = this.syncContext.syncInterrupted();
                  if (bool1)
                  {
                    MetricsUtils.endWithReport(k, "picasa.sync.metadata");
                    j = i;
                  }
                }
              }
            }
            finally
            {
              MetricsUtils.endWithReport(k, "picasa.sync.metadata");
            }
            boolean bool2 = localSyncResult.hasError();
            if (bool2)
              i = 2;
            else
              i = 0;
          }
        }
      };
      this.mPendingTaskMap.put(str2, local2);
      this.mThreadPool.submit(local2);
    }
  }

  public final String requestSyncAlbumListForAccount(String paramString)
  {
    try
    {
      String str = String.valueOf(paramString.hashCode());
      Task localTask = (Task)this.mPendingTaskMap.get(str);
      if ((localTask != null) && (localTask.addRequester()))
        Log.d("ImmediateSync", "task already exists:" + str);
      while (true)
      {
        return str;
        this.mCompleteTaskMap.remove(str);
        requestSyncAlbumList(str, new String[] { paramString });
      }
    }
    finally
    {
    }
  }

  public final String requestSyncAlbumListForAllAccounts()
  {
    try
    {
      Task localTask = (Task)this.mPendingTaskMap.get("all");
      if ((localTask != null) && (localTask.addRequester()))
        Log.d("ImmediateSync", "task already exists:all");
      for (String str = "all"; ; str = "all")
      {
        return str;
        this.mCompleteTaskMap.remove("all");
        requestSyncAlbumList("all", null);
      }
    }
    finally
    {
    }
  }

  private abstract class Task
    implements ThreadPool.Job<Void>
  {
    public int refCount = 1;
    public PicasaSyncHelper.SyncContext syncContext;
    public int syncResultCode = -1;
    public final String taskId;

    Task(String arg2)
    {
      Object localObject;
      this.taskId = localObject;
    }

    private Void run$44b8b4b6()
    {
      try
      {
        int i = doSync();
        synchronized (ImmediateSync.this)
        {
          if (this.syncResultCode == -1)
            this.syncResultCode = i;
          return null;
        }
      }
      finally
      {
        ImmediateSync.this.completeTask(this);
      }
    }

    final boolean addRequester()
    {
      if ((this.syncResultCode == -1) || (this.syncResultCode == 0))
        this.refCount = (1 + this.refCount);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    protected abstract int doSync();

    protected final boolean syncInterrupted()
    {
      int i = 1;
      synchronized (ImmediateSync.this)
      {
        if (this.syncResultCode == i)
          return i;
        int j = 0;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.ImmediateSync
 * JD-Core Version:    0.6.2
 */