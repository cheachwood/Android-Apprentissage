package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.views.OneUpBaseView.OnMeasuredListener;
import com.google.android.apps.plus.views.OneUpListener;
import com.google.android.apps.plus.views.StreamOneUpActivityView;
import com.google.android.apps.plus.views.StreamOneUpCommentCountView;
import com.google.android.apps.plus.views.StreamOneUpCommentView;
import com.google.android.apps.plus.views.StreamOneUpLeftoverView;
import java.util.HashSet;

public final class StreamOneUpAdapter extends EsCursorAdapter
  implements SettableItemAdapter
{
  private int mActivityPosition = -1;
  private int mContainerHeight;
  private HashSet<String> mFlaggedComments;
  private SparseIntArray mHeights;
  private int mLeftoverPosition = -1;
  private boolean mLoading;
  private final OneUpBaseView.OnMeasuredListener mOnMeasuredListener;
  private final OneUpListener mOneUpListener;

  public StreamOneUpAdapter(Context paramContext, Cursor paramCursor, OneUpListener paramOneUpListener, OneUpBaseView.OnMeasuredListener paramOnMeasuredListener)
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
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    case 4:
    }
    while (true)
    {
      return;
      StreamOneUpActivityView localStreamOneUpActivityView = (StreamOneUpActivityView)paramView;
      localStreamOneUpActivityView.setOneUpClickListener(this.mOneUpListener);
      localStreamOneUpActivityView.setOnMeasureListener(this.mOnMeasuredListener);
      localStreamOneUpActivityView.bind(paramCursor);
      continue;
      String str = paramCursor.getString(5);
      boolean bool = this.mFlaggedComments.contains(str);
      StreamOneUpCommentView localStreamOneUpCommentView = (StreamOneUpCommentView)paramView;
      localStreamOneUpCommentView.setOneUpClickListener(this.mOneUpListener);
      localStreamOneUpCommentView.setOnMeasureListener(this.mOnMeasuredListener);
      localStreamOneUpCommentView.bind(paramCursor, bool);
      continue;
      StreamOneUpCommentCountView localStreamOneUpCommentCountView = (StreamOneUpCommentCountView)paramView;
      localStreamOneUpCommentCountView.setOnMeasureListener(this.mOnMeasuredListener);
      localStreamOneUpCommentCountView.bind(paramCursor);
      continue;
      if (this.mLoading)
        paramView.findViewById(R.id.loading_spinner).setVisibility(0);
      while (true)
      {
        paramView.invalidate();
        paramView.requestLayout();
        break;
        paramView.findViewById(R.id.loading_spinner).setVisibility(8);
      }
      StreamOneUpLeftoverView localStreamOneUpLeftoverView = (StreamOneUpLeftoverView)paramView;
      int i = this.mContainerHeight;
      if (this.mHeights != null)
        for (int j = -1 + this.mHeights.size(); (i > 0) && (j >= 0); j--)
        {
          int k = this.mHeights.keyAt(j);
          i -= this.mHeights.get(k);
        }
      localStreamOneUpLeftoverView.bind(i);
    }
  }

  public final String getAclText()
  {
    int i = this.mActivityPosition;
    String str = null;
    if (i < 0);
    while (true)
    {
      return str;
      Cursor localCursor = (Cursor)getItem(this.mActivityPosition);
      str = null;
      if (localCursor != null)
        str = localCursor.getString(3);
    }
  }

  public final String getActivityAuthorId()
  {
    if ((this.mActivityPosition < 0) || (this.mActivityPosition > getCount()));
    for (String str = null; ; str = ((Cursor)getItem(this.mActivityPosition)).getString(4))
      return str;
  }

  public final int getItemViewType(int paramInt)
  {
    return ((Cursor)getItem(paramInt)).getInt(1);
  }

  public final int getViewTypeCount()
  {
    return 5;
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
    View localView;
    switch (paramCursor.getInt(1))
    {
    default:
      localView = null;
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return localView;
      localView = localLayoutInflater.inflate(R.layout.stream_one_up_activity_view, paramViewGroup, false);
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
    if (this.mLoading != paramBoolean)
    {
      this.mLoading = paramBoolean;
      notifyDataSetChanged();
    }
  }

  public final Cursor swapCursor(Cursor paramCursor)
  {
    this.mActivityPosition = -1;
    if (paramCursor != null)
    {
      int i = paramCursor.getCount();
      this.mHeights = new SparseIntArray(i);
      this.mLeftoverPosition = (i - 1);
      if (paramCursor.moveToFirst())
      {
        if (paramCursor.getInt(1) != 0)
          break label77;
        this.mActivityPosition = paramCursor.getPosition();
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

  public static abstract interface ActivityQuery
  {
    public static final String[] PROJECTION = { "2147483647 AS _id", "0 AS row_type", "activity_id", "acl_display", "author_id", "name", "avatar", "total_comment_count", "plus_one_data", "loc", "created", "is_edited", "modified", "source_id", "source_name", "public", "spam", "can_comment", "can_reshare", "has_muted", "data_state", "content_flags", "annotation", "title", "original_author_id", "original_author_name", "embed_deep_link", "embed_appinvite", "embed_media", "embed_skyjam", "embed_place_review", "embed_hangout", "embed_square", "embed_emotishare" };
  }

  public static abstract interface CommentCountQuery
  {
    public static final String[] PROJECTION = { "2147483646 AS _id", "1 AS row_type", "COUNT(*) AS _count" };
  }

  public static abstract interface CommentQuery
  {
    public static final String[] PROJECTION = { "_id", "2 AS row_type", "author_id", "name", "avatar", "comment_id", "content", "created", "plus_one_data" };
  }

  public static abstract interface LeftoverQuery
  {
    public static final String[] PROJECTION = { "2147483645 AS _id", "4 AS row_type" };
  }

  public static abstract interface LoadingQuery
  {
    public static final String[] PROJECTION = { "0 AS _id", "3 AS row_type" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.StreamOneUpAdapter
 * JD-Core Version:    0.6.2
 */