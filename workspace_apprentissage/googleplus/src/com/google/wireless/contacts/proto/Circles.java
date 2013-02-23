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
import com.google.protobuf.MessageLiteOrBuilder;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class Circles
{
  public static final class MobileCircle extends GeneratedMessageLite
    implements Circles.MobileCircleOrBuilder
  {
    private static final MobileCircle defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private Object id_;
    private int memberCount_;
    private UserMembershipStatus membershipStatus_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private Object name_;
    private int semanticHints_;
    private Type type_;

    static
    {
      MobileCircle localMobileCircle = new MobileCircle();
      defaultInstance = localMobileCircle;
      localMobileCircle.id_ = "";
      localMobileCircle.type_ = Type.PERSONAL;
      localMobileCircle.name_ = "";
      localMobileCircle.memberCount_ = 0;
      localMobileCircle.membershipStatus_ = UserMembershipStatus.OWNER;
      localMobileCircle.semanticHints_ = 0;
    }

    private MobileCircle()
    {
    }

    private MobileCircle(Builder paramBuilder)
    {
      super();
    }

    public static MobileCircle getDefaultInstance()
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

    public final int getMemberCount()
    {
      return this.memberCount_;
    }

    public final UserMembershipStatus getMembershipStatus()
    {
      return this.membershipStatus_;
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

    public final int getSemanticHints()
    {
      return this.semanticHints_;
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
          k = 0 + CodedOutputStream.computeBytesSize(1, getIdBytes());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeEnumSize(2, this.type_.getNumber());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeBytesSize(3, getNameBytes());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeInt32Size(4, this.memberCount_);
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeEnumSize(5, this.membershipStatus_.getNumber());
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeInt32Size(6, this.semanticHints_);
        this.memoizedSerializedSize = k;
      }
    }

    public final Type getType()
    {
      return this.type_;
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

    public final boolean hasMemberCount()
    {
      if ((0x8 & this.bitField0_) == 8);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasMembershipStatus()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasName()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasSemanticHints()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasType()
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
        paramCodedOutputStream.writeBytes(1, getIdBytes());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeEnum(2, this.type_.getNumber());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeBytes(3, getNameBytes());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeInt32(4, this.memberCount_);
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeEnum(5, this.membershipStatus_.getNumber());
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeInt32(6, this.semanticHints_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Circles.MobileCircle, Builder>
      implements Circles.MobileCircleOrBuilder
    {
      private int bitField0_;
      private Object id_ = "";
      private int memberCount_;
      private Circles.MobileCircle.UserMembershipStatus membershipStatus_ = Circles.MobileCircle.UserMembershipStatus.OWNER;
      private Object name_ = "";
      private int semanticHints_;
      private Circles.MobileCircle.Type type_ = Circles.MobileCircle.Type.PERSONAL;

      private Circles.MobileCircle buildPartial()
      {
        Circles.MobileCircle localMobileCircle = new Circles.MobileCircle(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Circles.MobileCircle.access$302(localMobileCircle, this.id_);
        if ((i & 0x2) == 2)
          k |= 2;
        Circles.MobileCircle.access$402(localMobileCircle, this.type_);
        if ((i & 0x4) == 4)
          k |= 4;
        Circles.MobileCircle.access$502(localMobileCircle, this.name_);
        if ((i & 0x8) == 8)
          k |= 8;
        Circles.MobileCircle.access$602(localMobileCircle, this.memberCount_);
        if ((i & 0x10) == 16)
          k |= 16;
        Circles.MobileCircle.access$702(localMobileCircle, this.membershipStatus_);
        if ((i & 0x20) == 32)
          k |= 32;
        Circles.MobileCircle.access$802(localMobileCircle, this.semanticHints_);
        Circles.MobileCircle.access$902(localMobileCircle, k);
        return localMobileCircle;
      }

      private Builder clear()
      {
        super.clear();
        this.id_ = "";
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.type_ = Circles.MobileCircle.Type.PERSONAL;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.name_ = "";
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.memberCount_ = 0;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.membershipStatus_ = Circles.MobileCircle.UserMembershipStatus.OWNER;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.semanticHints_ = 0;
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
          case 10:
            this.bitField0_ = (0x1 | this.bitField0_);
            this.id_ = paramCodedInputStream.readBytes();
            break;
          case 16:
            Circles.MobileCircle.Type localType = Circles.MobileCircle.Type.valueOf(paramCodedInputStream.readEnum());
            if (localType == null)
              continue;
            this.bitField0_ = (0x2 | this.bitField0_);
            this.type_ = localType;
            break;
          case 26:
            this.bitField0_ = (0x4 | this.bitField0_);
            this.name_ = paramCodedInputStream.readBytes();
            break;
          case 32:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.memberCount_ = paramCodedInputStream.readInt32();
            break;
          case 40:
            Circles.MobileCircle.UserMembershipStatus localUserMembershipStatus = Circles.MobileCircle.UserMembershipStatus.valueOf(paramCodedInputStream.readEnum());
            if (localUserMembershipStatus == null)
              continue;
            this.bitField0_ = (0x10 | this.bitField0_);
            this.membershipStatus_ = localUserMembershipStatus;
            break;
          case 48:
          }
          this.bitField0_ = (0x20 | this.bitField0_);
          this.semanticHints_ = paramCodedInputStream.readInt32();
        }
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Circles.MobileCircle paramMobileCircle)
      {
        if (paramMobileCircle == Circles.MobileCircle.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramMobileCircle.hasId())
          {
            String str2 = paramMobileCircle.getId();
            if (str2 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x1 | this.bitField0_);
            this.id_ = str2;
          }
          if (paramMobileCircle.hasType())
          {
            Circles.MobileCircle.Type localType = paramMobileCircle.getType();
            if (localType == null)
              throw new NullPointerException();
            this.bitField0_ = (0x2 | this.bitField0_);
            this.type_ = localType;
          }
          if (paramMobileCircle.hasName())
          {
            String str1 = paramMobileCircle.getName();
            if (str1 == null)
              throw new NullPointerException();
            this.bitField0_ = (0x4 | this.bitField0_);
            this.name_ = str1;
          }
          if (paramMobileCircle.hasMemberCount())
          {
            int j = paramMobileCircle.getMemberCount();
            this.bitField0_ = (0x8 | this.bitField0_);
            this.memberCount_ = j;
          }
          if (paramMobileCircle.hasMembershipStatus())
          {
            Circles.MobileCircle.UserMembershipStatus localUserMembershipStatus = paramMobileCircle.getMembershipStatus();
            if (localUserMembershipStatus == null)
              throw new NullPointerException();
            this.bitField0_ = (0x10 | this.bitField0_);
            this.membershipStatus_ = localUserMembershipStatus;
          }
          if (paramMobileCircle.hasSemanticHints())
          {
            int i = paramMobileCircle.getSemanticHints();
            this.bitField0_ = (0x20 | this.bitField0_);
            this.semanticHints_ = i;
          }
        }
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
        FOLLOWING = new Type("FOLLOWING", 3, 4);
        MY_CIRCLES = new Type("MY_CIRCLES", 4, 5);
        VISIBLE_CIRCLE_MEMBERS = new Type("VISIBLE_CIRCLE_MEMBERS", 5, 6);
        EXTENDED = new Type("EXTENDED", 6, 7);
        DOMAIN = new Type("DOMAIN", 7, 8);
        PUBLIC = new Type("PUBLIC", 8, 9);
        BLOCKED = new Type("BLOCKED", 9, 10);
        ALL_CIRCLES = new Type("ALL_CIRCLES", 10, 11);
        Type[] arrayOfType = new Type[11];
        arrayOfType[0] = PERSONAL;
        arrayOfType[1] = PRIVATE_SHARED;
        arrayOfType[2] = PUBLIC_SHARED;
        arrayOfType[3] = FOLLOWING;
        arrayOfType[4] = MY_CIRCLES;
        arrayOfType[5] = VISIBLE_CIRCLE_MEMBERS;
        arrayOfType[6] = EXTENDED;
        arrayOfType[7] = DOMAIN;
        arrayOfType[8] = PUBLIC;
        arrayOfType[9] = BLOCKED;
        arrayOfType[10] = ALL_CIRCLES;
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
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        }
        while (true)
        {
          return localType;
          localType = PERSONAL;
          continue;
          localType = PRIVATE_SHARED;
          continue;
          localType = PUBLIC_SHARED;
          continue;
          localType = FOLLOWING;
          continue;
          localType = MY_CIRCLES;
          continue;
          localType = VISIBLE_CIRCLE_MEMBERS;
          continue;
          localType = EXTENDED;
          continue;
          localType = DOMAIN;
          continue;
          localType = PUBLIC;
          continue;
          localType = BLOCKED;
          continue;
          localType = ALL_CIRCLES;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static enum UserMembershipStatus
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<UserMembershipStatus> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        MEMBER = new UserMembershipStatus("MEMBER", 1, 2);
        INVITED = new UserMembershipStatus("INVITED", 2, 3);
        UserMembershipStatus[] arrayOfUserMembershipStatus = new UserMembershipStatus[3];
        arrayOfUserMembershipStatus[0] = OWNER;
        arrayOfUserMembershipStatus[1] = MEMBER;
        arrayOfUserMembershipStatus[2] = INVITED;
      }

      private UserMembershipStatus(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static UserMembershipStatus valueOf(int paramInt)
      {
        UserMembershipStatus localUserMembershipStatus;
        switch (paramInt)
        {
        default:
          localUserMembershipStatus = null;
        case 1:
        case 2:
        case 3:
        }
        while (true)
        {
          return localUserMembershipStatus;
          localUserMembershipStatus = OWNER;
          continue;
          localUserMembershipStatus = MEMBER;
          continue;
          localUserMembershipStatus = INVITED;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface MobileCircleOrBuilder extends MessageLiteOrBuilder
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.contacts.proto.Circles
 * JD-Core Version:    0.6.2
 */