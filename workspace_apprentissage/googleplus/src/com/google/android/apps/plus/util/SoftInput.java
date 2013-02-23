package com.google.android.apps.plus.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class SoftInput
{
  public static InputMethodManager getInputMethodManager(Context paramContext)
  {
    return (InputMethodManager)paramContext.getSystemService("input_method");
  }

  public static void hide(View paramView)
  {
    InputMethodManager localInputMethodManager = getInputMethodManager(paramView.getContext());
    if (localInputMethodManager != null)
      localInputMethodManager.hideSoftInputFromWindow(paramView.getWindowToken(), 0);
  }

  public static void show(View paramView)
  {
    InputMethodManager localInputMethodManager = getInputMethodManager(paramView.getContext());
    if (localInputMethodManager != null)
      localInputMethodManager.showSoftInput(paramView, 0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.SoftInput
 * JD-Core Version:    0.6.2
 */