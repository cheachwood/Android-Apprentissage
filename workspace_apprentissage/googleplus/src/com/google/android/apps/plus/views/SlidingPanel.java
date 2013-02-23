package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;

public class SlidingPanel extends ScrollableViewGroup
{
  private static Drawable sTabHeaderOverlayLeft;
  private static Drawable sTabHeaderOverlayRight;
  private int[] mChildIndices = new int[0];
  private int mFirstVisiblePanel = -1;
  private int mLastVisiblePanel = -1;
  private OnPanelSelectedListener mOnPanelSelectedListener;
  private int mPanelCount;
  private int mPanelHeight;
  private int mPanelWidth;
  private int mSelectedPanel = 0;
  private Rect mSelectedTabLineBounds = new Rect();
  private int mSelectedTabLineHeight;
  private Paint mSelectedTabLinePaint;
  private TextPaint mSelectedTextPaint;
  private Paint mStripBackgroundPaint;
  private int mStripHeight;
  private Rect mTabLineBounds = new Rect();
  private int mTabLineHeight;
  private Paint mTabLinePaint;
  private CharSequence[] mText = new String[3];
  private int mTextHeight;
  private TextPaint mTextPaint;
  private int[] mTextX = new int[3];
  private int mTextY;
  private int[] mTitleBoldWidths = new int[0];
  private int mTitleSpacing;
  private int[] mTitleWidths = new int[0];
  private CharSequence[] mTitles = new String[0];

  public SlidingPanel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setVertical(false);
    setFlingable(false);
    Resources localResources = paramContext.getResources();
    this.mStripHeight = localResources.getDimensionPixelSize(R.dimen.stream_tab_strip_height);
    this.mTitleSpacing = localResources.getDimensionPixelSize(R.dimen.stream_tab_strip_spacing);
    this.mStripBackgroundPaint = new Paint();
    this.mStripBackgroundPaint.setColor(localResources.getColor(R.color.tab_background_color));
    this.mTabLineHeight = localResources.getDimensionPixelSize(R.dimen.stream_tab_line_height);
    this.mSelectedTabLineHeight = localResources.getDimensionPixelSize(R.dimen.stream_selected_tab_line_height);
    this.mSelectedTabLinePaint = new Paint();
    this.mSelectedTabLinePaint.setColor(localResources.getColor(R.color.stream_tab_line_color));
    int i = localResources.getColor(R.color.tab_text_color);
    float f = localResources.getDimension(R.dimen.stream_tab_text_size);
    this.mTextPaint = createTextPaint(Typeface.DEFAULT, i, f);
    this.mSelectedTextPaint = createTextPaint(Typeface.DEFAULT, i, f);
    Rect localRect = new Rect();
    this.mSelectedTextPaint.getTextBounds("X", 0, 1, localRect);
    this.mTextHeight = localRect.height();
    this.mTabLinePaint = new Paint();
    this.mTabLinePaint.setColor(localResources.getColor(R.color.stream_tab_line_color));
    if (sTabHeaderOverlayLeft == null)
    {
      sTabHeaderOverlayLeft = localResources.getDrawable(R.drawable.stream_tab_overlay_left);
      sTabHeaderOverlayRight = localResources.getDrawable(R.drawable.stream_tab_overlay_right);
    }
  }

  private int computePanelHeaderX(int paramInt1, int paramInt2)
  {
    int i = paramInt2 * this.mPanelWidth;
    int j;
    int m;
    if ((paramInt2 >= 0) && (paramInt2 < this.mPanelCount))
    {
      j = this.mTitleWidths[paramInt2];
      if ((i > paramInt1) || (paramInt1 >= i + this.mPanelWidth))
        break label121;
      int i7 = (this.mPanelWidth - j) / 2;
      int i8 = -1 + this.mPanelCount;
      int i9 = 0;
      if (paramInt2 < i8)
        i9 = Math.min(0, (this.mPanelWidth - this.mTitleWidths[(paramInt2 + 1)]) / 2 - this.mTitleSpacing - j);
      m = interpolate(i7, i9, paramInt1 - i);
    }
    while (true)
    {
      return m;
      j = 0;
      break;
      label121: if ((i + this.mPanelWidth <= paramInt1) && (paramInt1 < i + 2 * this.mPanelWidth))
      {
        int i4 = -1 + this.mPanelCount;
        int i5 = 0;
        if (paramInt2 < i4)
          i5 = Math.min(0, (this.mPanelWidth - this.mTitleWidths[(paramInt2 + 1)]) / 2 - this.mTitleSpacing - j);
        int i6 = -this.mPanelWidth;
        if (paramInt2 < -2 + this.mPanelCount)
          i6 = Math.min(i6, (this.mPanelWidth - this.mTitleWidths[(paramInt2 + 1)]) / 2 - this.mTitleSpacing - j);
        m = interpolate(i5, i6, paramInt1 - i - this.mPanelWidth);
      }
      else if ((i - this.mPanelWidth <= paramInt1) && (paramInt1 < i))
      {
        int i2 = (this.mPanelWidth - j) / 2;
        int i3 = this.mPanelWidth - j;
        if (paramInt2 > 0)
          i3 = Math.max(i3, (this.mTitleWidths[(paramInt2 - 1)] + this.mPanelWidth) / 2 + this.mTitleSpacing);
        m = interpolate(i2, i3, i - paramInt1);
      }
      else
      {
        int k = i - 2 * this.mPanelWidth;
        m = 0;
        if (k <= paramInt1)
        {
          int n = i - this.mPanelWidth;
          m = 0;
          if (paramInt1 < n)
          {
            int i1 = this.mPanelWidth - j;
            if (paramInt2 > 0)
              i1 = Math.max(i1, (this.mTitleWidths[(paramInt2 - 1)] + this.mPanelWidth) / 2 + this.mTitleSpacing);
            m = interpolate(i1, 2 * this.mPanelWidth - j, i - this.mPanelWidth - paramInt1);
          }
        }
      }
    }
  }

  private static TextPaint createTextPaint(Typeface paramTypeface, int paramInt, float paramFloat)
  {
    TextPaint localTextPaint = new TextPaint();
    localTextPaint.setColor(paramInt);
    localTextPaint.setTypeface(paramTypeface);
    localTextPaint.setTextSize(paramFloat);
    localTextPaint.setTextAlign(Paint.Align.LEFT);
    localTextPaint.setAntiAlias(true);
    return localTextPaint;
  }

  private int interpolate(int paramInt1, int paramInt2, int paramInt3)
  {
    float f = paramInt3 / this.mPanelWidth;
    return (int)(paramInt1 + f * (paramInt2 - paramInt1));
  }

  private void update(int paramInt)
  {
    if (this.mPanelWidth == 0)
      return;
    int i = paramInt / this.mPanelWidth;
    int j;
    int m;
    label62: View localView;
    if (paramInt % this.mPanelWidth == 0)
    {
      j = 0;
      int k = i + j;
      if ((i == this.mFirstVisiblePanel) && (k == this.mLastVisiblePanel))
        break label133;
      this.mFirstVisiblePanel = i;
      this.mLastVisiblePanel = k;
      m = 0;
      if (m >= getChildCount())
        break label133;
      localView = getChildAt(m);
      if ((m < this.mChildIndices[this.mFirstVisiblePanel]) || (m > this.mChildIndices[this.mLastVisiblePanel]))
        break label124;
      updateVisibility(localView, 0);
    }
    while (true)
    {
      m++;
      break label62;
      j = 1;
      break;
      label124: updateVisibility(localView, 4);
    }
    label133: int n = this.mSelectedPanel;
    if (n == 0)
    {
      this.mText[0] = null;
      label151: this.mText[1] = this.mTitles[n];
      if (n + 1 >= this.mPanelCount)
        break label340;
      this.mText[2] = this.mTitles[(n + 1)];
    }
    while (true)
    {
      if (n > 0)
        this.mTextX[0] = computePanelHeaderX(paramInt, n - 1);
      this.mTextX[1] = computePanelHeaderX(paramInt, n);
      if (n + 1 < this.mPanelCount)
        this.mTextX[2] = computePanelHeaderX(paramInt, n + 1);
      int i1 = this.mTitleBoldWidths[n] + this.mTitleSpacing;
      if (this.mPanelCount == 1)
        i1 = this.mPanelWidth;
      int i2 = this.mTextX[1] - this.mTitleSpacing / 2;
      this.mSelectedTabLineBounds.set(i2, this.mStripHeight - this.mSelectedTabLineHeight, i2 + i1, this.mStripHeight);
      break;
      this.mText[0] = this.mTitles[(n - 1)];
      break label151;
      label340: this.mText[2] = null;
    }
  }

  private static void updateVisibility(View paramView, int paramInt)
  {
    if (paramView.getVisibility() != paramInt)
      paramView.setVisibility(paramInt);
  }

  public void dispatchDraw(Canvas paramCanvas)
  {
    if ((this.mPanelCount == 0) || (getChildCount() == 0));
    while (true)
    {
      return;
      int i = getScrollX();
      paramCanvas.save();
      paramCanvas.translate(i, 0.0F);
      paramCanvas.drawRect(0.0F, 0.0F, this.mPanelWidth, this.mStripHeight, this.mStripBackgroundPaint);
      int j = 0;
      if (j < this.mText.length)
      {
        CharSequence localCharSequence = this.mText[j];
        if (localCharSequence != null)
          if (j != 1)
            break label126;
        label126: for (TextPaint localTextPaint = this.mSelectedTextPaint; ; localTextPaint = this.mTextPaint)
        {
          paramCanvas.drawText(localCharSequence, 0, localCharSequence.length(), this.mTextX[j], this.mTextY, localTextPaint);
          j++;
          break;
        }
      }
      paramCanvas.drawRect(this.mTabLineBounds, this.mTabLinePaint);
      paramCanvas.drawRect(this.mSelectedTabLineBounds, this.mSelectedTabLinePaint);
      sTabHeaderOverlayLeft.draw(paramCanvas);
      sTabHeaderOverlayRight.draw(paramCanvas);
      paramCanvas.restore();
      super.dispatchDraw(paramCanvas);
    }
  }

  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.mPanelCount == 0) || (getChildCount() == 0))
      return;
    int i = getScrollX();
    this.mPanelWidth = (paramInt3 - paramInt1);
    this.mPanelHeight = (paramInt4 - paramInt2 - this.mStripHeight);
    this.mTextY = (this.mStripHeight / 2 + this.mTextHeight / 2);
    int j = this.mStripHeight;
    int k = j + this.mPanelHeight;
    int m = 0;
    if (m < this.mTitles.length)
    {
      if (this.mChildIndices[m] >= 0)
      {
        View localView = getChildAt(this.mChildIndices[m]);
        int i1 = m * this.mPanelWidth;
        localView.layout(i1, j, i1 + this.mPanelWidth, k);
      }
      CharSequence localCharSequence = this.mTitles[m];
      if (this.mTitles.length == 1);
      for (int n = this.mPanelWidth; ; n = (int)(0.5F * this.mPanelWidth))
      {
        this.mTitles[m] = TextUtils.ellipsize(localCharSequence, this.mSelectedTextPaint, n, TextUtils.TruncateAt.END);
        this.mTitleWidths[m] = ((int)this.mTextPaint.measureText(localCharSequence, 0, localCharSequence.length()));
        this.mTitleBoldWidths[m] = ((int)this.mSelectedTextPaint.measureText(localCharSequence, 0, localCharSequence.length()));
        m++;
        break;
      }
    }
    setScrollLimits(0, this.mPanelWidth * (-1 + this.mPanelCount));
    if (i != this.mSelectedPanel * this.mPanelWidth)
      scrollTo(this.mSelectedPanel * this.mPanelWidth);
    while (true)
    {
      sTabHeaderOverlayLeft.setBounds(0, 0, sTabHeaderOverlayLeft.getIntrinsicWidth(), this.mStripHeight - this.mTabLineHeight);
      sTabHeaderOverlayRight.setBounds(this.mPanelWidth - sTabHeaderOverlayRight.getIntrinsicWidth(), 0, this.mPanelWidth, this.mStripHeight - this.mTabLineHeight);
      this.mTabLineBounds.set(0, this.mStripHeight - this.mTabLineHeight, this.mPanelWidth, this.mStripHeight);
      break;
      update(i);
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    setMeasuredDimension(i, j);
    int k = j - this.mStripHeight;
    int m = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    int n = View.MeasureSpec.makeMeasureSpec(k, 1073741824);
    for (int i1 = 0; i1 < getChildCount(); i1++)
      getChildAt(i1).measure(m, n);
  }

  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    update(paramInt1);
  }

  protected final void onScrollFinished(int paramInt)
  {
    if (this.mPanelWidth == 0)
      return;
    int i = getScrollX();
    if (paramInt < 0);
    for (int j = i / this.mPanelWidth * this.mPanelWidth; ; j = (i + this.mPanelWidth) / this.mPanelWidth * this.mPanelWidth)
    {
      smoothScrollTo(j);
      int k = j / this.mPanelWidth;
      if ((this.mSelectedPanel == k) || (k >= this.mPanelCount))
        break;
      this.mSelectedPanel = k;
      if (this.mOnPanelSelectedListener == null)
        break;
      break;
    }
  }

  public void setIndices(int[] paramArrayOfInt)
  {
    if (this.mTitles.length == 0)
      throw new IllegalArgumentException("setIndices must be called after setTitles!");
    if (this.mTitles.length != paramArrayOfInt.length)
      throw new IllegalArgumentException("mTitles.length must equal indices.length!");
    this.mChildIndices = paramArrayOfInt;
    this.mFirstVisiblePanel = -1;
    this.mLastVisiblePanel = -1;
  }

  public void setOnPanelSelectedListener(OnPanelSelectedListener paramOnPanelSelectedListener)
  {
    this.mOnPanelSelectedListener = paramOnPanelSelectedListener;
  }

  public void setSelectedPanel(int paramInt)
  {
    if ((this.mSelectedPanel != paramInt) && (paramInt < this.mPanelCount))
      this.mSelectedPanel = paramInt;
  }

  public void setTitles(String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    this.mTitles = new String[i];
    this.mTitleWidths = new int[i];
    this.mTitleBoldWidths = new int[i];
    for (int j = 0; j < i; j++)
      this.mTitles[j] = paramArrayOfString[j].toUpperCase();
    this.mPanelCount = i;
    this.mSelectedPanel = 0;
    requestLayout();
  }

  public static abstract interface OnPanelSelectedListener
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SlidingPanel
 * JD-Core Version:    0.6.2
 */