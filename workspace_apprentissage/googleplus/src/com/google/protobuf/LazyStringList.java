package com.google.protobuf;

import java.util.List;

public abstract interface LazyStringList extends List<String>
{
  public abstract void add(ByteString paramByteString);

  public abstract ByteString getByteString(int paramInt);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.LazyStringList
 * JD-Core Version:    0.6.2
 */