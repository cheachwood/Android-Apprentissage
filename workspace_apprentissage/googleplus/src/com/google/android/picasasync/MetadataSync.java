package com.google.android.picasasync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

final class MetadataSync
  implements SyncTaskProvider
{
  private final Context mContext;
  private final boolean mIsManual;

  public MetadataSync(Context paramContext, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mIsManual = paramBoolean;
  }

  public final void collectTasks(Collection<SyncTask> paramCollection)
  {
    PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(this.mContext);
    SQLiteDatabase localSQLiteDatabase = localPicasaSyncHelper.getWritableDatabase();
    if (this.mIsManual);
    for (SyncState localSyncState = SyncState.METADATA_MANUAL; ; localSyncState = SyncState.METADATA)
    {
      Iterator localIterator = localPicasaSyncHelper.getUsers().iterator();
      while (localIterator.hasNext())
      {
        UserEntry localUserEntry = (UserEntry)localIterator.next();
        if (localSyncState.isRequested(localSQLiteDatabase, localUserEntry.account))
        {
          String str = localUserEntry.account;
          paramCollection.add(new MetadataSyncTask(str));
        }
      }
    }
  }

  public final void resetSyncStates()
  {
    PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(this.mContext);
    SQLiteDatabase localSQLiteDatabase = localPicasaSyncHelper.getWritableDatabase();
    if (this.mIsManual);
    for (SyncState localSyncState = SyncState.METADATA_MANUAL; ; localSyncState = SyncState.METADATA)
    {
      Iterator localIterator = localPicasaSyncHelper.getUsers().iterator();
      while (localIterator.hasNext())
        localSyncState.resetSyncToDirty(localSQLiteDatabase, ((UserEntry)localIterator.next()).account);
    }
  }

  private final class MetadataSyncTask extends SyncTask
  {
    private boolean mSyncCancelled = false;
    private PicasaSyncHelper.SyncContext mSyncContext;

    public MetadataSyncTask(String arg2)
    {
      super();
    }

    public final void cancelSync()
    {
      try
      {
        this.mSyncCancelled = true;
        if (this.mSyncContext != null)
          this.mSyncContext.stopSync();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final boolean isBackgroundSync()
    {
      if (!MetadataSync.this.mIsManual);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean isSyncOnBattery()
    {
      return isSyncOnBattery(MetadataSync.this.mContext);
    }

    public final boolean isSyncOnWifiOnly()
    {
      if (MetadataSync.this.mIsManual);
      for (boolean bool = false; ; bool = isSyncPicasaOnWifiOnly(MetadataSync.this.mContext))
        return bool;
    }

    // ERROR //
    public final void performSync(android.content.SyncResult paramSyncResult)
    {
      // Byte code:
      //   0: ldc 54
      //   2: invokestatic 60	com/google/android/picasastore/MetricsUtils:begin	(Ljava/lang/String;)I
      //   5: istore_2
      //   6: aload_0
      //   7: getfield 14	com/google/android/picasasync/MetadataSync$MetadataSyncTask:this$0	Lcom/google/android/picasasync/MetadataSync;
      //   10: invokestatic 41	com/google/android/picasasync/MetadataSync:access$100	(Lcom/google/android/picasasync/MetadataSync;)Landroid/content/Context;
      //   13: invokestatic 66	com/google/android/picasasync/PicasaSyncHelper:getInstance	(Landroid/content/Context;)Lcom/google/android/picasasync/PicasaSyncHelper;
      //   16: astore 4
      //   18: aload 4
      //   20: invokevirtual 70	com/google/android/picasasync/PicasaSyncHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
      //   23: astore 5
      //   25: aload_0
      //   26: getfield 14	com/google/android/picasasync/MetadataSync$MetadataSyncTask:this$0	Lcom/google/android/picasasync/MetadataSync;
      //   29: invokestatic 36	com/google/android/picasasync/MetadataSync:access$000	(Lcom/google/android/picasasync/MetadataSync;)Z
      //   32: ifeq +49 -> 81
      //   35: getstatic 76	com/google/android/picasasync/SyncState:METADATA_MANUAL	Lcom/google/android/picasasync/SyncState;
      //   38: aload 5
      //   40: aload_0
      //   41: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   44: invokevirtual 84	com/google/android/picasasync/SyncState:onSyncStart	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
      //   47: ifeq +27 -> 74
      //   50: getstatic 87	com/google/android/picasasync/SyncState:METADATA	Lcom/google/android/picasasync/SyncState;
      //   53: aload 5
      //   55: aload_0
      //   56: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   59: invokevirtual 84	com/google/android/picasasync/SyncState:onSyncStart	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
      //   62: pop
      //   63: aload_0
      //   64: monitorenter
      //   65: aload_0
      //   66: getfield 19	com/google/android/picasasync/MetadataSync$MetadataSyncTask:mSyncCancelled	Z
      //   69: ifeq +53 -> 122
      //   72: aload_0
      //   73: monitorexit
      //   74: iload_2
      //   75: ldc 89
      //   77: invokestatic 93	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
      //   80: return
      //   81: iconst_0
      //   82: getstatic 87	com/google/android/picasasync/SyncState:METADATA	Lcom/google/android/picasasync/SyncState;
      //   85: aload 5
      //   87: aload_0
      //   88: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   91: invokevirtual 84	com/google/android/picasasync/SyncState:onSyncStart	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
      //   94: ior
      //   95: istore 6
      //   97: getstatic 76	com/google/android/picasasync/SyncState:METADATA_MANUAL	Lcom/google/android/picasasync/SyncState;
      //   100: aload 5
      //   102: aload_0
      //   103: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   106: invokevirtual 84	com/google/android/picasasync/SyncState:onSyncStart	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
      //   109: istore 7
      //   111: iload 6
      //   113: iload 7
      //   115: ior
      //   116: ifeq -42 -> 74
      //   119: goto -56 -> 63
      //   122: aload_0
      //   123: aload 4
      //   125: aload_1
      //   126: invokestatic 99	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   129: invokevirtual 103	com/google/android/picasasync/PicasaSyncHelper:createSyncContext	(Landroid/content/SyncResult;Ljava/lang/Thread;)Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
      //   132: putfield 23	com/google/android/picasasync/MetadataSync$MetadataSyncTask:mSyncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
      //   135: aload_0
      //   136: getfield 23	com/google/android/picasasync/MetadataSync$MetadataSyncTask:mSyncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
      //   139: aload_0
      //   140: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   143: invokevirtual 107	com/google/android/picasasync/PicasaSyncHelper$SyncContext:setAccount	(Ljava/lang/String;)Z
      //   146: pop
      //   147: aload_0
      //   148: monitorexit
      //   149: aload 4
      //   151: aload_0
      //   152: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   155: invokevirtual 111	com/google/android/picasasync/PicasaSyncHelper:findUser	(Ljava/lang/String;)Lcom/google/android/picasasync/UserEntry;
      //   158: astore 10
      //   160: aload 10
      //   162: ifnonnull +52 -> 214
      //   165: iconst_1
      //   166: anewarray 113	java/lang/Object
      //   169: astore 17
      //   171: aload 17
      //   173: iconst_0
      //   174: aload_0
      //   175: getfield 80	com/google/android/picasasync/MetadataSync$MetadataSyncTask:syncAccount	Ljava/lang/String;
      //   178: invokestatic 119	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
      //   181: aastore
      //   182: ldc 121
      //   184: ldc 123
      //   186: aload 17
      //   188: invokestatic 129	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   191: invokestatic 135	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
      //   194: pop
      //   195: goto -121 -> 74
      //   198: astore_3
      //   199: iload_2
      //   200: ldc 89
      //   202: invokestatic 93	com/google/android/picasastore/MetricsUtils:endWithReport	(ILjava/lang/String;)V
      //   205: aload_3
      //   206: athrow
      //   207: astore 8
      //   209: aload_0
      //   210: monitorexit
      //   211: aload 8
      //   213: athrow
      //   214: aload 4
      //   216: aload 10
      //   218: getfield 140	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
      //   221: invokevirtual 143	com/google/android/picasasync/PicasaSyncHelper:isPicasaAccount	(Ljava/lang/String;)Z
      //   224: istore 16
      //   226: iload 16
      //   228: istore 13
      //   230: iload 13
      //   232: ifeq +95 -> 327
      //   235: aload 4
      //   237: aload_0
      //   238: getfield 23	com/google/android/picasasync/MetadataSync$MetadataSyncTask:mSyncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
      //   241: aload 10
      //   243: invokevirtual 147	com/google/android/picasasync/PicasaSyncHelper:syncAlbumsForUser	(Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;Lcom/google/android/picasasync/UserEntry;)V
      //   246: aload 4
      //   248: aload_0
      //   249: getfield 23	com/google/android/picasasync/MetadataSync$MetadataSyncTask:mSyncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
      //   252: aload 10
      //   254: invokevirtual 150	com/google/android/picasasync/PicasaSyncHelper:syncPhotosForUser	(Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;Lcom/google/android/picasasync/UserEntry;)V
      //   257: aload_0
      //   258: getfield 23	com/google/android/picasasync/MetadataSync$MetadataSyncTask:mSyncContext	Lcom/google/android/picasasync/PicasaSyncHelper$SyncContext;
      //   261: invokevirtual 153	com/google/android/picasasync/PicasaSyncHelper$SyncContext:syncInterrupted	()Z
      //   264: ifne +97 -> 361
      //   267: getstatic 87	com/google/android/picasasync/SyncState:METADATA	Lcom/google/android/picasasync/SyncState;
      //   270: aload 5
      //   272: aload 10
      //   274: getfield 140	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
      //   277: invokevirtual 157	com/google/android/picasasync/SyncState:onSyncFinish	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   280: getstatic 76	com/google/android/picasasync/SyncState:METADATA_MANUAL	Lcom/google/android/picasasync/SyncState;
      //   283: aload 5
      //   285: aload 10
      //   287: getfield 140	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
      //   290: invokevirtual 157	com/google/android/picasasync/SyncState:onSyncFinish	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   293: aload_0
      //   294: getfield 14	com/google/android/picasasync/MetadataSync$MetadataSyncTask:this$0	Lcom/google/android/picasasync/MetadataSync;
      //   297: invokestatic 41	com/google/android/picasasync/MetadataSync:access$100	(Lcom/google/android/picasasync/MetadataSync;)Landroid/content/Context;
      //   300: invokestatic 163	com/google/android/picasasync/PicasaSyncManager:get	(Landroid/content/Context;)Lcom/google/android/picasasync/PicasaSyncManager;
      //   303: invokevirtual 166	com/google/android/picasasync/PicasaSyncManager:requestPrefetchSync	()V
      //   306: goto -232 -> 74
      //   309: astore 11
      //   311: ldc 121
      //   313: ldc 168
      //   315: aload 11
      //   317: invokestatic 171	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   320: pop
      //   321: iconst_0
      //   322: istore 13
      //   324: goto -94 -> 230
      //   327: iconst_1
      //   328: anewarray 113	java/lang/Object
      //   331: astore 14
      //   333: aload 14
      //   335: iconst_0
      //   336: aload 10
      //   338: getfield 140	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
      //   341: invokestatic 119	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
      //   344: aastore
      //   345: ldc 121
      //   347: ldc 173
      //   349: aload 14
      //   351: invokestatic 129	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   354: invokestatic 135	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
      //   357: pop
      //   358: goto -101 -> 257
      //   361: getstatic 87	com/google/android/picasasync/SyncState:METADATA	Lcom/google/android/picasasync/SyncState;
      //   364: aload 5
      //   366: aload 10
      //   368: getfield 140	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
      //   371: invokevirtual 176	com/google/android/picasasync/SyncState:resetSyncToDirty	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   374: getstatic 76	com/google/android/picasasync/SyncState:METADATA_MANUAL	Lcom/google/android/picasasync/SyncState;
      //   377: aload 5
      //   379: aload 10
      //   381: getfield 140	com/google/android/picasasync/UserEntry:account	Ljava/lang/String;
      //   384: invokevirtual 176	com/google/android/picasasync/SyncState:resetSyncToDirty	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   387: goto -313 -> 74
      //
      // Exception table:
      //   from	to	target	type
      //   6	65	198	finally
      //   81	111	198	finally
      //   149	195	198	finally
      //   209	214	198	finally
      //   214	226	198	finally
      //   235	387	198	finally
      //   65	74	207	finally
      //   122	149	207	finally
      //   214	226	309	java/lang/Exception
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.MetadataSync
 * JD-Core Version:    0.6.2
 */