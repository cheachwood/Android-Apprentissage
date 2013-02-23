package com.google.android.picasasync;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import java.io.IOException;

public abstract class SyncTask
{
  int mPriority;
  public final String syncAccount;

  protected SyncTask(String paramString)
  {
    this.syncAccount = ((String)Utils.checkNotNull(paramString));
  }

  protected static boolean isSyncOnBattery(Context paramContext)
  {
    return queryBooleanSetting(paramContext, "sync_on_battery", true);
  }

  protected static boolean isSyncOnRoaming(Context paramContext)
  {
    return queryBooleanSetting(paramContext, "sync_on_roaming", false);
  }

  protected static boolean isSyncPicasaOnWifiOnly(Context paramContext)
  {
    return queryBooleanSetting(paramContext, "sync_picasa_on_wifi_only", true);
  }

  private static boolean queryBooleanSetting(Context paramContext, String paramString, boolean paramBoolean)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramContext.getContentResolver().query(PicasaFacade.get(paramContext).getSettingsUri(), new String[] { paramString }, null, null, null);
      if ((localCursor != null) && (localCursor.moveToNext()))
      {
        int i = localCursor.getInt(0);
        if (i == 0)
          break label69;
      }
      label69: for (paramBoolean = true; ; paramBoolean = false)
        return paramBoolean;
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Log.w("gp.SyncTask", "cannot query " + paramString, localThrowable);
        if (localCursor != null)
          localCursor.close();
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public abstract void cancelSync();

  public abstract boolean isBackgroundSync();

  public abstract boolean isSyncOnBattery();

  public boolean isSyncOnExternalStorageOnly()
  {
    return false;
  }

  public boolean isSyncOnRoaming()
  {
    return true;
  }

  public abstract boolean isSyncOnWifiOnly();

  public abstract void performSync(SyncResult paramSyncResult)
    throws IOException;

  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getClass().getSimpleName();
    arrayOfObject[1] = Utils.maskDebugInfo(this.syncAccount);
    return String.format("%s (%s)", arrayOfObject);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.SyncTask
 * JD-Core Version:    0.6.2
 */