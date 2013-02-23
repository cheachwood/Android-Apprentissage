package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bb.a;
import com.google.android.gms.internal.bj;
import java.util.Arrays;

public final class LatLngBounds
  implements al
{
  public static final LatLngBoundsCreator CREATOR = new LatLngBoundsCreator();
  private final int mVersionCode;
  public final LatLng northeast;
  public final LatLng southwest;

  LatLngBounds(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2)
  {
    bj.a(paramLatLng1, "null southwest");
    bj.a(paramLatLng2, "null northeast");
    if (paramLatLng2.latitude >= paramLatLng1.latitude);
    for (int i = 1; ; i = 0)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Double.valueOf(paramLatLng1.latitude);
      arrayOfObject[1] = Double.valueOf(paramLatLng2.latitude);
      if (i != 0)
        break;
      throw new IllegalArgumentException(String.format("southern latitude exceeds northern latitude (%s > %s)", arrayOfObject));
    }
    this.mVersionCode = paramInt;
    this.southwest = paramLatLng1;
    this.northeast = paramLatLng2;
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
      if (!(paramObject instanceof LatLngBounds))
      {
        bool = false;
      }
      else
      {
        LatLngBounds localLatLngBounds = (LatLngBounds)paramObject;
        if ((!this.southwest.equals(localLatLngBounds.southwest)) || (!this.northeast.equals(localLatLngBounds.northeast)))
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
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.southwest;
    arrayOfObject[1] = this.northeast;
    return Arrays.hashCode(arrayOfObject);
  }

  public final String toString()
  {
    return bb.d(this).a("southwest", this.southwest).a("northeast", this.northeast).toString();
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LatLngBoundsCreator.a(this, paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.LatLngBounds
 * JD-Core Version:    0.6.2
 */