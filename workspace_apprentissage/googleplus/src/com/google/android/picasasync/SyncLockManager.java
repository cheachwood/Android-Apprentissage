package com.google.android.picasasync;

import com.android.gallery3d.common.Utils;
import java.util.HashSet;

final class SyncLockManager
{
  private final HashSet<SyncLock> mLocks = new HashSet();

  public final SyncLock acquireLock(int paramInt, Object paramObject)
    throws InterruptedException
  {
    SyncLock localSyncLock;
    synchronized (this.mLocks)
    {
      localSyncLock = new SyncLock(paramInt, paramObject, (byte)0);
      if (!this.mLocks.add(localSyncLock))
        this.mLocks.wait();
    }
    return localSyncLock;
  }

  public final class SyncLock
  {
    private Object mKey;
    private int mType;

    private SyncLock(int paramObject, Object arg3)
    {
      this.mType = paramObject;
      Object localObject;
      this.mKey = localObject;
    }

    public final boolean equals(Object paramObject)
    {
      boolean bool1 = false;
      if (paramObject != null)
      {
        boolean bool2 = paramObject instanceof SyncLock;
        bool1 = false;
        if (bool2)
          break label19;
      }
      while (true)
      {
        return bool1;
        label19: SyncLock localSyncLock = (SyncLock)paramObject;
        int i = localSyncLock.mType;
        int j = this.mType;
        bool1 = false;
        if (i == j)
        {
          boolean bool3 = localSyncLock.mKey.equals(this.mKey);
          bool1 = false;
          if (bool3)
            bool1 = true;
        }
      }
    }

    public final int hashCode()
    {
      return this.mType ^ this.mKey.hashCode();
    }

    public final void unlock()
    {
      synchronized (SyncLockManager.this.mLocks)
      {
        Utils.assertTrue(SyncLockManager.this.mLocks.remove(this));
        SyncLockManager.this.mLocks.notifyAll();
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.SyncLockManager
 * JD-Core Version:    0.6.2
 */