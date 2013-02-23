package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class TileCreator
  implements Parcelable.Creator<Tile>
{
  static void a$2bae1718(Tile paramTile, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramTile.getVersionCode());
    ac.b(paramParcel, 2, paramTile.width);
    ac.b(paramParcel, 3, paramTile.height);
    ac.a(paramParcel, 4, paramTile.cm);
    ac.c(paramParcel, i);
  }

  public static Tile createFromParcel(Parcel paramParcel)
  {
    int i = 0;
    int j = bm.g(paramParcel);
    byte[] arrayOfByte = null;
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = paramParcel.readInt();
      switch (0xFFFF & n)
      {
      default:
        bm.e(paramParcel, n);
        break;
      case 1:
        m = bm.h(paramParcel, n);
        break;
      case 2:
        k = bm.h(paramParcel, n);
        break;
      case 3:
        i = bm.h(paramParcel, n);
        break;
      case 4:
        int i1 = bm.d(paramParcel, n);
        int i2 = paramParcel.dataPosition();
        arrayOfByte = paramParcel.createByteArray();
        paramParcel.setDataPosition(i1 + i2);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new bm.a("Overread allowed size end=" + j, paramParcel);
    return new Tile(m, k, i, arrayOfByte);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.TileCreator
 * JD-Core Version:    0.6.2
 */