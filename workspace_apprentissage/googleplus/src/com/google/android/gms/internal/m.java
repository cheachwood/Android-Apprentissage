package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient.a;
import com.google.android.gms.plus.PlusClient.b;
import com.google.android.gms.plus.PlusClient.c;

public final class m extends aa<at>
{
  private final String dF;
  private final String dG;
  private final String dH;

  protected final void a(p paramp, aa<at>.a paramaa)
    throws RemoteException
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("skip_oob", false);
    paramp.a(paramaa, 1, this.dF, this.dG, j(), this.dH, localBundle);
  }

  public final void a(PlusClient.a parama, Uri paramUri, int paramInt)
  {
    m();
    Bundle localBundle = new Bundle();
    localBundle.putInt("bounding_box", paramInt);
    c localc = new c(parama);
    try
    {
      ((at)n()).a(localc, paramUri, localBundle);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localc.a(8, null, null);
    }
  }

  public final void a(PlusClient.b paramb, String paramString)
  {
    m();
    b localb = new b(paramb);
    try
    {
      ((at)n()).a(localb, paramString);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localb.a(8, null, null);
    }
  }

  public final void a(PlusClient.c paramc)
  {
    m();
    a locala = new a(paramc);
    try
    {
      ((at)n()).a(locala);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        locala.a(8, null, null);
    }
  }

  protected final String h()
  {
    return "com.google.android.gms.plus.service.START";
  }

  protected final String i()
  {
    return "com.google.android.gms.plus.internal.IPlusService";
  }

  final class a extends aj
  {
    private final PlusClient.c w;

    public a(PlusClient.c arg2)
    {
      Object localObject;
      this.w = localObject;
    }

    public final void a(int paramInt, Bundle paramBundle1, Bundle paramBundle2)
    {
      if (paramBundle1 != null);
      for (PendingIntent localPendingIntent = (PendingIntent)paramBundle1.getParcelable("pendingIntent"); ; localPendingIntent = null)
      {
        ConnectionResult localConnectionResult = new ConnectionResult(paramInt, localPendingIntent);
        av localav = null;
        if (paramBundle2 != null)
          localav = new av(paramBundle2);
        m.this.a(new m.e(m.this, this.w, localConnectionResult, localav));
        return;
      }
    }
  }

  final class b extends aj
  {
    private final PlusClient.b ax;

    public b(PlusClient.b arg2)
    {
      Object localObject;
      this.ax = localObject;
    }

    public final void a(int paramInt, Bundle paramBundle1, Bundle paramBundle2)
    {
      if (paramBundle1 != null);
      for (PendingIntent localPendingIntent = (PendingIntent)paramBundle1.getParcelable("pendingIntent"); ; localPendingIntent = null)
      {
        ConnectionResult localConnectionResult = new ConnectionResult(paramInt, localPendingIntent);
        bk localbk = null;
        if (paramBundle2 != null)
          localbk = new bk(paramBundle2);
        m.this.a(new m.f(m.this, this.ax, localConnectionResult, localbk));
        return;
      }
    }
  }

  final class c extends aj
  {
    private final PlusClient.a br;

    public c(PlusClient.a arg2)
    {
      Object localObject;
      this.br = localObject;
    }

    public final void a(int paramInt, Bundle paramBundle, ParcelFileDescriptor paramParcelFileDescriptor)
    {
      PendingIntent localPendingIntent = null;
      if (paramBundle != null)
        localPendingIntent = (PendingIntent)paramBundle.getParcelable("pendingIntent");
      ConnectionResult localConnectionResult = new ConnectionResult(paramInt, localPendingIntent);
      m.this.a(new m.d(m.this, this.br, localConnectionResult, paramParcelFileDescriptor));
    }
  }

  private final class d extends aa<at>.c<PlusClient.a>
  {
    private final ConnectionResult bQ;
    private final ParcelFileDescriptor bR;

    public d(PlusClient.a paramConnectionResult, ConnectionResult paramParcelFileDescriptor, ParcelFileDescriptor arg4)
    {
      super(paramConnectionResult);
      this.bQ = paramParcelFileDescriptor;
      Object localObject;
      this.bR = localObject;
    }

    public final void p()
    {
      super.p();
    }
  }

  final class e extends aa<at>.c<PlusClient.c>
  {
    public final ConnectionResult at;
    public final av ci;

    public e(PlusClient.c paramConnectionResult, ConnectionResult paramav, av arg4)
    {
      super(paramConnectionResult);
      this.at = paramav;
      Object localObject;
      this.ci = localObject;
    }
  }

  final class f extends aa<at>.c<PlusClient.b>
  {
    public final ConnectionResult at;
    public final bk cS;

    public f(PlusClient.b paramConnectionResult, ConnectionResult parambk, bk arg4)
    {
      super(paramConnectionResult);
      this.at = parambk;
      Object localObject;
      this.cS = localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.m
 * JD-Core Version:    0.6.2
 */