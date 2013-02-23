package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptionsCreator
  implements Parcelable.Creator<GoogleMapOptions>
{
  static void a(GoogleMapOptions paramGoogleMapOptions, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramGoogleMapOptions.getVersionCode());
    ac.a(paramParcel, 2, paramGoogleMapOptions.u());
    ac.a(paramParcel, 3, paramGoogleMapOptions.v());
    ac.b(paramParcel, 4, paramGoogleMapOptions.getMapType());
    ac.a(paramParcel, 5, paramGoogleMapOptions.getCamera(), paramInt);
    ac.a(paramParcel, 6, paramGoogleMapOptions.w());
    ac.a(paramParcel, 7, paramGoogleMapOptions.x());
    ac.a(paramParcel, 8, paramGoogleMapOptions.y());
    ac.a(paramParcel, 9, paramGoogleMapOptions.z());
    ac.a(paramParcel, 10, paramGoogleMapOptions.A());
    ac.a(paramParcel, 11, paramGoogleMapOptions.B());
    ac.c(paramParcel, i);
  }

  public static GoogleMapOptions createFromParcel(Parcel paramParcel)
  {
    byte b1 = 0;
    int i = bm.g(paramParcel);
    CameraPosition localCameraPosition = null;
    byte b2 = 0;
    byte b3 = 0;
    byte b4 = 0;
    byte b5 = 0;
    byte b6 = 0;
    int j = 0;
    byte b7 = 0;
    byte b8 = 0;
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
        b8 = bm.g(paramParcel, m);
        break;
      case 3:
        b7 = bm.g(paramParcel, m);
        break;
      case 4:
        j = bm.h(paramParcel, m);
        break;
      case 5:
        localCameraPosition = (CameraPosition)bm.a(paramParcel, m, CameraPosition.CREATOR);
        break;
      case 6:
        b6 = bm.g(paramParcel, m);
        break;
      case 7:
        b5 = bm.g(paramParcel, m);
        break;
      case 8:
        b4 = bm.g(paramParcel, m);
        break;
      case 9:
        b3 = bm.g(paramParcel, m);
        break;
      case 10:
        b2 = bm.g(paramParcel, m);
        break;
      case 11:
        b1 = bm.g(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new GoogleMapOptions(k, b8, b7, j, localCameraPosition, b6, b5, b4, b3, b2, b1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.GoogleMapOptionsCreator
 * JD-Core Version:    0.6.2
 */