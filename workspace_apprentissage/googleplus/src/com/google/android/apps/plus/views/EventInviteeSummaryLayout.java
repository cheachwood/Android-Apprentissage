package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsEventData;
import com.google.api.services.plusi.model.EmbedsPerson;
import com.google.api.services.plusi.model.Invitee;
import com.google.api.services.plusi.model.InviteeSummary;
import com.google.api.services.plusi.model.PlusEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventInviteeSummaryLayout extends ExactLayout
{
  private static int sFontColor;
  private static float sFontSize;
  private static String sGuestsFormat;
  private static boolean sInitialized;
  private static String sRsvpInvitedFormat;
  private static String sRsvpInvitedPastFormat;
  private static String sRsvpMaybeFormat;
  private static String sRsvpYesFormat;
  private static String sRsvpYesPastFormat;
  private AvatarLineupLayout mLineupLayout;
  private int mSize;
  private TextView mStatus;
  private int mVisibleSize;

  public EventInviteeSummaryLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventInviteeSummaryLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventInviteeSummaryLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private static int getGaiaIds(PlusEvent paramPlusEvent, String paramString, ArrayList<String> paramArrayList)
  {
    InviteeSummary localInviteeSummary = EsEventData.getInviteeSummary(paramPlusEvent, paramString);
    int i = 0;
    if (localInviteeSummary != null)
    {
      if (localInviteeSummary.invitee != null)
      {
        Iterator localIterator = localInviteeSummary.invitee.iterator();
        while (localIterator.hasNext())
        {
          Invitee localInvitee = (Invitee)localIterator.next();
          if (localInvitee.invitee != null)
          {
            String str = localInvitee.invitee.ownerObfuscatedId;
            if (str != null)
              paramArrayList.add(str);
          }
        }
      }
      i = localInviteeSummary.count.intValue();
    }
    return i;
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sRsvpYesFormat = localResources.getString(R.string.event_detail_rsvp_yes_count);
      sRsvpYesPastFormat = localResources.getString(R.string.event_detail_rsvp_yes_count_past);
      sGuestsFormat = localResources.getString(R.string.event_details_rsvp_guests_count);
      sRsvpMaybeFormat = localResources.getString(R.string.event_detail_rsvp_maybe_count);
      sRsvpInvitedFormat = localResources.getString(R.string.event_detail_rsvp_invited_count);
      sRsvpInvitedPastFormat = localResources.getString(R.string.event_detail_rsvp_invited_count_past);
      sFontSize = localResources.getDimension(R.dimen.event_card_details_rsvp_count_size);
      sFontColor = localResources.getColor(R.color.event_card_details_rsvp_count_color);
      sInitialized = true;
    }
    this.mStatus = TextViewUtils.createText(paramContext, paramAttributeSet, paramInt, sFontSize, sFontColor, false, true);
    addView(this.mStatus);
    this.mLineupLayout = new AvatarLineupLayout(paramContext, paramAttributeSet, paramInt);
    addView(this.mLineupLayout);
    this.mVisibleSize = 0;
    this.mSize = 0;
  }

  public final void bind(PlusEvent paramPlusEvent, EventActionListener paramEventActionListener, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    this.mSize = (0 + getGaiaIds(paramPlusEvent, "ATTENDING", localArrayList) + getGaiaIds(paramPlusEvent, "MAYBE", localArrayList) + getGaiaIds(paramPlusEvent, "NOT_RESPONDED", localArrayList));
    this.mLineupLayout.bindIds(localArrayList, paramEventActionListener, this.mSize);
    this.mVisibleSize = localArrayList.size();
    String str = sGuestsFormat;
    TextView localTextView = this.mStatus;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(this.mSize);
    localTextView.setText(String.format(str, arrayOfObject));
    requestLayout();
  }

  public final void clear()
  {
    this.mVisibleSize = 0;
    this.mSize = 0;
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    measure(this.mStatus, i, -2147483648, 0, 0);
    setCorner(this.mStatus, 0, 0);
    int j = 0 + this.mStatus.getMeasuredHeight();
    if (this.mVisibleSize > 0)
    {
      measure(this.mLineupLayout, i, -2147483648, 0, 0);
      setCorner(this.mLineupLayout, 0, j);
      this.mLineupLayout.getMeasuredHeight();
    }
  }

  public final int size()
  {
    return this.mSize;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventInviteeSummaryLayout
 * JD-Core Version:    0.6.2
 */