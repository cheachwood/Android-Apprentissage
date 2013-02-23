package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.util.AccessibilityUtils;
import com.google.android.apps.plus.util.EventDateUtils;
import com.google.api.services.plusi.model.EmbedsPostalAddress;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlusEvent;

public class EventDestinationCardView extends CardView
{
  private EventCardDrawer mDrawer = new EventCardDrawer(this);
  private PlusEvent mEvent;
  private boolean mIgnoreHeight;

  public EventDestinationCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public EventDestinationCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public EventDestinationCardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setPaddingEnabled(false);
    setFocusable(true);
  }

  public final void bindData(EsAccount paramEsAccount, PlusEvent paramPlusEvent)
  {
    this.mEvent = paramPlusEvent;
    this.mDrawer.bind(paramEsAccount, this, paramPlusEvent, this.mItemClickListener);
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return this.mDrawer.draw$680d9d43(paramInt2, paramInt2 + paramInt4, paramCanvas);
  }

  public CharSequence getContentDescription()
  {
    Resources localResources = getResources();
    StringBuilder localStringBuilder = new StringBuilder();
    AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mEvent.name);
    AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, EventDateUtils.getDateRange(getContext(), this.mEvent.startTime, null, true));
    String str;
    Object localObject;
    if (this.mEvent.location != null)
    {
      int n = R.string.event_location_accessibility_description;
      Object[] arrayOfObject4 = new Object[1];
      if (this.mEvent.location.address != null)
      {
        str = this.mEvent.location.address.name;
        arrayOfObject4[0] = str;
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, localResources.getString(n, arrayOfObject4));
      }
    }
    else
    {
      int i = EsEventData.getRsvpStatus(this.mEvent);
      localObject = null;
      switch (i)
      {
      default:
      case 1:
      case 3:
      case 2:
      case 0:
      }
    }
    while (true)
    {
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, (CharSequence)localObject);
      return localStringBuilder.toString();
      str = this.mEvent.location.name;
      break;
      int m = R.string.event_rsvp_accessibility_description;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = localResources.getString(R.string.card_event_going_prompt);
      localObject = localResources.getString(m, arrayOfObject3);
      continue;
      int k = R.string.event_rsvp_accessibility_description;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localResources.getString(R.string.card_event_declined_prompt);
      localObject = localResources.getString(k, arrayOfObject2);
      continue;
      int j = R.string.event_rsvp_accessibility_description;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localResources.getString(R.string.card_event_maybe_prompt);
      localObject = localResources.getString(j, arrayOfObject1);
      continue;
      localObject = localResources.getString(R.string.card_event_invited_prompt);
    }
  }

  public final PlusEvent getEvent()
  {
    return this.mEvent;
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return this.mDrawer.layout(paramInt1, paramInt2, this.mIgnoreHeight, paramInt3, paramInt4);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mDrawer.attach();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mDrawer.detach();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    boolean bool;
    if (View.MeasureSpec.getMode(paramInt2) == 0)
    {
      bool = true;
      this.mIgnoreHeight = bool;
      if (!this.mIgnoreHeight)
        break label122;
    }
    label122: for (int k = i; ; k = j)
    {
      int m = layoutElements(sLeftBorderPadding, sTopBorderPadding, i - (sLeftBorderPadding + sRightBorderPadding), k - (sTopBorderPadding + sBottomBorderPadding));
      if (this.mIgnoreHeight)
        k = m + sTopBorderPadding + sBottomBorderPadding + sYPadding;
      setMeasuredDimension(i, k);
      this.mBackgroundRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
      return;
      bool = false;
      break;
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mDrawer.clear();
    this.mEvent = null;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDestinationCardView
 * JD-Core Version:    0.6.2
 */