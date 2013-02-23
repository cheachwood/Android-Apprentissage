package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.R.styleable;
import java.util.ArrayList;
import java.util.Collections;

public class CirclesButton extends ViewGroup
{
  private final TextView mCircleCountText;
  private final Drawable mCircleIcon;
  private final int mCircleIconSpacing;
  private ArrayList<String> mCircleNames;
  private final TextView mCirclesText;
  private int mDefaultTextColor = 0;
  private String mFixedText;
  private final int mLabelSpacing;
  private Rect mPadding;
  private ProgressBar mProgressBar;
  private final StringBuilder mSb = new StringBuilder();
  private boolean mShowIcon = true;
  private boolean mShowProgressIndicator;

  public CirclesButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public CirclesButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.style.CirclesButton);
  }

  public CirclesButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CircleButton, 0, paramInt);
    this.mCircleIconSpacing = localTypedArray.getDimensionPixelSize(5, 0);
    this.mLabelSpacing = localTypedArray.getDimensionPixelSize(6, 0);
    this.mDefaultTextColor = localTypedArray.getColor(0, 0);
    this.mFixedText = localTypedArray.getString(3);
    this.mCircleIcon = localTypedArray.getDrawable(4);
    localTypedArray.recycle();
    this.mCircleIcon.setFilterBitmap(true);
    this.mCirclesText = new TextView(paramContext);
    this.mCirclesText.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
    this.mCirclesText.setTextAppearance(paramContext, 16973894);
    this.mCirclesText.setTypeface(this.mCirclesText.getTypeface(), 1);
    this.mCirclesText.setSingleLine();
    this.mCirclesText.setEllipsize(TextUtils.TruncateAt.END);
    this.mCirclesText.setGravity(16);
    if (this.mDefaultTextColor != 0)
      this.mCirclesText.setTextColor(this.mDefaultTextColor);
    addView(this.mCirclesText);
    this.mCircleCountText = new TextView(paramContext);
    this.mCircleCountText.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
    this.mCircleCountText.setTextAppearance(paramContext, 16973894);
    this.mCircleCountText.setGravity(16);
    this.mCircleCountText.setSingleLine();
    this.mCircleCountText.setEllipsize(TextUtils.TruncateAt.END);
    addView(this.mCircleCountText);
  }

  private void appendCirclesText(StringBuilder paramStringBuilder, int paramInt)
  {
    ArrayList localArrayList;
    if (paramInt == this.mCircleNames.size())
      localArrayList = this.mCircleNames;
    while (true)
    {
      for (int n = 0; n < paramInt; n++)
      {
        if (n > 0)
          paramStringBuilder.append(", ");
        paramStringBuilder.append((String)localArrayList.get(n));
      }
      localArrayList = new ArrayList(this.mCircleNames);
      while (localArrayList.size() > paramInt)
      {
        int i = 0;
        int j = -1;
        for (int k = 0; k < localArrayList.size(); k++)
        {
          int m = ((String)localArrayList.get(k)).length();
          if (m >= i)
          {
            i = m;
            j = k;
          }
        }
        localArrayList.remove(j);
      }
    }
    if (paramInt < this.mCircleNames.size())
      paramStringBuilder.append(",…");
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    if (this.mShowIcon)
      this.mCircleIcon.draw(paramCanvas);
    super.dispatchDraw(paramCanvas);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool = this.mShowIcon;
    int i = 0;
    if (bool)
      i = 0 + (this.mCircleIcon.getIntrinsicWidth() + this.mCircleIconSpacing);
    if (this.mCircleCountText.getVisibility() == 0)
      i += this.mCircleCountText.getMeasuredWidth() + this.mLabelSpacing;
    int j = i + this.mCirclesText.getMeasuredWidth();
    int k = (paramInt3 - paramInt1 - j) / 2;
    if (k < this.mPadding.left)
      k = this.mPadding.left;
    int m = k + j;
    if (m > paramInt3 - paramInt1 - this.mPadding.right)
      m = paramInt3 - paramInt1 - this.mPadding.right;
    if (this.mShowIcon)
    {
      int i4 = this.mCircleIcon.getIntrinsicHeight();
      int i5 = this.mCircleIcon.getIntrinsicWidth();
      int i6 = (paramInt4 - paramInt2 - i4) / 2;
      this.mCircleIcon.setBounds(k, i6, k + i5, i6 + i4);
      k += i5 + this.mCircleIconSpacing;
    }
    if (this.mCircleCountText.getVisibility() == 0)
    {
      if (this.mCirclesText.getVisibility() != 0)
        break label385;
      int i2 = this.mCircleCountText.getMeasuredWidth();
      int i3 = m - i2;
      this.mCircleCountText.layout(i3, this.mPadding.top, i3 + i2, paramInt4 - paramInt2 - this.mPadding.bottom);
      m = i3 - this.mLabelSpacing;
    }
    while (true)
    {
      if (this.mCirclesText.getVisibility() == 0)
        this.mCirclesText.layout(k, this.mPadding.top, m, paramInt4 - paramInt2 - this.mPadding.bottom);
      if (this.mShowProgressIndicator)
      {
        int n = this.mProgressBar.getMeasuredWidth();
        int i1 = (paramInt3 - paramInt1 - n) / 2;
        this.mProgressBar.layout(i1, this.mPadding.top, i1 + n, n + this.mPadding.top);
      }
      return;
      label385: this.mCircleCountText.layout(k, this.mPadding.top, m, paramInt4 - paramInt2 - this.mPadding.bottom);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mPadding == null)
    {
      this.mPadding = new Rect();
      Drawable localDrawable = getBackground();
      if (localDrawable != null)
        localDrawable.getPadding(this.mPadding);
      int i21 = getPaddingLeft();
      if (i21 != 0)
        this.mPadding.left = i21;
      int i22 = getPaddingRight();
      if (i22 != 0)
        this.mPadding.right = i22;
    }
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), -2147483648);
    int k = this.mPadding.left + this.mPadding.right;
    boolean bool = this.mShowIcon;
    int m = 0;
    if (bool)
    {
      int i20 = this.mCircleIcon.getIntrinsicWidth();
      m = this.mCircleIcon.getIntrinsicHeight();
      k += i20 + this.mCircleIconSpacing;
    }
    int n;
    int i5;
    if (i == 0)
    {
      n = 2147483647;
      this.mCirclesText.setVisibility(0);
      if (this.mFixedText == null)
        break label526;
      this.mCirclesText.setText(this.mFixedText);
      this.mCirclesText.measure(View.MeasureSpec.makeMeasureSpec(n, -2147483648), j);
      i5 = this.mCirclesText.getMeasuredWidth();
      this.mCircleCountText.setVisibility(8);
    }
    while (true)
    {
      int i6 = i5 + k;
      int i7 = Math.max(m, this.mCirclesText.getMeasuredHeight()) + this.mPadding.top + this.mPadding.bottom;
      int i8 = resolveSize(i6, paramInt1);
      int i9 = resolveSize(i7, paramInt2);
      int i10 = this.mPadding.left;
      if (this.mShowIcon)
        i10 += this.mCircleIcon.getIntrinsicWidth() + this.mCircleIconSpacing;
      int i11 = i8 - this.mPadding.right;
      if (this.mCircleCountText.getVisibility() == 0)
      {
        int i15 = this.mCircleCountText.getMeasuredWidth();
        this.mCircleCountText.measure(View.MeasureSpec.makeMeasureSpec(i15, 1073741824), View.MeasureSpec.makeMeasureSpec(i9 - this.mPadding.top - this.mPadding.bottom, 1073741824));
        i11 -= i15 + this.mLabelSpacing;
      }
      int i12 = this.mCirclesText.getMeasuredWidth();
      int i13 = i11 - i10;
      if (i12 > i13)
        i12 = i11 - i10;
      this.mCirclesText.measure(View.MeasureSpec.makeMeasureSpec(i12, 1073741824), View.MeasureSpec.makeMeasureSpec(i9 - this.mPadding.top - this.mPadding.bottom, 1073741824));
      if (this.mShowProgressIndicator)
      {
        int i14 = View.MeasureSpec.makeMeasureSpec(i9 - this.mPadding.top - this.mPadding.bottom, 1073741824);
        this.mProgressBar.measure(i14, i14);
      }
      setMeasuredDimension(i8, i9);
      return;
      n = i - k;
      break;
      label526: this.mSb.setLength(0);
      int i1 = this.mCircleNames.size();
      appendCirclesText(this.mSb, i1);
      this.mCirclesText.setText(this.mSb);
      this.mCirclesText.measure(0, j);
      int i2 = this.mCirclesText.getMeasuredWidth();
      if (i2 <= n)
      {
        i5 = i2;
        this.mCircleCountText.setVisibility(8);
      }
      else if (i1 == 1)
      {
        int i19 = n;
        this.mCirclesText.measure(View.MeasureSpec.makeMeasureSpec(i19, -2147483648), j);
        i5 = this.mCirclesText.getMeasuredWidth();
        this.mCircleCountText.setVisibility(8);
      }
      else
      {
        this.mCircleCountText.setVisibility(0);
        Resources localResources = getContext().getResources();
        int i3 = 0;
        for (int i4 = i1 - 1; i4 > 0; i4--)
        {
          this.mSb.setLength(0);
          appendCirclesText(this.mSb, i4);
          this.mCirclesText.setText(this.mSb);
          this.mCirclesText.measure(0, j);
          i2 = this.mCirclesText.getMeasuredWidth();
          int i17 = i1 - i4;
          TextView localTextView2 = this.mCircleCountText;
          int i18 = R.plurals.circle_button_more_circles;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(i17);
          localTextView2.setText(localResources.getQuantityString(i18, i17, arrayOfObject2));
          this.mCircleCountText.measure(0, j);
          i3 = this.mCircleCountText.getMeasuredWidth();
          if (i3 + (i2 + this.mLabelSpacing) <= n)
            break;
        }
        if (i3 + (i2 + this.mLabelSpacing) > n)
        {
          this.mCirclesText.setVisibility(8);
          i2 = 0;
          TextView localTextView1 = this.mCircleCountText;
          Context localContext = getContext();
          int i16 = R.string.circle_button_circles;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(i1);
          localTextView1.setText(localContext.getString(i16, arrayOfObject1));
          this.mCircleCountText.measure(View.MeasureSpec.makeMeasureSpec(n, -2147483648), j);
          i3 = this.mCircleCountText.getMeasuredWidth();
        }
        i5 = i3 + (i2 + this.mLabelSpacing);
      }
    }
  }

  public void setCircles(ArrayList<String> paramArrayList)
  {
    this.mFixedText = null;
    this.mCircleNames = paramArrayList;
    Collections.sort(this.mCircleNames, String.CASE_INSENSITIVE_ORDER);
    requestLayout();
  }

  public void setHighlighted(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setBackgroundResource(R.drawable.plusone_by_me_button);
      this.mCirclesText.setTextColor(-1);
    }
    while (true)
    {
      return;
      setBackgroundResource(R.drawable.plusone_button);
      this.mCirclesText.setTextColor(-16777216);
    }
  }

  public void setShowIcon(boolean paramBoolean)
  {
    if (this.mShowIcon != paramBoolean)
    {
      this.mShowIcon = paramBoolean;
      requestLayout();
    }
  }

  public void setShowProgressIndicator(boolean paramBoolean)
  {
    if (this.mShowProgressIndicator != paramBoolean)
    {
      this.mShowProgressIndicator = paramBoolean;
      if (!paramBoolean)
        break label87;
      if (this.mProgressBar == null)
      {
        this.mProgressBar = new ProgressBar(getContext());
        this.mProgressBar.setIndeterminate(true);
        addView(this.mProgressBar);
      }
      this.mProgressBar.setVisibility(0);
      this.mCirclesText.setVisibility(8);
      this.mShowIcon = false;
      setHighlighted(false);
    }
    while (true)
    {
      requestLayout();
      return;
      label87: if (this.mProgressBar != null)
      {
        this.mProgressBar.setVisibility(8);
        this.mCirclesText.setVisibility(0);
      }
    }
  }

  public void setText(String paramString)
  {
    if (!TextUtils.equals(this.mFixedText, paramString))
    {
      this.mFixedText = paramString;
      requestLayout();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CirclesButton
 * JD-Core Version:    0.6.2
 */