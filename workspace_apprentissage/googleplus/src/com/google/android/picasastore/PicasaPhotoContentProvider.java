package com.google.android.picasastore;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;
import java.io.FileNotFoundException;

public class PicasaPhotoContentProvider extends ContentProvider
{
  private static final Uri EXTERNAL_STORAGE_FSID_URI = Uri.parse("content://media/external/fs_id");
  private String mAuthority;
  private int mExternalStorageFsId;
  private FingerprintManager mFingerprintManager;
  private boolean mIsExternalStorageFsIdReady = false;
  private PicasaStore mPicasaStore;
  private SharedPreferences mPrefs;
  private final UriMatcher mUriMatcher = new UriMatcher(-1);

  private static int getFsId(Context paramContext)
  {
    Cursor localCursor = paramContext.getContentResolver().query(EXTERNAL_STORAGE_FSID_URI, null, null, null, null);
    if (localCursor != null);
    try
    {
      int j;
      if (localCursor.moveToFirst())
        j = localCursor.getInt(0);
      for (int i = j; ; i = -1)
      {
        return i;
        Log.d("gp.PicasaPhotoCP", "No FSID on this device!");
        Utils.closeSilently(localCursor);
      }
    }
    finally
    {
      Utils.closeSilently(localCursor);
    }
  }

  private PicasaStore getPicasaStore()
  {
    if (this.mPicasaStore == null)
      this.mPicasaStore = new PicasaStore(getContext());
    return this.mPicasaStore;
  }

  private void onFsIdChanged()
  {
    int i = 1;
    while (true)
    {
      Context localContext;
      int j;
      try
      {
        String str = Environment.getExternalStorageState();
        if (!str.equals("mounted"))
        {
          boolean bool = str.equals("mounted_ro");
          if (!bool);
        }
        else
        {
          if (i != 0)
            continue;
          return;
        }
        i = 0;
        continue;
        localContext = getContext();
        j = getFsId(localContext);
        if (!this.mIsExternalStorageFsIdReady)
        {
          Log.d("gp.PicasaPhotoCP", "set fsid first time:" + j);
          this.mIsExternalStorageFsIdReady = true;
          this.mExternalStorageFsId = j;
          this.mPrefs.edit().putInt("external_storage_fsid", j).commit();
          continue;
        }
      }
      finally
      {
      }
      if (this.mExternalStorageFsId != j)
      {
        Log.d("gp.PicasaPhotoCP", "fsid changed: " + this.mExternalStorageFsId + " -> " + j);
        this.mExternalStorageFsId = j;
        this.mPrefs.edit().putInt("external_storage_fsid", j).commit();
        if (PicasaStoreFacade.get(localContext).isMaster())
          this.mFingerprintManager.reset();
      }
    }
  }

  private Cursor queryFingerprint(Uri paramUri, String[] paramArrayOfString)
  {
    boolean bool = "1".equals(paramUri.getQueryParameter("force_recalculate"));
    int i;
    PicasaMatrixCursor localPicasaMatrixCursor;
    FingerprintManager localFingerprintManager;
    Object[] arrayOfObject;
    int m;
    label68: Fingerprint localFingerprint2;
    if ((!bool) && ("1".equals(paramUri.getQueryParameter("cache_only"))))
    {
      i = 1;
      localPicasaMatrixCursor = new PicasaMatrixCursor(paramArrayOfString);
      localFingerprintManager = this.mFingerprintManager;
      arrayOfObject = new Object[paramArrayOfString.length];
      if (i == 0)
        break label123;
      m = 0;
      int n = paramArrayOfString.length;
      if (m >= n)
        break label180;
      localFingerprint2 = localFingerprintManager.getCachedFingerprint(paramArrayOfString[m]);
      if (localFingerprint2 != null)
        break label113;
    }
    label113: for (byte[] arrayOfByte2 = null; ; arrayOfByte2 = localFingerprint2.getBytes())
    {
      arrayOfObject[m] = arrayOfByte2;
      m++;
      break label68;
      i = 0;
      break;
    }
    label123: int j = 0;
    int k = paramArrayOfString.length;
    if (j < k)
    {
      Fingerprint localFingerprint1 = localFingerprintManager.getFingerprint(paramArrayOfString[j], bool);
      if (localFingerprint1 == null);
      for (byte[] arrayOfByte1 = null; ; arrayOfByte1 = localFingerprint1.getBytes())
      {
        arrayOfObject[j] = arrayOfByte1;
        j++;
        break;
      }
    }
    label180: localPicasaMatrixCursor.addRow(arrayOfObject);
    return localPicasaMatrixCursor;
  }

  public void attachInfo(Context paramContext, ProviderInfo paramProviderInfo)
  {
    super.attachInfo(paramContext, paramProviderInfo);
    this.mAuthority = paramProviderInfo.authority;
    this.mUriMatcher.addURI(this.mAuthority, "photos", 1);
    this.mUriMatcher.addURI(this.mAuthority, "fingerprint", 3);
    this.mUriMatcher.addURI(this.mAuthority, "photos/#", 2);
    this.mUriMatcher.addURI(this.mAuthority, "albumcovers/#", 4);
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    switch (this.mUriMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("unsupported uri:" + paramUri);
    case 3:
    }
    return this.mFingerprintManager.invalidate(paramArrayOfString);
  }

  public String getType(Uri paramUri)
  {
    String str;
    switch (this.mUriMatcher.match(paramUri))
    {
    case 3:
    default:
      throw new IllegalArgumentException("Invalid URI: " + paramUri);
    case 1:
      str = "vnd.android.cursor.dir/vnd.google.android.picasasync.item";
    case 2:
    case 4:
    }
    while (true)
    {
      return str;
      str = "vnd.android.cursor.item/vnd.google.android.picasasync.item";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.picasasync.album_cover";
    }
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    int i = MetricsUtils.begin("INSERT " + paramUri);
    try
    {
      this.mUriMatcher.match(paramUri);
      throw new IllegalArgumentException("unsupported uri:" + paramUri);
    }
    finally
    {
      MetricsUtils.end(i);
    }
  }

  public boolean onCreate()
  {
    Context localContext = getContext();
    Config.init(localContext);
    this.mFingerprintManager = FingerprintManager.get(localContext);
    this.mPrefs = PreferenceManager.getDefaultSharedPreferences(localContext);
    HandlerThread localHandlerThread = new HandlerThread("picasa-photo-provider", 10);
    localHandlerThread.start();
    Handler local2 = new Handler(localHandlerThread.getLooper())
    {
      public final void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default:
          throw new AssertionError("unknown message: " + paramAnonymousMessage.what);
        case 1:
        }
        PicasaPhotoContentProvider.this.onFsIdChanged();
      }
    };
    localContext.getContentResolver().registerContentObserver(EXTERNAL_STORAGE_FSID_URI, false, new ContentObserver(local2)
    {
      public final void onChange(boolean paramAnonymousBoolean)
      {
        PicasaPhotoContentProvider.this.onFsIdChanged();
      }
    });
    Message.obtain(local2, 1).sendToTarget();
    return true;
  }

  public ParcelFileDescriptor openFile(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    int i = MetricsUtils.begin("OPEN " + paramUri.toString());
    try
    {
      switch (this.mUriMatcher.match(paramUri))
      {
      case 3:
      default:
        throw new IllegalArgumentException("unsupported uri: " + paramUri);
      case 2:
      case 4:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    ParcelFileDescriptor localParcelFileDescriptor2 = getPicasaStore().openFile(paramUri, paramString);
    Object localObject2 = localParcelFileDescriptor2;
    MetricsUtils.end(i);
    while (true)
    {
      return localObject2;
      ParcelFileDescriptor localParcelFileDescriptor1 = getPicasaStore().openAlbumCover(paramUri, paramString);
      localObject2 = localParcelFileDescriptor1;
      MetricsUtils.end(i);
    }
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    int i = MetricsUtils.begin("QUERY " + paramUri.toString());
    try
    {
      switch (this.mUriMatcher.match(paramUri))
      {
      default:
        throw new IllegalArgumentException("Invalid URI: " + paramUri);
      case 3:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    Cursor localCursor = queryFingerprint(paramUri, paramArrayOfString1);
    MetricsUtils.incrementQueryResultCount(localCursor.getCount());
    MetricsUtils.end(i);
    return localCursor;
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    this.mUriMatcher.match(paramUri);
    throw new IllegalArgumentException("unsupported uri:" + paramUri);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.PicasaPhotoContentProvider
 * JD-Core Version:    0.6.2
 */