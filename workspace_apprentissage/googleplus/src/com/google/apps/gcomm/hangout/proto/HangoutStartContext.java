package com.google.apps.gcomm.hangout.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.UnmodifiableLazyStringList;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HangoutStartContext extends GeneratedMessageLite
  implements HangoutStartContextOrBuilder
{
  private static final HangoutStartContext defaultInstance;
  private static final long serialVersionUID;
  private Object activityId_;
  private Object appData_;
  private Object appId_;
  private int bitField0_;
  private CallbackType callback_;
  private Object circleId_;
  private Object contextId_;
  private boolean create_;
  private boolean dEPRECATEDCallback_;
  private ExternalEntityKey externalKey_;
  private boolean flippy_;
  private Object hangoutId_;
  private Type hangoutType_;
  private Invitation invitation_;
  private List<Invitee> invitee_;
  private LatencyMarks latencyMarks_;
  private byte memoizedIsInitialized = -1;
  private int memoizedSerializedSize = -1;
  private Object nick_;
  private LazyStringList profileId_;
  private Object referringUrl_;
  private boolean shouldAutoInvite_;
  private boolean shouldMuteVideo_;
  private Source source_;
  private Object topic_;

  static
  {
    HangoutStartContext localHangoutStartContext = new HangoutStartContext();
    defaultInstance = localHangoutStartContext;
    localHangoutStartContext.hangoutId_ = "";
    localHangoutStartContext.hangoutType_ = Type.REGULAR;
    localHangoutStartContext.topic_ = "";
    localHangoutStartContext.referringUrl_ = "";
    localHangoutStartContext.circleId_ = "";
    localHangoutStartContext.profileId_ = LazyStringArrayList.EMPTY;
    localHangoutStartContext.activityId_ = "";
    localHangoutStartContext.appId_ = "";
    localHangoutStartContext.appData_ = "";
    localHangoutStartContext.flippy_ = false;
    localHangoutStartContext.dEPRECATEDCallback_ = false;
    localHangoutStartContext.source_ = Source.SANDBAR;
    localHangoutStartContext.invitation_ = Invitation.getDefaultInstance();
    localHangoutStartContext.create_ = false;
    localHangoutStartContext.nick_ = "";
    localHangoutStartContext.latencyMarks_ = LatencyMarks.getDefaultInstance();
    localHangoutStartContext.callback_ = CallbackType.NONE;
    localHangoutStartContext.externalKey_ = ExternalEntityKey.getDefaultInstance();
    localHangoutStartContext.invitee_ = Collections.emptyList();
    localHangoutStartContext.shouldAutoInvite_ = false;
    localHangoutStartContext.contextId_ = "";
    localHangoutStartContext.shouldMuteVideo_ = false;
  }

  private HangoutStartContext()
  {
  }

  private HangoutStartContext(Builder paramBuilder)
  {
    super((byte)0);
  }

  private ByteString getActivityIdBytes()
  {
    Object localObject = this.activityId_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.activityId_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getAppDataBytes()
  {
    Object localObject = this.appData_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.appData_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getAppIdBytes()
  {
    Object localObject = this.appId_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.appId_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getCircleIdBytes()
  {
    Object localObject = this.circleId_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.circleId_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getContextIdBytes()
  {
    Object localObject = this.contextId_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.contextId_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  public static HangoutStartContext getDefaultInstance()
  {
    return defaultInstance;
  }

  private ByteString getHangoutIdBytes()
  {
    Object localObject = this.hangoutId_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.hangoutId_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getNickBytes()
  {
    Object localObject = this.nick_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.nick_ = localByteString;
    }
    while (true)
    {
      return localByteString;
      localByteString = (ByteString)localObject;
    }
  }

  private ByteString getReferringUrlBytes()
  {
    Object localObject = this.referringUrl_;
    ByteString localByteString;
    if ((localObject instanceof String))
    {
      localByteString = ByteString.copyFromUtf8((String)localObject);
      this.referringUrl_ = localByteString;
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

  public static Builder newBuilder()
  {
    return Builder.access$2500();
  }

  public static Builder newBuilder(HangoutStartContext paramHangoutStartContext)
  {
    return Builder.access$2500().mergeFrom(paramHangoutStartContext);
  }

  public final String getActivityId()
  {
    Object localObject1 = this.activityId_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.activityId_ = str;
    }
  }

  public final String getAppData()
  {
    Object localObject1 = this.appData_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.appData_ = str;
    }
  }

  public final String getAppId()
  {
    Object localObject1 = this.appId_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.appId_ = str;
    }
  }

  public final CallbackType getCallback()
  {
    return this.callback_;
  }

  public final String getCircleId()
  {
    Object localObject1 = this.circleId_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.circleId_ = str;
    }
  }

  public final String getContextId()
  {
    Object localObject1 = this.contextId_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.contextId_ = str;
    }
  }

  public final boolean getCreate()
  {
    return this.create_;
  }

  public final boolean getDEPRECATEDCallback()
  {
    return this.dEPRECATEDCallback_;
  }

  public final ExternalEntityKey getExternalKey()
  {
    return this.externalKey_;
  }

  public final boolean getFlippy()
  {
    return this.flippy_;
  }

  public final String getHangoutId()
  {
    Object localObject1 = this.hangoutId_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.hangoutId_ = str;
    }
  }

  public final Type getHangoutType()
  {
    return this.hangoutType_;
  }

  public final Invitation getInvitation()
  {
    return this.invitation_;
  }

  public final LatencyMarks getLatencyMarks()
  {
    return this.latencyMarks_;
  }

  public final String getNick()
  {
    Object localObject1 = this.nick_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.nick_ = str;
    }
  }

  public final String getReferringUrl()
  {
    Object localObject1 = this.referringUrl_;
    if ((localObject1 instanceof String));
    String str;
    for (Object localObject2 = (String)localObject1; ; localObject2 = str)
    {
      return localObject2;
      ByteString localByteString = (ByteString)localObject1;
      str = localByteString.toStringUtf8();
      if (Internal.isValidUtf8(localByteString))
        this.referringUrl_ = str;
    }
  }

  public final int getSerializedSize()
  {
    int i = this.memoizedSerializedSize;
    if (i != -1);
    int i1;
    for (int i3 = i; ; i3 = i1)
    {
      return i3;
      int j = 0x1 & this.bitField0_;
      int k = 0;
      if (j == 1)
        k = 0 + CodedOutputStream.computeBytesSize(1, getHangoutIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        k += CodedOutputStream.computeEnumSize(2, this.hangoutType_.getNumber());
      if ((0x4 & this.bitField0_) == 4)
        k += CodedOutputStream.computeBytesSize(3, getTopicBytes());
      if ((0x8 & this.bitField0_) == 8)
        k += CodedOutputStream.computeBytesSize(4, getReferringUrlBytes());
      if ((0x10 & this.bitField0_) == 16)
        k += CodedOutputStream.computeBytesSize(5, getCircleIdBytes());
      int m = 0;
      for (int n = 0; n < this.profileId_.size(); n++)
        m += CodedOutputStream.computeBytesSizeNoTag(this.profileId_.getByteString(n));
      i1 = k + m + 1 * this.profileId_.size();
      if ((0x20 & this.bitField0_) == 32)
        i1 += CodedOutputStream.computeBytesSize(7, getActivityIdBytes());
      if ((0x40 & this.bitField0_) == 64)
        i1 += CodedOutputStream.computeBytesSize(8, getAppIdBytes());
      if ((0x80 & this.bitField0_) == 128)
        i1 += CodedOutputStream.computeBytesSize(9, getAppDataBytes());
      if ((0x100 & this.bitField0_) == 256)
        i1 += CodedOutputStream.computeBoolSize(10, this.flippy_);
      if ((0x200 & this.bitField0_) == 512)
        i1 += CodedOutputStream.computeBoolSize(11, this.dEPRECATEDCallback_);
      if ((0x400 & this.bitField0_) == 1024)
        i1 += CodedOutputStream.computeEnumSize(12, this.source_.getNumber());
      if ((0x800 & this.bitField0_) == 2048)
        i1 += CodedOutputStream.computeMessageSize(13, this.invitation_);
      if ((0x1000 & this.bitField0_) == 4096)
        i1 += CodedOutputStream.computeBoolSize(14, this.create_);
      if ((0x2000 & this.bitField0_) == 8192)
        i1 += CodedOutputStream.computeBytesSize(15, getNickBytes());
      if ((0x4000 & this.bitField0_) == 16384)
        i1 += CodedOutputStream.computeMessageSize(16, this.latencyMarks_);
      if ((0x8000 & this.bitField0_) == 32768)
        i1 += CodedOutputStream.computeEnumSize(17, this.callback_.getNumber());
      if ((0x10000 & this.bitField0_) == 65536)
        i1 += CodedOutputStream.computeMessageSize(18, this.externalKey_);
      for (int i2 = 0; i2 < this.invitee_.size(); i2++)
        i1 += CodedOutputStream.computeMessageSize(19, (MessageLite)this.invitee_.get(i2));
      if ((0x20000 & this.bitField0_) == 131072)
        i1 += CodedOutputStream.computeBoolSize(20, this.shouldAutoInvite_);
      if ((0x40000 & this.bitField0_) == 262144)
        i1 += CodedOutputStream.computeBytesSize(21, getContextIdBytes());
      if ((0x80000 & this.bitField0_) == 524288)
        i1 += CodedOutputStream.computeBoolSize(22, this.shouldMuteVideo_);
      this.memoizedSerializedSize = i1;
    }
  }

  public final boolean getShouldAutoInvite()
  {
    return this.shouldAutoInvite_;
  }

  public final boolean getShouldMuteVideo()
  {
    return this.shouldMuteVideo_;
  }

  public final Source getSource()
  {
    return this.source_;
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

  public final boolean hasActivityId()
  {
    if ((0x20 & this.bitField0_) == 32);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasAppData()
  {
    if ((0x80 & this.bitField0_) == 128);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasAppId()
  {
    if ((0x40 & this.bitField0_) == 64);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasCallback()
  {
    if ((0x8000 & this.bitField0_) == 32768);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasCircleId()
  {
    if ((0x10 & this.bitField0_) == 16);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasContextId()
  {
    if ((0x40000 & this.bitField0_) == 262144);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasCreate()
  {
    if ((0x1000 & this.bitField0_) == 4096);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasDEPRECATEDCallback()
  {
    if ((0x200 & this.bitField0_) == 512);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasExternalKey()
  {
    if ((0x10000 & this.bitField0_) == 65536);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasFlippy()
  {
    if ((0x100 & this.bitField0_) == 256);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasHangoutId()
  {
    int i = 1;
    if ((0x1 & this.bitField0_) == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean hasHangoutType()
  {
    if ((0x2 & this.bitField0_) == 2);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasInvitation()
  {
    if ((0x800 & this.bitField0_) == 2048);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasLatencyMarks()
  {
    if ((0x4000 & this.bitField0_) == 16384);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasNick()
  {
    if ((0x2000 & this.bitField0_) == 8192);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasReferringUrl()
  {
    if ((0x8 & this.bitField0_) == 8);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasShouldAutoInvite()
  {
    if ((0x20000 & this.bitField0_) == 131072);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasShouldMuteVideo()
  {
    if ((0x80000 & this.bitField0_) == 524288);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasSource()
  {
    if ((0x400 & this.bitField0_) == 1024);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasTopic()
  {
    if ((0x4 & this.bitField0_) == 4);
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
      if (!hasHangoutId())
      {
        this.memoizedIsInitialized = 0;
        bool = false;
      }
      else if (!hasHangoutType())
      {
        this.memoizedIsInitialized = 0;
        bool = false;
      }
      else if ((hasInvitation()) && (!this.invitation_.isInitialized()))
      {
        this.memoizedIsInitialized = 0;
        bool = false;
      }
      else if ((hasExternalKey()) && (!this.externalKey_.isInitialized()))
      {
        this.memoizedIsInitialized = 0;
        bool = false;
      }
      else
      {
        for (int j = 0; ; j++)
        {
          if (j >= this.invitee_.size())
            break label162;
          if (!((Invitee)this.invitee_.get(j)).isInitialized())
          {
            this.memoizedIsInitialized = 0;
            bool = false;
            break;
          }
        }
        label162: this.memoizedIsInitialized = 1;
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
      paramCodedOutputStream.writeBytes(1, getHangoutIdBytes());
    if ((0x2 & this.bitField0_) == 2)
      paramCodedOutputStream.writeEnum(2, this.hangoutType_.getNumber());
    if ((0x4 & this.bitField0_) == 4)
      paramCodedOutputStream.writeBytes(3, getTopicBytes());
    if ((0x8 & this.bitField0_) == 8)
      paramCodedOutputStream.writeBytes(4, getReferringUrlBytes());
    if ((0x10 & this.bitField0_) == 16)
      paramCodedOutputStream.writeBytes(5, getCircleIdBytes());
    for (int i = 0; i < this.profileId_.size(); i++)
      paramCodedOutputStream.writeBytes(6, this.profileId_.getByteString(i));
    if ((0x20 & this.bitField0_) == 32)
      paramCodedOutputStream.writeBytes(7, getActivityIdBytes());
    if ((0x40 & this.bitField0_) == 64)
      paramCodedOutputStream.writeBytes(8, getAppIdBytes());
    if ((0x80 & this.bitField0_) == 128)
      paramCodedOutputStream.writeBytes(9, getAppDataBytes());
    if ((0x100 & this.bitField0_) == 256)
      paramCodedOutputStream.writeBool(10, this.flippy_);
    if ((0x200 & this.bitField0_) == 512)
      paramCodedOutputStream.writeBool(11, this.dEPRECATEDCallback_);
    if ((0x400 & this.bitField0_) == 1024)
      paramCodedOutputStream.writeEnum(12, this.source_.getNumber());
    if ((0x800 & this.bitField0_) == 2048)
      paramCodedOutputStream.writeMessage(13, this.invitation_);
    if ((0x1000 & this.bitField0_) == 4096)
      paramCodedOutputStream.writeBool(14, this.create_);
    if ((0x2000 & this.bitField0_) == 8192)
      paramCodedOutputStream.writeBytes(15, getNickBytes());
    if ((0x4000 & this.bitField0_) == 16384)
      paramCodedOutputStream.writeMessage(16, this.latencyMarks_);
    if ((0x8000 & this.bitField0_) == 32768)
      paramCodedOutputStream.writeEnum(17, this.callback_.getNumber());
    if ((0x10000 & this.bitField0_) == 65536)
      paramCodedOutputStream.writeMessage(18, this.externalKey_);
    for (int j = 0; j < this.invitee_.size(); j++)
      paramCodedOutputStream.writeMessage(19, (MessageLite)this.invitee_.get(j));
    if ((0x20000 & this.bitField0_) == 131072)
      paramCodedOutputStream.writeBool(20, this.shouldAutoInvite_);
    if ((0x40000 & this.bitField0_) == 262144)
      paramCodedOutputStream.writeBytes(21, getContextIdBytes());
    if ((0x80000 & this.bitField0_) == 524288)
      paramCodedOutputStream.writeBool(22, this.shouldMuteVideo_);
  }

  public static final class Builder extends GeneratedMessageLite.Builder<HangoutStartContext, Builder>
    implements HangoutStartContextOrBuilder
  {
    private Object activityId_ = "";
    private Object appData_ = "";
    private Object appId_ = "";
    private int bitField0_;
    private HangoutStartContext.CallbackType callback_ = HangoutStartContext.CallbackType.NONE;
    private Object circleId_ = "";
    private Object contextId_ = "";
    private boolean create_;
    private boolean dEPRECATEDCallback_;
    private ExternalEntityKey externalKey_ = ExternalEntityKey.getDefaultInstance();
    private boolean flippy_;
    private Object hangoutId_ = "";
    private HangoutStartContext.Type hangoutType_ = HangoutStartContext.Type.REGULAR;
    private HangoutStartContext.Invitation invitation_ = HangoutStartContext.Invitation.getDefaultInstance();
    private List<HangoutStartContext.Invitee> invitee_ = Collections.emptyList();
    private HangoutStartContext.LatencyMarks latencyMarks_ = HangoutStartContext.LatencyMarks.getDefaultInstance();
    private Object nick_ = "";
    private LazyStringList profileId_ = LazyStringArrayList.EMPTY;
    private Object referringUrl_ = "";
    private boolean shouldAutoInvite_;
    private boolean shouldMuteVideo_;
    private HangoutStartContext.Source source_ = HangoutStartContext.Source.SANDBAR;
    private Object topic_ = "";

    private Builder clear()
    {
      super.clear();
      this.hangoutId_ = "";
      this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
      this.hangoutType_ = HangoutStartContext.Type.REGULAR;
      this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
      this.topic_ = "";
      this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
      this.referringUrl_ = "";
      this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
      this.circleId_ = "";
      this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
      this.profileId_ = LazyStringArrayList.EMPTY;
      this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
      this.activityId_ = "";
      this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
      this.appId_ = "";
      this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
      this.appData_ = "";
      this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
      this.flippy_ = false;
      this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
      this.dEPRECATEDCallback_ = false;
      this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
      this.source_ = HangoutStartContext.Source.SANDBAR;
      this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
      this.invitation_ = HangoutStartContext.Invitation.getDefaultInstance();
      this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
      this.create_ = false;
      this.bitField0_ = (0xFFFFDFFF & this.bitField0_);
      this.nick_ = "";
      this.bitField0_ = (0xFFFFBFFF & this.bitField0_);
      this.latencyMarks_ = HangoutStartContext.LatencyMarks.getDefaultInstance();
      this.bitField0_ = (0xFFFF7FFF & this.bitField0_);
      this.callback_ = HangoutStartContext.CallbackType.NONE;
      this.bitField0_ = (0xFFFEFFFF & this.bitField0_);
      this.externalKey_ = ExternalEntityKey.getDefaultInstance();
      this.bitField0_ = (0xFFFDFFFF & this.bitField0_);
      this.invitee_ = Collections.emptyList();
      this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
      this.shouldAutoInvite_ = false;
      this.bitField0_ = (0xFFF7FFFF & this.bitField0_);
      this.contextId_ = "";
      this.bitField0_ = (0xFFEFFFFF & this.bitField0_);
      this.shouldMuteVideo_ = false;
      this.bitField0_ = (0xFFDFFFFF & this.bitField0_);
      return this;
    }

    private Builder clone()
    {
      return new Builder().mergeFrom(buildPartial());
    }

    private void ensureInviteeIsMutable()
    {
      if ((0x40000 & this.bitField0_) != 262144)
      {
        this.invitee_ = new ArrayList(this.invitee_);
        this.bitField0_ = (0x40000 | this.bitField0_);
      }
    }

    private void ensureProfileIdIsMutable()
    {
      if ((0x20 & this.bitField0_) != 32)
      {
        this.profileId_ = new LazyStringArrayList(this.profileId_);
        this.bitField0_ = (0x20 | this.bitField0_);
      }
    }

    private boolean hasExternalKey()
    {
      if ((0x20000 & this.bitField0_) == 131072);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    private boolean hasInvitation()
    {
      if ((0x1000 & this.bitField0_) == 4096);
      for (boolean bool = true; ; bool = false)
        return bool;
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
          this.hangoutId_ = paramCodedInputStream.readBytes();
          break;
        case 16:
          HangoutStartContext.Type localType = HangoutStartContext.Type.valueOf(paramCodedInputStream.readEnum());
          if (localType == null)
            continue;
          this.bitField0_ = (0x2 | this.bitField0_);
          this.hangoutType_ = localType;
          break;
        case 26:
          this.bitField0_ = (0x4 | this.bitField0_);
          this.topic_ = paramCodedInputStream.readBytes();
          break;
        case 34:
          this.bitField0_ = (0x8 | this.bitField0_);
          this.referringUrl_ = paramCodedInputStream.readBytes();
          break;
        case 42:
          this.bitField0_ = (0x10 | this.bitField0_);
          this.circleId_ = paramCodedInputStream.readBytes();
          break;
        case 50:
          ensureProfileIdIsMutable();
          this.profileId_.add(paramCodedInputStream.readBytes());
          break;
        case 58:
          this.bitField0_ = (0x40 | this.bitField0_);
          this.activityId_ = paramCodedInputStream.readBytes();
          break;
        case 66:
          this.bitField0_ = (0x80 | this.bitField0_);
          this.appId_ = paramCodedInputStream.readBytes();
          break;
        case 74:
          this.bitField0_ = (0x100 | this.bitField0_);
          this.appData_ = paramCodedInputStream.readBytes();
          break;
        case 80:
          this.bitField0_ = (0x200 | this.bitField0_);
          this.flippy_ = paramCodedInputStream.readBool();
          break;
        case 88:
          this.bitField0_ = (0x400 | this.bitField0_);
          this.dEPRECATEDCallback_ = paramCodedInputStream.readBool();
          break;
        case 96:
          HangoutStartContext.Source localSource = HangoutStartContext.Source.valueOf(paramCodedInputStream.readEnum());
          if (localSource == null)
            continue;
          this.bitField0_ = (0x800 | this.bitField0_);
          this.source_ = localSource;
          break;
        case 106:
          HangoutStartContext.Invitation.Builder localBuilder3 = HangoutStartContext.Invitation.newBuilder();
          if (hasInvitation())
            localBuilder3.mergeFrom(this.invitation_);
          paramCodedInputStream.readMessage(localBuilder3, paramExtensionRegistryLite);
          HangoutStartContext.Invitation localInvitation = localBuilder3.buildPartial();
          if (localInvitation == null)
            throw new NullPointerException();
          this.invitation_ = localInvitation;
          this.bitField0_ = (0x1000 | this.bitField0_);
          break;
        case 112:
          this.bitField0_ = (0x2000 | this.bitField0_);
          this.create_ = paramCodedInputStream.readBool();
          break;
        case 122:
          this.bitField0_ = (0x4000 | this.bitField0_);
          this.nick_ = paramCodedInputStream.readBytes();
          break;
        case 130:
          HangoutStartContext.LatencyMarks.Builder localBuilder2 = HangoutStartContext.LatencyMarks.newBuilder();
          if ((0x8000 & this.bitField0_) == 32768);
          HangoutStartContext.LatencyMarks localLatencyMarks;
          for (int j = 1; ; j = 0)
          {
            if (j != 0)
              localBuilder2.mergeFrom(this.latencyMarks_);
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            localLatencyMarks = localBuilder2.buildPartial();
            if (localLatencyMarks != null)
              break;
            throw new NullPointerException();
          }
          this.latencyMarks_ = localLatencyMarks;
          this.bitField0_ = (0x8000 | this.bitField0_);
          break;
        case 136:
          HangoutStartContext.CallbackType localCallbackType = HangoutStartContext.CallbackType.valueOf(paramCodedInputStream.readEnum());
          if (localCallbackType == null)
            continue;
          this.bitField0_ = (0x10000 | this.bitField0_);
          this.callback_ = localCallbackType;
          break;
        case 146:
          ExternalEntityKey.Builder localBuilder1 = ExternalEntityKey.newBuilder();
          if (hasExternalKey())
            localBuilder1.mergeFrom(this.externalKey_);
          paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
          ExternalEntityKey localExternalEntityKey = localBuilder1.buildPartial();
          if (localExternalEntityKey == null)
            throw new NullPointerException();
          this.externalKey_ = localExternalEntityKey;
          this.bitField0_ = (0x20000 | this.bitField0_);
          break;
        case 154:
          HangoutStartContext.Invitee.Builder localBuilder = HangoutStartContext.Invitee.newBuilder();
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          HangoutStartContext.Invitee localInvitee = localBuilder.buildPartial();
          if (localInvitee == null)
            throw new NullPointerException();
          ensureInviteeIsMutable();
          this.invitee_.add(localInvitee);
          break;
        case 160:
          this.bitField0_ = (0x80000 | this.bitField0_);
          this.shouldAutoInvite_ = paramCodedInputStream.readBool();
          break;
        case 170:
          this.bitField0_ = (0x100000 | this.bitField0_);
          this.contextId_ = paramCodedInputStream.readBytes();
          break;
        case 176:
        }
        this.bitField0_ = (0x200000 | this.bitField0_);
        this.shouldMuteVideo_ = paramCodedInputStream.readBool();
      }
    }

    public final HangoutStartContext buildPartial()
    {
      HangoutStartContext localHangoutStartContext = new HangoutStartContext(this, (byte)0);
      int i = this.bitField0_;
      int j = i & 0x1;
      int k = 0;
      if (j == 1)
        k = 1;
      HangoutStartContext.access$2702(localHangoutStartContext, this.hangoutId_);
      if ((i & 0x2) == 2)
        k |= 2;
      HangoutStartContext.access$2802(localHangoutStartContext, this.hangoutType_);
      if ((i & 0x4) == 4)
        k |= 4;
      HangoutStartContext.access$2902(localHangoutStartContext, this.topic_);
      if ((i & 0x8) == 8)
        k |= 8;
      HangoutStartContext.access$3002(localHangoutStartContext, this.referringUrl_);
      if ((i & 0x10) == 16)
        k |= 16;
      HangoutStartContext.access$3102(localHangoutStartContext, this.circleId_);
      if ((0x20 & this.bitField0_) == 32)
      {
        this.profileId_ = new UnmodifiableLazyStringList(this.profileId_);
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
      }
      HangoutStartContext.access$3202(localHangoutStartContext, this.profileId_);
      if ((i & 0x40) == 64)
        k |= 32;
      HangoutStartContext.access$3302(localHangoutStartContext, this.activityId_);
      if ((i & 0x80) == 128)
        k |= 64;
      HangoutStartContext.access$3402(localHangoutStartContext, this.appId_);
      if ((i & 0x100) == 256)
        k |= 128;
      HangoutStartContext.access$3502(localHangoutStartContext, this.appData_);
      if ((i & 0x200) == 512)
        k |= 256;
      HangoutStartContext.access$3602(localHangoutStartContext, this.flippy_);
      if ((i & 0x400) == 1024)
        k |= 512;
      HangoutStartContext.access$3702(localHangoutStartContext, this.dEPRECATEDCallback_);
      if ((i & 0x800) == 2048)
        k |= 1024;
      HangoutStartContext.access$3802(localHangoutStartContext, this.source_);
      if ((i & 0x1000) == 4096)
        k |= 2048;
      HangoutStartContext.access$3902(localHangoutStartContext, this.invitation_);
      if ((i & 0x2000) == 8192)
        k |= 4096;
      HangoutStartContext.access$4002(localHangoutStartContext, this.create_);
      if ((i & 0x4000) == 16384)
        k |= 8192;
      HangoutStartContext.access$4102(localHangoutStartContext, this.nick_);
      if ((i & 0x8000) == 32768)
        k |= 16384;
      HangoutStartContext.access$4202(localHangoutStartContext, this.latencyMarks_);
      if ((i & 0x10000) == 65536)
        k |= 32768;
      HangoutStartContext.access$4302(localHangoutStartContext, this.callback_);
      if ((i & 0x20000) == 131072)
        k |= 65536;
      HangoutStartContext.access$4402(localHangoutStartContext, this.externalKey_);
      if ((0x40000 & this.bitField0_) == 262144)
      {
        this.invitee_ = Collections.unmodifiableList(this.invitee_);
        this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
      }
      HangoutStartContext.access$4502(localHangoutStartContext, this.invitee_);
      if ((i & 0x80000) == 524288)
        k |= 131072;
      HangoutStartContext.access$4602(localHangoutStartContext, this.shouldAutoInvite_);
      if ((0x100000 & i) == 1048576)
        k |= 262144;
      HangoutStartContext.access$4702(localHangoutStartContext, this.contextId_);
      if ((0x200000 & i) == 2097152)
        k |= 524288;
      HangoutStartContext.access$4802(localHangoutStartContext, this.shouldMuteVideo_);
      HangoutStartContext.access$4902(localHangoutStartContext, k);
      return localHangoutStartContext;
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
      label143: 
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
        label51: if ((hasInvitation()) && (!this.invitation_.isInitialized()))
        {
          bool = false;
        }
        else if ((hasExternalKey()) && (!this.externalKey_.isInitialized()))
        {
          bool = false;
        }
        else
        {
          for (int k = 0; ; k++)
          {
            if (k >= this.invitee_.size())
              break label143;
            if (!((HangoutStartContext.Invitee)this.invitee_.get(k)).isInitialized())
            {
              bool = false;
              break;
            }
          }
          bool = true;
        }
      }
    }

    public final Builder mergeFrom(HangoutStartContext paramHangoutStartContext)
    {
      if (paramHangoutStartContext == HangoutStartContext.getDefaultInstance());
      while (true)
      {
        return this;
        if (paramHangoutStartContext.hasHangoutId())
        {
          String str9 = paramHangoutStartContext.getHangoutId();
          if (str9 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x1 | this.bitField0_);
          this.hangoutId_ = str9;
        }
        if (paramHangoutStartContext.hasHangoutType())
        {
          HangoutStartContext.Type localType = paramHangoutStartContext.getHangoutType();
          if (localType == null)
            throw new NullPointerException();
          this.bitField0_ = (0x2 | this.bitField0_);
          this.hangoutType_ = localType;
        }
        if (paramHangoutStartContext.hasTopic())
        {
          String str8 = paramHangoutStartContext.getTopic();
          if (str8 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x4 | this.bitField0_);
          this.topic_ = str8;
        }
        if (paramHangoutStartContext.hasReferringUrl())
        {
          String str7 = paramHangoutStartContext.getReferringUrl();
          if (str7 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x8 | this.bitField0_);
          this.referringUrl_ = str7;
        }
        if (paramHangoutStartContext.hasCircleId())
        {
          String str6 = paramHangoutStartContext.getCircleId();
          if (str6 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x10 | this.bitField0_);
          this.circleId_ = str6;
        }
        if (!paramHangoutStartContext.profileId_.isEmpty())
        {
          if (!this.profileId_.isEmpty())
            break label290;
          this.profileId_ = paramHangoutStartContext.profileId_;
          this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        }
        while (paramHangoutStartContext.hasActivityId())
        {
          String str5 = paramHangoutStartContext.getActivityId();
          if (str5 == null)
          {
            throw new NullPointerException();
            label290: ensureProfileIdIsMutable();
            this.profileId_.addAll(paramHangoutStartContext.profileId_);
          }
          else
          {
            this.bitField0_ = (0x40 | this.bitField0_);
            this.activityId_ = str5;
          }
        }
        if (paramHangoutStartContext.hasAppId())
        {
          String str4 = paramHangoutStartContext.getAppId();
          if (str4 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x80 | this.bitField0_);
          this.appId_ = str4;
        }
        if (paramHangoutStartContext.hasAppData())
        {
          String str3 = paramHangoutStartContext.getAppData();
          if (str3 == null)
            throw new NullPointerException();
          this.bitField0_ = (0x100 | this.bitField0_);
          this.appData_ = str3;
        }
        if (paramHangoutStartContext.hasFlippy())
        {
          boolean bool5 = paramHangoutStartContext.getFlippy();
          this.bitField0_ = (0x200 | this.bitField0_);
          this.flippy_ = bool5;
        }
        if (paramHangoutStartContext.hasDEPRECATEDCallback())
        {
          boolean bool4 = paramHangoutStartContext.getDEPRECATEDCallback();
          this.bitField0_ = (0x400 | this.bitField0_);
          this.dEPRECATEDCallback_ = bool4;
        }
        if (paramHangoutStartContext.hasSource())
        {
          HangoutStartContext.Source localSource = paramHangoutStartContext.getSource();
          if (localSource == null)
            throw new NullPointerException();
          this.bitField0_ = (0x800 | this.bitField0_);
          this.source_ = localSource;
        }
        HangoutStartContext.Invitation localInvitation;
        if (paramHangoutStartContext.hasInvitation())
        {
          localInvitation = paramHangoutStartContext.getInvitation();
          if (((0x1000 & this.bitField0_) != 4096) || (this.invitation_ == HangoutStartContext.Invitation.getDefaultInstance()))
            break label647;
        }
        String str2;
        label647: for (this.invitation_ = HangoutStartContext.Invitation.newBuilder(this.invitation_).mergeFrom(localInvitation).buildPartial(); ; this.invitation_ = localInvitation)
        {
          this.bitField0_ = (0x1000 | this.bitField0_);
          if (paramHangoutStartContext.hasCreate())
          {
            boolean bool3 = paramHangoutStartContext.getCreate();
            this.bitField0_ = (0x2000 | this.bitField0_);
            this.create_ = bool3;
          }
          if (!paramHangoutStartContext.hasNick())
            break label674;
          str2 = paramHangoutStartContext.getNick();
          if (str2 != null)
            break;
          throw new NullPointerException();
        }
        this.bitField0_ = (0x4000 | this.bitField0_);
        this.nick_ = str2;
        label674: HangoutStartContext.LatencyMarks localLatencyMarks;
        if (paramHangoutStartContext.hasLatencyMarks())
        {
          localLatencyMarks = paramHangoutStartContext.getLatencyMarks();
          if (((0x8000 & this.bitField0_) != 32768) || (this.latencyMarks_ == HangoutStartContext.LatencyMarks.getDefaultInstance()))
            break label765;
        }
        HangoutStartContext.CallbackType localCallbackType;
        label765: for (this.latencyMarks_ = HangoutStartContext.LatencyMarks.newBuilder(this.latencyMarks_).mergeFrom(localLatencyMarks).buildPartial(); ; this.latencyMarks_ = localLatencyMarks)
        {
          this.bitField0_ = (0x8000 | this.bitField0_);
          if (!paramHangoutStartContext.hasCallback())
            break label791;
          localCallbackType = paramHangoutStartContext.getCallback();
          if (localCallbackType != null)
            break;
          throw new NullPointerException();
        }
        this.bitField0_ = (0x10000 | this.bitField0_);
        this.callback_ = localCallbackType;
        label791: ExternalEntityKey localExternalEntityKey;
        if (paramHangoutStartContext.hasExternalKey())
        {
          localExternalEntityKey = paramHangoutStartContext.getExternalKey();
          if (((0x20000 & this.bitField0_) == 131072) && (this.externalKey_ != ExternalEntityKey.getDefaultInstance()))
          {
            this.externalKey_ = ExternalEntityKey.newBuilder(this.externalKey_).mergeFrom(localExternalEntityKey).buildPartial();
            this.bitField0_ = (0x20000 | this.bitField0_);
          }
        }
        else if (!paramHangoutStartContext.invitee_.isEmpty())
        {
          if (!this.invitee_.isEmpty())
            break label963;
          this.invitee_ = paramHangoutStartContext.invitee_;
          this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
        }
        String str1;
        while (true)
        {
          if (paramHangoutStartContext.hasShouldAutoInvite())
          {
            boolean bool2 = paramHangoutStartContext.getShouldAutoInvite();
            this.bitField0_ = (0x80000 | this.bitField0_);
            this.shouldAutoInvite_ = bool2;
          }
          if (!paramHangoutStartContext.hasContextId())
            break label1001;
          str1 = paramHangoutStartContext.getContextId();
          if (str1 != null)
            break label984;
          throw new NullPointerException();
          this.externalKey_ = localExternalEntityKey;
          break;
          label963: ensureInviteeIsMutable();
          this.invitee_.addAll(paramHangoutStartContext.invitee_);
        }
        label984: this.bitField0_ = (0x100000 | this.bitField0_);
        this.contextId_ = str1;
        label1001: if (paramHangoutStartContext.hasShouldMuteVideo())
        {
          boolean bool1 = paramHangoutStartContext.getShouldMuteVideo();
          this.bitField0_ = (0x200000 | this.bitField0_);
          this.shouldMuteVideo_ = bool1;
        }
      }
    }
  }

  public static enum CallbackType
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<CallbackType> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      GROUP = new CallbackType("GROUP", 2, 2);
      CallbackType[] arrayOfCallbackType = new CallbackType[3];
      arrayOfCallbackType[0] = NONE;
      arrayOfCallbackType[1] = SINGLE;
      arrayOfCallbackType[2] = GROUP;
    }

    private CallbackType(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static CallbackType valueOf(int paramInt)
    {
      CallbackType localCallbackType;
      switch (paramInt)
      {
      default:
        localCallbackType = null;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        return localCallbackType;
        localCallbackType = NONE;
        continue;
        localCallbackType = SINGLE;
        continue;
        localCallbackType = GROUP;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static final class Invitation extends GeneratedMessageLite
    implements HangoutStartContext.InvitationOrBuilder
  {
    private static final Invitation defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private HangoutStartContext.InvitationType invitationType_;
    private Object inviterGaiaId_;
    private Object inviterProfileName_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object phoneNumber_;
    private boolean shouldAutoAccept_;
    private long timestamp_;

    static
    {
      Invitation localInvitation = new Invitation();
      defaultInstance = localInvitation;
      localInvitation.timestamp_ = 0L;
      localInvitation.inviterGaiaId_ = "";
      localInvitation.invitationType_ = HangoutStartContext.InvitationType.HANGOUT;
      localInvitation.inviterProfileName_ = "";
      localInvitation.shouldAutoAccept_ = false;
      localInvitation.phoneNumber_ = "";
    }

    private Invitation()
    {
    }

    private Invitation(Builder paramBuilder)
    {
      super();
    }

    public static Invitation getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getInviterGaiaIdBytes()
    {
      Object localObject = this.inviterGaiaId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.inviterGaiaId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getInviterProfileNameBytes()
    {
      Object localObject = this.inviterProfileName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.inviterProfileName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getPhoneNumberBytes()
    {
      Object localObject = this.phoneNumber_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.phoneNumber_ = localByteString;
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

    public static Builder newBuilder(Invitation paramInvitation)
    {
      return Builder.access$100().mergeFrom(paramInvitation);
    }

    public final HangoutStartContext.InvitationType getInvitationType()
    {
      return this.invitationType_;
    }

    public final String getInviterGaiaId()
    {
      Object localObject1 = this.inviterGaiaId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.inviterGaiaId_ = str;
      }
    }

    public final String getInviterProfileName()
    {
      Object localObject1 = this.inviterProfileName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.inviterProfileName_ = str;
      }
    }

    public final String getPhoneNumber()
    {
      Object localObject1 = this.phoneNumber_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.phoneNumber_ = str;
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
          k = 0 + CodedOutputStream.computeInt64Size(1, this.timestamp_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getInviterGaiaIdBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeEnumSize(3, this.invitationType_.getNumber());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBytesSize(4, getInviterProfileNameBytes());
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeBoolSize(5, this.shouldAutoAccept_);
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeBytesSize(6, getPhoneNumberBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean getShouldAutoAccept()
    {
      return this.shouldAutoAccept_;
    }

    public final long getTimestamp()
    {
      return this.timestamp_;
    }

    public final boolean hasInvitationType()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasInviterGaiaId()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasInviterProfileName()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPhoneNumber()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasShouldAutoAccept()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasTimestamp()
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
        if (!hasTimestamp())
        {
          this.memoizedIsInitialized = 0;
          i = 0;
        }
        else if (!hasInviterGaiaId())
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
        paramCodedOutputStream.writeInt64(1, this.timestamp_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getInviterGaiaIdBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeEnum(3, this.invitationType_.getNumber());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBytes(4, getInviterProfileNameBytes());
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeBool(5, this.shouldAutoAccept_);
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeBytes(6, getPhoneNumberBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<HangoutStartContext.Invitation, Builder>
      implements HangoutStartContext.InvitationOrBuilder
    {
      private int bitField0_;
      private HangoutStartContext.InvitationType invitationType_ = HangoutStartContext.InvitationType.HANGOUT;
      private Object inviterGaiaId_ = "";
      private Object inviterProfileName_ = "";
      private Object phoneNumber_ = "";
      private boolean shouldAutoAccept_;
      private long timestamp_;

      private Builder clear()
      {
        super.clear();
        this.timestamp_ = 0L;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.inviterGaiaId_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.invitationType_ = HangoutStartContext.InvitationType.HANGOUT;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.inviterProfileName_ = "";
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.shouldAutoAccept_ = false;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.phoneNumber_ = "";
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
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
            this.timestamp_ = paramCodedInputStream.readInt64();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.inviterGaiaId_ = paramCodedInputStream.readBytes();
            break;
          case 24:
            HangoutStartContext.InvitationType localInvitationType = HangoutStartContext.InvitationType.valueOf(paramCodedInputStream.readEnum());
            if (localInvitationType == null)
              continue;
            this.bitField0_ = (0x4 | this.bitField0_);
            this.invitationType_ = localInvitationType;
            break;
          case 34:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.inviterProfileName_ = paramCodedInputStream.readBytes();
            break;
          case 40:
            this.bitField0_ = (0x10 | this.bitField0_);
            this.shouldAutoAccept_ = paramCodedInputStream.readBool();
            break;
          case 50:
          }
          this.bitField0_ = (0x20 | this.bitField0_);
          this.phoneNumber_ = paramCodedInputStream.readBytes();
        }
      }

      public final HangoutStartContext.Invitation buildPartial()
      {
        HangoutStartContext.Invitation localInvitation = new HangoutStartContext.Invitation(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        HangoutStartContext.Invitation.access$302(localInvitation, this.timestamp_);
        if ((i & 0x2) == 2)
          k |= 2;
        HangoutStartContext.Invitation.access$402(localInvitation, this.inviterGaiaId_);
        if ((i & 0x4) == 4)
          k |= 4;
        HangoutStartContext.Invitation.access$502(localInvitation, this.invitationType_);
        if ((i & 0x8) == 8)
          k |= 8;
        HangoutStartContext.Invitation.access$602(localInvitation, this.inviterProfileName_);
        if ((i & 0x10) == 16)
          k |= 16;
        HangoutStartContext.Invitation.access$702(localInvitation, this.shouldAutoAccept_);
        if ((i & 0x20) == 32)
          k |= 32;
        HangoutStartContext.Invitation.access$802(localInvitation, this.phoneNumber_);
        HangoutStartContext.Invitation.access$902(localInvitation, k);
        return localInvitation;
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

      public final Builder mergeFrom(HangoutStartContext.Invitation paramInvitation)
      {
        if (paramInvitation == HangoutStartContext.Invitation.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramInvitation.hasTimestamp())
          {
            long l = paramInvitation.getTimestamp();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.timestamp_ = l;
          }
          if (paramInvitation.hasInviterGaiaId())
          {
            String str3 = paramInvitation.getInviterGaiaId();
            if (str3 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.inviterGaiaId_ = str3;
          }
          if (paramInvitation.hasInvitationType())
          {
            HangoutStartContext.InvitationType localInvitationType = paramInvitation.getInvitationType();
            if (localInvitationType == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.invitationType_ = localInvitationType;
          }
          if (paramInvitation.hasInviterProfileName())
          {
            String str2 = paramInvitation.getInviterProfileName();
            if (str2 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x8 | this.bitField0_);
            this.inviterProfileName_ = str2;
          }
          if (paramInvitation.hasShouldAutoAccept())
          {
            boolean bool = paramInvitation.getShouldAutoAccept();
            this.bitField0_ = (0x10 | this.bitField0_);
            this.shouldAutoAccept_ = bool;
          }
          if (paramInvitation.hasPhoneNumber())
          {
            String str1 = paramInvitation.getPhoneNumber();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x20 | this.bitField0_);
            this.phoneNumber_ = str1;
          }
        }
      }
    }
  }

  public static abstract interface InvitationOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static enum InvitationType
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<InvitationType> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      InvitationType[] arrayOfInvitationType = new InvitationType[3];
      arrayOfInvitationType[0] = HANGOUT;
      arrayOfInvitationType[1] = HANGOUT_SYNC;
      arrayOfInvitationType[2] = TRANSFER;
    }

    private InvitationType(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static InvitationType valueOf(int paramInt)
    {
      InvitationType localInvitationType;
      switch (paramInt)
      {
      default:
        localInvitationType = null;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        return localInvitationType;
        localInvitationType = HANGOUT;
        continue;
        localInvitationType = HANGOUT_SYNC;
        continue;
        localInvitationType = TRANSFER;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static final class Invitee extends GeneratedMessageLite
    implements HangoutStartContext.InviteeOrBuilder
  {
    private static final Invitee defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object profileId_;
    private Object profileName_;

    static
    {
      Invitee localInvitee = new Invitee();
      defaultInstance = localInvitee;
      localInvitee.profileId_ = "";
      localInvitee.profileName_ = "";
    }

    private Invitee()
    {
    }

    private Invitee(Builder paramBuilder)
    {
      super();
    }

    public static Invitee getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getProfileIdBytes()
    {
      Object localObject = this.profileId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.profileId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getProfileNameBytes()
    {
      Object localObject = this.profileName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.profileName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$1900();
    }

    public final String getProfileId()
    {
      Object localObject1 = this.profileId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.profileId_ = str;
      }
    }

    public final String getProfileName()
    {
      Object localObject1 = this.profileName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.profileName_ = str;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getProfileIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getProfileNameBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasProfileId()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasProfileName()
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
        if (!hasProfileId())
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
        paramCodedOutputStream.writeBytes(1, getProfileIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getProfileNameBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<HangoutStartContext.Invitee, Builder>
      implements HangoutStartContext.InviteeOrBuilder
    {
      private int bitField0_;
      private Object profileId_ = "";
      private Object profileName_ = "";

      private Builder clear()
      {
        super.clear();
        this.profileId_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.profileName_ = "";
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
            this.profileId_ = paramCodedInputStream.readBytes();
            break;
          case 18:
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.profileName_ = paramCodedInputStream.readBytes();
        }
      }

      public final HangoutStartContext.Invitee buildPartial()
      {
        HangoutStartContext.Invitee localInvitee = new HangoutStartContext.Invitee(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        HangoutStartContext.Invitee.access$2102(localInvitee, this.profileId_);
        if ((i & 0x2) == 2)
          k |= 2;
        HangoutStartContext.Invitee.access$2202(localInvitee, this.profileName_);
        HangoutStartContext.Invitee.access$2302(localInvitee, k);
        return localInvitee;
      }

      public final boolean isInitialized()
      {
        int i;
        boolean bool;
        if ((0x1 & this.bitField0_) == 1)
        {
          i = 1;
          bool = false;
          if (i != 0)
            break label25;
        }
        while (true)
        {
          return bool;
          i = 0;
          break;
          label25: bool = true;
        }
      }

      public final Builder mergeFrom(HangoutStartContext.Invitee paramInvitee)
      {
        if (paramInvitee == HangoutStartContext.Invitee.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramInvitee.hasProfileId())
          {
            String str2 = paramInvitee.getProfileId();
            if (str2 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.profileId_ = str2;
          }
          if (paramInvitee.hasProfileName())
          {
            String str1 = paramInvitee.getProfileName();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.profileName_ = str1;
          }
        }
      }
    }
  }

  public static abstract interface InviteeOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class LatencyMarks extends GeneratedMessageLite
    implements HangoutStartContext.LatencyMarksOrBuilder
  {
    private static final LatencyMarks defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private long clientLaunch_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private long serverCreateRedirectEnd_;
    private long serverCreateRoomEnd_;
    private long serverCreateRoomStart_;

    static
    {
      LatencyMarks localLatencyMarks = new LatencyMarks();
      defaultInstance = localLatencyMarks;
      localLatencyMarks.clientLaunch_ = 0L;
      localLatencyMarks.serverCreateRoomStart_ = 0L;
      localLatencyMarks.serverCreateRoomEnd_ = 0L;
      localLatencyMarks.serverCreateRedirectEnd_ = 0L;
    }

    private LatencyMarks()
    {
    }

    private LatencyMarks(Builder paramBuilder)
    {
      super();
    }

    public static LatencyMarks getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$1100();
    }

    public static Builder newBuilder(LatencyMarks paramLatencyMarks)
    {
      return Builder.access$1100().mergeFrom(paramLatencyMarks);
    }

    public final long getClientLaunch()
    {
      return this.clientLaunch_;
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
          k = 0 + CodedOutputStream.computeInt64Size(1, this.clientLaunch_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeInt64Size(2, this.serverCreateRoomStart_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeInt64Size(3, this.serverCreateRoomEnd_);
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeInt64Size(4, this.serverCreateRedirectEnd_);
        this.memoizedSerializedSize = k;
      }
    }

    public final long getServerCreateRedirectEnd()
    {
      return this.serverCreateRedirectEnd_;
    }

    public final long getServerCreateRoomEnd()
    {
      return this.serverCreateRoomEnd_;
    }

    public final long getServerCreateRoomStart()
    {
      return this.serverCreateRoomStart_;
    }

    public final boolean hasClientLaunch()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasServerCreateRedirectEnd()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasServerCreateRoomEnd()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasServerCreateRoomStart()
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
        paramCodedOutputStream.writeInt64(1, this.clientLaunch_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeInt64(2, this.serverCreateRoomStart_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeInt64(3, this.serverCreateRoomEnd_);
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeInt64(4, this.serverCreateRedirectEnd_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<HangoutStartContext.LatencyMarks, Builder>
      implements HangoutStartContext.LatencyMarksOrBuilder
    {
      private int bitField0_;
      private long clientLaunch_;
      private long serverCreateRedirectEnd_;
      private long serverCreateRoomEnd_;
      private long serverCreateRoomStart_;

      private Builder clear()
      {
        super.clear();
        this.clientLaunch_ = 0L;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.serverCreateRoomStart_ = 0L;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.serverCreateRoomEnd_ = 0L;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.serverCreateRedirectEnd_ = 0L;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
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
            this.clientLaunch_ = paramCodedInputStream.readInt64();
            break;
          case 16:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.serverCreateRoomStart_ = paramCodedInputStream.readInt64();
            break;
          case 24:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.serverCreateRoomEnd_ = paramCodedInputStream.readInt64();
            break;
          case 32:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.serverCreateRedirectEnd_ = paramCodedInputStream.readInt64();
        }
      }

      public final HangoutStartContext.LatencyMarks buildPartial()
      {
        HangoutStartContext.LatencyMarks localLatencyMarks = new HangoutStartContext.LatencyMarks(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        HangoutStartContext.LatencyMarks.access$1302(localLatencyMarks, this.clientLaunch_);
        if ((i & 0x2) == 2)
          k |= 2;
        HangoutStartContext.LatencyMarks.access$1402(localLatencyMarks, this.serverCreateRoomStart_);
        if ((i & 0x4) == 4)
          k |= 4;
        HangoutStartContext.LatencyMarks.access$1502(localLatencyMarks, this.serverCreateRoomEnd_);
        if ((i & 0x8) == 8)
          k |= 8;
        HangoutStartContext.LatencyMarks.access$1602(localLatencyMarks, this.serverCreateRedirectEnd_);
        HangoutStartContext.LatencyMarks.access$1702(localLatencyMarks, k);
        return localLatencyMarks;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(HangoutStartContext.LatencyMarks paramLatencyMarks)
      {
        if (paramLatencyMarks == HangoutStartContext.LatencyMarks.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramLatencyMarks.hasClientLaunch())
          {
            long l4 = paramLatencyMarks.getClientLaunch();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.clientLaunch_ = l4;
          }
          if (paramLatencyMarks.hasServerCreateRoomStart())
          {
            long l3 = paramLatencyMarks.getServerCreateRoomStart();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.serverCreateRoomStart_ = l3;
          }
          if (paramLatencyMarks.hasServerCreateRoomEnd())
          {
            long l2 = paramLatencyMarks.getServerCreateRoomEnd();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.serverCreateRoomEnd_ = l2;
          }
          if (paramLatencyMarks.hasServerCreateRedirectEnd())
          {
            long l1 = paramLatencyMarks.getServerCreateRedirectEnd();
            this.bitField0_ = (0x8 | this.bitField0_);
            this.serverCreateRedirectEnd_ = l1;
          }
        }
      }
    }
  }

  public static abstract interface LatencyMarksOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static enum Source
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<Source> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      MINIBAR_START = new Source("MINIBAR_START", 2, 2);
      MINIBAR_JOIN = new Source("MINIBAR_JOIN", 3, 3);
      INVITE = new Source("INVITE", 4, 4);
      YOUTUBE = new Source("YOUTUBE", 5, 5);
      GMAIL = new Source("GMAIL", 6, 6);
      FLIPPY = new Source("FLIPPY", 7, 7);
      YOUTUBE_PARTNER_MAILOUT = new Source("YOUTUBE_PARTNER_MAILOUT", 8, 8);
      Source[] arrayOfSource = new Source[9];
      arrayOfSource[0] = SANDBAR;
      arrayOfSource[1] = STREAM;
      arrayOfSource[2] = MINIBAR_START;
      arrayOfSource[3] = MINIBAR_JOIN;
      arrayOfSource[4] = INVITE;
      arrayOfSource[5] = YOUTUBE;
      arrayOfSource[6] = GMAIL;
      arrayOfSource[7] = FLIPPY;
      arrayOfSource[8] = YOUTUBE_PARTNER_MAILOUT;
    }

    private Source(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static Source valueOf(int paramInt)
    {
      Source localSource;
      switch (paramInt)
      {
      default:
        localSource = null;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      }
      while (true)
      {
        return localSource;
        localSource = SANDBAR;
        continue;
        localSource = STREAM;
        continue;
        localSource = MINIBAR_START;
        continue;
        localSource = MINIBAR_JOIN;
        continue;
        localSource = INVITE;
        continue;
        localSource = YOUTUBE;
        continue;
        localSource = GMAIL;
        continue;
        localSource = FLIPPY;
        continue;
        localSource = YOUTUBE_PARTNER_MAILOUT;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum Type
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      EXTRAS = new Type("EXTRAS", 1, 1);
      ONAIR = new Type("ONAIR", 2, 2);
      LITE = new Type("LITE", 3, 3);
      Type[] arrayOfType = new Type[4];
      arrayOfType[0] = REGULAR;
      arrayOfType[1] = EXTRAS;
      arrayOfType[2] = ONAIR;
      arrayOfType[3] = LITE;
    }

    private Type(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static Type valueOf(int paramInt)
    {
      Type localType;
      switch (paramInt)
      {
      default:
        localType = null;
      case 0:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localType;
        localType = REGULAR;
        continue;
        localType = EXTRAS;
        continue;
        localType = ONAIR;
        continue;
        localType = LITE;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.apps.gcomm.hangout.proto.HangoutStartContext
 * JD-Core Version:    0.6.2
 */