package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.HostActionBar;

public class PhotosSelectionActivity extends HostActivity
{
  protected final int getContentView()
  {
    return R.layout.photos_selection_activity;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTOS_LIST;
  }

  protected final void onAttachActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.setVisibility(8);
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
 * Qualified Name:     com.google.android.apps.plus.phone.PhotosSelectionActivity
 * JD-Core Version:    0.6.2
 */