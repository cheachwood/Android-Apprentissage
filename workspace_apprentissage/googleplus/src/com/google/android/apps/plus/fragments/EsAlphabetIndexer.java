package com.google.android.apps.plus.fragments;

import android.database.Cursor;
import android.widget.AlphabetIndexer;
import com.google.android.apps.plus.util.StringUtils;

public final class EsAlphabetIndexer extends AlphabetIndexer
{
  public EsAlphabetIndexer(Cursor paramCursor, int paramInt)
  {
    super(paramCursor, paramInt, computeAlphabet(paramCursor, paramInt));
  }

  private static CharSequence computeAlphabet(Cursor paramCursor, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    boolean bool = paramCursor.moveToFirst();
    int i = 0;
    if (bool)
      do
      {
        char c = StringUtils.firstLetter(paramCursor.getString(paramInt));
        if (c != i)
        {
          localStringBuilder.append(c);
          i = c;
        }
      }
      while (paramCursor.moveToNext());
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EsAlphabetIndexer
 * JD-Core Version:    0.6.2
 */