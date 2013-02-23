package com.google.android.apps.plus.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsProvider;

public class ViewStreamItemPhotoActivity extends FragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  private static final String[] ACTIVITY_RESULT_PROJECTION = { "person_id", "activity_id", "embed_media" };
  private static final String[] STREAM_ITEM_PHOTO_PROJECTION = { "raw_contact_source_id", "stream_item_photo_sync1", "stream_item_photo_sync2" };

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Uri localUri = getIntent().getData();
    if (localUri == null)
      finish();
    while (true)
    {
      return;
      EsAccount localEsAccount = EsAccountsData.getActiveAccount(this);
      if (localEsAccount == null)
      {
        finish();
      }
      else
      {
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("account", localEsAccount);
        localBundle.putParcelable("stream_item_uri", localUri);
        getSupportLoaderManager().initLoader(0, localBundle, this);
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new ActivityIdLoader(this, (EsAccount)paramBundle.getParcelable("account"), (Uri)paramBundle.getParcelable("stream_item_uri"));
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  private static final class ActivityIdLoader extends EsCursorLoader
  {
    private final EsAccount mAccount;
    private final Uri mStreamItemUri;

    public ActivityIdLoader(Context paramContext, EsAccount paramEsAccount, Uri paramUri)
    {
      super();
      this.mAccount = paramEsAccount;
      this.mStreamItemUri = paramUri;
    }

    private byte[] loadMediaFromDatabase$73948607(ContentResolver paramContentResolver, String paramString)
    {
      Cursor localCursor = paramContentResolver.query(EsProvider.appendAccountParameter(Uri.withAppendedPath(EsProvider.ACTIVITY_VIEW_BY_ACTIVITY_ID_URI, paramString), this.mAccount), new String[] { "embed_media" }, null, null, null);
      try
      {
        byte[] arrayOfByte1;
        if (localCursor.moveToFirst())
        {
          byte[] arrayOfByte2 = localCursor.getBlob(0);
          arrayOfByte1 = arrayOfByte2;
          if (arrayOfByte1 == null);
        }
        while (true)
        {
          return arrayOfByte1;
          arrayOfByte1 = new byte[0];
          localCursor.close();
          continue;
          localCursor.close();
          arrayOfByte1 = null;
        }
      }
      finally
      {
        localCursor.close();
      }
    }

    // ERROR //
    public final Cursor esLoadInBackground()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 64	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity$ActivityIdLoader:getContext	()Landroid/content/Context;
      //   4: invokevirtual 70	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
      //   7: astore_1
      //   8: aload_1
      //   9: aload_0
      //   10: getfield 17	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity$ActivityIdLoader:mStreamItemUri	Landroid/net/Uri;
      //   13: invokestatic 76	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity:access$000	()[Ljava/lang/String;
      //   16: aconst_null
      //   17: aconst_null
      //   18: aconst_null
      //   19: invokevirtual 44	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   22: astore_2
      //   23: aload_2
      //   24: invokeinterface 50 1 0
      //   29: ifeq +225 -> 254
      //   32: aload_2
      //   33: iconst_0
      //   34: invokeinterface 80 2 0
      //   39: astore 5
      //   41: aload_2
      //   42: iconst_1
      //   43: invokeinterface 80 2 0
      //   48: astore 12
      //   50: aload 12
      //   52: astore 4
      //   54: aload_2
      //   55: iconst_2
      //   56: invokeinterface 84 2 0
      //   61: pop
      //   62: aload_2
      //   63: invokeinterface 58 1 0
      //   68: aconst_null
      //   69: astore 6
      //   71: aload 4
      //   73: ifnull +83 -> 156
      //   76: aload_0
      //   77: aload_1
      //   78: aload 4
      //   80: invokespecial 86	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity$ActivityIdLoader:loadMediaFromDatabase$73948607	(Landroid/content/ContentResolver;Ljava/lang/String;)[B
      //   83: astore 6
      //   85: aload 6
      //   87: ifnonnull +69 -> 156
      //   90: new 88	com/google/android/apps/plus/api/GetActivityOperation
      //   93: dup
      //   94: aload_0
      //   95: invokevirtual 64	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity$ActivityIdLoader:getContext	()Landroid/content/Context;
      //   98: aload_0
      //   99: getfield 15	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity$ActivityIdLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
      //   102: aload 4
      //   104: aconst_null
      //   105: aconst_null
      //   106: aconst_null
      //   107: aconst_null
      //   108: invokespecial 91	com/google/android/apps/plus/api/GetActivityOperation:<init>	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/Intent;Lcom/google/android/apps/plus/network/HttpOperation$OperationListener;)V
      //   111: astore 8
      //   113: aload 8
      //   115: invokevirtual 94	com/google/android/apps/plus/api/GetActivityOperation:start	()V
      //   118: aload 8
      //   120: invokevirtual 98	com/google/android/apps/plus/api/GetActivityOperation:getException	()Ljava/lang/Exception;
      //   123: ifnull +81 -> 204
      //   126: ldc 100
      //   128: ldc 102
      //   130: aload 8
      //   132: invokevirtual 98	com/google/android/apps/plus/api/GetActivityOperation:getException	()Ljava/lang/Exception;
      //   135: invokestatic 108	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   138: pop
      //   139: iconst_0
      //   140: istore 9
      //   142: iload 9
      //   144: ifeq +12 -> 156
      //   147: aload_0
      //   148: aload_1
      //   149: aload 4
      //   151: invokespecial 86	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity$ActivityIdLoader:loadMediaFromDatabase$73948607	(Landroid/content/ContentResolver;Ljava/lang/String;)[B
      //   154: astore 6
      //   156: new 110	com/google/android/apps/plus/phone/EsMatrixCursor
      //   159: dup
      //   160: invokestatic 113	com/google/android/apps/plus/phone/ViewStreamItemPhotoActivity:access$100	()[Ljava/lang/String;
      //   163: invokespecial 116	com/google/android/apps/plus/phone/EsMatrixCursor:<init>	([Ljava/lang/String;)V
      //   166: astore 7
      //   168: aload 7
      //   170: iconst_3
      //   171: anewarray 118	java/lang/Object
      //   174: dup
      //   175: iconst_0
      //   176: aload 5
      //   178: aastore
      //   179: dup
      //   180: iconst_1
      //   181: aload 4
      //   183: aastore
      //   184: dup
      //   185: iconst_2
      //   186: aload 6
      //   188: aastore
      //   189: invokevirtual 122	com/google/android/apps/plus/phone/EsMatrixCursor:addRow	([Ljava/lang/Object;)V
      //   192: aload 7
      //   194: areturn
      //   195: astore_3
      //   196: aload_2
      //   197: invokeinterface 58 1 0
      //   202: aload_3
      //   203: athrow
      //   204: aload 8
      //   206: invokevirtual 125	com/google/android/apps/plus/api/GetActivityOperation:hasError	()Z
      //   209: ifeq +35 -> 244
      //   212: ldc 100
      //   214: new 127	java/lang/StringBuilder
      //   217: dup
      //   218: ldc 129
      //   220: invokespecial 132	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   223: aload 8
      //   225: invokevirtual 136	com/google/android/apps/plus/api/GetActivityOperation:getErrorCode	()I
      //   228: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   231: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   234: invokestatic 147	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   237: pop
      //   238: iconst_0
      //   239: istore 9
      //   241: goto -99 -> 142
      //   244: iconst_1
      //   245: istore 9
      //   247: goto -105 -> 142
      //   250: astore_3
      //   251: goto -55 -> 196
      //   254: aconst_null
      //   255: astore 4
      //   257: aconst_null
      //   258: astore 5
      //   260: goto -198 -> 62
      //
      // Exception table:
      //   from	to	target	type
      //   23	50	195	finally
      //   54	62	250	finally
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ViewStreamItemPhotoActivity
 * JD-Core Version:    0.6.2
 */