package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.ComposeMessageFragment;
import com.google.android.apps.plus.fragments.ComposeMessageFragment.Listener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.MessageListFragment;
import com.google.android.apps.plus.fragments.MessageListFragment.LeaveConversationListener;
import com.google.android.apps.plus.fragments.ParticipantsGalleryFragment;
import com.google.android.apps.plus.hangout.GCommApp;
import com.google.android.apps.plus.hangout.GCommNativeWrapper;
import com.google.android.apps.plus.hangout.HangoutPhoneTile;
import com.google.android.apps.plus.hangout.HangoutTabletTile;
import com.google.android.apps.plus.hangout.HangoutTile;
import com.google.android.apps.plus.hangout.HangoutTile.HangoutTileActivity;
import com.google.android.apps.plus.hangout.Log;
import com.google.android.apps.plus.realtimechat.CreateConversationOperation.ConversationResult;
import com.google.android.apps.plus.realtimechat.ParticipantUtils;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.android.apps.plus.views.ConversationTile;
import com.google.android.apps.plus.views.ParticipantsGalleryView;
import com.google.android.apps.plus.views.ParticipantsGalleryView.SimpleCommandListener;
import com.google.android.apps.plus.views.Tile;
import com.google.android.apps.plus.views.Tile.ParticipantPresenceListener;
import com.google.wireless.realtimechat.proto.Client.ClientConversation;
import com.google.wireless.realtimechat.proto.Client.Typing.Type;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class ConversationActivity extends EsFragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, MessageListFragment.LeaveConversationListener, HangoutTile.HangoutTileActivity, ImageUtils.InsertCameraPhotoDialogDisplayer
{
  private static int sInstanceCount;
  private final Tile.ParticipantPresenceListener conversationParticipantPresenceListener = new ConversationParticipantPresenceListener((byte)0);
  private EsAccount mAccount;
  private boolean mAdvancedHangoutsEnabled;
  private boolean mCheckExtraTile;
  private ComposeMessageFragment mComposeMessageFragment;
  private ParticipantsGalleryFragment mConversationHeader;
  private String mConversationId;
  private String mConversationName;
  private Long mConversationRowId;
  private ConversationTile mConversationTile;
  private int mCreateConversationRequestId;
  private Tile mCurrentTile;
  private long mEarliestEventTimestamp;
  private long mFirstEventTimestamp;
  private int mFirstHangoutMenuItemIndex;
  private HangoutTile mHangoutTile;
  private boolean mIsConversationLoaded;
  private boolean mIsGroup;
  private boolean mIsMuted;
  private int mLastHangoutMenuItemIndex;
  private MessageListFragment mMessageListFragment;
  private boolean mNeedToInviteParticipants;
  private int mParticipantCount;
  private HashMap<String, Data.Participant> mParticipantList;
  private final RTCServiceListener mRealTimeChatListener = new RTCServiceListener((byte)0);
  private AudienceData mResultAudience;
  private View mRootView;
  private boolean mShakeDetectorWasRunning;
  private Data.Participant mSingleParticipant;
  private LinearLayout mTileContainer;
  private MenuItem mTileSelectorMenuItem;

  static
  {
    if (!ConversationActivity.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public static boolean hasInstance()
  {
    if (sInstanceCount > 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void initHangoutTile()
  {
    if (this.mHangoutTile == null)
    {
      Hangout.SupportStatus localSupportStatus = Hangout.getSupportedStatus(this, this.mAccount);
      if ((this.mConversationId.startsWith("c:")) || (localSupportStatus != Hangout.SupportStatus.SUPPORTED))
        return;
    }
    while (true)
    {
      try
      {
        GCommApp.getInstance(this).getGCommNativeWrapper().getCurrentState();
        if (!this.mAdvancedHangoutsEnabled)
          break label249;
        this.mHangoutTile = new HangoutTabletTile(this);
        this.mHangoutTile.onCreate(null);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
        this.mHangoutTile.setLayoutParams(localLayoutParams);
        this.mTileContainer.addView(this.mHangoutTile);
        this.mHangoutTile.addParticipantPresenceListener(this.conversationParticipantPresenceListener);
        this.mHangoutTile.onStart();
        if (Build.VERSION.SDK_INT < 11)
          break label268;
        invalidateOptionsMenu();
        Hangout.Info localInfo = new Hangout.Info(Hangout.RoomType.EXTERNAL, null, "messenger", this.mConversationId, null, Hangout.LaunchSource.Messenger, false);
        this.mHangoutTile.setHangoutInfo(this.mAccount, localInfo, new ArrayList(this.mParticipantList.values()), false, false);
        if (!this.mCheckExtraTile)
          break;
        this.mCheckExtraTile = false;
        if (((!shouldShowHangoutTile()) || (this.mCurrentTile != this.mConversationTile)) && (this.mCurrentTile != this.mHangoutTile))
          break;
        toggleTiles();
      }
      catch (LinkageError localLinkageError)
      {
        Log.error("Could not load hangout native library");
        localLinkageError.printStackTrace();
      }
      break;
      label249: this.mHangoutTile = new HangoutPhoneTile(this).setInnerActionBarEnabled(false);
      continue;
      label268: createTitlebarButtons(R.menu.conversation_activity_menu);
    }
  }

  private void initialize()
  {
    Log.error("initialize");
    this.mParticipantList = null;
    this.mConversationId = null;
    this.mCheckExtraTile = true;
    this.mConversationRowId = null;
    this.mIsConversationLoaded = false;
    Intent localIntent = getIntent();
    this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
    this.mIsGroup = localIntent.getBooleanExtra("is_group", false);
    assert (this.mConversationHeader != null);
    this.mConversationHeader.setAccount(this.mAccount);
    long l = localIntent.getLongExtra("conversation_row_id", -1L);
    if (l != -1L)
      this.mConversationRowId = Long.valueOf(l);
    this.mSingleParticipant = ((Data.Participant)localIntent.getSerializableExtra("participant"));
    if (this.mSingleParticipant != null)
    {
      this.mParticipantList = new HashMap();
      this.mParticipantList.put(this.mSingleParticipant.getParticipantId(), this.mSingleParticipant);
      displayParticipantsInTray();
      setConversationLabel(this.mSingleParticipant.getFullName());
      if (this.mConversationHeader != null)
        this.mConversationHeader.setParticipantListButtonVisibility(false);
      if (this.mComposeMessageFragment != null)
        this.mComposeMessageFragment.allowSendingImages(false);
    }
    while (true)
    {
      this.mConversationHeader.setCommandListener(new ParticipantsCommandListener(this.mConversationHeader.getParticipantsGalleryView()));
      return;
      getSupportLoaderManager().restartLoader(1, null, this);
      getSupportLoaderManager().restartLoader(2, null, this);
      this.mConversationTile.setConversationRowId(this.mConversationRowId);
      if (this.mConversationHeader != null)
        this.mConversationHeader.setParticipantListButtonVisibility(true);
      if (this.mComposeMessageFragment != null)
        this.mComposeMessageFragment.allowSendingImages(true);
    }
  }

  private void inviteMoreParticipants()
  {
    OzActions localOzActions;
    boolean bool2;
    if (this.mIsGroup)
    {
      localOzActions = OzActions.GROUP_CONVERSATION_ADD_PEOPLE;
      recordUserAction(localOzActions);
      if ((this.mParticipantList == null) || (!this.mIsConversationLoaded))
        break label92;
      Collection localCollection = this.mParticipantList.values();
      boolean bool1 = this.mIsGroup;
      EsAccount localEsAccount = this.mAccount;
      if (this.mCurrentTile != this.mHangoutTile)
        break label86;
      bool2 = true;
      label63: ParticipantHelper.inviteMoreParticipants(this, localCollection, bool1, localEsAccount, bool2);
    }
    label86: label92: for (this.mNeedToInviteParticipants = false; ; this.mNeedToInviteParticipants = true)
    {
      return;
      localOzActions = OzActions.ONE_ON_ONE_CONVERSATION_ADD_PEOPLE;
      break;
      bool2 = false;
      break label63;
    }
  }

  private MenuItem prepareToggleTilesMenu(Menu paramMenu)
  {
    MenuItem localMenuItem = paramMenu.findItem(R.id.realtimechat_conversation_toggle_tile_menu_item);
    if (this.mHangoutTile != null)
    {
      int i;
      if (this.mCurrentTile == this.mHangoutTile)
      {
        i = 1;
        if (i == 0)
          break label113;
      }
      label113: for (int j = R.drawable.ic_speech_bubble; ; j = R.drawable.ic_menu_hangout)
      {
        localMenuItem.setIcon(j);
        localMenuItem.setVisible(true);
        localMenuItem.setEnabled(true);
        if (i != 0)
          break label137;
        for (int k = this.mFirstHangoutMenuItemIndex; k < this.mLastHangoutMenuItemIndex; k++)
          paramMenu.getItem(k).setVisible(false);
        i = 0;
        break;
      }
    }
    localMenuItem.setEnabled(false);
    localMenuItem.setVisible(false);
    label137: if (Build.VERSION.SDK_INT >= 11);
    while (true)
    {
      return localMenuItem;
      localMenuItem = null;
    }
  }

  private void setConversationLabel(String paramString)
  {
    if (Build.VERSION.SDK_INT < 11)
    {
      showTitlebar(true);
      setTitlebarTitle(paramString);
    }
    while (true)
    {
      this.mConversationName = paramString;
      return;
      getActionBar().setTitle(paramString);
    }
  }

  private boolean shouldShowHangoutTile()
  {
    if ((getIntent().hasExtra("tile")) && (HangoutTile.class.getName().equals(getIntent().getStringExtra("tile"))));
    for (boolean bool = true; ; bool = false)
      return bool;
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

  public final void blockPerson(Serializable paramSerializable)
  {
    if (this.mHangoutTile != null)
      this.mHangoutTile.blockPerson(paramSerializable);
  }

  public final void displayParticipantsInTray()
  {
    if (this.mParticipantList == null);
    while (true)
    {
      return;
      if (this.mCurrentTile == this.mConversationTile)
      {
        HashSet localHashSet = new HashSet();
        if (this.mHangoutTile != null)
          localHashSet = this.mHangoutTile.getActiveParticipantIds();
        this.mConversationHeader.setParticipants(this.mParticipantList, this.mConversationTile.getActiveParticipantIds(), localHashSet);
      }
      else
      {
        this.mHangoutTile.setParticipants(this.mParticipantList, this.mConversationTile.getActiveParticipantIds());
      }
    }
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final Intent getGreenRoomParticipantListActivityIntent(ArrayList<Data.Participant> paramArrayList)
  {
    return getParticipantListActivityIntent();
  }

  public final Intent getHangoutNotificationIntent()
  {
    return Intents.getConversationActivityHangoutTileIntent(this, this.mAccount, this.mConversationRowId.longValue(), this.mIsGroup);
  }

  public final Intent getParticipantListActivityIntent()
  {
    EsAccount localEsAccount = this.mAccount;
    long l = this.mConversationRowId.longValue();
    String str = this.mConversationName;
    boolean bool1 = this.mIsGroup;
    if (this.mCurrentTile == this.mHangoutTile);
    for (boolean bool2 = true; ; bool2 = false)
      return Intents.getParticipantListActivityIntent(this, localEsAccount, l, str, bool1, bool2);
  }

  public final OzViews getViewForLogging()
  {
    if (getIntent().getExtras().getBoolean("is_group"));
    for (OzViews localOzViews = OzViews.CONVERSATION_GROUP; ; localOzViews = OzViews.CONVERSATION_ONE_ON_ONE)
      return localOzViews;
  }

  public final void hideInsertCameraPhotoDialog()
  {
    dismissDialog(2131361854);
  }

  public final void leaveConversation()
  {
    assert (this.mConversationId != null);
    GCommApp localGCommApp = GCommApp.getInstance(this);
    if (localGCommApp.isInHangout(new Hangout.Info(Hangout.RoomType.EXTERNAL, null, "messenger", this.mConversationId, null, Hangout.LaunchSource.Messenger, false)))
      localGCommApp.exitMeeting();
  }

  protected final boolean needsAsyncData()
  {
    return true;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1)
      if ((paramInt2 == -1) && (paramIntent != null))
        this.mResultAudience = ((AudienceData)paramIntent.getParcelableExtra("audience"));
    while (true)
    {
      return;
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof ComposeMessageFragment))
    {
      this.mComposeMessageFragment = ((ComposeMessageFragment)paramFragment);
      if (this.mConversationRowId == null)
      {
        this.mComposeMessageFragment.allowSendingImages(false);
        this.mComposeMessageFragment.setListener(new ComposeMessageFragment.Listener()
        {
          public final void onSendPhoto(String paramAnonymousString, int paramAnonymousInt)
          {
            switch (paramAnonymousInt)
            {
            default:
              if (paramAnonymousString.startsWith("content://"))
                RealTimeChatService.sendLocalPhoto(ConversationActivity.this, ConversationActivity.this.mAccount, ConversationActivity.this.mConversationRowId.longValue(), paramAnonymousString);
              break;
            case 2:
            case 1:
            }
            while (true)
            {
              return;
              ConversationActivity localConversationActivity2 = ConversationActivity.this;
              if (ConversationActivity.this.mIsGroup);
              for (OzActions localOzActions2 = OzActions.GROUP_CONVERSATION_TAKE_PHOTO; ; localOzActions2 = OzActions.ONE_ON_ONE_CONVERSATION_TAKE_PHOTO)
              {
                localConversationActivity2.recordUserAction(localOzActions2);
                break;
              }
              ConversationActivity localConversationActivity1 = ConversationActivity.this;
              if (ConversationActivity.this.mIsGroup);
              for (OzActions localOzActions1 = OzActions.GROUP_CONVERSATION_CHOOSE_PHOTO; ; localOzActions1 = OzActions.ONE_ON_ONE_CONVERSATION_CHOOSE_PHOTO)
              {
                localConversationActivity1.recordUserAction(localOzActions1);
                break;
              }
              RealTimeChatService.sendMessage(ConversationActivity.this, ConversationActivity.this.mAccount, ConversationActivity.this.mConversationRowId.longValue(), null, paramAnonymousString);
            }
          }

          public final void onSendTextMessage(String paramAnonymousString)
          {
            ConversationActivity localConversationActivity = ConversationActivity.this;
            OzActions localOzActions;
            if (ConversationActivity.this.mIsGroup)
            {
              localOzActions = OzActions.GROUP_CONVERSATION_POST;
              localConversationActivity.recordUserAction(localOzActions);
              if (ConversationActivity.this.mConversationRowId == null)
                break label69;
              RealTimeChatService.sendMessage(ConversationActivity.this, ConversationActivity.this.mAccount, ConversationActivity.this.mConversationRowId.longValue(), paramAnonymousString, null);
            }
            while (true)
            {
              return;
              localOzActions = OzActions.ONE_ON_ONE_CONVERSATION_POST;
              break;
              label69: AudienceData localAudienceData = new AudienceData(ParticipantUtils.makePersonFromParticipant(ConversationActivity.this.mSingleParticipant));
              ConversationActivity.access$302(ConversationActivity.this, RealTimeChatService.createConversation(ConversationActivity.this, ConversationActivity.this.mAccount, localAudienceData, paramAnonymousString));
            }
          }

          public final void onTypingStatusChanged(Client.Typing.Type paramAnonymousType)
          {
            if (ConversationActivity.this.mConversationRowId != null)
              RealTimeChatService.sendTypingRequest(ConversationActivity.this, ConversationActivity.this.mAccount, ConversationActivity.this.mConversationRowId.longValue(), paramAnonymousType);
          }
        });
      }
    }
    label120: 
    do
    {
      do
        while (true)
        {
          return;
          this.mComposeMessageFragment.allowSendingImages(true);
          break;
          if (!(paramFragment instanceof MessageListFragment))
            break label120;
          this.mMessageListFragment = ((MessageListFragment)paramFragment);
          this.mMessageListFragment.setLeaveConversationListener(this);
          if (this.mConversationId != null)
            this.mMessageListFragment.setConversationInfo(this.mConversationId, this.mFirstEventTimestamp, this.mEarliestEventTimestamp);
          this.mMessageListFragment.setParticipantList(this.mParticipantList);
        }
      while (!(paramFragment instanceof ParticipantsGalleryFragment));
      this.mConversationHeader = ((ParticipantsGalleryFragment)paramFragment);
    }
    while (($assertionsDisabled) || (this.mAccount == null));
    throw new AssertionError();
  }

  public final void onBlockCompleted(boolean paramBoolean)
  {
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == R.id.title_button_1)
      toggleTiles();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAdvancedHangoutsEnabled = Hangout.isAdvancedUiSupported(this);
    FrameLayout local1 = new FrameLayout(this)
    {
      protected void onMeasure(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        ConversationActivity.access$600(ConversationActivity.this, paramAnonymousInt2);
        super.onMeasure(paramAnonymousInt1, paramAnonymousInt2);
      }
    };
    local1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    getLayoutInflater().inflate(R.layout.conversation_activity, local1, true);
    this.mRootView = local1;
    setContentView(this.mRootView);
    Log.debug("ConversationActivity.onCreate");
    this.mTileContainer = ((LinearLayout)findViewById(R.id.tile_container));
    this.mConversationTile = ((ConversationTile)findViewById(R.id.conversation_tile));
    this.mConversationTile.addParticipantPresenceListener(this.conversationParticipantPresenceListener);
    this.mCurrentTile = this.mConversationTile;
    this.mParticipantCount = 0;
    if (Build.VERSION.SDK_INT < 11)
    {
      showTitlebar(true);
      createTitlebarButtons(R.menu.conversation_activity_menu);
    }
    while (true)
    {
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
      if (localShakeDetector != null)
        this.mShakeDetectorWasRunning = localShakeDetector.stop();
      initialize();
      int i = 1 + sInstanceCount;
      sInstanceCount = i;
      if (i > 1)
        Log.error("ConversationActivity onCreate instanceCount out of sync: " + sInstanceCount);
      return;
      getActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 2131361854);
    for (Dialog localDialog = ImageUtils.createInsertCameraPhotoDialog(this); ; localDialog = super.onCreateDialog(paramInt, paramBundle))
      return localDialog;
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Log.debug("ConversationActivity.onCreateLoader: " + paramInt);
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
        Uri localUri1 = EsProvider.buildParticipantsUri(this.mAccount, this.mConversationRowId.longValue());
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
    MenuInflater localMenuInflater = getMenuInflater();
    localMenuInflater.inflate(R.menu.conversation_activity_menu, paramMenu);
    if (Build.VERSION.SDK_INT >= 11)
    {
      paramMenu.findItem(R.id.realtimechat_conversation_toggle_tile_menu_item).setShowAsAction(2);
      paramMenu.findItem(R.id.realtimechat_conversation_invite_menu_item).setShowAsAction(2);
    }
    this.mFirstHangoutMenuItemIndex = paramMenu.size();
    if (this.mHangoutTile != null)
      this.mHangoutTile.onCreateOptionsMenu(paramMenu, localMenuInflater);
    this.mLastHangoutMenuItemIndex = paramMenu.size();
    return true;
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
    int i = -1 + sInstanceCount;
    sInstanceCount = i;
    if (i < 0)
      Log.error("ConversationActivity onDestroy instanceCount out of sync: " + sInstanceCount);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onMeetingMediaStarted()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("AUTHOR_PROFILE_ID", this.mAccount.getPersonId());
    RealTimeChatService.sendTileEvent(this, this.mAccount, this.mConversationId, "com.google.hangouts", 0, "JOIN_HANGOUT", localHashMap);
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
    Log.debug("ConversationActivity.onNewIntent");
    initialize();
    this.mMessageListFragment.reinitialize();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    boolean bool;
    if (i == 16908332)
    {
      goHome(this.mAccount);
      bool = true;
    }
    while (true)
    {
      return bool;
      OzActions localOzActions2;
      if (i == R.id.realtimechat_conversation_mute_menu_item)
        if (this.mIsGroup)
        {
          localOzActions2 = OzActions.GROUP_CONVERSATION_MUTE;
          label45: recordUserAction(localOzActions2);
          RealTimeChatService.setConversationMuted(this, this.mAccount, this.mConversationRowId.longValue(), true);
          this.mIsMuted = true;
          getIntent().putExtra("conversation_is_muted", true);
        }
      label258: 
      do
        while (true)
        {
          bool = false;
          break;
          localOzActions2 = OzActions.ONE_ON_ONE_CONVERSATION_MUTE;
          break label45;
          if (i == R.id.realtimechat_conversation_unmute_menu_item)
          {
            if (this.mIsGroup);
            for (OzActions localOzActions1 = OzActions.GROUP_CONVERSATION_UNMUTE; ; localOzActions1 = OzActions.ONE_ON_ONE_CONVERSATION_UNMUTE)
            {
              recordUserAction(localOzActions1);
              RealTimeChatService.setConversationMuted(this, this.mAccount, this.mConversationRowId.longValue(), false);
              this.mIsMuted = false;
              getIntent().putExtra("conversation_is_muted", false);
              break;
            }
          }
          if (i == R.id.realtimechat_conversation_leave_menu_item)
          {
            if (this.mMessageListFragment != null)
              this.mMessageListFragment.displayLeaveConversationDialog();
          }
          else if (i == R.id.realtimechat_conversation_toggle_tile_menu_item)
          {
            toggleTiles();
          }
          else if (i == R.id.realtimechat_conversation_edit_name_menu_item)
          {
            new ConversationRenameDialog(this.mConversationName, this.mConversationRowId.longValue()).show(getSupportFragmentManager(), "rename_conversation");
          }
          else
          {
            if (i != R.id.realtimechat_conversation_invite_menu_item)
              break label258;
            inviteMoreParticipants();
          }
        }
      while (this.mHangoutTile == null);
      bool = this.mHangoutTile.onOptionsItemSelected(paramMenuItem);
    }
  }

  public void onPause()
  {
    Log.debug("ConversationActivity.onPause");
    RealTimeChatService.registerListener(this.mRealTimeChatListener);
    this.mCurrentTile.onTilePause();
    this.mConversationTile.onPause();
    if (this.mHangoutTile != null)
      this.mHangoutTile.onPause();
    super.onPause();
    RealTimeChatService.allowDisconnect(this, this.mAccount);
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    boolean bool1 = this.mIsConversationLoaded;
    boolean bool2 = false;
    if (!bool1)
      return bool2;
    if (this.mConversationRowId == null)
    {
      paramMenu.findItem(R.id.realtimechat_conversation_invite_menu_item).setVisible(true);
      paramMenu.findItem(R.id.realtimechat_conversation_mute_menu_item).setVisible(false);
      paramMenu.findItem(R.id.realtimechat_conversation_unmute_menu_item).setVisible(false);
      paramMenu.findItem(R.id.realtimechat_conversation_edit_name_menu_item).setVisible(false);
      paramMenu.findItem(R.id.realtimechat_conversation_leave_menu_item).setVisible(false);
      paramMenu.findItem(R.id.realtimechat_conversation_toggle_tile_menu_item).setVisible(false);
    }
    label158: label296: 
    while (true)
    {
      bool2 = true;
      break;
      paramMenu.findItem(R.id.realtimechat_conversation_invite_menu_item).setVisible(false);
      MenuItem localMenuItem = paramMenu.findItem(R.id.realtimechat_conversation_mute_menu_item);
      boolean bool3;
      if (!this.mIsMuted)
      {
        bool3 = true;
        localMenuItem.setVisible(bool3);
        paramMenu.findItem(R.id.realtimechat_conversation_unmute_menu_item).setVisible(this.mIsMuted);
        paramMenu.findItem(R.id.realtimechat_conversation_edit_name_menu_item).setVisible(this.mIsGroup);
        paramMenu.findItem(R.id.realtimechat_conversation_leave_menu_item).setVisible(true);
        if (Build.VERSION.SDK_INT < 11)
          break label263;
        this.mTileSelectorMenuItem = prepareToggleTilesMenu(paramMenu);
      }
      while (true)
      {
        if (this.mHangoutTile == null)
          break label296;
        this.mHangoutTile.onPrepareOptionsMenu(paramMenu);
        break;
        bool3 = false;
        break label158;
        paramMenu.findItem(R.id.realtimechat_conversation_invite_menu_item).setVisible(false);
        paramMenu.findItem(R.id.realtimechat_conversation_toggle_tile_menu_item).setVisible(false);
      }
    }
  }

  public final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    paramMenu.findItem(R.id.realtimechat_conversation_invite_menu_item).setVisible(true);
    prepareToggleTilesMenu(paramMenu);
    paramMenu.findItem(R.id.realtimechat_conversation_mute_menu_item).setVisible(false);
    paramMenu.findItem(R.id.realtimechat_conversation_unmute_menu_item).setVisible(false);
    paramMenu.findItem(R.id.realtimechat_conversation_edit_name_menu_item).setVisible(false);
    paramMenu.findItem(R.id.realtimechat_conversation_leave_menu_item).setVisible(false);
  }

  public void onResume()
  {
    super.onResume();
    Log.debug("ConversationActivity.onResume");
    RealTimeChatService.registerListener(this.mRealTimeChatListener);
    this.mConversationTile.onResume();
    if (this.mHangoutTile != null)
      this.mHangoutTile.onResume();
    this.mCurrentTile.onTileResume();
    if (isIntentAccountActive())
    {
      if (this.mComposeMessageFragment != null)
        this.mComposeMessageFragment.setAllowSendMessage(true);
      if (this.mResultAudience != null)
      {
        RealTimeChatService.inviteParticipants(this, this.mAccount, this.mConversationRowId.longValue(), this.mResultAudience);
        this.mResultAudience = null;
      }
      RealTimeChatService.connectAndStayConnected(this, this.mAccount);
      displayParticipantsInTray();
    }
    while (true)
    {
      return;
      finish();
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
  }

  protected void onStart()
  {
    super.onStart();
    Log.debug("ConversationActivity.onStart");
    this.mConversationTile.onStart();
    if (this.mHangoutTile != null)
      this.mHangoutTile.onStart();
    this.mCurrentTile.onTileStart();
    this.mComposeMessageFragment.requestFocus();
  }

  public void onStop()
  {
    super.onStop();
    Log.debug("ConversationActivity.onStop");
    this.mCurrentTile.onTileStop();
    this.mConversationTile.onStop();
    if (this.mHangoutTile != null)
      this.mHangoutTile.onStop();
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }

  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      RealTimeChatService.setCurrentConversationRowId(this.mConversationRowId);
      if ((!shouldShowHangoutTile()) && (this.mConversationRowId != null))
        RealTimeChatService.markConversationRead(this, this.mAccount, this.mConversationRowId.longValue());
    }
    while (true)
    {
      return;
      RealTimeChatService.setCurrentConversationRowId(null);
    }
  }

  public final void showInsertCameraPhotoDialog()
  {
    showDialog(2131361854);
  }

  public final void stopHangoutTile()
  {
    if (this.mCurrentTile == this.mHangoutTile)
      toggleTiles();
  }

  public final void toggleTiles()
  {
    assert (this.mHangoutTile != null);
    if (this.mHangoutTile == null);
    while (true)
    {
      return;
      Object localObject;
      if (this.mCurrentTile == this.mHangoutTile)
      {
        localObject = this.mConversationTile;
        label45: this.mCurrentTile.setVisibility(8);
        this.mCurrentTile.onTilePause();
        this.mCurrentTile.onTileStop();
        ((Tile)localObject).setVisibility(0);
        ((Tile)localObject).onTileStart();
        ((Tile)localObject).onTileResume();
        this.mCurrentTile = ((Tile)localObject);
        displayParticipantsInTray();
        if (Build.VERSION.SDK_INT < 11)
          break label193;
        if (this.mTileSelectorMenuItem != null)
          if (this.mCurrentTile != this.mHangoutTile)
            break label186;
      }
      label186: for (int i = R.drawable.ic_speech_bubble; ; i = R.drawable.ic_menu_hangout)
      {
        this.mTileSelectorMenuItem.setIcon(getResources().getDrawable(i));
        invalidateOptionsMenu();
        if (localObject != this.mConversationTile)
          break;
        ActionBar localActionBar = getActionBar();
        if (localActionBar == null)
          break;
        localActionBar.show();
        break;
        localObject = this.mHangoutTile;
        break label45;
      }
      label193: createTitlebarButtons(R.menu.conversation_activity_menu);
    }
  }

  private final class ConversationParticipantPresenceListener
    implements Tile.ParticipantPresenceListener
  {
    private ConversationParticipantPresenceListener()
    {
    }

    public final void onParticipantPresenceChanged()
    {
      ConversationActivity.this.displayParticipantsInTray();
    }
  }

  public static abstract interface ConversationQuery
  {
    public static final String[] PROJECTION = { "conversation_id", "is_muted", "is_group", "name", "generated_name", "first_event_timestamp", "earliest_event_timestamp", "fatal_error_type" };
  }

  private final class ParticipantsCommandListener extends ParticipantsGalleryView.SimpleCommandListener
  {
    ParticipantsCommandListener(ParticipantsGalleryView arg2)
    {
      super(ConversationActivity.this.mAccount);
    }

    public final void onShowParticipantList()
    {
      if ((ConversationActivity.this.mParticipantList != null) && (ConversationActivity.this.mIsConversationLoaded))
        ConversationActivity.this.startActivity(ConversationActivity.this.getParticipantListActivityIntent());
    }
  }

  public static abstract interface ParticipantsQuery
  {
    public static final String[] PROJECTION = { "_id", "participant_id", "full_name", "first_name", "type" };
  }

  private final class RTCServiceListener extends RealTimeChatServiceListener
  {
    private RTCServiceListener()
    {
    }

    public final void onConversationCreated$2ae26fbd(int paramInt, CreateConversationOperation.ConversationResult paramConversationResult, RealTimeChatServiceResult paramRealTimeChatServiceResult)
    {
      int i = 1;
      Intent localIntent;
      if (paramInt == ConversationActivity.this.mCreateConversationRequestId)
      {
        if (paramRealTimeChatServiceResult.getErrorCode() != i)
          break label144;
        if ((paramConversationResult.mConversation == null) || (paramConversationResult.mConversation.getParticipantCount() <= i))
          break label110;
        if (!HangoutTile.class.getName().equals(ConversationActivity.this.getIntent().getStringExtra("tile")))
          break label116;
        GCommApp.getInstance(ConversationActivity.this).exitMeeting();
        localIntent = Intents.getConversationActivityHangoutTileIntent(ConversationActivity.this, ConversationActivity.this.mAccount, paramConversationResult.mConversationRowId.longValue(), i);
        label100: ConversationActivity.this.startActivity(localIntent);
      }
      while (true)
      {
        return;
        label110: boolean bool = false;
        break;
        label116: localIntent = Intents.getConversationActivityIntent(ConversationActivity.this, ConversationActivity.this.mAccount, paramConversationResult.mConversationRowId.longValue(), bool);
        break label100;
        label144: if (paramRealTimeChatServiceResult.getErrorCode() == 4)
          Toast.makeText(ConversationActivity.this, R.string.conversation_too_large, 0).show();
        else
          Toast.makeText(ConversationActivity.this, R.string.error_creating_conversation, 0).show();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ConversationActivity
 * JD-Core Version:    0.6.2
 */