package com.google.android.apps.plus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.util.PlayStoreInstaller;

public class PackageAddedReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (Log.isLoggable("DeepLinking", 3))
      Log.d("DeepLinking", "PackageAddedReceiver.onReceive() " + paramIntent);
    String str1 = paramIntent.getDataString();
    if (str1 != null)
    {
      String str2 = str1.substring(8);
      if (PlayStoreInstaller.isPackageInstalled(paramContext.getPackageManager(), str2))
      {
        EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
        if (localEsAccount != null)
          EsService.notifyDeepLinkingInstall(paramContext, localEsAccount, str2);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.PackageAddedReceiver
 * JD-Core Version:    0.6.2
 */