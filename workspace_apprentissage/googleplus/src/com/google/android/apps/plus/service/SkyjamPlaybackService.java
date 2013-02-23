package com.google.android.apps.plus.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.google.android.apps.plus.R.mipmap;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.json.JsonReader;
import com.google.android.apps.plus.network.DefaultHttpRequestConfiguration;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.EsLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

public class SkyjamPlaybackService extends Service
  implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, HttpOperation.OperationListener, ServiceThread.IntentProcessor
{
  private static EsAccount sAccount;
  private static String sActivityId;
  private static int sCurrentTime;
  private static ArrayList<SkyjamPlaybackListener> sListeners = new ArrayList();
  private static String sMusicUrl;
  private static String sSongName;
  private static String sStatus;
  private static int sTotalPlayableTime;
  private Handler mHandler;
  private MediaPlayer mMediaPlayer;
  private NotificationManager mNotificationManager;
  private ServiceThread mServiceThread;
  private final Runnable mUpdateTimeRunnable = new Runnable()
  {
    public final void run()
    {
      if ((SkyjamPlaybackService.this.mMediaPlayer != null) && (SkyjamPlaybackService.this.mMediaPlayer.isPlaying()) && (SkyjamPlaybackService.this.mMediaPlayer.getCurrentPosition() < SkyjamPlaybackService.sTotalPlayableTime))
      {
        SkyjamPlaybackService.access$202(SkyjamPlaybackService.this.mMediaPlayer.getCurrentPosition());
        SkyjamPlaybackService localSkyjamPlaybackService = SkyjamPlaybackService.this;
        int i = R.string.skyjam_status_playing;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = SkyjamPlaybackService.this.getTimeString(SkyjamPlaybackService.sCurrentTime);
        arrayOfObject[1] = SkyjamPlaybackService.this.getTimeString(SkyjamPlaybackService.sTotalPlayableTime);
        SkyjamPlaybackService.access$302(localSkyjamPlaybackService.getString(i, arrayOfObject));
      }
      while (true)
      {
        SkyjamPlaybackService.this.dispatchStatusUpdate();
        SkyjamPlaybackService.this.mHandler.postDelayed(this, 1000L);
        return;
        SkyjamPlaybackService.access$302(SkyjamPlaybackService.this.getString(R.string.skyjam_status_stopped));
      }
    }
  };

  private void dispatchStatusUpdate()
  {
    final String str1 = sMusicUrl;
    if ((str1 != null) && (this.mMediaPlayer != null));
    for (final boolean bool = true; ; bool = false)
    {
      final String str2 = sStatus;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          Iterator localIterator = SkyjamPlaybackService.sListeners.iterator();
          while (localIterator.hasNext())
            ((SkyjamPlaybackService.SkyjamPlaybackListener)localIterator.next()).onPlaybackStatusUpdate(str1, bool, str2);
        }
      });
      return;
    }
  }

  public static String getPlaybackStatus(Context paramContext, String paramString)
  {
    if ((sMusicUrl != null) && (sMusicUrl.equals(paramString)) && (sStatus != null));
    for (String str = sStatus; ; str = paramContext.getString(R.string.skyjam_status_stopped))
      return str;
  }

  private String getTimeString(int paramInt)
  {
    int i = paramInt / 1000;
    int j = i / 60;
    int k = i % 60;
    int m = R.string.skyjam_time_formatting;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(j);
    arrayOfObject[1] = Integer.valueOf(k);
    return getString(m, arrayOfObject);
  }

  public static boolean isPlaying(String paramString)
  {
    if ((sMusicUrl != null) && (sMusicUrl.equals(paramString)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static void logOut(Context paramContext)
  {
    if (sMusicUrl != null)
    {
      Intent localIntent = new Intent(paramContext, SkyjamPlaybackService.class);
      localIntent.setAction("com.google.android.apps.plus.service.SkyjamPlaybackService.STOP");
      localIntent.putExtra("music_account", sAccount);
      localIntent.putExtra("music_url", sMusicUrl);
      localIntent.putExtra("song", sSongName);
      localIntent.putExtra("activity_id", sActivityId);
      paramContext.startService(localIntent);
    }
  }

  public static void registerListener(SkyjamPlaybackListener paramSkyjamPlaybackListener)
  {
    sListeners.add(paramSkyjamPlaybackListener);
  }

  private void stop()
  {
    this.mHandler.removeCallbacks(this.mUpdateTimeRunnable);
    if (this.mMediaPlayer != null)
    {
      if (EsLog.isLoggable("SkyjamPlaybackService", 3))
        Log.d("SkyjamPlaybackService", "stop");
      if (this.mMediaPlayer.isPlaying())
        this.mMediaPlayer.stop();
      this.mMediaPlayer.release();
      this.mMediaPlayer = null;
    }
    sStatus = getString(R.string.skyjam_status_stopped);
    dispatchStatusUpdate();
    sMusicUrl = null;
    sSongName = null;
    sAccount = null;
    sActivityId = null;
    sCurrentTime = 0;
    sTotalPlayableTime = 0;
    this.mNotificationManager.cancel(27312);
  }

  public static void unregisterListener(SkyjamPlaybackListener paramSkyjamPlaybackListener)
  {
    sListeners.remove(paramSkyjamPlaybackListener);
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    if (EsLog.isLoggable("SkyjamPlaybackService", 3))
      Log.d("SkyjamPlaybackService", "buffering: " + paramInt + "%");
  }

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    if (EsLog.isLoggable("SkyjamPlaybackService", 3))
      Log.d("SkyjamPlaybackService", "completion");
    stop();
    stopSelf();
  }

  public void onCreate()
  {
    super.onCreate();
    this.mNotificationManager = ((NotificationManager)getSystemService("notification"));
    sStatus = getString(R.string.skyjam_status_stopped);
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mServiceThread = new ServiceThread(this.mHandler, "SkyjamServiceThread", this);
    this.mServiceThread.start();
  }

  public void onDestroy()
  {
    super.onDestroy();
    if (this.mServiceThread != null)
    {
      this.mServiceThread.quit();
      this.mServiceThread = null;
    }
  }

  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    if (EsLog.isLoggable("SkyjamPlaybackService", 3))
      Log.d("SkyjamPlaybackService", "error: what=" + paramInt1 + ", extra=" + paramInt2);
    stop();
    stopSelf();
    return true;
  }

  public final void onOperationComplete(HttpOperation paramHttpOperation)
  {
    if ((!paramHttpOperation.hasError()) && (this.mMediaPlayer != null))
    {
      int i = 0;
      String str1 = null;
      String str2 = null;
      try
      {
        String str3 = paramHttpOperation.getOutputStream().toString();
        if (EsLog.isLoggable("SkyjamPlaybackService", 3))
          Log.d("SkyjamPlaybackService", "Received server response: " + str3);
        JsonReader localJsonReader = new JsonReader(new StringReader(str3));
        localJsonReader.beginObject();
        while (localJsonReader.hasNext())
        {
          String str4 = localJsonReader.nextName();
          if (str4.equals("durationMillis"))
            i = localJsonReader.nextInt();
          else if (str4.equals("playType"))
            str1 = localJsonReader.nextString().toLowerCase();
          else if (str4.equals("url"))
            str2 = localJsonReader.nextString();
          else
            localJsonReader.skipValue();
        }
        localJsonReader.endObject();
        if ((str1 == null) || (str1.equals("full")) || (!str1.endsWith("sp")));
        for (sTotalPlayableTime = i; ; sTotalPlayableTime = 1000 * Integer.parseInt(str1.substring(0, -2 + str1.length())))
        {
          if (EsLog.isLoggable("SkyjamPlaybackService", 3))
            Log.d("SkyjamPlaybackService", "Total playable time set to " + sTotalPlayableTime + " ms");
          try
          {
            if (EsLog.isLoggable("SkyjamPlaybackService", 3))
              Log.d("SkyjamPlaybackService", "play");
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setLooping(false);
            this.mMediaPlayer.setDataSource(str2);
            this.mMediaPlayer.prepareAsync();
            sStatus = getString(R.string.skyjam_status_buffering);
            dispatchStatusUpdate();
          }
          catch (IOException localIOException2)
          {
            stop();
          }
        }
      }
      catch (IOException localIOException1)
      {
      }
    }
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    if (EsLog.isLoggable("SkyjamPlaybackService", 3))
      Log.d("SkyjamPlaybackService", "prepared");
    if (paramMediaPlayer == this.mMediaPlayer)
    {
      this.mMediaPlayer.start();
      int i = R.string.skyjam_status_playing;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = getTimeString(sCurrentTime);
      arrayOfObject1[1] = getTimeString(sTotalPlayableTime);
      sStatus = getString(i, arrayOfObject1);
      dispatchStatusUpdate();
      this.mHandler.postDelayed(this.mUpdateTimeRunnable, 1000L);
      int j = R.string.skyjam_notification_playing_song;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = sSongName;
      String str1 = getString(j, arrayOfObject2);
      String str2 = getString(R.string.skyjam_notification_playing_song_title);
      int k = R.string.skyjam_notification_playing_song_subtitle;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = sSongName;
      String str3 = getString(k, arrayOfObject3);
      Notification localNotification = new Notification(R.mipmap.icon, str1, System.currentTimeMillis());
      localNotification.setLatestEventInfo(this, str2, str3, PendingIntent.getActivity(this, 0, Intents.getPostCommentsActivityIntent(this, sAccount, sActivityId), 134217728));
      localNotification.flags = (0x2 | localNotification.flags);
      this.mNotificationManager.notify(27312, localNotification);
    }
  }

  public final void onServiceThreadEnd()
  {
    stop();
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (paramIntent != null)
      this.mServiceThread.put(paramIntent);
    return 1;
  }

  public final void processIntent(Intent paramIntent)
  {
    String str1 = paramIntent.getAction();
    if (str1 == null);
    while (true)
    {
      return;
      if (str1.equals("com.google.android.apps.plus.service.SkyjamPlaybackService.PLAY"))
      {
        String str3 = paramIntent.getStringExtra("music_url");
        if ((str3 != null) && (!str3.equals(sMusicUrl)))
        {
          if (sMusicUrl != null)
            stop();
          sAccount = (EsAccount)paramIntent.getParcelableExtra("music_account");
          sMusicUrl = paramIntent.getStringExtra("music_url");
          sSongName = paramIntent.getStringExtra("song");
          sActivityId = paramIntent.getStringExtra("activity_id");
          this.mMediaPlayer = new MediaPlayer();
          this.mMediaPlayer.setOnBufferingUpdateListener(this);
          this.mMediaPlayer.setOnCompletionListener(this);
          this.mMediaPlayer.setOnErrorListener(this);
          this.mMediaPlayer.setOnPreparedListener(this);
          sStatus = getString(R.string.skyjam_status_connecting);
          dispatchStatusUpdate();
          new HttpOperation(this, "GET", sMusicUrl, new DefaultHttpRequestConfiguration(this, sAccount, "sj"), sAccount, new ByteArrayOutputStream(2048), null, this).startThreaded();
        }
      }
      else if (str1.equals("com.google.android.apps.plus.service.SkyjamPlaybackService.STOP"))
      {
        String str2 = paramIntent.getStringExtra("music_url");
        if ((str2 != null) && (str2.equals(sMusicUrl)))
        {
          stop();
          stopSelf();
        }
      }
    }
  }

  public static abstract interface SkyjamPlaybackListener
  {
    public abstract void onPlaybackStatusUpdate(String paramString1, boolean paramBoolean, String paramString2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.SkyjamPlaybackService
 * JD-Core Version:    0.6.2
 */