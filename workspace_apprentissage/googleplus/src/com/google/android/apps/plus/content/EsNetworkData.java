package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;

public final class EsNetworkData
{
  private static final Object mSyncLock = new Object();

  static void cleanupData$3105fef4()
  {
  }

  public static void clearTransactionData(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.delete("network_data_transactions", null, null);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.NETWORK_DATA_TRANSACTIONS_URI, paramEsAccount), null);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void deleteOldTransactions(SQLiteDatabase paramSQLiteDatabase)
  {
    long l = EsDatabaseHelper.getRowsCount(paramSQLiteDatabase, "network_data_transactions", null, null);
    if (EsLog.isLoggable("EsNetworkData", 3))
      Log.d("EsNetworkData", "deleteOldTransactions count: " + l);
    if (l - 100L <= 0L);
    while (true)
    {
      return;
      Cursor localCursor = paramSQLiteDatabase.query("network_data_transactions", TransactionIdsQuery.PROJECTION, null, null, null, null, "time ASC", Long.toString(l - 100L));
      if (localCursor != null)
      {
        StringBuffer localStringBuffer = new StringBuffer(256);
        while (true)
        {
          try
          {
            localStringBuffer.append("_id IN(");
            int i = 1;
            if (!localCursor.moveToNext())
              break;
            if (i != 0)
            {
              i = 0;
              localStringBuffer.append('\'');
              localStringBuffer.append(localCursor.getString(0));
              localStringBuffer.append('\'');
              continue;
            }
          }
          finally
          {
            localCursor.close();
          }
          localStringBuffer.append(',');
        }
        localStringBuffer.append(')');
        localCursor.close();
        paramSQLiteDatabase.delete("network_data_transactions", localStringBuffer.toString(), null);
      }
    }
  }

  // ERROR //
  public static void insertData(Context paramContext, EsAccount paramEsAccount, com.google.android.apps.plus.network.HttpTransactionMetrics paramHttpTransactionMetrics, java.lang.Exception paramException)
  {
    // Byte code:
    //   0: getstatic 154	com/google/android/apps/plus/util/EsLog:ENABLE_DOGFOOD_FEATURES	Z
    //   3: ifeq +22 -> 25
    //   6: aload_2
    //   7: ifnonnull +19 -> 26
    //   10: ldc 73
    //   12: ldc 156
    //   14: new 158	java/lang/Throwable
    //   17: dup
    //   18: invokespecial 159	java/lang/Throwable:<init>	()V
    //   21: invokestatic 163	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   24: pop
    //   25: return
    //   26: aload_3
    //   27: ifnonnull +26 -> 53
    //   30: aload_2
    //   31: ldc 73
    //   33: ldc 165
    //   35: invokevirtual 171	com/google/android/apps/plus/network/HttpTransactionMetrics:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   38: aload_1
    //   39: ifnonnull +39 -> 78
    //   42: ldc 73
    //   44: ldc 173
    //   46: invokestatic 175	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   49: pop
    //   50: goto -25 -> 25
    //   53: aload_2
    //   54: ldc 73
    //   56: new 81	java/lang/StringBuilder
    //   59: dup
    //   60: ldc 165
    //   62: invokespecial 86	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   65: aload_3
    //   66: invokevirtual 178	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   69: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   72: invokevirtual 171	com/google/android/apps/plus/network/HttpTransactionMetrics:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   75: goto -37 -> 38
    //   78: aload_0
    //   79: aload_1
    //   80: invokestatic 22	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   83: invokevirtual 26	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   86: astore 6
    //   88: aload 6
    //   90: invokevirtual 31	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   93: new 180	android/content/ContentValues
    //   96: dup
    //   97: invokespecial 181	android/content/ContentValues:<init>	()V
    //   100: astore 8
    //   102: aload 8
    //   104: ldc 183
    //   106: aload_2
    //   107: invokevirtual 186	com/google/android/apps/plus/network/HttpTransactionMetrics:getName	()Ljava/lang/String;
    //   110: invokevirtual 189	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   113: aload 8
    //   115: ldc 191
    //   117: invokestatic 197	java/lang/System:currentTimeMillis	()J
    //   120: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   123: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   126: aload 8
    //   128: ldc 206
    //   130: aload_2
    //   131: invokevirtual 209	com/google/android/apps/plus/network/HttpTransactionMetrics:getDuration	()J
    //   134: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   137: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   140: aload 8
    //   142: ldc 211
    //   144: aload_2
    //   145: invokevirtual 214	com/google/android/apps/plus/network/HttpTransactionMetrics:getProcessingDuration	()J
    //   148: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   151: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   154: aload 8
    //   156: ldc 216
    //   158: aload_2
    //   159: invokevirtual 219	com/google/android/apps/plus/network/HttpTransactionMetrics:getSentBytes	()J
    //   162: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   165: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   168: aload 8
    //   170: ldc 221
    //   172: aload_2
    //   173: invokevirtual 224	com/google/android/apps/plus/network/HttpTransactionMetrics:getReceivedBytes	()J
    //   176: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   179: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   182: aload 8
    //   184: ldc 226
    //   186: aload_2
    //   187: invokevirtual 229	com/google/android/apps/plus/network/HttpTransactionMetrics:getRequestCount	()J
    //   190: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   193: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   196: aload_3
    //   197: ifnull +54 -> 251
    //   200: aload_3
    //   201: invokevirtual 234	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   204: ifnull +192 -> 396
    //   207: aload 8
    //   209: ldc 236
    //   211: new 81	java/lang/StringBuilder
    //   214: dup
    //   215: invokespecial 237	java/lang/StringBuilder:<init>	()V
    //   218: aload_3
    //   219: invokevirtual 241	java/lang/Object:getClass	()Ljava/lang/Class;
    //   222: invokevirtual 246	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   225: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: ldc 251
    //   230: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: aload_3
    //   234: invokevirtual 234	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   237: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: ldc 253
    //   242: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   248: invokevirtual 189	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   251: aload 6
    //   253: ldc 33
    //   255: ldc 255
    //   257: aload 8
    //   259: invokevirtual 259	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   262: pop2
    //   263: aload 6
    //   265: invokestatic 261	com/google/android/apps/plus/content/EsNetworkData:deleteOldTransactions	(Landroid/database/sqlite/SQLiteDatabase;)V
    //   268: aload_3
    //   269: ifnonnull +53 -> 322
    //   272: iconst_1
    //   273: anewarray 263	java/lang/String
    //   276: dup
    //   277: iconst_0
    //   278: aload_2
    //   279: invokevirtual 186	com/google/android/apps/plus/network/HttpTransactionMetrics:getName	()Ljava/lang/String;
    //   282: aastore
    //   283: astore 11
    //   285: getstatic 13	com/google/android/apps/plus/content/EsNetworkData:mSyncLock	Ljava/lang/Object;
    //   288: astore 12
    //   290: aload 12
    //   292: monitorenter
    //   293: aload 6
    //   295: ldc_w 265
    //   298: getstatic 268	com/google/android/apps/plus/content/EsNetworkData$StatsQuery:PROJECTION	[Ljava/lang/String;
    //   301: ldc_w 270
    //   304: aload 11
    //   306: aconst_null
    //   307: aconst_null
    //   308: aconst_null
    //   309: invokevirtual 273	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   312: astore 14
    //   314: aload 14
    //   316: ifnonnull +107 -> 423
    //   319: aload 12
    //   321: monitorexit
    //   322: aload 6
    //   324: invokevirtual 40	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   327: aload 6
    //   329: invokevirtual 43	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   332: aload_0
    //   333: invokevirtual 49	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   336: getstatic 55	com/google/android/apps/plus/content/EsProvider:NETWORK_DATA_TRANSACTIONS_URI	Landroid/net/Uri;
    //   339: aload_1
    //   340: invokestatic 59	com/google/android/apps/plus/content/EsProvider:appendAccountParameter	(Landroid/net/Uri;Lcom/google/android/apps/plus/content/EsAccount;)Landroid/net/Uri;
    //   343: aconst_null
    //   344: invokevirtual 65	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   347: aload_0
    //   348: invokevirtual 49	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   351: getstatic 276	com/google/android/apps/plus/content/EsProvider:NETWORK_DATA_STATS_URI	Landroid/net/Uri;
    //   354: aload_1
    //   355: invokestatic 59	com/google/android/apps/plus/content/EsProvider:appendAccountParameter	(Landroid/net/Uri;Lcom/google/android/apps/plus/content/EsAccount;)Landroid/net/Uri;
    //   358: aconst_null
    //   359: invokevirtual 65	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   362: goto -337 -> 25
    //   365: astore 4
    //   367: ldc 73
    //   369: new 81	java/lang/StringBuilder
    //   372: dup
    //   373: ldc_w 278
    //   376: invokespecial 86	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   379: aload_2
    //   380: invokevirtual 186	com/google/android/apps/plus/network/HttpTransactionMetrics:getName	()Ljava/lang/String;
    //   383: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   386: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   389: invokestatic 175	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   392: pop
    //   393: goto -368 -> 25
    //   396: aload 8
    //   398: ldc 236
    //   400: aload_3
    //   401: invokevirtual 241	java/lang/Object:getClass	()Ljava/lang/Class;
    //   404: invokevirtual 246	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   407: invokevirtual 189	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   410: goto -159 -> 251
    //   413: astore 7
    //   415: aload 6
    //   417: invokevirtual 43	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   420: aload 7
    //   422: athrow
    //   423: new 180	android/content/ContentValues
    //   426: dup
    //   427: invokespecial 181	android/content/ContentValues:<init>	()V
    //   430: astore 15
    //   432: aload 14
    //   434: invokeinterface 281 1 0
    //   439: ifeq +169 -> 608
    //   442: aload 15
    //   444: ldc_w 283
    //   447: invokestatic 197	java/lang/System:currentTimeMillis	()J
    //   450: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   453: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   456: aload 15
    //   458: ldc 206
    //   460: aload_2
    //   461: invokevirtual 209	com/google/android/apps/plus/network/HttpTransactionMetrics:getDuration	()J
    //   464: aload 14
    //   466: iconst_0
    //   467: invokeinterface 287 2 0
    //   472: ladd
    //   473: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   476: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   479: aload 15
    //   481: ldc 211
    //   483: aload_2
    //   484: invokevirtual 214	com/google/android/apps/plus/network/HttpTransactionMetrics:getProcessingDuration	()J
    //   487: aload 14
    //   489: iconst_1
    //   490: invokeinterface 287 2 0
    //   495: ladd
    //   496: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   499: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   502: aload 15
    //   504: ldc 216
    //   506: aload_2
    //   507: invokevirtual 219	com/google/android/apps/plus/network/HttpTransactionMetrics:getSentBytes	()J
    //   510: aload 14
    //   512: iconst_2
    //   513: invokeinterface 287 2 0
    //   518: ladd
    //   519: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   522: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   525: aload 15
    //   527: ldc 221
    //   529: aload_2
    //   530: invokevirtual 224	com/google/android/apps/plus/network/HttpTransactionMetrics:getReceivedBytes	()J
    //   533: aload 14
    //   535: iconst_3
    //   536: invokeinterface 287 2 0
    //   541: ladd
    //   542: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   545: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   548: aload 15
    //   550: ldc 226
    //   552: aload 14
    //   554: iconst_4
    //   555: invokeinterface 287 2 0
    //   560: aload_2
    //   561: invokevirtual 229	com/google/android/apps/plus/network/HttpTransactionMetrics:getRequestCount	()J
    //   564: ladd
    //   565: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   568: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   571: aload 6
    //   573: ldc_w 265
    //   576: aload 15
    //   578: ldc_w 270
    //   581: aload 11
    //   583: invokevirtual 291	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   586: pop
    //   587: aload 14
    //   589: invokeinterface 145 1 0
    //   594: aload 12
    //   596: monitorexit
    //   597: goto -275 -> 322
    //   600: astore 13
    //   602: aload 12
    //   604: monitorexit
    //   605: aload 13
    //   607: athrow
    //   608: aload 15
    //   610: ldc 183
    //   612: aload_2
    //   613: invokevirtual 186	com/google/android/apps/plus/network/HttpTransactionMetrics:getName	()Ljava/lang/String;
    //   616: invokevirtual 189	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   619: aload 15
    //   621: ldc_w 293
    //   624: invokestatic 197	java/lang/System:currentTimeMillis	()J
    //   627: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   630: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   633: aload 15
    //   635: ldc_w 283
    //   638: invokestatic 197	java/lang/System:currentTimeMillis	()J
    //   641: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   644: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   647: aload 15
    //   649: ldc 206
    //   651: aload_2
    //   652: invokevirtual 209	com/google/android/apps/plus/network/HttpTransactionMetrics:getDuration	()J
    //   655: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   658: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   661: aload 15
    //   663: ldc 211
    //   665: aload_2
    //   666: invokevirtual 214	com/google/android/apps/plus/network/HttpTransactionMetrics:getProcessingDuration	()J
    //   669: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   672: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   675: aload 15
    //   677: ldc 216
    //   679: aload_2
    //   680: invokevirtual 219	com/google/android/apps/plus/network/HttpTransactionMetrics:getSentBytes	()J
    //   683: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   686: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   689: aload 15
    //   691: ldc 221
    //   693: aload_2
    //   694: invokevirtual 224	com/google/android/apps/plus/network/HttpTransactionMetrics:getReceivedBytes	()J
    //   697: invokestatic 201	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   700: invokevirtual 204	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   703: aload 15
    //   705: ldc 226
    //   707: iconst_1
    //   708: invokestatic 298	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   711: invokevirtual 301	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   714: aload 6
    //   716: ldc_w 265
    //   719: ldc 255
    //   721: aload 15
    //   723: invokevirtual 259	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   726: pop2
    //   727: goto -140 -> 587
    //   730: astore 16
    //   732: aload 14
    //   734: invokeinterface 145 1 0
    //   739: aload 16
    //   741: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   78	88	365	java/lang/IllegalArgumentException
    //   88	293	413	finally
    //   322	327	413	finally
    //   396	410	413	finally
    //   602	608	413	finally
    //   293	322	600	finally
    //   587	597	600	finally
    //   732	742	600	finally
    //   423	587	730	finally
    //   608	727	730	finally
  }

  public static void resetStatsData(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.delete("network_data_stats", null, null);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.NETWORK_DATA_STATS_URI, paramEsAccount), null);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static abstract interface StatsQuery
  {
    public static final String[] PROJECTION = { "network_duration", "process_duration", "sent", "recv", "req_count" };
  }

  private static abstract interface TransactionIdsQuery
  {
    public static final String[] PROJECTION = { "_id" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsNetworkData
 * JD-Core Version:    0.6.2
 */