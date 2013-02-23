package com.google.android.apps.plus.oob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.apps.plus.R.layout;
import com.google.api.services.plusi.model.MobileCoarseDate;
import com.google.api.services.plusi.model.MobileGender;
import com.google.api.services.plusi.model.OutOfBoxFieldOption;
import com.google.api.services.plusi.model.OutOfBoxFieldValue;
import java.util.List;

final class DropDownFieldAdapter extends BaseAdapter
{
  private final List<OutOfBoxFieldOption> mItems;

  public DropDownFieldAdapter(List<OutOfBoxFieldOption> paramList)
  {
    this.mItems = paramList;
  }

  private OutOfBoxFieldOption getItem(int paramInt)
  {
    return (OutOfBoxFieldOption)this.mItems.get(paramInt);
  }

  private TextView getTextView(int paramInt1, View paramView, ViewGroup paramViewGroup, int paramInt2)
  {
    TextView localTextView = (TextView)paramView;
    if (localTextView == null)
      localTextView = (TextView)LayoutInflater.from(paramViewGroup.getContext()).inflate(paramInt2, paramViewGroup, false);
    localTextView.setText(getItem(paramInt1).label);
    return localTextView;
  }

  public final int getCount()
  {
    return this.mItems.size();
  }

  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return getTextView(paramInt, paramView, paramViewGroup, 17367049);
  }

  public final long getItemId(int paramInt)
  {
    return paramInt;
  }

  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return getTextView(paramInt, paramView, paramViewGroup, R.layout.simple_spinner_item);
  }

  public final int indexOf(OutOfBoxFieldValue paramOutOfBoxFieldValue)
  {
    int i = 0;
    int j = getCount();
    OutOfBoxFieldValue localOutOfBoxFieldValue;
    int k;
    if (i < j)
    {
      localOutOfBoxFieldValue = getItem(i).value;
      if ((localOutOfBoxFieldValue == null) || (paramOutOfBoxFieldValue == null))
        if (localOutOfBoxFieldValue == paramOutOfBoxFieldValue)
        {
          k = 1;
          label40: if (k == 0)
            break label261;
        }
    }
    while (true)
    {
      return i;
      k = 0;
      break label40;
      if ((OutOfBoxMessages.areEqual(localOutOfBoxFieldValue.stringValue, paramOutOfBoxFieldValue.stringValue)) && (OutOfBoxMessages.areEqual(localOutOfBoxFieldValue.boolValue, paramOutOfBoxFieldValue.boolValue)))
      {
        MobileCoarseDate localMobileCoarseDate1 = localOutOfBoxFieldValue.dateValue;
        MobileCoarseDate localMobileCoarseDate2 = paramOutOfBoxFieldValue.dateValue;
        int m;
        label116: MobileGender localMobileGender1;
        MobileGender localMobileGender2;
        boolean bool;
        if ((localMobileCoarseDate1 == null) || (localMobileCoarseDate2 == null))
          if (localMobileCoarseDate1 == localMobileCoarseDate2)
          {
            m = 1;
            if (m == 0)
              break label255;
            localMobileGender1 = localOutOfBoxFieldValue.gender;
            localMobileGender2 = paramOutOfBoxFieldValue.gender;
            if ((localMobileGender1 != null) && (localMobileGender2 != null))
              break label237;
            if (localMobileGender1 != localMobileGender2)
              break label231;
            bool = true;
          }
        while (true)
        {
          if (!bool)
            break label255;
          k = 1;
          break;
          m = 0;
          break label116;
          if ((OutOfBoxMessages.areEqual(localMobileCoarseDate1.day, localMobileCoarseDate2.day)) && (OutOfBoxMessages.areEqual(localMobileCoarseDate1.month, localMobileCoarseDate2.month)) && (OutOfBoxMessages.areEqual(localMobileCoarseDate1.year, localMobileCoarseDate2.year)))
          {
            m = 1;
            break label116;
          }
          m = 0;
          break label116;
          label231: bool = false;
          continue;
          label237: bool = OutOfBoxMessages.areEqual(localMobileGender1.type, localMobileGender2.type);
        }
      }
      label255: k = 0;
      break label40;
      label261: i++;
      break;
      i = -1;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.DropDownFieldAdapter
 * JD-Core Version:    0.6.2
 */