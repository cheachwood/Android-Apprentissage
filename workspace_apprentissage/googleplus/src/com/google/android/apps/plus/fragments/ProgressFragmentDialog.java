package com.google.android.apps.plus.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextThemeWrapper;
import com.google.android.apps.plus.R.style;

public class ProgressFragmentDialog extends DialogFragment
{
  public static ProgressFragmentDialog newInstance(String paramString1, String paramString2)
  {
    return newInstance(paramString1, paramString2, true);
  }

  public static ProgressFragmentDialog newInstance(String paramString1, String paramString2, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    if (paramString1 != null)
      localBundle.putString("title", paramString1);
    localBundle.putString("message", paramString2);
    ProgressFragmentDialog localProgressFragmentDialog = new ProgressFragmentDialog();
    localProgressFragmentDialog.setArguments(localBundle);
    localProgressFragmentDialog.setCancelable(paramBoolean);
    return localProgressFragmentDialog;
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    ProgressDialog localProgressDialog = new ProgressDialog(new ContextThemeWrapper(getActivity(), R.style.Theme_EmeraldSea));
    if (localBundle.containsKey("title"))
      localProgressDialog.setTitle(localBundle.getString("title"));
    localProgressDialog.setMessage(localBundle.getString("message"));
    localProgressDialog.setCanceledOnTouchOutside(isCancelable());
    localProgressDialog.setProgressStyle(0);
    return localProgressDialog;
  }

  public final void show(FragmentManager paramFragmentManager, String paramString)
  {
    try
    {
      super.show(paramFragmentManager, paramString);
      label6: return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      break label6;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ProgressFragmentDialog
 * JD-Core Version:    0.6.2
 */