package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;

public class MuteProfileDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  private String mGender;
  private String mName;
  private boolean mTargetMuteState;

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    case -2:
    }
    while (true)
    {
      return;
      ((HostedProfileFragment)getTargetFragment()).setPersonMuted(this.mTargetMuteState);
      continue;
      paramDialogInterface.dismiss();
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    this.mName = localBundle.getString("name");
    this.mGender = localBundle.getString("gender");
    this.mTargetMuteState = localBundle.getBoolean("target_mute");
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    int i;
    View localView;
    TextView localTextView;
    if (this.mTargetMuteState)
    {
      i = R.string.mute_dialog_title;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mName;
      localBuilder.setTitle(getString(i, arrayOfObject));
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      localView = LayoutInflater.from(localFragmentActivity).inflate(R.layout.block_profile_confirm_dialog, null);
      localTextView = (TextView)localView.findViewById(R.id.message);
      if (!this.mGender.equals("MALE"))
        break label226;
      if (!this.mTargetMuteState)
        break label218;
    }
    String str;
    label218: for (int m = R.string.mute_dialog_content_male; ; m = R.string.unmute_dialog_content_male)
    {
      str = getString(m);
      localTextView.setText(str);
      ((TextView)localView.findViewById(R.id.explanation)).setVisibility(8);
      localBuilder.setView(localView);
      return localBuilder.create();
      i = R.string.unmute_dialog_title;
      break;
    }
    label226: if (this.mGender.equals("FEMALE"))
    {
      if (this.mTargetMuteState);
      for (int k = R.string.mute_dialog_content_female; ; k = R.string.unmute_dialog_content_female)
      {
        str = getString(k);
        break;
      }
    }
    if (this.mTargetMuteState);
    for (int j = R.string.mute_dialog_content_general; ; j = R.string.unmute_dialog_content_general)
    {
      str = getString(j);
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.MuteProfileDialog
 * JD-Core Version:    0.6.2
 */