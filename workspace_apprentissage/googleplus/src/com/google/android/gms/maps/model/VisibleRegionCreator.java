package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class VisibleRegionCreator
  implements Parcelable.Creator<VisibleRegion>
{
  static void a(VisibleRegion paramVisibleRegion, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramVisibleRegion.getVersionCode());
    ac.a(paramParcel, 2, paramVisibleRegion.nearLeft, paramInt);
    ac.a(paramParcel, 3, paramVisibleRegion.nearRight, paramInt);
    ac.a(paramParcel, 4, paramVisibleRegion.farLeft, paramInt);
    ac.a(paramParcel, 5, paramVisibleRegion.farRight, paramInt);
    ac.a(paramParcel, 6, paramVisibleRegion.latLngBounds, paramInt);
    ac.c(paramParcel, i);
  }

  public static VisibleRegion createFromParcel(Parcel paramParcel)
  {
    LatLngBounds localLatLngBounds = null;
    int i = bm.g(paramParcel);
    int j = 0;
    LatLng localLatLng1 = null;
    LatLng localLatLng2 = null;
    LatLng localLatLng3 = null;
    LatLng localLatLng4 = null;
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
        localLatLng4 = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 3:
        localLatLng3 = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 4:
        localLatLng2 = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 5:
        localLatLng1 = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 6:
        localLatLngBounds = (LatLngBounds)bm.a(paramParcel, k, LatLngBounds.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new VisibleRegion(j, localLatLng4, localLatLng3, localLatLng2, localLatLng1, localLatLngBounds);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.VisibleRegionCreator
 * JD-Core Version:    0.6.2
 */