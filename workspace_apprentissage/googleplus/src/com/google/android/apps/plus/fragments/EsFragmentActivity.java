package com.google.android.apps.plus.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class EsFragmentActivity extends InstrumentedActivity
{
  private boolean mHideTitleBar;
  private final MenuItem[] mMenuItems = new MenuItem[3];
  private final View.OnClickListener mTitleClickListener = new TitleClickListener((byte)0);

  private static MenuItem getVisibleItem(Menu paramMenu, int paramInt)
  {
    int i = 0;
    int j = 0;
    if (j < paramMenu.size())
      if (paramMenu.getItem(j).isVisible())
        if (i != paramInt);
    for (MenuItem localMenuItem = paramMenu.getItem(j); ; localMenuItem = null)
    {
      return localMenuItem;
      i++;
      j++;
      break;
    }
  }

  private void setupTitleButton1(MenuItem paramMenuItem)
  {
    ImageButton localImageButton = (ImageButton)findViewById(R.id.title_button_1);
    if (paramMenuItem != null)
    {
      localImageButton.setImageDrawable(paramMenuItem.getIcon());
      localImageButton.setVisibility(0);
      localImageButton.setEnabled(paramMenuItem.isEnabled());
      localImageButton.setOnClickListener(this.mTitleClickListener);
      localImageButton.setContentDescription(paramMenuItem.getTitle());
    }
    while (true)
    {
      this.mMenuItems[0] = paramMenuItem;
      return;
      localImageButton.setVisibility(8);
    }
  }

  private void setupTitleButton2(MenuItem paramMenuItem)
  {
    ImageButton localImageButton = (ImageButton)findViewById(R.id.title_button_2);
    if (paramMenuItem != null)
    {
      localImageButton.setImageDrawable(paramMenuItem.getIcon());
      localImageButton.setVisibility(0);
      localImageButton.setEnabled(paramMenuItem.isEnabled());
      localImageButton.setOnClickListener(this.mTitleClickListener);
      localImageButton.setContentDescription(paramMenuItem.getTitle());
    }
    while (true)
    {
      this.mMenuItems[1] = paramMenuItem;
      return;
      localImageButton.setVisibility(8);
    }
  }

  private void setupTitleButton3(MenuItem paramMenuItem)
  {
    Button localButton = (Button)findViewById(R.id.title_button_3);
    if (paramMenuItem != null)
    {
      localButton.setCompoundDrawablesWithIntrinsicBounds(paramMenuItem.getIcon(), null, null, null);
      localButton.setVisibility(0);
      localButton.setEnabled(paramMenuItem.isEnabled());
      localButton.setOnClickListener(this.mTitleClickListener);
      localButton.setContentDescription(paramMenuItem.getTitle());
      CharSequence localCharSequence = getTitleButton3Text$9aa72f6();
      if (!TextUtils.isEmpty(localCharSequence))
      {
        ViewGroup.LayoutParams localLayoutParams = localButton.getLayoutParams();
        localLayoutParams.width = -2;
        localButton.setLayoutParams(localLayoutParams);
        localButton.setText(localCharSequence);
        localButton.setPadding(10, 0, 10, 0);
      }
    }
    while (true)
    {
      this.mMenuItems[2] = paramMenuItem;
      return;
      localButton.setVisibility(8);
    }
  }

  public final void createTitlebarButtons(int paramInt)
  {
    setupTitleButton1(null);
    setupTitleButton2(null);
    setupTitleButton3(null);
    TitleMenu localTitleMenu = new TitleMenu(this);
    getMenuInflater().inflate(paramInt, localTitleMenu);
    onPrepareTitlebarButtons(localTitleMenu);
    int i = 0;
    for (int j = 0; j < localTitleMenu.size(); j++)
      if (localTitleMenu.getItem(j).isVisible())
        i++;
    switch (i)
    {
    default:
      Log.e("EsFragmentActivity", "Maximum title buttons is 3. You have " + i + " visible menu items");
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      setupTitleButton3(getVisibleItem(localTitleMenu, 0));
      continue;
      setupTitleButton2(getVisibleItem(localTitleMenu, 0));
      setupTitleButton3(getVisibleItem(localTitleMenu, 1));
      continue;
      setupTitleButton1(getVisibleItem(localTitleMenu, 0));
      setupTitleButton2(getVisibleItem(localTitleMenu, 1));
      setupTitleButton3(getVisibleItem(localTitleMenu, 2));
    }
  }

  protected CharSequence getTitleButton3Text$9aa72f6()
  {
    return null;
  }

  protected final void goHome(EsAccount paramEsAccount)
  {
    Intent localIntent1 = getIntent();
    if (localIntent1 != null)
    {
      Bundle localBundle = localIntent1.getExtras();
      if ((localBundle != null) && (localBundle.containsKey("notif_id")) && (localBundle.getString("notif_id") != null))
      {
        Intent localIntent2 = Intents.getHostNavigationActivityIntent(this, paramEsAccount);
        localIntent2.addFlags(67108864);
        startActivity(localIntent2);
        finish();
      }
    }
    while (true)
    {
      return;
      onBackPressed();
    }
  }

  protected final boolean isIntentAccountActive()
  {
    EsAccount localEsAccount = (EsAccount)getIntent().getParcelableExtra("account");
    boolean bool1 = false;
    if (localEsAccount != null)
    {
      if (localEsAccount.equals(EsService.getActiveAccount(this)))
        break label75;
      boolean bool2 = EsLog.isLoggable("EsFragmentActivity", 6);
      bool1 = false;
      if (bool2)
        Log.e("EsFragmentActivity", "Activity finished because it is associated with a signed-out account: " + getClass().getName());
    }
    while (true)
    {
      return bool1;
      label75: bool1 = true;
    }
  }

  protected void onPrepareTitlebarButtons(Menu paramMenu)
  {
  }

  protected void onTitlebarLabelClick()
  {
  }

  protected final void setTitlebarSubtitle(String paramString)
  {
    TextView localTextView = (TextView)findViewById(R.id.titlebar_label_2);
    if (paramString == null)
      localTextView.setVisibility(8);
    while (true)
    {
      return;
      localTextView.setVisibility(0);
      localTextView.setText(paramString);
      localTextView.setClickable(true);
      localTextView.setOnClickListener(this.mTitleClickListener);
    }
  }

  protected final void setTitlebarTitle(String paramString)
  {
    TextView localTextView = (TextView)findViewById(R.id.titlebar_label);
    localTextView.setText(paramString);
    localTextView.setClickable(true);
    localTextView.setOnClickListener(this.mTitleClickListener);
  }

  protected final void showTitlebar(boolean paramBoolean)
  {
    showTitlebar(false, paramBoolean);
  }

  protected void showTitlebar(boolean paramBoolean1, boolean paramBoolean2)
  {
    View localView1 = findViewById(R.id.title_layout);
    if (localView1.getVisibility() == 0)
      return;
    this.mHideTitleBar = false;
    Animation localAnimation = localView1.getAnimation();
    if (localAnimation != null)
      localAnimation.cancel();
    if (paramBoolean1)
      localView1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    View localView2 = localView1.findViewById(R.id.titlebar_up);
    int i;
    label68: View localView3;
    if (paramBoolean2)
    {
      i = 0;
      localView2.setVisibility(i);
      localView3 = localView1.findViewById(R.id.titlebar_icon_layout);
      if (!paramBoolean2)
        break label124;
      localView3.setOnClickListener(this.mTitleClickListener);
      localView3.setContentDescription(getString(R.string.nav_up_content_description));
    }
    while (true)
    {
      localView1.setVisibility(0);
      break;
      i = 8;
      break label68;
      label124: localView3.setBackgroundColor(0);
    }
  }

  private final class TitleClickListener
    implements View.OnClickListener
  {
    private TitleClickListener()
    {
    }

    public final void onClick(View paramView)
    {
      int i = paramView.getId();
      if ((i == R.id.titlebar_icon_layout) || (i == R.id.titlebar_label) || (i == R.id.titlebar_label_2))
        EsFragmentActivity.this.onTitlebarLabelClick();
      while (true)
      {
        return;
        if (i == R.id.title_button_1)
        {
          if (EsFragmentActivity.this.mMenuItems[0] != null)
            EsFragmentActivity.this.onOptionsItemSelected(EsFragmentActivity.this.mMenuItems[0]);
        }
        else if (i == R.id.title_button_2)
        {
          if (EsFragmentActivity.this.mMenuItems[1] != null)
            EsFragmentActivity.this.onOptionsItemSelected(EsFragmentActivity.this.mMenuItems[1]);
        }
        else if ((i == R.id.title_button_3) && (EsFragmentActivity.this.mMenuItems[2] != null))
          EsFragmentActivity.this.onOptionsItemSelected(EsFragmentActivity.this.mMenuItems[2]);
      }
    }
  }

  private static final class TitleMenu
    implements Menu
  {
    private final Context mContext;
    private final ArrayList<EsFragmentActivity.TitleMenuItem> mItems = new ArrayList();

    public TitleMenu(Context paramContext)
    {
      this.mContext = paramContext;
    }

    public final MenuItem add(int paramInt)
    {
      EsFragmentActivity.TitleMenuItem localTitleMenuItem = new EsFragmentActivity.TitleMenuItem(this.mContext, 0, paramInt);
      this.mItems.add(localTitleMenuItem);
      return localTitleMenuItem;
    }

    public final MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      EsFragmentActivity.TitleMenuItem localTitleMenuItem = new EsFragmentActivity.TitleMenuItem(this.mContext, paramInt2, paramInt4);
      this.mItems.add(localTitleMenuItem);
      return localTitleMenuItem;
    }

    public final MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
    {
      EsFragmentActivity.TitleMenuItem localTitleMenuItem = new EsFragmentActivity.TitleMenuItem(this.mContext, paramInt2, paramCharSequence);
      this.mItems.add(localTitleMenuItem);
      return localTitleMenuItem;
    }

    public final MenuItem add(CharSequence paramCharSequence)
    {
      EsFragmentActivity.TitleMenuItem localTitleMenuItem = new EsFragmentActivity.TitleMenuItem(this.mContext, 0, paramCharSequence);
      this.mItems.add(localTitleMenuItem);
      return localTitleMenuItem;
    }

    public final int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
    {
      return 0;
    }

    public final SubMenu addSubMenu(int paramInt)
    {
      return null;
    }

    public final SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return null;
    }

    public final SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
    {
      return null;
    }

    public final SubMenu addSubMenu(CharSequence paramCharSequence)
    {
      return null;
    }

    public final void clear()
    {
      this.mItems.clear();
    }

    public final void close()
    {
    }

    public final MenuItem findItem(int paramInt)
    {
      Iterator localIterator = this.mItems.iterator();
      EsFragmentActivity.TitleMenuItem localTitleMenuItem;
      do
      {
        if (!localIterator.hasNext())
          break;
        localTitleMenuItem = (EsFragmentActivity.TitleMenuItem)localIterator.next();
      }
      while (localTitleMenuItem.getItemId() != paramInt);
      while (true)
      {
        return localTitleMenuItem;
        localTitleMenuItem = null;
      }
    }

    public final MenuItem getItem(int paramInt)
    {
      return (MenuItem)this.mItems.get(paramInt);
    }

    public final boolean hasVisibleItems()
    {
      return false;
    }

    public final boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
    {
      return false;
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
    }

    public final void removeItem(int paramInt)
    {
    }

    public final void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    {
    }

    public final void setGroupEnabled(int paramInt, boolean paramBoolean)
    {
    }

    public final void setGroupVisible(int paramInt, boolean paramBoolean)
    {
    }

    public final void setQwertyMode(boolean paramBoolean)
    {
    }

    public final int size()
    {
      return this.mItems.size();
    }
  }

  private static final class TitleMenuItem
    implements MenuItem
  {
    private int mActionEnum;
    private boolean mEnabled;
    private Drawable mIcon;
    private final int mItemId;
    private final Resources mResources;
    private CharSequence mTitle;
    private boolean mVisible;

    public TitleMenuItem(Context paramContext, int paramInt1, int paramInt2)
    {
      this.mResources = paramContext.getResources();
      this.mTitle = this.mResources.getString(paramInt2);
      this.mItemId = paramInt1;
    }

    public TitleMenuItem(Context paramContext, int paramInt, CharSequence paramCharSequence)
    {
      this.mResources = paramContext.getResources();
      this.mTitle = paramCharSequence;
      this.mItemId = paramInt;
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
      return null;
    }

    public final char getAlphabeticShortcut()
    {
      return '\000';
    }

    public final int getGroupId()
    {
      return 0;
    }

    public final Drawable getIcon()
    {
      return this.mIcon;
    }

    public final Intent getIntent()
    {
      return null;
    }

    public final int getItemId()
    {
      return this.mItemId;
    }

    public final ContextMenu.ContextMenuInfo getMenuInfo()
    {
      return null;
    }

    public final char getNumericShortcut()
    {
      return '\000';
    }

    public final int getOrder()
    {
      return 0;
    }

    public final SubMenu getSubMenu()
    {
      return null;
    }

    public final CharSequence getTitle()
    {
      return this.mTitle;
    }

    public final CharSequence getTitleCondensed()
    {
      return null;
    }

    public final boolean hasSubMenu()
    {
      return false;
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
      return this;
    }

    public final MenuItem setActionView(View paramView)
    {
      return this;
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
      if (paramInt != 0)
        this.mIcon = this.mResources.getDrawable(paramInt);
      return this;
    }

    public final MenuItem setIcon(Drawable paramDrawable)
    {
      this.mIcon = paramDrawable;
      return this;
    }

    public final MenuItem setIntent(Intent paramIntent)
    {
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
      return this;
    }

    public final MenuItem setShortcut(char paramChar1, char paramChar2)
    {
      return this;
    }

    public final void setShowAsAction(int paramInt)
    {
      this.mActionEnum = paramInt;
    }

    public final MenuItem setShowAsActionFlags(int paramInt)
    {
      return null;
    }

    public final MenuItem setTitle(int paramInt)
    {
      this.mTitle = this.mResources.getString(paramInt);
      return this;
    }

    public final MenuItem setTitle(CharSequence paramCharSequence)
    {
      this.mTitle = paramCharSequence;
      return this;
    }

    public final MenuItem setTitleCondensed(CharSequence paramCharSequence)
    {
      return this;
    }

    public final MenuItem setVisible(boolean paramBoolean)
    {
      this.mVisible = paramBoolean;
      return this;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EsFragmentActivity
 * JD-Core Version:    0.6.2
 */