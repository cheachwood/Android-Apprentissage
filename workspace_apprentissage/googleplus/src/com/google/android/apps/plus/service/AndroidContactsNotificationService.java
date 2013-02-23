package com.google.android.apps.plus.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;

public class AndroidContactsNotificationService extends IntentService
{
  public AndroidContactsNotificationService()
  {
    super("ContactsNotificationSvc");
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    Uri localUri = paramIntent.getData();
    if (EsLog.isLoggable("ContactsNotificationSvc", 4))
      Log.i("ContactsNotificationSvc", "Contact opened in Contacts: " + localUri);
    AndroidContactsSync.syncRawContact(this, localUri);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.AndroidContactsNotificationService
 * JD-Core Version:    0.6.2
 */