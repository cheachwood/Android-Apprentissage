package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsTabActivity;
import com.google.android.apps.plus.fragments.EventThemeListFragment;
import com.google.android.apps.plus.fragments.EventThemeListFragment.OnThemeSelectedListener;

public class EventThemePickerActivity extends EsTabActivity
  implements EventThemeListFragment.OnThemeSelectedListener
{
  public EventThemePickerActivity()
  {
    super(0, R.id.fragment_container);
  }

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  protected final OzViews getViewForLogging$65a8335d()
  {
    return OzViews.EVENT_THEMES;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof EventThemeListFragment))
    {
      ((EventThemeListFragment)paramFragment).setOnThemeSelectedListener(this);
      switch (getTabIndexForFragment(paramFragment))
      {
      default:
      case 0:
      case 1:
      }
    }
    while (true)
    {
      return;
      onAttachFragment(0, paramFragment);
      continue;
      onAttachFragment(1, paramFragment);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.event_theme_picker_activity);
    addTab(0, R.id.event_themes_featured_button, R.id.event_themes_featured_fragment);
    addTab(1, R.id.event_themes_patterns_button, R.id.event_themes_patterns_fragment);
    showTitlebar(true);
    setTitlebarTitle(getString(R.string.event_picker_activity_title));
  }

  protected final Fragment onCreateTab(int paramInt)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 0:
    case 1:
    }
    while (true)
    {
      return localObject;
      localObject = new EventThemeListFragment(0);
      continue;
      localObject = new EventThemeListFragment(1);
    }
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
      onBackPressed();
    }
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  public final void onThemeSelected(int paramInt, String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("theme_id", paramInt);
    localIntent.putExtra("theme_url", paramString);
    setResult(-1, localIntent);
    finish();
  }

  protected final void onTitlebarLabelClick()
  {
    onBackPressed();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EventThemePickerActivity
 * JD-Core Version:    0.6.2
 */