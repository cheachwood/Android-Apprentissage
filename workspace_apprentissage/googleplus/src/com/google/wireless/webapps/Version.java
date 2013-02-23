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
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class Version
{
  public static final class ClientVersion extends GeneratedMessageLite
    implements Version.ClientVersionOrBuilder
  {
    private static final ClientVersion defaultInstance;
    private static final long serialVersionUID;
    private App app_;
    private int bitField0_;
    private BuildType buildType_;
    private int dataVersion_;
    private Object deviceHardware_;
    private Object deviceOs_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;
    private PlatformType platformType_;
    private int version_;

    static
    {
      ClientVersion localClientVersion = new ClientVersion();
      defaultInstance = localClientVersion;
      localClientVersion.app_ = App.GOOGLE_PLUS;
      localClientVersion.buildType_ = BuildType.DEVELOPER;
      localClientVersion.platformType_ = PlatformType.WEB;
      localClientVersion.version_ = 0;
      localClientVersion.dataVersion_ = 0;
      localClientVersion.deviceOs_ = "";
      localClientVersion.deviceHardware_ = "";
    }

    private ClientVersion()
    {
    }

    private ClientVersion(Builder paramBuilder)
    {
      super();
    }

    public static ClientVersion getDefaultInstance()
    {
      return defaultInstance;
    }

    private ByteString getDeviceHardwareBytes()
    {
      Object localObject = this.deviceHardware_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.deviceHardware_ = localByteString;
      }
      while (true)
      {
        return localByteString;
        localByteString = (ByteString)localObject;
      }
    }

    private ByteString getDeviceOsBytes()
    {
      Object localObject = this.deviceOs_;
      ByteString localByteString;
      if ((localObject instanceof String))
      {
        localByteString = ByteString.copyFromUtf8((String)localObject);
        this.deviceOs_ = localByteString;
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

    public static Builder newBuilder(ClientVersion paramClientVersion)
    {
      return Builder.access$100().mergeFrom(paramClientVersion);
    }

    public final App getApp()
    {
      return this.app_;
    }

    public final BuildType getBuildType()
    {
      return this.buildType_;
    }

    public final int getDataVersion()
    {
      return this.dataVersion_;
    }

    public final String getDeviceHardware()
    {
      Object localObject1 = this.deviceHardware_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.deviceHardware_ = str;
      }
    }

    public final String getDeviceOs()
    {
      Object localObject1 = this.deviceOs_;
      if ((localObject1 instanceof String));
      String str;
      for (Object localObject2 = (String)localObject1; ; localObject2 = str)
      {
        return localObject2;
        ByteString localByteString = (ByteString)localObject1;
        str = localByteString.toStringUtf8();
        if (Internal.isValidUtf8(localByteString))
          this.deviceOs_ = str;
      }
    }

    public final PlatformType getPlatformType()
    {
      return this.platformType_;
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
          k = 0 + CodedOutputStream.computeEnumSize(1, this.app_.getNumber());
        if ((0x2 & this.bitField0_) == 2)
          k += CodedOutputStream.computeEnumSize(2, this.buildType_.getNumber());
        if ((0x4 & this.bitField0_) == 4)
          k += CodedOutputStream.computeEnumSize(3, this.platformType_.getNumber());
        if ((0x8 & this.bitField0_) == 8)
          k += CodedOutputStream.computeInt32Size(4, this.version_);
        if ((0x10 & this.bitField0_) == 16)
          k += CodedOutputStream.computeInt32Size(5, this.dataVersion_);
        if ((0x20 & this.bitField0_) == 32)
          k += CodedOutputStream.computeBytesSize(6, getDeviceOsBytes());
        if ((0x40 & this.bitField0_) == 64)
          k += CodedOutputStream.computeBytesSize(7, getDeviceHardwareBytes());
        this.memoizedSerializedSize = k;
      }
    }

    public final int getVersion()
    {
      return this.version_;
    }

    public final boolean hasApp()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasBuildType()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDataVersion()
    {
      if ((0x10 & this.bitField0_) == 16);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDeviceHardware()
    {
      if ((0x40 & this.bitField0_) == 64);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasDeviceOs()
    {
      if ((0x20 & this.bitField0_) == 32);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasPlatformType()
    {
      if ((0x4 & this.bitField0_) == 4);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean hasVersion()
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
        paramCodedOutputStream.writeEnum(1, this.app_.getNumber());
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeEnum(2, this.buildType_.getNumber());
      if ((0x4 & this.bitField0_) == 4)
        paramCodedOutputStream.writeEnum(3, this.platformType_.getNumber());
      if ((0x8 & this.bitField0_) == 8)
        paramCodedOutputStream.writeInt32(4, this.version_);
      if ((0x10 & this.bitField0_) == 16)
        paramCodedOutputStream.writeInt32(5, this.dataVersion_);
      if ((0x20 & this.bitField0_) == 32)
        paramCodedOutputStream.writeBytes(6, getDeviceOsBytes());
      if ((0x40 & this.bitField0_) == 64)
        paramCodedOutputStream.writeBytes(7, getDeviceHardwareBytes());
    }

    public static enum App
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<App> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        App[] arrayOfApp = new App[2];
        arrayOfApp[0] = GOOGLE_PLUS;
        arrayOfApp[1] = MESSAGING;
      }

      private App(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static App valueOf(int paramInt)
      {
        App localApp;
        switch (paramInt)
        {
        default:
          localApp = null;
        case 1:
        case 2:
        }
        while (true)
        {
          return localApp;
          localApp = GOOGLE_PLUS;
          continue;
          localApp = MESSAGING;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static enum BuildType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<BuildType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        BuildType[] arrayOfBuildType = new BuildType[3];
        arrayOfBuildType[0] = DEVELOPER;
        arrayOfBuildType[1] = DOGFOOD;
        arrayOfBuildType[2] = PUBLIC;
      }

      private BuildType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static BuildType valueOf(int paramInt)
      {
        BuildType localBuildType;
        switch (paramInt)
        {
        default:
          localBuildType = null;
        case 1:
        case 2:
        case 3:
        }
        while (true)
        {
          return localBuildType;
          localBuildType = DEVELOPER;
          continue;
          localBuildType = DOGFOOD;
          continue;
          localBuildType = PUBLIC;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Version.ClientVersion, Builder>
      implements Version.ClientVersionOrBuilder
    {
      private Version.ClientVersion.App app_ = Version.ClientVersion.App.GOOGLE_PLUS;
      private int bitField0_;
      private Version.ClientVersion.BuildType buildType_ = Version.ClientVersion.BuildType.DEVELOPER;
      private int dataVersion_;
      private Object deviceHardware_ = "";
      private Object deviceOs_ = "";
      private Version.ClientVersion.PlatformType platformType_ = Version.ClientVersion.PlatformType.WEB;
      private int version_;

      private Builder clear()
      {
        super.clear();
        this.app_ = Version.ClientVersion.App.GOOGLE_PLUS;
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.buildType_ = Version.ClientVersion.BuildType.DEVELOPER;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.platformType_ = Version.ClientVersion.PlatformType.WEB;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.version_ = 0;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.dataVersion_ = 0;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.deviceOs_ = "";
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.deviceHardware_ = "";
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
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
            Version.ClientVersion.App localApp = Version.ClientVersion.App.valueOf(paramCodedInputStream.readEnum());
            if (localApp == null)
              continue;
            this.bitField0_ = (0x1 | this.bitField0_);
            this.app_ = localApp;
            break;
          case 16:
            Version.ClientVersion.BuildType localBuildType = Version.ClientVersion.BuildType.valueOf(paramCodedInputStream.readEnum());
            if (localBuildType == null)
              continue;
            this.bitField0_ = (0x2 | this.bitField0_);
            this.buildType_ = localBuildType;
            break;
          case 24:
            Version.ClientVersion.PlatformType localPlatformType = Version.ClientVersion.PlatformType.valueOf(paramCodedInputStream.readEnum());
            if (localPlatformType == null)
              continue;
            this.bitField0_ = (0x4 | this.bitField0_);
            this.platformType_ = localPlatformType;
            break;
          case 32:
            this.bitField0_ = (0x8 | this.bitField0_);
            this.version_ = paramCodedInputStream.readInt32();
            break;
          case 40:
            this.bitField0_ = (0x10 | this.bitField0_);
            this.dataVersion_ = paramCodedInputStream.readInt32();
            break;
          case 50:
            this.bitField0_ = (0x20 | this.bitField0_);
            this.deviceOs_ = paramCodedInputStream.readBytes();
            break;
          case 58:
          }
          this.bitField0_ = (0x40 | this.bitField0_);
          this.deviceHardware_ = paramCodedInputStream.readBytes();
        }
      }

      public final Version.ClientVersion build()
      {
        Version.ClientVersion localClientVersion = buildPartial();
        if (!localClientVersion.isInitialized())
          throw new UninitializedMessageException();
        return localClientVersion;
      }

      public final Version.ClientVersion buildPartial()
      {
        Version.ClientVersion localClientVersion = new Version.ClientVersion(this, (byte)0);
        int i = this.bitField0_;
        int j = i & 0x1;
        int k = 0;
        if (j == 1)
          k = 1;
        Version.ClientVersion.access$302(localClientVersion, this.app_);
        if ((i & 0x2) == 2)
          k |= 2;
        Version.ClientVersion.access$402(localClientVersion, this.buildType_);
        if ((i & 0x4) == 4)
          k |= 4;
        Version.ClientVersion.access$502(localClientVersion, this.platformType_);
        if ((i & 0x8) == 8)
          k |= 8;
        Version.ClientVersion.access$602(localClientVersion, this.version_);
        if ((i & 0x10) == 16)
          k |= 16;
        Version.ClientVersion.access$702(localClientVersion, this.dataVersion_);
        if ((i & 0x20) == 32)
          k |= 32;
        Version.ClientVersion.access$802(localClientVersion, this.deviceOs_);
        if ((i & 0x40) == 64)
          k |= 64;
        Version.ClientVersion.access$902(localClientVersion, this.deviceHardware_);
        Version.ClientVersion.access$1002(localClientVersion, k);
        return localClientVersion;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Version.ClientVersion paramClientVersion)
      {
        if (paramClientVersion == Version.ClientVersion.getDefaultInstance());
        while (true)
        {
          return this;
          if (paramClientVersion.hasApp())
            setApp(paramClientVersion.getApp());
          if (paramClientVersion.hasBuildType())
            setBuildType(paramClientVersion.getBuildType());
          if (paramClientVersion.hasPlatformType())
            setPlatformType(paramClientVersion.getPlatformType());
          if (paramClientVersion.hasVersion())
            setVersion(paramClientVersion.getVersion());
          if (paramClientVersion.hasDataVersion())
            setDataVersion(paramClientVersion.getDataVersion());
          if (paramClientVersion.hasDeviceOs())
            setDeviceOs(paramClientVersion.getDeviceOs());
          if (paramClientVersion.hasDeviceHardware())
            setDeviceHardware(paramClientVersion.getDeviceHardware());
        }
      }

      public final Builder setApp(Version.ClientVersion.App paramApp)
      {
        if (paramApp == null)
          throw new NullPointerException();
        this.bitField0_ = (0x1 | this.bitField0_);
        this.app_ = paramApp;
        return this;
      }

      public final Builder setBuildType(Version.ClientVersion.BuildType paramBuildType)
      {
        if (paramBuildType == null)
          throw new NullPointerException();
        this.bitField0_ = (0x2 | this.bitField0_);
        this.buildType_ = paramBuildType;
        return this;
      }

      public final Builder setDataVersion(int paramInt)
      {
        this.bitField0_ = (0x10 | this.bitField0_);
        this.dataVersion_ = paramInt;
        return this;
      }

      public final Builder setDeviceHardware(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x40 | this.bitField0_);
        this.deviceHardware_ = paramString;
        return this;
      }

      public final Builder setDeviceOs(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        this.bitField0_ = (0x20 | this.bitField0_);
        this.deviceOs_ = paramString;
        return this;
      }

      public final Builder setPlatformType(Version.ClientVersion.PlatformType paramPlatformType)
      {
        if (paramPlatformType == null)
          throw new NullPointerException();
        this.bitField0_ = (0x4 | this.bitField0_);
        this.platformType_ = paramPlatformType;
        return this;
      }

      public final Builder setVersion(int paramInt)
      {
        this.bitField0_ = (0x8 | this.bitField0_);
        this.version_ = paramInt;
        return this;
      }
    }

    public static enum PlatformType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<PlatformType> internalValueMap = new Internal.EnumLiteMap()
      {
      };
      private final int value;

      static
      {
        IOS = new PlatformType("IOS", 1, 1);
        ANDROID = new PlatformType("ANDROID", 2, 2);
        PlatformType[] arrayOfPlatformType = new PlatformType[3];
        arrayOfPlatformType[0] = WEB;
        arrayOfPlatformType[1] = IOS;
        arrayOfPlatformType[2] = ANDROID;
      }

      private PlatformType(int paramInt1, int paramInt2)
      {
        this.value = paramInt1;
      }

      public static PlatformType valueOf(int paramInt)
      {
        PlatformType localPlatformType;
        switch (paramInt)
        {
        default:
          localPlatformType = null;
        case 0:
        case 1:
        case 2:
        }
        while (true)
        {
          return localPlatformType;
          localPlatformType = WEB;
          continue;
          localPlatformType = IOS;
          continue;
          localPlatformType = ANDROID;
        }
      }

      public final int getNumber()
      {
        return this.value;
      }
    }
  }

  public static abstract interface ClientVersionOrBuilder extends MessageLiteOrBuilder
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.webapps.Version
 * JD-Core Version:    0.6.2
 */