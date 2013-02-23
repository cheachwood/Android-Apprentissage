package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.api.services.plusi.model.PlaceReview;
import com.google.api.services.plusi.model.PlaceReviewJson;

public class PlaceReviewCardView extends StreamCardView
{
  private static Paint sDividerPaint;
  private static int sDividerYPadding;
  private static Bitmap sLocationBitmap;
  private static int sLocationIconPadding;
  private static boolean sPlaceReviewCardInitialized;
  private static int sPostLocationYPadding;
  private float mDividerY = -1.0F;
  private Rect mLocationIconRect;
  private StaticLayout mLocationLayout;
  private Point mLocationLayoutCorner;
  private PlaceReview mReview;
  private StaticLayout mReviewBodyLayout;
  private boolean mWrapContent;

  public PlaceReviewCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PlaceReviewCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sPlaceReviewCardInitialized)
    {
      Resources localResources = paramContext.getResources();
      sLocationBitmap = ImageUtils.decodeResource(localResources, R.drawable.icn_location_card);
      sPlaceReviewCardInitialized = true;
      Paint localPaint = new Paint();
      sDividerPaint = localPaint;
      localPaint.setColor(localResources.getColor(R.color.card_place_review_divider));
      sDividerPaint.setStrokeWidth(localResources.getDimension(R.dimen.card_place_review_divider_stroke_width));
      sPostLocationYPadding = localResources.getDimensionPixelOffset(R.dimen.card_place_review_post_location_y_padding);
      sLocationIconPadding = localResources.getDimensionPixelOffset(R.dimen.card_place_review_location_icon_padding);
      sDividerYPadding = localResources.getDimensionPixelOffset(R.dimen.card_place_review_divider_y_padding);
    }
    this.mLocationIconRect = new Rect();
    this.mLocationLayoutCorner = new Point();
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    drawAuthorImage$494937f0(paramCanvas);
    int i = paramInt1 + (sAvatarSize + sContentXPadding);
    int j = paramInt3 - (sAvatarSize + sContentXPadding);
    int k = drawAuthorName(paramCanvas, i, paramInt2);
    if (this.mRelativeTimeLayout != null)
      drawRelativeTimeLayout(paramCanvas, i + j - this.mRelativeTimeLayout.getWidth(), k - this.mRelativeTimeLayout.getHeight() - sRelativeTimeYOffset);
    int m = k + sContentYPadding;
    if (this.mContentLayout != null)
    {
      paramCanvas.translate(i, m);
      this.mContentLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      m += this.mContentLayout.getHeight() + sContentYPadding;
    }
    if (this.mAutoTextLayout != null)
    {
      paramCanvas.translate(i, m);
      this.mAutoTextLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      m += this.mAutoTextLayout.getHeight() + sContentYPadding;
    }
    if (this.mDividerY != -1.0F)
      paramCanvas.drawLine(i, this.mDividerY, i + j, this.mDividerY, sDividerPaint);
    if (this.mLocationLayout != null)
    {
      int n = Math.max(this.mLocationIconRect.bottom, this.mLocationLayout.getHeight() + this.mLocationLayoutCorner.y);
      if ((n <= paramInt4) || (this.mWrapContent))
      {
        paramCanvas.translate(this.mLocationLayoutCorner.x, this.mLocationLayoutCorner.y);
        this.mLocationLayout.draw(paramCanvas);
        paramCanvas.translate(-this.mLocationLayoutCorner.x, -this.mLocationLayoutCorner.y);
        paramCanvas.drawBitmap(sLocationBitmap, null, this.mLocationIconRect, null);
        m = n + sPostLocationYPadding;
      }
    }
    if (this.mReviewBodyLayout != null)
    {
      paramCanvas.translate(i, m);
      this.mReviewBodyLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      this.mReviewBodyLayout.getHeight();
    }
    drawPlusOneBar(paramCanvas);
    drawCornerIcon(paramCanvas);
    return paramInt4;
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    byte[] arrayOfByte = paramCursor.getBlob(24);
    if (arrayOfByte != null)
      this.mReview = ((PlaceReview)PlaceReviewJson.getInstance().fromByteArray(arrayOfByte));
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    createPlusOneBar(paramInt1, paramInt2 + paramInt4, paramInt3);
    int i = paramInt4 - this.mPlusOneButton.getRect().height();
    setAuthorImagePosition(paramInt1, paramInt2);
    int j = paramInt1 + (sAvatarSize + sContentXPadding);
    int k = paramInt3 - (sAvatarSize + sContentXPadding);
    int m = createAuthorNameAndRelativeTimeLayoutOnSameLine$4868d301(paramInt2, k) + sContentYPadding;
    int i2;
    if (!TextUtils.isEmpty(this.mContent))
    {
      int i5 = (i - m) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
      if (i5 > 0)
      {
        this.mContentLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mContent, k, i5);
        m += this.mContentLayout.getHeight();
      }
      int i1 = m + sDividerYPadding;
      this.mDividerY = i1;
      i2 = i1 + sDividerYPadding;
      if (TextUtils.isEmpty(this.mReview.name))
        break label495;
    }
    label495: for (String str = this.mReview.name; ; str = null)
    {
      if (str != null)
      {
        Bitmap localBitmap = sLocationBitmap;
        Rect localRect2 = this.mLocationIconRect;
        int i4 = sLocationIconPadding;
        Point localPoint = this.mLocationLayoutCorner;
        TextPaint localTextPaint = sDefaultTextPaint;
        this.mLocationLayout = TextPaintUtils.layoutBitmapTextLabel(j, i2, k, 0, localBitmap, localRect2, i4, str, localPoint, localTextPaint, true);
        i2 += this.mLocationLayout.getHeight() + sPostLocationYPadding;
      }
      if (!TextUtils.isEmpty(this.mReview.reviewBody))
      {
        int i3 = (i - i2) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
        if (i3 > 0)
        {
          this.mReviewBodyLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mReview.reviewBody, k, i3);
          i2 += this.mReviewBodyLayout.getHeight() + sContentYPadding;
        }
      }
      Rect localRect1 = this.mPlusOneButton.getRect();
      if (this.mWrapContent)
      {
        localRect1.offsetTo(localRect1.left, i2);
        if (this.mReshareButton != null)
        {
          localRect1 = this.mReshareButton.getRect();
          localRect1.offsetTo(localRect1.left, i2);
        }
        if (this.mCommentsButton != null)
        {
          localRect1 = this.mCommentsButton.getRect();
          localRect1.offsetTo(localRect1.left, i2);
        }
      }
      return localRect1.bottom;
      if (this.mAutoText == 0)
        break;
      int n = (i - m) / (int)(sAutoTextPaint.descent() - sAutoTextPaint.ascent());
      if (n <= 0)
        break;
      this.mAutoTextLayout = TextPaintUtils.createConstrainedStaticLayout(sAutoTextPaint, getResources().getString(this.mAutoText), k, n);
      m += this.mAutoTextLayout.getHeight();
      break;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.mWrapContent = shouldWrapContent(paramInt2);
    int k;
    if (this.mWrapContent)
    {
      k = i;
      boolean bool = this.mPaddingEnabled;
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      if (bool)
      {
        n = sXPadding;
        i2 = sYPadding;
        m = sXDoublePadding;
        i1 = sYDoublePadding;
      }
      int i3 = layoutElements(n + sLeftBorderPadding, i2 + sTopBorderPadding, i - (m + sLeftBorderPadding + sRightBorderPadding), k - (i1 + sTopBorderPadding + sBottomBorderPadding));
      if (!this.mWrapContent)
        break label165;
      setMeasuredDimension(i, i1 + (i3 + sTopBorderPadding) + sBottomBorderPadding);
    }
    while (true)
    {
      this.mBackgroundRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
      return;
      k = j;
      break;
      label165: setMeasuredDimension(i, k);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mWrapContent = false;
    this.mLocationLayout = null;
    this.mLocationIconRect.setEmpty();
    this.mLocationLayoutCorner.set(0, 0);
    this.mReviewBodyLayout = null;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PlaceReviewCardView
 * JD-Core Version:    0.6.2
 */