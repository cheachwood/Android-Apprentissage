package com.google.android.apps.plus.oob;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CustomProgressFragmentDialog extends DialogFragment
{
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    ProgressDialog localProgressDialog = new ProgressDialog(getActivity());
    if (localBundle.containsKey("title"))
      localProgressDialog.setTitle(localBundle.getString("title"));
    localProgressDialog.setMessage(localBundle.getString("message"));
    boolean bool = localBundle.getBoolean("cancelable");
    localProgressDialog.setCancelable(bool);
    localProgressDialog.setCanceledOnTouchOutside(bool);
    setCancelable(bool);
    if (bool)
      localProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public final void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          if ((CustomProgressFragmentDialog.CustomProgressDialogListener)CustomProgressFragmentDialog.this.getTargetFragment() != null);
        }
      });
    localProgressDialog.setProgressStyle(0);
    return localProgressDialog;
  }

  public static abstract interface CustomProgressDialogListener
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.CustomProgressFragmentDialog
 * JD-Core Version:    0.6.2
 */