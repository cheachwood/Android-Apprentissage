package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bb.a;
import java.util.Arrays;

public final class VisibleRegion
  implements al
{
  public static final VisibleRegionCreator CREATOR = new VisibleRegionCreator();
  public final LatLng farLeft;
  public final LatLng farRight;
  public final LatLngBounds latLngBounds;
  private final int mVersionCode;
  public final LatLng nearLeft;
  public final LatLng nearRight;

  VisibleRegion(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2, LatLng paramLatLng3, LatLng paramLatLng4, LatLngBounds paramLatLngBounds)
  {
    this.mVersionCode = paramInt;
    this.nearLeft = paramLatLng1;
    this.nearRight = paramLatLng2;
    this.farLeft = paramLatLng3;
    this.farRight = paramLatLng4;
    this.latLngBounds = paramLatLngBounds;
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
      if (!(paramObject instanceof VisibleRegion))
      {
        bool = false;
      }
      else
      {
        VisibleRegion localVisibleRegion = (VisibleRegion)paramObject;
        if ((!this.nearLeft.equals(localVisibleRegion.nearLeft)) || (!this.nearRight.equals(localVisibleRegion.nearRight)) || (!this.farLeft.equals(localVisibleRegion.farLeft)) || (!this.farRight.equals(localVisibleRegion.farRight)) || (!this.latLngBounds.equals(localVisibleRegion.latLngBounds)))
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
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = this.nearLeft;
    arrayOfObject[1] = this.nearRight;
    arrayOfObject[2] = this.farLeft;
    arrayOfObject[3] = this.farRight;
    arrayOfObject[4] = this.latLngBounds;
    return Arrays.hashCode(arrayOfObject);
  }

  public final String toString()
  {
    return bb.d(this).a("nearLeft", this.nearLeft).a("nearRight", this.nearRight).a("farLeft", this.farLeft).a("farRight", this.farRight).a("latLngBounds", this.latLngBounds).toString();
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    VisibleRegionCreator.a(this, paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.VisibleRegion
 * JD-Core Version:    0.6.2
 */