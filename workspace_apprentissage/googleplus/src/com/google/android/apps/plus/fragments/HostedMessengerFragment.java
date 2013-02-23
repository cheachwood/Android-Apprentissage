package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.views.ConversationListItemView;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.SuggestedParticipantView;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class HostedMessengerFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
  private ConversationCursorAdapter mAdapter;
  private Boolean mConnected;
  private Cursor mConversationCursor;
  private Uri mConversationsUri;
  private Bundle mInvitationConversationBundle;
  private ListView mListView;
  private final RTCServiceListener mRTCServiceListener = new RTCServiceListener((byte)0);
  private boolean mRecordedConversationsEmpty;
  private AudienceData mResultAudience;
  private int mScrollOffset;
  private int mScrollPos;
  private Cursor mSuggestionCursor;
  private Uri mSuggestionsUri;

  private boolean isLoading()
  {
    if ((this.mConversationCursor == null) || (this.mSuggestionCursor == null) || ((!RealTimeChatService.getConversationsLoaded()) && (this.mAdapter.isEmpty())));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void updateView(View paramView)
  {
    if ((isEmpty()) && (this.mConnected != null) && (!this.mConnected.booleanValue()))
    {
      paramView.findViewById(16908292).setVisibility(8);
      paramView.findViewById(R.id.list_empty_text).setVisibility(8);
      paramView.findViewById(R.id.list_empty_progress).setVisibility(8);
      paramView.findViewById(R.id.server_error).setVisibility(0);
    }
    while (true)
    {
      return;
      if (isLoading())
        showEmptyViewProgress(paramView);
      else if (isEmpty())
        showEmptyView(paramView, null);
      else
        showContent(paramView);
    }
  }

  protected final void doShowEmptyView(View paramView, String paramString)
  {
    if (isEmpty())
    {
      paramView.findViewById(16908292).setVisibility(0);
      paramView.findViewById(R.id.list_empty_text).setVisibility(0);
      paramView.findViewById(R.id.list_empty_progress).setVisibility(8);
    }
    paramView.findViewById(R.id.server_error).setVisibility(8);
  }

  protected final void doShowEmptyViewProgress(View paramView)
  {
    if (isEmpty())
    {
      paramView.findViewById(16908292).setVisibility(8);
      paramView.findViewById(R.id.list_empty_text).setVisibility(8);
      paramView.findViewById(R.id.list_empty_progress).setVisibility(0);
    }
    paramView.findViewById(R.id.server_error).setVisibility(8);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CONVERSATIONS;
  }

  protected final boolean isEmpty()
  {
    if ((this.mConversationCursor != null) && (RealTimeChatService.getConversationsLoaded()) && (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean needsAsyncData()
  {
    return true;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    if (paramInt == 100)
    {
      recordUserAction(OzActions.CONVERSATIONS_START_NEW);
      startActivity(Intents.getNewConversationActivityIntent(getActivity(), this.mAccount, null));
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (paramInt2 == -1) && (paramIntent != null))
      this.mResultAudience = ((AudienceData)paramIntent.getParcelableExtra("audience"));
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mConnected = null;
    if (paramBundle != null)
      this.mScrollPos = paramBundle.getInt("scroll_pos");
    for (this.mScrollOffset = paramBundle.getInt("scroll_off"); ; this.mScrollOffset = 0)
    {
      if (paramBundle != null)
      {
        this.mInvitationConversationBundle = paramBundle.getBundle("InvitationConversationBundle");
        this.mRecordedConversationsEmpty = true;
      }
      return;
      this.mScrollPos = 0;
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (EsLog.isLoggable("ConversationList", 3))
      Log.d("ConversationList", "onCreateLoader " + paramInt);
    EsCursorLoader localEsCursorLoader;
    if (paramInt == 1)
      localEsCursorLoader = new EsCursorLoader(getActivity(), this.mConversationsUri, ConversationQuery.PROJECTION, "is_visible=1 AND is_pending_leave=0", null, "latest_message_timestamp DESC", null);
    while (true)
    {
      return localEsCursorLoader;
      if (paramInt == 3)
      {
        localEsCursorLoader = new EsCursorLoader(getActivity(), this.mSuggestionsUri, SuggestionsQuery.PROJECTION, null, null, "sequence ASC", null);
      }
      else if (paramInt == 2)
      {
        Uri localUri = EsProvider.buildParticipantsUri(this.mAccount, paramBundle.getLong("conversation_row_id"));
        FragmentActivity localFragmentActivity = getActivity();
        String[] arrayOfString1 = ParticipantsQuery.PROJECTION;
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = this.mAccount.getRealTimeChatParticipantId();
        localEsCursorLoader = new EsCursorLoader(localFragmentActivity, localUri, arrayOfString1, "participant_id!=?", arrayOfString2, "first_name ASC", localUri);
      }
      else
      {
        localEsCursorLoader = null;
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.conversation_list_fragment, paramViewGroup, false);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.setOnItemClickListener(this);
    this.mAdapter = new ConversationCursorAdapter(getActivity(), this.mListView);
    this.mListView.setAdapter(this.mAdapter);
    if ((Build.VERSION.SDK_INT >= 14) && (!ViewConfiguration.get(getActivity()).hasPermanentMenuKey()))
      localView.findViewById(R.id.help_spacer).setVisibility(0);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Cursor localCursor2;
    if (this.mAdapter.getPartitionForPosition(paramInt) == 0)
    {
      localCursor2 = (Cursor)this.mAdapter.getItem(paramInt);
      if (localCursor2 != null);
    }
    while (true)
    {
      return;
      if ((localCursor2.getInt(13) != 0) && (localCursor2.getString(14) != null))
      {
        FragmentActivity localFragmentActivity2 = getActivity();
        EsAccount localEsAccount2 = this.mAccount;
        String str4 = localCursor2.getString(14);
        if (localCursor2.getInt(3) != 0);
        for (boolean bool2 = true; ; bool2 = false)
        {
          startActivity(Intents.getConversationInvititationActivityIntent(localFragmentActivity2, localEsAccount2, paramLong, str4, bool2));
          break;
        }
      }
      FragmentActivity localFragmentActivity1 = getActivity();
      EsAccount localEsAccount1 = this.mAccount;
      if (localCursor2.getInt(3) != 0);
      for (boolean bool1 = true; ; bool1 = false)
      {
        startActivity(Intents.getConversationActivityIntent(localFragmentActivity1, localEsAccount1, paramLong, bool1));
        break;
      }
      Cursor localCursor1 = (Cursor)this.mAdapter.getItem(paramInt);
      if (localCursor1 != null)
      {
        String str1 = localCursor1.getString(1);
        String str2 = localCursor1.getString(2);
        String str3 = localCursor1.getString(3);
        Data.Participant localParticipant = Data.Participant.newBuilder().setParticipantId(str1).setFullName(str2).setFirstName(str3).build();
        startActivity(Intents.getFakeConversationActivityIntent(getActivity(), this.mAccount, localParticipant, false));
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.help)
    {
      String str = getResources().getString(R.string.url_param_help_messenger);
      startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(getActivity(), str)));
    }
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  public final void onPause()
  {
    super.onPause();
    RealTimeChatService.unregisterListener(this.mRTCServiceListener);
    ((TextView)getView().findViewById(R.id.huddle_help_text)).setText(null);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showTitle(R.string.home_screen_huddle_label);
    paramHostActionBar.showActionButton(100, R.drawable.ic_menu_start_new_huddle, R.string.menu_new_conversation);
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    int i = paramMenu.size();
    int j = 0;
    if (j < i)
    {
      MenuItem localMenuItem = paramMenu.getItem(j);
      switch (localMenuItem.getItemId())
      {
      default:
      case 100:
      }
      while (true)
      {
        j++;
        break;
        localMenuItem.setVisible(true);
      }
    }
  }

  public final void onResume()
  {
    super.onResume();
    RealTimeChatService.registerListener(this.mRTCServiceListener);
    TextView localTextView = (TextView)getView().findViewById(R.id.huddle_help_text);
    Uri localUri = HelpUrl.getHelpUrl(getActivity(), "plusone_messenger_promo");
    Resources localResources = getResources();
    int i = R.string.huddle_help_text;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localUri.toString();
    Spanned localSpanned = Html.fromHtml(localResources.getString(i, arrayOfObject));
    URLSpan[] arrayOfURLSpan = (URLSpan[])localSpanned.getSpans(0, localSpanned.length(), URLSpan.class);
    if (arrayOfURLSpan.length > 0)
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(localSpanned);
      final URLSpan localURLSpan = arrayOfURLSpan[0];
      int j = localSpanned.getSpanStart(localURLSpan);
      int k = localSpanned.getSpanEnd(localURLSpan);
      localSpannableStringBuilder.setSpan(new ClickableSpan()
      {
        public final void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW");
          localIntent.setData(Uri.parse(localURLSpan.getURL()));
          HostedMessengerFragment.this.startExternalActivity(localIntent);
        }
      }
      , j, k, 33);
      localTextView.setText(localSpannableStringBuilder);
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    updateView(getView());
    if (this.mResultAudience != null)
    {
      long l = this.mInvitationConversationBundle.getLong("conversation_row_id", -1L);
      RealTimeChatService.inviteParticipants(getActivity(), this.mAccount, l, this.mResultAudience);
      this.mInvitationConversationBundle = null;
      this.mResultAudience = null;
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBundle("InvitationConversationBundle", this.mInvitationConversationBundle);
    if ((!getActivity().isFinishing()) && (this.mListView != null))
    {
      if (this.mListView != null)
      {
        this.mScrollPos = this.mListView.getFirstVisiblePosition();
        this.mScrollOffset = 0;
      }
      paramBundle.putInt("scroll_pos", this.mScrollPos);
      paramBundle.putInt("scroll_off", this.mScrollOffset);
    }
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mAccount = ((EsAccount)paramBundle.getParcelable("account"));
    if (this.mAccount != null)
    {
      this.mConversationsUri = EsProvider.appendAccountParameter(EsProvider.CONVERSATIONS_URI, this.mAccount);
      this.mSuggestionsUri = EsProvider.appendAccountParameter(EsProvider.MESSENGER_SUGGESTIONS_URI, this.mAccount);
      getLoaderManager().initLoader(1, null, this);
      getLoaderManager().initLoader(3, null, this);
      if (EsLog.isLoggable("ConversationList", 3))
        Log.d("ConversationList", "setAccount");
    }
    if (paramBundle.getBoolean("reset_notifications", false))
      RealTimeChatService.resetNotifications(getActivity(), this.mAccount);
  }

  protected final void showContent(View paramView)
  {
    super.showContent(paramView);
    paramView.findViewById(16908292).setVisibility(8);
    paramView.findViewById(R.id.list_empty_text).setVisibility(8);
    paramView.findViewById(R.id.list_empty_progress).setVisibility(8);
    paramView.findViewById(R.id.server_error).setVisibility(8);
  }

  final class ConversationCursorAdapter extends EsCompositeCursorAdapter
  {
    private Cursor mConversationsCursor;
    private Cursor mSuggestionsCursor;

    public ConversationCursorAdapter(Context paramAbsListView, AbsListView arg3)
    {
      super((byte)0);
      addPartition(false, false);
      addPartition(false, true);
      Object localObject;
      localObject.setRecyclerListener(new AbsListView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          if ((paramAnonymousView instanceof ConversationListItemView))
            ((ConversationListItemView)paramAnonymousView).clear();
          while (true)
          {
            return;
            if ((paramAnonymousView instanceof SuggestedParticipantView))
              ((SuggestedParticipantView)paramAnonymousView).clear();
          }
        }
      });
    }

    protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
    {
      if ((paramCursor == null) || (paramCursor.isClosed()));
      while (true)
      {
        return;
        switch (paramInt1)
        {
        default:
        case 0:
        case 1:
        }
      }
      ConversationListItemView localConversationListItemView = (ConversationListItemView)paramView;
      localConversationListItemView.clear();
      String str1 = paramCursor.getString(11);
      label128: boolean bool;
      label177: LinkedList localLinkedList;
      int i;
      String str3;
      String str4;
      if (paramCursor.getInt(13) == 1)
      {
        localConversationListItemView.setConversationName(paramCursor.getString(7));
        String str8 = paramCursor.getString(15);
        if (str8 == null)
          str8 = "";
        localConversationListItemView.setLastMessage(getContext().getString(R.string.realtimechat_invitation_preview_text, new Object[] { str8 }));
        localConversationListItemView.setTimeSince(Dates.getShortRelativeTimeSpanString(getContext(), paramCursor.getLong(4) / 1000L));
        localConversationListItemView.setUnreadCount(paramCursor.getInt(5));
        if (paramCursor.getInt(2) == 1)
        {
          bool = true;
          localConversationListItemView.setMuted(bool);
          String str6 = paramCursor.getString(16);
          if (str6 == null)
            break label480;
          StringTokenizer localStringTokenizer = new StringTokenizer(str6, "|");
          localLinkedList = new LinkedList();
          while (localStringTokenizer.hasMoreElements())
          {
            String str7 = EsPeopleData.extractGaiaId(localStringTokenizer.nextToken());
            if ((str7 != null) && (!str7.equals(HostedMessengerFragment.this.mAccount.getGaiaId())))
              localLinkedList.add(str7);
          }
        }
      }
      else
      {
        String str2 = paramCursor.getString(6);
        if (str2 == null)
          str2 = paramCursor.getString(7);
        localConversationListItemView.setConversationName(str2);
        i = paramCursor.getInt(12);
        str3 = paramCursor.getString(8);
        str4 = paramCursor.getString(9);
        if (str1 != null)
          break label535;
      }
      label535: for (String str5 = getContext().getResources().getString(R.string.realtimechat_participant_without_name_text); ; str5 = str1)
      {
        if (str4 != null)
        {
          localConversationListItemView.setLastMessage(getContext().getString(R.string.realtimechat_name_and_message_image, new Object[] { str5 }));
          break label128;
        }
        if (str3 == null)
          break label128;
        if (i == 1)
        {
          localConversationListItemView.setLastMessage(getContext().getString(R.string.realtimechat_name_and_message_text, new Object[] { str5, str3 }));
          break label128;
        }
        localConversationListItemView.setLastMessage(str3.replaceAll("\\<.*?\\>", ""));
        break label128;
        bool = false;
        break label177;
        localConversationListItemView.setParticipantsId(localLinkedList, HostedMessengerFragment.this.mAccount.getGaiaId());
        while (true)
        {
          localConversationListItemView.updateContentDescription();
          break;
          label480: localConversationListItemView.setParticipantsId(null, HostedMessengerFragment.this.mAccount.getGaiaId());
        }
        SuggestedParticipantView localSuggestedParticipantView = (SuggestedParticipantView)paramView;
        localSuggestedParticipantView.setParticipantId(EsPeopleData.extractGaiaId(paramCursor.getString(1)));
        localSuggestedParticipantView.setParticipantName(paramCursor.getString(2));
        break;
      }
    }

    protected final View getView(int paramInt1, Cursor paramCursor, int paramInt2, View paramView, ViewGroup paramViewGroup)
    {
      View localView = null;
      if (paramView != null)
      {
        localView = null;
        switch (paramInt1)
        {
        default:
        case 0:
        case 1:
        }
      }
      while (true)
      {
        if (localView == null)
          localView = newView$54126883(getContext(), paramInt1, paramCursor, paramViewGroup);
        bindView(localView, paramInt1, paramCursor, paramInt2);
        return localView;
        boolean bool2 = paramView instanceof ConversationListItemView;
        localView = null;
        if (bool2)
        {
          localView = paramView;
          continue;
          boolean bool1 = paramView instanceof SuggestedParticipantView;
          localView = null;
          if (bool1)
            localView = paramView;
        }
      }
    }

    public final boolean hasStableIds()
    {
      return true;
    }

    protected final View newHeaderView$4ac0fa28(Context paramContext, int paramInt, ViewGroup paramViewGroup)
    {
      return LayoutInflater.from(paramContext).inflate(R.layout.section_header_view, null);
    }

    protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      LayoutInflater localLayoutInflater = LayoutInflater.from(paramContext);
      View localView = null;
      switch (paramInt)
      {
      default:
      case 0:
      case 1:
      }
      while (true)
      {
        return localView;
        localView = localLayoutInflater.inflate(R.layout.conversation_list_item_view, null);
        continue;
        localView = localLayoutInflater.inflate(R.layout.suggested_participant_view, null);
      }
    }

    public final void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
    {
      if (paramLoader.getId() == 1)
      {
        this.mConversationsCursor = paramCursor;
        changeCursor(0, paramCursor);
        if (this.mSuggestionsCursor != null)
          changeCursor(1, this.mSuggestionsCursor);
      }
      while (true)
      {
        if (EsLog.isLoggable("ConversationList", 3))
          Log.d("ConversationList", "onLoadFinished suggestions " + this.mSuggestionsCursor + " conversations " + this.mConversationsCursor);
        return;
        if (paramLoader.getId() == 3)
        {
          this.mSuggestionsCursor = paramCursor;
          if (HostedMessengerFragment.this.mConversationCursor != null)
            changeCursor(1, paramCursor);
        }
      }
    }
  }

  public static abstract interface ConversationQuery
  {
    public static final String[] PROJECTION = { "_id", "conversation_id", "is_muted", "is_group", "latest_message_timestamp", "unread_count", "name", "generated_name", "latest_message_text", "latest_message_image_url", "latest_message_author_full_name", "latest_message_author_first_name", "latest_message_type", "is_pending_accept", "inviter_id", "inviter_first_name", "packed_participants" };
  }

  public static abstract interface ParticipantsQuery
  {
    public static final String[] PROJECTION = { "participant_id", "full_name", "first_name" };
  }

  private final class RTCServiceListener extends RealTimeChatServiceListener
  {
    private RTCServiceListener()
    {
    }

    public final void onConnected()
    {
      HostedMessengerFragment.access$102(HostedMessengerFragment.this, Boolean.valueOf(true));
      HostedMessengerFragment.this.updateView(HostedMessengerFragment.this.getView());
    }

    public final void onConversationsLoaded$abe99c5()
    {
      if (HostedMessengerFragment.this.isEmpty())
      {
        HostedMessengerFragment.this.getLoaderManager().restartLoader(1, null, HostedMessengerFragment.this);
        HostedMessengerFragment.this.getLoaderManager().restartLoader(3, null, HostedMessengerFragment.this);
      }
    }

    public final void onDisconnected$13462e()
    {
      HostedMessengerFragment.access$102(HostedMessengerFragment.this, Boolean.valueOf(false));
      HostedMessengerFragment.this.updateView(HostedMessengerFragment.this.getView());
    }
  }

  public static abstract interface SuggestionsQuery
  {
    public static final String[] PROJECTION = { "_id", "participant_id", "full_name", "first_name" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedMessengerFragment
 * JD-Core Version:    0.6.2
 */