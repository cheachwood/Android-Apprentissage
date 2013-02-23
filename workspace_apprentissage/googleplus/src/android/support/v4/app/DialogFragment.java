package android.support.v4.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class DialogFragment extends Fragment
  implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener
{
  int mBackStackId = -1;
  boolean mCancelable = true;
  Dialog mDialog;
  boolean mDismissed;
  boolean mShownByMe;
  boolean mShowsDialog = true;
  int mStyle = 0;
  int mTheme = 0;
  boolean mViewDestroyed;

  private void dismissInternal(boolean paramBoolean)
  {
    if (this.mDismissed);
    while (true)
    {
      return;
      this.mDismissed = true;
      this.mShownByMe = false;
      if (this.mDialog != null)
      {
        this.mDialog.dismiss();
        this.mDialog = null;
      }
      this.mViewDestroyed = true;
      if (this.mBackStackId >= 0)
      {
        this.mFragmentManager.popBackStack(this.mBackStackId, 1);
        this.mBackStackId = -1;
      }
      else
      {
        FragmentTransaction localFragmentTransaction = this.mFragmentManager.beginTransaction();
        localFragmentTransaction.remove(this);
        if (paramBoolean)
          localFragmentTransaction.commitAllowingStateLoss();
        else
          localFragmentTransaction.commit();
      }
    }
  }

  public final void dismiss()
  {
    dismissInternal(false);
  }

  public final void dismissAllowingStateLoss()
  {
    dismissInternal(true);
  }

  public final Dialog getDialog()
  {
    return this.mDialog;
  }

  public final LayoutInflater getLayoutInflater(Bundle paramBundle)
  {
    LayoutInflater localLayoutInflater;
    if (!this.mShowsDialog)
      localLayoutInflater = super.getLayoutInflater(paramBundle);
    while (true)
    {
      return localLayoutInflater;
      this.mDialog = onCreateDialog(paramBundle);
      switch (this.mStyle)
      {
      default:
      case 3:
      case 1:
      case 2:
      }
      while (true)
      {
        if (this.mDialog == null)
          break label106;
        localLayoutInflater = (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
        break;
        this.mDialog.getWindow().addFlags(24);
        this.mDialog.requestWindowFeature(1);
      }
      label106: localLayoutInflater = (LayoutInflater)this.mActivity.getSystemService("layout_inflater");
    }
  }

  public final int getTheme()
  {
    return this.mTheme;
  }

  public final boolean isCancelable()
  {
    return this.mCancelable;
  }

  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!this.mShowsDialog);
    while (true)
    {
      return;
      View localView = this.mView;
      if (localView != null)
      {
        if (localView.getParent() != null)
          throw new IllegalStateException("DialogFragment can not be attached to a container view");
        this.mDialog.setContentView(localView);
      }
      this.mDialog.setOwnerActivity(this.mActivity);
      this.mDialog.setCancelable(this.mCancelable);
      this.mDialog.setOnCancelListener(this);
      this.mDialog.setOnDismissListener(this);
      if (paramBundle != null)
      {
        Bundle localBundle = paramBundle.getBundle("android:savedDialogState");
        if (localBundle != null)
          this.mDialog.onRestoreInstanceState(localBundle);
      }
    }
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!this.mShownByMe)
      this.mDismissed = false;
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.mContainerId == 0);
    for (boolean bool = true; ; bool = false)
    {
      this.mShowsDialog = bool;
      if (paramBundle != null)
      {
        this.mStyle = paramBundle.getInt("android:style", 0);
        this.mTheme = paramBundle.getInt("android:theme", 0);
        this.mCancelable = paramBundle.getBoolean("android:cancelable", true);
        this.mShowsDialog = paramBundle.getBoolean("android:showsDialog", this.mShowsDialog);
        this.mBackStackId = paramBundle.getInt("android:backStackId", -1);
      }
      return;
    }
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new Dialog(this.mActivity, this.mTheme);
  }

  public final void onDestroyView()
  {
    super.onDestroyView();
    if (this.mDialog != null)
    {
      this.mViewDestroyed = true;
      this.mDialog.dismiss();
      this.mDialog = null;
    }
  }

  public final void onDetach()
  {
    super.onDetach();
    if ((!this.mShownByMe) && (!this.mDismissed))
      this.mDismissed = true;
  }

  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (!this.mViewDestroyed)
      dismissInternal(true);
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mDialog != null)
    {
      Bundle localBundle = this.mDialog.onSaveInstanceState();
      if (localBundle != null)
        paramBundle.putBundle("android:savedDialogState", localBundle);
    }
    if (this.mStyle != 0)
      paramBundle.putInt("android:style", this.mStyle);
    if (this.mTheme != 0)
      paramBundle.putInt("android:theme", this.mTheme);
    if (!this.mCancelable)
      paramBundle.putBoolean("android:cancelable", this.mCancelable);
    if (!this.mShowsDialog)
      paramBundle.putBoolean("android:showsDialog", this.mShowsDialog);
    if (this.mBackStackId != -1)
      paramBundle.putInt("android:backStackId", this.mBackStackId);
  }

  public void onStart()
  {
    super.onStart();
    if (this.mDialog != null)
    {
      this.mViewDestroyed = false;
      this.mDialog.show();
    }
  }

  public final void onStop()
  {
    super.onStop();
    if (this.mDialog != null)
      this.mDialog.hide();
  }

  public final void setCancelable(boolean paramBoolean)
  {
    this.mCancelable = paramBoolean;
    if (this.mDialog != null)
      this.mDialog.setCancelable(paramBoolean);
  }

  public final void setStyle(int paramInt1, int paramInt2)
  {
    this.mStyle = 1;
    if ((this.mStyle == 2) || (this.mStyle == 3))
      this.mTheme = 16973913;
    if (paramInt2 != 0)
      this.mTheme = paramInt2;
  }

  public void show(FragmentManager paramFragmentManager, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(this, paramString);
    localFragmentTransaction.commit();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.DialogFragment
 * JD-Core Version:    0.6.2
 */