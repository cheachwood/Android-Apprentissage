package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.views.OneUpBaseView.OnMeasuredListener;
import com.google.android.apps.plus.views.OneUpListener;
import com.google.android.apps.plus.views.PhotoOneUpInfoView;
import com.google.android.apps.plus.views.StreamOneUpCommentCountView;
import com.google.android.apps.plus.views.StreamOneUpCommentView;
import com.google.android.apps.plus.views.StreamOneUpLeftoverView;
import com.google.api.services.plusi.model.DataPlusOne;
import com.google.api.services.plusi.model.DataPlusOneJson;
import java.io.IOException;
import java.util.HashSet;

public final class PhotoOneUpAdapter extends EsCursorAdapter
  implements SettableItemAdapter
{
  private int mContainerHeight;
  private HashSet<String> mFlaggedComments;
  private SparseIntArray mHeights;
  private int mLeftoverPosition = -1;
  private boolean mLoading;
  private final OneUpBaseView.OnMeasuredListener mOnMeasuredListener;
  private final OneUpListener mOneUpListener;
  private int mPhotoPosition = -1;

  public PhotoOneUpAdapter(Context paramContext, Cursor paramCursor, OneUpListener paramOneUpListener, OneUpBaseView.OnMeasuredListener paramOnMeasuredListener)
  {
    super(paramContext, null);
    this.mOneUpListener = paramOneUpListener;
    this.mOnMeasuredListener = paramOnMeasuredListener;
  }

  public final void addFlaggedComment(String paramString)
  {
    this.mFlaggedComments.add(paramString);
    notifyDataSetChanged();
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    switch (paramCursor.getInt(1))
    {
    case 2:
    default:
    case 0:
    case 4:
    case 1:
    case 3:
    case 5:
    }
    while (true)
    {
      return;
      PhotoOneUpInfoView localPhotoOneUpInfoView = (PhotoOneUpInfoView)paramView;
      localPhotoOneUpInfoView.setOneUpClickListener(this.mOneUpListener);
      localPhotoOneUpInfoView.setOnMeasureListener(this.mOnMeasuredListener);
      localPhotoOneUpInfoView.setOwner(paramCursor.getString(3), paramCursor.getString(4), EsAvatarData.uncompressAvatarUrl(paramCursor.getString(5)));
      localPhotoOneUpInfoView.setCaption(paramCursor.getString(19));
      if (!paramCursor.isNull(11))
        localPhotoOneUpInfoView.setDate(paramCursor.getLong(11));
      localPhotoOneUpInfoView.setPlusOne(paramCursor.getBlob(20));
      localPhotoOneUpInfoView.setAlbum(paramCursor.getString(6));
      localPhotoOneUpInfoView.invalidate();
      localPhotoOneUpInfoView.requestLayout();
      continue;
      StreamOneUpCommentCountView localStreamOneUpCommentCountView = (StreamOneUpCommentCountView)paramView;
      localStreamOneUpCommentCountView.setOnMeasureListener(this.mOnMeasuredListener);
      localStreamOneUpCommentCountView.setCount(paramCursor.getInt(2));
      localStreamOneUpCommentCountView.invalidate();
      localStreamOneUpCommentCountView.requestLayout();
      continue;
      String str = paramCursor.getString(2);
      byte[] arrayOfByte1 = paramCursor.getBlob(9);
      Object localObject = null;
      if (arrayOfByte1 != null);
      try
      {
        byte[] arrayOfByte2 = DbPlusOneData.serialize((DataPlusOne)DataPlusOneJson.getInstance().fromByteArray(arrayOfByte1));
        localObject = arrayOfByte2;
        boolean bool = this.mFlaggedComments.contains(str);
        StreamOneUpCommentView localStreamOneUpCommentView = (StreamOneUpCommentView)paramView;
        localStreamOneUpCommentView.setOneUpClickListener(this.mOneUpListener);
        localStreamOneUpCommentView.setOnMeasureListener(this.mOnMeasuredListener);
        localStreamOneUpCommentView.setAuthor(paramCursor.getString(3), paramCursor.getString(4), EsAvatarData.uncompressAvatarUrl(paramCursor.getString(5)));
        localStreamOneUpCommentView.setComment(str, paramCursor.getString(8), bool);
        localStreamOneUpCommentView.setPlusOne(localObject);
        localStreamOneUpCommentView.setDate(paramCursor.getLong(6));
        localStreamOneUpCommentView.invalidate();
        localStreamOneUpCommentView.requestLayout();
        continue;
        paramView.findViewById(R.id.loading_spinner).setVisibility(0);
        paramView.invalidate();
        paramView.requestLayout();
        continue;
        StreamOneUpLeftoverView localStreamOneUpLeftoverView = (StreamOneUpLeftoverView)paramView;
        int i = this.mContainerHeight;
        if (this.mHeights != null)
          for (int j = -1 + this.mHeights.size(); (i > 0) && (j >= 0); j--)
          {
            int k = this.mHeights.keyAt(j);
            i -= this.mHeights.get(k);
          }
        localStreamOneUpLeftoverView.bind(i);
        localStreamOneUpLeftoverView.invalidate();
        localStreamOneUpLeftoverView.requestLayout();
      }
      catch (IOException localIOException)
      {
        while (true)
          localObject = null;
      }
    }
  }

  public final int getItemViewType(int paramInt)
  {
    return ((Cursor)getItem(paramInt)).getInt(1);
  }

  public final int getViewTypeCount()
  {
    return 6;
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
    View localView;
    switch (paramCursor.getInt(1))
    {
    case 2:
    default:
      localView = null;
    case 0:
    case 4:
    case 1:
    case 3:
    case 5:
    }
    while (true)
    {
      return localView;
      localView = localLayoutInflater.inflate(R.layout.photo_one_up_info_view, paramViewGroup, false);
      continue;
      localView = localLayoutInflater.inflate(R.layout.stream_one_up_comment_count_view, paramViewGroup, false);
      continue;
      localView = localLayoutInflater.inflate(R.layout.stream_one_up_comment_view, paramViewGroup, false);
      continue;
      localView = localLayoutInflater.inflate(R.layout.stream_one_up_loading_view, paramViewGroup, false);
      continue;
      localView = localLayoutInflater.inflate(R.layout.stream_one_up_leftover_view, paramViewGroup, false);
      this.mLeftoverPosition = paramCursor.getPosition();
    }
  }

  public final void removeFlaggedComment(String paramString)
  {
    this.mFlaggedComments.remove(paramString);
    notifyDataSetChanged();
  }

  public final void setContainerHeight(int paramInt)
  {
    this.mContainerHeight = paramInt;
  }

  public final void setFlaggedComments(HashSet<String> paramHashSet)
  {
    this.mFlaggedComments = paramHashSet;
  }

  public final void setItemHeight(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (this.mHeights == null) || (paramInt1 == this.mLeftoverPosition));
    while (true)
    {
      return;
      this.mHeights.put(paramInt1, paramInt2);
    }
  }

  public final void setLoading(boolean paramBoolean)
  {
    if (paramBoolean != this.mLoading)
    {
      this.mLoading = paramBoolean;
      notifyDataSetChanged();
    }
  }

  public final Cursor swapCursor(Cursor paramCursor)
  {
    this.mPhotoPosition = -1;
    if (paramCursor != null)
    {
      int i = paramCursor.getCount();
      this.mHeights = new SparseIntArray(i);
      this.mLeftoverPosition = (i - 1);
      if (paramCursor.moveToFirst())
      {
        if (paramCursor.getInt(1) != 0)
          break label77;
        this.mPhotoPosition = paramCursor.getPosition();
        label64: paramCursor.moveToFirst();
      }
    }
    while (true)
    {
      return super.swapCursor(paramCursor);
      label77: if (paramCursor.moveToNext())
        break;
      break label64;
      this.mHeights = null;
      this.mLeftoverPosition = -1;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PhotoOneUpAdapter
 * JD-Core Version:    0.6.2
 */