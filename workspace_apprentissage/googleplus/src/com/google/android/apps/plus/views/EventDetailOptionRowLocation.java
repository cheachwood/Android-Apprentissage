package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.api.services.plusi.model.EmbedsPostalAddress;
import com.google.api.services.plusi.model.EventTime;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlusEvent;

public class EventDetailOptionRowLocation extends EventDetailsOptionRowLayout
  implements View.OnClickListener
{
  private static Drawable sDirectionIcon;
  private static Drawable sHangoutIcon;
  private static Drawable sHangoutJoinIcon;
  private static String sHangoutJoinText;
  private static String sHangoutTitle;
  private static String sHangoutbeforeText;
  private static Drawable sLocationIcon;
  private ImageView mLaunchIcon;
  private EventActionListener mListener;
  private boolean mLocation;
  private ImageView mTypeIcon;
  private boolean sInitialized;

  public EventDetailOptionRowLocation(Context paramContext)
  {
    super(paramContext);
  }

  public EventDetailOptionRowLocation(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }

  public EventDetailOptionRowLocation(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(PlusEvent paramPlusEvent, EventActionListener paramEventActionListener)
  {
    boolean bool1;
    String str2;
    String str3;
    if (paramPlusEvent.location != null)
    {
      bool1 = true;
      this.mLocation = bool1;
      this.mListener = paramEventActionListener;
      if (!this.mLocation)
        break label114;
      this.mTypeIcon.setImageDrawable(sLocationIcon);
      this.mLaunchIcon.setImageDrawable(sDirectionIcon);
      if (paramPlusEvent.location.address == null)
        break label99;
      str2 = paramPlusEvent.location.description;
      str3 = paramPlusEvent.location.address.name;
      label77: super.bind(str2, str3, this.mTypeIcon, this.mLaunchIcon);
    }
    while (true)
    {
      return;
      bool1 = false;
      break;
      label99: str2 = paramPlusEvent.location.name;
      str3 = null;
      break label77;
      label114: this.mTypeIcon.setImageDrawable(sHangoutIcon);
      String str1 = sHangoutbeforeText;
      boolean bool2 = System.currentTimeMillis() < paramPlusEvent.startTime.timeMs.longValue();
      ImageView localImageView = null;
      if (bool2)
      {
        this.mLaunchIcon.setImageDrawable(sHangoutJoinIcon);
        localImageView = this.mLaunchIcon;
        str1 = sHangoutJoinText;
      }
      super.bind(sHangoutTitle, str1, this.mTypeIcon, localImageView);
    }
  }

  protected final void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super.init(paramContext, paramAttributeSet, paramInt);
    if (!this.sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sLocationIcon = localResources.getDrawable(R.drawable.icn_events_details_location);
      sDirectionIcon = localResources.getDrawable(R.drawable.icn_events_directions);
      sHangoutIcon = localResources.getDrawable(R.drawable.icn_events_hangout_1up);
      sHangoutTitle = localResources.getString(R.string.event_detail_hangout_title);
      sHangoutbeforeText = localResources.getString(R.string.event_detail_hangout_before);
      sHangoutJoinText = localResources.getString(R.string.event_detail_hangout_during);
      sHangoutJoinIcon = localResources.getDrawable(R.drawable.icn_events_arrow_right);
      this.sInitialized = true;
    }
    this.mTypeIcon = new ImageView(paramContext, paramAttributeSet, paramInt);
    this.mLaunchIcon = new ImageView(paramContext, paramAttributeSet, paramInt);
    setClickable(true);
    setOnClickListener(this);
  }

  public void onClick(View paramView)
  {
    if (this.mLocation)
      this.mListener.onLocationClicked();
    while (true)
    {
      return;
      this.mListener.onHangoutClicked();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailOptionRowLocation
 * JD-Core Version:    0.6.2
 */