package com.google.wireless.contacts.proto;

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

public final class Contact
{
  public static final class Address extends GeneratedMessageLite
    implements Contact.AddressOrBuilder
  {
    private static final Address defaultInstance;
    private static final long serialVersionUID;
    private Object address_;
    private int bitField0_;
    private boolean evergreen_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Common.Metadata metadata_;
    private Object type_;

    static
    {
      Address localAddress = new Address();
      defaultInstance = localAddress;
      localAddress.metadata_ = Common.Metadata.getDefaultInstance();
      localAddress.address_ = "";
      localAddress.type_ = "";
      localAddress.evergreen_ = false;
    }

    private Address()
    {
    }

    private Address(Builder paramBuilder)
    {
      super();
    }

    private ByteString getAddressBytes()
    {
      Object localObject = this.address_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.address_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public static Address getDefaultInstance()
    {
      return defaultInstance;
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
      return Builder.access$1700();
    }

    public final String getAddress()
    {
      Object localObject1 = this.address_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.address_ = str;
      }
    }

    public final boolean getEvergreen()
    {
      return this.evergreen_;
    }

    public final Common.Metadata getMetadata()
    {
      return this.metadata_;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.metadata_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getAddressBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getTypeBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBoolSize(4, this.evergreen_);
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

    public final boolean hasAddress()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasEvergreen()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasMetadata()
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
        paramCodedOutputStream.writeMessage(1, this.metadata_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getAddressBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getTypeBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBool(4, this.evergreen_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Contact.Address, Builder>
      implements Contact.AddressOrBuilder
    {
      private Object address_ = "";
      private int bitField0_;
      private boolean evergreen_;
      private Common.Metadata metadata_ = Common.Metadata.getDefaultInstance();
      private Object type_ = "";

      private Builder clear()
      {
        super.clear();
        this.metadata_ = Common.Metadata.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.address_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.type_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.evergreen_ = false;
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
          case 10:
            Common.Metadata.Builder localBuilder = Common.Metadata.newBuilder();
            if ((0x1 & this.bitField0_) == 1);
            Common.Metadata localMetadata;
            for (int j = 1; ; j = 0)
            {
              if (j != 0)
                localBuilder.mergeFrom(this.metadata_);
              paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
              localMetadata = localBuilder.buildPartial();
              if (localMetadata != null)
                break;
              throw new NullPointerException();
            }
            this.metadata_ = localMetadata;
            this.bitField0_ = (0x1 | this.bitField0_);
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.address_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.type_ = paramCodedInputStream.readBytes();
            break;
          case 32:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.evergreen_ = paramCodedInputStream.readBool();
        }
      }

      public final Contact.Address buildPartial()
      {
        Contact.Address localAddress = new Contact.Address(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Contact.Address.access$1902(localAddress, this.metadata_);
        if ((i & 0x2) == 2)
          k |= 2;
        Contact.Address.access$2002(localAddress, this.address_);
        if ((i & 0x4) == 4)
          k |= 4;
        Contact.Address.access$2102(localAddress, this.type_);
        if ((i & 0x8) == 8)
          k |= 8;
        Contact.Address.access$2202(localAddress, this.evergreen_);
        Contact.Address.access$2302(localAddress, k);
        return localAddress;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Contact.Address paramAddress)
      {
        if (paramAddress == Contact.Address.getDefaultInstance());
        while (true)
        {
          return this;
          Common.Metadata localMetadata;
          if (paramAddress.hasMetadata())
          {
            localMetadata = paramAddress.getMetadata();
            if (((0x1 & this.bitField0_) != 1) || (this.metadata_ == Common.Metadata.getDefaultInstance()))
              break label97;
          }
          String str2;
          label97: for (this.metadata_ = Common.Metadata.newBuilder(this.metadata_).mergeFrom(localMetadata).buildPartial(); ; this.metadata_ = localMetadata)
          {
            this.bitField0_ = (0x1 | this.bitField0_);
            if (!paramAddress.hasAddress())
              break label122;
            str2 = paramAddress.getAddress();
            if (str2 != null)
              break;
            throw new NullPointerException();
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.address_ = str2;
          label122: if (paramAddress.hasType())
          {
            String str1 = paramAddress.getType();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.type_ = str1;
          }
          if (paramAddress.hasEvergreen())
          {
            boolean bool = paramAddress.getEvergreen();
            this.bitField0_ = (0x8 | this.bitField0_);
            this.evergreen_ = bool;
          }
        }
      }
    }
  }

  public static abstract interface AddressOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class Email extends GeneratedMessageLite
    implements Contact.EmailOrBuilder
  {
    private static final Email defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object email_;
    private boolean evergreen_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Common.Metadata metadata_;
    private Object type_;

    static
    {
      Email localEmail = new Email();
      defaultInstance = localEmail;
      localEmail.metadata_ = Common.Metadata.getDefaultInstance();
      localEmail.email_ = "";
      localEmail.type_ = "";
      localEmail.evergreen_ = false;
    }

    private Email()
    {
    }

    private Email(Builder paramBuilder)
    {
      super();
    }

    public static Email getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getEmailBytes()
    {
      Object localObject = this.email_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.email_ = localByteString;
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
      return Builder.access$100();
    }

    public final String getEmail()
    {
      Object localObject1 = this.email_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.email_ = str;
      }
    }

    public final boolean getEvergreen()
    {
      return this.evergreen_;
    }

    public final Common.Metadata getMetadata()
    {
      return this.metadata_;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.metadata_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getEmailBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getTypeBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBoolSize(4, this.evergreen_);
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

    public final boolean hasEmail()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasEvergreen()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasMetadata()
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
        paramCodedOutputStream.writeMessage(1, this.metadata_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getEmailBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getTypeBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBool(4, this.evergreen_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Contact.Email, Builder>
      implements Contact.EmailOrBuilder
    {
      private int bitField0_;
      private Object email_ = "";
      private boolean evergreen_;
      private Common.Metadata metadata_ = Common.Metadata.getDefaultInstance();
      private Object type_ = "";

      private Builder clear()
      {
        super.clear();
        this.metadata_ = Common.Metadata.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.email_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.type_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.evergreen_ = false;
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
          case 10:
            Common.Metadata.Builder localBuilder = Common.Metadata.newBuilder();
            if ((0x1 & this.bitField0_) == 1);
            Common.Metadata localMetadata;
            for (int j = 1; ; j = 0)
            {
              if (j != 0)
                localBuilder.mergeFrom(this.metadata_);
              paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
              localMetadata = localBuilder.buildPartial();
              if (localMetadata != null)
                break;
              throw new NullPointerException();
            }
            this.metadata_ = localMetadata;
            this.bitField0_ = (0x1 | this.bitField0_);
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.email_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.type_ = paramCodedInputStream.readBytes();
            break;
          case 32:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.evergreen_ = paramCodedInputStream.readBool();
        }
      }

      public final Contact.Email buildPartial()
      {
        Contact.Email localEmail = new Contact.Email(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Contact.Email.access$302(localEmail, this.metadata_);
        if ((i & 0x2) == 2)
          k |= 2;
        Contact.Email.access$402(localEmail, this.email_);
        if ((i & 0x4) == 4)
          k |= 4;
        Contact.Email.access$502(localEmail, this.type_);
        if ((i & 0x8) == 8)
          k |= 8;
        Contact.Email.access$602(localEmail, this.evergreen_);
        Contact.Email.access$702(localEmail, k);
        return localEmail;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Contact.Email paramEmail)
      {
        if (paramEmail == Contact.Email.getDefaultInstance());
        while (true)
        {
          return this;
          Common.Metadata localMetadata;
          if (paramEmail.hasMetadata())
          {
            localMetadata = paramEmail.getMetadata();
            if (((0x1 & this.bitField0_) != 1) || (this.metadata_ == Common.Metadata.getDefaultInstance()))
              break label97;
          }
          String str2;
          label97: for (this.metadata_ = Common.Metadata.newBuilder(this.metadata_).mergeFrom(localMetadata).buildPartial(); ; this.metadata_ = localMetadata)
          {
            this.bitField0_ = (0x1 | this.bitField0_);
            if (!paramEmail.hasEmail())
              break label122;
            str2 = paramEmail.getEmail();
            if (str2 != null)
              break;
            throw new NullPointerException();
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.email_ = str2;
          label122: if (paramEmail.hasType())
          {
            String str1 = paramEmail.getType();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.type_ = str1;
          }
          if (paramEmail.hasEvergreen())
          {
            boolean bool = paramEmail.getEvergreen();
            this.bitField0_ = (0x8 | this.bitField0_);
            this.evergreen_ = bool;
          }
        }
      }
    }
  }

  public static abstract interface EmailOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class MobileContact extends GeneratedMessageLite
    implements Contact.MobileContactOrBuilder
  {
    private static final MobileContact defaultInstance;
    private static final long serialVersionUID;
    private List<Contact.Address> address_;
    private int affinity_;
    private int attributes_;
    private int bitField0_;
    private int callAffinity_;
    private LazyStringList circleId_;
    private Object displayName_;
    private List<Contact.Email> email_;
    private Object groups_;
    private boolean iAmFollowing_;
    private Object id_;
    private boolean inMyContacts_;
    private boolean isFollowingMe_;
    private long lastUpdatedTime_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private LazyStringList otherId_;
    private Object packedCircleIds_;
    private List<Contact.Phone> phone_;
    private Object phonesLookup_;
    private Object photoUrl_;
    private Object preferredEmail_;
    private ProfileType profileType_;
    private Object publicUserName_;
    private Object tagLine_;

    static
    {
      MobileContact localMobileContact = new MobileContact();
      defaultInstance = localMobileContact;
      localMobileContact.id_ = "";
      localMobileContact.displayName_ = "";
      localMobileContact.email_ = Collections.emptyList();
      localMobileContact.preferredEmail_ = "";
      localMobileContact.photoUrl_ = "";
      localMobileContact.phone_ = Collections.emptyList();
      localMobileContact.phonesLookup_ = "";
      localMobileContact.address_ = Collections.emptyList();
      localMobileContact.groups_ = "";
      localMobileContact.otherId_ = LazyStringArrayList.EMPTY;
      localMobileContact.publicUserName_ = "";
      localMobileContact.lastUpdatedTime_ = 0L;
      localMobileContact.affinity_ = 0;
      localMobileContact.callAffinity_ = 0;
      localMobileContact.attributes_ = 0;
      localMobileContact.iAmFollowing_ = false;
      localMobileContact.isFollowingMe_ = false;
      localMobileContact.inMyContacts_ = false;
      localMobileContact.circleId_ = LazyStringArrayList.EMPTY;
      localMobileContact.packedCircleIds_ = "";
      localMobileContact.profileType_ = ProfileType.UNKNOWN;
      localMobileContact.tagLine_ = "";
    }

    private MobileContact()
    {
    }

    private MobileContact(Builder paramBuilder)
    {
      super();
    }

    public static MobileContact getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getDisplayNameBytes()
    {
      Object localObject = this.displayName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.displayName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getGroupsBytes()
    {
      Object localObject = this.groups_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.groups_ = localByteString;
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

    private ByteString getPackedCircleIdsBytes()
    {
      Object localObject = this.packedCircleIds_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.packedCircleIds_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getPhonesLookupBytes()
    {
      Object localObject = this.phonesLookup_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.phonesLookup_ = localByteString;
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

    private ByteString getPreferredEmailBytes()
    {
      Object localObject = this.preferredEmail_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.preferredEmail_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getPublicUserNameBytes()
    {
      Object localObject = this.publicUserName_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.publicUserName_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getTagLineBytes()
    {
      Object localObject = this.tagLine_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.tagLine_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    public final int getAffinity()
    {
      return this.affinity_;
    }

    public final int getAttributes()
    {
      return this.attributes_;
    }

    public final int getCallAffinity()
    {
      return this.callAffinity_;
    }

    public final String getDisplayName()
    {
      Object localObject1 = this.displayName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.displayName_ = str;
      }
    }

    public final String getGroups()
    {
      Object localObject1 = this.groups_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.groups_ = str;
      }
    }

    @Deprecated
    public final boolean getIAmFollowing()
    {
      return this.iAmFollowing_;
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

    public final boolean getInMyContacts()
    {
      return this.inMyContacts_;
    }

    @Deprecated
    public final boolean getIsFollowingMe()
    {
      return this.isFollowingMe_;
    }

    public final long getLastUpdatedTime()
    {
      return this.lastUpdatedTime_;
    }

    public final String getPackedCircleIds()
    {
      Object localObject1 = this.packedCircleIds_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.packedCircleIds_ = str;
      }
    }

    public final String getPhonesLookup()
    {
      Object localObject1 = this.phonesLookup_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.phonesLookup_ = str;
      }
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

    public final String getPreferredEmail()
    {
      Object localObject1 = this.preferredEmail_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.preferredEmail_ = str;
      }
    }

    public final ProfileType getProfileType()
    {
      return this.profileType_;
    }

    public final String getPublicUserName()
    {
      Object localObject1 = this.publicUserName_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.publicUserName_ = str;
      }
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int i7;
      for (int i8 = i; ; i8 = i7)
      {
        return i8;
        int j = 0x1 & this.bitField0_;
        int k = 0;
        if (j == 1)
          k = 0 + CodedOutputStream.computeBytesSize(1, getIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getDisplayNameBytes());
        for (int m = 0; m < this.email_.size(); m++)
          k += CodedOutputStream.computeMessageSize(3, (MessageLite)this.email_.get(m));
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(4, getPreferredEmailBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBytesSize(5, getPhotoUrlBytes());
        for (int n = 0; n < this.phone_.size(); n++)
          k += CodedOutputStream.computeMessageSize(6, (MessageLite)this.phone_.get(n));
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeBytesSize(7, getPhonesLookupBytes());
        for (int i1 = 0; i1 < this.address_.size(); i1++)
          k += CodedOutputStream.computeMessageSize(8, (MessageLite)this.address_.get(i1));
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeBytesSize(9, getGroupsBytes());
        int i2 = 0;
        for (int i3 = 0; i3 < this.otherId_.size(); i3++)
          i2 += CodedOutputStream.computeBytesSizeNoTag(this.otherId_.getByteString(i3));
        int i4 = k + i2 + 1 * this.otherId_.size();
        if ((0x40 & this.bitField0_) == 64)
          i4 += CodedOutputStream.computeBytesSize(11, getPublicUserNameBytes());
        if ((0x80 & this.bitField0_) == 128)
          i4 += CodedOutputStream.computeInt64Size(12, this.lastUpdatedTime_);
        if ((0x100 & this.bitField0_) == 256)
          i4 += CodedOutputStream.computeInt32Size(13, this.affinity_);
        if ((0x200 & this.bitField0_) == 512)
          i4 += CodedOutputStream.computeInt32Size(14, this.callAffinity_);
        if ((0x400 & this.bitField0_) == 1024)
          i4 += CodedOutputStream.computeInt32Size(15, this.attributes_);
        if ((0x800 & this.bitField0_) == 2048)
          i4 += CodedOutputStream.computeBoolSize(16, this.iAmFollowing_);
        if ((0x1000 & this.bitField0_) == 4096)
          i4 += CodedOutputStream.computeBoolSize(17, this.isFollowingMe_);
        if ((0x2000 & this.bitField0_) == 8192)
          i4 += CodedOutputStream.computeBoolSize(18, this.inMyContacts_);
        int i5 = 0;
        for (int i6 = 0; i6 < this.circleId_.size(); i6++)
          i5 += CodedOutputStream.computeBytesSizeNoTag(this.circleId_.getByteString(i6));
        i7 = i4 + i5 + 2 * this.circleId_.size();
        if ((0x4000 & this.bitField0_) == 16384)
          i7 += CodedOutputStream.computeBytesSize(20, getPackedCircleIdsBytes());
        if ((0x8000 & this.bitField0_) == 32768)
          i7 += CodedOutputStream.computeEnumSize(21, this.profileType_.getNumber());
        if ((0x10000 & this.bitField0_) == 65536)
          i7 += CodedOutputStream.computeBytesSize(22, getTagLineBytes());
        this.memoizedSerializedSize = i7;
      }
    }

    public final String getTagLine()
    {
      Object localObject1 = this.tagLine_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.tagLine_ = str;
      }
    }

    public final boolean hasAffinity()
    {
      if ((0x100 & this.bitField0_) == 256);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasAttributes()
    {
      if ((0x400 & this.bitField0_) == 1024);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasCallAffinity()
    {
      if ((0x200 & this.bitField0_) == 512);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDisplayName()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasGroups()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    @Deprecated
    public final boolean hasIAmFollowing()
    {
      if ((0x800 & this.bitField0_) == 2048);
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

    public final boolean hasInMyContacts()
    {
      if ((0x2000 & this.bitField0_) == 8192);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    @Deprecated
    public final boolean hasIsFollowingMe()
    {
      if ((0x1000 & this.bitField0_) == 4096);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasLastUpdatedTime()
    {
      if ((0x80 & this.bitField0_) == 128);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPackedCircleIds()
    {
      if ((0x4000 & this.bitField0_) == 16384);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPhonesLookup()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPhotoUrl()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPreferredEmail()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasProfileType()
    {
      if ((0x8000 & this.bitField0_) == 32768);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPublicUserName()
    {
      if ((0x40 & this.bitField0_) == 64);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasTagLine()
    {
      if ((0x10000 & this.bitField0_) == 65536);
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
        paramCodedOutputStream.writeBytes(1, getIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getDisplayNameBytes());
      for (int i = 0; i < this.email_.size(); i++)
        paramCodedOutputStream.writeMessage(3, (MessageLite)this.email_.get(i));
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(4, getPreferredEmailBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBytes(5, getPhotoUrlBytes());
      for (int j = 0; j < this.phone_.size(); j++)
        paramCodedOutputStream.writeMessage(6, (MessageLite)this.phone_.get(j));
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeBytes(7, getPhonesLookupBytes());
      for (int k = 0; k < this.address_.size(); k++)
        paramCodedOutputStream.writeMessage(8, (MessageLite)this.address_.get(k));
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeBytes(9, getGroupsBytes());
      for (int m = 0; m < this.otherId_.size(); m++)
        paramCodedOutputStream.writeBytes(10, this.otherId_.getByteString(m));
      if ((0x40 & this.bitField0_) == 64)
        paramCodedOutputStream.writeBytes(11, getPublicUserNameBytes());
      if ((0x80 & this.bitField0_) == 128)
        paramCodedOutputStream.writeInt64(12, this.lastUpdatedTime_);
      if ((0x100 & this.bitField0_) == 256)
        paramCodedOutputStream.writeInt32(13, this.affinity_);
      if ((0x200 & this.bitField0_) == 512)
        paramCodedOutputStream.writeInt32(14, this.callAffinity_);
      if ((0x400 & this.bitField0_) == 1024)
        paramCodedOutputStream.writeInt32(15, this.attributes_);
      if ((0x800 & this.bitField0_) == 2048)
        paramCodedOutputStream.writeBool(16, this.iAmFollowing_);
      if ((0x1000 & this.bitField0_) == 4096)
        paramCodedOutputStream.writeBool(17, this.isFollowingMe_);
      if ((0x2000 & this.bitField0_) == 8192)
        paramCodedOutputStream.writeBool(18, this.inMyContacts_);
      for (int n = 0; n < this.circleId_.size(); n++)
        paramCodedOutputStream.writeBytes(19, this.circleId_.getByteString(n));
      if ((0x4000 & this.bitField0_) == 16384)
        paramCodedOutputStream.writeBytes(20, getPackedCircleIdsBytes());
      if ((0x8000 & this.bitField0_) == 32768)
        paramCodedOutputStream.writeEnum(21, this.profileType_.getNumber());
      if ((0x10000 & this.bitField0_) == 65536)
        paramCodedOutputStream.writeBytes(22, getTagLineBytes());
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Contact.MobileContact, Builder>
      implements Contact.MobileContactOrBuilder
    {
      private List<Contact.Address> address_ = Collections.emptyList();
      private int affinity_;
      private int attributes_;
      private int bitField0_;
      private int callAffinity_;
      private LazyStringList circleId_ = LazyStringArrayList.EMPTY;
      private Object displayName_ = "";
      private List<Contact.Email> email_ = Collections.emptyList();
      private Object groups_ = "";
      private boolean iAmFollowing_;
      private Object id_ = "";
      private boolean inMyContacts_;
      private boolean isFollowingMe_;
      private long lastUpdatedTime_;
      private LazyStringList otherId_ = LazyStringArrayList.EMPTY;
      private Object packedCircleIds_ = "";
      private List<Contact.Phone> phone_ = Collections.emptyList();
      private Object phonesLookup_ = "";
      private Object photoUrl_ = "";
      private Object preferredEmail_ = "";
      private Contact.MobileContact.ProfileType profileType_ = Contact.MobileContact.ProfileType.UNKNOWN;
      private Object publicUserName_ = "";
      private Object tagLine_ = "";

      private Contact.MobileContact buildPartial()
      {
        Contact.MobileContact localMobileContact = new Contact.MobileContact(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Contact.MobileContact.access$2702(localMobileContact, this.id_);
        if ((i & 0x2) == 2)
          k |= 2;
        Contact.MobileContact.access$2802(localMobileContact, this.displayName_);
        if ((0x4 & this.bitField0_) == 4)
        {
          this.email_ = Collections.unmodifiableList(this.email_);
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        }
        Contact.MobileContact.access$2902(localMobileContact, this.email_);
        if ((i & 0x8) == 8)
          k |= 4;
        Contact.MobileContact.access$3002(localMobileContact, this.preferredEmail_);
        if ((i & 0x10) == 16)
          k |= 8;
        Contact.MobileContact.access$3102(localMobileContact, this.photoUrl_);
        if ((0x20 & this.bitField0_) == 32)
        {
          this.phone_ = Collections.unmodifiableList(this.phone_);
          this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        }
        Contact.MobileContact.access$3202(localMobileContact, this.phone_);
        if ((i & 0x40) == 64)
          k |= 16;
        Contact.MobileContact.access$3302(localMobileContact, this.phonesLookup_);
        if ((0x80 & this.bitField0_) == 128)
        {
          this.address_ = Collections.unmodifiableList(this.address_);
          this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        }
        Contact.MobileContact.access$3402(localMobileContact, this.address_);
        if ((i & 0x100) == 256)
          k |= 32;
        Contact.MobileContact.access$3502(localMobileContact, this.groups_);
        if ((0x200 & this.bitField0_) == 512)
        {
          this.otherId_ = new UnmodifiableLazyStringList(this.otherId_);
          this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
        }
        Contact.MobileContact.access$3602(localMobileContact, this.otherId_);
        if ((i & 0x400) == 1024)
          k |= 64;
        Contact.MobileContact.access$3702(localMobileContact, this.publicUserName_);
        if ((i & 0x800) == 2048)
          k |= 128;
        Contact.MobileContact.access$3802(localMobileContact, this.lastUpdatedTime_);
        if ((i & 0x1000) == 4096)
          k |= 256;
        Contact.MobileContact.access$3902(localMobileContact, this.affinity_);
        if ((i & 0x2000) == 8192)
          k |= 512;
        Contact.MobileContact.access$4002(localMobileContact, this.callAffinity_);
        if ((i & 0x4000) == 16384)
          k |= 1024;
        Contact.MobileContact.access$4102(localMobileContact, this.attributes_);
        if ((i & 0x8000) == 32768)
          k |= 2048;
        Contact.MobileContact.access$4202(localMobileContact, this.iAmFollowing_);
        if ((i & 0x10000) == 65536)
          k |= 4096;
        Contact.MobileContact.access$4302(localMobileContact, this.isFollowingMe_);
        if ((i & 0x20000) == 131072)
          k |= 8192;
        Contact.MobileContact.access$4402(localMobileContact, this.inMyContacts_);
        if ((0x40000 & this.bitField0_) == 262144)
        {
          this.circleId_ = new UnmodifiableLazyStringList(this.circleId_);
          this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
        }
        Contact.MobileContact.access$4502(localMobileContact, this.circleId_);
        if ((i & 0x80000) == 524288)
          k |= 16384;
        Contact.MobileContact.access$4602(localMobileContact, this.packedCircleIds_);
        if ((0x100000 & i) == 1048576)
          k |= 32768;
        Contact.MobileContact.access$4702(localMobileContact, this.profileType_);
        if ((0x200000 & i) == 2097152)
          k |= 65536;
        Contact.MobileContact.access$4802(localMobileContact, this.tagLine_);
        Contact.MobileContact.access$4902(localMobileContact, k);
        return localMobileContact;
      }

      private Builder clear()
      {
        super.clear();
        this.id_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.displayName_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.email_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.preferredEmail_ = "";
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.photoUrl_ = "";
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.phone_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.phonesLookup_ = "";
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.address_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        this.groups_ = "";
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        this.otherId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
        this.publicUserName_ = "";
        this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
        this.lastUpdatedTime_ = 0L;
        this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
        this.affinity_ = 0;
        this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
        this.callAffinity_ = 0;
        this.bitField0_ = (0xFFFFDFFF & this.bitField0_);
        this.attributes_ = 0;
        this.bitField0_ = (0xFFFFBFFF & this.bitField0_);
        this.iAmFollowing_ = false;
        this.bitField0_ = (0xFFFF7FFF & this.bitField0_);
        this.isFollowingMe_ = false;
        this.bitField0_ = (0xFFFEFFFF & this.bitField0_);
        this.inMyContacts_ = false;
        this.bitField0_ = (0xFFFDFFFF & this.bitField0_);
        this.circleId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
        this.packedCircleIds_ = "";
        this.bitField0_ = (0xFFF7FFFF & this.bitField0_);
        this.profileType_ = Contact.MobileContact.ProfileType.UNKNOWN;
        this.bitField0_ = (0xFFEFFFFF & this.bitField0_);
        this.tagLine_ = "";
        this.bitField0_ = (0xFFDFFFFF & this.bitField0_);
        return this;
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureAddressIsMutable()
      {
        if ((0x80 & this.bitField0_) != 128)
        {
          this.address_ = new ArrayList(this.address_);
          this.bitField0_ = (0x80 | this.bitField0_);
        }
      }

      private void ensureCircleIdIsMutable()
      {
        if ((0x40000 & this.bitField0_) != 262144)
        {
          this.circleId_ = new LazyStringArrayList(this.circleId_);
          this.bitField0_ = (0x40000 | this.bitField0_);
        }
      }

      private void ensureEmailIsMutable()
      {
        if ((0x4 & this.bitField0_) != 4)
        {
          this.email_ = new ArrayList(this.email_);
          this.bitField0_ = (0x4 | this.bitField0_);
        }
      }

      private void ensureOtherIdIsMutable()
      {
        if ((0x200 & this.bitField0_) != 512)
        {
          this.otherId_ = new LazyStringArrayList(this.otherId_);
          this.bitField0_ = (0x200 | this.bitField0_);
        }
      }

      private void ensurePhoneIsMutable()
      {
        if ((0x20 & this.bitField0_) != 32)
        {
          this.phone_ = new ArrayList(this.phone_);
          this.bitField0_ = (0x20 | this.bitField0_);
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
            this.id_ = paramCodedInputStream.readBytes();
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.displayName_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            Contact.Email.Builder localBuilder2 = Contact.Email.newBuilder();
            paramCodedInputStream.readMessage(localBuilder2, paramExtensionRegistryLite);
            Contact.Email localEmail = localBuilder2.buildPartial();
            if (localEmail == null)
              throw new NullPointerException();
            ensureEmailIsMutable();
            this.email_.add(localEmail);
            break;
          case 34:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.preferredEmail_ = paramCodedInputStream.readBytes();
            break;
          case 42:
            this.bitField0_ = (0x10 | this.bitField0_);
            this.photoUrl_ = paramCodedInputStream.readBytes();
            break;
          case 50:
            Contact.Phone.Builder localBuilder1 = Contact.Phone.newBuilder();
            paramCodedInputStream.readMessage(localBuilder1, paramExtensionRegistryLite);
            Contact.Phone localPhone = localBuilder1.buildPartial();
            if (localPhone == null)
              throw new NullPointerException();
            ensurePhoneIsMutable();
            this.phone_.add(localPhone);
            break;
          case 58:
            this.bitField0_ = (0x40 | this.bitField0_);
            this.phonesLookup_ = paramCodedInputStream.readBytes();
            break;
          case 66:
            Contact.Address.Builder localBuilder = Contact.Address.newBuilder();
            paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
            Contact.Address localAddress = localBuilder.buildPartial();
            if (localAddress == null)
              throw new NullPointerException();
            ensureAddressIsMutable();
            this.address_.add(localAddress);
            break;
          case 74:
            this.bitField0_ = (0x100 | this.bitField0_);
            this.groups_ = paramCodedInputStream.readBytes();
            break;
          case 82:
            ensureOtherIdIsMutable();
            this.otherId_.add(paramCodedInputStream.readBytes());
            break;
          case 90:
            this.bitField0_ = (0x400 | this.bitField0_);
            this.publicUserName_ = paramCodedInputStream.readBytes();
            break;
          case 96:
            this.bitField0_ = (0x800 | this.bitField0_);
            this.lastUpdatedTime_ = paramCodedInputStream.readInt64();
            break;
          case 104:
            this.bitField0_ = (0x1000 | this.bitField0_);
            this.affinity_ = paramCodedInputStream.readInt32();
            break;
          case 112:
            this.bitField0_ = (0x2000 | this.bitField0_);
            this.callAffinity_ = paramCodedInputStream.readInt32();
            break;
          case 120:
            this.bitField0_ = (0x4000 | this.bitField0_);
            this.attributes_ = paramCodedInputStream.readInt32();
            break;
          case 128:
            this.bitField0_ = (0x8000 | this.bitField0_);
            this.iAmFollowing_ = paramCodedInputStream.readBool();
            break;
          case 136:
            this.bitField0_ = (0x10000 | this.bitField0_);
            this.isFollowingMe_ = paramCodedInputStream.readBool();
            break;
          case 144:
            this.bitField0_ = (0x20000 | this.bitField0_);
            this.inMyContacts_ = paramCodedInputStream.readBool();
            break;
          case 154:
            ensureCircleIdIsMutable();
            this.circleId_.add(paramCodedInputStream.readBytes());
            break;
          case 162:
            this.bitField0_ = (0x80000 | this.bitField0_);
            this.packedCircleIds_ = paramCodedInputStream.readBytes();
            break;
          case 168:
            Contact.MobileContact.ProfileType localProfileType = Contact.MobileContact.ProfileType.valueOf(paramCodedInputStream.readEnum());
            if (localProfileType == null)
              continue;
            this.bitField0_ = (0x100000 | this.bitField0_);
            this.profileType_ = localProfileType;
            break;
          case 178:
          }
          this.bitField0_ = (0x200000 | this.bitField0_);
          this.tagLine_ = paramCodedInputStream.readBytes();
        }
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Contact.MobileContact paramMobileContact)
      {
        if (paramMobileContact == Contact.MobileContact.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramMobileContact.hasId())
          {
            String str9 = paramMobileContact.getId();
            if (str9 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.id_ = str9;
          }
          if (paramMobileContact.hasDisplayName())
          {
            String str8 = paramMobileContact.getDisplayName();
            if (str8 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.displayName_ = str8;
          }
          if (!paramMobileContact.email_.isEmpty())
          {
            if (!this.email_.isEmpty())
              break label162;
            this.email_ = paramMobileContact.email_;
            this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          }
          while (paramMobileContact.hasPreferredEmail())
          {
            String str7 = paramMobileContact.getPreferredEmail();
            if (str7 == null)
            {
              throw new NullPointerException();
              label162: ensureEmailIsMutable();
              this.email_.addAll(paramMobileContact.email_);
            }
            else
            {
              this.bitField0_ = (0x8 | this.bitField0_);
              this.preferredEmail_ = str7;
            }
          }
          if (paramMobileContact.hasPhotoUrl())
          {
            String str6 = paramMobileContact.getPhotoUrl();
            if (str6 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x10 | this.bitField0_);
            this.photoUrl_ = str6;
          }
          if (!paramMobileContact.phone_.isEmpty())
          {
            if (!this.phone_.isEmpty())
              break label312;
            this.phone_ = paramMobileContact.phone_;
            this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
          }
          while (paramMobileContact.hasPhonesLookup())
          {
            String str5 = paramMobileContact.getPhonesLookup();
            if (str5 == null)
            {
              throw new NullPointerException();
              label312: ensurePhoneIsMutable();
              this.phone_.addAll(paramMobileContact.phone_);
            }
            else
            {
              this.bitField0_ = (0x40 | this.bitField0_);
              this.phonesLookup_ = str5;
            }
          }
          if (!paramMobileContact.address_.isEmpty())
          {
            if (!this.address_.isEmpty())
              break label420;
            this.address_ = paramMobileContact.address_;
            this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
          }
          while (paramMobileContact.hasGroups())
          {
            String str4 = paramMobileContact.getGroups();
            if (str4 == null)
            {
              throw new NullPointerException();
              label420: ensureAddressIsMutable();
              this.address_.addAll(paramMobileContact.address_);
            }
            else
            {
              this.bitField0_ = (0x100 | this.bitField0_);
              this.groups_ = str4;
            }
          }
          if (!paramMobileContact.otherId_.isEmpty())
          {
            if (!this.otherId_.isEmpty())
              break label529;
            this.otherId_ = paramMobileContact.otherId_;
            this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
          }
          while (paramMobileContact.hasPublicUserName())
          {
            String str3 = paramMobileContact.getPublicUserName();
            if (str3 == null)
            {
              throw new NullPointerException();
              label529: ensureOtherIdIsMutable();
              this.otherId_.addAll(paramMobileContact.otherId_);
            }
            else
            {
              this.bitField0_ = (0x400 | this.bitField0_);
              this.publicUserName_ = str3;
            }
          }
          if (paramMobileContact.hasLastUpdatedTime())
          {
            long l = paramMobileContact.getLastUpdatedTime();
            this.bitField0_ = (0x800 | this.bitField0_);
            this.lastUpdatedTime_ = l;
          }
          if (paramMobileContact.hasAffinity())
          {
            int k = paramMobileContact.getAffinity();
            this.bitField0_ = (0x1000 | this.bitField0_);
            this.affinity_ = k;
          }
          if (paramMobileContact.hasCallAffinity())
          {
            int j = paramMobileContact.getCallAffinity();
            this.bitField0_ = (0x2000 | this.bitField0_);
            this.callAffinity_ = j;
          }
          if (paramMobileContact.hasAttributes())
          {
            int i = paramMobileContact.getAttributes();
            this.bitField0_ = (0x4000 | this.bitField0_);
            this.attributes_ = i;
          }
          if (paramMobileContact.hasIAmFollowing())
          {
            boolean bool3 = paramMobileContact.getIAmFollowing();
            this.bitField0_ = (0x8000 | this.bitField0_);
            this.iAmFollowing_ = bool3;
          }
          if (paramMobileContact.hasIsFollowingMe())
          {
            boolean bool2 = paramMobileContact.getIsFollowingMe();
            this.bitField0_ = (0x10000 | this.bitField0_);
            this.isFollowingMe_ = bool2;
          }
          if (paramMobileContact.hasInMyContacts())
          {
            boolean bool1 = paramMobileContact.getInMyContacts();
            this.bitField0_ = (0x20000 | this.bitField0_);
            this.inMyContacts_ = bool1;
          }
          if (!paramMobileContact.circleId_.isEmpty())
          {
            if (!this.circleId_.isEmpty())
              break label851;
            this.circleId_ = paramMobileContact.circleId_;
            this.bitField0_ = (0xFFFBFFFF & this.bitField0_);
          }
          while (paramMobileContact.hasPackedCircleIds())
          {
            String str2 = paramMobileContact.getPackedCircleIds();
            if (str2 == null)
            {
              throw new NullPointerException();
              label851: ensureCircleIdIsMutable();
              this.circleId_.addAll(paramMobileContact.circleId_);
            }
            else
            {
              this.bitField0_ = (0x80000 | this.bitField0_);
              this.packedCircleIds_ = str2;
            }
          }
          if (paramMobileContact.hasProfileType())
          {
            Contact.MobileContact.ProfileType localProfileType = paramMobileContact.getProfileType();
            if (localProfileType == null)
              throw new NullPointerException();
            this.bitField0_ = (0x100000 | this.bitField0_);
            this.profileType_ = localProfileType;
          }
          if (paramMobileContact.hasTagLine())
          {
            String str1 = paramMobileContact.getTagLine();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x200000 | this.bitField0_);
            this.tagLine_ = str1;
          }
        }
      }
    }

    public static enum ProfileType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<ProfileType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        PLUSPAGE = new ProfileType("PLUSPAGE", 2, 2);
        SQUARE = new ProfileType("SQUARE", 3, 3);
        ProfileType[] arrayOfProfileType = new ProfileType[4];
        arrayOfProfileType[0] = UNKNOWN;
        arrayOfProfileType[1] = USER;
        arrayOfProfileType[2] = PLUSPAGE;
        arrayOfProfileType[3] = SQUARE;
      }

      private ProfileType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static ProfileType valueOf(int paramInt)
      {
        ProfileType localProfileType;
        switch (paramInt)
        {
        default:
          localProfileType = null;
        case 0:
        case 1:
        case 2:
        case 3:
        }
        while (true)
        {
          return localProfileType;
          localProfileType = UNKNOWN;
          continue;
          localProfileType = USER;
          continue;
          localProfileType = PLUSPAGE;
          continue;
          localProfileType = SQUARE;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface MobileContactOrBuilder extends MessageLiteOrBuilder
  {
  }

  public static final class Phone extends GeneratedMessageLite
    implements Contact.PhoneOrBuilder
  {
    private static final Phone defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private boolean evergreen_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Common.Metadata metadata_;
    private Object phone_;
    private Object type_;

    static
    {
      Phone localPhone = new Phone();
      defaultInstance = localPhone;
      localPhone.metadata_ = Common.Metadata.getDefaultInstance();
      localPhone.phone_ = "";
      localPhone.type_ = "";
      localPhone.evergreen_ = false;
    }

    private Phone()
    {
    }

    private Phone(Builder paramBuilder)
    {
      super();
    }

    public static Phone getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getPhoneBytes()
    {
      Object localObject = this.phone_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.phone_ = localByteString;
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
      return Builder.access$900();
    }

    public final boolean getEvergreen()
    {
      return this.evergreen_;
    }

    public final Common.Metadata getMetadata()
    {
      return this.metadata_;
    }

    public final String getPhone()
    {
      Object localObject1 = this.phone_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.phone_ = str;
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
          k = 0 + CodedOutputStream.computeMessageSize(1, this.metadata_);
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeBytesSize(2, getPhoneBytes());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getTypeBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeBoolSize(4, this.evergreen_);
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

    public final boolean hasEvergreen()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasMetadata()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasPhone()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
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
        paramCodedOutputStream.writeMessage(1, this.metadata_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBytes(2, getPhoneBytes());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getTypeBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeBool(4, this.evergreen_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Contact.Phone, Builder>
      implements Contact.PhoneOrBuilder
    {
      private int bitField0_;
      private boolean evergreen_;
      private Common.Metadata metadata_ = Common.Metadata.getDefaultInstance();
      private Object phone_ = "";
      private Object type_ = "";

      private Builder clear()
      {
        super.clear();
        this.metadata_ = Common.Metadata.getDefaultInstance();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.phone_ = "";
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.type_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.evergreen_ = false;
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
          case 10:
            Common.Metadata.Builder localBuilder = Common.Metadata.newBuilder();
            if ((0x1 & this.bitField0_) == 1);
            Common.Metadata localMetadata;
            for (int j = 1; ; j = 0)
            {
              if (j != 0)
                localBuilder.mergeFrom(this.metadata_);
              paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
              localMetadata = localBuilder.buildPartial();
              if (localMetadata != null)
                break;
              throw new NullPointerException();
            }
            this.metadata_ = localMetadata;
            this.bitField0_ = (0x1 | this.bitField0_);
            break;
          case 18:
            this.bitField0_ = (0x2 | this.bitField0_);
            this.phone_ = paramCodedInputStream.readBytes();
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.type_ = paramCodedInputStream.readBytes();
            break;
          case 32:
          }
          this.bitField0_ = (0x8 | this.bitField0_);
          this.evergreen_ = paramCodedInputStream.readBool();
        }
      }

      public final Contact.Phone buildPartial()
      {
        Contact.Phone localPhone = new Contact.Phone(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Contact.Phone.access$1102(localPhone, this.metadata_);
        if ((i & 0x2) == 2)
          k |= 2;
        Contact.Phone.access$1202(localPhone, this.phone_);
        if ((i & 0x4) == 4)
          k |= 4;
        Contact.Phone.access$1302(localPhone, this.type_);
        if ((i & 0x8) == 8)
          k |= 8;
        Contact.Phone.access$1402(localPhone, this.evergreen_);
        Contact.Phone.access$1502(localPhone, k);
        return localPhone;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Contact.Phone paramPhone)
      {
        if (paramPhone == Contact.Phone.getDefaultInstance());
        while (true)
        {
          return this;
          Common.Metadata localMetadata;
          if (paramPhone.hasMetadata())
          {
            localMetadata = paramPhone.getMetadata();
            if (((0x1 & this.bitField0_) != 1) || (this.metadata_ == Common.Metadata.getDefaultInstance()))
              break label97;
          }
          String str2;
          label97: for (this.metadata_ = Common.Metadata.newBuilder(this.metadata_).mergeFrom(localMetadata).buildPartial(); ; this.metadata_ = localMetadata)
          {
            this.bitField0_ = (0x1 | this.bitField0_);
            if (!paramPhone.hasPhone())
              break label122;
            str2 = paramPhone.getPhone();
            if (str2 != null)
              break;
            throw new NullPointerException();
          }
          this.bitField0_ = (0x2 | this.bitField0_);
          this.phone_ = str2;
          label122: if (paramPhone.hasType())
          {
            String str1 = paramPhone.getType();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.type_ = str1;
          }
          if (paramPhone.hasEvergreen())
          {
            boolean bool = paramPhone.getEvergreen();
            this.bitField0_ = (0x8 | this.bitField0_);
            this.evergreen_ = bool;
          }
        }
      }
    }
  }

  public static abstract interface PhoneOrBuilder extends MessageLiteOrBuilder
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.contacts.proto.Contact
 * JD-Core Version:    0.6.2
 */