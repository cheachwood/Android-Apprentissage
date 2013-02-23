package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.apps.plus.R.string;

public class UnblockPersonDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  public UnblockPersonDialog()
  {
  }

  public UnblockPersonDialog(String paramString, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("person_id", paramString);
    localBundle.putBoolean("plus_page", paramBoolean);
    setArguments(localBundle);
  }

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
      String str = getArguments().getString("person_id");
      if ((getTargetFragment() instanceof PersonUnblocker))
      {
        ((PersonUnblocker)getTargetFragment()).unblockPerson(str);
      }
      else
      {
        ((PersonUnblocker)getActivity()).unblockPerson(str);
        continue;
        paramDialogInterface.dismiss();
      }
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    boolean bool = getArguments().getBoolean("plus_page");
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    int i;
    if (bool)
    {
      i = R.string.unblock_page_dialog_title;
      localBuilder.setTitle(i);
      if (!bool)
        break label98;
    }
    label98: for (int j = R.string.unblock_page_dialog_message; ; j = R.string.unblock_person_dialog_message)
    {
      localBuilder.setMessage(j);
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      return localBuilder.create();
      i = R.string.unblock_person_dialog_title;
      break;
    }
  }

  public static abstract interface PersonUnblocker
  {
    public abstract void unblockPerson(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.UnblockPersonDialog
 * JD-Core Version:    0.6.2
 */