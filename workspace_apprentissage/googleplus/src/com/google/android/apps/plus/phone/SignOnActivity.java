package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.external.PlatformContractUtils;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.service.EsService;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;
import java.util.Collections;

public class SignOnActivity extends BaseAccountSelectionActivity
{
  private ComponentName mCallingActivity;

  public static boolean finishIfNoAccount(Activity paramActivity, EsAccount paramEsAccount)
  {
    boolean bool = true;
    if ((paramEsAccount != null) && (!paramEsAccount.equals(EsService.getActiveAccount(paramActivity))))
    {
      paramActivity.setResult(0);
      if (paramActivity.getIntent().getBooleanExtra("from_signup", false))
      {
        Intent localIntent1 = new Intent();
        localIntent1.putExtra("no_account", bool);
        paramActivity.setResult(0, localIntent1);
        paramActivity.finish();
      }
    }
    while (true)
    {
      return bool;
      Intent localIntent2 = (Intent)paramActivity.getIntent().getParcelableExtra("intent");
      if (localIntent2 != null)
      {
        paramActivity.startActivity(localIntent2);
        paramActivity.finish();
      }
      else
      {
        paramActivity.finish();
        continue;
        bool = false;
      }
    }
  }

  private void fireIntent(int paramInt)
  {
    Intent localIntent1 = getIntent();
    String str;
    Intent localIntent2;
    if (this.mCallingActivity == null)
    {
      str = null;
      localIntent2 = Intents.getTargetIntent(this, localIntent1, str);
      if (localIntent2 != null)
        break label48;
      setResult(paramInt);
      finish();
    }
    while (true)
    {
      return;
      str = this.mCallingActivity.getPackageName();
      break;
      label48: if (this.mCallingActivity == null)
      {
        startActivity(localIntent2);
        finish();
      }
      else
      {
        localIntent2.putExtra("from_signup", true);
        localIntent2.setFlags(0xFDFFFFFF & localIntent2.getFlags());
        startActivityForResult(localIntent2, 11);
      }
    }
  }

  private void recordEvent$7c4c9d3f(EsAccount paramEsAccount)
  {
    Collections.emptyMap();
    if (this.mCallingActivity == null);
    for (String str = null; ; str = this.mCallingActivity.getPackageName())
    {
      if (str != null)
        PlatformContractUtils.getCallingPackageAnalytics(new ApiaryApiInfo(null, null, null, null, null, new ApiaryApiInfo(null, null, str, PlatformContractUtils.getCertificate(str, getPackageManager()), null)));
      EsAnalytics.recordEvent(this, paramEsAccount, getAnalyticsInfo$7d6d37aa(), OzActions.PLATFORM_CONNECT_SELECT_ACCOUNT);
      return;
    }
  }

  protected final String getUpgradeOrigin()
  {
    if (Intents.getTargetIntent(this, getIntent(), null).getComponent().getClassName().equals(PlusOneActivity.class.getName()));
    for (String str = "PLUS_ONE"; ; str = "DEFAULT")
      return str;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PLATFORM_THIRD_PARTY_APP;
  }

  protected final void onAccountSet(MobileOutOfBoxResponse paramMobileOutOfBoxResponse, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData)
  {
    Intent localIntent = Intents.getOobIntent(this, paramEsAccount, paramMobileOutOfBoxResponse, paramAccountSettingsData, getUpgradeOrigin());
    if (paramEsAccount != null)
    {
      recordEvent$7c4c9d3f(paramEsAccount);
      if (paramMobileOutOfBoxResponse != null)
        recordEvent$7c4c9d3f(paramEsAccount);
    }
    if (localIntent != null)
      startActivityForResult(localIntent, 10);
    while (true)
    {
      return;
      fireIntent(-1);
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    case 10:
    case 11:
    }
    while (true)
    {
      return;
      if (paramInt2 == -1)
      {
        fireIntent(paramInt2);
      }
      else
      {
        setResult(paramInt2);
        finish();
        continue;
        if ((paramIntent != null) && (paramIntent.getBooleanExtra("no_account", false)))
        {
          EsAccount localEsAccount = EsService.getActiveAccount(this);
          if ((localEsAccount == null) || (!localEsAccount.hasGaiaId()))
            showAccountList();
          else
            fireIntent(paramInt2);
        }
        else
        {
          setResult(paramInt2, paramIntent);
          finish();
        }
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mCallingActivity = ((ComponentName)paramBundle.getParcelable("SignOnActivity#callingActivity"));
      EsAccount localEsAccount = EsService.getActiveAccount(this);
      if ((localEsAccount != null) && (localEsAccount.hasGaiaId()))
        break label55;
      showAccountSelectionOrUpgradeAccount(paramBundle);
    }
    while (true)
    {
      return;
      this.mCallingActivity = getCallingActivity();
      break;
      label55: if (paramBundle == null)
        fireIntent(-1);
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("SignOnActivity#callingActivity", this.mCallingActivity);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.SignOnActivity
 * JD-Core Version:    0.6.2
 */