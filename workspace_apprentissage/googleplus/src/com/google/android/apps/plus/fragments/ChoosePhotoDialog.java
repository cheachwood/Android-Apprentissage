package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;
import com.google.android.apps.plus.R.string;

public class ChoosePhotoDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  private Long mCoverPhotoId;
  private boolean mHasScrapbook;
  private int[] mIndexToAction = new int[5];
  private boolean mIsCameraSupported;
  private boolean mIsForCoverPhoto;
  private boolean mIsOnlyFromInstantUpload;
  PhotoHandler mListener;
  private int mTitle = R.string.menu_choose_photo_from_gallery;

  public ChoosePhotoDialog()
  {
  }

  public ChoosePhotoDialog(int paramInt)
  {
    this.mTitle = paramInt;
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (this.mListener == null);
    while (true)
    {
      return;
      dismiss();
      switch (this.mIndexToAction[paramInt])
      {
      default:
        break;
      case 1:
        this.mListener.doRepositionCoverPhoto();
        break;
      case 2:
        this.mListener.doTakePhoto();
        break;
      case 3:
        this.mListener.doPickPhotoFromAlbums(0);
        break;
      case 4:
        this.mListener.doPickPhotoFromAlbums(1);
        break;
      case 5:
        this.mListener.doPickPhotoFromAlbums(2);
      }
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    super.onCreateDialog(paramBundle);
    if (paramBundle != null)
    {
      this.mIsCameraSupported = paramBundle.getBoolean("is_camera_supported");
      this.mTitle = paramBundle.getInt("title");
      this.mIsOnlyFromInstantUpload = paramBundle.getBoolean("only_instant_upload");
      this.mIsForCoverPhoto = paramBundle.getBoolean("has_scrapbook");
      this.mHasScrapbook = paramBundle.getBoolean("has_scrapbook");
      if (paramBundle.containsKey("cover_photo_id"))
        this.mCoverPhotoId = Long.valueOf(paramBundle.getLong("cover_photo_id"));
    }
    FragmentActivity localFragmentActivity = getActivity();
    int i;
    String[] arrayOfString;
    int k;
    int i1;
    if (this.mIsCameraSupported)
    {
      i = 1;
      int j = i + 1;
      if (this.mIsForCoverPhoto)
      {
        j++;
        if (this.mHasScrapbook)
          j++;
        if (this.mCoverPhotoId != null)
          j++;
      }
      arrayOfString = new String[j];
      Long localLong = this.mCoverPhotoId;
      k = 0;
      if (localLong != null)
      {
        this.mIndexToAction[0] = 1;
        k = 0 + 1;
        arrayOfString[0] = localFragmentActivity.getString(R.string.change_photo_option_reposition);
      }
      if (this.mIsForCoverPhoto)
      {
        this.mIndexToAction[k] = 4;
        int i3 = k + 1;
        arrayOfString[k] = localFragmentActivity.getString(R.string.change_photo_option_select_gallery);
        k = i3;
      }
      if (this.mIsCameraSupported)
      {
        this.mIndexToAction[k] = 2;
        int i2 = k + 1;
        arrayOfString[k] = localFragmentActivity.getString(R.string.change_photo_option_take_photo);
        k = i2;
      }
      this.mIndexToAction[k] = 3;
      if (!this.mIsOnlyFromInstantUpload)
        break label401;
      i1 = k + 1;
      arrayOfString[k] = localFragmentActivity.getString(R.string.change_photo_option_instant_upload);
    }
    label401: int m;
    for (int n = i1; ; n = m)
    {
      if ((this.mIsForCoverPhoto) && (this.mHasScrapbook))
      {
        this.mIndexToAction[n] = 5;
        arrayOfString[n] = localFragmentActivity.getString(R.string.change_photo_option_select_cover_photo);
      }
      if ((getTargetFragment() instanceof PhotoHandler))
        this.mListener = ((PhotoHandler)getTargetFragment());
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
      localBuilder.setTitle(this.mTitle);
      localBuilder.setAdapter(new ArrayAdapter(localFragmentActivity, 17367057, arrayOfString), this);
      localBuilder.setCancelable(true);
      return localBuilder.create();
      i = 0;
      break;
      m = k + 1;
      arrayOfString[k] = localFragmentActivity.getString(R.string.change_photo_option_your_photos);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("is_camera_supported", this.mIsCameraSupported);
    paramBundle.putInt("title", this.mTitle);
    paramBundle.putBoolean("only_instant_upload", this.mIsOnlyFromInstantUpload);
    paramBundle.putBoolean("has_scrapbook", this.mIsForCoverPhoto);
    paramBundle.putBoolean("has_scrapbook", this.mHasScrapbook);
    if (this.mCoverPhotoId != null)
      paramBundle.putLong("cover_photo_id", this.mCoverPhotoId.longValue());
  }

  public final void setIsCameraSupported(boolean paramBoolean)
  {
    this.mIsCameraSupported = paramBoolean;
  }

  public final void setIsForCoverPhoto(boolean paramBoolean1, boolean paramBoolean2, Long paramLong)
  {
    this.mIsForCoverPhoto = true;
    this.mHasScrapbook = paramBoolean2;
    this.mCoverPhotoId = paramLong;
  }

  public final void show(FragmentManager paramFragmentManager, String paramString)
  {
    if ((this.mIsCameraSupported) || (this.mIsForCoverPhoto))
      super.show(paramFragmentManager, paramString);
    while (true)
    {
      return;
      if (this.mListener != null)
        this.mListener.doPickPhotoFromAlbums(0);
    }
  }

  public static abstract interface PhotoHandler
  {
    public abstract void doPickPhotoFromAlbums(int paramInt);

    public abstract void doRepositionCoverPhoto();

    public abstract void doTakePhoto();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ChoosePhotoDialog
 * JD-Core Version:    0.6.2
 */