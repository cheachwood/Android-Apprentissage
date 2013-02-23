package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class LatLngCreator
  implements Parcelable.Creator<LatLng>
{
  static void a$7afccc40(LatLng paramLatLng, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramLatLng.getVersionCode());
    ac.a(paramParcel, 2, paramLatLng.latitude);
    ac.a(paramParcel, 3, paramLatLng.longitude);
    ac.c(paramParcel, i);
  }

  public static LatLng createFromParcel(Parcel paramParcel)
  {
    double d1 = 0.0D;
    int i = bm.g(paramParcel);
    int j = 0;
    double d2 = d1;
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
        d2 = bm.k(paramParcel, k);
        break;
      case 3:
        d1 = bm.k(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new LatLng(j, d2, d1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.LatLngCreator
 * JD-Core Version:    0.6.2
 */