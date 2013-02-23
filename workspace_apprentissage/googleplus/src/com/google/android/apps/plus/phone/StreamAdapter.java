package com.google.android.apps.plus.phone;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.util.ResourceRedirector;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ColumnGridView.OnScrollListener;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.DummyCardView;
import com.google.android.apps.plus.views.EmotiShareCardView;
import com.google.android.apps.plus.views.EventStreamCardView;
import com.google.android.apps.plus.views.HangoutCardView;
import com.google.android.apps.plus.views.ImageCardView;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.LinksCardView;
import com.google.android.apps.plus.views.PlaceReviewCardView;
import com.google.android.apps.plus.views.Recyclable;
import com.google.android.apps.plus.views.SkyjamCardView;
import com.google.android.apps.plus.views.SquareCardView;
import com.google.android.apps.plus.views.StreamCardView;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;
import com.google.android.apps.plus.views.StreamCardView.ViewedListener;
import com.google.android.apps.plus.views.TextCardView;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

public class StreamAdapter extends EsCompositeCursorAdapter
  implements TranslationAdapter.TranslationListAdapter, StreamCardView.ViewedListener
{
  private static Interpolator sDecelerateInterpolator = new DecelerateInterpolator();
  protected static ScreenMetrics sScreenMetrics;
  protected EsAccount mAccount;
  private int[][] mBoxLayout;
  private int[] mCardTypes;
  private ComposeBarController mComposeBarController;
  private ItemClickListener mItemClickListener;
  protected boolean mLandscape;
  private boolean mMarkPostsAsRead;
  private View.OnClickListener mOnClickListener;
  private StreamCardView.StreamMediaClickListener mStreamMediaClickListener;
  private StreamCardView.StreamPlusBarClickListener mStreamPlusBarClickListener;
  private ViewUseListener mViewUseListener;
  private final Set<String> mViewerHasReadPosts;
  protected int mVisibleIndex = -2147483648;

  public StreamAdapter(final Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    super(paramContext);
    addPartition(false, false);
    addPartition(false, false);
    addPartition(false, false);
    this.mAccount = paramEsAccount;
    this.mOnClickListener = paramOnClickListener;
    this.mItemClickListener = paramItemClickListener;
    this.mStreamPlusBarClickListener = paramStreamPlusBarClickListener;
    this.mStreamMediaClickListener = paramStreamMediaClickListener;
    this.mViewUseListener = paramViewUseListener;
    this.mViewerHasReadPosts = new HashSet();
    this.mMarkPostsAsRead = false;
    int j = paramContext.getResources().getConfiguration().orientation;
    boolean bool = false;
    if (j == 2)
      bool = i;
    this.mLandscape = bool;
    if (sScreenMetrics == null)
      sScreenMetrics = ScreenMetrics.getInstance(paramContext);
    this.mComposeBarController = paramComposeBarController;
    int k;
    if (this.mLandscape)
    {
      k = i;
      paramColumnGridView.setOrientation(k);
      if (sScreenMetrics.screenDisplayType != 0)
        break label235;
    }
    while (true)
    {
      paramColumnGridView.setColumnCount(i);
      paramColumnGridView.setItemMargin(sScreenMetrics.itemMargin);
      paramColumnGridView.setPadding(sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin);
      paramColumnGridView.setOnScrollListener(new ColumnGridView.OnScrollListener()
      {
        public final void onScroll(ColumnGridView paramAnonymousColumnGridView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5)
        {
          if (StreamAdapter.this.mComposeBarController != null)
            StreamAdapter.this.mComposeBarController.onScroll(paramAnonymousColumnGridView, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5);
          if ((Build.VERSION.SDK_INT < 12) || (paramAnonymousInt3 == 0));
          while (true)
          {
            return;
            int i = -2147483648;
            int j = 50;
            int k = 0;
            if (k < paramAnonymousInt3)
            {
              int m = paramAnonymousInt1 + k;
              final View localView;
              ScreenMetrics localScreenMetrics;
              int n;
              label136: int i1;
              int i2;
              float f1;
              label167: float f2;
              label182: float f3;
              if (m > StreamAdapter.this.mVisibleIndex)
              {
                i = Math.max(i, m);
                localView = paramAnonymousColumnGridView.getChildAt(k);
                if (localView.getId() != R.id.compose_bar)
                {
                  localScreenMetrics = ScreenMetrics.getInstance(paramContext);
                  if (paramContext.getResources().getConfiguration().orientation != 2)
                    break label303;
                  n = 1;
                  i1 = (int)localView.getTranslationX();
                  i2 = (int)localView.getTranslationY();
                  if (n == 0)
                    break label309;
                  f1 = localScreenMetrics.longDimension / 3;
                  localView.setTranslationX(f1);
                  if (n == 0)
                    break label315;
                  f2 = 0.0F;
                  localView.setTranslationY(f2);
                  if (n == 0)
                    break label328;
                  f3 = 0.0F;
                  label197: localView.setRotationX(f3);
                  if (n == 0)
                    break label335;
                }
              }
              label303: label309: label315: label328: label335: for (float f4 = -10.0F; ; f4 = 0.0F)
              {
                localView.setRotationY(f4);
                ViewPropertyAnimator localViewPropertyAnimator = localView.animate().rotationX(0.0F).rotationY(0.0F).translationX(i1).translationY(i2).setDuration(500L).setInterpolator(StreamAdapter.sDecelerateInterpolator);
                localViewPropertyAnimator.setListener(new Animator.AnimatorListener()
                {
                  public final void onAnimationCancel(Animator paramAnonymous2Animator)
                  {
                  }

                  public final void onAnimationEnd(Animator paramAnonymous2Animator)
                  {
                    localView.setTranslationX(0.0F);
                    localView.setTranslationY(0.0F);
                    localView.setRotationX(0.0F);
                    localView.setRotationY(0.0F);
                    localView.invalidate();
                  }

                  public final void onAnimationRepeat(Animator paramAnonymous2Animator)
                  {
                  }

                  public final void onAnimationStart(Animator paramAnonymous2Animator)
                  {
                  }
                });
                if (Build.VERSION.SDK_INT >= 14)
                  localViewPropertyAnimator.setStartDelay(j).start();
                j += 50;
                k++;
                break;
                n = 0;
                break label136;
                f1 = 0.0F;
                break label167;
                f2 = localScreenMetrics.longDimension / 3;
                break label182;
                f3 = 10.0F;
                break label197;
              }
            }
            StreamAdapter.this.mVisibleIndex = Math.max(StreamAdapter.this.mVisibleIndex, i);
          }
        }

        public final void onScrollStateChanged(ColumnGridView paramAnonymousColumnGridView, int paramAnonymousInt)
        {
          if (StreamAdapter.this.mComposeBarController != null)
            StreamAdapter.this.mComposeBarController.onScrollStateChanged(paramAnonymousColumnGridView, paramAnonymousInt);
        }
      });
      paramColumnGridView.setRecyclerListener(new ColumnGridView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          if ((paramAnonymousView instanceof Recyclable))
            ((Recyclable)paramAnonymousView).onRecycle();
        }
      });
      return;
      k = 2;
      break;
      label235: i = 2;
    }
  }

  private boolean isBoxStart(int paramInt)
  {
    boolean bool = true;
    int i = -1;
    int j = -1;
    int k = 0;
    if (k < getColumnCount())
      for (int m = 0; ; m++)
      {
        int n;
        if (m < this.mBoxLayout[k].length)
        {
          n = this.mBoxLayout[k][m];
          if (n != paramInt)
            break label71;
          i = k;
          j = m;
        }
        label71: 
        while (n > paramInt)
        {
          if (j >= 0)
            break label83;
          k++;
          break;
        }
      }
    label83: if (this.mLandscape)
      if (j % 2 != 0);
    while (true)
    {
      return bool;
      bool = false;
      continue;
      if (i != 0)
        bool = false;
    }
  }

  private void recreateBoxLayout()
  {
    int i = getCount();
    this.mCardTypes = new int[i];
    int[] arrayOfInt = { getColumnCount(), i * 2 };
    this.mBoxLayout = ((int[][])Array.newInstance(Integer.TYPE, arrayOfInt));
    int j = 0;
    int k = getColumnCount();
    while (j < k)
    {
      int i8 = 0;
      int i9 = i * 2;
      while (i8 < i9)
      {
        this.mBoxLayout[j][i8] = -1;
        i8++;
      }
      j++;
    }
    Cursor localCursor1 = getCursor(0);
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (localCursor1 != null)
    {
      boolean bool3 = localCursor1.moveToFirst();
      m = 0;
      n = 0;
      i1 = 0;
      if (bool3)
        i1 = 0 + localCursor1.getCount();
    }
    if (n < i1)
    {
      if (sScreenMetrics.screenDisplayType == 0)
      {
        this.mCardTypes[n] = 0;
        this.mBoxLayout[0][n] = n;
      }
      while (true)
      {
        n++;
        break;
        this.mCardTypes[n] = 3;
        this.mBoxLayout[0][m] = n;
        this.mBoxLayout[0][(m + 1)] = n;
        this.mBoxLayout[1][m] = n;
        this.mBoxLayout[1][(m + 1)] = n;
        m += 2;
      }
    }
    Cursor localCursor2 = getCursor(1);
    if (localCursor2 != null)
    {
      boolean bool1 = localCursor2.moveToFirst();
      int i5 = 0;
      if (bool1)
        while (n < i)
          if (sScreenMetrics.screenDisplayType == 0)
          {
            this.mCardTypes[n] = 0;
            this.mBoxLayout[0][n] = n;
            n++;
          }
          else
          {
            long l = localCursor2.getLong(15);
            boolean bool2 = (0x200 & l) < 0L;
            int i6 = 0;
            if (bool2)
            {
              i6 = 0;
              if (i5 == 0)
              {
                this.mCardTypes[n] = 3;
                i5 = 15;
                this.mBoxLayout[0][m] = n;
                this.mBoxLayout[0][(m + 1)] = n;
                this.mBoxLayout[1][m] = n;
                this.mBoxLayout[1][(m + 1)] = n;
                i6 = 1;
              }
            }
            if ((i6 == 0) && ((0x400 & l) != 0L))
            {
              if ((i5 & 0x3) != 0)
                break label782;
              i5 |= 3;
              this.mBoxLayout[0][m] = n;
              this.mBoxLayout[0][(m + 1)] = n;
              i6 = 1;
              label477: if (i6 != 0)
                this.mCardTypes[n] = 2;
            }
            if ((i6 == 0) && ((0x800 & l) != 0L))
            {
              if ((i5 & 0x5) != 0)
                break label827;
              i5 |= 5;
              this.mBoxLayout[0][m] = n;
              this.mBoxLayout[1][m] = n;
              i6 = 1;
              label544: if (i6 != 0)
                this.mCardTypes[n] = 1;
            }
            int i7;
            if (i6 == 0)
            {
              this.mCardTypes[n] = 0;
              i7 = 0;
              label573: if (i7 < 4)
              {
                if ((i5 & 1 << i7) != 0)
                  break label874;
                i5 |= 1 << i7;
                this.mBoxLayout[(i7 >> 1)][(m + (i7 & 0x1))] = n;
              }
            }
            String str1;
            label649: String str2;
            label670: String str3;
            label714: StringBuilder localStringBuilder4;
            if (Log.isLoggable("StreamAdapter", 3))
            {
              StringBuilder localStringBuilder1 = new StringBuilder("Box: [");
              if ((i5 & 0x1) != 1)
                break label880;
              str1 = "1";
              StringBuilder localStringBuilder2 = localStringBuilder1.append(str1);
              if ((i5 & 0x2) != 2)
                break label887;
              str2 = "1";
              Log.d("StreamAdapter", str2 + "]");
              StringBuilder localStringBuilder3 = new StringBuilder("     [");
              if ((i5 & 0x4) != 4)
                break label894;
              str3 = "1";
              localStringBuilder4 = localStringBuilder3.append(str3);
              if ((i5 & 0x8) != 8)
                break label901;
            }
            label901: for (String str4 = "1"; ; str4 = "0")
            {
              Log.d("StreamAdapter", str4 + "]");
              if (i5 == 15)
              {
                i5 = 0;
                m += 2;
              }
              localCursor2.moveToNext();
              break;
              label782: if ((i5 & 0xC) != 0)
                break label477;
              i5 |= 12;
              this.mBoxLayout[1][m] = n;
              this.mBoxLayout[1][(m + 1)] = n;
              i6 = 1;
              break label477;
              label827: if ((i5 & 0xA) != 0)
                break label544;
              i5 |= 10;
              this.mBoxLayout[0][(m + 1)] = n;
              this.mBoxLayout[1][(m + 1)] = n;
              i6 = 1;
              break label544;
              label874: i7++;
              break label573;
              label880: str1 = "0";
              break label649;
              label887: str2 = "0";
              break label670;
              label894: str3 = "0";
              break label714;
            }
          }
    }
    if ((Log.isLoggable("StreamAdapter", 3)) && (getColumnCount() > 1))
    {
      Log.d("StreamAdapter", "BoxLayout:");
      int i2 = 0;
      int i3 = i * 2;
      while (i2 < i3)
      {
        Log.d("StreamAdapter", this.mBoxLayout[0][i2] + " " + this.mBoxLayout[0][(i2 + 1)]);
        Log.d("StreamAdapter", this.mBoxLayout[1][i2] + " " + this.mBoxLayout[1][(i2 + 1)]);
        i2 += 2;
      }
      Log.d("StreamAdapter", "CardTypes:");
      for (int i4 = 0; i4 < i; i4++)
        Log.d("StreamAdapter", i4 + ": " + this.mCardTypes[i4]);
    }
  }

  public void bindStreamHeaderView(View paramView, Cursor paramCursor)
  {
  }

  public void bindStreamView(View paramView, Cursor paramCursor)
  {
    int i = paramCursor.getPosition() + getPositionForPartition(1);
    int j = this.mCardTypes[i];
    StreamCardView localStreamCardView = (StreamCardView)paramView;
    if (this.mLandscape);
    for (int k = 1; ; k = 2)
      switch (j)
      {
      default:
        throw new IllegalStateException();
      case 0:
      case 1:
      case 2:
      case 3:
      }
    int m = 1;
    int n = 1;
    while (true)
    {
      ColumnGridView.LayoutParams localLayoutParams = new ColumnGridView.LayoutParams(k, -3, m, n);
      localLayoutParams.isBoxStart = isBoxStart(i);
      if ((!this.mLandscape) && (sScreenMetrics.screenDisplayType == 0) && (((paramView instanceof TextCardView)) || ((paramView instanceof EventStreamCardView))))
        localLayoutParams.height = -2;
      localStreamCardView.setLayoutParams(localLayoutParams);
      localStreamCardView.init(paramCursor, j, 0, this.mOnClickListener, this.mItemClickListener, this, this.mStreamPlusBarClickListener, this.mStreamMediaClickListener);
      if (this.mViewUseListener != null)
        this.mViewUseListener.onViewUsed(i);
      return;
      if (this.mLandscape)
      {
        m = 2;
        label213: if (!this.mLandscape)
          break label232;
      }
      label232: for (n = 1; ; n = 2)
      {
        break;
        m = 1;
        break label213;
      }
      if (this.mLandscape)
      {
        m = 1;
        label248: if (!this.mLandscape)
          break label267;
      }
      label267: for (n = 2; ; n = 1)
      {
        break;
        m = 2;
        break label248;
      }
      m = 2;
      n = 2;
    }
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    if ((paramView instanceof ResourceConsumer))
      ((ResourceConsumer)paramView).unbindResources();
    switch (paramInt1)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      if ((paramView instanceof ResourceConsumer))
        ((ResourceConsumer)paramView).bindResources();
      return;
      bindStreamHeaderView(paramView, paramCursor);
      continue;
      bindStreamView(paramView, paramCursor);
    }
  }

  public final void changeStreamCursor(Cursor paramCursor)
  {
    super.changeCursor(1, paramCursor);
    recreateBoxLayout();
  }

  public final void changeStreamHeaderCursor(Cursor paramCursor)
  {
    int i = getCount(0);
    super.changeCursor(0, paramCursor);
    if (getCount(0) != i)
      recreateBoxLayout();
  }

  public final int getColumnCount()
  {
    if (sScreenMetrics.screenDisplayType == 0);
    for (int i = 1; ; i = 2)
      return i;
  }

  protected final int getItemViewType(int paramInt1, int paramInt2)
  {
    int i;
    switch (paramInt1)
    {
    default:
      i = 0;
    case 0:
    case 1:
    }
    while (true)
    {
      return i;
      i = getStreamHeaderViewType(paramInt2);
      continue;
      i = getStreamItemViewType(paramInt2);
    }
  }

  public final int[][] getLayoutArray()
  {
    return this.mBoxLayout;
  }

  public int getStreamHeaderViewType(int paramInt)
  {
    return 0;
  }

  public int getStreamItemViewType(int paramInt)
  {
    int i = 1;
    Cursor localCursor = getCursor(i);
    localCursor.moveToPosition(paramInt);
    long l = localCursor.getLong(15);
    if ((0x1000 & l) != 0L)
      i = 6;
    while (true)
    {
      return i;
      if ((0x2000 & l) != 0L)
        i = 5;
      else if ((0x4000 & l) != 0L)
        i = 4;
      else if ((0x400000 & l) != 0L)
        i = 9;
      else if ((0x10000 & l) != 0L)
        i = 7;
      else if ((0x300000 & l) != 0L)
        i = 8;
      else if ((0xA0 & l) != 0L)
      {
        if ((0x8004 & l) != 0L)
          i = 2;
        else
          i = 3;
      }
      else if ((0xF & l) == 0L)
        i = 0;
    }
  }

  public int getViewTypeCount()
  {
    return 10;
  }

  public boolean hasStableIds()
  {
    return false;
  }

  public boolean isEmpty()
  {
    int i = 1;
    if (getCount(i) == 0);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean isHorizontal()
  {
    return this.mLandscape;
  }

  public View newStreamHeaderView$4b8874c5(Context paramContext, Cursor paramCursor)
  {
    return null;
  }

  public View newStreamView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    long l = paramCursor.getLong(15);
    Object localObject;
    if ((0x1000 & l) != 0L)
      localObject = new EventStreamCardView(paramContext);
    while (true)
    {
      return localObject;
      if ((0x2000 & l) != 0L)
      {
        localObject = new HangoutCardView(paramContext);
      }
      else if ((0x4000 & l) != 0L)
      {
        localObject = new SkyjamCardView(paramContext);
      }
      else
      {
        ResourceRedirector.getInstance();
        if ((Property.ENABLE_EMOTISHARE.getBoolean()) && ((0x400000 & l) != 0L))
          localObject = new EmotiShareCardView(paramContext);
        else if ((0x10000 & l) != 0L)
          localObject = new PlaceReviewCardView(paramContext);
        else if ((0xA0 & l) != 0L)
        {
          if ((0x8004 & l) != 0L)
            localObject = new LinksCardView(paramContext);
          else
            localObject = new ImageCardView(paramContext);
        }
        else if ((0x300000 & l) != 0L)
          localObject = new SquareCardView(paramContext);
        else if ((0x20000 & l) != 0L)
          localObject = new LinksCardView(paramContext);
        else if ((0xF & l) != 0L)
          localObject = new TextCardView(paramContext);
        else
          localObject = new DummyCardView(paramContext);
      }
    }
  }

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    View localView;
    switch (paramInt)
    {
    default:
      localView = null;
    case 0:
    case 1:
    }
    while (true)
    {
      return localView;
      localView = newStreamHeaderView$4b8874c5(paramContext, paramCursor);
      continue;
      localView = newStreamView(paramContext, paramCursor, paramViewGroup);
    }
  }

  public final void onPause()
  {
    if (!this.mViewerHasReadPosts.isEmpty())
    {
      EsService.markActivitiesAsRead(getContext(), this.mAccount, (String[])this.mViewerHasReadPosts.toArray(new String[this.mViewerHasReadPosts.size()]));
      this.mViewerHasReadPosts.clear();
    }
  }

  public final void onStreamCardViewed(String paramString)
  {
    if (this.mMarkPostsAsRead)
      this.mViewerHasReadPosts.add(paramString);
  }

  public void resetAnimationState()
  {
    this.mVisibleIndex = -2147483648;
  }

  public final void setMarkPostsAsRead(boolean paramBoolean)
  {
    this.mMarkPostsAsRead = paramBoolean;
  }

  public static abstract interface StreamQuery
  {
    public static final String[] PROJECTION_ACTIVITY = { "_id", "activity_id", "author_id", "name", "avatar", "plus_one_data", "total_comment_count", "loc", "created", "public", "spam", "has_read", "can_reshare", "event_data", "popular_post", "content_flags", "annotation_plaintext", "title_plaintext", "original_author_id", "original_author_name" };
    public static final String[] PROJECTION_STREAM = { "_id", "activity_id", "author_id", "name", "avatar", "plus_one_data", "total_comment_count", "loc", "created", "public", "spam", "has_read", "can_reshare", "event_data", "popular_post", "content_flags", "annotation_plaintext", "title_plaintext", "original_author_id", "original_author_name", "last_activity", "source_name", "embed_media", "embed_skyjam", "embed_place_review", "embed_hangout", "embed_appinvite", "embed_square", "embed_emotishare" };
  }

  public static abstract interface ViewUseListener
  {
    public abstract void onViewUsed(int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.StreamAdapter
 * JD-Core Version:    0.6.2
 */