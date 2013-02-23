package com.google.android.apps.plus.phone;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public abstract class EsFragmentPagerAdapter extends PagerAdapter
{
  private FragmentTransaction mCurTransaction = null;
  private Fragment mCurrentPrimaryItem = null;
  private LruCache<String, Fragment> mFragmentCache = new FragmentCache(5);
  private final FragmentManager mFragmentManager;
  private OnFragmentPagerListener mPagerListener;

  public EsFragmentPagerAdapter(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }

  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    Fragment localFragment = (Fragment)paramObject;
    String str = localFragment.getTag();
    if (str == null)
      str = makeFragmentName(paramView.getId(), paramInt);
    this.mFragmentCache.put(str, localFragment);
    this.mCurTransaction.detach(localFragment);
  }

  public final void finishUpdate$3c7ec8c3()
  {
    if (this.mCurTransaction != null)
    {
      this.mCurTransaction.commitAllowingStateLoss();
      this.mCurTransaction = null;
      this.mFragmentManager.executePendingTransactions();
    }
  }

  public abstract Fragment getItem(int paramInt);

  public Object instantiateItem(View paramView, int paramInt)
  {
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    String str = makeFragmentName(paramView.getId(), paramInt);
    this.mFragmentCache.remove(str);
    Fragment localFragment = this.mFragmentManager.findFragmentByTag(str);
    if (localFragment != null)
      this.mCurTransaction.attach(localFragment);
    while (true)
    {
      if (localFragment != this.mCurrentPrimaryItem)
        localFragment.setMenuVisibility(false);
      return localFragment;
      localFragment = getItem(paramInt);
      this.mCurTransaction.add(paramView.getId(), localFragment, str);
    }
  }

  public final boolean isViewFromObject(View paramView, Object paramObject)
  {
    View localView = ((Fragment)paramObject).getView();
    Object localObject = paramView;
    if ((localObject instanceof View))
      if (localObject != localView);
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      localObject = ((View)localObject).getParent();
      break;
    }
  }

  protected String makeFragmentName(int paramInt1, int paramInt2)
  {
    return "android:switcher:" + paramInt1 + ":" + paramInt2;
  }

  public final void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
  }

  public final Parcelable saveState()
  {
    return null;
  }

  public final void setFragmentPagerListener(OnFragmentPagerListener paramOnFragmentPagerListener)
  {
    this.mPagerListener = paramOnFragmentPagerListener;
  }

  public final void setPrimaryItem$7e55ba3e(Object paramObject)
  {
    Fragment localFragment = (Fragment)paramObject;
    if (localFragment != this.mCurrentPrimaryItem)
    {
      if (this.mCurrentPrimaryItem != null)
        this.mCurrentPrimaryItem.setMenuVisibility(false);
      if (localFragment != null)
        localFragment.setMenuVisibility(true);
      this.mCurrentPrimaryItem = localFragment;
    }
    if (this.mPagerListener != null)
      this.mPagerListener.onPageActivated(localFragment);
  }

  private final class FragmentCache extends LruCache<String, Fragment>
  {
    public FragmentCache(int arg2)
    {
      super();
    }
  }

  public static abstract interface OnFragmentPagerListener
  {
    public abstract void onPageActivated(Fragment paramFragment);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsFragmentPagerAdapter
 * JD-Core Version:    0.6.2
 */