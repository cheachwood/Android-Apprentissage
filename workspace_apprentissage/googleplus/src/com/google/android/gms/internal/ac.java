package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public final class ac
{
  private static int a(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(0xFFFF0000 | paramInt);
    paramParcel.writeInt(0);
    return paramParcel.dataPosition();
  }

  public static void a(Parcel paramParcel, int paramInt, byte paramByte)
  {
    a(paramParcel, paramInt, 4);
    paramParcel.writeInt(paramByte);
  }

  public static void a(Parcel paramParcel, int paramInt, double paramDouble)
  {
    a(paramParcel, paramInt, 8);
    paramParcel.writeDouble(paramDouble);
  }

  public static void a(Parcel paramParcel, int paramInt, float paramFloat)
  {
    a(paramParcel, paramInt, 4);
    paramParcel.writeFloat(paramFloat);
  }

  private static void a(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    if (paramInt2 >= 65535)
    {
      paramParcel.writeInt(0xFFFF0000 | paramInt1);
      paramParcel.writeInt(paramInt2);
    }
    while (true)
    {
      return;
      paramParcel.writeInt(paramInt1 | paramInt2 << 16);
    }
  }

  public static void a(Parcel paramParcel, int paramInt, long paramLong)
  {
    a(paramParcel, paramInt, 8);
    paramParcel.writeLong(paramLong);
  }

  public static void a(Parcel paramParcel, int paramInt, IBinder paramIBinder)
  {
    if (paramIBinder != null)
    {
      int i = a(paramParcel, paramInt);
      paramParcel.writeStrongBinder(paramIBinder);
      b(paramParcel, i);
    }
  }

  public static void a(Parcel paramParcel, int paramInt1, Parcelable paramParcelable, int paramInt2)
  {
    if (paramParcelable != null)
    {
      int i = a(paramParcel, paramInt1);
      paramParcelable.writeToParcel(paramParcel, paramInt2);
      b(paramParcel, i);
    }
  }

  public static void a(Parcel paramParcel, int paramInt, String paramString)
  {
    if (paramString != null)
    {
      int i = a(paramParcel, paramInt);
      paramParcel.writeString(paramString);
      b(paramParcel, i);
    }
  }

  public static <T extends Parcelable> void a(Parcel paramParcel, int paramInt, List<T> paramList)
  {
    if (paramList != null)
    {
      int i = a(paramParcel, 2);
      paramParcel.writeTypedList(paramList);
      b(paramParcel, i);
    }
  }

  public static void a(Parcel paramParcel, int paramInt, boolean paramBoolean)
  {
    a(paramParcel, paramInt, 4);
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      paramParcel.writeInt(i);
      return;
    }
  }

  public static void a(Parcel paramParcel, int paramInt, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      int i = paramArrayOfByte.length;
      if (paramArrayOfByte != null)
      {
        int j = a(paramParcel, 4);
        paramParcel.writeByteArray(paramArrayOfByte, 0, i);
        b(paramParcel, j);
      }
    }
  }

  public static void a(Parcel paramParcel, int paramInt, String[] paramArrayOfString)
  {
    if (paramArrayOfString != null)
    {
      int i = a(paramParcel, 10);
      paramParcel.writeStringArray(paramArrayOfString);
      b(paramParcel, i);
    }
  }

  public static int b(Parcel paramParcel)
  {
    return a(paramParcel, 20293);
  }

  private static void b(Parcel paramParcel, int paramInt)
  {
    int i = paramParcel.dataPosition();
    int j = i - paramInt;
    paramParcel.setDataPosition(paramInt - 4);
    paramParcel.writeInt(j);
    paramParcel.setDataPosition(i);
  }

  public static void b(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    a(paramParcel, paramInt1, 4);
    paramParcel.writeInt(paramInt2);
  }

  public static void b(Parcel paramParcel, int paramInt, List paramList)
  {
    if (paramList != null)
    {
      int i = a(paramParcel, 3);
      paramParcel.writeList(paramList);
      b(paramParcel, i);
    }
  }

  public static void c(Parcel paramParcel, int paramInt)
  {
    b(paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ac
 * JD-Core Version:    0.6.2
 */