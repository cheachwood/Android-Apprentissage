package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class LatLngBoundsCreator
  implements Parcelable.Creator<LatLngBounds>
{
  static void a(LatLngBounds paramLatLngBounds, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramLatLngBounds.getVersionCode());
    ac.a(paramParcel, 2, paramLatLngBounds.southwest, paramInt);
    ac.a(paramParcel, 3, paramLatLngBounds.northeast, paramInt);
    ac.c(paramParcel, i);
  }

  public static LatLngBounds createFromParcel(Parcel paramParcel)
  {
    int i = bm.g(paramParcel);
    LatLng localLatLng1 = null;
    int j = 0;
    LatLng localLatLng2 = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = paramParcel.readInt();
      switch (0xFFFF & k)
      {
      default:
        bm.e(paramParcel, k);
        break;
      case 1:
        j = bm.h(paramParcel, k);
        break;
      case 2:
        localLatLng1 = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 3:
        localLatLng2 = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new LatLngBounds(j, localLatLng1, localLatLng2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.LatLngBoundsCreator
 * JD-Core Version:    0.6.2
 */