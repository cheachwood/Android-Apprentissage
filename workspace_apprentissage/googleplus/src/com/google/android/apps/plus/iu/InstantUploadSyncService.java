package com.google.android.apps.plus.iu;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.content.SyncStats;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;
import java.util.Iterator;
import java.util.List;

public final class InstantUploadSyncService extends Service
{
  private static InstantUploadSyncAdapter sSyncAdapter;

  public static void activateAccount(Context paramContext, String paramString)
  {
    Account localAccount = new Account(paramString, "com.google");
    ContentResolver.setIsSyncable(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", 1);
    ContentResolver.setSyncAutomatically(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", true);
    ContentResolver.requestSync(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", new Bundle());
    resetSyncStates(paramContext, paramString);
    InstantUploadSyncManager.getInstance(paramContext).onAccountActivated(paramString);
  }

  public static void deactivateAccount(Context paramContext, String paramString)
  {
    Account localAccount = new Account(paramString, "com.google");
    ContentResolver.setIsSyncable(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", 0);
    ContentResolver.cancelSync(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider");
    InstantUploadSyncManager.getInstance(paramContext).onAccountDeactivated(paramString);
  }

  private static InstantUploadSyncAdapter getSyncAdapter(Context paramContext)
  {
    try
    {
      if (sSyncAdapter == null)
        sSyncAdapter = new InstantUploadSyncAdapter(paramContext);
      InstantUploadSyncAdapter localInstantUploadSyncAdapter = sSyncAdapter;
      return localInstantUploadSyncAdapter;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static void resetSyncStates(Context paramContext, String paramString)
  {
    Iterator localIterator = AccountsUtil.getAccounts(paramContext).iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (!TextUtils.equals(paramString, localAccount.name))
        deactivateAccount(paramContext, localAccount.name);
    }
  }

  public final IBinder onBind(Intent paramIntent)
  {
    return getSyncAdapter(this).getSyncAdapterBinder();
  }

  public static class CarryOverDummyReceiver extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
    }
  }

  private static final class InstantUploadSyncAdapter extends AbstractThreadedSyncAdapter
  {
    private final Context mContext;
    private InstantUploadSyncManager.SyncSession mSession;

    public InstantUploadSyncAdapter(Context paramContext)
    {
      super(false);
      this.mContext = paramContext;
    }

    private static void carryOverSyncAutomaticallyForAllAccounts(Context paramContext)
    {
      while (true)
      {
        int i;
        try
        {
          PackageManager localPackageManager = paramContext.getPackageManager();
          ComponentName localComponentName = new ComponentName(paramContext, InstantUploadSyncService.CarryOverDummyReceiver.class);
          if (localPackageManager.getComponentEnabledSetting(localComponentName) != 2)
          {
            localPackageManager.setComponentEnabledSetting(localComponentName, 2, 1);
            Account[] arrayOfAccount = AccountManager.get(paramContext).getAccountsByType("com.google");
            if (arrayOfAccount == null)
              return;
            i = -1 + arrayOfAccount.length;
            if (i < 0)
              continue;
            Account localAccount = arrayOfAccount[i];
            boolean bool1 = ContentResolver.getSyncAutomatically(localAccount, "com.google.android.apps.plus.content.EsGooglePhotoProvider");
            boolean bool2 = ContentResolver.getSyncAutomatically(localAccount, "com.google.android.apps.plus.content.EsProvider");
            if (EsLog.isLoggable("InstantUploadSyncSvc", 3))
              Log.d("InstantUploadSyncSvc", "Carry over sync; plus? " + bool2 + ", photo? " + bool1 + "; acct: " + Utils.maskDebugInfo(localAccount.name));
            if ((bool2) && (bool1))
              InstantUploadSyncService.activateAccount(paramContext, localAccount.name);
            else
              InstantUploadSyncService.deactivateAccount(paramContext, localAccount.name);
          }
        }
        finally
        {
        }
        if (EsLog.isLoggable("InstantUploadSyncSvc", 3))
        {
          Log.d("InstantUploadSyncSvc", "leave auto sync alone");
          continue;
          i--;
        }
      }
    }

    public final void onPerformSync(Account paramAccount, Bundle paramBundle, String paramString, ContentProviderClient paramContentProviderClient, SyncResult paramSyncResult)
    {
      InstantUploadSyncManager localInstantUploadSyncManager = InstantUploadSyncManager.getInstance(getContext());
      EsAccount localEsAccount = EsAccountsData.getActiveAccount(this.mContext);
      int i;
      if ((localEsAccount != null) && (paramAccount.name.equals(localEsAccount.getName())))
      {
        i = 1;
        if (paramBundle.getBoolean("initialize"))
        {
          carryOverSyncAutomaticallyForAllAccounts(getContext());
          if ((!"com.google".equals(paramAccount.type)) || (i == 0))
            break label414;
        }
      }
      label407: label414: for (int j = 1; ; j = 0)
      {
        if (j != 0);
        try
        {
          ContentResolver.setIsSyncable(paramAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", 1);
          break label407;
          ContentResolver.setIsSyncable(paramAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider", 0);
        }
        catch (Exception localException2)
        {
          if (!EsLog.isLoggable("InstantUploadSyncSvc", 6))
            break label407;
        }
        Log.e("InstantUploadSyncSvc", "cannot do sync", localException2);
        break label407;
        if (i != 0)
        {
          NewMediaTracker.getInstance(this.mContext).processNewMedia();
          try
          {
            if (Thread.currentThread().isInterrupted())
              if (EsLog.isLoggable("InstantUploadSyncSvc", 3))
                Log.d("InstantUploadSyncSvc", "sync is cancelled");
          }
          finally
          {
          }
          this.mSession = InstantUploadSyncManager.createSession(paramAccount.name, paramSyncResult);
          if (EsLog.isLoggable("InstantUploadSyncSvc", 3))
            Log.d("InstantUploadSyncSvc", "start to perform sync on " + Utils.maskDebugInfo(paramAccount.name));
          try
          {
            localInstantUploadSyncManager.performSync(this.mSession);
          }
          catch (Exception localException1)
          {
            if (EsLog.isLoggable("InstantUploadSyncSvc", 6))
              Log.e("InstantUploadSyncSvc", "performSync error", localException1);
            SyncStats localSyncStats = paramSyncResult.stats;
            localSyncStats.numIoExceptions = (1L + localSyncStats.numIoExceptions);
          }
          finally
          {
            if (EsLog.isLoggable("InstantUploadSyncSvc", 3))
              Log.d("InstantUploadSyncSvc", "sync finished - " + this.mSession.isSyncCancelled());
          }
        }
        return;
        i = 0;
        break;
      }
    }

    public final void onSyncCanceled()
    {
      try
      {
        if (EsLog.isLoggable("InstantUploadSyncSvc", 3))
          Log.d("InstantUploadSyncSvc", "receive cancel request");
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
 * Qualified Name:     com.google.android.apps.plus.iu.InstantUploadSyncService
 * JD-Core Version:    0.6.2
 */