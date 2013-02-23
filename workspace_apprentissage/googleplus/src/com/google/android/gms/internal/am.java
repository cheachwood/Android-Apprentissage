package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.GoogleMapOptionsCreator;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate.a;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate.a;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate.a;

public abstract interface am extends IInterface
{
  public abstract IMapViewDelegate a(f paramf, GoogleMapOptions paramGoogleMapOptions)
    throws RemoteException;

  public abstract void b(f paramf)
    throws RemoteException;

  public abstract IMapFragmentDelegate c(f paramf)
    throws RemoteException;

  public abstract ICameraUpdateFactoryDelegate q()
    throws RemoteException;

  public abstract i r()
    throws RemoteException;

  public static abstract class a extends Binder
    implements am
  {
    public static am n(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null)
        localObject = null;
      while (true)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
        if ((localIInterface != null) && ((localIInterface instanceof am)))
          localObject = (am)localIInterface;
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
      }
      while (true)
      {
        return bool;
        paramParcel2.writeString("com.google.android.gms.maps.internal.ICreator");
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        b(f.a.E(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        IMapFragmentDelegate localIMapFragmentDelegate = c(f.a.E(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        IBinder localIBinder4 = null;
        if (localIMapFragmentDelegate != null)
          localIBinder4 = localIMapFragmentDelegate.asBinder();
        paramParcel2.writeStrongBinder(localIBinder4);
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        f localf = f.a.E(paramParcel1.readStrongBinder());
        if (paramParcel1.readInt() != 0);
        for (GoogleMapOptions localGoogleMapOptions = GoogleMapOptionsCreator.createFromParcel(paramParcel1); ; localGoogleMapOptions = null)
        {
          IMapViewDelegate localIMapViewDelegate = a(localf, localGoogleMapOptions);
          paramParcel2.writeNoException();
          IBinder localIBinder3 = null;
          if (localIMapViewDelegate != null)
            localIBinder3 = localIMapViewDelegate.asBinder();
          paramParcel2.writeStrongBinder(localIBinder3);
          bool = true;
          break;
        }
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        ICameraUpdateFactoryDelegate localICameraUpdateFactoryDelegate = q();
        paramParcel2.writeNoException();
        IBinder localIBinder2 = null;
        if (localICameraUpdateFactoryDelegate != null)
          localIBinder2 = localICameraUpdateFactoryDelegate.asBinder();
        paramParcel2.writeStrongBinder(localIBinder2);
        bool = true;
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        i locali = r();
        paramParcel2.writeNoException();
        IBinder localIBinder1 = null;
        if (locali != null)
          localIBinder1 = locali.asBinder();
        paramParcel2.writeStrongBinder(localIBinder1);
        bool = true;
      }
    }

    private static final class a
      implements am
    {
      private IBinder a;

      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }

      public final IMapViewDelegate a(f paramf, GoogleMapOptions paramGoogleMapOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          IBinder localIBinder;
          if (paramf != null)
          {
            localIBinder = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            if (paramGoogleMapOptions == null)
              break label96;
            localParcel1.writeInt(1);
            paramGoogleMapOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(3, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IMapViewDelegate localIMapViewDelegate = IMapViewDelegate.a.D(localParcel2.readStrongBinder());
            return localIMapViewDelegate;
            localIBinder = null;
            break;
            label96: localParcel1.writeInt(0);
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

      public final void b(f paramf)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          if (paramf != null)
          {
            localIBinder = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
          IBinder localIBinder = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final IMapFragmentDelegate c(f paramf)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          if (paramf != null)
          {
            localIBinder = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IMapFragmentDelegate localIMapFragmentDelegate = IMapFragmentDelegate.a.h(localParcel2.readStrongBinder());
            return localIMapFragmentDelegate;
          }
          IBinder localIBinder = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final ICameraUpdateFactoryDelegate q()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          this.a.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ICameraUpdateFactoryDelegate localICameraUpdateFactoryDelegate = ICameraUpdateFactoryDelegate.a.m(localParcel2.readStrongBinder());
          return localICameraUpdateFactoryDelegate;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final i r()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          this.a.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          i locali = i.a.c(localParcel2.readStrongBinder());
          return locali;
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
 * Qualified Name:     com.google.android.gms.internal.am
 * JD-Core Version:    0.6.2
 */