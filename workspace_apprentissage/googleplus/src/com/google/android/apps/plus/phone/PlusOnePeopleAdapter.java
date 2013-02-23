package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.views.AvatarView;

public final class PlusOnePeopleAdapter extends EsCursorAdapter
{
  private View mExtraPeopleView;

  public PlusOnePeopleAdapter(Context paramContext, Cursor paramCursor)
  {
    super(paramContext, null);
  }

  private boolean isLastViewExtraPeopleCount()
  {
    if (this.mExtraPeopleView != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    if (paramView == this.mExtraPeopleView);
    while (true)
    {
      return;
      if (paramCursor.getPosition() < super.getCount())
      {
        AvatarView localAvatarView = (AvatarView)paramView.findViewById(R.id.avatar);
        localAvatarView.setVisibility(0);
        localAvatarView.setGaiaIdAndAvatarUrl(paramCursor.getString(2), paramCursor.getString(4));
        ((TextView)paramView.findViewById(R.id.name)).setText(paramCursor.getString(3));
      }
    }
  }

  public final int getCount()
  {
    int i = super.getCount();
    if (isLastViewExtraPeopleCount());
    for (int j = 1; ; j = 0)
      return j + i;
  }

  public final int getItemViewType(int paramInt)
  {
    if (isExtraPeopleViewIndex(paramInt));
    for (int i = 1; ; i = 0)
      return i;
  }

  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= getCount())
      if (paramView == null)
        paramView = newView(this.mContext, getCursor(), paramViewGroup);
    while (true)
    {
      return paramView;
      if (isExtraPeopleViewIndex(paramInt))
        paramView = this.mExtraPeopleView;
      else
        paramView = super.getView(paramInt, paramView, paramViewGroup);
    }
  }

  public final int getViewTypeCount()
  {
    return 2;
  }

  public final boolean isExtraPeopleViewIndex(int paramInt)
  {
    if ((isLastViewExtraPeopleCount()) && (paramInt == super.getCount()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    if (isExtraPeopleViewIndex(paramCursor.getPosition()));
    for (View localView = this.mExtraPeopleView; ; localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.acl_row_view, null))
      return localView;
  }

  public final void setExtraPeopleCount(int paramInt)
  {
    if (paramInt <= 0)
      this.mExtraPeopleView = null;
    while (true)
    {
      return;
      this.mExtraPeopleView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(R.layout.acl_row_view, null, false);
      this.mExtraPeopleView.findViewById(R.id.avatar).setVisibility(4);
      TextView localTextView = (TextView)this.mExtraPeopleView.findViewById(R.id.name);
      Resources localResources = this.mContext.getResources();
      int i = R.plurals.plus_one_people_more_plus_ones;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      localTextView.setText(localResources.getQuantityString(i, paramInt, arrayOfObject));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PlusOnePeopleAdapter
 * JD-Core Version:    0.6.2
 */