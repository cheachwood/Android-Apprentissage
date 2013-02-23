package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public final class Presence
  implements Parcelable
{
  public static final Parcelable.Creator<Presence> CREATOR = new Parcelable.Creator()
  {
  };
  public static final Presence OFFLINE = new Presence();
  private boolean mAllowInvisibility;
  private boolean mAvailable;
  private int mCapabilities;
  private List<String> mDefaultStatusList;
  private List<String> mDndStatusList;
  private boolean mInvisible;
  private Show mShow;
  private String mStatus;
  private int mStatusListContentsMax;
  private int mStatusListMax;
  private int mStatusMax;

  public Presence()
  {
    this(false, Show.NONE, null, 8);
  }

  public Presence(Parcel paramParcel)
  {
    this.mStatusMax = paramParcel.readInt();
    this.mStatusListMax = paramParcel.readInt();
    this.mStatusListContentsMax = paramParcel.readInt();
    boolean bool2;
    boolean bool3;
    if (paramParcel.readInt() != 0)
    {
      bool2 = bool1;
      this.mAllowInvisibility = bool2;
      if (paramParcel.readInt() == 0)
        break label159;
      bool3 = bool1;
      label54: this.mAvailable = bool3;
      this.mShow = ((Show)Enum.valueOf(Show.class, paramParcel.readString()));
      this.mStatus = paramParcel.readString();
      if (paramParcel.readInt() == 0)
        break label165;
    }
    while (true)
    {
      this.mInvisible = bool1;
      if ((bool1) && (!this.mAllowInvisibility));
      this.mDefaultStatusList = new ArrayList();
      paramParcel.readStringList(this.mDefaultStatusList);
      this.mDndStatusList = new ArrayList();
      paramParcel.readStringList(this.mDndStatusList);
      this.mCapabilities = paramParcel.readInt();
      return;
      bool2 = false;
      break;
      label159: bool3 = false;
      break label54;
      label165: bool1 = false;
    }
  }

  private Presence(boolean paramBoolean, Show paramShow, String paramString, int paramInt)
  {
    this.mAvailable = false;
    this.mShow = paramShow;
    this.mStatus = null;
    this.mInvisible = false;
    this.mDefaultStatusList = new ArrayList();
    this.mDndStatusList = new ArrayList();
    this.mCapabilities = 8;
  }

  public final int describeContents()
  {
    return 0;
  }

  public final String toString()
  {
    if (!this.mAvailable);
    for (String str = "UNAVAILABLE"; ; str = "INVISIBLE")
    {
      return str;
      if (!this.mInvisible)
        break;
    }
    StringBuilder localStringBuilder = new StringBuilder(40);
    if (this.mShow == Show.NONE)
      localStringBuilder.append("AVAILABLE(x)");
    while (true)
    {
      if ((0x8 & this.mCapabilities) != 0)
        localStringBuilder.append(" pmuc-v1");
      if ((0x1 & this.mCapabilities) != 0)
        localStringBuilder.append(" voice-v1");
      if ((0x2 & this.mCapabilities) != 0)
        localStringBuilder.append(" video-v1");
      if ((0x4 & this.mCapabilities) != 0)
        localStringBuilder.append(" camera-v1");
      str = localStringBuilder.toString();
      break;
      localStringBuilder.append(this.mShow.toString());
    }
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeInt(this.mStatusMax);
    paramParcel.writeInt(this.mStatusListMax);
    paramParcel.writeInt(this.mStatusListContentsMax);
    int j;
    int k;
    if (this.mAllowInvisibility)
    {
      j = i;
      paramParcel.writeInt(j);
      if (!this.mAvailable)
        break label120;
      k = i;
      label52: paramParcel.writeInt(k);
      paramParcel.writeString(this.mShow.toString());
      paramParcel.writeString(this.mStatus);
      if (!this.mInvisible)
        break label126;
    }
    while (true)
    {
      paramParcel.writeInt(i);
      paramParcel.writeStringList(this.mDefaultStatusList);
      paramParcel.writeStringList(this.mDndStatusList);
      paramParcel.writeInt(this.mCapabilities);
      return;
      j = 0;
      break;
      label120: k = 0;
      break label52;
      label126: i = 0;
    }
  }

  public static enum Show
  {
    static
    {
      AWAY = new Show("AWAY", 1);
      EXTENDED_AWAY = new Show("EXTENDED_AWAY", 2);
      DND = new Show("DND", 3);
      AVAILABLE = new Show("AVAILABLE", 4);
      Show[] arrayOfShow = new Show[5];
      arrayOfShow[0] = NONE;
      arrayOfShow[1] = AWAY;
      arrayOfShow[2] = EXTENDED_AWAY;
      arrayOfShow[3] = DND;
      arrayOfShow[4] = AVAILABLE;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gtalkservice.Presence
 * JD-Core Version:    0.6.2
 */