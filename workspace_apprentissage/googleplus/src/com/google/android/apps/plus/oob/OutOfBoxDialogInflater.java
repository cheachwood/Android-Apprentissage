package com.google.android.apps.plus.oob;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.phone.Intents;
import com.google.api.services.plusi.model.MobileOutOfBoxRequest;
import com.google.api.services.plusi.model.OutOfBoxAction;
import com.google.api.services.plusi.model.OutOfBoxDialog;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class OutOfBoxDialogInflater
  implements View.OnClickListener
{
  private final ActionCallback mActionCallback;
  private final FragmentActivity mActivity;
  private final OutOfBoxView mOutOfBoxView;
  private final ViewGroup mParent;

  public OutOfBoxDialogInflater(FragmentActivity paramFragmentActivity, ViewGroup paramViewGroup, OutOfBoxView paramOutOfBoxView, ActionCallback paramActionCallback)
  {
    this.mActivity = paramFragmentActivity;
    this.mParent = paramViewGroup;
    this.mOutOfBoxView = paramOutOfBoxView;
    this.mActionCallback = paramActionCallback;
  }

  public final void inflate()
  {
    this.mParent.removeAllViews();
    ViewGroup localViewGroup1 = this.mParent;
    OutOfBoxDialog localOutOfBoxDialog = this.mOutOfBoxView.dialog;
    Context localContext = new AlertDialog.Builder(this.mActivity).create().getContext();
    Dialog localDialog = new Dialog(localContext);
    if (localOutOfBoxDialog.header != null)
    {
      localDialog.setTitle(localOutOfBoxDialog.header);
      TextView localTextView = (TextView)localDialog.findViewById(16908310);
      if (localTextView != null)
        localTextView.setSingleLine(false);
    }
    while (true)
    {
      localDialog.setContentView(R.layout.oob_dialog);
      if (localOutOfBoxDialog.text != null)
        ((TextView)localDialog.findViewById(R.id.message)).setText(localOutOfBoxDialog.text);
      ViewGroup localViewGroup2 = (ViewGroup)localDialog.findViewById(R.id.buttonPanel);
      if (localOutOfBoxDialog.action == null)
        break;
      Iterator localIterator = localOutOfBoxDialog.action.iterator();
      while (localIterator.hasNext())
      {
        OutOfBoxAction localOutOfBoxAction = (OutOfBoxAction)localIterator.next();
        Button localButton = (Button)LayoutInflater.from(localContext).inflate(R.layout.oob_dialog_button, localViewGroup2, false);
        localButton.setText(localOutOfBoxAction.text);
        localButton.setTag(localOutOfBoxAction);
        localButton.setOnClickListener(this);
        localViewGroup2.addView(localButton);
      }
      localDialog.requestWindowFeature(1);
    }
    localViewGroup1.addView(localDialog.getWindow().getDecorView());
  }

  public final void onClick(View paramView)
  {
    OutOfBoxAction localOutOfBoxAction = (OutOfBoxAction)paramView.getTag();
    if ("BACK".equals(localOutOfBoxAction.type))
      if (!this.mActivity.getSupportFragmentManager().popBackStackImmediate())
      {
        this.mActivity.setResult(0);
        this.mActivity.finish();
      }
    while (true)
    {
      return;
      if ("CLOSE".equals(localOutOfBoxAction.type))
      {
        this.mActivity.setResult(0);
        this.mActivity.finish();
      }
      else if ("URL".equals(localOutOfBoxAction.type))
      {
        Intents.viewUrl(this.mActivity, null, localOutOfBoxAction.url);
      }
      else
      {
        ActionCallback localActionCallback = this.mActionCallback;
        MobileOutOfBoxRequest localMobileOutOfBoxRequest = new MobileOutOfBoxRequest();
        localMobileOutOfBoxRequest.input = new ArrayList();
        Iterator localIterator = this.mOutOfBoxView.field.iterator();
        while (localIterator.hasNext())
        {
          OutOfBoxField localOutOfBoxField = (OutOfBoxField)localIterator.next();
          if (localOutOfBoxField.input != null)
            localMobileOutOfBoxRequest.input.add(localOutOfBoxField.input);
        }
        localMobileOutOfBoxRequest.action = new OutOfBoxAction();
        localMobileOutOfBoxRequest.action.type = localOutOfBoxAction.type;
        localActionCallback.sendOutOfBoxRequest(localMobileOutOfBoxRequest);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.OutOfBoxDialogInflater
 * JD-Core Version:    0.6.2
 */