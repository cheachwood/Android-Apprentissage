package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.HostedSquareStreamFragment;
import com.google.android.apps.plus.service.EsService;

public class HostSquareStreamActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new HostedSquareStreamFragment();
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.SQUARE_LANDING;
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
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostSquareStreamActivity
 * JD-Core Version:    0.6.2
 */