package com.google.android.apps.plus.service;

import com.android.volley.VolleyError;

public final class VolleyOutOfMemoryError extends VolleyError
{
  public VolleyOutOfMemoryError(OutOfMemoryError paramOutOfMemoryError)
  {
    super(paramOutOfMemoryError);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.VolleyOutOfMemoryError
 * JD-Core Version:    0.6.2
 */