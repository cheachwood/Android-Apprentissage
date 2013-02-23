package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface bd extends IInterface
{
  public abstract void a(c paramc, Uri paramUri, Bundle paramBundle, boolean paramBoolean)
    throws RemoteException;

  public static abstract class a extends Binder
    implements bd
  {
    public static bd M(IBinder paramIBinder)
    {
      Object localObject;
      if (paramIBinder == null)
        localObject = null;
      while (true)
      {
        return localObject;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaService");
        if ((localIInterface != null) && ((localIInterface instanceof bd)))
          localObject = (bd)localIInterface;
        else
          localObject = new a(paramIBinder);
      }
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      boolean bool1 = true;
      switch (paramInt1)
      {
      default:
        bool1 = super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        while (true)
        {
          return bool1;
          paramParcel2.writeString("com.google.android.gms.panorama.internal.IPanoramaService");
        }
      case 1:
      }
      paramParcel1.enforceInterface("com.google.android.gms.panorama.internal.IPanoramaService");
      IBinder localIBinder = paramParcel1.readStrongBinder();
      Object localObject;
      label75: Uri localUri;
      label96: Bundle localBundle;
      if (localIBinder == null)
      {
        localObject = null;
        if (paramParcel1.readInt() == 0)
          break label191;
        localUri = (Uri)Uri.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0)
          break label197;
        localBundle = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
        label117: if (paramParcel1.readInt() == 0)
          break label203;
      }
      label191: label197: label203: for (boolean bool2 = bool1; ; bool2 = false)
      {
        a((c)localObject, localUri, localBundle, bool2);
        break;
        IInterface localIInterface = localIBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
        if ((localIInterface != null) && ((localIInterface instanceof c)))
        {
          localObject = (c)localIInterface;
          break label75;
        }
        localObject = new c.a.a(localIBinder);
        break label75;
        localUri = null;
        break label96;
        localBundle = null;
        break label117;
      }
    }

    private static final class a
      implements bd
    {
      private IBinder a;

      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }

      public final void a(c paramc, Uri paramUri, Bundle paramBundle, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.panorama.internal.IPanoramaService");
            IBinder localIBinder = null;
            if (paramc != null)
              localIBinder = paramc.asBinder();
            localParcel.writeStrongBinder(localIBinder);
            if (paramUri != null)
            {
              localParcel.writeInt(1);
              paramUri.writeToParcel(localParcel, 0);
              if (paramBundle != null)
              {
                localParcel.writeInt(1);
                paramBundle.writeToParcel(localParcel, 0);
                break label136;
                localParcel.writeInt(i);
                this.a.transact(1, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel.recycle();
          }
          localParcel.writeInt(0);
          label136: 
          while (!paramBoolean)
          {
            i = 0;
            break;
          }
        }
      }

      public final IBinder asBinder()
      {
        return this.a;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bd
 * JD-Core Version:    0.6.2
 */