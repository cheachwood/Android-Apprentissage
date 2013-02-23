package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.fragments.BlockPersonDialog;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.QuickActions;
import com.google.android.apps.plus.views.OverlayedAvatarView;
import com.google.android.apps.plus.views.ParticipantsGalleryView;
import com.google.android.apps.plus.views.ParticipantsGalleryView.CommandListener;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class HangoutParticipantsGalleryView extends ParticipantsGalleryView
  implements ParticipantsGalleryView.CommandListener
{
  private HashMap<MeetingMember, OverlayedAvatarView> avatarViewsByMeetingMember;
  private MeetingMember currentSpeaker;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  private HangoutTile mHangoutTile;
  private int mainVideoRequestId = 0;
  private final List<MeetingMember> meetingMembers = new ArrayList();
  private HashMap<OverlayedAvatarView, MeetingMember> meetingMembersByAvatarView;
  private MeetingMember pinnedVideoMember;

  static
  {
    if (!HangoutParticipantsGalleryView.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public HangoutParticipantsGalleryView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setCommandListener(this);
  }

  private void pinVideo(MeetingMember paramMeetingMember)
  {
    assert (paramMeetingMember != null);
    MeetingMember localMeetingMember = this.pinnedVideoMember;
    GCommApp.getInstance(getContext()).setSelectedVideoSource(paramMeetingMember);
    this.mHangoutTile.updateMainVideoStreaming();
    this.pinnedVideoMember = paramMeetingMember;
    setOverlay(localMeetingMember);
    setOverlay(this.pinnedVideoMember);
  }

  private void setCurrentSpeaker(MeetingMember paramMeetingMember)
  {
    if (this.currentSpeaker != null)
    {
      OverlayedAvatarView localOverlayedAvatarView2 = (OverlayedAvatarView)this.avatarViewsByMeetingMember.get(this.currentSpeaker);
      if (localOverlayedAvatarView2 != null)
        setParticipantActive(localOverlayedAvatarView2, true);
    }
    if ((paramMeetingMember != null) && (this.avatarViewsByMeetingMember != null))
    {
      OverlayedAvatarView localOverlayedAvatarView1 = (OverlayedAvatarView)this.avatarViewsByMeetingMember.get(paramMeetingMember);
      if (localOverlayedAvatarView1 != null)
        setParticipantLoudestSpeaker(localOverlayedAvatarView1, true);
    }
    this.currentSpeaker = paramMeetingMember;
  }

  private void setOverlay(MeetingMember paramMeetingMember)
  {
    if ((paramMeetingMember == null) || (paramMeetingMember.isSelf()));
    while (true)
    {
      return;
      OverlayedAvatarView localOverlayedAvatarView = (OverlayedAvatarView)this.avatarViewsByMeetingMember.get(paramMeetingMember);
      if (paramMeetingMember == this.pinnedVideoMember)
      {
        localOverlayedAvatarView.setOverlayResource(R.drawable.hangout_pin);
      }
      else if (paramMeetingMember.isMediaBlocked())
      {
        localOverlayedAvatarView.setOverlayResource(R.drawable.list_circle_blocked);
      }
      else if (paramMeetingMember.isVideoPaused())
      {
        localOverlayedAvatarView.setOverlayResource(R.drawable.hangout_video_pause);
      }
      else
      {
        localOverlayedAvatarView.setOverlayResource(0);
        if (paramMeetingMember.getCurrentStatus() == MeetingMember.Status.CONNECTING)
          localOverlayedAvatarView.setParticipantType(1);
        else
          localOverlayedAvatarView.setParticipantType(3);
      }
    }
  }

  private void unpinVideo()
  {
    GCommApp.getInstance(getContext()).setSelectedVideoSource(null);
    this.mHangoutTile.updateMainVideoStreaming();
    MeetingMember localMeetingMember = this.pinnedVideoMember;
    this.pinnedVideoMember = null;
    setOverlay(localMeetingMember);
  }

  public final void onAvatarClicked(OverlayedAvatarView paramOverlayedAvatarView, Data.Participant paramParticipant)
  {
    AvatarContextMenuHelper localAvatarContextMenuHelper = new AvatarContextMenuHelper((MeetingMember)this.meetingMembersByAvatarView.get(paramOverlayedAvatarView), paramParticipant);
    this.avatarContextMenuDialog = QuickActions.show(paramOverlayedAvatarView, paramOverlayedAvatarView, null, localAvatarContextMenuHelper, localAvatarContextMenuHelper, true, false);
  }

  public final void onAvatarDoubleClicked(OverlayedAvatarView paramOverlayedAvatarView, Data.Participant paramParticipant)
  {
    MeetingMember localMeetingMember = (MeetingMember)this.meetingMembersByAvatarView.get(paramOverlayedAvatarView);
    if (localMeetingMember != null)
      if (localMeetingMember == this.pinnedVideoMember)
        unpinVideo();
    while (true)
    {
      return;
      pinVideo(localMeetingMember);
      continue;
      onAvatarClicked(paramOverlayedAvatarView, paramParticipant);
    }
  }

  public final void onPause()
  {
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.eventHandler, false);
    dismissAvatarMenuDialog();
  }

  public final void onResume()
  {
    GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
  }

  public final void onShowParticipantList()
  {
    Intent localIntent = ((HangoutTile.HangoutTileActivity)getContext()).getParticipantListActivityIntent();
    getContext().startActivity(localIntent);
  }

  public void setHangoutTile(HangoutTile paramHangoutTile)
  {
    this.mHangoutTile = paramHangoutTile;
  }

  final void setMainVideoRequestId(int paramInt)
  {
    assert (paramInt != 0);
    this.mainVideoRequestId = paramInt;
  }

  public void setParticipants(HashMap<String, Data.Participant> paramHashMap, HashSet<String> paramHashSet)
  {
    removeAllParticipants();
    this.pinnedVideoMember = null;
    this.avatarViewsByMeetingMember = new HashMap();
    this.meetingMembersByAvatarView = new HashMap();
    HashSet localHashSet;
    LayoutInflater localLayoutInflater;
    label103: MeetingMember localMeetingMember2;
    Data.Participant localParticipant;
    label164: OverlayedAvatarView localOverlayedAvatarView;
    if (paramHashMap != null)
    {
      localHashSet = new HashSet(paramHashMap.keySet());
      localLayoutInflater = LayoutInflater.from(getContext());
      dismissAvatarMenuDialog();
      this.meetingMembers.clear();
      this.meetingMembers.addAll(GCommApp.getInstance(getContext()).getGCommNativeWrapper().getMeetingMembersOrderedByEntry());
      Iterator localIterator1 = this.meetingMembers.iterator();
      String str2;
      do
      {
        if (!localIterator1.hasNext())
          break;
        localMeetingMember2 = (MeetingMember)localIterator1.next();
        str2 = localMeetingMember2.getId();
      }
      while (localMeetingMember2.isSelf());
      if ((localHashSet == null) || (!localHashSet.remove(str2)))
        break label235;
      localParticipant = (Data.Participant)paramHashMap.get(str2);
      localOverlayedAvatarView = addParticipant(localLayoutInflater, localParticipant);
      if ((this.currentSpeaker == null) || (localMeetingMember2 != this.currentSpeaker))
        break label261;
      setParticipantLoudestSpeaker(localOverlayedAvatarView, true);
    }
    while (true)
    {
      this.avatarViewsByMeetingMember.put(localMeetingMember2, localOverlayedAvatarView);
      this.meetingMembersByAvatarView.put(localOverlayedAvatarView, localMeetingMember2);
      setOverlay(localMeetingMember2);
      break label103;
      localHashSet = null;
      break;
      label235: Data.Participant.Builder localBuilder = Data.Participant.newBuilder();
      localBuilder.setParticipantId(localMeetingMember2.getId());
      localParticipant = localBuilder.build();
      break label164;
      label261: setParticipantActive(localOverlayedAvatarView, true);
    }
    if (paramHashSet != null)
    {
      Iterator localIterator3 = paramHashSet.iterator();
      while (localIterator3.hasNext())
      {
        String str1 = (String)localIterator3.next();
        if (localHashSet.remove(str1))
          setParticipantActive(addParticipant(localLayoutInflater, (Data.Participant)paramHashMap.get(str1)), false);
      }
    }
    if (localHashSet != null)
    {
      Iterator localIterator2 = localHashSet.iterator();
      while (localIterator2.hasNext())
        setParticipantActive(addParticipant(localLayoutInflater, (Data.Participant)paramHashMap.get((String)localIterator2.next())), false);
    }
    if (this.currentSpeaker != null)
    {
      if (this.meetingMembers.contains(this.currentSpeaker))
        setCurrentSpeaker(this.currentSpeaker);
    }
    else
    {
      MeetingMember localMeetingMember1 = GCommApp.getInstance(getContext()).getSelectedVideoSource();
      if ((localMeetingMember1 == null) || (!this.meetingMembers.contains(localMeetingMember1)))
        break label463;
      pinVideo(localMeetingMember1);
    }
    while (true)
    {
      return;
      setCurrentSpeaker(null);
      break;
      label463: unpinVideo();
    }
  }

  private final class AvatarContextMenuHelper
    implements MenuItem.OnMenuItemClickListener, View.OnCreateContextMenuListener
  {
    private final MeetingMember meetingMember;
    private final Data.Participant participant;

    AvatarContextMenuHelper(MeetingMember paramParticipant, Data.Participant arg3)
    {
      this.meetingMember = paramParticipant;
      Object localObject;
      this.participant = localObject;
    }

    public final void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
    {
      boolean bool1 = true;
      new MenuInflater(HangoutParticipantsGalleryView.this.getContext()).inflate(R.menu.hangout_avatar_tray, paramContextMenu);
      MenuItem localMenuItem1 = paramContextMenu.findItem(R.id.menu_hangout_avatar_profile);
      MenuItem localMenuItem2 = paramContextMenu.findItem(R.id.menu_hangout_avatar_pin_video);
      MenuItem localMenuItem3 = paramContextMenu.findItem(R.id.menu_hangout_avatar_unpin_video);
      MenuItem localMenuItem4 = paramContextMenu.findItem(R.id.menu_hangout_avatar_remote_mute);
      MenuItem localMenuItem5 = paramContextMenu.findItem(R.id.menu_hangout_avatar_block);
      if (this.meetingMember == null)
      {
        localMenuItem1.setTitle(this.participant.getFullName());
        localMenuItem2.setVisible(false);
        localMenuItem3.setVisible(false);
        localMenuItem4.setVisible(false);
        localMenuItem5.setVisible(false);
        return;
      }
      localMenuItem1.setTitle(this.meetingMember.getName(HangoutParticipantsGalleryView.this.getContext()));
      if (this.meetingMember != HangoutParticipantsGalleryView.this.pinnedVideoMember)
      {
        localMenuItem2.setVisible(bool1);
        label184: if (this.meetingMember.isMediaBlocked())
          break label243;
      }
      label243: for (boolean bool2 = bool1; ; bool2 = false)
      {
        localMenuItem4.setVisible(bool2);
        if (!this.meetingMember.isSelfProfile())
          break label249;
        localMenuItem5.setVisible(false);
        break;
        localMenuItem3.setVisible(bool1);
        break label184;
      }
      label249: if (!this.meetingMember.isMediaBlocked());
      while (true)
      {
        localMenuItem5.setVisible(bool1);
        break;
        bool1 = false;
      }
    }

    public final boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      int i = paramMenuItem.getItemId();
      if (i == R.id.menu_hangout_avatar_profile)
      {
        Intent localIntent = Intents.getProfileActivityIntent(HangoutParticipantsGalleryView.this.getContext(), HangoutParticipantsGalleryView.this.mHangoutTile.getAccount(), this.participant.getParticipantId(), null);
        HangoutParticipantsGalleryView.this.getContext().startActivity(localIntent);
      }
      while (true)
      {
        HangoutParticipantsGalleryView.access$702(HangoutParticipantsGalleryView.this, null);
        boolean bool = true;
        int j;
        do
        {
          return bool;
          if (i == R.id.menu_hangout_avatar_pin_video)
          {
            HangoutParticipantsGalleryView.this.pinVideo(this.meetingMember);
            break;
          }
          if (i == R.id.menu_hangout_avatar_unpin_video)
          {
            HangoutParticipantsGalleryView.this.unpinVideo();
            break;
          }
          if (i == R.id.menu_hangout_avatar_remote_mute)
          {
            GCommApp.getInstance(HangoutParticipantsGalleryView.this.getContext()).getGCommNativeWrapper().remoteMute(this.meetingMember);
            break;
          }
          j = R.id.menu_hangout_avatar_block;
          bool = false;
        }
        while (i != j);
        new BlockPersonDialog(false, this.meetingMember).show(((EsFragmentActivity)HangoutParticipantsGalleryView.this.getContext()).getSupportFragmentManager(), null);
      }
    }
  }

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onCurrentSpeakerChanged(MeetingMember paramMeetingMember)
    {
      if (HangoutParticipantsGalleryView.this.pinnedVideoMember != null)
        HangoutParticipantsGalleryView.this.setCurrentSpeaker(paramMeetingMember);
    }

    public final void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
    {
      super.onMediaBlock(paramMeetingMember1, paramMeetingMember2, paramBoolean);
      HangoutParticipantsGalleryView.this.setOverlay(paramMeetingMember1);
      HangoutParticipantsGalleryView.this.setOverlay(paramMeetingMember2);
    }

    public final void onVideoPauseStateChanged(MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoPauseStateChanged(paramMeetingMember, paramBoolean);
      HangoutParticipantsGalleryView.this.setOverlay(paramMeetingMember);
    }

    public final void onVideoSourceChanged(int paramInt, MeetingMember paramMeetingMember, boolean paramBoolean)
    {
      super.onVideoSourceChanged(paramInt, paramMeetingMember, paramBoolean);
      if ((paramInt == HangoutParticipantsGalleryView.this.mainVideoRequestId) && (HangoutParticipantsGalleryView.this.pinnedVideoMember == null))
        HangoutParticipantsGalleryView.this.setCurrentSpeaker(paramMeetingMember);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutParticipantsGalleryView
 * JD-Core Version:    0.6.2
 */