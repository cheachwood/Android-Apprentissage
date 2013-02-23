package com.google.protobuf;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class SmallSortedMap<K extends Comparable<K>, V> extends AbstractMap<K, V>
{
  private List<SmallSortedMap<K, V>.Entry> entryList;
  private boolean isImmutable;
  private volatile SmallSortedMap<K, V>.EntrySet lazyEntrySet;
  private final int maxArraySize;
  private Map<K, V> overflowEntries;

  private SmallSortedMap(int paramInt)
  {
    this.maxArraySize = paramInt;
    this.entryList = Collections.emptyList();
    this.overflowEntries = Collections.emptyMap();
  }

  private int binarySearchInArray(K paramK)
  {
    int i = -1 + this.entryList.size();
    int j = 0;
    int n;
    int k;
    if (i >= 0)
    {
      n = paramK.compareTo(((Entry)this.entryList.get(i)).getKey());
      if (n > 0)
        k = -(i + 2);
    }
    while (true)
    {
      return k;
      j = 0;
      if (n == 0)
      {
        k = i;
      }
      else
      {
        while (true)
        {
          if (j > i)
            break label132;
          k = (j + i) / 2;
          int m = paramK.compareTo(((Entry)this.entryList.get(k)).getKey());
          if (m < 0)
          {
            i = k - 1;
          }
          else
          {
            if (m <= 0)
              break;
            j = k + 1;
          }
        }
        label132: k = -(j + 1);
      }
    }
  }

  private void checkMutable()
  {
    if (this.isImmutable)
      throw new UnsupportedOperationException();
  }

  private SortedMap<K, V> getOverflowEntriesMutable()
  {
    checkMutable();
    if ((this.overflowEntries.isEmpty()) && (!(this.overflowEntries instanceof TreeMap)))
      this.overflowEntries = new TreeMap();
    return (SortedMap)this.overflowEntries;
  }

  static <FieldDescriptorType extends FieldSet.FieldDescriptorLite<FieldDescriptorType>> SmallSortedMap<FieldDescriptorType, Object> newFieldMap(int paramInt)
  {
    // Byte code:
    //   0: new 102	com/google/protobuf/SmallSortedMap$1
    //   3: dup
    //   4: iload_0
    //   5: invokespecial 103	com/google/protobuf/SmallSortedMap$1:<init>	(I)V
    //   8: areturn
  }

  private V removeArrayEntryAt(int paramInt)
  {
    checkMutable();
    Object localObject = ((Entry)this.entryList.remove(paramInt)).getValue();
    if (!this.overflowEntries.isEmpty())
    {
      Iterator localIterator = getOverflowEntriesMutable().entrySet().iterator();
      this.entryList.add(new Entry((Map.Entry)localIterator.next()));
      localIterator.remove();
    }
    return localObject;
  }

  public void clear()
  {
    checkMutable();
    if (!this.entryList.isEmpty())
      this.entryList.clear();
    if (!this.overflowEntries.isEmpty())
      this.overflowEntries.clear();
  }

  public boolean containsKey(Object paramObject)
  {
    Comparable localComparable = (Comparable)paramObject;
    if ((binarySearchInArray(localComparable) >= 0) || (this.overflowEntries.containsKey(localComparable)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public Set<Map.Entry<K, V>> entrySet()
  {
    if (this.lazyEntrySet == null)
      this.lazyEntrySet = new EntrySet((byte)0);
    return this.lazyEntrySet;
  }

  public V get(Object paramObject)
  {
    Comparable localComparable = (Comparable)paramObject;
    int i = binarySearchInArray(localComparable);
    if (i >= 0);
    for (Object localObject = ((Entry)this.entryList.get(i)).getValue(); ; localObject = this.overflowEntries.get(localComparable))
      return localObject;
  }

  public final Map.Entry<K, V> getArrayEntryAt(int paramInt)
  {
    return (Map.Entry)this.entryList.get(paramInt);
  }

  public final int getNumArrayEntries()
  {
    return this.entryList.size();
  }

  public final Iterable<Map.Entry<K, V>> getOverflowEntries()
  {
    if (this.overflowEntries.isEmpty());
    for (Object localObject = EmptySet.iterable(); ; localObject = this.overflowEntries.entrySet())
      return localObject;
  }

  public final boolean isImmutable()
  {
    return this.isImmutable;
  }

  public void makeImmutable()
  {
    if (!this.isImmutable)
      if (!this.overflowEntries.isEmpty())
        break label34;
    label34: for (Map localMap = Collections.emptyMap(); ; localMap = Collections.unmodifiableMap(this.overflowEntries))
    {
      this.overflowEntries = localMap;
      this.isImmutable = true;
      return;
    }
  }

  public final V put(K paramK, V paramV)
  {
    checkMutable();
    int i = binarySearchInArray(paramK);
    Object localObject;
    if (i >= 0)
      localObject = ((Entry)this.entryList.get(i)).setValue(paramV);
    while (true)
    {
      return localObject;
      checkMutable();
      if ((this.entryList.isEmpty()) && (!(this.entryList instanceof ArrayList)))
        this.entryList = new ArrayList(this.maxArraySize);
      int j = -(i + 1);
      if (j >= this.maxArraySize)
      {
        localObject = getOverflowEntriesMutable().put(paramK, paramV);
      }
      else
      {
        if (this.entryList.size() == this.maxArraySize)
        {
          Entry localEntry = (Entry)this.entryList.remove(-1 + this.maxArraySize);
          getOverflowEntriesMutable().put(localEntry.getKey(), localEntry.getValue());
        }
        this.entryList.add(j, new Entry(paramK, paramV));
        localObject = null;
      }
    }
  }

  public V remove(Object paramObject)
  {
    checkMutable();
    Comparable localComparable = (Comparable)paramObject;
    int i = binarySearchInArray(localComparable);
    Object localObject;
    if (i >= 0)
      localObject = removeArrayEntryAt(i);
    while (true)
    {
      return localObject;
      if (this.overflowEntries.isEmpty())
        localObject = null;
      else
        localObject = this.overflowEntries.remove(localComparable);
    }
  }

  public int size()
  {
    return this.entryList.size() + this.overflowEntries.size();
  }

  private static final class EmptySet
  {
    private static final Iterable<Object> ITERABLE = new Iterable()
    {
      public final Iterator<Object> iterator()
      {
        return SmallSortedMap.EmptySet.ITERATOR;
      }
    };
    private static final Iterator<Object> ITERATOR = new Iterator()
    {
      public final boolean hasNext()
      {
        return false;
      }

      public final Object next()
      {
        throw new NoSuchElementException();
      }

      public final void remove()
      {
        throw new UnsupportedOperationException();
      }
    };

    static <T> Iterable<T> iterable()
    {
      return ITERABLE;
    }
  }

  private final class Entry
    implements Comparable<SmallSortedMap<K, V>.Entry>, Map.Entry<K, V>
  {
    private final K key;
    private V value;

    Entry(V arg2)
    {
      Object localObject1;
      this.key = localObject1;
      Object localObject2;
      this.value = localObject2;
    }

    Entry()
    {
      this((Comparable)localObject.getKey(), localObject.getValue());
    }

    private static boolean equals(Object paramObject1, Object paramObject2)
    {
      boolean bool;
      if (paramObject1 == null)
        if (paramObject2 == null)
          bool = true;
      while (true)
      {
        return bool;
        bool = false;
        continue;
        bool = paramObject1.equals(paramObject2);
      }
    }

    public final boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (paramObject == this);
      while (true)
      {
        return bool;
        if (!(paramObject instanceof Map.Entry))
        {
          bool = false;
        }
        else
        {
          Map.Entry localEntry = (Map.Entry)paramObject;
          if ((!equals(this.key, localEntry.getKey())) || (!equals(this.value, localEntry.getValue())))
            bool = false;
        }
      }
    }

    public final K getKey()
    {
      return this.key;
    }

    public final V getValue()
    {
      return this.value;
    }

    public final int hashCode()
    {
      int i;
      int j;
      if (this.key == null)
      {
        i = 0;
        Object localObject = this.value;
        j = 0;
        if (localObject != null)
          break label35;
      }
      while (true)
      {
        return i ^ j;
        i = this.key.hashCode();
        break;
        label35: j = this.value.hashCode();
      }
    }

    public final V setValue(V paramV)
    {
      SmallSortedMap.this.checkMutable();
      Object localObject = this.value;
      this.value = paramV;
      return localObject;
    }

    public final String toString()
    {
      return this.key + "=" + this.value;
    }
  }

  private final class EntryIterator
    implements Iterator<Map.Entry<K, V>>
  {
    private Iterator<Map.Entry<K, V>> lazyOverflowIterator;
    private boolean nextCalledBeforeRemove;
    private int pos = -1;

    private EntryIterator()
    {
    }

    private Iterator<Map.Entry<K, V>> getOverflowIterator()
    {
      if (this.lazyOverflowIterator == null)
        this.lazyOverflowIterator = SmallSortedMap.this.overflowEntries.entrySet().iterator();
      return this.lazyOverflowIterator;
    }

    public final boolean hasNext()
    {
      if ((1 + this.pos < SmallSortedMap.this.entryList.size()) || (getOverflowIterator().hasNext()));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final void remove()
    {
      if (!this.nextCalledBeforeRemove)
        throw new IllegalStateException("remove() was called before next()");
      this.nextCalledBeforeRemove = false;
      SmallSortedMap.this.checkMutable();
      if (this.pos < SmallSortedMap.this.entryList.size())
      {
        SmallSortedMap localSmallSortedMap = SmallSortedMap.this;
        int i = this.pos;
        this.pos = (i - 1);
        localSmallSortedMap.removeArrayEntryAt(i);
      }
      while (true)
      {
        return;
        getOverflowIterator().remove();
      }
    }
  }

  private final class EntrySet extends AbstractSet<Map.Entry<K, V>>
  {
    private EntrySet()
    {
    }

    public final void clear()
    {
      SmallSortedMap.this.clear();
    }

    public final boolean contains(Object paramObject)
    {
      Map.Entry localEntry = (Map.Entry)paramObject;
      Object localObject1 = SmallSortedMap.this.get(localEntry.getKey());
      Object localObject2 = localEntry.getValue();
      if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2))));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final Iterator<Map.Entry<K, V>> iterator()
    {
      return new SmallSortedMap.EntryIterator(SmallSortedMap.this, (byte)0);
    }

    public final boolean remove(Object paramObject)
    {
      Map.Entry localEntry = (Map.Entry)paramObject;
      if (contains(localEntry))
        SmallSortedMap.this.remove(localEntry.getKey());
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final int size()
    {
      return SmallSortedMap.this.size();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.SmallSortedMap
 * JD-Core Version:    0.6.2
 */