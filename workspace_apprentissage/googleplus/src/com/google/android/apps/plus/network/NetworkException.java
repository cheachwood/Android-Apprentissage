package com.google.android.apps.plus.network;

import java.io.IOException;

public final class NetworkException extends IOException
{
  private static final long serialVersionUID = -2558890850533726919L;

  public NetworkException(String paramString)
  {
    super(paramString);
  }

  public NetworkException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    initCause(paramThrowable);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.NetworkException
 * JD-Core Version:    0.6.2
 */