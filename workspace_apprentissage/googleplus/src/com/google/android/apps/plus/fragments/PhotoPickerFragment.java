package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.views.ActionButton;
import com.google.android.apps.plus.views.PhotoHeaderView;
import com.google.android.apps.plus.views.PhotoHeaderView.OnImageListener;

public class PhotoPickerFragment extends EsFragment
  implements View.OnClickListener, PhotoHeaderView.OnImageListener
{
  private static Integer sPhotoSize;
  private Integer mCoverPhotoOffset;
  private int mCropMode;
  private Intent mIntent;
  private MediaRef mPhotoRef;
  private PhotoHeaderView mPhotoView;

  public PhotoPickerFragment()
  {
  }

  public PhotoPickerFragment(Intent paramIntent)
  {
    this();
    this.mIntent = paramIntent;
  }

  protected final boolean isEmpty()
  {
    if ((this.mPhotoView != null) && (!this.mPhotoView.isPhotoBound()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (sPhotoSize == null)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramActivity.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      sPhotoSize = Integer.valueOf(Math.max(localDisplayMetrics.heightPixels, localDisplayMetrics.widthPixels));
    }
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.cancel_button)
    {
      FragmentActivity localFragmentActivity2 = getActivity();
      localFragmentActivity2.setResult(1);
      localFragmentActivity2.finish();
      return;
    }
    FragmentActivity localFragmentActivity1;
    Intent localIntent;
    if (i == R.id.accept_button)
    {
      localFragmentActivity1 = getActivity();
      localIntent = new Intent();
      if (this.mCropMode != 0)
      {
        localIntent.putExtra("data", ImageUtils.compressBitmap(this.mPhotoView.getCroppedPhoto(), 90, false));
        if (this.mCropMode == 2)
        {
          localIntent.putExtra("top_offset", this.mPhotoView.getCoverPhotoTopOffset());
          String str2 = this.mPhotoRef.getOwnerGaiaId();
          if ((str2 != null) && (str2.equals("115239603441691718952")))
            localIntent.putExtra("is_gallery_photo", true);
        }
      }
      if (!this.mPhotoRef.hasLocalUri())
        break label213;
    }
    label213: for (String str1 = this.mPhotoRef.getLocalUri().toString(); ; str1 = this.mPhotoRef.getUrl())
    {
      if (str1 != null)
        localIntent.putExtra("photo_url", str1);
      if (this.mPhotoRef.getPhotoId() != 0L)
        localIntent.putExtra("photo_id", this.mPhotoRef.getPhotoId());
      localFragmentActivity1.setResult(-1, localIntent);
      localFragmentActivity1.finish();
      break;
      break;
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mIntent = new Intent().putExtras(paramBundle.getBundle("com.google.android.apps.plus.PhotoViewFragment.INTENT"));
    this.mPhotoRef = ((MediaRef)this.mIntent.getParcelableExtra("mediarefs"));
    this.mCropMode = this.mIntent.getIntExtra("photo_picker_crop_mode", 0);
    if (this.mIntent.hasExtra("top_offset"))
      this.mCoverPhotoOffset = Integer.valueOf(this.mIntent.getIntExtra("top_offset", 0));
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, R.layout.photo_picker_fragment);
    this.mPhotoView = ((PhotoHeaderView)localView.findViewById(R.id.photo_header_view));
    this.mPhotoView.init(this.mPhotoRef, false);
    this.mPhotoView.enableImageTransforms(true);
    this.mPhotoView.setCropMode(this.mCropMode);
    this.mPhotoView.setCropModeCoverPhotoOffset(this.mCoverPhotoOffset);
    this.mPhotoView.setOnImageListener(this);
    showEmptyViewProgress(localView);
    localView.findViewById(R.id.cancel_button).setOnClickListener(this);
    if (this.mCropMode != 0);
    for (String str = getString(R.string.photo_picker_save); ; str = getString(R.string.photo_picker_select))
    {
      ActionButton localActionButton = (ActionButton)localView.findViewById(R.id.accept_button);
      localActionButton.setText(str.toUpperCase());
      localActionButton.setTextAppearance(getActivity(), R.style.AlbumView_BottomActionBar_ActionButton_Disabled);
      return localView;
    }
  }

  public final void onImageLoadFinished(PhotoHeaderView paramPhotoHeaderView)
  {
    View localView = getView();
    ActionButton localActionButton = (ActionButton)localView.findViewById(R.id.accept_button);
    if (isEmpty())
    {
      localActionButton.setOnClickListener(null);
      localActionButton.setEnabled(false);
      localActionButton.setTextAppearance(getActivity(), R.style.AlbumView_BottomActionBar_ActionButton_Disabled);
      setupEmptyView(localView, R.string.photo_network_error);
      showEmptyView(localView);
    }
    while (true)
    {
      return;
      localActionButton.setOnClickListener(this);
      localActionButton.setEnabled(true);
      localActionButton.setTextAppearance(getActivity(), R.style.AlbumView_BottomActionBar_ActionButton);
      showContent(localView);
    }
  }

  public final void onImageScaled(float paramFloat)
  {
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mIntent != null)
      paramBundle.putParcelable("com.google.android.apps.plus.PhotoViewFragment.INTENT", this.mIntent.getExtras());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PhotoPickerFragment
 * JD-Core Version:    0.6.2
 */