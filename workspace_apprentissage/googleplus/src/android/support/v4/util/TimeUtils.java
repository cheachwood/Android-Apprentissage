package android.support.v4.util;

import java.io.PrintWriter;

public final class TimeUtils
{
  private static char[] sFormatStr = new char[24];
  private static final Object sFormatSync = new Object();

  private static int accumField(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    int i;
    if ((paramInt1 > 99) || ((paramBoolean) && (paramInt3 >= 3)))
      i = paramInt2 + 3;
    while (true)
    {
      return i;
      if ((paramInt1 > 9) || ((paramBoolean) && (paramInt3 >= 2)))
        i = paramInt2 + 2;
      else if ((paramBoolean) || (paramInt1 > 0))
        i = paramInt2 + 1;
      else
        i = 0;
    }
  }

  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter)
  {
    if (paramLong1 == 0L)
      paramPrintWriter.print("--");
    while (true)
    {
      return;
      formatDuration(paramLong1 - paramLong2, paramPrintWriter, 0);
    }
  }

  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter)
  {
    formatDuration(paramLong, paramPrintWriter, 0);
  }

  private static void formatDuration(long paramLong, PrintWriter paramPrintWriter, int paramInt)
  {
    while (true)
    {
      int k;
      int i2;
      int i;
      synchronized (sFormatSync)
      {
        if (sFormatStr.length < 0)
          sFormatStr = new char[0];
        char[] arrayOfChar = sFormatStr;
        if (paramLong != 0L)
          break label354;
        arrayOfChar[0] = '0';
        int i8 = 1;
        paramPrintWriter.print(new String(sFormatStr, 0, i8));
        return;
        int j = (int)(paramLong % 1000L);
        k = (int)Math.floor(paramLong / 1000L);
        int m = 0;
        if (k > 86400)
        {
          m = k / 86400;
          k -= 86400 * m;
        }
        if (k <= 3600)
          break label344;
        int i11 = k / 3600;
        int i12 = k - i11 * 3600;
        i1 = i11;
        n = i12;
        if (n > 60)
        {
          int i9 = n / 60;
          int i10 = n - i9 * 60;
          i3 = i9;
          i2 = i10;
          arrayOfChar[0] = i;
          int i4 = printField(arrayOfChar, m, 'd', 1, false, 0);
          if (i4 != 1)
          {
            bool1 = true;
            int i5 = printField(arrayOfChar, i1, 'h', i4, bool1, 0);
            if (i5 == 1)
              continue;
            bool2 = true;
            int i6 = printField(arrayOfChar, i3, 'm', i5, bool2, 0);
            if (i6 == 1)
              continue;
            bool3 = true;
            int i7 = printField(arrayOfChar, j, 'm', printField(arrayOfChar, i2, 's', i6, bool3, 0), true, 0);
            arrayOfChar[i7] = 's';
            i8 = i7 + 1;
            continue;
            paramLong = -paramLong;
            i = 45;
            continue;
          }
          boolean bool1 = false;
          continue;
          boolean bool2 = false;
          continue;
          boolean bool3 = false;
        }
      }
      int i3 = 0;
      continue;
      label344: int n = k;
      int i1 = 0;
      continue;
      label354: if (paramLong > 0L)
        i = 43;
    }
  }

  private static int printField(char[] paramArrayOfChar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if ((paramBoolean) || (paramInt1 > 0))
    {
      int i = paramInt2;
      if (((paramBoolean) && (paramInt3 >= 3)) || (paramInt1 > 99))
      {
        int m = paramInt1 / 100;
        paramArrayOfChar[paramInt2] = ((char)(m + 48));
        paramInt2++;
        paramInt1 -= m * 100;
      }
      if (((paramBoolean) && (paramInt3 >= 2)) || (paramInt1 > 9) || (i != paramInt2))
      {
        int j = paramInt1 / 10;
        paramArrayOfChar[paramInt2] = ((char)(j + 48));
        paramInt2++;
        paramInt1 -= j * 10;
      }
      paramArrayOfChar[paramInt2] = ((char)(paramInt1 + 48));
      int k = paramInt2 + 1;
      paramArrayOfChar[k] = paramChar;
      paramInt2 = k + 1;
    }
    return paramInt2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.util.TimeUtils
 * JD-Core Version:    0.6.2
 */