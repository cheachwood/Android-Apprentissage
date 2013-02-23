package android.support.v4.app;

public abstract class FragmentTransaction
{
  public abstract FragmentTransaction add(int paramInt, Fragment paramFragment);

  public abstract FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString);

  public abstract FragmentTransaction add(Fragment paramFragment, String paramString);

  public abstract FragmentTransaction addToBackStack(String paramString);

  public abstract FragmentTransaction attach(Fragment paramFragment);

  public abstract int commit();

  public abstract int commitAllowingStateLoss();

  public abstract FragmentTransaction detach(Fragment paramFragment);

  public abstract FragmentTransaction remove(Fragment paramFragment);

  public abstract FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString);

  public abstract FragmentTransaction setTransition(int paramInt);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentTransaction
 * JD-Core Version:    0.6.2
 */