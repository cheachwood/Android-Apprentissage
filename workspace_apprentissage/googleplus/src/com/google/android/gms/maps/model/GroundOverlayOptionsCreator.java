package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class GroundOverlayOptionsCreator
  implements Parcelable.Creator<GroundOverlayOptions>
{
  static void a(GroundOverlayOptions paramGroundOverlayOptions, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramGroundOverlayOptions.getVersionCode());
    ac.a(paramParcel, 2, paramGroundOverlayOptions.ag());
    ac.a(paramParcel, 3, paramGroundOverlayOptions.getLocation(), paramInt);
    ac.a(paramParcel, 4, paramGroundOverlayOptions.getWidth());
    ac.a(paramParcel, 5, paramGroundOverlayOptions.getHeight());
    ac.a(paramParcel, 6, paramGroundOverlayOptions.getBounds(), paramInt);
    ac.a(paramParcel, 7, paramGroundOverlayOptions.getBearing());
    ac.a(paramParcel, 8, paramGroundOverlayOptions.getZIndex());
    ac.a(paramParcel, 9, paramGroundOverlayOptions.isVisible());
    ac.a(paramParcel, 10, paramGroundOverlayOptions.getTransparency());
    ac.a(paramParcel, 11, paramGroundOverlayOptions.getAnchorU());
    ac.a(paramParcel, 12, paramGroundOverlayOptions.getAnchorV());
    ac.c(paramParcel, i);
  }

  public static GroundOverlayOptions createFromParcel(Parcel paramParcel)
  {
    boolean bool = false;
    LatLngBounds localLatLngBounds = null;
    float f1 = 0.0F;
    int i = bm.g(paramParcel);
    float f2 = 0.0F;
    float f3 = 0.0F;
    float f4 = 0.0F;
    float f5 = 0.0F;
    float f6 = 0.0F;
    float f7 = 0.0F;
    LatLng localLatLng = null;
    IBinder localIBinder = null;
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
        localIBinder = bm.m(paramParcel, k);
        break;
      case 3:
        localLatLng = (LatLng)bm.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 4:
        f7 = bm.j(paramParcel, k);
        break;
      case 5:
        f6 = bm.j(paramParcel, k);
        break;
      case 6:
        localLatLngBounds = (LatLngBounds)bm.a(paramParcel, k, LatLngBounds.CREATOR);
        break;
      case 7:
        f5 = bm.j(paramParcel, k);
        break;
      case 8:
        f4 = bm.j(paramParcel, k);
        break;
      case 9:
        bool = bm.f(paramParcel, k);
        break;
      case 10:
        f3 = bm.j(paramParcel, k);
        break;
      case 11:
        f2 = bm.j(paramParcel, k);
        break;
      case 12:
        f1 = bm.j(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new GroundOverlayOptions(j, localIBinder, localLatLng, f7, f6, localLatLngBounds, f5, f4, bool, f3, f2, f1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.GroundOverlayOptionsCreator
 * JD-Core Version:    0.6.2
 */