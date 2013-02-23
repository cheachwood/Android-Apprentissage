package com.google.android.apps.plus.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.style;

public class ProfileZagatExplanationDialog extends DialogFragment
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    dismiss();
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setStyle(1, R.style.ProfileLocalZagatExplanationDialog);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.zagat_explanation_dialog, paramViewGroup);
    ((Button)localView.findViewById(R.id.hint_ok)).setOnClickListener(this);
    return localView;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ProfileZagatExplanationDialog
 * JD-Core Version:    0.6.2
 */