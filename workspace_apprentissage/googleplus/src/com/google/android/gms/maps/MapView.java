package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.am;
import com.google.android.gms.internal.aq;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.d;
import com.google.android.gms.internal.q;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapView extends FrameLayout
{
  private final a bP;

  public MapView(Context paramContext)
  {
    super(paramContext);
    this.bP = new a(this, paramContext, null);
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.bP = new a(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.bP = new a(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
  }

  static final class a extends d<MapView.b>
  {
    private final ViewGroup dJ;
    private final GoogleMapOptions dK;
    protected aq<MapView.b> g;
    private final Context mContext;

    a(ViewGroup paramViewGroup, Context paramContext, GoogleMapOptions paramGoogleMapOptions)
    {
      this.dJ = paramViewGroup;
      this.mContext = paramContext;
      this.dK = paramGoogleMapOptions;
    }

    protected final void a(aq<MapView.b> paramaq)
    {
      this.g = paramaq;
      if ((this.g != null) && (ab() == null));
      try
      {
        IMapViewDelegate localIMapViewDelegate = q.a(this.mContext).a(ak.b(this.mContext), this.dK);
        this.g.b(new MapView.b(this.dJ, localIMapViewDelegate));
        label66: return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
      catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
      {
        break label66;
      }
    }
  }

  static final class b
    implements LifecycleDelegate
  {
    private final ViewGroup dX;
    private final IMapViewDelegate dY;
    private View dZ;

    public b(ViewGroup paramViewGroup, IMapViewDelegate paramIMapViewDelegate)
    {
      this.dY = ((IMapViewDelegate)bj.c(paramIMapViewDelegate));
      this.dX = ((ViewGroup)bj.c(paramViewGroup));
    }

    public final void onCreate(Bundle paramBundle)
    {
      try
      {
        this.dY.onCreate(paramBundle);
        this.dZ = ((View)ak.a(this.dY.getView()));
        this.dX.removeAllViews();
        this.dX.addView(this.dZ);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
    }

    public final void onDestroy()
    {
      try
      {
        this.dY.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onDestroyView()
    {
      throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
    }

    public final void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
    }

    public final void onLowMemory()
    {
      try
      {
        this.dY.onLowMemory();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onPause()
    {
      try
      {
        this.dY.onPause();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onResume()
    {
      try
      {
        this.dY.onResume();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onSaveInstanceState(Bundle paramBundle)
    {
      try
      {
        this.dY.onSaveInstanceState(paramBundle);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.MapView
 * JD-Core Version:    0.6.2
 */