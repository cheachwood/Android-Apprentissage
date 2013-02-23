package com.google.apps.gcomm.hangout.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class ExternalEntityKey extends GeneratedMessageLite
  implements ExternalEntityKeyOrBuilder
{
  private static final ExternalEntityKey defaultInstance;
  private static final long serialVersionUID;
  private int bitField0_;
  private Object domain_;
  private Object id_;
  private byte memoizedIsInitialized = -1;
  private int memoizedSerializedSize = -1;

  static
  {
    ExternalEntityKey localExternalEntityKey = new ExternalEntityKey();
    defaultInstance = localExternalEntityKey;
    localExternalEntityKey.domain_ = "";
    localExternalEntityKey.id_ = "";
  }

  private ExternalEntityKey()
  {
  }

  private ExternalEntityKey(Builder paramBuilder)
  {
    super((byte)0);
  }

  public static ExternalEntityKey getDefaultInstance()
  {
    return defaultInstance;
  }

  private ByteString getDomainBytes()
  {
    Object localObject = this.domain_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.domain_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getIdBytes()
  {
    Object localObject = this.id_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.id_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  public static Builder newBuilder()
  {
    return Builder.access$100();
  }

  public static Builder newBuilder(ExternalEntityKey paramExternalEntityKey)
  {
    return Builder.access$100().mergeFrom(paramExternalEntityKey);
  }

  public final String getDomain()
  {
    Object localObject1 = this.domain_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.domain_ = str;
    }
  }

  public final String getId()
  {
    Object localObject1 = this.id_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.id_ = str;
    }
  }

  public final int getSerializedSize()
  {
    int i = this.memoizedSerializedSize;
    if (i != -1);
    int k;
    for (int m = i; ; m = k)
    {
      return m;
      int j = 0x1 & this.bitField0_;
      k = 0;
      if (j == 1)
        k = 0 + CodedOutputStream.computeBytesSize(1, getDomainBytes());
      if ((0x2 & this.bitField0_) == 2)
        k += CodedOutputStream.computeBytesSize(2, getIdBytes());
      this.memoizedSerializedSize = k;
    }
  }

  public final boolean hasDomain()
  {
    int i = 1;
    if ((0x1 & this.bitField0_) == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean hasId()
  {
    if ((0x2 & this.bitField0_) == 2);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isInitialized()
  {
    int i = 1;
    int j = this.memoizedIsInitialized;
    if (j != -1)
      if (j != i);
    while (true)
    {
      return i;
      i = 0;
      continue;
      if (!hasDomain())
      {
        this.memoizedIsInitialized = 0;
        i = 0;
      }
      else if (!hasId())
      {
        this.memoizedIsInitialized = 0;
        i = 0;
      }
      else
      {
        this.memoizedIsInitialized = i;
      }
    }
  }

  protected final Object writeReplace()
    throws ObjectStreamException
  {
    return super.writeReplace();
  }

  public final void writeTo(CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    getSerializedSize();
    if ((0x1 & this.bitField0_) == 1)
      paramCodedOutputStream.writeBytes(1, getDomainBytes());
    if ((0x2 & this.bitField0_) == 2)
      paramCodedOutputStream.writeBytes(2, getIdBytes());
  }

  public static final class Builder extends GeneratedMessageLite.Builder<ExternalEntityKey, Builder>
    implements ExternalEntityKeyOrBuilder
  {
    private int bitField0_;
    private Object domain_ = "";
    private Object id_ = "";

    private Builder clear()
    {
      super.clear();
      this.domain_ = "";
      this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
      this.id_ = "";
      this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
      return this;
    }

    private Builder clone()
    {
      return new Builder().mergeFrom(buildPartial());
    }

    private Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      while (true)
      {
        int i = paramCodedInputStream.readTag();
        switch (i)
        {
        default:
          if (parseUnknownField(paramCodedInputStream, paramExtensionRegistryLite, i))
            continue;
        case 0:
          return this;
        case 10:
          this.bitField0_ = (0x1 | this.bitField0_);
          this.domain_ = paramCodedInputStream.readBytes();
          break;
        case 18:
        }
        this.bitField0_ = (0x2 | this.bitField0_);
        this.id_ = paramCodedInputStream.readBytes();
      }
    }

    public final ExternalEntityKey buildPartial()
    {
      ExternalEntityKey localExternalEntityKey = new ExternalEntityKey(this, (byte)0);
      int i = this.bitField0_;
      int j = i & 0x1;
      int k = 0;
      if (j == 1)
        k = 1;
      ExternalEntityKey.access$302(localExternalEntityKey, this.domain_);
      if ((i & 0x2) == 2)
        k |= 2;
      ExternalEntityKey.access$402(localExternalEntityKey, this.id_);
      ExternalEntityKey.access$502(localExternalEntityKey, k);
      return localExternalEntityKey;
    }

    public final boolean isInitialized()
    {
      if ((0x1 & this.bitField0_) == 1);
      boolean bool;
      for (int i = 1; ; i = 0)
      {
        bool = false;
        if (i != 0)
          break;
        return bool;
      }
      if ((0x2 & this.bitField0_) == 2);
      for (int j = 1; ; j = 0)
      {
        bool = false;
        if (j == 0)
          break;
        bool = true;
        break;
      }
    }

    public final Builder mergeFrom(ExternalEntityKey paramExternalEntityKey)
    {
      if (paramExternalEntityKey == ExternalEntityKey.getDefaultInstance());
      while (true)
      {
        return this;
        if (paramExternalEntityKey.hasDomain())
        {
          String str2 = paramExternalEntityKey.getDomain();
          if (str2 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x1 | this.bitField0_);
          this.domain_ = str2;
        }
        if (paramExternalEntityKey.hasId())
        {
          String str1 = paramExternalEntityKey.getId();
          if (str1 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x2 | this.bitField0_);
          this.id_ = str1;
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.apps.gcomm.hangout.proto.ExternalEntityKey
 * JD-Core Version:    0.6.2
 */