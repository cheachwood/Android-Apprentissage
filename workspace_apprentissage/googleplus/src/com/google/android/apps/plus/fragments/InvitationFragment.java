package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.views.AvatarView;

public class InvitationFragment extends EsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
  private final String TAG = "InvitationFragment";
  private EsAccount mAccount;
  private AvatarView mAvatarView;
  private long mConversationRowId;
  private TextView mInformationAndBlockMessage;
  private TextView mInvitationMessage;
  private String mInviterId;
  private boolean mIsGroup;

  public final boolean isEmpty()
  {
    return false;
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAccount = ((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == R.id.realtimechat_accept_invitation_button)
    {
      RealTimeChatService.replyToInvitation(getActivity(), this.mAccount, this.mConversationRowId, this.mInviterId, true);
      Intent localIntent = Intents.getConversationActivityIntent(getActivity(), this.mAccount, this.mConversationRowId, this.mIsGroup);
      getActivity().startActivity(localIntent);
      getActivity().finish();
    }
    while (true)
    {
      return;
      if (paramView.getId() == R.id.realtimechat_reject_invitation_button)
      {
        RealTimeChatService.leaveConversation(getActivity(), this.mAccount, this.mConversationRowId);
        getActivity().finish();
      }
      else if ((paramView == this.mAvatarView) && (this.mInviterId != null))
      {
        startActivity(Intents.getProfileActivityIntent(getActivity(), this.mAccount, this.mInviterId, null));
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FragmentActivity localFragmentActivity = getActivity();
    this.mConversationRowId = localFragmentActivity.getIntent().getLongExtra("conversation_row_id", -1L);
    this.mInviterId = localFragmentActivity.getIntent().getStringExtra("inviter_id");
    this.mAccount = ((EsAccount)getActivity().getIntent().getExtras().get("account"));
    this.mIsGroup = getActivity().getIntent().getBooleanExtra("is_group", false);
    getLoaderManager().initLoader(1, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Uri localUri;
    FragmentActivity localFragmentActivity;
    String[] arrayOfString1;
    String[] arrayOfString2;
    if (paramInt == 1)
    {
      localUri = EsProvider.buildParticipantsUri(this.mAccount, this.mConversationRowId);
      localFragmentActivity = getActivity();
      arrayOfString1 = ParticipantQuery.PROJECTION;
      arrayOfString2 = new String[1];
      arrayOfString2[0] = this.mInviterId;
    }
    for (EsCursorLoader localEsCursorLoader = new EsCursorLoader(localFragmentActivity, localUri, arrayOfString1, "participant_id=?", arrayOfString2, null); ; localEsCursorLoader = null)
      return localEsCursorLoader;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.invitation_fragment, paramViewGroup, false);
    this.mInvitationMessage = ((TextView)localView.findViewById(R.id.invitation_message_text));
    this.mInformationAndBlockMessage = ((TextView)localView.findViewById(R.id.invitation_acl_and_block));
    this.mAvatarView = ((AvatarView)localView.findViewById(R.id.inviter_avatar));
    this.mAvatarView.setOnClickListener(this);
    localView.findViewById(R.id.realtimechat_reject_invitation_button).setOnClickListener(this);
    localView.findViewById(R.id.realtimechat_accept_invitation_button).setOnClickListener(this);
    this.mAvatarView.setGaiaId(EsPeopleData.extractGaiaId(this.mInviterId));
    return localView;
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public static abstract interface ParticipantQuery
  {
    public static final String[] PROJECTION = { "full_name" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.InvitationFragment
 * JD-Core Version:    0.6.2
 */