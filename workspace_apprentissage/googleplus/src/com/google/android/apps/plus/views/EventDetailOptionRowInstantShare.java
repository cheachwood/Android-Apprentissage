package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.fragments.EventActiveState;

public class EventDetailOptionRowInstantShare extends EventDetailsOptionRowLayout
  implements View.OnClickListener
{
  private static String sAfterInstantDescription;
  private static String sBeforeInstantDescription;
  private static boolean sInitialized;
  private static Drawable sInstantShareDrawable;
  private static String sInstantTitle;
  private CheckBox mCheckBox;
  private EventActiveState mEventState;
  private ImageView mInstantIcon;
  private EventActionListener mListener;

  public EventDetailOptionRowInstantShare(Context paramContext)
  {
    super(paramContext);
  }

  public EventDetailOptionRowInstantShare(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EventDetailOptionRowInstantShare(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(EventActiveState paramEventActiveState)
  {
    this.mEventState = paramEventActiveState;
    String str1;
    if (!paramEventActiveState.isInstantShareExpired)
    {
      this.mCheckBox.setVisibility(0);
      this.mCheckBox.setChecked(paramEventActiveState.isInstantShareEnabled);
      setClickable(true);
      str1 = sInstantTitle;
      if (!paramEventActiveState.isInstantShareExpired)
        break label83;
    }
    label83: for (String str2 = sAfterInstantDescription; ; str2 = sBeforeInstantDescription)
    {
      super.bind(str1, str2, this.mInstantIcon, this.mCheckBox);
      return;
      this.mCheckBox.setVisibility(8);
      setClickable(false);
      break;
    }
  }

  protected final void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super.init(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sInstantShareDrawable = localResources.getDrawable(R.drawable.icn_events_party_mode_1up);
      sBeforeInstantDescription = localResources.getString(R.string.instant_share_description);
      sAfterInstantDescription = localResources.getString(R.string.instant_share_after_description);
      sInstantTitle = localResources.getString(R.string.event_detail_instantshare_title);
      sInitialized = true;
    }
    this.mInstantIcon = new ImageView(paramContext, paramAttributeSet, paramInt);
    this.mInstantIcon.setImageDrawable(sInstantShareDrawable);
    this.mCheckBox = new CheckBox(paramContext);
    this.mCheckBox.setLayoutParams(new ExactLayout.LayoutParams(-2, -2));
    this.mCheckBox.setVisibility(0);
    this.mCheckBox.setClickable(false);
    setOnClickListener(this);
  }

  public void onClick(View paramView)
  {
    EventActionListener localEventActionListener;
    if ((this == paramView) && (this.mListener != null))
    {
      localEventActionListener = this.mListener;
      if (this.mEventState.isInstantShareEnabled)
        break label37;
    }
    label37: for (boolean bool = true; ; bool = false)
    {
      localEventActionListener.onInstantShareToggle(bool);
      return;
    }
  }

  public void setListener(EventActionListener paramEventActionListener)
  {
    this.mListener = paramEventActionListener;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailOptionRowInstantShare
 * JD-Core Version:    0.6.2
 */