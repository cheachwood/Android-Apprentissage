package com.google.android.gms.common;

import android.app.PendingIntent;

public final class ConnectionResult
{
  public static final ConnectionResult aY = new ConnectionResult(0, null);
  private final PendingIntent aZ;
  private final int ba;

  public ConnectionResult(int paramInt, PendingIntent paramPendingIntent)
  {
    this.ba = paramInt;
    this.aZ = paramPendingIntent;
  }

  public final boolean isSuccess()
  {
    if (this.ba == 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.ConnectionResult
 * JD-Core Version:    0.6.2
 */