package com.google.android.picasasync;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.content.SyncStats;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.android.gallery3d.common.Utils;

public final class PicasaSyncService extends Service
{
  private static PicasaSyncAdapter sSyncAdapter;

  private static void carryOverSyncAutomatically$5e8deb68(Account paramAccount, String paramString)
  {
    try
    {
      if (ContentResolver.getSyncAutomatically(paramAccount, "com.cooliris.picasa.contentprovider"))
      {
        Log.d("gp.PicasaSyncService", "carry over syncAutomatically for " + Utils.maskDebugInfo(paramAccount.name));
        ContentResolver.setSyncAutomatically(paramAccount, "com.cooliris.picasa.contentprovider", false);
        ContentResolver.setSyncAutomatically(paramAccount, paramString, true);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static PicasaSyncAdapter getSyncAdapter(Context paramContext)
  {
    try
    {
      if (sSyncAdapter == null)
        sSyncAdapter = new PicasaSyncAdapter(paramContext);
      PicasaSyncAdapter localPicasaSyncAdapter = sSyncAdapter;
      return localPicasaSyncAdapter;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final IBinder onBind(Intent paramIntent)
  {
    return getSyncAdapter(this).getSyncAdapterBinder();
  }

  private static final class PicasaSyncAdapter extends AbstractThreadedSyncAdapter
  {
    private PicasaSyncManager.SyncSession mSession;

    public PicasaSyncAdapter(Context paramContext)
    {
      super(false);
    }

    public final void onPerformSync(Account paramAccount, Bundle paramBundle, String paramString, ContentProviderClient paramContentProviderClient, SyncResult paramSyncResult)
    {
      PicasaSyncManager localPicasaSyncManager = PicasaSyncManager.get(getContext());
      if (paramBundle.getBoolean("initialize"))
      {
        Log.d("gp.PicasaSyncService", "initialize account: " + Utils.maskDebugInfo(paramAccount.name));
        try
        {
          if ((PicasaFacade.get(getContext()).isMaster()) && ("com.google".equals(paramAccount.type)))
          {
            ContentResolver.setIsSyncable(paramAccount, paramString, 1);
            PicasaSyncService.carryOverSyncAutomatically$5e8deb68(getContext(), paramAccount, paramString);
            localPicasaSyncManager.onAccountInitialized(paramAccount.name);
          }
          else
          {
            ContentResolver.setIsSyncable(paramAccount, paramString, 0);
          }
        }
        catch (Exception localException2)
        {
          Log.e("gp.PicasaSyncService", "cannot do sync", localException2);
        }
      }
      else
      {
        try
        {
          if (Thread.currentThread().isInterrupted())
            Log.d("gp.PicasaSyncService", "sync is cancelled");
        }
        finally
        {
        }
        this.mSession = new PicasaSyncManager.SyncSession(paramAccount.name, paramSyncResult);
        if ((!paramBundle.getBoolean("upload", false)) && (!paramBundle.getBoolean("picasa-sync-manager-requested", false)))
        {
          localPicasaSyncManager.resetSyncStates();
          SyncState.METADATA.onSyncRequested(PicasaDatabaseHelper.get(getContext()).getWritableDatabase(), paramAccount.name);
        }
        Log.d("gp.PicasaSyncService", "start sync on " + Utils.maskDebugInfo(paramAccount.name));
        try
        {
          localPicasaSyncManager.performSync(this.mSession);
          if (this.mSession.isSyncCancelled())
            Log.d("gp.PicasaSyncService", "sync cancelled");
          else
            Log.d("gp.PicasaSyncService", "sync finished");
        }
        catch (Exception localException1)
        {
          Log.e("gp.PicasaSyncService", "performSync error", localException1);
          SyncStats localSyncStats = paramSyncResult.stats;
          localSyncStats.numIoExceptions = (1L + localSyncStats.numIoExceptions);
          if (this.mSession.isSyncCancelled())
            Log.d("gp.PicasaSyncService", "sync cancelled");
          else
            Log.d("gp.PicasaSyncService", "sync finished");
        }
        finally
        {
          if (this.mSession.isSyncCancelled())
            Log.d("gp.PicasaSyncService", "sync cancelled");
          while (true)
          {
            throw localObject2;
            Log.d("gp.PicasaSyncService", "sync finished");
          }
        }
      }
    }

    public final void onSyncCanceled()
    {
      try
      {
        Log.d("gp.PicasaSyncService", "receive cancel request");
        super.onSyncCanceled();
        if (this.mSession != null)
          this.mSession.cancelSync();
        return;
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
 * Qualified Name:     com.google.android.picasasync.PicasaSyncService
 * JD-Core Version:    0.6.2
 */