package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.Recyclable;
import com.google.android.apps.plus.views.SquareListItemView;
import com.google.android.apps.plus.views.SquareListItemView.OnItemClickListener;

public final class SquareCardAdapter extends EsCompositeCursorAdapter
{
  private static boolean sInitialized;
  private static int sInvitationMinHeight;
  private static int sItemMargin;
  private static float sLargeDisplayTypeSizeCutoff = 6.9F;
  private static int sMinHeight;
  private static int sMinWidth;
  private static int sScreenDisplayType;
  protected EsAccount mAccount;
  private int mCardType = 0;
  private ColumnGridView mColumnGridView;
  private LayoutInflater mInflater;
  private boolean mLandscape;
  protected SquareListItemView.OnItemClickListener mOnItemClickListener;

  public SquareCardAdapter(Context paramContext, EsAccount paramEsAccount, SquareListItemView.OnItemClickListener paramOnItemClickListener, ColumnGridView paramColumnGridView)
  {
    super(paramContext, (byte)0);
    DisplayMetrics localDisplayMetrics;
    if (!sInitialized)
    {
      sInitialized = i;
      WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
      localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      float f1 = localDisplayMetrics.widthPixels / localDisplayMetrics.xdpi;
      float f2 = localDisplayMetrics.heightPixels / localDisplayMetrics.ydpi;
      if (FloatMath.sqrt(f1 * f1 + f2 * f2) < sLargeDisplayTypeSizeCutoff)
        break label317;
    }
    label317: for (int k = i; ; k = 0)
    {
      sScreenDisplayType = k;
      sItemMargin = (int)(paramContext.getResources().getDimension(R.dimen.card_margin_percentage) * Math.min(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels));
      sMinWidth = paramContext.getResources().getDimensionPixelSize(R.dimen.square_card_min_width);
      sMinHeight = paramContext.getResources().getDimensionPixelSize(R.dimen.square_card_min_height);
      sInvitationMinHeight = paramContext.getResources().getDimensionPixelSize(R.dimen.square_card_invitation_min_height);
      this.mAccount = paramEsAccount;
      this.mOnItemClickListener = paramOnItemClickListener;
      this.mColumnGridView = paramColumnGridView;
      addPartition(false, false);
      addPartition(false, false);
      this.mInflater = LayoutInflater.from(paramContext);
      int j = paramContext.getResources().getConfiguration().orientation;
      boolean bool = false;
      if (j == 2)
        bool = i;
      this.mLandscape = bool;
      if (!this.mLandscape)
        break;
      paramColumnGridView.setOrientation(i);
      paramColumnGridView.setColumnCount(-1);
      paramColumnGridView.setMinColumnWidth(sMinHeight);
      paramColumnGridView.setItemMargin(sItemMargin);
      paramColumnGridView.setPadding(sItemMargin, sItemMargin, sItemMargin, sItemMargin);
      paramColumnGridView.setRecyclerListener(new ColumnGridView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          if ((paramAnonymousView instanceof Recyclable))
            ((Recyclable)paramAnonymousView).onRecycle();
        }
      });
      return;
    }
    paramColumnGridView.setOrientation(2);
    if (sScreenDisplayType == 0);
    while (true)
    {
      paramColumnGridView.setColumnCount(i);
      break;
      i = 2;
    }
  }

  private boolean showTallDescriptionHeader()
  {
    int i = 1;
    if ((this.mLandscape) || (sScreenDisplayType == i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    int i = -2;
    int j = 1;
    switch (paramInt1)
    {
    default:
    case 1:
      do
        return;
      while (paramCursor.getPosition() >= getCount(j));
      SquareListItemView localSquareListItemView = (SquareListItemView)paramView;
      label66: int i2;
      if (this.mCardType != 3)
      {
        int m = j;
        int i1 = this.mCardType;
        boolean bool = false;
        if (i1 == 2)
          bool = j;
        localSquareListItemView.init(paramCursor, this.mOnItemClickListener, m, bool);
        if (!this.mLandscape)
          break label150;
        i2 = j;
        label110: if (!this.mLandscape)
          break label156;
      }
      label150: label156: for (int i3 = sMinWidth; ; i3 = i)
      {
        paramView.setLayoutParams(new ColumnGridView.LayoutParams(i2, i3, j, j));
        break;
        int n = 0;
        break label66;
        i2 = 2;
        break label110;
      }
    case 0:
    }
    int k = this.mColumnGridView.getColumnCount();
    if (this.mLandscape);
    while (true)
    {
      if (this.mLandscape)
        i = sMinWidth;
      paramView.setLayoutParams(new ColumnGridView.LayoutParams(j, i, k, k));
      break;
      j = 2;
    }
  }

  public final void changeDescriptionHeaderCursor(Cursor paramCursor)
  {
    super.changeCursor(0, paramCursor);
  }

  public final void changeSquaresCursor(Cursor paramCursor, int paramInt)
  {
    super.changeCursor(1, paramCursor);
    ColumnGridView localColumnGridView;
    if (this.mCardType != paramInt)
    {
      this.mCardType = paramInt;
      if (this.mLandscape)
      {
        localColumnGridView = this.mColumnGridView;
        if (this.mCardType != 1)
          break label58;
      }
    }
    label58: for (int i = sInvitationMinHeight; ; i = sMinHeight)
    {
      localColumnGridView.setMinColumnWidth(i);
      this.mColumnGridView.setSelectionToTop();
      return;
    }
  }

  protected final int getItemViewType(int paramInt1, int paramInt2)
  {
    int i = 1;
    switch (paramInt1)
    {
    default:
      if (this.mCardType != i)
        break;
    case 0:
    }
    while (true)
    {
      return i;
      if (showTallDescriptionHeader())
      {
        i = 3;
      }
      else
      {
        i = 2;
        continue;
        i = 0;
      }
    }
  }

  public final int getViewTypeCount()
  {
    return 4;
  }

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    View localView;
    switch (paramInt)
    {
    default:
      localView = null;
    case 1:
    case 0:
    }
    while (true)
    {
      return localView;
      if (this.mCardType == 1)
      {
        localView = this.mInflater.inflate(R.layout.square_list_invitation_view, paramViewGroup, false);
      }
      else
      {
        localView = this.mInflater.inflate(R.layout.square_list_item_view, paramViewGroup, false);
        continue;
        if (showTallDescriptionHeader())
          localView = this.mInflater.inflate(R.layout.square_description_header_tall, paramViewGroup, false);
        else
          localView = this.mInflater.inflate(R.layout.square_description_header, paramViewGroup, false);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.SquareCardAdapter
 * JD-Core Version:    0.6.2
 */