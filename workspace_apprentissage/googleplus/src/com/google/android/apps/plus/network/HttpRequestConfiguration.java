package com.google.android.apps.plus.network;

import org.apache.http.client.methods.HttpRequestBase;

public abstract interface HttpRequestConfiguration
{
  public abstract void addHeaders(HttpRequestBase paramHttpRequestBase);

  public abstract void invalidateAuthToken();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.HttpRequestConfiguration
 * JD-Core Version:    0.6.2
 */