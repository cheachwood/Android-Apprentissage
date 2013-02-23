package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ConfirmIntentDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  public static DialogFragment newInstance(CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, Intent paramIntent)
  {
    Bundle localBundle = new Bundle();
    localBundle.putCharSequence("title", paramCharSequence1);
    localBundle.putCharSequence("message", paramCharSequence2);
    localBundle.putCharSequence("positive", paramCharSequence3);
    localBundle.putParcelable("intent", paramIntent);
    ConfirmIntentDialog localConfirmIntentDialog = new ConfirmIntentDialog();
    localConfirmIntentDialog.setArguments(localBundle);
    return localConfirmIntentDialog;
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    }
    while (true)
    {
      dismiss();
      return;
      startActivity((Intent)getArguments().getParcelable("intent"));
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    localBuilder.setTitle(localBundle.getCharSequence("title"));
    localBuilder.setMessage(localBundle.getCharSequence("message"));
    localBuilder.setPositiveButton(localBundle.getCharSequence("positive"), this);
    return localBuilder.create();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ConfirmIntentDialog
 * JD-Core Version:    0.6.2
 */