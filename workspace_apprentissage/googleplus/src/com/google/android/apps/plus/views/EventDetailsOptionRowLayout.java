package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.styleable;
import java.util.List;

public class EventDetailsOptionRowLayout extends ExactLayout
{
  private static float sDividerHeight;
  private static Paint sDividerPaint;
  private static boolean sInitialized;
  private static int sMinHeight;
  private static int sMinSideWidth;
  private static int sPadding;
  private boolean mFirst;
  private View mLeftView;
  private int mMeasuredHeight;
  private int mMeasuredWidth;
  private View mRightView;
  private EventDetailsOptionTitleDescription mText;

  public EventDetailsOptionRowLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventDetailsOptionRowLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventDetailsOptionRowLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(String paramString1, String paramString2, View paramView1, View paramView2)
  {
    clear();
    this.mLeftView = paramView1;
    this.mRightView = paramView2;
    if (this.mLeftView != null)
      addView(this.mLeftView);
    if (this.mRightView != null)
      addView(this.mRightView);
    this.mText.bind(paramString1, paramString2);
  }

  public final void bind(String paramString, List<String> paramList, View paramView1, View paramView2)
  {
    clear();
    this.mLeftView = paramView1;
    this.mRightView = null;
    if (this.mLeftView != null)
      addView(this.mLeftView);
    if (this.mRightView != null)
      addView(this.mRightView);
    this.mText.bind(paramString, paramList);
  }

  public final void clear()
  {
    if (this.mLeftView != null)
    {
      removeView(this.mLeftView);
      this.mLeftView = null;
    }
    if (this.mRightView != null)
    {
      removeView(this.mRightView);
      this.mRightView = null;
    }
    this.mText.clear();
  }

  protected void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sMinSideWidth = localResources.getDimensionPixelSize(R.dimen.event_card_details_option_min_side_width);
      sMinHeight = localResources.getDimensionPixelSize(R.dimen.event_card_details_option_min_height);
      sPadding = localResources.getDimensionPixelSize(R.dimen.event_card_padding);
      Paint localPaint = new Paint();
      sDividerPaint = localPaint;
      localPaint.setColor(localResources.getColor(R.color.card_event_divider));
      sDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.event_card_divider_stroke_width));
      sDividerHeight = sDividerPaint.getStrokeWidth();
      sInitialized = true;
    }
    this.mText = new EventDetailsOptionTitleDescription(paramContext, paramAttributeSet, paramInt);
    this.mText.setLayoutParams(new ExactLayout.LayoutParams(0, 0));
    addView(this.mText);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Theme);
    setBackgroundDrawable(localTypedArray.getDrawable(5));
    localTypedArray.recycle();
    setWillNotDraw(false);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mFirst)
      paramCanvas.drawLine(0.0F, 0.0F, this.mMeasuredWidth, 0.0F, sDividerPaint);
    int i = Math.round(this.mMeasuredHeight - sDividerHeight);
    paramCanvas.drawLine(0.0F, i, this.mMeasuredWidth, i, sDividerPaint);
    super.onDraw(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = i;
    boolean bool = this.mFirst;
    int m = 0;
    if (bool)
      m = (int)(0.0F + sDividerHeight);
    View localView = this.mLeftView;
    int n = 0;
    if (localView != null)
    {
      measure(this.mLeftView, i, -2147483648, 0, 0);
      int i3 = Math.max(2 * sPadding + this.mLeftView.getMeasuredWidth(), sMinSideWidth);
      setCorner(this.mLeftView, 0, m);
      setCenterBounds(this.mLeftView, i3, 0);
      n = i3 + 0;
      k = i - i3;
    }
    if (this.mRightView != null)
    {
      measure(this.mRightView, k, -2147483648, 0, 0);
      int i2 = Math.max(2 * sPadding + this.mRightView.getMeasuredWidth(), sMinSideWidth);
      setCorner(this.mRightView, i - i2, m);
      setCenterBounds(this.mRightView, i2, 0);
      k -= i2;
    }
    measure(this.mText, k, -2147483648, j, 0);
    setCorner(this.mText, n, m);
    View[] arrayOfView = new View[3];
    arrayOfView[0] = this.mRightView;
    arrayOfView[1] = this.mLeftView;
    arrayOfView[2] = this.mText;
    int i1 = Math.max(sMinHeight, getMaxHeight(arrayOfView) + 2 * sPadding);
    verticallyCenter(i1, arrayOfView);
    this.mMeasuredHeight = ((int)(m + i1 + sDividerHeight));
    this.mMeasuredWidth = i;
    setMeasuredDimension(i, this.mMeasuredHeight);
  }

  public void setFirst(boolean paramBoolean)
  {
    this.mFirst = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventDetailsOptionRowLayout
 * JD-Core Version:    0.6.2
 */