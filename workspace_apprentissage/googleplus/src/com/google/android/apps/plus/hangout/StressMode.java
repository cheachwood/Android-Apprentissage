package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.util.Property;

public final class StressMode
{
  private static StressMode stressMode;
  private final Context context;
  private Runnable disconnectRunnable = new Runnable()
  {
    public final void run()
    {
      Log.debug("StressMode: disconnect");
      StressMode.this.gcommApp.disconnect();
      StressMode.this.handler.postDelayed(StressMode.this.launchGreenRoomRunnable, 2000L);
    }
  };
  private final EventHandler eventHandler = new EventHandler();
  private Runnable exitMeetingRunnable = new Runnable()
  {
    public final void run()
    {
      Log.debug("StressMode: disconnect");
      StressMode.this.gcommApp.exitMeeting();
    }
  };
  private final GCommApp gcommApp;
  private final Handler handler = new Handler();
  private final Hangout.Info hangoutInfo;
  private Runnable launchGreenRoomRunnable = new Runnable()
  {
    public final void run()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(StressMode.isEnabled());
      Log.debug("StressMode: launchGreenRoom: %s", arrayOfObject);
      if (StressMode.isEnabled())
      {
        Intent localIntent = Intents.getHangoutActivityIntent(StressMode.this.context, StressMode.this.gcommApp.getAccount(), StressMode.this.hangoutInfo, false, null);
        StressMode.this.context.startActivity(localIntent);
      }
    }
  };
  private Runnable meetingEnterRunnable = new Runnable()
  {
    public final void run()
    {
      Log.debug("StressMode: enterHangout");
      StressMode.this.gcommApp.enterHangout(StressMode.this.hangoutInfo, true, null, false);
    }
  };

  private StressMode(Context paramContext, GCommApp paramGCommApp, Hangout.Info paramInfo)
  {
    this.context = paramContext;
    this.gcommApp = paramGCommApp;
    this.hangoutInfo = paramInfo;
    paramGCommApp.registerForEvents(paramContext, this.eventHandler, false);
  }

  static void initialize(Context paramContext, GCommApp paramGCommApp, Hangout.Info paramInfo)
  {
    if ((isEnabled()) && (stressMode == null))
      stressMode = new StressMode(paramContext, paramGCommApp, paramInfo);
  }

  static boolean isEnabled()
  {
    return Property.HANGOUT_STRESS_MODE.get().toUpperCase().equals("TRUE");
  }

  final class EventHandler extends GCommEventHandler
  {
    EventHandler()
    {
    }

    public final void onError(GCommNativeWrapper.Error paramError)
    {
      super.onError(paramError);
      StressMode.access$200(StressMode.this);
      StressMode.this.handler.postDelayed(StressMode.this.launchGreenRoomRunnable, 0L);
    }

    public final void onMeetingEnterError(GCommNativeWrapper.MeetingEnterError paramMeetingEnterError)
    {
      super.onMeetingEnterError(paramMeetingEnterError);
      StressMode.this.handler.postDelayed(StressMode.this.meetingEnterRunnable, 0L);
    }

    public final void onMeetingExited(boolean paramBoolean)
    {
      super.onMeetingExited(paramBoolean);
      StressMode.access$200(StressMode.this);
      StressMode.this.handler.postDelayed(StressMode.this.launchGreenRoomRunnable, 0L);
    }

    public final void onMeetingMediaStarted()
    {
      super.onMeetingMediaStarted();
      StressMode.this.handler.postDelayed(StressMode.this.exitMeetingRunnable, 15000L);
    }

    public final void onSignedIn(String paramString)
    {
      super.onSignedIn(paramString);
      StressMode.this.handler.postDelayed(StressMode.this.meetingEnterRunnable, 0L);
    }

    public final void onSignedOut()
    {
      super.onSignedOut();
      StressMode.access$200(StressMode.this);
      StressMode.this.handler.postDelayed(StressMode.this.launchGreenRoomRunnable, 0L);
    }

    public final void onSigninTimeOutError()
    {
      super.onSigninTimeOutError();
      StressMode.this.handler.postDelayed(StressMode.this.launchGreenRoomRunnable, 0L);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.StressMode
 * JD-Core Version:    0.6.2
 */