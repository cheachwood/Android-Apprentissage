package com.google.android.apps.plus.fragments;

import android.support.v4.app.Fragment;
import com.google.android.apps.plus.phone.PhotoOneUpActivity.OnMenuItemListener;
import com.google.android.apps.plus.phone.PhotoOneUpActivity.OnScreenListener;

public abstract interface PhotoOneUpCallbacks
{
  public abstract void addMenuItemListener(PhotoOneUpActivity.OnMenuItemListener paramOnMenuItemListener);

  public abstract void addScreenListener(PhotoOneUpActivity.OnScreenListener paramOnScreenListener);

  public abstract boolean isFragmentActive(Fragment paramFragment);

  public abstract void onFragmentVisible(Fragment paramFragment);

  public abstract void onPhotoRemoved$1349ef();

  public abstract void removeMenuItemListener(PhotoOneUpActivity.OnMenuItemListener paramOnMenuItemListener);

  public abstract void removeScreenListener(PhotoOneUpActivity.OnScreenListener paramOnScreenListener);

  public abstract void toggleFullScreen();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PhotoOneUpCallbacks
 * JD-Core Version:    0.6.2
 */