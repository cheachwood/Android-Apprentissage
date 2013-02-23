package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.api.MediaRef;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlacesMapView extends ImageResourceView
{
  private static Pattern sSizePattern = Pattern.compile(".*size=(\\d+)x(\\d+)");
  private final int mAspectRatio;
  private int mBitmapHeight;
  private int mBitmapWidth;
  private final int mMaxWidth;

  public PlacesMapView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PlacesMapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setScaleMode(0);
    Resources localResources = paramContext.getResources();
    this.mMaxWidth = localResources.getDimensionPixelSize(R.dimen.places_map_max_width);
    this.mAspectRatio = localResources.getInteger(R.integer.places_map_aspect_ratio);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    View.MeasureSpec.getSize(paramInt2);
    if ((this.mBitmapWidth == 0) || (this.mBitmapHeight == 0));
    for (int j = 100 * Math.min(i, this.mMaxWidth) / this.mAspectRatio; ; j = Math.min(i, this.mMaxWidth) * this.mBitmapHeight / this.mBitmapWidth)
    {
      setMeasuredDimension(i, j);
      return;
    }
  }

  public void setMediaRef(MediaRef paramMediaRef)
  {
    Matcher localMatcher = sSizePattern.matcher(paramMediaRef.getUrl());
    if (localMatcher.find())
    {
      this.mBitmapWidth = Integer.parseInt(localMatcher.group(1));
      this.mBitmapHeight = Integer.parseInt(localMatcher.group(2));
    }
    super.setMediaRef(paramMediaRef);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PlacesMapView
 * JD-Core Version:    0.6.2
 */