package com.google.android.apps.plus.iu;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.picasastore.PicasaStoreFacade;

final class FingerprintHelper
{
  private static FingerprintHelper sInstance;
  private final Uri mCachedFingerprintUri;
  private final Uri mFingerprintUri;
  private final Uri mRecalculateFingerprintUri;
  private final ContentResolver mResolver;

  private FingerprintHelper(Context paramContext)
  {
    this.mResolver = paramContext.getContentResolver();
    PicasaStoreFacade localPicasaStoreFacade = PicasaStoreFacade.get(paramContext);
    this.mFingerprintUri = localPicasaStoreFacade.getFingerprintUri();
    this.mCachedFingerprintUri = localPicasaStoreFacade.getFingerprintUri(false, true);
    this.mRecalculateFingerprintUri = localPicasaStoreFacade.getFingerprintUri(true, false);
  }

  public static FingerprintHelper get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new FingerprintHelper(paramContext);
      FingerprintHelper localFingerprintHelper = sInstance;
      return localFingerprintHelper;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private Fingerprint getFingerprint(Uri paramUri, String paramString)
  {
    Cursor localCursor = this.mResolver.query(paramUri, new String[] { paramString }, null, null, null);
    if (localCursor != null);
    try
    {
      Fingerprint localFingerprint2;
      if ((localCursor.moveToNext()) && (!localCursor.isNull(0)))
      {
        localFingerprint2 = new Fingerprint(localCursor.getBlob(0));
        Utils.closeSilently(localCursor);
      }
      for (localFingerprint1 = localFingerprint2; ; localFingerprint1 = null)
      {
        return localFingerprint1;
        Utils.closeSilently(localCursor);
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        if (EsLog.isLoggable("FingerprintHelper", 5))
          Log.w("FingerprintHelper", "cannot get fingerprint for " + paramString, localThrowable);
        Utils.closeSilently(localCursor);
        Fingerprint localFingerprint1 = null;
      }
    }
    finally
    {
      Utils.closeSilently(localCursor);
    }
  }

  public final Fingerprint getCachedFingerprint(String paramString)
  {
    return getFingerprint(this.mCachedFingerprintUri, paramString);
  }

  public final Fingerprint getFingerprint(String paramString, boolean paramBoolean)
  {
    try
    {
      Fingerprint localFingerprint = getFingerprint(this.mRecalculateFingerprintUri, paramString);
      return localFingerprint;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final void invalidate(String paramString)
  {
    this.mResolver.delete(this.mFingerprintUri, null, new String[] { paramString });
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.FingerprintHelper
 * JD-Core Version:    0.6.2
 */