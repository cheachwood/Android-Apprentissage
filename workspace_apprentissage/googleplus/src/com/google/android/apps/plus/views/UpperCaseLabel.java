package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.apps.plus.R.style;

public class UpperCaseLabel extends TextView
{
  public UpperCaseLabel(Context paramContext)
  {
    this(paramContext, null);
  }

  public UpperCaseLabel(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.style.Tab);
  }

  public UpperCaseLabel(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    CharSequence localCharSequence = getText();
    if (localCharSequence != null)
      setText(localCharSequence.toString().toUpperCase());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.UpperCaseLabel
 * JD-Core Version:    0.6.2
 */