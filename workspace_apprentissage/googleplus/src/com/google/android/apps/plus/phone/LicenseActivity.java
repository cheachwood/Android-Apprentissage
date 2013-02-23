package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;

public class LicenseActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.license_activity);
    ((WebView)findViewById(R.id.content)).loadUrl("file:///android_asset/licenses.html");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.LicenseActivity
 * JD-Core Version:    0.6.2
 */