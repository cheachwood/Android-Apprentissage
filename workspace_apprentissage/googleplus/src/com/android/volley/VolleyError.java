package com.android.volley;

public class VolleyError extends Exception
{
  public final NetworkResponse networkResponse;

  public VolleyError()
  {
    this.networkResponse = null;
  }

  public VolleyError(NetworkResponse paramNetworkResponse)
  {
    this.networkResponse = paramNetworkResponse;
  }

  public VolleyError(String paramString)
  {
    super(paramString);
    this.networkResponse = null;
  }

  public VolleyError(Throwable paramThrowable)
  {
    super(paramThrowable);
    this.networkResponse = null;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.VolleyError
 * JD-Core Version:    0.6.2
 */