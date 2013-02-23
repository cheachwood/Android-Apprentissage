package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.ImageRequest;
import com.google.android.apps.plus.content.LocalImageRequest;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.views.HangoutTileEventMessageListItemView;
import com.google.android.apps.plus.views.MessageClickListener;
import com.google.android.apps.plus.views.MessageListItemView;
import com.google.android.apps.plus.views.MessageListItemViewImage;
import com.google.android.apps.plus.views.MessageListItemViewImage.OnMeasuredListener;
import com.google.android.apps.plus.views.SystemMessageListItemView;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MessageListFragment extends EsListFragment<ListView, MessageCursorAdapter>
  implements LoaderManager.LoaderCallbacks<Cursor>, AlertFragmentDialog.AlertDialogListener, MessageClickListener
{
  private static long COLLAPSE_POSTS_THRESHOLD = 60000L;
  private RelativeLayout.LayoutParams defaultListViewLayoutParams;
  private EsAccount mAccount;
  private Runnable mAnimateTypingVisibilityRunnable = new Runnable()
  {
    public final void run()
    {
      MessageListFragment.this.animateTypingVisibility();
    }
  };
  private Runnable mCheckExpiredTypingRunnable = new Runnable()
  {
    public final void run()
    {
      MessageListFragment.access$200(MessageListFragment.this);
      MessageListFragment.this.updateTypingVisibility();
    }
  };
  private String mConversationId;
  private Long mConversationRowId;
  private long mEarliestEventTimestamp;
  private long mFirstEventTimestamp;
  private Handler mHandler = new Handler();
  private View mHeaderView;
  private boolean mInitialLoadFinished;
  private boolean mIsGroup;
  private boolean mIsTypingVisible = false;
  private LeaveConversationListener mLeaveConversationListener;
  private boolean mLoadingOlderEvents;
  private Uri mMessagesUri;
  private HashMap<String, Data.Participant> mParticipantList;
  private final RealTimeChatServiceListener mRTCServiceListener = new RTCServiceListener((byte)0);
  private Integer mRequestId = null;
  private Data.Participant mSingleParticipant;
  private Animation mSlideInUpAnimation;
  private Animation mSlideOutDownAnimation;
  private int mTotalItemBeforeLoadingOlder;
  private TranslateAnimation mTranslateListAnimation;
  private HashMap<String, UserTypingInfo> mTypingParticipants = new HashMap();
  private TextView mTypingTextView;
  private View mTypingView;
  private boolean mTypingVisibilityChanged = false;

  private void animateTypingVisibility()
  {
    while (true)
    {
      int j;
      try
      {
        if ((this.mTypingView == null) || (this.mListView == null))
        {
          if (EsLog.isLoggable("MessageListFragment", 3))
            Log.d("MessageListFragment", "Ignoring animation due to null views");
          return;
        }
        this.mTypingVisibilityChanged = false;
        if (this.mTypingParticipants.size() > 0)
        {
          bool = true;
          if (this.mIsTypingVisible != bool)
          {
            View localView1 = this.mTypingView;
            i = 0;
            if (!bool)
              break label304;
            localView1.setVisibility(i);
            ((ListView)this.mListView).setLayoutParams(this.defaultListViewLayoutParams);
            j = this.mTypingView.getHeight();
            if (!bool)
              break label311;
            RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(this.defaultListViewLayoutParams);
            localLayoutParams.addRule(2, this.mTypingView.getId());
            ((ListView)this.mListView).setLayoutParams(localLayoutParams);
            this.mTranslateListAnimation = new TranslateAnimation(0, 0.0F, 0, 0.0F, 0, j, 0, 0.0F);
            int k = ((View)((ListView)this.mListView).getParent()).getHeight();
            int m = ((View)((ListView)this.mListView).getParent()).getWidth();
            this.mTranslateListAnimation.initialize(((ListView)this.mListView).getWidth(), ((ListView)this.mListView).getHeight(), m, k);
            this.mTranslateListAnimation.setDuration(this.mSlideInUpAnimation.getDuration());
            View localView2 = this.mTypingView;
            if (!bool)
              break label336;
            localAnimation = this.mSlideInUpAnimation;
            localView2.startAnimation(localAnimation);
            ((ListView)this.mListView).startAnimation(this.mTranslateListAnimation);
          }
          this.mIsTypingVisible = bool;
          continue;
        }
      }
      finally
      {
      }
      boolean bool = false;
      continue;
      label304: int i = 8;
      continue;
      label311: this.mTranslateListAnimation = new TranslateAnimation(0, 0.0F, 0, 0.0F, 0, -j, 0, 0.0F);
      continue;
      label336: Animation localAnimation = this.mSlideOutDownAnimation;
    }
  }

  private boolean isTypingAnimationPlaying()
  {
    try
    {
      if ((this.mTranslateListAnimation != null) && (this.mTranslateListAnimation.hasStarted()))
      {
        boolean bool2 = this.mTranslateListAnimation.hasEnded();
        if (!bool2)
        {
          bool1 = true;
          return bool1;
        }
      }
      boolean bool1 = false;
    }
    finally
    {
    }
  }

  private void recordUserAction(OzActions paramOzActions)
  {
    if (this.mAccount != null)
    {
      FragmentActivity localFragmentActivity = getActivity();
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, paramOzActions, OzViews.getViewForLogging(localFragmentActivity));
    }
  }

  private void updateHeaderVisibility()
  {
    if (EsLog.isLoggable("MessageListFragment", 3))
      Log.d("MessageListFragment", "updateHeaderVisibility " + this.mLoadingOlderEvents + " " + this.mInitialLoadFinished);
    if ((this.mLoadingOlderEvents) && (this.mInitialLoadFinished))
      this.mHeaderView.setVisibility(0);
    while (true)
    {
      return;
      this.mHeaderView.setVisibility(8);
    }
  }

  private void updateTypingVisibility()
  {
    int i;
    label360: label367: 
    while (true)
    {
      String[] arrayOfString;
      try
      {
        TextView localTextView = this.mTypingTextView;
        if (localTextView == null)
          return;
        arrayOfString = new String[Math.min(3, this.mTypingParticipants.size())];
        Iterator localIterator = this.mTypingParticipants.values().iterator();
        i = 0;
        if (!localIterator.hasNext())
          break label367;
        UserTypingInfo localUserTypingInfo = (UserTypingInfo)localIterator.next();
        int i1 = i + 1;
        arrayOfString[i] = localUserTypingInfo.userName;
        if (i1 != 3)
        {
          i = i1;
          continue;
        }
        switch (this.mTypingParticipants.size())
        {
        default:
          int n = R.string.realtimechat_more_than_three_people_typing_text;
          Object[] arrayOfObject4 = new Object[3];
          arrayOfObject4[0] = arrayOfString[0];
          arrayOfObject4[1] = arrayOfString[1];
          arrayOfObject4[2] = Integer.valueOf(-2 + this.mTypingParticipants.size());
          str = getString(n, arrayOfObject4);
          this.mTypingTextView.setText(str);
          if (!isTypingAnimationPlaying())
            break label360;
          if (EsLog.isLoggable("MessageListFragment", 3))
            Log.d("MessageListFragment", "Animation already playing. Setting typing visibility changed");
          this.mTypingVisibilityChanged = true;
          continue;
        case 0:
        case 1:
        case 2:
        case 3:
        }
      }
      finally
      {
      }
      String str = this.mTypingTextView.getText().toString();
      continue;
      int m = R.string.realtimechat_one_person_typing_text;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = arrayOfString[0];
      str = getString(m, arrayOfObject3);
      continue;
      int k = R.string.realtimechat_two_people_typing_text;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = arrayOfString[0];
      arrayOfObject2[1] = arrayOfString[1];
      str = getString(k, arrayOfObject2);
      continue;
      int j = R.string.realtimechat_three_people_typing_text;
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = arrayOfString[0];
      arrayOfObject1[1] = arrayOfString[1];
      arrayOfObject1[2] = arrayOfString[2];
      str = getString(j, arrayOfObject1);
      continue;
      animateTypingVisibility();
    }
  }

  public final void displayLeaveConversationDialog()
  {
    AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.realtimechat_leave_conversation_title), getString(R.string.realtimechat_leave_conversation_text), getString(R.string.realtimechat_conversation_leave_menu_item_text), getString(R.string.cancel));
    localAlertFragmentDialog.setTargetFragment(this, 0);
    localAlertFragmentDialog.show(getFragmentManager(), "leave_conversation");
  }

  public final void handleFatalError(int paramInt)
  {
    int i;
    switch (paramInt)
    {
    default:
      i = R.string.realtimechat_conversation_error_dialog_general;
    case 3:
    case 4:
    }
    while (true)
    {
      Resources localResources = getResources();
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(localResources.getString(R.string.realtimechat_conversation_error_dialog_title), localResources.getString(i), getString(R.string.realtimechat_conversation_error_dialog_leave_button), null);
      localAlertFragmentDialog.setTargetFragment(this, 0);
      localAlertFragmentDialog.show(getFragmentManager(), "conversation_error");
      return;
      i = R.string.realtimechat_conversation_error_dialog_huddle_too_big;
      continue;
      i = R.string.realtimechat_conversation_error_dialog_some_invalid_participants;
    }
  }

  public final void messageLoadFailed()
  {
    if (EsLog.isLoggable("MessageListFragment", 3))
      Log.d("MessageListFragment", "messageLoadFailed");
    this.mLoadingOlderEvents = false;
    updateHeaderVisibility();
    Toast.makeText(getActivity(), R.string.realtimechat_failure_loading_messages, 0).show();
  }

  public final void messageLoadSucceeded()
  {
    if (EsLog.isLoggable("MessageListFragment", 3))
      Log.d("MessageListFragment", "messageLoadSucceeded");
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public final void run()
      {
        MessageListFragment.access$1202(MessageListFragment.this, false);
        MessageListFragment.this.updateHeaderVisibility();
      }
    }
    , 500L);
  }

  public final void onCancelButtonClicked(long paramLong)
  {
    RealTimeChatService.removeMessage(getActivity(), this.mAccount, paramLong);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FragmentActivity localFragmentActivity = getActivity();
    if (paramBundle != null)
      if (paramBundle.containsKey("request_id"))
      {
        this.mRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
        this.mLoadingOlderEvents = paramBundle.getBoolean("loading_older_events", false);
        this.mInitialLoadFinished = paramBundle.getBoolean("initial_load_finished", false);
        label62: this.mSingleParticipant = ((Data.Participant)localFragmentActivity.getIntent().getSerializableExtra("participant"));
        this.mAccount = ((EsAccount)getActivity().getIntent().getExtras().get("account"));
        this.mIsGroup = getActivity().getIntent().getBooleanExtra("is_group", false);
        long l = localFragmentActivity.getIntent().getLongExtra("conversation_row_id", -1L);
        if (l == -1L)
          break label278;
        this.mConversationRowId = Long.valueOf(l);
        this.mMessagesUri = EsProvider.buildMessagesUri(this.mAccount, this.mConversationRowId.longValue());
        getLoaderManager().initLoader(1, null, this);
      }
    while (true)
    {
      this.mSlideOutDownAnimation = AnimationUtils.loadAnimation(localFragmentActivity, R.anim.slide_out_down_self);
      this.mSlideOutDownAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          if (MessageListFragment.this.mTypingVisibilityChanged)
            MessageListFragment.this.mHandler.post(MessageListFragment.this.mAnimateTypingVisibilityRunnable);
        }

        public final void onAnimationRepeat(Animation paramAnonymousAnimation)
        {
        }

        public final void onAnimationStart(Animation paramAnonymousAnimation)
        {
        }
      });
      this.mSlideOutDownAnimation.setDuration(350L);
      this.mSlideInUpAnimation = AnimationUtils.loadAnimation(localFragmentActivity, R.anim.slide_in_up_self);
      this.mSlideInUpAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          if (MessageListFragment.this.mTypingVisibilityChanged)
            MessageListFragment.this.mHandler.post(MessageListFragment.this.mAnimateTypingVisibilityRunnable);
        }

        public final void onAnimationRepeat(Animation paramAnonymousAnimation)
        {
        }

        public final void onAnimationStart(Animation paramAnonymousAnimation)
        {
        }
      });
      this.mSlideInUpAnimation.setDuration(350L);
      return;
      this.mRequestId = null;
      break;
      this.mRequestId = null;
      this.mInitialLoadFinished = false;
      this.mLoadingOlderEvents = false;
      break label62;
      label278: this.mConversationRowId = null;
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1);
    for (EsCursorLoader localEsCursorLoader = new EsCursorLoader(getActivity(), this.mMessagesUri, MessagesQuery.PROJECTION, null, null, "timestamp"); ; localEsCursorLoader = null)
      return localEsCursorLoader;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.message_list_fragment, paramViewGroup, false);
    this.mListView = ((ListView)localView1.findViewById(16908298));
    this.defaultListViewLayoutParams = ((RelativeLayout.LayoutParams)((ListView)this.mListView).getLayoutParams());
    this.mTypingView = localView1.findViewById(R.id.typing_text);
    this.mTypingTextView = ((TextView)this.mTypingView.findViewById(R.id.typing_text_view));
    View localView2 = paramLayoutInflater.inflate(R.layout.message_list_item_loading_older, paramViewGroup);
    ((ListView)this.mListView).addHeaderView(localView2);
    this.mHeaderView = localView2.findViewById(R.id.message_list_item_loading_content);
    this.mAdapter = new MessageCursorAdapter(this, this.mListView, null);
    ((ListView)this.mListView).setAdapter(this.mAdapter);
    if (Build.VERSION.SDK_INT >= 11)
      ((ListView)this.mListView).setChoiceMode(0);
    ((ListView)this.mListView).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public final boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        CharSequence localCharSequence;
        if ((paramAnonymousView instanceof MessageListItemView))
          localCharSequence = ((MessageListItemView)paramAnonymousView).getMessage();
        while (true)
        {
          if ((localCharSequence != null) && (Build.VERSION.SDK_INT >= 11))
          {
            ClipboardManager localClipboardManager = (ClipboardManager)MessageListFragment.this.getActivity().getSystemService("clipboard");
            if (localClipboardManager != null)
            {
              localClipboardManager.setText(localCharSequence);
              Toast.makeText(MessageListFragment.this.getActivity(), R.string.copied_to_clipboard, 0).show();
            }
          }
          return true;
          boolean bool = paramAnonymousView instanceof SystemMessageListItemView;
          localCharSequence = null;
          if (bool)
            localCharSequence = ((SystemMessageListItemView)paramAnonymousView).getText();
        }
      }
    });
    ((ListView)this.mListView).setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public final void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if ((MessageListFragment.this.mInitialLoadFinished) && (!MessageListFragment.this.mLoadingOlderEvents) && (paramAnonymousInt1 == 0) && (MessageListFragment.this.mConversationRowId != null) && ((MessageListFragment.this.mEarliestEventTimestamp > MessageListFragment.this.mFirstEventTimestamp) || (MessageListFragment.this.mFirstEventTimestamp == 0L) || (MessageListFragment.this.mEarliestEventTimestamp == 0L)) && (MessageListFragment.this.mConversationId != null) && (!MessageListFragment.this.mConversationId.startsWith("c:")))
        {
          MessageListFragment.access$1202(MessageListFragment.this, true);
          MessageListFragment.this.mHeaderView.setVisibility(0);
          MessageListFragment.access$2202(MessageListFragment.this, ((MessageListFragment.MessageCursorAdapter)MessageListFragment.this.mAdapter).getCount());
          MessageListFragment.access$402(MessageListFragment.this, Integer.valueOf(RealTimeChatService.requestMoreEvents(MessageListFragment.this.getActivity(), MessageListFragment.this.mAccount, MessageListFragment.this.mConversationRowId.longValue())));
          MessageListFragment.this.updateHeaderVisibility();
        }
      }

      public final void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    if (this.mSingleParticipant != null)
    {
      View localView3 = localView1.findViewById(R.id.empty_conversation_view);
      TextView localTextView = (TextView)localView1.findViewById(R.id.empty_conversation_text);
      FragmentActivity localFragmentActivity = getActivity();
      int i = R.string.new_conversation_description;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mSingleParticipant.getFullName();
      localTextView.setText(localFragmentActivity.getString(i, arrayOfObject));
      localView3.setVisibility(0);
    }
    return localView1;
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
    if (paramString.equals("conversation_error"))
    {
      RealTimeChatService.leaveConversation(getActivity(), this.mAccount, this.mConversationRowId.longValue());
      this.mLeaveConversationListener.leaveConversation();
      getActivity().finish();
    }
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if (paramString.equals("leave_conversation"))
    {
      recordUserAction(OzActions.GROUP_CONVERSATION_LEAVE);
      RealTimeChatService.leaveConversation(getActivity(), this.mAccount, this.mConversationRowId.longValue());
      this.mLeaveConversationListener.leaveConversation();
      getActivity().finish();
    }
    while (true)
    {
      return;
      if (paramString.equals("conversation_error"))
      {
        RealTimeChatService.leaveConversation(getActivity(), this.mAccount, this.mConversationRowId.longValue());
        this.mLeaveConversationListener.leaveConversation();
        getActivity().finish();
      }
      else if (EsLog.isLoggable("MessageListFragment", 6))
      {
        Log.e("MessageListFragment", "invalidate dialog " + paramString);
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
    ((MessageCursorAdapter)this.mAdapter).swapCursor(null);
  }

  public final void onMediaImageClick(String paramString1, String paramString2)
  {
    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoViewActivityIntentBuilder(getActivity());
    localPhotoViewIntentBuilder.setAccount(this.mAccount).setPhotoOnly(Boolean.valueOf(true)).setPhotoUrl(paramString1).setGaiaId(paramString2).setAlbumName(getString(R.string.photo_view_messenger_title));
    startActivity(localPhotoViewIntentBuilder.build());
  }

  public final void onPause()
  {
    super.onPause();
    RealTimeChatService.unregisterListener(this.mRTCServiceListener);
    this.mHandler.removeCallbacks(this.mAnimateTypingVisibilityRunnable);
    this.mHandler.removeCallbacks(this.mCheckExpiredTypingRunnable);
  }

  public final void onResume()
  {
    super.onResume();
    if (getActivity().isFinishing())
      return;
    this.mIsTypingVisible = false;
    this.mTypingVisibilityChanged = false;
    this.mTypingParticipants.clear();
    RealTimeChatService.registerListener(this.mRTCServiceListener);
    if ((this.mConversationRowId != null) && (((MessageCursorAdapter)this.mAdapter).getCursor() == null))
      showEmptyViewProgress(getView());
    if ((this.mRequestId != null) && (!RealTimeChatService.isRequestPending(this.mRequestId.intValue())))
    {
      RealTimeChatServiceResult localRealTimeChatServiceResult = RealTimeChatService.removeResult(this.mRequestId.intValue());
      if (localRealTimeChatServiceResult != null)
      {
        if (localRealTimeChatServiceResult.getErrorCode() != 1)
          break label132;
        messageLoadSucceeded();
      }
    }
    while (true)
    {
      this.mRequestId = null;
      ((MessageCursorAdapter)this.mAdapter).onResume();
      break;
      label132: messageLoadFailed();
    }
  }

  public final void onRetryButtonClicked(long paramLong)
  {
    recordUserAction(OzActions.CONVERSATION_RETRY_SEND);
    RealTimeChatService.retrySendMessage(getActivity(), this.mAccount, paramLong);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mRequestId != null)
      paramBundle.putInt("request_id", this.mRequestId.intValue());
    paramBundle.putBoolean("loading_older_events", this.mLoadingOlderEvents);
    paramBundle.putBoolean("initial_load_finished", this.mInitialLoadFinished);
  }

  public final void onUserImageClicked(String paramString)
  {
    if (paramString != null)
    {
      Intent localIntent = Intents.getProfileActivityByGaiaIdIntent(getActivity(), this.mAccount, String.valueOf(paramString), null);
      getActivity().startActivity(localIntent);
    }
  }

  public final void reinitialize()
  {
    this.mInitialLoadFinished = false;
    this.mAccount = ((EsAccount)getActivity().getIntent().getExtras().get("account"));
    this.mIsGroup = getActivity().getIntent().getBooleanExtra("is_group", false);
    long l = getActivity().getIntent().getLongExtra("conversation_row_id", -1L);
    View localView;
    if (l != -1L)
    {
      this.mConversationRowId = Long.valueOf(l);
      this.mMessagesUri = EsProvider.buildMessagesUri(this.mAccount, this.mConversationRowId.longValue());
      getLoaderManager().restartLoader(1, null, this);
      this.mSingleParticipant = ((Data.Participant)getActivity().getIntent().getSerializableExtra("participant"));
      if (getView() != null)
      {
        localView = getView().findViewById(R.id.empty_conversation_view);
        if (this.mSingleParticipant == null)
          break label235;
        TextView localTextView = (TextView)getView().findViewById(R.id.empty_conversation_text);
        FragmentActivity localFragmentActivity = getActivity();
        int i = R.string.new_conversation_description;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.mSingleParticipant.getFullName();
        localTextView.setText(localFragmentActivity.getString(i, arrayOfObject));
        localView.setVisibility(0);
      }
    }
    while (true)
    {
      this.mTypingParticipants.clear();
      animateTypingVisibility();
      return;
      this.mConversationRowId = null;
      break;
      label235: localView.setVisibility(8);
    }
  }

  public final void setConversationInfo(String paramString, long paramLong1, long paramLong2)
  {
    if (EsLog.isLoggable("MessageListFragment", 3))
      Log.d("MessageListFragment", "setConversationInfo first " + paramLong1 + " earliest local " + paramLong2);
    this.mConversationId = paramString;
    this.mFirstEventTimestamp = paramLong1;
    this.mEarliestEventTimestamp = paramLong2;
  }

  public final void setLeaveConversationListener(LeaveConversationListener paramLeaveConversationListener)
  {
    this.mLeaveConversationListener = paramLeaveConversationListener;
  }

  public final void setParticipantList(HashMap<String, Data.Participant> paramHashMap)
  {
    this.mParticipantList = paramHashMap;
  }

  public static abstract interface LeaveConversationListener
  {
    public abstract void leaveConversation();
  }

  static final class MessageCursorAdapter extends EsCursorAdapter
    implements MessageListItemViewImage.OnMeasuredListener
  {
    final MessageListFragment mFragment;
    final List<View> mViews;

    public MessageCursorAdapter(MessageListFragment paramMessageListFragment, AbsListView paramAbsListView, Cursor paramCursor)
    {
      super(null);
      this.mFragment = paramMessageListFragment;
      this.mViews = new ArrayList();
      paramAbsListView.setRecyclerListener(new AbsListView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          if ((paramAnonymousView instanceof MessageListItemView))
            ((MessageListItemView)paramAnonymousView).clear();
          while (true)
          {
            MessageListFragment.MessageCursorAdapter.this.mViews.remove(paramAnonymousView);
            return;
            if ((paramAnonymousView instanceof MessageListItemViewImage))
              ((MessageListItemViewImage)paramAnonymousView).clear();
          }
        }
      });
    }

    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (!this.mDataValid)
        throw new IllegalStateException("this should only be called when the cursor is valid");
      if (!this.mCursor.moveToPosition(paramInt))
        throw new IllegalStateException("couldn't move cursor to position " + paramInt);
      LayoutInflater localLayoutInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
      int i = this.mCursor.getInt(7);
      Object localObject1;
      String str2;
      long l3;
      CharSequence localCharSequence2;
      String str3;
      if (i == 1)
        if (this.mCursor.getString(11) != null)
          if ((paramView != null) && ((paramView instanceof MessageListItemViewImage)))
          {
            localObject1 = (MessageListItemViewImage)paramView;
            Context localContext4 = this.mContext;
            if (!this.mViews.contains(localObject1))
              this.mViews.add(localObject1);
            ((MessageListItemViewImage)localObject1).clear();
            str2 = this.mCursor.getString(3);
            l3 = this.mCursor.getLong(5) / 1000L;
            localCharSequence2 = Dates.getShortRelativeTimeSpanString(localContext4, l3);
            ((MessageListItemViewImage)localObject1).setMessageRowId(this.mCursor.getLong(0));
            ((MessageListItemViewImage)localObject1).setAuthorName(this.mCursor.getString(9));
            str3 = this.mCursor.getString(11);
            if (str3.length() > 0)
              if (!str3.startsWith("//"))
                break label1299;
          }
      label384: label1293: label1299: for (String str4 = "http:" + str3; ; str4 = str3)
      {
        int i2;
        int i3;
        Object localObject2;
        int n;
        if (((MessageListItemViewImage)localObject1).getImageWidth() != null)
        {
          i2 = ((MessageListItemViewImage)localObject1).getImageWidth().intValue();
          i3 = ((MessageListItemViewImage)localObject1).getImageHeight().intValue();
          if (EsLog.isLoggable("MessageListFragment", 4))
            Log.i("MessageListFragment", "BindUserImageMessageView image width " + i2 + " height " + i3);
          if (str4.startsWith("content://"))
          {
            localObject2 = new LocalImageRequest(new MediaRef(null, 0L, null, Uri.parse(str4), MediaRef.MediaType.IMAGE), i2, i3);
            ((MessageListItemViewImage)localObject1).setImage(str3, (ImageRequest)localObject2);
            boolean bool6 = this.mCursor.isFirst();
            n = 0;
            if (!bool6)
            {
              boolean bool8 = this.mCursor.moveToPrevious();
              n = 0;
              if (bool8)
              {
                long l4 = this.mCursor.getLong(5) / 1000L;
                if ((!str2.equals(this.mCursor.getString(3))) || (this.mCursor.getInt(7) != 1) || (l3 - l4 > MessageListFragment.COLLAPSE_POSTS_THRESHOLD))
                  break label1293;
              }
            }
          }
        }
        for (int i1 = 1; ; i1 = 0)
        {
          this.mCursor.moveToNext();
          n = i1;
          boolean bool7;
          if ((str2 != null) && (str2.equals(this.mFragment.mAccount.getRealTimeChatParticipantId())) && (!this.mFragment.mIsGroup))
          {
            bool7 = true;
            label545: ((MessageListItemViewImage)localObject1).setMessageStatus(this.mCursor.getInt(6), bool7);
            ((MessageListItemViewImage)localObject1).setTimeSince(localCharSequence2);
            ((MessageListItemViewImage)localObject1).setGaiaId(EsPeopleData.extractGaiaId(str2));
            if (n == 0)
              break label669;
            ((MessageListItemViewImage)localObject1).hideAuthor();
          }
          while (true)
          {
            ((MessageListItemViewImage)localObject1).updateContentDescription();
            return localObject1;
            localObject1 = (MessageListItemViewImage)localLayoutInflater.inflate(R.layout.message_list_item_view_image, null);
            ((MessageListItemViewImage)localObject1).setMessageClickListener(this.mFragment);
            break;
            localObject2 = new MediaImageRequest(ImageUtils.getCenterCroppedAndResizedUrl(i2, i3, str3), 3, i2, i3, false);
            break label384;
            ((MessageListItemViewImage)localObject1).setOnMeasureListener(this);
            localObject2 = null;
            break label384;
            bool7 = false;
            break label545;
            label669: ((MessageListItemViewImage)localObject1).showAuthor();
          }
          label694: boolean bool2;
          if ((paramView != null) && ((paramView instanceof MessageListItemView)))
          {
            localObject1 = (MessageListItemView)paramView;
            Context localContext3 = this.mContext;
            if (!this.mViews.contains(localObject1))
              this.mViews.add(localObject1);
            ((MessageListItemView)localObject1).clear();
            String str1 = this.mCursor.getString(3);
            long l1 = this.mCursor.getLong(5) / 1000L;
            CharSequence localCharSequence1 = Dates.getShortRelativeTimeSpanString(localContext3, l1);
            ((MessageListItemView)localObject1).setMessageRowId(this.mCursor.getLong(0));
            ((MessageListItemView)localObject1).setAuthorName(this.mCursor.getString(9));
            ((MessageListItemView)localObject1).setMessage(this.mCursor.getString(4));
            boolean bool1 = this.mCursor.isFirst();
            int j = 0;
            if (!bool1)
            {
              boolean bool3 = this.mCursor.moveToPrevious();
              j = 0;
              if (bool3)
              {
                long l2 = this.mCursor.getLong(5) / 1000L;
                boolean bool4 = str1.equals(this.mCursor.getString(3));
                j = 0;
                if (bool4)
                {
                  int m = this.mCursor.getInt(7);
                  j = 0;
                  if (m == 1)
                  {
                    boolean bool5 = l1 - l2 < MessageListFragment.COLLAPSE_POSTS_THRESHOLD;
                    j = 0;
                    if (!bool5)
                      j = 1;
                  }
                }
                this.mCursor.moveToNext();
              }
            }
            int k = j;
            if ((str1 == null) || (!str1.equals(this.mFragment.mAccount.getRealTimeChatParticipantId())) || (this.mFragment.mIsGroup))
              break label1066;
            bool2 = true;
            label987: ((MessageListItemView)localObject1).setMessageStatus(this.mCursor.getInt(6), bool2);
            ((MessageListItemView)localObject1).setTimeSince(localCharSequence1);
            ((MessageListItemView)localObject1).setGaiaId(EsPeopleData.extractGaiaId(str1));
            if (k == 0)
              break label1072;
            ((MessageListItemView)localObject1).hideAuthor();
          }
          while (true)
          {
            ((MessageListItemView)localObject1).updateContentDescription();
            break;
            localObject1 = (MessageListItemView)localLayoutInflater.inflate(R.layout.message_list_item_view, null);
            ((MessageListItemView)localObject1).setMessageClickListener(this.mFragment);
            break label694;
            label1066: bool2 = false;
            break label987;
            label1072: ((MessageListItemView)localObject1).showAuthor();
          }
          if (i == 6)
          {
            if ((paramView != null) && ((paramView instanceof HangoutTileEventMessageListItemView)));
            for (localObject1 = (HangoutTileEventMessageListItemView)paramView; ; localObject1 = (HangoutTileEventMessageListItemView)localLayoutInflater.inflate(R.layout.hangout_tile_event_message_list_item_view, null))
            {
              Context localContext2 = this.mContext;
              ((HangoutTileEventMessageListItemView)localObject1).setType(this.mCursor.getInt(7));
              ((HangoutTileEventMessageListItemView)localObject1).setText(this.mCursor.getString(4));
              ((HangoutTileEventMessageListItemView)localObject1).setTimeSince(Dates.getShortRelativeTimeSpanString(localContext2, this.mCursor.getLong(5) / 1000L));
              ((HangoutTileEventMessageListItemView)localObject1).updateContentDescription();
              break;
            }
          }
          if ((paramView != null) && ((paramView instanceof SystemMessageListItemView)));
          for (localObject1 = (SystemMessageListItemView)paramView; ; localObject1 = (SystemMessageListItemView)localLayoutInflater.inflate(R.layout.system_message_list_item_view, null))
          {
            Context localContext1 = this.mContext;
            ((SystemMessageListItemView)localObject1).setType(this.mCursor.getInt(7));
            ((SystemMessageListItemView)localObject1).setText(this.mCursor.getString(4));
            ((SystemMessageListItemView)localObject1).setTimeSince(Dates.getShortRelativeTimeSpanString(localContext1, this.mCursor.getLong(5) / 1000L));
            ((SystemMessageListItemView)localObject1).updateContentDescription();
            break;
          }
        }
      }
    }

    public final void onMeasured(View paramView)
    {
      MessageListItemViewImage localMessageListItemViewImage;
      int i;
      int j;
      String str;
      if ((paramView instanceof MessageListItemViewImage))
      {
        localMessageListItemViewImage = (MessageListItemViewImage)paramView;
        localMessageListItemViewImage.setOnMeasureListener(null);
        i = localMessageListItemViewImage.getImageWidth().intValue();
        j = localMessageListItemViewImage.getImageHeight().intValue();
        if (EsLog.isLoggable("MessageListFragment", 4))
          Log.i("MessageListFragment", "onMeasured image width " + i + " height " + j);
        str = localMessageListItemViewImage.getFullResUrl();
        if (!str.startsWith("content://"))
          break label131;
      }
      label131: for (Object localObject = new LocalImageRequest(new MediaRef(null, 0L, null, Uri.parse(str), MediaRef.MediaType.IMAGE), i, j); ; localObject = new MediaImageRequest(ImageUtils.getCenterCroppedAndResizedUrl(i, j, str), 3, i, j, false))
      {
        localMessageListItemViewImage.setImage(str, (ImageRequest)localObject);
        return;
      }
    }

    public final void onResume()
    {
      this.mViews.clear();
    }
  }

  public static abstract interface MessagesQuery
  {
    public static final String[] PROJECTION = { "_id", "message_id", "conversation_id", "author_id", "text", "timestamp", "status", "type", "author_full_name", "author_first_name", "author_type", "image_url" };
  }

  private final class RTCServiceListener extends RealTimeChatServiceListener
  {
    private RTCServiceListener()
    {
    }

    public final void onResponseReceived$1587694a(int paramInt, RealTimeChatServiceResult paramRealTimeChatServiceResult)
    {
      if ((MessageListFragment.this.mRequestId != null) && (MessageListFragment.this.mRequestId.intValue() == paramInt))
      {
        if (paramRealTimeChatServiceResult.getErrorCode() != 1)
          break label40;
        MessageListFragment.this.messageLoadSucceeded();
      }
      while (true)
      {
        return;
        label40: MessageListFragment.this.messageLoadFailed();
        if (EsLog.isLoggable("MessageListFragment", 4))
          Log.i("MessageListFragment", "message load failed " + paramRealTimeChatServiceResult.getErrorCode());
      }
    }

    public final void onResponseTimeout(int paramInt)
    {
      if ((MessageListFragment.this.mRequestId != null) && (MessageListFragment.this.mRequestId.intValue() == paramInt))
      {
        MessageListFragment.this.messageLoadFailed();
        if (EsLog.isLoggable("MessageListFragment", 4))
          Log.i("MessageListFragment", "message load timeout");
      }
    }

    public final void onUserTypingStatusChanged(long paramLong, String paramString1, String paramString2, boolean paramBoolean)
    {
      if ((MessageListFragment.this.mConversationRowId != null) && (MessageListFragment.this.mConversationRowId.longValue() == paramLong))
      {
        HashMap localHashMap = MessageListFragment.this.mParticipantList;
        Data.Participant localParticipant = null;
        if (localHashMap != null)
          localParticipant = (Data.Participant)MessageListFragment.this.mParticipantList.get(paramString2);
        if (localParticipant == null)
          break label216;
        if (!paramBoolean)
          break label200;
        MessageListFragment.this.mTypingParticipants.put(paramString2, new MessageListFragment.UserTypingInfo(MessageListFragment.this, localParticipant.getFullName()));
        if (EsLog.isLoggable("MessageListFragment", 3))
          Log.d("MessageListFragment", "Typing status for " + localParticipant.getFullName() + " changed to " + paramBoolean);
        MessageListFragment.this.mHandler.removeCallbacks(MessageListFragment.this.mCheckExpiredTypingRunnable);
        MessageListFragment.this.mHandler.post(MessageListFragment.this.mCheckExpiredTypingRunnable);
        MessageListFragment.this.mHandler.postDelayed(MessageListFragment.this.mCheckExpiredTypingRunnable, 31000L);
      }
      while (true)
      {
        return;
        label200: MessageListFragment.this.mTypingParticipants.remove(paramString2);
        break;
        label216: if (EsLog.isLoggable("MessageListFragment", 6))
          Log.e("MessageListFragment", "Typing status for non existing participant " + paramString2 + " conversation " + paramString1);
      }
    }
  }

  private final class UserTypingInfo
  {
    public long typingStartTimeMs;
    public String userName;

    public UserTypingInfo(String arg2)
    {
      Object localObject;
      this.userName = localObject;
      this.typingStartTimeMs = System.currentTimeMillis();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.MessageListFragment
 * JD-Core Version:    0.6.2
 */