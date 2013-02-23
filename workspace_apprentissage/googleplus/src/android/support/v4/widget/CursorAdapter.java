package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public abstract class CursorAdapter extends BaseAdapter
  implements CursorFilter.CursorFilterClient, Filterable
{
  protected boolean mAutoRequery;
  protected ChangeObserver mChangeObserver;
  protected Context mContext;
  protected Cursor mCursor;
  protected CursorFilter mCursorFilter;
  protected DataSetObserver mDataSetObserver;
  protected boolean mDataValid;
  protected int mRowIDColumn;

  public CursorAdapter(Context paramContext, Cursor paramCursor, int paramInt)
  {
    init(paramContext, null, 0);
  }

  public CursorAdapter(Context paramContext, Cursor paramCursor, boolean paramBoolean)
  {
    init(paramContext, paramCursor, 2);
  }

  private void init(Context paramContext, Cursor paramCursor, int paramInt)
  {
    int i = 1;
    label25: int j;
    if ((paramInt & 0x1) == i)
    {
      paramInt |= 2;
      this.mAutoRequery = i;
      if (paramCursor == null)
        break label142;
      this.mCursor = paramCursor;
      this.mDataValid = i;
      this.mContext = paramContext;
      if (i == 0)
        break label148;
      j = paramCursor.getColumnIndexOrThrow("_id");
      label56: this.mRowIDColumn = j;
      if ((paramInt & 0x2) != 2)
        break label154;
      this.mChangeObserver = new ChangeObserver();
    }
    for (this.mDataSetObserver = new MyDataSetObserver((byte)0); ; this.mDataSetObserver = null)
    {
      if (i != 0)
      {
        if (this.mChangeObserver != null)
          paramCursor.registerContentObserver(this.mChangeObserver);
        if (this.mDataSetObserver != null)
          paramCursor.registerDataSetObserver(this.mDataSetObserver);
      }
      return;
      this.mAutoRequery = false;
      break;
      label142: i = 0;
      break label25;
      label148: j = -1;
      break label56;
      label154: this.mChangeObserver = null;
    }
  }

  public abstract void bindView(View paramView, Context paramContext, Cursor paramCursor);

  public void changeCursor(Cursor paramCursor)
  {
    Cursor localCursor = swapCursor(paramCursor);
    if (localCursor != null)
      localCursor.close();
  }

  public CharSequence convertToString(Cursor paramCursor)
  {
    if (paramCursor == null);
    for (String str = ""; ; str = paramCursor.toString())
      return str;
  }

  public int getCount()
  {
    if ((this.mDataValid) && (this.mCursor != null));
    for (int i = this.mCursor.getCount(); ; i = 0)
      return i;
  }

  public final Cursor getCursor()
  {
    return this.mCursor;
  }

  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView;
    if (this.mDataValid)
    {
      this.mCursor.moveToPosition(paramInt);
      if (paramView == null)
      {
        localView = newView(this.mContext, this.mCursor, paramViewGroup);
        bindView(localView, this.mContext, this.mCursor);
      }
    }
    while (true)
    {
      return localView;
      localView = paramView;
      break;
      localView = null;
    }
  }

  public Filter getFilter()
  {
    if (this.mCursorFilter == null)
      this.mCursorFilter = new CursorFilter(this);
    return this.mCursorFilter;
  }

  public Object getItem(int paramInt)
  {
    if ((this.mDataValid) && (this.mCursor != null))
      this.mCursor.moveToPosition(paramInt);
    for (Cursor localCursor = this.mCursor; ; localCursor = null)
      return localCursor;
  }

  public long getItemId(int paramInt)
  {
    long l = 0L;
    if ((this.mDataValid) && (this.mCursor != null) && (this.mCursor.moveToPosition(paramInt)))
      l = this.mCursor.getLong(this.mRowIDColumn);
    return l;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (!this.mDataValid)
      throw new IllegalStateException("this should only be called when the cursor is valid");
    if (!this.mCursor.moveToPosition(paramInt))
      throw new IllegalStateException("couldn't move cursor to position " + paramInt);
    if (paramView == null);
    for (View localView = newView(this.mContext, this.mCursor, paramViewGroup); ; localView = paramView)
    {
      bindView(localView, this.mContext, this.mCursor);
      return localView;
    }
  }

  public boolean hasStableIds()
  {
    return true;
  }

  public abstract View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup);

  protected final void onContentChanged()
  {
    if ((this.mAutoRequery) && (this.mCursor != null) && (!this.mCursor.isClosed()))
      this.mDataValid = this.mCursor.requery();
  }

  public final Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence)
  {
    return this.mCursor;
  }

  public Cursor swapCursor(Cursor paramCursor)
  {
    Cursor localCursor;
    if (paramCursor == this.mCursor)
      localCursor = null;
    while (true)
    {
      return localCursor;
      localCursor = this.mCursor;
      if (localCursor != null)
      {
        if (this.mChangeObserver != null)
          localCursor.unregisterContentObserver(this.mChangeObserver);
        if (this.mDataSetObserver != null)
          localCursor.unregisterDataSetObserver(this.mDataSetObserver);
      }
      this.mCursor = paramCursor;
      if (paramCursor != null)
      {
        if (this.mChangeObserver != null)
          paramCursor.registerContentObserver(this.mChangeObserver);
        if (this.mDataSetObserver != null)
          paramCursor.registerDataSetObserver(this.mDataSetObserver);
        this.mRowIDColumn = paramCursor.getColumnIndexOrThrow("_id");
        this.mDataValid = true;
        notifyDataSetChanged();
      }
      else
      {
        this.mRowIDColumn = -1;
        this.mDataValid = false;
        notifyDataSetInvalidated();
      }
    }
  }

  private final class ChangeObserver extends ContentObserver
  {
    public ChangeObserver()
    {
      super();
    }

    public final boolean deliverSelfNotifications()
    {
      return true;
    }

    public final void onChange(boolean paramBoolean)
    {
      CursorAdapter.this.onContentChanged();
    }
  }

  private final class MyDataSetObserver extends DataSetObserver
  {
    private MyDataSetObserver()
    {
    }

    public final void onChanged()
    {
      CursorAdapter.this.mDataValid = true;
      CursorAdapter.this.notifyDataSetChanged();
    }

    public final void onInvalidated()
    {
      CursorAdapter.this.mDataValid = false;
      CursorAdapter.this.notifyDataSetInvalidated();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.CursorAdapter
 * JD-Core Version:    0.6.2
 */