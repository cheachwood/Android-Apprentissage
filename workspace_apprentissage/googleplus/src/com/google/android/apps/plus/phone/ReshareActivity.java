package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.ReshareFragment;

public class ReshareActivity extends EsFragmentActivity
{
  private EsAccount mAccount;
  private ReshareFragment mReshareFragment;
  private boolean mShakeDetectorWasRunning;

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.RESHARE;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if (paramFragment.getId() == R.id.reshare_fragment)
      this.mReshareFragment = ((ReshareFragment)paramFragment);
  }

  public void onBackPressed()
  {
    this.mReshareFragment.onDiscard();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.reshare_activity);
    this.mAccount = ((EsAccount)getIntent().getParcelableExtra("account"));
    showTitlebar(true);
    setTitlebarTitle(getString(R.string.reshare_title));
    createTitlebarButtons(R.menu.post_menu);
    ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
    if (localShakeDetector != null)
      this.mShakeDetectorWasRunning = localShakeDetector.stop();
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(R.menu.post_menu, paramMenu);
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
      goHome(this.mAccount);
    while (true)
    {
      return bool;
      if (i == R.id.menu_post)
        this.mReshareFragment.reshare();
      else if (i == R.id.menu_discard)
        this.mReshareFragment.onDiscard();
      else
        bool = super.onOptionsItemSelected(paramMenuItem);
    }
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.findItem(R.id.menu_post).setVisible(false);
    return true;
  }

  protected final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    int i = 0;
    if (i < paramMenu.size())
    {
      MenuItem localMenuItem = paramMenu.getItem(i);
      if (localMenuItem.getItemId() == R.id.menu_post);
      for (boolean bool = true; ; bool = false)
      {
        localMenuItem.setVisible(bool);
        i++;
        break;
      }
    }
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ReshareActivity
 * JD-Core Version:    0.6.2
 */