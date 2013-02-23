package com.google.android.apps.plus.phone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PanoramaDetector;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.ProgressFragmentDialog;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.panorama.PanoramaClient;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;

public class PanoramaViewerActivity extends EsFragmentActivity
  implements ResourceConsumer, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, PanoramaClient.OnPanoramaInfoLoadedListener
{
  private Handler mHandler;
  private MediaRef mMediaRef;
  private PanoramaClient mPanoramaClient;
  private Resource mResource;

  private void hideProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)getSupportFragmentManager().findFragmentByTag("progress");
    if (localDialogFragment != null)
      localDialogFragment.dismissAllowingStateLoss();
  }

  private void loadPanoramaInfo()
  {
    if (this.mResource == null);
    while (true)
    {
      return;
      switch (this.mResource.getStatus())
      {
      case 2:
      case 3:
      default:
        break;
      case 1:
        hideProgressDialog();
        if (this.mPanoramaClient.isConnected())
        {
          boolean bool1 = this.mMediaRef.hasLocalUri();
          Object localObject = null;
          if (bool1)
          {
            Uri localUri = this.mMediaRef.getLocalUri();
            if (!MediaStoreUtils.isMediaStoreUri(localUri))
            {
              boolean bool2 = ImageUtils.isFileUri(localUri);
              localObject = null;
              if (!bool2);
            }
            else
            {
              localObject = localUri;
            }
          }
          if (localObject == null)
            localObject = EsProvider.buildPanoramaUri(this.mResource.getCacheFileName());
          this.mPanoramaClient.loadPanoramaInfoAndGrantAccess(this, (Uri)localObject);
        }
        break;
      case 4:
      case 5:
      case 6:
      case 7:
        hideProgressDialog();
        showFailureMessage();
        finish();
      }
    }
  }

  private void showFailureMessage()
  {
    Toast.makeText(this, R.string.toast_panorama_viewer_failure, 0).show();
  }

  public final void bindResources()
  {
    this.mResource = ImageResourceManager.getInstance(this).getMedia(this.mMediaRef, 1, 2, this);
  }

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  public final void onConnected()
  {
    loadPanoramaInfo();
  }

  public final void onConnectionFailed$5d4cef71()
  {
    showFailureMessage();
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (i != 0)
    {
      PanoramaDetector.clearCache();
      FragmentManager localFragmentManager = getSupportFragmentManager();
      if (localFragmentManager.findFragmentByTag("GMS_error") == null)
        new GmsErrorDialogFragment(i, (byte)0).show(localFragmentManager, "GMS_error");
    }
    while (true)
    {
      return;
      this.mPanoramaClient = new PanoramaClient(this, this, this);
      this.mPanoramaClient.connect();
      this.mMediaRef = ((MediaRef)getIntent().getParcelableExtra("mediaref"));
      bindResources();
      this.mHandler = new Handler();
      this.mHandler.postDelayed(new Runnable()
      {
        public final void run()
        {
          PanoramaViewerActivity.this.showProgressDialog();
        }
      }
      , 200L);
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
    unbindResources();
    if (this.mPanoramaClient != null)
    {
      this.mPanoramaClient.disconnect();
      this.mPanoramaClient = null;
    }
  }

  public final void onDisconnected()
  {
    finish();
  }

  public final void onPanoramaInfoLoaded$680664b4(Intent paramIntent)
  {
    if (paramIntent != null)
      startActivity(paramIntent);
    while (true)
    {
      finish();
      return;
      showFailureMessage();
    }
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    loadPanoramaInfo();
  }

  protected final void showProgressDialog()
  {
    if (isFinishing());
    while (true)
    {
      return;
      if (this.mResource.getStatus() != 1)
      {
        FragmentManager localFragmentManager = getSupportFragmentManager();
        if (localFragmentManager.findFragmentByTag("progress") == null)
          ProgressFragmentDialog.newInstance(null, getString(R.string.loading_panorama)).show(localFragmentManager, "progress");
      }
    }
  }

  public final void unbindResources()
  {
    if (this.mResource != null)
      this.mResource.unregister(this);
  }

  public static class GmsErrorDialogFragment extends DialogFragment
  {
    public GmsErrorDialogFragment()
    {
    }

    private GmsErrorDialogFragment(int paramInt)
    {
      Bundle localBundle = new Bundle();
      localBundle.putInt("errorCode", paramInt);
      setArguments(localBundle);
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      return GooglePlayServicesUtil.getErrorDialog(getArguments().getInt("errorCode"), getActivity(), 0);
    }

    public void onDismiss(DialogInterface paramDialogInterface)
    {
      FragmentActivity localFragmentActivity = getActivity();
      if (localFragmentActivity != null)
        localFragmentActivity.finish();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PanoramaViewerActivity
 * JD-Core Version:    0.6.2
 */