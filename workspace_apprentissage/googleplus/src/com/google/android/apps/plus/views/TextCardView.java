package com.google.android.apps.plus.views;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.content.DbEmbedMedia;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class TextCardView extends StreamCardView
{
  protected static Bitmap sCheckinIcon;
  private static boolean sTextCardViewInitialized;
  private boolean mIsCheckin;
  private boolean mWrapContent;

  public TextCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public TextCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sTextCardViewInitialized)
    {
      sTextCardViewInitialized = true;
      sCheckinIcon = ImageUtils.decodeResource(paramContext.getResources(), R.drawable.ic_checkin_small);
    }
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
    if (this.mAttributionLayout != null)
    {
      paramCanvas.translate(i, m);
      this.mAttributionLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      m += this.mAttributionLayout.getHeight() + sContentYPadding;
    }
    if (this.mContentLayout != null)
    {
      paramCanvas.translate(i, m);
      this.mContentLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      m += this.mContentLayout.getHeight() + sContentYPadding;
    }
    if (this.mFillerContentLayout != null)
    {
      paramCanvas.translate(i, m);
      this.mFillerContentLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      m += this.mFillerContentLayout.getHeight() + sContentYPadding;
    }
    if (this.mTagLayout != null)
      if (this.mTagIcon != null)
        if (!this.mIsCheckin)
          break label364;
    label364: for (int n = sTagIconYPaddingCheckin; ; n = sTagIconYPaddingLocation)
    {
      int i1 = m + n;
      paramCanvas.drawBitmap(this.mTagIcon, i, i1, null);
      i += this.mTagIcon.getWidth() + sTagIconXPadding;
      paramCanvas.translate(i, m);
      this.mTagLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -m);
      this.mTagLayout.getHeight();
      drawPlusOneBar(paramCanvas);
      drawCornerIcon(paramCanvas);
      return paramInt4;
    }
  }

  protected final String formatLocationName(String paramString)
  {
    return paramString;
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    if ((0x10 & paramCursor.getLong(15)) != 0L);
    for (boolean bool = true; ; bool = false)
    {
      this.mIsCheckin = bool;
      byte[] arrayOfByte = paramCursor.getBlob(22);
      if (arrayOfByte != null)
      {
        DbEmbedMedia localDbEmbedMedia = DbEmbedMedia.deserialize(arrayOfByte);
        if ((localDbEmbedMedia != null) && (!TextUtils.isEmpty(localDbEmbedMedia.getContentUrl())) && (TextUtils.isEmpty(this.mFillerContent)) && (!TextUtils.isEmpty(localDbEmbedMedia.getTitle())))
          this.mFillerContent = localDbEmbedMedia.getTitle();
      }
      return;
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    createPlusOneBar(paramInt1, paramInt2 + paramInt4, paramInt3);
    int i = paramInt4 - this.mPlusOneButton.getRect().height();
    setAuthorImagePosition(paramInt1, paramInt2);
    int j = paramInt3 - (sAvatarSize + sContentXPadding);
    int k = createAuthorNameAndRelativeTimeLayoutOnSameLine$4868d301(paramInt2, j) + sContentYPadding;
    if (!TextUtils.isEmpty(this.mAttribution))
    {
      int i2 = (i - k) / (int)(sAttributionTextPaint.descent() - sAttributionTextPaint.ascent());
      if (i2 > 0)
      {
        this.mAttributionLayout = TextPaintUtils.createConstrainedStaticLayout(sAttributionTextPaint, this.mAttribution, j, i2);
        k += this.mAttributionLayout.getHeight() + sContentYPadding;
      }
    }
    if (!TextUtils.isEmpty(this.mContent))
    {
      int i1 = (i - k) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
      if (i1 > 0)
      {
        this.mContentLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mContent, j, i1);
        k += this.mContentLayout.getHeight() + sContentYPadding;
      }
    }
    if (!TextUtils.isEmpty(this.mFillerContent))
    {
      int n = (i - k) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
      if (n > 0)
      {
        this.mFillerContentLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mFillerContent, j, n);
        k += this.mFillerContentLayout.getHeight() + sContentYPadding;
      }
    }
    int m;
    if (!TextUtils.isEmpty(this.mTag))
    {
      m = (i - k) / (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
      if (m > 0)
        if (!this.mIsCheckin)
          break label469;
    }
    label469: for (Bitmap localBitmap = sCheckinIcon; ; localBitmap = sTagLocationBitmaps[1])
    {
      this.mTagIcon = localBitmap;
      this.mTagLayout = TextPaintUtils.createConstrainedStaticLayout(sDefaultTextPaint, this.mTag, j - this.mTagIcon.getWidth(), m);
      k += this.mTagLayout.getHeight() + sContentYPadding;
      if (this.mWrapContent)
      {
        Rect localRect1 = this.mPlusOneButton.getRect();
        localRect1.offsetTo(localRect1.left, k);
        if (this.mReshareButton != null)
        {
          Rect localRect3 = this.mReshareButton.getRect();
          localRect3.offsetTo(localRect3.left, k);
        }
        if (this.mCommentsButton != null)
        {
          Rect localRect2 = this.mCommentsButton.getRect();
          localRect2.offsetTo(localRect2.left, k);
        }
      }
      return this.mPlusOneButton.getRect().bottom;
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
        break label181;
      setMeasuredDimension(i, i1 + (i3 + sTopBorderPadding) + sBottomBorderPadding);
    }
    while (true)
    {
      createGraySpamBar(getMeasuredWidth() - sLeftBorderPadding - sRightBorderPadding);
      this.mBackgroundRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
      return;
      k = j;
      break;
      label181: setMeasuredDimension(i, k);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mWrapContent = false;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TextCardView
 * JD-Core Version:    0.6.2
 */