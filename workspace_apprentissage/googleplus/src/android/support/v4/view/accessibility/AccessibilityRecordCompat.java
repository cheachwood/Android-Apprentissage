package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;

public final class AccessibilityRecordCompat
{
  private static final AccessibilityRecordImpl IMPL;
  private final Object mRecord;

  static
  {
    if (Build.VERSION.SDK_INT >= 16)
      IMPL = new AccessibilityRecordJellyBeanImpl();
    while (true)
    {
      return;
      if (Build.VERSION.SDK_INT >= 15)
        IMPL = new AccessibilityRecordIcsMr1Impl();
      else if (Build.VERSION.SDK_INT >= 14)
        IMPL = new AccessibilityRecordIcsImpl();
      else
        IMPL = new AccessibilityRecordStubImpl();
    }
  }

  public AccessibilityRecordCompat(Object paramObject)
  {
    this.mRecord = paramObject;
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
        AccessibilityRecordCompat localAccessibilityRecordCompat = (AccessibilityRecordCompat)paramObject;
        if (this.mRecord == null)
        {
          if (localAccessibilityRecordCompat.mRecord != null)
            bool = false;
        }
        else if (!this.mRecord.equals(localAccessibilityRecordCompat.mRecord))
          bool = false;
      }
    }
  }

  public final int hashCode()
  {
    if (this.mRecord == null);
    for (int i = 0; ; i = this.mRecord.hashCode())
      return i;
  }

  public final void setSource(View paramView, int paramInt)
  {
    IMPL.setSource(this.mRecord, paramView, paramInt);
  }

  static class AccessibilityRecordIcsImpl extends AccessibilityRecordCompat.AccessibilityRecordStubImpl
  {
  }

  static class AccessibilityRecordIcsMr1Impl extends AccessibilityRecordCompat.AccessibilityRecordIcsImpl
  {
  }

  static abstract interface AccessibilityRecordImpl
  {
    public abstract void setSource(Object paramObject, View paramView, int paramInt);
  }

  static final class AccessibilityRecordJellyBeanImpl extends AccessibilityRecordCompat.AccessibilityRecordIcsMr1Impl
  {
    public final void setSource(Object paramObject, View paramView, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setSource(paramView, paramInt);
    }
  }

  static class AccessibilityRecordStubImpl
    implements AccessibilityRecordCompat.AccessibilityRecordImpl
  {
    public void setSource(Object paramObject, View paramView, int paramInt)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityRecordCompat
 * JD-Core Version:    0.6.2
 */