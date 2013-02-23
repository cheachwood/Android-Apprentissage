package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.apps.plus.api.MediaRef;

public class EsAlbumCoverSwitcher extends ImageSwitcher
{
  private int mCurrentRefIndex;
  private MediaRef[] mRefArray;

  public EsAlbumCoverSwitcher(Context paramContext)
  {
    super(paramContext);
  }

  public EsAlbumCoverSwitcher(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public void setImageDrawable(Drawable paramDrawable)
  {
    Context localContext = getContext();
    if (paramDrawable == null)
      removeAllViews();
    while (true)
    {
      super.setImageDrawable(paramDrawable);
      return;
      if (getChildCount() < 2)
      {
        ImageView localImageView1 = new ImageView(localContext);
        localImageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(localImageView1);
        ImageView localImageView2 = new ImageView(localContext);
        localImageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(localImageView2);
      }
    }
  }

  public void setRefs(MediaRef[] paramArrayOfMediaRef, MediaRef paramMediaRef)
  {
    this.mRefArray = paramArrayOfMediaRef;
    if ((paramArrayOfMediaRef != null) && (paramMediaRef != null))
    {
      int i = 0;
      int j = paramArrayOfMediaRef.length;
      for (int k = 0; (k < j) && (!paramMediaRef.equals(paramArrayOfMediaRef[k])); k++)
        i++;
      this.mCurrentRefIndex = i;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EsAlbumCoverSwitcher
 * JD-Core Version:    0.6.2
 */