package com.google.android.apps.plus.service;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.content.SyncStats;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class EsSyncAdapterService extends Service
{
  private static SyncState sCurrentSyncState;
  private static SyncAdapterImpl sSyncAdapter = null;
  private static final Object sSyncAdapterLock = new Object();
  private static HashMap<String, SyncState> sSyncStates = new HashMap();

  public static void activateAccount(Context paramContext, String paramString)
  {
    Account localAccount = AccountsUtil.newAccount(paramString);
    ContentResolver.setIsSyncable(localAccount, "com.google.android.apps.plus.content.EsProvider", 1);
    ContentResolver.setSyncAutomatically(localAccount, "com.google.android.apps.plus.content.EsProvider", true);
    ContentResolver.requestSync(localAccount, "com.google.android.apps.plus.content.EsProvider", new Bundle());
    resetSyncStates(paramContext, paramString);
  }

  public static void deactivateAccount(String paramString)
  {
    Account localAccount = AccountsUtil.newAccount(paramString);
    ContentResolver.setIsSyncable(localAccount, "com.google.android.apps.plus.content.EsProvider", 0);
    ContentResolver.cancelSync(localAccount, "com.google.android.apps.plus.content.EsProvider");
    SyncState localSyncState = (SyncState)sSyncStates.get(paramString);
    if (localSyncState != null)
      localSyncState.cancel();
  }

  public static SyncState getAccountSyncState(String paramString)
  {
    synchronized (sSyncStates)
    {
      SyncState localSyncState = (SyncState)sSyncStates.get(paramString);
      if (localSyncState == null)
      {
        localSyncState = new SyncState();
        sSyncStates.put(paramString, localSyncState);
      }
      return localSyncState;
    }
  }

  private static void resetSyncStates(Context paramContext, String paramString)
  {
    Iterator localIterator = AccountsUtil.getAccounts(paramContext).iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (!TextUtils.equals(paramString, localAccount.name))
        deactivateAccount(localAccount.name);
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return sSyncAdapter.getSyncAdapterBinder();
  }

  public void onCreate()
  {
    synchronized (sSyncAdapterLock)
    {
      if (sSyncAdapter == null)
        sSyncAdapter = new SyncAdapterImpl(getApplicationContext());
      return;
    }
  }

  private static final class SyncAdapterImpl extends AbstractThreadedSyncAdapter
  {
    private final Context context;

    public SyncAdapterImpl(Context paramContext)
    {
      super(false);
      this.context = paramContext;
    }

    private boolean isSubscribed(Account paramAccount, String paramString1, String paramString2)
    {
      String[] arrayOfString = new String[5];
      arrayOfString[0] = "com.google.android.apps.plus.content.EsProvider";
      arrayOfString[1] = paramString1;
      arrayOfString[2] = paramAccount.name;
      arrayOfString[3] = paramAccount.type;
      arrayOfString[4] = paramString2;
      Cursor localCursor = getContext().getContentResolver().query(SubscribedFeeds.Feeds.CONTENT_URI, new String[] { "_id" }, "authority = ? AND feed = ? AND _sync_account = ? AND _sync_account_type = ? AND service = ?", arrayOfString, null);
      boolean bool2;
      if (localCursor == null)
        bool2 = false;
      while (true)
      {
        return bool2;
        try
        {
          boolean bool1 = localCursor.moveToFirst();
          bool2 = bool1;
          localCursor.close();
        }
        finally
        {
          localCursor.close();
        }
      }
    }

    private void subscribe(Account paramAccount, String paramString1, String paramString2)
    {
      if (EsLog.isLoggable("EsSyncAdapterService", 3))
        Log.d("EsSyncAdapterService", "  --> Subscribe all feeds");
      ContentResolver localContentResolver = getContext().getContentResolver();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("feed", paramString1);
      localContentValues.put("_sync_account", paramAccount.name);
      localContentValues.put("_sync_account_type", paramAccount.type);
      localContentValues.put("authority", "com.google.android.apps.plus.content.EsProvider");
      localContentValues.put("service", paramString2);
      localContentResolver.insert(SubscribedFeeds.Feeds.CONTENT_URI, localContentValues);
    }

    private void updateSubscribedFeeds(Account paramAccount)
    {
      int i;
      if ((ContentResolver.getMasterSyncAutomatically()) && (ContentResolver.getSyncAutomatically(paramAccount, "com.google.android.apps.plus.content.EsProvider")))
      {
        i = 1;
        if (i == 0)
          break label144;
        int j = 1;
        if (!isSubscribed(paramAccount, "https://m.google.com/app/feed/notifications?authority=com.google.plus.notifications", "webupdates"))
        {
          subscribe(paramAccount, "https://m.google.com/app/feed/notifications?authority=com.google.plus.notifications", "webupdates");
          j = 0;
        }
        if (!isSubscribed(paramAccount, "com.google.plus.events", "events"))
        {
          subscribe(paramAccount, "com.google.plus.events", "events");
          j = 0;
        }
        if (j != 0)
        {
          if (EsLog.isLoggable("EsSyncAdapterService", 3))
            Log.d("EsSyncAdapterService", "  --> Already subscribed");
          String[] arrayOfString2 = new String[3];
          arrayOfString2[0] = "com.google.plus.notifications";
          arrayOfString2[1] = "https://m.google.com/app/feed/notifications?authority=com.google.plus.notifications";
          arrayOfString2[2] = paramAccount.type;
          getContext().getContentResolver().delete(SubscribedFeeds.Feeds.CONTENT_URI, "authority = ? AND feed = ? AND _sync_account_type = ?", arrayOfString2);
        }
      }
      while (true)
      {
        return;
        i = 0;
        break;
        label144: if (EsLog.isLoggable("EsSyncAdapterService", 3))
          Log.d("EsSyncAdapterService", "  --> Unsubscribe all feeds");
        ContentResolver localContentResolver = getContext().getContentResolver();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("_sync_account=?");
        localStringBuilder.append(" AND _sync_account_type=?");
        localStringBuilder.append(" AND authority=?");
        Uri localUri = SubscribedFeeds.Feeds.CONTENT_URI;
        String str = localStringBuilder.toString();
        String[] arrayOfString1 = new String[3];
        arrayOfString1[0] = paramAccount.name;
        arrayOfString1[1] = paramAccount.type;
        arrayOfString1[2] = "com.google.android.apps.plus.content.EsProvider";
        localContentResolver.delete(localUri, str, arrayOfString1);
      }
    }

    // ERROR //
    public final void onPerformSync(Account paramAccount, final Bundle paramBundle, String paramString, android.content.ContentProviderClient paramContentProviderClient, SyncResult paramSyncResult)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   4: invokestatic 166	com/google/android/apps/plus/content/EsAccountsData:getActiveAccount	(Landroid/content/Context;)Lcom/google/android/apps/plus/content/EsAccount;
      //   7: astore 6
      //   9: aload 6
      //   11: ifnull +75 -> 86
      //   14: aload_1
      //   15: getfield 27	android/accounts/Account:name	Ljava/lang/String;
      //   18: aload 6
      //   20: invokevirtual 171	com/google/android/apps/plus/content/EsAccount:getName	()Ljava/lang/String;
      //   23: invokevirtual 175	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   26: ifeq +60 -> 86
      //   29: iconst_1
      //   30: istore 7
      //   32: aload_2
      //   33: ifnull +59 -> 92
      //   36: aload_2
      //   37: ldc 177
      //   39: iconst_0
      //   40: invokevirtual 183	android/os/Bundle:getBoolean	(Ljava/lang/String;Z)Z
      //   43: ifeq +49 -> 92
      //   46: iconst_1
      //   47: istore 8
      //   49: iload 8
      //   51: ifeq +53 -> 104
      //   54: aload_1
      //   55: getfield 27	android/accounts/Account:name	Ljava/lang/String;
      //   58: invokestatic 189	com/google/android/apps/plus/util/AccountsUtil:newAccount	(Ljava/lang/String;)Landroid/accounts/Account;
      //   61: astore 29
      //   63: iload 7
      //   65: ifeq +33 -> 98
      //   68: iconst_1
      //   69: istore 30
      //   71: aload 29
      //   73: ldc 21
      //   75: iload 30
      //   77: invokestatic 193	android/content/ContentResolver:setIsSyncable	(Landroid/accounts/Account;Ljava/lang/String;I)V
      //   80: aload_0
      //   81: aload_1
      //   82: invokespecial 195	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:updateSubscribedFeeds	(Landroid/accounts/Account;)V
      //   85: return
      //   86: iconst_0
      //   87: istore 7
      //   89: goto -57 -> 32
      //   92: iconst_0
      //   93: istore 8
      //   95: goto -46 -> 49
      //   98: iconst_0
      //   99: istore 30
      //   101: goto -30 -> 71
      //   104: aload_0
      //   105: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   108: astore 9
      //   110: aload 9
      //   112: ldc 197
      //   114: iconst_0
      //   115: invokevirtual 201	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
      //   118: astore 10
      //   120: aload 10
      //   122: ldc 203
      //   124: iconst_0
      //   125: invokeinterface 206 3 0
      //   130: ifne +104 -> 234
      //   133: aload 9
      //   135: invokestatic 212	android/accounts/AccountManager:get	(Landroid/content/Context;)Landroid/accounts/AccountManager;
      //   138: ldc 214
      //   140: invokevirtual 218	android/accounts/AccountManager:getAccountsByType	(Ljava/lang/String;)[Landroid/accounts/Account;
      //   143: astore 23
      //   145: aload 23
      //   147: ifnull +66 -> 213
      //   150: aload 23
      //   152: arraylength
      //   153: istore 25
      //   155: iconst_0
      //   156: istore 26
      //   158: iload 26
      //   160: iload 25
      //   162: if_icmpge +51 -> 213
      //   165: aload 23
      //   167: iload 26
      //   169: aaload
      //   170: astore 27
      //   172: aload 27
      //   174: ldc 21
      //   176: invokestatic 222	android/content/ContentResolver:getIsSyncable	(Landroid/accounts/Account;Ljava/lang/String;)I
      //   179: iconst_1
      //   180: if_icmpne +27 -> 207
      //   183: iconst_1
      //   184: istore 28
      //   186: iload 28
      //   188: ifne +13 -> 201
      //   191: aload 9
      //   193: aload 27
      //   195: getfield 27	android/accounts/Account:name	Ljava/lang/String;
      //   198: invokestatic 228	com/google/android/apps/plus/iu/InstantUploadSyncService:deactivateAccount	(Landroid/content/Context;Ljava/lang/String;)V
      //   201: iinc 26 1
      //   204: goto -46 -> 158
      //   207: iconst_0
      //   208: istore 28
      //   210: goto -24 -> 186
      //   213: aload 10
      //   215: invokeinterface 232 1 0
      //   220: ldc 203
      //   222: iconst_1
      //   223: invokeinterface 238 3 0
      //   228: invokeinterface 241 1 0
      //   233: pop
      //   234: aload_0
      //   235: aload_1
      //   236: invokespecial 195	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:updateSubscribedFeeds	(Landroid/accounts/Account;)V
      //   239: iload 7
      //   241: ifne +75 -> 316
      //   244: aload_0
      //   245: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   248: invokestatic 244	com/google/android/apps/plus/content/EsAccountsData:getActiveAccountUnsafe	(Landroid/content/Context;)Lcom/google/android/apps/plus/content/EsAccount;
      //   251: astore 19
      //   253: aload 19
      //   255: ifnull -170 -> 85
      //   258: aload_0
      //   259: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   262: aload 19
      //   264: invokestatic 248	com/google/android/apps/plus/content/EsAccountsData:isAccountUpgradeRequired	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Z
      //   267: ifeq -182 -> 85
      //   270: aload_0
      //   271: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   274: aload 19
      //   276: invokestatic 252	com/google/android/apps/plus/content/EsAccountsData:upgradeAccount	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
      //   279: aload_0
      //   280: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   283: invokestatic 166	com/google/android/apps/plus/content/EsAccountsData:getActiveAccount	(Landroid/content/Context;)Lcom/google/android/apps/plus/content/EsAccount;
      //   286: astore 6
      //   288: aload 6
      //   290: ifnull +213 -> 503
      //   293: aload_1
      //   294: getfield 27	android/accounts/Account:name	Ljava/lang/String;
      //   297: aload 6
      //   299: invokevirtual 171	com/google/android/apps/plus/content/EsAccount:getName	()Ljava/lang/String;
      //   302: invokevirtual 175	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   305: ifeq +198 -> 503
      //   308: iconst_1
      //   309: istore 22
      //   311: iload 22
      //   313: ifeq -228 -> 85
      //   316: aload_2
      //   317: ifnull +102 -> 419
      //   320: aload_2
      //   321: ldc 90
      //   323: invokevirtual 256	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
      //   326: ifeq +93 -> 419
      //   329: aload_2
      //   330: ldc 90
      //   332: invokevirtual 260	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   335: astore 16
      //   337: ldc 70
      //   339: iconst_3
      //   340: invokestatic 76	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
      //   343: ifeq +27 -> 370
      //   346: ldc 70
      //   348: new 141	java/lang/StringBuilder
      //   351: dup
      //   352: ldc_w 262
      //   355: invokespecial 265	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   358: aload 16
      //   360: invokevirtual 148	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   363: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   366: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   369: pop
      //   370: aload_2
      //   371: ldc_w 267
      //   374: iconst_1
      //   375: invokevirtual 270	android/os/Bundle:putBoolean	(Ljava/lang/String;Z)V
      //   378: ldc 117
      //   380: aload 16
      //   382: invokevirtual 175	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   385: ifeq +124 -> 509
      //   388: aload_2
      //   389: ldc_w 272
      //   392: iconst_1
      //   393: invokevirtual 276	android/os/Bundle:putInt	(Ljava/lang/String;I)V
      //   396: aload_0
      //   397: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   400: aload 6
      //   402: new 278	com/google/android/apps/plus/analytics/AnalyticsInfo
      //   405: dup
      //   406: getstatic 284	com/google/android/apps/plus/analytics/OzViews:NOTIFICATIONS_SYSTEM	Lcom/google/android/apps/plus/analytics/OzViews;
      //   409: invokespecial 287	com/google/android/apps/plus/analytics/AnalyticsInfo:<init>	(Lcom/google/android/apps/plus/analytics/OzViews;)V
      //   412: getstatic 293	com/google/android/apps/plus/analytics/OzActions:TICKLE_NOTIFICATION_RECEIVED	Lcom/google/android/apps/plus/analytics/OzActions;
      //   415: aconst_null
      //   416: invokestatic 299	com/google/android/apps/plus/analytics/EsAnalytics:postRecordEvent	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/android/apps/plus/analytics/AnalyticsInfo;Lcom/google/android/apps/plus/analytics/OzActions;Landroid/os/Bundle;)V
      //   419: aload_1
      //   420: getfield 27	android/accounts/Account:name	Ljava/lang/String;
      //   423: invokestatic 305	com/google/android/apps/plus/service/EsSyncAdapterService:getAccountSyncState	(Ljava/lang/String;)Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;
      //   426: invokestatic 309	com/google/android/apps/plus/service/EsSyncAdapterService:access$002	(Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;)Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;
      //   429: pop
      //   430: aload_0
      //   431: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   434: aload 6
      //   436: aload_2
      //   437: invokestatic 313	com/google/android/apps/plus/service/EsSyncAdapterService:access$000	()Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;
      //   440: aload 5
      //   442: invokestatic 317	com/google/android/apps/plus/service/EsSyncAdapterService:access$100	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Landroid/os/Bundle;Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;Landroid/content/SyncResult;)V
      //   445: invokestatic 313	com/google/android/apps/plus/service/EsSyncAdapterService:access$000	()Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;
      //   448: invokevirtual 322	com/google/android/apps/plus/service/EsSyncAdapterService$SyncState:isCanceled	()Z
      //   451: ifne +28 -> 479
      //   454: new 324	android/os/Handler
      //   457: dup
      //   458: invokestatic 330	android/os/Looper:getMainLooper	()Landroid/os/Looper;
      //   461: invokespecial 333	android/os/Handler:<init>	(Landroid/os/Looper;)V
      //   464: new 335	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl$1
      //   467: dup
      //   468: aload_0
      //   469: aload 6
      //   471: aload_2
      //   472: invokespecial 338	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl$1:<init>	(Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl;Lcom/google/android/apps/plus/content/EsAccount;Landroid/os/Bundle;)V
      //   475: invokevirtual 342	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   478: pop
      //   479: aconst_null
      //   480: invokestatic 309	com/google/android/apps/plus/service/EsSyncAdapterService:access$002	(Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;)Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;
      //   483: pop
      //   484: goto -399 -> 85
      //   487: astore 20
      //   489: ldc 70
      //   491: ldc_w 344
      //   494: aload 20
      //   496: invokestatic 348	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   499: pop
      //   500: goto -415 -> 85
      //   503: iconst_0
      //   504: istore 22
      //   506: goto -195 -> 311
      //   509: ldc 125
      //   511: aload 16
      //   513: invokevirtual 175	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   516: ifeq +37 -> 553
      //   519: aload_2
      //   520: ldc_w 272
      //   523: iconst_2
      //   524: invokevirtual 276	android/os/Bundle:putInt	(Ljava/lang/String;I)V
      //   527: aload_0
      //   528: getfield 13	com/google/android/apps/plus/service/EsSyncAdapterService$SyncAdapterImpl:context	Landroid/content/Context;
      //   531: aload 6
      //   533: new 278	com/google/android/apps/plus/analytics/AnalyticsInfo
      //   536: dup
      //   537: getstatic 284	com/google/android/apps/plus/analytics/OzViews:NOTIFICATIONS_SYSTEM	Lcom/google/android/apps/plus/analytics/OzViews;
      //   540: invokespecial 287	com/google/android/apps/plus/analytics/AnalyticsInfo:<init>	(Lcom/google/android/apps/plus/analytics/OzViews;)V
      //   543: getstatic 351	com/google/android/apps/plus/analytics/OzActions:TICKLE_EVENT_RECEIVED	Lcom/google/android/apps/plus/analytics/OzActions;
      //   546: aconst_null
      //   547: invokestatic 299	com/google/android/apps/plus/analytics/EsAnalytics:postRecordEvent	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/android/apps/plus/analytics/AnalyticsInfo;Lcom/google/android/apps/plus/analytics/OzActions;Landroid/os/Bundle;)V
      //   550: goto -131 -> 419
      //   553: ldc 70
      //   555: new 141	java/lang/StringBuilder
      //   558: dup
      //   559: ldc_w 353
      //   562: invokespecial 265	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   565: aload 16
      //   567: invokevirtual 148	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   570: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   573: invokestatic 355	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   576: pop
      //   577: goto -492 -> 85
      //   580: astore 12
      //   582: aconst_null
      //   583: invokestatic 309	com/google/android/apps/plus/service/EsSyncAdapterService:access$002	(Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;)Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;
      //   586: pop
      //   587: aload 12
      //   589: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   270	279	487	java/lang/Exception
      //   430	479	580	finally
    }

    public final void onSyncCanceled()
    {
      if (EsSyncAdapterService.sCurrentSyncState != null)
        EsSyncAdapterService.sCurrentSyncState.cancel();
    }
  }

  private static final class SyncListener
    implements HttpOperation.OperationListener
  {
    private final SyncResult mSyncResult;

    public SyncListener(SyncResult paramSyncResult)
    {
      this.mSyncResult = paramSyncResult;
    }

    public final void onOperationComplete(HttpOperation paramHttpOperation)
    {
      int i = paramHttpOperation.getErrorCode();
      Exception localException = paramHttpOperation.getException();
      if (EsLog.isLoggable("EsSyncAdapterService", 3))
        Log.d("EsSyncAdapterService", "Sync operation complete: " + i + '/' + paramHttpOperation.getReasonPhrase() + '/' + localException);
      if (localException != null)
        if ((localException instanceof AuthenticatorException))
        {
          SyncStats localSyncStats5 = this.mSyncResult.stats;
          localSyncStats5.numAuthExceptions = (1L + localSyncStats5.numAuthExceptions);
        }
      while (true)
      {
        return;
        if (!(localException instanceof OperationCanceledException))
          if ((localException instanceof IOException))
          {
            SyncStats localSyncStats4 = this.mSyncResult.stats;
            localSyncStats4.numIoExceptions = (1L + localSyncStats4.numIoExceptions);
          }
          else
          {
            SyncStats localSyncStats3 = this.mSyncResult.stats;
            localSyncStats3.numIoExceptions = (1L + localSyncStats3.numIoExceptions);
            continue;
            if (i == 401)
            {
              SyncStats localSyncStats2 = this.mSyncResult.stats;
              localSyncStats2.numAuthExceptions = (1L + localSyncStats2.numAuthExceptions);
            }
            else if (paramHttpOperation.hasError())
            {
              SyncStats localSyncStats1 = this.mSyncResult.stats;
              localSyncStats1.numIoExceptions = (1L + localSyncStats1.numIoExceptions);
            }
          }
      }
    }
  }

  public static final class SyncOperationState
  {
    public int count;
    public long duration;
    public HttpTransactionMetrics metrics;
    public String operation;
    public int subCount;
  }

  public static final class SyncState
  {
    private boolean mCanceled;
    private int mCurrentCount;
    private HttpTransactionMetrics mCurrentMetrics;
    private String mCurrentOperation;
    private long mCurrentOperationStart;
    private int mCurrentSubCount;
    private boolean mFullSync;
    private final ArrayList<EsSyncAdapterService.SyncOperationState> mOperations = new ArrayList();
    private final LinkedBlockingQueue<Bundle> mRequestQueue = new LinkedBlockingQueue();
    private long mStartTimestamp;
    private String mSyncName;

    private static void logSyncStats(SyncState paramSyncState)
    {
      while (true)
      {
        EsSyncAdapterService.SyncOperationState localSyncOperationState;
        try
        {
          if (!EsLog.isLoggable("EsSyncAdapterService", 4))
            break;
          Log.i("EsSyncAdapterService", paramSyncState.mSyncName + " finished. Elapsed time: " + (System.currentTimeMillis() - paramSyncState.mStartTimestamp) + "ms");
          Iterator localIterator = paramSyncState.mOperations.iterator();
          if (!localIterator.hasNext())
            break;
          localSyncOperationState = (EsSyncAdapterService.SyncOperationState)localIterator.next();
          if (localSyncOperationState.count == 0)
          {
            l = localSyncOperationState.duration;
            Log.i("EsSyncAdapterService", "  [" + localSyncOperationState.operation + "] items: " + localSyncOperationState.count + ", sub-items: " + localSyncOperationState.subCount + ", duration: " + localSyncOperationState.duration + "ms, avg: " + l + "ms");
            localSyncOperationState.metrics.log("EsSyncAdapterService", "    ");
            continue;
          }
        }
        finally
        {
        }
        double d = localSyncOperationState.duration;
        int i = localSyncOperationState.count;
        long l = ()(d / i);
      }
    }

    private void onFinish(int paramInt1, int paramInt2)
    {
      try
      {
        EsSyncAdapterService.SyncOperationState localSyncOperationState = new EsSyncAdapterService.SyncOperationState();
        localSyncOperationState.operation = this.mCurrentOperation;
        localSyncOperationState.duration = (System.currentTimeMillis() - this.mCurrentOperationStart);
        localSyncOperationState.count = paramInt1;
        localSyncOperationState.subCount = paramInt2;
        localSyncOperationState.metrics = this.mCurrentMetrics;
        this.mCurrentMetrics = null;
        this.mOperations.add(localSyncOperationState);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void cancel()
    {
      try
      {
        this.mCanceled = true;
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final HttpTransactionMetrics getHttpTransactionMetrics()
    {
      return this.mCurrentMetrics;
    }

    public final void incrementCount()
    {
      try
      {
        this.mCurrentCount = (1 + this.mCurrentCount);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void incrementCount(int paramInt)
    {
      try
      {
        this.mCurrentCount = (paramInt + this.mCurrentCount);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void incrementSubCount()
    {
      try
      {
        this.mCurrentSubCount = (1 + this.mCurrentSubCount);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final boolean isCanceled()
    {
      try
      {
        boolean bool = this.mCanceled;
        return bool;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void onFinish()
    {
      try
      {
        onFinish(this.mCurrentCount, this.mCurrentSubCount);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void onFinish(int paramInt)
    {
      try
      {
        onFinish(paramInt, 0);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void onStart(String paramString)
    {
      try
      {
        this.mCurrentOperation = paramString;
        this.mCurrentOperationStart = System.currentTimeMillis();
        this.mCurrentCount = 0;
        this.mCurrentSubCount = 0;
        this.mCurrentMetrics = new HttpTransactionMetrics();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void onSyncFinish()
    {
      try
      {
        logSyncStats(this);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void onSyncStart(String paramString)
    {
      try
      {
        if (EsLog.isLoggable("EsSyncAdapterService", 4))
          Log.i("EsSyncAdapterService", paramString + " started.");
        this.mSyncName = paramString;
        this.mCanceled = false;
        this.mStartTimestamp = System.currentTimeMillis();
        this.mOperations.clear();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final Bundle pollAccountSyncRequest()
    {
      return (Bundle)this.mRequestQueue.poll();
    }

    public final boolean requestAccountSync(Bundle paramBundle)
    {
      try
      {
        boolean bool = this.mRequestQueue.isEmpty();
        if (paramBundle == null)
          paramBundle = new Bundle();
        this.mRequestQueue.offer(paramBundle);
        return bool;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public final void setFullSync(boolean paramBoolean)
    {
      this.mFullSync = paramBoolean;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.EsSyncAdapterService
 * JD-Core Version:    0.6.2
 */