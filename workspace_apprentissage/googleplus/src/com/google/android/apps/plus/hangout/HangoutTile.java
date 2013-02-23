package com.google.android.apps.plus.hangout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.fragments.AlertFragmentDialog;
import com.google.android.apps.plus.fragments.AlertFragmentDialog.AlertDialogListener;
import com.google.android.apps.plus.fragments.BlockFragment;
import com.google.android.apps.plus.fragments.BlockFragment.Listener;
import com.google.android.apps.plus.fragments.BlockPersonDialog.PersonBlocker;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.realtimechat.ParticipantUtils;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.views.Tile;
import com.google.android.apps.plus.views.Tile.ParticipantPresenceListener;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class HangoutTile extends RelativeLayout
  implements Tile
{
  protected ArrayList<Data.Participant> greenRoomParticipants;
  protected Hangout.Info hangoutInfo;
  protected List<Tile.ParticipantPresenceListener> listeners;
  protected EsAccount mAccount;
  protected boolean mHoaConsented;
  protected boolean skipGreenRoom;

  public HangoutTile(Context paramContext)
  {
    super(paramContext);
  }

  public HangoutTile(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public HangoutTile(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private String getString(int paramInt)
  {
    return getResources().getString(paramInt);
  }

  public final void addParticipantPresenceListener(Tile.ParticipantPresenceListener paramParticipantPresenceListener)
  {
    if (this.listeners == null)
      this.listeners = new LinkedList();
    this.listeners.add(paramParticipantPresenceListener);
  }

  public final void blockPerson(Serializable paramSerializable)
  {
    MeetingMember localMeetingMember1 = (MeetingMember)paramSerializable;
    GCommNativeWrapper localGCommNativeWrapper = getGCommNativeWrapper();
    Iterator localIterator = localGCommNativeWrapper.getMeetingMembersOrderedByEntry().iterator();
    while (localIterator.hasNext())
    {
      MeetingMember localMeetingMember2 = (MeetingMember)localIterator.next();
      if (localMeetingMember2.getMucJid().equals(localMeetingMember1.getMucJid()))
        localGCommNativeWrapper.blockMedia(localMeetingMember2);
    }
    EsFragmentActivity localEsFragmentActivity = (EsFragmentActivity)getContext();
    BlockFragment.getInstance(localEsFragmentActivity, this.mAccount, localMeetingMember1.getId(), localMeetingMember1.getName(getContext()), false, true).show(localEsFragmentActivity);
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final HashSet<String> getActiveParticipantIds()
  {
    HashSet localHashSet = new HashSet();
    if (GCommApp.isInstantiated())
    {
      Iterator localIterator = getGCommNativeWrapper().getMeetingMembersOrderedByEntry().iterator();
      while (localIterator.hasNext())
        localHashSet.add(((MeetingMember)localIterator.next()).getId());
    }
    return localHashSet;
  }

  protected final EsFragmentActivity getEsFragmentActivity()
  {
    return (EsFragmentActivity)getContext();
  }

  public final GCommNativeWrapper getGCommNativeWrapper()
  {
    return GCommApp.getInstance(getContext()).getGCommNativeWrapper();
  }

  protected final HangoutTileActivity getHangoutTileActivity()
  {
    return (HangoutTileActivity)getContext();
  }

  protected final String getWaitingMessage(boolean paramBoolean)
  {
    String str1 = getResources().getString(R.string.hangout_waiting_for_participants);
    Intent localIntent = ((Activity)getContext()).getIntent();
    AudienceData localAudienceData;
    if ((localIntent.hasExtra("audience")) && (!paramBoolean))
    {
      localAudienceData = (AudienceData)localIntent.getParcelableExtra("audience");
      if (localAudienceData.getCircleCount() == 0)
      {
        if (localAudienceData.getUserCount() != 1)
          break label101;
        String str4 = getString(R.string.hangout_waiting_for_participant);
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = localAudienceData.getUser(0).getName();
        str1 = String.format(str4, arrayOfObject3);
      }
    }
    while (true)
    {
      return str1;
      label101: if (localAudienceData.getUserCount() == 2)
      {
        String str3 = getString(R.string.hangout_waiting_for_two_participants);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localAudienceData.getUser(0).getName();
        str1 = String.format(str3, arrayOfObject2);
      }
      else if (localAudienceData.getUserCount() > 2)
      {
        String str2 = getString(R.string.hangout_waiting_for_more_than_two_participants);
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = localAudienceData.getUser(0).getName();
        arrayOfObject1[1] = Integer.valueOf(-1 + localAudienceData.getUserCount());
        str1 = String.format(str2, arrayOfObject1);
      }
    }
  }

  public void hideChild(View paramView)
  {
  }

  protected final void inviteMoreParticipants()
  {
    Activity localActivity = (Activity)getContext();
    String str1 = getResources().getString(R.string.realtimechat_conversation_invite_menu_item_text);
    List localList = GCommApp.getInstance(localActivity).getGCommNativeWrapper().getMeetingMembersOrderedByEntry();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      MeetingMember localMeetingMember = (MeetingMember)localIterator.next();
      if (!localMeetingMember.isSelf())
      {
        String str2 = "";
        if (localMeetingMember.getVCard() != null)
          str2 = localMeetingMember.getVCard().getFullName();
        localArrayList.add(ParticipantUtils.makePersonFromParticipant(Data.Participant.newBuilder().setParticipantId(localMeetingMember.getId()).setFullName(str2).setFirstName(Hangout.getFirstNameFromFullName(str2)).build()));
      }
    }
    AudienceData localAudienceData = new AudienceData(localArrayList, null);
    localActivity.startActivityForResult(Intents.getEditAudienceActivityIntent(localActivity, this.mAccount, str1, localAudienceData, 5, false, false, true, true, true), 0);
  }

  public abstract boolean isTileStarted();

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    AudienceData localAudienceData;
    if ((paramInt1 == 0) && (paramInt2 == -1) && (paramIntent != null))
    {
      localAudienceData = (AudienceData)paramIntent.getParcelableExtra("audience");
      if ((this.hangoutInfo != null) && (this.hangoutInfo.getLaunchSource() == Hangout.LaunchSource.Creation) && (!this.hangoutInfo.getRingInvitees()))
        break label79;
    }
    label79: for (boolean bool = true; ; bool = false)
    {
      GCommApp.getInstance(getContext()).getGCommNativeWrapper().inviteToMeeting(localAudienceData, "HANGOUT", bool, true);
      return;
    }
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    return false;
  }

  public void onPrepareOptionsMenu(Menu paramMenu)
  {
  }

  public final void removeParticipantPresenceListener(Tile.ParticipantPresenceListener paramParticipantPresenceListener)
  {
    if (this.listeners != null)
      this.listeners.remove(paramParticipantPresenceListener);
  }

  protected final void sendInvites()
  {
    Intent localIntent = ((Activity)getContext()).getIntent();
    AudienceData localAudienceData;
    if (localIntent.hasExtra("audience"))
    {
      localAudienceData = (AudienceData)localIntent.getParcelableExtra("audience");
      if (localAudienceData != null)
        break label35;
    }
    while (true)
    {
      return;
      label35: GCommApp.getInstance(getContext()).getGCommNativeWrapper().inviteToMeeting(localAudienceData, "HANGOUT", this.hangoutInfo.getRingInvitees(), true);
    }
  }

  public void setHangoutInfo(EsAccount paramEsAccount, Hangout.Info paramInfo, ArrayList<Data.Participant> paramArrayList, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mAccount = paramEsAccount;
    this.hangoutInfo = paramInfo;
    this.greenRoomParticipants = paramArrayList;
    this.skipGreenRoom = paramBoolean2;
    Log.info("setHangoutInfo: %s", new Object[] { paramInfo });
    if (((EsFragmentActivity)getContext() instanceof HangoutActivity))
      StressMode.initialize(getContext(), GCommApp.getInstance(getContext()), paramInfo);
  }

  public abstract void setParticipants(HashMap<String, Data.Participant> paramHashMap, HashSet<String> paramHashSet);

  public void showChild(View paramView)
  {
  }

  protected final void showError(int paramInt, boolean paramBoolean)
  {
    showError(getResources().getString(paramInt), paramBoolean);
  }

  protected final void showError(String paramString, final boolean paramBoolean)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Boolean.valueOf(paramBoolean);
    Log.debug("showError: message=%s finishOnOk=%s", arrayOfObject);
    if (StressMode.isEnabled())
      if (paramBoolean)
        ((Activity)getContext()).finish();
    while (true)
    {
      return;
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(null, paramString, getContext().getResources().getString(R.string.ok), null, 17301543);
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
          if (paramBoolean)
            HangoutTile.this.getHangoutTileActivity().stopHangoutTile();
        }
      });
      localAlertFragmentDialog.show(((EsFragmentActivity)getContext()).getSupportFragmentManager(), "error");
    }
  }

  protected final void showHoaNotification(Button paramButton)
  {
    new HoaNotificationDialog(paramButton).show(((EsFragmentActivity)getContext()).getSupportFragmentManager(), "notification");
  }

  public abstract void transfer();

  public abstract void updateMainVideoStreaming();

  public static abstract interface HangoutTileActivity extends BlockFragment.Listener, BlockPersonDialog.PersonBlocker
  {
    public abstract Intent getGreenRoomParticipantListActivityIntent(ArrayList<Data.Participant> paramArrayList);

    public abstract Intent getHangoutNotificationIntent();

    public abstract Intent getParticipantListActivityIntent();

    public abstract void onMeetingMediaStarted();

    public abstract void stopHangoutTile();
  }

  private final class HoaNotificationDialog extends AlertFragmentDialog
  {
    Button mJoinButton;

    public HoaNotificationDialog(Button arg2)
    {
      Object localObject;
      this.mJoinButton = localObject;
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      Context localContext = getDialogContext();
      View localView = LayoutInflater.from(localContext).inflate(R.layout.hangout_onair_dialog, null);
      CheckBox localCheckBox = (CheckBox)localView.findViewById(R.id.hangoutOnAirCheckbox);
      final AlertDialog localAlertDialog = new AlertDialog.Builder(localContext).setIcon(17301543).setView(localView).setTitle(R.string.hangout_onair_warning_header).setPositiveButton(R.string.hangout_onair_ok_button_text, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          HangoutTile.this.mHoaConsented = true;
          if ((HangoutTile.HoaNotificationDialog.this.mJoinButton.isShown()) && (HangoutTile.HoaNotificationDialog.this.mJoinButton.isEnabled()))
            HangoutTile.HoaNotificationDialog.this.mJoinButton.performClick();
        }
      }).setNegativeButton(R.string.hangout_onair_cancel_button_text, null).show();
      localAlertDialog.getButton(-1).setEnabled(localCheckBox.isChecked());
      localCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
        {
          localAlertDialog.getButton(-1).setEnabled(paramAnonymousBoolean);
        }
      });
      return localAlertDialog;
    }
  }

  protected static enum State
  {
    static
    {
      SIGNING_IN = new State("SIGNING_IN", 1);
      SIGNIN_ERROR = new State("SIGNIN_ERROR", 2);
      READY_TO_LAUNCH_MEETING = new State("READY_TO_LAUNCH_MEETING", 3);
      ENTERING_MEETING = new State("ENTERING_MEETING", 4);
      IN_MEETING = new State("IN_MEETING", 5);
      IN_MEETING_WITH_SELF_VIDEO_INSET = new State("IN_MEETING_WITH_SELF_VIDEO_INSET", 6);
      IN_MEETING_WITH_FILM_STRIP = new State("IN_MEETING_WITH_FILM_STRIP", 7);
      State[] arrayOfState = new State[8];
      arrayOfState[0] = START;
      arrayOfState[1] = SIGNING_IN;
      arrayOfState[2] = SIGNIN_ERROR;
      arrayOfState[3] = READY_TO_LAUNCH_MEETING;
      arrayOfState[4] = ENTERING_MEETING;
      arrayOfState[5] = IN_MEETING;
      arrayOfState[6] = IN_MEETING_WITH_SELF_VIDEO_INSET;
      arrayOfState[7] = IN_MEETING_WITH_FILM_STRIP;
    }

    final boolean isInMeeting()
    {
      if ((this == IN_MEETING) || (this == IN_MEETING_WITH_SELF_VIDEO_INSET) || (this == IN_MEETING_WITH_FILM_STRIP));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    final boolean isSigningIn()
    {
      if (this == SIGNING_IN);
      for (boolean bool = true; ; bool = false)
        return bool;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutTile
 * JD-Core Version:    0.6.2
 */