package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class CameraPositionCreator
  implements Parcelable.Creator<CameraPosition>
{
  static void a(CameraPosition paramCameraPosition, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramCameraPosition.getVersionCode());
    ac.a(paramParcel, 2, paramCameraPosition.target, paramInt);
    ac.a(paramParcel, 3, paramCameraPosition.zoom);
    ac.a(paramParcel, 4, paramCameraPosition.tilt);
    ac.a(paramParcel, 5, paramCameraPosition.bearing);
    ac.c(paramParcel, i);
  }

  public static CameraPosition createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    int i = bm.g(paramParcel);
    int j = 0;
    LatLng localLatLng = null;
    float f2 = 0.0F;
    float f3 = 0.0F;
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
        f3 = bm.j(paramParcel, k);
        break;
      case 4:
        f2 = bm.j(paramParcel, k);
        break;
      case 5:
        f1 = bm.j(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new CameraPosition(j, localLatLng, f3, f2, f1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.CameraPositionCreator
 * JD-Core Version:    0.6.2
 */