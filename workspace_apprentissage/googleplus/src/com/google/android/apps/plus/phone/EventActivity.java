package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.HostedEventFragment;
import com.google.android.apps.plus.fragments.HostedPhotosFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.HostActionBar;

public class EventActivity extends HostActivity
{
  private int mCurrentSpinnerIndex = 0;
  private ArrayAdapter<String> mPrimarySpinnerAdapter;

  protected final Fragment createDefaultFragment()
  {
    return new HostedEventFragment();
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.EVENT;
  }

  protected final void onAttachActionBar(HostActionBar paramHostActionBar)
  {
    super.onAttachActionBar(paramHostActionBar);
    paramHostActionBar.showPrimarySpinner(this.mPrimarySpinnerAdapter, this.mCurrentSpinnerIndex);
  }

  protected void onCreate(Bundle paramBundle)
  {
    this.mPrimarySpinnerAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item);
    this.mPrimarySpinnerAdapter.setDropDownViewResource(17367049);
    this.mPrimarySpinnerAdapter.add(getString(R.string.event_tab_event_text));
    this.mPrimarySpinnerAdapter.add(getString(R.string.event_tab_photos_text));
    if (paramBundle == null)
    {
      String str = getIntent().getStringExtra("notif_id");
      if (str != null)
        EsService.markNotificationAsRead(this, getAccount(), str);
    }
    super.onCreate(paramBundle);
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    super.onPrimarySpinnerSelectionChange(paramInt);
    if (this.mCurrentSpinnerIndex == paramInt)
      return;
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 0:
    case 1:
    }
    while (localObject != null)
    {
      this.mCurrentSpinnerIndex = paramInt;
      replaceFragment((Fragment)localObject);
      break;
      localObject = new HostedEventFragment();
      continue;
      localObject = new HostedPhotosFragment();
      String str = getIntent().getExtras().getString("event_id");
      if (!TextUtils.isEmpty(str))
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("event_id", str);
        ((HostedFragment)localObject).setArguments(localBundle);
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

  protected final void onUpButtonLaunchNewTask()
  {
    TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
    localTaskStackBuilder.addNextIntent(Intents.getEventsActivityIntent(this, getAccount()));
    localTaskStackBuilder.startActivities();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EventActivity
 * JD-Core Version:    0.6.2
 */