package com.google.android.gms.internal;

import android.graphics.Color;
import android.net.Uri;
import android.net.Uri.Builder;

public final class e
{
  public static final int i = Color.argb(30, 0, 0, 0);
  private static final Uri j = new Uri.Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("drawable").build();

  public static Uri a(String paramString)
  {
    bj.a(paramString, "Resource name must not be null.");
    return j.buildUpon().appendPath(paramString).build();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.e
 * JD-Core Version:    0.6.2
 */