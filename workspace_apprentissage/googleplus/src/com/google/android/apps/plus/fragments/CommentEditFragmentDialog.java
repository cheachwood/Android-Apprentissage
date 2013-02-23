package com.google.android.apps.plus.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;

public class CommentEditFragmentDialog extends AlertFragmentDialog
  implements TextWatcher
{
  private MentionMultiAutoCompleteTextView mInputTextView;

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

  public static CommentEditFragmentDialog newInstance(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("comment_id", "");
    localBundle.putString("comment_text", "");
    localBundle.putInt("title_id", paramInt);
    CommentEditFragmentDialog localCommentEditFragmentDialog = new CommentEditFragmentDialog();
    localCommentEditFragmentDialog.setArguments(localBundle);
    return localCommentEditFragmentDialog;
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.mInputTextView.getText());
      while ((localSpannableStringBuilder.length() > 0) && (Character.isWhitespace(localSpannableStringBuilder.charAt(0))))
        localSpannableStringBuilder.delete(0, 1);
      while ((localSpannableStringBuilder.length() > 0) && (Character.isWhitespace(localSpannableStringBuilder.charAt(-1 + localSpannableStringBuilder.length()))))
        localSpannableStringBuilder.delete(-1 + localSpannableStringBuilder.length(), localSpannableStringBuilder.length());
      CommentEditDialogListener localCommentEditDialogListener = (CommentEditDialogListener)getTargetFragment();
      getArguments().getString("comment_id");
      localCommentEditDialogListener.onCommentEditComplete$42c1be52(localSpannableStringBuilder);
    }
    super.onClick(paramDialogInterface, paramInt);
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(R.layout.comment_edit_dialog, null);
    this.mInputTextView = ((MentionMultiAutoCompleteTextView)localView.findViewById(R.id.text));
    this.mInputTextView.addTextChangedListener(this);
    if (paramBundle != null)
      this.mInputTextView.setText(paramBundle.getCharSequence("comment_text"));
    while (true)
    {
      localBuilder.setView(localView);
      localBuilder.setTitle(localBundle.getInt("title_id", R.string.menu_edit_comment));
      localBuilder.setPositiveButton(R.string.ok, this);
      localBuilder.setNegativeButton(R.string.cancel, this);
      return localBuilder.create();
      this.mInputTextView.setHtml(localBundle.getString("comment_text"));
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putCharSequence("comment_text", this.mInputTextView.getText());
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

  public static abstract interface CommentEditDialogListener
  {
    public abstract void onCommentEditComplete$42c1be52(Spannable paramSpannable);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CommentEditFragmentDialog
 * JD-Core Version:    0.6.2
 */