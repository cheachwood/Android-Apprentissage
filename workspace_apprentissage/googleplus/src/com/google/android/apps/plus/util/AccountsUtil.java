package com.google.android.apps.plus.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.NetworkException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

public final class AccountsUtil
{
  public static void addAccount(Activity paramActivity)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("allowSkip", false);
    localBundle.putCharSequence("introMessage", paramActivity.getString(R.string.create_account_prompt));
    AccountManager.get(paramActivity).addAccount("com.google", "webupdates", null, localBundle, paramActivity, null, null);
  }

  public static List<Account> getAccounts(Context paramContext)
  {
    String str = Property.AUTH_EMAIL.get();
    ArrayList localArrayList;
    if (TextUtils.isEmpty(str))
    {
      Account[] arrayOfAccount = AccountManager.get(paramContext).getAccountsByType("com.google");
      localArrayList = new ArrayList(arrayOfAccount.length);
      int i = arrayOfAccount.length;
      for (int j = 0; j < i; j++)
      {
        Account localAccount = arrayOfAccount[j];
        if (!localAccount.name.toLowerCase().endsWith("@youtube.com"))
          localArrayList.add(localAccount);
      }
    }
    for (List localList = Collections.unmodifiableList(localArrayList); ; localList = Collections.singletonList(new Account(str, "com.google")))
      return localList;
  }

  public static String getAuthToken(Context paramContext, String paramString1, String paramString2)
    throws AuthenticatorException, NetworkException, OperationCanceledException
  {
    String str1 = Property.AUTH_URL.get();
    String str2 = Property.AUTH_EMAIL.get();
    String str3 = Property.AUTH_PASSWORD.get();
    String str4;
    if ((str1 != null) && (paramString1.equals(str2)) && (str3 != null))
    {
      Properties localProperties;
      try
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new BasicNameValuePair("Email", str2));
        localArrayList.add(new BasicNameValuePair("Passwd", str3));
        localArrayList.add(new BasicNameValuePair("accountType", "GOOGLE"));
        localArrayList.add(new BasicNameValuePair("service", paramString2));
        localProperties = post(str1, new UrlEncodedFormEntity(localArrayList));
        String str5 = localProperties.getProperty("Error");
        if (str5 != null)
          throw new IOException(str5);
      }
      catch (IOException localIOException2)
      {
        throw new NetworkException("Cannot get auth token", localIOException2);
      }
      String str6 = localProperties.getProperty("Auth");
      str4 = str6;
      return str4;
    }
    AccountManager localAccountManager = AccountManager.get(paramContext);
    Account[] arrayOfAccount = localAccountManager.getAccountsByType("com.google");
    for (int i = 0; ; i++)
      while (true)
      {
        if (i >= arrayOfAccount.length)
          break label276;
        if (arrayOfAccount[i].name.equals(paramString1))
          try
          {
            str4 = localAccountManager.blockingGetAuthToken(arrayOfAccount[i], paramString2, true);
            if (str4 != null)
              break;
            throw new NetworkException("Cannot get auth token");
          }
          catch (IOException localIOException1)
          {
            throw new NetworkException("Cannot get auth token", localIOException1);
          }
      }
    label276: throw new IllegalArgumentException("Account not found: " + paramString1);
  }

  public static void invalidateAuthToken(Context paramContext, String paramString)
  {
    AccountManager.get(paramContext).invalidateAuthToken("com.google", paramString);
  }

  public static boolean isRestrictedCircleForAccount(EsAccount paramEsAccount, int paramInt)
  {
    if ((paramEsAccount.isChild()) && ((paramInt == 9) || (paramInt == 7)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static Account newAccount(String paramString)
  {
    return new Account(paramString, "com.google");
  }

  private static Properties post(String paramString, UrlEncodedFormEntity paramUrlEncodedFormEntity)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
    localHttpURLConnection.setInstanceFollowRedirects(false);
    localHttpURLConnection.setDoOutput(true);
    localHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    localHttpURLConnection.setRequestProperty("Content-Length", Long.toString(paramUrlEncodedFormEntity.getContentLength()));
    try
    {
      OutputStream localOutputStream = localHttpURLConnection.getOutputStream();
      paramUrlEncodedFormEntity.writeTo(localOutputStream);
      localOutputStream.flush();
      int i = localHttpURLConnection.getResponseCode();
      if (i == 200)
      {
        Properties localProperties = new Properties();
        localProperties.load(new BufferedInputStream(localHttpURLConnection.getInputStream()));
        return localProperties;
      }
      throw new IOException("Unexpected HTTP response code: " + i);
    }
    finally
    {
      localHttpURLConnection.disconnect();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.AccountsUtil
 * JD-Core Version:    0.6.2
 */