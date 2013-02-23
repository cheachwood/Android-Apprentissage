package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface i extends IInterface
{
  public abstract f a(float paramFloat)
    throws RemoteException;

  public abstract f a(Bitmap paramBitmap)
    throws RemoteException;

  public abstract f g(int paramInt)
    throws RemoteException;

  public abstract f g(String paramString)
    throws RemoteException;

  public abstract f h(String paramString)
    throws RemoteException;

  public abstract f t()
    throws RemoteException;

  public static abstract class a extends Binder
    implements i
  {
    public static i c(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null)
        localObject = null;
      while (true)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        if ((localIInterface != null) && ((localIInterface instanceof i)))
          localObject = (i)localIInterface;
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
      case 3:
      case 4:
      case 5:
        while (true)
        {
          return bool;
          paramParcel2.writeString("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          f localf6 = g(paramParcel1.readInt());
          paramParcel2.writeNoException();
          if (localf6 != null);
          for (IBinder localIBinder6 = localf6.asBinder(); ; localIBinder6 = null)
          {
            paramParcel2.writeStrongBinder(localIBinder6);
            bool = true;
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          f localf5 = g(paramParcel1.readString());
          paramParcel2.writeNoException();
          IBinder localIBinder5 = null;
          if (localf5 != null)
            localIBinder5 = localf5.asBinder();
          paramParcel2.writeStrongBinder(localIBinder5);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          f localf4 = h(paramParcel1.readString());
          paramParcel2.writeNoException();
          IBinder localIBinder4 = null;
          if (localf4 != null)
            localIBinder4 = localf4.asBinder();
          paramParcel2.writeStrongBinder(localIBinder4);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          f localf3 = t();
          paramParcel2.writeNoException();
          IBinder localIBinder3 = null;
          if (localf3 != null)
            localIBinder3 = localf3.asBinder();
          paramParcel2.writeStrongBinder(localIBinder3);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          f localf2 = a(paramParcel1.readFloat());
          paramParcel2.writeNoException();
          IBinder localIBinder2 = null;
          if (localf2 != null)
            localIBinder2 = localf2.asBinder();
          paramParcel2.writeStrongBinder(localIBinder2);
          bool = true;
        }
      case 6:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
      if (paramParcel1.readInt() != 0);
      for (Bitmap localBitmap = (Bitmap)Bitmap.CREATOR.createFromParcel(paramParcel1); ; localBitmap = null)
      {
        f localf1 = a(localBitmap);
        paramParcel2.writeNoException();
        IBinder localIBinder1 = null;
        if (localf1 != null)
          localIBinder1 = localf1.asBinder();
        paramParcel2.writeStrongBinder(localIBinder1);
        bool = true;
        break;
      }
    }

    private static final class a
      implements i
    {
      private IBinder a;

      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }

      public final f a(float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeFloat(paramFloat);
          this.a.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          f localf = f.a.E(localParcel2.readStrongBinder());
          return localf;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final f a(Bitmap paramBitmap)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          if (paramBitmap != null)
          {
            localParcel1.writeInt(1);
            paramBitmap.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(6, localParcel1, localParcel2, 0);
            localParcel2.readException();
            f localf = f.a.E(localParcel2.readStrongBinder());
            return localf;
            localParcel1.writeInt(0);
          }
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

      public final f g(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeInt(paramInt);
          this.a.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          f localf = f.a.E(localParcel2.readStrongBinder());
          return localf;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final f g(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeString(paramString);
          this.a.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          f localf = f.a.E(localParcel2.readStrongBinder());
          return localf;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final f h(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeString(paramString);
          this.a.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          f localf = f.a.E(localParcel2.readStrongBinder());
          return localf;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final f t()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          this.a.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          f localf = f.a.E(localParcel2.readStrongBinder());
          return localf;
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
 * Qualified Name:     com.google.android.gms.internal.i
 * JD-Core Version:    0.6.2
 */