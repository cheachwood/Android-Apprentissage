package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

public class AlertFragmentDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  private AlertDialogListener alertDialogListener;

  public static AlertFragmentDialog newInstance(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return newInstance(paramString1, paramString2, paramString3, paramString4, 0);
  }

  public static AlertFragmentDialog newInstance(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    Bundle localBundle = new Bundle();
    if (paramString1 != null)
      localBundle.putString("title", paramString1);
    localBundle.putString("message", paramString2);
    if (paramString3 != null)
      localBundle.putString("positive", paramString3);
    if (paramString4 != null)
      localBundle.putString("negative", paramString4);
    if (paramInt != 0)
      localBundle.putInt("icon", paramInt);
    AlertFragmentDialog localAlertFragmentDialog = new AlertFragmentDialog();
    localAlertFragmentDialog.setArguments(localBundle);
    return localAlertFragmentDialog;
  }

  public static AlertFragmentDialog newInstance(String paramString, String[] paramArrayOfString)
  {
    Bundle localBundle = new Bundle();
    if (paramString != null)
      localBundle.putString("title", paramString);
    if (paramArrayOfString != null)
      localBundle.putStringArray("list", paramArrayOfString);
    AlertFragmentDialog localAlertFragmentDialog = new AlertFragmentDialog();
    localAlertFragmentDialog.setArguments(localBundle);
    return localAlertFragmentDialog;
  }

  public final Context getDialogContext()
  {
    if (Build.VERSION.SDK_INT >= 11);
    for (Object localObject = getActivity(); ; localObject = new ContextThemeWrapper(getActivity(), 16973835))
      return localObject;
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    AlertDialogListener localAlertDialogListener = this.alertDialogListener;
    if (localAlertDialogListener == null)
      localAlertDialogListener = (AlertDialogListener)getTargetFragment();
    if (localAlertDialogListener != null)
    {
      getArguments();
      localAlertDialogListener.onDialogCanceled$20f9a4b7(getTag());
    }
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    AlertDialogListener localAlertDialogListener = this.alertDialogListener;
    if ((localAlertDialogListener == null) && ((getTargetFragment() instanceof AlertDialogListener)))
      localAlertDialogListener = (AlertDialogListener)getTargetFragment();
    if (localAlertDialogListener != null)
      switch (paramInt)
      {
      default:
        Bundle localBundle = getArguments();
        if ((localBundle.containsKey("list")) && (paramInt >= 0))
        {
          getTag();
          localAlertDialogListener.onDialogListClick$12e92030(paramInt, localBundle);
        }
        break;
      case -1:
      case -2:
      }
    while (true)
    {
      return;
      localAlertDialogListener.onDialogPositiveClick(getArguments(), getTag());
      continue;
      getArguments();
      localAlertDialogListener.onDialogNegativeClick$20f9a4b7(getTag());
    }
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    if (localBundle.containsKey("title"))
      localBuilder.setTitle(localBundle.getString("title"));
    if (localBundle.containsKey("message"))
      localBuilder.setMessage(localBundle.getString("message"));
    if (localBundle.containsKey("positive"))
      localBuilder.setPositiveButton(localBundle.getString("positive"), this);
    if (localBundle.containsKey("negative"))
      localBuilder.setNegativeButton(localBundle.getString("negative"), this);
    if (localBundle.containsKey("icon"))
      localBuilder.setIcon(localBundle.getInt("icon"));
    if (localBundle.containsKey("list"))
      localBuilder.setItems(localBundle.getStringArray("list"), this);
    return localBuilder.create();
  }

  public final void setListener(AlertDialogListener paramAlertDialogListener)
  {
    this.alertDialogListener = paramAlertDialogListener;
  }

  public static abstract interface AlertDialogListener
  {
    public abstract void onDialogCanceled$20f9a4b7(String paramString);

    public abstract void onDialogListClick$12e92030(int paramInt, Bundle paramBundle);

    public abstract void onDialogNegativeClick$20f9a4b7(String paramString);

    public abstract void onDialogPositiveClick(Bundle paramBundle, String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.AlertFragmentDialog
 * JD-Core Version:    0.6.2
 */