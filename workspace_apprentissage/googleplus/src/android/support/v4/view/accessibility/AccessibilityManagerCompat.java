package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;

public final class AccessibilityManagerCompat
{
  private static final AccessibilityManagerVersionImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 14);
    for (IMPL = new AccessibilityManagerIcsImpl(); ; IMPL = new AccessibilityManagerStubImpl())
      return;
  }

  public static boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager)
  {
    return IMPL.isTouchExplorationEnabled(paramAccessibilityManager);
  }

  static final class AccessibilityManagerIcsImpl extends AccessibilityManagerCompat.AccessibilityManagerStubImpl
  {
    public final boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager)
    {
      return paramAccessibilityManager.isTouchExplorationEnabled();
    }
  }

  static class AccessibilityManagerStubImpl
    implements AccessibilityManagerCompat.AccessibilityManagerVersionImpl
  {
    public boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager)
    {
      return false;
    }
  }

  static abstract interface AccessibilityManagerVersionImpl
  {
    public abstract boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityManagerCompat
 * JD-Core Version:    0.6.2
 */