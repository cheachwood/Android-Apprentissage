package com.google.android.picasastore;

import android.database.AbstractCursor;
import android.database.CursorIndexOutOfBoundsException;

public final class PicasaMatrixCursor extends AbstractCursor
{
  private final int columnCount;
  private final String[] columnNames;
  private Object[] data;
  private int rowCount = 0;

  public PicasaMatrixCursor(String[] paramArrayOfString)
  {
    this(paramArrayOfString, 16);
  }

  private PicasaMatrixCursor(String[] paramArrayOfString, int paramInt)
  {
    this.columnNames = paramArrayOfString;
    this.columnCount = paramArrayOfString.length;
    this.data = new Object[16 * this.columnCount];
  }

  private void ensureCapacity(int paramInt)
  {
    if (paramInt > this.data.length)
    {
      Object[] arrayOfObject = this.data;
      int i = 2 * this.data.length;
      if (i < paramInt)
        i = paramInt;
      this.data = new Object[i];
      System.arraycopy(arrayOfObject, 0, this.data, 0, arrayOfObject.length);
    }
  }

  private Object get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.columnCount))
      throw new CursorIndexOutOfBoundsException("Requested column: " + paramInt + ", # of columns: " + this.columnCount);
    if (this.mPos < 0)
      throw new CursorIndexOutOfBoundsException("Before first row.");
    if (this.mPos >= this.rowCount)
      throw new CursorIndexOutOfBoundsException("After last row.");
    return this.data[(paramInt + this.mPos * this.columnCount)];
  }

  public final void addRow(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject.length != this.columnCount)
      throw new IllegalArgumentException("columnNames.length = " + this.columnCount + ", columnValues.length = " + paramArrayOfObject.length);
    int i = this.rowCount;
    this.rowCount = (i + 1);
    int j = i * this.columnCount;
    ensureCapacity(j + this.columnCount);
    System.arraycopy(paramArrayOfObject, 0, this.data, j, this.columnCount);
  }

  public final byte[] getBlob(int paramInt)
  {
    return (byte[])get(paramInt);
  }

  public final String[] getColumnNames()
  {
    return this.columnNames;
  }

  public final int getCount()
  {
    return this.rowCount;
  }

  public final double getDouble(int paramInt)
  {
    Object localObject = get(paramInt);
    double d;
    if (localObject == null)
      d = 0.0D;
    while (true)
    {
      return d;
      if ((localObject instanceof Number))
        d = ((Number)localObject).doubleValue();
      else
        d = Double.parseDouble(localObject.toString());
    }
  }

  public final float getFloat(int paramInt)
  {
    Object localObject = get(paramInt);
    float f;
    if (localObject == null)
      f = 0.0F;
    while (true)
    {
      return f;
      if ((localObject instanceof Number))
        f = ((Number)localObject).floatValue();
      else
        f = Float.parseFloat(localObject.toString());
    }
  }

  public final int getInt(int paramInt)
  {
    Object localObject = get(paramInt);
    int i;
    if (localObject == null)
      i = 0;
    while (true)
    {
      return i;
      if ((localObject instanceof Number))
        i = ((Number)localObject).intValue();
      else
        i = Integer.parseInt(localObject.toString());
    }
  }

  public final long getLong(int paramInt)
  {
    Object localObject = get(paramInt);
    long l;
    if (localObject == null)
      l = 0L;
    while (true)
    {
      return l;
      if ((localObject instanceof Number))
        l = ((Number)localObject).longValue();
      else
        l = Long.parseLong(localObject.toString());
    }
  }

  public final short getShort(int paramInt)
  {
    Object localObject = get(paramInt);
    short s;
    if (localObject == null)
      s = 0;
    while (true)
    {
      return s;
      if ((localObject instanceof Number))
        s = ((Number)localObject).shortValue();
      else
        s = Short.parseShort(localObject.toString());
    }
  }

  public final String getString(int paramInt)
  {
    Object localObject = get(paramInt);
    if (localObject == null);
    for (String str = null; ; str = localObject.toString())
      return str;
  }

  public final int getType(int paramInt)
  {
    Object localObject = get(paramInt);
    int i;
    if (localObject == null)
      i = 0;
    while (true)
    {
      return i;
      if ((localObject instanceof byte[]))
        i = 4;
      else if (((localObject instanceof Float)) || ((localObject instanceof Double)))
        i = 2;
      else if (((localObject instanceof Long)) || ((localObject instanceof Integer)))
        i = 1;
      else
        i = 3;
    }
  }

  public final boolean isNull(int paramInt)
  {
    if (get(paramInt) == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final RowBuilder newRow()
  {
    this.rowCount = (1 + this.rowCount);
    int i = this.rowCount * this.columnCount;
    ensureCapacity(i);
    return new RowBuilder(i - this.columnCount, i);
  }

  public final class RowBuilder
  {
    private final int endIndex;
    private int index;

    RowBuilder(int paramInt1, int arg3)
    {
      this.index = paramInt1;
      int i;
      this.endIndex = i;
    }

    public final RowBuilder add(Object paramObject)
    {
      if (this.index == this.endIndex)
        throw new CursorIndexOutOfBoundsException("No more columns left.");
      Object[] arrayOfObject = PicasaMatrixCursor.this.data;
      int i = this.index;
      this.index = (i + 1);
      arrayOfObject[i] = paramObject;
      return this;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.PicasaMatrixCursor
 * JD-Core Version:    0.6.2
 */