package com.google.android.apps.plus.hangout;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.raw;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;

public class GCommService extends Service
{
  private Runnable callTimeoutRunnable;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  private Handler handler = new Handler(Looper.getMainLooper());
  private final LocalBinder localBinder = new LocalBinder();
  private Intent notificationIntent;
  private MediaPlayer playbackMediaPlayer;

  public final Intent getNotificationIntent()
  {
    return this.notificationIntent;
  }

  public IBinder onBind(Intent paramIntent)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setClass(this, GCommService.class);
    startService(localIntent);
    return this.localBinder;
  }

  public void onCreate()
  {
    super.onCreate();
    Log.debug("GCommService.onCreate called");
    GCommApp.getInstance(this).registerForEvents(this, this.eventHandler, true);
    stopForeground(true);
  }

  public void onDestroy()
  {
    Log.debug("GCommService.onDestroy called");
    GCommApp.getInstance(this).unregisterForEvents(this, this.eventHandler, true);
    super.onDestroy();
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return 1;
  }

  public final void showHangoutNotification(Intent paramIntent)
  {
    this.notificationIntent = paramIntent;
    Log.info("GCommService.showHangoutNotification");
    PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, paramIntent, 134217728);
    Notification localNotification = new Notification();
    localNotification.icon = R.drawable.hangout_notification_icon;
    localNotification.flags = (0x2 | localNotification.flags);
    localNotification.flags = (0x20 | localNotification.flags);
    localNotification.setLatestEventInfo(getApplicationContext(), getResources().getString(R.string.hangout_ongoing_notify_title), getResources().getString(R.string.hangout_ongoing_notify_text), localPendingIntent);
    startForeground(1, localNotification);
  }

  public final void startRingback()
  {
    stopRingback();
    Log.debug("GCommService.startRingback");
    try
    {
      this.playbackMediaPlayer = MediaPlayer.create(this, R.raw.hangout_ringback);
      if (this.playbackMediaPlayer == null)
      {
        Log.error("Could not create MediaPlayer for " + R.raw.hangout_ringback);
        return;
      }
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      while (true)
      {
        Log.error("Error playing media: ", new Object[] { localNotFoundException });
        continue;
        this.playbackMediaPlayer.setLooping(true);
        this.playbackMediaPlayer.start();
      }
    }
  }

  public final void stopRingback()
  {
    Log.debug("GCommService.stopRingback");
    if (this.playbackMediaPlayer != null)
    {
      this.playbackMediaPlayer.stop();
      this.playbackMediaPlayer = null;
    }
  }

  private final class EventHandler extends GCommEventHandler
    implements MediaPlayer.OnCompletionListener
  {
    private EventHandler()
    {
    }

    private void playSound(int paramInt)
    {
      try
      {
        MediaPlayer localMediaPlayer2 = MediaPlayer.create(GCommService.this, paramInt);
        localMediaPlayer1 = localMediaPlayer2;
        if (localMediaPlayer1 == null)
        {
          Log.error("Could not create MediaPlayer for " + paramInt);
          return;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        while (true)
        {
          Log.error("Error playing media: ", new Object[] { localNotFoundException });
          MediaPlayer localMediaPlayer1 = null;
          continue;
          localMediaPlayer1.setOnCompletionListener(this);
          localMediaPlayer1.start();
        }
      }
    }

    public final void onCompletion(MediaPlayer paramMediaPlayer)
    {
      paramMediaPlayer.release();
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      if (GCommService.this.callTimeoutRunnable != null)
      {
        GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
        GCommService.access$102(GCommService.this, null);
      }
      GCommService.this.stopRingback();
      GCommService.this.stopForeground(true);
    }

    public final void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
    {
      super.onMediaBlock(paramMeetingMember1, paramMeetingMember2, paramBoolean);
      if ((paramMeetingMember2 != null) && (!paramMeetingMember2.isSelf()))
        playSound(R.raw.hangout_alert);
    }

    public final void onMeetingEnterError(GCommNativeWrapper.MeetingEnterError paramMeetingEnterError)
    {
      super.onMeetingEnterError(paramMeetingEnterError);
      if (GCommService.this.callTimeoutRunnable != null)
      {
        GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
        GCommService.access$102(GCommService.this, null);
      }
      GCommService.this.stopRingback();
      GCommService.this.stopForeground(true);
    }

    public final void onMeetingExited(boolean paramBoolean)
    {
      super.onMeetingExited(paramBoolean);
      if (GCommService.this.callTimeoutRunnable != null)
      {
        GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
        GCommService.access$102(GCommService.this, null);
      }
      GCommService.this.stopRingback();
      GCommService.this.stopForeground(true);
    }

    public final void onMeetingMediaStarted()
    {
      super.onMeetingMediaStarted();
      final GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(GCommService.this).getGCommNativeWrapper();
      Hangout.Info localInfo = localGCommNativeWrapper.getHangoutInfo();
      if (localInfo == null)
        Log.debug("Hangout info is null");
      while (true)
      {
        return;
        if ((localInfo.getLaunchSource() == Hangout.LaunchSource.Ring) && (localGCommNativeWrapper.getMeetingMemberCount() == 1))
        {
          Log.debug("Leaving meeting since there are no participants");
          GCommService.access$102(GCommService.this, new Runnable()
          {
            public final void run()
            {
              if (localGCommNativeWrapper.getMeetingMemberCount() == 1)
                GCommApp.getInstance(GCommService.this).exitMeeting();
              GCommService.access$102(GCommService.this, null);
            }
          });
          GCommService.this.handler.postDelayed(GCommService.this.callTimeoutRunnable, 3000L);
        }
      }
    }

    public final void onMeetingMemberEntered(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberEntered(paramMeetingMember);
      if (paramMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTED)
      {
        if (GCommService.this.callTimeoutRunnable != null)
        {
          GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
          GCommService.access$102(GCommService.this, null);
        }
        GCommService.this.stopRingback();
      }
    }

    public final void onMeetingMemberExited(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberExited(paramMeetingMember);
      final GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(GCommService.this).getGCommNativeWrapper();
      Hangout.Info localInfo = localGCommNativeWrapper.getHangoutInfo();
      if ((localInfo != null) && ((localInfo.getLaunchSource() == Hangout.LaunchSource.Ring) || (localInfo.getRingInvitees())) && (localGCommNativeWrapper.getMeetingMemberCount() == 1))
      {
        if (GCommService.this.callTimeoutRunnable != null)
        {
          GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
          GCommService.access$102(GCommService.this, null);
        }
        GCommService.access$102(GCommService.this, new Runnable()
        {
          public final void run()
          {
            if (localGCommNativeWrapper.getMeetingMemberCount() == 1)
              GCommApp.getInstance(GCommService.this).exitMeeting();
            GCommService.access$102(GCommService.this, null);
          }
        });
        GCommService.this.handler.postDelayed(GCommService.this.callTimeoutRunnable, 3000L);
      }
      playSound(R.raw.hangout_leave);
    }

    public final void onMeetingMemberPresenceConnectionStatusChanged(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberPresenceConnectionStatusChanged(paramMeetingMember);
      if (paramMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTED)
      {
        if (GCommService.this.callTimeoutRunnable != null)
        {
          GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
          GCommService.access$102(GCommService.this, null);
        }
        GCommService.this.stopRingback();
      }
      if ((GCommApp.getInstance(GCommService.this).shouldShowToastForMember(paramMeetingMember)) && (paramMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTED))
        playSound(R.raw.hangout_join);
    }

    public final void onMucEntered(MeetingMember paramMeetingMember)
    {
      super.onMucEntered(paramMeetingMember);
      final GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(GCommService.this).getGCommNativeWrapper();
      final Hangout.Info localInfo = localGCommNativeWrapper.getHangoutInfo();
      if (localInfo == null)
        Log.debug("hangoutInfo is null?!?");
      while (true)
      {
        return;
        if (localInfo.getRingInvitees())
        {
          GCommService.this.startRingback();
          if (GCommService.this.callTimeoutRunnable != null)
          {
            GCommService.this.handler.removeCallbacks(GCommService.this.callTimeoutRunnable);
            GCommService.access$102(GCommService.this, null);
          }
          GCommService.access$102(GCommService.this, new Runnable()
          {
            public final void run()
            {
              GCommService.this.stopRingback();
              GCommApp.sendObjectMessage(GCommService.this, 51, localInfo);
              GCommService.access$102(GCommService.this, new Runnable()
              {
                public final void run()
                {
                  if (GCommService.EventHandler.2.this.val$nativeWrapper.getMeetingMemberCount() == 1)
                    GCommApp.getInstance(GCommService.this).exitMeeting();
                  GCommService.access$102(GCommService.this, null);
                }
              });
              GCommService.this.handler.postDelayed(GCommService.this.callTimeoutRunnable, 3000L);
            }
          });
          GCommService.this.handler.postDelayed(GCommService.this.callTimeoutRunnable, 45000L);
        }
      }
    }

    public final void onRemoteMute(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2)
    {
      super.onRemoteMute(paramMeetingMember1, paramMeetingMember2);
      if (!paramMeetingMember2.isSelf())
        playSound(R.raw.hangout_alert);
    }

    public final void onVCardResponse(MeetingMember paramMeetingMember)
    {
      super.onVCardResponse(paramMeetingMember);
      if ((GCommApp.getInstance(GCommService.this).shouldShowToastForMember(paramMeetingMember)) && (paramMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTED))
        playSound(R.raw.hangout_join);
    }
  }

  public final class LocalBinder extends Binder
  {
    public LocalBinder()
    {
    }

    final GCommService getService()
    {
      return GCommService.this;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.GCommService
 * JD-Core Version:    0.6.2
 */