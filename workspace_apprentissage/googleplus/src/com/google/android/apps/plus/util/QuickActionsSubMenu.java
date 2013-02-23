package com.google.android.apps.plus.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class QuickActionsSubMenu
  implements DialogInterface.OnClickListener, SubMenu
{
  private final Context mContext;
  private final ContextMenu.ContextMenuInfo mContextMenuInfo;
  private CharSequence mHeaderTitle;
  private final QuickActionsMenuItem mItem;
  private final List<QuickActionsMenuItem> mItems;
  private final MenuItem.OnMenuItemClickListener mOnMenuItemClickListener;

  public QuickActionsSubMenu(Context paramContext, QuickActionsMenuItem paramQuickActionsMenuItem, ContextMenu.ContextMenuInfo paramContextMenuInfo, MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mContext = paramContext;
    this.mItem = paramQuickActionsMenuItem;
    this.mContextMenuInfo = paramContextMenuInfo;
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
    this.mItems = new ArrayList();
  }

  private static <T extends MenuItem> List<T> visible(List<T> paramList)
  {
    int i = 0;
    Iterator localIterator1 = paramList.iterator();
    while (localIterator1.hasNext())
      if (((MenuItem)localIterator1.next()).isVisible())
        i++;
    if (i == paramList.size());
    while (true)
    {
      return paramList;
      ArrayList localArrayList = new ArrayList(i);
      Iterator localIterator2 = paramList.iterator();
      while (localIterator2.hasNext())
      {
        MenuItem localMenuItem = (MenuItem)localIterator2.next();
        if (localMenuItem.isVisible())
          localArrayList.add(localMenuItem);
      }
      paramList = localArrayList;
    }
  }

  public final MenuItem add(int paramInt)
  {
    return add(0, 0, 0, paramInt);
  }

  public final MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return add(paramInt1, paramInt2, paramInt3, this.mContext.getText(paramInt4));
  }

  public final MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    QuickActionsMenuItem localQuickActionsMenuItem = new QuickActionsMenuItem(this.mContext, paramInt1, paramInt2, paramInt3, paramCharSequence, this.mContextMenuInfo, this.mOnMenuItemClickListener);
    this.mItems.add(localQuickActionsMenuItem);
    return localQuickActionsMenuItem;
  }

  public final MenuItem add(CharSequence paramCharSequence)
  {
    return add(0, 0, 0, paramCharSequence);
  }

  public final int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu addSubMenu(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    throw new UnsupportedOperationException();
  }

  public final void clear()
  {
    this.mItems.clear();
  }

  public final void clearHeader()
  {
    this.mHeaderTitle = null;
  }

  public final void close()
  {
    throw new UnsupportedOperationException();
  }

  public final MenuItem findItem(int paramInt)
  {
    Iterator localIterator = this.mItems.iterator();
    QuickActionsMenuItem localQuickActionsMenuItem;
    do
    {
      if (!localIterator.hasNext())
        break;
      localQuickActionsMenuItem = (QuickActionsMenuItem)localIterator.next();
    }
    while (paramInt != localQuickActionsMenuItem.getItemId());
    while (true)
    {
      return localQuickActionsMenuItem;
      localQuickActionsMenuItem = null;
    }
  }

  public final boolean hasVisibleItems()
  {
    Iterator localIterator = this.mItems.iterator();
    do
      if (!localIterator.hasNext())
        break;
    while (!((QuickActionsMenuItem)localIterator.next()).isVisible());
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }

  public final void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    ((QuickActionsMenuItem)((AlertDialog)paramDialogInterface).getListView().getAdapter().getItem(paramInt)).invoke();
  }

  public final boolean performIdentifierAction(int paramInt1, int paramInt2)
  {
    return false;
  }

  public final boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    return false;
  }

  public final void removeGroup(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public final void removeItem(int paramInt)
  {
    this.mItems.remove(findItem(paramInt));
  }

  public final void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    throw new UnsupportedOperationException();
  }

  public final void setGroupEnabled(int paramInt, boolean paramBoolean)
  {
    throw new UnsupportedOperationException();
  }

  public final void setGroupVisible(int paramInt, boolean paramBoolean)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu setHeaderIcon(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu setHeaderIcon(Drawable paramDrawable)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu setHeaderTitle(int paramInt)
  {
    return setHeaderTitle(this.mContext.getText(paramInt));
  }

  public final SubMenu setHeaderTitle(CharSequence paramCharSequence)
  {
    this.mHeaderTitle = paramCharSequence;
    return this;
  }

  public final SubMenu setHeaderView(View paramView)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu setIcon(int paramInt)
  {
    throw new UnsupportedOperationException();
  }

  public final SubMenu setIcon(Drawable paramDrawable)
  {
    throw new UnsupportedOperationException();
  }

  public final void setQwertyMode(boolean paramBoolean)
  {
    throw new UnsupportedOperationException();
  }

  public final void show()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mContext);
    List localList = visible(this.mItems);
    localBuilder.setAdapter(new ArrayAdapter(this.mContext, 17367043, 16908308, localList), this);
    localBuilder.setIcon(17170445);
    if (this.mHeaderTitle != null)
      localBuilder.setTitle(this.mHeaderTitle);
    AlertDialog localAlertDialog = localBuilder.create();
    localAlertDialog.setCanceledOnTouchOutside(true);
    localAlertDialog.show();
  }

  public final int size()
  {
    return this.mItems.size();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.QuickActionsSubMenu
 * JD-Core Version:    0.6.2
 */