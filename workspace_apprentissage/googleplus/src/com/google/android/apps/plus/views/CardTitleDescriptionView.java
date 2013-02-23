package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.util.TextPaintUtils;

public class CardTitleDescriptionView extends ViewGroup
  implements ItemClickListener
{
  private static TextPaint sDescriptionTextPaint;
  private static boolean sInitialized;
  private Point mDateCorner;
  private TextView mDateTextView;
  private Point mDescriptionCorner;
  private ConstrainedTextView mDescriptionTextView;
  private EventActionListener mListener;
  private Point mTitleCorner;
  private TextView mTitleTextView;

  public CardTitleDescriptionView(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public CardTitleDescriptionView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public CardTitleDescriptionView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    if (!sInitialized)
    {
      TextPaint localTextPaint = new TextPaint();
      sDescriptionTextPaint = localTextPaint;
      localTextPaint.setAntiAlias(true);
      sDescriptionTextPaint.setColor(localResources.getColor(R.color.event_card_activity_description_color));
      sDescriptionTextPaint.setTextSize(localResources.getDimension(R.dimen.event_card_activity_description_size));
      sDescriptionTextPaint.linkColor = localResources.getColor(R.color.comment_link);
      TextPaintUtils.registerTextPaint(sDescriptionTextPaint, R.dimen.event_card_activity_description_size);
      sInitialized = true;
    }
    this.mTitleCorner = new Point();
    this.mTitleTextView = new TextView(paramContext, paramAttributeSet, paramInt);
    this.mTitleTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.mTitleTextView.setTextSize(0, localResources.getDimensionPixelSize(R.dimen.event_card_activity_title_size));
    this.mTitleTextView.setTypeface(null, 1);
    this.mTitleTextView.setTextColor(localResources.getColor(R.color.event_card_activity_title_color));
    this.mTitleTextView.setSingleLine();
    this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
    addView(this.mTitleTextView);
    this.mDateCorner = new Point();
    this.mDateTextView = new TextView(paramContext, paramAttributeSet, paramInt);
    this.mDateTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.mDateTextView.setTextSize(0, localResources.getDimensionPixelSize(R.dimen.event_card_activity_time_size));
    this.mDateTextView.setTextColor(localResources.getColor(R.color.event_card_activity_time_color));
    this.mDateTextView.setSingleLine();
    this.mDateTextView.setEllipsize(TextUtils.TruncateAt.END);
    addView(this.mDateTextView);
    this.mDescriptionCorner = new Point();
    this.mDescriptionTextView = new ConstrainedTextView(paramContext, paramAttributeSet, paramInt);
    this.mDescriptionTextView.setTextPaint(sDescriptionTextPaint);
    this.mDescriptionTextView.setClickListener(this);
    addView(this.mDescriptionTextView);
  }

  public final void clear()
  {
    this.mTitleTextView.setText(null);
    this.mDateTextView.setText(null);
    this.mDescriptionTextView.setText(null);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mTitleTextView.layout(this.mTitleCorner.x, this.mTitleCorner.y, this.mTitleCorner.x + this.mTitleTextView.getMeasuredWidth(), this.mTitleCorner.y + this.mTitleTextView.getMeasuredHeight());
    this.mDateTextView.layout(this.mDateCorner.x, this.mDateCorner.y, this.mDateCorner.x + this.mDateTextView.getMeasuredWidth(), this.mDateCorner.y + this.mDateTextView.getMeasuredHeight());
    this.mDescriptionTextView.layout(this.mDescriptionCorner.x, this.mDescriptionCorner.y, this.mDescriptionCorner.x + this.mDescriptionTextView.getMeasuredWidth(), this.mDescriptionCorner.y + this.mDescriptionTextView.getMeasuredHeight());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = View.MeasureSpec.getMode(paramInt2);
    this.mDateTextView.measure(View.MeasureSpec.makeMeasureSpec(i, -2147483648), View.MeasureSpec.makeMeasureSpec(j, k));
    int m = this.mDateTextView.getMeasuredWidth();
    int n = this.mDateTextView.getMeasuredHeight();
    this.mDateCorner.x = (i - m);
    this.mDateCorner.y = 0;
    this.mTitleTextView.measure(View.MeasureSpec.makeMeasureSpec(this.mDateCorner.x, -2147483648), View.MeasureSpec.makeMeasureSpec(j, k));
    int i1 = this.mTitleTextView.getMeasuredHeight();
    this.mTitleCorner.x = 0;
    this.mTitleCorner.y = 0;
    Point localPoint1 = this.mTitleCorner;
    localPoint1.y += Math.max(0, n - i1);
    Point localPoint2 = this.mDateCorner;
    localPoint2.y += Math.max(0, i1 - n);
    int i2 = 0 + (i1 + this.mTitleCorner.y);
    if (this.mDescriptionTextView.getLength() > 0)
    {
      this.mDescriptionTextView.measure(View.MeasureSpec.makeMeasureSpec(i, -2147483648), View.MeasureSpec.makeMeasureSpec(j - i2, k));
      int i3 = this.mDescriptionTextView.getMeasuredHeight();
      this.mDescriptionCorner.x = 0;
      this.mDescriptionCorner.y = i2;
      i2 += i3;
    }
    setMeasuredDimension(resolveSize(i, paramInt1), resolveSize(i2, paramInt2));
  }

  public final void onSpanClick(URLSpan paramURLSpan)
  {
    if (this.mListener != null)
      this.mListener.onLinkClicked(paramURLSpan.getURL());
  }

  public final void onUserImageClick(String paramString1, String paramString2)
  {
  }

  public void setListener(EventActionListener paramEventActionListener)
  {
    this.mListener = paramEventActionListener;
  }

  public void setText(CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, boolean paramBoolean)
  {
    this.mTitleTextView.setText(paramCharSequence1);
    this.mDateTextView.setText(paramCharSequence2);
    ConstrainedTextView localConstrainedTextView = this.mDescriptionTextView;
    if (paramCharSequence3 != null);
    for (String str = paramCharSequence3.toString(); ; str = null)
    {
      localConstrainedTextView.setHtmlText(str, paramBoolean);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CardTitleDescriptionView
 * JD-Core Version:    0.6.2
 */