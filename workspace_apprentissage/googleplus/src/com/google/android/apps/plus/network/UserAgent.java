package com.google.android.apps.plus.network;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import java.util.Locale;

public final class UserAgent
{
  public static String from(Context paramContext)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getPackageName());
      localStringBuilder.append('/');
      localStringBuilder.append(ClientVersion.from(paramContext));
      localStringBuilder.append(" (Linux; U; Android ");
      localStringBuilder.append(Build.VERSION.RELEASE);
      localStringBuilder.append("; ");
      localStringBuilder.append(Locale.getDefault().toString());
      String str1 = Build.MODEL;
      if (str1.length() > 0)
      {
        localStringBuilder.append("; ");
        localStringBuilder.append(str1);
      }
      String str2 = Build.ID;
      if (str2.length() > 0)
      {
        localStringBuilder.append("; Build/");
        localStringBuilder.append(str2);
      }
      localStringBuilder.append(')');
      String str3 = localStringBuilder.toString();
      return str3;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.UserAgent
 * JD-Core Version:    0.6.2
 */