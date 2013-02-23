package com.google.android.apps.plus.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Log;
import java.util.Locale;

public final class HelpUrl
{
  public static Uri getHelpUrl(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      throw new IllegalArgumentException("getHelpUrl(): fromWhere must be non-empty");
    String str = "http://www.google.com/support/mobile/?hl=%locale%";
    if (str.contains("%locale%"))
    {
      Locale localLocale = Locale.getDefault();
      str = str.replace("%locale%", localLocale.getLanguage() + "-" + localLocale.getCountry().toLowerCase());
    }
    Uri.Builder localBuilder = Uri.parse(str).buildUpon();
    localBuilder.appendQueryParameter("p", paramString);
    try
    {
      localBuilder.appendQueryParameter("version", String.valueOf(paramContext.getPackageManager().getPackageInfo(paramContext.getApplicationInfo().packageName, 0).versionCode));
      return localBuilder.build();
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        Log.e("HelpUrl", "Error finding package " + paramContext.getApplicationInfo().packageName);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.HelpUrl
 * JD-Core Version:    0.6.2
 */