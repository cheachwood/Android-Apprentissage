package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;

public final class NotificationCompat
{
  private static final NotificationCompatImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 11);
    for (IMPL = new NotificationCompatImplHoneycomb(); ; IMPL = new NotificationCompatImplBase())
      return;
  }

  public static final class Builder
  {
    PendingIntent mContentIntent;
    CharSequence mContentText;
    CharSequence mContentTitle;
    Context mContext;
    Notification mNotification = new Notification();

    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.mNotification.when = System.currentTimeMillis();
      this.mNotification.audioStreamType = -1;
    }

    public final Notification getNotification()
    {
      return NotificationCompat.IMPL.getNotification(this);
    }

    public final Builder setAutoCancel(boolean paramBoolean)
    {
      Notification localNotification = this.mNotification;
      localNotification.flags = (0x10 | localNotification.flags);
      return this;
    }

    public final Builder setContentIntent(PendingIntent paramPendingIntent)
    {
      this.mContentIntent = paramPendingIntent;
      return this;
    }

    public final Builder setContentText(CharSequence paramCharSequence)
    {
      this.mContentText = paramCharSequence;
      return this;
    }

    public final Builder setContentTitle(CharSequence paramCharSequence)
    {
      this.mContentTitle = paramCharSequence;
      return this;
    }

    public final Builder setSmallIcon(int paramInt)
    {
      this.mNotification.icon = paramInt;
      return this;
    }

    public final Builder setTicker(CharSequence paramCharSequence)
    {
      this.mNotification.tickerText = paramCharSequence;
      return this;
    }
  }

  static abstract interface NotificationCompatImpl
  {
    public abstract Notification getNotification(NotificationCompat.Builder paramBuilder);
  }

  static final class NotificationCompatImplBase
    implements NotificationCompat.NotificationCompatImpl
  {
    public final Notification getNotification(NotificationCompat.Builder paramBuilder)
    {
      Notification localNotification = paramBuilder.mNotification;
      localNotification.setLatestEventInfo(paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent);
      return localNotification;
    }
  }

  static final class NotificationCompatImplHoneycomb
    implements NotificationCompat.NotificationCompatImpl
  {
    public final Notification getNotification(NotificationCompat.Builder paramBuilder)
    {
      boolean bool1 = true;
      Context localContext = paramBuilder.mContext;
      Notification localNotification = paramBuilder.mNotification;
      CharSequence localCharSequence1 = paramBuilder.mContentTitle;
      CharSequence localCharSequence2 = paramBuilder.mContentText;
      PendingIntent localPendingIntent = paramBuilder.mContentIntent;
      Notification.Builder localBuilder1 = new Notification.Builder(localContext).setWhen(localNotification.when).setSmallIcon(localNotification.icon, localNotification.iconLevel).setContent(localNotification.contentView).setTicker(localNotification.tickerText, null).setSound(localNotification.sound, localNotification.audioStreamType).setVibrate(localNotification.vibrate).setLights(localNotification.ledARGB, localNotification.ledOnMS, localNotification.ledOffMS);
      boolean bool2;
      boolean bool3;
      label154: boolean bool4;
      label177: Notification.Builder localBuilder4;
      if ((0x2 & localNotification.flags) != 0)
      {
        bool2 = bool1;
        Notification.Builder localBuilder2 = localBuilder1.setOngoing(bool2);
        if ((0x8 & localNotification.flags) == 0)
          break label258;
        bool3 = bool1;
        Notification.Builder localBuilder3 = localBuilder2.setOnlyAlertOnce(bool3);
        if ((0x10 & localNotification.flags) == 0)
          break label264;
        bool4 = bool1;
        localBuilder4 = localBuilder3.setAutoCancel(bool4).setDefaults(localNotification.defaults).setContentTitle(localCharSequence1).setContentText(localCharSequence2).setContentInfo(null).setContentIntent(localPendingIntent).setDeleteIntent(localNotification.deleteIntent);
        if ((0x80 & localNotification.flags) == 0)
          break label270;
      }
      while (true)
      {
        return localBuilder4.setFullScreenIntent(null, bool1).setLargeIcon(null).setNumber(0).getNotification();
        bool2 = false;
        break;
        label258: bool3 = false;
        break label154;
        label264: bool4 = false;
        break label177;
        label270: bool1 = false;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.NotificationCompat
 * JD-Core Version:    0.6.2
 */