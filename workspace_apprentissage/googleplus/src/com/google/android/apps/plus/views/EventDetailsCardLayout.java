package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.fragments.EventActiveState;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.api.services.plusi.model.PlusEvent;

public class EventDetailsCardLayout extends EsScrollView
  implements View.OnClickListener, Recyclable
{
  private static NinePatchDrawable sBackground;
  private static boolean sInitialized;
  private static int sPadding;
  private static int sPaddingBottom;
  private static int sPaddingLeft;
  private static int sPaddingRight;
  private static int sPaddingTop;
  private static int sScrollingSecondaryPadding;
  private static int sSecondaryPadding;
  private static float sTwoSpanLayoutDividerPercentage;
  private boolean mCardLayout;
  private ContainerView mContainer;
  private boolean mExpanded;
  private EventDetailsHeaderView mHeaderView;
  private boolean mLandscape;
  private EventDetailsMainLayout mMainLayout;
  private EventDetailsSecondaryLayout mSecondaryLayout;
  private boolean mUserClick;

  public EventDetailsCardLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public EventDetailsCardLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public EventDetailsCardLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sSecondaryPadding = localResources.getDimensionPixelSize(R.dimen.event_card_details_secondary_padding);
      sScrollingSecondaryPadding = localResources.getDimensionPixelSize(R.dimen.event_card_details_secondary_scroll_padding);
      sTwoSpanLayoutDividerPercentage = localResources.getDimension(R.dimen.event_card_devails_percent_divider);
      sBackground = (NinePatchDrawable)localResources.getDrawable(R.drawable.bg_tacos);
      sPaddingLeft = (int)localResources.getDimension(R.dimen.card_border_left_padding);
      sPaddingTop = (int)localResources.getDimension(R.dimen.card_border_top_padding);
      sPaddingRight = (int)localResources.getDimension(R.dimen.card_border_right_padding);
      sPaddingBottom = (int)localResources.getDimension(R.dimen.card_border_bottom_padding);
      sPadding = (int)localResources.getDimension(R.dimen.event_card_padding);
      sInitialized = true;
    }
    boolean bool1;
    int j;
    label211: int k;
    if (paramContext.getResources().getConfiguration().orientation == 2)
    {
      bool1 = true;
      this.mLandscape = bool1;
      int i = ScreenMetrics.getInstance(paramContext).screenDisplayType;
      boolean bool2 = false;
      if (i == 1)
        bool2 = true;
      this.mCardLayout = bool2;
      this.mContainer = new ContainerView(paramContext, paramAttributeSet, paramInt);
      ContainerView localContainerView = this.mContainer;
      if (!this.mLandscape)
        break label383;
      j = -2;
      localContainerView.setLayoutParams(new FrameLayout.LayoutParams(-1, j));
      addView(this.mContainer);
      this.mHeaderView = new EventDetailsHeaderView(paramContext, paramAttributeSet, paramInt);
      this.mHeaderView.setId(R.id.event_header_view);
      this.mContainer.addView(this.mHeaderView);
      this.mExpanded = this.mLandscape;
      this.mMainLayout = new EventDetailsMainLayout(paramContext, paramAttributeSet, paramInt);
      if (this.mExpanded)
        this.mContainer.addView(this.mMainLayout);
      this.mSecondaryLayout = new EventDetailsSecondaryLayout(paramContext, paramAttributeSet, paramInt);
      if (this.mExpanded)
        this.mContainer.addView(this.mSecondaryLayout);
      if (!this.mCardLayout)
        break label389;
      k = 2;
      label351: if (!this.mLandscape)
        break label395;
      setLayoutParams(new ColumnGridView.LayoutParams(1, -2, k, k));
    }
    while (true)
    {
      return;
      bool1 = false;
      break;
      label383: j = -1;
      break label211;
      label389: k = 1;
      break label351;
      label395: setLayoutParams(new ColumnGridView.LayoutParams(2, -2, k, k));
    }
  }

  private void toggleExpansion()
  {
    boolean bool = true;
    if (!this.mExpanded)
    {
      this.mContainer.addView(this.mMainLayout);
      this.mContainer.addView(this.mSecondaryLayout);
      this.mHeaderView.setExpandState(bool);
      if (this.mExpanded)
        break label85;
    }
    while (true)
    {
      this.mExpanded = bool;
      return;
      this.mContainer.removeView(this.mMainLayout);
      this.mContainer.removeView(this.mSecondaryLayout);
      this.mHeaderView.setExpandState(false);
      break;
      label85: bool = false;
    }
  }

  public final void bind(PlusEvent paramPlusEvent, EventActiveState paramEventActiveState, EventActionListener paramEventActionListener)
  {
    int i;
    label47: EventDetailsHeaderView localEventDetailsHeaderView;
    if ((!this.mUserClick) && (!paramEventActiveState.hasUserInteracted))
    {
      if ((!this.mLandscape) && (!paramEventActiveState.expanded))
      {
        i = 1;
        if ((i == 0) || (!this.mExpanded))
          break label103;
        toggleExpansion();
      }
    }
    else
    {
      localEventDetailsHeaderView = this.mHeaderView;
      if (!this.mLandscape)
        break label122;
    }
    label103: label122: for (Object localObject = null; ; localObject = this)
    {
      localEventDetailsHeaderView.bind(paramPlusEvent, (Recyclable)localObject, this.mCardLayout, paramEventActionListener);
      this.mMainLayout.bind(paramPlusEvent, paramEventActiveState, paramEventActionListener);
      this.mSecondaryLayout.bind(paramPlusEvent, paramEventActiveState, paramEventActionListener);
      return;
      i = 0;
      break;
      if ((i != 0) || (this.mExpanded))
        break label47;
      toggleExpansion();
      break label47;
    }
  }

  public void onClick(View paramView)
  {
    toggleExpansion();
    this.mUserClick = true;
  }

  public void onDraw(Canvas paramCanvas)
  {
    sBackground.setBounds(0, 0, getMeasuredWidth(), this.mContainer.getMeasuredHeight());
    sBackground.draw(paramCanvas);
    super.onDraw(paramCanvas);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = getMeasuredWidth();
    this.mContainer.layout(0, 0, i, this.mContainer.getMeasuredHeight());
    int j = this.mHeaderView.getMeasuredHeight();
    int k = j + sPadding;
    this.mHeaderView.layout(sPaddingLeft, 0, i, j);
    int n;
    if (this.mExpanded)
    {
      int m = sPaddingLeft + this.mMainLayout.getMeasuredWidth();
      n = k + this.mMainLayout.getMeasuredHeight();
      this.mMainLayout.layout(sPaddingLeft, k, m, n);
      this.mHeaderView.setLayoutType(this.mCardLayout);
      if (!this.mCardLayout)
        break label192;
      this.mContainer.setDivider(m, k);
      this.mSecondaryLayout.layout(m + sSecondaryPadding, k, m + sSecondaryPadding + this.mSecondaryLayout.getMeasuredWidth(), k + this.mSecondaryLayout.getMeasuredHeight());
    }
    while (true)
    {
      return;
      label192: this.mContainer.clearDivider();
      int i1 = n + sScrollingSecondaryPadding;
      this.mSecondaryLayout.layout(sPaddingLeft + sSecondaryPadding, i1, sPaddingLeft + sSecondaryPadding + this.mSecondaryLayout.getMeasuredWidth(), i1 + this.mSecondaryLayout.getMeasuredHeight());
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    if (i == 0)
      i = j;
    int k = sPaddingTop;
    int m = i - (sPaddingLeft + sPaddingRight);
    this.mHeaderView.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 0));
    int n = k + (this.mHeaderView.getMeasuredHeight() + sPadding);
    int i2;
    int i3;
    if (this.mExpanded)
    {
      if (!this.mCardLayout)
        break label272;
      i2 = (int)(m * sTwoSpanLayoutDividerPercentage);
      i3 = m - i2 - 2 * sSecondaryPadding;
      this.mMainLayout.measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 0));
      this.mSecondaryLayout.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 0));
      if (!this.mCardLayout)
        break label289;
      n += Math.max(this.mMainLayout.getMeasuredHeight(), this.mSecondaryLayout.getMeasuredHeight());
    }
    while (true)
    {
      int i1 = n + sPaddingBottom;
      this.mContainer.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), View.MeasureSpec.makeMeasureSpec(Math.max(i1, j), 1073741824));
      if (!this.mLandscape)
      {
        j = i1;
        this.mContainer.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), View.MeasureSpec.makeMeasureSpec(j, 1073741824));
      }
      setMeasuredDimension(m + sPaddingLeft + sPaddingRight, j);
      return;
      label272: i2 = m;
      i3 = m - 2 * sSecondaryPadding;
      break;
      label289: n += this.mMainLayout.getMeasuredHeight() + this.mSecondaryLayout.getMeasuredHeight() + sScrollingSecondaryPadding;
    }
  }

  public void onRecycle()
  {
    this.mHeaderView.onRecycle();
    this.mMainLayout.clear();
    this.mSecondaryLayout.clear();
  }

  private static class ContainerView extends ViewGroup
  {
    private int mDividerLeft;
    private Paint mDividerPaint;
    private int mDividerTop;
    private boolean mDrawDivider;

    public ContainerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      Resources localResources = paramContext.getResources();
      this.mDividerPaint = new Paint();
      this.mDividerPaint.setColor(localResources.getColor(R.color.card_event_divider));
      this.mDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.event_card_divider_stroke_width));
      setWillNotDraw(false);
    }

    public final void clearDivider()
    {
      this.mDrawDivider = false;
    }

    protected void onDraw(Canvas paramCanvas)
    {
      if (this.mDrawDivider)
        paramCanvas.drawLine(this.mDividerLeft, this.mDividerTop, this.mDividerLeft, getMeasuredHeight() - EventDetailsCardLayout.sPaddingBottom, this.mDividerPaint);
      super.onDraw(paramCanvas);
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
    }

    public final void setDivider(int paramInt1, int paramInt2)
    {
      this.mDrawDivider = true;
      this.mDividerLeft = paramInt1;
      this.mDividerTop = paramInt2;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailsCardLayout
 * JD-Core Version:    0.6.2
 */