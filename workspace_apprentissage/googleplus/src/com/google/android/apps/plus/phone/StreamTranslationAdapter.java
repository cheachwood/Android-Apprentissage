package com.google.android.apps.plus.phone;

import android.database.DataSetObserver;
import android.util.Log;
import android.util.SparseIntArray;
import java.util.Arrays;

public final class StreamTranslationAdapter extends TranslationAdapter
{
  private final AdapterDataSetObserver mObserver = new AdapterDataSetObserver((byte)0);
  private SparseIntArray mTranslation;

  public StreamTranslationAdapter(TranslationAdapter.TranslationListAdapter paramTranslationListAdapter)
  {
    super(paramTranslationListAdapter);
    if (this.mInnerAdapter != null)
      this.mInnerAdapter.registerDataSetObserver(this.mObserver);
  }

  private static int getNextPosition(int paramInt, boolean paramBoolean, int[][] paramArrayOfInt, int[] paramArrayOfInt1)
  {
    int i = -2147483648;
    int j = 2147483647;
    if (paramBoolean)
      for (int i11 = 0; i11 < paramInt; i11++)
      {
        int i12 = paramArrayOfInt1[i11];
        if (i12 < j)
        {
          i = i11;
          j = i12;
        }
      }
    int k = -2147483648;
    int m = 0;
    int i10;
    while (m < paramInt)
    {
      i10 = paramArrayOfInt1[m];
      if ((i10 + 1) % paramInt != 0)
        break label146;
      if ((m == 0) || (k == i10))
      {
        k = i10;
        m++;
      }
      else
      {
        i = m;
        j = i10;
      }
    }
    if (i == -2147483648)
    {
      i = 0;
      j = paramArrayOfInt1[0];
    }
    int n = j + 1;
    int i1 = paramArrayOfInt[0].length;
    int i2;
    if (n >= i1)
      i2 = -1;
    while (true)
    {
      return i2;
      label146: i = m;
      j = i10;
      break;
      i2 = paramArrayOfInt[i][n];
      int i3 = n;
      int i4 = Math.min(paramInt, i1 - n);
      for (int i5 = 1; (i5 < i4) && (paramArrayOfInt[i][(n + i5)] == i2); i5++)
        i3 = n + i5;
      for (int i6 = i; (i6 < paramInt) && (paramArrayOfInt[i6][i3] == i2) && (paramArrayOfInt1[i6] == n - 1); i6++)
        paramArrayOfInt1[i6] = i3;
      for (int i7 = 1; i7 < paramInt; i7++)
      {
        int i8 = paramArrayOfInt1[(i7 - 1)];
        for (int i9 = 1 + paramArrayOfInt1[i7]; (i9 < i1) && (i8 > paramArrayOfInt1[i7]) && (paramArrayOfInt[(i7 - 1)][i9] == paramArrayOfInt[i7][i9]); i9++)
          paramArrayOfInt1[i7] = i9;
      }
    }
  }

  protected final int translate(int paramInt)
  {
    int i;
    boolean bool;
    int[][] arrayOfInt;
    if (this.mTranslation == null)
    {
      this.mTranslation = new SparseIntArray();
      i = this.mInnerAdapter.getColumnCount();
      bool = this.mInnerAdapter.isHorizontal();
      arrayOfInt = this.mInnerAdapter.getLayoutArray();
      if (arrayOfInt != null)
        break label72;
      Log.w("TranslationAdapter", "Building translation without an array. Did you forget to set the layout?");
    }
    label72: 
    while (i == 1)
      return this.mTranslation.get(paramInt, paramInt);
    int[] arrayOfInt1 = new int[i];
    Arrays.fill(arrayOfInt1, -1);
    int j = 0;
    while (true)
    {
      int k = getNextPosition(i, bool, arrayOfInt, arrayOfInt1);
      if (k < 0)
        break;
      if (j == k)
      {
        j++;
      }
      else
      {
        this.mTranslation.put(j, k);
        j++;
      }
    }
  }

  private final class AdapterDataSetObserver extends DataSetObserver
  {
    private AdapterDataSetObserver()
    {
    }

    public final void onChanged()
    {
      StreamTranslationAdapter.access$102(StreamTranslationAdapter.this, null);
    }

    public final void onInvalidated()
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.StreamTranslationAdapter
 * JD-Core Version:    0.6.2
 */