package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.util.TextPaintUtils;

public class StreamOneUpCommentCountView extends OneUpBaseView
  implements Recyclable
{
  private static Paint sBackgroundPaint;
  private static int sCountMarginLeft;
  private static TextPaint sCountPaint;
  private static Paint sDividerPaint;
  private static int sDividerWidth;
  private static int sMarginLeft;
  private static int sMarginRight;
  private boolean mContentDescriptionDirty = true;
  private String mCount;
  private PositionedStaticLayout mCountLayout;
  private RectF mDivider = new RectF();

  public StreamOneUpCommentCountView(Context paramContext)
  {
    super(paramContext);
    if (sBackgroundPaint == null)
    {
      Resources localResources = getContext().getResources();
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sCountMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_count_margin_left);
      sDividerWidth = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_count_divider_width);
      TextPaint localTextPaint = new TextPaint();
      sCountPaint = localTextPaint;
      localTextPaint.setAntiAlias(true);
      sCountPaint.setColor(localResources.getColor(R.color.stream_one_up_comment_count));
      sCountPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_count_text_size));
      TextPaintUtils.registerTextPaint(sCountPaint, R.dimen.stream_one_up_comment_count_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sDividerPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_comment_count_divider));
      sDividerPaint.setStyle(Paint.Style.STROKE);
      sDividerPaint.setStrokeWidth(sDividerWidth);
    }
  }

  public StreamOneUpCommentCountView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sBackgroundPaint == null)
    {
      Resources localResources = getContext().getResources();
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sCountMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_count_margin_left);
      sDividerWidth = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_count_divider_width);
      TextPaint localTextPaint = new TextPaint();
      sCountPaint = localTextPaint;
      localTextPaint.setAntiAlias(true);
      sCountPaint.setColor(localResources.getColor(R.color.stream_one_up_comment_count));
      sCountPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_count_text_size));
      TextPaintUtils.registerTextPaint(sCountPaint, R.dimen.stream_one_up_comment_count_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sDividerPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_comment_count_divider));
      sDividerPaint.setStyle(Paint.Style.STROKE);
      sDividerPaint.setStrokeWidth(sDividerWidth);
    }
  }

  public StreamOneUpCommentCountView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sBackgroundPaint == null)
    {
      Resources localResources = getContext().getResources();
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sCountMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_count_margin_left);
      sDividerWidth = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_count_divider_width);
      TextPaint localTextPaint = new TextPaint();
      sCountPaint = localTextPaint;
      localTextPaint.setAntiAlias(true);
      sCountPaint.setColor(localResources.getColor(R.color.stream_one_up_comment_count));
      sCountPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_count_text_size));
      TextPaintUtils.registerTextPaint(sCountPaint, R.dimen.stream_one_up_comment_count_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sDividerPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_comment_count_divider));
      sDividerPaint.setStyle(Paint.Style.STROKE);
      sDividerPaint.setStrokeWidth(sDividerWidth);
    }
  }

  public final void bind(Cursor paramCursor)
  {
    setCount(paramCursor.getInt(2));
    invalidate();
    requestLayout();
  }

  public void invalidate()
  {
    super.invalidate();
    if (this.mContentDescriptionDirty)
    {
      setContentDescription(this.mCount);
      this.mContentDescriptionDirty = false;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    paramCanvas.drawRect(0.0F, 0.0F, i, j, sBackgroundPaint);
    if (this.mCountLayout != null)
    {
      int k = this.mCountLayout.getLeft();
      int m = this.mCountLayout.getTop();
      paramCanvas.translate(k, m);
      this.mCountLayout.draw(paramCanvas);
      paramCanvas.translate(-k, -m);
      paramCanvas.drawLine(this.mDivider.left, this.mDivider.top, this.mDivider.right, this.mDivider.bottom, sDividerPaint);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getPaddingLeft() + sMarginLeft;
    int j = getPaddingTop();
    int k = getMeasuredWidth();
    int m = k - i - sMarginRight;
    int n = (int)sCountPaint.measureText(this.mCount);
    this.mCountLayout = new PositionedStaticLayout(this.mCount, sCountPaint, n, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
    this.mCountLayout.setPosition(i, j);
    Paint.FontMetricsInt localFontMetricsInt = sCountPaint.getFontMetricsInt();
    int i1 = i + (n + sCountMarginLeft);
    int i2 = j + (localFontMetricsInt.descent - localFontMetricsInt.ascent - sDividerWidth) / 2;
    this.mDivider.set(i1, i2, i + m - sCountMarginLeft, i2);
    setMeasuredDimension(k, j + this.mCountLayout.getHeight() + getPaddingBottom());
    if (this.mOnMeasuredListener != null)
      this.mOnMeasuredListener.onMeasured(this);
  }

  public void onRecycle()
  {
    this.mCountLayout = null;
    this.mCount = null;
  }

  public void setCount(int paramInt)
  {
    Resources localResources = getContext().getResources();
    int i = R.plurals.stream_one_up_comment_count;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    this.mCount = localResources.getQuantityString(i, paramInt, arrayOfObject).toUpperCase(localResources.getConfiguration().locale);
    this.mCountLayout = null;
    this.mContentDescriptionDirty = true;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpCommentCountView
 * JD-Core Version:    0.6.2
 */