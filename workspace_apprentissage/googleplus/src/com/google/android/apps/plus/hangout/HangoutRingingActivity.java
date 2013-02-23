package com.google.android.apps.plus.hangout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.apps.plus.R.array;
import com.google.android.apps.plus.R.bool;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.fragments.CircleNameResolver;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.hangout.multiwaveview.MultiWaveView;
import com.google.android.apps.plus.hangout.multiwaveview.MultiWaveView.OnTriggerListener;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.AndroidNotification;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.AvatarView;
import com.google.apps.gcomm.hangout.proto.HangoutInviteNotification;
import com.google.apps.gcomm.hangout.proto.HangoutInviteNotification.Command;
import com.google.apps.gcomm.hangout.proto.HangoutInviteNotification.NotificationType;
import com.google.apps.gcomm.hangout.proto.HangoutStartContext;
import com.google.apps.gcomm.hangout.proto.HangoutStartContext.Invitation;
import com.google.apps.gcomm.hangout.proto.HangoutStartContext.InvitationType;
import com.google.apps.gcomm.hangout.proto.HangoutStartContext.Type;
import com.google.protobuf.InvalidProtocolBufferException;

public class HangoutRingingActivity extends EsFragmentActivity
{
  private static final String[] INVITER_PROJECTION = { "packed_circle_ids" };
  private static boolean isCurrentlyRinging;
  private static Ringtone mRingtone;
  private static HangoutRingingActivity sRingingActivity = null;
  private EsAccount mAccount;
  private MultiWaveView mAnswerWidget;
  private final Runnable mAnswerWidgetPingRunnable = new Runnable()
  {
    public final void run()
    {
      if (HangoutRingingActivity.this.mAnswerWidget != null)
        HangoutRingingActivity.this.mAnswerWidget.ping();
      if ((HangoutRingingActivity.this.mHandler != null) && (!HangoutRingingActivity.this.mHasActed))
        HangoutRingingActivity.this.mHandler.postDelayed(this, 2000L);
    }
  };
  private Runnable mCallTimeoutRunnable = null;
  private CircleNameResolver mCircleNameResolver;
  volatile boolean mContinueVibrating;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private Hangout.Info mHangoutInfo;
  private final HangoutRingingActivityEventHandler mHangoutRingingEventHandler = new HangoutRingingActivityEventHandler((byte)0);
  private boolean mHasActed = false;
  private String mInviteId;
  private AvatarView mInviterAvatar;
  private TextView mInviterCircleNamesTextView;
  private String mInviterId;
  private String mInviterName;
  private boolean mIsHangoutLite;
  private NotificationManager mNotificationManager;
  private String mPackedCircleIds;
  private RingStatus mPendingFinishStatus = null;
  private final PersonLoaderCallbacks mPersonLoaderCallbacks = new PersonLoaderCallbacks((byte)0);
  private PhoneStateChangeListener mPhoneStateChangeListener;
  private float mSelfVideoVerticalGravity = -0.35F;
  private SelfVideoView mSelfVideoView;
  private FrameLayout mSelfVideoViewContainer;
  Vibrator mVibrator;
  VibratorThread mVibratorThread;
  private ImageButton toggleAudioMuteMenuButton;
  private ImageButton toggleVideoMuteMenuButton;

  static
  {
    isCurrentlyRinging = false;
  }

  private static String buildNotificationTag(Context paramContext, EsAccount paramEsAccount)
  {
    return paramContext.getPackageName() + ":notifications:" + paramEsAccount.getName();
  }

  private void createMissedHangoutNotification()
  {
    String str1 = getResources().getString(R.string.hangout_missed_notification_title);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mInviterName;
    String str2 = String.format(str1, arrayOfObject);
    PersonData localPersonData = new PersonData(EsPeopleData.extractGaiaId(this.mInviterId), this.mInviterName, null);
    PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, Intents.getMissedHangoutCallbackIntent(this, this.mAccount, this.mHangoutInfo, new AudienceData(localPersonData)), 134217728);
    Notification localNotification = new Notification(R.drawable.ic_stat_gplus, str2, System.currentTimeMillis());
    localNotification.flags = (0x10 | localNotification.flags);
    localNotification.setLatestEventInfo(this, str2, getResources().getString(R.string.hangout_missed_notification_content), localPendingIntent);
    ((NotificationManager)getSystemService("notification")).notify(buildNotificationTag(this, this.mAccount), 2, localNotification);
  }

  public static void deactivateAccount(Context paramContext, EsAccount paramEsAccount)
  {
    HangoutRingingActivity localHangoutRingingActivity = sRingingActivity;
    if (localHangoutRingingActivity != null)
      localHangoutRingingActivity.exit(RingStatus.IGNORED);
    String str = buildNotificationTag(paramContext, paramEsAccount);
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    localNotificationManager.cancel(str, 2);
    localNotificationManager.cancel(str, 3);
  }

  private void exit(RingStatus paramRingStatus)
  {
    sRingingActivity = null;
    isCurrentlyRinging = false;
    if (this.mCallTimeoutRunnable != null)
    {
      this.mHandler.removeCallbacks(this.mCallTimeoutRunnable);
      this.mCallTimeoutRunnable = null;
    }
    stopRingTone();
    GCommApp.getInstance(this).unregisterForEvents(this, this.mHangoutRingingEventHandler, false);
    if (this.mPhoneStateChangeListener != null)
      GCommApp.getInstance(this).getApp().unregisterReceiver(this.mPhoneStateChangeListener);
    if (paramRingStatus != RingStatus.ACCEPTED)
      GCommApp.getInstance(this).disconnect();
    removeStatusBarNotification();
    finish();
  }

  public static void onC2DMReceive(Context paramContext, EsAccount paramEsAccount, Intent paramIntent)
  {
    Log.debug("Hangout Invitation Receiver got invitation tickle");
    if (Hangout.getSupportedStatus(paramContext, paramEsAccount) != Hangout.SupportStatus.SUPPORTED)
      Log.debug("Ignoring hangout invitation since this device doesn't support hangouts");
    HangoutStartContext localHangoutStartContext1;
    String str4;
    String str5;
    Hangout.Info localInfo1;
    label828: label834: Hangout.Info localInfo2;
    do
    {
      String str3;
      while (true)
      {
        return;
        Resources localResources = paramContext.getResources();
        String str1 = localResources.getString(R.string.hangout_notify_setting_key);
        boolean bool1 = localResources.getBoolean(R.bool.hangout_notify_setting_default_value);
        if (!PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(str1, bool1))
        {
          Log.debug("Ignoring hangout invitation because of setting");
        }
        else
        {
          String str2 = paramIntent.getStringExtra("id");
          str3 = paramIntent.getStringExtra("notification");
          if ((str2 != null) && (str3 != null))
            break;
          Object[] arrayOfObject1 = { str2, str3 };
          if (EsLog.isLoggable("GoogleMeeting", 5))
            android.util.Log.w("GoogleMeeting", String.format("Incorrect tickle: inviteId = %s, hangoutInviteProtoBase64 = %s", arrayOfObject1));
        }
      }
      byte[] arrayOfByte = Base64.decode(str3, 0);
      HangoutInviteNotification localHangoutInviteNotification1;
      boolean bool2;
      GCommNativeWrapper.GCommAppState localGCommAppState1;
      boolean bool3;
      while (true)
      {
        try
        {
          HangoutInviteNotification localHangoutInviteNotification2 = HangoutInviteNotification.parseFrom(arrayOfByte);
          localHangoutInviteNotification1 = localHangoutInviteNotification2;
          if (localHangoutInviteNotification1 == null)
            Log.debug("Could not decode invite: %s", new Object[] { str3 });
        }
        catch (InvalidProtocolBufferException localInvalidProtocolBufferException)
        {
          while (true)
          {
            Log.warn("Invalid BatchCommand message received");
            localHangoutInviteNotification1 = null;
            continue;
            if (!localHangoutInviteNotification1.hasCommand())
            {
              Log.debug("Ignoring hangoutInviteNotification without any command");
              break;
            }
            localHangoutStartContext1 = localHangoutInviteNotification1.getContext();
            if (localHangoutInviteNotification1.getCommand() == HangoutInviteNotification.Command.DISMISSED)
            {
              Object[] arrayOfObject4 = new Object[2];
              arrayOfObject4[0] = localHangoutInviteNotification1.getCommand();
              arrayOfObject4[1] = localHangoutStartContext1.getHangoutId();
              Log.debug("Got hangoutInviteNotification:\nCommand: %s\nHangoutId: %s", arrayOfObject4);
              if (sRingingActivity != null)
              {
                HangoutRingingActivity localHangoutRingingActivity = sRingingActivity;
                if (localHangoutRingingActivity.mHasActed)
                {
                  Log.debug("Ignoring hangout ring cancellation since user already acted on it");
                  break;
                }
                HangoutStartContext localHangoutStartContext2 = localHangoutInviteNotification1.getContext();
                if ((localHangoutStartContext2.hasHangoutId()) && (localHangoutStartContext2.getHangoutId().equals(localHangoutRingingActivity.mHangoutInfo.getId())))
                {
                  Log.debug("Cancelling hangout ringing.");
                  localHangoutRingingActivity.exit(RingStatus.IGNORED);
                  break;
                }
                Log.debug("Ignoring hangout ring cancellation since hangout ids don't match");
                break;
              }
              Log.debug("Ignoring dismiss command since ring activity is not running.");
              break;
            }
            HangoutStartContext.Type localType = localHangoutStartContext1.getHangoutType();
            if ((localType != HangoutStartContext.Type.REGULAR) && (localType != HangoutStartContext.Type.LITE) && (localType != HangoutStartContext.Type.ONAIR))
            {
              Object[] arrayOfObject3 = new Object[1];
              arrayOfObject3[0] = localHangoutStartContext1.getHangoutType();
              Log.debug("Ignoring Hangout ring for unsupported hangout type: %s", arrayOfObject3);
              break;
            }
            if (!localHangoutStartContext1.hasInvitation())
            {
              Log.debug("Ignoring hangoutStartContext without invitation");
              break;
            }
            HangoutStartContext.Invitation localInvitation = localHangoutStartContext1.getInvitation();
            if ((!localInvitation.hasInviterGaiaId()) || (!localInvitation.hasTimestamp()))
            {
              Log.debug("Ignoring hangoutStartContext without invitation data");
              break;
            }
            if (System.currentTimeMillis() - localInvitation.getTimestamp() > 300000L)
            {
              Log.debug("Ignoring expired hangout invitation tickle. Tickle age = " + (System.currentTimeMillis() - localInvitation.getTimestamp()) + " ms");
              break;
            }
            HangoutStartContext.InvitationType localInvitationType = localInvitation.getInvitationType();
            if ((localInvitationType != HangoutStartContext.InvitationType.HANGOUT) && (localInvitationType != HangoutStartContext.InvitationType.HANGOUT_SYNC) && (localInvitationType != HangoutStartContext.InvitationType.TRANSFER))
            {
              Log.debug("Ignoring unsupported invitation type " + localInvitation.getInvitationType());
              break;
            }
            str4 = "g:" + localInvitation.getInviterGaiaId();
            str5 = localInvitation.getInviterProfileName();
            Object[] arrayOfObject2 = new Object[6];
            arrayOfObject2[0] = localHangoutInviteNotification1.getCommand();
            arrayOfObject2[1] = str4;
            arrayOfObject2[2] = str5;
            arrayOfObject2[3] = localHangoutStartContext1.getHangoutId();
            arrayOfObject2[4] = localHangoutStartContext1.getHangoutType();
            arrayOfObject2[5] = localHangoutInviteNotification1.getNotificationType();
            Log.debug("Got hangoutInviteNotification:\nCommand: %s\nInviterGaiaId: %s\nInviterName: %s\nHangoutId: %s\nHangoutType: %s\nNotificationType: %s", arrayOfObject2);
            Hangout.LaunchSource localLaunchSource = Hangout.LaunchSource.Ring;
            if (localInvitationType == HangoutStartContext.InvitationType.TRANSFER)
              localLaunchSource = Hangout.LaunchSource.Transfer;
            localInfo1 = new Hangout.Info(Hangout.RoomType.CONSUMER, null, null, localHangoutStartContext1.getHangoutId(), localHangoutStartContext1.getNick(), localLaunchSource, false);
            if (((TelephonyManager)paramContext.getSystemService("phone")).getCallState() != 0)
              bool2 = true;
            try
            {
              while (true)
              {
                localGCommAppState1 = GCommApp.getInstance(paramContext).getGCommNativeWrapper().getCurrentState();
                if ((localGCommAppState1 != GCommNativeWrapper.GCommAppState.ENTERING_MEETING) && (localGCommAppState1 != GCommNativeWrapper.GCommAppState.IN_MEETING_WITHOUT_MEDIA) && (localGCommAppState1 != GCommNativeWrapper.GCommAppState.IN_MEETING_WITH_MEDIA))
                  break label828;
                bool3 = true;
                if ((!bool3) || (!GCommApp.getInstance(paramContext).getGCommNativeWrapper().isInHangout(localInfo1)))
                  break label834;
                Log.debug("Ignoring the ring/ding since user is already in same hangout");
                break;
                bool2 = false;
              }
            }
            catch (LinkageError localLinkageError)
            {
              Log.error("Hangout native lib is missing or misconfigured");
              localLinkageError.printStackTrace();
            }
          }
        }
        break;
        bool3 = false;
      }
      if ((localHangoutInviteNotification1.getNotificationType() != HangoutInviteNotification.NotificationType.NOTIFICATION_DING) && (!bool2) && (!bool3) && (!isCurrentlyRinging))
        break;
      Log.debug("Creating ding notification. AppState: " + localGCommAppState1 + ". callInProgress: " + bool2 + ". hangoutInProgress: " + bool3 + ". isCurrentlyRinging: " + isCurrentlyRinging);
      localInfo2 = new Hangout.Info(Hangout.RoomType.CONSUMER, null, null, localHangoutStartContext1.getHangoutId(), localHangoutStartContext1.getNick(), Hangout.LaunchSource.Ding, false);
    }
    while (!AndroidNotification.shouldNotify(paramContext));
    Notification localNotification = new Notification();
    localNotification.icon = R.drawable.ic_stat_gplus;
    localNotification.when = System.currentTimeMillis();
    localNotification.flags = (0x10 | localNotification.flags);
    if (AndroidNotification.hasRingtone(paramContext))
      localNotification.sound = AndroidNotification.getRingtone(paramContext);
    while (true)
    {
      if (AndroidNotification.shouldVibrate(paramContext))
        localNotification.defaults = (0x2 | localNotification.defaults);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, Intents.getHangoutActivityIntent(paramContext, paramEsAccount, localInfo2, false, null), 134217728);
      String str6 = String.format(paramContext.getResources().getString(R.string.hangout_dinging_ticker), new Object[] { str5 });
      String str7 = String.format(paramContext.getResources().getString(R.string.hangout_dinging_content_title), new Object[] { str5 });
      String str8 = String.format(paramContext.getResources().getString(R.string.hangout_dinging_content), new Object[] { str5 });
      localNotification.tickerText = str6;
      localNotification.setLatestEventInfo(paramContext, str7, str8, localPendingIntent);
      ((NotificationManager)paramContext.getSystemService("notification")).notify(buildNotificationTag(paramContext, paramEsAccount), 2, localNotification);
      break;
      localNotification.defaults = (0x1 | localNotification.defaults);
    }
    isCurrentlyRinging = true;
    if (localHangoutStartContext1.getHangoutType() == HangoutStartContext.Type.LITE);
    for (boolean bool4 = true; ; bool4 = false)
    {
      GCommApp localGCommApp = GCommApp.getInstance(paramContext);
      GCommNativeWrapper.GCommAppState localGCommAppState2 = localGCommApp.getGCommNativeWrapper().getCurrentState();
      if ((localGCommAppState2 == GCommNativeWrapper.GCommAppState.SIGNING_IN) || (localGCommAppState2 == GCommNativeWrapper.GCommAppState.SIGNED_IN))
        localGCommApp.disconnect();
      GCommApp.getInstance(paramContext).signinUser(paramEsAccount);
      Intent localIntent = Intents.getHangoutRingingActivityIntent$55105fd9(paramContext, paramEsAccount, str4, str5, localInfo1, bool4);
      localIntent.addFlags(268435456);
      paramContext.startActivity(localIntent);
      break;
    }
  }

  private void removeStatusBarNotification()
  {
    this.mNotificationManager.cancel(buildNotificationTag(this, this.mAccount), 3);
  }

  private void sendHangoutRingStatus(RingStatus paramRingStatus)
  {
    Log.debug("Sending hangout finish request. Status: " + paramRingStatus);
    GCommApp.getInstance(this).getGCommNativeWrapper().sendRingStatus(this.mInviteId, this.mHangoutInfo.getId(), paramRingStatus.toString());
  }

  public static void stopRingActivity()
  {
    HangoutRingingActivity localHangoutRingingActivity = sRingingActivity;
    if (localHangoutRingingActivity != null)
    {
      localHangoutRingingActivity.exit(RingStatus.IGNORED);
      localHangoutRingingActivity.createMissedHangoutNotification();
    }
  }

  private void stopRingTone()
  {
    if (mRingtone != null)
    {
      mRingtone.stop();
      mRingtone = null;
    }
    if (this.mVibratorThread != null)
    {
      this.mContinueVibrating = false;
      this.mVibratorThread = null;
    }
    this.mVibrator.cancel();
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return null;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    int i;
    float f;
    label49: int j;
    label63: label69: label252: Uri localUri;
    if ((0xF & getResources().getConfiguration().screenLayout) == 4)
      if (getResources().getConfiguration().orientation == 1)
      {
        i = 1;
        if (i == 0)
          break label776;
        f = -0.19F;
        this.mSelfVideoVerticalGravity = f;
        if (i == 0)
          break label784;
        j = 1;
        setRequestedOrientation(j);
        setContentView(R.layout.hangout_ringing_activity);
        getWindow().addFlags(6815872);
        Intent localIntent = getIntent();
        this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
        this.mHangoutInfo = ((Hangout.Info)localIntent.getSerializableExtra("hangout_info"));
        this.mInviteId = localIntent.getStringExtra("hangout_invite_id");
        this.mInviterId = localIntent.getStringExtra("hangout_inviter_id");
        this.mInviterName = localIntent.getStringExtra("hangout_inviter_name");
        if (TextUtils.isEmpty(this.mInviterName))
          this.mInviterName = getResources().getString(R.string.hangout_anonymous_person);
        this.mIsHangoutLite = localIntent.getBooleanExtra("hangout_is_lite", false);
        ((TextView)findViewById(R.id.inviter_name)).setText(this.mInviterName.toUpperCase());
        this.mInviterCircleNamesTextView = ((TextView)findViewById(R.id.circle_names));
        this.mInviterAvatar = ((AvatarView)findViewById(R.id.inviter_avatar));
        if (!this.mIsHangoutLite)
          break label798;
        this.mInviterAvatar.setVisibility(8);
        this.mSelfVideoViewContainer = ((FrameLayout)findViewById(R.id.self_video_container));
        this.mCircleNameResolver = new CircleNameResolver(this, getSupportLoaderManager(), this.mAccount);
        this.mCircleNameResolver.initLoader();
        this.mCircleNameResolver.registerObserver(new DataSetObserver()
        {
          public final void onChanged()
          {
            HangoutRingingActivity.access$1000(HangoutRingingActivity.this);
          }
        });
        this.mAnswerWidget = ((MultiWaveView)findViewById(R.id.incomingCallWidget));
        this.mAnswerWidget.setOnTriggerListener(new MultiWaveView.OnTriggerListener()
        {
          public final void onTrigger$5359dc9a(int paramAnonymousInt)
          {
            if (HangoutRingingActivity.this.mHasActed);
            while (true)
            {
              return;
              switch (paramAnonymousInt)
              {
              case 1:
              default:
                Log.debug("Unexpected trigger for MultiwaveView widget value: " + paramAnonymousInt);
                break;
              case 0:
                HangoutRingingActivity.access$1100(HangoutRingingActivity.this);
                break;
              case 2:
                HangoutRingingActivity.access$1200(HangoutRingingActivity.this);
              }
            }
          }
        });
        this.mAnswerWidget.clearAnimation();
        this.mAnswerWidget.setTargetResources(R.array.incoming_hangout_widget_2way_targets);
        this.mAnswerWidget.setTargetDescriptionsResourceId(R.array.incoming_hangout_widget_2way_target_descriptions);
        this.mAnswerWidget.setDirectionDescriptionsResourceId(R.array.incoming_hangout_widget_2way_direction_descriptions);
        this.mAnswerWidget.reset(false);
        this.mHandler.postDelayed(this.mAnswerWidgetPingRunnable, 1000L);
        if (!this.mIsHangoutLite)
          getSupportLoaderManager().initLoader(0, null, this.mPersonLoaderCallbacks);
        this.mVibrator = ((Vibrator)getSystemService("vibrator"));
        this.mNotificationManager = ((NotificationManager)getSystemService("notification"));
        if (this.mCallTimeoutRunnable == null)
        {
          this.mCallTimeoutRunnable = new Runnable()
          {
            public final void run()
            {
              HangoutRingingActivity.this.exit(HangoutRingingActivity.RingStatus.TIMED_OUT);
              HangoutRingingActivity.this.createMissedHangoutNotification();
            }
          };
          this.mHandler.postDelayed(this.mCallTimeoutRunnable, 30000L);
          if (mRingtone != null)
            break label848;
          Resources localResources2 = getResources();
          String str2 = localResources2.getString(R.string.hangout_ringtone_setting_key);
          String str3 = localResources2.getString(R.string.hangout_ringtone_setting_default_value);
          localUri = Uri.parse(PreferenceManager.getDefaultSharedPreferences(this).getString(str2, str3));
          mRingtone = RingtoneManager.getRingtone(this, localUri);
        }
      }
    while (true)
    {
      if (mRingtone == null)
        Log.error("Cannot get a ringtone for " + localUri);
      while (true)
      {
        Resources localResources1 = getResources();
        String str1 = localResources1.getString(R.string.hangout_vibrate_setting_key);
        boolean bool = localResources1.getBoolean(R.bool.hangout_vibrate_setting_default_value);
        if ((PreferenceManager.getDefaultSharedPreferences(this).getBoolean(str1, bool)) && (this.mVibratorThread == null))
        {
          this.mContinueVibrating = true;
          this.mVibratorThread = new VibratorThread((byte)0);
          this.mVibratorThread.start();
        }
        label776: label784: label798: 
        do
        {
          new IntentFilter("com.google.android.c2dm.intent.RECEIVE").addCategory("com.google.android.apps.hangout.NOTIFICATION");
          GCommApp.getInstance(this).registerForEvents(this, this.mHangoutRingingEventHandler, false);
          sRingingActivity = this;
          this.mPhoneStateChangeListener = new PhoneStateChangeListener((byte)0);
          GCommApp.getInstance(this).getApp().registerReceiver(this.mPhoneStateChangeListener, new IntentFilter("android.intent.action.PHONE_STATE"));
          this.toggleAudioMuteMenuButton = ((ImageButton)findViewById(R.id.hangout_menu_toggle_audio_mute));
          this.toggleAudioMuteMenuButton.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              GCommApp localGCommApp = GCommApp.getInstance(HangoutRingingActivity.this);
              if (!GCommApp.getInstance(HangoutRingingActivity.this).isAudioMute());
              for (boolean bool = true; ; bool = false)
              {
                localGCommApp.setAudioMute(bool);
                return;
              }
            }
          });
          this.toggleVideoMuteMenuButton = ((ImageButton)findViewById(R.id.hangout_menu_toggle_video_mute));
          this.toggleVideoMuteMenuButton.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              GCommApp.sendEmptyMessage(HangoutRingingActivity.this, 202);
            }
          });
          return;
          i = 0;
          break;
          f = -0.1F;
          break label49;
          j = 0;
          break label63;
          setRequestedOrientation(1);
          break label69;
          this.mInviterAvatar.setVisibility(0);
          this.mInviterAvatar.setGaiaId(EsPeopleData.extractGaiaId(this.mInviterId));
          break label252;
        }
        while (mRingtone.isPlaying());
        mRingtone.setStreamType(2);
        mRingtone.play();
      }
      label848: localUri = null;
    }
  }

  protected void onPause()
  {
    super.onPause();
    if (sRingingActivity != null)
    {
      Notification localNotification = new Notification(R.drawable.ic_stat_gplus, getString(R.string.hangout_ringing_incoming), System.currentTimeMillis());
      Context localContext = getApplicationContext();
      String str1 = getString(R.string.hangout_ringing_incoming);
      String str2 = this.mInviterName;
      EsAccount localEsAccount = this.mAccount;
      PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, Intents.getHangoutRingingActivityIntent$55105fd9(this, localEsAccount, this.mInviterId, this.mInviterName, this.mHangoutInfo, this.mIsHangoutLite), 0);
      localNotification.flags = 16;
      localNotification.setLatestEventInfo(localContext, str1, str2, localPendingIntent);
      this.mNotificationManager.notify(buildNotificationTag(this, this.mAccount), 3, localNotification);
    }
    this.mSelfVideoView.onPause();
  }

  protected void onResume()
  {
    super.onResume();
    removeStatusBarNotification();
    this.mSelfVideoView.onResume();
  }

  protected void onStart()
  {
    super.onStart();
    this.mSelfVideoView = new SelfVideoView(this, null);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    this.mSelfVideoView.setLayoutParams(localLayoutParams);
    this.mSelfVideoView.turnOffFlashLightSupport();
    this.mSelfVideoView.setLayoutMode(SelfVideoView.LayoutMode.FIT);
    this.mSelfVideoView.setVerticalGravity(this.mSelfVideoVerticalGravity);
    this.mSelfVideoViewContainer.addView(this.mSelfVideoView);
  }

  private final class HangoutRingingActivityEventHandler extends GCommEventHandler
  {
    private HangoutRingingActivityEventHandler()
    {
    }

    public final void onAudioMuteStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      if ((paramMeetingMember == null) || (paramMeetingMember.isSelf()))
      {
        if (!paramBoolean)
          break label52;
        HangoutRingingActivity.this.toggleAudioMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_audio_unmute);
        HangoutRingingActivity.this.toggleAudioMuteMenuButton.setContentDescription(HangoutRingingActivity.this.getResources().getString(R.string.hangout_menu_audio_unmute));
      }
      while (true)
      {
        return;
        label52: HangoutRingingActivity.this.toggleAudioMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_audio_mute);
        HangoutRingingActivity.this.toggleAudioMuteMenuButton.setContentDescription(HangoutRingingActivity.this.getResources().getString(R.string.hangout_menu_audio_mute));
      }
    }

    public final void onSignedIn(String paramString)
    {
      super.onSignedIn(paramString);
      if ((HangoutRingingActivity.this.mHasActed) && (HangoutRingingActivity.this.mPendingFinishStatus != null))
      {
        HangoutRingingActivity.this.sendHangoutRingStatus(HangoutRingingActivity.this.mPendingFinishStatus);
        HangoutRingingActivity.RingStatus localRingStatus = HangoutRingingActivity.this.mPendingFinishStatus;
        HangoutRingingActivity.access$302(HangoutRingingActivity.this, null);
        HangoutRingingActivity.this.exit(localRingStatus);
      }
      Log.debug("Signed in! User jid = " + paramString);
    }

    public final void onVideoMuteChanged(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        HangoutRingingActivity.this.toggleVideoMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_video_unmute);
        HangoutRingingActivity.this.toggleVideoMuteMenuButton.setContentDescription(HangoutRingingActivity.this.getResources().getString(R.string.hangout_menu_video_unmute));
      }
      while (true)
      {
        return;
        HangoutRingingActivity.this.toggleVideoMuteMenuButton.setImageResource(R.drawable.hangout_ic_menu_video_mute);
        HangoutRingingActivity.this.toggleVideoMuteMenuButton.setContentDescription(HangoutRingingActivity.this.getResources().getString(R.string.hangout_menu_video_mute));
      }
    }
  }

  private final class PersonLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<Cursor>
  {
    private PersonLoaderCallbacks()
    {
    }

    public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      if ((HangoutRingingActivity.this.mAccount == null) || (HangoutRingingActivity.this.mInviterId == null));
      final HangoutRingingActivity localHangoutRingingActivity;
      Uri localUri;
      String[] arrayOfString1;
      String[] arrayOfString2;
      for (Object localObject = null; ; localObject = new EsCursorLoader(localHangoutRingingActivity, localUri, arrayOfString1, "person_id=?", arrayOfString2, null)
      {
        public final Cursor esLoadInBackground()
        {
          EsPeopleData.ensurePeopleSynced(localHangoutRingingActivity, HangoutRingingActivity.this.mAccount);
          return super.esLoadInBackground();
        }
      })
      {
        return localObject;
        localHangoutRingingActivity = HangoutRingingActivity.this;
        localUri = EsProvider.appendAccountParameter(EsProvider.CONTACTS_URI, HangoutRingingActivity.this.mAccount);
        arrayOfString1 = HangoutRingingActivity.INVITER_PROJECTION;
        arrayOfString2 = new String[1];
        arrayOfString2[0] = HangoutRingingActivity.this.mInviterId;
      }
    }

    public final void onLoaderReset(Loader<Cursor> paramLoader)
    {
    }
  }

  private final class PhoneStateChangeListener extends BroadcastReceiver
  {
    private PhoneStateChangeListener()
    {
    }

    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getStringExtra("state");
      if (TelephonyManager.EXTRA_STATE_RINGING.equals(str))
      {
        Log.debug("Received incoming phone call. Stopping hangout ring...");
        HangoutRingingActivity.stopRingActivity();
      }
    }
  }

  static enum RingStatus
  {
    static
    {
      RingStatus[] arrayOfRingStatus = new RingStatus[3];
      arrayOfRingStatus[0] = ACCEPTED;
      arrayOfRingStatus[1] = IGNORED;
      arrayOfRingStatus[2] = TIMED_OUT;
    }
  }

  private final class VibratorThread extends Thread
  {
    private VibratorThread()
    {
    }

    public final void run()
    {
      while (HangoutRingingActivity.this.mContinueVibrating)
      {
        HangoutRingingActivity.this.mVibrator.vibrate(1000L);
        SystemClock.sleep(2000L);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutRingingActivity
 * JD-Core Version:    0.6.2
 */