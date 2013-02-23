package com.google.android.apps.plus.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.TextureView;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbEmbedHangout;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.hangout.GCommApp;
import com.google.android.apps.plus.hangout.GCommService;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.Property;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Hangout
{
  public static final String CONSUMER_HANGOUT_DOMAIN;
  private static final Pattern HANGOUT_URL_PATTERN;
  private static EsAccount sAccountForCachedStatus;
  private static boolean sCachedIsCreationSupported;
  private static SupportStatus sCachedStatus;
  private static boolean sHangoutCreationSupportCacheIsDirty;
  private static boolean sHangoutSupportStatusCacheIsDirty;

  static
  {
    if (!Hangout.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      CONSUMER_HANGOUT_DOMAIN = null;
      HANGOUT_URL_PATTERN = Pattern.compile("http s? ://plus.google.com/hangouts/(    \\p{Alnum}+)", 6);
      sHangoutCreationSupportCacheIsDirty = true;
      sHangoutSupportStatusCacheIsDirty = true;
      return;
    }
  }

  public static void enterGreenRoom(EsAccount paramEsAccount, Context paramContext, String paramString1, String paramString2, DbEmbedHangout paramDbEmbedHangout)
  {
    assert (paramDbEmbedHangout.isInProgress());
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(Data.Participant.newBuilder().setParticipantId("g:" + paramString1).setFullName(paramString2).setFirstName(getFirstNameFromFullName(paramString2)).build());
    ArrayList localArrayList2 = paramDbEmbedHangout.getAttendeeGaiaIds();
    ArrayList localArrayList3 = paramDbEmbedHangout.getAttendeeNames();
    int i = 0;
    int j = paramDbEmbedHangout.getNumAttendees();
    while (i < j)
    {
      String str = (String)localArrayList3.get(i);
      localArrayList1.add(Data.Participant.newBuilder().setParticipantId("g:" + (String)localArrayList2.get(i)).setFullName(str).setFirstName(getFirstNameFromFullName(str)).build());
      i++;
    }
    Object localObject = Intents.getHangoutActivityIntent(paramContext, paramEsAccount, RoomType.CONSUMER, null, null, paramDbEmbedHangout.getHangoutId(), null, LaunchSource.Stream, false, false, localArrayList1);
    GCommApp localGCommApp = GCommApp.getInstance(paramContext);
    if (localGCommApp.isInAHangout())
    {
      Intent localIntent = localGCommApp.getGCommService().getNotificationIntent();
      if (localIntent != null)
      {
        Info localInfo = (Info)localIntent.getSerializableExtra("hangout_info");
        if ((localInfo != null) && (localInfo.id.equals(paramDbEmbedHangout.getHangoutId())))
          localObject = localIntent;
      }
    }
    paramContext.startActivity((Intent)localObject);
  }

  public static String getFirstNameFromFullName(String paramString)
  {
    int i = paramString.indexOf(' ');
    if (i == -1);
    while (true)
    {
      return paramString;
      paramString = paramString.substring(0, i);
    }
  }

  public static SupportStatus getSupportedStatus(Context paramContext, EsAccount paramEsAccount)
  {
    updateCacheDirtyFlags(paramEsAccount);
    SupportStatus localSupportStatus;
    if (sHangoutSupportStatusCacheIsDirty)
    {
      if (Build.VERSION.SDK_INT >= 8)
        break label36;
      localSupportStatus = SupportStatus.OS_NOT_SUPPORTED;
    }
    while (true)
    {
      sCachedStatus = localSupportStatus;
      sHangoutSupportStatusCacheIsDirty = false;
      return sCachedStatus;
      label36: FeatureInfo[] arrayOfFeatureInfo = paramContext.getPackageManager().getSystemAvailableFeatures();
      int i;
      if ((arrayOfFeatureInfo != null) && (arrayOfFeatureInfo.length > 0))
      {
        int j = arrayOfFeatureInfo.length;
        int k = 0;
        i = 0;
        if (k < j)
        {
          FeatureInfo localFeatureInfo = arrayOfFeatureInfo[k];
          if (localFeatureInfo.name == null)
            if ((short)(localFeatureInfo.reqGlEsVersion >> 16) < 2)
              break label104;
          label104: for (i = 1; ; i = 0)
          {
            k++;
            break;
          }
        }
      }
      else
      {
        i = 0;
      }
      if (i == 0)
        localSupportStatus = SupportStatus.DEVICE_NOT_SUPPORTED;
      else if ((!Build.CPU_ABI.equals("armeabi-v7a")) && (!Build.CPU_ABI2.equals("armeabi-v7a")))
        localSupportStatus = SupportStatus.DEVICE_NOT_SUPPORTED;
      else if ((paramEsAccount == null) || (paramEsAccount.getName() == null) || (paramEsAccount.isPlusPage()))
        localSupportStatus = SupportStatus.ACCOUNT_NOT_CONFIGURED;
      else
        localSupportStatus = SupportStatus.SUPPORTED;
    }
  }

  public static boolean isAdvancedUiSupported(Context paramContext)
  {
    boolean bool1 = true;
    boolean bool2;
    if ((Build.VERSION.SDK_INT >= 14) && (Property.ENABLE_ADVANCED_HANGOUTS.getBoolean()) && (new TextureView(paramContext).getLayerType() == 2))
      if ((Build.MANUFACTURER.equals("samsung")) && (!Build.BRAND.equals("google")) && (Build.VERSION.SDK_INT <= 15))
      {
        bool2 = bool1;
        if (bool2)
          break label79;
      }
    while (true)
    {
      return bool1;
      bool2 = false;
      break;
      label79: bool1 = false;
    }
  }

  public static boolean isHangoutCreationSupported(Context paramContext, EsAccount paramEsAccount)
  {
    boolean bool = true;
    updateCacheDirtyFlags(paramEsAccount);
    int j;
    FeatureInfo localFeatureInfo;
    if (sHangoutCreationSupportCacheIsDirty)
    {
      if (getSupportedStatus(paramContext, paramEsAccount) != SupportStatus.SUPPORTED)
        break label101;
      FeatureInfo[] arrayOfFeatureInfo = paramContext.getPackageManager().getSystemAvailableFeatures();
      if (arrayOfFeatureInfo == null)
        break label101;
      int i = arrayOfFeatureInfo.length;
      j = 0;
      if (j >= i)
        break label101;
      localFeatureInfo = arrayOfFeatureInfo[j];
      if (!"android.hardware.camera.front".equals(localFeatureInfo.name))
        break label81;
    }
    while (true)
    {
      sCachedIsCreationSupported = bool;
      sHangoutCreationSupportCacheIsDirty = false;
      return sCachedIsCreationSupported;
      label81: if (!"android.hardware.camera".equals(localFeatureInfo.name))
      {
        j++;
        break;
        label101: bool = false;
      }
    }
  }

  private static void updateCacheDirtyFlags(EsAccount paramEsAccount)
  {
    if ((sAccountForCachedStatus == null) || (!sAccountForCachedStatus.equals(paramEsAccount)))
    {
      sHangoutCreationSupportCacheIsDirty = true;
      sHangoutSupportStatusCacheIsDirty = true;
      sAccountForCachedStatus = paramEsAccount;
    }
  }

  public static abstract interface ApplicationEventListener
  {
  }

  public static class Info
    implements Serializable
  {
    private final String domain;
    private final String id;
    private final Hangout.LaunchSource launchSource;
    private final String nick;
    private boolean ringInvitees = false;
    private final Hangout.RoomType roomType;
    private final String serviceId;

    public Info(Hangout.RoomType paramRoomType, String paramString1, String paramString2, String paramString3, String paramString4, Hangout.LaunchSource paramLaunchSource, boolean paramBoolean)
    {
      this.roomType = paramRoomType;
      this.domain = paramString1;
      this.serviceId = paramString2;
      this.id = paramString3.toLowerCase();
      this.nick = paramString4;
      this.launchSource = paramLaunchSource;
      this.ringInvitees = paramBoolean;
    }

    public boolean equals(Object paramObject)
    {
      boolean bool1 = true;
      if (this == paramObject);
      label137: label141: 
      while (true)
      {
        return bool1;
        if (!(paramObject instanceof Info))
        {
          bool1 = false;
        }
        else
        {
          Info localInfo = (Info)paramObject;
          boolean bool2;
          if ((this.domain == null) || (this.domain.equals("")))
          {
            bool2 = bool1;
            label48: if ((localInfo.domain != null) && (!localInfo.domain.equals("")))
              break label137;
          }
          for (boolean bool3 = bool1; ; bool3 = false)
          {
            if ((this.roomType == localInfo.roomType) && (((!bool2) || (!bool3)) && ((this.domain != null) && (this.domain.equals(localInfo.domain)) && (this.id.equals(localInfo.id)))))
              break label141;
            bool1 = false;
            break;
            bool2 = false;
            break label48;
          }
        }
      }
    }

    public final String getDomain()
    {
      return this.domain;
    }

    public final String getId()
    {
      return this.id;
    }

    public final Hangout.LaunchSource getLaunchSource()
    {
      return this.launchSource;
    }

    public final String getNick()
    {
      return this.nick;
    }

    public final boolean getRingInvitees()
    {
      return this.ringInvitees;
    }

    public final Hangout.RoomType getRoomType()
    {
      return this.roomType;
    }

    public final String getServiceId()
    {
      return this.serviceId;
    }

    public int hashCode()
    {
      int i = this.roomType.hashCode() ^ this.id.hashCode();
      if ((this.domain != null) && (!this.domain.equals("")))
        i ^= this.domain.hashCode();
      return i;
    }

    public String toString()
    {
      Object[] arrayOfObject2;
      if (this.serviceId == null)
      {
        arrayOfObject2 = new Object[6];
        arrayOfObject2[0] = this.id;
        arrayOfObject2[1] = this.domain;
        arrayOfObject2[2] = this.roomType;
        arrayOfObject2[3] = this.nick;
        arrayOfObject2[4] = this.launchSource;
        arrayOfObject2[5] = Boolean.valueOf(this.ringInvitees);
      }
      Object[] arrayOfObject1;
      for (String str = String.format("%s@%s %s (%s, %s, %s)", arrayOfObject2); ; str = String.format("%s:%s@%s %s (%s, %s, %s)", arrayOfObject1))
      {
        return str;
        arrayOfObject1 = new Object[7];
        arrayOfObject1[0] = this.serviceId;
        arrayOfObject1[1] = this.id;
        arrayOfObject1[2] = this.domain;
        arrayOfObject1[3] = this.roomType;
        arrayOfObject1[4] = this.nick;
        arrayOfObject1[5] = this.launchSource;
        arrayOfObject1[6] = Boolean.valueOf(this.ringInvitees);
      }
    }
  }

  public static enum LaunchSource
  {
    static
    {
      MissedCall = new LaunchSource("MissedCall", 3);
      Ring = new LaunchSource("Ring", 4);
      Ding = new LaunchSource("Ding", 5);
      Creation = new LaunchSource("Creation", 6);
      Messenger = new LaunchSource("Messenger", 7);
      Transfer = new LaunchSource("Transfer", 8);
      Event = new LaunchSource("Event", 9);
      LaunchSource[] arrayOfLaunchSource = new LaunchSource[10];
      arrayOfLaunchSource[0] = None;
      arrayOfLaunchSource[1] = Stream;
      arrayOfLaunchSource[2] = Url;
      arrayOfLaunchSource[3] = MissedCall;
      arrayOfLaunchSource[4] = Ring;
      arrayOfLaunchSource[5] = Ding;
      arrayOfLaunchSource[6] = Creation;
      arrayOfLaunchSource[7] = Messenger;
      arrayOfLaunchSource[8] = Transfer;
      arrayOfLaunchSource[9] = Event;
    }
  }

  public static enum RoomType
  {
    static
    {
      EXTERNAL = new RoomType("EXTERNAL", 2);
      UNKNOWN = new RoomType("UNKNOWN", 3);
      RoomType[] arrayOfRoomType = new RoomType[4];
      arrayOfRoomType[0] = CONSUMER;
      arrayOfRoomType[1] = WITH_EXTRAS;
      arrayOfRoomType[2] = EXTERNAL;
      arrayOfRoomType[3] = UNKNOWN;
    }
  }

  public static enum SupportStatus
  {
    static
    {
      DEVICE_NOT_SUPPORTED = new SupportStatus("DEVICE_NOT_SUPPORTED", 1);
      CHILD_NOT_SUPPORTED = new SupportStatus("CHILD_NOT_SUPPORTED", 2);
      ACCOUNT_NOT_CONFIGURED = new SupportStatus("ACCOUNT_NOT_CONFIGURED", 3);
      TYPE_NOT_SUPPORTED = new SupportStatus("TYPE_NOT_SUPPORTED", 4);
      SUPPORTED = new SupportStatus("SUPPORTED", 5);
      SupportStatus[] arrayOfSupportStatus = new SupportStatus[6];
      arrayOfSupportStatus[0] = OS_NOT_SUPPORTED;
      arrayOfSupportStatus[1] = DEVICE_NOT_SUPPORTED;
      arrayOfSupportStatus[2] = CHILD_NOT_SUPPORTED;
      arrayOfSupportStatus[3] = ACCOUNT_NOT_CONFIGURED;
      arrayOfSupportStatus[4] = TYPE_NOT_SUPPORTED;
      arrayOfSupportStatus[5] = SUPPORTED;
    }

    public final String getErrorMessage(Context paramContext)
    {
      String str;
      switch (Hangout.1.$SwitchMap$com$google$android$apps$plus$service$Hangout$SupportStatus[ordinal()])
      {
      case 2:
      default:
        str = paramContext.getResources().getString(R.string.hangout_not_supported_device);
      case 1:
      case 3:
      case 4:
      case 5:
      }
      while (true)
      {
        return str;
        str = paramContext.getResources().getString(R.string.hangout_not_supported_os);
        continue;
        str = paramContext.getResources().getString(R.string.hangout_not_supported_child);
        continue;
        str = paramContext.getResources().getString(R.string.hangout_not_supported_account);
        continue;
        str = paramContext.getResources().getString(R.string.hangout_not_supported_type);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.Hangout
 * JD-Core Version:    0.6.2
 */