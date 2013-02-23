package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EditEventFragment;
import com.google.android.apps.plus.fragments.EditEventFragment.OnEditEventListener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;

public class NewEventActivity extends EsFragmentActivity
  implements EditEventFragment.OnEditEventListener
{
  private EditEventFragment mEditEventFragment;
  private boolean mShakeDetectorWasRunning;

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  protected final CharSequence getTitleButton3Text$9aa72f6()
  {
    return getResources().getText(R.string.invite);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CREATE_EVENT;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof EditEventFragment))
    {
      this.mEditEventFragment = ((EditEventFragment)paramFragment);
      this.mEditEventFragment.createEvent();
      this.mEditEventFragment.setOnEventChangedListener(this);
    }
  }

  public void onBackPressed()
  {
    if (this.mEditEventFragment != null)
      this.mEditEventFragment.onDiscard();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.new_event_activity);
    View localView1 = findViewById(R.id.cancel_button);
    if (localView1 != null)
      localView1.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (NewEventActivity.this.mEditEventFragment != null)
            NewEventActivity.this.mEditEventFragment.onDiscard();
        }
      });
    View localView2 = findViewById(R.id.share_button);
    if (localView2 != null)
      localView2.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (NewEventActivity.this.mEditEventFragment != null)
            NewEventActivity.this.mEditEventFragment.save();
        }
      });
    ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
    if (localShakeDetector != null)
      this.mShakeDetectorWasRunning = localShakeDetector.stop();
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

  public final void onEventClosed()
  {
    finish();
  }

  public final void onEventSaved()
  {
    finish();
  }

  protected void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected final void onTitlebarLabelClick()
  {
    onBackPressed();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.NewEventActivity
 * JD-Core Version:    0.6.2
 */