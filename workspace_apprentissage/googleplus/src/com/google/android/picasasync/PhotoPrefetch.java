package com.google.android.picasasync;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SyncResult;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.gallery3d.common.Utils;
import com.google.android.picasastore.MetricsUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class PhotoPrefetch
  implements SyncTaskProvider
{
  private final Context mContext;
  private final int mImageType;
  private final SharedPreferences mPrefs;

  public PhotoPrefetch(Context paramContext, int paramInt)
  {
    this.mContext = paramContext;
    this.mImageType = paramInt;
    this.mPrefs = PreferenceManager.getDefaultSharedPreferences(this.mContext);
  }

  private static boolean compareAndSetCleanBit(SharedPreferences paramSharedPreferences, int paramInt1, int paramInt2)
  {
    try
    {
      int i = paramSharedPreferences.getInt("picasasync.prefetch.clean-cache", 0);
      boolean bool = false;
      if (i != paramInt1);
      while (true)
      {
        return bool;
        paramSharedPreferences.edit().putInt("picasasync.prefetch.clean-cache", paramInt2).commit();
        bool = true;
      }
    }
    finally
    {
    }
  }

  static void onRequestSync(Context paramContext)
  {
    compareAndSetCleanBit(PreferenceManager.getDefaultSharedPreferences(paramContext), 0, 2);
  }

  public final void collectTasks(Collection<SyncTask> paramCollection)
  {
    PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(this.mContext);
    SQLiteDatabase localSQLiteDatabase = localPicasaSyncHelper.getWritableDatabase();
    Iterator localIterator3;
    switch (this.mImageType)
    {
    default:
      throw new AssertionError();
    case 2:
      localIterator3 = localPicasaSyncHelper.getUsers().iterator();
    case 1:
    case 3:
    }
    while (localIterator3.hasNext())
    {
      UserEntry localUserEntry3 = (UserEntry)localIterator3.next();
      if (SyncState.PREFETCH_FULL_IMAGE.isRequested(localSQLiteDatabase, localUserEntry3.account))
      {
        paramCollection.add(new PrefetchFullImage(localUserEntry3.account));
        continue;
        Iterator localIterator2 = localPicasaSyncHelper.getUsers().iterator();
        while (localIterator2.hasNext())
        {
          UserEntry localUserEntry2 = (UserEntry)localIterator2.next();
          if (SyncState.PREFETCH_SCREEN_NAIL.isRequested(localSQLiteDatabase, localUserEntry2.account))
          {
            paramCollection.add(new PrefetchScreenNail(localUserEntry2.account));
            continue;
            Iterator localIterator1 = localPicasaSyncHelper.getUsers().iterator();
            while (localIterator1.hasNext())
            {
              UserEntry localUserEntry1 = (UserEntry)localIterator1.next();
              if (SyncState.PREFETCH_ALBUM_COVER.isRequested(localSQLiteDatabase, localUserEntry1.account))
                paramCollection.add(new PrefetchAlbumCover(localUserEntry1.account));
            }
          }
        }
      }
    }
  }

  public final void resetSyncStates()
  {
    PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(this.mContext);
    SQLiteDatabase localSQLiteDatabase = localPicasaSyncHelper.getWritableDatabase();
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    try
    {
      if (localSharedPreferences.getInt("picasasync.prefetch.clean-cache", 0) == 1)
        localSharedPreferences.edit().putInt("picasasync.prefetch.clean-cache", 2).commit();
      switch (this.mImageType)
      {
      default:
        throw new AssertionError();
      case 2:
      case 1:
      case 3:
      }
    }
    finally
    {
    }
    SyncState localSyncState = SyncState.PREFETCH_FULL_IMAGE;
    while (true)
    {
      Iterator localIterator = localPicasaSyncHelper.getUsers().iterator();
      while (localIterator.hasNext())
        localSyncState.resetSyncToDirty(localSQLiteDatabase, ((UserEntry)localIterator.next()).account);
      localSyncState = SyncState.PREFETCH_SCREEN_NAIL;
      continue;
      localSyncState = SyncState.PREFETCH_ALBUM_COVER;
    }
  }

  final void showPrefetchCompleteNotification(int paramInt)
  {
    RemoteViews localRemoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.ps_cache_notification);
    localRemoteViews.setTextViewText(R.id.ps_status, this.mContext.getString(R.string.ps_cache_done));
    localRemoteViews.setProgressBar(R.id.ps_progress, paramInt, paramInt, false);
    Notification localNotification = new Notification();
    localNotification.icon = 17301634;
    localNotification.contentView = localRemoteViews;
    localNotification.when = System.currentTimeMillis();
    ((NotificationManager)this.mContext.getSystemService("notification")).notify(2, localNotification);
  }

  final void updateOngoingNotification(int paramInt1, int paramInt2)
  {
    Resources localResources = this.mContext.getResources();
    int i = R.string.ps_cache_status;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    String str = localResources.getString(i, arrayOfObject);
    RemoteViews localRemoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.ps_cache_notification);
    localRemoteViews.setTextViewText(R.id.ps_status, str);
    localRemoteViews.setProgressBar(R.id.ps_progress, paramInt2, paramInt1, false);
    Notification localNotification = new Notification();
    localNotification.icon = 17301633;
    localNotification.contentView = localRemoteViews;
    localNotification.when = System.currentTimeMillis();
    localNotification.flags = (0x2 | localNotification.flags);
    ((NotificationManager)this.mContext.getSystemService("notification")).notify(1, localNotification);
  }

  private final class PrefetchAlbumCover extends PhotoPrefetch.PrefetchScreenNail
  {
    public PrefetchAlbumCover(String arg2)
    {
      super(str, SyncState.PREFETCH_ALBUM_COVER);
    }

    public final void performSync(SyncResult paramSyncResult)
      throws IOException
    {
      int i = MetricsUtils.begin("PrefetchAlbumCover");
      try
      {
        performSyncCommon(paramSyncResult);
        return;
      }
      finally
      {
        MetricsUtils.endWithReport(i, "picasa.prefetch.thumbnail");
      }
    }

    protected final boolean performSyncInternal$45259df4(UserEntry paramUserEntry, PrefetchHelper paramPrefetchHelper)
      throws IOException
    {
      boolean bool = true;
      if (PhotoPrefetch.this.mContext.getExternalCacheDir() == null)
        Log.w("PhotoPrefetch", "no external storage, skip album cover prefetching");
      while (true)
      {
        return bool;
        PhotoPrefetch.access$100(PhotoPrefetch.this, this.mSyncContext);
        paramPrefetchHelper.syncAlbumCoversForUser(this.mSyncContext, paramUserEntry);
        if (this.mSyncContext.syncInterrupted())
          bool = false;
      }
    }
  }

  private final class PrefetchFullImage extends PhotoPrefetch.PrefetchScreenNail
    implements PrefetchHelper.PrefetchListener
  {
    private PrefetchHelper.CacheStats mCacheStats;

    public PrefetchFullImage(String arg2)
    {
      super(str, SyncState.PREFETCH_FULL_IMAGE);
    }

    public final boolean isBackgroundSync()
    {
      return false;
    }

    public final void onDownloadFinish$256a6c5()
    {
      PrefetchHelper.CacheStats localCacheStats = this.mCacheStats;
      localCacheStats.pendingCount = (-1 + localCacheStats.pendingCount);
      int i = localCacheStats.totalCount - localCacheStats.pendingCount;
      PhotoPrefetch.this.updateOngoingNotification(i, localCacheStats.totalCount);
    }

    public final void performSync(SyncResult paramSyncResult)
      throws IOException
    {
      int i = MetricsUtils.begin("PrefetchFullImage");
      try
      {
        performSyncCommon(paramSyncResult);
        return;
      }
      finally
      {
        MetricsUtils.endWithReport(i, "picasa.prefetch.full_image");
      }
    }

    protected final boolean performSyncInternal$45259df4(UserEntry paramUserEntry, PrefetchHelper paramPrefetchHelper)
      throws IOException
    {
      this.mSyncContext.setCacheDownloadListener(this);
      PhotoPrefetch.access$100(PhotoPrefetch.this, this.mSyncContext);
      this.mCacheStats = paramPrefetchHelper.getCacheStatistics(2);
      boolean bool;
      if (this.mCacheStats.pendingCount == 0)
        bool = true;
      while (true)
      {
        return bool;
        try
        {
          paramPrefetchHelper.syncFullImagesForUser(this.mSyncContext, paramUserEntry);
          ((NotificationManager)PhotoPrefetch.this.mContext.getSystemService("notification")).cancel(1);
          if (this.mCacheStats.pendingCount == 0)
            PhotoPrefetch.this.showPrefetchCompleteNotification(this.mCacheStats.totalCount);
          if (!this.mSyncContext.syncInterrupted())
            bool = true;
        }
        finally
        {
          ((NotificationManager)PhotoPrefetch.this.mContext.getSystemService("notification")).cancel(1);
        }
      }
    }
  }

  private class PrefetchScreenNail extends SyncTask
  {
    protected boolean mSyncCancelled = false;
    protected PrefetchHelper.PrefetchContext mSyncContext;
    private SyncState mSyncState;

    public PrefetchScreenNail(String arg2)
    {
      this(str, SyncState.PREFETCH_SCREEN_NAIL);
    }

    public PrefetchScreenNail(String paramSyncState, SyncState arg3)
    {
      super();
      Object localObject;
      this.mSyncState = localObject;
    }

    public final void cancelSync()
    {
      this.mSyncCancelled = true;
      if (this.mSyncContext != null)
        this.mSyncContext.stopSync();
    }

    public boolean isBackgroundSync()
    {
      return true;
    }

    public final boolean isSyncOnBattery()
    {
      return isSyncOnBattery(PhotoPrefetch.this.mContext);
    }

    public final boolean isSyncOnExternalStorageOnly()
    {
      return true;
    }

    public final boolean isSyncOnRoaming()
    {
      return isSyncOnRoaming(PhotoPrefetch.this.mContext);
    }

    public final boolean isSyncOnWifiOnly()
    {
      return isSyncPicasaOnWifiOnly(PhotoPrefetch.this.mContext);
    }

    public void performSync(SyncResult paramSyncResult)
      throws IOException
    {
      int i = MetricsUtils.begin("PrefetchScreenNail");
      try
      {
        performSyncCommon(paramSyncResult);
        return;
      }
      finally
      {
        MetricsUtils.endWithReport(i, "picasa.prefetch.screennail");
      }
    }

    protected final void performSyncCommon(SyncResult paramSyncResult)
      throws IOException
    {
      PrefetchHelper localPrefetchHelper = PrefetchHelper.get(PhotoPrefetch.this.mContext);
      PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(PhotoPrefetch.this.mContext);
      SQLiteDatabase localSQLiteDatabase = localPicasaSyncHelper.getWritableDatabase();
      UserEntry localUserEntry;
      try
      {
        if (this.mSyncCancelled)
          return;
        this.mSyncContext = localPrefetchHelper.createPrefetchContext(paramSyncResult, Thread.currentThread());
        this.mSyncContext.updateCacheConfigVersion();
        if (!this.mSyncState.onSyncStart(localSQLiteDatabase, this.syncAccount))
          Log.e("PhotoPrefetch", "invalid state: " + this.mSyncState.getState(localSQLiteDatabase, this.syncAccount));
      }
      finally
      {
      }
      if (localUserEntry == null)
        Log.w("PhotoPrefetch", "cannot find user: " + Utils.maskDebugInfo(this.syncAccount));
      else if (performSyncInternal$45259df4(localUserEntry, localPrefetchHelper))
        this.mSyncState.onSyncFinish(localSQLiteDatabase, this.syncAccount);
      else
        this.mSyncState.resetSyncToDirty(localSQLiteDatabase, this.syncAccount);
    }

    protected boolean performSyncInternal$45259df4(UserEntry paramUserEntry, PrefetchHelper paramPrefetchHelper)
      throws IOException
    {
      boolean bool = true;
      if (PhotoPrefetch.this.mContext.getExternalCacheDir() == null)
        Log.w("PhotoPrefetch", "no external storage, skip screenail prefetching");
      while (true)
      {
        return bool;
        PhotoPrefetch.access$100(PhotoPrefetch.this, this.mSyncContext);
        paramPrefetchHelper.syncScreenNailsForUser(this.mSyncContext, paramUserEntry);
        if (this.mSyncContext.syncInterrupted())
          bool = false;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PhotoPrefetch
 * JD-Core Version:    0.6.2
 */