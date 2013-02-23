package com.google.android.apps.plus.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

final class QuickActionsMenuItem
  implements MenuItem
{
  private final Context mContext;
  private boolean mEnabled;
  private final int mGroupId;
  private Drawable mIcon;
  private Intent mIntent;
  private final int mItemId;
  private final MenuItem.OnMenuItemClickListener mMenuClickListener;
  private final ContextMenu.ContextMenuInfo mMenuInfo;
  private MenuItem.OnMenuItemClickListener mMenuItemClickListener;
  private final int mOrder;
  private QuickActionsSubMenu mSubMenu;
  private CharSequence mTitle;
  private CharSequence mTitleCondensed;
  private boolean mVisible;

  public QuickActionsMenuItem(Context paramContext, int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence, ContextMenu.ContextMenuInfo paramContextMenuInfo, MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mContext = paramContext;
    this.mGroupId = paramInt1;
    this.mItemId = paramInt2;
    this.mOrder = paramInt3;
    this.mTitle = paramCharSequence;
    this.mMenuInfo = paramContextMenuInfo;
    this.mEnabled = true;
    this.mVisible = true;
    this.mMenuClickListener = paramOnMenuItemClickListener;
  }

  public final boolean collapseActionView()
  {
    return false;
  }

  public final boolean expandActionView()
  {
    return false;
  }

  public final ActionProvider getActionProvider()
  {
    return null;
  }

  public final View getActionView()
  {
    throw new UnsupportedOperationException();
  }

  public final char getAlphabeticShortcut()
  {
    return '\000';
  }

  public final int getGroupId()
  {
    return this.mGroupId;
  }

  public final Drawable getIcon()
  {
    return this.mIcon;
  }

  public final Intent getIntent()
  {
    return this.mIntent;
  }

  public final int getItemId()
  {
    return this.mItemId;
  }

  public final ContextMenu.ContextMenuInfo getMenuInfo()
  {
    return this.mMenuInfo;
  }

  public final char getNumericShortcut()
  {
    return '\000';
  }

  public final int getOrder()
  {
    return this.mOrder;
  }

  public final CharSequence getTitle()
  {
    return this.mTitle;
  }

  public final CharSequence getTitleCondensed()
  {
    if (this.mTitleCondensed != null);
    for (CharSequence localCharSequence = this.mTitleCondensed; ; localCharSequence = this.mTitle)
      return localCharSequence;
  }

  public final boolean hasSubMenu()
  {
    if (this.mSubMenu != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean invoke()
  {
    boolean bool = true;
    if ((this.mMenuItemClickListener != null) && (this.mMenuItemClickListener.onMenuItemClick(this)));
    while (true)
    {
      return bool;
      if ((this.mMenuClickListener == null) || (!this.mMenuClickListener.onMenuItemClick(this)))
        if (this.mIntent != null)
          try
          {
            this.mContext.startActivity(this.mIntent);
          }
          catch (ActivityNotFoundException localActivityNotFoundException)
          {
            bool = false;
          }
        else if (hasSubMenu())
          this.mSubMenu.show();
        else
          bool = false;
    }
  }

  public final boolean isActionViewExpanded()
  {
    return false;
  }

  public final boolean isCheckable()
  {
    return false;
  }

  public final boolean isChecked()
  {
    return false;
  }

  public final boolean isEnabled()
  {
    return this.mEnabled;
  }

  public final boolean isVisible()
  {
    return this.mVisible;
  }

  public final MenuItem setActionProvider(ActionProvider paramActionProvider)
  {
    return null;
  }

  public final MenuItem setActionView(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public final MenuItem setActionView(View paramView)
  {
    throw new UnsupportedOperationException();
  }

  public final MenuItem setAlphabeticShortcut(char paramChar)
  {
    return this;
  }

  public final MenuItem setCheckable(boolean paramBoolean)
  {
    return this;
  }

  public final MenuItem setChecked(boolean paramBoolean)
  {
    return this;
  }

  public final MenuItem setEnabled(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
    return this;
  }

  public final MenuItem setIcon(int paramInt)
  {
    if (paramInt != 0);
    for (this.mIcon = this.mContext.getResources().getDrawable(paramInt); ; this.mIcon = null)
      return this;
  }

  public final MenuItem setIcon(Drawable paramDrawable)
  {
    this.mIcon = paramDrawable;
    return this;
  }

  public final MenuItem setIntent(Intent paramIntent)
  {
    this.mIntent = paramIntent;
    return this;
  }

  public final MenuItem setNumericShortcut(char paramChar)
  {
    return this;
  }

  public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener)
  {
    return null;
  }

  public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mMenuItemClickListener = paramOnMenuItemClickListener;
    return this;
  }

  public final MenuItem setShortcut(char paramChar1, char paramChar2)
  {
    return this;
  }

  public final void setShowAsAction(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public final MenuItem setShowAsActionFlags(int paramInt)
  {
    return null;
  }

  final void setSubMenu(QuickActionsSubMenu paramQuickActionsSubMenu)
  {
    this.mSubMenu = paramQuickActionsSubMenu;
  }

  public final MenuItem setTitle(int paramInt)
  {
    return setTitle(this.mContext.getText(paramInt));
  }

  public final MenuItem setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    return this;
  }

  public final MenuItem setTitleCondensed(CharSequence paramCharSequence)
  {
    this.mTitleCondensed = paramCharSequence;
    return this;
  }

  public final MenuItem setVisible(boolean paramBoolean)
  {
    this.mVisible = paramBoolean;
    return this;
  }

  public final String toString()
  {
    return this.mTitle.toString();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.QuickActionsMenuItem
 * JD-Core Version:    0.6.2
 */