package com.google.android.apps.plus.util;

public final class ResourceRedirector extends BaseResourceRedirector
{
  private static ResourceRedirector sInstance;

  public static ResourceRedirector getInstance()
  {
    if (sInstance == null)
      sInstance = new ResourceRedirector();
    return sInstance;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.ResourceRedirector
 * JD-Core Version:    0.6.2
 */