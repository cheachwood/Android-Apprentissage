package com.google.android.apps.plus.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParticipantParcelable
  implements Parcelable
{
  public static final Parcelable.Creator<ParticipantParcelable> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mName;
  private final String mParticipantId;

  private ParticipantParcelable(Parcel paramParcel)
  {
    this.mName = paramParcel.readString();
    this.mParticipantId = paramParcel.readString();
  }

  public ParticipantParcelable(String paramString1, String paramString2)
  {
    this.mName = paramString1;
    this.mParticipantId = paramString2;
  }

  public int describeContents()
  {
    return 0;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final String getParticipantId()
  {
    return this.mParticipantId;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(64);
    localStringBuilder.append("Name: ").append(this.mName);
    localStringBuilder.append("ParticipantId: ").append(this.mParticipantId);
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mParticipantId);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.ParticipantParcelable
 * JD-Core Version:    0.6.2
 */