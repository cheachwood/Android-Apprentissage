package com.google.protobuf;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public final class LazyStringArrayList extends AbstractList<String>
  implements LazyStringList, RandomAccess
{
  public static final LazyStringList EMPTY = new UnmodifiableLazyStringList(new LazyStringArrayList());
  private final List<Object> list;

  public LazyStringArrayList()
  {
    this.list = new ArrayList();
  }

  public LazyStringArrayList(List<String> paramList)
  {
    this.list = new ArrayList(paramList);
  }

  private static String asString(Object paramObject)
  {
    if ((paramObject instanceof String));
    for (String str = (String)paramObject; ; str = ((ByteString)paramObject).toStringUtf8())
      return str;
  }

  public final void add(ByteString paramByteString)
  {
    this.list.add(paramByteString);
    this.modCount = (1 + this.modCount);
  }

  public final boolean addAll(int paramInt, Collection<? extends String> paramCollection)
  {
    boolean bool = this.list.addAll(paramInt, paramCollection);
    this.modCount = (1 + this.modCount);
    return bool;
  }

  public final void clear()
  {
    this.list.clear();
    this.modCount = (1 + this.modCount);
  }

  public final ByteString getByteString(int paramInt)
  {
    Object localObject = this.list.get(paramInt);
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.list.set(paramInt, localByteString);
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  public final int size()
  {
    return this.list.size();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.LazyStringArrayList
 * JD-Core Version:    0.6.2
 */