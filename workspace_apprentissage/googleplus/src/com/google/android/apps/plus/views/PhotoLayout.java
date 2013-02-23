package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.google.android.apps.plus.R.id;

public class PhotoLayout extends LinearLayout
{
  private int mFixedHeight = -1;
  private PhotoInfoView mHeaderInfo;
  private PhotoHeaderView mPhotoView;
  private View mScroller;

  public PhotoLayout(Context paramContext)
  {
    super(paramContext);
  }

  public PhotoLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public PhotoLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    int i = paramView.getId();
    if (i == R.id.photo_header_view)
      this.mPhotoView = ((PhotoHeaderView)paramView);
    while (true)
    {
      return;
      if (i == R.id.photo_view_header_info)
        this.mHeaderInfo = ((PhotoInfoView)paramView);
      else if (i == R.id.photo_tag_layout)
        this.mScroller = paramView;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setFixedHeight(this.mFixedHeight);
  }

  public void setFixedHeight(int paramInt)
  {
    if (paramInt <= 0)
      return;
    if (paramInt != this.mFixedHeight);
    for (int i = 1; ; i = 0)
    {
      this.mFixedHeight = paramInt;
      if (this.mPhotoView != null)
      {
        PhotoInfoView localPhotoInfoView = this.mHeaderInfo;
        int j = 0;
        if (localPhotoInfoView != null)
        {
          int k = this.mHeaderInfo.getVisibility();
          j = 0;
          if (k != 8)
            j = 0 + this.mHeaderInfo.getMeasuredHeight();
        }
        if ((this.mScroller != null) && (this.mScroller.getVisibility() != 8))
          j += this.mScroller.getMeasuredHeight();
        this.mPhotoView.setFixedHeight(this.mFixedHeight - j);
      }
      setMeasuredDimension(getMeasuredWidth(), this.mFixedHeight);
      if (i == 0)
        break;
      requestLayout();
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoLayout
 * JD-Core Version:    0.6.2
 */