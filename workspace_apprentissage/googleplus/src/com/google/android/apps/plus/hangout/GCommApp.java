package com.google.android.apps.plus.hangout;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Pair;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.hangout.crash.CrashReport;
import com.google.android.apps.plus.hangout.crash.CrashTriggerActivity;
import com.google.android.apps.plus.network.AuthData;
import com.google.android.apps.plus.network.ClientVersion;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.service.Hangout.ApplicationEventListener;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.util.Property;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class GCommApp
  implements AudioManager.OnAudioFocusChangeListener, Hangout.ApplicationEventListener
{
  private static boolean crashReported;
  private static GCommApp gcommApp;
  private EsAccount account;
  private final GCommEventHandler appEventHandler = new AppEventHandler((byte)0);
  private boolean audioFocus;
  private AudioManager audioManager;
  private final ConnectivityChangeListener connectivityChangeListener;
  private ConnectivityManager connectivityManager;
  private PowerManager.WakeLock cpuWakeLock;
  private int currentNetworkSubtype = -1;
  private int currentNetworkType = -1;
  private EsFragmentActivity currentStartedHangoutActivity;
  private ArrayList<GCommEventHandler> eventHandlers = new ArrayList();
  private boolean exitMeetingCleanupDone;
  private GCommNativeWrapper gcommNativeWrapper;
  private volatile GCommService gcommService;
  private final ServiceConnection gcommServiceConnection = new ServiceConnection()
  {
    public final void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Log.debug("onServiceConnected called. Service: " + paramAnonymousIBinder.getClass().getName());
      if ((paramAnonymousIBinder instanceof GCommService.LocalBinder))
        GCommApp.access$2102(GCommApp.this, ((GCommService.LocalBinder)paramAnonymousIBinder).getService());
    }

    public final void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      Log.debug("onServiceDisconnected called");
      GCommApp.access$2102(GCommApp.this, null);
    }
  };
  private HashSet<String> greenRoomParticipantIds;
  private Handler handler = new Handler(Looper.getMainLooper());
  private Hangout.Info hangoutInfo;
  private int hangoutSigninRefCount;
  private long hangoutStartTime = -1L;
  private HeadsetBroadcastReceiver headsetBroadcastReceiver;
  private int incomingAudioLevelBeforeAudioFocusLoss;
  private boolean isBound;
  private boolean isExitingHangout;
  private Cameras.CameraType lastUsedCameraType;
  private int mSavedAudioMode = -2;
  private boolean muteMicOnAudioFocusGain;
  private boolean outgoingVideoMute;
  private final EsApplication plusOneApplication;
  private PowerManager.WakeLock screenWakeLock;
  BroadcastReceiver screenoffBroadcastListener = new BroadcastReceiver()
  {
    public final void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (!paramAnonymousIntent.getAction().equals("android.intent.action.SCREEN_OFF"));
      while (true)
      {
        return;
        if (GCommApp.this.isInAHangout())
        {
          GCommApp.this.exitMeetingAndDisconnect();
          GCommApp.sendEmptyMessage(paramAnonymousContext, 54);
        }
      }
    }
  };
  private MeetingMember selectedVideoSource;
  private GCommEventHandler serviceEventHandler;
  private SigninTask signinTask;
  private WifiManager.WifiLock wifiLock;

  static
  {
    if (!GCommApp.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private GCommApp(EsApplication paramEsApplication)
  {
    Log.debug("Constructing GCommApp");
    this.plusOneApplication = paramEsApplication;
    resetSelfMediaState();
    this.connectivityChangeListener = new ConnectivityChangeListener((byte)0);
    Utils.initialize(paramEsApplication);
    String str1 = paramEsApplication.getFilesDir().getAbsolutePath();
    boolean bool = Property.NATIVE_HANGOUT_LOG.getBoolean();
    String str2 = Property.NATIVE_WRAPPER_HANGOUT_LOG_LEVEL.get();
    GCommNativeWrapper.initialize(paramEsApplication, str1, "Google_Plus_Android", Utils.getVersion(), bool, ClientVersion.from(paramEsApplication), str2);
    new AsyncTask()
    {
    }
    .execute(new Void[0]);
    if (!this.isBound)
    {
      Intent localIntent = new Intent(this.plusOneApplication, GCommService.class);
      this.isBound = this.plusOneApplication.bindService(localIntent, this.gcommServiceConnection, 1);
    }
    if (!this.isBound)
      Log.error("Unable to bind to GCommService");
    while (true)
    {
      return;
      this.audioManager = ((AudioManager)paramEsApplication.getSystemService("audio"));
      this.connectivityManager = ((ConnectivityManager)paramEsApplication.getSystemService("connectivity"));
      this.gcommNativeWrapper = new GCommNativeWrapper(paramEsApplication.getApplicationContext());
      PowerManager localPowerManager = (PowerManager)paramEsApplication.getSystemService("power");
      this.cpuWakeLock = localPowerManager.newWakeLock(1, "gcomm");
      this.screenWakeLock = localPowerManager.newWakeLock(10, "gcomm");
      this.wifiLock = ((WifiManager)paramEsApplication.getSystemService("wifi")).createWifiLock(1, "gcomm");
      this.hangoutSigninRefCount = 0;
      gcommApp = this;
      paramEsApplication.registerReceiver(this.screenoffBroadcastListener, new IntentFilter("android.intent.action.SCREEN_OFF"));
    }
  }

  public static void deactivateAccount(Context paramContext, EsAccount paramEsAccount)
  {
    if ((gcommApp != null) && (gcommApp.isInAHangout()))
    {
      gcommApp.exitMeetingAndDisconnect();
      gcommApp.dispatchMessage(54, null);
    }
    HangoutRingingActivity.deactivateAccount(paramContext, paramEsAccount);
  }

  private void exitMeetingAndDisconnect()
  {
    Log.debug("GCommApp.exitMeetingAndDisconnect");
    this.gcommNativeWrapper.stopOutgoingVideo();
    exitMeeting();
    disconnect();
  }

  private ArrayList<GCommEventHandler> getAllEventHandlers()
  {
    ArrayList localArrayList = new ArrayList(2 + this.eventHandlers.size());
    if (this.appEventHandler != null)
      localArrayList.add(this.appEventHandler);
    if (this.serviceEventHandler != null)
      localArrayList.add(this.serviceEventHandler);
    localArrayList.addAll(this.eventHandlers);
    return localArrayList;
  }

  private static GCommNativeWrapper.DeviceCaptureType getCaptureSessionType()
  {
    String str1 = "";
    float f;
    int i;
    try
    {
      InputStream localInputStream = new ProcessBuilder(new String[] { "/system/bin/cat", "/proc/cpuinfo" }).start().getInputStream();
      byte[] arrayOfByte = new byte[1024];
      while (localInputStream.read(arrayOfByte) != -1)
        str1 = str1 + new String(arrayOfByte);
      localInputStream.close();
      f = 0.0F;
      i = 0;
      for (String str2 : str1.split("\n"))
        if (str2.matches("BogoMIPS.*"))
        {
          f += Float.parseFloat(str2.replaceAll("[^.0-9]", ""));
          i++;
        }
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.debug(localIOException.toString());
      if (f <= 10.0F);
    }
    if ((f < 200.0F) || (f > 1000.0F) || (i > 1));
    for (GCommNativeWrapper.DeviceCaptureType localDeviceCaptureType = GCommNativeWrapper.DeviceCaptureType.MEDIUM_RESOLUTION; ; localDeviceCaptureType = GCommNativeWrapper.DeviceCaptureType.LOW_RESOLUTION)
      return localDeviceCaptureType;
  }

  public static GCommApp getInstance(Context paramContext)
  {
    if (gcommApp == null)
      gcommApp = new GCommApp((EsApplication)paramContext.getApplicationContext());
    return gcommApp;
  }

  public static boolean isDebuggable(Context paramContext)
  {
    if ((0x2 & paramContext.getApplicationInfo().flags) != 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isInstantiated()
  {
    if (gcommApp != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static void reportCrash(String paramString, boolean paramBoolean)
  {
    Thread.setDefaultUncaughtExceptionHandler(null);
    if ((crashReported) || (gcommApp == null));
    while (true)
    {
      return;
      crashReported = true;
      Intent localIntent = new Intent(gcommApp.plusOneApplication, CrashTriggerActivity.class);
      localIntent.addFlags(268435456);
      if (paramString != null)
        localIntent.putExtra("com.google.android.apps.plus.hangout.java_crash_signature", paramString);
      gcommApp.plusOneApplication.startActivity(localIntent);
      if (gcommApp.currentStartedHangoutActivity != null)
        gcommApp.currentStartedHangoutActivity.finish();
      GCommApp localGCommApp = gcommApp;
      if (localGCommApp.isBound)
      {
        localGCommApp.plusOneApplication.unbindService(localGCommApp.gcommServiceConnection);
        localGCommApp.isBound = false;
      }
    }
  }

  static void reportJavaCrashFromNativeCode(Throwable paramThrowable)
  {
    Log.error(android.util.Log.getStackTraceString(paramThrowable));
    reportCrash(CrashReport.computeJavaCrashSignature(paramThrowable), false);
  }

  static void reportNativeCrash()
  {
    reportCrash(null, false);
  }

  private void resetSelfMediaState()
  {
    this.outgoingVideoMute = false;
    if (Cameras.isFrontFacingCameraAvailable())
      this.lastUsedCameraType = Cameras.CameraType.FrontFacing;
    while (true)
    {
      return;
      if (Cameras.isRearFacingCameraAvailable())
        this.lastUsedCameraType = Cameras.CameraType.RearFacing;
    }
  }

  public static void sendEmptyMessage(Context paramContext, int paramInt)
  {
    sendObjectMessage(paramContext, paramInt, null);
  }

  public static void sendObjectMessage(Context paramContext, final int paramInt, final Object paramObject)
  {
    GCommApp localGCommApp = getInstance(paramContext);
    localGCommApp.handler.post(new Runnable()
    {
      public final void run()
      {
        this.val$gcommApp.dispatchMessage(paramInt, paramObject);
      }
    });
  }

  private void setupAudio()
  {
    this.audioFocus = true;
    setAudioMute(this.muteMicOnAudioFocusGain);
    this.gcommNativeWrapper.setIncomingAudioVolume(this.incomingAudioLevelBeforeAudioFocusLoss);
    if ((this.headsetBroadcastReceiver != null) && (!this.headsetBroadcastReceiver.isHeadsetPluggedIn()))
      this.audioManager.setSpeakerphoneOn(true);
  }

  private void updateWakeLockState(boolean paramBoolean)
  {
    WakeLockType localWakeLockType;
    if (paramBoolean)
    {
      localWakeLockType = WakeLockType.SCREEN;
      switch (5.$SwitchMap$com$google$android$apps$plus$hangout$GCommApp$WakeLockType[localWakeLockType.ordinal()])
      {
      default:
      case 1:
      case 2:
      case 3:
      }
    }
    while (true)
    {
      return;
      localWakeLockType = WakeLockType.NONE;
      break;
      if (this.cpuWakeLock.isHeld())
      {
        this.cpuWakeLock.release();
        Log.info("Released CPU wake lock");
      }
      if (this.screenWakeLock.isHeld())
      {
        this.screenWakeLock.release();
        Log.info("Released screen wake lock");
        continue;
        if (!this.cpuWakeLock.isHeld())
        {
          this.cpuWakeLock.acquire();
          Log.info("Acquired CPU wake lock");
        }
        if (this.screenWakeLock.isHeld())
        {
          this.screenWakeLock.release();
          Log.info("Released screen wake lock");
          continue;
          if (!this.screenWakeLock.isHeld())
          {
            this.screenWakeLock.acquire();
            Log.info("Acquired screen wake lock");
          }
          if (this.cpuWakeLock.isHeld())
          {
            this.cpuWakeLock.release();
            Log.info("Released CPU wake lock");
          }
        }
      }
    }
  }

  public final void createHangout(boolean paramBoolean)
  {
    Log.debug("GCommApp.createHangout");
    updateWakeLockState(true);
    this.gcommNativeWrapper.createHangout(paramBoolean);
  }

  public final void disconnect()
  {
    Log.debug("GCommApp.disconnect: " + this.gcommNativeWrapper);
    if (this.wifiLock.isHeld())
    {
      this.wifiLock.release();
      Log.info("Released wifi lock");
    }
    if (this.currentNetworkType != -1)
    {
      this.currentNetworkType = -1;
      this.currentNetworkSubtype = -1;
      this.plusOneApplication.unregisterReceiver(this.connectivityChangeListener);
    }
    this.gcommNativeWrapper.signoutAndDisconnect();
    resetSelfMediaState();
  }

  public final void dispatchMessage(int paramInt, Object paramObject)
  {
    Iterator localIterator32;
    switch (paramInt)
    {
    default:
      throw new IllegalStateException();
    case -1:
      localIterator32 = getAllEventHandlers().iterator();
    case -2:
    case 1:
    case 2:
    case 3:
    case 5:
    case 6:
      while (localIterator32.hasNext())
      {
        ((GCommEventHandler)localIterator32.next()).onError((GCommNativeWrapper.Error)paramObject);
        continue;
        Iterator localIterator31 = getAllEventHandlers().iterator();
        while (localIterator31.hasNext())
        {
          ((GCommEventHandler)localIterator31.next()).onSigninTimeOutError();
          continue;
          Iterator localIterator30 = getAllEventHandlers().iterator();
          while (localIterator30.hasNext())
          {
            ((GCommEventHandler)localIterator30.next()).onSignedIn((String)paramObject);
            continue;
            Iterator localIterator29 = getAllEventHandlers().iterator();
            while (localIterator29.hasNext())
            {
              ((GCommEventHandler)localIterator29.next()).onSignedOut();
              continue;
              Iterator localIterator28 = getAllEventHandlers().iterator();
              while (localIterator28.hasNext())
              {
                ((GCommEventHandler)localIterator28.next()).onVCardResponse((MeetingMember)paramObject);
                continue;
                Iterator localIterator27 = getAllEventHandlers().iterator();
                while (localIterator27.hasNext())
                {
                  localIterator27.next();
                  continue;
                  createHangout(true);
                }
              }
            }
          }
        }
      }
    case -3:
    case 52:
    case 53:
    case 54:
    case 60:
    case 50:
    case 51:
    case 55:
    case 56:
    case 57:
    case 59:
    case 106:
    case 107:
    case 101:
    case 111:
    case 112:
    case 102:
    case 103:
    case 104:
    case 105:
    case 109:
    case 110:
    case 201:
    case 202:
    case 203:
    case 204:
    }
    while (true)
    {
      return;
      Iterator localIterator26 = getAllEventHandlers().iterator();
      while (localIterator26.hasNext())
        ((GCommEventHandler)localIterator26.next()).onMeetingEnterError((GCommNativeWrapper.MeetingEnterError)paramObject);
      continue;
      Iterator localIterator25 = getAllEventHandlers().iterator();
      while (localIterator25.hasNext())
        ((GCommEventHandler)localIterator25.next()).onMucEntered((MeetingMember)paramObject);
      continue;
      Iterator localIterator24 = getAllEventHandlers().iterator();
      while (localIterator24.hasNext())
        ((GCommEventHandler)localIterator24.next()).onMeetingMediaStarted();
      continue;
      Iterator localIterator23 = getAllEventHandlers().iterator();
      label629: GCommEventHandler localGCommEventHandler2;
      if (localIterator23.hasNext())
      {
        localGCommEventHandler2 = (GCommEventHandler)localIterator23.next();
        if (paramObject == null)
          break label668;
      }
      label668: for (boolean bool = true; ; bool = false)
      {
        localGCommEventHandler2.onMeetingExited(bool);
        break label629;
        break;
      }
      Pair localPair6 = (Pair)paramObject;
      Iterator localIterator22 = getAllEventHandlers().iterator();
      while (localIterator22.hasNext())
      {
        GCommEventHandler localGCommEventHandler1 = (GCommEventHandler)localIterator22.next();
        ((Integer)localPair6.first).intValue();
        localGCommEventHandler1.onCallgrokLogUploadCompleted$4f708078();
      }
      continue;
      Iterator localIterator21 = getAllEventHandlers().iterator();
      while (localIterator21.hasNext())
        ((GCommEventHandler)localIterator21.next()).onHangoutCreated((Hangout.Info)paramObject);
      continue;
      Iterator localIterator20 = getAllEventHandlers().iterator();
      while (localIterator20.hasNext())
        ((GCommEventHandler)localIterator20.next()).onHangoutWaitTimeout((Hangout.Info)paramObject);
      continue;
      Iterator localIterator19 = getAllEventHandlers().iterator();
      while (localIterator19.hasNext())
        ((GCommEventHandler)localIterator19.next()).onMeetingMemberEntered((MeetingMember)paramObject);
      continue;
      Iterator localIterator18 = getAllEventHandlers().iterator();
      while (localIterator18.hasNext())
        ((GCommEventHandler)localIterator18.next()).onMeetingMemberPresenceConnectionStatusChanged((MeetingMember)paramObject);
      continue;
      Iterator localIterator17 = getAllEventHandlers().iterator();
      while (localIterator17.hasNext())
        ((GCommEventHandler)localIterator17.next()).onMeetingMemberExited((MeetingMember)paramObject);
      continue;
      Iterator localIterator16 = getAllEventHandlers().iterator();
      while (localIterator16.hasNext())
        localIterator16.next();
      continue;
      Iterator localIterator15 = getAllEventHandlers().iterator();
      while (localIterator15.hasNext())
        ((GCommEventHandler)localIterator15.next()).onIncomingVideoFrameReceived(((Integer)paramObject).intValue());
      continue;
      GCommNativeWrapper.FrameDimensionsChangedMessageParams localFrameDimensionsChangedMessageParams = (GCommNativeWrapper.FrameDimensionsChangedMessageParams)paramObject;
      Iterator localIterator14 = getAllEventHandlers().iterator();
      while (localIterator14.hasNext())
        ((GCommEventHandler)localIterator14.next()).onIncomingVideoFrameDimensionsChanged(localFrameDimensionsChangedMessageParams.getRequestID(), localFrameDimensionsChangedMessageParams.getDimensions().width, localFrameDimensionsChangedMessageParams.getDimensions().height);
      continue;
      Pair localPair5 = (Pair)paramObject;
      Iterator localIterator13 = getAllEventHandlers().iterator();
      while (localIterator13.hasNext())
        ((GCommEventHandler)localIterator13.next()).onAudioMuteStateChanged((MeetingMember)localPair5.first, ((Boolean)localPair5.second).booleanValue());
      continue;
      Pair localPair4 = (Pair)paramObject;
      Iterator localIterator12 = getAllEventHandlers().iterator();
      while (localIterator12.hasNext())
        ((GCommEventHandler)localIterator12.next()).onVideoPauseStateChanged((MeetingMember)localPair4.first, ((Boolean)localPair4.second).booleanValue());
      continue;
      Pair localPair3 = (Pair)paramObject;
      Iterator localIterator11 = getAllEventHandlers().iterator();
      while (localIterator11.hasNext())
        ((GCommEventHandler)localIterator11.next()).onVolumeChanged((MeetingMember)localPair3.first, ((Integer)localPair3.second).intValue());
      continue;
      Iterator localIterator10 = getAllEventHandlers().iterator();
      while (localIterator10.hasNext())
        ((GCommEventHandler)localIterator10.next()).onCurrentSpeakerChanged((MeetingMember)paramObject);
      continue;
      GCommNativeWrapper.VideoSourceChangedMessageParams localVideoSourceChangedMessageParams = (GCommNativeWrapper.VideoSourceChangedMessageParams)paramObject;
      Iterator localIterator9 = getAllEventHandlers().iterator();
      while (localIterator9.hasNext())
        ((GCommEventHandler)localIterator9.next()).onVideoSourceChanged(localVideoSourceChangedMessageParams.getRequestID(), localVideoSourceChangedMessageParams.getSource(), localVideoSourceChangedMessageParams.isVideoAvailable());
      continue;
      Iterator localIterator8 = getAllEventHandlers().iterator();
      while (localIterator8.hasNext())
        ((GCommEventHandler)localIterator8.next()).onIncomingVideoStarted(((Integer)paramObject).intValue());
      continue;
      Iterator localIterator7 = getAllEventHandlers().iterator();
      while (localIterator7.hasNext())
        ((GCommEventHandler)localIterator7.next()).onOutgoingVideoStarted();
      continue;
      Pair localPair2 = (Pair)paramObject;
      Iterator localIterator6 = getAllEventHandlers().iterator();
      while (localIterator6.hasNext())
        ((GCommEventHandler)localIterator6.next()).onRemoteMute((MeetingMember)localPair2.first, (MeetingMember)localPair2.second);
      continue;
      Pair localPair1 = (Pair)paramObject;
      Iterator localIterator5 = getAllEventHandlers().iterator();
      while (localIterator5.hasNext())
        ((GCommEventHandler)localIterator5.next()).onMediaBlock((MeetingMember)((Pair)localPair1.first).first, (MeetingMember)((Pair)localPair1.first).second, ((Boolean)localPair1.second).booleanValue());
      continue;
      Iterator localIterator4 = getAllEventHandlers().iterator();
      while (localIterator4.hasNext())
        ((GCommEventHandler)localIterator4.next()).onCameraSwitchRequested();
      continue;
      Iterator localIterator3 = getAllEventHandlers().iterator();
      while (localIterator3.hasNext())
        ((GCommEventHandler)localIterator3.next()).onVideoMuteToggleRequested();
      continue;
      Iterator localIterator2 = getAllEventHandlers().iterator();
      while (localIterator2.hasNext())
        ((GCommEventHandler)localIterator2.next()).onVideoMuteChanged(((Boolean)paramObject).booleanValue());
      continue;
      RectangleDimensions localRectangleDimensions = (RectangleDimensions)paramObject;
      Iterator localIterator1 = getAllEventHandlers().iterator();
      while (localIterator1.hasNext())
        ((GCommEventHandler)localIterator1.next()).onCameraPreviewFrameDimensionsChanged(localRectangleDimensions.width, localRectangleDimensions.height);
    }
  }

  public final void enterHangout(Hangout.Info paramInfo, boolean paramBoolean1, List<Data.Participant> paramList, boolean paramBoolean2)
  {
    Log.debug("GCommApp.enterHangout: %s", new Object[] { paramInfo });
    updateWakeLockState(true);
    this.selectedVideoSource = null;
    this.gcommNativeWrapper.enterMeeting(paramInfo, paramBoolean1, paramBoolean2);
    if (paramList == null)
    {
      this.greenRoomParticipantIds = null;
      this.isExitingHangout = false;
      this.hangoutInfo = paramInfo;
      return;
    }
    if (this.greenRoomParticipantIds == null)
      this.greenRoomParticipantIds = new HashSet();
    while (true)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Data.Participant localParticipant = (Data.Participant)localIterator.next();
        this.greenRoomParticipantIds.add(localParticipant.getParticipantId());
      }
      break;
      this.greenRoomParticipantIds.clear();
    }
  }

  public final void exitMeeting()
  {
    Log.debug("GCommApp.exitMeeting");
    exitMeetingCleanup();
    assert (this.gcommNativeWrapper != null);
    this.gcommNativeWrapper.exitMeeting();
  }

  public final void exitMeetingCleanup()
  {
    this.isExitingHangout = true;
    updateWakeLockState(false);
    assert (this.isExitingHangout);
    int i;
    if (this.audioFocus)
    {
      i = this.gcommNativeWrapper.getIncomingAudioVolume();
      SharedPreferences.Editor localEditor = this.plusOneApplication.getSharedPreferences(getClass().getName(), 0).edit();
      localEditor.putInt("INCOMING_AUDIO_VOLUME", i);
      localEditor.commit();
      onAudioFocusChange(-1);
      if ((Build.VERSION.SDK_INT >= 14) || (this.mSavedAudioMode == -2))
        break label182;
      this.audioManager.setMode(this.mSavedAudioMode);
    }
    while (true)
    {
      this.mSavedAudioMode = -2;
      this.audioManager.abandonAudioFocus(this);
      this.audioFocus = false;
      if (this.headsetBroadcastReceiver != null)
      {
        this.plusOneApplication.unregisterReceiver(this.headsetBroadcastReceiver);
        this.headsetBroadcastReceiver = null;
      }
      this.hangoutStartTime = -1L;
      this.exitMeetingCleanupDone = true;
      return;
      i = this.incomingAudioLevelBeforeAudioFocusLoss;
      break;
      label182: this.audioManager.setMode(0);
    }
  }

  public final EsAccount getAccount()
  {
    return this.account;
  }

  public final EsApplication getApp()
  {
    return this.plusOneApplication;
  }

  public final GCommNativeWrapper getGCommNativeWrapper()
  {
    return this.gcommNativeWrapper;
  }

  public final GCommService getGCommService()
  {
    return this.gcommService;
  }

  public final Cameras.CameraType getLastUsedCameraType()
  {
    return this.lastUsedCameraType;
  }

  public final MeetingMember getSelectedVideoSource()
  {
    return this.selectedVideoSource;
  }

  public final boolean hasAudioFocus()
  {
    return this.audioFocus;
  }

  public final boolean isAudioMute()
  {
    return this.gcommNativeWrapper.isAudioMute();
  }

  public final boolean isExitingHangout()
  {
    return this.isExitingHangout;
  }

  public final boolean isInAHangout()
  {
    GCommNativeWrapper localGCommNativeWrapper = this.gcommNativeWrapper;
    boolean bool = false;
    if (localGCommNativeWrapper == null);
    while (true)
    {
      return bool;
      GCommNativeWrapper.GCommAppState localGCommAppState1 = this.gcommNativeWrapper.getCurrentState();
      if (localGCommAppState1 != GCommNativeWrapper.GCommAppState.IN_MEETING_WITHOUT_MEDIA)
      {
        GCommNativeWrapper.GCommAppState localGCommAppState2 = GCommNativeWrapper.GCommAppState.IN_MEETING_WITH_MEDIA;
        bool = false;
        if (localGCommAppState1 != localGCommAppState2);
      }
      else
      {
        bool = true;
      }
    }
  }

  public final boolean isInAHangoutWithMedia()
  {
    if ((this.gcommNativeWrapper != null) && (this.gcommNativeWrapper.getCurrentState() == GCommNativeWrapper.GCommAppState.IN_MEETING_WITH_MEDIA));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isInHangout(Hangout.Info paramInfo)
  {
    if (!isInAHangout());
    for (boolean bool = false; ; bool = this.gcommNativeWrapper.isInHangout(paramInfo))
      return bool;
  }

  public final boolean isOutgoingVideoMute()
  {
    return this.outgoingVideoMute;
  }

  public void onAudioFocusChange(int paramInt)
  {
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(paramInt);
    arrayOfObject1[1] = Boolean.valueOf(isInAHangoutWithMedia());
    Log.debug("onAudioFocusChange: %d (meeting=%s)", arrayOfObject1);
    if (!isInAHangoutWithMedia());
    while (true)
    {
      return;
      switch (paramInt)
      {
      case 0:
      default:
        break;
      case -3:
        this.incomingAudioLevelBeforeAudioFocusLoss = this.gcommNativeWrapper.getIncomingAudioVolume();
        int i = 0 + (0 + this.incomingAudioLevelBeforeAudioFocusLoss) / 5;
        this.gcommNativeWrapper.setIncomingAudioVolume(i);
        this.muteMicOnAudioFocusGain = this.gcommNativeWrapper.isAudioMute();
        setAudioMute(true);
        break;
      case 1:
        setupAudio();
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Boolean.valueOf(this.audioManager.isSpeakerphoneOn());
        arrayOfObject3[1] = Integer.valueOf(this.gcommNativeWrapper.getIncomingAudioVolume());
        Log.info("AUDIOFOCUS_GAIN: speakerphone=%s volume=%d", arrayOfObject3);
        break;
      case -2:
      case -1:
        this.audioFocus = false;
        if (!this.isExitingHangout)
        {
          this.incomingAudioLevelBeforeAudioFocusLoss = this.gcommNativeWrapper.getIncomingAudioVolume();
          this.gcommNativeWrapper.setIncomingAudioVolume(0);
          this.muteMicOnAudioFocusGain = this.gcommNativeWrapper.isAudioMute();
          setAudioMute(true);
        }
        this.audioManager.setSpeakerphoneOn(false);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Boolean.valueOf(this.audioManager.isSpeakerphoneOn());
        Log.info("AUDIOFOCUS_LOSS: speakerphone=%s", arrayOfObject2);
      }
    }
  }

  final void raiseNetworkError()
  {
    sendObjectMessage(this.plusOneApplication, -1, GCommNativeWrapper.Error.NETWORK);
  }

  public final void registerForEvents(Context paramContext, GCommEventHandler paramGCommEventHandler, boolean paramBoolean)
  {
    Log.info("Registering for events: %s", new Object[] { paramGCommEventHandler });
    assert (Utils.isOnMainThread(paramContext));
    assert (paramGCommEventHandler != null);
    if (paramBoolean)
    {
      assert (this.serviceEventHandler == null);
      this.serviceEventHandler = paramGCommEventHandler;
    }
    while (true)
    {
      return;
      this.eventHandlers.add(paramGCommEventHandler);
    }
  }

  public final void setAudioMute(boolean paramBoolean)
  {
    this.gcommNativeWrapper.setAudioMute(paramBoolean);
  }

  public final void setLastUsedCameraType(Cameras.CameraType paramCameraType)
  {
    assert (paramCameraType != null);
    this.lastUsedCameraType = paramCameraType;
  }

  public final void setOutgoingVideoMute(boolean paramBoolean)
  {
    this.outgoingVideoMute = paramBoolean;
  }

  public final void setSelectedVideoSource(MeetingMember paramMeetingMember)
  {
    this.selectedVideoSource = paramMeetingMember;
  }

  public final boolean shouldShowToastForMember(MeetingMember paramMeetingMember)
  {
    boolean bool1 = paramMeetingMember.isSelf();
    boolean bool2 = false;
    if (bool1)
      return bool2;
    long l = new Date().getTime();
    int i;
    if ((this.hangoutStartTime != -1L) && (l - this.hangoutStartTime > 5000L))
    {
      i = 1;
      label53: if ((this.greenRoomParticipantIds == null) || (this.greenRoomParticipantIds.contains(paramMeetingMember.getId())))
        break label100;
    }
    label100: for (int j = 1; ; j = 0)
    {
      if (j == 0)
      {
        bool2 = false;
        if (i == 0)
          break;
      }
      bool2 = true;
      break;
      i = 0;
      break label53;
    }
  }

  public final void signinUser(EsAccount paramEsAccount)
  {
    int i = 1;
    Log.info("GCommApp.signinUser: signinTask=" + this.signinTask);
    NetworkInfo localNetworkInfo;
    if (this.signinTask == null)
    {
      localNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo != null)
        break label97;
      Log.info("startUsingNetwork: info is null");
      raiseNetworkError();
      i = 0;
    }
    while (true)
    {
      if (i != 0)
      {
        this.account = paramEsAccount;
        this.signinTask = new SigninTask(this.plusOneApplication.getApplicationContext());
        this.signinTask.execute(new Void[0]);
      }
      return;
      label97: this.currentNetworkType = localNetworkInfo.getType();
      this.currentNetworkSubtype = localNetworkInfo.getSubtype();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.currentNetworkType);
      arrayOfObject[i] = Integer.valueOf(this.currentNetworkSubtype);
      Log.info("Current network type: %d subtype: %d", arrayOfObject);
      this.plusOneApplication.registerReceiver(this.connectivityChangeListener, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
      if ((this.currentNetworkType == i) && (!this.wifiLock.isHeld()))
      {
        this.wifiLock.acquire();
        Log.info("Acquired wifi lock");
      }
    }
  }

  public final void startingHangoutActivity(EsFragmentActivity paramEsFragmentActivity)
  {
    this.hangoutSigninRefCount = (1 + this.hangoutSigninRefCount);
    this.currentStartedHangoutActivity = paramEsFragmentActivity;
    Log.debug("Starting HangoutActivity: " + this.hangoutSigninRefCount);
  }

  public final void stoppingHangoutActivity()
  {
    assert (this.hangoutSigninRefCount > 0);
    this.hangoutSigninRefCount = (-1 + this.hangoutSigninRefCount);
    this.currentStartedHangoutActivity = null;
    Log.debug("Stopping HangoutActivity: " + this.hangoutSigninRefCount);
    if (this.gcommNativeWrapper == null);
    while (true)
    {
      return;
      if (this.hangoutSigninRefCount == 0)
        if (isInAHangoutWithMedia())
        {
          if (this.gcommNativeWrapper.isOutgoingVideoStarted())
            this.gcommNativeWrapper.stopOutgoingVideo();
        }
        else
          disconnect();
    }
  }

  public final void unregisterForEvents(Context paramContext, GCommEventHandler paramGCommEventHandler, boolean paramBoolean)
  {
    Log.info("Unregistering for events: %s", new Object[] { paramGCommEventHandler });
    assert (Utils.isOnMainThread(paramContext));
    assert (paramGCommEventHandler != null);
    if (paramBoolean)
    {
      assert (paramGCommEventHandler == this.serviceEventHandler);
      this.serviceEventHandler = null;
    }
    while (true)
    {
      return;
      assert (this.eventHandlers.contains(paramGCommEventHandler));
      this.eventHandlers.remove(paramGCommEventHandler);
    }
  }

  private final class AppEventHandler extends GCommEventHandler
  {
    static
    {
      if (!GCommApp.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    private AppEventHandler()
    {
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      if (GCommApp.this.hangoutInfo != null)
      {
        ExitHistory.recordErrorExit(GCommApp.this.plusOneApplication, GCommApp.this.hangoutInfo, paramError, false);
        GCommApp.access$902(GCommApp.this, null);
      }
      GCommApp.this.exitMeetingAndDisconnect();
    }

    public final void onMeetingEnterError(GCommNativeWrapper.MeetingEnterError paramMeetingEnterError)
    {
    }

    public final void onMeetingExited(boolean paramBoolean)
    {
      if (!GCommApp.this.exitMeetingCleanupDone)
        GCommApp.this.exitMeetingCleanup();
      GCommApp.access$1702(GCommApp.this, false);
      if (GCommApp.this.hangoutInfo != null)
      {
        ExitHistory.recordNormalExit(GCommApp.this.plusOneApplication, GCommApp.this.hangoutInfo, false);
        GCommApp.access$902(GCommApp.this, null);
      }
    }

    public final void onMeetingMediaStarted()
    {
      GCommApp.access$1102(GCommApp.this, false);
      assert (GCommApp.access$1200(GCommApp.this));
      GCommApp.access$1302(GCommApp.this, new GCommApp.HeadsetBroadcastReceiver(GCommApp.this, (byte)0));
      GCommApp.access$1500(GCommApp.this);
      GCommApp.this.plusOneApplication.registerReceiver(GCommApp.this.headsetBroadcastReceiver, new IntentFilter("android.intent.action.HEADSET_PLUG"));
      GCommApp.access$1602(GCommApp.this, System.currentTimeMillis());
    }

    public final void onSigninTimeOutError()
    {
      GCommApp.this.exitMeetingAndDisconnect();
    }
  }

  private final class ConnectivityChangeListener extends BroadcastReceiver
  {
    private ConnectivityChangeListener()
    {
    }

    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      NetworkInfo localNetworkInfo1 = (NetworkInfo)paramIntent.getParcelableExtra("networkInfo");
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = localNetworkInfo1.getTypeName();
      arrayOfObject1[1] = localNetworkInfo1.getState();
      Log.info("Connectivity change: network %s in state %s", arrayOfObject1);
      if (paramIntent.getBooleanExtra("noConnectivity", false))
        Log.info("No connectivity");
      NetworkInfo localNetworkInfo2 = (NetworkInfo)paramIntent.getParcelableExtra("otherNetwork");
      if (localNetworkInfo2 != null)
      {
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = localNetworkInfo2.getTypeName();
        arrayOfObject2[1] = localNetworkInfo2.getSubtypeName();
        arrayOfObject2[2] = localNetworkInfo2.getState();
        Log.info("Other network is %s in state %s", arrayOfObject2);
      }
      if ((localNetworkInfo1.getType() == GCommApp.this.currentNetworkType) && (localNetworkInfo1.getSubtype() == GCommApp.this.currentNetworkSubtype) && (!GCommApp.access$200(localNetworkInfo1)))
        GCommApp.this.raiseNetworkError();
    }
  }

  private final class HeadsetBroadcastReceiver extends BroadcastReceiver
  {
    private boolean headsetPluggedIn = false;

    private HeadsetBroadcastReceiver()
    {
    }

    final boolean isHeadsetPluggedIn()
    {
      return this.headsetPluggedIn;
    }

    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      boolean bool1 = true;
      if (!"android.intent.action.HEADSET_PLUG".equals(paramIntent.getAction()));
      boolean bool2;
      label28: 
      do
      {
        return;
        if (paramIntent.getIntExtra("state", 0) == 0)
          break;
        bool2 = bool1;
        this.headsetPluggedIn = bool2;
      }
      while ((!GCommApp.this.isInAHangoutWithMedia()) || (!GCommApp.this.audioFocus));
      AudioManager localAudioManager = GCommApp.this.audioManager;
      if (!this.headsetPluggedIn);
      while (true)
      {
        localAudioManager.setSpeakerphoneOn(bool1);
        break;
        bool2 = false;
        break label28;
        bool1 = false;
      }
    }
  }

  private final class SigninTask extends AsyncTask<Void, Void, String>
  {
    private final Context context;

    static
    {
      if (!GCommApp.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    SigninTask(Context arg2)
    {
      Object localObject;
      this.context = localObject;
    }

    private String doInBackground(Void[] paramArrayOfVoid)
    {
      assert (paramArrayOfVoid.length == 0);
      try
      {
        String str2 = AuthData.getAuthToken(this.context, GCommApp.this.account.getName(), "webupdates");
        str1 = str2;
        return str1;
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          String str1 = null;
        }
      }
    }
  }

  private static enum WakeLockType
  {
    static
    {
      CPU = new WakeLockType("CPU", 1);
      SCREEN = new WakeLockType("SCREEN", 2);
      WakeLockType[] arrayOfWakeLockType = new WakeLockType[3];
      arrayOfWakeLockType[0] = NONE;
      arrayOfWakeLockType[1] = CPU;
      arrayOfWakeLockType[2] = SCREEN;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.GCommApp
 * JD-Core Version:    0.6.2
 */