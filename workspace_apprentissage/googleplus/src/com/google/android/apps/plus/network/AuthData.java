package com.google.android.apps.plus.network;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.util.Pair;
import com.google.android.apps.plus.util.AccountsUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AuthData
{
  private static final Map<Pair<String, String>, String> sActionTokens = newSynchronizedMap();
  private static final Map<Pair<String, String>, String> sAuthTokens = newSynchronizedMap();

  public static String getActionToken(String paramString1, String paramString2)
  {
    String str = (String)sActionTokens.get(new Pair(paramString1, paramString2));
    if (str == null)
      str = "XXX";
    return str;
  }

  public static String getAuthToken(Context paramContext, String paramString1, String paramString2)
    throws OperationCanceledException, IOException, AuthenticatorException
  {
    Pair localPair = new Pair(paramString1, paramString2);
    String str = (String)sAuthTokens.get(localPair);
    if (str == null)
    {
      str = AccountsUtil.getAuthToken(paramContext, paramString1, paramString2);
      sAuthTokens.put(localPair, str);
    }
    return str;
  }

  public static void invalidateAuthToken(Context paramContext, String paramString1, String paramString2)
    throws OperationCanceledException, IOException, AuthenticatorException
  {
    Pair localPair = new Pair(paramString1, paramString2);
    String str = (String)sAuthTokens.remove(localPair);
    if (str == null)
      str = AccountsUtil.getAuthToken(paramContext, paramString1, paramString2);
    AccountsUtil.invalidateAuthToken(paramContext, str);
  }

  private static <K, V> Map<K, V> newSynchronizedMap()
  {
    return Collections.synchronizedMap(new HashMap());
  }

  public static void setActionToken(String paramString1, String paramString2, String paramString3)
  {
    sActionTokens.put(new Pair(paramString1, paramString2), paramString3);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.AuthData
 * JD-Core Version:    0.6.2
 */