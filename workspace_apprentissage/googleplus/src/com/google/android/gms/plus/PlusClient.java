package com.google.android.gms.plus;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.internal.av;
import com.google.android.gms.internal.bk;
import com.google.android.gms.internal.m;

public final class PlusClient
  implements GooglePlayServicesClient
{
  private final m bL;

  public final void a(a parama, Uri paramUri, int paramInt)
  {
    this.bL.a(parama, paramUri, paramInt);
  }

  public final void a(b paramb, String paramString)
  {
    this.bL.a(paramb, paramString);
  }

  public final void a(c paramc)
  {
    this.bL.a(paramc);
  }

  public final boolean isConnected()
  {
    return this.bL.isConnected();
  }

  public final boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    return this.bL.isConnectionCallbacksRegistered(paramConnectionCallbacks);
  }

  public final void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    this.bL.registerConnectionCallbacks(paramConnectionCallbacks);
  }

  public final void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    this.bL.unregisterConnectionCallbacks(paramConnectionCallbacks);
  }

  public static abstract interface a
  {
    public abstract void a(ConnectionResult paramConnectionResult, ParcelFileDescriptor paramParcelFileDescriptor);
  }

  public static abstract interface b
  {
    public abstract void a(ConnectionResult paramConnectionResult, bk parambk);
  }

  public static abstract interface c
  {
    public abstract void a(ConnectionResult paramConnectionResult, av paramav);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.plus.PlusClient
 * JD-Core Version:    0.6.2
 */