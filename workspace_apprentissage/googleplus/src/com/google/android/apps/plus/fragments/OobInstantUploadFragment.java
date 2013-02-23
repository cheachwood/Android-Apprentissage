package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.phone.OobDeviceActivity;

public class OobInstantUploadFragment extends Fragment
  implements AlertFragmentDialog.AlertDialogListener
{
  private RadioGroup mUploadChoice;

  private void doNextStep()
  {
    int i = this.mUploadChoice.getCheckedRadioButtonId();
    final boolean bool2;
    final boolean bool1;
    final OobDeviceActivity localOobDeviceActivity;
    final EsAccount localEsAccount;
    OzActions localOzActions;
    if (i == R.id.option_wifi_only)
    {
      bool2 = true;
      bool1 = true;
      localOobDeviceActivity = (OobDeviceActivity)getActivity();
      localEsAccount = (EsAccount)localOobDeviceActivity.getIntent().getParcelableExtra("account");
      new AsyncTask()
      {
      }
      .execute(null);
      if (!bool2)
        break label117;
      if (!bool1)
        break label109;
      localOzActions = OzActions.CAMERA_SYNC_WIFI_ONLY_OPTED_IN;
    }
    while (true)
    {
      EsAnalytics.recordActionEvent(localOobDeviceActivity, localEsAccount, localOzActions, OzViews.OOB_CAMERA_SYNC);
      return;
      if (i == R.id.option_wifi_and_mobile)
      {
        bool2 = true;
        bool1 = false;
        break;
      }
      bool1 = true;
      bool2 = false;
      break;
      label109: localOzActions = OzActions.CAMERA_SYNC_OPTED_IN;
      continue;
      label117: localOzActions = OzActions.CAMERA_SYNC_OPTED_OUT;
    }
  }

  public final boolean commit()
  {
    boolean bool1 = true;
    if (this.mUploadChoice.getCheckedRadioButtonId() == R.id.option_disable);
    boolean bool4;
    for (boolean bool2 = bool1; ; bool2 = false)
    {
      boolean bool3 = InstantUpload.isSyncEnabled$1f9c1b43((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
      bool4 = ContentResolver.getMasterSyncAutomatically();
      if (((!bool4) || (!bool3)) && (!bool2))
        break;
      doNextStep();
      return bool1;
    }
    if (!bool4)
    {
      FragmentManager localFragmentManager2 = getFragmentManager();
      if (localFragmentManager2.findFragmentByTag("photo_master_dialog") == null)
      {
        AlertFragmentDialog localAlertFragmentDialog2 = AlertFragmentDialog.newInstance(getString(R.string.oob_master_sync_dialog_title), getString(R.string.oob_master_sync_dialog_message), getString(R.string.ok), null);
        localAlertFragmentDialog2.setTargetFragment(this, 0);
        localAlertFragmentDialog2.show(localFragmentManager2, "photo_master_dialog");
      }
    }
    while (true)
    {
      bool1 = false;
      break;
      FragmentManager localFragmentManager1 = getFragmentManager();
      if (localFragmentManager1.findFragmentByTag("photo_sync_dialog") == null)
      {
        String str1 = getString(R.string.es_google_iu_provider);
        int i = R.string.oob_enable_sync_dialog_message;
        Object[] arrayOfObject = new Object[bool1];
        arrayOfObject[0] = str1;
        String str2 = getString(i, arrayOfObject);
        AlertFragmentDialog localAlertFragmentDialog1 = AlertFragmentDialog.newInstance(getString(R.string.oob_enable_sync_dialog_title), str2, getString(R.string.yes), getString(R.string.no));
        localAlertFragmentDialog1.setTargetFragment(this, 0);
        localAlertFragmentDialog1.show(localFragmentManager1, "photo_sync_dialog");
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.oob_instant_upload_fragment, paramViewGroup, false);
    this.mUploadChoice = ((RadioGroup)localView1.findViewById(R.id.uploadChoice));
    this.mUploadChoice.check(R.id.option_wifi_and_mobile);
    if (((ConnectivityManager)getActivity().getSystemService("connectivity")).getNetworkInfo(0) == null)
    {
      View localView2 = this.mUploadChoice.findViewById(R.id.option_wifi_and_mobile);
      if (localView2 != null)
        this.mUploadChoice.removeView(localView2);
      this.mUploadChoice.check(R.id.option_wifi_only);
    }
    return localView1;
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    doNextStep();
    ((OobDeviceActivity)getActivity()).onContinue();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.OobInstantUploadFragment
 * JD-Core Version:    0.6.2
 */