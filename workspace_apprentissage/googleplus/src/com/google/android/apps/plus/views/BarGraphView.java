package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.TextPaintUtils;

public class BarGraphView extends View
{
  protected static int BAR_GRAPH_HEIGHT;
  protected static int BAR_SPACING;
  protected static int LABEL_BAR_SPACING;
  protected static int TOTAL_GRAPH_SPACING;
  protected static Paint sBarGraphPaint;
  protected static TextPaint sLabelTextPaint;
  protected static TextPaint sTotalTextPaint;
  protected static TextPaint sValueTextPaint;
  protected InternalRowInfo[] mInternalRowInfos;
  protected long mMaxValue;
  protected StaticLayout mTotalLayout;
  protected long mTotalValue;
  protected String mUnits;

  public BarGraphView(Context paramContext)
  {
    this(paramContext, null);
  }

  public BarGraphView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public BarGraphView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sTotalTextPaint == null)
    {
      Resources localResources = paramContext.getResources();
      TextPaint localTextPaint1 = new TextPaint();
      sTotalTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sTotalTextPaint.setColor(localResources.getColor(R.color.bar_graph_total));
      sTotalTextPaint.setTextSize(localResources.getDimension(R.dimen.bar_graph_total_text_size));
      sTotalTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      TextPaintUtils.registerTextPaint(sTotalTextPaint, R.dimen.bar_graph_total_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sLabelTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sLabelTextPaint.setColor(localResources.getColor(R.color.bar_graph_label));
      sLabelTextPaint.setTextSize(localResources.getDimension(R.dimen.bar_graph_label_text_size));
      sLabelTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      TextPaintUtils.registerTextPaint(sLabelTextPaint, R.dimen.bar_graph_label_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sValueTextPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sValueTextPaint.setColor(localResources.getColor(R.color.bar_graph_value));
      sValueTextPaint.setTextSize(localResources.getDimension(R.dimen.bar_graph_value_text_size));
      sValueTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      TextPaintUtils.registerTextPaint(sValueTextPaint, R.dimen.bar_graph_value_text_size);
      Paint localPaint = new Paint();
      sBarGraphPaint = localPaint;
      localPaint.setColor(localResources.getColor(R.color.bar_graph_bar));
      sBarGraphPaint.setStyle(Paint.Style.FILL);
      TOTAL_GRAPH_SPACING = (int)localResources.getDimension(R.dimen.bar_graph_total_graph_spacing);
      BAR_GRAPH_HEIGHT = (int)localResources.getDimension(R.dimen.bar_graph_bar_height);
      LABEL_BAR_SPACING = (int)localResources.getDimension(R.dimen.bar_graph_label_text_bar_spacing);
      BAR_SPACING = (int)localResources.getDimension(R.dimen.bar_graph_bar_spacing);
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mInternalRowInfos == null);
    while (true)
    {
      return;
      int i = getPaddingLeft();
      int j = getPaddingRight();
      int k = getWidth() - i - j;
      int m = this.mInternalRowInfos.length;
      int n = getPaddingTop();
      if (m > 0)
      {
        paramCanvas.translate(i, n);
        this.mTotalLayout.draw(paramCanvas);
        paramCanvas.translate(-i, -n);
        n += this.mTotalLayout.getHeight() + TOTAL_GRAPH_SPACING;
      }
      for (int i1 = 0; i1 < m; i1++)
      {
        InternalRowInfo localInternalRowInfo = this.mInternalRowInfos[i1];
        if ((localInternalRowInfo.mLabelLayout != null) && (localInternalRowInfo.mValueLayout != null))
        {
          float f = (float)localInternalRowInfo.mValue / (float)this.mMaxValue;
          paramCanvas.translate(i, n);
          localInternalRowInfo.mLabelLayout.draw(paramCanvas);
          paramCanvas.translate(-i, -n);
          int i2 = n + (localInternalRowInfo.mLabelLayout.getHeight() + LABEL_BAR_SPACING);
          paramCanvas.translate(i, i2);
          localInternalRowInfo.mValueLayout.draw(paramCanvas);
          paramCanvas.translate(-i, -i2);
          int i3 = i2 + (localInternalRowInfo.mValueLayout.getHeight() + LABEL_BAR_SPACING);
          paramCanvas.drawRect(i, i3, i + f * k, i3 + BAR_GRAPH_HEIGHT, sBarGraphPaint);
          n = i3 + (BAR_GRAPH_HEIGHT + BAR_SPACING);
        }
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    if (i == 1073741824);
    while (true)
    {
      int k = View.MeasureSpec.getMode(paramInt2);
      int m = View.MeasureSpec.getSize(paramInt2);
      int n = j - (getPaddingLeft() + getPaddingRight());
      if (k == 1073741824);
      Resources localResources;
      do
      {
        setMeasuredDimension(j, m);
        return;
        if (i != -2147483648)
          break label395;
        j = Math.min(480, j);
        break;
        m = getPaddingTop() + getPaddingBottom();
        localResources = getResources();
      }
      while (this.mInternalRowInfos == null);
      int i1 = this.mInternalRowInfos.length;
      if (i1 > 0)
      {
        int i8 = R.string.network_statistics_total;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Long.valueOf(this.mTotalValue);
        arrayOfObject2[1] = this.mUnits;
        this.mTotalLayout = new StaticLayout(localResources.getString(i8, arrayOfObject2), sTotalTextPaint, n, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      }
      for (int i2 = m + (this.mTotalLayout.getHeight() + TOTAL_GRAPH_SPACING); ; i2 = m)
      {
        int i3 = 0;
        int i7;
        for (int i4 = i2; i3 < i1; i4 = i7)
        {
          InternalRowInfo localInternalRowInfo = this.mInternalRowInfos[i3];
          localInternalRowInfo.mLabelLayout = new StaticLayout(localInternalRowInfo.mLabel, sLabelTextPaint, n, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
          int i5 = i4 + (localInternalRowInfo.mLabelLayout.getHeight() + LABEL_BAR_SPACING);
          int i6 = R.string.network_statistics_value;
          Object[] arrayOfObject1 = new Object[3];
          arrayOfObject1[0] = Long.valueOf(localInternalRowInfo.mValue);
          arrayOfObject1[1] = this.mUnits;
          arrayOfObject1[2] = Long.valueOf(100L * localInternalRowInfo.mValue / this.mTotalValue);
          localInternalRowInfo.mValueLayout = new StaticLayout(localResources.getString(i6, arrayOfObject1), sValueTextPaint, n, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
          i7 = i5 + (localInternalRowInfo.mValueLayout.getHeight() + LABEL_BAR_SPACING);
          i3++;
        }
        m = i4 + i1 * (BAR_GRAPH_HEIGHT + BAR_SPACING);
        break;
      }
      label395: j = 480;
    }
  }

  public final void update(RowInfo[] paramArrayOfRowInfo, String paramString)
  {
    this.mUnits = paramString;
    int i = paramArrayOfRowInfo.length;
    this.mInternalRowInfos = new InternalRowInfo[i];
    this.mTotalValue = 0L;
    this.mMaxValue = -2147483648L;
    for (int j = 0; j < i; j++)
    {
      this.mInternalRowInfos[j] = new InternalRowInfo();
      this.mInternalRowInfos[j].mValue = paramArrayOfRowInfo[j].mValue;
      this.mInternalRowInfos[j].mLabel = paramArrayOfRowInfo[j].mLabel;
      this.mTotalValue += this.mInternalRowInfos[j].mValue;
      this.mMaxValue = Math.max(this.mMaxValue, this.mInternalRowInfos[j].mValue);
    }
    invalidate();
    requestLayout();
  }

  protected static final class InternalRowInfo
  {
    public String mLabel;
    public StaticLayout mLabelLayout;
    public long mValue;
    public StaticLayout mValueLayout;
  }

  public static final class RowInfo
  {
    public String mLabel;
    public long mValue;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.BarGraphView
 * JD-Core Version:    0.6.2
 */