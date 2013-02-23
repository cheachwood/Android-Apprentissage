package android.support.v4.view;

import android.animation.ValueAnimator;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

public final class ViewCompat
{
  static final ViewCompatImpl IMPL;

  static
  {
    int i = Build.VERSION.SDK_INT;
    if ((i >= 16) || (Build.VERSION.CODENAME.equals("JellyBean")))
      IMPL = new JBViewCompatImpl();
    while (true)
    {
      return;
      if (i >= 14)
        IMPL = new ICSViewCompatImpl();
      else if (i >= 11)
        IMPL = new HCViewCompatImpl();
      else if (i >= 9)
        IMPL = new GBViewCompatImpl();
      else
        IMPL = new BaseViewCompatImpl();
    }
  }

  public static boolean canScrollHorizontally(View paramView, int paramInt)
  {
    return IMPL.canScrollHorizontally(paramView, paramInt);
  }

  public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView)
  {
    return IMPL.getAccessibilityNodeProvider(paramView);
  }

  public static int getImportantForAccessibility(View paramView)
  {
    return IMPL.getImportantForAccessibility(paramView);
  }

  public static int getOverScrollMode(View paramView)
  {
    return IMPL.getOverScrollMode(paramView);
  }

  public static boolean hasTransientState(View paramView)
  {
    return IMPL.hasTransientState(paramView);
  }

  public static void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    IMPL.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
  }

  public static void postInvalidateOnAnimation(View paramView)
  {
    IMPL.postInvalidateOnAnimation(paramView);
  }

  public static void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
  {
    IMPL.setAccessibilityDelegate(paramView, paramAccessibilityDelegateCompat);
  }

  public static void setImportantForAccessibility(View paramView, int paramInt)
  {
    IMPL.setImportantForAccessibility(paramView, paramInt);
  }

  static class BaseViewCompatImpl
    implements ViewCompat.ViewCompatImpl
  {
    public boolean canScrollHorizontally(View paramView, int paramInt)
    {
      return false;
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView)
    {
      return null;
    }

    long getFrameTime()
    {
      return 10L;
    }

    public int getImportantForAccessibility(View paramView)
    {
      return 0;
    }

    public int getOverScrollMode(View paramView)
    {
      return 2;
    }

    public boolean hasTransientState(View paramView)
    {
      return false;
    }

    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
    }

    public void postInvalidateOnAnimation(View paramView)
    {
      paramView.postInvalidateDelayed(getFrameTime());
    }

    public void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
    {
    }

    public void setImportantForAccessibility(View paramView, int paramInt)
    {
    }
  }

  static class GBViewCompatImpl extends ViewCompat.BaseViewCompatImpl
  {
    public final int getOverScrollMode(View paramView)
    {
      return paramView.getOverScrollMode();
    }
  }

  static class HCViewCompatImpl extends ViewCompat.GBViewCompatImpl
  {
    final long getFrameTime()
    {
      return ValueAnimator.getFrameDelay();
    }
  }

  static class ICSViewCompatImpl extends ViewCompat.HCViewCompatImpl
  {
    public final boolean canScrollHorizontally(View paramView, int paramInt)
    {
      return paramView.canScrollHorizontally(paramInt);
    }

    public final void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      paramView.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)paramAccessibilityNodeInfoCompat.getInfo());
    }

    public final void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
    {
      paramView.setAccessibilityDelegate((View.AccessibilityDelegate)paramAccessibilityDelegateCompat.getBridge());
    }
  }

  static final class JBViewCompatImpl extends ViewCompat.ICSViewCompatImpl
  {
    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView)
    {
      AccessibilityNodeProvider localAccessibilityNodeProvider = paramView.getAccessibilityNodeProvider();
      if (localAccessibilityNodeProvider != null);
      for (AccessibilityNodeProviderCompat localAccessibilityNodeProviderCompat = new AccessibilityNodeProviderCompat(localAccessibilityNodeProvider); ; localAccessibilityNodeProviderCompat = null)
        return localAccessibilityNodeProviderCompat;
    }

    public final int getImportantForAccessibility(View paramView)
    {
      return paramView.getImportantForAccessibility();
    }

    public final boolean hasTransientState(View paramView)
    {
      return paramView.hasTransientState();
    }

    public final void postInvalidateOnAnimation(View paramView)
    {
      paramView.postInvalidateOnAnimation();
    }

    public final void setImportantForAccessibility(View paramView, int paramInt)
    {
      paramView.setImportantForAccessibility(paramInt);
    }
  }

  static abstract interface ViewCompatImpl
  {
    public abstract boolean canScrollHorizontally(View paramView, int paramInt);

    public abstract AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView);

    public abstract int getImportantForAccessibility(View paramView);

    public abstract int getOverScrollMode(View paramView);

    public abstract boolean hasTransientState(View paramView);

    public abstract void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat);

    public abstract void postInvalidateOnAnimation(View paramView);

    public abstract void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat);

    public abstract void setImportantForAccessibility(View paramView, int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewCompat
 * JD-Core Version:    0.6.2
 */