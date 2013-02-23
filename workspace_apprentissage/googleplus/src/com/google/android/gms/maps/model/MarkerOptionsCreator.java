package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class MarkerOptionsCreator
  implements Parcelable.Creator<MarkerOptions>
{
  static void a(MarkerOptions paramMarkerOptions, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramMarkerOptions.getVersionCode());
    ac.a(paramParcel, 2, paramMarkerOptions.getPosition(), paramInt);
    ac.a(paramParcel, 3, paramMarkerOptions.getTitle());
    ac.a(paramParcel, 4, paramMarkerOptions.getSnippet());
    ac.a(paramParcel, 5, paramMarkerOptions.e());
    ac.a(paramParcel, 6, paramMarkerOptions.getAnchorU());
    ac.a(paramParcel, 7, paramMarkerOptions.getAnchorV());
    ac.a(paramParcel, 8, paramMarkerOptions.isDraggable());
    ac.a(paramParcel, 9, paramMarkerOptions.isVisible());
    ac.c(paramParcel, i);
  }

  public static MarkerOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool1 = false;
    IBinder localIBinder = null;
    int i = bm.g(paramParcel);
    boolean bool2 = false;
    float f2 = 0.0F;
    String str1 = null;
    String str2 = null;
    LatLng localLatLng = null;
    int j = 0;
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
        localLatLng = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 3:
        str2 = bm.l(paramParcel, k);
        break;
      case 4:
        str1 = bm.l(paramParcel, k);
        break;
      case 5:
        localIBinder = bm.m(paramParcel, k);
        break;
      case 6:
        f2 = bm.j(paramParcel, k);
        break;
      case 7:
        f1 = bm.j(paramParcel, k);
        break;
      case 8:
        bool2 = bm.f(paramParcel, k);
        break;
      case 9:
        bool1 = bm.f(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new MarkerOptions(j, localLatLng, str2, str1, localIBinder, f2, f1, bool2, bool1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.MarkerOptionsCreator
 * JD-Core Version:    0.6.2
 */