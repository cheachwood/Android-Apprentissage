package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.MeasureSpec;
import android.view.animation.AlphaAnimation;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.fragments.EventActiveState;
import com.google.api.services.plusi.model.PlusEvent;

public class EventRsvpLayout extends ExactLayout
  implements EventRsvpListener
{
  private static int sBackgroundColor;
  private static Paint sDividerPaint;
  private static boolean sInitialized;
  private static int sRsvpSectionHeight;
  private boolean mEventOver;
  private EventActionListener mListener;
  private int mMeasuredWidth;
  private EventRsvpButtonLayout mRsvpButtonLayout;
  private EventRsvpSpinnerLayout mRsvpSpinnerLayout;

  public EventRsvpLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventRsvpLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventRsvpLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sBackgroundColor = localResources.getColor(R.color.event_card_details_rsvp_background);
      Paint localPaint1 = new Paint();
      sDividerPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.card_event_divider));
      sDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.event_card_divider_stroke_width));
      Paint localPaint2 = new Paint();
      sDividerPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.card_event_divider));
      sDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.event_card_divider_stroke_width));
      sRsvpSectionHeight = localResources.getDimensionPixelSize(R.dimen.event_card_details_rsvp_height);
      sInitialized = true;
    }
    setBackgroundColor(sBackgroundColor);
    this.mRsvpButtonLayout = ((EventRsvpButtonLayout)((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.event_rsvp_button_layout, null));
    addView(this.mRsvpButtonLayout);
    this.mRsvpSpinnerLayout = new EventRsvpSpinnerLayout(paramContext, paramAttributeSet, paramInt);
    this.mRsvpSpinnerLayout.setVisibility(4);
    addView(this.mRsvpSpinnerLayout);
    setWillNotDraw(false);
  }

  private void setRsvpView(String paramString, boolean paramBoolean)
  {
    int i;
    if ((TextUtils.equals("MAYBE", paramString)) || (TextUtils.equals("NOT_RESPONDED", paramString)))
    {
      i = 1;
      if ((TextUtils.equals("NOT_RESPONDED", paramString)) || ((this.mEventOver) && (i != 0)))
        break label148;
      int j = this.mRsvpButtonLayout.getVisibility();
      this.mRsvpButtonLayout.setVisibility(4);
      this.mRsvpSpinnerLayout.setVisibility(0);
      if ((paramBoolean) && (j == 0))
      {
        AlphaAnimation localAlphaAnimation1 = new AlphaAnimation(1.0F, 0.0F);
        localAlphaAnimation1.setDuration(500L);
        localAlphaAnimation1.setFillAfter(true);
        AlphaAnimation localAlphaAnimation2 = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation2.setDuration(500L);
        localAlphaAnimation2.setFillAfter(true);
        this.mRsvpButtonLayout.startAnimation(localAlphaAnimation1);
        this.mRsvpSpinnerLayout.startAnimation(localAlphaAnimation2);
      }
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label148: this.mRsvpButtonLayout.setVisibility(0);
      this.mRsvpSpinnerLayout.setVisibility(4);
    }
  }

  public final void bind(PlusEvent paramPlusEvent, EventActiveState paramEventActiveState, EventActionListener paramEventActionListener)
  {
    this.mListener = paramEventActionListener;
    this.mEventOver = EsEventData.isEventOver(paramPlusEvent, System.currentTimeMillis());
    this.mRsvpSpinnerLayout.bind(paramPlusEvent, paramEventActiveState, this, paramEventActionListener);
    this.mRsvpButtonLayout.bind(this, this.mEventOver);
    setRsvpView(EsEventData.getRsvpType(paramPlusEvent), false);
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    this.mMeasuredWidth = View.MeasureSpec.getSize(paramInt1);
    int i = sRsvpSectionHeight;
    measure(this.mRsvpButtonLayout, this.mMeasuredWidth, 1073741824, 0, 0);
    int j = Math.max(i, this.mRsvpButtonLayout.getMeasuredHeight());
    measure(this.mRsvpSpinnerLayout, this.mMeasuredWidth, 1073741824, 0, 0);
    int k = Math.max(j, this.mRsvpSpinnerLayout.getMeasuredHeight());
    measure(this.mRsvpButtonLayout, this.mMeasuredWidth, 1073741824, k, 1073741824);
    setCorner(this.mRsvpButtonLayout, 0, 0 + Math.max(0, (k - this.mRsvpButtonLayout.getMeasuredHeight()) / 2));
    measure(this.mRsvpSpinnerLayout, this.mMeasuredWidth, 1073741824, k, 1073741824);
    setCorner(this.mRsvpSpinnerLayout, 0, 0 + Math.max(0, (k - this.mRsvpSpinnerLayout.getMeasuredHeight()) / 2));
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawLine(0.0F, 0.0F, this.mMeasuredWidth, 0.0F, sDividerPaint);
  }

  public final void onRsvpChanged(String paramString)
  {
    if (this.mListener != null)
    {
      setRsvpView(paramString, true);
      this.mListener.onRsvpChanged(paramString);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventRsvpLayout
 * JD-Core Version:    0.6.2
 */