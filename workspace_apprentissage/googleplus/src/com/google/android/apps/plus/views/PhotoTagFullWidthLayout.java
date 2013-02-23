package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.google.android.apps.plus.R.dimen;

public class PhotoTagFullWidthLayout extends LinearLayout
{
  private static Integer sWidthAdjust;

  public PhotoTagFullWidthLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public PhotoTagFullWidthLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sWidthAdjust == null)
    {
      Resources localResources = getResources();
      sWidthAdjust = Integer.valueOf(localResources.getDimensionPixelSize(R.dimen.photo_tag_scroller_padding_left) + localResources.getDimensionPixelSize(R.dimen.photo_tag_scroller_padding_left));
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(View.MeasureSpec.makeMeasureSpec(((WindowManager)getContext().getSystemService("window")).getDefaultDisplay().getWidth() - sWidthAdjust.intValue(), 1073741824), paramInt2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoTagFullWidthLayout
 * JD-Core Version:    0.6.2
 */