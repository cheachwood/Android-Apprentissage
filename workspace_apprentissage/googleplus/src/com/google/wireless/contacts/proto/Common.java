package com.google.wireless.contacts.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.MessageLiteOrBuilder;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class Common
{
  public static final class Metadata extends GeneratedMessageLite
    implements Common.MetadataOrBuilder
  {
    private static final Metadata defaultInstance;
    private static final long serialVersionUID;
    private ACL acl_;
    private int bitField0_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    static
    {
      Metadata localMetadata = new Metadata();
      defaultInstance = localMetadata;
      localMetadata.acl_ = ACL.PUBLIC;
    }

    private Metadata()
    {
    }

    private Metadata(Builder paramBuilder)
    {
      super();
    }

    public static Metadata getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$100();
    }

    public static Builder newBuilder(Metadata paramMetadata)
    {
      return Builder.access$100().mergeFrom(paramMetadata);
    }

    public final ACL getAcl()
    {
      return this.acl_;
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
          k = 0 + CodedOutputStream.computeEnumSize(1, this.acl_.getNumber());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasAcl()
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
        this.memoizedIsInitialized = i;
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
        paramCodedOutputStream.writeEnum(1, this.acl_.getNumber());
    }

    public static enum ACL
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<ACL> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        PUBLIC = new ACL("PUBLIC", 2, 2);
        ACL[] arrayOfACL = new ACL[3];
        arrayOfACL[0] = PRIVATE;
        arrayOfACL[1] = SHARED;
        arrayOfACL[2] = PUBLIC;
      }

      private ACL(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static ACL valueOf(int paramInt)
      {
        ACL localACL;
        switch (paramInt)
        {
        default:
          localACL = null;
        case 0:
        case 1:
        case 2:
        }
        while (true)
        {
          return localACL;
          localACL = PRIVATE;
          continue;
          localACL = SHARED;
          continue;
          localACL = PUBLIC;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Common.Metadata, Builder>
      implements Common.MetadataOrBuilder
    {
      private Common.Metadata.ACL acl_ = Common.Metadata.ACL.PUBLIC;
      private int bitField0_;

      private Builder clear()
      {
        super.clear();
        this.acl_ = Common.Metadata.ACL.PUBLIC;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
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
          case 8:
          }
          Common.Metadata.ACL localACL = Common.Metadata.ACL.valueOf(paramCodedInputStream.readEnum());
          if (localACL != null)
          {
            this.bitField0_ = (0x1 | this.bitField0_);
            this.acl_ = localACL;
          }
        }
      }

      public final Common.Metadata buildPartial()
      {
        Common.Metadata localMetadata = new Common.Metadata(this, (byte)0);
        int i = 0x1 & this.bitField0_;
        int j = 0;
        if (i == 1)
          j = 1;
        Common.Metadata.access$302(localMetadata, this.acl_);
        Common.Metadata.access$402(localMetadata, j);
        return localMetadata;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Common.Metadata paramMetadata)
      {
        if (paramMetadata == Common.Metadata.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramMetadata.hasAcl())
          {
            Common.Metadata.ACL localACL = paramMetadata.getAcl();
            if (localACL == null)
              throw new NullPointerException();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.acl_ = localACL;
          }
        }
      }
    }
  }

  public static abstract interface MetadataOrBuilder extends MessageLiteOrBuilder
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.contacts.proto.Common
 * JD-Core Version:    0.6.2
 */