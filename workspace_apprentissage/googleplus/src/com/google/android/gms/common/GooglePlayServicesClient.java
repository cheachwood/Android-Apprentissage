package com.google.android.gms.common;

public abstract interface GooglePlayServicesClient
{
  public static abstract interface ConnectionCallbacks
  {
    public abstract void onConnected();

    public abstract void onDisconnected();
  }

  public static abstract interface OnConnectionFailedListener
  {
    public abstract void onConnectionFailed$5d4cef71();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GooglePlayServicesClient
 * JD-Core Version:    0.6.2
 */