package com.android.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class EsCompositeCursorAdapter extends BaseAdapter
{
  private boolean mCacheValid = true;
  protected final Context mContext;
  private int mCount = 0;
  private boolean mNotificationNeeded;
  private boolean mNotificationsEnabled = true;
  private Partition[] mPartitions;
  private int mSize = 0;

  public EsCompositeCursorAdapter(Context paramContext)
  {
    this(paramContext, (byte)0);
  }

  public EsCompositeCursorAdapter(Context paramContext, byte paramByte)
  {
    this.mContext = paramContext;
    this.mPartitions = new Partition[2];
  }

  private void ensureCacheValid()
  {
    if (this.mCacheValid);
    while (true)
    {
      return;
      this.mCount = 0;
      int i = 0;
      if (i < this.mSize)
      {
        Cursor localCursor = this.mPartitions[i].cursor;
        if (localCursor != null);
        for (int j = localCursor.getCount(); ; j = 0)
        {
          if ((this.mPartitions[i].hasHeader) && ((j != 0) || (this.mPartitions[i].showIfEmpty)))
            j++;
          this.mPartitions[i].count = j;
          this.mCount = (j + this.mCount);
          i++;
          break;
        }
      }
      this.mCacheValid = true;
    }
  }

  public final void addPartition(boolean paramBoolean1, boolean paramBoolean2)
  {
    Partition localPartition = new Partition(paramBoolean1, paramBoolean2);
    if (this.mSize >= this.mPartitions.length)
    {
      Partition[] arrayOfPartition2 = new Partition[2 + this.mSize];
      System.arraycopy(this.mPartitions, 0, arrayOfPartition2, 0, this.mSize);
      this.mPartitions = arrayOfPartition2;
    }
    Partition[] arrayOfPartition1 = this.mPartitions;
    int i = this.mSize;
    this.mSize = (i + 1);
    arrayOfPartition1[i] = localPartition;
    this.mCacheValid = false;
    notifyDataSetChanged();
  }

  public boolean areAllItemsEnabled()
  {
    int i = 0;
    if (i < this.mSize)
      if (!this.mPartitions[i].hasHeader);
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      i++;
      break;
    }
  }

  protected void bindHeaderView$3ab248f1(View paramView)
  {
  }

  protected abstract void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2);

  public void changeCursor(int paramInt, Cursor paramCursor)
  {
    Cursor localCursor = this.mPartitions[paramInt].cursor;
    if (localCursor != paramCursor)
    {
      if ((localCursor != null) && (!localCursor.isClosed()) && (isCursorClosingEnabled()))
        localCursor.close();
      this.mPartitions[paramInt].cursor = paramCursor;
      if (paramCursor != null)
        this.mPartitions[paramInt].idColumnIndex = paramCursor.getColumnIndex("_id");
      this.mCacheValid = false;
      notifyDataSetChanged();
    }
  }

  public final void checkPartitions(String paramString1, String paramString2)
  {
    int i = -1 + this.mSize;
    if (i >= 0)
    {
      Partition localPartition = this.mPartitions[i];
      Cursor localCursor;
      if (localPartition != null)
      {
        localCursor = localPartition.cursor;
        label31: if (localCursor != null)
          break label78;
        Log.i(paramString1, "partcheck s:" + paramString2 + " emptypart:" + i);
      }
      while (true)
      {
        i--;
        break;
        localCursor = null;
        break label31;
        label78: if (localCursor.isClosed())
          Log.i(paramString1, "partcheck s:" + paramString2 + " stalepart:" + i);
      }
    }
  }

  public final void clearPartitions()
  {
    for (int i = 0; i < this.mSize; i++)
      this.mPartitions[i].cursor = null;
    this.mCacheValid = false;
    notifyDataSetChanged();
  }

  public final void close()
  {
    for (int i = 0; i < this.mSize; i++)
    {
      Cursor localCursor = this.mPartitions[i].cursor;
      if ((localCursor != null) && (!localCursor.isClosed()))
      {
        localCursor.close();
        this.mPartitions[i].cursor = null;
      }
    }
    this.mSize = 0;
    this.mCacheValid = false;
    notifyDataSetChanged();
  }

  public final Context getContext()
  {
    return this.mContext;
  }

  public int getCount()
  {
    ensureCacheValid();
    return this.mCount;
  }

  public final int getCount(int paramInt)
  {
    ensureCacheValid();
    if (this.mPartitions[paramInt].cursor == null);
    for (int i = 0; ; i = this.mPartitions[paramInt].cursor.getCount())
      return i;
  }

  public final Cursor getCursor(int paramInt)
  {
    return this.mPartitions[paramInt].cursor;
  }

  public Object getItem(int paramInt)
  {
    ensureCacheValid();
    int i = 0;
    for (int j = 0; ; j++)
    {
      int k = this.mSize;
      Object localObject = null;
      int m;
      int n;
      if (j < k)
      {
        m = i + this.mPartitions[j].count;
        if ((paramInt < i) || (paramInt >= m))
          break label103;
        n = paramInt - i;
        if (this.mPartitions[j].hasHeader)
          n--;
        localObject = null;
        if (n != -1)
          break label79;
      }
      while (true)
      {
        return localObject;
        label79: localObject = this.mPartitions[j].cursor;
        ((Cursor)localObject).moveToPosition(n);
      }
      label103: i = m;
    }
  }

  public long getItemId(int paramInt)
  {
    long l = 0L;
    ensureCacheValid();
    int i = 0;
    for (int j = 0; ; j++)
    {
      int k;
      int m;
      if (j < this.mSize)
      {
        k = i + this.mPartitions[j].count;
        if ((paramInt < i) || (paramInt >= k))
          break label152;
        m = paramInt - i;
        if (this.mPartitions[j].hasHeader)
          m--;
        if (m != -1)
          break label78;
      }
      while (true)
      {
        return l;
        label78: if (this.mPartitions[j].idColumnIndex != -1)
        {
          Cursor localCursor = this.mPartitions[j].cursor;
          if ((localCursor != null) && (!localCursor.isClosed()) && (localCursor.moveToPosition(m)))
            l = localCursor.getLong(this.mPartitions[j].idColumnIndex);
        }
      }
      label152: i = k;
    }
  }

  public int getItemViewType(int paramInt)
  {
    ensureCacheValid();
    int i = 0;
    for (int j = 0; j < this.mSize; j++)
    {
      int k = i + this.mPartitions[j].count;
      if ((paramInt >= i) && (paramInt < k))
      {
        int m = paramInt - i;
        if ((this.mPartitions[j].hasHeader) && (m == 0));
        for (int n = -1; ; n = getItemViewType(j, m))
          return n;
      }
      i = k;
    }
    throw new ArrayIndexOutOfBoundsException(paramInt);
  }

  protected int getItemViewType(int paramInt1, int paramInt2)
  {
    return 1;
  }

  public int getItemViewTypeCount()
  {
    return 1;
  }

  public final int getOffsetInPartition(int paramInt)
  {
    ensureCacheValid();
    int i = 0;
    int j = 0;
    int m;
    int k;
    if (j < this.mSize)
    {
      m = i + this.mPartitions[j].count;
      if ((paramInt >= i) && (paramInt < m))
      {
        k = paramInt - i;
        if (this.mPartitions[j].hasHeader)
          k--;
      }
    }
    while (true)
    {
      return k;
      i = m;
      j++;
      break;
      k = -1;
    }
  }

  public final int getPartitionForPosition(int paramInt)
  {
    ensureCacheValid();
    int i = 0;
    int j = 0;
    int k;
    if (j < this.mSize)
    {
      k = i + this.mPartitions[j].count;
      if ((paramInt < i) || (paramInt >= k));
    }
    while (true)
    {
      return j;
      i = k;
      j++;
      break;
      j = -1;
    }
  }

  public final int getPositionForPartition(int paramInt)
  {
    ensureCacheValid();
    int i = 0;
    for (int j = 0; j < paramInt; j++)
      i += this.mPartitions[j].count;
    return i;
  }

  protected View getView(int paramInt1, Cursor paramCursor, int paramInt2, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView != null);
    for (View localView = paramView; ; localView = newView$54126883(this.mContext, paramInt1, paramCursor, paramViewGroup))
    {
      bindView(localView, paramInt1, paramCursor, paramInt2);
      return localView;
    }
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ensureCacheValid();
    int i = 0;
    View localView;
    for (int j = 0; j < this.mSize; j++)
    {
      int k = i + this.mPartitions[j].count;
      if ((paramInt >= i) && (paramInt < k))
      {
        int m = paramInt - i;
        if (this.mPartitions[j].hasHeader)
          m--;
        if (m == -1)
          if (paramView != null)
          {
            localView = paramView;
            bindHeaderView$3ab248f1(localView);
          }
        while (true)
        {
          if (localView != null)
            break label243;
          throw new NullPointerException("View should not be null, partition: " + j + " position: " + m);
          localView = newHeaderView$4ac0fa28(this.mContext, j, paramViewGroup);
          break;
          if (!this.mPartitions[j].cursor.moveToPosition(m))
            throw new IllegalStateException("Couldn't move cursor to position " + m);
          localView = getView(j, this.mPartitions[j].cursor, m, paramView, paramViewGroup);
        }
      }
      i = k;
    }
    throw new ArrayIndexOutOfBoundsException(paramInt);
    label243: return localView;
  }

  public int getViewTypeCount()
  {
    return 1 + getItemViewTypeCount();
  }

  public boolean isCursorClosingEnabled()
  {
    return true;
  }

  public boolean isEnabled(int paramInt)
  {
    ensureCacheValid();
    int i = 0;
    for (int j = 0; ; j++)
    {
      int k = this.mSize;
      boolean bool = false;
      int m;
      if (j < k)
      {
        m = i + this.mPartitions[j].count;
        if ((paramInt < i) || (paramInt >= m))
          break label85;
        int n = paramInt - i;
        if (!this.mPartitions[j].hasHeader)
          break label75;
        bool = false;
        if (n != 0)
          break label75;
      }
      while (true)
      {
        return bool;
        label75: bool = isEnabled$255f299(j);
      }
      label85: i = m;
    }
  }

  protected boolean isEnabled$255f299(int paramInt)
  {
    return true;
  }

  public final boolean isPartitionEmpty(int paramInt)
  {
    Cursor localCursor = this.mPartitions[paramInt].cursor;
    if ((localCursor == null) || (localCursor.getCount() == 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected View newHeaderView$4ac0fa28(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    return null;
  }

  protected abstract View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup);

  public void notifyDataSetChanged()
  {
    if (this.mNotificationsEnabled)
    {
      this.mNotificationNeeded = false;
      super.notifyDataSetChanged();
    }
    while (true)
    {
      return;
      this.mNotificationNeeded = true;
    }
  }

  public static final class Partition
  {
    int count;
    Cursor cursor;
    boolean hasHeader;
    int idColumnIndex;
    boolean showIfEmpty;

    public Partition(boolean paramBoolean1, boolean paramBoolean2)
    {
      this.showIfEmpty = paramBoolean1;
      this.hasHeader = paramBoolean2;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.common.widget.EsCompositeCursorAdapter
 * JD-Core Version:    0.6.2
 */