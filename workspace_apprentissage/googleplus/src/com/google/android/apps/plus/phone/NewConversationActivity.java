package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.AudienceFragment;
import com.google.android.apps.plus.fragments.ComposeMessageFragment;
import com.google.android.apps.plus.fragments.ComposeMessageFragment.Listener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.hangout.GCommApp;
import com.google.android.apps.plus.hangout.HangoutTile;
import com.google.android.apps.plus.hangout.Log;
import com.google.android.apps.plus.realtimechat.CreateConversationOperation.ConversationResult;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.wireless.realtimechat.proto.Client.ClientConversation;
import com.google.wireless.realtimechat.proto.Client.Typing.Type;

public class NewConversationActivity extends EsFragmentActivity
  implements ImageUtils.InsertCameraPhotoDialogDisplayer
{
  private static int sInstanceCount;
  private EsAccount mAccount;
  private AudienceFragment mAudienceFragment;
  private ComposeMessageFragment mComposeMessageFragment;
  private Integer mCreateConversationRequestId = null;
  private final RTCServiceListener mRTCServiceListener = new RTCServiceListener((byte)0);

  public static boolean hasInstance()
  {
    if (sInstanceCount > 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void updateAllowSendMessage()
  {
    ComposeMessageFragment localComposeMessageFragment;
    if (this.mComposeMessageFragment != null)
    {
      localComposeMessageFragment = this.mComposeMessageFragment;
      if ((this.mAudienceFragment == null) || (this.mAudienceFragment.isAudienceEmpty()))
        break label37;
    }
    label37: for (boolean bool = true; ; bool = false)
    {
      localComposeMessageFragment.setAllowSendMessage(bool);
      return;
    }
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CONVERSATION_START_NEW;
  }

  public final void hideInsertCameraPhotoDialog()
  {
    dismissDialog(2131361854);
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof ComposeMessageFragment))
    {
      this.mComposeMessageFragment = ((ComposeMessageFragment)paramFragment);
      updateAllowSendMessage();
      this.mComposeMessageFragment.allowSendingImages(false);
      this.mComposeMessageFragment.setListener(new ComposeMessageFragment.Listener()
      {
        public final void onSendPhoto(String paramAnonymousString, int paramAnonymousInt)
        {
        }

        public final void onSendTextMessage(String paramAnonymousString)
        {
          NewConversationActivity.access$300(NewConversationActivity.this, paramAnonymousString);
        }

        public final void onTypingStatusChanged(Client.Typing.Type paramAnonymousType)
        {
        }
      });
    }
    while (true)
    {
      return;
      if ((paramFragment instanceof AudienceFragment))
      {
        this.mAudienceFragment = ((AudienceFragment)paramFragment);
        this.mAudienceFragment.setCirclesUsageType(6);
        this.mAudienceFragment.setIncludePhoneOnlyContacts(true);
        this.mAudienceFragment.setIncludePlusPages(false);
        this.mAudienceFragment.setPublicProfileSearchEnabled(true);
        this.mAudienceFragment.setShowSuggestedPeople(true);
        updateAllowSendMessage();
        this.mAudienceFragment.setAudienceChangedCallback(new Runnable()
        {
          public final void run()
          {
            ComposeMessageFragment localComposeMessageFragment;
            if (NewConversationActivity.this.mComposeMessageFragment != null)
            {
              localComposeMessageFragment = NewConversationActivity.this.mComposeMessageFragment;
              if (NewConversationActivity.this.mAudienceFragment.isAudienceEmpty())
                break label39;
            }
            label39: for (boolean bool = true; ; bool = false)
            {
              localComposeMessageFragment.setAllowSendMessage(bool);
              return;
            }
          }
        });
      }
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
    setContentView(R.layout.new_conversation_activity);
    this.mAccount = EsService.getActiveAccount(this);
    if (Build.VERSION.SDK_INT < 11)
    {
      showTitlebar(true);
      setTitlebarTitle(getString(R.string.new_huddle_label));
    }
    if (paramBundle != null)
      if (!paramBundle.containsKey("requestId"))
        break label107;
    label107: for (this.mCreateConversationRequestId = Integer.valueOf(paramBundle.getInt("requestId")); ; this.mCreateConversationRequestId = null)
    {
      int i = 1 + sInstanceCount;
      sInstanceCount = i;
      if (i > 1)
        Log.error("NewConversationActivity onCreate instanceCount out of sync: " + sInstanceCount);
      return;
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 2131361854);
    for (Dialog localDialog = ImageUtils.createInsertCameraPhotoDialog(this); ; localDialog = super.onCreateDialog(paramInt, paramBundle))
      return localDialog;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    int i = -1 + sInstanceCount;
    sInstanceCount = i;
    if (i < 0)
    {
      Log.error("NewConversationActivity onDestroy instanceCount out of sync: " + sInstanceCount);
      sInstanceCount = 0;
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
      goHome(this.mAccount);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected void onPause()
  {
    super.onPause();
    RealTimeChatService.unregisterListener(this.mRTCServiceListener);
    RealTimeChatService.allowDisconnect(this, this.mAccount);
  }

  protected void onResume()
  {
    super.onResume();
    if (isIntentAccountActive())
    {
      RealTimeChatService.registerListener(this.mRTCServiceListener);
      RealTimeChatService.connectAndStayConnected(this, this.mAccount);
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
    if (this.mCreateConversationRequestId != null)
      paramBundle.putInt("requestId", this.mCreateConversationRequestId.intValue());
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

  public final void showInsertCameraPhotoDialog()
  {
    showDialog(2131361854);
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
      if (paramInt == NewConversationActivity.this.mCreateConversationRequestId.intValue())
      {
        if (paramRealTimeChatServiceResult.getErrorCode() != i)
          break label154;
        if ((paramConversationResult.mConversation == null) || (paramConversationResult.mConversation.getParticipantCount() <= i))
          break label120;
        if (!HangoutTile.class.getName().equals(NewConversationActivity.this.getIntent().getStringExtra("tile")))
          break label126;
        GCommApp.getInstance(NewConversationActivity.this).exitMeeting();
        localIntent = Intents.getConversationActivityHangoutTileIntent(NewConversationActivity.this, NewConversationActivity.this.mAccount, paramConversationResult.mConversationRowId.longValue(), i);
        label103: NewConversationActivity.this.startActivity(localIntent);
        NewConversationActivity.this.finish();
      }
      while (true)
      {
        return;
        label120: boolean bool = false;
        break;
        label126: localIntent = Intents.getConversationActivityIntent(NewConversationActivity.this, NewConversationActivity.this.mAccount, paramConversationResult.mConversationRowId.longValue(), bool);
        break label103;
        label154: if (paramRealTimeChatServiceResult.getErrorCode() == 4)
          Toast.makeText(NewConversationActivity.this, R.string.conversation_too_large, 0).show();
        else
          Toast.makeText(NewConversationActivity.this, R.string.error_creating_conversation, 0).show();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.NewConversationActivity
 * JD-Core Version:    0.6.2
 */