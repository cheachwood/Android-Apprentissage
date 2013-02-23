package com.google.android.apps.plus.service;

import com.android.volley.toolbox.ByteArrayPool;

public final class SharedByteArrayPool
{
  private static ByteArrayPool sByteArrayPool;

  public static ByteArrayPool getInstance()
  {
    if (sByteArrayPool == null)
      sByteArrayPool = new ByteArrayPool(131072);
    return sByteArrayPool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.SharedByteArrayPool
 * JD-Core Version:    0.6.2
 */