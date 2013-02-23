package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.layout;

public final class CircleSpinnerAdapter extends ArrayAdapter<CircleSpinnerInfo>
{
  public CircleSpinnerAdapter(Context paramContext)
  {
    super(paramContext, R.layout.simple_spinner_item, 16908308);
    setDropDownViewResource(R.layout.circle_spinner_dropdown_item);
  }

  private void bindView(View paramView, int paramInt)
  {
    CircleSpinnerInfo localCircleSpinnerInfo = (CircleSpinnerInfo)getItem(paramInt);
    TextView localTextView = (TextView)paramView.findViewById(16908309);
    ImageView localImageView;
    if (localTextView != null)
    {
      if ((localCircleSpinnerInfo.id != null) && (localCircleSpinnerInfo.circleType != 10))
        localTextView.setText(" (" + localCircleSpinnerInfo.count + ")");
    }
    else
    {
      localImageView = (ImageView)paramView.findViewById(16908294);
      if (localImageView != null)
      {
        if (localCircleSpinnerInfo.iconResId != 0)
          break label110;
        localImageView.setVisibility(8);
      }
    }
    while (true)
    {
      return;
      localTextView.setText(null);
      break;
      label110: localImageView.setVisibility(0);
      localImageView.setImageResource(localCircleSpinnerInfo.iconResId);
    }
  }

  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = super.getDropDownView(paramInt, paramView, paramViewGroup);
    bindView(localView, paramInt);
    return localView;
  }

  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = super.getView(paramInt, paramView, paramViewGroup);
    bindView(localView, paramInt);
    return localView;
  }

  public static final class CircleSpinnerInfo
  {
    public final int circleType;
    public final int count;
    public final int iconResId;
    public final String id;
    public final String title;

    public CircleSpinnerInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
    {
      this.id = paramString1;
      this.title = paramString2;
      this.count = paramInt2;
      this.circleType = paramInt1;
      this.iconResId = paramInt3;
    }

    public final String toString()
    {
      return this.title;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CircleSpinnerAdapter
 * JD-Core Version:    0.6.2
 */