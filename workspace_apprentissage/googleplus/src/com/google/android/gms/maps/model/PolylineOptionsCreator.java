package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;
import java.util.List;

public final class PolylineOptionsCreator
  implements Parcelable.Creator<PolylineOptions>
{
  static void a$37f6d9f8(PolylineOptions paramPolylineOptions, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramPolylineOptions.getVersionCode());
    ac.a(paramParcel, 2, paramPolylineOptions.getPoints());
    ac.a(paramParcel, 3, paramPolylineOptions.getWidth());
    ac.b(paramParcel, 4, paramPolylineOptions.getColor());
    ac.a(paramParcel, 5, paramPolylineOptions.getZIndex());
    ac.a(paramParcel, 6, paramPolylineOptions.isVisible());
    ac.a(paramParcel, 7, paramPolylineOptions.isGeodesic());
    ac.c(paramParcel, i);
  }

  public static PolylineOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool1 = false;
    int i = bm.g(paramParcel);
    List localList = null;
    boolean bool2 = false;
    int j = 0;
    float f2 = 0.0F;
    int k = 0;
    while (paramParcel.dataPosition() < i)
    {
      int m = paramParcel.readInt();
      switch (0xFFFF & m)
      {
      default:
        bm.e(paramParcel, m);
        break;
      case 1:
        k = bm.h(paramParcel, m);
        break;
      case 2:
        localList = bm.b(paramParcel, m, LatLng.CREATOR);
        break;
      case 3:
        f2 = bm.j(paramParcel, m);
        break;
      case 4:
        j = bm.h(paramParcel, m);
        break;
      case 5:
        f1 = bm.j(paramParcel, m);
        break;
      case 6:
        bool2 = bm.f(paramParcel, m);
        break;
      case 7:
        bool1 = bm.f(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new PolylineOptions(k, localList, f2, j, f1, bool2, bool1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolylineOptionsCreator
 * JD-Core Version:    0.6.2
 */