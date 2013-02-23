package com.google.android.gms.internal;

import android.content.UriMatcher;
import android.net.Uri;
import android.net.Uri.Builder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class bh
{
  private static final Pattern dj = Pattern.compile("s[0-9]+");
  private static final UriMatcher dk;

  static
  {
    UriMatcher localUriMatcher = new UriMatcher(-1);
    dk = localUriMatcher;
    localUriMatcher.addURI("com.google.android.gms", "drawable/*", 0);
    dk.addURI("com.google.android.gms.plus", "images", 1);
    dk.addURI("com.google.android.gms.plus", "avatars/*", 2);
  }

  public static Uri a(Uri paramUri, String paramString)
  {
    Uri.Builder localBuilder = new Uri.Builder().scheme(paramUri.getScheme()).authority(paramUri.getAuthority());
    List localList = paramUri.getPathSegments();
    int i = localList.size();
    int j = 0;
    int k = 0;
    if (j < i)
    {
      if (dj.matcher((CharSequence)localList.get(j)).matches())
      {
        localBuilder.appendPath("s" + paramString);
        k = 1;
      }
      while (true)
      {
        j++;
        break;
        if ((k == 0) && (j == i - 1))
        {
          localBuilder.appendPath("s" + paramString);
          k = 1;
        }
        localBuilder.appendPath((String)localList.get(j));
      }
    }
    return localBuilder.build();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bh
 * JD-Core Version:    0.6.2
 */