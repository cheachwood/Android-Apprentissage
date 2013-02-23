package com.google.android.picasasync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Callable;

final class PicasaSyncManager
{
  private static PicasaSyncManager sInstance;
  private boolean mBackgroundData = false;
  private final Context mContext;
  private volatile SyncSession mCurrentSession;
  private final PicasaFacade mFacade;
  private boolean mHasWifiConnectivity = false;
  private final HashSet<String> mInvalidAccounts = new HashSet();
  private boolean mIsPlugged = false;
  private boolean mIsRoaming = false;
  private final ArrayList<SyncTaskProvider> mProviders = new ArrayList();
  private final Handler mSyncHandler;
  private final PicasaSyncHelper mSyncHelper;
  private ArrayList<SyncRequest> mSyncRequests = new ArrayList();

  private PicasaSyncManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mFacade = PicasaFacade.get(this.mContext);
    this.mSyncHelper = PicasaSyncHelper.getInstance(this.mContext);
    HandlerThread localHandlerThread = new HandlerThread("picasa-sync-manager", 10);
    localHandlerThread.start();
    this.mSyncHandler = new Handler(localHandlerThread.getLooper())
    {
      public final void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default:
          throw new AssertionError("unknown message: " + paramAnonymousMessage.what);
        case 6:
          PicasaSyncManager.access$100(PicasaSyncManager.this);
        case 1:
        case 2:
        case 5:
        case 3:
        case 4:
        }
        while (true)
        {
          return;
          PicasaSyncManager.access$200(PicasaSyncManager.this);
          continue;
          PicasaSyncManager.access$300(PicasaSyncManager.this);
          continue;
          PicasaSyncManager.access$400(PicasaSyncManager.this, (Boolean)paramAnonymousMessage.obj);
          continue;
          PicasaSyncManager.this.updateTasksInternal();
          continue;
          PicasaSyncManager.this.mSyncHelper.syncAccounts(PicasaSyncManager.this.mFacade.getAuthority());
        }
      }
    };
    this.mSyncHandler.sendEmptyMessage(6);
    this.mSyncHandler.sendEmptyMessage(4);
    this.mSyncHandler.sendEmptyMessage(2);
    this.mSyncHandler.sendEmptyMessage(5);
    OnAccountsUpdateListener local1 = new OnAccountsUpdateListener()
    {
      public final void onAccountsUpdated(Account[] paramAnonymousArrayOfAccount)
      {
        Log.i("gp.PicasaSyncManager", "account change detect - update database");
        PicasaSyncManager.this.mSyncHandler.sendEmptyMessage(4);
      }
    };
    AccountManager.get(this.mContext).addOnAccountsUpdatedListener(local1, null, false);
  }

  private boolean acceptSyncTask(SyncTask paramSyncTask)
  {
    boolean bool = false;
    if (paramSyncTask.isBackgroundSync())
      if (!ContentResolver.getMasterSyncAutomatically())
        Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " because master auto sync is off");
    while (true)
    {
      return bool;
      if (!ContentResolver.getSyncAutomatically(new Account(paramSyncTask.syncAccount, "com.google"), this.mFacade.getAuthority()))
      {
        Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " because auto sync is off");
        bool = false;
      }
      else if ((!this.mBackgroundData) && (paramSyncTask.isBackgroundSync()))
      {
        Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " for disabled background data");
        bool = false;
      }
      else if (!this.mFacade.getMasterInfo().enableDownSync)
      {
        Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " because downsync is disabled");
        bool = false;
      }
      else if ((!this.mIsPlugged) && (!paramSyncTask.isSyncOnBattery()))
      {
        Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " on battery");
        bool = false;
      }
      else if (!this.mHasWifiConnectivity)
      {
        if (paramSyncTask.isSyncOnWifiOnly())
        {
          Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " for non-wifi connection");
          bool = false;
        }
        else if ((this.mIsRoaming) && (!paramSyncTask.isSyncOnRoaming()))
        {
          Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " for roaming");
          bool = false;
        }
      }
      else
      {
        String str = Environment.getExternalStorageState();
        if ((str.equals("mounted")) || (str.equals("mounted_ro")));
        for (int i = 1; ; i = 0)
        {
          if ((i != 0) || (!paramSyncTask.isSyncOnExternalStorageOnly()))
            break label406;
          Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " on external storage");
          bool = false;
          break;
        }
        label406: synchronized (this.mInvalidAccounts)
        {
          if (this.mInvalidAccounts.contains(paramSyncTask.syncAccount))
          {
            Log.d("gp.PicasaSyncManager", "reject " + paramSyncTask + " for invalid account: " + Utils.maskDebugInfo(paramSyncTask.syncAccount));
            bool = false;
          }
        }
        bool = true;
      }
    }
  }

  public static PicasaSyncManager get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new PicasaSyncManager(paramContext);
      PicasaSyncManager localPicasaSyncManager = sInstance;
      return localPicasaSyncManager;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private boolean isValidAccount(Account paramAccount)
  {
    Account[] arrayOfAccount = AccountManager.get(this.mContext).getAccountsByType("com.google");
    int i = arrayOfAccount.length;
    int j = 0;
    if (j < i)
    {
      Account localAccount = arrayOfAccount[j];
      if (!paramAccount.name.equals(localAccount.name));
    }
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      j++;
      break;
    }
  }

  private SyncTask nextSyncTaskInternal(String paramString)
  {
    int i = 0;
    int j = this.mProviders.size();
    Object localObject;
    if (i < j)
    {
      SyncTaskProvider localSyncTaskProvider = (SyncTaskProvider)this.mProviders.get(i);
      ArrayList localArrayList = new ArrayList();
      localSyncTaskProvider.collectTasks(localArrayList);
      localObject = null;
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        SyncTask localSyncTask = (SyncTask)localIterator.next();
        localSyncTask.mPriority = i;
        if ((acceptSyncTask(localSyncTask)) && ((localObject == null) || (localSyncTask.syncAccount.equals(paramString))))
          localObject = localSyncTask;
      }
      if (localObject == null);
    }
    while (true)
    {
      return localObject;
      i++;
      break;
      localObject = null;
    }
  }

  private void requestSync(String paramString, SyncState paramSyncState)
  {
    try
    {
      if (this.mSyncRequests.size() == 0)
        this.mSyncHandler.sendEmptyMessage(1);
      this.mSyncRequests.add(new SyncRequest(null, paramSyncState));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void updateTasksInternal()
  {
    SyncSession localSyncSession = this.mCurrentSession;
    SyncTask localSyncTask3;
    if (localSyncSession == null)
    {
      this.mSyncHandler.removeMessages(3);
      localSyncTask3 = nextSyncTaskInternal(null);
      if (localSyncTask3 != null);
    }
    while (true)
    {
      SyncTask localSyncTask1;
      while (true)
      {
        return;
        Account localAccount = new Account(localSyncTask3.syncAccount, "com.google");
        if (isValidAccount(localAccount))
        {
          Bundle localBundle = new Bundle();
          localBundle.putBoolean("picasa-sync-manager-requested", true);
          localBundle.putBoolean("ignore_settings", true);
          Log.d("gp.PicasaSyncManager", "request sync for " + localSyncTask3);
          ContentResolver.requestSync(localAccount, this.mFacade.getAuthority(), localBundle);
        }
        else
        {
          String str = localAccount.name;
          Log.w("gp.PicasaSyncManager", "account: " + Utils.maskDebugInfo(str) + " has been removed ?!");
          synchronized (this.mInvalidAccounts)
          {
            this.mInvalidAccounts.add(str);
            localSyncSession = this.mCurrentSession;
          }
        }
      }
      if (localSyncTask1 != null)
        if (!acceptSyncTask(localSyncTask1))
        {
          Log.d("gp.PicasaSyncManager", "stop task: " + localSyncTask1 + " due to environment change");
          localSyncTask1.cancelSync();
        }
        else
        {
          SyncTask localSyncTask2 = nextSyncTaskInternal(localSyncSession.account);
          if ((localSyncTask2 != null) && (localSyncTask2.mPriority < localSyncTask1.mPriority))
          {
            Log.d("gp.PicasaSyncManager", "cancel task: " + localSyncTask1 + " for " + localSyncTask2);
            localSyncTask1.cancelSync();
          }
        }
    }
  }

  public final void onAccountInitialized(String paramString)
  {
    try
    {
      if (this.mSyncHelper.findUser(paramString) == null)
      {
        SQLiteDatabase localSQLiteDatabase = this.mSyncHelper.getWritableDatabase();
        UserEntry localUserEntry = new UserEntry(paramString);
        UserEntry.SCHEMA.insertOrReplace(localSQLiteDatabase, localUserEntry);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final void onBatteryStateChanged(boolean paramBoolean)
  {
    Handler localHandler = this.mSyncHandler;
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      Message localMessage = Message.obtain(localHandler, 5, localBoolean);
      this.mSyncHandler.sendMessage(localMessage);
      return;
    }
  }

  public final void onEnvironmentChanged()
  {
    this.mSyncHandler.sendEmptyMessage(2);
  }

  // ERROR //
  public final void performSync(SyncSession paramSyncSession)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 511	com/android/gallery3d/common/Utils:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   5: checkcast 449	com/google/android/picasasync/PicasaSyncManager$SyncSession
    //   8: putfield 424	com/google/android/picasasync/PicasaSyncManager:mCurrentSession	Lcom/google/android/picasasync/PicasaSyncManager$SyncSession;
    //   11: aload_1
    //   12: getfield 515	com/google/android/picasasync/PicasaSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   15: getfield 521	android/content/SyncResult:stats	Landroid/content/SyncStats;
    //   18: astore_3
    //   19: ldc_w 523
    //   22: invokestatic 529	com/google/android/picasastore/MetricsUtils:begin	(Ljava/lang/String;)I
    //   25: istore 4
    //   27: new 531	java/util/concurrent/FutureTask
    //   30: dup
    //   31: new 533	com/google/android/picasasync/PicasaSyncManager$GetNextSyncTask
    //   34: dup
    //   35: aload_0
    //   36: aload_1
    //   37: invokespecial 536	com/google/android/picasasync/PicasaSyncManager$GetNextSyncTask:<init>	(Lcom/google/android/picasasync/PicasaSyncManager;Lcom/google/android/picasasync/PicasaSyncManager$SyncSession;)V
    //   40: invokespecial 539	java/util/concurrent/FutureTask:<init>	(Ljava/util/concurrent/Callable;)V
    //   43: astore 5
    //   45: aload_0
    //   46: getfield 99	com/google/android/picasasync/PicasaSyncManager:mSyncHandler	Landroid/os/Handler;
    //   49: aload 5
    //   51: invokevirtual 543	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   54: pop
    //   55: aload 5
    //   57: invokevirtual 545	java/util/concurrent/FutureTask:get	()Ljava/lang/Object;
    //   60: pop
    //   61: iload 4
    //   63: invokestatic 548	com/google/android/picasastore/MetricsUtils:end	(I)V
    //   66: aload_1
    //   67: getfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   70: ifnull +20 -> 90
    //   73: aload_1
    //   74: getfield 461	com/google/android/picasasync/PicasaSyncManager$SyncSession:account	Ljava/lang/String;
    //   77: aload_1
    //   78: getfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   81: getfield 166	com/google/android/picasasync/SyncTask:syncAccount	Ljava/lang/String;
    //   84: invokevirtual 221	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   87: ifne +54 -> 141
    //   90: aload_0
    //   91: aconst_null
    //   92: putfield 424	com/google/android/picasasync/PicasaSyncManager:mCurrentSession	Lcom/google/android/picasasync/PicasaSyncManager$SyncSession;
    //   95: aload_1
    //   96: monitorenter
    //   97: aload_1
    //   98: getfield 551	com/google/android/picasasync/PicasaSyncManager$SyncSession:mSyncCancelled	Z
    //   101: ifne +8 -> 109
    //   104: aload_0
    //   105: lconst_0
    //   106: invokevirtual 301	com/google/android/picasasync/PicasaSyncManager:updateTasks	(J)V
    //   109: aload_1
    //   110: monitorexit
    //   111: aload_0
    //   112: aconst_null
    //   113: putfield 424	com/google/android/picasasync/PicasaSyncManager:mCurrentSession	Lcom/google/android/picasasync/PicasaSyncManager$SyncSession;
    //   116: return
    //   117: astore 7
    //   119: ldc 134
    //   121: ldc_w 553
    //   124: aload 7
    //   126: invokestatic 556	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   129: pop
    //   130: goto -69 -> 61
    //   133: astore_2
    //   134: aload_0
    //   135: aconst_null
    //   136: putfield 424	com/google/android/picasasync/PicasaSyncManager:mCurrentSession	Lcom/google/android/picasasync/PicasaSyncManager$SyncSession;
    //   139: aload_2
    //   140: athrow
    //   141: ldc 134
    //   143: new 136	java/lang/StringBuilder
    //   146: dup
    //   147: ldc_w 558
    //   150: invokespecial 141	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   153: aload_1
    //   154: getfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   157: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   160: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   163: invokestatic 160	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   166: pop
    //   167: aload_1
    //   168: getfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   171: aload_1
    //   172: getfield 515	com/google/android/picasasync/PicasaSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   175: invokevirtual 561	com/google/android/picasasync/SyncTask:performSync	(Landroid/content/SyncResult;)V
    //   178: aload_1
    //   179: getfield 515	com/google/android/picasasync/PicasaSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   182: invokevirtual 564	android/content/SyncResult:hasError	()Z
    //   185: ifeq +29 -> 214
    //   188: ldc 134
    //   190: new 136	java/lang/StringBuilder
    //   193: dup
    //   194: ldc_w 566
    //   197: invokespecial 141	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   200: aload_1
    //   201: getfield 515	com/google/android/picasasync/PicasaSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   204: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   207: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   210: invokestatic 160	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   213: pop
    //   214: aload_1
    //   215: aconst_null
    //   216: putfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   219: aload_3
    //   220: getfield 572	android/content/SyncStats:numIoExceptions	J
    //   223: lconst_0
    //   224: lcmp
    //   225: ifle -206 -> 19
    //   228: ldc 134
    //   230: ldc_w 574
    //   233: invokestatic 356	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   236: pop
    //   237: aload_1
    //   238: invokevirtual 575	com/google/android/picasasync/PicasaSyncManager$SyncSession:cancelSync	()V
    //   241: goto -151 -> 90
    //   244: astore 14
    //   246: ldc 134
    //   248: ldc_w 577
    //   251: aload 14
    //   253: invokestatic 556	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   256: pop
    //   257: aload_3
    //   258: lconst_1
    //   259: aload_3
    //   260: getfield 572	android/content/SyncStats:numIoExceptions	J
    //   263: ladd
    //   264: putfield 572	android/content/SyncStats:numIoExceptions	J
    //   267: aload_1
    //   268: aconst_null
    //   269: putfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   272: goto -53 -> 219
    //   275: astore 11
    //   277: ldc 134
    //   279: ldc_w 577
    //   282: aload 11
    //   284: invokestatic 556	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   287: pop
    //   288: aload_1
    //   289: aconst_null
    //   290: putfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   293: goto -74 -> 219
    //   296: astore 10
    //   298: aload_1
    //   299: aconst_null
    //   300: putfield 453	com/google/android/picasasync/PicasaSyncManager$SyncSession:mCurrentTask	Lcom/google/android/picasasync/SyncTask;
    //   303: aload 10
    //   305: athrow
    //   306: astore 9
    //   308: aload_1
    //   309: monitorexit
    //   310: aload 9
    //   312: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   55	61	117	java/lang/Throwable
    //   11	55	133	finally
    //   55	61	133	finally
    //   61	97	133	finally
    //   119	130	133	finally
    //   214	241	133	finally
    //   267	272	133	finally
    //   288	313	133	finally
    //   141	214	244	java/io/IOException
    //   141	214	275	java/lang/Throwable
    //   141	214	296	finally
    //   246	267	296	finally
    //   277	288	296	finally
    //   97	111	306	finally
  }

  public final void requestAccountSync()
  {
    this.mSyncHandler.sendEmptyMessage(4);
  }

  public final void requestMetadataSync(boolean paramBoolean)
  {
    if (paramBoolean);
    for (SyncState localSyncState = SyncState.METADATA_MANUAL; ; localSyncState = SyncState.METADATA)
    {
      requestSync(null, localSyncState);
      return;
    }
  }

  public final void requestPrefetchSync()
  {
    PhotoPrefetch.onRequestSync(this.mContext);
    requestSync(null, SyncState.PREFETCH_FULL_IMAGE);
    requestSync(null, SyncState.PREFETCH_SCREEN_NAIL);
    requestSync(null, SyncState.PREFETCH_ALBUM_COVER);
  }

  public final void resetSyncStates()
  {
    synchronized (this.mInvalidAccounts)
    {
      this.mInvalidAccounts.clear();
      try
      {
        Iterator localIterator = this.mProviders.iterator();
        while (localIterator.hasNext())
          ((SyncTaskProvider)localIterator.next()).resetSyncStates();
      }
      finally
      {
      }
    }
  }

  public final void updateTasks(long paramLong)
  {
    this.mSyncHandler.sendEmptyMessageDelayed(3, paramLong);
  }

  private final class GetNextSyncTask
    implements Callable<Void>
  {
    private final PicasaSyncManager.SyncSession mSession;

    public GetNextSyncTask(PicasaSyncManager.SyncSession arg2)
    {
      Object localObject;
      this.mSession = localObject;
    }

    private Void call()
    {
      PicasaSyncManager.this.mSyncHandler.removeMessages(3);
      SyncTask localSyncTask = PicasaSyncManager.this.nextSyncTaskInternal(this.mSession.account);
      synchronized (this.mSession)
      {
        if (!this.mSession.mSyncCancelled)
          this.mSession.mCurrentTask = localSyncTask;
      }
      return null;
    }
  }

  private static final class SyncRequest
  {
    public String account;
    public SyncState state;

    public SyncRequest(String paramString, SyncState paramSyncState)
    {
      this.account = paramString;
      this.state = paramSyncState;
    }
  }

  public static final class SyncSession
  {
    public final String account;
    SyncTask mCurrentTask;
    boolean mSyncCancelled;
    public final SyncResult result;

    public SyncSession(String paramString, SyncResult paramSyncResult)
    {
      this.account = paramString;
      this.result = paramSyncResult;
    }

    public final void cancelSync()
    {
      try
      {
        this.mSyncCancelled = true;
        if (this.mCurrentTask != null)
        {
          this.mCurrentTask.cancelSync();
          this.mCurrentTask = null;
        }
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final boolean isSyncCancelled()
    {
      try
      {
        boolean bool = this.mSyncCancelled;
        return bool;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PicasaSyncManager
 * JD-Core Version:    0.6.2
 */