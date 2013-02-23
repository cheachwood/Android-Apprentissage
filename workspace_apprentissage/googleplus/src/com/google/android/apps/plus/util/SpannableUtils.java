package com.google.android.apps.plus.util;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.TextView;

public final class SpannableUtils
{
  public static void appendWithSpan(SpannableStringBuilder paramSpannableStringBuilder, CharSequence paramCharSequence, Object paramObject)
  {
    int i = paramSpannableStringBuilder.length();
    paramSpannableStringBuilder.append(paramCharSequence);
    paramSpannableStringBuilder.setSpan(paramObject, i, paramSpannableStringBuilder.length(), 33);
  }

  public static void setTextWithHighlight$5cdafd0b(TextView paramTextView, String paramString1, SpannableStringBuilder paramSpannableStringBuilder, String paramString2, Object paramObject1, Object paramObject2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      paramTextView.setText(paramString1);
    while (true)
    {
      return;
      int i = paramString1.toUpperCase().indexOf(paramString2);
      if (i == -1)
      {
        paramTextView.setText(paramString1);
      }
      else
      {
        paramSpannableStringBuilder.clear();
        paramSpannableStringBuilder.append(paramString1);
        int j = i + paramString2.length();
        if (j > paramSpannableStringBuilder.length())
          j = paramSpannableStringBuilder.length();
        paramSpannableStringBuilder.setSpan(paramObject1, i, j, 0);
        paramSpannableStringBuilder.setSpan(paramObject2, i, j, 0);
        paramTextView.setText(paramSpannableStringBuilder);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.SpannableUtils
 * JD-Core Version:    0.6.2
 */