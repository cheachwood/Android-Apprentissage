package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.PeopleSearchFragment;
import com.google.android.apps.plus.fragments.PeopleSearchFragment.OnSelectionChangeListener;

public class PeopleSearchActivity extends EsFragmentActivity
  implements View.OnClickListener, PeopleSearchFragment.OnSelectionChangeListener
{
  private PeopleSearchFragment mSearchFragment;

  private boolean isPickerMode()
  {
    return getIntent().getBooleanExtra("picker_mode", false);
  }

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PEOPLE_SEARCH;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    Intent localIntent;
    PeopleSearchFragment localPeopleSearchFragment;
    if ((paramFragment instanceof PeopleSearchFragment))
    {
      this.mSearchFragment = ((PeopleSearchFragment)paramFragment);
      ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.progress_spinner);
      this.mSearchFragment.setProgressBar(localProgressBar);
      this.mSearchFragment.setOnSelectionChangeListener(this);
      localIntent = getIntent();
      boolean bool1 = getAccount().isPlusPage();
      this.mSearchFragment.setCircleUsageType(localIntent.getIntExtra("search_circles_usage", -1));
      localPeopleSearchFragment = this.mSearchFragment;
      if ((isPickerMode()) || (bool1))
        break label168;
    }
    label168: for (boolean bool2 = true; ; bool2 = false)
    {
      localPeopleSearchFragment.setAddToCirclesActionEnabled(bool2);
      this.mSearchFragment.setPublicProfileSearchEnabled(localIntent.getBooleanExtra("search_pub_profiles_enabled", false));
      this.mSearchFragment.setPhoneOnlyContactsEnabled(localIntent.getBooleanExtra("search_phones_enabled", false));
      this.mSearchFragment.setPlusPagesEnabled(localIntent.getBooleanExtra("search_plus_pages_enabled", false));
      this.mSearchFragment.setPeopleInCirclesEnabled(localIntent.getBooleanExtra("search_in_circles_enabled", true));
      this.mSearchFragment.setInitialQueryString(localIntent.getStringExtra("query"));
      return;
    }
  }

  public final void onCircleSelected(String paramString, CircleData paramCircleData)
  {
    if (isPickerMode())
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("circle_id", paramString);
      localIntent.putExtra("circle_data", paramCircleData);
      setResult(-1, localIntent);
      finish();
      return;
    }
    throw new IllegalStateException();
  }

  public void onClick(View paramView)
  {
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.people_search_activity);
    showTitlebar(true);
    setTitlebarTitle(getString(R.string.search_people_tab_text));
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case 16908332:
    }
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      goHome(getAccount());
    }
  }

  public final void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData)
  {
    if (isPickerMode())
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("person_id", paramString1);
      localIntent.putExtra("person_data", paramPersonData);
      setResult(-1, localIntent);
      finish();
    }
    while (true)
    {
      return;
      if (paramString2 != null)
        startExternalActivity(new Intent("android.intent.action.VIEW", Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, paramString2)));
      else
        startActivity(Intents.getProfileActivityIntent(this, getAccount(), paramString1, null));
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (isIntentAccountActive())
      if (this.mSearchFragment != null)
        this.mSearchFragment.startSearch();
    while (true)
    {
      return;
      finish();
    }
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(getAccount());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PeopleSearchActivity
 * JD-Core Version:    0.6.2
 */