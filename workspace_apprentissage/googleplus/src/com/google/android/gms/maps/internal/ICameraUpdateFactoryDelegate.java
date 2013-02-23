package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.f;
import com.google.android.gms.internal.f.a;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPositionCreator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.LatLngCreator;

public abstract interface ICameraUpdateFactoryDelegate extends IInterface
{
  public abstract f newCameraPosition(CameraPosition paramCameraPosition)
    throws RemoteException;

  public abstract f newLatLng(LatLng paramLatLng)
    throws RemoteException;

  public abstract f newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt)
    throws RemoteException;

  public abstract f newLatLngBoundsWithSize(LatLngBounds paramLatLngBounds, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract f newLatLngZoom(LatLng paramLatLng, float paramFloat)
    throws RemoteException;

  public abstract f scrollBy(float paramFloat1, float paramFloat2)
    throws RemoteException;

  public abstract f zoomBy(float paramFloat)
    throws RemoteException;

  public abstract f zoomByWithFocus(float paramFloat, int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract f zoomIn()
    throws RemoteException;

  public abstract f zoomOut()
    throws RemoteException;

  public abstract f zoomTo(float paramFloat)
    throws RemoteException;

  public static abstract class a extends Binder
    implements ICameraUpdateFactoryDelegate
  {
    public static ICameraUpdateFactoryDelegate m(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null)
        localObject = null;
      while (true)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
        if ((localIInterface != null) && ((localIInterface instanceof ICameraUpdateFactoryDelegate)))
          localObject = (ICameraUpdateFactoryDelegate)localIInterface;
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
      case 6:
        while (true)
        {
          return bool;
          paramParcel2.writeString("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          f localf11 = zoomIn();
          paramParcel2.writeNoException();
          IBinder localIBinder11 = null;
          if (localf11 != null)
            localIBinder11 = localf11.asBinder();
          paramParcel2.writeStrongBinder(localIBinder11);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          f localf10 = zoomOut();
          paramParcel2.writeNoException();
          IBinder localIBinder10 = null;
          if (localf10 != null)
            localIBinder10 = localf10.asBinder();
          paramParcel2.writeStrongBinder(localIBinder10);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          f localf9 = scrollBy(paramParcel1.readFloat(), paramParcel1.readFloat());
          paramParcel2.writeNoException();
          IBinder localIBinder9 = null;
          if (localf9 != null)
            localIBinder9 = localf9.asBinder();
          paramParcel2.writeStrongBinder(localIBinder9);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          f localf8 = zoomTo(paramParcel1.readFloat());
          paramParcel2.writeNoException();
          IBinder localIBinder8 = null;
          if (localf8 != null)
            localIBinder8 = localf8.asBinder();
          paramParcel2.writeStrongBinder(localIBinder8);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          f localf7 = zoomBy(paramParcel1.readFloat());
          paramParcel2.writeNoException();
          IBinder localIBinder7 = null;
          if (localf7 != null)
            localIBinder7 = localf7.asBinder();
          paramParcel2.writeStrongBinder(localIBinder7);
          bool = true;
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          f localf6 = zoomByWithFocus(paramParcel1.readFloat(), paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          IBinder localIBinder6 = null;
          if (localf6 != null)
            localIBinder6 = localf6.asBinder();
          paramParcel2.writeStrongBinder(localIBinder6);
          bool = true;
        }
      case 7:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
        if (paramParcel1.readInt() != 0);
        for (CameraPosition localCameraPosition = CameraPositionCreator.createFromParcel(paramParcel1); ; localCameraPosition = null)
        {
          f localf5 = newCameraPosition(localCameraPosition);
          paramParcel2.writeNoException();
          IBinder localIBinder5 = null;
          if (localf5 != null)
            localIBinder5 = localf5.asBinder();
          paramParcel2.writeStrongBinder(localIBinder5);
          bool = true;
          break;
        }
      case 8:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
        if (paramParcel1.readInt() != 0);
        for (LatLng localLatLng2 = LatLngCreator.createFromParcel(paramParcel1); ; localLatLng2 = null)
        {
          f localf4 = newLatLng(localLatLng2);
          paramParcel2.writeNoException();
          IBinder localIBinder4 = null;
          if (localf4 != null)
            localIBinder4 = localf4.asBinder();
          paramParcel2.writeStrongBinder(localIBinder4);
          bool = true;
          break;
        }
      case 9:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
        if (paramParcel1.readInt() != 0);
        for (LatLng localLatLng1 = LatLngCreator.createFromParcel(paramParcel1); ; localLatLng1 = null)
        {
          f localf3 = newLatLngZoom(localLatLng1, paramParcel1.readFloat());
          paramParcel2.writeNoException();
          IBinder localIBinder3 = null;
          if (localf3 != null)
            localIBinder3 = localf3.asBinder();
          paramParcel2.writeStrongBinder(localIBinder3);
          bool = true;
          break;
        }
      case 10:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
        if (paramParcel1.readInt() != 0);
        for (LatLngBounds localLatLngBounds2 = LatLngBoundsCreator.createFromParcel(paramParcel1); ; localLatLngBounds2 = null)
        {
          f localf2 = newLatLngBounds(localLatLngBounds2, paramParcel1.readInt());
          paramParcel2.writeNoException();
          IBinder localIBinder2 = null;
          if (localf2 != null)
            localIBinder2 = localf2.asBinder();
          paramParcel2.writeStrongBinder(localIBinder2);
          bool = true;
          break;
        }
      case 11:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
      if (paramParcel1.readInt() != 0);
      for (LatLngBounds localLatLngBounds1 = LatLngBoundsCreator.createFromParcel(paramParcel1); ; localLatLngBounds1 = null)
      {
        f localf1 = newLatLngBoundsWithSize(localLatLngBounds1, paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
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
      implements ICameraUpdateFactoryDelegate
    {
      private IBinder a;

      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }

      public final IBinder asBinder()
      {
        return this.a;
      }

      public final f newCameraPosition(CameraPosition paramCameraPosition)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          if (paramCameraPosition != null)
          {
            localParcel1.writeInt(1);
            paramCameraPosition.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(7, localParcel1, localParcel2, 0);
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

      public final f newLatLng(LatLng paramLatLng)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          if (paramLatLng != null)
          {
            localParcel1.writeInt(1);
            paramLatLng.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(8, localParcel1, localParcel2, 0);
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

      public final f newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          if (paramLatLngBounds != null)
          {
            localParcel1.writeInt(1);
            paramLatLngBounds.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            localParcel1.writeInt(paramInt);
            this.a.transact(10, localParcel1, localParcel2, 0);
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

      public final f newLatLngBoundsWithSize(LatLngBounds paramLatLngBounds, int paramInt1, int paramInt2, int paramInt3)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          if (paramLatLngBounds != null)
          {
            localParcel1.writeInt(1);
            paramLatLngBounds.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            localParcel1.writeInt(paramInt1);
            localParcel1.writeInt(paramInt2);
            localParcel1.writeInt(paramInt3);
            this.a.transact(11, localParcel1, localParcel2, 0);
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

      public final f newLatLngZoom(LatLng paramLatLng, float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          if (paramLatLng != null)
          {
            localParcel1.writeInt(1);
            paramLatLng.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            localParcel1.writeFloat(paramFloat);
            this.a.transact(9, localParcel1, localParcel2, 0);
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

      public final f scrollBy(float paramFloat1, float paramFloat2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          localParcel1.writeFloat(paramFloat1);
          localParcel1.writeFloat(paramFloat2);
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

      public final f zoomBy(float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
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

      public final f zoomByWithFocus(float paramFloat, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          localParcel1.writeFloat(paramFloat);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(6, localParcel1, localParcel2, 0);
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

      public final f zoomIn()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
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

      public final f zoomOut()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
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

      public final f zoomTo(float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
          localParcel1.writeFloat(paramFloat);
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
 * Qualified Name:     com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
 * JD-Core Version:    0.6.2
 */