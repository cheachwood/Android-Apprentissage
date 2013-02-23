package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.apps.plus.network.ApiaryActivity;

public class ActivityPreviewView extends FrameLayout
{
  private ApiaryActivity mActivity;

  public ActivityPreviewView(Context paramContext)
  {
    super(paramContext, null, 0);
  }

  public ActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }

  public ActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final ApiaryActivity getActivity()
  {
    return this.mActivity;
  }

  public void setActivity(ApiaryActivity paramApiaryActivity)
  {
    this.mActivity = paramApiaryActivity;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ActivityPreviewView
 * JD-Core Version:    0.6.2
 */