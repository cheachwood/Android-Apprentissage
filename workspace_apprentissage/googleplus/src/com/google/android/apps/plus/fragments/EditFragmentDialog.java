package com.google.android.apps.plus.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;

public class EditFragmentDialog extends AlertFragmentDialog
  implements TextWatcher
{
  private EditText mInputTextView;

  private void checkPositiveButtonEnabled()
  {
    AlertDialog localAlertDialog = (AlertDialog)getDialog();
    if (localAlertDialog == null)
      return;
    Button localButton;
    if (!getArguments().getBoolean("allow_empty"))
    {
      localButton = localAlertDialog.getButton(-1);
      if (TextUtils.isEmpty(this.mInputTextView.getText().toString().trim()))
        break label60;
    }
    label60: for (boolean bool = true; ; bool = false)
    {
      localButton.setEnabled(bool);
      break;
      break;
    }
  }

  public static EditFragmentDialog newInstance$405ed676(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    if (paramString1 != null)
      localBundle.putString("title", paramString1);
    localBundle.putString("message", null);
    localBundle.putString("hint", paramString3);
    if (paramString4 != null)
      localBundle.putString("positive", paramString4);
    if (paramString5 != null)
      localBundle.putString("negative", paramString5);
    localBundle.putBoolean("allow_empty", false);
    EditFragmentDialog localEditFragmentDialog = new EditFragmentDialog();
    localEditFragmentDialog.setArguments(localBundle);
    return localEditFragmentDialog;
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    getArguments().putString("message", this.mInputTextView.getText().toString().trim());
    super.onClick(paramDialogInterface, paramInt);
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(R.layout.text_input_dialog, null);
    this.mInputTextView = ((EditText)localView.findViewById(R.id.text_input));
    if (!localBundle.getBoolean("allow_empty"))
      this.mInputTextView.addTextChangedListener(this);
    if (paramBundle != null)
      this.mInputTextView.setText(paramBundle.getString("message"));
    while (true)
    {
      this.mInputTextView.setHint(localBundle.getString("hint"));
      localBuilder.setView(localView);
      if (localBundle.containsKey("title"))
        localBuilder.setTitle(localBundle.getString("title"));
      if (localBundle.containsKey("positive"))
        localBuilder.setPositiveButton(localBundle.getString("positive"), this);
      if (localBundle.containsKey("negative"))
        localBuilder.setNegativeButton(localBundle.getString("negative"), this);
      return localBuilder.create();
      this.mInputTextView.setText(localBundle.getString("message"));
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putString("message", this.mInputTextView.getText().toString());
  }

  public final void onStart()
  {
    super.onStart();
    checkPositiveButtonEnabled();
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    checkPositiveButtonEnabled();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EditFragmentDialog
 * JD-Core Version:    0.6.2
 */