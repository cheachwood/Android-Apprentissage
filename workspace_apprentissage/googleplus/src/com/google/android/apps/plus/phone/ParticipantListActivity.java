package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.hangout.HangoutTile;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.util.EsLog;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.Collection;

public class ParticipantListActivity extends EsFragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
  private EsAccount mAccount;
  private long mConversationRowId;
  private boolean mIsGroup;
  private boolean mNeedToInviteParticipants;
  private int mParticipantCount;
  private Collection<Data.Participant> mParticipantList;
  private AudienceData mResultAudience;

  private void inviteMoreParticipants()
  {
    if (this.mParticipantList != null)
      ParticipantHelper.inviteMoreParticipants(this, this.mParticipantList, this.mIsGroup, this.mAccount, HangoutTile.class.getName().equals(getIntent().getStringExtra("tile")));
    for (this.mNeedToInviteParticipants = false; ; this.mNeedToInviteParticipants = true)
      return;
  }

  private void setParticipantCount(int paramInt)
  {
    this.mParticipantCount = paramInt;
    updateSubtitle();
  }

  private void updateSubtitle()
  {
    boolean bool = this.mIsGroup;
    String str = null;
    if (bool)
    {
      int i = this.mParticipantCount;
      str = null;
      if (i > 0)
      {
        Resources localResources = getResources();
        int j = R.string.participant_count;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(this.mParticipantCount);
        str = localResources.getString(j, arrayOfObject);
      }
    }
    if (Build.VERSION.SDK_INT < 11)
    {
      showTitlebar(true);
      setTitlebarSubtitle(str);
    }
    while (true)
    {
      return;
      getActionBar().setSubtitle(str);
    }
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CONVERSATION_PARTICIPANT_LIST;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1)
      if ((paramInt2 == -1) && (paramIntent != null))
      {
        this.mResultAudience = ((AudienceData)paramIntent.getParcelableExtra("audience"));
        if ((this.mResultAudience != null) && (EsLog.isLoggable("ParticipantList", 3)))
          Log.d("ParticipantList", "got audience " + this.mResultAudience);
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
    this.mAccount = ((EsAccount)getIntent().getExtras().get("account"));
    this.mIsGroup = getIntent().getBooleanExtra("is_group", false);
    this.mConversationRowId = getIntent().getLongExtra("conversation_row_id", -1L);
    if (Build.VERSION.SDK_INT >= 11)
      getActionBar().setDisplayHomeAsUpEnabled(true);
    while (true)
    {
      getSupportLoaderManager().restartLoader(1, null, this);
      getSupportLoaderManager().restartLoader(2, null, this);
      return;
      showTitlebar(true);
      createTitlebarButtons(R.menu.participant_list_activity_menu);
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    EsCursorLoader localEsCursorLoader;
    if (paramInt == 1)
    {
      Uri localUri2 = EsProvider.appendAccountParameter(EsProvider.CONVERSATIONS_URI, this.mAccount);
      String[] arrayOfString3 = ConversationQuery.PROJECTION;
      String[] arrayOfString4 = new String[1];
      arrayOfString4[0] = String.valueOf(this.mConversationRowId);
      localEsCursorLoader = new EsCursorLoader(this, localUri2, arrayOfString3, "_id=?", arrayOfString4, null);
    }
    while (true)
    {
      return localEsCursorLoader;
      if (paramInt == 2)
      {
        Uri localUri1 = EsProvider.buildParticipantsUri(this.mAccount, this.mConversationRowId);
        String[] arrayOfString1 = ParticipantsQuery.PROJECTION;
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = this.mAccount.getRealTimeChatParticipantId();
        localEsCursorLoader = new EsCursorLoader(this, localUri1, arrayOfString1, "participant_id!=? AND active=1", arrayOfString2, "first_name ASC");
      }
      else
      {
        localEsCursorLoader = null;
      }
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    if (Build.VERSION.SDK_INT >= 11)
      getMenuInflater().inflate(R.menu.participant_list_activity_menu, paramMenu);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if (i == R.id.realtimechat_conversation_invite_menu_item)
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
    RealTimeChatService.allowDisconnect(this, this.mAccount);
  }

  protected void onResume()
  {
    super.onResume();
    if (isIntentAccountActive())
    {
      RealTimeChatService.connectAndStayConnected(this, this.mAccount);
      if (this.mResultAudience != null)
      {
        RealTimeChatService.inviteParticipants(this, this.mAccount, this.mConversationRowId, this.mResultAudience);
        this.mResultAudience = null;
      }
    }
    while (true)
    {
      return;
      finish();
    }
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }

  public static abstract interface ConversationQuery
  {
    public static final String[] PROJECTION = { "is_group", "name", "generated_name" };
  }

  public static abstract interface ParticipantsQuery
  {
    public static final String[] PROJECTION = { "_id", "participant_id", "full_name", "first_name", "type" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ParticipantListActivity
 * JD-Core Version:    0.6.2
 */