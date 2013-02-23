package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.al;

public final class Tile
  implements al
{
  public static final TileCreator CREATOR = new TileCreator();
  public final byte[] cm;
  public final int height;
  private final int mVersionCode;
  public final int width;

  Tile(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    this.mVersionCode = paramInt1;
    this.width = paramInt2;
    this.height = paramInt3;
    this.cm = paramArrayOfByte;
  }

  public Tile(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    this(1, -1, -1, null);
  }

  public final int describeContents()
  {
    return 0;
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    TileCreator.a$2bae1718(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.Tile
 * JD-Core Version:    0.6.2
 */