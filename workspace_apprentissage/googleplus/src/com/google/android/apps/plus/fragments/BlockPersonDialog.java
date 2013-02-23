package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.HelpUrl;
import java.io.Serializable;

public class BlockPersonDialog extends DialogFragment
  implements DialogInterface.OnClickListener
{
  public BlockPersonDialog()
  {
    this(false);
  }

  public BlockPersonDialog(boolean paramBoolean)
  {
    this(paramBoolean, null);
  }

  public BlockPersonDialog(boolean paramBoolean, Serializable paramSerializable)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("plus_page", paramBoolean);
    localBundle.putSerializable("callback_data", paramSerializable);
    setArguments(localBundle);
  }

  private void configureExplanationLink(TextView paramTextView)
  {
    String str = getActivity().getString(R.string.what_does_this_mean_link);
    SpannableString localSpannableString = new SpannableString(str);
    localSpannableString.setSpan(new ClickableSpan()
    {
      public final void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", this.val$url);
        localIntent.addFlags(524288);
        BlockPersonDialog.this.startActivity(localIntent);
      }
    }
    , 0, str.length(), 33);
    paramTextView.setText(localSpannableString);
    paramTextView.setMovementMethod(LinkMovementMethod.getInstance());
    paramTextView.setClickable(true);
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
      Serializable localSerializable = getArguments().getSerializable("callback_data");
      Fragment localFragment = getTargetFragment();
      if ((localFragment instanceof PersonBlocker))
      {
        ((PersonBlocker)localFragment).blockPerson(localSerializable);
      }
      else
      {
        FragmentActivity localFragmentActivity = getActivity();
        if ((localFragmentActivity instanceof PersonBlocker))
        {
          ((PersonBlocker)localFragmentActivity).blockPerson(localSerializable);
          continue;
          paramDialogInterface.dismiss();
        }
      }
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    boolean bool = getArguments().getBoolean("plus_page");
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    int i;
    View localView;
    TextView localTextView;
    if (bool)
    {
      i = R.string.block_page_dialog_title;
      localBuilder.setTitle(i);
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      localView = LayoutInflater.from(localFragmentActivity).inflate(R.layout.block_profile_confirm_dialog, null);
      localTextView = (TextView)localView.findViewById(R.id.message);
      if (!bool)
        break label146;
    }
    label146: for (int j = R.string.block_page_dialog_message; ; j = R.string.block_person_dialog_message)
    {
      localTextView.setText(j);
      configureExplanationLink((TextView)localView.findViewById(R.id.explanation));
      localBuilder.setView(localView);
      return localBuilder.create();
      i = R.string.block_person_dialog_title;
      break;
    }
  }

  public final void onPause()
  {
    super.onPause();
    Dialog localDialog = getDialog();
    if (localDialog != null)
      ((TextView)localDialog.findViewById(R.id.explanation)).setText(null);
  }

  public final void onResume()
  {
    super.onResume();
    Dialog localDialog = getDialog();
    if (localDialog != null)
      configureExplanationLink((TextView)localDialog.findViewById(R.id.explanation));
  }

  public static abstract interface PersonBlocker
  {
    public abstract void blockPerson(Serializable paramSerializable);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.BlockPersonDialog
 * JD-Core Version:    0.6.2
 */