package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.OobContactsSyncFragment;
import com.google.android.apps.plus.views.BottomActionBar;

public class ContactsSyncConfigActivity extends EsFragmentActivity
  implements View.OnClickListener
{
  private OobContactsSyncFragment mFragment;

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CONTACTS_SYNC_CONFIG;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof OobContactsSyncFragment))
      this.mFragment = ((OobContactsSyncFragment)paramFragment);
  }

  public void onClick(View paramView)
  {
    if ((paramView.getId() == 16908313) && (this.mFragment != null))
    {
      this.mFragment.commit();
      finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.oob_contacts_sync_activity);
    ((BottomActionBar)findViewById(R.id.bottom_bar)).addButton(16908313, R.string.signup_done, this);
    showTitlebar(false);
    setTitlebarTitle(getString(R.string.app_name));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ContactsSyncConfigActivity
 * JD-Core Version:    0.6.2
 */