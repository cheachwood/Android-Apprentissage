package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EditCommentFragment;
import com.google.android.apps.plus.fragments.EsFragmentActivity;

public class EditCommentActivity extends EsFragmentActivity
  implements DialogInterface.OnClickListener
{
  protected EditCommentFragment mEditCommentFragment;

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.COMMENT;
  }

  public final void invalidateMenu()
  {
    createTitlebarButtons(R.menu.edit_comment_menu);
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof EditCommentFragment))
      this.mEditCommentFragment = ((EditCommentFragment)paramFragment);
  }

  public void onBackPressed()
  {
    this.mEditCommentFragment.onDiscard();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    }
    while (true)
    {
      paramDialogInterface.dismiss();
      return;
      setResult(0);
      finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.edit_comment_activity);
    showTitlebar(true);
    setTitlebarTitle(getString(R.string.edit_comment));
    createTitlebarButtons(R.menu.edit_comment_menu);
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 901234:
    case 207893:
    }
    while (true)
    {
      return localObject;
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setMessage(R.string.edit_comment_cancel_prompt);
      localBuilder.setPositiveButton(R.string.yes, this);
      localBuilder.setNegativeButton(R.string.no, this);
      localBuilder.setCancelable(true);
      localObject = localBuilder.create();
      continue;
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setProgressStyle(0);
      ((ProgressDialog)localObject).setMessage(getString(R.string.post_operation_pending));
      ((ProgressDialog)localObject).setCancelable(false);
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.edit_comment_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    int i = paramMenuItem.getItemId();
    if (i == 16908332)
      this.mEditCommentFragment.onDiscard();
    while (true)
    {
      return bool;
      if (i == R.id.menu_post)
        this.mEditCommentFragment.onPost();
      else if (i == R.id.menu_discard)
        this.mEditCommentFragment.onDiscard();
      else
        bool = false;
    }
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.findItem(R.id.menu_post).setVisible(false);
    return true;
  }

  protected final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    int i = 0;
    if (i < paramMenu.size())
    {
      MenuItem localMenuItem = paramMenu.getItem(i);
      if (localMenuItem.getItemId() == R.id.menu_post);
      for (boolean bool = true; ; bool = false)
      {
        localMenuItem.setVisible(bool);
        i++;
        break;
      }
    }
  }

  protected final void onTitlebarLabelClick()
  {
    if (this.mEditCommentFragment != null)
      this.mEditCommentFragment.onDiscard();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EditCommentActivity
 * JD-Core Version:    0.6.2
 */