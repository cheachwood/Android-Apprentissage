package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.VideoViewFragment;

public class VideoViewActivity extends EsFragmentActivity
{
  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.VIDEO;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.video_view_activity);
    if (paramBundle == null)
    {
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      Intent localIntent = new Intent(getIntent());
      localFragmentTransaction.add(R.id.video_view_fragment_container, new VideoViewFragment(localIntent));
      localFragmentTransaction.commit();
    }
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.VideoViewActivity
 * JD-Core Version:    0.6.2
 */