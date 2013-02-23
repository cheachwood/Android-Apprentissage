package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.PhotoPickerFragment;

public class PhotoPickerActivity extends EsFragmentActivity
{
  private int mCropMode;
  private String mDisplayName;

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTO_PICKER;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.photo_picker_activity);
    if (paramBundle == null)
    {
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      Intent localIntent = new Intent(getIntent());
      localFragmentTransaction.add(R.id.photo_picker_fragment_container, new PhotoPickerFragment(localIntent));
      localFragmentTransaction.commit();
    }
    this.mDisplayName = getIntent().getStringExtra("display_name");
    this.mCropMode = getIntent().getIntExtra("photo_picker_crop_mode", 0);
    String str;
    if (Build.VERSION.SDK_INT >= 11)
    {
      getActionBar().setDisplayHomeAsUpEnabled(true);
      if (this.mCropMode == 0)
        break label159;
      str = getString(R.string.photo_picker_sublabel);
      label120: if (Build.VERSION.SDK_INT < 11)
        break label167;
      ActionBar localActionBar = getActionBar();
      localActionBar.setTitle(str);
      localActionBar.setSubtitle(null);
    }
    while (true)
    {
      return;
      showTitlebar(true);
      createTitlebarButtons(R.menu.album_view_menu);
      break;
      label159: str = this.mDisplayName;
      break label120;
      label167: setTitlebarTitle(str);
      setTitlebarSubtitle(null);
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
      finish();
    }
  }

  protected final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    for (int i = 0; i < paramMenu.size(); i++)
      paramMenu.getItem(i).setVisible(false);
  }

  protected void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected final void onTitlebarLabelClick()
  {
    finish();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoPickerActivity
 * JD-Core Version:    0.6.2
 */