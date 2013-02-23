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

public class CircleSubscribeDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  private int mAction;
  private String mCircleId;
  private String mCircleName;

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
      if (this.mAction == 2)
      {
        ((HostedStreamFragment)getTargetFragment()).doCircleSubscribe(this.mCircleId, this.mCircleName);
      }
      else
      {
        ((HostedStreamFragment)getTargetFragment()).doCircleUnsubscribe(this.mCircleId, this.mCircleName);
        continue;
        paramDialogInterface.dismiss();
      }
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    this.mAction = localBundle.getInt("do_subscribe");
    this.mCircleName = localBundle.getString("circle_name");
    this.mCircleId = localBundle.getString("circle_id");
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    int i;
    View localView;
    TextView localTextView;
    if (this.mAction == 2)
    {
      i = R.string.dialog_title_subscribe;
      localBuilder.setTitle(getString(i));
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      localView = LayoutInflater.from(localFragmentActivity).inflate(R.layout.block_profile_confirm_dialog, null);
      localTextView = (TextView)localView.findViewById(R.id.message);
      if (this.mAction != 2)
        break label204;
    }
    label204: for (int j = R.string.dialog_content_subscribe; ; j = R.string.dialog_content_unsubscribe)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mCircleName;
      localTextView.setText(getString(j, arrayOfObject));
      ((TextView)localView.findViewById(R.id.explanation)).setVisibility(8);
      localBuilder.setView(localView);
      return localBuilder.create();
      i = R.string.dialog_title_unsubscribe;
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CircleSubscribeDialog
 * JD-Core Version:    0.6.2
 */