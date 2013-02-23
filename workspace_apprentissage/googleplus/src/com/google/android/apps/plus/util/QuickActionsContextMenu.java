package com.google.android.apps.plus.util;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.style;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class QuickActionsContextMenu extends Dialog
  implements ContextMenu
{
  private final ContextMenu.ContextMenuInfo mContextMenuInfo;
  private final List<QuickActionsMenuItem> mItems;
  private boolean mLeftAligned;
  private final MenuItem.OnMenuItemClickListener mOnMenuItemClickListener;
  private boolean mShowAbove;
  private boolean mVertical;

  QuickActionsContextMenu(Context paramContext, ContextMenu.ContextMenuInfo paramContextMenuInfo, MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    super(paramContext, R.style.QuickActions);
    this.mLeftAligned = paramBoolean1;
    this.mVertical = paramBoolean3;
    this.mShowAbove = paramBoolean2;
    this.mItems = new ArrayList();
    this.mContextMenuInfo = paramContextMenuInfo;
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
    Window localWindow = getWindow();
    localWindow.clearFlags(2);
    localWindow.setLayout(-1, -2);
    int i;
    if (paramBoolean1)
      if (paramBoolean2)
        i = R.drawable.tooltip_top_left_background;
    while (true)
    {
      localWindow.setBackgroundDrawableResource(i);
      setCanceledOnTouchOutside(true);
      return;
      i = R.drawable.tooltip_bottom_left_background;
      continue;
      if (paramBoolean2)
        i = R.drawable.tooltip_top_right_background;
      else
        i = R.drawable.tooltip_bottom_right_background;
    }
  }

  private QuickActionsMenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    QuickActionsMenuItem localQuickActionsMenuItem = new QuickActionsMenuItem(getContext(), paramInt1, paramInt2, paramInt3, paramCharSequence, this.mContextMenuInfo, this.mOnMenuItemClickListener);
    this.mItems.add(localQuickActionsMenuItem);
    return localQuickActionsMenuItem;
  }

  public final MenuItem add(int paramInt)
  {
    return add(0, 0, 0, paramInt);
  }

  public final MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return add(paramInt1, paramInt2, paramInt3, getContext().getText(paramInt4));
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
    return addSubMenu(0, 0, 0, paramInt);
  }

  public final SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return addSubMenu(paramInt1, paramInt2, paramInt3, getContext().getText(paramInt4));
  }

  public final SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    Context localContext = getContext();
    QuickActionsMenuItem localQuickActionsMenuItem = add(paramInt1, paramInt2, paramInt3, paramCharSequence);
    QuickActionsSubMenu localQuickActionsSubMenu = new QuickActionsSubMenu(localContext, localQuickActionsMenuItem, this.mContextMenuInfo, this.mOnMenuItemClickListener);
    localQuickActionsMenuItem.setSubMenu(localQuickActionsSubMenu);
    return localQuickActionsSubMenu;
  }

  public final SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    return addSubMenu(0, 0, 0, paramCharSequence);
  }

  public final void clear()
  {
    this.mItems.clear();
  }

  public final void clearHeader()
  {
  }

  public final void close()
  {
    dismiss();
  }

  public final MenuItem findItem(int paramInt)
  {
    Iterator localIterator = this.mItems.iterator();
    Object localObject;
    if (localIterator.hasNext())
    {
      localObject = (QuickActionsMenuItem)localIterator.next();
      if (paramInt != ((MenuItem)localObject).getItemId());
    }
    while (true)
    {
      return localObject;
      if (!((MenuItem)localObject).hasSubMenu())
        break;
      MenuItem localMenuItem = ((MenuItem)localObject).getSubMenu().findItem(paramInt);
      if (localMenuItem == null)
        break;
      localObject = localMenuItem;
      continue;
      localObject = null;
    }
  }

  public final MenuItem getItem(int paramInt)
  {
    return (MenuItem)this.mItems.get(paramInt);
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

  protected final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    int i;
    int j;
    label32: ViewGroup localViewGroup;
    label68: final QuickActionsMenuItem localQuickActionsMenuItem;
    int k;
    label118: Button localButton;
    CharSequence localCharSequence;
    Drawable localDrawable;
    if (this.mVertical)
    {
      i = R.layout.quick_actions_dialog_vertical;
      setContentView(i);
      if (!this.mVertical)
        break label253;
      j = R.layout.quick_actions_item_vertical;
      Context localContext = getContext();
      localViewGroup = (ViewGroup)findViewById(R.id.quick_actions_buttons);
      LayoutInflater localLayoutInflater = LayoutInflater.from(localContext);
      Iterator localIterator = this.mItems.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        localQuickActionsMenuItem = (QuickActionsMenuItem)localIterator.next();
      }
      while (!localQuickActionsMenuItem.isVisible());
      if (localViewGroup.getChildCount() != 0)
      {
        if (!this.mVertical)
          break label260;
        k = R.layout.quick_actions_divider_horizontal;
        localLayoutInflater.inflate(k, localViewGroup, true);
      }
      localButton = (Button)localLayoutInflater.inflate(j, localViewGroup, false);
      localCharSequence = localQuickActionsMenuItem.getTitle();
      localDrawable = localQuickActionsMenuItem.getIcon();
      if (localDrawable == null)
        break label268;
      localDrawable.setBounds(0, 0, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight());
      localButton.setText(" " + localCharSequence);
    }
    while (true)
    {
      localButton.setCompoundDrawables(localDrawable, null, null, null);
      localButton.setEnabled(localQuickActionsMenuItem.isEnabled());
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          localQuickActionsMenuItem.invoke();
          QuickActionsContextMenu.this.dismiss();
        }
      });
      localViewGroup.addView(localButton);
      break label68;
      i = R.layout.quick_actions_dialog;
      break;
      label253: j = R.layout.quick_actions_item;
      break label32;
      label260: k = R.layout.quick_actions_divider_vertical;
      break label118;
      label268: localButton.setText(localCharSequence);
    }
  }

  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if (super.onTouchEvent(paramMotionEvent));
    while (true)
    {
      return bool;
      if (paramMotionEvent.getAction() == 0)
        dismiss();
      else
        bool = false;
    }
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

  public final ContextMenu setHeaderIcon(int paramInt)
  {
    return this;
  }

  public final ContextMenu setHeaderIcon(Drawable paramDrawable)
  {
    return this;
  }

  public final ContextMenu setHeaderTitle(int paramInt)
  {
    return this;
  }

  public final ContextMenu setHeaderTitle(CharSequence paramCharSequence)
  {
    return this;
  }

  public final ContextMenu setHeaderView(View paramView)
  {
    return this;
  }

  public final void setQwertyMode(boolean paramBoolean)
  {
  }

  final void showAnchoredAt(int paramInt1, int paramInt2)
  {
    Window localWindow = getWindow();
    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
    int i;
    int j;
    if (this.mShowAbove)
    {
      i = 80;
      localLayoutParams.gravity = i;
      localLayoutParams.y = paramInt2;
      j = localLayoutParams.gravity;
      if (!this.mLeftAligned)
        break label86;
    }
    label86: for (int k = 3; ; k = 5)
    {
      localLayoutParams.gravity = (k | j);
      localLayoutParams.x = paramInt1;
      localWindow.setAttributes(localLayoutParams);
      show();
      return;
      i = 48;
      break;
    }
  }

  public final int size()
  {
    return this.mItems.size();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.QuickActionsContextMenu
 * JD-Core Version:    0.6.2
 */