package com.google.android.apps.plus.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

final class IntentPool
{
  private final List<Intent> mIntentPool = new ArrayList(8);

  public IntentPool(int paramInt)
  {
  }

  public final Intent get(Context paramContext, Class<?> paramClass)
  {
    try
    {
      if (this.mIntentPool.size() > 0)
      {
        localIntent = (Intent)this.mIntentPool.remove(0);
        Iterator localIterator = localIntent.getExtras().keySet().iterator();
        while (localIterator.hasNext())
          localIntent.removeExtra((String)localIterator.next());
      }
    }
    finally
    {
    }
    if (EsLog.isLoggable("IntentPool", 3))
      Log.d("IntentPool", "Pool enlarged: " + this.mIntentPool.size());
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(paramContext, paramClass));
    localIntent.putExtra("from_pool", true);
    return localIntent;
  }

  public final void put(Intent paramIntent)
  {
    try
    {
      this.mIntentPool.add(paramIntent);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.IntentPool
 * JD-Core Version:    0.6.2
 */