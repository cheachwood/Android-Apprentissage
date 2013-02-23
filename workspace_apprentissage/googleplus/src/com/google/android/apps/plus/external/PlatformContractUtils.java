package com.google.android.apps.plus.external;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Base64;
import android.util.Log;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.util.EsLog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public final class PlatformContractUtils
{
  public static Map<String, String> getCallingPackageAnalytics(ApiaryApiInfo paramApiaryApiInfo)
  {
    HashMap localHashMap = new HashMap();
    if ((paramApiaryApiInfo != null) && (paramApiaryApiInfo.getSourceInfo() != null))
      localHashMap.put("CONTAINER_URL", getContainerUrl(paramApiaryApiInfo));
    return localHashMap;
  }

  public static String getCertificate(String paramString, PackageManager paramPackageManager)
  {
    try
    {
      PackageInfo localPackageInfo = paramPackageManager.getPackageInfo(paramString, 64);
      Signature[] arrayOfSignature = localPackageInfo.signatures;
      localObject = null;
      if (arrayOfSignature != null)
      {
        int i = localPackageInfo.signatures.length;
        localObject = null;
        if (i <= 0);
      }
      try
      {
        byte[] arrayOfByte1 = localPackageInfo.signatures[0].toByteArray();
        MessageDigest localMessageDigest = MessageDigest.getInstance("SHA1");
        if (localMessageDigest == null)
          localObject = null;
        while (true)
        {
          if (localObject == null)
            localObject = "0";
          return localObject;
          byte[] arrayOfByte2 = localMessageDigest.digest(arrayOfByte1);
          if (arrayOfByte2 == null)
          {
            localObject = null;
          }
          else
          {
            String str = Base64.encodeToString(arrayOfByte2, 2);
            localObject = str;
          }
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        while (true)
        {
          boolean bool = EsLog.isLoggable("PlatformContractUtils", 5);
          localObject = null;
          if (bool)
          {
            Log.w("PlatformContractUtils", "Unable to compute digest, returning zeros");
            localObject = null;
          }
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        Object localObject = null;
    }
  }

  public static String getContainerUrl(ApiaryApiInfo paramApiaryApiInfo)
  {
    if (paramApiaryApiInfo.getSourceInfo() != null)
      paramApiaryApiInfo = paramApiaryApiInfo.getSourceInfo();
    if (paramApiaryApiInfo.getCertificate() != null);
    for (String str1 = paramApiaryApiInfo.getCertificate(); ; str1 = "0")
    {
      String str2 = paramApiaryApiInfo.getClientId();
      String str3 = paramApiaryApiInfo.getApiKey();
      String str4 = paramApiaryApiInfo.getPackageName();
      Uri.Builder localBuilder = Uri.parse("http://" + Uri.encode(str1) + ".apps.googleusercontent.com/").buildUpon();
      if (str2 != null)
        localBuilder.appendQueryParameter("client_id", str2);
      if (str3 != null)
        localBuilder.appendQueryParameter("api_key", str3);
      if (str4 != null)
        localBuilder.appendQueryParameter("pkg", str4);
      return localBuilder.build().toString();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.external.PlatformContractUtils
 * JD-Core Version:    0.6.2
 */