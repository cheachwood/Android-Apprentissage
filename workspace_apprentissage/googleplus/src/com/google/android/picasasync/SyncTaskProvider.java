package com.google.android.picasasync;

import java.util.Collection;

public abstract interface SyncTaskProvider
{
  public abstract void collectTasks(Collection<SyncTask> paramCollection);

  public abstract void resetSyncStates();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.SyncTaskProvider
 * JD-Core Version:    0.6.2
 */