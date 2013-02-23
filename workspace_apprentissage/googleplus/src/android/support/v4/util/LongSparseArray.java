package android.support.v4.util;

public final class LongSparseArray<E>
  implements Cloneable
{
  private static final Object DELETED = new Object();
  private boolean mGarbage = false;
  private long[] mKeys;
  private int mSize;
  private Object[] mValues;

  public LongSparseArray()
  {
    this(10);
  }

  private LongSparseArray(int paramInt)
  {
    int i = idealLongArraySize(10);
    this.mKeys = new long[i];
    this.mValues = new Object[i];
    this.mSize = 0;
  }

  private static int binarySearch(long[] paramArrayOfLong, int paramInt1, int paramInt2, long paramLong)
  {
    int i = paramInt2 + 0;
    int j = -1;
    while (i - j > 1)
    {
      int k = (i + j) / 2;
      if (paramArrayOfLong[k] < paramLong)
        j = k;
      else
        i = k;
    }
    if (i == paramInt2 + 0)
      i = 0xFFFFFFFF ^ paramInt2 + 0;
    while (true)
    {
      return i;
      if (paramArrayOfLong[i] != paramLong)
        i ^= -1;
    }
  }

  private LongSparseArray<E> clone()
  {
    LongSparseArray localLongSparseArray = null;
    try
    {
      localLongSparseArray = (LongSparseArray)super.clone();
      localLongSparseArray.mKeys = ((long[])this.mKeys.clone());
      localLongSparseArray.mValues = ((Object[])this.mValues.clone());
      label38: return localLongSparseArray;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      break label38;
    }
  }

  private static int idealLongArraySize(int paramInt)
  {
    int i = paramInt * 8;
    for (int j = 4; ; j++)
      if (j < 32)
      {
        if (i <= -12 + (1 << j))
          i = -12 + (1 << j);
      }
      else
        return i / 8;
  }

  public final E get(long paramLong)
  {
    int i = binarySearch(this.mKeys, 0, this.mSize, paramLong);
    if ((i < 0) || (this.mValues[i] == DELETED));
    for (Object localObject = null; ; localObject = this.mValues[i])
      return localObject;
  }

  public final int indexOfKey(long paramLong)
  {
    return binarySearch(this.mKeys, 0, this.mSize, paramLong);
  }

  public final long keyAt(int paramInt)
  {
    return this.mKeys[paramInt];
  }

  public final void put(long paramLong, E paramE)
  {
    int i = binarySearch(this.mKeys, 0, this.mSize, paramLong);
    if (i >= 0)
      this.mValues[i] = paramE;
    while (true)
    {
      return;
      int j = i ^ 0xFFFFFFFF;
      if ((j < this.mSize) && (this.mValues[j] == DELETED))
      {
        this.mKeys[j] = paramLong;
        this.mValues[j] = paramE;
      }
      else
      {
        if (this.mSize >= this.mKeys.length)
        {
          int k = idealLongArraySize(1 + this.mSize);
          long[] arrayOfLong = new long[k];
          Object[] arrayOfObject = new Object[k];
          System.arraycopy(this.mKeys, 0, arrayOfLong, 0, this.mKeys.length);
          System.arraycopy(this.mValues, 0, arrayOfObject, 0, this.mValues.length);
          this.mKeys = arrayOfLong;
          this.mValues = arrayOfObject;
        }
        if (this.mSize - j != 0)
        {
          System.arraycopy(this.mKeys, j, this.mKeys, j + 1, this.mSize - j);
          System.arraycopy(this.mValues, j, this.mValues, j + 1, this.mSize - j);
        }
        this.mKeys[j] = paramLong;
        this.mValues[j] = paramE;
        this.mSize = (1 + this.mSize);
      }
    }
  }

  public final int size()
  {
    return this.mSize;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.util.LongSparseArray
 * JD-Core Version:    0.6.2
 */