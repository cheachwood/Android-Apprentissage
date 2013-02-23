package com.google.android.apps.plus.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;

public class CirclePropertiesFragmentDialog extends AlertFragmentDialog
  implements TextWatcher
{
  private TextView mInputTextView;
  private CheckBox mJustFollowingCheckBox;

  private void checkPositiveButtonEnabled()
  {
    AlertDialog localAlertDialog = (AlertDialog)getDialog();
    if (localAlertDialog == null)
      return;
    Button localButton = localAlertDialog.getButton(-1);
    if (!TextUtils.isEmpty(this.mInputTextView.getText().toString().trim()));
    for (boolean bool = true; ; bool = false)
    {
      localButton.setEnabled(bool);
      break;
    }
  }

  private boolean isNewCircle()
  {
    Bundle localBundle = getArguments();
    if ((localBundle == null) || (TextUtils.isEmpty(localBundle.getString("circle_id"))));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static CirclePropertiesFragmentDialog newInstance$47e87423()
  {
    return new CirclePropertiesFragmentDialog();
  }

  public static CirclePropertiesFragmentDialog newInstance$50fd8769(String paramString1, String paramString2, boolean paramBoolean)
  {
    CirclePropertiesFragmentDialog localCirclePropertiesFragmentDialog = new CirclePropertiesFragmentDialog();
    Bundle localBundle = new Bundle();
    localBundle.putString("circle_id", paramString1);
    localBundle.putString("name", paramString2);
    localBundle.putBoolean("just_following", paramBoolean);
    localCirclePropertiesFragmentDialog.setArguments(localBundle);
    return localCirclePropertiesFragmentDialog;
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    CirclePropertiesListener localCirclePropertiesListener;
    if (paramInt == -1)
    {
      localCirclePropertiesListener = (CirclePropertiesListener)getTargetFragment();
      if (localCirclePropertiesListener == null)
        localCirclePropertiesListener = (CirclePropertiesListener)getActivity();
      if (!isNewCircle())
        break label70;
    }
    label70: for (String str = null; ; str = getArguments().getString("circle_id"))
    {
      localCirclePropertiesListener.onCirclePropertiesChange(str, this.mInputTextView.getText().toString().trim(), this.mJustFollowingCheckBox.isChecked());
      super.onClick(paramDialogInterface, paramInt);
      return;
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Context localContext = getDialogContext();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContext);
    View localView = LayoutInflater.from(localContext).inflate(R.layout.circle_properties_dialog, null);
    this.mInputTextView = ((TextView)localView.findViewById(R.id.text));
    this.mInputTextView.addTextChangedListener(this);
    this.mInputTextView.setHint(R.string.new_circle_dialog_hint);
    this.mJustFollowingCheckBox = ((CheckBox)localView.findViewById(R.id.just_following_checkbox));
    localView.findViewById(R.id.just_following_layout).setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        CirclePropertiesFragmentDialog.this.mJustFollowingCheckBox.toggle();
      }
    });
    if (paramBundle != null)
    {
      this.mInputTextView.setText(paramBundle.getCharSequence("name"));
      this.mJustFollowingCheckBox.setChecked(paramBundle.getBoolean("just_following"));
      localBuilder.setView(localView);
      if (!isNewCircle())
        break label217;
    }
    label217: for (int i = R.string.new_circle_dialog_title; ; i = R.string.circle_properties_dialog_title)
    {
      localBuilder.setTitle(i);
      localBuilder.setPositiveButton(R.string.ok, this);
      localBuilder.setNegativeButton(R.string.cancel, this);
      return localBuilder.create();
      if (isNewCircle())
        break;
      Bundle localBundle = getArguments();
      this.mInputTextView.setText(localBundle.getCharSequence("name"));
      this.mJustFollowingCheckBox.setChecked(localBundle.getBoolean("just_following"));
      break;
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putCharSequence("name", this.mInputTextView.getText());
    paramBundle.putBoolean("just_following", this.mJustFollowingCheckBox.isChecked());
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

  public static abstract interface CirclePropertiesListener
  {
    public abstract void onCirclePropertiesChange(String paramString1, String paramString2, boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CirclePropertiesFragmentDialog
 * JD-Core Version:    0.6.2
 */