package com.google.android.apps.plus.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EsListView extends ListView
{
  private final DataSetObserver mObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      EsListView.this.adjustFastScroll();
    }
  };

  public EsListView(Context paramContext)
  {
    super(paramContext);
  }

  public EsListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(wrapContextIfNeeded(paramContext, paramAttributeSet), paramAttributeSet);
  }

  public EsListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(wrapContextIfNeeded(paramContext, paramAttributeSet), paramAttributeSet, paramInt);
  }

  private static Context wrapContextIfNeeded(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet == null);
    while (true)
    {
      return paramContext;
      int i = paramAttributeSet.getAttributeResourceValue(null, "theme", 0);
      if (i != 0)
        paramContext = new ContextThemeWrapper(paramContext, i);
    }
  }

  protected final void adjustFastScroll()
  {
    if (!isFastScrollEnabled());
    while (true)
    {
      return;
      setFastScrollEnabled(false);
      setFastScrollEnabled(true);
      int i = getWidth();
      int j = getHeight();
      onSizeChanged(i, j, i, j);
    }
  }

  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (Build.VERSION.SDK_INT >= 11)
      super.setAdapter(paramListAdapter);
    while (true)
    {
      return;
      ListAdapter localListAdapter = getAdapter();
      if (localListAdapter != null)
        localListAdapter.unregisterDataSetObserver(this.mObserver);
      if (paramListAdapter != null)
        paramListAdapter.registerDataSetObserver(this.mObserver);
      super.setAdapter(paramListAdapter);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EsListView
 * JD-Core Version:    0.6.2
 */