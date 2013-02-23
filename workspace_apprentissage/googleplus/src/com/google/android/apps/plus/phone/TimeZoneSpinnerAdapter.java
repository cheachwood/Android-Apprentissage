package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.TimeZoneHelper;
import com.google.android.apps.plus.util.TimeZoneHelper.TimeZoneInfo;
import java.util.List;
import java.util.TimeZone;

public final class TimeZoneSpinnerAdapter extends BaseAdapter
{
  private static String stimeZoneFormat;
  private Context mContext;
  private TimeZoneHelper mTimeZoneHelper;
  private List<TimeZoneHelper.TimeZoneInfo> mTimeZones;

  public TimeZoneSpinnerAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    if (stimeZoneFormat == null)
      stimeZoneFormat = paramContext.getResources().getString(R.string.time_zone_format);
  }

  private View prepareRow(int paramInt, View paramView, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if (paramView == null)
      if (!paramBoolean)
        break label141;
    label141: Context localContext;
    for (paramView = LayoutInflater.from(this.mContext).inflate(R.layout.timezone_spinner_dropdown_item, paramViewGroup, false); ; paramView = new TextView(localContext))
    {
      if ((paramView instanceof TextView))
      {
        TimeZoneHelper.TimeZoneInfo localTimeZoneInfo = (TimeZoneHelper.TimeZoneInfo)this.mTimeZones.get(paramInt);
        TimeZone localTimeZone = localTimeZoneInfo.getTimeZone();
        long l = localTimeZoneInfo.getOffset();
        TextView localTextView = (TextView)paramView;
        String str = stimeZoneFormat;
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = localTimeZone.getDisplayName();
        arrayOfObject[1] = Long.valueOf(l / 3600000L);
        arrayOfObject[2] = Long.valueOf(Math.abs((l - 3600000L * (l / 3600000L)) / 60000L));
        localTextView.setText(String.format(str, arrayOfObject));
      }
      return paramView;
      localContext = this.mContext;
    }
  }

  public final int getCount()
  {
    return this.mTimeZones.size();
  }

  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return prepareRow(paramInt, paramView, paramViewGroup, true);
  }

  public final Object getItem(int paramInt)
  {
    return this.mTimeZones.get(paramInt);
  }

  public final long getItemId(int paramInt)
  {
    return paramInt;
  }

  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return prepareRow(paramInt, paramView, paramViewGroup, false);
  }

  public final void setTimeZoneHelper(TimeZoneHelper paramTimeZoneHelper)
  {
    this.mTimeZoneHelper = paramTimeZoneHelper;
    this.mTimeZones = this.mTimeZoneHelper.getTimeZoneInfos();
    notifyDataSetChanged();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.TimeZoneSpinnerAdapter
 * JD-Core Version:    0.6.2
 */