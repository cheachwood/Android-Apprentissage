package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public final class bm
{
  public static <T extends Parcelable> T a(Parcel paramParcel, int paramInt, Parcelable.Creator<T> paramCreator)
  {
    int i = d(paramParcel, paramInt);
    int j = paramParcel.dataPosition();
    Parcelable localParcelable = (Parcelable)paramCreator.createFromParcel(paramParcel);
    paramParcel.setDataPosition(i + j);
    return localParcelable;
  }

  public static <T> List<T> b(Parcel paramParcel, int paramInt, Parcelable.Creator<T> paramCreator)
  {
    int i = d(paramParcel, paramInt);
    int j = paramParcel.dataPosition();
    ArrayList localArrayList = paramParcel.createTypedArrayList(paramCreator);
    paramParcel.setDataPosition(i + j);
    return localArrayList;
  }

  private static void c(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    int i = d(paramParcel, paramInt1);
    if (i != paramInt2)
      throw new a("Expected size 4 got " + i + " (0x" + Integer.toHexString(i) + ")", paramParcel);
  }

  public static int d(Parcel paramParcel, int paramInt)
  {
    if ((paramInt & 0xFFFF0000) != -65536);
    for (int i = 0xFFFF & paramInt >> 16; ; i = paramParcel.readInt())
      return i;
  }

  public static void e(Parcel paramParcel, int paramInt)
  {
    paramParcel.setDataPosition(paramParcel.dataPosition() + d(paramParcel, paramInt));
  }

  public static boolean f(Parcel paramParcel, int paramInt)
  {
    c(paramParcel, paramInt, 4);
    if (paramParcel.readInt() != 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static byte g(Parcel paramParcel, int paramInt)
  {
    c(paramParcel, paramInt, 4);
    return (byte)paramParcel.readInt();
  }

  public static int g(Parcel paramParcel)
  {
    int i = paramParcel.readInt();
    int j = d(paramParcel, i);
    int k = paramParcel.dataPosition();
    if ((0xFFFF & i) != 20293)
      throw new a("Expected object header. Got 0x" + Integer.toHexString(i), paramParcel);
    int m = k + j;
    if ((m < k) || (m > paramParcel.dataSize()))
      throw new a("Size read is invalid start=" + k + " end=" + m, paramParcel);
    return m;
  }

  public static int h(Parcel paramParcel, int paramInt)
  {
    c(paramParcel, paramInt, 4);
    return paramParcel.readInt();
  }

  public static long i(Parcel paramParcel, int paramInt)
  {
    c(paramParcel, paramInt, 8);
    return paramParcel.readLong();
  }

  public static float j(Parcel paramParcel, int paramInt)
  {
    c(paramParcel, paramInt, 4);
    return paramParcel.readFloat();
  }

  public static double k(Parcel paramParcel, int paramInt)
  {
    c(paramParcel, paramInt, 8);
    return paramParcel.readDouble();
  }

  public static String l(Parcel paramParcel, int paramInt)
  {
    int i = d(paramParcel, paramInt);
    int j = paramParcel.dataPosition();
    String str = paramParcel.readString();
    paramParcel.setDataPosition(i + j);
    return str;
  }

  public static IBinder m(Parcel paramParcel, int paramInt)
  {
    int i = d(paramParcel, paramInt);
    int j = paramParcel.dataPosition();
    IBinder localIBinder = paramParcel.readStrongBinder();
    paramParcel.setDataPosition(i + j);
    return localIBinder;
  }

  public static final class a extends RuntimeException
  {
    public a(String paramString, Parcel paramParcel)
    {
      super();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bm
 * JD-Core Version:    0.6.2
 */