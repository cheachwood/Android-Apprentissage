package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.a.a;
import com.google.android.gms.internal.ab;
import com.google.android.gms.internal.ab.a;
import com.google.android.gms.internal.ai;
import com.google.android.gms.internal.ai.a;
import com.google.android.gms.internal.ao;
import com.google.android.gms.internal.ao.a;
import com.google.android.gms.internal.ax;
import com.google.android.gms.internal.ax.a;
import com.google.android.gms.internal.ay;
import com.google.android.gms.internal.ay.a;
import com.google.android.gms.internal.az;
import com.google.android.gms.internal.az.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.b.a;
import com.google.android.gms.internal.f;
import com.google.android.gms.internal.f.a;
import com.google.android.gms.internal.l;
import com.google.android.gms.internal.l.a;
import com.google.android.gms.internal.o;
import com.google.android.gms.internal.o.a;
import com.google.android.gms.internal.s;
import com.google.android.gms.internal.s.a;
import com.google.android.gms.internal.z;
import com.google.android.gms.internal.z.a;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPositionCreator;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.GroundOverlayOptionsCreator;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MarkerOptionsCreator;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolygonOptionsCreator;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.PolylineOptionsCreator;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileOverlayOptionsCreator;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.IPolylineDelegate.a;

public abstract interface IGoogleMapDelegate extends IInterface
{
  public abstract ao addGroundOverlay(GroundOverlayOptions paramGroundOverlayOptions)
    throws RemoteException;

  public abstract z addMarker(MarkerOptions paramMarkerOptions)
    throws RemoteException;

  public abstract b addPolygon(PolygonOptions paramPolygonOptions)
    throws RemoteException;

  public abstract IPolylineDelegate addPolyline(PolylineOptions paramPolylineOptions)
    throws RemoteException;

  public abstract ay addTileOverlay(TileOverlayOptions paramTileOverlayOptions)
    throws RemoteException;

  public abstract void animateCamera(f paramf)
    throws RemoteException;

  public abstract void animateCameraWithCallback(f paramf, ax paramax)
    throws RemoteException;

  public abstract void animateCameraWithDurationAndCallback(f paramf, int paramInt, ax paramax)
    throws RemoteException;

  public abstract void clear()
    throws RemoteException;

  public abstract CameraPosition getCameraPosition()
    throws RemoteException;

  public abstract int getMapType()
    throws RemoteException;

  public abstract float getMaxZoomLevel()
    throws RemoteException;

  public abstract float getMinZoomLevel()
    throws RemoteException;

  public abstract Location getMyLocation()
    throws RemoteException;

  public abstract IProjectionDelegate getProjection()
    throws RemoteException;

  public abstract f getTestingHelper()
    throws RemoteException;

  public abstract IUiSettingsDelegate getUiSettings()
    throws RemoteException;

  public abstract boolean isIndoorEnabled()
    throws RemoteException;

  public abstract boolean isMyLocationEnabled()
    throws RemoteException;

  public abstract boolean isTrafficEnabled()
    throws RemoteException;

  public abstract void moveCamera(f paramf)
    throws RemoteException;

  public abstract boolean setIndoorEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void setInfoWindowAdapter(o paramo)
    throws RemoteException;

  public abstract void setLocationSource(ILocationSourceDelegate paramILocationSourceDelegate)
    throws RemoteException;

  public abstract void setMapType(int paramInt)
    throws RemoteException;

  public abstract void setMyLocationEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void setOnCameraChangeListener(a parama)
    throws RemoteException;

  public abstract void setOnInfoWindowClickListener(ab paramab)
    throws RemoteException;

  public abstract void setOnMapClickListener(s params)
    throws RemoteException;

  public abstract void setOnMapLongClickListener(l paraml)
    throws RemoteException;

  public abstract void setOnMarkerClickListener(az paramaz)
    throws RemoteException;

  public abstract void setOnMarkerDragListener(ai paramai)
    throws RemoteException;

  public abstract void setTrafficEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void stopAnimation()
    throws RemoteException;

  public static abstract class a extends Binder
    implements IGoogleMapDelegate
  {
    public static IGoogleMapDelegate t(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null)
        localObject = null;
      while (true)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
        if ((localIInterface != null) && ((localIInterface instanceof IGoogleMapDelegate)))
          localObject = (IGoogleMapDelegate)localIInterface;
        else
          localObject = new a(paramIBinder);
      }
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      int i = 1;
      switch (paramInt1)
      {
      default:
        i = super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      }
      while (true)
      {
        return i;
        paramParcel2.writeString("com.google.android.gms.maps.internal.IGoogleMapDelegate");
        continue;
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
        CameraPosition localCameraPosition = getCameraPosition();
        paramParcel2.writeNoException();
        if (localCameraPosition != null)
        {
          paramParcel2.writeInt(i);
          localCameraPosition.writeToParcel(paramParcel2, i);
        }
        else
        {
          paramParcel2.writeInt(0);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          float f2 = getMaxZoomLevel();
          paramParcel2.writeNoException();
          paramParcel2.writeFloat(f2);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          float f1 = getMinZoomLevel();
          paramParcel2.writeNoException();
          paramParcel2.writeFloat(f1);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          moveCamera(f.a.E(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          animateCamera(f.a.E(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          animateCameraWithCallback(f.a.E(paramParcel1.readStrongBinder()), ax.a.y(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          animateCameraWithDurationAndCallback(f.a.E(paramParcel1.readStrongBinder()), paramParcel1.readInt(), ax.a.y(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          stopAnimation();
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramParcel1.readInt() != 0);
          for (PolylineOptions localPolylineOptions = PolylineOptionsCreator.createFromParcel(paramParcel1); ; localPolylineOptions = null)
          {
            IPolylineDelegate localIPolylineDelegate = addPolyline(localPolylineOptions);
            paramParcel2.writeNoException();
            IBinder localIBinder9 = null;
            if (localIPolylineDelegate != null)
              localIBinder9 = localIPolylineDelegate.asBinder();
            paramParcel2.writeStrongBinder(localIBinder9);
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramParcel1.readInt() != 0);
          for (PolygonOptions localPolygonOptions = PolygonOptions.CREATOR.createFromParcel(paramParcel1); ; localPolygonOptions = null)
          {
            b localb = addPolygon(localPolygonOptions);
            paramParcel2.writeNoException();
            IBinder localIBinder8 = null;
            if (localb != null)
              localIBinder8 = localb.asBinder();
            paramParcel2.writeStrongBinder(localIBinder8);
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramParcel1.readInt() != 0);
          for (MarkerOptions localMarkerOptions = MarkerOptionsCreator.createFromParcel(paramParcel1); ; localMarkerOptions = null)
          {
            z localz = addMarker(localMarkerOptions);
            paramParcel2.writeNoException();
            IBinder localIBinder7 = null;
            if (localz != null)
              localIBinder7 = localz.asBinder();
            paramParcel2.writeStrongBinder(localIBinder7);
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramParcel1.readInt() != 0);
          for (GroundOverlayOptions localGroundOverlayOptions = GroundOverlayOptionsCreator.createFromParcel(paramParcel1); ; localGroundOverlayOptions = null)
          {
            ao localao = addGroundOverlay(localGroundOverlayOptions);
            paramParcel2.writeNoException();
            IBinder localIBinder6 = null;
            if (localao != null)
              localIBinder6 = localao.asBinder();
            paramParcel2.writeStrongBinder(localIBinder6);
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramParcel1.readInt() != 0);
          for (TileOverlayOptions localTileOverlayOptions = TileOverlayOptionsCreator.createFromParcel(paramParcel1); ; localTileOverlayOptions = null)
          {
            ay localay = addTileOverlay(localTileOverlayOptions);
            paramParcel2.writeNoException();
            IBinder localIBinder5 = null;
            if (localay != null)
              localIBinder5 = localay.asBinder();
            paramParcel2.writeStrongBinder(localIBinder5);
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          clear();
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          int i5 = getMapType();
          paramParcel2.writeNoException();
          paramParcel2.writeInt(i5);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          setMapType(paramParcel1.readInt());
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          boolean bool6 = isTrafficEnabled();
          paramParcel2.writeNoException();
          int i4 = 0;
          if (bool6)
            i4 = i;
          paramParcel2.writeInt(i4);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          int i3 = paramParcel1.readInt();
          boolean bool5 = false;
          if (i3 != 0)
            bool5 = i;
          setTrafficEnabled(bool5);
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          boolean bool4 = isIndoorEnabled();
          paramParcel2.writeNoException();
          int i2 = 0;
          if (bool4)
            i2 = i;
          paramParcel2.writeInt(i2);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramParcel1.readInt() != 0);
          int n;
          for (int m = i; ; n = 0)
          {
            boolean bool3 = setIndoorEnabled(m);
            paramParcel2.writeNoException();
            int i1 = 0;
            if (bool3)
              i1 = i;
            paramParcel2.writeInt(i1);
            break;
          }
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          boolean bool2 = isMyLocationEnabled();
          paramParcel2.writeNoException();
          int k = 0;
          if (bool2)
            k = i;
          paramParcel2.writeInt(k);
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          int j = paramParcel1.readInt();
          boolean bool1 = false;
          if (j != 0)
            bool1 = i;
          setMyLocationEnabled(bool1);
          paramParcel2.writeNoException();
          continue;
          paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          Location localLocation = getMyLocation();
          paramParcel2.writeNoException();
          if (localLocation != null)
          {
            paramParcel2.writeInt(i);
            localLocation.writeToParcel(paramParcel2, i);
          }
          else
          {
            paramParcel2.writeInt(0);
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            IBinder localIBinder4 = paramParcel1.readStrongBinder();
            Object localObject = null;
            if (localIBinder4 == null);
            while (true)
            {
              setLocationSource((ILocationSourceDelegate)localObject);
              paramParcel2.writeNoException();
              break;
              IInterface localIInterface = localIBinder4.queryLocalInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
              if ((localIInterface != null) && ((localIInterface instanceof ILocationSourceDelegate)))
                localObject = (ILocationSourceDelegate)localIInterface;
              else
                localObject = new ILocationSourceDelegate.a.a(localIBinder4);
            }
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            IUiSettingsDelegate localIUiSettingsDelegate = getUiSettings();
            paramParcel2.writeNoException();
            IBinder localIBinder3 = null;
            if (localIUiSettingsDelegate != null)
              localIBinder3 = localIUiSettingsDelegate.asBinder();
            paramParcel2.writeStrongBinder(localIBinder3);
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            IProjectionDelegate localIProjectionDelegate = getProjection();
            paramParcel2.writeNoException();
            IBinder localIBinder2 = null;
            if (localIProjectionDelegate != null)
              localIBinder2 = localIProjectionDelegate.asBinder();
            paramParcel2.writeStrongBinder(localIBinder2);
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setOnCameraChangeListener(a.a.a(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setOnMapClickListener(s.a.w(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setOnMapLongClickListener(l.a.A(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setOnMarkerClickListener(az.a.z(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setOnMarkerDragListener(ai.a.i(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setOnInfoWindowClickListener(ab.a.r(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            setInfoWindowAdapter(o.a.g(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            continue;
            paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            f localf = getTestingHelper();
            paramParcel2.writeNoException();
            IBinder localIBinder1 = null;
            if (localf != null)
              localIBinder1 = localf.asBinder();
            paramParcel2.writeStrongBinder(localIBinder1);
          }
        }
      }
    }

    private static final class a
      implements IGoogleMapDelegate
    {
      private IBinder a;

      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }

      public final ao addGroundOverlay(GroundOverlayOptions paramGroundOverlayOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramGroundOverlayOptions != null)
          {
            localParcel1.writeInt(1);
            paramGroundOverlayOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(12, localParcel1, localParcel2, 0);
            localParcel2.readException();
            ao localao = ao.a.o(localParcel2.readStrongBinder());
            return localao;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final z addMarker(MarkerOptions paramMarkerOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramMarkerOptions != null)
          {
            localParcel1.writeInt(1);
            paramMarkerOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(11, localParcel1, localParcel2, 0);
            localParcel2.readException();
            z localz = z.a.s(localParcel2.readStrongBinder());
            return localz;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final b addPolygon(PolygonOptions paramPolygonOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramPolygonOptions != null)
          {
            localParcel1.writeInt(1);
            paramPolygonOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(10, localParcel1, localParcel2, 0);
            localParcel2.readException();
            b localb = b.a.b(localParcel2.readStrongBinder());
            return localb;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final IPolylineDelegate addPolyline(PolylineOptions paramPolylineOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramPolylineOptions != null)
          {
            localParcel1.writeInt(1);
            paramPolylineOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(9, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IPolylineDelegate localIPolylineDelegate = IPolylineDelegate.a.p(localParcel2.readStrongBinder());
            return localIPolylineDelegate;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final ay addTileOverlay(TileOverlayOptions paramTileOverlayOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramTileOverlayOptions != null)
          {
            localParcel1.writeInt(1);
            paramTileOverlayOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.a.transact(13, localParcel1, localParcel2, 0);
            localParcel2.readException();
            ay localay = ay.a.v(localParcel2.readStrongBinder());
            return localay;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void animateCamera(f paramf)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramf != null)
          {
            localIBinder = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(5, localParcel1, localParcel2, 0);
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

      public final void animateCameraWithCallback(f paramf, ax paramax)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramf != null)
          {
            localIBinder1 = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder1);
            IBinder localIBinder2 = null;
            if (paramax != null)
              localIBinder2 = paramax.asBinder();
            localParcel1.writeStrongBinder(localIBinder2);
            this.a.transact(6, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
          IBinder localIBinder1 = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void animateCameraWithDurationAndCallback(f paramf, int paramInt, ax paramax)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramf != null)
          {
            localIBinder1 = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder1);
            localParcel1.writeInt(paramInt);
            IBinder localIBinder2 = null;
            if (paramax != null)
              localIBinder2 = paramax.asBinder();
            localParcel1.writeStrongBinder(localIBinder2);
            this.a.transact(7, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
          IBinder localIBinder1 = null;
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

      public final void clear()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(14, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final CameraPosition getCameraPosition()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            CameraPosition localCameraPosition2 = CameraPositionCreator.createFromParcel(localParcel2);
            localCameraPosition1 = localCameraPosition2;
            return localCameraPosition1;
          }
          CameraPosition localCameraPosition1 = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final int getMapType()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(15, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final float getMaxZoomLevel()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          float f = localParcel2.readFloat();
          return f;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final float getMinZoomLevel()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          float f = localParcel2.readFloat();
          return f;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final Location getMyLocation()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(23, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localLocation = (Location)Location.CREATOR.createFromParcel(localParcel2);
            return localLocation;
          }
          Location localLocation = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final IProjectionDelegate getProjection()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(26, localParcel1, localParcel2, 0);
          localParcel2.readException();
          IProjectionDelegate localIProjectionDelegate = IProjectionDelegate.a.H(localParcel2.readStrongBinder());
          return localIProjectionDelegate;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final f getTestingHelper()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(34, localParcel1, localParcel2, 0);
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

      public final IUiSettingsDelegate getUiSettings()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(25, localParcel1, localParcel2, 0);
          localParcel2.readException();
          IUiSettingsDelegate localIUiSettingsDelegate = IUiSettingsDelegate.a.C(localParcel2.readStrongBinder());
          return localIUiSettingsDelegate;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final boolean isIndoorEnabled()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final boolean isMyLocationEnabled()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final boolean isTrafficEnabled()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void moveCamera(f paramf)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramf != null)
          {
            localIBinder = paramf.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(4, localParcel1, localParcel2, 0);
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

      public final boolean setIndoorEnabled(boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramBoolean);
          int k;
          for (int j = i; ; k = 0)
          {
            localParcel1.writeInt(j);
            this.a.transact(20, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int m = localParcel2.readInt();
            if (m == 0)
              break;
            return i;
          }
          i = 0;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void setInfoWindowAdapter(o paramo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramo != null)
          {
            localIBinder = paramo.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(33, localParcel1, localParcel2, 0);
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

      public final void setLocationSource(ILocationSourceDelegate paramILocationSourceDelegate)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramILocationSourceDelegate != null)
          {
            localIBinder = paramILocationSourceDelegate.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(24, localParcel1, localParcel2, 0);
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

      public final void setMapType(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          localParcel1.writeInt(paramInt);
          this.a.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void setMyLocationEnabled(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          int i = 0;
          if (paramBoolean)
            i = 1;
          localParcel1.writeInt(i);
          this.a.transact(22, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void setOnCameraChangeListener(a parama)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (parama != null)
          {
            localIBinder = parama.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(27, localParcel1, localParcel2, 0);
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

      public final void setOnInfoWindowClickListener(ab paramab)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramab != null)
          {
            localIBinder = paramab.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(32, localParcel1, localParcel2, 0);
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

      public final void setOnMapClickListener(s params)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (params != null)
          {
            localIBinder = params.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(28, localParcel1, localParcel2, 0);
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

      public final void setOnMapLongClickListener(l paraml)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paraml != null)
          {
            localIBinder = paraml.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(29, localParcel1, localParcel2, 0);
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

      public final void setOnMarkerClickListener(az paramaz)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramaz != null)
          {
            localIBinder = paramaz.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(30, localParcel1, localParcel2, 0);
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

      public final void setOnMarkerDragListener(ai paramai)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          if (paramai != null)
          {
            localIBinder = paramai.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            this.a.transact(31, localParcel1, localParcel2, 0);
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

      public final void setTrafficEnabled(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          int i = 0;
          if (paramBoolean)
            i = 1;
          localParcel1.writeInt(i);
          this.a.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public final void stopAnimation()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
          this.a.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
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
 * Qualified Name:     com.google.android.gms.maps.internal.IGoogleMapDelegate
 * JD-Core Version:    0.6.2
 */