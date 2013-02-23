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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnmodifiableLazyStringList;
import com.google.wireless.webapps.Version.ClientVersion;
import com.google.wireless.webapps.Version.ClientVersion.Builder;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.Collections;
import java.util.List;

public final class Data
{
  public static final class Content extends GeneratedMessageLite
    implements Data.ContentOrBuilder
  {
    private static final Content defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object linkUrl_;
    private Data.Location location_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Data.PhotoMetadata photoMetadata_;
    private Object photoUrl_;
    private Object text_;

    static
    {
      Content localContent = new Content();
      defaultInstance = localContent;
      localContent.text_ = "";
      localContent.photoUrl_ = "";
      localContent.linkUrl_ = "";
      localContent.location_ = Data.Location.getDefaultInstance();
      localContent.photoMetadata_ = Data.PhotoMetadata.getDefaultInstance();
    }

    private Content()
    {
    }

    private Content(Builder paramBuilder)
    {
      super();
    }

    public static Content getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getLinkUrlBytes()
    {
      Object localObject = this.linkUrl_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.linkUrl_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getPhotoUrlBytes()
    {
      Object localObject = this.photoUrl_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.photoUrl_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getTextBytes()
    {
      Object localObject = this.text_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.text_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$2700();
    }

    public final Content getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getLinkUrl()
    {
      Object localObject1 = this.linkUrl_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.linkUrl_ = str;
      }
    }

    public final Data.Location getLocation()
    {
      return this.location_;
    }

    public final Data.PhotoMetadata getPhotoMetadata()
    {
      return this.photoMetadata_;
    }

    public final String getPhotoUrl()
    {
      Object localObject1 = this.photoUrl_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.photoUrl_ = str;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getTextBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getPhotoUrlBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getLinkUrlBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeMessageSize(4, this.location_);
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeMessageSize(5, this.photoMetadata_);
        this.memoizedSerializedSize = k;
      }
    }

    public final String getText()
    {
      Object localObject1 = this.text_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.text_ = str;
      }
    }

    public final boolean hasLinkUrl()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasLocation()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPhotoMetadata()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPhotoUrl()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasText()
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
        paramCodedOutputStream.writeBytes(1, getTextBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getPhotoUrlBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getLinkUrlBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeMessage(4, this.location_);
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeMessage(5, this.photoMetadata_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.Content, Builder>
      implements Data.ContentOrBuilder
    {
      private int bitField0_;
      private Object linkUrl_ = "";
      private Data.Location location_ = Data.Location.getDefaultInstance();
      private Data.PhotoMetadata photoMetadata_ = Data.PhotoMetadata.getDefaultInstance();
      private Object photoUrl_ = "";
      private Object text_ = "";

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
            this.text_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.photoUrl_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.linkUrl_ = paramCodedInputStream.readBytes();
            break;
          case 34:
            Data.Location.Builder localBuilder1 = Data.Location.newBuilder();
            if (hasLocation())
              localBuilder1.mergeFrom(getLocation());
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            setLocation(localBuilder1.buildPartial());
            break;
          case 42:
          }
          Data.PhotoMetadata.Builder localBuilder = Data.PhotoMetadata.newBuilder();
          if (hasPhotoMetadata())
            localBuilder.mergeFrom(getPhotoMetadata());
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          setPhotoMetadata(localBuilder.buildPartial());
        }
      }

      public final Data.Content build()
      {
        Data.Content localContent = buildPartial();
        if (!localContent.isInitialized())
          throw new UninitializedMessageException();
        return localContent;
      }

      public final Data.Content buildPartial()
      {
        Data.Content localContent = new Data.Content(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.Content.access$2902(localContent, this.text_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.Content.access$3002(localContent, this.photoUrl_);
        if ((i & 0x4) == 4)
          k |= 4;
        Data.Content.access$3102(localContent, this.linkUrl_);
        if ((i & 0x8) == 8)
          k |= 8;
        Data.Content.access$3202(localContent, this.location_);
        if ((i & 0x10) == 16)
          k |= 16;
        Data.Content.access$3302(localContent, this.photoMetadata_);
        Data.Content.access$3402(localContent, k);
        return localContent;
      }

      public final Builder clear()
      {
        super.clear();
        this.text_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.photoUrl_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.linkUrl_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.location_ = Data.Location.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.photoMetadata_ = Data.PhotoMetadata.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearLinkUrl()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.linkUrl_ = Data.Content.getDefaultInstance().getLinkUrl();
        return this;
      }

      public final Builder clearLocation()
      {
        this.location_ = Data.Location.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearPhotoMetadata()
      {
        this.photoMetadata_ = Data.PhotoMetadata.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearPhotoUrl()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.photoUrl_ = Data.Content.getDefaultInstance().getPhotoUrl();
        return this;
      }

      public final Builder clearText()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.text_ = Data.Content.getDefaultInstance().getText();
        return this;
      }

      public final Data.Content getDefaultInstanceForType()
      {
        return Data.Content.getDefaultInstance();
      }

      public final String getLinkUrl()
      {
        Object localObject = this.linkUrl_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.linkUrl_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Data.Location getLocation()
      {
        return this.location_;
      }

      public final Data.PhotoMetadata getPhotoMetadata()
      {
        return this.photoMetadata_;
      }

      public final String getPhotoUrl()
      {
        Object localObject = this.photoUrl_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.photoUrl_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final String getText()
      {
        Object localObject = this.text_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.text_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasLinkUrl()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasLocation()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasPhotoMetadata()
      {
        if ((0x10 & this.bitField0_) == 16);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasPhotoUrl()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasText()
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

      public final Builder mergeFrom(Data.Content paramContent)
      {
        if (paramContent == Data.Content.getDefaultInstance());
        Data.Location localLocation;
        label109: 
        do
        {
          return this;
          if (paramContent.hasText())
            setText(paramContent.getText());
          if (paramContent.hasPhotoUrl())
            setPhotoUrl(paramContent.getPhotoUrl());
          if (paramContent.hasLinkUrl())
            setLinkUrl(paramContent.getLinkUrl());
          if (paramContent.hasLocation())
          {
            localLocation = paramContent.getLocation();
            if (((0x8 & this.bitField0_) != 8) || (this.location_ == Data.Location.getDefaultInstance()))
              break;
            this.location_ = Data.Location.newBuilder(this.location_).mergeFrom(localLocation).buildPartial();
            this.bitField0_ = (0x8 | this.bitField0_);
          }
        }
        while (!paramContent.hasPhotoMetadata());
        Data.PhotoMetadata localPhotoMetadata = paramContent.getPhotoMetadata();
        if (((0x10 & this.bitField0_) == 16) && (this.photoMetadata_ != Data.PhotoMetadata.getDefaultInstance()));
        for (this.photoMetadata_ = Data.PhotoMetadata.newBuilder(this.photoMetadata_).mergeFrom(localPhotoMetadata).buildPartial(); ; this.photoMetadata_ = localPhotoMetadata)
        {
          this.bitField0_ = (0x10 | this.bitField0_);
          break;
          this.location_ = localLocation;
          break label109;
        }
      }

      public final Builder setLinkUrl(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.linkUrl_ = paramString;
        return this;
      }

      public final Builder setLocation(Data.Location.Builder paramBuilder)
      {
        this.location_ = paramBuilder.build();
        this.bitField0_ = (0x8 | this.bitField0_);
        return this;
      }

      public final Builder setLocation(Data.Location paramLocation)
      {
        if (paramLocation == null)
          throw new NullPointerException();
        this.location_ = paramLocation;
        this.bitField0_ = (0x8 | this.bitField0_);
        return this;
      }

      public final Builder setPhotoMetadata(Data.PhotoMetadata.Builder paramBuilder)
      {
        this.photoMetadata_ = paramBuilder.build();
        this.bitField0_ = (0x10 | this.bitField0_);
        return this;
      }

      public final Builder setPhotoMetadata(Data.PhotoMetadata paramPhotoMetadata)
      {
        if (paramPhotoMetadata == null)
          throw new NullPointerException();
        this.photoMetadata_ = paramPhotoMetadata;
        this.bitField0_ = (0x10 | this.bitField0_);
        return this;
      }

      public final Builder setPhotoUrl(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.photoUrl_ = paramString;
        return this;
      }

      public final Builder setText(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.text_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface ContentOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getLinkUrl();

    public abstract Data.Location getLocation();

    public abstract Data.PhotoMetadata getPhotoMetadata();

    public abstract String getPhotoUrl();

    public abstract String getText();

    public abstract boolean hasLinkUrl();

    public abstract boolean hasLocation();

    public abstract boolean hasPhotoMetadata();

    public abstract boolean hasPhotoUrl();

    public abstract boolean hasText();
  }

  public static final class ConversationAttributes extends GeneratedMessageLite
    implements Data.ConversationAttributesOrBuilder
  {
    private static final ConversationAttributes defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object conversationId_;
    private Object conversationName_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    static
    {
      ConversationAttributes localConversationAttributes = new ConversationAttributes();
      defaultInstance = localConversationAttributes;
      localConversationAttributes.conversationName_ = "";
      localConversationAttributes.conversationId_ = "";
    }

    private ConversationAttributes()
    {
    }

    private ConversationAttributes(Builder paramBuilder)
    {
      super();
    }

    private ByteString getConversationIdBytes()
    {
      Object localObject = this.conversationId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.conversationId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getConversationNameBytes()
    {
      Object localObject = this.conversationName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.conversationName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static ConversationAttributes getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$5400();
    }

    public final String getConversationId()
    {
      Object localObject1 = this.conversationId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.conversationId_ = str;
      }
    }

    public final String getConversationName()
    {
      Object localObject1 = this.conversationName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.conversationName_ = str;
      }
    }

    public final ConversationAttributes getDefaultInstanceForType()
    {
      return defaultInstance;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getConversationNameBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getConversationIdBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasConversationId()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasConversationName()
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
        paramCodedOutputStream.writeBytes(1, getConversationNameBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getConversationIdBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.ConversationAttributes, Builder>
      implements Data.ConversationAttributesOrBuilder
    {
      private int bitField0_;
      private Object conversationId_ = "";
      private Object conversationName_ = "";

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
            this.conversationName_ = paramCodedInputStream.readBytes();
            break;
          case 18:
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.conversationId_ = paramCodedInputStream.readBytes();
        }
      }

      public final Data.ConversationAttributes build()
      {
        Data.ConversationAttributes localConversationAttributes = buildPartial();
        if (!localConversationAttributes.isInitialized())
          throw new UninitializedMessageException();
        return localConversationAttributes;
      }

      public final Data.ConversationAttributes buildPartial()
      {
        Data.ConversationAttributes localConversationAttributes = new Data.ConversationAttributes(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.ConversationAttributes.access$5602(localConversationAttributes, this.conversationName_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.ConversationAttributes.access$5702(localConversationAttributes, this.conversationId_);
        Data.ConversationAttributes.access$5802(localConversationAttributes, k);
        return localConversationAttributes;
      }

      public final Builder clear()
      {
        super.clear();
        this.conversationName_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.conversationId_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearConversationId()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.conversationId_ = Data.ConversationAttributes.getDefaultInstance().getConversationId();
        return this;
      }

      public final Builder clearConversationName()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.conversationName_ = Data.ConversationAttributes.getDefaultInstance().getConversationName();
        return this;
      }

      public final String getConversationId()
      {
        Object localObject = this.conversationId_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.conversationId_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final String getConversationName()
      {
        Object localObject = this.conversationName_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.conversationName_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Data.ConversationAttributes getDefaultInstanceForType()
      {
        return Data.ConversationAttributes.getDefaultInstance();
      }

      public final boolean hasConversationId()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasConversationName()
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

      public final Builder mergeFrom(Data.ConversationAttributes paramConversationAttributes)
      {
        if (paramConversationAttributes == Data.ConversationAttributes.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramConversationAttributes.hasConversationName())
            setConversationName(paramConversationAttributes.getConversationName());
          if (paramConversationAttributes.hasConversationId())
            setConversationId(paramConversationAttributes.getConversationId());
        }
      }

      public final Builder setConversationId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.conversationId_ = paramString;
        return this;
      }

      public final Builder setConversationName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.conversationName_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface ConversationAttributesOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getConversationId();

    public abstract String getConversationName();

    public abstract boolean hasConversationId();

    public abstract boolean hasConversationName();
  }

  public static final class ConversationMetadata extends GeneratedMessageLite
    implements Data.ConversationMetadataOrBuilder
  {
    private static final ConversationMetadata defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object joinCode_;
    private Data.Location location_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private LazyStringList tag_;
    private ConversationVisibility visibility_;

    static
    {
      ConversationMetadata localConversationMetadata = new ConversationMetadata();
      defaultInstance = localConversationMetadata;
      localConversationMetadata.visibility_ = ConversationVisibility.PUBLIC;
      localConversationMetadata.location_ = Data.Location.getDefaultInstance();
      localConversationMetadata.tag_ = LazyStringArrayList.EMPTY;
      localConversationMetadata.joinCode_ = "";
    }

    private ConversationMetadata()
    {
    }

    private ConversationMetadata(Builder paramBuilder)
    {
      super();
    }

    public static ConversationMetadata getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getJoinCodeBytes()
    {
      Object localObject = this.joinCode_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.joinCode_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$1100();
    }

    public static Builder newBuilder(ConversationMetadata paramConversationMetadata)
    {
      return Builder.access$1100().mergeFrom(paramConversationMetadata);
    }

    public final ConversationMetadata getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getJoinCode()
    {
      Object localObject1 = this.joinCode_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.joinCode_ = str;
      }
    }

    public final Data.Location getLocation()
    {
      return this.location_;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int i1;
      for (int i2 = i; ; i2 = i1)
      {
        return i2;
        int j = 0x1 & this.bitField0_;
        int k = 0;
        if (j == 1)
          k = 0 + CodedOutputStream.computeEnumSize(1, this.visibility_.getNumber());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeMessageSize(2, this.location_);
        int m = 0;
        for (int n = 0; n < this.tag_.size(); n++)
          m += CodedOutputStream.computeBytesSizeNoTag(this.tag_.getByteString(n));
        i1 = k + m + 1 * getTagList().size();
        if ((0x4 & this.bitField0_) == 4)
          i1 += CodedOutputStream.computeBytesSize(4, getJoinCodeBytes());
        this.memoizedSerializedSize = i1;
      }
    }

    public final String getTag(int paramInt)
    {
      return (String)this.tag_.get(paramInt);
    }

    public final int getTagCount()
    {
      return this.tag_.size();
    }

    public final List<String> getTagList()
    {
      return this.tag_;
    }

    public final ConversationVisibility getVisibility()
    {
      return this.visibility_;
    }

    public final boolean hasJoinCode()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasLocation()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasVisibility()
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
        paramCodedOutputStream.writeEnum(1, this.visibility_.getNumber());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeMessage(2, this.location_);
      for (int i = 0; i < this.tag_.size(); i++)
        paramCodedOutputStream.writeBytes(3, this.tag_.getByteString(i));
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(4, getJoinCodeBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.ConversationMetadata, Builder>
      implements Data.ConversationMetadataOrBuilder
    {
      private int bitField0_;
      private Object joinCode_ = "";
      private Data.Location location_ = Data.Location.getDefaultInstance();
      private LazyStringList tag_ = LazyStringArrayList.EMPTY;
      private Data.ConversationMetadata.ConversationVisibility visibility_ = Data.ConversationMetadata.ConversationVisibility.PUBLIC;

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureTagIsMutable()
      {
        if ((0x4 & this.bitField0_) != 4)
        {
          this.tag_ = new LazyStringArrayList(this.tag_);
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
          case 8:
            Data.ConversationMetadata.ConversationVisibility localConversationVisibility = Data.ConversationMetadata.ConversationVisibility.valueOf(paramCodedInputStream.readEnum());
            if (localConversationVisibility == null)
              continue;
            this.bitField0_ = (0x1 | this.bitField0_);
            this.visibility_ = localConversationVisibility;
            break;
          case 18:
            Data.Location.Builder localBuilder = Data.Location.newBuilder();
            if (hasLocation())
              localBuilder.mergeFrom(getLocation());
            paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
            setLocation(localBuilder.buildPartial());
            break;
          case 26:
            ensureTagIsMutable();
            this.tag_.add(paramCodedInputStream.readBytes());
            break;
          case 34:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.joinCode_ = paramCodedInputStream.readBytes();
        }
      }

      public final Builder addAllTag(Iterable<String> paramIterable)
      {
        ensureTagIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.tag_);
        return this;
      }

      public final Builder addTag(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureTagIsMutable();
        this.tag_.add(paramString);
        return this;
      }

      public final Data.ConversationMetadata build()
      {
        Data.ConversationMetadata localConversationMetadata = buildPartial();
        if (!localConversationMetadata.isInitialized())
          throw new UninitializedMessageException();
        return localConversationMetadata;
      }

      public final Data.ConversationMetadata buildPartial()
      {
        Data.ConversationMetadata localConversationMetadata = new Data.ConversationMetadata(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.ConversationMetadata.access$1302(localConversationMetadata, this.visibility_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.ConversationMetadata.access$1402(localConversationMetadata, this.location_);
        if ((0x4 & this.bitField0_) == 4)
        {
          this.tag_ = new UnmodifiableLazyStringList(this.tag_);
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        }
        Data.ConversationMetadata.access$1502(localConversationMetadata, this.tag_);
        if ((i & 0x8) == 8)
          k |= 4;
        Data.ConversationMetadata.access$1602(localConversationMetadata, this.joinCode_);
        Data.ConversationMetadata.access$1702(localConversationMetadata, k);
        return localConversationMetadata;
      }

      public final Builder clear()
      {
        super.clear();
        this.visibility_ = Data.ConversationMetadata.ConversationVisibility.PUBLIC;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.location_ = Data.Location.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.tag_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.joinCode_ = "";
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearJoinCode()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.joinCode_ = Data.ConversationMetadata.getDefaultInstance().getJoinCode();
        return this;
      }

      public final Builder clearLocation()
      {
        this.location_ = Data.Location.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearTag()
      {
        this.tag_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearVisibility()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.visibility_ = Data.ConversationMetadata.ConversationVisibility.PUBLIC;
        return this;
      }

      public final Data.ConversationMetadata getDefaultInstanceForType()
      {
        return Data.ConversationMetadata.getDefaultInstance();
      }

      public final String getJoinCode()
      {
        Object localObject = this.joinCode_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.joinCode_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Data.Location getLocation()
      {
        return this.location_;
      }

      public final String getTag(int paramInt)
      {
        return (String)this.tag_.get(paramInt);
      }

      public final int getTagCount()
      {
        return this.tag_.size();
      }

      public final List<String> getTagList()
      {
        return Collections.unmodifiableList(this.tag_);
      }

      public final Data.ConversationMetadata.ConversationVisibility getVisibility()
      {
        return this.visibility_;
      }

      public final boolean hasJoinCode()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasLocation()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasVisibility()
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

      public final Builder mergeFrom(Data.ConversationMetadata paramConversationMetadata)
      {
        if (paramConversationMetadata == Data.ConversationMetadata.getDefaultInstance());
        label158: label177: 
        while (true)
        {
          return this;
          if (paramConversationMetadata.hasVisibility())
            setVisibility(paramConversationMetadata.getVisibility());
          Data.Location localLocation;
          if (paramConversationMetadata.hasLocation())
          {
            localLocation = paramConversationMetadata.getLocation();
            if (((0x2 & this.bitField0_) == 2) && (this.location_ != Data.Location.getDefaultInstance()))
            {
              this.location_ = Data.Location.newBuilder(this.location_).mergeFrom(localLocation).buildPartial();
              label77: this.bitField0_ = (0x2 | this.bitField0_);
            }
          }
          else if (!paramConversationMetadata.tag_.isEmpty())
          {
            if (!this.tag_.isEmpty())
              break label158;
            this.tag_ = paramConversationMetadata.tag_;
            this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          }
          while (true)
          {
            if (!paramConversationMetadata.hasJoinCode())
              break label177;
            setJoinCode(paramConversationMetadata.getJoinCode());
            break;
            this.location_ = localLocation;
            break label77;
            ensureTagIsMutable();
            this.tag_.addAll(paramConversationMetadata.tag_);
          }
        }
      }

      public final Builder setJoinCode(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x8 | this.bitField0_);
        this.joinCode_ = paramString;
        return this;
      }

      public final Builder setLocation(Data.Location.Builder paramBuilder)
      {
        this.location_ = paramBuilder.build();
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setLocation(Data.Location paramLocation)
      {
        if (paramLocation == null)
          throw new NullPointerException();
        this.location_ = paramLocation;
        this.bitField0_ = (0x2 | this.bitField0_);
        return this;
      }

      public final Builder setTag(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureTagIsMutable();
        this.tag_.set(paramInt, paramString);
        return this;
      }

      public final Builder setVisibility(Data.ConversationMetadata.ConversationVisibility paramConversationVisibility)
      {
        if (paramConversationVisibility == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.visibility_ = paramConversationVisibility;
        return this;
      }
    }

    public static enum ConversationVisibility
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<ConversationVisibility> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        PRIVATE = new ConversationVisibility("PRIVATE", 1, 2);
        ConversationVisibility[] arrayOfConversationVisibility = new ConversationVisibility[2];
        arrayOfConversationVisibility[0] = PUBLIC;
        arrayOfConversationVisibility[1] = PRIVATE;
      }

      private ConversationVisibility(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static ConversationVisibility valueOf(int paramInt)
      {
        ConversationVisibility localConversationVisibility;
        switch (paramInt)
        {
        default:
          localConversationVisibility = null;
        case 1:
        case 2:
        }
        while (true)
        {
          return localConversationVisibility;
          localConversationVisibility = PUBLIC;
          continue;
          localConversationVisibility = PRIVATE;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface ConversationMetadataOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getJoinCode();

    public abstract Data.Location getLocation();

    public abstract String getTag(int paramInt);

    public abstract int getTagCount();

    public abstract List<String> getTagList();

    public abstract Data.ConversationMetadata.ConversationVisibility getVisibility();

    public abstract boolean hasJoinCode();

    public abstract boolean hasLocation();

    public abstract boolean hasVisibility();
  }

  public static enum ConversationType
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<ConversationType> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      GROUP = new ConversationType("GROUP", 1, 2);
      ConversationType[] arrayOfConversationType = new ConversationType[2];
      arrayOfConversationType[0] = ONE_TO_ONE;
      arrayOfConversationType[1] = GROUP;
    }

    private ConversationType(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static ConversationType valueOf(int paramInt)
    {
      ConversationType localConversationType;
      switch (paramInt)
      {
      default:
        localConversationType = null;
      case 1:
      case 2:
      }
      while (true)
      {
        return localConversationType;
        localConversationType = ONE_TO_ONE;
        continue;
        localConversationType = GROUP;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static final class KeyValue extends GeneratedMessageLite
    implements Data.KeyValueOrBuilder
  {
    private static final KeyValue defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object key_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object value_;

    static
    {
      KeyValue localKeyValue = new KeyValue();
      defaultInstance = localKeyValue;
      localKeyValue.key_ = "";
      localKeyValue.value_ = "";
    }

    private KeyValue()
    {
    }

    private KeyValue(Builder paramBuilder)
    {
      super();
    }

    public static KeyValue getDefaultInstance()
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
      return Builder.access$6700();
    }

    public final KeyValue getDefaultInstanceForType()
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
        this.memoizedSerializedSize = k;
      }
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
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.KeyValue, Builder>
      implements Data.KeyValueOrBuilder
    {
      private int bitField0_;
      private Object key_ = "";
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
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.value_ = paramCodedInputStream.readBytes();
        }
      }

      public final Data.KeyValue build()
      {
        Data.KeyValue localKeyValue = buildPartial();
        if (!localKeyValue.isInitialized())
          throw new UninitializedMessageException();
        return localKeyValue;
      }

      public final Data.KeyValue buildPartial()
      {
        Data.KeyValue localKeyValue = new Data.KeyValue(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.KeyValue.access$6902(localKeyValue, this.key_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.KeyValue.access$7002(localKeyValue, this.value_);
        Data.KeyValue.access$7102(localKeyValue, k);
        return localKeyValue;
      }

      public final Builder clear()
      {
        super.clear();
        this.key_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.value_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearKey()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.key_ = Data.KeyValue.getDefaultInstance().getKey();
        return this;
      }

      public final Builder clearValue()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.value_ = Data.KeyValue.getDefaultInstance().getValue();
        return this;
      }

      public final Data.KeyValue getDefaultInstanceForType()
      {
        return Data.KeyValue.getDefaultInstance();
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

      public final Builder mergeFrom(Data.KeyValue paramKeyValue)
      {
        if (paramKeyValue == Data.KeyValue.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramKeyValue.hasKey())
            setKey(paramKeyValue.getKey());
          if (paramKeyValue.hasValue())
            setValue(paramKeyValue.getValue());
        }
      }

      public final Builder setKey(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.key_ = paramString;
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

  public static abstract interface KeyValueOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getKey();

    public abstract String getValue();

    public abstract boolean hasKey();

    public abstract boolean hasValue();
  }

  public static final class Location extends GeneratedMessageLite
    implements Data.LocationOrBuilder
  {
    private static final Location defaultInstance;
    private static final long serialVersionUID;
    private double accuracy_;
    private int bitField0_;
    private double latitude_;
    private double longitude_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object name_;

    static
    {
      Location localLocation = new Location();
      defaultInstance = localLocation;
      localLocation.latitude_ = 0.0D;
      localLocation.longitude_ = 0.0D;
      localLocation.accuracy_ = 0.0D;
      localLocation.name_ = "";
    }

    private Location()
    {
    }

    private Location(Builder paramBuilder)
    {
      super();
    }

    public static Location getDefaultInstance()
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
      return Builder.access$1900();
    }

    public static Builder newBuilder(Location paramLocation)
    {
      return Builder.access$1900().mergeFrom(paramLocation);
    }

    public final double getAccuracy()
    {
      return this.accuracy_;
    }

    public final Location getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final double getLatitude()
    {
      return this.latitude_;
    }

    public final double getLongitude()
    {
      return this.longitude_;
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
          k = 0 + CodedOutputStream.computeDoubleSize(1, this.latitude_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeDoubleSize(2, this.longitude_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeDoubleSize(3, this.accuracy_);
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBytesSize(4, getNameBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasAccuracy()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasLatitude()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasLongitude()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasName()
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
        paramCodedOutputStream.writeDouble(1, this.latitude_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeDouble(2, this.longitude_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeDouble(3, this.accuracy_);
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBytes(4, getNameBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.Location, Builder>
      implements Data.LocationOrBuilder
    {
      private double accuracy_;
      private int bitField0_;
      private double latitude_;
      private double longitude_;
      private Object name_ = "";

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
          case 9:
            this.bitField0_ = (0x1 | this.bitField0_);
            this.latitude_ = paramCodedInputStream.readDouble();
            break;
          case 17:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.longitude_ = paramCodedInputStream.readDouble();
            break;
          case 25:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.accuracy_ = paramCodedInputStream.readDouble();
            break;
          case 34:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.name_ = paramCodedInputStream.readBytes();
        }
      }

      public final Data.Location build()
      {
        Data.Location localLocation = buildPartial();
        if (!localLocation.isInitialized())
          throw new UninitializedMessageException();
        return localLocation;
      }

      public final Data.Location buildPartial()
      {
        Data.Location localLocation = new Data.Location(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.Location.access$2102(localLocation, this.latitude_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.Location.access$2202(localLocation, this.longitude_);
        if ((i & 0x4) == 4)
          k |= 4;
        Data.Location.access$2302(localLocation, this.accuracy_);
        if ((i & 0x8) == 8)
          k |= 8;
        Data.Location.access$2402(localLocation, this.name_);
        Data.Location.access$2502(localLocation, k);
        return localLocation;
      }

      public final Builder clear()
      {
        super.clear();
        this.latitude_ = 0.0D;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.longitude_ = 0.0D;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.accuracy_ = 0.0D;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.name_ = "";
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearAccuracy()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.accuracy_ = 0.0D;
        return this;
      }

      public final Builder clearLatitude()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.latitude_ = 0.0D;
        return this;
      }

      public final Builder clearLongitude()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.longitude_ = 0.0D;
        return this;
      }

      public final Builder clearName()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.name_ = Data.Location.getDefaultInstance().getName();
        return this;
      }

      public final double getAccuracy()
      {
        return this.accuracy_;
      }

      public final Data.Location getDefaultInstanceForType()
      {
        return Data.Location.getDefaultInstance();
      }

      public final double getLatitude()
      {
        return this.latitude_;
      }

      public final double getLongitude()
      {
        return this.longitude_;
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

      public final boolean hasAccuracy()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasLatitude()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasLongitude()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasName()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Data.Location paramLocation)
      {
        if (paramLocation == Data.Location.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramLocation.hasLatitude())
            setLatitude(paramLocation.getLatitude());
          if (paramLocation.hasLongitude())
            setLongitude(paramLocation.getLongitude());
          if (paramLocation.hasAccuracy())
            setAccuracy(paramLocation.getAccuracy());
          if (paramLocation.hasName())
            setName(paramLocation.getName());
        }
      }

      public final Builder setAccuracy(double paramDouble)
      {
        this.bitField0_ = (0x4 | this.bitField0_);
        this.accuracy_ = paramDouble;
        return this;
      }

      public final Builder setLatitude(double paramDouble)
      {
        this.bitField0_ = (0x1 | this.bitField0_);
        this.latitude_ = paramDouble;
        return this;
      }

      public final Builder setLongitude(double paramDouble)
      {
        this.bitField0_ = (0x2 | this.bitField0_);
        this.longitude_ = paramDouble;
        return this;
      }

      public final Builder setName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x8 | this.bitField0_);
        this.name_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface LocationOrBuilder extends MessageLiteOrBuilder
  {
    public abstract double getAccuracy();

    public abstract double getLatitude();

    public abstract double getLongitude();

    public abstract String getName();

    public abstract boolean hasAccuracy();

    public abstract boolean hasLatitude();

    public abstract boolean hasLongitude();

    public abstract boolean hasName();
  }

  public static final class Participant extends GeneratedMessageLite
    implements Data.ParticipantOrBuilder
  {
    private static final Participant defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object firstName_;
    private Object fullName_;
    private long lastSeenAt_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object participantId_;
    private Object profilePhotoUrl_;
    private Type type_;

    static
    {
      Participant localParticipant = new Participant();
      defaultInstance = localParticipant;
      localParticipant.participantId_ = "";
      localParticipant.lastSeenAt_ = 0L;
      localParticipant.fullName_ = "";
      localParticipant.firstName_ = "";
      localParticipant.type_ = Type.INVITED;
      localParticipant.profilePhotoUrl_ = "";
    }

    private Participant()
    {
    }

    private Participant(Builder paramBuilder)
    {
      super();
    }

    public static Participant getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getFirstNameBytes()
    {
      Object localObject = this.firstName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.firstName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getFullNameBytes()
    {
      Object localObject = this.fullName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.fullName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getParticipantIdBytes()
    {
      Object localObject = this.participantId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.participantId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getProfilePhotoUrlBytes()
    {
      Object localObject = this.profilePhotoUrl_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.profilePhotoUrl_ = localByteString;
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

    public static Builder newBuilder(Participant paramParticipant)
    {
      return Builder.access$100().mergeFrom(paramParticipant);
    }

    public final Participant getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getFirstName()
    {
      Object localObject1 = this.firstName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.firstName_ = str;
      }
    }

    public final String getFullName()
    {
      Object localObject1 = this.fullName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.fullName_ = str;
      }
    }

    public final long getLastSeenAt()
    {
      return this.lastSeenAt_;
    }

    public final String getParticipantId()
    {
      Object localObject1 = this.participantId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.participantId_ = str;
      }
    }

    public final String getProfilePhotoUrl()
    {
      Object localObject1 = this.profilePhotoUrl_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.profilePhotoUrl_ = str;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getParticipantIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeInt64Size(2, this.lastSeenAt_);
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getFullNameBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBytesSize(4, getFirstNameBytes());
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeEnumSize(5, this.type_.getNumber());
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeBytesSize(6, getProfilePhotoUrlBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final Type getType()
    {
      return this.type_;
    }

    public final boolean hasFirstName()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasFullName()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasLastSeenAt()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasParticipantId()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasProfilePhotoUrl()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasType()
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
        paramCodedOutputStream.writeBytes(1, getParticipantIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeInt64(2, this.lastSeenAt_);
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getFullNameBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBytes(4, getFirstNameBytes());
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeEnum(5, this.type_.getNumber());
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeBytes(6, getProfilePhotoUrlBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.Participant, Builder>
      implements Data.ParticipantOrBuilder
    {
      private int bitField0_;
      private Object firstName_ = "";
      private Object fullName_ = "";
      private long lastSeenAt_;
      private Object participantId_ = "";
      private Object profilePhotoUrl_ = "";
      private Data.Participant.Type type_ = Data.Participant.Type.INVITED;

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
            this.participantId_ = paramCodedInputStream.readBytes();
            break;
          case 16:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.lastSeenAt_ = paramCodedInputStream.readInt64();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.fullName_ = paramCodedInputStream.readBytes();
            break;
          case 34:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.firstName_ = paramCodedInputStream.readBytes();
            break;
          case 40:
            Data.Participant.Type localType = Data.Participant.Type.valueOf(paramCodedInputStream.readEnum());
            if (localType == null)
              continue;
            this.bitField0_ = (0x10 | this.bitField0_);
            this.type_ = localType;
            break;
          case 50:
          }
          this.bitField0_ = (0x20 | this.bitField0_);
          this.profilePhotoUrl_ = paramCodedInputStream.readBytes();
        }
      }

      public final Data.Participant build()
      {
        Data.Participant localParticipant = buildPartial();
        if (!localParticipant.isInitialized())
          throw new UninitializedMessageException();
        return localParticipant;
      }

      public final Data.Participant buildPartial()
      {
        Data.Participant localParticipant = new Data.Participant(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.Participant.access$302(localParticipant, this.participantId_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.Participant.access$402(localParticipant, this.lastSeenAt_);
        if ((i & 0x4) == 4)
          k |= 4;
        Data.Participant.access$502(localParticipant, this.fullName_);
        if ((i & 0x8) == 8)
          k |= 8;
        Data.Participant.access$602(localParticipant, this.firstName_);
        if ((i & 0x10) == 16)
          k |= 16;
        Data.Participant.access$702(localParticipant, this.type_);
        if ((i & 0x20) == 32)
          k |= 32;
        Data.Participant.access$802(localParticipant, this.profilePhotoUrl_);
        Data.Participant.access$902(localParticipant, k);
        return localParticipant;
      }

      public final Builder clear()
      {
        super.clear();
        this.participantId_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.lastSeenAt_ = 0L;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.fullName_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.firstName_ = "";
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.type_ = Data.Participant.Type.INVITED;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.profilePhotoUrl_ = "";
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        return this;
      }

      public final Builder clearFirstName()
      {
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.firstName_ = Data.Participant.getDefaultInstance().getFirstName();
        return this;
      }

      public final Builder clearFullName()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.fullName_ = Data.Participant.getDefaultInstance().getFullName();
        return this;
      }

      public final Builder clearLastSeenAt()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.lastSeenAt_ = 0L;
        return this;
      }

      public final Builder clearParticipantId()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.participantId_ = Data.Participant.getDefaultInstance().getParticipantId();
        return this;
      }

      public final Builder clearProfilePhotoUrl()
      {
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.profilePhotoUrl_ = Data.Participant.getDefaultInstance().getProfilePhotoUrl();
        return this;
      }

      public final Builder clearType()
      {
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.type_ = Data.Participant.Type.INVITED;
        return this;
      }

      public final Data.Participant getDefaultInstanceForType()
      {
        return Data.Participant.getDefaultInstance();
      }

      public final String getFirstName()
      {
        Object localObject = this.firstName_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.firstName_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final String getFullName()
      {
        Object localObject = this.fullName_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.fullName_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final long getLastSeenAt()
      {
        return this.lastSeenAt_;
      }

      public final String getParticipantId()
      {
        Object localObject = this.participantId_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.participantId_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final String getProfilePhotoUrl()
      {
        Object localObject = this.profilePhotoUrl_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.profilePhotoUrl_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final Data.Participant.Type getType()
      {
        return this.type_;
      }

      public final boolean hasFirstName()
      {
        if ((0x8 & this.bitField0_) == 8);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasFullName()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasLastSeenAt()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasParticipantId()
      {
        int i = 1;
        if ((0x1 & this.bitField0_) == i);
        while (true)
        {
          return i;
          int j = 0;
        }
      }

      public final boolean hasProfilePhotoUrl()
      {
        if ((0x20 & this.bitField0_) == 32);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasType()
      {
        if ((0x10 & this.bitField0_) == 16);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Data.Participant paramParticipant)
      {
        if (paramParticipant == Data.Participant.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramParticipant.hasParticipantId())
            setParticipantId(paramParticipant.getParticipantId());
          if (paramParticipant.hasLastSeenAt())
            setLastSeenAt(paramParticipant.getLastSeenAt());
          if (paramParticipant.hasFullName())
            setFullName(paramParticipant.getFullName());
          if (paramParticipant.hasFirstName())
            setFirstName(paramParticipant.getFirstName());
          if (paramParticipant.hasType())
            setType(paramParticipant.getType());
          if (paramParticipant.hasProfilePhotoUrl())
            setProfilePhotoUrl(paramParticipant.getProfilePhotoUrl());
        }
      }

      public final Builder setFirstName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x8 | this.bitField0_);
        this.firstName_ = paramString;
        return this;
      }

      public final Builder setFullName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.fullName_ = paramString;
        return this;
      }

      public final Builder setLastSeenAt(long paramLong)
      {
        this.bitField0_ = (0x2 | this.bitField0_);
        this.lastSeenAt_ = paramLong;
        return this;
      }

      public final Builder setParticipantId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.participantId_ = paramString;
        return this;
      }

      public final Builder setProfilePhotoUrl(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x20 | this.bitField0_);
        this.profilePhotoUrl_ = paramString;
        return this;
      }

      public final Builder setType(Data.Participant.Type paramType)
      {
        if (paramType == null)
          throw new NullPointerException();
        this.bitField0_ = (0x10 | this.bitField0_);
        this.type_ = paramType;
        return this;
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
        ANDROID = new Type("ANDROID", 2, 3);
        IPHONE = new Type("IPHONE", 3, 4);
        WEB = new Type("WEB", 4, 5);
        Type[] arrayOfType = new Type[5];
        arrayOfType[0] = INVITED;
        arrayOfType[1] = SMS;
        arrayOfType[2] = ANDROID;
        arrayOfType[3] = IPHONE;
        arrayOfType[4] = WEB;
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
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        }
        while (true)
        {
          return localType;
          localType = INVITED;
          continue;
          localType = SMS;
          continue;
          localType = ANDROID;
          continue;
          localType = IPHONE;
          continue;
          localType = WEB;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static final class ParticipantAttributes extends GeneratedMessageLite
    implements Data.ParticipantAttributesOrBuilder
  {
    private static final ParticipantAttributes defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object firstName_;
    private Object fullName_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object participantId_;

    static
    {
      ParticipantAttributes localParticipantAttributes = new ParticipantAttributes();
      defaultInstance = localParticipantAttributes;
      localParticipantAttributes.participantId_ = "";
      localParticipantAttributes.fullName_ = "";
      localParticipantAttributes.firstName_ = "";
    }

    private ParticipantAttributes()
    {
    }

    private ParticipantAttributes(Builder paramBuilder)
    {
      super();
    }

    public static ParticipantAttributes getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getFirstNameBytes()
    {
      Object localObject = this.firstName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.firstName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getFullNameBytes()
    {
      Object localObject = this.fullName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.fullName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getParticipantIdBytes()
    {
      Object localObject = this.participantId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.participantId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$6000();
    }

    public final ParticipantAttributes getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getFirstName()
    {
      Object localObject1 = this.firstName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.firstName_ = str;
      }
    }

    public final String getFullName()
    {
      Object localObject1 = this.fullName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.fullName_ = str;
      }
    }

    public final String getParticipantId()
    {
      Object localObject1 = this.participantId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.participantId_ = str;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getParticipantIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getFullNameBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getFirstNameBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final boolean hasFirstName()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasFullName()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasParticipantId()
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
        paramCodedOutputStream.writeBytes(1, getParticipantIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getFullNameBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getFirstNameBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.ParticipantAttributes, Builder>
      implements Data.ParticipantAttributesOrBuilder
    {
      private int bitField0_;
      private Object firstName_ = "";
      private Object fullName_ = "";
      private Object participantId_ = "";

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
            this.participantId_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.fullName_ = paramCodedInputStream.readBytes();
            break;
          case 26:
          }
          this.bitField0_ = (0x4 | this.bitField0_);
          this.firstName_ = paramCodedInputStream.readBytes();
        }
      }

      public final Data.ParticipantAttributes build()
      {
        Data.ParticipantAttributes localParticipantAttributes = buildPartial();
        if (!localParticipantAttributes.isInitialized())
          throw new UninitializedMessageException();
        return localParticipantAttributes;
      }

      public final Data.ParticipantAttributes buildPartial()
      {
        Data.ParticipantAttributes localParticipantAttributes = new Data.ParticipantAttributes(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.ParticipantAttributes.access$6202(localParticipantAttributes, this.participantId_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.ParticipantAttributes.access$6302(localParticipantAttributes, this.fullName_);
        if ((i & 0x4) == 4)
          k |= 4;
        Data.ParticipantAttributes.access$6402(localParticipantAttributes, this.firstName_);
        Data.ParticipantAttributes.access$6502(localParticipantAttributes, k);
        return localParticipantAttributes;
      }

      public final Builder clear()
      {
        super.clear();
        this.participantId_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.fullName_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.firstName_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearFirstName()
      {
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.firstName_ = Data.ParticipantAttributes.getDefaultInstance().getFirstName();
        return this;
      }

      public final Builder clearFullName()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.fullName_ = Data.ParticipantAttributes.getDefaultInstance().getFullName();
        return this;
      }

      public final Builder clearParticipantId()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.participantId_ = Data.ParticipantAttributes.getDefaultInstance().getParticipantId();
        return this;
      }

      public final Data.ParticipantAttributes getDefaultInstanceForType()
      {
        return Data.ParticipantAttributes.getDefaultInstance();
      }

      public final String getFirstName()
      {
        Object localObject = this.firstName_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.firstName_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final String getFullName()
      {
        Object localObject = this.fullName_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.fullName_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final String getParticipantId()
      {
        Object localObject = this.participantId_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.participantId_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasFirstName()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasFullName()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasParticipantId()
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

      public final Builder mergeFrom(Data.ParticipantAttributes paramParticipantAttributes)
      {
        if (paramParticipantAttributes == Data.ParticipantAttributes.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramParticipantAttributes.hasParticipantId())
            setParticipantId(paramParticipantAttributes.getParticipantId());
          if (paramParticipantAttributes.hasFullName())
            setFullName(paramParticipantAttributes.getFullName());
          if (paramParticipantAttributes.hasFirstName())
            setFirstName(paramParticipantAttributes.getFirstName());
        }
      }

      public final Builder setFirstName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.firstName_ = paramString;
        return this;
      }

      public final Builder setFullName(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.fullName_ = paramString;
        return this;
      }

      public final Builder setParticipantId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.participantId_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface ParticipantAttributesOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getFirstName();

    public abstract String getFullName();

    public abstract String getParticipantId();

    public abstract boolean hasFirstName();

    public abstract boolean hasFullName();

    public abstract boolean hasParticipantId();
  }

  public static abstract interface ParticipantOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getFirstName();

    public abstract String getFullName();

    public abstract long getLastSeenAt();

    public abstract String getParticipantId();

    public abstract String getProfilePhotoUrl();

    public abstract Data.Participant.Type getType();

    public abstract boolean hasFirstName();

    public abstract boolean hasFullName();

    public abstract boolean hasLastSeenAt();

    public abstract boolean hasParticipantId();

    public abstract boolean hasProfilePhotoUrl();

    public abstract boolean hasType();
  }

  public static final class PhotoMetadata extends GeneratedMessageLite
    implements Data.PhotoMetadataOrBuilder
  {
    private static final PhotoMetadata defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object url_;

    static
    {
      PhotoMetadata localPhotoMetadata = new PhotoMetadata();
      defaultInstance = localPhotoMetadata;
      localPhotoMetadata.url_ = "";
    }

    private PhotoMetadata()
    {
    }

    private PhotoMetadata(Builder paramBuilder)
    {
      super();
    }

    public static PhotoMetadata getDefaultInstance()
    {
      return defaultInstance;
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
      return Builder.access$3600();
    }

    public static Builder newBuilder(PhotoMetadata paramPhotoMetadata)
    {
      return Builder.access$3600().mergeFrom(paramPhotoMetadata);
    }

    public final PhotoMetadata getDefaultInstanceForType()
    {
      return defaultInstance;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getUrlBytes());
        this.memoizedSerializedSize = k;
      }
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

    public final boolean hasUrl()
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
        paramCodedOutputStream.writeBytes(1, getUrlBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.PhotoMetadata, Builder>
      implements Data.PhotoMetadataOrBuilder
    {
      private int bitField0_;
      private Object url_ = "";

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
          }
          this.bitField0_ = (0x1 | this.bitField0_);
          this.url_ = paramCodedInputStream.readBytes();
        }
      }

      public final Data.PhotoMetadata build()
      {
        Data.PhotoMetadata localPhotoMetadata = buildPartial();
        if (!localPhotoMetadata.isInitialized())
          throw new UninitializedMessageException();
        return localPhotoMetadata;
      }

      public final Data.PhotoMetadata buildPartial()
      {
        Data.PhotoMetadata localPhotoMetadata = new Data.PhotoMetadata(this, (byte)0);
        int i = 0x1 & this.bitField0_;
        int j = 0;
        if (i == 1)
          j = 1;
        Data.PhotoMetadata.access$3802(localPhotoMetadata, this.url_);
        Data.PhotoMetadata.access$3902(localPhotoMetadata, j);
        return localPhotoMetadata;
      }

      public final Builder clear()
      {
        super.clear();
        this.url_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Builder clearUrl()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.url_ = Data.PhotoMetadata.getDefaultInstance().getUrl();
        return this;
      }

      public final Data.PhotoMetadata getDefaultInstanceForType()
      {
        return Data.PhotoMetadata.getDefaultInstance();
      }

      public final String getUrl()
      {
        Object localObject = this.url_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.url_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasUrl()
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

      public final Builder mergeFrom(Data.PhotoMetadata paramPhotoMetadata)
      {
        if (paramPhotoMetadata == Data.PhotoMetadata.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramPhotoMetadata.hasUrl())
            setUrl(paramPhotoMetadata.getUrl());
        }
      }

      public final Builder setUrl(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.url_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface PhotoMetadataOrBuilder extends MessageLiteOrBuilder
  {
    public abstract String getUrl();

    public abstract boolean hasUrl();
  }

  public static enum ResponseStatus
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<ResponseStatus> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      ERROR = new ResponseStatus("ERROR", 1, 2);
      ERROR_CANNOT_CONTACT = new ResponseStatus("ERROR_CANNOT_CONTACT", 2, 3);
      NEED_ID = new ResponseStatus("NEED_ID", 3, 4);
      CONVERSATION_TOO_LARGE = new ResponseStatus("CONVERSATION_TOO_LARGE", 4, 5);
      ERROR_INVALID_CONTACT = new ResponseStatus("ERROR_INVALID_CONTACT", 5, 6);
      ERROR_INVALID_EMAIL = new ResponseStatus("ERROR_INVALID_EMAIL", 6, 7);
      ERROR_INVALID_PHONE = new ResponseStatus("ERROR_INVALID_PHONE", 7, 8);
      ERROR_COUNTRY_UNSUPPORTED = new ResponseStatus("ERROR_COUNTRY_UNSUPPORTED", 8, 9);
      ERROR_INVALID_URL = new ResponseStatus("ERROR_INVALID_URL", 9, 10);
      ERROR_APP_BLOCKED = new ResponseStatus("ERROR_APP_BLOCKED", 10, 11);
      ERROR_EXCEED_SMS_INVITES = new ResponseStatus("ERROR_EXCEED_SMS_INVITES", 11, 12);
      ERROR_ALREADY_IN_CONVERSATION = new ResponseStatus("ERROR_ALREADY_IN_CONVERSATION", 12, 13);
      ERROR_USER_NOT_IN_CONVERSATION = new ResponseStatus("ERROR_USER_NOT_IN_CONVERSATION", 13, 14);
      ERROR_INVALID_REQUEST = new ResponseStatus("ERROR_INVALID_REQUEST", 14, 15);
      ERROR_UNEXPECTED = new ResponseStatus("ERROR_UNEXPECTED", 15, 16);
      ERROR_USER_MUST_BE_GAIA = new ResponseStatus("ERROR_USER_MUST_BE_GAIA", 16, 17);
      ERROR_USER_NOT_FOUND = new ResponseStatus("ERROR_USER_NOT_FOUND", 17, 18);
      ERROR_DUPLICATE_REQUEST = new ResponseStatus("ERROR_DUPLICATE_REQUEST", 18, 19);
      ERROR_HANGOUT_INVITE_NOT_FOUND = new ResponseStatus("ERROR_HANGOUT_INVITE_NOT_FOUND", 19, 20);
      ERROR_HANGOUT_INVITE_EXPIRED = new ResponseStatus("ERROR_HANGOUT_INVITE_EXPIRED", 20, 21);
      ERROR_HANGOUT_INVITE_ALREADY_HANDLED = new ResponseStatus("ERROR_HANGOUT_INVITE_ALREADY_HANDLED", 21, 22);
      ERROR_HANGOUT_INVITE_NO_DEVICE_FOUND = new ResponseStatus("ERROR_HANGOUT_INVITE_NO_DEVICE_FOUND", 22, 23);
      ERROR_TEMPORARY = new ResponseStatus("ERROR_TEMPORARY", 23, 24);
      ResponseStatus[] arrayOfResponseStatus = new ResponseStatus[24];
      arrayOfResponseStatus[0] = OK;
      arrayOfResponseStatus[1] = ERROR;
      arrayOfResponseStatus[2] = ERROR_CANNOT_CONTACT;
      arrayOfResponseStatus[3] = NEED_ID;
      arrayOfResponseStatus[4] = CONVERSATION_TOO_LARGE;
      arrayOfResponseStatus[5] = ERROR_INVALID_CONTACT;
      arrayOfResponseStatus[6] = ERROR_INVALID_EMAIL;
      arrayOfResponseStatus[7] = ERROR_INVALID_PHONE;
      arrayOfResponseStatus[8] = ERROR_COUNTRY_UNSUPPORTED;
      arrayOfResponseStatus[9] = ERROR_INVALID_URL;
      arrayOfResponseStatus[10] = ERROR_APP_BLOCKED;
      arrayOfResponseStatus[11] = ERROR_EXCEED_SMS_INVITES;
      arrayOfResponseStatus[12] = ERROR_ALREADY_IN_CONVERSATION;
      arrayOfResponseStatus[13] = ERROR_USER_NOT_IN_CONVERSATION;
      arrayOfResponseStatus[14] = ERROR_INVALID_REQUEST;
      arrayOfResponseStatus[15] = ERROR_UNEXPECTED;
      arrayOfResponseStatus[16] = ERROR_USER_MUST_BE_GAIA;
      arrayOfResponseStatus[17] = ERROR_USER_NOT_FOUND;
      arrayOfResponseStatus[18] = ERROR_DUPLICATE_REQUEST;
      arrayOfResponseStatus[19] = ERROR_HANGOUT_INVITE_NOT_FOUND;
      arrayOfResponseStatus[20] = ERROR_HANGOUT_INVITE_EXPIRED;
      arrayOfResponseStatus[21] = ERROR_HANGOUT_INVITE_ALREADY_HANDLED;
      arrayOfResponseStatus[22] = ERROR_HANGOUT_INVITE_NO_DEVICE_FOUND;
      arrayOfResponseStatus[23] = ERROR_TEMPORARY;
    }

    private ResponseStatus(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static ResponseStatus valueOf(int paramInt)
    {
      ResponseStatus localResponseStatus;
      switch (paramInt)
      {
      default:
        localResponseStatus = null;
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
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      }
      while (true)
      {
        return localResponseStatus;
        localResponseStatus = OK;
        continue;
        localResponseStatus = ERROR;
        continue;
        localResponseStatus = ERROR_CANNOT_CONTACT;
        continue;
        localResponseStatus = NEED_ID;
        continue;
        localResponseStatus = CONVERSATION_TOO_LARGE;
        continue;
        localResponseStatus = ERROR_INVALID_CONTACT;
        continue;
        localResponseStatus = ERROR_INVALID_EMAIL;
        continue;
        localResponseStatus = ERROR_INVALID_PHONE;
        continue;
        localResponseStatus = ERROR_COUNTRY_UNSUPPORTED;
        continue;
        localResponseStatus = ERROR_INVALID_URL;
        continue;
        localResponseStatus = ERROR_APP_BLOCKED;
        continue;
        localResponseStatus = ERROR_EXCEED_SMS_INVITES;
        continue;
        localResponseStatus = ERROR_ALREADY_IN_CONVERSATION;
        continue;
        localResponseStatus = ERROR_USER_NOT_IN_CONVERSATION;
        continue;
        localResponseStatus = ERROR_INVALID_REQUEST;
        continue;
        localResponseStatus = ERROR_UNEXPECTED;
        continue;
        localResponseStatus = ERROR_USER_MUST_BE_GAIA;
        continue;
        localResponseStatus = ERROR_USER_NOT_FOUND;
        continue;
        localResponseStatus = ERROR_DUPLICATE_REQUEST;
        continue;
        localResponseStatus = ERROR_HANGOUT_INVITE_NOT_FOUND;
        continue;
        localResponseStatus = ERROR_HANGOUT_INVITE_EXPIRED;
        continue;
        localResponseStatus = ERROR_HANGOUT_INVITE_ALREADY_HANDLED;
        continue;
        localResponseStatus = ERROR_HANGOUT_INVITE_NO_DEVICE_FOUND;
        continue;
        localResponseStatus = ERROR_TEMPORARY;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static final class StubbyInfo extends GeneratedMessageLite
    implements Data.StubbyInfoOrBuilder
  {
    private static final StubbyInfo defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Version.ClientVersion clientVersion_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private LazyStringList recipientId_;
    private Object senderId_;

    static
    {
      StubbyInfo localStubbyInfo = new StubbyInfo();
      defaultInstance = localStubbyInfo;
      localStubbyInfo.senderId_ = "";
      localStubbyInfo.recipientId_ = LazyStringArrayList.EMPTY;
      localStubbyInfo.clientVersion_ = Version.ClientVersion.getDefaultInstance();
    }

    private StubbyInfo()
    {
    }

    private StubbyInfo(Builder paramBuilder)
    {
      super();
    }

    public static StubbyInfo getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getSenderIdBytes()
    {
      Object localObject = this.senderId_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.senderId_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Builder newBuilder()
    {
      return Builder.access$4700();
    }

    public static Builder newBuilder(StubbyInfo paramStubbyInfo)
    {
      return Builder.access$4700().mergeFrom(paramStubbyInfo);
    }

    public final Version.ClientVersion getClientVersion()
    {
      return this.clientVersion_;
    }

    public final StubbyInfo getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final String getRecipientId(int paramInt)
    {
      return (String)this.recipientId_.get(paramInt);
    }

    public final int getRecipientIdCount()
    {
      return this.recipientId_.size();
    }

    public final List<String> getRecipientIdList()
    {
      return this.recipientId_;
    }

    public final String getSenderId()
    {
      Object localObject1 = this.senderId_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.senderId_ = str;
      }
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int i1;
      for (int i2 = i; ; i2 = i1)
      {
        return i2;
        int j = 0x1 & this.bitField0_;
        int k = 0;
        if (j == 1)
          k = 0 + CodedOutputStream.computeBytesSize(1, getSenderIdBytes());
        int m = 0;
        for (int n = 0; n < this.recipientId_.size(); n++)
          m += CodedOutputStream.computeBytesSizeNoTag(this.recipientId_.getByteString(n));
        i1 = k + m + 1 * getRecipientIdList().size();
        if ((0x2 & this.bitField0_) == 2)
          i1 += CodedOutputStream.computeMessageSize(3, this.clientVersion_);
        this.memoizedSerializedSize = i1;
      }
    }

    public final boolean hasClientVersion()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasSenderId()
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
        paramCodedOutputStream.writeBytes(1, getSenderIdBytes());
      for (int i = 0; i < this.recipientId_.size(); i++)
        paramCodedOutputStream.writeBytes(2, this.recipientId_.getByteString(i));
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeMessage(3, this.clientVersion_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.StubbyInfo, Builder>
      implements Data.StubbyInfoOrBuilder
    {
      private int bitField0_;
      private Version.ClientVersion clientVersion_ = Version.ClientVersion.getDefaultInstance();
      private LazyStringList recipientId_ = LazyStringArrayList.EMPTY;
      private Object senderId_ = "";

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureRecipientIdIsMutable()
      {
        if ((0x2 & this.bitField0_) != 2)
        {
          this.recipientId_ = new LazyStringArrayList(this.recipientId_);
          this.bitField0_ = (0x2 | this.bitField0_);
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
            this.senderId_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            ensureRecipientIdIsMutable();
            this.recipientId_.add(paramCodedInputStream.readBytes());
            break;
          case 26:
          }
          Version.ClientVersion.Builder localBuilder = Version.ClientVersion.newBuilder();
          if (hasClientVersion())
            localBuilder.mergeFrom(getClientVersion());
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          setClientVersion(localBuilder.buildPartial());
        }
      }

      public final Builder addAllRecipientId(Iterable<String> paramIterable)
      {
        ensureRecipientIdIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.recipientId_);
        return this;
      }

      public final Builder addRecipientId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureRecipientIdIsMutable();
        this.recipientId_.add(paramString);
        return this;
      }

      public final Data.StubbyInfo build()
      {
        Data.StubbyInfo localStubbyInfo = buildPartial();
        if (!localStubbyInfo.isInitialized())
          throw new UninitializedMessageException();
        return localStubbyInfo;
      }

      public final Data.StubbyInfo buildPartial()
      {
        Data.StubbyInfo localStubbyInfo = new Data.StubbyInfo(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.StubbyInfo.access$4902(localStubbyInfo, this.senderId_);
        if ((0x2 & this.bitField0_) == 2)
        {
          this.recipientId_ = new UnmodifiableLazyStringList(this.recipientId_);
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        }
        Data.StubbyInfo.access$5002(localStubbyInfo, this.recipientId_);
        if ((i & 0x4) == 4)
          k |= 2;
        Data.StubbyInfo.access$5102(localStubbyInfo, this.clientVersion_);
        Data.StubbyInfo.access$5202(localStubbyInfo, k);
        return localStubbyInfo;
      }

      public final Builder clear()
      {
        super.clear();
        this.senderId_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.recipientId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.clientVersion_ = Version.ClientVersion.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearClientVersion()
      {
        this.clientVersion_ = Version.ClientVersion.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearRecipientId()
      {
        this.recipientId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearSenderId()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.senderId_ = Data.StubbyInfo.getDefaultInstance().getSenderId();
        return this;
      }

      public final Version.ClientVersion getClientVersion()
      {
        return this.clientVersion_;
      }

      public final Data.StubbyInfo getDefaultInstanceForType()
      {
        return Data.StubbyInfo.getDefaultInstance();
      }

      public final String getRecipientId(int paramInt)
      {
        return (String)this.recipientId_.get(paramInt);
      }

      public final int getRecipientIdCount()
      {
        return this.recipientId_.size();
      }

      public final List<String> getRecipientIdList()
      {
        return Collections.unmodifiableList(this.recipientId_);
      }

      public final String getSenderId()
      {
        Object localObject = this.senderId_;
        String str;
        if (!(localObject instanceof String))
        {
          str = ((ByteString)localObject).toStringUtf8();
          this.senderId_ = str;
        }
        while (true)
        {
          return str;
          str = (String)localObject;
        }
      }

      public final boolean hasClientVersion()
      {
        if ((0x4 & this.bitField0_) == 4);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasSenderId()
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

      public final Builder mergeFrom(Data.StubbyInfo paramStubbyInfo)
      {
        if (paramStubbyInfo == Data.StubbyInfo.getDefaultInstance())
          return this;
        if (paramStubbyInfo.hasSenderId())
          setSenderId(paramStubbyInfo.getSenderId());
        label68: Version.ClientVersion localClientVersion;
        if (!paramStubbyInfo.recipientId_.isEmpty())
        {
          if (this.recipientId_.isEmpty())
          {
            this.recipientId_ = paramStubbyInfo.recipientId_;
            this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          }
        }
        else
        {
          if (!paramStubbyInfo.hasClientVersion())
            break label150;
          localClientVersion = paramStubbyInfo.getClientVersion();
          if (((0x4 & this.bitField0_) != 4) || (this.clientVersion_ == Version.ClientVersion.getDefaultInstance()))
            break label152;
        }
        label150: label152: for (this.clientVersion_ = Version.ClientVersion.newBuilder(this.clientVersion_).mergeFrom(localClientVersion).buildPartial(); ; this.clientVersion_ = localClientVersion)
        {
          this.bitField0_ = (0x4 | this.bitField0_);
          break;
          ensureRecipientIdIsMutable();
          this.recipientId_.addAll(paramStubbyInfo.recipientId_);
          break label68;
          break;
        }
      }

      public final Builder setClientVersion(Version.ClientVersion.Builder paramBuilder)
      {
        this.clientVersion_ = paramBuilder.build();
        this.bitField0_ = (0x4 | this.bitField0_);
        return this;
      }

      public final Builder setClientVersion(Version.ClientVersion paramClientVersion)
      {
        if (paramClientVersion == null)
          throw new NullPointerException();
        this.clientVersion_ = paramClientVersion;
        this.bitField0_ = (0x4 | this.bitField0_);
        return this;
      }

      public final Builder setRecipientId(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureRecipientIdIsMutable();
        this.recipientId_.set(paramInt, paramString);
        return this;
      }

      public final Builder setSenderId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.senderId_ = paramString;
        return this;
      }
    }
  }

  public static abstract interface StubbyInfoOrBuilder extends MessageLiteOrBuilder
  {
    public abstract Version.ClientVersion getClientVersion();

    public abstract String getRecipientId(int paramInt);

    public abstract int getRecipientIdCount();

    public abstract List<String> getRecipientIdList();

    public abstract String getSenderId();

    public abstract boolean hasClientVersion();

    public abstract boolean hasSenderId();
  }

  public static final class TimeRange extends GeneratedMessageLite
    implements Data.TimeRangeOrBuilder
  {
    private static final TimeRange defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private long end_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private long start_;

    static
    {
      TimeRange localTimeRange = new TimeRange();
      defaultInstance = localTimeRange;
      localTimeRange.start_ = 0L;
      localTimeRange.end_ = 0L;
    }

    private TimeRange()
    {
    }

    private TimeRange(Builder paramBuilder)
    {
      super();
    }

    public static TimeRange getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$4100();
    }

    public static Builder newBuilder(TimeRange paramTimeRange)
    {
      return Builder.access$4100().mergeFrom(paramTimeRange);
    }

    public final TimeRange getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final long getEnd()
    {
      return this.end_;
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
          k = 0 + CodedOutputStream.computeInt64Size(1, this.start_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeInt64Size(2, this.end_);
        this.memoizedSerializedSize = k;
      }
    }

    public final long getStart()
    {
      return this.start_;
    }

    public final boolean hasEnd()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasStart()
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
        paramCodedOutputStream.writeInt64(1, this.start_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeInt64(2, this.end_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Data.TimeRange, Builder>
      implements Data.TimeRangeOrBuilder
    {
      private int bitField0_;
      private long end_;
      private long start_;

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
            this.start_ = paramCodedInputStream.readInt64();
            break;
          case 16:
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.end_ = paramCodedInputStream.readInt64();
        }
      }

      public final Data.TimeRange build()
      {
        Data.TimeRange localTimeRange = buildPartial();
        if (!localTimeRange.isInitialized())
          throw new UninitializedMessageException();
        return localTimeRange;
      }

      public final Data.TimeRange buildPartial()
      {
        Data.TimeRange localTimeRange = new Data.TimeRange(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Data.TimeRange.access$4302(localTimeRange, this.start_);
        if ((i & 0x2) == 2)
          k |= 2;
        Data.TimeRange.access$4402(localTimeRange, this.end_);
        Data.TimeRange.access$4502(localTimeRange, k);
        return localTimeRange;
      }

      public final Builder clear()
      {
        super.clear();
        this.start_ = 0L;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.end_ = 0L;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearEnd()
      {
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.end_ = 0L;
        return this;
      }

      public final Builder clearStart()
      {
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.start_ = 0L;
        return this;
      }

      public final Data.TimeRange getDefaultInstanceForType()
      {
        return Data.TimeRange.getDefaultInstance();
      }

      public final long getEnd()
      {
        return this.end_;
      }

      public final long getStart()
      {
        return this.start_;
      }

      public final boolean hasEnd()
      {
        if ((0x2 & this.bitField0_) == 2);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasStart()
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

      public final Builder mergeFrom(Data.TimeRange paramTimeRange)
      {
        if (paramTimeRange == Data.TimeRange.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramTimeRange.hasStart())
            setStart(paramTimeRange.getStart());
          if (paramTimeRange.hasEnd())
            setEnd(paramTimeRange.getEnd());
        }
      }

      public final Builder setEnd(long paramLong)
      {
        this.bitField0_ = (0x2 | this.bitField0_);
        this.end_ = paramLong;
        return this;
      }

      public final Builder setStart(long paramLong)
      {
        this.bitField0_ = (0x1 | this.bitField0_);
        this.start_ = paramLong;
        return this;
      }
    }
  }

  public static abstract interface TimeRangeOrBuilder extends MessageLiteOrBuilder
  {
    public abstract long getEnd();

    public abstract long getStart();

    public abstract boolean hasEnd();

    public abstract boolean hasStart();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.realtimechat.proto.Data
 * JD-Core Version:    0.6.2
 */