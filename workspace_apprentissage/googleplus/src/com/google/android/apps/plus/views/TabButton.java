package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.apps.plus.R.style;

public class TabButton extends Button
{
  public TabButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public TabButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.style.Tab);
  }

  public TabButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    CharSequence localCharSequence = getText();
    if (localCharSequence != null)
      setText(localCharSequence.toString().toUpperCase());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TabButton
 * JD-Core Version:    0.6.2
 */