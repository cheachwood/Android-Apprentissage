package com.google.android.apps.plus.phone;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class UrlGatewayActivity extends EsUrlGatewayActivity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (isFinishing());
    while (true)
    {
      return;
      if (this.mRequestType == 0)
      {
        redirectToBrowser();
      }
      else if (isReadyToRedirect())
      {
        redirect();
      }
      else
      {
        Intent localIntent = getIntent();
        localIntent.setComponent(new ComponentName(this, UrlGatewayLoaderActivity.class));
        localIntent.setFlags(1115684864);
        startActivity(localIntent);
        finish();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.UrlGatewayActivity
 * JD-Core Version:    0.6.2
 */