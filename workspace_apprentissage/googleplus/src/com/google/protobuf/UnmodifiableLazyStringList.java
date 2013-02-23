package com.google.protobuf;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class UnmodifiableLazyStringList extends AbstractList<String>
  implements LazyStringList, RandomAccess
{
  private final LazyStringList list;

  public UnmodifiableLazyStringList(LazyStringList paramLazyStringList)
  {
    this.list = paramLazyStringList;
  }

  public final void add(ByteString paramByteString)
  {
    throw new UnsupportedOperationException();
  }

  public final ByteString getByteString(int paramInt)
  {
    return this.list.getByteString(paramInt);
  }

  public final Iterator<String> iterator()
  {
    return new Iterator()
    {
      Iterator<String> iter = UnmodifiableLazyStringList.this.list.iterator();

      public final boolean hasNext()
      {
        return this.iter.hasNext();
      }

      public final void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  public final ListIterator<String> listIterator(final int paramInt)
  {
    return new ListIterator()
    {
      ListIterator<String> iter = UnmodifiableLazyStringList.this.list.listIterator(paramInt);

      public final boolean hasNext()
      {
        return this.iter.hasNext();
      }

      public final boolean hasPrevious()
      {
        return this.iter.hasPrevious();
      }

      public final int nextIndex()
      {
        return this.iter.nextIndex();
      }

      public final int previousIndex()
      {
        return this.iter.previousIndex();
      }

      public final void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  public final int size()
  {
    return this.list.size();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.UnmodifiableLazyStringList
 * JD-Core Version:    0.6.2
 */