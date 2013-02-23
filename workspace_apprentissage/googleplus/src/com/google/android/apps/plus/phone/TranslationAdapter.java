package com.google.android.apps.plus.phone;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public abstract class TranslationAdapter
  implements ListAdapter
{
  final TranslationListAdapter mInnerAdapter;

  public TranslationAdapter(TranslationListAdapter paramTranslationListAdapter)
  {
    this.mInnerAdapter = paramTranslationListAdapter;
  }

  public boolean areAllItemsEnabled()
  {
    return this.mInnerAdapter.areAllItemsEnabled();
  }

  public int getCount()
  {
    return this.mInnerAdapter.getCount();
  }

  public Object getItem(int paramInt)
  {
    return this.mInnerAdapter.getItem(translate(paramInt));
  }

  public long getItemId(int paramInt)
  {
    return this.mInnerAdapter.getItemId(translate(paramInt));
  }

  public int getItemViewType(int paramInt)
  {
    return this.mInnerAdapter.getItemViewType(translate(paramInt));
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return this.mInnerAdapter.getView(translate(paramInt), paramView, paramViewGroup);
  }

  public int getViewTypeCount()
  {
    return this.mInnerAdapter.getViewTypeCount();
  }

  public boolean hasStableIds()
  {
    return this.mInnerAdapter.hasStableIds();
  }

  public boolean isEmpty()
  {
    return this.mInnerAdapter.isEmpty();
  }

  public boolean isEnabled(int paramInt)
  {
    return this.mInnerAdapter.isEnabled(translate(paramInt));
  }

  public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    this.mInnerAdapter.registerDataSetObserver(paramDataSetObserver);
  }

  protected abstract int translate(int paramInt);

  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    this.mInnerAdapter.unregisterDataSetObserver(paramDataSetObserver);
  }

  public static abstract interface TranslationListAdapter extends ListAdapter
  {
    public abstract int getColumnCount();

    public abstract int[][] getLayoutArray();

    public abstract boolean isHorizontal();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.TranslationAdapter
 * JD-Core Version:    0.6.2
 */