package com.google.android.apps.plus.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.api.MediaRef;

public final class EmotiShareView
{
  private final Context mContext;
  private final ImageResourceView mImageView;
  private final View mMainView;
  private final ImageView mMissingImageView;
  private final View mSelector;

  public EmotiShareView(Context paramContext)
  {
    this.mContext = paramContext;
    this.mMainView = LayoutInflater.from(this.mContext).inflate(R.layout.emotishare_view, null, false);
    this.mImageView = ((ImageResourceView)this.mMainView.findViewById(R.id.image_view));
    this.mMissingImageView = ((ImageView)this.mMainView.findViewById(R.id.missing_image_view));
    this.mImageView.setScaleMode(0);
    this.mSelector = this.mMainView.findViewById(R.id.selector_view);
  }

  public final ImageResourceView getImageView()
  {
    return this.mImageView;
  }

  public final ImageView getMissingImageView()
  {
    return this.mMissingImageView;
  }

  public final View getView()
  {
    return this.mMainView;
  }

  public final void setMediaRef(MediaRef paramMediaRef)
  {
    this.mImageView.setImageResourceFlags(4);
    this.mImageView.setMediaRef(paramMediaRef);
  }

  public final void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mSelector.setOnClickListener(paramOnClickListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EmotiShareView
 * JD-Core Version:    0.6.2
 */