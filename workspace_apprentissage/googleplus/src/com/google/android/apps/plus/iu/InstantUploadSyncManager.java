package com.google.android.apps.plus.iu;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import java.util.HashSet;
import java.util.concurrent.Callable;

public final class InstantUploadSyncManager
{
  private static final String[] PROJECTION_ENABLE_ACCOUNT = { "auto_upload_enabled", "auto_upload_account_name", "instant_share_eventid", "instant_share_starttime", "instant_share_endtime" };
  private static final String UPLOAD_TASK_CLEANUP_DELETE_WHERE = "media_record_id NOT IN ( SELECT _id FROM " + MediaRecordEntry.SCHEMA.getTableName() + " WHERE upload_account" + " == ? AND " + "upload_state < " + 200 + " )";
  private static InstantUploadSyncManager sInstance;
  private boolean mBackgroundData = false;
  private final Context mContext;
  private volatile SyncSession mCurrentSession;
  private boolean mHasWifiConnectivity = false;
  private final HashSet<String> mInvalidAccounts = new HashSet();
  private boolean mIsPlugged = false;
  private boolean mIsRoaming = false;
  SyncTaskProvider mProvider;
  private final Handler mSyncHandler;
  private final PicasaSyncHelper mSyncHelper;

  private InstantUploadSyncManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
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
        case 1:
          InstantUploadSyncManager.access$100(InstantUploadSyncManager.this);
        case 2:
        case 5:
        case 3:
        case 4:
        }
        while (true)
        {
          return;
          InstantUploadSyncManager.access$200(InstantUploadSyncManager.this);
          continue;
          InstantUploadSyncManager.access$300(InstantUploadSyncManager.this, (Boolean)paramAnonymousMessage.obj);
          continue;
          InstantUploadSyncManager.this.updateTasksInternal();
          continue;
          try
          {
            InstantUploadSyncManager.this.mSyncHelper.syncAccounts();
            InstantUploadSyncManager.access$600(InstantUploadSyncManager.this);
          }
          catch (Exception localException)
          {
            Log.w("iu.SyncManager", "MSG_UPDATE_PICASA_ACCOUNTS: " + localException);
          }
        }
      }
    };
    this.mSyncHandler.sendEmptyMessage(1);
    this.mSyncHandler.sendEmptyMessage(4);
    this.mSyncHandler.sendEmptyMessage(2);
    this.mSyncHandler.sendEmptyMessage(5);
    OnAccountsUpdateListener local1 = new OnAccountsUpdateListener()
    {
      public final void onAccountsUpdated(Account[] paramAnonymousArrayOfAccount)
      {
        if (EsLog.isLoggable("iu.SyncManager", 4))
          Log.i("iu.SyncManager", "account change detect - update database");
        InstantUploadSyncManager.this.mSyncHandler.sendEmptyMessage(4);
      }
    };
    AccountManager.get(this.mContext).addOnAccountsUpdatedListener(local1, null, false);
  }

  private boolean acceptSyncTask(SyncTask paramSyncTask)
  {
    boolean bool = false;
    if (paramSyncTask.isBackgroundSync())
      if (!ContentResolver.getMasterSyncAutomatically())
      {
        if (EsLog.isLoggable("iu.SyncManager", 3))
          Log.d("iu.SyncManager", "reject " + paramSyncTask + " because master auto sync is off");
        paramSyncTask.onRejected(6);
      }
    while (true)
    {
      return bool;
      if (!ContentResolver.getSyncAutomatically(new Account(paramSyncTask.syncAccount, "com.google"), "com.google.android.apps.plus.iu.EsGoogleIuProvider"))
      {
        if (EsLog.isLoggable("iu.SyncManager", 3))
          Log.d("iu.SyncManager", "reject " + paramSyncTask + " because auto sync is off");
        paramSyncTask.onRejected(6);
        bool = false;
      }
      else
      {
        synchronized (this.mInvalidAccounts)
        {
          if (this.mInvalidAccounts.contains(paramSyncTask.syncAccount))
          {
            if (EsLog.isLoggable("iu.SyncManager", 3))
              Log.d("iu.SyncManager", "reject " + paramSyncTask + " for invalid account: " + Utils.maskDebugInfo(paramSyncTask.syncAccount));
            bool = false;
          }
        }
        if ((!this.mBackgroundData) && (paramSyncTask.isBackgroundSync()))
        {
          if (EsLog.isLoggable("iu.SyncManager", 3))
            Log.d("iu.SyncManager", "reject " + paramSyncTask + " for disabled background data");
          paramSyncTask.onRejected(8);
          bool = false;
        }
        else if ((!this.mIsPlugged) && (!paramSyncTask.isSyncOnBattery()))
        {
          if (EsLog.isLoggable("iu.SyncManager", 3))
            Log.d("iu.SyncManager", "reject " + paramSyncTask + " on battery");
          paramSyncTask.onRejected(4);
          bool = false;
        }
        else if (!this.mHasWifiConnectivity)
        {
          if (paramSyncTask.isSyncOnWifiOnly())
          {
            if (EsLog.isLoggable("iu.SyncManager", 3))
              Log.d("iu.SyncManager", "reject " + paramSyncTask + " for non-wifi connection");
            paramSyncTask.onRejected(2);
            bool = false;
          }
          else if ((this.mIsRoaming) && (!paramSyncTask.isSyncOnRoaming()))
          {
            if (EsLog.isLoggable("iu.SyncManager", 3))
              Log.d("iu.SyncManager", "reject " + paramSyncTask + " for roaming");
            paramSyncTask.onRejected(3);
            bool = false;
          }
        }
        else
        {
          bool = true;
        }
      }
    }
  }

  public static SyncSession createSession(String paramString, SyncResult paramSyncResult)
  {
    return new SyncSession(paramString, paramSyncResult);
  }

  public static InstantUploadSyncManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new InstantUploadSyncManager(paramContext);
      InstantUploadSyncManager localInstantUploadSyncManager = sInstance;
      return localInstantUploadSyncManager;
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
    SyncTask localSyncTask = this.mProvider.getNextTask(paramString);
    if ((localSyncTask != null) && (acceptSyncTask(localSyncTask)) && ((paramString == null) || (TextUtils.equals(localSyncTask.syncAccount, paramString))));
    while (true)
    {
      return localSyncTask;
      localSyncTask = null;
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
          localBundle.putBoolean("ignore_settings", true);
          if (EsLog.isLoggable("iu.SyncManager", 3))
            Log.d("iu.SyncManager", "request sync for " + localSyncTask3);
          ContentResolver.requestSync(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", localBundle);
        }
        else
        {
          String str = localAccount.name;
          if (EsLog.isLoggable("iu.SyncManager", 5))
            Log.w("iu.SyncManager", "account: " + Utils.maskDebugInfo(str) + " has been removed ?!");
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
          if (EsLog.isLoggable("iu.SyncManager", 3))
            Log.d("iu.SyncManager", "stop task: " + localSyncTask1 + " due to environment change");
          localSyncTask1.cancelSync();
        }
        else
        {
          SyncTask localSyncTask2 = nextSyncTaskInternal(localSyncSession.account);
          if ((localSyncTask2 != null) && (localSyncTask2.mPriority < localSyncTask1.mPriority))
          {
            if (EsLog.isLoggable("iu.SyncManager", 3))
              Log.d("iu.SyncManager", "cancel task: " + localSyncTask1 + " for " + localSyncTask2);
            localSyncTask1.cancelSync();
          }
        }
    }
  }

  public final void onAccountActivated(String paramString)
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

  public final void onAccountDeactivated(String paramString)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = UploadsDatabaseHelper.getInstance(this.mContext).getWritableDatabase();
      String[] arrayOfString = { paramString };
      localSQLiteDatabase.delete(MediaRecordEntry.SCHEMA.getTableName(), "upload_account == ?", arrayOfString);
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
    //   2: invokestatic 536	com/android/gallery3d/common/Utils:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   5: checkcast 399	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession
    //   8: putfield 431	com/google/android/apps/plus/iu/InstantUploadSyncManager:mCurrentSession	Lcom/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession;
    //   11: aload_0
    //   12: getfield 92	com/google/android/apps/plus/iu/InstantUploadSyncManager:mInvalidAccounts	Ljava/util/HashSet;
    //   15: astore_2
    //   16: aload_2
    //   17: monitorenter
    //   18: aload_0
    //   19: getfield 92	com/google/android/apps/plus/iu/InstantUploadSyncManager:mInvalidAccounts	Ljava/util/HashSet;
    //   22: invokevirtual 539	java/util/HashSet:clear	()V
    //   25: aload_2
    //   26: monitorexit
    //   27: aload_0
    //   28: monitorenter
    //   29: aload_0
    //   30: getfield 257	com/google/android/apps/plus/iu/InstantUploadSyncManager:mProvider	Lcom/google/android/apps/plus/iu/SyncTaskProvider;
    //   33: invokeinterface 542 1 0
    //   38: aload_0
    //   39: monitorexit
    //   40: aload_1
    //   41: getfield 546	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   44: getfield 552	android/content/SyncResult:stats	Landroid/content/SyncStats;
    //   47: astore 6
    //   49: ldc_w 554
    //   52: invokestatic 560	com/google/android/apps/plus/iu/MetricsUtils:begin	(Ljava/lang/String;)I
    //   55: istore 7
    //   57: new 562	java/util/concurrent/FutureTask
    //   60: dup
    //   61: new 564	com/google/android/apps/plus/iu/InstantUploadSyncManager$GetNextSyncTask
    //   64: dup
    //   65: aload_0
    //   66: aload_1
    //   67: invokespecial 567	com/google/android/apps/plus/iu/InstantUploadSyncManager$GetNextSyncTask:<init>	(Lcom/google/android/apps/plus/iu/InstantUploadSyncManager;Lcom/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession;)V
    //   70: invokespecial 570	java/util/concurrent/FutureTask:<init>	(Ljava/util/concurrent/Callable;)V
    //   73: astore 8
    //   75: aload_0
    //   76: getfield 137	com/google/android/apps/plus/iu/InstantUploadSyncManager:mSyncHandler	Landroid/os/Handler;
    //   79: aload 8
    //   81: invokevirtual 574	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   84: pop
    //   85: aload 8
    //   87: invokevirtual 577	java/util/concurrent/FutureTask:get	()Ljava/lang/Object;
    //   90: pop
    //   91: iload 7
    //   93: invokestatic 580	com/google/android/apps/plus/iu/MetricsUtils:end	(I)V
    //   96: aload_1
    //   97: getfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   100: ifnull +142 -> 242
    //   103: aload_1
    //   104: getfield 467	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:account	Ljava/lang/String;
    //   107: aload_1
    //   108: getfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   111: getfield 201	com/google/android/apps/plus/iu/SyncTask:syncAccount	Ljava/lang/String;
    //   114: invokevirtual 417	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   117: istore 18
    //   119: iload 18
    //   121: ifeq +121 -> 242
    //   124: ldc 173
    //   126: iconst_3
    //   127: invokestatic 179	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   130: ifeq +29 -> 159
    //   133: ldc 173
    //   135: new 46	java/lang/StringBuilder
    //   138: dup
    //   139: ldc_w 582
    //   142: invokespecial 52	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   145: aload_1
    //   146: getfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   149: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   152: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokestatic 192	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   158: pop
    //   159: aload_1
    //   160: getfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   163: aload_1
    //   164: getfield 546	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   167: invokevirtual 585	com/google/android/apps/plus/iu/SyncTask:performSync	(Landroid/content/SyncResult;)V
    //   170: ldc 173
    //   172: iconst_3
    //   173: invokestatic 179	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   176: ifeq +29 -> 205
    //   179: ldc 173
    //   181: new 46	java/lang/StringBuilder
    //   184: dup
    //   185: ldc_w 587
    //   188: invokespecial 52	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   191: aload_1
    //   192: getfield 546	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:result	Landroid/content/SyncResult;
    //   195: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   198: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   201: invokestatic 192	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   204: pop
    //   205: aload_1
    //   206: aconst_null
    //   207: putfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   210: aload 6
    //   212: getfield 593	android/content/SyncStats:numIoExceptions	J
    //   215: lconst_0
    //   216: lcmp
    //   217: ifle -168 -> 49
    //   220: ldc 173
    //   222: iconst_5
    //   223: invokestatic 179	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   226: ifeq +12 -> 238
    //   229: ldc 173
    //   231: ldc_w 595
    //   234: invokestatic 311	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   237: pop
    //   238: aload_1
    //   239: invokevirtual 596	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:cancelSync	()V
    //   242: aload_1
    //   243: getfield 467	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:account	Ljava/lang/String;
    //   246: astore 12
    //   248: aload_0
    //   249: getfield 108	com/google/android/apps/plus/iu/InstantUploadSyncManager:mContext	Landroid/content/Context;
    //   252: invokestatic 498	com/google/android/apps/plus/iu/UploadsDatabaseHelper:getInstance	(Landroid/content/Context;)Lcom/google/android/apps/plus/iu/UploadsDatabaseHelper;
    //   255: invokevirtual 499	com/google/android/apps/plus/iu/UploadsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   258: astore 13
    //   260: iconst_1
    //   261: anewarray 32	java/lang/String
    //   264: dup
    //   265: iconst_0
    //   266: aload 12
    //   268: aastore
    //   269: astore 14
    //   271: aload 13
    //   273: getstatic 58	com/google/android/apps/plus/iu/MediaRecordEntry:SCHEMA	Lcom/android/gallery3d/common/EntrySchema;
    //   276: invokevirtual 64	com/android/gallery3d/common/EntrySchema:getTableName	()Ljava/lang/String;
    //   279: ldc_w 598
    //   282: aload 14
    //   284: invokevirtual 506	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   287: pop
    //   288: aload 13
    //   290: getstatic 601	com/google/android/apps/plus/iu/UploadTaskEntry:SCHEMA	Lcom/android/gallery3d/common/EntrySchema;
    //   293: invokevirtual 64	com/android/gallery3d/common/EntrySchema:getTableName	()Ljava/lang/String;
    //   296: getstatic 84	com/google/android/apps/plus/iu/InstantUploadSyncManager:UPLOAD_TASK_CLEANUP_DELETE_WHERE	Ljava/lang/String;
    //   299: aload 14
    //   301: invokevirtual 506	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   304: pop
    //   305: aload_0
    //   306: aconst_null
    //   307: putfield 431	com/google/android/apps/plus/iu/InstantUploadSyncManager:mCurrentSession	Lcom/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession;
    //   310: aload_1
    //   311: monitorenter
    //   312: aload_1
    //   313: getfield 604	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mSyncCancelled	Z
    //   316: ifne +8 -> 324
    //   319: aload_0
    //   320: lconst_0
    //   321: invokevirtual 608	com/google/android/apps/plus/iu/InstantUploadSyncManager:updateTasks	(J)V
    //   324: aload_1
    //   325: monitorexit
    //   326: aload_0
    //   327: aconst_null
    //   328: putfield 431	com/google/android/apps/plus/iu/InstantUploadSyncManager:mCurrentSession	Lcom/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession;
    //   331: return
    //   332: astore_3
    //   333: aload_2
    //   334: monitorexit
    //   335: aload_3
    //   336: athrow
    //   337: astore 4
    //   339: aload_0
    //   340: monitorexit
    //   341: aload 4
    //   343: athrow
    //   344: astore 10
    //   346: ldc 173
    //   348: iconst_5
    //   349: invokestatic 179	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   352: ifeq -261 -> 91
    //   355: ldc 173
    //   357: ldc_w 610
    //   360: aload 10
    //   362: invokestatic 613	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   365: pop
    //   366: goto -275 -> 91
    //   369: astore 5
    //   371: aload_0
    //   372: aconst_null
    //   373: putfield 431	com/google/android/apps/plus/iu/InstantUploadSyncManager:mCurrentSession	Lcom/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession;
    //   376: aload 5
    //   378: athrow
    //   379: astore 23
    //   381: ldc 173
    //   383: iconst_5
    //   384: invokestatic 179	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   387: ifeq +14 -> 401
    //   390: ldc 173
    //   392: ldc_w 615
    //   395: aload 23
    //   397: invokestatic 613	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   400: pop
    //   401: aload 6
    //   403: lconst_1
    //   404: aload 6
    //   406: getfield 593	android/content/SyncStats:numIoExceptions	J
    //   409: ladd
    //   410: putfield 593	android/content/SyncStats:numIoExceptions	J
    //   413: aload_1
    //   414: aconst_null
    //   415: putfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   418: goto -208 -> 210
    //   421: astore 20
    //   423: ldc 173
    //   425: iconst_5
    //   426: invokestatic 179	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   429: ifeq +14 -> 443
    //   432: ldc 173
    //   434: ldc_w 615
    //   437: aload 20
    //   439: invokestatic 613	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   442: pop
    //   443: aload_1
    //   444: aconst_null
    //   445: putfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   448: goto -238 -> 210
    //   451: astore 19
    //   453: aload_1
    //   454: aconst_null
    //   455: putfield 457	com/google/android/apps/plus/iu/InstantUploadSyncManager$SyncSession:mCurrentTask	Lcom/google/android/apps/plus/iu/SyncTask;
    //   458: aload 19
    //   460: athrow
    //   461: astore 17
    //   463: aload_1
    //   464: monitorexit
    //   465: aload 17
    //   467: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   18	27	332	finally
    //   29	40	337	finally
    //   85	91	344	java/lang/Throwable
    //   40	85	369	finally
    //   85	91	369	finally
    //   91	119	369	finally
    //   205	312	369	finally
    //   346	366	369	finally
    //   413	418	369	finally
    //   443	468	369	finally
    //   124	205	379	java/io/IOException
    //   124	205	421	java/lang/Throwable
    //   124	205	451	finally
    //   381	413	451	finally
    //   423	443	451	finally
    //   312	326	461	finally
  }

  public final void requestAccountSync()
  {
    this.mSyncHandler.sendEmptyMessage(4);
  }

  public final void updateTasks(long paramLong)
  {
    this.mSyncHandler.sendEmptyMessageDelayed(3, paramLong);
  }

  private final class GetNextSyncTask
    implements Callable<Void>
  {
    private final InstantUploadSyncManager.SyncSession mSession;

    public GetNextSyncTask(InstantUploadSyncManager.SyncSession arg2)
    {
      Object localObject;
      this.mSession = localObject;
    }

    private Void call()
    {
      InstantUploadSyncManager.this.mSyncHandler.removeMessages(3);
      SyncTask localSyncTask = InstantUploadSyncManager.this.nextSyncTaskInternal(this.mSession.account);
      synchronized (this.mSession)
      {
        if (!this.mSession.mSyncCancelled)
          this.mSession.mCurrentTask = localSyncTask;
      }
      return null;
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
 * Qualified Name:     com.google.android.apps.plus.iu.InstantUploadSyncManager
 * JD-Core Version:    0.6.2
 */