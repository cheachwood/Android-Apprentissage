package com.google.android.apps.plus.phone;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;

public class ConversationRenameDialog extends DialogFragment
  implements DialogInterface.OnClickListener, TextWatcher
{
  public ConversationRenameDialog()
  {
  }

  public ConversationRenameDialog(String paramString, long paramLong)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("name", paramString);
    localBundle.putLong("row_id", paramLong);
    setArguments(localBundle);
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
      paramDialogInterface.dismiss();
    case -1:
    }
    while (true)
    {
      return;
      FragmentActivity localFragmentActivity = getActivity();
      EsAccount localEsAccount = EsAccountsData.getActiveAccount(localFragmentActivity);
      if (localEsAccount != null)
      {
        OzViews localOzViews = OzViews.getViewForLogging(localFragmentActivity);
        EsAnalytics.recordActionEvent(localFragmentActivity, localEsAccount, OzActions.GROUP_CONVERSATION_RENAME, localOzViews);
        Dialog localDialog = getDialog();
        if (localDialog != null)
        {
          String str = ((EditText)localDialog.findViewById(R.id.conversation_rename_input)).getText().toString();
          RealTimeChatService.setConversationName(localFragmentActivity, localEsAccount, getArguments().getLong("row_id"), str);
        }
      }
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    View localView = localFragmentActivity.getLayoutInflater().inflate(R.layout.conversation_rename, null);
    EditText localEditText = (EditText)localView.findViewById(R.id.conversation_rename_input);
    localEditText.setText(getArguments().getString("name"));
    localBuilder.setTitle(R.string.realtimechat_conversation_rename_dialog_title).setCancelable(false).setView(localView).setPositiveButton(getString(R.string.realtimechat_conversation_rename_save_button_text), this).setNegativeButton(getString(R.string.realtimechat_conversation_rename_cancel_button_text), this);
    AlertDialog localAlertDialog = localBuilder.create();
    localEditText.addTextChangedListener(this);
    localAlertDialog.getWindow().setSoftInputMode(5);
    return localAlertDialog;
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    AlertDialog localAlertDialog = (AlertDialog)getDialog();
    Button localButton;
    if (localAlertDialog != null)
    {
      localButton = localAlertDialog.getButton(-1);
      if (paramCharSequence.toString().trim().length() <= 0)
        break label48;
    }
    label48: for (boolean bool = true; ; bool = false)
    {
      localButton.setEnabled(bool);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ConversationRenameDialog
 * JD-Core Version:    0.6.2
 */