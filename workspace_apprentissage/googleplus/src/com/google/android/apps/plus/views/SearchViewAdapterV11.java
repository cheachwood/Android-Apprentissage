package com.google.android.apps.plus.views;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import com.google.android.apps.plus.util.SoftInput;
import java.util.ArrayList;
import java.util.Iterator;

public class SearchViewAdapterV11 extends SearchViewAdapter
  implements SearchView.OnCloseListener, SearchView.OnQueryTextListener
{
  protected final SearchView mSearchView;

  public SearchViewAdapterV11(View paramView)
  {
    super(null);
    this.mSearchView = ((SearchView)paramView);
    this.mSearchView.setSubmitButtonEnabled(false);
    this.mSearchView.setOnQueryTextListener(this);
    this.mSearchView.setOnCloseListener(this);
  }

  public void hideSoftInput()
  {
    SoftInput.hide(this.mSearchView);
  }

  public boolean onClose()
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((SearchViewAdapter.OnQueryChangeListener)localIterator.next()).onQueryClose();
    return true;
  }

  public boolean onQueryTextSubmit(String paramString)
  {
    super.onQueryTextSubmit(paramString);
    SoftInput.hide(this.mSearchView);
    return false;
  }

  public void setQueryHint(int paramInt)
  {
    this.mSearchView.setQueryHint(this.mSearchView.getContext().getString(paramInt));
  }

  public void setQueryText(String paramString)
  {
    this.mSearchView.setQuery(paramString, false);
    if (this.mRequestFocus)
      this.mSearchView.requestFocus();
  }

  public void setVisible(boolean paramBoolean)
  {
    setVisible(paramBoolean, this.mSearchView);
  }

  protected final void showSoftInput()
  {
    this.mSearchView.postDelayed(new Runnable()
    {
      public final void run()
      {
        SearchViewAdapterV11.this.mSearchView.setIconified(false);
      }
    }
    , 50L);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SearchViewAdapterV11
 * JD-Core Version:    0.6.2
 */