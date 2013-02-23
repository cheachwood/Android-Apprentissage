package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class ConnectionState
  implements Parcelable
{
  public static final Parcelable.Creator<ConnectionState> CREATOR = new Parcelable.Creator()
  {
  };
  private volatile int mState;

  public ConnectionState(Parcel paramParcel)
  {
    this.mState = paramParcel.readInt();
  }

  public final int describeContents()
  {
    return 0;
  }

  public final String toString()
  {
    String str;
    switch (this.mState)
    {
    default:
      str = "IDLE";
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return str;
      str = "RECONNECTION_SCHEDULED";
      continue;
      str = "CONNECTING";
      continue;
      str = "AUTHENTICATED";
      continue;
      str = "ONLINE";
    }
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mState);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gtalkservice.ConnectionState
 * JD-Core Version:    0.6.2
 */