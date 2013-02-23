package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.AlbumColumnGridItemView;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.ImageResourceView;
import java.util.HashSet;

public final class AlbumGridViewAdapter extends EsCursorAdapter
{
  private static StateFilter sDefaultFilter = new StateFilter()
  {
    public final int getState(int paramAnonymousInt)
    {
      return 0;
    }
  };
  private final String mAlbumType;
  private final View.OnClickListener mClickListener;
  private StateFilter mFilter = sDefaultFilter;
  private final ColumnGridView mGridView;
  private Handler mHandler = new Handler(Looper.getMainLooper());
  private Boolean mHasDisabledPhotos;
  private final boolean mLandscape;
  private final View.OnLongClickListener mLongClickListener;
  private HashSet<MediaRef> mSelectedMediaRefs;
  private final ViewUseListener mViewUseListener;

  public AlbumGridViewAdapter(Context paramContext, Cursor paramCursor, String paramString, ColumnGridView paramColumnGridView, View.OnClickListener paramOnClickListener, View.OnLongClickListener paramOnLongClickListener, ViewUseListener paramViewUseListener)
  {
    super(paramContext, null);
    int j;
    if (paramContext.getResources().getConfiguration().orientation == 2)
    {
      j = i;
      this.mLandscape = j;
      if (!this.mLandscape)
        break label360;
    }
    while (true)
    {
      paramColumnGridView.setOrientation(i);
      this.mViewUseListener = paramViewUseListener;
      this.mClickListener = paramOnClickListener;
      this.mLongClickListener = paramOnLongClickListener;
      this.mGridView = paramColumnGridView;
      this.mAlbumType = paramString;
      ColumnGridView localColumnGridView = this.mGridView;
      Resources localResources = paramContext.getResources();
      int k = localResources.getDimensionPixelOffset(R.dimen.album_photo_grid_width);
      int m = localResources.getDimensionPixelOffset(R.dimen.album_photo_grid_spacing);
      localColumnGridView.setPadding(m, m, m, m);
      localColumnGridView.setItemMargin(m);
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      int n = localDisplayMetrics.widthPixels;
      int i1 = localDisplayMetrics.heightPixels;
      int i2 = n - m * (-1 + n / k);
      int i3 = i2 / (i2 / k);
      int i4 = i1 - m * (-1 + i1 / k);
      int i5 = Math.max(i3, i4 / (i4 / k));
      int i6 = Math.min(n, i1) / i5;
      if (EsLog.isLoggable("AlbumGridViewAdapter", 3))
      {
        Log.d("AlbumGridViewAdapter", "Usable width: " + n + ", usable height: " + i1);
        Log.d("AlbumGridViewAdapter", "Thumbnail size: " + i5 + ", columns: " + i6);
      }
      localColumnGridView.setColumnCount(i6);
      paramColumnGridView.setRecyclerListener(new ColumnGridView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          ImageResourceView localImageResourceView = (ImageResourceView)paramAnonymousView;
          if (localImageResourceView != null)
          {
            localImageResourceView.onRecycle();
            localImageResourceView.setTag(null);
            localImageResourceView.setOnClickListener(null);
            localImageResourceView.setOnLongClickListener(null);
          }
        }
      });
      return;
      j = 0;
      break;
      label360: i = 2;
    }
  }

  private MediaRef createMediaRef(String paramString1, long paramLong, MediaRef.MediaType paramMediaType, String paramString2)
  {
    if (TextUtils.equals(this.mAlbumType, "camera_photos"));
    for (MediaRef localMediaRef = new MediaRef(paramString1, 0L, null, Uri.parse(paramString2), paramMediaType); ; localMediaRef = new MediaRef(paramString1, paramLong, paramString2, null, paramMediaType))
      return localMediaRef;
  }

  private static MediaRef.MediaType getMediaTypeForRow(Cursor paramCursor)
  {
    MediaRef.MediaType localMediaType;
    if (!paramCursor.isNull(11))
      localMediaType = MediaRef.MediaType.VIDEO;
    while (true)
    {
      return localMediaType;
      if (paramCursor.getInt(12) != 0)
        localMediaType = MediaRef.MediaType.PANORAMA;
      else
        localMediaType = MediaRef.MediaType.IMAGE;
    }
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    AlbumColumnGridItemView localAlbumColumnGridItemView = (AlbumColumnGridItemView)paramView;
    int i = paramCursor.getInt(14);
    final int j;
    int n;
    label200: label215: int i1;
    label228: label243: boolean bool;
    switch (this.mFilter.getState(i))
    {
    default:
      j = paramCursor.getPosition();
      int k = R.string.photo_in_list_count;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(j + 1);
      arrayOfObject[1] = Integer.valueOf(paramCursor.getCount());
      localAlbumColumnGridItemView.setContentDescription(paramContext.getString(k, arrayOfObject));
      localAlbumColumnGridItemView.setTag(R.id.tag_position, Integer.valueOf(j));
      if (!paramCursor.isNull(9))
      {
        String str1 = paramCursor.getString(5);
        long l = paramCursor.getLong(8);
        String str2 = paramCursor.getString(9);
        MediaRef localMediaRef = createMediaRef(str1, l, getMediaTypeForRow(paramCursor), str2);
        localAlbumColumnGridItemView.setMediaRef(localMediaRef);
        localAlbumColumnGridItemView.setTag(localMediaRef);
        if (paramCursor.isNull(4))
        {
          n = 0;
          if (n <= 0)
            break label412;
          localAlbumColumnGridItemView.setPlusOneCount(Integer.valueOf(n));
          if (!paramCursor.isNull(2))
            break label421;
          i1 = 0;
          if (i1 <= 0)
            break label433;
          localAlbumColumnGridItemView.setCommentCount(Integer.valueOf(i1));
          if (paramCursor.isNull(10))
            break label442;
          bool = true;
          label257: localAlbumColumnGridItemView.setNotification(bool);
          if ((this.mSelectedMediaRefs == null) || (!this.mSelectedMediaRefs.contains(localMediaRef)))
            break label448;
          this.mGridView.select(j);
          label292: if (!this.mLandscape)
            break label469;
        }
      }
      break;
    case 0:
    case 1:
    }
    label412: label421: label433: label442: label448: label469: for (int m = 1; ; m = 2)
    {
      paramView.setLayoutParams(new ColumnGridView.LayoutParams(m, -3));
      if ((this.mViewUseListener != null) && (j < getCount()))
        this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            AlbumGridViewAdapter.this.mViewUseListener.onViewUsed(j);
          }
        });
      return;
      localAlbumColumnGridItemView.setOnClickListener(this.mClickListener);
      localAlbumColumnGridItemView.setOnLongClickListener(this.mLongClickListener);
      localAlbumColumnGridItemView.setEnabled(true);
      break;
      localAlbumColumnGridItemView.setOnClickListener(null);
      localAlbumColumnGridItemView.setOnLongClickListener(null);
      localAlbumColumnGridItemView.setEnabled(false);
      break;
      n = paramCursor.getInt(4);
      break label200;
      localAlbumColumnGridItemView.setPlusOneCount(null);
      break label215;
      i1 = paramCursor.getInt(2);
      break label228;
      localAlbumColumnGridItemView.setCommentCount(null);
      break label243;
      bool = false;
      break label257;
      this.mGridView.deselect(j);
      break label292;
      localAlbumColumnGridItemView.setMediaRef(null);
      break label292;
    }
  }

  public final MediaRef getMediaRefForItem(int paramInt)
  {
    Cursor localCursor = getCursor();
    if ((localCursor != null) && (localCursor.moveToPosition(paramInt)));
    for (MediaRef localMediaRef = createMediaRef(localCursor.getString(5), localCursor.getLong(8), getMediaTypeForRow(localCursor), localCursor.getString(9)); ; localMediaRef = null)
      return localMediaRef;
  }

  public final long getTimestampForItem(int paramInt)
  {
    Cursor localCursor = getCursor();
    if ((localCursor != null) && (localCursor.moveToPosition(paramInt)) && (!localCursor.isNull(13)));
    for (long l = localCursor.getLong(13); ; l = 0L)
      return l;
  }

  public final boolean hasStableIds()
  {
    return false;
  }

  public final boolean isAnyPhotoDisabled()
  {
    if (this.mHasDisabledPhotos == null)
    {
      this.mHasDisabledPhotos = Boolean.valueOf(false);
      if (this.mCursor.moveToFirst())
      {
        int i = this.mCursor.getInt(14);
        if (this.mFilter.getState(i) != 1)
          break label69;
        this.mHasDisabledPhotos = Boolean.valueOf(true);
      }
    }
    while (true)
    {
      return this.mHasDisabledPhotos.booleanValue();
      label69: if (this.mCursor.moveToNext())
        break;
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return LayoutInflater.from(paramContext).inflate(R.layout.album_column_grid_view_item, null);
  }

  public final void onResume()
  {
    super.onResume();
    if (this.mGridView != null)
    {
      int i = 0;
      int j = this.mGridView.getChildCount();
      while (i < j)
      {
        ((ImageResourceView)this.mGridView.getChildAt(i)).onResume();
        i++;
      }
      this.mGridView.onResume();
    }
  }

  public final void onStop()
  {
    super.onStop();
    int i = 0;
    int j = this.mGridView.getChildCount();
    while (i < j)
    {
      ((ImageResourceView)this.mGridView.getChildAt(i)).onStop();
      i++;
    }
  }

  public final void setSelectedMediaRefs(HashSet<MediaRef> paramHashSet)
  {
    this.mSelectedMediaRefs = paramHashSet;
  }

  public final void setStateFilter(StateFilter paramStateFilter)
  {
    if (paramStateFilter == null);
    for (this.mFilter = sDefaultFilter; ; this.mFilter = paramStateFilter)
      return;
  }

  public final Cursor swapCursor(Cursor paramCursor)
  {
    this.mHasDisabledPhotos = null;
    return super.swapCursor(paramCursor);
  }

  public static abstract interface StateFilter
  {
    public abstract int getState(int paramInt);
  }

  public static abstract interface ViewUseListener
  {
    public abstract void onViewUsed(int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.AlbumGridViewAdapter
 * JD-Core Version:    0.6.2
 */