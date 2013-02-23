package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.b;
import com.google.android.gms.plus.PlusClient.c;

final class y
  implements PlusClient.c
{
  y(g paramg)
  {
  }

  public final void a(ConnectionResult paramConnectionResult, av paramav)
  {
    if ((paramConnectionResult.isSuccess()) && (paramav != null))
    {
      g.a(this.k, paramav);
      PlusClient.b localb = g.c(this.k);
      null.a(localb, null);
      if (this.k.dy)
      {
        if (Log.isLoggable("PlusOneButtonWithPopup", 2))
          Log.v("PlusOneButtonWithPopup", "onSignUpStateLoaded: performing pending click");
        this.k.cz = false;
        this.k.performClick();
        this.k.dy = false;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.y
 * JD-Core Version:    0.6.2
 */