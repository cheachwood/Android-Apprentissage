package com.googlecode.eyesfree.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@TargetApi(14)
public abstract class TouchExplorationHelper<T> extends AccessibilityNodeProviderCompat
{
  private T mCurrentItem = null;
  private final AccessibilityDelegateCompat mDelegate = new AccessibilityDelegateCompat()
  {
    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramAnonymousView)
    {
      return TouchExplorationHelper.this;
    }
  };
  private int mFocusedItemId = -2147483648;
  private final AccessibilityManager mManager;
  private final SparseArray<AccessibilityNodeInfoCompat> mNodeCache = new SparseArray();
  private final View.OnHoverListener mOnHoverListener = new View.OnHoverListener()
  {
    public final boolean onHover(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      boolean bool1 = AccessibilityManagerCompat.isTouchExplorationEnabled(TouchExplorationHelper.this.mManager);
      boolean bool2 = false;
      if (!bool1);
      while (true)
      {
        return bool2;
        switch (paramAnonymousMotionEvent.getAction())
        {
        case 8:
        default:
          bool2 = false;
          break;
        case 7:
        case 9:
          Object localObject = TouchExplorationHelper.this.getItemAt(paramAnonymousMotionEvent.getX(), paramAnonymousMotionEvent.getY());
          TouchExplorationHelper.access$100(TouchExplorationHelper.this, localObject);
          bool2 = true;
          break;
        case 10:
          TouchExplorationHelper.access$100(TouchExplorationHelper.this, null);
          bool2 = true;
        }
      }
    }
  };
  private View mParentView;
  private final Rect mTempGlobalRect = new Rect();
  private final Rect mTempParentRect = new Rect();
  private final Rect mTempScreenRect = new Rect();

  public TouchExplorationHelper(Context paramContext)
  {
    this.mManager = ((AccessibilityManager)paramContext.getSystemService("accessibility"));
  }

  private void clearCache()
  {
    for (int i = 0; i < this.mNodeCache.size(); i++)
      ((AccessibilityNodeInfoCompat)this.mNodeCache.valueAt(i)).recycle();
    this.mNodeCache.clear();
  }

  private void sendEventForItem(T paramT, int paramInt)
  {
    AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(paramInt);
    AccessibilityRecordCompat localAccessibilityRecordCompat = new AccessibilityRecordCompat(localAccessibilityEvent);
    int i = getIdForItem(paramT);
    localAccessibilityEvent.setEnabled(true);
    populateEventForItem(paramT, localAccessibilityEvent);
    if ((localAccessibilityEvent.getText().isEmpty()) && (TextUtils.isEmpty(localAccessibilityEvent.getContentDescription())))
      throw new RuntimeException("You must add text or a content description in populateEventForItem()");
    localAccessibilityEvent.setClassName(paramT.getClass().getName());
    localAccessibilityEvent.setPackageName(this.mParentView.getContext().getPackageName());
    localAccessibilityRecordCompat.setSource(this.mParentView, i);
    ((ViewGroup)this.mParentView.getParent()).requestSendAccessibilityEvent(this.mParentView, localAccessibilityEvent);
  }

  public final AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int paramInt)
  {
    if (paramInt == -1)
    {
      localAccessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(this.mParentView);
      ViewCompat.onInitializeAccessibilityNodeInfo(this.mParentView, localAccessibilityNodeInfoCompat2);
      LinkedList localLinkedList = new LinkedList();
      getVisibleItems(localLinkedList);
      Iterator localIterator = localLinkedList.iterator();
      while (localIterator.hasNext())
      {
        int m = getIdForItem(localIterator.next());
        localAccessibilityNodeInfoCompat2.addChild(this.mParentView, m);
      }
    }
    AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat1 = (AccessibilityNodeInfoCompat)this.mNodeCache.get(paramInt);
    if (localAccessibilityNodeInfoCompat1 != null);
    Object localObject;
    for (AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(localAccessibilityNodeInfoCompat1); ; localAccessibilityNodeInfoCompat2 = null)
    {
      return localAccessibilityNodeInfoCompat2;
      localObject = getItemForId(paramInt);
      if (localObject != null)
        break;
    }
    localAccessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain();
    int i = getIdForItem(localObject);
    localAccessibilityNodeInfoCompat2.setEnabled(true);
    localAccessibilityNodeInfoCompat2.setVisibleToUser(true);
    populateNodeForItem(localObject, localAccessibilityNodeInfoCompat2);
    if ((TextUtils.isEmpty(localAccessibilityNodeInfoCompat2.getText())) && (TextUtils.isEmpty(localAccessibilityNodeInfoCompat2.getContentDescription())))
      throw new RuntimeException("You must add text or a content description in populateNodeForItem()");
    localAccessibilityNodeInfoCompat2.setPackageName(this.mParentView.getContext().getPackageName());
    localAccessibilityNodeInfoCompat2.setClassName(localObject.getClass().getName());
    localAccessibilityNodeInfoCompat2.setParent(this.mParentView);
    localAccessibilityNodeInfoCompat2.setSource(this.mParentView, i);
    if (this.mFocusedItemId == i)
      localAccessibilityNodeInfoCompat2.addAction(128);
    while (true)
    {
      localAccessibilityNodeInfoCompat2.getBoundsInParent(this.mTempParentRect);
      localAccessibilityNodeInfoCompat2.getBoundsInScreen(this.mTempScreenRect);
      if ((!this.mTempParentRect.isEmpty()) || (!this.mTempScreenRect.isEmpty()))
        break;
      throw new RuntimeException("You must set parent or screen bounds in populateNodeForItem()");
      localAccessibilityNodeInfoCompat2.addAction(64);
    }
    int j;
    int k;
    if ((this.mTempScreenRect.isEmpty()) || (this.mTempParentRect.isEmpty()))
    {
      this.mParentView.getGlobalVisibleRect(this.mTempGlobalRect);
      j = this.mTempGlobalRect.left;
      k = this.mTempGlobalRect.top;
      if (!this.mTempScreenRect.isEmpty())
        break label416;
      this.mTempScreenRect.set(this.mTempParentRect);
      this.mTempScreenRect.offset(j, k);
      localAccessibilityNodeInfoCompat2.setBoundsInScreen(this.mTempScreenRect);
    }
    while (true)
    {
      this.mNodeCache.put(paramInt, AccessibilityNodeInfoCompat.obtain(localAccessibilityNodeInfoCompat2));
      break;
      label416: this.mTempParentRect.set(this.mTempScreenRect);
      this.mTempParentRect.offset(-j, -k);
      localAccessibilityNodeInfoCompat2.setBoundsInParent(this.mTempParentRect);
    }
  }

  protected abstract int getIdForItem(T paramT);

  protected abstract T getItemAt(float paramFloat1, float paramFloat2);

  protected abstract T getItemForId(int paramInt);

  protected abstract void getVisibleItems(List<T> paramList);

  public final void install(View paramView)
  {
    if ((ViewCompat.getAccessibilityNodeProvider(paramView) instanceof TouchExplorationHelper))
      throw new RuntimeException("Cannot install TouchExplorationHelper on a View that already has a helper installed.");
    this.mParentView = paramView;
    this.mParentView.setOnHoverListener(this.mOnHoverListener);
    ViewCompat.setAccessibilityDelegate(this.mParentView, this.mDelegate);
    ViewCompat.setImportantForAccessibility(this.mParentView, 1);
    invalidateParent();
  }

  public final void invalidateParent()
  {
    clearCache();
    ViewCompat.setAccessibilityDelegate(this.mParentView, this.mDelegate);
    this.mParentView.sendAccessibilityEvent(2048);
  }

  public final boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    if (paramInt1 == -1);
    Object localObject;
    for (boolean bool = this.mDelegate.performAccessibilityAction(this.mParentView, paramInt2, paramBundle); ; bool = false)
    {
      return bool;
      localObject = getItemForId(paramInt1);
      if (localObject != null)
        break;
    }
    int i = 0;
    switch (paramInt2)
    {
    default:
    case 64:
    case 128:
    }
    while (true)
    {
      bool = i | performActionForItem(localObject, paramInt2, paramBundle);
      break;
      int k = this.mFocusedItemId;
      i = 0;
      if (k != paramInt1)
      {
        this.mFocusedItemId = paramInt1;
        sendEventForItem(localObject, 32768);
        i = 1;
        continue;
        int j = this.mFocusedItemId;
        i = 0;
        if (j == paramInt1)
        {
          this.mFocusedItemId = -2147483648;
          sendEventForItem(localObject, 65536);
          i = 1;
        }
      }
    }
  }

  protected abstract boolean performActionForItem(T paramT, int paramInt, Bundle paramBundle);

  protected abstract void populateEventForItem(T paramT, AccessibilityEvent paramAccessibilityEvent);

  protected abstract void populateNodeForItem(T paramT, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat);

  public final void uninstall()
  {
    if (this.mParentView == null)
      throw new RuntimeException("Cannot uninstall TouchExplorationHelper on a View that does not have a helper installed.");
    ViewCompat.setAccessibilityDelegate(this.mParentView, new AccessibilityDelegateCompat());
    ViewCompat.setImportantForAccessibility(this.mParentView, 0);
    clearCache();
    this.mParentView.setOnHoverListener(null);
    this.mParentView = null;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.googlecode.eyesfree.utils.TouchExplorationHelper
 * JD-Core Version:    0.6.2
 */