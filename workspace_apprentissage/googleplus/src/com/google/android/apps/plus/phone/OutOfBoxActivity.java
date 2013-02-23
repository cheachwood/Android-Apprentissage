package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.OutOfBoxFragment;
import com.google.android.apps.plus.oob.OutOfBoxResponseParcelable;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;

public class OutOfBoxActivity extends EsFragmentActivity
{
  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.out_of_box_activity);
    showTitlebar(false);
    setTitlebarTitle(getString(R.string.app_name));
    EsAccountsData.setHasVisitedOob(this, true);
    MobileOutOfBoxResponse localMobileOutOfBoxResponse;
    if (paramBundle == null)
    {
      EsAccount localEsAccount = getAccount();
      OutOfBoxResponseParcelable localOutOfBoxResponseParcelable = (OutOfBoxResponseParcelable)getIntent().getParcelableExtra("network_oob");
      if (localOutOfBoxResponseParcelable == null)
        break label125;
      localMobileOutOfBoxResponse = localOutOfBoxResponseParcelable.getResponse();
      if ((localEsAccount == null) || (localMobileOutOfBoxResponse == null))
        break label131;
      String str1 = getIntent().getStringExtra("oob_origin");
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      String str2 = OutOfBoxFragment.createInitialTag();
      localFragmentTransaction.add(R.id.oob_container, OutOfBoxFragment.newInstance(localEsAccount, localMobileOutOfBoxResponse, str1), str2);
      localFragmentTransaction.commit();
    }
    while (true)
    {
      return;
      label125: localMobileOutOfBoxResponse = null;
      break;
      label131: setResult(0);
      finish();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OutOfBoxActivity
 * JD-Core Version:    0.6.2
 */