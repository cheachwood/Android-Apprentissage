package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public final class TextViewUtils
{
  public static TextView createText(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, float paramFloat, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    TextView localTextView = new TextView(paramContext, paramAttributeSet, paramInt1);
    localTextView.setTextSize(0, paramFloat);
    localTextView.setSingleLine(true);
    localTextView.setTextColor(paramInt2);
    localTextView.setEllipsize(TextUtils.TruncateAt.END);
    if (paramBoolean1)
      localTextView.setTypeface(Typeface.DEFAULT_BOLD);
    return localTextView;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TextViewUtils
 * JD-Core Version:    0.6.2
 */