package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.apps.plus.R.string;
import java.io.Serializable;
import java.util.Comparator;

public final class MeetingMember
  implements Serializable
{
  private static String BIG_NASTY_GAIA_ID_PREFIX = "g:";
  private static boolean isAnonymousMuc = false;
  private static final long serialVersionUID = 1717157811110988432L;
  private Status currentStatus;
  private final int entryOrder;
  private final String gaiaId;
  private boolean isMediaBlocked;
  private final boolean isSelf;
  private final boolean isSelfProfile;
  private boolean isVideoPaused;
  private final String memberMucJid;
  private final String nickName;
  private Status previousStatus;
  private VCard vCard;

  public MeetingMember(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.memberMucJid = paramString1;
    this.nickName = paramString2;
    this.previousStatus = Status.DISCONNECTED;
    this.currentStatus = Status.DISCONNECTED;
    this.gaiaId = paramString3;
    this.entryOrder = paramInt;
    this.isSelf = paramBoolean1;
    this.isSelfProfile = paramBoolean2;
  }

  public final Status getCurrentStatus()
  {
    return this.currentStatus;
  }

  public final String getId()
  {
    return BIG_NASTY_GAIA_ID_PREFIX + this.gaiaId;
  }

  public final String getMucJid()
  {
    return this.memberMucJid;
  }

  final String getName(Context paramContext)
  {
    String str;
    if (this.vCard == null)
      if (isAnonymousMuc)
        str = paramContext.getResources().getString(R.string.hangout_anonymous_person);
    while (true)
    {
      return str;
      str = this.nickName;
      continue;
      str = this.vCard.getFullName();
    }
  }

  public final Status getPreviousStatus()
  {
    return this.previousStatus;
  }

  public final VCard getVCard()
  {
    return this.vCard;
  }

  public final boolean isMediaBlocked()
  {
    return this.isMediaBlocked;
  }

  public final boolean isSelf()
  {
    return this.isSelf;
  }

  public final boolean isSelfProfile()
  {
    return this.isSelfProfile;
  }

  public final boolean isVideoPaused()
  {
    return this.isVideoPaused;
  }

  public final void setCurrentStatus(Status paramStatus)
  {
    if (this.currentStatus == paramStatus);
    while (true)
    {
      return;
      this.previousStatus = this.currentStatus;
      this.currentStatus = paramStatus;
    }
  }

  public final void setMediaBlocked(boolean paramBoolean)
  {
    this.isMediaBlocked = true;
  }

  final void setVCard(VCard paramVCard)
  {
    this.vCard = paramVCard;
  }

  public final void setVideoPaused(boolean paramBoolean)
  {
    this.isVideoPaused = paramBoolean;
  }

  static final class SortByEntryOrder
    implements Comparator<MeetingMember>
  {
  }

  public static enum Status
  {
    static
    {
      CONNECTING = new Status("CONNECTING", 1);
      CONNECTED = new Status("CONNECTED", 2);
      Status[] arrayOfStatus = new Status[3];
      arrayOfStatus[0] = DISCONNECTED;
      arrayOfStatus[1] = CONNECTING;
      arrayOfStatus[2] = CONNECTED;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.MeetingMember
 * JD-Core Version:    0.6.2
 */