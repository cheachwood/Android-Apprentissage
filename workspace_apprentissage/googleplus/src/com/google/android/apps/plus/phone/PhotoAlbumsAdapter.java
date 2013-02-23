package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.ImageResourceView;
import java.util.HashSet;
import java.util.Iterator;

public final class PhotoAlbumsAdapter extends EsCursorAdapter
{
  private final HashSet<ImageResourceView> mImageViews = new HashSet();
  private final boolean mLandscape;
  private View.OnClickListener mOnClickListener;

  public PhotoAlbumsAdapter(Context paramContext, Cursor paramCursor, ColumnGridView paramColumnGridView, View.OnClickListener paramOnClickListener)
  {
    super(paramContext, null);
    this.mOnClickListener = paramOnClickListener;
    int j;
    if (paramContext.getResources().getConfiguration().orientation == 2)
    {
      j = i;
      this.mLandscape = j;
      if (!this.mLandscape)
        break label82;
    }
    while (true)
    {
      paramColumnGridView.setOrientation(i);
      paramColumnGridView.setRecyclerListener(new ColumnGridView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          ImageResourceView localImageResourceView = (ImageResourceView)paramAnonymousView.findViewById(R.id.photo);
          localImageResourceView.onRecycle();
          PhotoAlbumsAdapter.this.mImageViews.remove(localImageResourceView);
          paramAnonymousView.setOnClickListener(null);
        }
      });
      return;
      j = 0;
      break;
      label82: i = 2;
    }
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    ImageResourceView localImageResourceView = (ImageResourceView)paramView.findViewById(R.id.photo);
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.title);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.count);
    Resources localResources = paramContext.getResources();
    paramView.setTag(Integer.valueOf(paramCursor.getPosition()));
    paramView.setOnClickListener(this.mOnClickListener);
    localImageResourceView.setDefaultIconEnabled(true);
    if (!paramCursor.isNull(10))
    {
      localImageResourceView.setMediaRef(new MediaRef(paramCursor.getString(10), MediaRef.MediaType.IMAGE));
      this.mImageViews.add(localImageResourceView);
    }
    String str;
    if (paramCursor.isNull(8))
    {
      str = localResources.getString(R.string.photos_home_unknown_label);
      localTextView1.setText(str);
      localTextView1.setContentDescription(str);
      if (paramCursor.isNull(1))
        break label285;
      Integer localInteger = Integer.valueOf(paramCursor.getInt(1));
      localTextView2.setText(paramContext.getResources().getQuantityString(R.plurals.album_photo_count, localInteger.intValue(), new Object[] { localInteger }).toUpperCase());
      localTextView2.setVisibility(0);
      label208: if (!this.mLandscape)
        break label295;
    }
    label285: label295: for (int i = 1; ; i = 2)
    {
      ColumnGridView.LayoutParams localLayoutParams = new ColumnGridView.LayoutParams(i, -3);
      ScreenMetrics localScreenMetrics = ScreenMetrics.getInstance(paramContext);
      if ((localScreenMetrics.screenDisplayType == 1) && (this.mLandscape))
        localLayoutParams.width = (localScreenMetrics.longDimension / 3);
      paramView.setLayoutParams(localLayoutParams);
      return;
      str = paramCursor.getString(8);
      break;
      localTextView2.setVisibility(8);
      break label208;
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return LayoutInflater.from(paramContext).inflate(R.layout.photo_home_view_item, null);
  }

  public final void onResume()
  {
    super.onResume();
    Iterator localIterator = this.mImageViews.iterator();
    while (localIterator.hasNext())
      ((ImageResourceView)localIterator.next()).onResume();
  }

  public final void onStop()
  {
    super.onStop();
    Iterator localIterator = this.mImageViews.iterator();
    while (localIterator.hasNext())
      ((ImageResourceView)localIterator.next()).onStop();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoAlbumsAdapter
 * JD-Core Version:    0.6.2
 */