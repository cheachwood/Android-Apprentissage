package com.google.wireless.realtimechat.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Buzz
{
  public static final class BuzzHeader extends GeneratedMessageLite
    implements Buzz.BuzzHeaderOrBuilder
  {
    private static final BuzzHeader defaultInstance;
    private static final long serialVersionUID;
    private boolean actAsPrimary_;
    private boolean alreadySentToPrimary_;
    private int bitField0_;
    private boolean countForReliabilityTest_;
    private boolean destinationPayloadsSetSender_;
    private boolean dropIfNoEndpoint_;
    private boolean dropIfNoResource_;
    private boolean individuallyRoutedPayload_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object secondaryPayload_;
    private boolean stateUpdate_;

    static
    {
      BuzzHeader localBuzzHeader = new BuzzHeader();
      defaultInstance = localBuzzHeader;
      localBuzzHeader.stateUpdate_ = false;
      localBuzzHeader.alreadySentToPrimary_ = false;
      localBuzzHeader.secondaryPayload_ = "";
      localBuzzHeader.dropIfNoEndpoint_ = false;
      localBuzzHeader.actAsPrimary_ = false;
      localBuzzHeader.dropIfNoResource_ = false;
      localBuzzHeader.individuallyRoutedPayload_ = false;
      localBuzzHeader.countForReliabilityTest_ = false;
      localBuzzHeader.destinationPayloadsSetSender_ = false;
    }

    private BuzzHeader()
    {
    }

    private BuzzHeader(Builder paramBuilder)
    {
      super();
    }

    public static BuzzHeader getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getSecondaryPayloadBytes()
    {
      Object localObject = this.secondaryPayload_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.secondaryPayload_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$2100();
    }

    public static Builder newBuilder(BuzzHeader paramBuzzHeader)
    {
      return Builder.access$2100().mergeFrom(paramBuzzHeader);
    }

    public final boolean getActAsPrimary()
    {
      return this.actAsPrimary_;
    }

    public final boolean getAlreadySentToPrimary()
    {
      return this.alreadySentToPrimary_;
    }

    public final boolean getCountForReliabilityTest()
    {
      return this.countForReliabilityTest_;
    }

    public final BuzzHeader getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final boolean getDestinationPayloadsSetSender()
    {
      return this.destinationPayloadsSetSender_;
    }

    public final boolean getDropIfNoEndpoint()
    {
      return this.dropIfNoEndpoint_;
    }

    public final boolean getDropIfNoResource()
    {
      return this.dropIfNoResource_;
    }

    public final boolean getIndividuallyRoutedPayload()
    {
      return this.individuallyRoutedPayload_;
    }

    public final String getSecondaryPayload()
    {
      Object localObject1 = this.secondaryPayload_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.secondaryPayload_ = str;
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
          k = 0 + CodedOutputStream.computeBoolSize(1, this.stateUpdate_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBoolSize(2, this.alreadySentToPrimary_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(6, getSecondaryPayloadBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBoolSize(7, this.dropIfNoEndpoint_);
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeBoolSize(8, this.actAsPrimary_);
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeBoolSize(9, this.dropIfNoResource_);
        if ((0x40 & this.bitField0_) == 64)
          k += CodedOutputStream.computeBoolSize(10, this.individuallyRoutedPayload_);
        if ((0x80 & this.bitField0_) == 128)
          k += CodedOutputStream.computeBoolSize(11, this.countForReliabilityTest_);
        if ((0x100 & this.bitField0_) == 256)
          k += CodedOutputStream.computeBoolSize(12, this.destinationPayloadsSetSender_);
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean getStateUpdate()
    {
      return this.stateUpdate_;
    }

    public final boolean hasActAsPrimary()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasAlreadySentToPrimary()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasCountForReliabilityTest()
    {
      if ((0x80 & this.bitField0_) == 128);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDestinationPayloadsSetSender()
    {
      if ((0x100 & this.bitField0_) == 256);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDropIfNoEndpoint()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDropIfNoResource()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasIndividuallyRoutedPayload()
    {
      if ((0x40 & this.bitField0_) == 64);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasSecondaryPayload()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasStateUpdate()
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
        paramCodedOutputStream.writeBool(1, this.stateUpdate_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBool(2, this.alreadySentToPrimary_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(6, getSecondaryPayloadBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBool(7, this.dropIfNoEndpoint_);
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeBool(8, this.actAsPrimary_);
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeBool(9, this.dropIfNoResource_);
      if ((0x40 & this.bitField0_) == 64)
        paramCodedOutputStream.writeBool(10, this.individuallyRoutedPayload_);
      if ((0x80 & this.bitField0_) == 128)
        paramCodedOutputStream.writeBool(11, this.countForReliabilityTest_);
      if ((0x100 & this.bitField0_) == 256)
        paramCodedOutputStream.writeBool(12, this.destinationPayloadsSetSender_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.BuzzHeader, Builder>
      implements Buzz.BuzzHeaderOrBuilder
    {
      private boolean actAsPrimary_;
      private boolean alreadySentToPrimary_;
      private int bitField0_;
      private boolean countForReliabilityTest_;
      private boolean destinationPayloadsSetSender_;
      private boolean dropIfNoEndpoint_;
      private boolean dropIfNoResource_;
      private boolean individuallyRoutedPayload_;
      private Object secondaryPayload_ = "";
      private boolean stateUpdate_;

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
            this.bitField0_ = (0x1 | this.bitField0_);
            this.stateUpdate_ = paramCodedInputStream.readBool();
            break;
          case 16:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.alreadySentToPrimary_ = paramCodedInputStream.readBool();
            break;
          case 50:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.secondaryPayload_ = paramCodedInputStream.readBytes();
            break;
          case 56:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.dropIfNoEndpoint_ = paramCodedInputStream.readBool();
            break;
          case 64:
            this.bitField0_ = (0x10 | this.bitField0_);
            this.actAsPrimary_ = paramCodedInputStream.readBool();
            break;
          case 72:
            this.bitField0_ = (0x20 | this.bitField0_);
            this.dropIfNoResource_ = paramCodedInputStream.readBool();
            break;
          case 80:
            this.bitField0_ = (0x40 | this.bitField0_);
            this.individuallyRoutedPayload_ = paramCodedInputStream.readBool();
            break;
          case 88:
            this.bitField0_ = (0x80 | this.bitField0_);
            this.countForReliabilityTest_ = paramCodedInputStream.readBool();
            break;
          case 96:
          }
          this.bitField0_ = (0x100 | this.bitField0_);
          this.destinationPayloadsSetSender_ = paramCodedInputStream.readBool();
        }
      }

      public final Buzz.BuzzHeader build()
      {
        Buzz.BuzzHeader localBuzzHeader = buildPartial();
        if (!localBuzzHeader.isInitialized())
          throw new UninitializedMessageException();
        return localBuzzHeader;
      }

      public final Buzz.BuzzHeader buildPartial()
      {
        Buzz.BuzzHeader localBuzzHeader = new Buzz.BuzzHeader(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Buzz.BuzzHeader.access$2302(localBuzzHeader, this.stateUpdate_);
        if ((i & 0x2) == 2)
          k |= 2;
        Buzz.BuzzHeader.access$2402(localBuzzHeader, this.alreadySentToPrimary_);
        if ((i & 0x4) == 4)
          k |= 4;
        Buzz.BuzzHeader.access$2502(localBuzzHeader, this.secondaryPayload_);
        if ((i & 0x8) == 8)
          k |= 8;
        Buzz.BuzzHeader.access$2602(localBuzzHeader, this.dropIfNoEndpoint_);
        if ((i & 0x10) == 16)
          k |= 16;
        Buzz.BuzzHeader.access$2702(localBuzzHeader, this.actAsPrimary_);
        if ((i & 0x20) == 32)
          k |= 32;
        Buzz.BuzzHeader.access$2802(localBuzzHeader, this.dropIfNoResource_);
        if ((i & 0x40) == 64)
          k |= 64;
        Buzz.BuzzHeader.access$2902(localBuzzHeader, this.individuallyRoutedPayload_);
        if ((i & 0x80) == 128)
          k |= 128;
        Buzz.BuzzHeader.access$3002(localBuzzHeader, this.countForReliabilityTest_);
        if ((i & 0x100) == 256)
          k |= 256;
        Buzz.BuzzHeader.access$3102(localBuzzHeader, this.destinationPayloadsSetSender_);
        Buzz.BuzzHeader.access$3202(localBuzzHeader, k);
        return localBuzzHeader;
      }

      public final Builder clear()
      {
        super.clear();
        this.stateUpdate_ = false;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.alreadySentToPrimary_ = false;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.secondaryPayload_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.dropIfNoEndpoint_ = false;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.actAsPrimary_ = false;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.dropIfNoResource_ = false;
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.individuallyRoutedPayload_ = false;
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.countForReliabilityTest_ = false;
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        this.destinationPayloadsSetSender_ = false;
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        return this;
      }

      public final Builder clearActAsPrimary()
      {
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.actAsPrimary_ = false;
        return this;
      }

      public final Builder clearAlreadySentToPrimary()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.alreadySentToPrimary_ = false;
        return this;
      }

      public final Builder clearCountForReliabilityTest()
      {
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        this.countForReliabilityTest_ = false;
        return this;
      }

      public final Builder clearDestinationPayloadsSetSender()
      {
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        this.destinationPayloadsSetSender_ = false;
        return this;
      }

      public final Builder clearDropIfNoEndpoint()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.dropIfNoEndpoint_ = false;
        return this;
      }

      public final Builder clearDropIfNoResource()
      {
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.dropIfNoResource_ = false;
        return this;
      }

      public final Builder clearIndividuallyRoutedPayload()
      {
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.individuallyRoutedPayload_ = false;
        return this;
      }

      public final Builder clearSecondaryPayload()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.secondaryPayload_ = Buzz.BuzzHeader.getDefaultInstance().getSecondaryPayload();
        return this;
      }

      public final Builder clearStateUpdate()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.stateUpdate_ = false;
        return this;
      }

      public final boolean getActAsPrimary()
      {
        return this.actAsPrimary_;
      }

      public final boolean getAlreadySentToPrimary()
      {
        return this.alreadySentToPrimary_;
      }

      public final boolean getCountForReliabilityTest()
      {
        return this.countForReliabilityTest_;
      }

      public final Buzz.BuzzHeader getDefaultInstanceForType()
      {
        return Buzz.BuzzHeader.getDefaultInstance();
      }

      public final boolean getDestinationPayloadsSetSender()
      {
        return this.destinationPayloadsSetSender_;
      }

      public final boolean getDropIfNoEndpoint()
      {
        return this.dropIfNoEndpoint_;
      }

      public final boolean getDropIfNoResource()
      {
        return this.dropIfNoResource_;
      }

      public final boolean getIndividuallyRoutedPayload()
      {
        return this.individuallyRoutedPayload_;
      }

      public final String getSecondaryPayload()
      {
        Object localObject = this.secondaryPayload_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.secondaryPayload_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean getStateUpdate()
      {
        return this.stateUpdate_;
      }

      public final boolean hasActAsPrimary()
      {
        if ((0x10 & this.bitField0_) == 16);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasAlreadySentToPrimary()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasCountForReliabilityTest()
      {
        if ((0x80 & this.bitField0_) == 128);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasDestinationPayloadsSetSender()
      {
        if ((0x100 & this.bitField0_) == 256);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasDropIfNoEndpoint()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasDropIfNoResource()
      {
        if ((0x20 & this.bitField0_) == 32);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasIndividuallyRoutedPayload()
      {
        if ((0x40 & this.bitField0_) == 64);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasSecondaryPayload()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasStateUpdate()
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
        return true;
      }

      public final Builder mergeFrom(Buzz.BuzzHeader paramBuzzHeader)
      {
        if (paramBuzzHeader == Buzz.BuzzHeader.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramBuzzHeader.hasStateUpdate())
            setStateUpdate(paramBuzzHeader.getStateUpdate());
          if (paramBuzzHeader.hasAlreadySentToPrimary())
            setAlreadySentToPrimary(paramBuzzHeader.getAlreadySentToPrimary());
          if (paramBuzzHeader.hasSecondaryPayload())
            setSecondaryPayload(paramBuzzHeader.getSecondaryPayload());
          if (paramBuzzHeader.hasDropIfNoEndpoint())
            setDropIfNoEndpoint(paramBuzzHeader.getDropIfNoEndpoint());
          if (paramBuzzHeader.hasActAsPrimary())
            setActAsPrimary(paramBuzzHeader.getActAsPrimary());
          if (paramBuzzHeader.hasDropIfNoResource())
            setDropIfNoResource(paramBuzzHeader.getDropIfNoResource());
          if (paramBuzzHeader.hasIndividuallyRoutedPayload())
            setIndividuallyRoutedPayload(paramBuzzHeader.getIndividuallyRoutedPayload());
          if (paramBuzzHeader.hasCountForReliabilityTest())
            setCountForReliabilityTest(paramBuzzHeader.getCountForReliabilityTest());
          if (paramBuzzHeader.hasDestinationPayloadsSetSender())
            setDestinationPayloadsSetSender(paramBuzzHeader.getDestinationPayloadsSetSender());
        }
      }

      public final Builder setActAsPrimary(boolean paramBoolean)
      {
        this.bitField0_ = (0x10 | this.bitField0_);
        this.actAsPrimary_ = paramBoolean;
        return this;
      }

      public final Builder setAlreadySentToPrimary(boolean paramBoolean)
      {
        this.bitField0_ = (0x2 | this.bitField0_);
        this.alreadySentToPrimary_ = paramBoolean;
        return this;
      }

      public final Builder setCountForReliabilityTest(boolean paramBoolean)
      {
        this.bitField0_ = (0x80 | this.bitField0_);
        this.countForReliabilityTest_ = paramBoolean;
        return this;
      }

      public final Builder setDestinationPayloadsSetSender(boolean paramBoolean)
      {
        this.bitField0_ = (0x100 | this.bitField0_);
        this.destinationPayloadsSetSender_ = paramBoolean;
        return this;
      }

      public final Builder setDropIfNoEndpoint(boolean paramBoolean)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.dropIfNoEndpoint_ = paramBoolean;
        return this;
      }

      public final Builder setDropIfNoResource(boolean paramBoolean)
      {
        this.bitField0_ = (0x20 | this.bitField0_);
        this.dropIfNoResource_ = paramBoolean;
        return this;
      }

      public final Builder setIndividuallyRoutedPayload(boolean paramBoolean)
      {
        this.bitField0_ = (0x40 | this.bitField0_);
        this.individuallyRoutedPayload_ = paramBoolean;
        return this;
      }

      public final Builder setSecondaryPayload(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.secondaryPayload_ = paramString;
        return this;
      }

      public final Builder setStateUpdate(boolean paramBoolean)
      {
        this.bitField0_ = (0x1 | this.bitField0_);
        this.stateUpdate_ = paramBoolean;
        return this;
      }
    }
  }

  public static abstract interface BuzzHeaderOrBuilder extends MessageLiteOrBuilder
  {
    public abstract boolean getActAsPrimary();

    public abstract boolean getAlreadySentToPrimary();

    public abstract boolean getCountForReliabilityTest();

    public abstract boolean getDestinationPayloadsSetSender();

    public abstract boolean getDropIfNoEndpoint();

    public abstract boolean getDropIfNoResource();

    public abstract boolean getIndividuallyRoutedPayload();

    public abstract String getSecondaryPayload();

    public abstract boolean getStateUpdate();

    public abstract boolean hasActAsPrimary();

    public abstract boolean hasAlreadySentToPrimary();

    public abstract boolean hasCountForReliabilityTest();

    public abstract boolean hasDestinationPayloadsSetSender();

    public abstract boolean hasDropIfNoEndpoint();

    public abstract boolean hasDropIfNoResource();

    public abstract boolean hasIndividuallyRoutedPayload();

    public abstract boolean hasSecondaryPayload();

    public abstract boolean hasStateUpdate();
  }

  public static final class InternalAddress extends GeneratedMessageLite
    implements Buzz.InternalAddressOrBuilder
  {
    private static final InternalAddress defaultInstance;
    private static final long serialVersionUID;
    private boolean addressable_;
    private int bitField0_;
    private Gateway gateway_;
    private JID jID_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    static
    {
      InternalAddress localInternalAddress = new InternalAddress();
      defaultInstance = localInternalAddress;
      localInternalAddress.jID_ = JID.getDefaultInstance();
      localInternalAddress.gateway_ = Gateway.getDefaultInstance();
      localInternalAddress.addressable_ = true;
    }

    private InternalAddress()
    {
    }

    private InternalAddress(Builder paramBuilder)
    {
      super();
    }

    public static InternalAddress getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$1400();
    }

    public static Builder newBuilder(InternalAddress paramInternalAddress)
    {
      return Builder.access$1400().mergeFrom(paramInternalAddress);
    }

    public final boolean getAddressable()
    {
      return this.addressable_;
    }

    public final InternalAddress getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final Gateway getGateway()
    {
      return this.gateway_;
    }

    public final JID getJID()
    {
      return this.jID_;
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
          k = 0 + CodedOutputStream.computeGroupSize(1, this.jID_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeGroupSize(5, this.gateway_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBoolSize(8, this.addressable_);
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasAddressable()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasGateway()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasJID()
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
        if ((hasJID()) && (!getJID().isInitialized()))
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if ((hasGateway()) && (!getGateway().isInitialized()))
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
        paramCodedOutputStream.writeGroup(1, this.jID_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeGroup(5, this.gateway_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBool(8, this.addressable_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.InternalAddress, Builder>
      implements Buzz.InternalAddressOrBuilder
    {
      private boolean addressable_ = true;
      private int bitField0_;
      private Buzz.InternalAddress.Gateway gateway_ = Buzz.InternalAddress.Gateway.getDefaultInstance();
      private Buzz.InternalAddress.JID jID_ = Buzz.InternalAddress.JID.getDefaultInstance();

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
          case 11:
            Buzz.InternalAddress.JID.Builder localBuilder1 = Buzz.InternalAddress.JID.newBuilder();
            if (hasJID())
              localBuilder1.mergeFrom(getJID());
            paramCodedInputStream.readGroup(1, localBuilder1, paramExtensionRegistryLite);
            setJID(localBuilder1.buildPartial());
            break;
          case 43:
            Buzz.InternalAddress.Gateway.Builder localBuilder = Buzz.InternalAddress.Gateway.newBuilder();
            if (hasGateway())
              localBuilder.mergeFrom(getGateway());
            paramCodedInputStream.readGroup(5, localBuilder, paramExtensionRegistryLite);
            setGateway(localBuilder.buildPartial());
            break;
          case 64:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.addressable_ = paramCodedInputStream.readBool();
        }
      }

      public final Buzz.InternalAddress build()
      {
        Buzz.InternalAddress localInternalAddress = buildPartial();
        if (!localInternalAddress.isInitialized())
          throw new UninitializedMessageException();
        return localInternalAddress;
      }

      public final Buzz.InternalAddress buildPartial()
      {
        Buzz.InternalAddress localInternalAddress = new Buzz.InternalAddress(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Buzz.InternalAddress.access$1602(localInternalAddress, this.jID_);
        if ((i & 0x2) == 2)
          k |= 2;
        Buzz.InternalAddress.access$1702(localInternalAddress, this.gateway_);
        if ((i & 0x4) == 4)
          k |= 4;
        Buzz.InternalAddress.access$1802(localInternalAddress, this.addressable_);
        Buzz.InternalAddress.access$1902(localInternalAddress, k);
        return localInternalAddress;
      }

      public final Builder clear()
      {
        super.clear();
        this.jID_ = Buzz.InternalAddress.JID.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.gateway_ = Buzz.InternalAddress.Gateway.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.addressable_ = true;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearAddressable()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.addressable_ = true;
        return this;
      }

      public final Builder clearGateway()
      {
        this.gateway_ = Buzz.InternalAddress.Gateway.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearJID()
      {
        this.jID_ = Buzz.InternalAddress.JID.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final boolean getAddressable()
      {
        return this.addressable_;
      }

      public final Buzz.InternalAddress getDefaultInstanceForType()
      {
        return Buzz.InternalAddress.getDefaultInstance();
      }

      public final Buzz.InternalAddress.Gateway getGateway()
      {
        return this.gateway_;
      }

      public final Buzz.InternalAddress.JID getJID()
      {
        return this.jID_;
      }

      public final boolean hasAddressable()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasGateway()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasJID()
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
        boolean bool1;
        if (hasJID())
        {
          boolean bool3 = getJID().isInitialized();
          bool1 = false;
          if (bool3);
        }
        while (true)
        {
          return bool1;
          if (hasGateway())
          {
            boolean bool2 = getGateway().isInitialized();
            bool1 = false;
            if (!bool2);
          }
          else
          {
            bool1 = true;
          }
        }
      }

      public final Builder mergeFrom(Buzz.InternalAddress paramInternalAddress)
      {
        if (paramInternalAddress == Buzz.InternalAddress.getDefaultInstance())
          return this;
        Buzz.InternalAddress.JID localJID;
        label61: Buzz.InternalAddress.Gateway localGateway;
        if (paramInternalAddress.hasJID())
        {
          localJID = paramInternalAddress.getJID();
          if (((0x1 & this.bitField0_) == 1) && (this.jID_ != Buzz.InternalAddress.JID.getDefaultInstance()))
          {
            this.jID_ = Buzz.InternalAddress.JID.newBuilder(this.jID_).mergeFrom(localJID).buildPartial();
            this.bitField0_ = (0x1 | this.bitField0_);
          }
        }
        else if (paramInternalAddress.hasGateway())
        {
          localGateway = paramInternalAddress.getGateway();
          if (((0x2 & this.bitField0_) != 2) || (this.gateway_ == Buzz.InternalAddress.Gateway.getDefaultInstance()))
            break label159;
        }
        label159: for (this.gateway_ = Buzz.InternalAddress.Gateway.newBuilder(this.gateway_).mergeFrom(localGateway).buildPartial(); ; this.gateway_ = localGateway)
        {
          this.bitField0_ = (0x2 | this.bitField0_);
          if (!paramInternalAddress.hasAddressable())
            break;
          setAddressable(paramInternalAddress.getAddressable());
          break;
          this.jID_ = localJID;
          break label61;
        }
      }

      public final Builder setAddressable(boolean paramBoolean)
      {
        this.bitField0_ = (0x4 | this.bitField0_);
        this.addressable_ = paramBoolean;
        return this;
      }

      public final Builder setGateway(Buzz.InternalAddress.Gateway.Builder paramBuilder)
      {
        this.gateway_ = paramBuilder.build();
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setGateway(Buzz.InternalAddress.Gateway paramGateway)
      {
        if (paramGateway == null)
          throw new NullPointerException();
        this.gateway_ = paramGateway;
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setJID(Buzz.InternalAddress.JID.Builder paramBuilder)
      {
        this.jID_ = paramBuilder.build();
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setJID(Buzz.InternalAddress.JID paramJID)
      {
        if (paramJID == null)
          throw new NullPointerException();
        this.jID_ = paramJID;
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }
    }

    public static final class Gateway extends GeneratedMessageLite
      implements Buzz.InternalAddress.GatewayOrBuilder
    {
      private static final Gateway defaultInstance;
      private static final long serialVersionUID;
      private int bitField0_;
      private Object cookie_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private Object networkLocation_;

      static
      {
        Gateway localGateway = new Gateway();
        defaultInstance = localGateway;
        localGateway.networkLocation_ = "";
        localGateway.cookie_ = "";
      }

      private Gateway()
      {
      }

      private Gateway(Builder paramBuilder)
      {
        super();
      }

      private ByteString getCookieBytes()
      {
        Object localObject = this.cookie_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.cookie_ = localByteString;
        }
        while (true)
        {
          return localByteString;
          localByteString = (ByteString)localObject;
        }
      }

      public static Gateway getDefaultInstance()
      {
        return defaultInstance;
      }

      private ByteString getNetworkLocationBytes()
      {
        Object localObject = this.networkLocation_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.networkLocation_ = localByteString;
        }
        while (true)
        {
          return localByteString;
          localByteString = (ByteString)localObject;
        }
      }

      public static Builder newBuilder()
      {
        return Builder.access$800();
      }

      public static Builder newBuilder(Gateway paramGateway)
      {
        return Builder.access$800().mergeFrom(paramGateway);
      }

      public final String getCookie()
      {
        Object localObject1 = this.cookie_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.cookie_ = str;
        }
      }

      public final Gateway getDefaultInstanceForType()
      {
        return defaultInstance;
      }

      public final String getNetworkLocation()
      {
        Object localObject1 = this.networkLocation_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.networkLocation_ = str;
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
            k = 0 + CodedOutputStream.computeBytesSize(6, getNetworkLocationBytes());
          if ((0x2 & this.bitField0_) == 2)
            k += CodedOutputStream.computeBytesSize(7, getCookieBytes());
          this.memoizedSerializedSize = k;
        }
      }

      public final boolean hasCookie()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasNetworkLocation()
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
          if (!hasNetworkLocation())
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
          paramCodedOutputStream.writeBytes(6, getNetworkLocationBytes());
        if ((0x2 & this.bitField0_) == 2)
          paramCodedOutputStream.writeBytes(7, getCookieBytes());
      }

      public static final class Builder extends GeneratedMessageLite.Builder<Buzz.InternalAddress.Gateway, Builder>
        implements Buzz.InternalAddress.GatewayOrBuilder
      {
        private int bitField0_;
        private Object cookie_ = "";
        private Object networkLocation_ = "";

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
            case 50:
              this.bitField0_ = (0x1 | this.bitField0_);
              this.networkLocation_ = paramCodedInputStream.readBytes();
              break;
            case 58:
            }
            this.bitField0_ = (0x2 | this.bitField0_);
            this.cookie_ = paramCodedInputStream.readBytes();
          }
        }

        public final Buzz.InternalAddress.Gateway build()
        {
          Buzz.InternalAddress.Gateway localGateway = buildPartial();
          if (!localGateway.isInitialized())
            throw new UninitializedMessageException();
          return localGateway;
        }

        public final Buzz.InternalAddress.Gateway buildPartial()
        {
          Buzz.InternalAddress.Gateway localGateway = new Buzz.InternalAddress.Gateway(this, (byte)0);
          int i = this.bitField0_;
          int j = i & 0x1;
          int k = 0;
          if (j == 1)
            k = 1;
          Buzz.InternalAddress.Gateway.access$1002(localGateway, this.networkLocation_);
          if ((i & 0x2) == 2)
            k |= 2;
          Buzz.InternalAddress.Gateway.access$1102(localGateway, this.cookie_);
          Buzz.InternalAddress.Gateway.access$1202(localGateway, k);
          return localGateway;
        }

        public final Builder clear()
        {
          super.clear();
          this.networkLocation_ = "";
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.cookie_ = "";
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          return this;
        }

        public final Builder clearCookie()
        {
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          this.cookie_ = Buzz.InternalAddress.Gateway.getDefaultInstance().getCookie();
          return this;
        }

        public final Builder clearNetworkLocation()
        {
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.networkLocation_ = Buzz.InternalAddress.Gateway.getDefaultInstance().getNetworkLocation();
          return this;
        }

        public final String getCookie()
        {
          Object localObject = this.cookie_;
          String str;
          if (!(localObject instanceof String))
          {
            str = ((ByteString)localObject).toStringUtf8();
            this.cookie_ = str;
          }
          while (true)
          {
            return str;
            str = (String)localObject;
          }
        }

        public final Buzz.InternalAddress.Gateway getDefaultInstanceForType()
        {
          return Buzz.InternalAddress.Gateway.getDefaultInstance();
        }

        public final String getNetworkLocation()
        {
          Object localObject = this.networkLocation_;
          String str;
          if (!(localObject instanceof String))
          {
            str = ((ByteString)localObject).toStringUtf8();
            this.networkLocation_ = str;
          }
          while (true)
          {
            return str;
            str = (String)localObject;
          }
        }

        public final boolean hasCookie()
        {
          if ((0x2 & this.bitField0_) == 2);
          for (boolean bool = true; ; bool = false)
            return bool;
        }

        public final boolean hasNetworkLocation()
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
          if (!hasNetworkLocation());
          for (boolean bool = false; ; bool = true)
            return bool;
        }

        public final Builder mergeFrom(Buzz.InternalAddress.Gateway paramGateway)
        {
          if (paramGateway == Buzz.InternalAddress.Gateway.getDefaultInstance());
          while (true)
          {
            return this;
            if (paramGateway.hasNetworkLocation())
              setNetworkLocation(paramGateway.getNetworkLocation());
            if (paramGateway.hasCookie())
              setCookie(paramGateway.getCookie());
          }
        }

        public final Builder setCookie(String paramString)
        {
          if (paramString == null)
            throw new NullPointerException();
          this.bitField0_ = (0x2 | this.bitField0_);
          this.cookie_ = paramString;
          return this;
        }

        public final Builder setNetworkLocation(String paramString)
        {
          if (paramString == null)
            throw new NullPointerException();
          this.bitField0_ = (0x1 | this.bitField0_);
          this.networkLocation_ = paramString;
          return this;
        }
      }
    }

    public static abstract interface GatewayOrBuilder extends MessageLiteOrBuilder
    {
      public abstract String getCookie();

      public abstract String getNetworkLocation();

      public abstract boolean hasCookie();

      public abstract boolean hasNetworkLocation();
    }

    public static final class JID extends GeneratedMessageLite
      implements Buzz.InternalAddress.JIDOrBuilder
    {
      private static final JID defaultInstance;
      private static final long serialVersionUID;
      private int bitField0_;
      private Object domain_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private Object node_;
      private Object resource_;

      static
      {
        JID localJID = new JID();
        defaultInstance = localJID;
        localJID.domain_ = "";
        localJID.node_ = "";
        localJID.resource_ = "";
      }

      private JID()
      {
      }

      private JID(Builder paramBuilder)
      {
        super();
      }

      public static JID getDefaultInstance()
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

      private ByteString getNodeBytes()
      {
        Object localObject = this.node_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.node_ = localByteString;
        }
        while (true)
        {
          return localByteString;
          localByteString = (ByteString)localObject;
        }
      }

      private ByteString getResourceBytes()
      {
        Object localObject = this.resource_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.resource_ = localByteString;
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

      public static Builder newBuilder(JID paramJID)
      {
        return Builder.access$100().mergeFrom(paramJID);
      }

      public final JID getDefaultInstanceForType()
      {
        return defaultInstance;
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

      public final String getNode()
      {
        Object localObject1 = this.node_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.node_ = str;
        }
      }

      public final String getResource()
      {
        Object localObject1 = this.resource_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.resource_ = str;
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
            k = 0 + CodedOutputStream.computeBytesSize(2, getDomainBytes());
          if ((0x2 & this.bitField0_) == 2)
            k += CodedOutputStream.computeBytesSize(3, getNodeBytes());
          if ((0x4 & this.bitField0_) == 4)
            k += CodedOutputStream.computeBytesSize(4, getResourceBytes());
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

      public final boolean hasNode()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasResource()
      {
        if ((0x4 & this.bitField0_) == 4);
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
          paramCodedOutputStream.writeBytes(2, getDomainBytes());
        if ((0x2 & this.bitField0_) == 2)
          paramCodedOutputStream.writeBytes(3, getNodeBytes());
        if ((0x4 & this.bitField0_) == 4)
          paramCodedOutputStream.writeBytes(4, getResourceBytes());
      }

      public static final class Builder extends GeneratedMessageLite.Builder<Buzz.InternalAddress.JID, Builder>
        implements Buzz.InternalAddress.JIDOrBuilder
      {
        private int bitField0_;
        private Object domain_ = "";
        private Object node_ = "";
        private Object resource_ = "";

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
            case 18:
              this.bitField0_ = (0x1 | this.bitField0_);
              this.domain_ = paramCodedInputStream.readBytes();
              break;
            case 26:
              this.bitField0_ = (0x2 | this.bitField0_);
              this.node_ = paramCodedInputStream.readBytes();
              break;
            case 34:
            }
            this.bitField0_ = (0x4 | this.bitField0_);
            this.resource_ = paramCodedInputStream.readBytes();
          }
        }

        public final Buzz.InternalAddress.JID build()
        {
          Buzz.InternalAddress.JID localJID = buildPartial();
          if (!localJID.isInitialized())
            throw new UninitializedMessageException();
          return localJID;
        }

        public final Buzz.InternalAddress.JID buildPartial()
        {
          Buzz.InternalAddress.JID localJID = new Buzz.InternalAddress.JID(this, (byte)0);
          int i = this.bitField0_;
          int j = i & 0x1;
          int k = 0;
          if (j == 1)
            k = 1;
          Buzz.InternalAddress.JID.access$302(localJID, this.domain_);
          if ((i & 0x2) == 2)
            k |= 2;
          Buzz.InternalAddress.JID.access$402(localJID, this.node_);
          if ((i & 0x4) == 4)
            k |= 4;
          Buzz.InternalAddress.JID.access$502(localJID, this.resource_);
          Buzz.InternalAddress.JID.access$602(localJID, k);
          return localJID;
        }

        public final Builder clear()
        {
          super.clear();
          this.domain_ = "";
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.node_ = "";
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          this.resource_ = "";
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          return this;
        }

        public final Builder clearDomain()
        {
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.domain_ = Buzz.InternalAddress.JID.getDefaultInstance().getDomain();
          return this;
        }

        public final Builder clearNode()
        {
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          this.node_ = Buzz.InternalAddress.JID.getDefaultInstance().getNode();
          return this;
        }

        public final Builder clearResource()
        {
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          this.resource_ = Buzz.InternalAddress.JID.getDefaultInstance().getResource();
          return this;
        }

        public final Buzz.InternalAddress.JID getDefaultInstanceForType()
        {
          return Buzz.InternalAddress.JID.getDefaultInstance();
        }

        public final String getDomain()
        {
          Object localObject = this.domain_;
          String str;
          if (!(localObject instanceof String))
          {
            str = ((ByteString)localObject).toStringUtf8();
            this.domain_ = str;
          }
          while (true)
          {
            return str;
            str = (String)localObject;
          }
        }

        public final String getNode()
        {
          Object localObject = this.node_;
          String str;
          if (!(localObject instanceof String))
          {
            str = ((ByteString)localObject).toStringUtf8();
            this.node_ = str;
          }
          while (true)
          {
            return str;
            str = (String)localObject;
          }
        }

        public final String getResource()
        {
          Object localObject = this.resource_;
          String str;
          if (!(localObject instanceof String))
          {
            str = ((ByteString)localObject).toStringUtf8();
            this.resource_ = str;
          }
          while (true)
          {
            return str;
            str = (String)localObject;
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

        public final boolean hasNode()
        {
          if ((0x2 & this.bitField0_) == 2);
          for (boolean bool = true; ; bool = false)
            return bool;
        }

        public final boolean hasResource()
        {
          if ((0x4 & this.bitField0_) == 4);
          for (boolean bool = true; ; bool = false)
            return bool;
        }

        public final boolean isInitialized()
        {
          if (!hasDomain());
          for (boolean bool = false; ; bool = true)
            return bool;
        }

        public final Builder mergeFrom(Buzz.InternalAddress.JID paramJID)
        {
          if (paramJID == Buzz.InternalAddress.JID.getDefaultInstance());
          while (true)
          {
            return this;
            if (paramJID.hasDomain())
              setDomain(paramJID.getDomain());
            if (paramJID.hasNode())
              setNode(paramJID.getNode());
            if (paramJID.hasResource())
              setResource(paramJID.getResource());
          }
        }

        public final Builder setDomain(String paramString)
        {
          if (paramString == null)
            throw new NullPointerException();
          this.bitField0_ = (0x1 | this.bitField0_);
          this.domain_ = paramString;
          return this;
        }

        public final Builder setNode(String paramString)
        {
          if (paramString == null)
            throw new NullPointerException();
          this.bitField0_ = (0x2 | this.bitField0_);
          this.node_ = paramString;
          return this;
        }

        public final Builder setResource(String paramString)
        {
          if (paramString == null)
            throw new NullPointerException();
          this.bitField0_ = (0x4 | this.bitField0_);
          this.resource_ = paramString;
          return this;
        }
      }
    }

    public static abstract interface JIDOrBuilder extends MessageLiteOrBuilder
    {
      public abstract String getDomain();

      public abstract String getNode();

      public abstract String getResource();

      public abstract boolean hasDomain();

      public abstract boolean hasNode();

      public abstract boolean hasResource();
    }
  }

  public static abstract interface InternalAddressOrBuilder extends MessageLiteOrBuilder
  {
    public abstract boolean getAddressable();

    public abstract Buzz.InternalAddress.Gateway getGateway();

    public abstract Buzz.InternalAddress.JID getJID();

    public abstract boolean hasAddressable();

    public abstract boolean hasGateway();

    public abstract boolean hasJID();
  }

  public static final class MessagingPayload extends GeneratedMessageLite
    implements Buzz.MessagingPayloadOrBuilder
  {
    private static final MessagingPayload defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Buzz.BuzzHeader buzzHeader_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private int payloadType_;
    private ByteString payload_;
    private List<Buzz.MessagingRecipientData> recipient_;
    private Buzz.InternalAddress sender_;

    static
    {
      MessagingPayload localMessagingPayload = new MessagingPayload();
      defaultInstance = localMessagingPayload;
      localMessagingPayload.recipient_ = Collections.emptyList();
      localMessagingPayload.sender_ = Buzz.InternalAddress.getDefaultInstance();
      localMessagingPayload.payload_ = ByteString.EMPTY;
      localMessagingPayload.payloadType_ = 0;
      localMessagingPayload.buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
    }

    private MessagingPayload()
    {
    }

    private MessagingPayload(Builder paramBuilder)
    {
      super();
    }

    public static MessagingPayload getDefaultInstance()
    {
      return defaultInstance;
    }

    public final Buzz.BuzzHeader getBuzzHeader()
    {
      return this.buzzHeader_;
    }

    public final MessagingPayload getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final ByteString getPayload()
    {
      return this.payload_;
    }

    public final int getPayloadType()
    {
      return this.payloadType_;
    }

    public final Buzz.MessagingRecipientData getRecipient(int paramInt)
    {
      return (Buzz.MessagingRecipientData)this.recipient_.get(paramInt);
    }

    public final int getRecipientCount()
    {
      return this.recipient_.size();
    }

    public final List<Buzz.MessagingRecipientData> getRecipientList()
    {
      return this.recipient_;
    }

    public final Buzz.MessagingRecipientDataOrBuilder getRecipientOrBuilder(int paramInt)
    {
      return (Buzz.MessagingRecipientDataOrBuilder)this.recipient_.get(paramInt);
    }

    public final List<? extends Buzz.MessagingRecipientDataOrBuilder> getRecipientOrBuilderList()
    {
      return this.recipient_;
    }

    public final Buzz.InternalAddress getSender()
    {
      return this.sender_;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int j;
      for (int m = i; ; m = j)
      {
        return m;
        j = 0;
        for (int k = 0; k < this.recipient_.size(); k++)
          j += CodedOutputStream.computeMessageSize(1, (MessageLite)this.recipient_.get(k));
        if ((0x1 & this.bitField0_) == 1)
          j += CodedOutputStream.computeMessageSize(2, this.sender_);
        if ((0x2 & this.bitField0_) == 2)
          j += CodedOutputStream.computeBytesSize(3, this.payload_);
        if ((0x4 & this.bitField0_) == 4)
          j += CodedOutputStream.computeInt32Size(4, this.payloadType_);
        if ((0x8 & this.bitField0_) == 8)
          j += CodedOutputStream.computeMessageSize(5, this.buzzHeader_);
        this.memoizedSerializedSize = j;
      }
    }

    public final boolean hasBuzzHeader()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPayload()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPayloadType()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasSender()
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
        if (!hasSender())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasPayload())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasPayloadType())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else
        {
          for (int k = 0; ; k++)
          {
            if (k >= getRecipientCount())
              break label112;
            if (!getRecipient(k).isInitialized())
            {
              this.memoizedIsInitialized = 0;
              i = 0;
              break;
            }
          }
          label112: if (!getSender().isInitialized())
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
      for (int i = 0; i < this.recipient_.size(); i++)
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.recipient_.get(i));
      if ((0x1 & this.bitField0_) == 1)
        paramCodedOutputStream.writeMessage(2, this.sender_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(3, this.payload_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeInt32(4, this.payloadType_);
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeMessage(5, this.buzzHeader_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.MessagingPayload, Builder>
      implements Buzz.MessagingPayloadOrBuilder
    {
      private int bitField0_;
      private Buzz.BuzzHeader buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
      private int payloadType_;
      private ByteString payload_ = ByteString.EMPTY;
      private List<Buzz.MessagingRecipientData> recipient_ = Collections.emptyList();
      private Buzz.InternalAddress sender_ = Buzz.InternalAddress.getDefaultInstance();

      private Buzz.MessagingPayload buildPartial()
      {
        Buzz.MessagingPayload localMessagingPayload = new Buzz.MessagingPayload(this, (byte)0);
        int i = this.bitField0_;
        if ((0x1 & this.bitField0_) == 1)
        {
          this.recipient_ = Collections.unmodifiableList(this.recipient_);
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        }
        Buzz.MessagingPayload.access$5502(localMessagingPayload, this.recipient_);
        int j = i & 0x2;
        int k = 0;
        if (j == 2)
          k = 1;
        Buzz.MessagingPayload.access$5602(localMessagingPayload, this.sender_);
        if ((i & 0x4) == 4)
          k |= 2;
        Buzz.MessagingPayload.access$5702(localMessagingPayload, this.payload_);
        if ((i & 0x8) == 8)
          k |= 4;
        Buzz.MessagingPayload.access$5802(localMessagingPayload, this.payloadType_);
        if ((i & 0x10) == 16)
          k |= 8;
        Buzz.MessagingPayload.access$5902(localMessagingPayload, this.buzzHeader_);
        Buzz.MessagingPayload.access$6002(localMessagingPayload, k);
        return localMessagingPayload;
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureRecipientIsMutable()
      {
        if ((0x1 & this.bitField0_) != 1)
        {
          this.recipient_ = new ArrayList(this.recipient_);
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
          case 10:
            Buzz.MessagingRecipientData.Builder localBuilder2 = Buzz.MessagingRecipientData.newBuilder();
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            addRecipient(localBuilder2.buildPartial());
            break;
          case 18:
            Buzz.InternalAddress.Builder localBuilder1 = Buzz.InternalAddress.newBuilder();
            if (hasSender())
              localBuilder1.mergeFrom(getSender());
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            setSender(localBuilder1.buildPartial());
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.payload_ = paramCodedInputStream.readBytes();
            break;
          case 32:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.payloadType_ = paramCodedInputStream.readInt32();
            break;
          case 42:
          }
          Buzz.BuzzHeader.Builder localBuilder = Buzz.BuzzHeader.newBuilder();
          if (hasBuzzHeader())
            localBuilder.mergeFrom(getBuzzHeader());
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          setBuzzHeader(localBuilder.buildPartial());
        }
      }

      public final Builder addAllRecipient(Iterable<? extends Buzz.MessagingRecipientData> paramIterable)
      {
        ensureRecipientIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.recipient_);
        return this;
      }

      public final Builder addRecipient(int paramInt, Buzz.MessagingRecipientData.Builder paramBuilder)
      {
        ensureRecipientIsMutable();
        this.recipient_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addRecipient(int paramInt, Buzz.MessagingRecipientData paramMessagingRecipientData)
      {
        if (paramMessagingRecipientData == null)
          throw new NullPointerException();
        ensureRecipientIsMutable();
        this.recipient_.add(paramInt, paramMessagingRecipientData);
        return this;
      }

      public final Builder addRecipient(Buzz.MessagingRecipientData.Builder paramBuilder)
      {
        ensureRecipientIsMutable();
        this.recipient_.add(paramBuilder.build());
        return this;
      }

      public final Builder addRecipient(Buzz.MessagingRecipientData paramMessagingRecipientData)
      {
        if (paramMessagingRecipientData == null)
          throw new NullPointerException();
        ensureRecipientIsMutable();
        this.recipient_.add(paramMessagingRecipientData);
        return this;
      }

      public final Builder clear()
      {
        super.clear();
        this.recipient_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.sender_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.payload_ = ByteString.EMPTY;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.payloadType_ = 0;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearBuzzHeader()
      {
        this.buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearPayload()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.payload_ = Buzz.MessagingPayload.getDefaultInstance().getPayload();
        return this;
      }

      public final Builder clearPayloadType()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.payloadType_ = 0;
        return this;
      }

      public final Builder clearRecipient()
      {
        this.recipient_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Builder clearSender()
      {
        this.sender_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Buzz.BuzzHeader getBuzzHeader()
      {
        return this.buzzHeader_;
      }

      public final Buzz.MessagingPayload getDefaultInstanceForType()
      {
        return Buzz.MessagingPayload.getDefaultInstance();
      }

      public final ByteString getPayload()
      {
        return this.payload_;
      }

      public final int getPayloadType()
      {
        return this.payloadType_;
      }

      public final Buzz.MessagingRecipientData getRecipient(int paramInt)
      {
        return (Buzz.MessagingRecipientData)this.recipient_.get(paramInt);
      }

      public final int getRecipientCount()
      {
        return this.recipient_.size();
      }

      public final List<Buzz.MessagingRecipientData> getRecipientList()
      {
        return Collections.unmodifiableList(this.recipient_);
      }

      public final Buzz.InternalAddress getSender()
      {
        return this.sender_;
      }

      public final boolean hasBuzzHeader()
      {
        if ((0x10 & this.bitField0_) == 16);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasPayload()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasPayloadType()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasSender()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        boolean bool1 = hasSender();
        boolean bool2 = false;
        if (!bool1);
        while (true)
        {
          return bool2;
          boolean bool3 = hasPayload();
          bool2 = false;
          if (bool3)
          {
            boolean bool4 = hasPayloadType();
            bool2 = false;
            if (bool4)
            {
              for (int i = 0; ; i++)
              {
                if (i >= getRecipientCount())
                  break label73;
                boolean bool6 = getRecipient(i).isInitialized();
                bool2 = false;
                if (!bool6)
                  break;
              }
              label73: boolean bool5 = getSender().isInitialized();
              bool2 = false;
              if (bool5)
                bool2 = true;
            }
          }
        }
      }

      public final Builder mergeFrom(Buzz.MessagingPayload paramMessagingPayload)
      {
        if (paramMessagingPayload == Buzz.MessagingPayload.getDefaultInstance());
        label52: Buzz.InternalAddress localInternalAddress;
        label104: 
        do
        {
          return this;
          if (!paramMessagingPayload.recipient_.isEmpty())
          {
            if (!this.recipient_.isEmpty())
              break;
            this.recipient_ = paramMessagingPayload.recipient_;
            this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          }
          if (paramMessagingPayload.hasSender())
          {
            localInternalAddress = paramMessagingPayload.getSender();
            if (((0x2 & this.bitField0_) != 2) || (this.sender_ == Buzz.InternalAddress.getDefaultInstance()))
              break label233;
            this.sender_ = Buzz.InternalAddress.newBuilder(this.sender_).mergeFrom(localInternalAddress).buildPartial();
            this.bitField0_ = (0x2 | this.bitField0_);
          }
          if (paramMessagingPayload.hasPayload())
            setPayload(paramMessagingPayload.getPayload());
          if (paramMessagingPayload.hasPayloadType())
            setPayloadType(paramMessagingPayload.getPayloadType());
        }
        while (!paramMessagingPayload.hasBuzzHeader());
        Buzz.BuzzHeader localBuzzHeader = paramMessagingPayload.getBuzzHeader();
        if (((0x10 & this.bitField0_) == 16) && (this.buzzHeader_ != Buzz.BuzzHeader.getDefaultInstance()));
        for (this.buzzHeader_ = Buzz.BuzzHeader.newBuilder(this.buzzHeader_).mergeFrom(localBuzzHeader).buildPartial(); ; this.buzzHeader_ = localBuzzHeader)
        {
          this.bitField0_ = (0x10 | this.bitField0_);
          break;
          ensureRecipientIsMutable();
          this.recipient_.addAll(paramMessagingPayload.recipient_);
          break label52;
          label233: this.sender_ = localInternalAddress;
          break label104;
        }
      }

      public final Builder setBuzzHeader(Buzz.BuzzHeader.Builder paramBuilder)
      {
        this.buzzHeader_ = paramBuilder.build();
        this.bitField0_ = (0x10 | this.bitField0_);
        return this;
      }

      public final Builder setBuzzHeader(Buzz.BuzzHeader paramBuzzHeader)
      {
        if (paramBuzzHeader == null)
          throw new NullPointerException();
        this.buzzHeader_ = paramBuzzHeader;
        this.bitField0_ = (0x10 | this.bitField0_);
        return this;
      }

      public final Builder setPayload(ByteString paramByteString)
      {
        if (paramByteString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.payload_ = paramByteString;
        return this;
      }

      public final Builder setPayloadType(int paramInt)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.payloadType_ = paramInt;
        return this;
      }

      public final Builder setRecipient(int paramInt, Buzz.MessagingRecipientData.Builder paramBuilder)
      {
        ensureRecipientIsMutable();
        this.recipient_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setRecipient(int paramInt, Buzz.MessagingRecipientData paramMessagingRecipientData)
      {
        if (paramMessagingRecipientData == null)
          throw new NullPointerException();
        ensureRecipientIsMutable();
        this.recipient_.set(paramInt, paramMessagingRecipientData);
        return this;
      }

      public final Builder setSender(Buzz.InternalAddress.Builder paramBuilder)
      {
        this.sender_ = paramBuilder.build();
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setSender(Buzz.InternalAddress paramInternalAddress)
      {
        if (paramInternalAddress == null)
          throw new NullPointerException();
        this.sender_ = paramInternalAddress;
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }
    }

    public static enum PAYLOAD_TYPE
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<PAYLOAD_TYPE> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        PUSH_MESSAGE = new PAYLOAD_TYPE("PUSH_MESSAGE", 1, 1);
        BATCHED_STANZA = new PAYLOAD_TYPE("BATCHED_STANZA", 2, 2);
        PAYLOAD_TYPE[] arrayOfPAYLOAD_TYPE = new PAYLOAD_TYPE[3];
        arrayOfPAYLOAD_TYPE[0] = PUSH_SUBSCRIPTION;
        arrayOfPAYLOAD_TYPE[1] = PUSH_MESSAGE;
        arrayOfPAYLOAD_TYPE[2] = BATCHED_STANZA;
      }

      private PAYLOAD_TYPE(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static PAYLOAD_TYPE valueOf(int paramInt)
      {
        PAYLOAD_TYPE localPAYLOAD_TYPE;
        switch (paramInt)
        {
        default:
          localPAYLOAD_TYPE = null;
        case 0:
        case 1:
        case 2:
        }
        while (true)
        {
          return localPAYLOAD_TYPE;
          localPAYLOAD_TYPE = PUSH_SUBSCRIPTION;
          continue;
          localPAYLOAD_TYPE = PUSH_MESSAGE;
          continue;
          localPAYLOAD_TYPE = BATCHED_STANZA;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface MessagingPayloadOrBuilder extends MessageLiteOrBuilder
  {
    public abstract Buzz.BuzzHeader getBuzzHeader();

    public abstract ByteString getPayload();

    public abstract int getPayloadType();

    public abstract Buzz.MessagingRecipientData getRecipient(int paramInt);

    public abstract int getRecipientCount();

    public abstract List<Buzz.MessagingRecipientData> getRecipientList();

    public abstract Buzz.InternalAddress getSender();

    public abstract boolean hasBuzzHeader();

    public abstract boolean hasPayload();

    public abstract boolean hasPayloadType();

    public abstract boolean hasSender();
  }

  public static final class MessagingRecipientData extends GeneratedMessageLite
    implements Buzz.MessagingRecipientDataOrBuilder
  {
    private static final MessagingRecipientData defaultInstance;
    private static final long serialVersionUID;
    private Buzz.InternalAddress address_;
    private int bitField0_;
    private long gaiaId_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object payload_;

    static
    {
      MessagingRecipientData localMessagingRecipientData = new MessagingRecipientData();
      defaultInstance = localMessagingRecipientData;
      localMessagingRecipientData.address_ = Buzz.InternalAddress.getDefaultInstance();
      localMessagingRecipientData.gaiaId_ = 0L;
      localMessagingRecipientData.payload_ = "";
    }

    private MessagingRecipientData()
    {
    }

    private MessagingRecipientData(Builder paramBuilder)
    {
      super();
    }

    public static MessagingRecipientData getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getPayloadBytes()
    {
      Object localObject = this.payload_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.payload_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$4600();
    }

    public final Buzz.InternalAddress getAddress()
    {
      return this.address_;
    }

    public final MessagingRecipientData getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final long getGaiaId()
    {
      return this.gaiaId_;
    }

    public final String getPayload()
    {
      Object localObject1 = this.payload_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.payload_ = str;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.address_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeInt64Size(2, this.gaiaId_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getPayloadBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasAddress()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasGaiaId()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPayload()
    {
      if ((0x4 & this.bitField0_) == 4);
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
        if ((hasAddress()) && (!getAddress().isInitialized()))
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
        paramCodedOutputStream.writeMessage(1, this.address_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeInt64(2, this.gaiaId_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getPayloadBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.MessagingRecipientData, Builder>
      implements Buzz.MessagingRecipientDataOrBuilder
    {
      private Buzz.InternalAddress address_ = Buzz.InternalAddress.getDefaultInstance();
      private int bitField0_;
      private long gaiaId_;
      private Object payload_ = "";

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
            Buzz.InternalAddress.Builder localBuilder = Buzz.InternalAddress.newBuilder();
            if (hasAddress())
              localBuilder.mergeFrom(getAddress());
            paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
            setAddress(localBuilder.buildPartial());
            break;
          case 16:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.gaiaId_ = paramCodedInputStream.readInt64();
            break;
          case 26:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.payload_ = paramCodedInputStream.readBytes();
        }
      }

      public final Buzz.MessagingRecipientData build()
      {
        Buzz.MessagingRecipientData localMessagingRecipientData = buildPartial();
        if (!localMessagingRecipientData.isInitialized())
          throw new UninitializedMessageException();
        return localMessagingRecipientData;
      }

      public final Buzz.MessagingRecipientData buildPartial()
      {
        Buzz.MessagingRecipientData localMessagingRecipientData = new Buzz.MessagingRecipientData(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Buzz.MessagingRecipientData.access$4802(localMessagingRecipientData, this.address_);
        if ((i & 0x2) == 2)
          k |= 2;
        Buzz.MessagingRecipientData.access$4902(localMessagingRecipientData, this.gaiaId_);
        if ((i & 0x4) == 4)
          k |= 4;
        Buzz.MessagingRecipientData.access$5002(localMessagingRecipientData, this.payload_);
        Buzz.MessagingRecipientData.access$5102(localMessagingRecipientData, k);
        return localMessagingRecipientData;
      }

      public final Builder clear()
      {
        super.clear();
        this.address_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.gaiaId_ = 0L;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.payload_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearAddress()
      {
        this.address_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Builder clearGaiaId()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.gaiaId_ = 0L;
        return this;
      }

      public final Builder clearPayload()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.payload_ = Buzz.MessagingRecipientData.getDefaultInstance().getPayload();
        return this;
      }

      public final Buzz.InternalAddress getAddress()
      {
        return this.address_;
      }

      public final Buzz.MessagingRecipientData getDefaultInstanceForType()
      {
        return Buzz.MessagingRecipientData.getDefaultInstance();
      }

      public final long getGaiaId()
      {
        return this.gaiaId_;
      }

      public final String getPayload()
      {
        Object localObject = this.payload_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.payload_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasAddress()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasGaiaId()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasPayload()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        if ((hasAddress()) && (!getAddress().isInitialized()));
        for (boolean bool = false; ; bool = true)
          return bool;
      }

      public final Builder mergeFrom(Buzz.MessagingRecipientData paramMessagingRecipientData)
      {
        if (paramMessagingRecipientData == Buzz.MessagingRecipientData.getDefaultInstance())
          return this;
        Buzz.InternalAddress localInternalAddress;
        if (paramMessagingRecipientData.hasAddress())
        {
          localInternalAddress = paramMessagingRecipientData.getAddress();
          if (((0x1 & this.bitField0_) != 1) || (this.address_ == Buzz.InternalAddress.getDefaultInstance()))
            break label106;
        }
        label106: for (this.address_ = Buzz.InternalAddress.newBuilder(this.address_).mergeFrom(localInternalAddress).buildPartial(); ; this.address_ = localInternalAddress)
        {
          this.bitField0_ = (0x1 | this.bitField0_);
          if (paramMessagingRecipientData.hasGaiaId())
            setGaiaId(paramMessagingRecipientData.getGaiaId());
          if (!paramMessagingRecipientData.hasPayload())
            break;
          setPayload(paramMessagingRecipientData.getPayload());
          break;
        }
      }

      public final Builder setAddress(Buzz.InternalAddress.Builder paramBuilder)
      {
        this.address_ = paramBuilder.build();
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setAddress(Buzz.InternalAddress paramInternalAddress)
      {
        if (paramInternalAddress == null)
          throw new NullPointerException();
        this.address_ = paramInternalAddress;
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setGaiaId(long paramLong)
      {
        this.bitField0_ = (0x2 | this.bitField0_);
        this.gaiaId_ = paramLong;
        return this;
      }

      public final Builder setPayload(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.payload_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface MessagingRecipientDataOrBuilder extends MessageLiteOrBuilder
  {
    public abstract Buzz.InternalAddress getAddress();

    public abstract long getGaiaId();

    public abstract String getPayload();

    public abstract boolean hasAddress();

    public abstract boolean hasGaiaId();

    public abstract boolean hasPayload();
  }

  public static final class MessagingResult extends GeneratedMessageLite
    implements Buzz.MessagingResultOrBuilder
  {
    private static final MessagingResult defaultInstance = new MessagingResult();
    private static final long serialVersionUID;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    private MessagingResult()
    {
    }

    private MessagingResult(Builder paramBuilder)
    {
      super();
    }

    public static MessagingResult getDefaultInstance()
    {
      return defaultInstance;
    }

    public final MessagingResult getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      while (true)
      {
        return i;
        this.memoizedSerializedSize = 0;
        i = 0;
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
    }

    public static enum ApplicationError
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<ApplicationError> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        EXPECTED_SENDER_TYPE_NOT_FOUND = new ApplicationError("EXPECTED_SENDER_TYPE_NOT_FOUND", 2, 2);
        UNEXPECTED_SENDER_TYPE_FOUND = new ApplicationError("UNEXPECTED_SENDER_TYPE_FOUND", 3, 3);
        RECIPIENT_ERROR = new ApplicationError("RECIPIENT_ERROR", 4, 4);
        SENDER_ERROR = new ApplicationError("SENDER_ERROR", 5, 5);
        RECIPIENT_JID_ERROR = new ApplicationError("RECIPIENT_JID_ERROR", 6, 6);
        RECIPIENT_GATEWAY_ERROR = new ApplicationError("RECIPIENT_GATEWAY_ERROR", 7, 7);
        SENDER_JID_ERROR = new ApplicationError("SENDER_JID_ERROR", 8, 8);
        SENDER_GATEWAY_ERROR = new ApplicationError("SENDER_GATEWAY_ERROR", 9, 9);
        BUZZ_HEADER_ERROR = new ApplicationError("BUZZ_HEADER_ERROR", 10, 10);
        BACKEND_ERROR = new ApplicationError("BACKEND_ERROR", 11, 11);
        NON_EXISTENT_RECIPIENT = new ApplicationError("NON_EXISTENT_RECIPIENT", 12, 12);
        COMPRESSION_TYPE_NOT_SUPPORTED = new ApplicationError("COMPRESSION_TYPE_NOT_SUPPORTED", 13, 13);
        STANZA_ERROR = new ApplicationError("STANZA_ERROR", 14, 14);
        ACCESS_DENIED = new ApplicationError("ACCESS_DENIED", 15, 15);
        TASK_NOT_REACHABLE = new ApplicationError("TASK_NOT_REACHABLE", 16, 16);
        PREPICKED_SERVER_NOT_REACHABLE = new ApplicationError("PREPICKED_SERVER_NOT_REACHABLE", 17, 17);
        NOT_IMPLEMENTED = new ApplicationError("NOT_IMPLEMENTED", 18, 18);
        SERVER_OVERLOADED = new ApplicationError("SERVER_OVERLOADED", 19, 19);
        ApplicationError[] arrayOfApplicationError = new ApplicationError[20];
        arrayOfApplicationError[0] = EXPECTED_RECIPIENT_TYPE_NOT_FOUND;
        arrayOfApplicationError[1] = UNEXPECTED_RECIPIENT_TYPE_FOUND;
        arrayOfApplicationError[2] = EXPECTED_SENDER_TYPE_NOT_FOUND;
        arrayOfApplicationError[3] = UNEXPECTED_SENDER_TYPE_FOUND;
        arrayOfApplicationError[4] = RECIPIENT_ERROR;
        arrayOfApplicationError[5] = SENDER_ERROR;
        arrayOfApplicationError[6] = RECIPIENT_JID_ERROR;
        arrayOfApplicationError[7] = RECIPIENT_GATEWAY_ERROR;
        arrayOfApplicationError[8] = SENDER_JID_ERROR;
        arrayOfApplicationError[9] = SENDER_GATEWAY_ERROR;
        arrayOfApplicationError[10] = BUZZ_HEADER_ERROR;
        arrayOfApplicationError[11] = BACKEND_ERROR;
        arrayOfApplicationError[12] = NON_EXISTENT_RECIPIENT;
        arrayOfApplicationError[13] = COMPRESSION_TYPE_NOT_SUPPORTED;
        arrayOfApplicationError[14] = STANZA_ERROR;
        arrayOfApplicationError[15] = ACCESS_DENIED;
        arrayOfApplicationError[16] = TASK_NOT_REACHABLE;
        arrayOfApplicationError[17] = PREPICKED_SERVER_NOT_REACHABLE;
        arrayOfApplicationError[18] = NOT_IMPLEMENTED;
        arrayOfApplicationError[19] = SERVER_OVERLOADED;
      }

      private ApplicationError(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static ApplicationError valueOf(int paramInt)
      {
        ApplicationError localApplicationError;
        switch (paramInt)
        {
        default:
          localApplicationError = null;
        case 0:
        case 1:
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
        case 16:
        case 17:
        case 18:
        case 19:
        }
        while (true)
        {
          return localApplicationError;
          localApplicationError = EXPECTED_RECIPIENT_TYPE_NOT_FOUND;
          continue;
          localApplicationError = UNEXPECTED_RECIPIENT_TYPE_FOUND;
          continue;
          localApplicationError = EXPECTED_SENDER_TYPE_NOT_FOUND;
          continue;
          localApplicationError = UNEXPECTED_SENDER_TYPE_FOUND;
          continue;
          localApplicationError = RECIPIENT_ERROR;
          continue;
          localApplicationError = SENDER_ERROR;
          continue;
          localApplicationError = RECIPIENT_JID_ERROR;
          continue;
          localApplicationError = RECIPIENT_GATEWAY_ERROR;
          continue;
          localApplicationError = SENDER_JID_ERROR;
          continue;
          localApplicationError = SENDER_GATEWAY_ERROR;
          continue;
          localApplicationError = BUZZ_HEADER_ERROR;
          continue;
          localApplicationError = BACKEND_ERROR;
          continue;
          localApplicationError = NON_EXISTENT_RECIPIENT;
          continue;
          localApplicationError = COMPRESSION_TYPE_NOT_SUPPORTED;
          continue;
          localApplicationError = STANZA_ERROR;
          continue;
          localApplicationError = ACCESS_DENIED;
          continue;
          localApplicationError = TASK_NOT_REACHABLE;
          continue;
          localApplicationError = PREPICKED_SERVER_NOT_REACHABLE;
          continue;
          localApplicationError = NOT_IMPLEMENTED;
          continue;
          localApplicationError = SERVER_OVERLOADED;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.MessagingResult, Builder>
      implements Buzz.MessagingResultOrBuilder
    {
      private Buzz.MessagingResult buildPartial()
      {
        return new Buzz.MessagingResult(this, (byte)0);
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        int i;
        do
        {
          i = paramCodedInputStream.readTag();
          switch (i)
          {
          default:
          case 0:
          }
        }
        while (parseUnknownField(paramCodedInputStream, paramExtensionRegistryLite, i));
        return this;
      }

      public final Builder clear()
      {
        super.clear();
        return this;
      }

      public final Buzz.MessagingResult getDefaultInstanceForType()
      {
        return Buzz.MessagingResult.getDefaultInstance();
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Buzz.MessagingResult paramMessagingResult)
      {
        if (paramMessagingResult == Buzz.MessagingResult.getDefaultInstance());
        return this;
      }
    }
  }

  public static abstract interface MessagingResultOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class MessagingStanza extends GeneratedMessageLite
    implements Buzz.MessagingStanzaOrBuilder
  {
    private static final MessagingStanza defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Buzz.BuzzHeader buzzHeader_;
    private int compressionType_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Buzz.InternalAddress recipient_;
    private Buzz.InternalAddress sender_;
    private Object xmppStanza_;

    static
    {
      MessagingStanza localMessagingStanza = new MessagingStanza();
      defaultInstance = localMessagingStanza;
      localMessagingStanza.recipient_ = Buzz.InternalAddress.getDefaultInstance();
      localMessagingStanza.sender_ = Buzz.InternalAddress.getDefaultInstance();
      localMessagingStanza.xmppStanza_ = "";
      localMessagingStanza.compressionType_ = 0;
      localMessagingStanza.buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
    }

    private MessagingStanza()
    {
    }

    private MessagingStanza(Builder paramBuilder)
    {
      super();
    }

    public static MessagingStanza getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getXmppStanzaBytes()
    {
      Object localObject = this.xmppStanza_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.xmppStanza_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public final Buzz.BuzzHeader getBuzzHeader()
    {
      return this.buzzHeader_;
    }

    public final int getCompressionType()
    {
      return this.compressionType_;
    }

    public final MessagingStanza getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final Buzz.InternalAddress getRecipient()
    {
      return this.recipient_;
    }

    public final Buzz.InternalAddress getSender()
    {
      return this.sender_;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.recipient_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeMessageSize(2, this.sender_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getXmppStanzaBytes());
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeMessageSize(4, this.buzzHeader_);
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeInt32Size(5, this.compressionType_);
        this.memoizedSerializedSize = k;
      }
    }

    public final String getXmppStanza()
    {
      Object localObject1 = this.xmppStanza_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.xmppStanza_ = str;
      }
    }

    public final boolean hasBuzzHeader()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasCompressionType()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasRecipient()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasSender()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasXmppStanza()
    {
      if ((0x4 & this.bitField0_) == 4);
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
        if (!hasRecipient())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasSender())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasXmppStanza())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!getRecipient().isInitialized())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!getSender().isInitialized())
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
        paramCodedOutputStream.writeMessage(1, this.recipient_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeMessage(2, this.sender_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getXmppStanzaBytes());
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeMessage(4, this.buzzHeader_);
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeInt32(5, this.compressionType_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.MessagingStanza, Builder>
      implements Buzz.MessagingStanzaOrBuilder
    {
      private int bitField0_;
      private Buzz.BuzzHeader buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
      private int compressionType_;
      private Buzz.InternalAddress recipient_ = Buzz.InternalAddress.getDefaultInstance();
      private Buzz.InternalAddress sender_ = Buzz.InternalAddress.getDefaultInstance();
      private Object xmppStanza_ = "";

      private Buzz.MessagingStanza buildPartial()
      {
        Buzz.MessagingStanza localMessagingStanza = new Buzz.MessagingStanza(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Buzz.MessagingStanza.access$3602(localMessagingStanza, this.recipient_);
        if ((i & 0x2) == 2)
          k |= 2;
        Buzz.MessagingStanza.access$3702(localMessagingStanza, this.sender_);
        if ((i & 0x4) == 4)
          k |= 4;
        Buzz.MessagingStanza.access$3802(localMessagingStanza, this.xmppStanza_);
        if ((i & 0x8) == 8)
          k |= 8;
        Buzz.MessagingStanza.access$3902(localMessagingStanza, this.compressionType_);
        if ((i & 0x10) == 16)
          k |= 16;
        Buzz.MessagingStanza.access$4002(localMessagingStanza, this.buzzHeader_);
        Buzz.MessagingStanza.access$4102(localMessagingStanza, k);
        return localMessagingStanza;
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
            Buzz.InternalAddress.Builder localBuilder2 = Buzz.InternalAddress.newBuilder();
            if (hasRecipient())
              localBuilder2.mergeFrom(getRecipient());
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            setRecipient(localBuilder2.buildPartial());
            break;
          case 18:
            Buzz.InternalAddress.Builder localBuilder1 = Buzz.InternalAddress.newBuilder();
            if (hasSender())
              localBuilder1.mergeFrom(getSender());
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            setSender(localBuilder1.buildPartial());
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.xmppStanza_ = paramCodedInputStream.readBytes();
            break;
          case 34:
            Buzz.BuzzHeader.Builder localBuilder = Buzz.BuzzHeader.newBuilder();
            if (hasBuzzHeader())
              localBuilder.mergeFrom(getBuzzHeader());
            paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
            setBuzzHeader(localBuilder.buildPartial());
            break;
          case 40:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.compressionType_ = paramCodedInputStream.readInt32();
        }
      }

      public final Builder clear()
      {
        super.clear();
        this.recipient_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.sender_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.xmppStanza_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.compressionType_ = 0;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearBuzzHeader()
      {
        this.buzzHeader_ = Buzz.BuzzHeader.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearCompressionType()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.compressionType_ = 0;
        return this;
      }

      public final Builder clearRecipient()
      {
        this.recipient_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Builder clearSender()
      {
        this.sender_ = Buzz.InternalAddress.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearXmppStanza()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.xmppStanza_ = Buzz.MessagingStanza.getDefaultInstance().getXmppStanza();
        return this;
      }

      public final Buzz.BuzzHeader getBuzzHeader()
      {
        return this.buzzHeader_;
      }

      public final int getCompressionType()
      {
        return this.compressionType_;
      }

      public final Buzz.MessagingStanza getDefaultInstanceForType()
      {
        return Buzz.MessagingStanza.getDefaultInstance();
      }

      public final Buzz.InternalAddress getRecipient()
      {
        return this.recipient_;
      }

      public final Buzz.InternalAddress getSender()
      {
        return this.sender_;
      }

      public final String getXmppStanza()
      {
        Object localObject = this.xmppStanza_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.xmppStanza_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasBuzzHeader()
      {
        if ((0x10 & this.bitField0_) == 16);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasCompressionType()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasRecipient()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasSender()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasXmppStanza()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        boolean bool1 = hasRecipient();
        boolean bool2 = false;
        if (!bool1);
        while (true)
        {
          return bool2;
          boolean bool3 = hasSender();
          bool2 = false;
          if (bool3)
          {
            boolean bool4 = hasXmppStanza();
            bool2 = false;
            if (bool4)
            {
              boolean bool5 = getRecipient().isInitialized();
              bool2 = false;
              if (bool5)
              {
                boolean bool6 = getSender().isInitialized();
                bool2 = false;
                if (bool6)
                  bool2 = true;
              }
            }
          }
        }
      }

      public final Builder mergeFrom(Buzz.MessagingStanza paramMessagingStanza)
      {
        if (paramMessagingStanza == Buzz.MessagingStanza.getDefaultInstance());
        Buzz.InternalAddress localInternalAddress2;
        label61: Buzz.InternalAddress localInternalAddress1;
        label123: 
        do
        {
          return this;
          if (paramMessagingStanza.hasRecipient())
          {
            localInternalAddress2 = paramMessagingStanza.getRecipient();
            if (((0x1 & this.bitField0_) != 1) || (this.recipient_ == Buzz.InternalAddress.getDefaultInstance()))
              break;
            this.recipient_ = Buzz.InternalAddress.newBuilder(this.recipient_).mergeFrom(localInternalAddress2).buildPartial();
            this.bitField0_ = (0x1 | this.bitField0_);
          }
          if (paramMessagingStanza.hasSender())
          {
            localInternalAddress1 = paramMessagingStanza.getSender();
            if (((0x2 & this.bitField0_) != 2) || (this.sender_ == Buzz.InternalAddress.getDefaultInstance()))
              break label240;
            this.sender_ = Buzz.InternalAddress.newBuilder(this.sender_).mergeFrom(localInternalAddress1).buildPartial();
            this.bitField0_ = (0x2 | this.bitField0_);
          }
          if (paramMessagingStanza.hasXmppStanza())
            setXmppStanza(paramMessagingStanza.getXmppStanza());
          if (paramMessagingStanza.hasCompressionType())
            setCompressionType(paramMessagingStanza.getCompressionType());
        }
        while (!paramMessagingStanza.hasBuzzHeader());
        Buzz.BuzzHeader localBuzzHeader = paramMessagingStanza.getBuzzHeader();
        if (((0x10 & this.bitField0_) == 16) && (this.buzzHeader_ != Buzz.BuzzHeader.getDefaultInstance()));
        for (this.buzzHeader_ = Buzz.BuzzHeader.newBuilder(this.buzzHeader_).mergeFrom(localBuzzHeader).buildPartial(); ; this.buzzHeader_ = localBuzzHeader)
        {
          this.bitField0_ = (0x10 | this.bitField0_);
          break;
          this.recipient_ = localInternalAddress2;
          break label61;
          label240: this.sender_ = localInternalAddress1;
          break label123;
        }
      }

      public final Builder setBuzzHeader(Buzz.BuzzHeader.Builder paramBuilder)
      {
        this.buzzHeader_ = paramBuilder.build();
        this.bitField0_ = (0x10 | this.bitField0_);
        return this;
      }

      public final Builder setBuzzHeader(Buzz.BuzzHeader paramBuzzHeader)
      {
        if (paramBuzzHeader == null)
          throw new NullPointerException();
        this.buzzHeader_ = paramBuzzHeader;
        this.bitField0_ = (0x10 | this.bitField0_);
        return this;
      }

      public final Builder setCompressionType(int paramInt)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.compressionType_ = paramInt;
        return this;
      }

      public final Builder setRecipient(Buzz.InternalAddress.Builder paramBuilder)
      {
        this.recipient_ = paramBuilder.build();
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setRecipient(Buzz.InternalAddress paramInternalAddress)
      {
        if (paramInternalAddress == null)
          throw new NullPointerException();
        this.recipient_ = paramInternalAddress;
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setSender(Buzz.InternalAddress.Builder paramBuilder)
      {
        this.sender_ = paramBuilder.build();
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setSender(Buzz.InternalAddress paramInternalAddress)
      {
        if (paramInternalAddress == null)
          throw new NullPointerException();
        this.sender_ = paramInternalAddress;
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setXmppStanza(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.xmppStanza_ = paramString;
        return this;
      }
    }

    public static enum COMPRESSION_TYPE
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<COMPRESSION_TYPE> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        COMPRESSION_TYPE[] arrayOfCOMPRESSION_TYPE = new COMPRESSION_TYPE[2];
        arrayOfCOMPRESSION_TYPE[0] = NONE;
        arrayOfCOMPRESSION_TYPE[1] = ZIPPY;
      }

      private COMPRESSION_TYPE(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static COMPRESSION_TYPE valueOf(int paramInt)
      {
        COMPRESSION_TYPE localCOMPRESSION_TYPE;
        switch (paramInt)
        {
        default:
          localCOMPRESSION_TYPE = null;
        case 0:
        case 1:
        }
        while (true)
        {
          return localCOMPRESSION_TYPE;
          localCOMPRESSION_TYPE = NONE;
          continue;
          localCOMPRESSION_TYPE = ZIPPY;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface MessagingStanzaOrBuilder extends MessageLiteOrBuilder
  {
    public abstract Buzz.BuzzHeader getBuzzHeader();

    public abstract int getCompressionType();

    public abstract Buzz.InternalAddress getRecipient();

    public abstract Buzz.InternalAddress getSender();

    public abstract String getXmppStanza();

    public abstract boolean hasBuzzHeader();

    public abstract boolean hasCompressionType();

    public abstract boolean hasRecipient();

    public abstract boolean hasSender();

    public abstract boolean hasXmppStanza();
  }

  public static final class PushMessagePayload extends GeneratedMessageLite
    implements Buzz.PushMessagePayloadOrBuilder
  {
    private static final PushMessagePayload defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object channel_;
    private Object from_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private ByteString payload_;
    private boolean sendOnDisconnect_;

    static
    {
      PushMessagePayload localPushMessagePayload = new PushMessagePayload();
      defaultInstance = localPushMessagePayload;
      localPushMessagePayload.channel_ = "";
      localPushMessagePayload.from_ = "";
      localPushMessagePayload.payload_ = ByteString.EMPTY;
      localPushMessagePayload.sendOnDisconnect_ = false;
    }

    private PushMessagePayload()
    {
    }

    private PushMessagePayload(Builder paramBuilder)
    {
      super();
    }

    private ByteString getChannelBytes()
    {
      Object localObject = this.channel_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.channel_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static PushMessagePayload getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getFromBytes()
    {
      Object localObject = this.from_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.from_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public final String getChannel()
    {
      Object localObject1 = this.channel_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.channel_ = str;
      }
    }

    public final PushMessagePayload getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getFrom()
    {
      Object localObject1 = this.from_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.from_ = str;
      }
    }

    public final ByteString getPayload()
    {
      return this.payload_;
    }

    public final boolean getSendOnDisconnect()
    {
      return this.sendOnDisconnect_;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getChannelBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getFromBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, this.payload_);
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBoolSize(4, this.sendOnDisconnect_);
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasChannel()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasFrom()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPayload()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasSendOnDisconnect()
    {
      if ((0x8 & this.bitField0_) == 8);
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
        if (!hasChannel())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasFrom())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasPayload())
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
        paramCodedOutputStream.writeBytes(1, getChannelBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getFromBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, this.payload_);
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBool(4, this.sendOnDisconnect_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Buzz.PushMessagePayload, Builder>
      implements Buzz.PushMessagePayloadOrBuilder
    {
      private int bitField0_;
      private Object channel_ = "";
      private Object from_ = "";
      private ByteString payload_ = ByteString.EMPTY;
      private boolean sendOnDisconnect_;

      private Buzz.PushMessagePayload buildPartial()
      {
        Buzz.PushMessagePayload localPushMessagePayload = new Buzz.PushMessagePayload(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Buzz.PushMessagePayload.access$6402(localPushMessagePayload, this.channel_);
        if ((i & 0x2) == 2)
          k |= 2;
        Buzz.PushMessagePayload.access$6502(localPushMessagePayload, this.from_);
        if ((i & 0x4) == 4)
          k |= 4;
        Buzz.PushMessagePayload.access$6602(localPushMessagePayload, this.payload_);
        if ((i & 0x8) == 8)
          k |= 8;
        Buzz.PushMessagePayload.access$6702(localPushMessagePayload, this.sendOnDisconnect_);
        Buzz.PushMessagePayload.access$6802(localPushMessagePayload, k);
        return localPushMessagePayload;
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
            this.channel_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.from_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.payload_ = paramCodedInputStream.readBytes();
            break;
          case 32:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.sendOnDisconnect_ = paramCodedInputStream.readBool();
        }
      }

      public final Builder clear()
      {
        super.clear();
        this.channel_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.from_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.payload_ = ByteString.EMPTY;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.sendOnDisconnect_ = false;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearChannel()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.channel_ = Buzz.PushMessagePayload.getDefaultInstance().getChannel();
        return this;
      }

      public final Builder clearFrom()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.from_ = Buzz.PushMessagePayload.getDefaultInstance().getFrom();
        return this;
      }

      public final Builder clearPayload()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.payload_ = Buzz.PushMessagePayload.getDefaultInstance().getPayload();
        return this;
      }

      public final Builder clearSendOnDisconnect()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.sendOnDisconnect_ = false;
        return this;
      }

      public final String getChannel()
      {
        Object localObject = this.channel_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.channel_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Buzz.PushMessagePayload getDefaultInstanceForType()
      {
        return Buzz.PushMessagePayload.getDefaultInstance();
      }

      public final String getFrom()
      {
        Object localObject = this.from_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.from_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final ByteString getPayload()
      {
        return this.payload_;
      }

      public final boolean getSendOnDisconnect()
      {
        return this.sendOnDisconnect_;
      }

      public final boolean hasChannel()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasFrom()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasPayload()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasSendOnDisconnect()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        boolean bool1 = hasChannel();
        boolean bool2 = false;
        if (!bool1);
        while (true)
        {
          return bool2;
          boolean bool3 = hasFrom();
          bool2 = false;
          if (bool3)
          {
            boolean bool4 = hasPayload();
            bool2 = false;
            if (bool4)
              bool2 = true;
          }
        }
      }

      public final Builder mergeFrom(Buzz.PushMessagePayload paramPushMessagePayload)
      {
        if (paramPushMessagePayload == Buzz.PushMessagePayload.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramPushMessagePayload.hasChannel())
            setChannel(paramPushMessagePayload.getChannel());
          if (paramPushMessagePayload.hasFrom())
            setFrom(paramPushMessagePayload.getFrom());
          if (paramPushMessagePayload.hasPayload())
            setPayload(paramPushMessagePayload.getPayload());
          if (paramPushMessagePayload.hasSendOnDisconnect())
            setSendOnDisconnect(paramPushMessagePayload.getSendOnDisconnect());
        }
      }

      public final Builder setChannel(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.channel_ = paramString;
        return this;
      }

      public final Builder setFrom(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.from_ = paramString;
        return this;
      }

      public final Builder setPayload(ByteString paramByteString)
      {
        if (paramByteString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.payload_ = paramByteString;
        return this;
      }

      public final Builder setSendOnDisconnect(boolean paramBoolean)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.sendOnDisconnect_ = paramBoolean;
        return this;
      }
    }
  }

  public static abstract interface PushMessagePayloadOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getChannel();

    public abstract String getFrom();

    public abstract ByteString getPayload();

    public abstract boolean getSendOnDisconnect();

    public abstract boolean hasChannel();

    public abstract boolean hasFrom();

    public abstract boolean hasPayload();

    public abstract boolean hasSendOnDisconnect();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.realtimechat.proto.Buzz
 * JD-Core Version:    0.6.2
 */