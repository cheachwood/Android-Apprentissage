package com.google.android.apps.plus.iu;

public abstract interface SyncTaskProvider
{
  public abstract SyncTask getNextTask(String paramString);

  public abstract void onSyncStart();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.SyncTaskProvider
 * JD-Core Version:    0.6.2
 */