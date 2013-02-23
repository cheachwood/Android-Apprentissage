package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.apps.plus.R.dimen;
import java.util.Iterator;
import java.util.List;

public class FilmStripView extends LinearLayout
{
  private final EventHandler eventHandler = new EventHandler();
  private HangoutTile hangoutTile;
  private boolean isResumed;
  private final int size;

  public FilmStripView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.size = paramContext.getResources().getDimensionPixelSize(R.dimen.micro_kind_max_dimension);
  }

  private void addParticipantVideo(MeetingMember paramMeetingMember)
  {
    IncomingVideoView.ParticipantVideoView localParticipantVideoView = new IncomingVideoView.ParticipantVideoView(getContext(), null, paramMeetingMember);
    localParticipantVideoView.setLayoutParams(new LinearLayout.LayoutParams(this.size, this.size));
    addView(localParticipantVideoView);
    localParticipantVideoView.setHangoutTile(this.hangoutTile);
    localParticipantVideoView.onResume();
  }

  public final void onPause()
  {
    this.isResumed = false;
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if ((localView instanceof IncomingVideoView.ParticipantVideoView))
        ((IncomingVideoView.ParticipantVideoView)localView).onPause();
    }
    removeAllViews();
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.eventHandler, false);
  }

  public final void onResume(SelfVideoView paramSelfVideoView)
  {
    this.isResumed = true;
    if (paramSelfVideoView.getParent() != null)
      ((ViewGroup)paramSelfVideoView.getParent()).removeView(paramSelfVideoView);
    Iterator localIterator = this.hangoutTile.getGCommNativeWrapper().getMeetingMembersOrderedByEntry().iterator();
    while (localIterator.hasNext())
    {
      MeetingMember localMeetingMember = (MeetingMember)localIterator.next();
      if (localMeetingMember.isSelf())
      {
        paramSelfVideoView.setLayoutMode(SelfVideoView.LayoutMode.FIT);
        paramSelfVideoView.setLayoutParams(new LinearLayout.LayoutParams(this.size, this.size));
        addView(paramSelfVideoView);
      }
      else
      {
        addParticipantVideo(localMeetingMember);
      }
    }
    GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
  }

  public void setHangoutTile(HangoutTile paramHangoutTile)
  {
    this.hangoutTile = paramHangoutTile;
  }

  final class EventHandler extends GCommEventHandler
  {
    EventHandler()
    {
    }

    public final void onMeetingMemberEntered(MeetingMember paramMeetingMember)
    {
      if (FilmStripView.this.isResumed)
        FilmStripView.this.addParticipantVideo(paramMeetingMember);
    }

    public final void onMeetingMemberExited(MeetingMember paramMeetingMember)
    {
      if (FilmStripView.this.isResumed)
        for (int i = 0; i < FilmStripView.this.getChildCount(); i++)
        {
          View localView = FilmStripView.this.getChildAt(i);
          if ((localView != null) && ((localView instanceof IncomingVideoView.ParticipantVideoView)) && (((IncomingVideoView.ParticipantVideoView)localView).getMember() == paramMeetingMember))
          {
            ((IncomingVideoView.ParticipantVideoView)localView).onPause();
            FilmStripView.this.removeView(localView);
          }
        }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.FilmStripView
 * JD-Core Version:    0.6.2
 */