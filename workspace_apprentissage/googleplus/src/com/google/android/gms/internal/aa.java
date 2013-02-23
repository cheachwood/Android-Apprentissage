package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.ArrayList;

public abstract class aa<T extends IInterface>
  implements GooglePlayServicesClient
{
  public static final String[] aW = { "service_esmobile", "service_googleme" };
  private T aM;
  private ArrayList<GooglePlayServicesClient.ConnectionCallbacks> aN;
  final ArrayList<GooglePlayServicesClient.ConnectionCallbacks> aO = new ArrayList();
  private boolean aP = false;
  private ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> aQ;
  private boolean aR = false;
  private final ArrayList<aa<T>.c<?>> aS = new ArrayList();
  private ServiceConnection aT;
  private final String[] aU;
  boolean aV = false;
  private final Context mContext;
  final Handler mHandler;

  protected aa(Context paramContext, GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener paramOnConnectionFailedListener, String[] paramArrayOfString)
  {
    if (Looper.getMainLooper().getThread() != Thread.currentThread())
      throw new IllegalStateException("Clients must be created on the UI thread.");
    this.mContext = ((Context)bj.c(paramContext));
    this.aN = new ArrayList();
    this.aN.add(bj.c(paramConnectionCallbacks));
    this.aQ = new ArrayList();
    this.aQ.add(bj.c(paramOnConnectionFailedListener));
    this.mHandler = new e();
    this.aU = null;
  }

  protected final void a(ConnectionResult paramConnectionResult)
  {
    this.mHandler.removeMessages(4);
    while (true)
    {
      int j;
      synchronized (this.aQ)
      {
        this.aR = true;
        ArrayList localArrayList2 = this.aQ;
        int i = localArrayList2.size();
        j = 0;
        if (j < i)
        {
          if (this.aV)
          {
            if (!this.aQ.contains(localArrayList2.get(j)))
              break label106;
            ((GooglePlayServicesClient.OnConnectionFailedListener)localArrayList2.get(j)).onConnectionFailed$5d4cef71();
            break label106;
          }
        }
        else
          this.aR = false;
      }
      return;
      label106: j++;
    }
  }

  public final void a(aa<T>.c<?> paramaa)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(2, paramaa));
  }

  protected abstract void a(p paramp, aa<T>.a paramaa)
    throws RemoteException;

  public final void connect()
  {
    this.aV = true;
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
    if (i != 0)
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(i)));
    while (true)
    {
      return;
      Intent localIntent = new Intent(h());
      if (this.aT != null)
      {
        Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
        this.aM = null;
        this.mContext.unbindService(this.aT);
      }
      this.aT = new d();
      boolean bool = this.mContext.bindService(localIntent, this.aT, 129);
      Log.i("GmsClient", "connect: bindService returned " + bool + " for " + localIntent);
    }
  }

  public final void disconnect()
  {
    this.aV = false;
    synchronized (this.aS)
    {
      int i = this.aS.size();
      for (int j = 0; j < i; j++)
        ((c)this.aS.get(j)).p();
      this.aS.clear();
      this.aM = null;
      if (this.aT != null)
      {
        this.mContext.unbindService(this.aT);
        this.aT = null;
      }
      return;
    }
  }

  public final Context getContext()
  {
    return this.mContext;
  }

  protected abstract String h();

  protected abstract String i();

  public final boolean isConnected()
  {
    if (this.aM != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    bj.c(paramConnectionCallbacks);
    synchronized (this.aN)
    {
      boolean bool = this.aN.contains(paramConnectionCallbacks);
      return bool;
    }
  }

  protected abstract T j(IBinder paramIBinder);

  public final String[] j()
  {
    return this.aU;
  }

  protected final void k()
  {
    boolean bool1 = true;
    while (true)
    {
      int j;
      synchronized (this.aN)
      {
        if (this.aP)
          break label157;
        bool2 = bool1;
        bj.a(bool2);
        this.mHandler.removeMessages(4);
        this.aP = true;
        if (this.aO.size() != 0)
          break label163;
        bj.a(bool1);
        ArrayList localArrayList2 = this.aN;
        int i = localArrayList2.size();
        j = 0;
        if ((j < i) && (this.aV) && (isConnected()))
        {
          this.aO.size();
          if (!this.aO.contains(localArrayList2.get(j)))
            ((GooglePlayServicesClient.ConnectionCallbacks)localArrayList2.get(j)).onConnected();
        }
        else
        {
          this.aO.clear();
          this.aP = false;
          return;
        }
      }
      j++;
      continue;
      label157: boolean bool2 = false;
      continue;
      label163: bool1 = false;
    }
  }

  protected final void k(IBinder paramIBinder)
  {
    try
    {
      a(p.a.d(paramIBinder), new a());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        Log.w("GmsClient", "service died");
    }
  }

  protected final void l()
  {
    this.mHandler.removeMessages(4);
    while (true)
    {
      int j;
      synchronized (this.aN)
      {
        this.aP = true;
        ArrayList localArrayList2 = this.aN;
        int i = localArrayList2.size();
        j = 0;
        if ((j < i) && (this.aV))
        {
          if (this.aN.contains(localArrayList2.get(j)))
            ((GooglePlayServicesClient.ConnectionCallbacks)localArrayList2.get(j)).onDisconnected();
        }
        else
        {
          this.aP = false;
          return;
        }
      }
      j++;
    }
  }

  protected final void m()
  {
    if (!isConnected())
      throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
  }

  protected final T n()
  {
    m();
    return this.aM;
  }

  public final void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    bj.c(paramConnectionCallbacks);
    synchronized (this.aN)
    {
      if (this.aN.contains(paramConnectionCallbacks))
      {
        Log.w("GmsClient", "registerConnectionListener(): listener " + paramConnectionCallbacks + " is already registered");
        if (isConnected())
          this.mHandler.sendMessage(this.mHandler.obtainMessage(4, paramConnectionCallbacks));
        return;
      }
      if (this.aP)
        this.aN = new ArrayList(this.aN);
      this.aN.add(paramConnectionCallbacks);
    }
  }

  public final void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    bj.c(paramConnectionCallbacks);
    synchronized (this.aN)
    {
      if (this.aN != null)
      {
        if (this.aP)
          this.aN = new ArrayList(this.aN);
        if (this.aN.remove(paramConnectionCallbacks))
          break label84;
        Log.w("GmsClient", "unregisterConnectionListener(): listener " + paramConnectionCallbacks + " not found");
      }
      label84: 
      while ((!this.aP) || (this.aO.contains(paramConnectionCallbacks)))
        return;
      this.aO.add(paramConnectionCallbacks);
    }
  }

  protected final class a extends ar.a
  {
    protected a()
    {
    }

    public final void a(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    {
      aa.this.mHandler.sendMessage(aa.this.mHandler.obtainMessage(1, new aa.b(aa.this, paramInt, paramIBinder, paramBundle)));
    }
  }

  protected final class b extends aa<T>.c<Boolean>
  {
    public final Bundle bd;
    public final IBinder be;
    public final int statusCode;

    public b(int paramIBinder, IBinder paramBundle, Bundle arg4)
    {
      super(Boolean.valueOf(true));
      this.statusCode = paramIBinder;
      this.be = paramBundle;
      Object localObject;
      this.bd = localObject;
    }
  }

  protected abstract class c<TListener>
  {
    private TListener mListener;

    public c()
    {
      Object localObject1;
      this.mListener = localObject1;
      synchronized (aa.b(aa.this))
      {
        aa.b(aa.this).add(this);
        return;
      }
    }

    protected abstract void a(TListener paramTListener);

    public final void o()
    {
      try
      {
        Object localObject2 = this.mListener;
        a(localObject2);
        return;
      }
      finally
      {
      }
    }

    public void p()
    {
      try
      {
        this.mListener = null;
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }

  final class d
    implements ServiceConnection
  {
    d()
    {
    }

    public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      Log.d("GmsClient", "service broker connected, binder: " + paramIBinder);
      aa.this.k(paramIBinder);
    }

    public final void onServiceDisconnected(ComponentName paramComponentName)
    {
      Log.d("GmsClient", "service disconnected: " + paramComponentName);
      aa.a(aa.this, null);
      aa.this.l();
    }
  }

  final class e extends Handler
  {
    e()
    {
    }

    public final void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 3)
        aa.this.a(new ConnectionResult(((Integer)paramMessage.obj).intValue(), null));
      while (true)
      {
        return;
        if (paramMessage.what == 4)
          synchronized (aa.a(aa.this))
          {
            if ((aa.this.aV) && (aa.this.isConnected()) && (aa.a(aa.this).contains(paramMessage.obj)))
              ((GooglePlayServicesClient.ConnectionCallbacks)paramMessage.obj).onConnected();
          }
        if (((paramMessage.what != 2) || (aa.this.isConnected())) && ((paramMessage.what == 2) || (paramMessage.what == 1)))
          ((aa.c)paramMessage.obj).o();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.aa
 * JD-Core Version:    0.6.2
 */