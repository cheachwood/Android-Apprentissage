package com.google.protobuf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

final class FieldSet<FieldDescriptorType extends FieldDescriptorLite<FieldDescriptorType>>
{
  private static final FieldSet DEFAULT_INSTANCE = new FieldSet((byte)0);
  private final SmallSortedMap<FieldDescriptorType, Object> fields;
  private boolean isImmutable;

  private FieldSet()
  {
    this.fields = SmallSortedMap.newFieldMap(16);
  }

  private FieldSet(byte paramByte)
  {
    this.fields = SmallSortedMap.newFieldMap(0);
    makeImmutable();
  }

  private static int computeElementSize(WireFormat.FieldType paramFieldType, int paramInt, Object paramObject)
  {
    int i = CodedOutputStream.computeTagSize(paramInt);
    if (paramFieldType == WireFormat.FieldType.GROUP)
      i *= 2;
    return i + computeElementSizeNoTag(paramFieldType, paramObject);
  }

  private static int computeElementSizeNoTag(WireFormat.FieldType paramFieldType, Object paramObject)
  {
    int i = 8;
    switch (1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[paramFieldType.ordinal()])
    {
    default:
      throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
    case 1:
      ((Double)paramObject).doubleValue();
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 16:
    case 17:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
    case 18:
    }
    while (true)
    {
      return i;
      ((Float)paramObject).floatValue();
      i = 4;
      continue;
      i = CodedOutputStream.computeRawVarint64Size(((Long)paramObject).longValue());
      continue;
      i = CodedOutputStream.computeRawVarint64Size(((Long)paramObject).longValue());
      continue;
      i = CodedOutputStream.computeInt32SizeNoTag(((Integer)paramObject).intValue());
      continue;
      ((Long)paramObject).longValue();
      continue;
      ((Integer)paramObject).intValue();
      i = 4;
      continue;
      ((Boolean)paramObject).booleanValue();
      i = 1;
      continue;
      i = CodedOutputStream.computeStringSizeNoTag((String)paramObject);
      continue;
      i = ((MessageLite)paramObject).getSerializedSize();
      continue;
      i = CodedOutputStream.computeMessageSizeNoTag((MessageLite)paramObject);
      continue;
      i = CodedOutputStream.computeBytesSizeNoTag((ByteString)paramObject);
      continue;
      i = CodedOutputStream.computeRawVarint32Size(((Integer)paramObject).intValue());
      continue;
      ((Integer)paramObject).intValue();
      i = 4;
      continue;
      ((Long)paramObject).longValue();
      continue;
      i = CodedOutputStream.computeRawVarint32Size(CodedOutputStream.encodeZigZag32(((Integer)paramObject).intValue()));
      continue;
      i = CodedOutputStream.computeRawVarint64Size(CodedOutputStream.encodeZigZag64(((Long)paramObject).longValue()));
      continue;
      i = CodedOutputStream.computeInt32SizeNoTag(((Internal.EnumLite)paramObject).getNumber());
    }
  }

  private static int computeFieldSize(FieldDescriptorLite<?> paramFieldDescriptorLite, Object paramObject)
  {
    WireFormat.FieldType localFieldType = paramFieldDescriptorLite.getLiteType();
    int i = paramFieldDescriptorLite.getNumber();
    int j;
    if (paramFieldDescriptorLite.isRepeated())
      if (paramFieldDescriptorLite.isPacked())
      {
        int k = 0;
        Iterator localIterator2 = ((List)paramObject).iterator();
        while (localIterator2.hasNext())
          k += computeElementSizeNoTag(localFieldType, localIterator2.next());
        j = k + CodedOutputStream.computeTagSize(i) + CodedOutputStream.computeRawVarint32Size(k);
      }
    while (true)
    {
      return j;
      j = 0;
      Iterator localIterator1 = ((List)paramObject).iterator();
      while (localIterator1.hasNext())
        j += computeElementSize(localFieldType, i, localIterator1.next());
      continue;
      j = computeElementSize(localFieldType, i, paramObject);
    }
  }

  public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet()
  {
    return DEFAULT_INSTANCE;
  }

  private static int getMessageSetSerializedSize(Map.Entry<FieldDescriptorType, Object> paramEntry)
  {
    FieldDescriptorLite localFieldDescriptorLite = (FieldDescriptorLite)paramEntry.getKey();
    int j;
    MessageLite localMessageLite;
    if ((localFieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE) && (!localFieldDescriptorLite.isRepeated()) && (!localFieldDescriptorLite.isPacked()))
    {
      j = ((FieldDescriptorLite)paramEntry.getKey()).getNumber();
      localMessageLite = (MessageLite)paramEntry.getValue();
    }
    for (int i = 2 * CodedOutputStream.computeTagSize(1) + (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(j)) + CodedOutputStream.computeMessageSize(3, localMessageLite); ; i = computeFieldSize(localFieldDescriptorLite, paramEntry.getValue()))
      return i;
  }

  static int getWireFormatForFieldType(WireFormat.FieldType paramFieldType, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 2; ; i = paramFieldType.getWireType())
      return i;
  }

  private static boolean isInitialized(Map.Entry<FieldDescriptorType, Object> paramEntry)
  {
    FieldDescriptorLite localFieldDescriptorLite = (FieldDescriptorLite)paramEntry.getKey();
    boolean bool;
    if (localFieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE)
      if (localFieldDescriptorLite.isRepeated())
      {
        Iterator localIterator = ((List)paramEntry.getValue()).iterator();
        while (localIterator.hasNext())
          if (!((MessageLite)localIterator.next()).isInitialized())
            bool = false;
      }
    while (true)
    {
      return bool;
      if (!((MessageLite)paramEntry.getValue()).isInitialized())
        bool = false;
      else
        bool = true;
    }
  }

  private void mergeFromField(Map.Entry<FieldDescriptorType, Object> paramEntry)
  {
    FieldDescriptorLite localFieldDescriptorLite = (FieldDescriptorLite)paramEntry.getKey();
    Object localObject1 = paramEntry.getValue();
    Object localObject3;
    if (localFieldDescriptorLite.isRepeated())
    {
      localObject3 = this.fields.get(localFieldDescriptorLite);
      if (localObject3 == null)
        this.fields.put(localFieldDescriptorLite, new ArrayList((List)localObject1));
    }
    while (true)
    {
      return;
      ((List)localObject3).addAll((List)localObject1);
      continue;
      if (localFieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE)
      {
        Object localObject2 = this.fields.get(localFieldDescriptorLite);
        if (localObject2 == null)
          this.fields.put(localFieldDescriptorLite, localObject1);
        else
          this.fields.put(localFieldDescriptorLite, localFieldDescriptorLite.internalMergeFrom(((MessageLite)localObject2).toBuilder(), (MessageLite)localObject1).build());
      }
      else
      {
        this.fields.put(localFieldDescriptorLite, localObject1);
      }
    }
  }

  public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet()
  {
    return new FieldSet();
  }

  public static Object readPrimitiveField(CodedInputStream paramCodedInputStream, WireFormat.FieldType paramFieldType)
    throws IOException
  {
    Object localObject;
    switch (1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[paramFieldType.ordinal()])
    {
    default:
      throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
    case 1:
      localObject = Double.valueOf(paramCodedInputStream.readDouble());
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
      while (true)
      {
        return localObject;
        localObject = Float.valueOf(paramCodedInputStream.readFloat());
        continue;
        localObject = Long.valueOf(paramCodedInputStream.readInt64());
        continue;
        localObject = Long.valueOf(paramCodedInputStream.readUInt64());
        continue;
        localObject = Integer.valueOf(paramCodedInputStream.readInt32());
        continue;
        localObject = Long.valueOf(paramCodedInputStream.readRawLittleEndian64());
        continue;
        localObject = Integer.valueOf(paramCodedInputStream.readFixed32());
        continue;
        localObject = Boolean.valueOf(paramCodedInputStream.readBool());
        continue;
        localObject = paramCodedInputStream.readString();
        continue;
        localObject = paramCodedInputStream.readBytes();
        continue;
        localObject = Integer.valueOf(paramCodedInputStream.readRawVarint32());
        continue;
        localObject = Integer.valueOf(paramCodedInputStream.readRawLittleEndian32());
        continue;
        localObject = Long.valueOf(paramCodedInputStream.readRawLittleEndian64());
        continue;
        int i = paramCodedInputStream.readRawVarint32();
        localObject = Integer.valueOf(i >>> 1 ^ -(i & 0x1));
        continue;
        long l = paramCodedInputStream.readRawVarint64();
        localObject = Long.valueOf(l >>> 1 ^ -(l & 1L));
      }
    case 16:
      throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
    case 17:
      throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
    case 18:
    }
    throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
  }

  private static void verifyType(WireFormat.FieldType paramFieldType, Object paramObject)
  {
    if (paramObject == null)
      throw new NullPointerException();
    int i = 1.$SwitchMap$com$google$protobuf$WireFormat$JavaType[paramFieldType.getJavaType().ordinal()];
    boolean bool = false;
    switch (i)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    }
    while (!bool)
    {
      throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
      bool = paramObject instanceof Integer;
      continue;
      bool = paramObject instanceof Long;
      continue;
      bool = paramObject instanceof Float;
      continue;
      bool = paramObject instanceof Double;
      continue;
      bool = paramObject instanceof Boolean;
      continue;
      bool = paramObject instanceof String;
      continue;
      bool = paramObject instanceof ByteString;
      continue;
      bool = paramObject instanceof Internal.EnumLite;
      continue;
      bool = paramObject instanceof MessageLite;
    }
  }

  private static void writeElement(CodedOutputStream paramCodedOutputStream, WireFormat.FieldType paramFieldType, int paramInt, Object paramObject)
    throws IOException
  {
    if (paramFieldType == WireFormat.FieldType.GROUP)
      paramCodedOutputStream.writeGroup(paramInt, (MessageLite)paramObject);
    while (true)
    {
      return;
      paramCodedOutputStream.writeTag(paramInt, getWireFormatForFieldType(paramFieldType, false));
      writeElementNoTag(paramCodedOutputStream, paramFieldType, paramObject);
    }
  }

  private static void writeElementNoTag(CodedOutputStream paramCodedOutputStream, WireFormat.FieldType paramFieldType, Object paramObject)
    throws IOException
  {
    switch (1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[paramFieldType.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 16:
    case 17:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
    case 18:
    }
    while (true)
    {
      return;
      paramCodedOutputStream.writeDoubleNoTag(((Double)paramObject).doubleValue());
      continue;
      paramCodedOutputStream.writeFloatNoTag(((Float)paramObject).floatValue());
      continue;
      paramCodedOutputStream.writeInt64NoTag(((Long)paramObject).longValue());
      continue;
      paramCodedOutputStream.writeUInt64NoTag(((Long)paramObject).longValue());
      continue;
      paramCodedOutputStream.writeInt32NoTag(((Integer)paramObject).intValue());
      continue;
      paramCodedOutputStream.writeRawLittleEndian64(((Long)paramObject).longValue());
      continue;
      paramCodedOutputStream.writeFixed32NoTag(((Integer)paramObject).intValue());
      continue;
      paramCodedOutputStream.writeBoolNoTag(((Boolean)paramObject).booleanValue());
      continue;
      paramCodedOutputStream.writeStringNoTag((String)paramObject);
      continue;
      ((MessageLite)paramObject).writeTo(paramCodedOutputStream);
      continue;
      paramCodedOutputStream.writeMessageNoTag((MessageLite)paramObject);
      continue;
      paramCodedOutputStream.writeBytesNoTag((ByteString)paramObject);
      continue;
      paramCodedOutputStream.writeUInt32NoTag(((Integer)paramObject).intValue());
      continue;
      paramCodedOutputStream.writeRawLittleEndian32(((Integer)paramObject).intValue());
      continue;
      paramCodedOutputStream.writeRawLittleEndian64(((Long)paramObject).longValue());
      continue;
      paramCodedOutputStream.writeRawVarint32(CodedOutputStream.encodeZigZag32(((Integer)paramObject).intValue()));
      continue;
      paramCodedOutputStream.writeRawVarint64(CodedOutputStream.encodeZigZag64(((Long)paramObject).longValue()));
      continue;
      paramCodedOutputStream.writeEnumNoTag(((Internal.EnumLite)paramObject).getNumber());
    }
  }

  public static void writeField(FieldDescriptorLite<?> paramFieldDescriptorLite, Object paramObject, CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    WireFormat.FieldType localFieldType = paramFieldDescriptorLite.getLiteType();
    int i = paramFieldDescriptorLite.getNumber();
    if (paramFieldDescriptorLite.isRepeated())
    {
      List localList = (List)paramObject;
      if (paramFieldDescriptorLite.isPacked())
      {
        paramCodedOutputStream.writeTag(i, 2);
        int j = 0;
        Iterator localIterator2 = localList.iterator();
        while (localIterator2.hasNext())
          j += computeElementSizeNoTag(localFieldType, localIterator2.next());
        paramCodedOutputStream.writeRawVarint32(j);
        Iterator localIterator3 = localList.iterator();
        while (localIterator3.hasNext())
          writeElementNoTag(paramCodedOutputStream, localFieldType, localIterator3.next());
      }
      Iterator localIterator1 = localList.iterator();
      while (localIterator1.hasNext())
        writeElement(paramCodedOutputStream, localFieldType, i, localIterator1.next());
    }
    writeElement(paramCodedOutputStream, localFieldType, i, paramObject);
  }

  public final void addRepeatedField(FieldDescriptorType paramFieldDescriptorType, Object paramObject)
  {
    if (!paramFieldDescriptorType.isRepeated())
      throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    verifyType(paramFieldDescriptorType.getLiteType(), paramObject);
    Object localObject1 = this.fields.get(paramFieldDescriptorType);
    Object localObject2;
    if (localObject1 == null)
    {
      localObject2 = new ArrayList();
      this.fields.put(paramFieldDescriptorType, localObject2);
    }
    while (true)
    {
      ((List)localObject2).add(paramObject);
      return;
      localObject2 = (List)localObject1;
    }
  }

  public final void clear()
  {
    this.fields.clear();
  }

  public final FieldSet<FieldDescriptorType> clone()
  {
    FieldSet localFieldSet = new FieldSet();
    for (int i = 0; i < this.fields.getNumArrayEntries(); i++)
    {
      Map.Entry localEntry2 = this.fields.getArrayEntryAt(i);
      localFieldSet.setField((FieldDescriptorLite)localEntry2.getKey(), localEntry2.getValue());
    }
    Iterator localIterator = this.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator.next();
      localFieldSet.setField((FieldDescriptorLite)localEntry1.getKey(), localEntry1.getValue());
    }
    return localFieldSet;
  }

  public final Object getField(FieldDescriptorType paramFieldDescriptorType)
  {
    return this.fields.get(paramFieldDescriptorType);
  }

  public final int getMessageSetSerializedSize()
  {
    int i = 0;
    for (int j = 0; j < this.fields.getNumArrayEntries(); j++)
      i += getMessageSetSerializedSize(this.fields.getArrayEntryAt(j));
    Iterator localIterator = this.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext())
      i += getMessageSetSerializedSize((Map.Entry)localIterator.next());
    return i;
  }

  public final int getSerializedSize()
  {
    int i = 0;
    for (int j = 0; j < this.fields.getNumArrayEntries(); j++)
    {
      Map.Entry localEntry2 = this.fields.getArrayEntryAt(j);
      i += computeFieldSize((FieldDescriptorLite)localEntry2.getKey(), localEntry2.getValue());
    }
    Iterator localIterator = this.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator.next();
      i += computeFieldSize((FieldDescriptorLite)localEntry1.getKey(), localEntry1.getValue());
    }
    return i;
  }

  public final boolean isInitialized()
  {
    int i = 0;
    boolean bool1;
    if (i < this.fields.getNumArrayEntries())
    {
      boolean bool2 = isInitialized(this.fields.getArrayEntryAt(i));
      bool1 = false;
      if (bool2);
    }
    while (true)
    {
      return bool1;
      i++;
      break;
      Iterator localIterator = this.fields.getOverflowEntries().iterator();
      while (true)
        if (localIterator.hasNext())
          if (!isInitialized((Map.Entry)localIterator.next()))
          {
            bool1 = false;
            break;
          }
      bool1 = true;
    }
  }

  public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator()
  {
    return this.fields.entrySet().iterator();
  }

  public final void makeImmutable()
  {
    if (this.isImmutable);
    while (true)
    {
      return;
      this.fields.makeImmutable();
      this.isImmutable = true;
    }
  }

  public final void mergeFrom(FieldSet<FieldDescriptorType> paramFieldSet)
  {
    for (int i = 0; i < paramFieldSet.fields.getNumArrayEntries(); i++)
      mergeFromField(paramFieldSet.fields.getArrayEntryAt(i));
    Iterator localIterator = paramFieldSet.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext())
      mergeFromField((Map.Entry)localIterator.next());
  }

  public final void setField(FieldDescriptorType paramFieldDescriptorType, Object paramObject)
  {
    if (paramFieldDescriptorType.isRepeated())
    {
      if (!(paramObject instanceof List))
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll((List)paramObject);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        verifyType(paramFieldDescriptorType.getLiteType(), localObject);
      }
      paramObject = localArrayList;
    }
    while (true)
    {
      this.fields.put(paramFieldDescriptorType, paramObject);
      return;
      verifyType(paramFieldDescriptorType.getLiteType(), paramObject);
    }
  }

  public static abstract interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T>
  {
    public abstract WireFormat.JavaType getLiteJavaType();

    public abstract WireFormat.FieldType getLiteType();

    public abstract int getNumber();

    public abstract MessageLite.Builder internalMergeFrom(MessageLite.Builder paramBuilder, MessageLite paramMessageLite);

    public abstract boolean isPacked();

    public abstract boolean isRepeated();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.FieldSet
 * JD-Core Version:    0.6.2
 */