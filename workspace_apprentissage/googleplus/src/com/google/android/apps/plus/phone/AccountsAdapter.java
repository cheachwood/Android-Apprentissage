package com.google.android.apps.plus.phone;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public final class AccountsAdapter extends ArrayAdapter<String>
{
  public AccountsAdapter(Context paramContext)
  {
    super(paramContext, 17367043, new ArrayList());
  }

  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = super.getView(paramInt, paramView, paramViewGroup);
    String str = (String)getItem(paramInt);
    ((TextView)localView.findViewById(16908308)).setText(str);
    return localView;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.AccountsAdapter
 * JD-Core Version:    0.6.2
 */