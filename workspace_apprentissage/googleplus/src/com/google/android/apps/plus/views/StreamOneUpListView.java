package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.google.android.apps.plus.fragments.SettableItemAdapter;

public class StreamOneUpListView extends ListView
  implements OneUpBaseView.OnMeasuredListener
{
  private int mMaxWidth = -1;
  private OneUpBaseView.OnMeasuredListener mOnMeasuredListener;

  public StreamOneUpListView(Context paramContext)
  {
    super(paramContext);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public final void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public final void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        if (paramAnonymousInt != 0)
        {
          int i = 0;
          int j = paramAnonymousAbsListView.getChildCount();
          while (i < j)
          {
            View localView = paramAnonymousAbsListView.getChildAt(i);
            if ((localView instanceof StreamOneUpCommentView))
              ((StreamOneUpCommentView)localView).cancelPressedState();
            i++;
          }
        }
      }
    });
  }

  public StreamOneUpListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public final void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public final void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        if (paramAnonymousInt != 0)
        {
          int i = 0;
          int j = paramAnonymousAbsListView.getChildCount();
          while (i < j)
          {
            View localView = paramAnonymousAbsListView.getChildAt(i);
            if ((localView instanceof StreamOneUpCommentView))
              ((StreamOneUpCommentView)localView).cancelPressedState();
            i++;
          }
        }
      }
    });
  }

  public StreamOneUpListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public final void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public final void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        if (paramAnonymousInt != 0)
        {
          int i = 0;
          int j = paramAnonymousAbsListView.getChildCount();
          while (i < j)
          {
            View localView = paramAnonymousAbsListView.getChildAt(i);
            if ((localView instanceof StreamOneUpCommentView))
              ((StreamOneUpCommentView)localView).cancelPressedState();
            i++;
          }
        }
      }
    });
  }

  protected void layoutChildren()
  {
    super.layoutChildren();
    SettableItemAdapter localSettableItemAdapter = (SettableItemAdapter)getAdapter();
    if (localSettableItemAdapter == null);
    while (true)
    {
      return;
      int i = getFirstVisiblePosition();
      for (int j = -1 + getChildCount(); j >= 0; j--)
        localSettableItemAdapter.setItemHeight(i + j, getChildAt(j).getMeasuredHeight());
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mMaxWidth > 0) && (getMeasuredWidth() > this.mMaxWidth))
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824), paramInt2);
    if (this.mOnMeasuredListener != null)
      this.mOnMeasuredListener.onMeasured(this);
  }

  public final void onMeasured(View paramView)
  {
    int i = -1;
    int j = -1 + getChildCount();
    if (j >= 0)
    {
      if (getChildAt(j).equals(paramView))
        i = j;
    }
    else
      if (i >= 0)
        break label38;
    while (true)
    {
      return;
      j--;
      break;
      label38: int k = i + getFirstVisiblePosition();
      SettableItemAdapter localSettableItemAdapter = (SettableItemAdapter)getAdapter();
      if (localSettableItemAdapter != null)
        localSettableItemAdapter.setItemHeight(k, paramView.getMeasuredHeight());
    }
  }

  public void setMaxWidth(int paramInt)
  {
    this.mMaxWidth = paramInt;
  }

  public void setOnMeasureListener(OneUpBaseView.OnMeasuredListener paramOnMeasuredListener)
  {
    this.mOnMeasuredListener = paramOnMeasuredListener;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpListView
 * JD-Core Version:    0.6.2
 */