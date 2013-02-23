package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.fragments.EventActiveState;
import com.google.api.services.plusi.model.EventOptions;
import com.google.api.services.plusi.model.PlusEvent;

public class EventDetailsSecondaryLayout extends ExactLayout
  implements View.OnClickListener
{
  private static boolean sInitialized;
  private static int sPadding;
  private static int sSeeInviteesTextColor;
  private static int sSeeInvitesHeight;
  private static String sSeeInvitesText;
  private static float sSeeInvitesTextSize;
  private EventInviteeSummaryLayout mGuestSummary;
  private boolean mHideInvitees;
  private EventActionListener mListener;
  private TextView mViewInvitees;

  public EventDetailsSecondaryLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventDetailsSecondaryLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventDetailsSecondaryLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sPadding = localResources.getDimensionPixelSize(R.dimen.event_card_details_secondary_padding);
      sSeeInviteesTextColor = localResources.getColor(R.color.event_card_details_see_invitees_color);
      sSeeInvitesText = localResources.getString(R.string.event_button_view_all_invitees);
      sSeeInvitesTextSize = localResources.getDimension(R.dimen.event_card_details_see_invitees_size);
      sSeeInvitesHeight = localResources.getDimensionPixelSize(R.dimen.event_card_details_see_invitees_height);
      sInitialized = true;
    }
    this.mGuestSummary = new EventInviteeSummaryLayout(paramContext, paramAttributeSet, paramInt);
    addView(this.mGuestSummary);
    this.mViewInvitees = new TextView(paramContext, paramAttributeSet, paramInt);
    addView(this.mViewInvitees);
    this.mViewInvitees.setText(sSeeInvitesText);
    this.mViewInvitees.setTextSize(0, sSeeInvitesTextSize);
    this.mViewInvitees.setTextColor(sSeeInviteesTextColor);
    this.mViewInvitees.setGravity(17);
    this.mViewInvitees.setClickable(true);
    this.mViewInvitees.setOnClickListener(this);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Theme);
    this.mViewInvitees.setBackgroundDrawable(localTypedArray.getDrawable(5));
    localTypedArray.recycle();
    addPadding(0, 0, 0, sPadding);
  }

  public final void bind(PlusEvent paramPlusEvent, EventActiveState paramEventActiveState, EventActionListener paramEventActionListener)
  {
    boolean bool1 = true;
    boolean bool2;
    if (System.currentTimeMillis() > EsEventData.getEventEndTime(paramPlusEvent))
    {
      bool2 = bool1;
      if ((paramPlusEvent.eventOptions == null) || (paramPlusEvent.eventOptions.hideGuestList == null) || (!paramPlusEvent.eventOptions.hideGuestList.booleanValue()) || (paramEventActiveState.isOwner))
        break label117;
      label55: this.mHideInvitees = bool1;
      if (!this.mHideInvitees)
        break label123;
    }
    label117: label123: for (int i = 8; ; i = 0)
    {
      this.mViewInvitees.setVisibility(i);
      this.mGuestSummary.setVisibility(i);
      this.mGuestSummary.bind(paramPlusEvent, paramEventActionListener, bool2);
      this.mListener = paramEventActionListener;
      invalidate();
      return;
      bool2 = false;
      break;
      bool1 = false;
      break label55;
    }
  }

  public final void clear()
  {
    this.mGuestSummary.clear();
    this.mListener = null;
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    EventInviteeSummaryLayout localEventInviteeSummaryLayout;
    int j;
    if (!this.mHideInvitees)
    {
      localEventInviteeSummaryLayout = this.mGuestSummary;
      if (localEventInviteeSummaryLayout.size() <= 0)
        break label106;
      int m = 0 + sPadding;
      measure(localEventInviteeSummaryLayout, i, 1073741824, 0, 0);
      setCorner(localEventInviteeSummaryLayout, 0, 0);
      j = m + localEventInviteeSummaryLayout.getMeasuredHeight();
      localEventInviteeSummaryLayout.setVisibility(0);
    }
    while (true)
    {
      int k = j + 0;
      measure(this.mViewInvitees, i, 1073741824, sSeeInvitesHeight, 1073741824);
      setCorner(this.mViewInvitees, 0, k);
      this.mViewInvitees.getMeasuredHeight();
      return;
      label106: localEventInviteeSummaryLayout.setVisibility(8);
      j = 0;
    }
  }

  public void onClick(View paramView)
  {
    if ((paramView == this.mViewInvitees) && (this.mListener != null))
      this.mListener.onViewAllInviteesClicked();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailsSecondaryLayout
 * JD-Core Version:    0.6.2
 */