package com.google.android.apps.plus.phone;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.VideoDataLoader;
import com.google.android.apps.plus.views.PhotoHeaderView;
import com.google.android.apps.plus.views.PhotoHeaderView.OnImageListener;

public class PhotoComposeFragment extends HostedFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, PhotoHeaderView.OnImageListener
{
  private EsAccount mAccount;
  private PhotoHeaderView mBackgroundView;
  private ImageButton mDeleteButton;
  private MediaRef mMediaRef;
  private RemoveImageListener mRemoveImageListener;

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTO;
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.remove_image_button)
      if (this.mRemoveImageListener != null)
        this.mRemoveImageListener.onImageRemoved(this.mMediaRef);
    while (true)
    {
      return;
      if (i == R.id.background)
        if (this.mBackgroundView.isVideo())
        {
          if (this.mBackgroundView.isVideoReady())
          {
            startActivity(Intents.getVideoViewActivityIntent(getActivity(), this.mAccount, this.mMediaRef.getOwnerGaiaId(), this.mMediaRef.getPhotoId(), this.mBackgroundView.getVideoData()));
          }
          else
          {
            String str = getString(R.string.photo_view_video_not_ready);
            Toast.makeText(getActivity(), str, 1).show();
          }
        }
        else if (this.mBackgroundView.isPanorama())
          startActivity(Intents.getViewPanoramaActivityIntent(getActivity(), this.mAccount, this.mMediaRef));
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    this.mAccount = ((EsAccount)localBundle.getParcelable("account"));
    this.mMediaRef = ((MediaRef)localBundle.getParcelable("photo_ref"));
    if (this.mMediaRef == null)
      getActivity().finish();
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new VideoDataLoader(getSafeContext(), this.mAccount, this.mMediaRef.getUrl(), this.mMediaRef.getPhotoId(), this.mMediaRef.getLocalUri());
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.photo_compose_fragment, paramViewGroup, false);
    this.mDeleteButton = ((ImageButton)localView1.findViewById(R.id.remove_image_button));
    this.mDeleteButton.setOnClickListener(this);
    if ((localView1.findViewById(R.id.stage) == null) && (this.mMediaRef != null))
    {
      View localView2 = ((ViewStub)localView1.findViewById(R.id.stage_media)).inflate();
      localView2.findViewById(R.id.loading).setVisibility(0);
      this.mBackgroundView = ((PhotoHeaderView)localView2.findViewById(R.id.background));
      this.mBackgroundView.init(this.mMediaRef, false);
      this.mBackgroundView.setOnClickListener(this);
      this.mBackgroundView.setOnImageListener(this);
      localView2.invalidate();
    }
    getLoaderManager().initLoader(0, null, this);
    return localView1;
  }

  public final void onImageLoadFinished(PhotoHeaderView paramPhotoHeaderView)
  {
    getView().findViewById(R.id.loading).setVisibility(8);
  }

  public final void onImageScaled(float paramFloat)
  {
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void setRemoveImageListener(RemoveImageListener paramRemoveImageListener)
  {
    this.mRemoveImageListener = paramRemoveImageListener;
  }

  public static abstract interface RemoveImageListener
  {
    public abstract void onImageRemoved(MediaRef paramMediaRef);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoComposeFragment
 * JD-Core Version:    0.6.2
 */