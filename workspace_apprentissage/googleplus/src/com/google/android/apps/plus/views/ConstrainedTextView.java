package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.styleable;

public class ConstrainedTextView extends View
{
  private ItemClickListener mClickListener;
  private ClickableStaticLayout mContentLayout;
  private ClickableItem mCurrentClickableItem;
  private boolean mEllipsize = true;
  private int mMaxHeight;
  private int mMaxLines;
  private CharSequence mText;
  private TextPaint mTextPaint;

  public ConstrainedTextView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ConstrainedTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public ConstrainedTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ConstrainedTextView, 0, paramInt);
    int i = localTypedArray.getColor(2, 0);
    int j = localTypedArray.getInt(1, 0);
    float f = localTypedArray.getDimension(0, 0.0F);
    this.mMaxLines = localTypedArray.getInt(5, -1);
    this.mMaxHeight = localTypedArray.getDimensionPixelSize(3, -1);
    this.mText = localTypedArray.getString(4);
    this.mTextPaint = new TextPaint();
    this.mTextPaint.setAntiAlias(true);
    this.mTextPaint.setColor(i);
    this.mTextPaint.setTextSize(f);
    this.mTextPaint.setTypeface(Typeface.defaultFromStyle(j));
    if (this.mText == null)
      this.mText = "";
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int k = (int)paramMotionEvent.getX();
    int m = (int)paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    case 2:
    default:
      i = 0;
    case 0:
    case 1:
    case 3:
    }
    while (true)
    {
      return i;
      if ((this.mContentLayout != null) && (this.mContentLayout.handleEvent(k, m, 0)))
      {
        this.mCurrentClickableItem = this.mContentLayout;
        invalidate();
      }
      else
      {
        i = 0;
        continue;
        this.mCurrentClickableItem = null;
        if (this.mContentLayout != null)
          this.mContentLayout.handleEvent(k, m, i);
        invalidate();
        int j = 0;
        continue;
        if (this.mCurrentClickableItem != null)
        {
          this.mCurrentClickableItem.handleEvent(k, m, 3);
          this.mCurrentClickableItem = null;
          invalidate();
        }
        else
        {
          j = 0;
        }
      }
    }
  }

  public final int getLength()
  {
    if (this.mText != null);
    for (int i = this.mText.length(); ; i = 0)
      return i;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mContentLayout != null)
      this.mContentLayout.draw(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m;
    label66: int n;
    if ((k == -2147483648) || (k == 1073741824) || (this.mMaxLines >= 0) || (this.mMaxHeight >= 0))
    {
      m = 1;
      if (this.mMaxHeight >= 0)
      {
        if (k != 0)
          break label218;
        j = this.mMaxHeight;
      }
      TextPaint localTextPaint = this.mTextPaint;
      n = 0;
      if (localTextPaint != null)
      {
        if ((k == 0) && (this.mMaxHeight < 0))
          break label232;
        n = j / (int)(this.mTextPaint.descent() - this.mTextPaint.ascent());
        if (this.mMaxLines >= 0)
          n = Math.min(n, this.mMaxLines);
      }
      label131: if ((this.mTextPaint == null) || ((n <= 0) && (k != 0)))
        break label274;
      if ((!this.mEllipsize) || (m == 0))
        break label241;
      this.mContentLayout = ClickableStaticLayout.createConstrainedLayout(this.mTextPaint, this.mText, i, n, this.mClickListener);
      label182: this.mContentLayout.setPosition(0, 0);
    }
    for (int i1 = this.mContentLayout.getHeight(); ; i1 = 0)
    {
      setMeasuredDimension(i, View.resolveSize(i1, paramInt2));
      return;
      m = 0;
      break;
      label218: j = Math.min(j, this.mMaxHeight);
      break label66;
      label232: n = this.mMaxLines;
      break label131;
      label241: this.mContentLayout = new ClickableStaticLayout(this.mText, this.mTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false, this.mClickListener);
      break label182;
      label274: this.mContentLayout = null;
    }
  }

  public void setClickListener(ItemClickListener paramItemClickListener)
  {
    this.mClickListener = paramItemClickListener;
  }

  public void setHtmlText(String paramString, boolean paramBoolean)
  {
    this.mText = ClickableStaticLayout.buildStateSpans(paramString);
    this.mEllipsize = paramBoolean;
    requestLayout();
  }

  public void setMaxHeight(int paramInt)
  {
    this.mMaxHeight = paramInt;
  }

  public void setMaxLines(int paramInt)
  {
    this.mMaxLines = paramInt;
    requestLayout();
  }

  public void setText(CharSequence paramCharSequence)
  {
    if (paramCharSequence != null);
    while (true)
    {
      setText(paramCharSequence, true);
      return;
      paramCharSequence = "";
    }
  }

  public void setText(CharSequence paramCharSequence, boolean paramBoolean)
  {
    this.mText = paramCharSequence;
    this.mEllipsize = paramBoolean;
    requestLayout();
  }

  public void setTextColor(int paramInt)
  {
    this.mTextPaint.setColor(paramInt);
    requestLayout();
  }

  public void setTextPaint(TextPaint paramTextPaint)
  {
    this.mTextPaint = paramTextPaint;
  }

  public void setTextSize(float paramFloat)
  {
    this.mTextPaint.setTextSize(paramFloat);
    requestLayout();
  }

  public void setTypeface(Typeface paramTypeface)
  {
    this.mTextPaint.setTypeface(paramTypeface);
    requestLayout();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ConstrainedTextView
 * JD-Core Version:    0.6.2
 */