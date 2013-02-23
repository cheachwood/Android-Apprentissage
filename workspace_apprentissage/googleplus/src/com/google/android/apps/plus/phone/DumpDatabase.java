package com.google.android.apps.plus.phone;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsProvider;
import java.io.File;

public final class DumpDatabase
{
  private final Context mContext;
  private final Handler mHandler = new Handler()
  {
    public final void handleMessage(Message paramAnonymousMessage)
    {
      int i = paramAnonymousMessage.arg1;
      DumpDatabase.this.mProgressDialog.setProgress(i);
    }
  };
  private ProgressDialog mProgressDialog;

  private DumpDatabase(Context paramContext, DatabaseAction paramDatabaseAction)
  {
    this.mContext = paramContext;
    switch (2.$SwitchMap$com$google$android$apps$plus$phone$DumpDatabase$DatabaseAction[paramDatabaseAction.ordinal()])
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      this.mProgressDialog = ProgressDialog.show(this.mContext, "Dump database", "Dumping ...", false, false, null);
      new DumpTask().execute(new Void[] { null });
      continue;
      this.mProgressDialog = ProgressDialog.show(this.mContext, "Clean database", "Cleaning ...", false, false, null);
      new CleanTask().execute(new Void[] { null });
    }
  }

  public static void cleanNow(Context paramContext)
  {
    new DumpDatabase(paramContext, DatabaseAction.CLEAN);
  }

  public static void dumpNow(Context paramContext)
  {
    new DumpDatabase(paramContext, DatabaseAction.DUMP);
  }

  final class CleanTask extends AsyncTask<Void, Void, Void>
  {
    CleanTask()
    {
    }

    private Void doInBackground$10299ca()
    {
      EsAccount localEsAccount = EsAccountsData.getActiveAccount(DumpDatabase.this.mContext);
      int i = localEsAccount.getIndex();
      String str = "es" + i + ".db";
      File localFile1 = DumpDatabase.this.mContext.getDatabasePath(str);
      long l;
      if ((localFile1.exists()) && (localFile1.isFile()))
        l = localFile1.length();
      try
      {
        EsProvider.cleanupData(DumpDatabase.this.mContext, localEsAccount, true);
        File localFile3 = DumpDatabase.this.mContext.getDatabasePath(str);
        return null;
        l = 0L;
      }
      finally
      {
        File localFile2 = DumpDatabase.this.mContext.getDatabasePath(str);
        Log.i("DumpDatabase", "Clean complete; orig size: " + l + ", copy size: " + localFile2.length());
      }
    }
  }

  private static enum DatabaseAction
  {
    static
    {
      CLEAN = new DatabaseAction("CLEAN", 1);
      DatabaseAction[] arrayOfDatabaseAction = new DatabaseAction[2];
      arrayOfDatabaseAction[0] = DUMP;
      arrayOfDatabaseAction[1] = CLEAN;
    }
  }

  final class DumpTask extends AsyncTask<Void, Void, Void>
  {
    private String[] mFromDbName = new String[4];
    private long[] mOriginalSize = new long[4];
    private String[] mToDbName = new String[4];
    private long mTotalBytes;

    DumpTask()
    {
    }

    // ERROR //
    private File doDump(String paramString1, String paramString2)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: aconst_null
      //   3: astore 4
      //   5: new 35	java/io/File
      //   8: dup
      //   9: invokestatic 41	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
      //   12: aload_2
      //   13: invokespecial 44	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   16: astore 5
      //   18: aload 5
      //   20: invokevirtual 48	java/io/File:exists	()Z
      //   23: ifeq +9 -> 32
      //   26: aload 5
      //   28: invokevirtual 51	java/io/File:delete	()Z
      //   31: pop
      //   32: aload_0
      //   33: getfield 18	com/google/android/apps/plus/phone/DumpDatabase$DumpTask:this$0	Lcom/google/android/apps/plus/phone/DumpDatabase;
      //   36: invokestatic 57	com/google/android/apps/plus/phone/DumpDatabase:access$100	(Lcom/google/android/apps/plus/phone/DumpDatabase;)Landroid/content/Context;
      //   39: aload_1
      //   40: invokevirtual 63	android/content/Context:getDatabasePath	(Ljava/lang/String;)Ljava/io/File;
      //   43: astore 6
      //   45: aload 5
      //   47: invokevirtual 66	java/io/File:createNewFile	()Z
      //   50: pop
      //   51: new 68	java/io/BufferedOutputStream
      //   54: dup
      //   55: new 70	java/io/FileOutputStream
      //   58: dup
      //   59: aload 5
      //   61: invokespecial 73	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   64: invokespecial 76	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   67: astore 15
      //   69: new 78	java/io/BufferedInputStream
      //   72: dup
      //   73: new 80	java/io/FileInputStream
      //   76: dup
      //   77: aload 6
      //   79: invokespecial 81	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   82: invokespecial 84	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
      //   85: astore 16
      //   87: sipush 16384
      //   90: newarray byte
      //   92: astore 17
      //   94: aload 16
      //   96: aload 17
      //   98: invokevirtual 88	java/io/BufferedInputStream:read	([B)I
      //   101: istore 18
      //   103: iload 18
      //   105: ifle +103 -> 208
      //   108: aload 15
      //   110: aload 17
      //   112: iconst_0
      //   113: iload 18
      //   115: invokevirtual 92	java/io/BufferedOutputStream:write	([BII)V
      //   118: aload_0
      //   119: aload_0
      //   120: getfield 94	com/google/android/apps/plus/phone/DumpDatabase$DumpTask:mTotalBytes	J
      //   123: iload 18
      //   125: i2l
      //   126: ladd
      //   127: putfield 94	com/google/android/apps/plus/phone/DumpDatabase$DumpTask:mTotalBytes	J
      //   130: aload_0
      //   131: getfield 18	com/google/android/apps/plus/phone/DumpDatabase$DumpTask:this$0	Lcom/google/android/apps/plus/phone/DumpDatabase;
      //   134: invokestatic 98	com/google/android/apps/plus/phone/DumpDatabase:access$200	(Lcom/google/android/apps/plus/phone/DumpDatabase;)Landroid/os/Handler;
      //   137: invokevirtual 104	android/os/Handler:obtainMessage	()Landroid/os/Message;
      //   140: astore 21
      //   142: aload 21
      //   144: aload_0
      //   145: getfield 94	com/google/android/apps/plus/phone/DumpDatabase$DumpTask:mTotalBytes	J
      //   148: l2i
      //   149: putfield 110	android/os/Message:arg1	I
      //   152: aload_0
      //   153: getfield 18	com/google/android/apps/plus/phone/DumpDatabase$DumpTask:this$0	Lcom/google/android/apps/plus/phone/DumpDatabase;
      //   156: invokestatic 98	com/google/android/apps/plus/phone/DumpDatabase:access$200	(Lcom/google/android/apps/plus/phone/DumpDatabase;)Landroid/os/Handler;
      //   159: aload 21
      //   161: invokevirtual 114	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
      //   164: pop
      //   165: goto -71 -> 94
      //   168: astore 7
      //   170: aload 16
      //   172: astore 4
      //   174: aload 15
      //   176: astore_3
      //   177: ldc 116
      //   179: ldc 118
      //   181: aload 7
      //   183: invokestatic 124	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   186: pop
      //   187: aload_3
      //   188: ifnull +7 -> 195
      //   191: aload_3
      //   192: invokevirtual 127	java/io/BufferedOutputStream:close	()V
      //   195: aload 4
      //   197: ifnull +8 -> 205
      //   200: aload 4
      //   202: invokevirtual 128	java/io/BufferedInputStream:close	()V
      //   205: aload 5
      //   207: areturn
      //   208: aload 15
      //   210: invokevirtual 127	java/io/BufferedOutputStream:close	()V
      //   213: aload 16
      //   215: invokevirtual 128	java/io/BufferedInputStream:close	()V
      //   218: goto -13 -> 205
      //   221: astore 20
      //   223: goto -18 -> 205
      //   226: astore 8
      //   228: aload_3
      //   229: ifnull +7 -> 236
      //   232: aload_3
      //   233: invokevirtual 127	java/io/BufferedOutputStream:close	()V
      //   236: aload 4
      //   238: ifnull +8 -> 246
      //   241: aload 4
      //   243: invokevirtual 128	java/io/BufferedInputStream:close	()V
      //   246: aload 8
      //   248: athrow
      //   249: astore 19
      //   251: goto -38 -> 213
      //   254: astore 13
      //   256: goto -61 -> 195
      //   259: astore 12
      //   261: goto -56 -> 205
      //   264: astore 10
      //   266: goto -30 -> 236
      //   269: astore 9
      //   271: goto -25 -> 246
      //   274: astore 8
      //   276: aload 15
      //   278: astore_3
      //   279: aconst_null
      //   280: astore 4
      //   282: goto -54 -> 228
      //   285: astore 8
      //   287: aload 16
      //   289: astore 4
      //   291: aload 15
      //   293: astore_3
      //   294: goto -66 -> 228
      //   297: astore 7
      //   299: aconst_null
      //   300: astore 4
      //   302: aconst_null
      //   303: astore_3
      //   304: goto -127 -> 177
      //   307: astore 7
      //   309: aload 15
      //   311: astore_3
      //   312: aconst_null
      //   313: astore 4
      //   315: goto -138 -> 177
      //
      // Exception table:
      //   from	to	target	type
      //   87	165	168	java/io/IOException
      //   213	218	221	java/io/IOException
      //   45	69	226	finally
      //   177	187	226	finally
      //   208	213	249	java/io/IOException
      //   191	195	254	java/io/IOException
      //   200	205	259	java/io/IOException
      //   232	236	264	java/io/IOException
      //   241	246	269	java/io/IOException
      //   69	87	274	finally
      //   87	165	285	finally
      //   45	69	297	java/io/IOException
      //   69	87	307	java/io/IOException
    }

    protected final void onPreExecute()
    {
      int i = EsAccountsData.getActiveAccount(DumpDatabase.this.mContext).getIndex();
      long l = 0L;
      this.mFromDbName[0] = ("es" + i + ".db");
      this.mFromDbName[1] = "picasa.db";
      this.mFromDbName[2] = "iu.picasa.db";
      this.mFromDbName[3] = "iu.upload.db";
      this.mToDbName[0] = ("es" + i + "_dump.bin");
      this.mToDbName[1] = "picasa_dump.bin";
      this.mToDbName[2] = "iu.picasa_dump.bin";
      this.mToDbName[3] = "iu.upload_dump.bin";
      for (int j = 0; j < 4; j++)
      {
        String str = this.mFromDbName[j];
        File localFile = DumpDatabase.this.mContext.getDatabasePath(str);
        if ((localFile.exists()) && (localFile.isFile()))
        {
          this.mOriginalSize[j] = localFile.length();
          l += this.mOriginalSize[j];
        }
      }
      DumpDatabase.this.mProgressDialog.setMax((int)l);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.DumpDatabase
 * JD-Core Version:    0.6.2
 */