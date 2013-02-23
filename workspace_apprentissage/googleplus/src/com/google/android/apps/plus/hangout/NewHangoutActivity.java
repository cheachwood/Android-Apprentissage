package com.google.android.apps.plus.hangout;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.AudienceFragment;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.phone.GoogleFeedback;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.util.ImageUtils;

public class NewHangoutActivity extends EsFragmentActivity
{
  private EsAccount mAccount;
  private AudienceFragment mAudienceFragment;
  private Button mHangoutButton;

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.HANGOUT_START_NEW;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof AudienceFragment))
    {
      this.mAudienceFragment = ((AudienceFragment)paramFragment);
      this.mAudienceFragment.setCirclesUsageType(10);
      this.mAudienceFragment.setIncludePhoneOnlyContacts(false);
      this.mAudienceFragment.setIncludePlusPages(false);
      this.mAudienceFragment.setPublicProfileSearchEnabled(true);
      this.mAudienceFragment.setShowSuggestedPeople(true);
      this.mAudienceFragment.setFilterNullGaiaIds(true);
      this.mAudienceFragment.setAudienceChangedCallback(new Runnable()
      {
        public final void run()
        {
          Button localButton;
          if (NewHangoutActivity.this.mHangoutButton != null)
          {
            localButton = NewHangoutActivity.this.mHangoutButton;
            if (NewHangoutActivity.this.mAudienceFragment.isAudienceEmpty())
              break label145;
          }
          label145: for (boolean bool = true; ; bool = false)
          {
            localButton.setEnabled(bool);
            if ((NewHangoutActivity.access$100(NewHangoutActivity.this) != null) && (!NewHangoutActivity.this.mAudienceFragment.isAudienceEmpty()) && (NewHangoutActivity.access$100(NewHangoutActivity.this).getVisibility() == 8))
            {
              NewHangoutActivity.access$100(NewHangoutActivity.this).setVisibility(0);
              TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, -NewHangoutActivity.this.getResources().getDimension(R.dimen.hangout_ring_toggle_height), 0.0F);
              localTranslateAnimation.setDuration(150L);
              NewHangoutActivity.access$100(NewHangoutActivity.this).startAnimation(localTranslateAnimation);
              NewHangoutActivity.this.findViewById(R.id.list_layout_parent).startAnimation(localTranslateAnimation);
            }
            return;
          }
        }
      });
    }
  }

  public void onBackPressed()
  {
    recordUserAction(OzActions.CONVERSATION_ABORT_NEW);
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.new_hangout_activity);
    this.mAccount = EsService.getActiveAccount(this);
    this.mHangoutButton = ((Button)findViewById(R.id.start_hangout_button));
    this.mHangoutButton.setEnabled(false);
    this.mHangoutButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        NewHangoutActivity.this.startActivity(Intents.getNewHangoutActivityIntent(NewHangoutActivity.this, NewHangoutActivity.this.mAccount, NewHangoutActivity.access$100(NewHangoutActivity.this).getRingInvitees(), NewHangoutActivity.this.mAudienceFragment.getAudience()));
        NewHangoutActivity.this.finish();
      }
    });
    if (Build.VERSION.SDK_INT < 11)
    {
      showTitlebar(true);
      setTitlebarTitle(getString(R.string.new_hangout_label));
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 2131361854);
    for (Dialog localDialog = ImageUtils.createInsertCameraPhotoDialog(this); ; localDialog = super.onCreateDialog(paramInt, paramBundle))
      return localDialog;
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(R.menu.hangout_menu, paramMenu);
    return true;
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
      if (i == R.id.feedback)
      {
        recordUserAction(OzActions.SETTINGS_FEEDBACK);
        GoogleFeedback.launch(this);
      }
      else if (i == R.id.help)
      {
        startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(this, getResources().getString(R.string.url_param_help_hangouts))));
      }
      else
      {
        bool = super.onOptionsItemSelected(paramMenuItem);
      }
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected void onStart()
  {
    Log.debug("NewHangoutActivity.onStart: this=%s", new Object[] { this });
    super.onStart();
    if (Build.VERSION.SDK_INT >= 11)
      getActionBar().setDisplayHomeAsUpEnabled(true);
    GCommApp.getInstance(this).signinUser(this.mAccount);
    GCommApp.getInstance(this).startingHangoutActivity(this);
  }

  protected void onStop()
  {
    Log.debug("NewHangoutActivity.onStop: this=%s", new Object[] { this });
    super.onStop();
    GCommApp.getInstance(this).stoppingHangoutActivity();
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.NewHangoutActivity
 * JD-Core Version:    0.6.2
 */