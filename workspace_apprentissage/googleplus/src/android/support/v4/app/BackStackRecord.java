package android.support.v4.app;

import java.io.PrintWriter;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction
  implements Runnable
{
  boolean mAddToBackStack;
  boolean mAllowAddToBackStack = true;
  int mBreadCrumbShortTitleRes;
  CharSequence mBreadCrumbShortTitleText;
  int mBreadCrumbTitleRes;
  CharSequence mBreadCrumbTitleText;
  boolean mCommitted;
  Op mHead;
  int mIndex;
  final FragmentManagerImpl mManager;
  String mName;
  int mNumOp;
  Op mTail;
  int mTransition;
  int mTransitionStyle;

  public BackStackRecord(FragmentManagerImpl paramFragmentManagerImpl)
  {
    this.mManager = paramFragmentManagerImpl;
  }

  private int commitInternal(boolean paramBoolean)
  {
    if (this.mCommitted)
      throw new IllegalStateException("commit already called");
    this.mCommitted = true;
    if (this.mAddToBackStack);
    for (this.mIndex = this.mManager.allocBackStackIndex(this); ; this.mIndex = -1)
    {
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    }
  }

  private void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    paramFragment.mFragmentManager = this.mManager;
    if (paramString != null)
    {
      if ((paramFragment.mTag != null) && (!paramString.equals(paramFragment.mTag)))
        throw new IllegalStateException("Can't change tag of fragment " + paramFragment + ": was " + paramFragment.mTag + " now " + paramString);
      paramFragment.mTag = paramString;
    }
    if (paramInt1 != 0)
    {
      if ((paramFragment.mFragmentId != 0) && (paramFragment.mFragmentId != paramInt1))
        throw new IllegalStateException("Can't change container ID of fragment " + paramFragment + ": was " + paramFragment.mFragmentId + " now " + paramInt1);
      paramFragment.mFragmentId = paramInt1;
      paramFragment.mContainerId = paramInt1;
    }
    Op localOp = new Op();
    localOp.cmd = paramInt2;
    localOp.fragment = paramFragment;
    addOp(localOp);
  }

  public final FragmentTransaction add(int paramInt, Fragment paramFragment)
  {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }

  public final FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString)
  {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }

  public final FragmentTransaction add(Fragment paramFragment, String paramString)
  {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }

  final void addOp(Op paramOp)
  {
    if (this.mHead == null)
    {
      this.mTail = paramOp;
      this.mHead = paramOp;
    }
    while (true)
    {
      paramOp.enterAnim = 0;
      paramOp.exitAnim = 0;
      paramOp.popEnterAnim = 0;
      paramOp.popExitAnim = 0;
      this.mNumOp = (1 + this.mNumOp);
      return;
      paramOp.prev = this.mTail;
      this.mTail.next = paramOp;
      this.mTail = paramOp;
    }
  }

  public final FragmentTransaction addToBackStack(String paramString)
  {
    if (!this.mAllowAddToBackStack)
      throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    this.mAddToBackStack = true;
    this.mName = paramString;
    return this;
  }

  public final FragmentTransaction attach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 7;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  final void bumpBackStackNesting(int paramInt)
  {
    if (!this.mAddToBackStack);
    while (true)
    {
      return;
      for (Op localOp = this.mHead; localOp != null; localOp = localOp.next)
      {
        if (localOp.fragment != null)
        {
          Fragment localFragment2 = localOp.fragment;
          localFragment2.mBackStackNesting = (paramInt + localFragment2.mBackStackNesting);
        }
        if (localOp.removed != null)
          for (int i = -1 + localOp.removed.size(); i >= 0; i--)
          {
            Fragment localFragment1 = (Fragment)localOp.removed.get(i);
            localFragment1.mBackStackNesting = (paramInt + localFragment1.mBackStackNesting);
          }
      }
    }
  }

  public final int commit()
  {
    return commitInternal(false);
  }

  public final int commitAllowingStateLoss()
  {
    return commitInternal(true);
  }

  public final FragmentTransaction detach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 6;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public final void dump$ec96877(String paramString, PrintWriter paramPrintWriter)
  {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mName=");
    paramPrintWriter.print(this.mName);
    paramPrintWriter.print(" mIndex=");
    paramPrintWriter.print(this.mIndex);
    paramPrintWriter.print(" mCommitted=");
    paramPrintWriter.println(this.mCommitted);
    if (this.mTransition != 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTransition=#");
      paramPrintWriter.print(Integer.toHexString(this.mTransition));
      paramPrintWriter.print(" mTransitionStyle=#");
      paramPrintWriter.println(Integer.toHexString(this.mTransitionStyle));
    }
    if ((this.mBreadCrumbTitleRes != 0) || (this.mBreadCrumbTitleText != null))
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mBreadCrumbTitleRes=#");
      paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
      paramPrintWriter.print(" mBreadCrumbTitleText=");
      paramPrintWriter.println(this.mBreadCrumbTitleText);
    }
    if ((this.mBreadCrumbShortTitleRes != 0) || (this.mBreadCrumbShortTitleText != null))
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
      paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
      paramPrintWriter.print(" mBreadCrumbShortTitleText=");
      paramPrintWriter.println(this.mBreadCrumbShortTitleText);
    }
    if (this.mHead != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      String str = paramString + "    ";
      for (Op localOp = this.mHead; localOp != null; localOp = localOp.next)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  Op #");
        paramPrintWriter.print(0);
        paramPrintWriter.println(":");
        paramPrintWriter.print(str);
        paramPrintWriter.print("cmd=");
        paramPrintWriter.print(localOp.cmd);
        paramPrintWriter.print(" fragment=");
        paramPrintWriter.println(localOp.fragment);
        if ((localOp.enterAnim != 0) || (localOp.exitAnim != 0))
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("enterAnim=#");
          paramPrintWriter.print(Integer.toHexString(localOp.enterAnim));
          paramPrintWriter.print(" exitAnim=#");
          paramPrintWriter.println(Integer.toHexString(localOp.exitAnim));
        }
        if ((localOp.popEnterAnim != 0) || (localOp.popExitAnim != 0))
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("popEnterAnim=#");
          paramPrintWriter.print(Integer.toHexString(localOp.popEnterAnim));
          paramPrintWriter.print(" popExitAnim=#");
          paramPrintWriter.println(Integer.toHexString(localOp.popExitAnim));
        }
        if ((localOp.removed != null) && (localOp.removed.size() > 0))
        {
          int i = 0;
          if (i < localOp.removed.size())
          {
            paramPrintWriter.print(str);
            if (localOp.removed.size() == 1)
              paramPrintWriter.print("Removed: ");
            while (true)
            {
              paramPrintWriter.println(localOp.removed.get(i));
              i++;
              break;
              paramPrintWriter.println("Removed:");
              paramPrintWriter.print(str);
              paramPrintWriter.print("  #");
              paramPrintWriter.print(0);
              paramPrintWriter.print(": ");
            }
          }
        }
      }
    }
  }

  public final void popFromBackStack(boolean paramBoolean)
  {
    bumpBackStackNesting(-1);
    Op localOp = this.mTail;
    if (localOp != null)
    {
      switch (localOp.cmd)
      {
      default:
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
      case 1:
        Fragment localFragment8 = localOp.fragment;
        localFragment8.mNextAnim = localOp.popExitAnim;
        this.mManager.removeFragment(localFragment8, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      }
      while (true)
      {
        localOp = localOp.prev;
        break;
        Fragment localFragment6 = localOp.fragment;
        if (localFragment6 != null)
        {
          localFragment6.mNextAnim = localOp.popExitAnim;
          this.mManager.removeFragment(localFragment6, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
        }
        if (localOp.removed != null)
        {
          for (int i = 0; i < localOp.removed.size(); i++)
          {
            Fragment localFragment7 = (Fragment)localOp.removed.get(i);
            localFragment7.mNextAnim = localOp.popEnterAnim;
            this.mManager.addFragment(localFragment7, false);
          }
          Fragment localFragment5 = localOp.fragment;
          localFragment5.mNextAnim = localOp.popEnterAnim;
          this.mManager.addFragment(localFragment5, false);
          continue;
          Fragment localFragment4 = localOp.fragment;
          localFragment4.mNextAnim = localOp.popEnterAnim;
          this.mManager.showFragment(localFragment4, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
          continue;
          Fragment localFragment3 = localOp.fragment;
          localFragment3.mNextAnim = localOp.popExitAnim;
          this.mManager.hideFragment(localFragment3, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
          continue;
          Fragment localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.popEnterAnim;
          this.mManager.attachFragment(localFragment2, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
          continue;
          Fragment localFragment1 = localOp.fragment;
          localFragment1.mNextAnim = localOp.popEnterAnim;
          this.mManager.detachFragment(localFragment1, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
        }
      }
    }
    if (paramBoolean)
      this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle, true);
    if (this.mIndex >= 0)
    {
      this.mManager.freeBackStackIndex(this.mIndex);
      this.mIndex = -1;
    }
  }

  public final FragmentTransaction remove(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 3;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public final FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString)
  {
    if (paramInt == 0)
      throw new IllegalArgumentException("Must use non-zero containerViewId");
    doAddOp(paramInt, paramFragment, paramString, 2);
    return this;
  }

  public final void run()
  {
    if ((this.mAddToBackStack) && (this.mIndex < 0))
      throw new IllegalStateException("addToBackStack() called after commit()");
    bumpBackStackNesting(1);
    Op localOp = this.mHead;
    if (localOp != null)
    {
      switch (localOp.cmd)
      {
      default:
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
      case 1:
        Fragment localFragment8 = localOp.fragment;
        localFragment8.mNextAnim = localOp.enterAnim;
        this.mManager.addFragment(localFragment8, false);
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      }
      while (true)
      {
        localOp = localOp.next;
        break;
        Fragment localFragment6 = localOp.fragment;
        if (this.mManager.mAdded != null)
        {
          int i = 0;
          if (i < this.mManager.mAdded.size())
          {
            Fragment localFragment7 = (Fragment)this.mManager.mAdded.get(i);
            if ((localFragment6 == null) || (localFragment7.mContainerId == localFragment6.mContainerId))
            {
              if (localFragment7 != localFragment6)
                break label235;
              localFragment6 = null;
              localOp.fragment = null;
            }
            while (true)
            {
              i++;
              break;
              label235: if (localOp.removed == null)
                localOp.removed = new ArrayList();
              localOp.removed.add(localFragment7);
              localFragment7.mNextAnim = localOp.exitAnim;
              if (this.mAddToBackStack)
                localFragment7.mBackStackNesting = (1 + localFragment7.mBackStackNesting);
              this.mManager.removeFragment(localFragment7, this.mTransition, this.mTransitionStyle);
            }
          }
        }
        if (localFragment6 != null)
        {
          localFragment6.mNextAnim = localOp.enterAnim;
          this.mManager.addFragment(localFragment6, false);
          continue;
          Fragment localFragment5 = localOp.fragment;
          localFragment5.mNextAnim = localOp.exitAnim;
          this.mManager.removeFragment(localFragment5, this.mTransition, this.mTransitionStyle);
          continue;
          Fragment localFragment4 = localOp.fragment;
          localFragment4.mNextAnim = localOp.exitAnim;
          this.mManager.hideFragment(localFragment4, this.mTransition, this.mTransitionStyle);
          continue;
          Fragment localFragment3 = localOp.fragment;
          localFragment3.mNextAnim = localOp.enterAnim;
          this.mManager.showFragment(localFragment3, this.mTransition, this.mTransitionStyle);
          continue;
          Fragment localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.exitAnim;
          this.mManager.detachFragment(localFragment2, this.mTransition, this.mTransitionStyle);
          continue;
          Fragment localFragment1 = localOp.fragment;
          localFragment1.mNextAnim = localOp.enterAnim;
          this.mManager.attachFragment(localFragment1, this.mTransition, this.mTransitionStyle);
        }
      }
    }
    this.mManager.moveToState(this.mManager.mCurState, this.mTransition, this.mTransitionStyle, true);
    if (this.mAddToBackStack)
      this.mManager.addBackStackState(this);
  }

  public final FragmentTransaction setTransition(int paramInt)
  {
    this.mTransition = paramInt;
    return this;
  }

  static final class Op
  {
    int cmd;
    int enterAnim;
    int exitAnim;
    Fragment fragment;
    Op next;
    int popEnterAnim;
    int popExitAnim;
    Op prev;
    ArrayList<Fragment> removed;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.BackStackRecord
 * JD-Core Version:    0.6.2
 */