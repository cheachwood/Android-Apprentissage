package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.apps.plus.R.string;

public class ReportAbuseDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  private static final String[] ABUSE_TYPES = { "FAKE_USER", "HATE", "IMPERSONATION", "PORN", "SPAM", "COPYRIGHT" };
  private static final int[] ABUSE_TYPE_LABELS = arrayOfInt;
  private int mAbuseType = -1;

  static
  {
    int[] arrayOfInt = new int[6];
    arrayOfInt[0] = R.string.report_abuse_reason_fake_profile;
    arrayOfInt[1] = R.string.report_abuse_reason_hate_speech_or_violence;
    arrayOfInt[2] = R.string.report_abuse_reason_impersonation;
    arrayOfInt[3] = R.string.report_abuse_reason_nudity;
    arrayOfInt[4] = R.string.report_abuse_reason_spam;
    arrayOfInt[5] = R.string.report_abuse_reason_copyright;
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
      if (paramInt >= 0)
        this.mAbuseType = paramInt;
      break;
    case -1:
    case -2:
    }
    while (true)
    {
      return;
      if (this.mAbuseType != -1)
      {
        ((HostedProfileFragment)getTargetFragment()).reportAbuse(ABUSE_TYPES[this.mAbuseType]);
        continue;
        paramDialogInterface.dismiss();
      }
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    if (paramBundle != null)
      this.mAbuseType = paramBundle.getInt("abuse_type");
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    localBuilder.setTitle(R.string.report_user_dialog_title);
    localBuilder.setPositiveButton(17039370, this);
    localBuilder.setNegativeButton(17039360, this);
    localBuilder.setCancelable(true);
    String[] arrayOfString = new String[ABUSE_TYPE_LABELS.length];
    for (int i = 0; i < ABUSE_TYPE_LABELS.length; i++)
      arrayOfString[i] = localFragmentActivity.getString(ABUSE_TYPE_LABELS[i]);
    localBuilder.setSingleChoiceItems(arrayOfString, this.mAbuseType, this);
    return localBuilder.create();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("abuse_type", this.mAbuseType);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ReportAbuseDialog
 * JD-Core Version:    0.6.2
 */