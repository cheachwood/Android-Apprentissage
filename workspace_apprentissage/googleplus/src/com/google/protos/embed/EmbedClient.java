package com.google.protos.embed;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite.ExtendableBuilder;
import com.google.protobuf.GeneratedMessageLite.ExtendableMessage;
import com.google.protobuf.GeneratedMessageLite.ExtendableMessage.ExtensionWriter;
import com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder;
import com.google.protobuf.Internal;
import com.google.protobuf.UninitializedMessageException;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EmbedClient
{
  public static final class EmbedClientItem extends GeneratedMessageLite.ExtendableMessage<EmbedClientItem>
    implements EmbedClient.EmbedClientItemOrBuilder
  {
    private static final EmbedClientItem defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object canonicalId_;
    private Object id_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private List<ItemTypes.ItemType> type_;

    static
    {
      EmbedClientItem localEmbedClientItem = new EmbedClientItem();
      defaultInstance = localEmbedClientItem;
      localEmbedClientItem.type_ = Collections.emptyList();
      localEmbedClientItem.id_ = "";
      localEmbedClientItem.canonicalId_ = "";
    }

    private EmbedClientItem()
    {
    }

    private EmbedClientItem(Builder paramBuilder)
    {
      super();
    }

    private ByteString getCanonicalIdBytes()
    {
      Object localObject = this.canonicalId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.canonicalId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static EmbedClientItem getDefaultInstance()
    {
      return defaultInstance;
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

    public static Builder newBuilder(EmbedClientItem paramEmbedClientItem)
    {
      return Builder.access$100().mergeFrom(paramEmbedClientItem);
    }

    public final String getCanonicalId()
    {
      Object localObject1 = this.canonicalId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.canonicalId_ = str;
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
      int n;
      for (int i1 = i; ; i1 = n)
      {
        return i1;
        int j = 0;
        for (int k = 0; k < this.type_.size(); k++)
          j += CodedOutputStream.computeInt32SizeNoTag(((ItemTypes.ItemType)this.type_.get(k)).getNumber());
        int m = j + 0 + 1 * this.type_.size();
        if ((0x1 & this.bitField0_) == 1)
          m += CodedOutputStream.computeBytesSize(2, getIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          m += CodedOutputStream.computeBytesSize(3, getCanonicalIdBytes());
        n = m + extensionsSerializedSize();
        this.memoizedSerializedSize = n;
      }
    }

    public final boolean hasCanonicalId()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasId()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
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
        if (!extensionsAreInitialized())
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      for (int i = 0; i < this.type_.size(); i++)
        paramCodedOutputStream.writeEnum(1, ((ItemTypes.ItemType)this.type_.get(i)).getNumber());
      if ((0x1 & this.bitField0_) == 1)
        paramCodedOutputStream.writeBytes(2, getIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(3, getCanonicalIdBytes());
      localExtensionWriter.writeUntil(536870912, paramCodedOutputStream);
    }

    public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<EmbedClient.EmbedClientItem, Builder>
      implements EmbedClient.EmbedClientItemOrBuilder
    {
      private int bitField0_;
      private Object canonicalId_ = "";
      private Object id_ = "";
      private List<ItemTypes.ItemType> type_ = Collections.emptyList();

      private Builder addType(ItemTypes.ItemType paramItemType)
      {
        if (paramItemType == null)
          throw new NullPointerException();
        ensureTypeIsMutable();
        this.type_.add(paramItemType);
        return this;
      }

      private Builder clear()
      {
        super.clear();
        this.type_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.id_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.canonicalId_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureTypeIsMutable()
      {
        if ((0x1 & this.bitField0_) != 1)
        {
          this.type_ = new ArrayList(this.type_);
          this.bitField0_ = (0x1 | this.bitField0_);
        }
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
          case 8:
            ItemTypes.ItemType localItemType2 = ItemTypes.ItemType.valueOf(paramCodedInputStream.readEnum());
            if (localItemType2 == null)
              continue;
            addType(localItemType2);
            break;
          case 10:
            int j = paramCodedInputStream.pushLimit(paramCodedInputStream.readRawVarint32());
            while (paramCodedInputStream.getBytesUntilLimit() > 0)
            {
              ItemTypes.ItemType localItemType1 = ItemTypes.ItemType.valueOf(paramCodedInputStream.readEnum());
              if (localItemType1 != null)
                addType(localItemType1);
            }
            paramCodedInputStream.popLimit(j);
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.id_ = paramCodedInputStream.readBytes();
            break;
          case 26:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.canonicalId_ = paramCodedInputStream.readBytes();
        }
      }

      public final EmbedClient.EmbedClientItem build()
      {
        EmbedClient.EmbedClientItem localEmbedClientItem = buildPartial();
        if (!localEmbedClientItem.isInitialized())
          throw new UninitializedMessageException();
        return localEmbedClientItem;
      }

      public final EmbedClient.EmbedClientItem buildPartial()
      {
        EmbedClient.EmbedClientItem localEmbedClientItem = new EmbedClient.EmbedClientItem(this, (byte)0);
        int i = this.bitField0_;
        if ((0x1 & this.bitField0_) == 1)
        {
          this.type_ = Collections.unmodifiableList(this.type_);
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        }
        EmbedClient.EmbedClientItem.access$302(localEmbedClientItem, this.type_);
        int j = i & 0x2;
        int k = 0;
        if (j == 2)
          k = 1;
        EmbedClient.EmbedClientItem.access$402(localEmbedClientItem, this.id_);
        if ((i & 0x4) == 4)
          k |= 2;
        EmbedClient.EmbedClientItem.access$502(localEmbedClientItem, this.canonicalId_);
        EmbedClient.EmbedClientItem.access$602(localEmbedClientItem, k);
        return localEmbedClientItem;
      }

      public final boolean isInitialized()
      {
        if (!extensionsAreInitialized());
        for (boolean bool = false; ; bool = true)
          return bool;
      }

      public final Builder mergeFrom(EmbedClient.EmbedClientItem paramEmbedClientItem)
      {
        if (paramEmbedClientItem == EmbedClient.EmbedClientItem.getDefaultInstance());
        while (true)
        {
          return this;
          if (!paramEmbedClientItem.type_.isEmpty())
          {
            if (!this.type_.isEmpty())
              break label76;
            this.type_ = paramEmbedClientItem.type_;
            this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          }
          while (paramEmbedClientItem.hasId())
          {
            String str2 = paramEmbedClientItem.getId();
            if (str2 == null)
            {
              throw new NullPointerException();
              label76: ensureTypeIsMutable();
              this.type_.addAll(paramEmbedClientItem.type_);
            }
            else
            {
              this.bitField0_ = (0x2 | this.bitField0_);
              this.id_ = str2;
            }
          }
          if (paramEmbedClientItem.hasCanonicalId())
          {
            String str1 = paramEmbedClientItem.getCanonicalId();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.canonicalId_ = str1;
          }
          mergeExtensionFields(paramEmbedClientItem);
        }
      }
    }
  }

  public static abstract interface EmbedClientItemOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<EmbedClient.EmbedClientItem>
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protos.embed.EmbedClient
 * JD-Core Version:    0.6.2
 */