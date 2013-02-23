package com.android.volley;

public abstract interface ResponseDelivery
{
  public abstract void postError(Request<?> paramRequest, VolleyError paramVolleyError);

  public abstract void postResponse(Request<?> paramRequest, Response<?> paramResponse);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.ResponseDelivery
 * JD-Core Version:    0.6.2
 */