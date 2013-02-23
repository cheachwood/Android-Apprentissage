package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.EventDestinationCardView;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.Recyclable;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;

public final class EventCardAdapter extends EsCursorAdapter
{
  private static boolean sInitialized;
  private static int sItemMargin;
  private static double sLargeDisplayTypeSizeCutoff = 6.9D;
  private static int sScreenDisplayType;
  protected EsAccount mAccount;
  protected ItemClickListener mItemClickListener;
  private boolean mLandscape;
  protected View.OnClickListener mOnClickListener;

  public EventCardAdapter(Context paramContext, EsAccount paramEsAccount, Cursor paramCursor, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, ColumnGridView paramColumnGridView)
  {
    super(paramContext, null);
    this.mAccount = paramEsAccount;
    this.mOnClickListener = paramOnClickListener;
    this.mItemClickListener = paramItemClickListener;
    int m;
    int k;
    if (!sInitialized)
    {
      sInitialized = i;
      WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      float f1 = localDisplayMetrics.widthPixels / localDisplayMetrics.xdpi;
      float f2 = localDisplayMetrics.heightPixels / localDisplayMetrics.ydpi;
      if (Math.sqrt(f1 * f1 + f2 * f2) >= sLargeDisplayTypeSizeCutoff)
      {
        m = i;
        sScreenDisplayType = m;
        sItemMargin = (int)(paramContext.getResources().getDimension(R.dimen.card_margin_percentage) * Math.min(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels));
      }
    }
    else
    {
      int j = paramContext.getResources().getConfiguration().orientation;
      boolean bool = false;
      if (j == 2)
        bool = i;
      this.mLandscape = bool;
      if (!this.mLandscape)
        break label264;
      k = i;
      label199: paramColumnGridView.setOrientation(k);
      if (sScreenDisplayType != 0)
        break label270;
    }
    while (true)
    {
      paramColumnGridView.setColumnCount(i);
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
      m = 0;
      break;
      label264: k = 2;
      break label199;
      label270: i = 2;
    }
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    EventDestinationCardView localEventDestinationCardView;
    if (paramCursor.getPosition() < getCount())
    {
      localEventDestinationCardView = (EventDestinationCardView)paramView;
      byte[] arrayOfByte = paramCursor.getBlob(1);
      PlusEvent localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(arrayOfByte);
      localEventDestinationCardView.init(paramCursor, sScreenDisplayType, 0, this.mOnClickListener, this.mItemClickListener, null, null, null);
      localEventDestinationCardView.bindData(this.mAccount, localPlusEvent);
      if (!this.mLandscape)
        break label126;
    }
    label126: for (int i = 1; ; i = 2)
    {
      ColumnGridView.LayoutParams localLayoutParams = new ColumnGridView.LayoutParams(i, -3, 1, 1);
      if ((!this.mLandscape) && (sScreenDisplayType == 0))
        localLayoutParams.height = -2;
      localEventDestinationCardView.setLayoutParams(localLayoutParams);
      return;
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return new EventDestinationCardView(paramContext);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EventCardAdapter
 * JD-Core Version:    0.6.2
 */