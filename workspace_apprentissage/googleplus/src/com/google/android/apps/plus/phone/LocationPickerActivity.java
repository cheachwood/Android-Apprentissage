package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.LocationPickerFragment;

public class LocationPickerActivity extends HostActivity
  implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener
{
  private LocationPickerFragment mLocationPickerFragment;

  protected final Fragment createDefaultFragment()
  {
    return new LocationPickerFragment();
  }

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  protected final int getContentView()
  {
    return R.layout.location_picker_activity;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.LOCATION_PICKER;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof LocationPickerFragment))
    {
      this.mLocationPickerFragment = ((LocationPickerFragment)paramFragment);
      this.mLocationPickerFragment.setSearchMode(false);
    }
  }

  public void onBackPressed()
  {
    if ((this.mLocationPickerFragment == null) || (!this.mLocationPickerFragment.onBackPressed()))
      super.onBackPressed();
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    finish();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    case -2:
    }
    while (true)
    {
      paramDialogInterface.dismiss();
      return;
      startActivity(Intents.getLocationSettingActivityIntent());
      continue;
      finish();
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 29341608:
    }
    AlertDialog.Builder localBuilder;
    for (Object localObject = null; ; localObject = localBuilder.create())
    {
      return localObject;
      localBuilder = new AlertDialog.Builder(this);
      localBuilder.setMessage(R.string.location_provider_disabled).setPositiveButton(R.string.yes, this).setNegativeButton(R.string.no, this);
      localBuilder.setOnCancelListener(this);
    }
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  public final void onUpButtonClick()
  {
    if ((this.mLocationPickerFragment == null) || (!this.mLocationPickerFragment.onUpButtonClicked()))
      super.onUpButtonClick();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.LocationPickerActivity
 * JD-Core Version:    0.6.2
 */