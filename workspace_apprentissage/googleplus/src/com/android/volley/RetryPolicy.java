package com.android.volley;

public abstract interface RetryPolicy
{
  public abstract int getCurrentRetryCount();

  public abstract int getCurrentTimeout();

  public abstract void retry(VolleyError paramVolleyError)
    throws VolleyError;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.RetryPolicy
 * JD-Core Version:    0.6.2
 */