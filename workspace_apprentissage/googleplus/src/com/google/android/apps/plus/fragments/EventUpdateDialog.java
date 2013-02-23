package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.google.android.apps.plus.views.EventActionListener;
import com.google.android.apps.plus.views.EventActivityUpdateCardLayout;
import com.google.android.apps.plus.views.EventUpdate;

public class EventUpdateDialog extends DialogFragment
{
  private EventUpdate mEventUpdate;

  static EventUpdateDialog newInstance()
  {
    return new EventUpdateDialog();
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setStyle(1, getTheme());
    if (paramBundle != null)
    {
      this.mEventUpdate = new EventUpdate();
      EventUpdate localEventUpdate = this.mEventUpdate;
      if (paramBundle != null)
      {
        localEventUpdate.timestamp = paramBundle.getLong("eventupdate" + ".timestampe");
        localEventUpdate.ownerName = paramBundle.getString("eventupdate" + ".ownername");
        localEventUpdate.gaiaId = paramBundle.getString("eventupdate" + ".gaiaid");
        localEventUpdate.comment = paramBundle.getString("eventupdate" + ".comment");
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 11);
    for (Object localObject = getActivity(); ; localObject = new ContextThemeWrapper(getActivity(), 16973835))
    {
      LinearLayout localLinearLayout = new LinearLayout((Context)localObject);
      EventActivityUpdateCardLayout localEventActivityUpdateCardLayout = new EventActivityUpdateCardLayout((Context)localObject);
      localEventActivityUpdateCardLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
      localEventActivityUpdateCardLayout.toggleCardBorderStyle(false);
      Fragment localFragment = getTargetFragment();
      localEventActivityUpdateCardLayout.bind(this.mEventUpdate, (EventActionListener)localFragment, false);
      ScrollView localScrollView = new ScrollView((Context)localObject);
      localScrollView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      localScrollView.addView(localEventActivityUpdateCardLayout);
      localLinearLayout.addView(localScrollView);
      localLinearLayout.setBackgroundResource(17170443);
      return localLinearLayout;
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    EventUpdate localEventUpdate = this.mEventUpdate;
    if (paramBundle != null)
    {
      paramBundle.putLong("eventupdate" + ".timestampe", localEventUpdate.timestamp);
      paramBundle.putString("eventupdate" + ".ownername", localEventUpdate.ownerName);
      paramBundle.putString("eventupdate" + ".gaiaid", localEventUpdate.gaiaId);
      paramBundle.putString("eventupdate" + ".comment", localEventUpdate.comment);
    }
    super.onSaveInstanceState(paramBundle);
  }

  public final void setUpdate(EventUpdate paramEventUpdate)
  {
    this.mEventUpdate = paramEventUpdate;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EventUpdateDialog
 * JD-Core Version:    0.6.2
 */