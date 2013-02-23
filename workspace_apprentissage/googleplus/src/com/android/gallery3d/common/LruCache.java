package com.android.gallery3d.common;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class LruCache<K, V>
{
  private final HashMap<K, V> mLruMap = new LinkedHashMap(16, 0.75F, true)
  {
    protected final boolean removeEldestEntry(Map.Entry<K, V> paramAnonymousEntry)
    {
      if (size() > this.val$capacity);
      for (boolean bool = true; ; bool = false)
        return bool;
    }
  };
  private ReferenceQueue<V> mQueue = new ReferenceQueue();
  private final HashMap<K, Entry<K, V>> mWeakMap = new HashMap();

  public LruCache(int paramInt)
  {
  }

  private void cleanUpWeakMap()
  {
    for (Entry localEntry = (Entry)this.mQueue.poll(); localEntry != null; localEntry = (Entry)this.mQueue.poll())
      this.mWeakMap.remove(localEntry.mKey);
  }

  public final boolean containsKey(K paramK)
  {
    try
    {
      cleanUpWeakMap();
      boolean bool = this.mWeakMap.containsKey(paramK);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final V get(K paramK)
  {
    try
    {
      cleanUpWeakMap();
      Object localObject2 = this.mLruMap.get(paramK);
      Object localObject3 = localObject2;
      if (localObject3 != null);
      while (true)
      {
        return localObject3;
        Entry localEntry = (Entry)this.mWeakMap.get(paramK);
        if (localEntry == null)
        {
          localObject3 = null;
        }
        else
        {
          Object localObject4 = localEntry.get();
          localObject3 = localObject4;
        }
      }
    }
    finally
    {
    }
  }

  public final V put(K paramK, V paramV)
  {
    try
    {
      cleanUpWeakMap();
      this.mLruMap.put(paramK, paramV);
      Entry localEntry = (Entry)this.mWeakMap.put(paramK, new Entry(paramK, paramV, this.mQueue));
      if (localEntry == null);
      Object localObject2;
      for (Object localObject3 = null; ; localObject3 = localObject2)
      {
        return localObject3;
        localObject2 = localEntry.get();
      }
    }
    finally
    {
    }
  }

  private static final class Entry<K, V> extends WeakReference<V>
  {
    K mKey;

    public Entry(K paramK, V paramV, ReferenceQueue<V> paramReferenceQueue)
    {
      super(paramReferenceQueue);
      this.mKey = paramK;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.common.LruCache
 * JD-Core Version:    0.6.2
 */