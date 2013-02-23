package com.android.common.content;

import android.content.ContentProvider;
import android.database.sqlite.SQLiteTransactionListener;

public abstract class SQLiteContentProvider extends ContentProvider
  implements SQLiteTransactionListener
{
  private final ThreadLocal<Boolean> mApplyingBatch = new ThreadLocal();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.common.content.SQLiteContentProvider
 * JD-Core Version:    0.6.2
 */