package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.network.AuthData;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;
import com.google.android.apps.plus.util.Property;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GCommNativeWrapper
{
  private static final int GCOMM_NATIVE_LIB_API_LEVEL = 6;
  public static final String HANGOUT = "HANGOUT";
  public static final String HANGOUT_SYNC = "HANGOUT_SYNC";
  public static final int INVALID_INCOMING_VIDEO_REQUEST_ID = 0;
  static final int MAX_INCOMING_AUDIO_LEVEL = 255;
  static final int MIN_INCOMING_AUDIO_LEVEL = 0;
  private static final String SELF_MUC_JID_BEFORE_ENTERING_HANGOUT = "";
  public static final String TRANSFER = "TRANSFER";
  private volatile EsAccount account;
  private volatile boolean clientInitiatedExit;
  private final Context context;
  private volatile boolean hadSomeConnectedParticipant;
  private volatile boolean hangoutCreated;
  private volatile Hangout.Info hangoutInfo;
  private volatile boolean isHangoutLite;
  private volatile Map<String, MeetingMember> memberMucJidToMeetingMember;
  private volatile int membersCount;
  private volatile long nativePeerObject;
  private volatile boolean retrySignin = true;
  private volatile boolean ringInvitees;
  private volatile List<RoomEntry> roomHistory;
  private volatile MeetingMember selfMeetingMember;
  private volatile String userJid;

  static
  {
    if (!GCommNativeWrapper.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      Log.info("GCommNativeWrapper loading gcomm_ini");
      System.loadLibrary("gcomm_jni");
      Log.info("GCommNativeWrapper done loading gcomm_ini");
      return;
    }
  }

  public GCommNativeWrapper(Context paramContext)
  {
    this.context = paramContext;
    this.roomHistory = new ArrayList();
    this.memberMucJidToMeetingMember = Collections.synchronizedMap(new HashMap());
  }

  private static <T> ArrayList<T> ToArrayList(T[] paramArrayOfT)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfT.length);
    int i = paramArrayOfT.length;
    for (int j = 0; j < i; j++)
      localArrayList.add(paramArrayOfT[j]);
    return localArrayList;
  }

  public static void initialize(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5)
    throws LinkageError
  {
    int i = nativeStaticGetVersion();
    Log.debug("GComm native lib API version:     " + i);
    Log.debug("GComm native wrapper API version: 6");
    Log.debug("GComm native lib logging: " + paramBoolean + " at level " + paramString5);
    if (i != 6)
    {
      Log.error("GComm native lib version mismatch.  Expected 6 but got " + i);
      throw new UnsupportedClassVersionError();
    }
    if (!nativeStaticInitialize(paramContext, paramString1, paramString2, paramString3, paramBoolean, paramString4, paramString5))
    {
      Log.error("GComm native lib initialization failed");
      throw new ExceptionInInitializerError();
    }
  }

  private native void nativeBlockMedia(String paramString);

  private native void nativeConnectAndSignin(String paramString1, String paramString2);

  private native void nativeConnectAndSigninFull(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean);

  private native void nativeCreateHangout();

  private native void nativeEnterMeeting(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2);

  private native void nativeEnterMeetingWithCachedGreenRoomInfo(boolean paramBoolean);

  private native void nativeExitMeeting();

  private native int nativeGetIncomingAudioVolume();

  private native boolean nativeInitializeIncomingVideoRenderer(int paramInt);

  private native void nativeInviteToMeeting(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, boolean paramBoolean1, boolean paramBoolean2);

  private native boolean nativeIsAudioMute();

  private native boolean nativeIsOutgoingVideoStarted();

  private native void nativeKickMeetingMember(String paramString1, String paramString2);

  private native long nativePeerCreate();

  private native void nativePeerDestroy(long paramLong);

  private native void nativeProvideOutgoingVideoFrame(byte[] paramArrayOfByte, long paramLong, int paramInt);

  private native void nativeRemoteMute(String paramString);

  private native boolean nativeRenderIncomingVideoFrame(int paramInt);

  private native void nativeRequestVCard(String paramString1, String paramString2);

  private native void nativeSendInstantMessage(String paramString);

  private native void nativeSendInstantMessageToUser(String paramString1, String paramString2);

  private native void nativeSendRingStatus(String paramString1, String paramString2, String paramString3);

  private native void nativeSetAudioMute(boolean paramBoolean);

  private native void nativeSetIncomingAudioVolume(int paramInt);

  private native void nativeSetIncomingVideoParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  private native boolean nativeSetIncomingVideoRendererSurfaceSize(int paramInt1, int paramInt2, int paramInt3);

  private native void nativeSetIncomingVideoSourceToSpeakerIndex(int paramInt1, int paramInt2);

  private native void nativeSetIncomingVideoSourceToUser(int paramInt, String paramString);

  private native void nativeSetPresenceConnectionStatus(int paramInt);

  private native void nativeSignoutAndDisconnect();

  public static native void nativeSimulateCrash();

  private native int nativeStartIncomingVideoForSpeakerIndex(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  private native int nativeStartIncomingVideoForUser(String paramString, int paramInt1, int paramInt2, int paramInt3);

  private native void nativeStartOutgoingVideo(int paramInt1, int paramInt2);

  public static native void nativeStaticCleanup();

  private static native int nativeStaticGetVersion();

  private static native boolean nativeStaticInitialize(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5);

  public static native void nativeStaticSetDeviceCaptureType(int paramInt);

  private native void nativeStopIncomingVideo(int paramInt);

  private native void nativeStopOutgoingVideo();

  private native void nativeUploadCallgrokLog();

  private void onAudioMuteStateChanged(String paramString, boolean paramBoolean)
  {
    Object localObject;
    if (paramString.equals(""))
    {
      localObject = null;
      GCommApp.sendObjectMessage(this.context, 101, Pair.create(localObject, Boolean.valueOf(paramBoolean)));
    }
    while (true)
    {
      return;
      localObject = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString);
      if (localObject != null)
        break;
    }
  }

  private void onCallgrokLogUploadCompleted(int paramInt, String paramString)
  {
    GCommApp.sendObjectMessage(this.context, 60, Pair.create(Integer.valueOf(paramInt), paramString));
  }

  private void onCurrentSpeakerChanged(String paramString)
  {
    GCommApp.sendObjectMessage(this.context, 102, this.memberMucJidToMeetingMember.get(paramString));
  }

  private void onError(int paramInt)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    Log.info("GCommNativeWrapper.onError: %d", arrayOfObject);
    Error localError = Error.values()[paramInt];
    if (localError == Error.AUTHENTICATION)
      Log.info("Invalidating auth token...");
    try
    {
      AuthData.invalidateAuthToken(this.context, this.account.getName(), "webupdates");
      label55: if (this.retrySignin)
      {
        GCommApp.getInstance(this.context).signinUser(getAccount());
        this.retrySignin = false;
      }
      while (true)
      {
        return;
        GCommApp.sendObjectMessage(this.context, -1, localError);
      }
    }
    catch (Exception localException)
    {
      break label55;
    }
  }

  private void onHangoutCreated(String paramString)
  {
    this.hangoutCreated = true;
    Hangout.Info localInfo = new Hangout.Info(Hangout.RoomType.CONSUMER, null, null, paramString, null, Hangout.LaunchSource.Creation, this.ringInvitees);
    GCommApp.sendObjectMessage(this.context, 50, localInfo);
  }

  private void onIncomingVideoFrameDimensionsChanged(int paramInt1, int paramInt2, int paramInt3)
  {
    GCommApp.sendObjectMessage(this.context, 107, new FrameDimensionsChangedMessageParams(paramInt1, new RectangleDimensions(paramInt2, paramInt3)));
  }

  private void onIncomingVideoFrameReceived(int paramInt)
  {
    GCommApp.sendObjectMessage(this.context, 106, Integer.valueOf(paramInt));
  }

  private void onIncomingVideoStarted(int paramInt)
  {
    GCommApp.sendObjectMessage(this.context, 104, Integer.valueOf(paramInt));
  }

  private void onInstantMessageReceived(String paramString1, String paramString2)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString1);
    if (localMeetingMember == null)
      Log.error("onInstantMessageReceived missing fromMucJid: " + paramString1);
    InstantMessage localInstantMessage = new InstantMessage(localMeetingMember, paramString1, paramString2);
    GCommApp.sendObjectMessage(this.context, 59, localInstantMessage);
  }

  private void onMediaBlock(String paramString1, String paramString2, boolean paramBoolean)
  {
    MeetingMember localMeetingMember1 = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString1);
    MeetingMember localMeetingMember2 = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString2);
    if (localMeetingMember1.isSelf())
      localMeetingMember2.setMediaBlocked(true);
    while (true)
    {
      if (!Property.ENABLE_HANGOUT_RECORD_ABUSE.getBoolean())
        paramBoolean = false;
      if ((paramBoolean) || ((localMeetingMember2 != null) && (localMeetingMember1 != null)))
        GCommApp.sendObjectMessage(this.context, 110, Pair.create(Pair.create(localMeetingMember1, localMeetingMember2), Boolean.valueOf(paramBoolean)));
      return;
      if (localMeetingMember2.isSelf())
        localMeetingMember1.setMediaBlocked(true);
    }
  }

  private void onMeetingEnterError(int paramInt)
  {
    MeetingEnterError localMeetingEnterError = MeetingEnterError.values()[paramInt];
    if ((localMeetingEnterError == MeetingEnterError.HANGOUT_OVER) && (this.hangoutInfo != null) && (this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.MissedCall))
    {
      this.hangoutInfo = null;
      GCommApp.sendEmptyMessage(this.context, 6);
    }
    while (true)
    {
      return;
      clearMeetingState();
      GCommApp.sendObjectMessage(this.context, -3, localMeetingEnterError);
    }
  }

  private void onMeetingExited()
  {
    boolean bool = this.clientInitiatedExit;
    clearMeetingState();
    Context localContext = this.context;
    if (bool);
    for (Object localObject = new Object(); ; localObject = null)
    {
      GCommApp.sendObjectMessage(localContext, 54, localObject);
      return;
    }
  }

  private void onMeetingMediaStarted()
  {
    GCommApp.sendEmptyMessage(this.context, 53);
  }

  private void onMeetingMemberEntered(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if ((!this.isHangoutLite) && ((paramString3 == null) || ("".equals(paramString3))))
    {
      Log.debug("Ignoring invalid user: JID=" + paramString1 + " nickname=" + paramString2 + " ID=<empty> status=" + paramInt);
      return;
    }
    boolean bool = this.account.isMyGaiaId(paramString3);
    int i = this.membersCount;
    this.membersCount = (i + 1);
    MeetingMember localMeetingMember = new MeetingMember(paramString1, paramString2, paramString3, i, false, bool);
    PresenceConnectionStatus localPresenceConnectionStatus = PresenceConnectionStatus.values()[paramInt];
    if ((localPresenceConnectionStatus == PresenceConnectionStatus.CONNECTING) || (localPresenceConnectionStatus == PresenceConnectionStatus.JOINING))
      localMeetingMember.setCurrentStatus(MeetingMember.Status.CONNECTING);
    while (true)
    {
      this.memberMucJidToMeetingMember.put(paramString1, localMeetingMember);
      if ((!this.hadSomeConnectedParticipant) && (localMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTED))
        this.hadSomeConnectedParticipant = true;
      nativeRequestVCard(paramString1, "");
      GCommApp.sendObjectMessage(this.context, 55, localMeetingMember);
      break;
      localMeetingMember.setCurrentStatus(MeetingMember.Status.CONNECTED);
    }
  }

  private void onMeetingMemberExited(String paramString)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.memberMucJidToMeetingMember.remove(paramString);
    if (localMeetingMember == null)
      Log.error("onMeetingMemberExited missing memberMucJid: " + paramString);
    while (true)
    {
      return;
      localMeetingMember.setCurrentStatus(MeetingMember.Status.DISCONNECTED);
      GCommApp.sendObjectMessage(this.context, 57, localMeetingMember);
    }
  }

  private void onMeetingMemberPresenceConnectionStateChanged(String paramString, int paramInt)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString);
    if (localMeetingMember == null)
      Log.error("onMeetingMemberPresenceConnectionStateChanged missing memberMucJid: " + paramString);
    label123: 
    while (true)
    {
      return;
      PresenceConnectionStatus localPresenceConnectionStatus = PresenceConnectionStatus.values()[paramInt];
      if ((localPresenceConnectionStatus == PresenceConnectionStatus.CONNECTING) || (localPresenceConnectionStatus == PresenceConnectionStatus.JOINING));
      for (MeetingMember.Status localStatus = MeetingMember.Status.CONNECTING; ; localStatus = MeetingMember.Status.CONNECTED)
      {
        if (localStatus == localMeetingMember.getCurrentStatus())
          break label123;
        localMeetingMember.setCurrentStatus(localStatus);
        if ((!this.hadSomeConnectedParticipant) && (localMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTED))
          this.hadSomeConnectedParticipant = true;
        GCommApp.sendObjectMessage(this.context, 56, localMeetingMember);
        break;
      }
    }
  }

  private void onMucEntered(String paramString1, String paramString2, boolean paramBoolean)
  {
    String str = this.account.getGaiaId();
    int i = this.membersCount;
    this.membersCount = (i + 1);
    this.selfMeetingMember = new MeetingMember(paramString1, paramString2, str, i, true, true);
    this.memberMucJidToMeetingMember.put(paramString1, this.selfMeetingMember);
    nativeRequestVCard(paramString1, "");
    this.isHangoutLite = paramBoolean;
    GCommApp.sendObjectMessage(this.context, 52, this.selfMeetingMember);
  }

  public static void onNativeCrash()
  {
    Log.error("GCommNativeWrapper.onNativeCrash - Crash from native code!!!");
    GCommApp.reportNativeCrash();
  }

  private void onOutgoingVideoStarted()
  {
    GCommApp.sendEmptyMessage(this.context, 105);
  }

  private void onReceivedRoomHistory(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    assert (paramArrayOfString1.length == paramArrayOfString2.length);
    ArrayList localArrayList = new ArrayList(paramArrayOfString1.length);
    String str1 = this.account.getName().split("@")[1];
    int i = 0;
    if (i < paramArrayOfString1.length)
    {
      String[] arrayOfString = paramArrayOfString1[i].split("@");
      if (arrayOfString.length != 2)
        Log.warn("Bad format for room history: " + paramArrayOfString1[i]);
      while (true)
      {
        i++;
        break;
        String str2 = arrayOfString[0];
        String str3 = arrayOfString[1];
        String str4 = str2;
        if (!str3.equals(str1))
          str4 = String.format("%s (%s)", new Object[] { str2, str3 });
        localArrayList.add(new RoomEntry(str4));
      }
    }
    this.roomHistory = localArrayList;
    GCommApp.sendObjectMessage(this.context, 5, localArrayList);
  }

  private void onRemoteMute(String paramString1, String paramString2)
  {
    MeetingMember localMeetingMember1 = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString1);
    MeetingMember localMeetingMember2 = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString2);
    if ((localMeetingMember2 != null) && (localMeetingMember1 != null))
      GCommApp.sendObjectMessage(this.context, 109, Pair.create(localMeetingMember1, localMeetingMember2));
  }

  private void onSignedIn(String paramString)
  {
    this.userJid = paramString;
    GCommApp.sendObjectMessage(this.context, 1, paramString);
  }

  private void onSignedOut()
  {
    GCommApp.sendEmptyMessage(this.context, 2);
  }

  private void onSigninTimeOutError()
  {
    GCommApp.sendEmptyMessage(this.context, -2);
  }

  public static void onUnhandledJavaException(Throwable paramThrowable)
  {
    GCommApp.reportJavaCrashFromNativeCode(paramThrowable);
  }

  private void onVCardResponse(String paramString, VCard paramVCard)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString);
    if (localMeetingMember == null)
      Log.warn("onVCardResponse missing memberMucJid: " + paramString);
    while (true)
    {
      return;
      localMeetingMember.setVCard(paramVCard);
      GCommApp.sendObjectMessage(this.context, 3, localMeetingMember);
    }
  }

  private void onVideoPauseStateChanged(String paramString, boolean paramBoolean)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString);
    if (localMeetingMember == null);
    while (true)
    {
      return;
      localMeetingMember.setVideoPaused(paramBoolean);
      GCommApp.sendObjectMessage(this.context, 111, Pair.create(localMeetingMember, Boolean.valueOf(paramBoolean)));
    }
  }

  private void onVideoSourceChanged(int paramInt, String paramString, boolean paramBoolean)
  {
    VideoSourceChangedMessageParams localVideoSourceChangedMessageParams = new VideoSourceChangedMessageParams(paramInt, (MeetingMember)this.memberMucJidToMeetingMember.get(paramString), paramBoolean);
    GCommApp.sendObjectMessage(this.context, 103, localVideoSourceChangedMessageParams);
  }

  private void onVolumeChanged(String paramString, int paramInt)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.memberMucJidToMeetingMember.get(paramString);
    if (localMeetingMember == null);
    while (true)
    {
      return;
      GCommApp.sendObjectMessage(this.context, 112, Pair.create(localMeetingMember, Integer.valueOf(paramInt)));
    }
  }

  public void blockMedia(MeetingMember paramMeetingMember)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeBlockMedia(paramMeetingMember.getMucJid());
    }
  }

  void clearMeetingState()
  {
    this.hangoutInfo = null;
    this.selfMeetingMember = null;
    this.memberMucJidToMeetingMember.clear();
    this.membersCount = 0;
    this.hadSomeConnectedParticipant = false;
    this.hangoutCreated = false;
    this.ringInvitees = false;
    this.clientInitiatedExit = false;
  }

  public void connectAndSignin(EsAccount paramEsAccount, String paramString)
  {
    if (this.nativePeerObject == 0L);
    for (int i = 1; i == 0; i = 0)
      throw new IllegalStateException();
    this.retrySignin = true;
    this.nativePeerObject = nativePeerCreate();
    this.account = paramEsAccount;
    Log.info("Created native peer: " + this.nativePeerObject);
    nativeConnectAndSignin(paramEsAccount.getName(), paramString);
  }

  public void createHangout(boolean paramBoolean)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      this.ringInvitees = paramBoolean;
      nativeCreateHangout();
    }
  }

  public void enterMeeting(Hangout.Info paramInfo, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.nativePeerObject == 0L)
      return;
    clearMeetingState();
    this.hangoutInfo = paramInfo;
    String str1;
    label30: int i;
    String str2;
    label50: String str3;
    if (paramInfo.getDomain() == null)
    {
      str1 = "";
      i = paramInfo.getRoomType().ordinal();
      if (paramInfo.getServiceId() != null)
        break label95;
      str2 = "";
      str3 = paramInfo.getId();
      if (paramInfo.getNick() != null)
        break label104;
    }
    label95: label104: for (String str4 = ""; ; str4 = paramInfo.getNick())
    {
      nativeEnterMeeting(i, str1, str2, str3, str4, paramBoolean1, paramBoolean2);
      break;
      str1 = paramInfo.getDomain();
      break label30;
      str2 = paramInfo.getServiceId();
      break label50;
    }
  }

  public void exitMeeting()
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      this.clientInitiatedExit = true;
      nativeExitMeeting();
    }
  }

  public EsAccount getAccount()
  {
    return this.account;
  }

  public GCommAppState getCurrentState()
  {
    if (this.nativePeerObject == 0L);
    for (GCommAppState localGCommAppState = GCommAppState.NONE; ; localGCommAppState = GCommAppState.values()[nativeGetCurrentState()])
      return localGCommAppState;
  }

  public boolean getHadSomeConnectedParticipantInPast()
  {
    return this.hadSomeConnectedParticipant;
  }

  public boolean getHangoutCreated()
  {
    return this.hangoutCreated;
  }

  public String getHangoutDomain()
  {
    return this.hangoutInfo.getDomain();
  }

  public String getHangoutId()
  {
    return this.hangoutInfo.getId();
  }

  public Hangout.Info getHangoutInfo()
  {
    return this.hangoutInfo;
  }

  public Hangout.RoomType getHangoutRoomType()
  {
    return this.hangoutInfo.getRoomType();
  }

  public boolean getHasSomeConnectedParticipant()
  {
    Iterator localIterator = this.memberMucJidToMeetingMember.values().iterator();
    do
      if (!localIterator.hasNext())
        break;
    while (((MeetingMember)localIterator.next()).getCurrentStatus() != MeetingMember.Status.CONNECTED);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public int getIncomingAudioVolume()
  {
    if (this.nativePeerObject == 0L);
    for (int i = 0; ; i = nativeGetIncomingAudioVolume())
      return i;
  }

  public boolean getIsHangoutLite()
  {
    return this.isHangoutLite;
  }

  public MeetingMember getMeetingMember(String paramString)
  {
    return (MeetingMember)this.memberMucJidToMeetingMember.get(paramString);
  }

  public int getMeetingMemberCount()
  {
    return this.memberMucJidToMeetingMember.size();
  }

  public List<MeetingMember> getMeetingMembersOrderedByEntry()
  {
    ArrayList localArrayList = new ArrayList(this.memberMucJidToMeetingMember.values());
    Collections.sort(localArrayList, new MeetingMember.SortByEntryOrder());
    return localArrayList;
  }

  public List<RoomEntry> getRoomHistory()
  {
    return this.roomHistory;
  }

  public MeetingMember getSelfMeetingMember()
  {
    return this.selfMeetingMember;
  }

  public String getUserJid()
  {
    return this.userJid;
  }

  public boolean initializeIncomingVideoRenderer(int paramInt)
  {
    if (this.nativePeerObject == 0L);
    for (boolean bool = false; ; bool = nativeInitializeIncomingVideoRenderer(paramInt))
      return bool;
  }

  void inviteToMeeting(AudienceData paramAudienceData, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      AudienceData localAudienceData = ApiUtils.removeCircleIdNamespaces(paramAudienceData);
      HashSet localHashSet = new HashSet();
      Iterator localIterator = this.memberMucJidToMeetingMember.values().iterator();
      while (localIterator.hasNext())
      {
        String str2 = EsPeopleData.extractGaiaId(((MeetingMember)localIterator.next()).getId());
        if (str2 != null)
          localHashSet.add(str2);
      }
      ArrayList localArrayList1 = new ArrayList(localAudienceData.getUserCount());
      PersonData[] arrayOfPersonData = localAudienceData.getUsers();
      int i = arrayOfPersonData.length;
      int j = 0;
      if (j < i)
      {
        PersonData localPersonData = arrayOfPersonData[j];
        String str1 = localPersonData.getObfuscatedId();
        if (TextUtils.isEmpty(str1))
          Log.error("Person object with no id: " + localPersonData);
        while (true)
        {
          j++;
          break;
          if (localHashSet.contains(str1))
            Log.debug("Skip adding: " + str1);
          else
            localArrayList1.add(str1);
        }
      }
      ArrayList localArrayList2 = new ArrayList(localAudienceData.getCircleCount());
      CircleData[] arrayOfCircleData = localAudienceData.getCircles();
      int k = arrayOfCircleData.length;
      for (int m = 0; m < k; m++)
        localArrayList2.add(arrayOfCircleData[m].getId());
      if ((localArrayList1.size() == 0) && (localArrayList2.size() == 0) && (paramString != "TRANSFER"))
      {
        Log.debug("Skipping invite since no one to invite");
      }
      else
      {
        String[] arrayOfString1 = new String[localArrayList1.size()];
        localArrayList1.toArray(arrayOfString1);
        String[] arrayOfString2 = new String[localArrayList2.size()];
        localArrayList2.toArray(arrayOfString2);
        nativeInviteToMeeting(arrayOfString1, arrayOfString2, paramString, paramBoolean1, paramBoolean2);
      }
    }
  }

  public boolean isAudioMute()
  {
    if (this.nativePeerObject == 0L);
    for (boolean bool = false; ; bool = nativeIsAudioMute())
      return bool;
  }

  public boolean isInHangout(Hangout.Info paramInfo)
  {
    if (paramInfo == null);
    for (boolean bool = false; ; bool = paramInfo.equals(this.hangoutInfo))
      return bool;
  }

  public boolean isOutgoingVideoStarted()
  {
    if (this.nativePeerObject == 0L);
    for (boolean bool = false; ; bool = nativeIsOutgoingVideoStarted())
      return bool;
  }

  public void kickMeetingMember(String paramString1, String paramString2)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeKickMeetingMember(paramString1, paramString2);
    }
  }

  public native int nativeGetCurrentState();

  public void provideOutgoingVideoFrame(byte[] paramArrayOfByte, long paramLong, int paramInt)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      if (getCurrentState() == GCommAppState.IN_MEETING_WITH_MEDIA)
        nativeProvideOutgoingVideoFrame(paramArrayOfByte, paramLong, paramInt);
    }
  }

  public void remoteMute(MeetingMember paramMeetingMember)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeRemoteMute(paramMeetingMember.getMucJid());
    }
  }

  public boolean renderIncomingVideoFrame(int paramInt)
  {
    if (this.nativePeerObject == 0L);
    for (boolean bool = false; ; bool = nativeRenderIncomingVideoFrame(paramInt))
      return bool;
  }

  public void sendInstantMessage(String paramString)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSendInstantMessage(paramString);
    }
  }

  public void sendInstantMessageToUser(String paramString1, String paramString2)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSendInstantMessageToUser(paramString1, paramString2);
    }
  }

  public void sendRingStatus(String paramString1, String paramString2, String paramString3)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSendRingStatus(paramString1, paramString2, paramString3);
    }
  }

  public void setAudioMute(boolean paramBoolean)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSetAudioMute(paramBoolean);
    }
  }

  public void setIncomingAudioVolume(int paramInt)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      if ((paramInt < 0) || (paramInt > 255))
        throw new IllegalArgumentException("level is " + paramInt);
      nativeSetIncomingAudioVolume(paramInt);
    }
  }

  public void setIncomingVideoParameters(int paramInt1, int paramInt2, int paramInt3, ScalingMode paramScalingMode, int paramInt4)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSetIncomingVideoParameters(paramInt1, paramInt2, paramInt3, paramScalingMode.ordinal(), paramInt4);
    }
  }

  public boolean setIncomingVideoRendererSurfaceSize(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.nativePeerObject == 0L);
    for (boolean bool = false; ; bool = nativeSetIncomingVideoRendererSurfaceSize(paramInt1, paramInt2, paramInt3))
      return bool;
  }

  public void setIncomingVideoSourceToSpeakerIndex(int paramInt1, int paramInt2)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSetIncomingVideoSourceToSpeakerIndex(paramInt1, paramInt2);
    }
  }

  public void setIncomingVideoSourceToUser(int paramInt, String paramString)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSetIncomingVideoSourceToUser(paramInt, paramString);
    }
  }

  public void setPresenceConnectionStatus(PresenceConnectionStatus paramPresenceConnectionStatus)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSetPresenceConnectionStatus(paramPresenceConnectionStatus.ordinal());
    }
  }

  public void signoutAndDisconnect()
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeSignoutAndDisconnect();
      if (this.nativePeerObject != 0L)
      {
        nativePeerDestroy(this.nativePeerObject);
        this.nativePeerObject = 0L;
      }
    }
  }

  public int startIncomingVideoForSpeakerIndex(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.nativePeerObject == 0L);
    for (int i = 0; ; i = nativeStartIncomingVideoForSpeakerIndex(paramInt1, paramInt2, paramInt3, paramInt4))
      return i;
  }

  public int startIncomingVideoForUser(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.nativePeerObject == 0L);
    for (int i = 0; ; i = nativeStartIncomingVideoForUser(paramString, paramInt1, paramInt2, paramInt3))
      return i;
  }

  public void startOutgoingVideo(int paramInt1, int paramInt2)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeStartOutgoingVideo(paramInt1, paramInt2);
    }
  }

  public void stopIncomingVideo(int paramInt)
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeStopIncomingVideo(paramInt);
    }
  }

  public void stopOutgoingVideo()
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeStopOutgoingVideo();
    }
  }

  public void uploadCallgrokLog()
  {
    if (this.nativePeerObject == 0L);
    while (true)
    {
      return;
      nativeUploadCallgrokLog();
    }
  }

  public static enum DeviceCaptureType
  {
    static
    {
      DeviceCaptureType[] arrayOfDeviceCaptureType = new DeviceCaptureType[2];
      arrayOfDeviceCaptureType[0] = LOW_RESOLUTION;
      arrayOfDeviceCaptureType[1] = MEDIUM_RESOLUTION;
    }
  }

  public static enum Error
  {
    static
    {
      AUTHENTICATION = new Error("AUTHENTICATION", 3);
      AUDIO_VIDEO_SESSION = new Error("AUDIO_VIDEO_SESSION", 4);
      UNKNOWN = new Error("UNKNOWN", 5);
      Error[] arrayOfError = new Error[6];
      arrayOfError[0] = FATAL;
      arrayOfError[1] = INCONSISTENT_STATE;
      arrayOfError[2] = NETWORK;
      arrayOfError[3] = AUTHENTICATION;
      arrayOfError[4] = AUDIO_VIDEO_SESSION;
      arrayOfError[5] = UNKNOWN;
    }
  }

  public static class FrameDimensionsChangedMessageParams
  {
    private final RectangleDimensions dimensions;
    private final int requestID;

    public FrameDimensionsChangedMessageParams(int paramInt, RectangleDimensions paramRectangleDimensions)
    {
      this.requestID = paramInt;
      this.dimensions = paramRectangleDimensions;
    }

    public final RectangleDimensions getDimensions()
    {
      return this.dimensions;
    }

    public final int getRequestID()
    {
      return this.requestID;
    }
  }

  public static enum GCommAppState
  {
    static
    {
      SIGNING_IN = new GCommAppState("SIGNING_IN", 2);
      SIGNED_IN = new GCommAppState("SIGNED_IN", 3);
      ENTERING_MEETING = new GCommAppState("ENTERING_MEETING", 4);
      IN_MEETING_WITHOUT_MEDIA = new GCommAppState("IN_MEETING_WITHOUT_MEDIA", 5);
      IN_MEETING_WITH_MEDIA = new GCommAppState("IN_MEETING_WITH_MEDIA", 6);
      GCommAppState[] arrayOfGCommAppState = new GCommAppState[7];
      arrayOfGCommAppState[0] = NONE;
      arrayOfGCommAppState[1] = START;
      arrayOfGCommAppState[2] = SIGNING_IN;
      arrayOfGCommAppState[3] = SIGNED_IN;
      arrayOfGCommAppState[4] = ENTERING_MEETING;
      arrayOfGCommAppState[5] = IN_MEETING_WITHOUT_MEDIA;
      arrayOfGCommAppState[6] = IN_MEETING_WITH_MEDIA;
    }
  }

  public static enum MeetingEnterError
  {
    static
    {
      TIMEOUT = new MeetingEnterError("TIMEOUT", 1);
      BLOCKED_BY_SOMEONE_IN_HANGOUT = new MeetingEnterError("BLOCKED_BY_SOMEONE_IN_HANGOUT", 2);
      BLOCKING_SOMEONE_IN_HANGOUT = new MeetingEnterError("BLOCKING_SOMEONE_IN_HANGOUT", 3);
      MAX_USERS = new MeetingEnterError("MAX_USERS", 4);
      SERVER = new MeetingEnterError("SERVER", 5);
      MEDIA_START_TIMEOUT = new MeetingEnterError("MEDIA_START_TIMEOUT", 6);
      AUDIO_VIDEO_SESSION = new MeetingEnterError("AUDIO_VIDEO_SESSION", 7);
      GREEN_ROOM_INFO = new MeetingEnterError("GREEN_ROOM_INFO", 8);
      OUTDATED_CLIENT = new MeetingEnterError("OUTDATED_CLIENT", 9);
      HANGOUT_OVER = new MeetingEnterError("HANGOUT_OVER", 10);
      HANGOUT_ON_AIR = new MeetingEnterError("HANGOUT_ON_AIR", 11);
      MeetingEnterError[] arrayOfMeetingEnterError = new MeetingEnterError[12];
      arrayOfMeetingEnterError[0] = UNKNOWN;
      arrayOfMeetingEnterError[1] = TIMEOUT;
      arrayOfMeetingEnterError[2] = BLOCKED_BY_SOMEONE_IN_HANGOUT;
      arrayOfMeetingEnterError[3] = BLOCKING_SOMEONE_IN_HANGOUT;
      arrayOfMeetingEnterError[4] = MAX_USERS;
      arrayOfMeetingEnterError[5] = SERVER;
      arrayOfMeetingEnterError[6] = MEDIA_START_TIMEOUT;
      arrayOfMeetingEnterError[7] = AUDIO_VIDEO_SESSION;
      arrayOfMeetingEnterError[8] = GREEN_ROOM_INFO;
      arrayOfMeetingEnterError[9] = OUTDATED_CLIENT;
      arrayOfMeetingEnterError[10] = HANGOUT_OVER;
      arrayOfMeetingEnterError[11] = HANGOUT_ON_AIR;
    }
  }

  private static enum PresenceConnectionStatus
  {
    static
    {
      CONNECTING = new PresenceConnectionStatus("CONNECTING", 1);
      JOINING = new PresenceConnectionStatus("JOINING", 2);
      CONNECTED = new PresenceConnectionStatus("CONNECTED", 3);
      PresenceConnectionStatus[] arrayOfPresenceConnectionStatus = new PresenceConnectionStatus[4];
      arrayOfPresenceConnectionStatus[0] = UNKNOWN;
      arrayOfPresenceConnectionStatus[1] = CONNECTING;
      arrayOfPresenceConnectionStatus[2] = JOINING;
      arrayOfPresenceConnectionStatus[3] = CONNECTED;
    }
  }

  static class RoomEntry
  {
    private final String displayRoomName;
    private final Date lastEnterTime;

    RoomEntry(String paramString)
    {
      this.displayRoomName = paramString;
      this.lastEnterTime = new Date();
    }

    public String toString()
    {
      return this.displayRoomName;
    }
  }

  public static enum ScalingMode
  {
    static
    {
      ZOOM_IN_TO_FILL = new ScalingMode("ZOOM_IN_TO_FILL", 1);
      AUTO_ZOOM = new ScalingMode("AUTO_ZOOM", 2);
      ScalingMode[] arrayOfScalingMode = new ScalingMode[3];
      arrayOfScalingMode[0] = ZOOM_OUT_TO_FIT;
      arrayOfScalingMode[1] = ZOOM_IN_TO_FILL;
      arrayOfScalingMode[2] = AUTO_ZOOM;
    }
  }

  public static class VideoSourceChangedMessageParams
  {
    private final int requestID;
    private final MeetingMember source;
    private final boolean videoAvailable;

    public VideoSourceChangedMessageParams(int paramInt, MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      this.requestID = paramInt;
      this.source = paramMeetingMember;
      this.videoAvailable = paramBoolean;
    }

    public final int getRequestID()
    {
      return this.requestID;
    }

    public final MeetingMember getSource()
    {
      return this.source;
    }

    public final boolean isVideoAvailable()
    {
      return this.videoAvailable;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.GCommNativeWrapper
 * JD-Core Version:    0.6.2
 */