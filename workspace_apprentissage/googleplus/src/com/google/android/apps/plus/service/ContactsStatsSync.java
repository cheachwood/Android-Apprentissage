package com.google.android.apps.plus.service;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import com.google.android.apps.plus.api.SyncMobileContactsOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.util.AndroidUtils;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.DataCircleMemberId;
import com.google.api.services.plusi.model.MobileContact;
import com.google.api.services.plusi.model.MobileContactAffinity;
import java.util.ArrayList;
import java.util.List;

public final class ContactsStatsSync
{
  private static final String[] PROJECTION_FOR_ICS_AND_LATER = { "times_contacted", "last_time_contacted", "sourceid", "data_set" };
  private static final String[] PROJECTION_FOR_PRE_ICS = { "times_contacted", "last_time_contacted", "sourceid" };
  private boolean isFirstStatsSync;
  private final EsAccount mAccount;
  private final List<MobileContact> mContacts;
  private final Context mContext;
  private final EsSyncAdapterService.SyncState mSyncState;
  private long maxLastContacted = -1L;

  private ContactsStatsSync(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mContacts = new ArrayList();
    this.mSyncState = paramSyncState;
  }

  public static void sync(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    if (paramSyncState.isCanceled())
      return;
    if (EsLog.isLoggable("ContactsStatsSync", 3))
      Log.d("ContactsStatsSync", "Contacts stats sync operation started");
    ContactsStatsSync localContactsStatsSync = new ContactsStatsSync(paramContext, paramEsAccount, paramSyncState);
    Uri localUri = ContactsContract.RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("account_name", localContactsStatsSync.mAccount.getName()).appendQueryParameter("account_type", "com.google").build();
    long l1 = EsAccountsData.queryLastContactedTimestamp(localContactsStatsSync.mContext, localContactsStatsSync.mAccount);
    boolean bool;
    label89: String[] arrayOfString;
    if (l1 <= 0L)
    {
      bool = true;
      localContactsStatsSync.isFirstStatsSync = bool;
      if (Build.VERSION.SDK_INT >= 14)
        break label362;
      arrayOfString = PROJECTION_FOR_PRE_ICS;
    }
    while (true)
    {
      Cursor localCursor;
      MobileContact localMobileContact;
      String str;
      int i;
      try
      {
        localCursor = localContactsStatsSync.mContext.getContentResolver().query(localUri, arrayOfString, "times_contacted > 0", null, "last_time_contacted");
        long l2 = System.currentTimeMillis();
        if (localContactsStatsSync.isFirstStatsSync)
          l2 -= SystemClock.elapsedRealtime() / 2L;
        try
        {
          if (!localCursor.moveToNext())
            break label453;
          localMobileContact = new MobileContact();
          localMobileContact.id = new DataCircleMemberId();
          localMobileContact.affinity = new MobileContactAffinity();
          localMobileContact.affinity.outgoingPhoneCallCount = Integer.valueOf(localCursor.getInt(0));
          localMobileContact.affinity.lastOutgoingPhoneCallPosixTimestamp = Long.valueOf(localCursor.getLong(1));
          if (localMobileContact.affinity.lastOutgoingPhoneCallPosixTimestamp.longValue() > 0L)
            break label420;
          localMobileContact.affinity.lastOutgoingPhoneCallPosixTimestamp = Long.valueOf(l2);
          str = localCursor.getString(2);
          if ((localCursor.getColumnCount() <= 3) || (localCursor.isNull(3)) || (!"plus".equals(localCursor.getString(3))))
            break label463;
          i = 1;
          if (i == 0)
            break label440;
          localMobileContact.id.obfuscatedGaiaId = str;
          localContactsStatsSync.mContacts.add(localMobileContact);
          continue;
        }
        finally
        {
          localCursor.close();
        }
        bool = false;
        break label89;
        label362: arrayOfString = PROJECTION_FOR_ICS_AND_LATER;
        continue;
      }
      catch (RuntimeException localRuntimeException)
      {
        Log.e("ContactsStatsSync", "Query on RawContacts failed. " + localRuntimeException);
        localContactsStatsSync.upload();
      }
      if (!EsLog.isLoggable("ContactsStatsSync", 3))
        break;
      Log.d("ContactsStatsSync", "Contacts stats sync operation complete");
      break;
      label420: if (localMobileContact.affinity.lastOutgoingPhoneCallPosixTimestamp.longValue() > l1)
      {
        continue;
        label440: localMobileContact.id.contactId = str;
        continue;
        label453: localCursor.close();
        continue;
        label463: i = 0;
      }
    }
  }

  private void upload()
  {
    while (true)
    {
      if (!this.mContacts.isEmpty())
      {
        this.maxLastContacted = -1L;
        ArrayList localArrayList = new ArrayList();
        while ((!this.mContacts.isEmpty()) && (localArrayList.size() < 20))
        {
          MobileContact localMobileContact = (MobileContact)this.mContacts.remove(0);
          localArrayList.add(localMobileContact);
          if (this.maxLastContacted < localMobileContact.affinity.lastOutgoingPhoneCallPosixTimestamp.longValue())
            this.maxLastContacted = localMobileContact.affinity.lastOutgoingPhoneCallPosixTimestamp.longValue();
        }
        this.mSyncState.onStart("ContactsStatsSync:PartialUpload");
        SyncMobileContactsOperation localSyncMobileContactsOperation = new SyncMobileContactsOperation(this.mContext, this.mAccount, String.valueOf(AndroidUtils.getAndroidId(this.mContext)), localArrayList, "FULL", null, null);
        localSyncMobileContactsOperation.start(this.mSyncState, new HttpTransactionMetrics());
        this.mSyncState.onFinish();
        if (localSyncMobileContactsOperation.hasError())
          localSyncMobileContactsOperation.logError("ContactsStatsSync");
      }
      else
      {
        return;
      }
      EsAccountsData.saveLastStatsSyncTimestamp(this.mContext, this.mAccount);
      if ((EsAccountsData.queryLastContactedTimestamp(this.mContext, this.mAccount) < this.maxLastContacted) && ((!this.isFirstStatsSync) || (this.mContacts.isEmpty())))
        EsAccountsData.saveLastContactedTimestamp(this.mContext, this.mAccount, this.maxLastContacted);
    }
  }

  public static void wipeout(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    if (EsLog.isLoggable("ContactsStatsSync", 3))
      Log.d("ContactsStatsSync", "Contacts stats wipeout operation started");
    new SyncMobileContactsOperation(paramContext, paramEsAccount, String.valueOf(AndroidUtils.getAndroidId(paramContext)), null, "WIPEOUT", paramIntent, paramOperationListener).startThreaded();
    if (EsLog.isLoggable("ContactsStatsSync", 3))
      Log.d("ContactsStatsSync", "Contacts stats wipeout operation complete");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ContactsStatsSync
 * JD-Core Version:    0.6.2
 */