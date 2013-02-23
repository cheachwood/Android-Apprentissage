package com.google.android.apps.plus.hangout;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.realtimechat.ParticipantUtils;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.util.EsLog;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HangoutParticipantListActivity extends EsFragmentActivity
  implements View.OnClickListener
{
  private Hangout.Info hangoutInfo;
  private EsAccount mAccount;
  private Collection<Data.Participant> mParticipantList;
  private ToastsView toastsView;

  private boolean canInviteMoreParticipants()
  {
    if ((this.hangoutInfo != null) && (EsLog.ENABLE_DOGFOOD_FEATURES));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void inviteMoreParticipants()
  {
    String str = getResources().getString(R.string.realtimechat_conversation_invite_menu_item_text);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mParticipantList.iterator();
    while (localIterator.hasNext())
      localArrayList.add(ParticipantUtils.makePersonFromParticipant((Data.Participant)localIterator.next()));
    AudienceData localAudienceData = new AudienceData(localArrayList, null);
    startActivityForResult(Intents.getEditAudienceActivityIntent(this, this.mAccount, str, localAudienceData, 5, false, false, true, true), 0);
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.HANGOUT_PARTICIPANTS;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 0) && (paramInt2 == -1) && (paramIntent != null))
    {
      AudienceData localAudienceData = (AudienceData)paramIntent.getParcelableExtra("audience");
      GCommApp.getInstance(this).getGCommNativeWrapper().inviteToMeeting(localAudienceData, "HANGOUT", false, true);
      this.toastsView.addToast(R.string.hangout_invites_sent);
    }
    while (true)
    {
      return;
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == R.id.title_button_1)
      inviteMoreParticipants();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.participant_list_activity);
    this.toastsView = ((ToastsView)findViewById(R.id.toasts_view));
    this.mAccount = ((EsAccount)getIntent().getExtras().get("account"));
    this.hangoutInfo = GCommApp.getInstance(this).getGCommNativeWrapper().getHangoutInfo();
    showTitlebar(true);
    createTitlebarButtons(R.menu.hangout_participant_list_activity_menu);
    this.mParticipantList = ((ArrayList)getIntent().getSerializableExtra("hangout_participants"));
    String str1 = getResources().getString(R.string.hangout_participants_title);
    showTitlebar(true);
    setTitlebarTitle(str1);
    this.mParticipantList.size();
    Resources localResources = getResources();
    int i = R.string.participant_count;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(this.mParticipantList.size());
    String str2 = localResources.getString(i, arrayOfObject);
    showTitlebar(true);
    setTitlebarSubtitle(str2);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    return false;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if (i == R.id.hangout_invite_menu_item)
      inviteMoreParticipants();
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      if (i != 16908332)
        break;
      goHome(this.mAccount);
    }
  }

  protected void onPause()
  {
    super.onPause();
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    return false;
  }

  public final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    paramMenu.findItem(R.id.hangout_invite_menu_item).setVisible(canInviteMoreParticipants());
  }

  protected void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutParticipantListActivity
 * JD-Core Version:    0.6.2
 */