package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class ConnectionError
  implements Parcelable
{
  public static final Parcelable.Creator<ConnectionError> CREATOR = new Parcelable.Creator()
  {
  };
  private int mError;

  public ConnectionError(Parcel paramParcel)
  {
    this.mError = paramParcel.readInt();
  }

  public final int describeContents()
  {
    return 0;
  }

  public final String toString()
  {
    String str;
    switch (this.mError)
    {
    case 9:
    default:
      str = "NO ERROR";
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 10:
    }
    while (true)
    {
      return str;
      str = "NO NETWORK";
      continue;
      str = "CONNECTION FAILED";
      continue;
      str = "UNKNOWN HOST";
      continue;
      str = "AUTH FAILED";
      continue;
      str = "AUTH EXPIRED";
      continue;
      str = "HEARTBEAT TIMEOUT";
      continue;
      str = "SERVER FAILED";
      continue;
      str = "SERVER REJECT - RATE LIMIT";
      continue;
      str = "UNKNOWN";
    }
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mError);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gtalkservice.ConnectionError
 * JD-Core Version:    0.6.2
 */