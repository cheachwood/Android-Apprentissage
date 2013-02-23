package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.LinkedList;

public abstract class d<T extends LifecycleDelegate>
{
  private static final String TAG = d.class.getSimpleName();
  private T cZ;
  private Bundle da;
  private LinkedList<a> db;
  private final aq<T> dc = new k(this);

  private void a(Bundle paramBundle, a parama)
  {
    if (this.cZ != null)
    {
      parama.a$6728a24f();
      return;
    }
    if (this.db == null)
      this.db = new LinkedList();
    this.db.add(parama);
    if (paramBundle != null)
    {
      if (this.da != null)
        break label79;
      this.da = ((Bundle)paramBundle.clone());
    }
    while (true)
    {
      a(this.dc);
      break;
      label79: this.da.putAll(paramBundle);
    }
  }

  private void h(int paramInt)
  {
    while ((!this.db.isEmpty()) && (((a)this.db.getLast()).getState() >= paramInt))
      this.db.removeLast();
  }

  protected abstract void a(aq<T> paramaq);

  public final T ab()
  {
    return this.cZ;
  }

  public final void onCreate(final Bundle paramBundle)
  {
    a(paramBundle, new a()
    {
      public final void a$6728a24f()
      {
        d.b(d.this).onCreate(paramBundle);
      }

      public final int getState()
      {
        return 1;
      }
    });
  }

  public final View onCreateView(final LayoutInflater paramLayoutInflater, final ViewGroup paramViewGroup, final Bundle paramBundle)
  {
    final FrameLayout localFrameLayout = new FrameLayout(paramLayoutInflater.getContext());
    a(paramBundle, new a()
    {
      public final void a$6728a24f()
      {
        localFrameLayout.removeAllViews();
        localFrameLayout.addView(d.b(d.this).onCreateView(paramLayoutInflater, paramViewGroup, paramBundle));
      }

      public final int getState()
      {
        return 2;
      }
    });
    if (this.cZ == null)
    {
      final Context localContext = localFrameLayout.getContext();
      final int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(localContext);
      String str1 = GooglePlayServicesUtil.b(localContext, i, -1);
      String str2 = GooglePlayServicesUtil.a(localContext, i);
      LinearLayout localLinearLayout = new LinearLayout(localFrameLayout.getContext());
      localLinearLayout.setOrientation(1);
      localLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
      localFrameLayout.addView(localLinearLayout);
      TextView localTextView = new TextView(localFrameLayout.getContext());
      localTextView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
      localTextView.setText(str1);
      localLinearLayout.addView(localTextView);
      if (str2 != null)
      {
        Button localButton = new Button(localContext);
        localButton.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        localButton.setText(str2);
        localLinearLayout.addView(localButton);
        localButton.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            localContext.startActivity(GooglePlayServicesUtil.a(localContext, i, -1));
          }
        });
      }
    }
    return localFrameLayout;
  }

  public final void onDestroy()
  {
    if (this.cZ != null)
      this.cZ.onDestroy();
    while (true)
    {
      return;
      h(1);
    }
  }

  public final void onDestroyView()
  {
    if (this.cZ != null)
      this.cZ.onDestroyView();
    while (true)
    {
      return;
      h(2);
    }
  }

  public final void onInflate(final Activity paramActivity, final Bundle paramBundle1, final Bundle paramBundle2)
  {
    a(paramBundle2, new a()
    {
      public final void a$6728a24f()
      {
        d.b(d.this).onInflate(paramActivity, paramBundle1, paramBundle2);
      }

      public final int getState()
      {
        return 0;
      }
    });
  }

  public final void onLowMemory()
  {
    if (this.cZ != null)
      this.cZ.onLowMemory();
  }

  public final void onPause()
  {
    if (this.cZ != null)
      this.cZ.onPause();
    while (true)
    {
      return;
      h(3);
    }
  }

  public final void onResume()
  {
    a(null, new a()
    {
      public final void a$6728a24f()
      {
        d.b(d.this).onResume();
      }

      public final int getState()
      {
        return 3;
      }
    });
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.cZ != null)
      this.cZ.onSaveInstanceState(paramBundle);
    while (true)
    {
      return;
      if (this.da != null)
        paramBundle.putAll(this.da);
    }
  }

  private static abstract interface a
  {
    public abstract void a$6728a24f();

    public abstract int getState();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.d
 * JD-Core Version:    0.6.2
 */