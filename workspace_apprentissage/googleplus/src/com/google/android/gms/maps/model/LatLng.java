package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.al;

public final class LatLng
  implements al
{
  public static final LatLngCreator CREATOR = new LatLngCreator();
  public final double latitude;
  public final double longitude;
  private final int mVersionCode;

  public LatLng(double paramDouble1, double paramDouble2)
  {
    this(1, paramDouble1, paramDouble2);
  }

  LatLng(int paramInt, double paramDouble1, double paramDouble2)
  {
    this.mVersionCode = paramInt;
    if ((-180.0D <= paramDouble2) && (paramDouble2 < 180.0D));
    for (this.longitude = paramDouble2; ; this.longitude = ((360.0D + (paramDouble2 - 180.0D) % 360.0D) % 360.0D - 180.0D))
    {
      this.latitude = Math.max(-90.0D, Math.min(90.0D, paramDouble1));
      return;
    }
  }

  public final int describeContents()
  {
    return 0;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject);
    while (true)
    {
      return bool;
      if (!(paramObject instanceof LatLng))
      {
        bool = false;
      }
      else
      {
        LatLng localLatLng = (LatLng)paramObject;
        if ((Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(localLatLng.latitude)) || (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(localLatLng.longitude)))
          bool = false;
      }
    }
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
  }

  public final int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.latitude);
    int i = 31 + (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.longitude);
    return i * 31 + (int)(l2 ^ l2 >>> 32);
  }

  public final String toString()
  {
    return "lat/lng: (" + this.latitude + "," + this.longitude + ")";
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LatLngCreator.a$7afccc40(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.LatLng
 * JD-Core Version:    0.6.2
 */