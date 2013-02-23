package com.google.android.apps.plus.views;

import android.text.TextPaint;
import android.text.style.URLSpan;

public class MentionSpan extends URLSpan
{
  MentionSpan(URLSpan paramURLSpan)
  {
    super(paramURLSpan.getURL());
    if (!isMention(paramURLSpan))
      throw new IllegalArgumentException(paramURLSpan.getURL());
  }

  public MentionSpan(String paramString)
  {
    super("+" + paramString);
  }

  public static boolean isMention(URLSpan paramURLSpan)
  {
    String str = paramURLSpan.getURL();
    if ((str != null) && (str.startsWith("+")));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String getAggregateId()
  {
    return getURL().substring(1);
  }

  public void updateDrawState(TextPaint paramTextPaint)
  {
    paramTextPaint.setColor(-13408564);
    paramTextPaint.bgColor = 0;
    paramTextPaint.setUnderlineText(false);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.MentionSpan
 * JD-Core Version:    0.6.2
 */