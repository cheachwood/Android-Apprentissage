package com.google.android.apps.plus.iu;

import android.content.SyncResult;
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

  public abstract void cancelSync();

  public abstract boolean isBackgroundSync();

  public abstract boolean isSyncOnBattery();

  public boolean isSyncOnRoaming()
  {
    return true;
  }

  public abstract boolean isSyncOnWifiOnly();

  public void onRejected(int paramInt)
  {
  }

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
 * Qualified Name:     com.google.android.apps.plus.iu.SyncTask
 * JD-Core Version:    0.6.2
 */