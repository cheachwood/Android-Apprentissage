package android.support.v4.widget;

import android.database.Cursor;
import android.widget.Filter;
import android.widget.Filter.FilterResults;

final class CursorFilter extends Filter
{
  CursorFilterClient mClient;

  CursorFilter(CursorFilterClient paramCursorFilterClient)
  {
    this.mClient = paramCursorFilterClient;
  }

  public final CharSequence convertResultToString(Object paramObject)
  {
    return this.mClient.convertToString((Cursor)paramObject);
  }

  protected final Filter.FilterResults performFiltering(CharSequence paramCharSequence)
  {
    Cursor localCursor = this.mClient.runQueryOnBackgroundThread(paramCharSequence);
    Filter.FilterResults localFilterResults = new Filter.FilterResults();
    if (localCursor != null)
      localFilterResults.count = localCursor.getCount();
    for (localFilterResults.values = localCursor; ; localFilterResults.values = null)
    {
      return localFilterResults;
      localFilterResults.count = 0;
    }
  }

  protected final void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
  {
    Cursor localCursor = this.mClient.getCursor();
    if ((paramFilterResults.values != null) && (paramFilterResults.values != localCursor))
      this.mClient.changeCursor((Cursor)paramFilterResults.values);
  }

  static abstract interface CursorFilterClient
  {
    public abstract void changeCursor(Cursor paramCursor);

    public abstract CharSequence convertToString(Cursor paramCursor);

    public abstract Cursor getCursor();

    public abstract Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.CursorFilter
 * JD-Core Version:    0.6.2
 */