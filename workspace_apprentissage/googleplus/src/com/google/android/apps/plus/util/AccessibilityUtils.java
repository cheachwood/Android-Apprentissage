package com.google.android.apps.plus.util;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

public final class AccessibilityUtils
{
  public static void appendAndSeparateIfNotEmpty(StringBuilder paramStringBuilder, CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence))
      paramStringBuilder.append(paramCharSequence).append(". ");
  }

  public static boolean isAccessibilityEnabled(Context paramContext)
  {
    if (paramContext != null);
    for (boolean bool = AccessibilityManagerCompat.isTouchExplorationEnabled((AccessibilityManager)paramContext.getSystemService("accessibility")); ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.AccessibilityUtils
 * JD-Core Version:    0.6.2
 */