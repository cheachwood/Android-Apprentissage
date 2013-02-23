package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.BlockFragment;
import com.google.android.apps.plus.fragments.BlockFragment.Listener;
import com.google.android.apps.plus.fragments.BlockPersonDialog.PersonBlocker;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.ParticipantsGalleryFragment;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.views.ParticipantsGalleryView.SimpleCommandListener;
import java.io.Serializable;

public class InvitationActivity extends EsFragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, BlockFragment.Listener, BlockPersonDialog.PersonBlocker
{
  EsAccount mAccount;
  String mConversationName;
  long mConversationRowId;
  String mInviterId;
  String mInviterName;
  boolean mIsGroup;
  private ParticipantsGalleryFragment mParticipantsGalleryFragment;

  static
  {
    if (!InvitationActivity.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void initialize(Intent paramIntent)
  {
    this.mAccount = ((EsAccount)paramIntent.getParcelableExtra("account"));
    this.mConversationRowId = paramIntent.getLongExtra("conversation_row_id", 0L);
    this.mInviterId = paramIntent.getStringExtra("inviter_id");
    this.mIsGroup = paramIntent.getBooleanExtra("is_group", false);
    this.mParticipantsGalleryFragment.setAccount(this.mAccount);
    this.mParticipantsGalleryFragment.setCommandListener(new ParticipantsGalleryView.SimpleCommandListener(this.mParticipantsGalleryFragment.getParticipantsGalleryView(), this.mAccount));
    this.mParticipantsGalleryFragment.setParticipantListButtonVisibility(false);
    getSupportLoaderManager().restartLoader(1, null, this);
    getSupportLoaderManager().restartLoader(2, null, this);
    this.mParticipantsGalleryFragment.getView().setVisibility(0);
    RealTimeChatService.markConversationNotificationsSeen(this, this.mAccount, this.mConversationRowId);
  }

  public final void blockPerson(Serializable paramSerializable)
  {
    BlockFragment.getInstance(this, this.mAccount, this.mInviterId, this.mInviterName, false, true).show(this);
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CONVERSATION_INVITE;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof ParticipantsGalleryFragment))
    {
      this.mParticipantsGalleryFragment = ((ParticipantsGalleryFragment)paramFragment);
      assert (this.mAccount == null);
    }
  }

  public final void onBlockCompleted(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      RealTimeChatService.leaveConversation(this, this.mAccount, this.mConversationRowId);
      finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.invitation_activity);
    initialize(getIntent());
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
        String[] arrayOfString1 = ConversationActivity.ParticipantsQuery.PROJECTION;
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = this.mAccount.getRealTimeChatParticipantId();
        localEsCursorLoader = new EsCursorLoader(this, localUri1, arrayOfString1, "participant_id!=?", arrayOfString2, "first_name");
      }
      else
      {
        localEsCursorLoader = null;
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    initialize(paramIntent);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
      goHome(this.mAccount);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected void onStart()
  {
    super.onStart();
    if (Build.VERSION.SDK_INT >= 11)
      getActionBar().setDisplayHomeAsUpEnabled(true);
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }

  public static abstract interface ConversationQuery
  {
    public static final String[] PROJECTION = { "_id", "is_group", "generated_name", "inviter_full_name" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.InvitationActivity
 * JD-Core Version:    0.6.2
 */