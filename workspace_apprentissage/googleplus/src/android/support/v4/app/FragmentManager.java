package android.support.v4.app;

import android.os.Bundle;

public abstract class FragmentManager
{
  public abstract FragmentTransaction beginTransaction();

  public abstract boolean executePendingTransactions();

  public abstract Fragment findFragmentById(int paramInt);

  public abstract Fragment findFragmentByTag(String paramString);

  public abstract int getBackStackEntryCount();

  public abstract Fragment getFragment(Bundle paramBundle, String paramString);

  public abstract void popBackStack();

  public abstract void popBackStack(int paramInt1, int paramInt2);

  public abstract boolean popBackStackImmediate();

  public abstract void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment);

  public abstract Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentManager
 * JD-Core Version:    0.6.2
 */