package com.google.android.apps.plus.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import com.google.android.apps.plus.views.MentionSpan;

public final class MentionTokenizer
  implements MultiAutoCompleteTextView.Tokenizer
{
  private static int findTokenEnd(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    int i = paramCharSequence.length();
    int j = 0;
    for (int k = paramInt1; ; k++)
    {
      char c1;
      if (k < paramInt2)
      {
        c1 = paramCharSequence.charAt(k);
        if (c1 != '\n')
          break label41;
        paramInt2 = k;
      }
      while (true)
      {
        return paramInt2;
        label41: if (Character.isWhitespace(c1))
        {
          j++;
          if (j >= 4)
          {
            paramInt2 = k;
          }
          else
          {
            for (int m = k + 1; ; m++)
            {
              if (m >= i)
                break label113;
              char c2 = paramCharSequence.charAt(m);
              if (c2 == '\n')
              {
                paramInt2 = k;
                break;
              }
              if (!Character.isWhitespace(c2))
                break label113;
            }
            label113: if (m == i)
            {
              paramInt2 = k;
            }
            else
            {
              k = m;
              c1 = paramCharSequence.charAt(k);
            }
          }
        }
        else
        {
          if ((k <= paramInt2) || (!isMentionTrigger(c1)) || ((k != 0) && (!Character.isWhitespace(paramCharSequence.charAt(k - 1)))))
            break;
          paramInt2 = k;
        }
      }
    }
  }

  public static boolean isMentionTrigger(char paramChar)
  {
    if ((paramChar == '+') || (paramChar == '@'));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final int findTokenEnd(CharSequence paramCharSequence, int paramInt)
  {
    return findTokenEnd(paramCharSequence, paramInt, paramInt);
  }

  public final int findTokenStart(CharSequence paramCharSequence, int paramInt)
  {
    for (int i = paramInt - 1; ; i--)
    {
      char c;
      if (i >= 0)
      {
        c = paramCharSequence.charAt(i);
        if (c != '\n')
          break label26;
      }
      while (true)
      {
        return paramInt;
        label26: if ((!isMentionTrigger(c)) || ((i != 0) && (!Character.isWhitespace(paramCharSequence.charAt(i - 1)))))
          break;
        int j;
        if ((paramCharSequence instanceof Spannable))
        {
          MentionSpan[] arrayOfMentionSpan = (MentionSpan[])((Spannable)paramCharSequence).getSpans(i, i, MentionSpan.class);
          if ((arrayOfMentionSpan != null) && (arrayOfMentionSpan.length != 0))
            j = 1;
        }
        int k;
        while (j == 0)
        {
          for (k = findTokenEnd(paramCharSequence, i, paramInt); (k < paramInt) && (Character.isWhitespace(paramCharSequence.charAt(k))); k++);
          URLSpan[] arrayOfURLSpan = (URLSpan[])((Spannable)paramCharSequence).getSpans(i, i, URLSpan.class);
          if ((arrayOfURLSpan != null) && (arrayOfURLSpan.length != 0))
          {
            int m = arrayOfURLSpan.length;
            for (int n = 0; ; n++)
            {
              if (n >= m)
                break label198;
              if (MentionSpan.isMention(arrayOfURLSpan[n]))
              {
                j = 1;
                break;
              }
            }
          }
          label198: j = 0;
        }
        continue;
        if (k != paramInt)
          break;
        paramInt = i;
      }
    }
  }

  public final CharSequence terminateToken(CharSequence paramCharSequence)
  {
    int i = paramCharSequence.length();
    Object localObject;
    if ((i == 0) || (Character.isWhitespace(paramCharSequence.charAt(i - 1))))
      localObject = paramCharSequence;
    while (true)
    {
      return localObject;
      if ((paramCharSequence instanceof Spanned))
      {
        localObject = new SpannableString(paramCharSequence + " ");
        TextUtils.copySpansFrom((Spanned)paramCharSequence, 0, paramCharSequence.length(), Object.class, (Spannable)localObject, 0);
      }
      else
      {
        localObject = paramCharSequence + " ";
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.MentionTokenizer
 * JD-Core Version:    0.6.2
 */