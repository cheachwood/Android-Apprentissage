package com.google.android.apps.plus.iu;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

final class Authorizer
{
  private final AccountManager mAccountManager;

  public Authorizer(Context paramContext)
  {
    this.mAccountManager = AccountManager.get(paramContext);
  }

  public final String getAuthToken(String paramString1, String paramString2)
    throws OperationCanceledException, IOException, AuthenticatorException
  {
    int i = 0;
    if (EsLog.isLoggable("UploaderAuthorizer", 3))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString2;
      arrayOfObject[1] = Utils.maskDebugInfo(paramString1);
      Log.d("UploaderAuthorizer", String.format("Authorizer.getAuthToken: authTokenType=%s; account=%s;", arrayOfObject));
    }
    Account[] arrayOfAccount = this.mAccountManager.getAccountsByType("com.google");
    int j = arrayOfAccount.length;
    Account localAccount;
    label83: Bundle localBundle;
    String str;
    if (i < j)
    {
      localAccount = arrayOfAccount[i];
      if (localAccount.name.equals(paramString1))
      {
        if (localAccount == null)
          break label152;
        localBundle = (Bundle)this.mAccountManager.getAuthToken(localAccount, paramString2, true, null, null).getResult(30000L, TimeUnit.MILLISECONDS);
        str = null;
        if (localBundle != null)
          break label140;
      }
    }
    while (true)
    {
      return str;
      i++;
      break;
      localAccount = null;
      break label83;
      label140: str = localBundle.getString("authtoken");
    }
    label152: throw new AuthenticatorException("account doesn't exist");
  }

  public final String getFreshAuthToken(String paramString1, String paramString2, String paramString3)
    throws OperationCanceledException, IOException, AuthenticatorException
  {
    if (EsLog.isLoggable("UploaderAuthorizer", 3))
      Log.d("UploaderAuthorizer", "Refreshing authToken for " + Utils.maskDebugInfo(paramString1));
    this.mAccountManager.invalidateAuthToken("com.google", paramString3);
    return getAuthToken(paramString1, paramString2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.Authorizer
 * JD-Core Version:    0.6.2
 */