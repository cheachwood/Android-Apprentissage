package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.util.EventDateUtils;
import com.google.android.apps.plus.util.TimeZoneHelper;
import com.google.api.services.plusi.model.EventTime;
import com.google.api.services.plusi.model.PlusEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class EventDetailOptionRowTime extends EventDetailsOptionRowLayout
{
  private static Drawable sClockIconDrawabale;
  private ImageView mClockIcon;
  private boolean sInitialized;

  public EventDetailOptionRowTime(Context paramContext)
  {
    super(paramContext);
  }

  public EventDetailOptionRowTime(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EventDetailOptionRowTime(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind$3ba8bae(PlusEvent paramPlusEvent)
  {
    Context localContext = getContext();
    EventTime localEventTime1 = paramPlusEvent.startTime;
    TimeZone localTimeZone = null;
    if (localEventTime1 != null)
    {
      String str4 = paramPlusEvent.startTime.timezone;
      localTimeZone = null;
      if (str4 != null)
        localTimeZone = TimeZoneHelper.getSystemTimeZone(paramPlusEvent.startTime.timezone);
    }
    EventTime localEventTime2 = paramPlusEvent.startTime;
    String str1 = null;
    if (localEventTime2 != null)
    {
      Long localLong2 = paramPlusEvent.startTime.timeMs;
      str1 = null;
      if (localLong2 != null)
        str1 = EventDateUtils.getSingleDisplayLine(localContext, paramPlusEvent.startTime, null, false, localTimeZone);
    }
    EventTime localEventTime3 = paramPlusEvent.endTime;
    String str2 = null;
    if (localEventTime3 != null)
    {
      Long localLong1 = paramPlusEvent.endTime.timeMs;
      str2 = null;
      if (localLong1 != null)
        str2 = EventDateUtils.getSingleDisplayLine(localContext, paramPlusEvent.endTime, null, true, localTimeZone);
    }
    ArrayList localArrayList = new ArrayList();
    if (str2 != null)
      localArrayList.add(str2);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramPlusEvent.startTime.timeMs.longValue());
    String str3 = TimeZoneHelper.getDisplayString(paramPlusEvent.startTime.timezone, localCalendar, EsEventData.isEventHangout(paramPlusEvent));
    if (str3 != null)
      localArrayList.add(str3);
    super.bind(str1, localArrayList, this.mClockIcon, null);
  }

  protected final void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super.init(paramContext, paramAttributeSet, paramInt);
    TimeZoneHelper.initialize(paramContext);
    if (!this.sInitialized)
    {
      sClockIconDrawabale = paramContext.getResources().getDrawable(R.drawable.icn_events_details_time);
      this.sInitialized = true;
    }
    this.mClockIcon = new ImageView(paramContext, paramAttributeSet, paramInt);
    this.mClockIcon.setImageDrawable(sClockIconDrawabale);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailOptionRowTime
 * JD-Core Version:    0.6.2
 */