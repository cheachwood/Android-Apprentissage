package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public final class AccessibilityNodeInfoCompat
{
  private static final AccessibilityNodeInfoImpl IMPL;
  private final Object mInfo;

  static
  {
    if (Build.VERSION.SDK_INT >= 16)
      IMPL = new AccessibilityNodeInfoJellybeanImpl();
    while (true)
    {
      return;
      if (Build.VERSION.SDK_INT >= 14)
        IMPL = new AccessibilityNodeInfoIcsImpl();
      else
        IMPL = new AccessibilityNodeInfoStubImpl();
    }
  }

  public AccessibilityNodeInfoCompat(Object paramObject)
  {
    this.mInfo = paramObject;
  }

  public static AccessibilityNodeInfoCompat obtain()
  {
    return wrapNonNullInstance(IMPL.obtain());
  }

  public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    return wrapNonNullInstance(IMPL.obtain(paramAccessibilityNodeInfoCompat.mInfo));
  }

  public static AccessibilityNodeInfoCompat obtain(View paramView)
  {
    return wrapNonNullInstance(IMPL.obtain(paramView));
  }

  private static AccessibilityNodeInfoCompat wrapNonNullInstance(Object paramObject)
  {
    if (paramObject != null);
    for (AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = new AccessibilityNodeInfoCompat(paramObject); ; localAccessibilityNodeInfoCompat = null)
      return localAccessibilityNodeInfoCompat;
  }

  public final void addAction(int paramInt)
  {
    IMPL.addAction(this.mInfo, paramInt);
  }

  public final void addChild(View paramView, int paramInt)
  {
    IMPL.addChild(this.mInfo, paramView, paramInt);
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject);
    while (true)
    {
      return bool;
      if (paramObject == null)
      {
        bool = false;
      }
      else if (getClass() != paramObject.getClass())
      {
        bool = false;
      }
      else
      {
        AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat)paramObject;
        if (this.mInfo == null)
        {
          if (localAccessibilityNodeInfoCompat.mInfo != null)
            bool = false;
        }
        else if (!this.mInfo.equals(localAccessibilityNodeInfoCompat.mInfo))
          bool = false;
      }
    }
  }

  public final void getBoundsInParent(Rect paramRect)
  {
    IMPL.getBoundsInParent(this.mInfo, paramRect);
  }

  public final void getBoundsInScreen(Rect paramRect)
  {
    IMPL.getBoundsInScreen(this.mInfo, paramRect);
  }

  public final CharSequence getContentDescription()
  {
    return IMPL.getContentDescription(this.mInfo);
  }

  public final Object getInfo()
  {
    return this.mInfo;
  }

  public final CharSequence getText()
  {
    return IMPL.getText(this.mInfo);
  }

  public final int hashCode()
  {
    if (this.mInfo == null);
    for (int i = 0; ; i = this.mInfo.hashCode())
      return i;
  }

  public final void recycle()
  {
    IMPL.recycle(this.mInfo);
  }

  public final void setBoundsInParent(Rect paramRect)
  {
    IMPL.setBoundsInParent(this.mInfo, paramRect);
  }

  public final void setBoundsInScreen(Rect paramRect)
  {
    IMPL.setBoundsInScreen(this.mInfo, paramRect);
  }

  public final void setClassName(CharSequence paramCharSequence)
  {
    IMPL.setClassName(this.mInfo, paramCharSequence);
  }

  public final void setEnabled(boolean paramBoolean)
  {
    IMPL.setEnabled(this.mInfo, true);
  }

  public final void setPackageName(CharSequence paramCharSequence)
  {
    IMPL.setPackageName(this.mInfo, paramCharSequence);
  }

  public final void setParent(View paramView)
  {
    IMPL.setParent(this.mInfo, paramView);
  }

  public final void setScrollable(boolean paramBoolean)
  {
    IMPL.setScrollable(this.mInfo, paramBoolean);
  }

  public final void setSource(View paramView, int paramInt)
  {
    IMPL.setSource(this.mInfo, paramView, paramInt);
  }

  public final void setText(CharSequence paramCharSequence)
  {
    IMPL.setText(this.mInfo, paramCharSequence);
  }

  public final void setVisibleToUser(boolean paramBoolean)
  {
    IMPL.setVisibleToUser(this.mInfo, true);
  }

  static class AccessibilityNodeInfoIcsImpl extends AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl
  {
    public final void addAction(Object paramObject, int paramInt)
    {
      ((AccessibilityNodeInfo)paramObject).addAction(paramInt);
    }

    public final void getBoundsInParent(Object paramObject, Rect paramRect)
    {
      ((AccessibilityNodeInfo)paramObject).getBoundsInParent(paramRect);
    }

    public final void getBoundsInScreen(Object paramObject, Rect paramRect)
    {
      ((AccessibilityNodeInfo)paramObject).getBoundsInScreen(paramRect);
    }

    public final CharSequence getContentDescription(Object paramObject)
    {
      return ((AccessibilityNodeInfo)paramObject).getContentDescription();
    }

    public final CharSequence getText(Object paramObject)
    {
      return ((AccessibilityNodeInfo)paramObject).getText();
    }

    public final Object obtain()
    {
      return AccessibilityNodeInfo.obtain();
    }

    public final Object obtain(View paramView)
    {
      return AccessibilityNodeInfo.obtain(paramView);
    }

    public final Object obtain(Object paramObject)
    {
      return AccessibilityNodeInfo.obtain((AccessibilityNodeInfo)paramObject);
    }

    public final void recycle(Object paramObject)
    {
      ((AccessibilityNodeInfo)paramObject).recycle();
    }

    public final void setBoundsInParent(Object paramObject, Rect paramRect)
    {
      ((AccessibilityNodeInfo)paramObject).setBoundsInParent(paramRect);
    }

    public final void setBoundsInScreen(Object paramObject, Rect paramRect)
    {
      ((AccessibilityNodeInfo)paramObject).setBoundsInScreen(paramRect);
    }

    public final void setClassName(Object paramObject, CharSequence paramCharSequence)
    {
      ((AccessibilityNodeInfo)paramObject).setClassName(paramCharSequence);
    }

    public final void setEnabled(Object paramObject, boolean paramBoolean)
    {
      ((AccessibilityNodeInfo)paramObject).setEnabled(paramBoolean);
    }

    public final void setPackageName(Object paramObject, CharSequence paramCharSequence)
    {
      ((AccessibilityNodeInfo)paramObject).setPackageName(paramCharSequence);
    }

    public final void setParent(Object paramObject, View paramView)
    {
      ((AccessibilityNodeInfo)paramObject).setParent(paramView);
    }

    public final void setScrollable(Object paramObject, boolean paramBoolean)
    {
      ((AccessibilityNodeInfo)paramObject).setScrollable(paramBoolean);
    }

    public final void setText(Object paramObject, CharSequence paramCharSequence)
    {
      ((AccessibilityNodeInfo)paramObject).setText(paramCharSequence);
    }
  }

  static abstract interface AccessibilityNodeInfoImpl
  {
    public abstract void addAction(Object paramObject, int paramInt);

    public abstract void addChild(Object paramObject, View paramView, int paramInt);

    public abstract void getBoundsInParent(Object paramObject, Rect paramRect);

    public abstract void getBoundsInScreen(Object paramObject, Rect paramRect);

    public abstract CharSequence getContentDescription(Object paramObject);

    public abstract CharSequence getText(Object paramObject);

    public abstract Object obtain();

    public abstract Object obtain(View paramView);

    public abstract Object obtain(Object paramObject);

    public abstract void recycle(Object paramObject);

    public abstract void setBoundsInParent(Object paramObject, Rect paramRect);

    public abstract void setBoundsInScreen(Object paramObject, Rect paramRect);

    public abstract void setClassName(Object paramObject, CharSequence paramCharSequence);

    public abstract void setEnabled(Object paramObject, boolean paramBoolean);

    public abstract void setPackageName(Object paramObject, CharSequence paramCharSequence);

    public abstract void setParent(Object paramObject, View paramView);

    public abstract void setScrollable(Object paramObject, boolean paramBoolean);

    public abstract void setSource(Object paramObject, View paramView, int paramInt);

    public abstract void setText(Object paramObject, CharSequence paramCharSequence);

    public abstract void setVisibleToUser(Object paramObject, boolean paramBoolean);
  }

  static final class AccessibilityNodeInfoJellybeanImpl extends AccessibilityNodeInfoCompat.AccessibilityNodeInfoIcsImpl
  {
    public final void addChild(Object paramObject, View paramView, int paramInt)
    {
      ((AccessibilityNodeInfo)paramObject).addChild(paramView, paramInt);
    }

    public final void setSource(Object paramObject, View paramView, int paramInt)
    {
      ((AccessibilityNodeInfo)paramObject).setSource(paramView, paramInt);
    }

    public final void setVisibleToUser(Object paramObject, boolean paramBoolean)
    {
      ((AccessibilityNodeInfo)paramObject).setVisibleToUser(paramBoolean);
    }
  }

  static class AccessibilityNodeInfoStubImpl
    implements AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
  {
    public void addAction(Object paramObject, int paramInt)
    {
    }

    public void addChild(Object paramObject, View paramView, int paramInt)
    {
    }

    public void getBoundsInParent(Object paramObject, Rect paramRect)
    {
    }

    public void getBoundsInScreen(Object paramObject, Rect paramRect)
    {
    }

    public CharSequence getContentDescription(Object paramObject)
    {
      return null;
    }

    public CharSequence getText(Object paramObject)
    {
      return null;
    }

    public Object obtain()
    {
      return null;
    }

    public Object obtain(View paramView)
    {
      return null;
    }

    public Object obtain(Object paramObject)
    {
      return null;
    }

    public void recycle(Object paramObject)
    {
    }

    public void setBoundsInParent(Object paramObject, Rect paramRect)
    {
    }

    public void setBoundsInScreen(Object paramObject, Rect paramRect)
    {
    }

    public void setClassName(Object paramObject, CharSequence paramCharSequence)
    {
    }

    public void setEnabled(Object paramObject, boolean paramBoolean)
    {
    }

    public void setPackageName(Object paramObject, CharSequence paramCharSequence)
    {
    }

    public void setParent(Object paramObject, View paramView)
    {
    }

    public void setScrollable(Object paramObject, boolean paramBoolean)
    {
    }

    public void setSource(Object paramObject, View paramView, int paramInt)
    {
    }

    public void setText(Object paramObject, CharSequence paramCharSequence)
    {
    }

    public void setVisibleToUser(Object paramObject, boolean paramBoolean)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
 * JD-Core Version:    0.6.2
 */