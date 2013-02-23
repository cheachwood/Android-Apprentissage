package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.fragments.HostedAlbumsFragment;
import com.google.android.apps.plus.fragments.HostedProfileFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.HostActionBar;

public class ProfileActivity extends HostActivity
{
  private int mCurrentSpinnerIndex = 0;
  private ArrayAdapter<String> mPrimarySpinnerAdapter;

  public static ArrayAdapter<String> createSpinnerAdapter(Context paramContext)
  {
    ArrayAdapter localArrayAdapter = new ArrayAdapter(paramContext, R.layout.simple_spinner_item);
    localArrayAdapter.setDropDownViewResource(17367049);
    localArrayAdapter.add(paramContext.getString(R.string.profile_posts_tab_text));
    localArrayAdapter.add(paramContext.getString(R.string.profile_photos_tab_text));
    return localArrayAdapter;
  }

  private static HostedFragment getFragmentForPosition(int paramInt)
  {
    Object localObject = null;
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return localObject;
      localObject = new HostedProfileFragment();
      continue;
      localObject = new HostedAlbumsFragment();
    }
  }

  protected final Fragment createDefaultFragment()
  {
    return getFragmentForPosition(this.mCurrentSpinnerIndex);
  }

  protected final Bundle getExtrasForLogging()
  {
    String str1 = getIntent().getStringExtra("person_id");
    String str2;
    if (!TextUtils.isEmpty(str1))
    {
      str2 = EsPeopleData.extractGaiaId(str1);
      if (TextUtils.isEmpty(str2));
    }
    for (Bundle localBundle = EsAnalyticsData.createExtras("extra_gaia_id", str2); ; localBundle = null)
      return localBundle;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.LOOP_USER;
  }

  protected final void onAttachActionBar(HostActionBar paramHostActionBar)
  {
    super.onAttachActionBar(paramHostActionBar);
    paramHostActionBar.showPrimarySpinner(this.mPrimarySpinnerAdapter, this.mCurrentSpinnerIndex);
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof HostedProfileFragment))
      ((HostedProfileFragment)paramFragment).relinquishPrimarySpinner();
    while (true)
    {
      super.onAttachFragment(paramFragment);
      return;
      if ((paramFragment instanceof HostedAlbumsFragment))
        ((HostedAlbumsFragment)paramFragment).relinquishPrimarySpinner();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    this.mPrimarySpinnerAdapter = createSpinnerAdapter(this);
    if (paramBundle == null)
      switch (getIntent().getIntExtra("profile_view_type", 0))
      {
      default:
      case 0:
      case 1:
      }
    while (true)
    {
      String str = getIntent().getStringExtra("notif_id");
      if (str != null)
        EsService.markNotificationAsRead(this, getAccount(), str);
      super.onCreate(paramBundle);
      return;
      this.mCurrentSpinnerIndex = 0;
      continue;
      this.mCurrentSpinnerIndex = 1;
    }
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    super.onPrimarySpinnerSelectionChange(paramInt);
    if (this.mCurrentSpinnerIndex == paramInt);
    while (true)
    {
      return;
      HostedFragment localHostedFragment = getFragmentForPosition(paramInt);
      if (localHostedFragment != null)
      {
        this.mCurrentSpinnerIndex = paramInt;
        replaceFragment(localHostedFragment);
      }
    }
  }

  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mCurrentSpinnerIndex = paramBundle.getInt("spinnerIndex", 0);
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("spinnerIndex", this.mCurrentSpinnerIndex);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ProfileActivity
 * JD-Core Version:    0.6.2
 */