package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.Rect;
import android.widget.AbsListView.SelectionBoundsAdjuster;

public class PeopleListItemViewV11 extends PeopleListItemView
  implements AbsListView.SelectionBoundsAdjuster
{
  public PeopleListItemViewV11(Context paramContext)
  {
    super(paramContext);
  }

  public void adjustListItemSelectionBounds(Rect paramRect)
  {
    if (this.mSectionHeaderVisible)
      paramRect.top += this.mSectionHeaderHeight;
  }

  protected final void setSectionHeaderBackgroundColor()
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PeopleListItemViewV11
 * JD-Core Version:    0.6.2
 */