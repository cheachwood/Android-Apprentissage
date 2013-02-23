package com.google.android.apps.plus.util;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.WindowManager;

public final class QuickActions
{
  public static Dialog show(View paramView1, View paramView2, ContextMenu.ContextMenuInfo paramContextMenuInfo, View.OnCreateContextMenuListener paramOnCreateContextMenuListener, MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramOnCreateContextMenuListener == null)
      throw new NullPointerException();
    int[] arrayOfInt1 = new int[2];
    paramView1.getLocationOnScreen(arrayOfInt1);
    int i = arrayOfInt1[0];
    int j = ((WindowManager)paramView1.getContext().getSystemService("window")).getDefaultDisplay().getWidth();
    boolean bool;
    if (i < j / 2)
    {
      bool = true;
      if (!bool)
        break label244;
    }
    label244: for (int k = i; ; k = j - (i + paramView1.getWidth()))
    {
      int[] arrayOfInt2 = new int[2];
      paramView1.getLocationOnScreen(arrayOfInt2);
      int m = arrayOfInt2[1];
      if (paramView2 != null)
      {
        int[] arrayOfInt3 = new int[2];
        paramView2.getLocationOnScreen(arrayOfInt3);
        m = Math.max(arrayOfInt3[1] + paramView2.getHeight() / 2, arrayOfInt2[1] + paramView1.getHeight() / 2);
      }
      if (paramBoolean2)
        m = ((WindowManager)paramView1.getContext().getSystemService("window")).getDefaultDisplay().getHeight() - m;
      QuickActionsContextMenu localQuickActionsContextMenu = new QuickActionsContextMenu(paramView1.getContext(), paramContextMenuInfo, paramOnMenuItemClickListener, bool, paramBoolean2, paramBoolean1);
      paramOnCreateContextMenuListener.onCreateContextMenu(localQuickActionsContextMenu, paramView1, paramContextMenuInfo);
      int n = (int)(0.5F + -6.0F * paramView1.getContext().getResources().getDisplayMetrics().density);
      if (paramBoolean2)
        n = 0;
      localQuickActionsContextMenu.showAnchoredAt(k, m + n);
      return localQuickActionsContextMenu;
      bool = false;
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.QuickActions
 * JD-Core Version:    0.6.2
 */