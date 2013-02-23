package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface o extends IInterface
{
  public abstract f a(z paramz)
    throws RemoteException;

  public abstract f b(z paramz)
    throws RemoteException;

  public static abstract class a extends Binder
    implements o
  {
    public static o g(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null)
        localObject = null;
      while (true)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
        if ((localIInterface != null) && ((localIInterface instanceof o)))
          localObject = (o)localIInterface;
        else
          localObject = new a(paramIBinder);
      }
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      boolean bool;
      switch (paramInt1)
      {
      default:
        bool = super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
      case 1:
      case 2:
      }
      while (true)
      {
        return bool;
        paramParcel2.writeString("com.google.android.gms.maps.internal.IInfoWindowAdapter");
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
        f localf2 = a(z.a.s(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        IBinder localIBinder2 = null;
        if (localf2 != null)
          localIBinder2 = localf2.asBinder();
        paramParcel2.writeStrongBinder(localIBinder2);
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
        f localf1 = b(z.a.s(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        IBinder localIBinder1 = null;
        if (localf1 != null)
          localIBinder1 = localf1.asBinder();
        paramParcel2.writeStrongBinder(localIBinder1);
        bool = true;
      }
    }

    private static final class a
      implements o
    {
      private IBinder a;

      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }

      public final f a(z paramz)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
          if (paramz != null)
          {
            localIBinder = paramz.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            f localf = f.a.E(localParcel2.readStrongBinder());
            return localf;
          }
          IBinder localIBinder = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final IBinder asBinder()
      {
        return this.a;
      }

      public final f b(z paramz)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
          if (paramz != null)
          {
            localIBinder = paramz.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            f localf = f.a.E(localParcel2.readStrongBinder());
            return localf;
          }
          IBinder localIBinder = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.o
 * JD-Core Version:    0.6.2
 */