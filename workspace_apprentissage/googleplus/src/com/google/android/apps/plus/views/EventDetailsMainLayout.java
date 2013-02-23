package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.fragments.EventActiveState;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventDisplayContent;

public class EventDetailsMainLayout extends ExactLayout
  implements ItemClickListener
{
  private static TextPaint sDescriptionTextPaint;
  private static Paint sDividerPaint;
  private static int sGoingLabelColor;
  private static int sGoingLabelSize;
  private static String sGoingLabelText;
  private static boolean sInitialized;
  private static int sPadding;
  private static String sWentLabelText;
  private ConstrainedTextView mDescriptionTextView;
  private EventDetailOptionRowInstantShare mInstantShareRow;
  private EventActionListener mListener;
  private EventDetailOptionRowLocation mLocationRow;
  private TextView mRsvpLabel;
  private EventRsvpLayout mRsvpLayout;
  private EventDetailOptionRowTime mTimeRow;

  public EventDetailsMainLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventDetailsMainLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventDetailsMainLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sPadding = localResources.getDimensionPixelSize(R.dimen.event_card_padding);
      Paint localPaint = new Paint();
      sDividerPaint = localPaint;
      localPaint.setColor(localResources.getColor(R.color.card_event_divider));
      sDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.event_card_divider_stroke_width));
      TextPaint localTextPaint = new TextPaint();
      sDescriptionTextPaint = localTextPaint;
      localTextPaint.setAntiAlias(true);
      sDescriptionTextPaint.setColor(localResources.getColor(R.color.event_card_details_description_color));
      sDescriptionTextPaint.setTextSize(localResources.getDimension(R.dimen.event_card_details_description_size));
      sDescriptionTextPaint.linkColor = localResources.getColor(R.color.comment_link);
      TextPaintUtils.registerTextPaint(sDescriptionTextPaint, R.dimen.event_card_details_description_size);
      sGoingLabelColor = localResources.getColor(R.color.event_card_details_going_label);
      sGoingLabelSize = localResources.getDimensionPixelSize(R.dimen.event_card_details_going_label_text_size);
      sGoingLabelText = localResources.getString(R.string.event_rsvp_are_you_going);
      sWentLabelText = localResources.getString(R.string.event_rsvp_are_you_going_past);
      sInitialized = true;
    }
    this.mDescriptionTextView = new ConstrainedTextView(paramContext, paramAttributeSet, paramInt);
    this.mDescriptionTextView.setTextPaint(sDescriptionTextPaint);
    this.mDescriptionTextView.setClickListener(this);
    addView(this.mDescriptionTextView);
    this.mTimeRow = new EventDetailOptionRowTime(paramContext, paramAttributeSet, paramInt);
    addView(this.mTimeRow);
    this.mLocationRow = new EventDetailOptionRowLocation(paramContext, paramAttributeSet, paramInt);
    addView(this.mLocationRow);
    this.mInstantShareRow = new EventDetailOptionRowInstantShare(paramContext, paramAttributeSet, paramInt);
    this.mInstantShareRow.setId(R.id.event_instant_share_selection);
    addView(this.mInstantShareRow);
    this.mRsvpLayout = new EventRsvpLayout(paramContext, paramAttributeSet, paramInt);
    addView(this.mRsvpLayout);
    this.mRsvpLayout.setLayoutParams(new ExactLayout.LayoutParams(-1, -2));
    this.mRsvpLayout.setId(R.id.event_rsvp_section);
    this.mRsvpLabel = TextViewUtils.createText(paramContext, paramAttributeSet, paramInt, sGoingLabelSize, sGoingLabelColor, false, true);
    this.mRsvpLabel.setLayoutParams(new ExactLayout.LayoutParams(-1, -2));
    addView(this.mRsvpLabel);
  }

  public final void bind(PlusEvent paramPlusEvent, EventActiveState paramEventActiveState, EventActionListener paramEventActionListener)
  {
    this.mListener = paramEventActionListener;
    String str;
    if ((paramPlusEvent.displayContent != null) && (paramPlusEvent.displayContent.descriptionHtml != null) && (!TextUtils.isEmpty(paramPlusEvent.displayContent.descriptionHtml)))
    {
      this.mDescriptionTextView.setHtmlText(paramPlusEvent.displayContent.descriptionHtml, false);
      long l = System.currentTimeMillis();
      TextView localTextView = this.mRsvpLabel;
      if (!EsEventData.isEventOver(paramPlusEvent, l))
        break label245;
      str = sWentLabelText;
      label75: localTextView.setText(str);
      this.mTimeRow.bind$3ba8bae(paramPlusEvent);
      if ((paramPlusEvent.location == null) && (paramPlusEvent.hangoutInfo == null))
        break label253;
      this.mLocationRow.bind(paramPlusEvent, paramEventActionListener);
      this.mLocationRow.setVisibility(0);
      label121: if ((!paramEventActiveState.isInstantShareAvailable) && (!paramEventActiveState.isInstantShareExpired))
        break label265;
      this.mInstantShareRow.setListener(paramEventActionListener);
      this.mInstantShareRow.bind(paramEventActiveState);
      this.mInstantShareRow.setVisibility(0);
      label159: if ((!EsEventData.canRsvp(paramPlusEvent)) || (paramEventActiveState.eventSource != 1))
        break label277;
      this.mRsvpLayout.bind(paramPlusEvent, paramEventActiveState, paramEventActionListener);
      this.mRsvpLabel.setVisibility(0);
      this.mRsvpLayout.setVisibility(0);
    }
    while (true)
    {
      return;
      if ((paramPlusEvent.description != null) && (!TextUtils.isEmpty(paramPlusEvent.description)))
      {
        this.mDescriptionTextView.setText(paramPlusEvent.description, false);
        break;
      }
      this.mDescriptionTextView.setText(null, false);
      break;
      label245: str = sGoingLabelText;
      break label75;
      label253: this.mLocationRow.setVisibility(8);
      break label121;
      label265: this.mInstantShareRow.setVisibility(8);
      break label159;
      label277: this.mRsvpLabel.setVisibility(8);
      this.mRsvpLayout.setVisibility(8);
    }
  }

  public final void clear()
  {
    this.mTimeRow.clear();
    this.mLocationRow.clear();
    this.mInstantShareRow.clear();
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = 0 + sPadding;
    int m = i - 2 * sPadding;
    int n;
    EventDetailsOptionRowLayout[] arrayOfEventDetailsOptionRowLayout;
    int i1;
    int i3;
    if (this.mDescriptionTextView.getLength() > 0)
    {
      this.mDescriptionTextView.setVisibility(0);
      int i7 = j + 0;
      measure(this.mDescriptionTextView, m - sPadding, -2147483648, i7, 0);
      setCorner(this.mDescriptionTextView, k + sPadding, 0);
      n = 0 + (this.mDescriptionTextView.getMeasuredHeight() + sPadding);
      if (this.mRsvpLabel.getVisibility() != 8)
      {
        measure(this.mRsvpLabel, m, 1073741824, 0, 0);
        setCorner(this.mRsvpLabel, k, n);
        n += this.mRsvpLabel.getMeasuredHeight() + sPadding;
      }
      if (this.mRsvpLayout.getVisibility() != 8)
      {
        measure(this.mRsvpLayout, i, 1073741824, 0, 0);
        setCorner(this.mRsvpLayout, 0, n);
        n += this.mRsvpLayout.getMeasuredHeight();
      }
      arrayOfEventDetailsOptionRowLayout = new EventDetailsOptionRowLayout[3];
      arrayOfEventDetailsOptionRowLayout[0] = this.mInstantShareRow;
      arrayOfEventDetailsOptionRowLayout[1] = this.mTimeRow;
      arrayOfEventDetailsOptionRowLayout[2] = this.mLocationRow;
      i1 = arrayOfEventDetailsOptionRowLayout.length;
      i2 = 0;
      i3 = 0;
      label240: if (i3 >= i1)
        break label293;
      if ((i2 == 0) && (arrayOfEventDetailsOptionRowLayout[i3].getVisibility() != 0))
        break label287;
    }
    label287: for (int i2 = 1; ; i2 = 0)
    {
      i3++;
      break label240;
      this.mDescriptionTextView.setVisibility(8);
      n = 0;
      break;
    }
    label293: int i4 = 0;
    boolean bool1 = true;
    int i5 = 0;
    if (i5 < i1)
    {
      EventDetailsOptionRowLayout localEventDetailsOptionRowLayout = arrayOfEventDetailsOptionRowLayout[i5];
      int i6;
      if (localEventDetailsOptionRowLayout.getVisibility() != 8)
      {
        localEventDetailsOptionRowLayout.setFirst(bool1);
        measure(localEventDetailsOptionRowLayout, i, -2147483648, 0, 0);
        setCorner(localEventDetailsOptionRowLayout, 0, n);
        i6 = localEventDetailsOptionRowLayout.getMeasuredHeight();
        label359: i4 += i6;
        n += i6;
        if ((!bool1) || (i6 != 0))
          break label402;
      }
      label402: for (boolean bool2 = true; ; bool2 = false)
      {
        i5++;
        bool1 = bool2;
        break;
        i6 = 0;
        break label359;
      }
    }
  }

  public final void onSpanClick(URLSpan paramURLSpan)
  {
    if (this.mListener != null)
      this.mListener.onLinkClicked(paramURLSpan.getURL());
  }

  public final void onUserImageClick(String paramString1, String paramString2)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailsMainLayout
 * JD-Core Version:    0.6.2
 */