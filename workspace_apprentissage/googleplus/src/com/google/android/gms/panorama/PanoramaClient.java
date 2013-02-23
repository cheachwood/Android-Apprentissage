package com.google.android.gms.panorama;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.internal.af;

public final class PanoramaClient
  implements GooglePlayServicesClient
{
  private final af dS;

  public PanoramaClient(Context paramContext, GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this.dS = new af(paramContext, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }

  public final void connect()
  {
    this.dS.connect();
  }

  public final void disconnect()
  {
    this.dS.disconnect();
  }

  public final boolean isConnected()
  {
    return this.dS.isConnected();
  }

  public final void loadPanoramaInfo(OnPanoramaInfoLoadedListener paramOnPanoramaInfoLoadedListener, Uri paramUri)
  {
    this.dS.a(paramOnPanoramaInfoLoadedListener, paramUri, false);
  }

  public final void loadPanoramaInfoAndGrantAccess(OnPanoramaInfoLoadedListener paramOnPanoramaInfoLoadedListener, Uri paramUri)
  {
    this.dS.a(paramOnPanoramaInfoLoadedListener, paramUri, true);
  }

  public static abstract interface OnFullPanoramaInfoLoadedListener
  {
  }

  public static abstract interface OnPanoramaInfoLoadedListener
  {
    public abstract void onPanoramaInfoLoaded$680664b4(Intent paramIntent);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.panorama.PanoramaClient
 * JD-Core Version:    0.6.2
 */