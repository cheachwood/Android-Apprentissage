package com.google.apps.tacotown.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.GeneratedMessageLite.GeneratedExtension;
import com.google.protobuf.Internal;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnmodifiableLazyStringList;
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import proto2.bridge.MessageSet;

public final class A2a
{
  public static final class A2aData extends GeneratedMessageLite
    implements A2a.A2aDataOrBuilder
  {
    private static final A2aData defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private A2a.HangoutData hangoutData_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    static
    {
      A2aData localA2aData = new A2aData();
      defaultInstance = localA2aData;
      localA2aData.hangoutData_ = A2a.HangoutData.getDefaultInstance();
    }

    private A2aData()
    {
    }

    private A2aData(Builder paramBuilder)
    {
      super();
    }

    public static A2aData getDefaultInstance()
    {
      return defaultInstance;
    }

    public final A2a.HangoutData getHangoutData()
    {
      return this.hangoutData_;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.hangoutData_);
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasHangoutData()
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
        if ((hasHangoutData()) && (!this.hangoutData_.isInitialized()))
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
        paramCodedOutputStream.writeMessage(1, this.hangoutData_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<A2a.A2aData, Builder>
      implements A2a.A2aDataOrBuilder
    {
      private int bitField0_;
      private A2a.HangoutData hangoutData_ = A2a.HangoutData.getDefaultInstance();

      private A2a.A2aData buildPartial()
      {
        A2a.A2aData localA2aData = new A2a.A2aData(this, (byte)0);
        int i = 0x1 & this.bitField0_;
        int j = 0;
        if (i == 1)
          j = 1;
        A2a.A2aData.access$302(localA2aData, this.hangoutData_);
        A2a.A2aData.access$402(localA2aData, j);
        return localA2aData;
      }

      private Builder clear()
      {
        super.clear();
        this.hangoutData_ = A2a.HangoutData.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private boolean hasHangoutData()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
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
          }
          A2a.HangoutData.Builder localBuilder = A2a.HangoutData.newBuilder();
          if (hasHangoutData())
            localBuilder.mergeFrom(this.hangoutData_);
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          A2a.HangoutData localHangoutData = localBuilder.buildPartial();
          if (localHangoutData == null)
            throw new NullPointerException();
          this.hangoutData_ = localHangoutData;
          this.bitField0_ = (0x1 | this.bitField0_);
        }
      }

      public final boolean isInitialized()
      {
        if ((hasHangoutData()) && (!this.hangoutData_.isInitialized()));
        for (boolean bool = false; ; bool = true)
          return bool;
      }

      public final Builder mergeFrom(A2a.A2aData paramA2aData)
      {
        if (paramA2aData == A2a.A2aData.getDefaultInstance());
        while (!paramA2aData.hasHangoutData())
          return this;
        A2a.HangoutData localHangoutData = paramA2aData.getHangoutData();
        if (((0x1 & this.bitField0_) == 1) && (this.hangoutData_ != A2a.HangoutData.getDefaultInstance()));
        for (this.hangoutData_ = A2a.HangoutData.newBuilder(this.hangoutData_).mergeFrom(localHangoutData).buildPartial(); ; this.hangoutData_ = localHangoutData)
        {
          this.bitField0_ = (0x1 | this.bitField0_);
          break;
        }
      }
    }
  }

  public static abstract interface A2aDataOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class HangoutData extends GeneratedMessageLite
    implements A2a.HangoutDataOrBuilder
  {
    private static final HangoutData defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private BroadcastDetails broadcastDetails_;
    private Object broadcastId_;
    private Object broadcastTitle_;
    private Object hashedRoomId_;
    private boolean idIsAutogenerated_;
    private boolean isActive_;
    private boolean isNoMinors_;
    private boolean isViewOnly_;
    private HangoutJoinType joinType_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private LazyStringList notificationId_;
    private List<A2a.HangoutOccupant> occupantEver_;
    private List<A2a.HangoutOccupant> occupant_;
    private Object preferredLanguage_;
    private Object region_;
    private Object roomDomain_;
    private Object roomId_;
    private Object subject_;
    private boolean topicMaybeNsfw_;
    private Object topicUrl_;
    private Object topic_;
    private HangoutType type_;
    private Object url_;

    static
    {
      HangoutData localHangoutData = new HangoutData();
      defaultInstance = localHangoutData;
      localHangoutData.roomId_ = "";
      localHangoutData.url_ = "";
      localHangoutData.subject_ = "";
      localHangoutData.occupant_ = Collections.emptyList();
      localHangoutData.occupantEver_ = Collections.emptyList();
      localHangoutData.notificationId_ = LazyStringArrayList.EMPTY;
      localHangoutData.type_ = HangoutType.CONSUMER;
      localHangoutData.roomDomain_ = "";
      localHangoutData.idIsAutogenerated_ = true;
      localHangoutData.broadcastId_ = "";
      localHangoutData.broadcastTitle_ = "";
      localHangoutData.hashedRoomId_ = "";
      localHangoutData.isActive_ = false;
      localHangoutData.broadcastDetails_ = BroadcastDetails.getDefaultInstance();
      localHangoutData.isViewOnly_ = false;
      localHangoutData.topic_ = "";
      localHangoutData.topicUrl_ = "";
      localHangoutData.joinType_ = HangoutJoinType.ORIGINAL;
      localHangoutData.isNoMinors_ = false;
      localHangoutData.topicMaybeNsfw_ = false;
      localHangoutData.preferredLanguage_ = "";
      localHangoutData.region_ = "";
    }

    private HangoutData()
    {
    }

    private HangoutData(Builder paramBuilder)
    {
      super();
    }

    private ByteString getBroadcastIdBytes()
    {
      Object localObject = this.broadcastId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.broadcastId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getBroadcastTitleBytes()
    {
      Object localObject = this.broadcastTitle_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.broadcastTitle_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static HangoutData getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getHashedRoomIdBytes()
    {
      Object localObject = this.hashedRoomId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.hashedRoomId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getPreferredLanguageBytes()
    {
      Object localObject = this.preferredLanguage_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.preferredLanguage_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getRegionBytes()
    {
      Object localObject = this.region_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.region_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getRoomDomainBytes()
    {
      Object localObject = this.roomDomain_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.roomDomain_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getRoomIdBytes()
    {
      Object localObject = this.roomId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.roomId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getSubjectBytes()
    {
      Object localObject = this.subject_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.subject_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getTopicBytes()
    {
      Object localObject = this.topic_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.topic_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getTopicUrlBytes()
    {
      Object localObject = this.topicUrl_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.topicUrl_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getUrlBytes()
    {
      Object localObject = this.url_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.url_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$1300();
    }

    public static Builder newBuilder(HangoutData paramHangoutData)
    {
      return Builder.access$1300().mergeFrom(paramHangoutData);
    }

    public final BroadcastDetails getBroadcastDetails()
    {
      return this.broadcastDetails_;
    }

    @Deprecated
    public final String getBroadcastId()
    {
      Object localObject1 = this.broadcastId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.broadcastId_ = str;
      }
    }

    @Deprecated
    public final String getBroadcastTitle()
    {
      Object localObject1 = this.broadcastTitle_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.broadcastTitle_ = str;
      }
    }

    public final String getHashedRoomId()
    {
      Object localObject1 = this.hashedRoomId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.hashedRoomId_ = str;
      }
    }

    public final boolean getIdIsAutogenerated()
    {
      return this.idIsAutogenerated_;
    }

    public final boolean getIsActive()
    {
      return this.isActive_;
    }

    public final boolean getIsNoMinors()
    {
      return this.isNoMinors_;
    }

    public final boolean getIsViewOnly()
    {
      return this.isViewOnly_;
    }

    public final HangoutJoinType getJoinType()
    {
      return this.joinType_;
    }

    public final String getPreferredLanguage()
    {
      Object localObject1 = this.preferredLanguage_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.preferredLanguage_ = str;
      }
    }

    public final String getRegion()
    {
      Object localObject1 = this.region_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.region_ = str;
      }
    }

    public final String getRoomDomain()
    {
      Object localObject1 = this.roomDomain_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.roomDomain_ = str;
      }
    }

    public final String getRoomId()
    {
      Object localObject1 = this.roomId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.roomId_ = str;
      }
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int i3;
      for (int i4 = i; ; i4 = i3)
      {
        return i4;
        int j = 0x1 & this.bitField0_;
        int k = 0;
        if (j == 1)
          k = 0 + CodedOutputStream.computeBytesSize(1, getRoomIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getUrlBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getSubjectBytes());
        for (int m = 0; m < this.occupant_.size(); m++)
          k += CodedOutputStream.computeMessageSize(4, (MessageLite)this.occupant_.get(m));
        for (int n = 0; n < this.occupantEver_.size(); n++)
          k += CodedOutputStream.computeMessageSize(5, (MessageLite)this.occupantEver_.get(n));
        int i1 = 0;
        for (int i2 = 0; i2 < this.notificationId_.size(); i2++)
          i1 += CodedOutputStream.computeBytesSizeNoTag(this.notificationId_.getByteString(i2));
        i3 = k + i1 + 1 * this.notificationId_.size();
        if ((0x8 & this.bitField0_) == 8)
          i3 += CodedOutputStream.computeEnumSize(7, this.type_.getNumber());
        if ((0x10 & this.bitField0_) == 16)
          i3 += CodedOutputStream.computeBytesSize(8, getRoomDomainBytes());
        if ((0x20 & this.bitField0_) == 32)
          i3 += CodedOutputStream.computeBoolSize(9, this.idIsAutogenerated_);
        if ((0x40 & this.bitField0_) == 64)
          i3 += CodedOutputStream.computeBytesSize(10, getBroadcastIdBytes());
        if ((0x80 & this.bitField0_) == 128)
          i3 += CodedOutputStream.computeBytesSize(11, getBroadcastTitleBytes());
        if ((0x100 & this.bitField0_) == 256)
          i3 += CodedOutputStream.computeBytesSize(12, getHashedRoomIdBytes());
        if ((0x200 & this.bitField0_) == 512)
          i3 += CodedOutputStream.computeBoolSize(13, this.isActive_);
        if ((0x400 & this.bitField0_) == 1024)
          i3 += CodedOutputStream.computeMessageSize(14, this.broadcastDetails_);
        if ((0x800 & this.bitField0_) == 2048)
          i3 += CodedOutputStream.computeBoolSize(15, this.isViewOnly_);
        if ((0x1000 & this.bitField0_) == 4096)
          i3 += CodedOutputStream.computeBytesSize(16, getTopicBytes());
        if ((0x2000 & this.bitField0_) == 8192)
          i3 += CodedOutputStream.computeBytesSize(17, getTopicUrlBytes());
        if ((0x4000 & this.bitField0_) == 16384)
          i3 += CodedOutputStream.computeEnumSize(18, this.joinType_.getNumber());
        if ((0x8000 & this.bitField0_) == 32768)
          i3 += CodedOutputStream.computeBoolSize(19, this.isNoMinors_);
        if ((0x10000 & this.bitField0_) == 65536)
          i3 += CodedOutputStream.computeBoolSize(20, this.topicMaybeNsfw_);
        if ((0x20000 & this.bitField0_) == 131072)
          i3 += CodedOutputStream.computeBytesSize(21, getPreferredLanguageBytes());
        if ((0x40000 & this.bitField0_) == 262144)
          i3 += CodedOutputStream.computeBytesSize(22, getRegionBytes());
        this.memoizedSerializedSize = i3;
      }
    }

    public final String getSubject()
    {
      Object localObject1 = this.subject_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.subject_ = str;
      }
    }

    public final String getTopic()
    {
      Object localObject1 = this.topic_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.topic_ = str;
      }
    }

    public final boolean getTopicMaybeNsfw()
    {
      return this.topicMaybeNsfw_;
    }

    public final String getTopicUrl()
    {
      Object localObject1 = this.topicUrl_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.topicUrl_ = str;
      }
    }

    public final HangoutType getType()
    {
      return this.type_;
    }

    public final String getUrl()
    {
      Object localObject1 = this.url_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.url_ = str;
      }
    }

    public final boolean hasBroadcastDetails()
    {
      if ((0x400 & this.bitField0_) == 1024);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    @Deprecated
    public final boolean hasBroadcastId()
    {
      if ((0x40 & this.bitField0_) == 64);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    @Deprecated
    public final boolean hasBroadcastTitle()
    {
      if ((0x80 & this.bitField0_) == 128);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasHashedRoomId()
    {
      if ((0x100 & this.bitField0_) == 256);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasIdIsAutogenerated()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasIsActive()
    {
      if ((0x200 & this.bitField0_) == 512);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasIsNoMinors()
    {
      if ((0x8000 & this.bitField0_) == 32768);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasIsViewOnly()
    {
      if ((0x800 & this.bitField0_) == 2048);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasJoinType()
    {
      if ((0x4000 & this.bitField0_) == 16384);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPreferredLanguage()
    {
      if ((0x20000 & this.bitField0_) == 131072);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasRegion()
    {
      if ((0x40000 & this.bitField0_) == 262144);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasRoomDomain()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasRoomId()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasSubject()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasTopic()
    {
      if ((0x1000 & this.bitField0_) == 4096);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasTopicMaybeNsfw()
    {
      if ((0x10000 & this.bitField0_) == 65536);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasTopicUrl()
    {
      if ((0x2000 & this.bitField0_) == 8192);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasType()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasUrl()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean isInitialized()
    {
      int i = this.memoizedIsInitialized;
      boolean bool;
      if (i != -1)
        if (i == 1)
          bool = true;
      while (true)
      {
        return bool;
        bool = false;
        continue;
        if (!hasRoomId())
        {
          this.memoizedIsInitialized = 0;
          bool = false;
        }
        else if (!hasUrl())
        {
          this.memoizedIsInitialized = 0;
          bool = false;
        }
        else
        {
          for (int j = 0; ; j++)
          {
            if (j >= this.occupant_.size())
              break label114;
            if (!((A2a.HangoutOccupant)this.occupant_.get(j)).isInitialized())
            {
              this.memoizedIsInitialized = 0;
              bool = false;
              break;
            }
          }
          label114: for (int k = 0; ; k++)
          {
            if (k >= this.occupantEver_.size())
              break label165;
            if (!((A2a.HangoutOccupant)this.occupantEver_.get(k)).isInitialized())
            {
              this.memoizedIsInitialized = 0;
              bool = false;
              break;
            }
          }
          label165: this.memoizedIsInitialized = 1;
          bool = true;
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
        paramCodedOutputStream.writeBytes(1, getRoomIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getUrlBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getSubjectBytes());
      for (int i = 0; i < this.occupant_.size(); i++)
        paramCodedOutputStream.writeMessage(4, (MessageLite)this.occupant_.get(i));
      for (int j = 0; j < this.occupantEver_.size(); j++)
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.occupantEver_.get(j));
      for (int k = 0; k < this.notificationId_.size(); k++)
        paramCodedOutputStream.writeBytes(6, this.notificationId_.getByteString(k));
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeEnum(7, this.type_.getNumber());
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeBytes(8, getRoomDomainBytes());
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeBool(9, this.idIsAutogenerated_);
      if ((0x40 & this.bitField0_) == 64)
        paramCodedOutputStream.writeBytes(10, getBroadcastIdBytes());
      if ((0x80 & this.bitField0_) == 128)
        paramCodedOutputStream.writeBytes(11, getBroadcastTitleBytes());
      if ((0x100 & this.bitField0_) == 256)
        paramCodedOutputStream.writeBytes(12, getHashedRoomIdBytes());
      if ((0x200 & this.bitField0_) == 512)
        paramCodedOutputStream.writeBool(13, this.isActive_);
      if ((0x400 & this.bitField0_) == 1024)
        paramCodedOutputStream.writeMessage(14, this.broadcastDetails_);
      if ((0x800 & this.bitField0_) == 2048)
        paramCodedOutputStream.writeBool(15, this.isViewOnly_);
      if ((0x1000 & this.bitField0_) == 4096)
        paramCodedOutputStream.writeBytes(16, getTopicBytes());
      if ((0x2000 & this.bitField0_) == 8192)
        paramCodedOutputStream.writeBytes(17, getTopicUrlBytes());
      if ((0x4000 & this.bitField0_) == 16384)
        paramCodedOutputStream.writeEnum(18, this.joinType_.getNumber());
      if ((0x8000 & this.bitField0_) == 32768)
        paramCodedOutputStream.writeBool(19, this.isNoMinors_);
      if ((0x10000 & this.bitField0_) == 65536)
        paramCodedOutputStream.writeBool(20, this.topicMaybeNsfw_);
      if ((0x20000 & this.bitField0_) == 131072)
        paramCodedOutputStream.writeBytes(21, getPreferredLanguageBytes());
      if ((0x40000 & this.bitField0_) == 262144)
        paramCodedOutputStream.writeBytes(22, getRegionBytes());
    }

    public static final class BroadcastDetails extends GeneratedMessageLite
      implements A2a.HangoutData.BroadcastDetailsOrBuilder
    {
      private static final BroadcastDetails defaultInstance;
      private static final long serialVersionUID;
      private int bitField0_;
      private Object broadcastTitle_;
      private Object broadcastUrl_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private Object youtubeLiveId_;

      static
      {
        BroadcastDetails localBroadcastDetails = new BroadcastDetails();
        defaultInstance = localBroadcastDetails;
        localBroadcastDetails.youtubeLiveId_ = "";
        localBroadcastDetails.broadcastTitle_ = "";
        localBroadcastDetails.broadcastUrl_ = "";
      }

      private BroadcastDetails()
      {
      }

      private BroadcastDetails(Builder paramBuilder)
      {
        super();
      }

      private ByteString getBroadcastTitleBytes()
      {
        Object localObject = this.broadcastTitle_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.broadcastTitle_ = localByteString;
        }
        while (true)
        {
          return localByteString;
          localByteString = (ByteString)localObject;
        }
      }

      private ByteString getBroadcastUrlBytes()
      {
        Object localObject = this.broadcastUrl_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.broadcastUrl_ = localByteString;
        }
        while (true)
        {
          return localByteString;
          localByteString = (ByteString)localObject;
        }
      }

      public static BroadcastDetails getDefaultInstance()
      {
        return defaultInstance;
      }

      private ByteString getYoutubeLiveIdBytes()
      {
        Object localObject = this.youtubeLiveId_;
        ByteString localByteString;
        if ((localObject instanceof String))
        {
          localByteString = ByteString.copyFromUtf8((String)localObject);
          this.youtubeLiveId_ = localByteString;
        }
        while (true)
        {
          return localByteString;
          localByteString = (ByteString)localObject;
        }
      }

      public static Builder newBuilder()
      {
        return Builder.access$600();
      }

      public static Builder newBuilder(BroadcastDetails paramBroadcastDetails)
      {
        return Builder.access$600().mergeFrom(paramBroadcastDetails);
      }

      @Deprecated
      public final String getBroadcastTitle()
      {
        Object localObject1 = this.broadcastTitle_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.broadcastTitle_ = str;
        }
      }

      public final String getBroadcastUrl()
      {
        Object localObject1 = this.broadcastUrl_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.broadcastUrl_ = str;
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
            k = 0 + CodedOutputStream.computeBytesSize(1, getYoutubeLiveIdBytes());
          if ((0x2 & this.bitField0_) == 2)
            k += CodedOutputStream.computeBytesSize(2, getBroadcastTitleBytes());
          if ((0x4 & this.bitField0_) == 4)
            k += CodedOutputStream.computeBytesSize(3, getBroadcastUrlBytes());
          this.memoizedSerializedSize = k;
        }
      }

      public final String getYoutubeLiveId()
      {
        Object localObject1 = this.youtubeLiveId_;
        if ((localObject1 instanceof String));
        String str;
        for (Object localObject2 = (String)localObject1; ; localObject2 = str)
        {
          return localObject2;
          ByteString localByteString = (ByteString)localObject1;
          str = localByteString.toStringUtf8();
          if (Internal.isValidUtf8(localByteString))
            this.youtubeLiveId_ = str;
        }
      }

      @Deprecated
      public final boolean hasBroadcastTitle()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasBroadcastUrl()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasYoutubeLiveId()
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
          paramCodedOutputStream.writeBytes(1, getYoutubeLiveIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          paramCodedOutputStream.writeBytes(2, getBroadcastTitleBytes());
        if ((0x4 & this.bitField0_) == 4)
          paramCodedOutputStream.writeBytes(3, getBroadcastUrlBytes());
      }

      public static final class Builder extends GeneratedMessageLite.Builder<A2a.HangoutData.BroadcastDetails, Builder>
        implements A2a.HangoutData.BroadcastDetailsOrBuilder
      {
        private int bitField0_;
        private Object broadcastTitle_ = "";
        private Object broadcastUrl_ = "";
        private Object youtubeLiveId_ = "";

        private Builder clear()
        {
          super.clear();
          this.youtubeLiveId_ = "";
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.broadcastTitle_ = "";
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          this.broadcastUrl_ = "";
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
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
              this.youtubeLiveId_ = paramCodedInputStream.readBytes();
              break;
            case 18:
              this.bitField0_ = (0x2 | this.bitField0_);
              this.broadcastTitle_ = paramCodedInputStream.readBytes();
              break;
            case 26:
            }
            this.bitField0_ = (0x4 | this.bitField0_);
            this.broadcastUrl_ = paramCodedInputStream.readBytes();
          }
        }

        public final A2a.HangoutData.BroadcastDetails buildPartial()
        {
          A2a.HangoutData.BroadcastDetails localBroadcastDetails = new A2a.HangoutData.BroadcastDetails(this, (byte)0);
          int i = this.bitField0_;
          int j = i & 0x1;
          int k = 0;
          if (j == 1)
            k = 1;
          A2a.HangoutData.BroadcastDetails.access$802(localBroadcastDetails, this.youtubeLiveId_);
          if ((i & 0x2) == 2)
            k |= 2;
          A2a.HangoutData.BroadcastDetails.access$902(localBroadcastDetails, this.broadcastTitle_);
          if ((i & 0x4) == 4)
            k |= 4;
          A2a.HangoutData.BroadcastDetails.access$1002(localBroadcastDetails, this.broadcastUrl_);
          A2a.HangoutData.BroadcastDetails.access$1102(localBroadcastDetails, k);
          return localBroadcastDetails;
        }

        public final boolean isInitialized()
        {
          return true;
        }

        public final Builder mergeFrom(A2a.HangoutData.BroadcastDetails paramBroadcastDetails)
        {
          if (paramBroadcastDetails == A2a.HangoutData.BroadcastDetails.getDefaultInstance());
          while (true)
          {
            return this;
            if (paramBroadcastDetails.hasYoutubeLiveId())
            {
              String str3 = paramBroadcastDetails.getYoutubeLiveId();
              if (str3 == null)
                throw new NullPointerException();
              this.bitField0_ = (0x1 | this.bitField0_);
              this.youtubeLiveId_ = str3;
            }
            if (paramBroadcastDetails.hasBroadcastTitle())
            {
              String str2 = paramBroadcastDetails.getBroadcastTitle();
              if (str2 == null)
                throw new NullPointerException();
              this.bitField0_ = (0x2 | this.bitField0_);
              this.broadcastTitle_ = str2;
            }
            if (paramBroadcastDetails.hasBroadcastUrl())
            {
              String str1 = paramBroadcastDetails.getBroadcastUrl();
              if (str1 == null)
                throw new NullPointerException();
              this.bitField0_ = (0x4 | this.bitField0_);
              this.broadcastUrl_ = str1;
            }
          }
        }
      }
    }

    public static abstract interface BroadcastDetailsOrBuilder extends MessageLiteOrBuilder
    {
    }

    public static final class Builder extends GeneratedMessageLite.Builder<A2a.HangoutData, Builder>
      implements A2a.HangoutDataOrBuilder
    {
      private int bitField0_;
      private A2a.HangoutData.BroadcastDetails broadcastDetails_ = A2a.HangoutData.BroadcastDetails.getDefaultInstance();
      private Object broadcastId_ = "";
      private Object broadcastTitle_ = "";
      private Object hashedRoomId_ = "";
      private boolean idIsAutogenerated_ = true;
      private boolean isActive_;
      private boolean isNoMinors_;
      private boolean isViewOnly_;
      private A2a.HangoutData.HangoutJoinType joinType_ = A2a.HangoutData.HangoutJoinType.ORIGINAL;
      private LazyStringList notificationId_ = LazyStringArrayList.EMPTY;
      private List<A2a.HangoutOccupant> occupantEver_ = Collections.emptyList();
      private List<A2a.HangoutOccupant> occupant_ = Collections.emptyList();
      private Object preferredLanguage_ = "";
      private Object region_ = "";
      private Object roomDomain_ = "";
      private Object roomId_ = "";
      private Object subject_ = "";
      private boolean topicMaybeNsfw_;
      private Object topicUrl_ = "";
      private Object topic_ = "";
      private A2a.HangoutData.HangoutType type_ = A2a.HangoutData.HangoutType.CONSUMER;
      private Object url_ = "";

      private Builder clear()
      {
        super.clear();
        this.roomId_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.url_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.subject_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.occupant_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.occupantEver_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.notificationId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.type_ = A2a.HangoutData.HangoutType.CONSUMER;
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.roomDomain_ = "";
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        this.idIsAutogenerated_ = true;
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        this.broadcastId_ = "";
        this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
        this.broadcastTitle_ = "";
        this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
        this.hashedRoomId_ = "";
        this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
        this.isActive_ = false;
        this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
        this.broadcastDetails_ = A2a.HangoutData.BroadcastDetails.getDefaultInstance();
        this.bitField0_ = (0xFFFFDFFF & this.bitField0_);
        this.isViewOnly_ = false;
        this.bitField0_ = (0xFFFFBFFF & this.bitField0_);
        this.topic_ = "";
        this.bitField0_ = (0xFFFF7FFF & this.bitField0_);
        this.topicUrl_ = "";
        this.bitField0_ = (0xFFFEFFFF & this.bitField0_);
        this.joinType_ = A2a.HangoutData.HangoutJoinType.ORIGINAL;
        this.bitField0_ = (0xFFFDFFFF & this.bitField0_);
        this.isNoMinors_ = false;
        this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
        this.topicMaybeNsfw_ = false;
        this.bitField0_ = (0xFFF7FFFF & this.bitField0_);
        this.preferredLanguage_ = "";
        this.bitField0_ = (0xFFEFFFFF & this.bitField0_);
        this.region_ = "";
        this.bitField0_ = (0xFFDFFFFF & this.bitField0_);
        return this;
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureNotificationIdIsMutable()
      {
        if ((0x20 & this.bitField0_) != 32)
        {
          this.notificationId_ = new LazyStringArrayList(this.notificationId_);
          this.bitField0_ = (0x20 | this.bitField0_);
        }
      }

      private void ensureOccupantEverIsMutable()
      {
        if ((0x10 & this.bitField0_) != 16)
        {
          this.occupantEver_ = new ArrayList(this.occupantEver_);
          this.bitField0_ = (0x10 | this.bitField0_);
        }
      }

      private void ensureOccupantIsMutable()
      {
        if ((0x8 & this.bitField0_) != 8)
        {
          this.occupant_ = new ArrayList(this.occupant_);
          this.bitField0_ = (0x8 | this.bitField0_);
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
            this.bitField0_ = (0x1 | this.bitField0_);
            this.roomId_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.url_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.subject_ = paramCodedInputStream.readBytes();
            break;
          case 34:
            A2a.HangoutOccupant.Builder localBuilder2 = A2a.HangoutOccupant.newBuilder();
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            A2a.HangoutOccupant localHangoutOccupant2 = localBuilder2.buildPartial();
            if (localHangoutOccupant2 == null)
              throw new NullPointerException();
            ensureOccupantIsMutable();
            this.occupant_.add(localHangoutOccupant2);
            break;
          case 42:
            A2a.HangoutOccupant.Builder localBuilder1 = A2a.HangoutOccupant.newBuilder();
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            A2a.HangoutOccupant localHangoutOccupant1 = localBuilder1.buildPartial();
            if (localHangoutOccupant1 == null)
              throw new NullPointerException();
            ensureOccupantEverIsMutable();
            this.occupantEver_.add(localHangoutOccupant1);
            break;
          case 50:
            ensureNotificationIdIsMutable();
            this.notificationId_.add(paramCodedInputStream.readBytes());
            break;
          case 56:
            A2a.HangoutData.HangoutType localHangoutType = A2a.HangoutData.HangoutType.valueOf(paramCodedInputStream.readEnum());
            if (localHangoutType == null)
              continue;
            this.bitField0_ = (0x40 | this.bitField0_);
            this.type_ = localHangoutType;
            break;
          case 66:
            this.bitField0_ = (0x80 | this.bitField0_);
            this.roomDomain_ = paramCodedInputStream.readBytes();
            break;
          case 72:
            this.bitField0_ = (0x100 | this.bitField0_);
            this.idIsAutogenerated_ = paramCodedInputStream.readBool();
            break;
          case 82:
            this.bitField0_ = (0x200 | this.bitField0_);
            this.broadcastId_ = paramCodedInputStream.readBytes();
            break;
          case 90:
            this.bitField0_ = (0x400 | this.bitField0_);
            this.broadcastTitle_ = paramCodedInputStream.readBytes();
            break;
          case 98:
            this.bitField0_ = (0x800 | this.bitField0_);
            this.hashedRoomId_ = paramCodedInputStream.readBytes();
            break;
          case 104:
            this.bitField0_ = (0x1000 | this.bitField0_);
            this.isActive_ = paramCodedInputStream.readBool();
            break;
          case 114:
            A2a.HangoutData.BroadcastDetails.Builder localBuilder = A2a.HangoutData.BroadcastDetails.newBuilder();
            if ((0x2000 & this.bitField0_) == 8192);
            A2a.HangoutData.BroadcastDetails localBroadcastDetails;
            for (int j = 1; ; j = 0)
            {
              if (j != 0)
                localBuilder.mergeFrom(this.broadcastDetails_);
              paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
              localBroadcastDetails = localBuilder.buildPartial();
              if (localBroadcastDetails != null)
                break;
              throw new NullPointerException();
            }
            this.broadcastDetails_ = localBroadcastDetails;
            this.bitField0_ = (0x2000 | this.bitField0_);
            break;
          case 120:
            this.bitField0_ = (0x4000 | this.bitField0_);
            this.isViewOnly_ = paramCodedInputStream.readBool();
            break;
          case 130:
            this.bitField0_ = (0x8000 | this.bitField0_);
            this.topic_ = paramCodedInputStream.readBytes();
            break;
          case 138:
            this.bitField0_ = (0x10000 | this.bitField0_);
            this.topicUrl_ = paramCodedInputStream.readBytes();
            break;
          case 144:
            A2a.HangoutData.HangoutJoinType localHangoutJoinType = A2a.HangoutData.HangoutJoinType.valueOf(paramCodedInputStream.readEnum());
            if (localHangoutJoinType == null)
              continue;
            this.bitField0_ = (0x20000 | this.bitField0_);
            this.joinType_ = localHangoutJoinType;
            break;
          case 152:
            this.bitField0_ = (0x40000 | this.bitField0_);
            this.isNoMinors_ = paramCodedInputStream.readBool();
            break;
          case 160:
            this.bitField0_ = (0x80000 | this.bitField0_);
            this.topicMaybeNsfw_ = paramCodedInputStream.readBool();
            break;
          case 170:
            this.bitField0_ = (0x100000 | this.bitField0_);
            this.preferredLanguage_ = paramCodedInputStream.readBytes();
            break;
          case 178:
          }
          this.bitField0_ = (0x200000 | this.bitField0_);
          this.region_ = paramCodedInputStream.readBytes();
        }
      }

      public final A2a.HangoutData build()
      {
        A2a.HangoutData localHangoutData = buildPartial();
        if (!localHangoutData.isInitialized())
          throw new UninitializedMessageException();
        return localHangoutData;
      }

      public final A2a.HangoutData buildPartial()
      {
        A2a.HangoutData localHangoutData = new A2a.HangoutData(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        A2a.HangoutData.access$1502(localHangoutData, this.roomId_);
        if ((i & 0x2) == 2)
          k |= 2;
        A2a.HangoutData.access$1602(localHangoutData, this.url_);
        if ((i & 0x4) == 4)
          k |= 4;
        A2a.HangoutData.access$1702(localHangoutData, this.subject_);
        if ((0x8 & this.bitField0_) == 8)
        {
          this.occupant_ = Collections.unmodifiableList(this.occupant_);
          this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        }
        A2a.HangoutData.access$1802(localHangoutData, this.occupant_);
        if ((0x10 & this.bitField0_) == 16)
        {
          this.occupantEver_ = Collections.unmodifiableList(this.occupantEver_);
          this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        }
        A2a.HangoutData.access$1902(localHangoutData, this.occupantEver_);
        if ((0x20 & this.bitField0_) == 32)
        {
          this.notificationId_ = new UnmodifiableLazyStringList(this.notificationId_);
          this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        }
        A2a.HangoutData.access$2002(localHangoutData, this.notificationId_);
        if ((i & 0x40) == 64)
          k |= 8;
        A2a.HangoutData.access$2102(localHangoutData, this.type_);
        if ((i & 0x80) == 128)
          k |= 16;
        A2a.HangoutData.access$2202(localHangoutData, this.roomDomain_);
        if ((i & 0x100) == 256)
          k |= 32;
        A2a.HangoutData.access$2302(localHangoutData, this.idIsAutogenerated_);
        if ((i & 0x200) == 512)
          k |= 64;
        A2a.HangoutData.access$2402(localHangoutData, this.broadcastId_);
        if ((i & 0x400) == 1024)
          k |= 128;
        A2a.HangoutData.access$2502(localHangoutData, this.broadcastTitle_);
        if ((i & 0x800) == 2048)
          k |= 256;
        A2a.HangoutData.access$2602(localHangoutData, this.hashedRoomId_);
        if ((i & 0x1000) == 4096)
          k |= 512;
        A2a.HangoutData.access$2702(localHangoutData, this.isActive_);
        if ((i & 0x2000) == 8192)
          k |= 1024;
        A2a.HangoutData.access$2802(localHangoutData, this.broadcastDetails_);
        if ((i & 0x4000) == 16384)
          k |= 2048;
        A2a.HangoutData.access$2902(localHangoutData, this.isViewOnly_);
        if ((i & 0x8000) == 32768)
          k |= 4096;
        A2a.HangoutData.access$3002(localHangoutData, this.topic_);
        if ((i & 0x10000) == 65536)
          k |= 8192;
        A2a.HangoutData.access$3102(localHangoutData, this.topicUrl_);
        if ((i & 0x20000) == 131072)
          k |= 16384;
        A2a.HangoutData.access$3202(localHangoutData, this.joinType_);
        if ((i & 0x40000) == 262144)
          k |= 32768;
        A2a.HangoutData.access$3302(localHangoutData, this.isNoMinors_);
        if ((i & 0x80000) == 524288)
          k |= 65536;
        A2a.HangoutData.access$3402(localHangoutData, this.topicMaybeNsfw_);
        if ((0x100000 & i) == 1048576)
          k |= 131072;
        A2a.HangoutData.access$3502(localHangoutData, this.preferredLanguage_);
        if ((0x200000 & i) == 2097152)
          k |= 262144;
        A2a.HangoutData.access$3602(localHangoutData, this.region_);
        A2a.HangoutData.access$3702(localHangoutData, k);
        return localHangoutData;
      }

      public final boolean isInitialized()
      {
        int i;
        boolean bool;
        if ((0x1 & this.bitField0_) == 1)
        {
          i = 1;
          if (i != 0)
            break label25;
          bool = false;
        }
        while (true)
        {
          return bool;
          i = 0;
          break;
          label25: if ((0x2 & this.bitField0_) == 2);
          for (int j = 1; ; j = 0)
          {
            if (j != 0)
              break label51;
            bool = false;
            break;
          }
          label51: for (int k = 0; ; k++)
          {
            if (k >= this.occupant_.size())
              break label99;
            if (!((A2a.HangoutOccupant)this.occupant_.get(k)).isInitialized())
            {
              bool = false;
              break;
            }
          }
          label99: for (int m = 0; ; m++)
          {
            if (m >= this.occupantEver_.size())
              break label147;
            if (!((A2a.HangoutOccupant)this.occupantEver_.get(m)).isInitialized())
            {
              bool = false;
              break;
            }
          }
          label147: bool = true;
        }
      }

      public final Builder mergeFrom(A2a.HangoutData paramHangoutData)
      {
        if (paramHangoutData == A2a.HangoutData.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramHangoutData.hasRoomId())
          {
            String str11 = paramHangoutData.getRoomId();
            if (str11 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.roomId_ = str11;
          }
          if (paramHangoutData.hasUrl())
          {
            String str10 = paramHangoutData.getUrl();
            if (str10 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.url_ = str10;
          }
          if (paramHangoutData.hasSubject())
          {
            String str9 = paramHangoutData.getSubject();
            if (str9 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.subject_ = str9;
          }
          if (!paramHangoutData.occupant_.isEmpty())
          {
            if (this.occupant_.isEmpty())
            {
              this.occupant_ = paramHangoutData.occupant_;
              this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
            }
          }
          else
          {
            if (!paramHangoutData.occupantEver_.isEmpty())
            {
              if (!this.occupantEver_.isEmpty())
                break label311;
              this.occupantEver_ = paramHangoutData.occupantEver_;
              this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
            }
            label221: if (!paramHangoutData.notificationId_.isEmpty())
            {
              if (!this.notificationId_.isEmpty())
                break label332;
              this.notificationId_ = paramHangoutData.notificationId_;
              this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
            }
          }
          while (true)
            if (paramHangoutData.hasType())
            {
              A2a.HangoutData.HangoutType localHangoutType = paramHangoutData.getType();
              if (localHangoutType == null)
              {
                throw new NullPointerException();
                ensureOccupantIsMutable();
                this.occupant_.addAll(paramHangoutData.occupant_);
                break;
                label311: ensureOccupantEverIsMutable();
                this.occupantEver_.addAll(paramHangoutData.occupantEver_);
                break label221;
                label332: ensureNotificationIdIsMutable();
                this.notificationId_.addAll(paramHangoutData.notificationId_);
                continue;
              }
              this.bitField0_ = (0x40 | this.bitField0_);
              this.type_ = localHangoutType;
            }
          if (paramHangoutData.hasRoomDomain())
          {
            String str8 = paramHangoutData.getRoomDomain();
            if (str8 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x80 | this.bitField0_);
            this.roomDomain_ = str8;
          }
          if (paramHangoutData.hasIdIsAutogenerated())
          {
            boolean bool5 = paramHangoutData.getIdIsAutogenerated();
            this.bitField0_ = (0x100 | this.bitField0_);
            this.idIsAutogenerated_ = bool5;
          }
          if (paramHangoutData.hasBroadcastId())
          {
            String str7 = paramHangoutData.getBroadcastId();
            if (str7 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x200 | this.bitField0_);
            this.broadcastId_ = str7;
          }
          if (paramHangoutData.hasBroadcastTitle())
          {
            String str6 = paramHangoutData.getBroadcastTitle();
            if (str6 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x400 | this.bitField0_);
            this.broadcastTitle_ = str6;
          }
          if (paramHangoutData.hasHashedRoomId())
          {
            String str5 = paramHangoutData.getHashedRoomId();
            if (str5 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x800 | this.bitField0_);
            this.hashedRoomId_ = str5;
          }
          if (paramHangoutData.hasIsActive())
          {
            boolean bool4 = paramHangoutData.getIsActive();
            this.bitField0_ = (0x1000 | this.bitField0_);
            this.isActive_ = bool4;
          }
          A2a.HangoutData.BroadcastDetails localBroadcastDetails;
          if (paramHangoutData.hasBroadcastDetails())
          {
            localBroadcastDetails = paramHangoutData.getBroadcastDetails();
            if (((0x2000 & this.bitField0_) != 8192) || (this.broadcastDetails_ == A2a.HangoutData.BroadcastDetails.getDefaultInstance()))
              break label733;
          }
          String str4;
          label733: for (this.broadcastDetails_ = A2a.HangoutData.BroadcastDetails.newBuilder(this.broadcastDetails_).mergeFrom(localBroadcastDetails).buildPartial(); ; this.broadcastDetails_ = localBroadcastDetails)
          {
            this.bitField0_ = (0x2000 | this.bitField0_);
            if (paramHangoutData.hasIsViewOnly())
            {
              boolean bool3 = paramHangoutData.getIsViewOnly();
              this.bitField0_ = (0x4000 | this.bitField0_);
              this.isViewOnly_ = bool3;
            }
            if (!paramHangoutData.hasTopic())
              break label759;
            str4 = paramHangoutData.getTopic();
            if (str4 != null)
              break;
            throw new NullPointerException();
          }
          this.bitField0_ = (0x8000 | this.bitField0_);
          this.topic_ = str4;
          label759: if (paramHangoutData.hasTopicUrl())
          {
            String str3 = paramHangoutData.getTopicUrl();
            if (str3 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x10000 | this.bitField0_);
            this.topicUrl_ = str3;
          }
          if (paramHangoutData.hasJoinType())
          {
            A2a.HangoutData.HangoutJoinType localHangoutJoinType = paramHangoutData.getJoinType();
            if (localHangoutJoinType == null)
              throw new NullPointerException();
            this.bitField0_ = (0x20000 | this.bitField0_);
            this.joinType_ = localHangoutJoinType;
          }
          if (paramHangoutData.hasIsNoMinors())
          {
            boolean bool2 = paramHangoutData.getIsNoMinors();
            this.bitField0_ = (0x40000 | this.bitField0_);
            this.isNoMinors_ = bool2;
          }
          if (paramHangoutData.hasTopicMaybeNsfw())
          {
            boolean bool1 = paramHangoutData.getTopicMaybeNsfw();
            this.bitField0_ = (0x80000 | this.bitField0_);
            this.topicMaybeNsfw_ = bool1;
          }
          if (paramHangoutData.hasPreferredLanguage())
          {
            String str2 = paramHangoutData.getPreferredLanguage();
            if (str2 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x100000 | this.bitField0_);
            this.preferredLanguage_ = str2;
          }
          if (paramHangoutData.hasRegion())
          {
            String str1 = paramHangoutData.getRegion();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x200000 | this.bitField0_);
            this.region_ = str1;
          }
        }
      }
    }

    public static enum HangoutJoinType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<HangoutJoinType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        HangoutJoinType[] arrayOfHangoutJoinType = new HangoutJoinType[2];
        arrayOfHangoutJoinType[0] = ORIGINAL;
        arrayOfHangoutJoinType[1] = WITH_UNIQUE_ID;
      }

      private HangoutJoinType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static HangoutJoinType valueOf(int paramInt)
      {
        HangoutJoinType localHangoutJoinType;
        switch (paramInt)
        {
        default:
          localHangoutJoinType = null;
        case 0:
        case 1:
        }
        while (true)
        {
          return localHangoutJoinType;
          localHangoutJoinType = ORIGINAL;
          continue;
          localHangoutJoinType = WITH_UNIQUE_ID;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static enum HangoutType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<HangoutType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        BUSINESS = new HangoutType("BUSINESS", 1, 1);
        OPEN_BROADCAST = new HangoutType("OPEN_BROADCAST", 2, 2);
        BROADCAST = new HangoutType("BROADCAST", 3, 3);
        HangoutType[] arrayOfHangoutType = new HangoutType[4];
        arrayOfHangoutType[0] = CONSUMER;
        arrayOfHangoutType[1] = BUSINESS;
        arrayOfHangoutType[2] = OPEN_BROADCAST;
        arrayOfHangoutType[3] = BROADCAST;
      }

      private HangoutType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static HangoutType valueOf(int paramInt)
      {
        HangoutType localHangoutType;
        switch (paramInt)
        {
        default:
          localHangoutType = null;
        case 0:
        case 1:
        case 2:
        case 3:
        }
        while (true)
        {
          return localHangoutType;
          localHangoutType = CONSUMER;
          continue;
          localHangoutType = BUSINESS;
          continue;
          localHangoutType = OPEN_BROADCAST;
          continue;
          localHangoutType = BROADCAST;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface HangoutDataOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class HangoutOccupant extends GeneratedMessageLite
    implements A2a.HangoutOccupantOrBuilder
  {
    private static final HangoutOccupant defaultInstance;
    private static final long serialVersionUID;
    private Object avatarUrl_;
    private int bitField0_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object name_;
    private Object obfuscatedGaiaId_;

    static
    {
      HangoutOccupant localHangoutOccupant = new HangoutOccupant();
      defaultInstance = localHangoutOccupant;
      localHangoutOccupant.name_ = "";
      localHangoutOccupant.avatarUrl_ = "";
      localHangoutOccupant.obfuscatedGaiaId_ = "";
    }

    private HangoutOccupant()
    {
    }

    private HangoutOccupant(Builder paramBuilder)
    {
      super();
    }

    private ByteString getAvatarUrlBytes()
    {
      Object localObject = this.avatarUrl_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.avatarUrl_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static HangoutOccupant getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getNameBytes()
    {
      Object localObject = this.name_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.name_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getObfuscatedGaiaIdBytes()
    {
      Object localObject = this.obfuscatedGaiaId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.obfuscatedGaiaId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$3900();
    }

    public final String getAvatarUrl()
    {
      Object localObject1 = this.avatarUrl_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.avatarUrl_ = str;
      }
    }

    public final String getName()
    {
      Object localObject1 = this.name_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.name_ = str;
      }
    }

    public final String getObfuscatedGaiaId()
    {
      Object localObject1 = this.obfuscatedGaiaId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.obfuscatedGaiaId_ = str;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getAvatarUrlBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getObfuscatedGaiaIdBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasAvatarUrl()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasName()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasObfuscatedGaiaId()
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
        if (!hasName())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasAvatarUrl())
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
        paramCodedOutputStream.writeBytes(1, getNameBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getAvatarUrlBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getObfuscatedGaiaIdBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<A2a.HangoutOccupant, Builder>
      implements A2a.HangoutOccupantOrBuilder
    {
      private Object avatarUrl_ = "";
      private int bitField0_;
      private Object name_ = "";
      private Object obfuscatedGaiaId_ = "";

      private Builder clear()
      {
        super.clear();
        this.name_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.avatarUrl_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.obfuscatedGaiaId_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
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
            this.name_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.avatarUrl_ = paramCodedInputStream.readBytes();
            break;
          case 26:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.obfuscatedGaiaId_ = paramCodedInputStream.readBytes();
        }
      }

      public final A2a.HangoutOccupant buildPartial()
      {
        A2a.HangoutOccupant localHangoutOccupant = new A2a.HangoutOccupant(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        A2a.HangoutOccupant.access$4102(localHangoutOccupant, this.name_);
        if ((i & 0x2) == 2)
          k |= 2;
        A2a.HangoutOccupant.access$4202(localHangoutOccupant, this.avatarUrl_);
        if ((i & 0x4) == 4)
          k |= 4;
        A2a.HangoutOccupant.access$4302(localHangoutOccupant, this.obfuscatedGaiaId_);
        A2a.HangoutOccupant.access$4402(localHangoutOccupant, k);
        return localHangoutOccupant;
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

      public final Builder mergeFrom(A2a.HangoutOccupant paramHangoutOccupant)
      {
        if (paramHangoutOccupant == A2a.HangoutOccupant.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramHangoutOccupant.hasName())
          {
            String str3 = paramHangoutOccupant.getName();
            if (str3 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.name_ = str3;
          }
          if (paramHangoutOccupant.hasAvatarUrl())
          {
            String str2 = paramHangoutOccupant.getAvatarUrl();
            if (str2 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.avatarUrl_ = str2;
          }
          if (paramHangoutOccupant.hasObfuscatedGaiaId())
          {
            String str1 = paramHangoutOccupant.getObfuscatedGaiaId();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.obfuscatedGaiaId_ = str1;
          }
        }
      }
    }
  }

  public static abstract interface HangoutOccupantOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class HangoutOnPostData extends GeneratedMessageLite
    implements A2a.HangoutOnPostDataOrBuilder
  {
    private static final HangoutOnPostData defaultInstance;
    public static final GeneratedMessageLite.GeneratedExtension<MessageSet, HangoutOnPostData> messageSetExtension = GeneratedMessageLite.newSingularGeneratedExtension(MessageSet.getDefaultInstance(), defaultInstance, defaultInstance, null, 27309818, WireFormat.FieldType.MESSAGE);
    private static final long serialVersionUID;
    private int bitField0_;
    private boolean isActive_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object roomId_;
    private int totalOtherParticipantsCount_;

    static
    {
      HangoutOnPostData localHangoutOnPostData = new HangoutOnPostData();
      defaultInstance = localHangoutOnPostData;
      localHangoutOnPostData.isActive_ = false;
      localHangoutOnPostData.roomId_ = "";
      localHangoutOnPostData.totalOtherParticipantsCount_ = 0;
    }

    private HangoutOnPostData()
    {
    }

    private HangoutOnPostData(Builder paramBuilder)
    {
      super();
    }

    public static HangoutOnPostData getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getRoomIdBytes()
    {
      Object localObject = this.roomId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.roomId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public final boolean getIsActive()
    {
      return this.isActive_;
    }

    public final String getRoomId()
    {
      Object localObject1 = this.roomId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.roomId_ = str;
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
          k = 0 + CodedOutputStream.computeBoolSize(1, this.isActive_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getRoomIdBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeInt32Size(3, this.totalOtherParticipantsCount_);
        this.memoizedSerializedSize = k;
      }
    }

    public final int getTotalOtherParticipantsCount()
    {
      return this.totalOtherParticipantsCount_;
    }

    public final boolean hasIsActive()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasRoomId()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasTotalOtherParticipantsCount()
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
        paramCodedOutputStream.writeBool(1, this.isActive_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getRoomIdBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeInt32(3, this.totalOtherParticipantsCount_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<A2a.HangoutOnPostData, Builder>
      implements A2a.HangoutOnPostDataOrBuilder
    {
      private int bitField0_;
      private boolean isActive_;
      private Object roomId_ = "";
      private int totalOtherParticipantsCount_;

      private A2a.HangoutOnPostData buildPartial()
      {
        A2a.HangoutOnPostData localHangoutOnPostData = new A2a.HangoutOnPostData(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        A2a.HangoutOnPostData.access$4802(localHangoutOnPostData, this.isActive_);
        if ((i & 0x2) == 2)
          k |= 2;
        A2a.HangoutOnPostData.access$4902(localHangoutOnPostData, this.roomId_);
        if ((i & 0x4) == 4)
          k |= 4;
        A2a.HangoutOnPostData.access$5002(localHangoutOnPostData, this.totalOtherParticipantsCount_);
        A2a.HangoutOnPostData.access$5102(localHangoutOnPostData, k);
        return localHangoutOnPostData;
      }

      private Builder clear()
      {
        super.clear();
        this.isActive_ = false;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.roomId_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.totalOtherParticipantsCount_ = 0;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
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
            this.bitField0_ = (0x1 | this.bitField0_);
            this.isActive_ = paramCodedInputStream.readBool();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.roomId_ = paramCodedInputStream.readBytes();
            break;
          case 24:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.totalOtherParticipantsCount_ = paramCodedInputStream.readInt32();
        }
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(A2a.HangoutOnPostData paramHangoutOnPostData)
      {
        if (paramHangoutOnPostData == A2a.HangoutOnPostData.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramHangoutOnPostData.hasIsActive())
          {
            boolean bool = paramHangoutOnPostData.getIsActive();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.isActive_ = bool;
          }
          if (paramHangoutOnPostData.hasRoomId())
          {
            String str = paramHangoutOnPostData.getRoomId();
            if (str == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.roomId_ = str;
          }
          if (paramHangoutOnPostData.hasTotalOtherParticipantsCount())
          {
            int i = paramHangoutOnPostData.getTotalOtherParticipantsCount();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.totalOtherParticipantsCount_ = i;
          }
        }
      }
    }
  }

  public static abstract interface HangoutOnPostDataOrBuilder extends MessageLiteOrBuilder
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.apps.tacotown.proto.A2a
 * JD-Core Version:    0.6.2
 */