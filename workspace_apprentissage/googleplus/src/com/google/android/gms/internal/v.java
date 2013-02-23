package com.google.android.gms.internal;

import android.widget.PopupWindow;

final class v
  implements Runnable
{
  v(g paramg)
  {
  }

  public final void run()
  {
    if ((g.a(this.k)) && (g.b(this.k) != null))
    {
      g.b(this.k).dismiss();
      g.a(this.k, null);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.v
 * JD-Core Version:    0.6.2
 */