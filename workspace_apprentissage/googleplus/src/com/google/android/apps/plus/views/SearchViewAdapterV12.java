package com.google.android.apps.plus.views;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.widget.SearchView;
import com.google.android.apps.plus.util.SoftInput;

public final class SearchViewAdapterV12 extends SearchViewAdapterV11
  implements View.OnAttachStateChangeListener
{
  public SearchViewAdapterV12(View paramView)
  {
    super(paramView);
    this.mSearchView.addOnAttachStateChangeListener(this);
  }

  public final void onViewAttachedToWindow(View paramView)
  {
    if ((this.mSearchView.hasFocus()) && (this.mRequestFocus))
      this.mSearchView.postDelayed(new Runnable()
      {
        public final void run()
        {
          SoftInput.show(SearchViewAdapterV12.this.mSearchView);
        }
      }
      , 100L);
  }

  public final void onViewDetachedFromWindow(View paramView)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SearchViewAdapterV12
 * JD-Core Version:    0.6.2
 */