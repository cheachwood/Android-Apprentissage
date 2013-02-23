package com.google.android.apps.plus.util;

import android.text.Html;
import android.text.TextUtils;
import java.util.Random;

public final class StringUtils
{
  private static Random randGen = new Random();
  private static char[] sNumbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  public static boolean equals(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if ((TextUtils.equals(paramCharSequence1, paramCharSequence2)) || ((TextUtils.isEmpty(paramCharSequence1)) && (TextUtils.isEmpty(paramCharSequence2))));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static char firstLetter(String paramString)
  {
    char c1 = ' ';
    if ((paramString == null) || (paramString.length() == 0));
    while (true)
    {
      return c1;
      char c2 = paramString.charAt(0);
      if (Character.isLetter(c2))
        c1 = Character.toUpperCase(c2);
    }
  }

  public static String getDomain(String paramString)
  {
    String[] arrayOfString = paramString.split("@");
    if (arrayOfString.length != 2);
    for (String str = null; ; str = arrayOfString[1])
      return str;
  }

  public static String randomString(int paramInt)
  {
    char[] arrayOfChar = new char[32];
    for (int i = 0; i < arrayOfChar.length; i++)
      arrayOfChar[i] = sNumbersAndLetters[randGen.nextInt(71)];
    return new String(arrayOfChar);
  }

  public static String unescape(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return paramString;
      paramString = Html.fromHtml(paramString).toString();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.StringUtils
 * JD-Core Version:    0.6.2
 */