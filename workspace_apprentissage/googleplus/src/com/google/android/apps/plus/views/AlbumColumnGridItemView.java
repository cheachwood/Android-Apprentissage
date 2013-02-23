package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class AlbumColumnGridItemView extends ImageResourceView
  implements ColumnGridView.PressedHighlightable
{
  private static TextPaint sCommentCountPaint;
  private static Bitmap sCommentImage;
  private static Rect sDisabledArea;
  private static Paint sDisabledPaint;
  private static int sInfoHeight;
  private static int sInfoInnerPadding;
  private static int sInfoLeftMargin;
  private static Paint sInfoPaint;
  private static int sInfoRightMargin;
  private static boolean sInitialized;
  private static Bitmap sNotifyImage;
  private static int sNotifyRightMargin;
  private static int sNotifyTopMargin;
  private static TextPaint sPlusOneCountPaint;
  private static Bitmap sPlusOneImage;
  private CharSequence mCommentCount;
  private boolean mNotify;
  private CharSequence mPlusOneCount;

  public AlbumColumnGridItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public AlbumColumnGridItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setSizeCategory(2);
    setReleaseImageWhenPaused(true);
    setDefaultIconEnabled(true);
    Resources localResources = paramContext.getApplicationContext().getResources();
    if (!sInitialized)
    {
      TextPaint localTextPaint1 = new TextPaint();
      sCommentCountPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sCommentCountPaint.setColor(localResources.getColor(R.color.album_comment_count_color));
      sCommentCountPaint.setTextSize(localResources.getDimension(R.dimen.album_comment_count_text_size));
      TextPaintUtils.registerTextPaint(sCommentCountPaint, R.dimen.album_comment_count_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sPlusOneCountPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sPlusOneCountPaint.setColor(localResources.getColor(R.color.album_plusone_count_color));
      sPlusOneCountPaint.setTextSize(localResources.getDimension(R.dimen.album_plusone_count_text_size));
      TextPaintUtils.registerTextPaint(sPlusOneCountPaint, R.dimen.album_plusone_count_text_size);
      Paint localPaint1 = new Paint();
      sInfoPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.album_info_background_color));
      sInfoPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sDisabledPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.album_disabled_color));
      sDisabledPaint.setStyle(Paint.Style.FILL);
      sDisabledArea = new Rect();
      sInfoInnerPadding = localResources.getDimensionPixelSize(R.dimen.album_info_inner_padding);
      sInfoRightMargin = localResources.getDimensionPixelSize(R.dimen.album_info_right_margin);
      sInfoLeftMargin = localResources.getDimensionPixelSize(R.dimen.album_info_left_margin);
      sInfoHeight = localResources.getDimensionPixelSize(R.dimen.album_info_height);
      sNotifyRightMargin = localResources.getDimensionPixelSize(R.dimen.album_notification_right_margin);
      sNotifyTopMargin = localResources.getDimensionPixelSize(R.dimen.album_notification_top_margin);
      sPlusOneImage = ImageUtils.decodeResource(localResources, R.drawable.photo_plusone);
      sCommentImage = ImageUtils.decodeResource(localResources, R.drawable.photo_comment);
      sNotifyImage = ImageUtils.decodeResource(localResources, R.drawable.tag);
      sInitialized = true;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = paramCanvas.getSaveCount();
    paramCanvas.save();
    super.onDraw(paramCanvas);
    paramCanvas.restoreToCount(i);
    if (!hasImage());
    while (true)
    {
      return;
      if (!isEnabled())
      {
        sDisabledArea.set(0, 0, getWidth(), getHeight());
        paramCanvas.drawRect(sDisabledArea, sDisabledPaint);
      }
      else
      {
        if (this.mNotify)
        {
          int n = getWidth() - sNotifyRightMargin - sNotifyImage.getWidth();
          paramCanvas.drawBitmap(sNotifyImage, n, sNotifyTopMargin, null);
        }
        if ((this.mPlusOneCount != null) || (this.mCommentCount != null))
        {
          int j = getHeight() - sInfoHeight;
          paramCanvas.drawRect(0.0F, j, getWidth(), getHeight(), sInfoPaint);
          int k = sInfoLeftMargin;
          if (this.mPlusOneCount != null)
          {
            float f4 = j + (sInfoHeight - sPlusOneImage.getHeight()) / 2;
            float f5 = sPlusOneCountPaint.descent() - sPlusOneCountPaint.ascent();
            float f6 = j + (sInfoHeight - f5) / 2.0F - sPlusOneCountPaint.ascent();
            paramCanvas.drawBitmap(sPlusOneImage, k, f4, null);
            k += sPlusOneImage.getWidth() + sInfoInnerPadding;
            paramCanvas.drawText(this.mPlusOneCount, 0, this.mPlusOneCount.length(), k, f6, sPlusOneCountPaint);
            if (this.mCommentCount != null)
              k = (int)(getWidth() - sInfoRightMargin - sCommentCountPaint.measureText(this.mCommentCount, 0, this.mCommentCount.length())) - sInfoInnerPadding - sCommentImage.getWidth();
          }
          if (this.mCommentCount != null)
          {
            float f1 = j + (sInfoHeight - sCommentImage.getHeight()) / 2;
            float f2 = sCommentCountPaint.descent() - sCommentCountPaint.ascent();
            float f3 = j + (sInfoHeight - f2) / 2.0F - sCommentCountPaint.ascent();
            paramCanvas.drawBitmap(sCommentImage, k, f1, null);
            int m = k + (sCommentImage.getWidth() + sInfoInnerPadding);
            paramCanvas.drawText(this.mCommentCount, 0, this.mCommentCount.length(), m, f3, sCommentCountPaint);
          }
        }
      }
    }
  }

  public void setCommentCount(Integer paramInteger)
  {
    if (paramInteger == null)
      this.mCommentCount = null;
    while (true)
    {
      return;
      if (paramInteger.intValue() > 99)
        this.mCommentCount = getResources().getString(R.string.ninety_nine_plus);
      else
        this.mCommentCount = paramInteger.toString();
    }
  }

  public void setNotification(boolean paramBoolean)
  {
    this.mNotify = paramBoolean;
  }

  public void setPlusOneCount(Integer paramInteger)
  {
    if (paramInteger == null)
      this.mPlusOneCount = null;
    while (true)
    {
      return;
      if (paramInteger.intValue() > 99)
        this.mPlusOneCount = getResources().getString(R.string.ninety_nine_plus);
      else
        this.mPlusOneCount = paramInteger.toString();
    }
  }

  public final boolean shouldHighlightOnPress()
  {
    return isEnabled();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.AlbumColumnGridItemView
 * JD-Core Version:    0.6.2
 */