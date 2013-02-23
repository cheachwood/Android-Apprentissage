package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.panorama.PanoramaClient.OnFullPanoramaInfoLoadedListener;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;

public final class af extends aa<bd>
{
  public af(Context paramContext, GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramConnectionCallbacks, paramOnConnectionFailedListener, null);
  }

  protected final void a(p paramp, aa<bd>.a paramaa)
    throws RemoteException
  {
    Bundle localBundle = new Bundle();
    paramp.a(paramaa, 1, getContext().getPackageName(), localBundle);
  }

  public final void a(PanoramaClient.OnPanoramaInfoLoadedListener paramOnPanoramaInfoLoadedListener, Uri paramUri, boolean paramBoolean)
  {
    Uri localUri;
    if (paramBoolean)
      localUri = paramUri;
    while (true)
    {
      a locala = new a(paramOnPanoramaInfoLoadedListener, localUri);
      m();
      if (paramBoolean)
        getContext().grantUriPermission("com.google.android.gms", paramUri, 1);
      try
      {
        ((bd)n()).a(locala, paramUri, null, paramBoolean);
        return;
        localUri = null;
      }
      catch (RemoteException localRemoteException)
      {
        while (true)
          locala.a(8, null, 0, null);
      }
    }
  }

  protected final String h()
  {
    return "com.google.android.gms.panorama.service.START";
  }

  protected final String i()
  {
    return "com.google.android.gms.panorama.internal.IPanoramaService";
  }

  final class a extends c.a
  {
    private final Uri aA;
    private final PanoramaClient.OnFullPanoramaInfoLoadedListener ay = null;
    private final PanoramaClient.OnPanoramaInfoLoadedListener az;

    public a(PanoramaClient.OnPanoramaInfoLoadedListener paramUri, Uri arg3)
    {
      this.az = paramUri;
      Object localObject;
      this.aA = localObject;
    }

    public final void a(int paramInt1, Bundle paramBundle, int paramInt2, Intent paramIntent)
    {
      if (this.aA != null)
        af.this.getContext().revokeUriPermission(this.aA, 1);
      PendingIntent localPendingIntent = null;
      if (paramBundle != null)
        localPendingIntent = (PendingIntent)paramBundle.getParcelable("pendingIntent");
      ConnectionResult localConnectionResult = new ConnectionResult(paramInt1, localPendingIntent);
      af.this.a(new af.c(af.this, this.az, localConnectionResult, paramIntent));
    }
  }

  final class c extends aa<bd>.c<PanoramaClient.OnPanoramaInfoLoadedListener>
  {
    private final ConnectionResult at;
    private final Intent bf;

    public c(PanoramaClient.OnPanoramaInfoLoadedListener paramConnectionResult, ConnectionResult paramIntent, Intent arg4)
    {
      super(paramConnectionResult);
      this.at = paramIntent;
      Object localObject;
      this.bf = localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.af
 * JD-Core Version:    0.6.2
 */