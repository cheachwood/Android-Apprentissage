package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.HostedPhotosFragment;
import com.google.android.apps.plus.service.EsService;

public class HostPhotosActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new HostedPhotosFragment();
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTOS_LIST;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      String str = getIntent().getStringExtra("notif_id");
      if (!TextUtils.isEmpty(str))
        EsService.markNotificationAsRead(this, getAccount(), str);
    }
  }

  protected final void onUpButtonLaunchNewTask()
  {
    TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
    String str = getIntent().getStringExtra("owner_id");
    if (str == null)
      str = getIntent().getStringExtra("photos_of_user_id");
    if (!getAccount().isMyGaiaId(str))
      localTaskStackBuilder.addNextIntent(Intents.getStreamActivityIntent(this, getAccount()));
    localTaskStackBuilder.addNextIntent(Intents.getProfilePhotosActivityIntent(this, getAccount(), "g:" + str));
    localTaskStackBuilder.startActivities();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostPhotosActivity
 * JD-Core Version:    0.6.2
 */