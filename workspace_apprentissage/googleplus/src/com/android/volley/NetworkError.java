package com.android.volley;

public class NetworkError extends VolleyError
{
  public NetworkError()
  {
  }

  public NetworkError(NetworkResponse paramNetworkResponse)
  {
    super(null);
  }

  public NetworkError(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.NetworkError
 * JD-Core Version:    0.6.2
 */