package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.fragments.EditAudienceFragment;
import com.google.android.apps.plus.fragments.EditAudienceFragment.OnAudienceChangeListener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;

public class EditAudienceActivity extends EsFragmentActivity
  implements View.OnClickListener, EditAudienceFragment.OnAudienceChangeListener
{
  private EditAudienceFragment mEditAudienceFragment;
  private View mPositiveButton;
  private boolean mShakeDetectorWasRunning;

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PEOPLE_PICKER;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 0) && (paramInt2 == -1) && (this.mEditAudienceFragment != null))
    {
      String str1 = paramIntent.getStringExtra("person_id");
      if (str1 != null)
      {
        PersonData localPersonData = (PersonData)paramIntent.getParcelableExtra("person_data");
        this.mEditAudienceFragment.addSelectedPerson(str1, localPersonData);
      }
      String str2 = paramIntent.getStringExtra("circle_id");
      if (str2 != null)
      {
        CircleData localCircleData = (CircleData)paramIntent.getParcelableExtra("circle_data");
        this.mEditAudienceFragment.addSelectedCircle(str2, localCircleData);
      }
    }
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof EditAudienceFragment))
    {
      this.mEditAudienceFragment = ((EditAudienceFragment)paramFragment);
      this.mEditAudienceFragment.setOnSelectionChangeListener(this);
      this.mEditAudienceFragment.setCircleSelectionEnabled(true);
      this.mEditAudienceFragment.setCircleUsageType(getIntent().getIntExtra("circle_usage_type", 0));
      this.mEditAudienceFragment.setIncludePlusPages(getIntent().getBooleanExtra("search_plus_pages_enabled", true));
      this.mEditAudienceFragment.setFilterNullGaiaIds(getIntent().getBooleanExtra("filter_null_gaia_ids", false));
      this.mEditAudienceFragment.setIncomingAudienceIsReadOnly(getIntent().getBooleanExtra("audience_is_read_only", false));
    }
  }

  public final void onAudienceChanged(String paramString)
  {
    if ((this.mPositiveButton != null) && (this.mEditAudienceFragment != null))
      this.mPositiveButton.setEnabled(this.mEditAudienceFragment.isSelectionValid());
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.ok)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("audience", this.mEditAudienceFragment.getAudience());
      setResult(-1, localIntent);
      finish();
    }
    while (true)
    {
      return;
      if (i == R.id.cancel)
        finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.edit_audience_activity);
    String str = getIntent().getStringExtra("title");
    showTitlebar(true, false);
    setTitlebarTitle(str);
    createTitlebarButtons(R.menu.edit_audience_menu);
    this.mPositiveButton = findViewById(R.id.ok);
    if (this.mEditAudienceFragment != null)
      this.mPositiveButton.setEnabled(this.mEditAudienceFragment.isSelectionValid());
    this.mPositiveButton.setOnClickListener(this);
    findViewById(R.id.cancel).setOnClickListener(this);
    ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
    if (localShakeDetector != null)
      this.mShakeDetectorWasRunning = localShakeDetector.stop();
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.edit_audience_menu, paramMenu);
    return true;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mShakeDetectorWasRunning)
    {
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
      if (localShakeDetector != null)
        localShakeDetector.start();
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    int i = paramMenuItem.getItemId();
    if (i == 16908332)
      goHome(getAccount());
    while (true)
    {
      return bool;
      if (i == R.id.search)
        onSearchRequested();
      else
        bool = false;
    }
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.findItem(R.id.search).setVisible(false);
    return paramMenu.hasVisibleItems();
  }

  protected void onResume()
  {
    super.onResume();
    if (isIntentAccountActive())
      if (!this.mEditAudienceFragment.hasAudience())
      {
        AudienceData localAudienceData = (AudienceData)getIntent().getParcelableExtra("audience");
        if (localAudienceData != null)
          this.mEditAudienceFragment.setAudience(localAudienceData);
      }
    while (true)
    {
      return;
      finish();
    }
  }

  public boolean onSearchRequested()
  {
    boolean bool1 = getIntent().getBooleanExtra("search_phones_enabled", false);
    boolean bool2 = getIntent().getBooleanExtra("search_plus_pages_enabled", false);
    boolean bool3 = getIntent().getBooleanExtra("search_pub_profiles_enabled", false);
    int i = getIntent().getIntExtra("circle_usage_type", -1);
    startActivityForResult(Intents.getPeopleSearchActivityIntent(this, getAccount(), true, i, bool3, bool1, bool2, true, getIntent().getBooleanExtra("filter_null_gaia_ids", false)), 0);
    return true;
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(getAccount());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EditAudienceActivity
 * JD-Core Version:    0.6.2
 */