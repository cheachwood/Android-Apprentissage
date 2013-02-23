package com.google.apps.gcomm.hangout.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class HangoutInviteNotification extends GeneratedMessageLite
  implements HangoutInviteNotificationOrBuilder
{
  private static final HangoutInviteNotification defaultInstance;
  private static final long serialVersionUID;
  private int bitField0_;
  private Command command_;
  private HangoutStartContext context_;
  private DismissReason dismissReason_;
  private HangoutType hangoutType_;
  private byte memoizedIsInitialized = -1;
  private int memoizedSerializedSize = -1;
  private NotificationType notificationType_;
  private Status status_;

  static
  {
    HangoutInviteNotification localHangoutInviteNotification = new HangoutInviteNotification();
    defaultInstance = localHangoutInviteNotification;
    localHangoutInviteNotification.context_ = HangoutStartContext.getDefaultInstance();
    localHangoutInviteNotification.status_ = Status.RINGING;
    localHangoutInviteNotification.command_ = Command.RING;
    localHangoutInviteNotification.notificationType_ = NotificationType.NOTIFICATION_RING;
    localHangoutInviteNotification.hangoutType_ = HangoutType.REGULAR;
    localHangoutInviteNotification.dismissReason_ = DismissReason.UNKNOWN;
  }

  private HangoutInviteNotification()
  {
  }

  private HangoutInviteNotification(Builder paramBuilder)
  {
    super((byte)0);
  }

  public static HangoutInviteNotification getDefaultInstance()
  {
    return defaultInstance;
  }

  public static HangoutInviteNotification parseFrom(byte[] paramArrayOfByte)
    throws InvalidProtocolBufferException
  {
    return Builder.access$000((Builder)Builder.access$100().mergeFrom(paramArrayOfByte));
  }

  public final Command getCommand()
  {
    return this.command_;
  }

  public final HangoutStartContext getContext()
  {
    return this.context_;
  }

  public final DismissReason getDismissReason()
  {
    return this.dismissReason_;
  }

  public final HangoutType getHangoutType()
  {
    return this.hangoutType_;
  }

  public final NotificationType getNotificationType()
  {
    return this.notificationType_;
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
        k = 0 + CodedOutputStream.computeMessageSize(1, this.context_);
      if ((0x2 & this.bitField0_) == 2)
        k += CodedOutputStream.computeEnumSize(2, this.status_.getNumber());
      if ((0x4 & this.bitField0_) == 4)
        k += CodedOutputStream.computeEnumSize(3, this.command_.getNumber());
      if ((0x8 & this.bitField0_) == 8)
        k += CodedOutputStream.computeEnumSize(5, this.notificationType_.getNumber());
      if ((0x10 & this.bitField0_) == 16)
        k += CodedOutputStream.computeEnumSize(6, this.hangoutType_.getNumber());
      if ((0x20 & this.bitField0_) == 32)
        k += CodedOutputStream.computeEnumSize(7, this.dismissReason_.getNumber());
      this.memoizedSerializedSize = k;
    }
  }

  public final Status getStatus()
  {
    return this.status_;
  }

  public final boolean hasCommand()
  {
    if ((0x4 & this.bitField0_) == 4);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasContext()
  {
    int i = 1;
    if ((0x1 & this.bitField0_) == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean hasDismissReason()
  {
    if ((0x20 & this.bitField0_) == 32);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasHangoutType()
  {
    if ((0x10 & this.bitField0_) == 16);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasNotificationType()
  {
    if ((0x8 & this.bitField0_) == 8);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasStatus()
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
      if (!hasContext())
      {
        this.memoizedIsInitialized = 0;
        i = 0;
      }
      else if (!this.context_.isInitialized())
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
      paramCodedOutputStream.writeMessage(1, this.context_);
    if ((0x2 & this.bitField0_) == 2)
      paramCodedOutputStream.writeEnum(2, this.status_.getNumber());
    if ((0x4 & this.bitField0_) == 4)
      paramCodedOutputStream.writeEnum(3, this.command_.getNumber());
    if ((0x8 & this.bitField0_) == 8)
      paramCodedOutputStream.writeEnum(5, this.notificationType_.getNumber());
    if ((0x10 & this.bitField0_) == 16)
      paramCodedOutputStream.writeEnum(6, this.hangoutType_.getNumber());
    if ((0x20 & this.bitField0_) == 32)
      paramCodedOutputStream.writeEnum(7, this.dismissReason_.getNumber());
  }

  public static final class Builder extends GeneratedMessageLite.Builder<HangoutInviteNotification, Builder>
    implements HangoutInviteNotificationOrBuilder
  {
    private int bitField0_;
    private HangoutInviteNotification.Command command_ = HangoutInviteNotification.Command.RING;
    private HangoutStartContext context_ = HangoutStartContext.getDefaultInstance();
    private HangoutInviteNotification.DismissReason dismissReason_ = HangoutInviteNotification.DismissReason.UNKNOWN;
    private HangoutInviteNotification.HangoutType hangoutType_ = HangoutInviteNotification.HangoutType.REGULAR;
    private HangoutInviteNotification.NotificationType notificationType_ = HangoutInviteNotification.NotificationType.NOTIFICATION_RING;
    private HangoutInviteNotification.Status status_ = HangoutInviteNotification.Status.RINGING;

    private HangoutInviteNotification buildPartial()
    {
      HangoutInviteNotification localHangoutInviteNotification = new HangoutInviteNotification(this, (byte)0);
      int i = this.bitField0_;
      int j = i & 0x1;
      int k = 0;
      if (j == 1)
        k = 1;
      HangoutInviteNotification.access$302(localHangoutInviteNotification, this.context_);
      if ((i & 0x2) == 2)
        k |= 2;
      HangoutInviteNotification.access$402(localHangoutInviteNotification, this.status_);
      if ((i & 0x4) == 4)
        k |= 4;
      HangoutInviteNotification.access$502(localHangoutInviteNotification, this.command_);
      if ((i & 0x8) == 8)
        k |= 8;
      HangoutInviteNotification.access$602(localHangoutInviteNotification, this.notificationType_);
      if ((i & 0x10) == 16)
        k |= 16;
      HangoutInviteNotification.access$702(localHangoutInviteNotification, this.hangoutType_);
      if ((i & 0x20) == 32)
        k |= 32;
      HangoutInviteNotification.access$802(localHangoutInviteNotification, this.dismissReason_);
      HangoutInviteNotification.access$902(localHangoutInviteNotification, k);
      return localHangoutInviteNotification;
    }

    private Builder clear()
    {
      super.clear();
      this.context_ = HangoutStartContext.getDefaultInstance();
      this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
      this.status_ = HangoutInviteNotification.Status.RINGING;
      this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
      this.command_ = HangoutInviteNotification.Command.RING;
      this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
      this.notificationType_ = HangoutInviteNotification.NotificationType.NOTIFICATION_RING;
      this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
      this.hangoutType_ = HangoutInviteNotification.HangoutType.REGULAR;
      this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
      this.dismissReason_ = HangoutInviteNotification.DismissReason.UNKNOWN;
      this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
      return this;
    }

    private Builder clone()
    {
      return new Builder().mergeFrom(buildPartial());
    }

    private boolean hasContext()
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
          HangoutStartContext.Builder localBuilder = HangoutStartContext.newBuilder();
          if (hasContext())
            localBuilder.mergeFrom(this.context_);
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          HangoutStartContext localHangoutStartContext = localBuilder.buildPartial();
          if (localHangoutStartContext == null)
            throw new NullPointerException();
          this.context_ = localHangoutStartContext;
          this.bitField0_ = (0x1 | this.bitField0_);
          break;
        case 16:
          HangoutInviteNotification.Status localStatus = HangoutInviteNotification.Status.valueOf(paramCodedInputStream.readEnum());
          if (localStatus == null)
            continue;
          this.bitField0_ = (0x2 | this.bitField0_);
          this.status_ = localStatus;
          break;
        case 24:
          HangoutInviteNotification.Command localCommand = HangoutInviteNotification.Command.valueOf(paramCodedInputStream.readEnum());
          if (localCommand == null)
            continue;
          this.bitField0_ = (0x4 | this.bitField0_);
          this.command_ = localCommand;
          break;
        case 40:
          HangoutInviteNotification.NotificationType localNotificationType = HangoutInviteNotification.NotificationType.valueOf(paramCodedInputStream.readEnum());
          if (localNotificationType == null)
            continue;
          this.bitField0_ = (0x8 | this.bitField0_);
          this.notificationType_ = localNotificationType;
          break;
        case 48:
          HangoutInviteNotification.HangoutType localHangoutType = HangoutInviteNotification.HangoutType.valueOf(paramCodedInputStream.readEnum());
          if (localHangoutType == null)
            continue;
          this.bitField0_ = (0x10 | this.bitField0_);
          this.hangoutType_ = localHangoutType;
          break;
        case 56:
        }
        HangoutInviteNotification.DismissReason localDismissReason = HangoutInviteNotification.DismissReason.valueOf(paramCodedInputStream.readEnum());
        if (localDismissReason != null)
        {
          this.bitField0_ = (0x20 | this.bitField0_);
          this.dismissReason_ = localDismissReason;
        }
      }
    }

    public final boolean isInitialized()
    {
      boolean bool1 = hasContext();
      boolean bool2 = false;
      if (!bool1);
      while (true)
      {
        return bool2;
        boolean bool3 = this.context_.isInitialized();
        bool2 = false;
        if (bool3)
          bool2 = true;
      }
    }

    public final Builder mergeFrom(HangoutInviteNotification paramHangoutInviteNotification)
    {
      if (paramHangoutInviteNotification == HangoutInviteNotification.getDefaultInstance());
      while (true)
      {
        return this;
        HangoutStartContext localHangoutStartContext;
        if (paramHangoutInviteNotification.hasContext())
        {
          localHangoutStartContext = paramHangoutInviteNotification.getContext();
          if (((0x1 & this.bitField0_) != 1) || (this.context_ == HangoutStartContext.getDefaultInstance()))
            break label97;
        }
        HangoutInviteNotification.Status localStatus;
        label97: for (this.context_ = HangoutStartContext.newBuilder(this.context_).mergeFrom(localHangoutStartContext).buildPartial(); ; this.context_ = localHangoutStartContext)
        {
          this.bitField0_ = (0x1 | this.bitField0_);
          if (!paramHangoutInviteNotification.hasStatus())
            break label122;
          localStatus = paramHangoutInviteNotification.getStatus();
          if (localStatus != null)
            break;
          throw new NullPointerException();
        }
        this.bitField0_ = (0x2 | this.bitField0_);
        this.status_ = localStatus;
        label122: if (paramHangoutInviteNotification.hasCommand())
        {
          HangoutInviteNotification.Command localCommand = paramHangoutInviteNotification.getCommand();
          if (localCommand == null)
            throw new NullPointerException();
          this.bitField0_ = (0x4 | this.bitField0_);
          this.command_ = localCommand;
        }
        if (paramHangoutInviteNotification.hasNotificationType())
        {
          HangoutInviteNotification.NotificationType localNotificationType = paramHangoutInviteNotification.getNotificationType();
          if (localNotificationType == null)
            throw new NullPointerException();
          this.bitField0_ = (0x8 | this.bitField0_);
          this.notificationType_ = localNotificationType;
        }
        if (paramHangoutInviteNotification.hasHangoutType())
        {
          HangoutInviteNotification.HangoutType localHangoutType = paramHangoutInviteNotification.getHangoutType();
          if (localHangoutType == null)
            throw new NullPointerException();
          this.bitField0_ = (0x10 | this.bitField0_);
          this.hangoutType_ = localHangoutType;
        }
        if (paramHangoutInviteNotification.hasDismissReason())
        {
          HangoutInviteNotification.DismissReason localDismissReason = paramHangoutInviteNotification.getDismissReason();
          if (localDismissReason == null)
            throw new NullPointerException();
          this.bitField0_ = (0x20 | this.bitField0_);
          this.dismissReason_ = localDismissReason;
        }
      }
    }
  }

  public static enum Command
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<Command> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      DISMISSED = new Command("DISMISSED", 1, 1);
      Command[] arrayOfCommand = new Command[2];
      arrayOfCommand[0] = RING;
      arrayOfCommand[1] = DISMISSED;
    }

    private Command(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static Command valueOf(int paramInt)
    {
      Command localCommand;
      switch (paramInt)
      {
      default:
        localCommand = null;
      case 0:
      case 1:
      }
      while (true)
      {
        return localCommand;
        localCommand = RING;
        continue;
        localCommand = DISMISSED;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum DismissReason
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<DismissReason> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      USER_KICKED = new DismissReason("USER_KICKED", 2, 2);
      INVITER_CANCELLED = new DismissReason("INVITER_CANCELLED", 3, 3);
      INVITE_TIMEOUT = new DismissReason("INVITE_TIMEOUT", 4, 4);
      DismissReason[] arrayOfDismissReason = new DismissReason[5];
      arrayOfDismissReason[0] = UNKNOWN;
      arrayOfDismissReason[1] = USER_RESPONDED;
      arrayOfDismissReason[2] = USER_KICKED;
      arrayOfDismissReason[3] = INVITER_CANCELLED;
      arrayOfDismissReason[4] = INVITE_TIMEOUT;
    }

    private DismissReason(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static DismissReason valueOf(int paramInt)
    {
      DismissReason localDismissReason;
      switch (paramInt)
      {
      default:
        localDismissReason = null;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      }
      while (true)
      {
        return localDismissReason;
        localDismissReason = UNKNOWN;
        continue;
        localDismissReason = USER_RESPONDED;
        continue;
        localDismissReason = USER_KICKED;
        continue;
        localDismissReason = INVITER_CANCELLED;
        continue;
        localDismissReason = INVITE_TIMEOUT;
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
      HangoutType[] arrayOfHangoutType = new HangoutType[2];
      arrayOfHangoutType[0] = REGULAR;
      arrayOfHangoutType[1] = UNSUPPORTED;
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
      }
      while (true)
      {
        return localHangoutType;
        localHangoutType = REGULAR;
        continue;
        localHangoutType = UNSUPPORTED;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum NotificationType
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<NotificationType> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      NOTIFICATION_DING = new NotificationType("NOTIFICATION_DING", 1, 1);
      NotificationType[] arrayOfNotificationType = new NotificationType[2];
      arrayOfNotificationType[0] = NOTIFICATION_RING;
      arrayOfNotificationType[1] = NOTIFICATION_DING;
    }

    private NotificationType(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static NotificationType valueOf(int paramInt)
    {
      NotificationType localNotificationType;
      switch (paramInt)
      {
      default:
        localNotificationType = null;
      case 0:
      case 1:
      }
      while (true)
      {
        return localNotificationType;
        localNotificationType = NOTIFICATION_RING;
        continue;
        localNotificationType = NOTIFICATION_DING;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum Status
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<Status> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      ACCEPTED = new Status("ACCEPTED", 1, 1);
      IGNORED = new Status("IGNORED", 2, 2);
      TIMEOUT = new Status("TIMEOUT", 3, 3);
      Status[] arrayOfStatus = new Status[4];
      arrayOfStatus[0] = RINGING;
      arrayOfStatus[1] = ACCEPTED;
      arrayOfStatus[2] = IGNORED;
      arrayOfStatus[3] = TIMEOUT;
    }

    private Status(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static Status valueOf(int paramInt)
    {
      Status localStatus;
      switch (paramInt)
      {
      default:
        localStatus = null;
      case 0:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localStatus;
        localStatus = RINGING;
        continue;
        localStatus = ACCEPTED;
        continue;
        localStatus = IGNORED;
        continue;
        localStatus = TIMEOUT;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.apps.gcomm.hangout.proto.HangoutInviteNotification
 * JD-Core Version:    0.6.2
 */