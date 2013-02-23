package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class EsCursorAdapter extends CursorAdapter
{
  public EsCursorAdapter(Context paramContext, Cursor paramCursor)
  {
    super(paramContext, paramCursor, false);
  }

  public static void onPause()
  {
  }

  public void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= getCount())
      if (paramView != null);
    for (paramView = newView(this.mContext, getCursor(), paramViewGroup); ; paramView = super.getView(paramInt, paramView, paramViewGroup))
      return paramView;
  }

  public boolean isEmpty()
  {
    if (getCursor() == null);
    for (boolean bool = true; ; bool = super.isEmpty())
      return bool;
  }

  public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return null;
  }

  public void onResume()
  {
  }

  public void onStop()
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsCursorAdapter
 * JD-Core Version:    0.6.2
 */