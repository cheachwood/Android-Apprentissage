package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.widget.ListView;
import com.google.android.apps.plus.R.id;

public class PhotoListView extends ListView
{
  private static final int PHOTO_TAG = R.id.tag_photo_view;

  public PhotoListView(Context paramContext)
  {
    super(paramContext);
  }

  public PhotoListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public PhotoListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    Object localObject = getTag();
    if (!(localObject instanceof SparseArray));
    while (true)
    {
      return super.dispatchTouchEvent(paramMotionEvent);
      ((PhotoHeaderView)((SparseArray)localObject).get(PHOTO_TAG));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoListView
 * JD-Core Version:    0.6.2
 */