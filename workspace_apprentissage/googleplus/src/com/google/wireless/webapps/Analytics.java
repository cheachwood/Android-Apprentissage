package com.google.wireless.webapps;

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

public final class Analytics
{
  public static final class CustomValue extends GeneratedMessageLite
    implements Analytics.CustomValueOrBuilder
  {
    private static final CustomValue defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object context_;
    private Object key_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private long timestamp_;
    private Object value_;

    static
    {
      CustomValue localCustomValue = new CustomValue();
      defaultInstance = localCustomValue;
      localCustomValue.key_ = "";
      localCustomValue.value_ = "";
      localCustomValue.context_ = "";
      localCustomValue.timestamp_ = 0L;
    }

    private CustomValue()
    {
    }

    private CustomValue(Builder paramBuilder)
    {
      super();
    }

    private ByteString getContextBytes()
    {
      Object localObject = this.context_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.context_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static CustomValue getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getKeyBytes()
    {
      Object localObject = this.key_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.key_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getValueBytes()
    {
      Object localObject = this.value_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.value_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$5100();
    }

    public final String getContext()
    {
      Object localObject1 = this.context_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.context_ = str;
      }
    }

    public final CustomValue getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getKey()
    {
      Object localObject1 = this.key_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.key_ = str;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getKeyBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getValueBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getContextBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeInt64Size(4, this.timestamp_);
        this.memoizedSerializedSize = k;
      }
    }

    public final long getTimestamp()
    {
      return this.timestamp_;
    }

    public final String getValue()
    {
      Object localObject1 = this.value_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.value_ = str;
      }
    }

    public final boolean hasContext()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasKey()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasTimestamp()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasValue()
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
        paramCodedOutputStream.writeBytes(1, getKeyBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getValueBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getContextBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeInt64(4, this.timestamp_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Analytics.CustomValue, Builder>
      implements Analytics.CustomValueOrBuilder
    {
      private int bitField0_;
      private Object context_ = "";
      private Object key_ = "";
      private long timestamp_;
      private Object value_ = "";

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
            this.key_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.value_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.context_ = paramCodedInputStream.readBytes();
            break;
          case 32:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.timestamp_ = paramCodedInputStream.readInt64();
        }
      }

      public final Analytics.CustomValue build()
      {
        Analytics.CustomValue localCustomValue = buildPartial();
        if (!localCustomValue.isInitialized())
          throw new UninitializedMessageException();
        return localCustomValue;
      }

      public final Analytics.CustomValue buildPartial()
      {
        Analytics.CustomValue localCustomValue = new Analytics.CustomValue(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Analytics.CustomValue.access$5302(localCustomValue, this.key_);
        if ((i & 0x2) == 2)
          k |= 2;
        Analytics.CustomValue.access$5402(localCustomValue, this.value_);
        if ((i & 0x4) == 4)
          k |= 4;
        Analytics.CustomValue.access$5502(localCustomValue, this.context_);
        if ((i & 0x8) == 8)
          k |= 8;
        Analytics.CustomValue.access$5602(localCustomValue, this.timestamp_);
        Analytics.CustomValue.access$5702(localCustomValue, k);
        return localCustomValue;
      }

      public final Builder clear()
      {
        super.clear();
        this.key_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.value_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.context_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.timestamp_ = 0L;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearContext()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.context_ = Analytics.CustomValue.getDefaultInstance().getContext();
        return this;
      }

      public final Builder clearKey()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.key_ = Analytics.CustomValue.getDefaultInstance().getKey();
        return this;
      }

      public final Builder clearTimestamp()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.timestamp_ = 0L;
        return this;
      }

      public final Builder clearValue()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.value_ = Analytics.CustomValue.getDefaultInstance().getValue();
        return this;
      }

      public final String getContext()
      {
        Object localObject = this.context_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.context_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Analytics.CustomValue getDefaultInstanceForType()
      {
        return Analytics.CustomValue.getDefaultInstance();
      }

      public final String getKey()
      {
        Object localObject = this.key_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.key_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final long getTimestamp()
      {
        return this.timestamp_;
      }

      public final String getValue()
      {
        Object localObject = this.value_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.value_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasContext()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasKey()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasTimestamp()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasValue()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == Analytics.CustomValue.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramCustomValue.hasKey())
            setKey(paramCustomValue.getKey());
          if (paramCustomValue.hasValue())
            setValue(paramCustomValue.getValue());
          if (paramCustomValue.hasContext())
            setContext(paramCustomValue.getContext());
          if (paramCustomValue.hasTimestamp())
            setTimestamp(paramCustomValue.getTimestamp());
        }
      }

      public final Builder setContext(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.context_ = paramString;
        return this;
      }

      public final Builder setKey(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.key_ = paramString;
        return this;
      }

      public final Builder setTimestamp(long paramLong)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.timestamp_ = paramLong;
        return this;
      }

      public final Builder setValue(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.value_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface CustomValueOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getContext();

    public abstract String getKey();

    public abstract long getTimestamp();

    public abstract String getValue();

    public abstract boolean hasContext();

    public abstract boolean hasKey();

    public abstract boolean hasTimestamp();

    public abstract boolean hasValue();
  }

  public static final class Event extends GeneratedMessageLite
    implements Analytics.EventOrBuilder
  {
    private static final Event defaultInstance;
    private static final long serialVersionUID;
    private Analytics.UniqueId action_;
    private int bitField0_;
    private List<Analytics.CustomValue> customValue_;
    private Analytics.EventDetails details_;
    private long endTimeMsec_;
    private long endTime_;
    private EventType eventType_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private long startTimeMsec_;
    private long startTime_;
    private List<Step> step_;

    static
    {
      Event localEvent = new Event();
      defaultInstance = localEvent;
      localEvent.eventType_ = EventType.UNSPECIFIED;
      localEvent.action_ = Analytics.UniqueId.getDefaultInstance();
      localEvent.startTime_ = 0L;
      localEvent.endTime_ = 0L;
      localEvent.startTimeMsec_ = 0L;
      localEvent.endTimeMsec_ = 0L;
      localEvent.step_ = Collections.emptyList();
      localEvent.details_ = Analytics.EventDetails.getDefaultInstance();
      localEvent.customValue_ = Collections.emptyList();
    }

    private Event()
    {
    }

    private Event(Builder paramBuilder)
    {
      super();
    }

    public static Event getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$2100();
    }

    public final Analytics.UniqueId getAction()
    {
      return this.action_;
    }

    public final Analytics.CustomValue getCustomValue(int paramInt)
    {
      return (Analytics.CustomValue)this.customValue_.get(paramInt);
    }

    public final int getCustomValueCount()
    {
      return this.customValue_.size();
    }

    public final List<Analytics.CustomValue> getCustomValueList()
    {
      return this.customValue_;
    }

    public final Analytics.CustomValueOrBuilder getCustomValueOrBuilder(int paramInt)
    {
      return (Analytics.CustomValueOrBuilder)this.customValue_.get(paramInt);
    }

    public final List<? extends Analytics.CustomValueOrBuilder> getCustomValueOrBuilderList()
    {
      return this.customValue_;
    }

    public final Event getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final Analytics.EventDetails getDetails()
    {
      return this.details_;
    }

    @Deprecated
    public final long getEndTime()
    {
      return this.endTime_;
    }

    public final long getEndTimeMsec()
    {
      return this.endTimeMsec_;
    }

    public final EventType getEventType()
    {
      return this.eventType_;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int k;
      for (int i1 = i; ; i1 = k)
      {
        return i1;
        int j = 0x1 & this.bitField0_;
        k = 0;
        if (j == 1)
          k = 0 + CodedOutputStream.computeEnumSize(1, this.eventType_.getNumber());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeMessageSize(2, this.action_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeInt64Size(3, this.startTime_);
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeInt64Size(4, this.endTime_);
        for (int m = 0; m < this.step_.size(); m++)
          k += CodedOutputStream.computeMessageSize(5, (MessageLite)this.step_.get(m));
        if ((0x40 & this.bitField0_) == 64)
          k += CodedOutputStream.computeMessageSize(6, this.details_);
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeInt64Size(7, this.startTimeMsec_);
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeInt64Size(8, this.endTimeMsec_);
        for (int n = 0; n < this.customValue_.size(); n++)
          k += CodedOutputStream.computeMessageSize(9, (MessageLite)this.customValue_.get(n));
        this.memoizedSerializedSize = k;
      }
    }

    @Deprecated
    public final long getStartTime()
    {
      return this.startTime_;
    }

    public final long getStartTimeMsec()
    {
      return this.startTimeMsec_;
    }

    public final Step getStep(int paramInt)
    {
      return (Step)this.step_.get(paramInt);
    }

    public final int getStepCount()
    {
      return this.step_.size();
    }

    public final List<Step> getStepList()
    {
      return this.step_;
    }

    public final StepOrBuilder getStepOrBuilder(int paramInt)
    {
      return (StepOrBuilder)this.step_.get(paramInt);
    }

    public final List<? extends StepOrBuilder> getStepOrBuilderList()
    {
      return this.step_;
    }

    public final boolean hasAction()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDetails()
    {
      if ((0x40 & this.bitField0_) == 64);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    @Deprecated
    public final boolean hasEndTime()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasEndTimeMsec()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasEventType()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    @Deprecated
    public final boolean hasStartTime()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasStartTimeMsec()
    {
      if ((0x10 & this.bitField0_) == 16);
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
        paramCodedOutputStream.writeEnum(1, this.eventType_.getNumber());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeMessage(2, this.action_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeInt64(3, this.startTime_);
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeInt64(4, this.endTime_);
      for (int i = 0; i < this.step_.size(); i++)
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.step_.get(i));
      if ((0x40 & this.bitField0_) == 64)
        paramCodedOutputStream.writeMessage(6, this.details_);
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeInt64(7, this.startTimeMsec_);
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeInt64(8, this.endTimeMsec_);
      for (int j = 0; j < this.customValue_.size(); j++)
        paramCodedOutputStream.writeMessage(9, (MessageLite)this.customValue_.get(j));
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Analytics.Event, Builder>
      implements Analytics.EventOrBuilder
    {
      private Analytics.UniqueId action_ = Analytics.UniqueId.getDefaultInstance();
      private int bitField0_;
      private List<Analytics.CustomValue> customValue_ = Collections.emptyList();
      private Analytics.EventDetails details_ = Analytics.EventDetails.getDefaultInstance();
      private long endTimeMsec_;
      private long endTime_;
      private Analytics.Event.EventType eventType_ = Analytics.Event.EventType.UNSPECIFIED;
      private long startTimeMsec_;
      private long startTime_;
      private List<Analytics.Event.Step> step_ = Collections.emptyList();

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureCustomValueIsMutable()
      {
        if ((0x100 & this.bitField0_) != 256)
        {
          this.customValue_ = new ArrayList(this.customValue_);
          this.bitField0_ = (0x100 | this.bitField0_);
        }
      }

      private void ensureStepIsMutable()
      {
        if ((0x40 & this.bitField0_) != 64)
        {
          this.step_ = new ArrayList(this.step_);
          this.bitField0_ = (0x40 | this.bitField0_);
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
            Analytics.Event.EventType localEventType = Analytics.Event.EventType.valueOf(paramCodedInputStream.readEnum());
            if (localEventType == null)
              continue;
            this.bitField0_ = (0x1 | this.bitField0_);
            this.eventType_ = localEventType;
            break;
          case 18:
            Analytics.UniqueId.Builder localBuilder3 = Analytics.UniqueId.newBuilder();
            if (hasAction())
              localBuilder3.mergeFrom(getAction());
            paramCodedInputStream.readMessage(localBuilder3, paramExtensionRegistryLite);
            setAction(localBuilder3.buildPartial());
            break;
          case 24:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.startTime_ = paramCodedInputStream.readInt64();
            break;
          case 32:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.endTime_ = paramCodedInputStream.readInt64();
            break;
          case 42:
            Analytics.Event.Step.Builder localBuilder2 = Analytics.Event.Step.newBuilder();
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            addStep(localBuilder2.buildPartial());
            break;
          case 50:
            Analytics.EventDetails.Builder localBuilder1 = Analytics.EventDetails.newBuilder();
            if (hasDetails())
              localBuilder1.mergeFrom(getDetails());
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            setDetails(localBuilder1.buildPartial());
            break;
          case 56:
            this.bitField0_ = (0x10 | this.bitField0_);
            this.startTimeMsec_ = paramCodedInputStream.readInt64();
            break;
          case 64:
            this.bitField0_ = (0x20 | this.bitField0_);
            this.endTimeMsec_ = paramCodedInputStream.readInt64();
            break;
          case 74:
          }
          Analytics.CustomValue.Builder localBuilder = Analytics.CustomValue.newBuilder();
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          addCustomValue(localBuilder.buildPartial());
        }
      }

      public final Builder addAllCustomValue(Iterable<? extends Analytics.CustomValue> paramIterable)
      {
        ensureCustomValueIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.customValue_);
        return this;
      }

      public final Builder addAllStep(Iterable<? extends Analytics.Event.Step> paramIterable)
      {
        ensureStepIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.step_);
        return this;
      }

      public final Builder addCustomValue(int paramInt, Analytics.CustomValue.Builder paramBuilder)
      {
        ensureCustomValueIsMutable();
        this.customValue_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addCustomValue(int paramInt, Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == null)
          throw new NullPointerException();
        ensureCustomValueIsMutable();
        this.customValue_.add(paramInt, paramCustomValue);
        return this;
      }

      public final Builder addCustomValue(Analytics.CustomValue.Builder paramBuilder)
      {
        ensureCustomValueIsMutable();
        this.customValue_.add(paramBuilder.build());
        return this;
      }

      public final Builder addCustomValue(Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == null)
          throw new NullPointerException();
        ensureCustomValueIsMutable();
        this.customValue_.add(paramCustomValue);
        return this;
      }

      public final Builder addStep(int paramInt, Analytics.Event.Step.Builder paramBuilder)
      {
        ensureStepIsMutable();
        this.step_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addStep(int paramInt, Analytics.Event.Step paramStep)
      {
        if (paramStep == null)
          throw new NullPointerException();
        ensureStepIsMutable();
        this.step_.add(paramInt, paramStep);
        return this;
      }

      public final Builder addStep(Analytics.Event.Step.Builder paramBuilder)
      {
        ensureStepIsMutable();
        this.step_.add(paramBuilder.build());
        return this;
      }

      public final Builder addStep(Analytics.Event.Step paramStep)
      {
        if (paramStep == null)
          throw new NullPointerException();
        ensureStepIsMutable();
        this.step_.add(paramStep);
        return this;
      }

      public final Analytics.Event build()
      {
        Analytics.Event localEvent = buildPartial();
        if (!localEvent.isInitialized())
          throw new UninitializedMessageException();
        return localEvent;
      }

      public final Analytics.Event buildPartial()
      {
        Analytics.Event localEvent = new Analytics.Event(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Analytics.Event.access$2302(localEvent, this.eventType_);
        if ((i & 0x2) == 2)
          k |= 2;
        Analytics.Event.access$2402(localEvent, this.action_);
        if ((i & 0x4) == 4)
          k |= 4;
        Analytics.Event.access$2502(localEvent, this.startTime_);
        if ((i & 0x8) == 8)
          k |= 8;
        Analytics.Event.access$2602(localEvent, this.endTime_);
        if ((i & 0x10) == 16)
          k |= 16;
        Analytics.Event.access$2702(localEvent, this.startTimeMsec_);
        if ((i & 0x20) == 32)
          k |= 32;
        Analytics.Event.access$2802(localEvent, this.endTimeMsec_);
        if ((0x40 & this.bitField0_) == 64)
        {
          this.step_ = Collections.unmodifiableList(this.step_);
          this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        }
        Analytics.Event.access$2902(localEvent, this.step_);
        if ((i & 0x80) == 128)
          k |= 64;
        Analytics.Event.access$3002(localEvent, this.details_);
        if ((0x100 & this.bitField0_) == 256)
        {
          this.customValue_ = Collections.unmodifiableList(this.customValue_);
          this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        }
        Analytics.Event.access$3102(localEvent, this.customValue_);
        Analytics.Event.access$3202(localEvent, k);
        return localEvent;
      }

      public final Builder clear()
      {
        super.clear();
        this.eventType_ = Analytics.Event.EventType.UNSPECIFIED;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.action_ = Analytics.UniqueId.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.startTime_ = 0L;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.endTime_ = 0L;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.startTimeMsec_ = 0L;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.endTimeMsec_ = 0L;
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.step_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.details_ = Analytics.EventDetails.getDefaultInstance();
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        this.customValue_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        return this;
      }

      public final Builder clearAction()
      {
        this.action_ = Analytics.UniqueId.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearCustomValue()
      {
        this.customValue_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        return this;
      }

      public final Builder clearDetails()
      {
        this.details_ = Analytics.EventDetails.getDefaultInstance();
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        return this;
      }

      @Deprecated
      public final Builder clearEndTime()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.endTime_ = 0L;
        return this;
      }

      public final Builder clearEndTimeMsec()
      {
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.endTimeMsec_ = 0L;
        return this;
      }

      public final Builder clearEventType()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.eventType_ = Analytics.Event.EventType.UNSPECIFIED;
        return this;
      }

      @Deprecated
      public final Builder clearStartTime()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.startTime_ = 0L;
        return this;
      }

      public final Builder clearStartTimeMsec()
      {
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.startTimeMsec_ = 0L;
        return this;
      }

      public final Builder clearStep()
      {
        this.step_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        return this;
      }

      public final Analytics.UniqueId getAction()
      {
        return this.action_;
      }

      public final Analytics.CustomValue getCustomValue(int paramInt)
      {
        return (Analytics.CustomValue)this.customValue_.get(paramInt);
      }

      public final int getCustomValueCount()
      {
        return this.customValue_.size();
      }

      public final List<Analytics.CustomValue> getCustomValueList()
      {
        return Collections.unmodifiableList(this.customValue_);
      }

      public final Analytics.Event getDefaultInstanceForType()
      {
        return Analytics.Event.getDefaultInstance();
      }

      public final Analytics.EventDetails getDetails()
      {
        return this.details_;
      }

      @Deprecated
      public final long getEndTime()
      {
        return this.endTime_;
      }

      public final long getEndTimeMsec()
      {
        return this.endTimeMsec_;
      }

      public final Analytics.Event.EventType getEventType()
      {
        return this.eventType_;
      }

      @Deprecated
      public final long getStartTime()
      {
        return this.startTime_;
      }

      public final long getStartTimeMsec()
      {
        return this.startTimeMsec_;
      }

      public final Analytics.Event.Step getStep(int paramInt)
      {
        return (Analytics.Event.Step)this.step_.get(paramInt);
      }

      public final int getStepCount()
      {
        return this.step_.size();
      }

      public final List<Analytics.Event.Step> getStepList()
      {
        return Collections.unmodifiableList(this.step_);
      }

      public final boolean hasAction()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasDetails()
      {
        if ((0x80 & this.bitField0_) == 128);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      @Deprecated
      public final boolean hasEndTime()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasEndTimeMsec()
      {
        if ((0x20 & this.bitField0_) == 32);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasEventType()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      @Deprecated
      public final boolean hasStartTime()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasStartTimeMsec()
      {
        if ((0x10 & this.bitField0_) == 16);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Analytics.Event paramEvent)
      {
        if (paramEvent == Analytics.Event.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramEvent.hasEventType())
            setEventType(paramEvent.getEventType());
          Analytics.UniqueId localUniqueId;
          label77: Analytics.EventDetails localEventDetails;
          if (paramEvent.hasAction())
          {
            localUniqueId = paramEvent.getAction();
            if (((0x2 & this.bitField0_) == 2) && (this.action_ != Analytics.UniqueId.getDefaultInstance()))
            {
              this.action_ = Analytics.UniqueId.newBuilder(this.action_).mergeFrom(localUniqueId).buildPartial();
              this.bitField0_ = (0x2 | this.bitField0_);
            }
          }
          else
          {
            if (paramEvent.hasStartTime())
              setStartTime(paramEvent.getStartTime());
            if (paramEvent.hasEndTime())
              setEndTime(paramEvent.getEndTime());
            if (paramEvent.hasStartTimeMsec())
              setStartTimeMsec(paramEvent.getStartTimeMsec());
            if (paramEvent.hasEndTimeMsec())
              setEndTimeMsec(paramEvent.getEndTimeMsec());
            if (!paramEvent.step_.isEmpty())
            {
              if (!this.step_.isEmpty())
                break label316;
              this.step_ = paramEvent.step_;
              this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
            }
            label194: if (paramEvent.hasDetails())
            {
              localEventDetails = paramEvent.getDetails();
              if (((0x80 & this.bitField0_) != 128) || (this.details_ == Analytics.EventDetails.getDefaultInstance()))
                break label337;
            }
          }
          label316: label337: for (this.details_ = Analytics.EventDetails.newBuilder(this.details_).mergeFrom(localEventDetails).buildPartial(); ; this.details_ = localEventDetails)
          {
            this.bitField0_ = (0x80 | this.bitField0_);
            if (paramEvent.customValue_.isEmpty())
              break;
            if (!this.customValue_.isEmpty())
              break label345;
            this.customValue_ = paramEvent.customValue_;
            this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
            break;
            this.action_ = localUniqueId;
            break label77;
            ensureStepIsMutable();
            this.step_.addAll(paramEvent.step_);
            break label194;
          }
          label345: ensureCustomValueIsMutable();
          this.customValue_.addAll(paramEvent.customValue_);
        }
      }

      public final Builder setAction(Analytics.UniqueId.Builder paramBuilder)
      {
        this.action_ = paramBuilder.build();
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setAction(Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        this.action_ = paramUniqueId;
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setCustomValue(int paramInt, Analytics.CustomValue.Builder paramBuilder)
      {
        ensureCustomValueIsMutable();
        this.customValue_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setCustomValue(int paramInt, Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == null)
          throw new NullPointerException();
        ensureCustomValueIsMutable();
        this.customValue_.set(paramInt, paramCustomValue);
        return this;
      }

      public final Builder setDetails(Analytics.EventDetails.Builder paramBuilder)
      {
        this.details_ = paramBuilder.build();
        this.bitField0_ = (0x80 | this.bitField0_);
        return this;
      }

      public final Builder setDetails(Analytics.EventDetails paramEventDetails)
      {
        if (paramEventDetails == null)
          throw new NullPointerException();
        this.details_ = paramEventDetails;
        this.bitField0_ = (0x80 | this.bitField0_);
        return this;
      }

      @Deprecated
      public final Builder setEndTime(long paramLong)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.endTime_ = paramLong;
        return this;
      }

      public final Builder setEndTimeMsec(long paramLong)
      {
        this.bitField0_ = (0x20 | this.bitField0_);
        this.endTimeMsec_ = paramLong;
        return this;
      }

      public final Builder setEventType(Analytics.Event.EventType paramEventType)
      {
        if (paramEventType == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.eventType_ = paramEventType;
        return this;
      }

      @Deprecated
      public final Builder setStartTime(long paramLong)
      {
        this.bitField0_ = (0x4 | this.bitField0_);
        this.startTime_ = paramLong;
        return this;
      }

      public final Builder setStartTimeMsec(long paramLong)
      {
        this.bitField0_ = (0x10 | this.bitField0_);
        this.startTimeMsec_ = paramLong;
        return this;
      }

      public final Builder setStep(int paramInt, Analytics.Event.Step.Builder paramBuilder)
      {
        ensureStepIsMutable();
        this.step_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setStep(int paramInt, Analytics.Event.Step paramStep)
      {
        if (paramStep == null)
          throw new NullPointerException();
        ensureStepIsMutable();
        this.step_.set(paramInt, paramStep);
        return this;
      }
    }

    public static enum EventType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<EventType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        SYSTEM = new EventType("SYSTEM", 1, 1);
        USER = new EventType("USER", 2, 2);
        EventType[] arrayOfEventType = new EventType[3];
        arrayOfEventType[0] = UNSPECIFIED;
        arrayOfEventType[1] = SYSTEM;
        arrayOfEventType[2] = USER;
      }

      private EventType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static EventType valueOf(int paramInt)
      {
        EventType localEventType;
        switch (paramInt)
        {
        default:
          localEventType = null;
        case 0:
        case 1:
        case 2:
        }
        while (true)
        {
          return localEventType;
          localEventType = UNSPECIFIED;
          continue;
          localEventType = SYSTEM;
          continue;
          localEventType = USER;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static final class Step extends GeneratedMessageLite
      implements Analytics.Event.StepOrBuilder
    {
      private static final Step defaultInstance;
      private static final long serialVersionUID;
      private int bitField0_;
      private long endTimeMsec_;
      private long endTime_;
      private byte memoizedIsInitialized = -1;
      private int memoizedSerializedSize = -1;
      private Object name_;
      private long startTimeMsec_;
      private long startTime_;

      static
      {
        Step localStep = new Step();
        defaultInstance = localStep;
        localStep.name_ = "";
        localStep.startTime_ = 0L;
        localStep.endTime_ = 0L;
        localStep.startTimeMsec_ = 0L;
        localStep.endTimeMsec_ = 0L;
      }

      private Step()
      {
      }

      private Step(Builder paramBuilder)
      {
        super();
      }

      public static Step getDefaultInstance()
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

      public static Builder newBuilder()
      {
        return Builder.access$1200();
      }

      public final Step getDefaultInstanceForType()
      {
        return defaultInstance;
      }

      @Deprecated
      public final long getEndTime()
      {
        return this.endTime_;
      }

      public final long getEndTimeMsec()
      {
        return this.endTimeMsec_;
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
            k += CodedOutputStream.computeInt64Size(2, this.startTime_);
          if ((0x4 & this.bitField0_) == 4)
            k += CodedOutputStream.computeInt64Size(3, this.endTime_);
          if ((0x8 & this.bitField0_) == 8)
            k += CodedOutputStream.computeInt64Size(4, this.startTimeMsec_);
          if ((0x10 & this.bitField0_) == 16)
            k += CodedOutputStream.computeInt64Size(5, this.endTimeMsec_);
          this.memoizedSerializedSize = k;
        }
      }

      @Deprecated
      public final long getStartTime()
      {
        return this.startTime_;
      }

      public final long getStartTimeMsec()
      {
        return this.startTimeMsec_;
      }

      @Deprecated
      public final boolean hasEndTime()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasEndTimeMsec()
      {
        if ((0x10 & this.bitField0_) == 16);
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

      @Deprecated
      public final boolean hasStartTime()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasStartTimeMsec()
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
          paramCodedOutputStream.writeBytes(1, getNameBytes());
        if ((0x2 & this.bitField0_) == 2)
          paramCodedOutputStream.writeInt64(2, this.startTime_);
        if ((0x4 & this.bitField0_) == 4)
          paramCodedOutputStream.writeInt64(3, this.endTime_);
        if ((0x8 & this.bitField0_) == 8)
          paramCodedOutputStream.writeInt64(4, this.startTimeMsec_);
        if ((0x10 & this.bitField0_) == 16)
          paramCodedOutputStream.writeInt64(5, this.endTimeMsec_);
      }

      public static final class Builder extends GeneratedMessageLite.Builder<Analytics.Event.Step, Builder>
        implements Analytics.Event.StepOrBuilder
      {
        private int bitField0_;
        private long endTimeMsec_;
        private long endTime_;
        private Object name_ = "";
        private long startTimeMsec_;
        private long startTime_;

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
            case 16:
              this.bitField0_ = (0x2 | this.bitField0_);
              this.startTime_ = paramCodedInputStream.readInt64();
              break;
            case 24:
              this.bitField0_ = (0x4 | this.bitField0_);
              this.endTime_ = paramCodedInputStream.readInt64();
              break;
            case 32:
              this.bitField0_ = (0x8 | this.bitField0_);
              this.startTimeMsec_ = paramCodedInputStream.readInt64();
              break;
            case 40:
            }
            this.bitField0_ = (0x10 | this.bitField0_);
            this.endTimeMsec_ = paramCodedInputStream.readInt64();
          }
        }

        public final Analytics.Event.Step build()
        {
          Analytics.Event.Step localStep = buildPartial();
          if (!localStep.isInitialized())
            throw new UninitializedMessageException();
          return localStep;
        }

        public final Analytics.Event.Step buildPartial()
        {
          Analytics.Event.Step localStep = new Analytics.Event.Step(this, (byte)0);
          int i = this.bitField0_;
          int j = i & 0x1;
          int k = 0;
          if (j == 1)
            k = 1;
          Analytics.Event.Step.access$1402(localStep, this.name_);
          if ((i & 0x2) == 2)
            k |= 2;
          Analytics.Event.Step.access$1502(localStep, this.startTime_);
          if ((i & 0x4) == 4)
            k |= 4;
          Analytics.Event.Step.access$1602(localStep, this.endTime_);
          if ((i & 0x8) == 8)
            k |= 8;
          Analytics.Event.Step.access$1702(localStep, this.startTimeMsec_);
          if ((i & 0x10) == 16)
            k |= 16;
          Analytics.Event.Step.access$1802(localStep, this.endTimeMsec_);
          Analytics.Event.Step.access$1902(localStep, k);
          return localStep;
        }

        public final Builder clear()
        {
          super.clear();
          this.name_ = "";
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.startTime_ = 0L;
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          this.endTime_ = 0L;
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          this.startTimeMsec_ = 0L;
          this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
          this.endTimeMsec_ = 0L;
          this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
          return this;
        }

        @Deprecated
        public final Builder clearEndTime()
        {
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          this.endTime_ = 0L;
          return this;
        }

        public final Builder clearEndTimeMsec()
        {
          this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
          this.endTimeMsec_ = 0L;
          return this;
        }

        public final Builder clearName()
        {
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          this.name_ = Analytics.Event.Step.getDefaultInstance().getName();
          return this;
        }

        @Deprecated
        public final Builder clearStartTime()
        {
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          this.startTime_ = 0L;
          return this;
        }

        public final Builder clearStartTimeMsec()
        {
          this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
          this.startTimeMsec_ = 0L;
          return this;
        }

        public final Analytics.Event.Step getDefaultInstanceForType()
        {
          return Analytics.Event.Step.getDefaultInstance();
        }

        @Deprecated
        public final long getEndTime()
        {
          return this.endTime_;
        }

        public final long getEndTimeMsec()
        {
          return this.endTimeMsec_;
        }

        public final String getName()
        {
          Object localObject = this.name_;
          String str;
          if (!(localObject instanceof String))
          {
            str = ((ByteString)localObject).toStringUtf8();
            this.name_ = str;
          }
          while (true)
          {
            return str;
            str = (String)localObject;
          }
        }

        @Deprecated
        public final long getStartTime()
        {
          return this.startTime_;
        }

        public final long getStartTimeMsec()
        {
          return this.startTimeMsec_;
        }

        @Deprecated
        public final boolean hasEndTime()
        {
          if ((0x4 & this.bitField0_) == 4);
          for (boolean bool = true; ; bool = false)
            return bool;
        }

        public final boolean hasEndTimeMsec()
        {
          if ((0x10 & this.bitField0_) == 16);
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

        @Deprecated
        public final boolean hasStartTime()
        {
          if ((0x2 & this.bitField0_) == 2);
          for (boolean bool = true; ; bool = false)
            return bool;
        }

        public final boolean hasStartTimeMsec()
        {
          if ((0x8 & this.bitField0_) == 8);
          for (boolean bool = true; ; bool = false)
            return bool;
        }

        public final boolean isInitialized()
        {
          return true;
        }

        public final Builder mergeFrom(Analytics.Event.Step paramStep)
        {
          if (paramStep == Analytics.Event.Step.getDefaultInstance());
          while (true)
          {
            return this;
            if (paramStep.hasName())
              setName(paramStep.getName());
            if (paramStep.hasStartTime())
              setStartTime(paramStep.getStartTime());
            if (paramStep.hasEndTime())
              setEndTime(paramStep.getEndTime());
            if (paramStep.hasStartTimeMsec())
              setStartTimeMsec(paramStep.getStartTimeMsec());
            if (paramStep.hasEndTimeMsec())
              setEndTimeMsec(paramStep.getEndTimeMsec());
          }
        }

        @Deprecated
        public final Builder setEndTime(long paramLong)
        {
          this.bitField0_ = (0x4 | this.bitField0_);
          this.endTime_ = paramLong;
          return this;
        }

        public final Builder setEndTimeMsec(long paramLong)
        {
          this.bitField0_ = (0x10 | this.bitField0_);
          this.endTimeMsec_ = paramLong;
          return this;
        }

        public final Builder setName(String paramString)
        {
          if (paramString == null)
            throw new NullPointerException();
          this.bitField0_ = (0x1 | this.bitField0_);
          this.name_ = paramString;
          return this;
        }

        @Deprecated
        public final Builder setStartTime(long paramLong)
        {
          this.bitField0_ = (0x2 | this.bitField0_);
          this.startTime_ = paramLong;
          return this;
        }

        public final Builder setStartTimeMsec(long paramLong)
        {
          this.bitField0_ = (0x8 | this.bitField0_);
          this.startTimeMsec_ = paramLong;
          return this;
        }
      }
    }

    public static abstract interface StepOrBuilder extends MessageLiteOrBuilder
    {
      @Deprecated
      public abstract long getEndTime();

      public abstract long getEndTimeMsec();

      public abstract String getName();

      @Deprecated
      public abstract long getStartTime();

      public abstract long getStartTimeMsec();

      @Deprecated
      public abstract boolean hasEndTime();

      public abstract boolean hasEndTimeMsec();

      public abstract boolean hasName();

      @Deprecated
      public abstract boolean hasStartTime();

      public abstract boolean hasStartTimeMsec();
    }
  }

  public static final class EventDetails extends GeneratedMessageLite
    implements Analytics.EventDetailsOrBuilder
  {
    private static final EventDetails defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private int count_;
    private Analytics.UniqueId endView_;
    private List<Analytics.UniqueId> errorCode_;
    private List<Long> favaRequestId_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Analytics.UniqueId startView_;
    private List<Analytics.UniqueId> target_;

    static
    {
      EventDetails localEventDetails = new EventDetails();
      defaultInstance = localEventDetails;
      localEventDetails.startView_ = Analytics.UniqueId.getDefaultInstance();
      localEventDetails.endView_ = Analytics.UniqueId.getDefaultInstance();
      localEventDetails.target_ = Collections.emptyList();
      localEventDetails.count_ = 0;
      localEventDetails.errorCode_ = Collections.emptyList();
      localEventDetails.favaRequestId_ = Collections.emptyList();
    }

    private EventDetails()
    {
    }

    private EventDetails(Builder paramBuilder)
    {
      super();
    }

    public static EventDetails getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$3400();
    }

    public static Builder newBuilder(EventDetails paramEventDetails)
    {
      return Builder.access$3400().mergeFrom(paramEventDetails);
    }

    public final int getCount()
    {
      return this.count_;
    }

    public final EventDetails getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final Analytics.UniqueId getEndView()
    {
      return this.endView_;
    }

    public final Analytics.UniqueId getErrorCode(int paramInt)
    {
      return (Analytics.UniqueId)this.errorCode_.get(paramInt);
    }

    public final int getErrorCodeCount()
    {
      return this.errorCode_.size();
    }

    public final List<Analytics.UniqueId> getErrorCodeList()
    {
      return this.errorCode_;
    }

    public final Analytics.UniqueIdOrBuilder getErrorCodeOrBuilder(int paramInt)
    {
      return (Analytics.UniqueIdOrBuilder)this.errorCode_.get(paramInt);
    }

    public final List<? extends Analytics.UniqueIdOrBuilder> getErrorCodeOrBuilderList()
    {
      return this.errorCode_;
    }

    public final long getFavaRequestId(int paramInt)
    {
      return ((Long)this.favaRequestId_.get(paramInt)).longValue();
    }

    public final int getFavaRequestIdCount()
    {
      return this.favaRequestId_.size();
    }

    public final List<Long> getFavaRequestIdList()
    {
      return this.favaRequestId_;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.startView_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeMessageSize(2, this.endView_);
        for (int m = 0; m < this.target_.size(); m++)
          k += CodedOutputStream.computeMessageSize(3, (MessageLite)this.target_.get(m));
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeInt32Size(4, this.count_);
        for (int n = 0; n < this.errorCode_.size(); n++)
          k += CodedOutputStream.computeMessageSize(5, (MessageLite)this.errorCode_.get(n));
        int i1 = 0;
        for (int i2 = 0; i2 < this.favaRequestId_.size(); i2++)
          i1 += CodedOutputStream.computeRawVarint64Size(((Long)this.favaRequestId_.get(i2)).longValue());
        i3 = k + i1 + 1 * getFavaRequestIdList().size();
        this.memoizedSerializedSize = i3;
      }
    }

    public final Analytics.UniqueId getStartView()
    {
      return this.startView_;
    }

    public final Analytics.UniqueId getTarget(int paramInt)
    {
      return (Analytics.UniqueId)this.target_.get(paramInt);
    }

    public final int getTargetCount()
    {
      return this.target_.size();
    }

    public final List<Analytics.UniqueId> getTargetList()
    {
      return this.target_;
    }

    public final Analytics.UniqueIdOrBuilder getTargetOrBuilder(int paramInt)
    {
      return (Analytics.UniqueIdOrBuilder)this.target_.get(paramInt);
    }

    public final List<? extends Analytics.UniqueIdOrBuilder> getTargetOrBuilderList()
    {
      return this.target_;
    }

    public final boolean hasCount()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasEndView()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasStartView()
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
        paramCodedOutputStream.writeMessage(1, this.startView_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeMessage(2, this.endView_);
      for (int i = 0; i < this.target_.size(); i++)
        paramCodedOutputStream.writeMessage(3, (MessageLite)this.target_.get(i));
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeInt32(4, this.count_);
      for (int j = 0; j < this.errorCode_.size(); j++)
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.errorCode_.get(j));
      for (int k = 0; k < this.favaRequestId_.size(); k++)
        paramCodedOutputStream.writeInt64(6, ((Long)this.favaRequestId_.get(k)).longValue());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Analytics.EventDetails, Builder>
      implements Analytics.EventDetailsOrBuilder
    {
      private int bitField0_;
      private int count_;
      private Analytics.UniqueId endView_ = Analytics.UniqueId.getDefaultInstance();
      private List<Analytics.UniqueId> errorCode_ = Collections.emptyList();
      private List<Long> favaRequestId_ = Collections.emptyList();
      private Analytics.UniqueId startView_ = Analytics.UniqueId.getDefaultInstance();
      private List<Analytics.UniqueId> target_ = Collections.emptyList();

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureErrorCodeIsMutable()
      {
        if ((0x10 & this.bitField0_) != 16)
        {
          this.errorCode_ = new ArrayList(this.errorCode_);
          this.bitField0_ = (0x10 | this.bitField0_);
        }
      }

      private void ensureFavaRequestIdIsMutable()
      {
        if ((0x20 & this.bitField0_) != 32)
        {
          this.favaRequestId_ = new ArrayList(this.favaRequestId_);
          this.bitField0_ = (0x20 | this.bitField0_);
        }
      }

      private void ensureTargetIsMutable()
      {
        if ((0x4 & this.bitField0_) != 4)
        {
          this.target_ = new ArrayList(this.target_);
          this.bitField0_ = (0x4 | this.bitField0_);
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
            Analytics.UniqueId.Builder localBuilder4 = Analytics.UniqueId.newBuilder();
            if (hasStartView())
              localBuilder4.mergeFrom(getStartView());
            paramCodedInputStream.readMessage(localBuilder4, paramExtensionRegistryLite);
            setStartView(localBuilder4.buildPartial());
            break;
          case 18:
            Analytics.UniqueId.Builder localBuilder3 = Analytics.UniqueId.newBuilder();
            if (hasEndView())
              localBuilder3.mergeFrom(getEndView());
            paramCodedInputStream.readMessage(localBuilder3, paramExtensionRegistryLite);
            setEndView(localBuilder3.buildPartial());
            break;
          case 26:
            Analytics.UniqueId.Builder localBuilder2 = Analytics.UniqueId.newBuilder();
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            addTarget(localBuilder2.buildPartial());
            break;
          case 32:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.count_ = paramCodedInputStream.readInt32();
            break;
          case 42:
            Analytics.UniqueId.Builder localBuilder1 = Analytics.UniqueId.newBuilder();
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            addErrorCode(localBuilder1.buildPartial());
            break;
          case 48:
            ensureFavaRequestIdIsMutable();
            this.favaRequestId_.add(Long.valueOf(paramCodedInputStream.readInt64()));
            break;
          case 50:
          }
          int j = paramCodedInputStream.pushLimit(paramCodedInputStream.readRawVarint32());
          while (paramCodedInputStream.getBytesUntilLimit() > 0)
            addFavaRequestId(paramCodedInputStream.readInt64());
          paramCodedInputStream.popLimit(j);
        }
      }

      public final Builder addAllErrorCode(Iterable<? extends Analytics.UniqueId> paramIterable)
      {
        ensureErrorCodeIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.errorCode_);
        return this;
      }

      public final Builder addAllFavaRequestId(Iterable<? extends Long> paramIterable)
      {
        ensureFavaRequestIdIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.favaRequestId_);
        return this;
      }

      public final Builder addAllTarget(Iterable<? extends Analytics.UniqueId> paramIterable)
      {
        ensureTargetIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.target_);
        return this;
      }

      public final Builder addErrorCode(int paramInt, Analytics.UniqueId.Builder paramBuilder)
      {
        ensureErrorCodeIsMutable();
        this.errorCode_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addErrorCode(int paramInt, Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        ensureErrorCodeIsMutable();
        this.errorCode_.add(paramInt, paramUniqueId);
        return this;
      }

      public final Builder addErrorCode(Analytics.UniqueId.Builder paramBuilder)
      {
        ensureErrorCodeIsMutable();
        this.errorCode_.add(paramBuilder.build());
        return this;
      }

      public final Builder addErrorCode(Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        ensureErrorCodeIsMutable();
        this.errorCode_.add(paramUniqueId);
        return this;
      }

      public final Builder addFavaRequestId(long paramLong)
      {
        ensureFavaRequestIdIsMutable();
        this.favaRequestId_.add(Long.valueOf(paramLong));
        return this;
      }

      public final Builder addTarget(int paramInt, Analytics.UniqueId.Builder paramBuilder)
      {
        ensureTargetIsMutable();
        this.target_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addTarget(int paramInt, Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        ensureTargetIsMutable();
        this.target_.add(paramInt, paramUniqueId);
        return this;
      }

      public final Builder addTarget(Analytics.UniqueId.Builder paramBuilder)
      {
        ensureTargetIsMutable();
        this.target_.add(paramBuilder.build());
        return this;
      }

      public final Builder addTarget(Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        ensureTargetIsMutable();
        this.target_.add(paramUniqueId);
        return this;
      }

      public final Analytics.EventDetails build()
      {
        Analytics.EventDetails localEventDetails = buildPartial();
        if (!localEventDetails.isInitialized())
          throw new UninitializedMessageException();
        return localEventDetails;
      }

      public final Analytics.EventDetails buildPartial()
      {
        Analytics.EventDetails localEventDetails = new Analytics.EventDetails(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Analytics.EventDetails.access$3602(localEventDetails, this.startView_);
        if ((i & 0x2) == 2)
          k |= 2;
        Analytics.EventDetails.access$3702(localEventDetails, this.endView_);
        if ((0x4 & this.bitField0_) == 4)
        {
          this.target_ = Collections.unmodifiableList(this.target_);
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        }
        Analytics.EventDetails.access$3802(localEventDetails, this.target_);
        if ((i & 0x8) == 8)
          k |= 4;
        Analytics.EventDetails.access$3902(localEventDetails, this.count_);
        if ((0x10 & this.bitField0_) == 16)
        {
          this.errorCode_ = Collections.unmodifiableList(this.errorCode_);
          this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        }
        Analytics.EventDetails.access$4002(localEventDetails, this.errorCode_);
        if ((0x20 & this.bitField0_) == 32)
        {
          this.favaRequestId_ = Collections.unmodifiableList(this.favaRequestId_);
          this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        }
        Analytics.EventDetails.access$4102(localEventDetails, this.favaRequestId_);
        Analytics.EventDetails.access$4202(localEventDetails, k);
        return localEventDetails;
      }

      public final Builder clear()
      {
        super.clear();
        this.startView_ = Analytics.UniqueId.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.endView_ = Analytics.UniqueId.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.target_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.count_ = 0;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.errorCode_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.favaRequestId_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        return this;
      }

      public final Builder clearCount()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.count_ = 0;
        return this;
      }

      public final Builder clearEndView()
      {
        this.endView_ = Analytics.UniqueId.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearErrorCode()
      {
        this.errorCode_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearFavaRequestId()
      {
        this.favaRequestId_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        return this;
      }

      public final Builder clearStartView()
      {
        this.startView_ = Analytics.UniqueId.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Builder clearTarget()
      {
        this.target_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final int getCount()
      {
        return this.count_;
      }

      public final Analytics.EventDetails getDefaultInstanceForType()
      {
        return Analytics.EventDetails.getDefaultInstance();
      }

      public final Analytics.UniqueId getEndView()
      {
        return this.endView_;
      }

      public final Analytics.UniqueId getErrorCode(int paramInt)
      {
        return (Analytics.UniqueId)this.errorCode_.get(paramInt);
      }

      public final int getErrorCodeCount()
      {
        return this.errorCode_.size();
      }

      public final List<Analytics.UniqueId> getErrorCodeList()
      {
        return Collections.unmodifiableList(this.errorCode_);
      }

      public final long getFavaRequestId(int paramInt)
      {
        return ((Long)this.favaRequestId_.get(paramInt)).longValue();
      }

      public final int getFavaRequestIdCount()
      {
        return this.favaRequestId_.size();
      }

      public final List<Long> getFavaRequestIdList()
      {
        return Collections.unmodifiableList(this.favaRequestId_);
      }

      public final Analytics.UniqueId getStartView()
      {
        return this.startView_;
      }

      public final Analytics.UniqueId getTarget(int paramInt)
      {
        return (Analytics.UniqueId)this.target_.get(paramInt);
      }

      public final int getTargetCount()
      {
        return this.target_.size();
      }

      public final List<Analytics.UniqueId> getTargetList()
      {
        return Collections.unmodifiableList(this.target_);
      }

      public final boolean hasCount()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasEndView()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasStartView()
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

      public final Builder mergeFrom(Analytics.EventDetails paramEventDetails)
      {
        if (paramEventDetails == Analytics.EventDetails.getDefaultInstance());
        while (true)
        {
          return this;
          Analytics.UniqueId localUniqueId2;
          label61: Analytics.UniqueId localUniqueId1;
          if (paramEventDetails.hasStartView())
          {
            localUniqueId2 = paramEventDetails.getStartView();
            if (((0x1 & this.bitField0_) == 1) && (this.startView_ != Analytics.UniqueId.getDefaultInstance()))
            {
              this.startView_ = Analytics.UniqueId.newBuilder(this.startView_).mergeFrom(localUniqueId2).buildPartial();
              this.bitField0_ = (0x1 | this.bitField0_);
            }
          }
          else
          {
            if (paramEventDetails.hasEndView())
            {
              localUniqueId1 = paramEventDetails.getEndView();
              if (((0x2 & this.bitField0_) != 2) || (this.endView_ == Analytics.UniqueId.getDefaultInstance()))
                break label290;
              this.endView_ = Analytics.UniqueId.newBuilder(this.endView_).mergeFrom(localUniqueId1).buildPartial();
              label123: this.bitField0_ = (0x2 | this.bitField0_);
            }
            if (!paramEventDetails.target_.isEmpty())
            {
              if (!this.target_.isEmpty())
                break label299;
              this.target_ = paramEventDetails.target_;
              this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
            }
            label176: if (paramEventDetails.hasCount())
              setCount(paramEventDetails.getCount());
            if (!paramEventDetails.errorCode_.isEmpty())
            {
              if (!this.errorCode_.isEmpty())
                break label320;
              this.errorCode_ = paramEventDetails.errorCode_;
              this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
            }
          }
          while (true)
          {
            if (paramEventDetails.favaRequestId_.isEmpty())
              break label339;
            if (!this.favaRequestId_.isEmpty())
              break label341;
            this.favaRequestId_ = paramEventDetails.favaRequestId_;
            this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
            break;
            this.startView_ = localUniqueId2;
            break label61;
            label290: this.endView_ = localUniqueId1;
            break label123;
            label299: ensureTargetIsMutable();
            this.target_.addAll(paramEventDetails.target_);
            break label176;
            label320: ensureErrorCodeIsMutable();
            this.errorCode_.addAll(paramEventDetails.errorCode_);
          }
          label339: continue;
          label341: ensureFavaRequestIdIsMutable();
          this.favaRequestId_.addAll(paramEventDetails.favaRequestId_);
        }
      }

      public final Builder setCount(int paramInt)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.count_ = paramInt;
        return this;
      }

      public final Builder setEndView(Analytics.UniqueId.Builder paramBuilder)
      {
        this.endView_ = paramBuilder.build();
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setEndView(Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        this.endView_ = paramUniqueId;
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setErrorCode(int paramInt, Analytics.UniqueId.Builder paramBuilder)
      {
        ensureErrorCodeIsMutable();
        this.errorCode_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setErrorCode(int paramInt, Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        ensureErrorCodeIsMutable();
        this.errorCode_.set(paramInt, paramUniqueId);
        return this;
      }

      public final Builder setFavaRequestId(int paramInt, long paramLong)
      {
        ensureFavaRequestIdIsMutable();
        this.favaRequestId_.set(paramInt, Long.valueOf(paramLong));
        return this;
      }

      public final Builder setStartView(Analytics.UniqueId.Builder paramBuilder)
      {
        this.startView_ = paramBuilder.build();
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setStartView(Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        this.startView_ = paramUniqueId;
        this.bitField0_ = (0x1 | this.bitField0_);
        return this;
      }

      public final Builder setTarget(int paramInt, Analytics.UniqueId.Builder paramBuilder)
      {
        ensureTargetIsMutable();
        this.target_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setTarget(int paramInt, Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == null)
          throw new NullPointerException();
        ensureTargetIsMutable();
        this.target_.set(paramInt, paramUniqueId);
        return this;
      }
    }
  }

  public static abstract interface EventDetailsOrBuilder extends MessageLiteOrBuilder
  {
    public abstract int getCount();

    public abstract Analytics.UniqueId getEndView();

    public abstract Analytics.UniqueId getErrorCode(int paramInt);

    public abstract int getErrorCodeCount();

    public abstract List<Analytics.UniqueId> getErrorCodeList();

    public abstract long getFavaRequestId(int paramInt);

    public abstract int getFavaRequestIdCount();

    public abstract List<Long> getFavaRequestIdList();

    public abstract Analytics.UniqueId getStartView();

    public abstract Analytics.UniqueId getTarget(int paramInt);

    public abstract int getTargetCount();

    public abstract List<Analytics.UniqueId> getTargetList();

    public abstract boolean hasCount();

    public abstract boolean hasEndView();

    public abstract boolean hasStartView();
  }

  public static abstract interface EventOrBuilder extends MessageLiteOrBuilder
  {
    public abstract Analytics.UniqueId getAction();

    public abstract Analytics.CustomValue getCustomValue(int paramInt);

    public abstract int getCustomValueCount();

    public abstract List<Analytics.CustomValue> getCustomValueList();

    public abstract Analytics.EventDetails getDetails();

    @Deprecated
    public abstract long getEndTime();

    public abstract long getEndTimeMsec();

    public abstract Analytics.Event.EventType getEventType();

    @Deprecated
    public abstract long getStartTime();

    public abstract long getStartTimeMsec();

    public abstract Analytics.Event.Step getStep(int paramInt);

    public abstract int getStepCount();

    public abstract List<Analytics.Event.Step> getStepList();

    public abstract boolean hasAction();

    public abstract boolean hasDetails();

    @Deprecated
    public abstract boolean hasEndTime();

    public abstract boolean hasEndTimeMsec();

    public abstract boolean hasEventType();

    @Deprecated
    public abstract boolean hasStartTime();

    public abstract boolean hasStartTimeMsec();
  }

  public static final class LogEnvelope extends GeneratedMessageLite
    implements Analytics.LogEnvelopeOrBuilder
  {
    private static final LogEnvelope defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object buildNumber_;
    private ClientType client_;
    private List<Analytics.CustomValue> customValue_;
    private List<Analytics.Event> event_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object platform_;
    private long sendTimeMsec_;
    private Object version_;

    static
    {
      LogEnvelope localLogEnvelope = new LogEnvelope();
      defaultInstance = localLogEnvelope;
      localLogEnvelope.client_ = ClientType.WEB;
      localLogEnvelope.version_ = "";
      localLogEnvelope.platform_ = "";
      localLogEnvelope.event_ = Collections.emptyList();
      localLogEnvelope.customValue_ = Collections.emptyList();
      localLogEnvelope.buildNumber_ = "";
      localLogEnvelope.sendTimeMsec_ = 0L;
    }

    private LogEnvelope()
    {
    }

    private LogEnvelope(Builder paramBuilder)
    {
      super();
    }

    private ByteString getBuildNumberBytes()
    {
      Object localObject = this.buildNumber_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.buildNumber_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static LogEnvelope getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getPlatformBytes()
    {
      Object localObject = this.platform_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.platform_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getVersionBytes()
    {
      Object localObject = this.version_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.version_ = localByteString;
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

    public static Builder newBuilder(LogEnvelope paramLogEnvelope)
    {
      return Builder.access$100().mergeFrom(paramLogEnvelope);
    }

    public final String getBuildNumber()
    {
      Object localObject1 = this.buildNumber_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.buildNumber_ = str;
      }
    }

    public final ClientType getClient()
    {
      return this.client_;
    }

    public final Analytics.CustomValue getCustomValue(int paramInt)
    {
      return (Analytics.CustomValue)this.customValue_.get(paramInt);
    }

    public final int getCustomValueCount()
    {
      return this.customValue_.size();
    }

    public final List<Analytics.CustomValue> getCustomValueList()
    {
      return this.customValue_;
    }

    public final Analytics.CustomValueOrBuilder getCustomValueOrBuilder(int paramInt)
    {
      return (Analytics.CustomValueOrBuilder)this.customValue_.get(paramInt);
    }

    public final List<? extends Analytics.CustomValueOrBuilder> getCustomValueOrBuilderList()
    {
      return this.customValue_;
    }

    public final LogEnvelope getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final Analytics.Event getEvent(int paramInt)
    {
      return (Analytics.Event)this.event_.get(paramInt);
    }

    public final int getEventCount()
    {
      return this.event_.size();
    }

    public final List<Analytics.Event> getEventList()
    {
      return this.event_;
    }

    public final Analytics.EventOrBuilder getEventOrBuilder(int paramInt)
    {
      return (Analytics.EventOrBuilder)this.event_.get(paramInt);
    }

    public final List<? extends Analytics.EventOrBuilder> getEventOrBuilderList()
    {
      return this.event_;
    }

    public final String getPlatform()
    {
      Object localObject1 = this.platform_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.platform_ = str;
      }
    }

    public final long getSendTimeMsec()
    {
      return this.sendTimeMsec_;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int k;
      for (int i1 = i; ; i1 = k)
      {
        return i1;
        int j = 0x1 & this.bitField0_;
        k = 0;
        if (j == 1)
          k = 0 + CodedOutputStream.computeEnumSize(1, this.client_.getNumber());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getVersionBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getPlatformBytes());
        for (int m = 0; m < this.event_.size(); m++)
          k += CodedOutputStream.computeMessageSize(4, (MessageLite)this.event_.get(m));
        for (int n = 0; n < this.customValue_.size(); n++)
          k += CodedOutputStream.computeMessageSize(5, (MessageLite)this.customValue_.get(n));
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBytesSize(6, getBuildNumberBytes());
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeInt64Size(7, this.sendTimeMsec_);
        this.memoizedSerializedSize = k;
      }
    }

    public final String getVersion()
    {
      Object localObject1 = this.version_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.version_ = str;
      }
    }

    public final boolean hasBuildNumber()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasClient()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasPlatform()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasSendTimeMsec()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasVersion()
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
        paramCodedOutputStream.writeEnum(1, this.client_.getNumber());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getVersionBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getPlatformBytes());
      for (int i = 0; i < this.event_.size(); i++)
        paramCodedOutputStream.writeMessage(4, (MessageLite)this.event_.get(i));
      for (int j = 0; j < this.customValue_.size(); j++)
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.customValue_.get(j));
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBytes(6, getBuildNumberBytes());
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeInt64(7, this.sendTimeMsec_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Analytics.LogEnvelope, Builder>
      implements Analytics.LogEnvelopeOrBuilder
    {
      private int bitField0_;
      private Object buildNumber_ = "";
      private Analytics.LogEnvelope.ClientType client_ = Analytics.LogEnvelope.ClientType.WEB;
      private List<Analytics.CustomValue> customValue_ = Collections.emptyList();
      private List<Analytics.Event> event_ = Collections.emptyList();
      private Object platform_ = "";
      private long sendTimeMsec_;
      private Object version_ = "";

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureCustomValueIsMutable()
      {
        if ((0x10 & this.bitField0_) != 16)
        {
          this.customValue_ = new ArrayList(this.customValue_);
          this.bitField0_ = (0x10 | this.bitField0_);
        }
      }

      private void ensureEventIsMutable()
      {
        if ((0x8 & this.bitField0_) != 8)
        {
          this.event_ = new ArrayList(this.event_);
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
          case 8:
            Analytics.LogEnvelope.ClientType localClientType = Analytics.LogEnvelope.ClientType.valueOf(paramCodedInputStream.readEnum());
            if (localClientType == null)
              continue;
            this.bitField0_ = (0x1 | this.bitField0_);
            this.client_ = localClientType;
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.version_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.platform_ = paramCodedInputStream.readBytes();
            break;
          case 34:
            Analytics.Event.Builder localBuilder1 = Analytics.Event.newBuilder();
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            addEvent(localBuilder1.buildPartial());
            break;
          case 42:
            Analytics.CustomValue.Builder localBuilder = Analytics.CustomValue.newBuilder();
            paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
            addCustomValue(localBuilder.buildPartial());
            break;
          case 50:
            this.bitField0_ = (0x20 | this.bitField0_);
            this.buildNumber_ = paramCodedInputStream.readBytes();
            break;
          case 56:
          }
          this.bitField0_ = (0x40 | this.bitField0_);
          this.sendTimeMsec_ = paramCodedInputStream.readInt64();
        }
      }

      public final Builder addAllCustomValue(Iterable<? extends Analytics.CustomValue> paramIterable)
      {
        ensureCustomValueIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.customValue_);
        return this;
      }

      public final Builder addAllEvent(Iterable<? extends Analytics.Event> paramIterable)
      {
        ensureEventIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.event_);
        return this;
      }

      public final Builder addCustomValue(int paramInt, Analytics.CustomValue.Builder paramBuilder)
      {
        ensureCustomValueIsMutable();
        this.customValue_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addCustomValue(int paramInt, Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == null)
          throw new NullPointerException();
        ensureCustomValueIsMutable();
        this.customValue_.add(paramInt, paramCustomValue);
        return this;
      }

      public final Builder addCustomValue(Analytics.CustomValue.Builder paramBuilder)
      {
        ensureCustomValueIsMutable();
        this.customValue_.add(paramBuilder.build());
        return this;
      }

      public final Builder addCustomValue(Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == null)
          throw new NullPointerException();
        ensureCustomValueIsMutable();
        this.customValue_.add(paramCustomValue);
        return this;
      }

      public final Builder addEvent(int paramInt, Analytics.Event.Builder paramBuilder)
      {
        ensureEventIsMutable();
        this.event_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addEvent(int paramInt, Analytics.Event paramEvent)
      {
        if (paramEvent == null)
          throw new NullPointerException();
        ensureEventIsMutable();
        this.event_.add(paramInt, paramEvent);
        return this;
      }

      public final Builder addEvent(Analytics.Event.Builder paramBuilder)
      {
        ensureEventIsMutable();
        this.event_.add(paramBuilder.build());
        return this;
      }

      public final Builder addEvent(Analytics.Event paramEvent)
      {
        if (paramEvent == null)
          throw new NullPointerException();
        ensureEventIsMutable();
        this.event_.add(paramEvent);
        return this;
      }

      public final Analytics.LogEnvelope build()
      {
        Analytics.LogEnvelope localLogEnvelope = buildPartial();
        if (!localLogEnvelope.isInitialized())
          throw new UninitializedMessageException();
        return localLogEnvelope;
      }

      public final Analytics.LogEnvelope buildPartial()
      {
        Analytics.LogEnvelope localLogEnvelope = new Analytics.LogEnvelope(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Analytics.LogEnvelope.access$302(localLogEnvelope, this.client_);
        if ((i & 0x2) == 2)
          k |= 2;
        Analytics.LogEnvelope.access$402(localLogEnvelope, this.version_);
        if ((i & 0x4) == 4)
          k |= 4;
        Analytics.LogEnvelope.access$502(localLogEnvelope, this.platform_);
        if ((0x8 & this.bitField0_) == 8)
        {
          this.event_ = Collections.unmodifiableList(this.event_);
          this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        }
        Analytics.LogEnvelope.access$602(localLogEnvelope, this.event_);
        if ((0x10 & this.bitField0_) == 16)
        {
          this.customValue_ = Collections.unmodifiableList(this.customValue_);
          this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        }
        Analytics.LogEnvelope.access$702(localLogEnvelope, this.customValue_);
        if ((i & 0x20) == 32)
          k |= 8;
        Analytics.LogEnvelope.access$802(localLogEnvelope, this.buildNumber_);
        if ((i & 0x40) == 64)
          k |= 16;
        Analytics.LogEnvelope.access$902(localLogEnvelope, this.sendTimeMsec_);
        Analytics.LogEnvelope.access$1002(localLogEnvelope, k);
        return localLogEnvelope;
      }

      public final Builder clear()
      {
        super.clear();
        this.client_ = Analytics.LogEnvelope.ClientType.WEB;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.version_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.platform_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.event_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.customValue_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.buildNumber_ = "";
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.sendTimeMsec_ = 0L;
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        return this;
      }

      public final Builder clearBuildNumber()
      {
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.buildNumber_ = Analytics.LogEnvelope.getDefaultInstance().getBuildNumber();
        return this;
      }

      public final Builder clearClient()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.client_ = Analytics.LogEnvelope.ClientType.WEB;
        return this;
      }

      public final Builder clearCustomValue()
      {
        this.customValue_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearEvent()
      {
        this.event_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearPlatform()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.platform_ = Analytics.LogEnvelope.getDefaultInstance().getPlatform();
        return this;
      }

      public final Builder clearSendTimeMsec()
      {
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.sendTimeMsec_ = 0L;
        return this;
      }

      public final Builder clearVersion()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.version_ = Analytics.LogEnvelope.getDefaultInstance().getVersion();
        return this;
      }

      public final String getBuildNumber()
      {
        Object localObject = this.buildNumber_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.buildNumber_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Analytics.LogEnvelope.ClientType getClient()
      {
        return this.client_;
      }

      public final Analytics.CustomValue getCustomValue(int paramInt)
      {
        return (Analytics.CustomValue)this.customValue_.get(paramInt);
      }

      public final int getCustomValueCount()
      {
        return this.customValue_.size();
      }

      public final List<Analytics.CustomValue> getCustomValueList()
      {
        return Collections.unmodifiableList(this.customValue_);
      }

      public final Analytics.LogEnvelope getDefaultInstanceForType()
      {
        return Analytics.LogEnvelope.getDefaultInstance();
      }

      public final Analytics.Event getEvent(int paramInt)
      {
        return (Analytics.Event)this.event_.get(paramInt);
      }

      public final int getEventCount()
      {
        return this.event_.size();
      }

      public final List<Analytics.Event> getEventList()
      {
        return Collections.unmodifiableList(this.event_);
      }

      public final String getPlatform()
      {
        Object localObject = this.platform_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.platform_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final long getSendTimeMsec()
      {
        return this.sendTimeMsec_;
      }

      public final String getVersion()
      {
        Object localObject = this.version_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.version_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasBuildNumber()
      {
        if ((0x20 & this.bitField0_) == 32);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasClient()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasPlatform()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasSendTimeMsec()
      {
        if ((0x40 & this.bitField0_) == 64);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasVersion()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Analytics.LogEnvelope paramLogEnvelope)
      {
        if (paramLogEnvelope == Analytics.LogEnvelope.getDefaultInstance())
          return this;
        if (paramLogEnvelope.hasClient())
          setClient(paramLogEnvelope.getClient());
        if (paramLogEnvelope.hasVersion())
          setVersion(paramLogEnvelope.getVersion());
        if (paramLogEnvelope.hasPlatform())
          setPlatform(paramLogEnvelope.getPlatform());
        if (!paramLogEnvelope.event_.isEmpty())
        {
          if (this.event_.isEmpty())
          {
            this.event_ = paramLogEnvelope.event_;
            this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
          }
        }
        else
          label100: if (!paramLogEnvelope.customValue_.isEmpty())
          {
            if (!this.customValue_.isEmpty())
              break label199;
            this.customValue_ = paramLogEnvelope.customValue_;
            this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
          }
        while (true)
        {
          if (paramLogEnvelope.hasBuildNumber())
            setBuildNumber(paramLogEnvelope.getBuildNumber());
          if (!paramLogEnvelope.hasSendTimeMsec())
            break;
          setSendTimeMsec(paramLogEnvelope.getSendTimeMsec());
          break;
          ensureEventIsMutable();
          this.event_.addAll(paramLogEnvelope.event_);
          break label100;
          label199: ensureCustomValueIsMutable();
          this.customValue_.addAll(paramLogEnvelope.customValue_);
        }
      }

      public final Builder setBuildNumber(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x20 | this.bitField0_);
        this.buildNumber_ = paramString;
        return this;
      }

      public final Builder setClient(Analytics.LogEnvelope.ClientType paramClientType)
      {
        if (paramClientType == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.client_ = paramClientType;
        return this;
      }

      public final Builder setCustomValue(int paramInt, Analytics.CustomValue.Builder paramBuilder)
      {
        ensureCustomValueIsMutable();
        this.customValue_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setCustomValue(int paramInt, Analytics.CustomValue paramCustomValue)
      {
        if (paramCustomValue == null)
          throw new NullPointerException();
        ensureCustomValueIsMutable();
        this.customValue_.set(paramInt, paramCustomValue);
        return this;
      }

      public final Builder setEvent(int paramInt, Analytics.Event.Builder paramBuilder)
      {
        ensureEventIsMutable();
        this.event_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setEvent(int paramInt, Analytics.Event paramEvent)
      {
        if (paramEvent == null)
          throw new NullPointerException();
        ensureEventIsMutable();
        this.event_.set(paramInt, paramEvent);
        return this;
      }

      public final Builder setPlatform(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.platform_ = paramString;
        return this;
      }

      public final Builder setSendTimeMsec(long paramLong)
      {
        this.bitField0_ = (0x40 | this.bitField0_);
        this.sendTimeMsec_ = paramLong;
        return this;
      }

      public final Builder setVersion(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.version_ = paramString;
        return this;
      }
    }

    public static enum ClientType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<ClientType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        IPHONE = new ClientType("IPHONE", 1, 1);
        ANDROID = new ClientType("ANDROID", 2, 2);
        ANDROID_OS = new ClientType("ANDROID_OS", 3, 3);
        TIER2_3 = new ClientType("TIER2_3", 4, 4);
        PICASA_CLIENT = new ClientType("PICASA_CLIENT", 5, 5);
        ClientType[] arrayOfClientType = new ClientType[6];
        arrayOfClientType[0] = WEB;
        arrayOfClientType[1] = IPHONE;
        arrayOfClientType[2] = ANDROID;
        arrayOfClientType[3] = ANDROID_OS;
        arrayOfClientType[4] = TIER2_3;
        arrayOfClientType[5] = PICASA_CLIENT;
      }

      private ClientType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static ClientType valueOf(int paramInt)
      {
        ClientType localClientType;
        switch (paramInt)
        {
        default:
          localClientType = null;
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        }
        while (true)
        {
          return localClientType;
          localClientType = WEB;
          continue;
          localClientType = IPHONE;
          continue;
          localClientType = ANDROID;
          continue;
          localClientType = ANDROID_OS;
          continue;
          localClientType = TIER2_3;
          continue;
          localClientType = PICASA_CLIENT;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface LogEnvelopeOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getBuildNumber();

    public abstract Analytics.LogEnvelope.ClientType getClient();

    public abstract Analytics.CustomValue getCustomValue(int paramInt);

    public abstract int getCustomValueCount();

    public abstract List<Analytics.CustomValue> getCustomValueList();

    public abstract Analytics.Event getEvent(int paramInt);

    public abstract int getEventCount();

    public abstract List<Analytics.Event> getEventList();

    public abstract String getPlatform();

    public abstract long getSendTimeMsec();

    public abstract String getVersion();

    public abstract boolean hasBuildNumber();

    public abstract boolean hasClient();

    public abstract boolean hasPlatform();

    public abstract boolean hasSendTimeMsec();

    public abstract boolean hasVersion();
  }

  public static final class UniqueId extends GeneratedMessageLite
    implements Analytics.UniqueIdOrBuilder
  {
    private static final UniqueId defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object name_;
    private int number_;
    private Object type_;

    static
    {
      UniqueId localUniqueId = new UniqueId();
      defaultInstance = localUniqueId;
      localUniqueId.number_ = 0;
      localUniqueId.name_ = "";
      localUniqueId.type_ = "";
    }

    private UniqueId()
    {
    }

    private UniqueId(Builder paramBuilder)
    {
      super();
    }

    public static UniqueId getDefaultInstance()
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

    private ByteString getTypeBytes()
    {
      Object localObject = this.type_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.type_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$4400();
    }

    public static Builder newBuilder(UniqueId paramUniqueId)
    {
      return Builder.access$4400().mergeFrom(paramUniqueId);
    }

    public final UniqueId getDefaultInstanceForType()
    {
      return defaultInstance;
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

    public final int getNumber()
    {
      return this.number_;
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
          k = 0 + CodedOutputStream.computeInt32Size(1, this.number_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getNameBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getTypeBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final String getType()
    {
      Object localObject1 = this.type_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.type_ = str;
      }
    }

    public final boolean hasName()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasNumber()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasType()
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
        paramCodedOutputStream.writeInt32(1, this.number_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getNameBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getTypeBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Analytics.UniqueId, Builder>
      implements Analytics.UniqueIdOrBuilder
    {
      private int bitField0_;
      private Object name_ = "";
      private int number_;
      private Object type_ = "";

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
            this.number_ = paramCodedInputStream.readInt32();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.name_ = paramCodedInputStream.readBytes();
            break;
          case 26:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.type_ = paramCodedInputStream.readBytes();
        }
      }

      public final Analytics.UniqueId build()
      {
        Analytics.UniqueId localUniqueId = buildPartial();
        if (!localUniqueId.isInitialized())
          throw new UninitializedMessageException();
        return localUniqueId;
      }

      public final Analytics.UniqueId buildPartial()
      {
        Analytics.UniqueId localUniqueId = new Analytics.UniqueId(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Analytics.UniqueId.access$4602(localUniqueId, this.number_);
        if ((i & 0x2) == 2)
          k |= 2;
        Analytics.UniqueId.access$4702(localUniqueId, this.name_);
        if ((i & 0x4) == 4)
          k |= 4;
        Analytics.UniqueId.access$4802(localUniqueId, this.type_);
        Analytics.UniqueId.access$4902(localUniqueId, k);
        return localUniqueId;
      }

      public final Builder clear()
      {
        super.clear();
        this.number_ = 0;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.name_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.type_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearName()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.name_ = Analytics.UniqueId.getDefaultInstance().getName();
        return this;
      }

      public final Builder clearNumber()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.number_ = 0;
        return this;
      }

      public final Builder clearType()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.type_ = Analytics.UniqueId.getDefaultInstance().getType();
        return this;
      }

      public final Analytics.UniqueId getDefaultInstanceForType()
      {
        return Analytics.UniqueId.getDefaultInstance();
      }

      public final String getName()
      {
        Object localObject = this.name_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.name_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final int getNumber()
      {
        return this.number_;
      }

      public final String getType()
      {
        Object localObject = this.type_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.type_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasName()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasNumber()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasType()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Analytics.UniqueId paramUniqueId)
      {
        if (paramUniqueId == Analytics.UniqueId.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramUniqueId.hasNumber())
            setNumber(paramUniqueId.getNumber());
          if (paramUniqueId.hasName())
            setName(paramUniqueId.getName());
          if (paramUniqueId.hasType())
            setType(paramUniqueId.getType());
        }
      }

      public final Builder setName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.name_ = paramString;
        return this;
      }

      public final Builder setNumber(int paramInt)
      {
        this.bitField0_ = (0x1 | this.bitField0_);
        this.number_ = paramInt;
        return this;
      }

      public final Builder setType(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.type_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface UniqueIdOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getName();

    public abstract int getNumber();

    public abstract String getType();

    public abstract boolean hasName();

    public abstract boolean hasNumber();

    public abstract boolean hasType();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.webapps.Analytics
 * JD-Core Version:    0.6.2
 */