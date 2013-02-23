package com.google.android.apps.plus.hangout;

import android.app.ActionBar;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.AlertFragmentDialog;
import com.google.android.apps.plus.fragments.AlertFragmentDialog.AlertDialogListener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.hangout.crash.CrashReport;
import com.google.android.apps.plus.phone.GoogleFeedback;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.ShakeDetector;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.views.Tile.ParticipantPresenceListener;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HangoutActivity extends EsFragmentActivity
  implements Html.ImageGetter, HangoutTile.HangoutTileActivity
{
  private Hangout.Info hangoutInfo;
  private final Tile.ParticipantPresenceListener hangoutParticipantPresenceListener = new HangoutParticipantPresenceListener((byte)0);
  private EsAccount mAccount;
  HangoutTile mHangoutTile;
  private boolean mShakeDetectorWasRunning;
  private boolean mSkipGreenRoom = false;
  private boolean mSkipMinorWarning;

  private boolean canTransfer()
  {
    try
    {
      GCommNativeWrapper.GCommAppState localGCommAppState1 = GCommApp.getInstance(this).getGCommNativeWrapper().getCurrentState();
      boolean bool2 = Property.ENABLE_HANGOUT_SWITCH.getBoolean();
      bool1 = false;
      if (bool2)
      {
        GCommNativeWrapper.GCommAppState localGCommAppState2 = GCommNativeWrapper.GCommAppState.IN_MEETING_WITH_MEDIA;
        bool1 = false;
        if (localGCommAppState1 == localGCommAppState2)
          bool1 = true;
      }
      return bool1;
    }
    catch (LinkageError localLinkageError)
    {
      while (true)
        boolean bool1 = false;
    }
  }

  private void displayParticipantsInTray()
  {
    this.mHangoutTile.setParticipants(null, null);
  }

  public final void blockPerson(Serializable paramSerializable)
  {
    this.mHangoutTile.blockPerson(paramSerializable);
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public Drawable getDrawable(String paramString)
  {
    Resources localResources = getResources();
    Bitmap localBitmap;
    BitmapDrawable localBitmapDrawable;
    if ("block_icon".equals(paramString))
    {
      localBitmap = BitmapFactory.decodeResource(localResources, R.drawable.icn_drop_block_unpadded);
      localBitmapDrawable = new BitmapDrawable(localResources, localBitmap);
      localBitmapDrawable.setBounds(0, 0, localBitmap.getWidth(), localBitmap.getHeight());
    }
    while (true)
    {
      return localBitmapDrawable;
      if ("exit_icon".equals(paramString))
      {
        localBitmap = BitmapFactory.decodeResource(localResources, R.drawable.hangout_ic_menu_exit_unpadded);
        break;
      }
      localBitmapDrawable = null;
    }
  }

  public final Intent getGreenRoomParticipantListActivityIntent(ArrayList<Data.Participant> paramArrayList)
  {
    return Intents.getHangoutParticipantListActivityIntent(this, this.mHangoutTile.getAccount(), paramArrayList);
  }

  public final Intent getHangoutNotificationIntent()
  {
    GCommNativeWrapper localGCommNativeWrapper = GCommApp.getInstance(this).getGCommNativeWrapper();
    Intent localIntent1 = getIntent();
    boolean bool = localIntent1.hasExtra("audience");
    AudienceData localAudienceData = null;
    if (bool)
      localAudienceData = (AudienceData)localIntent1.getParcelableExtra("audience");
    Intent localIntent2 = Intents.getHangoutActivityAudienceIntent(this, localGCommNativeWrapper.getAccount(), localGCommNativeWrapper.getHangoutInfo(), this.mSkipGreenRoom, localAudienceData);
    localIntent2.putExtra("hangout_skip_minor_warning", this.mSkipMinorWarning);
    return localIntent2;
  }

  public final Intent getParticipantListActivityIntent()
  {
    List localList = GCommApp.getInstance(this).getGCommNativeWrapper().getMeetingMembersOrderedByEntry();
    ArrayList localArrayList = new ArrayList(localList.size());
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      MeetingMember localMeetingMember = (MeetingMember)localIterator.next();
      if (!localMeetingMember.isSelf())
      {
        String str = "";
        if (localMeetingMember.getVCard() != null)
          str = localMeetingMember.getVCard().getFullName();
        localArrayList.add(Data.Participant.newBuilder().setParticipantId(localMeetingMember.getId()).setFullName(str).setFirstName(Hangout.getFirstNameFromFullName(str)).build());
      }
    }
    return Intents.getHangoutParticipantListActivityIntent(this, this.mHangoutTile.getAccount(), localArrayList);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.HANGOUT;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mHangoutTile.onActivityResult(paramInt1, paramInt2, paramIntent);
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public final void onBlockCompleted(boolean paramBoolean)
  {
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.debug("HangoutActivity.onCreate: " + this);
    try
    {
      GCommApp.getInstance(this).getGCommNativeWrapper().getCurrentState();
      Intent localIntent = getIntent();
      this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
      if ((this.mAccount.isChild()) && (!EsAccountsData.hasSeenMinorHangoutWarningDialog(this, this.mAccount)) && (!localIntent.getBooleanExtra("hangout_skip_minor_warning", false)))
      {
        j = 1;
        if ((!Property.ENABLE_HANGOUT_RECORD_ABUSE.getBoolean()) || (!Property.ENABLE_HANGOUT_RECORD_ABUSE_INTERSTITIAL.getBoolean()) || (EsAccountsData.hasSeenReportAbusetWarningDialog(this, this.mAccount)))
          break label517;
        k = 1;
        if ((j != 0) || (k != 0) || (!localIntent.getBooleanExtra("hangout_skip_green_room", false)))
          break label523;
        bool = true;
        this.mSkipGreenRoom = bool;
        this.hangoutInfo = ((Hangout.Info)localIntent.getSerializableExtra("hangout_info"));
        ArrayList localArrayList = (ArrayList)localIntent.getSerializableExtra("hangout_participants");
        if ((this.hangoutInfo == null) || ((this.hangoutInfo.getLaunchSource() != Hangout.LaunchSource.Ring) && (this.hangoutInfo.getLaunchSource() != Hangout.LaunchSource.Transfer)))
          break label529;
        getWindow().addFlags(6815872);
        int m = Build.VERSION.SDK_INT;
        localActionBar = null;
        if (m >= 11)
          localActionBar = getActionBar();
        if (localActionBar != null)
          localActionBar.setDisplayHomeAsUpEnabled(true);
        if (!Hangout.isAdvancedUiSupported(this))
          break label535;
        this.mHangoutTile = new HangoutTabletTile(this);
        ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
        setContentView(this.mHangoutTile, localLayoutParams);
        this.mHangoutTile.setHangoutInfo(this.mAccount, this.hangoutInfo, localArrayList, true, this.mSkipGreenRoom);
        this.mHangoutTile.onCreate(paramBundle);
        if (j != 0)
        {
          MinorWarningDialog localMinorWarningDialog = new MinorWarningDialog();
          localMinorWarningDialog.setCancelable(false);
          localMinorWarningDialog.show(getSupportFragmentManager(), "warning");
        }
        if (k != 0)
        {
          AbuseWarningDialog localAbuseWarningDialog = new AbuseWarningDialog();
          localAbuseWarningDialog.setCancelable(false);
          localAbuseWarningDialog.show(getSupportFragmentManager(), "warning");
        }
      }
    }
    catch (LinkageError localLinkageError)
    {
      while (true)
      {
        ActionBar localActionBar;
        View localView = new View(this);
        localView.setBackgroundColor(getResources().getColor(R.color.clear));
        setContentView(localView);
        int i = R.string.hangout_native_lib_error;
        String str = getResources().getString(i);
        Log.debug("showError: message=%s", new Object[] { str });
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(null, str, getResources().getString(R.string.ok), null, 17301543);
        localAlertFragmentDialog.setCancelable(false);
        localAlertFragmentDialog.setListener(new AlertFragmentDialog.AlertDialogListener()
        {
          public final void onDialogCanceled$20f9a4b7(String paramAnonymousString)
          {
          }

          public final void onDialogListClick$12e92030(int paramAnonymousInt, Bundle paramAnonymousBundle)
          {
          }

          public final void onDialogNegativeClick$20f9a4b7(String paramAnonymousString)
          {
          }

          public final void onDialogPositiveClick(Bundle paramAnonymousBundle, String paramAnonymousString)
          {
            HangoutActivity.this.finish();
          }
        });
        localAlertFragmentDialog.show(getSupportFragmentManager(), "error");
        continue;
        int j = 0;
        continue;
        label517: int k = 0;
        continue;
        label523: boolean bool = false;
        continue;
        label529: HangoutRingingActivity.stopRingActivity();
        continue;
        label535: this.mHangoutTile = new HangoutPhoneTile(this);
        if (localActionBar != null)
          localActionBar.hide();
      }
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    MenuInflater localMenuInflater = getMenuInflater();
    localMenuInflater.inflate(R.menu.hangout_menu, paramMenu);
    localMenuInflater.inflate(R.menu.hangout_transfer, paramMenu);
    paramMenu.findItem(R.id.hangout_transfer_menu_item).setVisible(canTransfer());
    paramMenu.findItem(R.id.help).setVisible(false);
    paramMenu.findItem(R.id.feedback).setVisible(false);
    if (GCommApp.isDebuggable(this))
    {
      SubMenu localSubMenu = paramMenu.addSubMenu("Debug");
      localMenuInflater.inflate(R.menu.hangout_debug, localSubMenu);
    }
    if (this.mHangoutTile != null)
      this.mHangoutTile.onCreateOptionsMenu(paramMenu, localMenuInflater);
    return super.onCreateOptionsMenu(paramMenu);
  }

  public final void onMeetingMediaStarted()
  {
  }

  protected void onNewIntent(Intent paramIntent)
  {
    Log.debug("onNewIntent:" + this);
    setIntent(paramIntent);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    int i = paramMenuItem.getItemId();
    if (this.mHangoutTile.onOptionsItemSelected(paramMenuItem));
    while (true)
    {
      return bool;
      if (i == 16908332)
      {
        goHome(this.mAccount);
      }
      else if (i == R.id.help)
      {
        startExternalActivity(new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.hangout_help_url))));
      }
      else if (i == R.id.feedback)
      {
        recordUserAction(OzActions.SETTINGS_FEEDBACK);
        GoogleFeedback.launch(this);
      }
      else if (i == R.id.menu_hangout_debug_upload_logs)
      {
        CrashReport localCrashReport = new CrashReport(bool);
        try
        {
          throw new Exception("Dummy exception for testing crash reports");
        }
        catch (Exception localException)
        {
          localCrashReport.generateReport(CrashReport.computeJavaCrashSignature(localException));
          localCrashReport.send(this, false);
        }
      }
      else if (i == R.id.menu_hangout_debug_simulate_network_error)
      {
        GCommApp.getInstance(this).raiseNetworkError();
      }
      else if (i == R.id.hangout_transfer_menu_item)
      {
        this.mHangoutTile.transfer();
      }
      else
      {
        bool = super.onOptionsItemSelected(paramMenuItem);
      }
    }
  }

  protected void onPause()
  {
    if (this.mHangoutTile != null)
    {
      this.mHangoutTile.onTilePause();
      this.mHangoutTile.onPause();
    }
    super.onPause();
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.findItem(R.id.hangout_transfer_menu_item).setVisible(canTransfer());
    if (this.mHangoutTile != null)
      this.mHangoutTile.onPrepareOptionsMenu(paramMenu);
    return super.onPrepareOptionsMenu(paramMenu);
  }

  protected void onResume()
  {
    super.onResume();
    if (this.mHangoutTile != null)
    {
      this.mHangoutTile.onResume();
      this.mHangoutTile.onTileResume();
    }
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mHangoutTile != null)
      this.mHangoutTile.onSaveInstanceState(paramBundle);
  }

  public void onStart()
  {
    super.onStart();
    if (this.mHangoutTile != null)
    {
      this.mHangoutTile.onStart();
      this.mHangoutTile.onTileStart();
      displayParticipantsInTray();
      this.mHangoutTile.addParticipantPresenceListener(this.hangoutParticipantPresenceListener);
    }
    ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
    if (localShakeDetector != null)
      this.mShakeDetectorWasRunning = localShakeDetector.stop();
  }

  protected void onStop()
  {
    super.onStop();
    if (this.mHangoutTile != null)
    {
      this.mHangoutTile.removeParticipantPresenceListener(this.hangoutParticipantPresenceListener);
      this.mHangoutTile.onTileStop();
      this.mHangoutTile.onStop();
    }
    if (this.mShakeDetectorWasRunning)
    {
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
      if (localShakeDetector != null)
        localShakeDetector.start();
    }
  }

  public final void stopHangoutTile()
  {
    finish();
  }

  private final class AbuseWarningDialog extends AlertFragmentDialog
  {
    public AbuseWarningDialog()
    {
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      Context localContext = getDialogContext();
      View localView = LayoutInflater.from(localContext).inflate(R.layout.hangout_abuse_dialog, null);
      TextView localTextView = (TextView)localView.findViewById(R.id.reportAbuseLink);
      String str = getString(R.string.hangout_abuse_learn_more);
      SpannableString localSpannableString = new SpannableString(str);
      localSpannableString.setSpan(new ClickableSpan()
      {
        public final void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", this.val$url);
          localIntent.addFlags(524288);
          HangoutActivity.AbuseWarningDialog.this.startActivity(localIntent);
        }
      }
      , 0, str.length(), 33);
      localTextView.setText(localSpannableString);
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView.setClickable(true);
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContext);
      localBuilder.setCancelable(false).setIcon(17301543).setView(localView).setTitle(R.string.hangout_abuse_warning_header).setPositiveButton(R.string.hangout_abuse_ok_button_text, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          EsAccountsData.saveReportAbuseWarningDialogSeenPreference(HangoutActivity.this, HangoutActivity.this.mAccount, true);
        }
      });
      return localBuilder.create();
    }
  }

  private final class HangoutParticipantPresenceListener
    implements Tile.ParticipantPresenceListener
  {
    private HangoutParticipantPresenceListener()
    {
    }

    public final void onParticipantPresenceChanged()
    {
      HangoutActivity.this.displayParticipantsInTray();
    }
  }

  private final class MinorWarningDialog extends AlertFragmentDialog
  {
    public MinorWarningDialog()
    {
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      Context localContext = getDialogContext();
      View localView = LayoutInflater.from(localContext).inflate(R.layout.hangout_minor_dialog, null);
      final CheckBox localCheckBox = (CheckBox)localView.findViewById(R.id.minorHangoutDontShow);
      ((TextView)localView.findViewById(R.id.minorHangoutMessage)).setText(Html.fromHtml(getResources().getString(R.string.hangout_minor_warning_message), HangoutActivity.this, null));
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContext);
      localBuilder.setCancelable(false).setIcon(17301543).setView(localView).setTitle(R.string.hangout_minor_warning_header).setPositiveButton(R.string.hangout_minor_ok_button_text, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if (localCheckBox.isChecked())
            EsAccountsData.saveMinorHangoutWarningDialogSeenPreference(HangoutActivity.this, HangoutActivity.this.mAccount, true);
          HangoutActivity.access$302(HangoutActivity.this, true);
        }
      });
      return localBuilder.create();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutActivity
 * JD-Core Version:    0.6.2
 */