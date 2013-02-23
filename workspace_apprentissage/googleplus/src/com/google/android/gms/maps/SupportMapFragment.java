package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.am;
import com.google.android.gms.internal.aq;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.d;
import com.google.android.gms.internal.f;
import com.google.android.gms.internal.h;
import com.google.android.gms.internal.q;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class SupportMapFragment extends Fragment
{
  private final b bg = new b(this);

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    b.a(this.bg, paramActivity);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.bg.onCreate(paramBundle);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return this.bg.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }

  public final void onDestroy()
  {
    this.bg.onDestroy();
    super.onDestroy();
  }

  public final void onDestroyView()
  {
    this.bg.onDestroyView();
    super.onDestroyView();
  }

  public final void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
    b.a(this.bg, paramActivity);
    GoogleMapOptions localGoogleMapOptions = GoogleMapOptions.createFromAttributes(paramActivity, paramAttributeSet);
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MapOptions", localGoogleMapOptions);
    this.bg.onInflate(paramActivity, localBundle, paramBundle);
  }

  public void onLowMemory()
  {
    this.bg.onLowMemory();
    super.onLowMemory();
  }

  public final void onPause()
  {
    this.bg.onPause();
    super.onPause();
  }

  public final void onResume()
  {
    super.onResume();
    this.bg.onResume();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    this.bg.onSaveInstanceState(paramBundle);
    super.onSaveInstanceState(paramBundle);
  }

  public final void setArguments(Bundle paramBundle)
  {
    super.setArguments(paramBundle);
  }

  static final class a
    implements LifecycleDelegate
  {
    private final Fragment aC;
    private final IMapFragmentDelegate aD;

    public a(Fragment paramFragment, IMapFragmentDelegate paramIMapFragmentDelegate)
    {
      this.aD = ((IMapFragmentDelegate)bj.c(paramIMapFragmentDelegate));
      this.aC = ((Fragment)bj.c(paramFragment));
    }

    public final void onCreate(Bundle paramBundle)
    {
      if (paramBundle == null);
      try
      {
        paramBundle = new Bundle();
        Bundle localBundle = this.aC.getArguments();
        if ((localBundle != null) && (localBundle.containsKey("MapOptions")))
          h.a(paramBundle, "MapOptions", localBundle.getParcelable("MapOptions"));
        this.aD.onCreate(paramBundle);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      try
      {
        f localf = this.aD.onCreateView(ak.b(paramLayoutInflater), ak.b(paramViewGroup), paramBundle);
        return (View)ak.a(localf);
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onDestroy()
    {
      try
      {
        this.aD.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onDestroyView()
    {
      try
      {
        this.aD.onDestroyView();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      GoogleMapOptions localGoogleMapOptions = (GoogleMapOptions)paramBundle1.getParcelable("MapOptions");
      try
      {
        this.aD.onInflate(ak.b(paramActivity), localGoogleMapOptions, paramBundle2);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public final void onLowMemory()
    {
      try
      {
        this.aD.onLowMemory();
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
        this.aD.onPause();
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
        this.aD.onResume();
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
        this.aD.onSaveInstanceState(paramBundle);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
  }

  static final class b extends d<SupportMapFragment.a>
  {
    private final Fragment aC;
    protected aq<SupportMapFragment.a> g;
    private Activity h;

    b(Fragment paramFragment)
    {
      this.aC = paramFragment;
    }

    private void a()
    {
      if ((this.h != null) && (this.g != null) && (ab() == null))
        try
        {
          Activity localActivity = this.h;
          bj.c(localActivity);
          am localam = q.a(localActivity);
          try
          {
            CameraUpdateFactory.a(localam.q());
            BitmapDescriptorFactory.a(localam.r());
            IMapFragmentDelegate localIMapFragmentDelegate = q.a(this.h).c(ak.b(this.h));
            this.g.b(new SupportMapFragment.a(this.aC, localIMapFragmentDelegate));
          }
          catch (RemoteException localRemoteException2)
          {
            throw new RuntimeRemoteException(localRemoteException2);
          }
        }
        catch (RemoteException localRemoteException1)
        {
          throw new RuntimeRemoteException(localRemoteException1);
        }
        catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
        {
        }
    }

    protected final void a(aq<SupportMapFragment.a> paramaq)
    {
      this.g = paramaq;
      a();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.SupportMapFragment
 * JD-Core Version:    0.6.2
 */