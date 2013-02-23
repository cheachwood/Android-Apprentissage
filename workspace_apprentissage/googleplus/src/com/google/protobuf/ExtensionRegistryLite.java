package com.google.protobuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExtensionRegistryLite
{
  private static final ExtensionRegistryLite EMPTY = new ExtensionRegistryLite((byte)0);
  private final Map<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;

  ExtensionRegistryLite()
  {
    this.extensionsByNumber = new HashMap();
  }

  private ExtensionRegistryLite(byte paramByte)
  {
    this.extensionsByNumber = Collections.emptyMap();
  }

  public static ExtensionRegistryLite getEmptyRegistry()
  {
    return EMPTY;
  }

  public final <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType paramContainingType, int paramInt)
  {
    return (GeneratedMessageLite.GeneratedExtension)this.extensionsByNumber.get(new ObjectIntPair(paramContainingType, paramInt));
  }

  private static final class ObjectIntPair
  {
    private final int number;
    private final Object object;

    ObjectIntPair(Object paramObject, int paramInt)
    {
      this.object = paramObject;
      this.number = paramInt;
    }

    public final boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof ObjectIntPair;
      boolean bool2 = false;
      if (!bool1);
      while (true)
      {
        return bool2;
        ObjectIntPair localObjectIntPair = (ObjectIntPair)paramObject;
        Object localObject1 = this.object;
        Object localObject2 = localObjectIntPair.object;
        bool2 = false;
        if (localObject1 == localObject2)
        {
          int i = this.number;
          int j = localObjectIntPair.number;
          bool2 = false;
          if (i == j)
            bool2 = true;
        }
      }
    }

    public final int hashCode()
    {
      return 65535 * System.identityHashCode(this.object) + this.number;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.ExtensionRegistryLite
 * JD-Core Version:    0.6.2
 */