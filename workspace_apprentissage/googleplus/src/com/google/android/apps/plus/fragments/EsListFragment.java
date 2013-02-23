package com.google.android.apps.plus.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.google.android.apps.plus.phone.EsCursorAdapter;

abstract class EsListFragment<ListViewType extends AbsListView, AdapterType extends EsCursorAdapter> extends EsFragment
  implements AbsListView.OnScrollListener
{
  protected AdapterType mAdapter;
  protected ListViewType mListView;
  private int mPrevScrollItemCount = -1;
  private int mPrevScrollPosition = -1;
  private int mScrollOffset;
  private int mScrollPos;

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.getCursor() == null) || (this.mAdapter.getCount() == 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mScrollPos = paramBundle.getInt("scroll_pos");
    for (this.mScrollOffset = paramBundle.getInt("scroll_off"); ; this.mScrollOffset = 0)
    {
      return;
      this.mScrollPos = 0;
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle, int paramInt)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, paramInt);
    this.mListView = ((AbsListView)localView.findViewById(16908298));
    this.mListView.setOnScrollListener(this);
    return localView;
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    if (this.mListView != null)
    {
      this.mListView.setOnScrollListener(null);
      this.mListView = null;
    }
  }

  public void onPause()
  {
    super.onPause();
    if ((this.mAdapter != null) && (this.mAdapter.getCursor() != null))
      EsCursorAdapter.onPause();
  }

  public void onResume()
  {
    super.onResume();
    if ((this.mAdapter != null) && (this.mAdapter.getCursor() != null))
      this.mAdapter.onResume();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if ((!getActivity().isFinishing()) && (this.mListView != null))
      if (this.mListView != null)
      {
        this.mScrollPos = this.mListView.getFirstVisiblePosition();
        if (this.mAdapter == null)
          break label97;
        View localView = this.mListView.getChildAt(0);
        if (localView == null)
          break label89;
        this.mScrollOffset = localView.getTop();
      }
    while (true)
    {
      paramBundle.putInt("scroll_pos", this.mScrollPos);
      paramBundle.putInt("scroll_off", this.mScrollOffset);
      return;
      label89: this.mScrollOffset = 0;
      continue;
      label97: this.mScrollOffset = 0;
    }
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 > 0)
    {
      int i = paramInt1 + paramInt2;
      if ((i >= paramInt3) && (i == this.mPrevScrollPosition));
      this.mPrevScrollPosition = i;
      this.mPrevScrollItemCount = paramInt3;
    }
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
  }

  protected final void restoreScrollPosition()
  {
    if (this.mListView == null);
    while (true)
    {
      return;
      if (((this.mListView instanceof ListView)) && ((this.mScrollOffset != 0) || (this.mScrollPos != 0)))
      {
        ((ListView)this.mListView).setSelectionFromTop(this.mScrollPos, this.mScrollOffset);
        this.mScrollPos = 0;
        this.mScrollOffset = 0;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EsListFragment
 * JD-Core Version:    0.6.2
 */