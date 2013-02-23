package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView.ScaleType;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class EventPhotoView extends EsImageView
{
  private static TextPaint sCommentCountPaint;
  private static Bitmap sCommentImage;
  private static int sInfoHeight;
  private static int sInfoInnerPadding;
  private static Paint sInfoPaint;
  private static int sInfoRightMargin;
  private static boolean sInitialized;
  private static int sMinVisibleImageHeight;
  private static Bitmap sNoImageImage;
  private static TextPaint sPlusOneCountPaint;
  private static Bitmap sPlusOneImage;
  private String mCommentCount;
  private int mCommentCountXPos;
  private float mCommentCountYPos;
  private int mCommentIconXPos;
  private float mCommentIconYPos;
  private int mImageHeight;
  private boolean mImageLoaded;
  private boolean mImageRequested;
  private String mImageUrl;
  private int mImageWidth;
  private int mMaxHeight;
  private String mPlusOneCount;
  private int mPlusOneCountXPos;
  private float mPlusOneCountYPos;
  private int mPlusOneIconXPos;
  private float mPlusOneIconYPos;
  private int mPreferredImageHeight;
  private int mPreferredImageWidth;
  private boolean mShowInfo;
  private boolean mShowNoImage;

  public EventPhotoView(Context paramContext)
  {
    super(paramContext);
    if (!sInitialized)
    {
      initialize(getResources());
      sInitialized = true;
    }
  }

  public EventPhotoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sInitialized)
    {
      initialize(getResources());
      sInitialized = true;
    }
  }

  public EventPhotoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      initialize(getResources());
      sInitialized = true;
    }
  }

  private static void initialize(Resources paramResources)
  {
    TextPaint localTextPaint1 = new TextPaint();
    sCommentCountPaint = localTextPaint1;
    localTextPaint1.setAntiAlias(true);
    sCommentCountPaint.setColor(paramResources.getColor(R.color.event_activity_comment_count_color));
    sCommentCountPaint.setTextSize(paramResources.getDimension(R.dimen.event_activity_comment_count_text_size));
    TextPaintUtils.registerTextPaint(sCommentCountPaint, R.dimen.event_activity_comment_count_text_size);
    TextPaint localTextPaint2 = new TextPaint();
    sPlusOneCountPaint = localTextPaint2;
    localTextPaint2.setAntiAlias(true);
    sPlusOneCountPaint.setColor(paramResources.getColor(R.color.event_activity_plusone_count_color));
    sPlusOneCountPaint.setTextSize(paramResources.getDimension(R.dimen.event_activity_plusone_count_text_size));
    TextPaintUtils.registerTextPaint(sPlusOneCountPaint, R.dimen.event_activity_plusone_count_text_size);
    Paint localPaint = new Paint();
    sInfoPaint = localPaint;
    localPaint.setColor(paramResources.getColor(R.color.event_activity_info_background_color));
    sInfoPaint.setStyle(Paint.Style.FILL);
    sInfoInnerPadding = paramResources.getDimensionPixelSize(R.dimen.event_activity_info_inner_padding);
    sInfoRightMargin = paramResources.getDimensionPixelSize(R.dimen.event_activity_info_right_margin);
    int i = paramResources.getDimensionPixelSize(R.dimen.event_activity_info_height);
    sInfoHeight = i;
    sMinVisibleImageHeight = (int)(i / 0.25F);
    sPlusOneImage = ImageUtils.decodeResource(paramResources, R.drawable.photo_plusone);
    sCommentImage = ImageUtils.decodeResource(paramResources, R.drawable.photo_comment);
    sNoImageImage = ImageUtils.decodeResource(paramResources, R.drawable.ic_missing_photo_light);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mImageLoaded) && (this.mShowInfo))
    {
      int i = getWidth();
      int j = getHeight();
      paramCanvas.drawRect(0.0F, j - sInfoHeight, i, j, sInfoPaint);
      if (this.mPlusOneCount != null)
      {
        paramCanvas.drawBitmap(sPlusOneImage, this.mPlusOneIconXPos, this.mPlusOneIconYPos, null);
        paramCanvas.drawText(this.mPlusOneCount, 0, this.mPlusOneCount.length(), this.mPlusOneCountXPos, this.mPlusOneCountYPos, sPlusOneCountPaint);
      }
      if (this.mCommentCount != null)
      {
        paramCanvas.drawBitmap(sCommentImage, this.mCommentIconXPos, this.mCommentIconYPos, null);
        paramCanvas.drawText(this.mCommentCount, 0, this.mCommentCount.length(), this.mCommentCountXPos, this.mCommentCountYPos, sCommentCountPaint);
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt4 - paramInt2 > 0) && (!this.mImageRequested) && (this.mImageUrl != null))
    {
      this.mImageRequested = true;
      setRequest(new MediaImageRequest(ImageUtils.getResizedUrl(this.mPreferredImageWidth, 1600, this.mImageUrl), 3, this.mPreferredImageWidth, this.mPreferredImageHeight, false));
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    boolean bool = true;
    int i = View.MeasureSpec.getMode(paramInt1);
    int j;
    int k;
    if (i == 1073741824)
    {
      j = View.MeasureSpec.getSize(paramInt1);
      if ((this.mImageWidth != 0) && (this.mImageHeight != 0))
        break label402;
      this.mPreferredImageWidth = j;
      k = this.mMaxHeight;
      label48: this.mPreferredImageHeight = k;
      if ((this.mCommentCount == null) && (this.mPlusOneCount == null))
        break label466;
      this.mShowInfo = bool;
      if (k < sMinVisibleImageHeight)
        k += sInfoHeight;
      int m = k - sInfoHeight;
      int n = j - sInfoRightMargin;
      if (this.mCommentCount != null)
      {
        this.mCommentIconYPos = (m + (sInfoHeight - sCommentImage.getHeight()) / 2);
        float f2 = sCommentCountPaint.descent() - sCommentCountPaint.ascent();
        this.mCommentCountYPos = (m + (sInfoHeight - f2) / 2.0F - sCommentCountPaint.ascent());
        int i3 = this.mCommentCount.length();
        int i4 = (int)(n - sCommentCountPaint.measureText(this.mCommentCount, 0, i3));
        this.mCommentCountXPos = i4;
        int i5 = i4 - (sCommentImage.getWidth() + sInfoInnerPadding);
        this.mCommentIconXPos = i5;
        n = i5 - sInfoRightMargin;
      }
      if (this.mPlusOneCount != null)
      {
        this.mPlusOneIconYPos = (m + (sInfoHeight - sPlusOneImage.getHeight()) / 2);
        float f1 = sPlusOneCountPaint.descent() - sPlusOneCountPaint.ascent();
        this.mPlusOneCountYPos = (m + (sInfoHeight - f1) / 2.0F - sPlusOneCountPaint.ascent());
        int i1 = this.mPlusOneCount.length();
        int i2 = (int)(n - sPlusOneCountPaint.measureText(this.mPlusOneCount, 0, i1));
        this.mPlusOneCountXPos = i2;
        this.mPlusOneIconXPos = (i2 - (sPlusOneImage.getWidth() + sInfoInnerPadding));
      }
      label353: if (k <= sNoImageImage.getHeight())
        break label474;
    }
    while (true)
    {
      this.mShowNoImage = bool;
      setMeasuredDimension(j, k);
      return;
      j = 0;
      if (i != -2147483648)
        break;
      j = Math.min(0, View.MeasureSpec.getSize(paramInt1));
      break;
      label402: if (j > this.mImageWidth)
        this.mPreferredImageWidth = this.mImageWidth;
      for (k = this.mImageHeight; ; k = j * this.mImageHeight / this.mImageWidth)
      {
        if (k <= this.mMaxHeight)
          break label464;
        k = this.mMaxHeight;
        break;
        this.mPreferredImageWidth = j;
      }
      label464: break label48;
      label466: this.mShowInfo = false;
      break label353;
      label474: bool = false;
    }
  }

  public void setCommentCount(int paramInt)
  {
    Object localObject;
    if (paramInt == 0)
      localObject = null;
    while (true)
    {
      if (!TextUtils.equals(this.mCommentCount, (CharSequence)localObject))
      {
        this.mCommentCount = ((String)localObject);
        requestLayout();
      }
      return;
      if (paramInt > 99)
        localObject = getResources().getString(R.string.ninety_nine_plus);
      else
        localObject = Integer.toString(paramInt);
    }
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      setScaleType(ImageView.ScaleType.MATRIX);
      setImageMatrix(null);
      this.mImageLoaded = true;
    }
    while (true)
    {
      super.setImageBitmap(paramBitmap);
      return;
      if (this.mShowNoImage)
      {
        setScaleType(ImageView.ScaleType.CENTER);
        paramBitmap = sNoImageImage;
      }
    }
  }

  public void setMaxHeight(int paramInt)
  {
    super.setMaxHeight(paramInt);
    this.mMaxHeight = paramInt;
  }

  public void setPhoto(String paramString, int paramInt1, int paramInt2)
  {
    if (!TextUtils.equals(paramString, this.mImageUrl))
    {
      setImageDrawable(null);
      this.mImageUrl = paramString;
      this.mImageWidth = paramInt1;
      this.mImageHeight = paramInt2;
      this.mImageRequested = false;
      this.mImageLoaded = false;
      requestLayout();
    }
  }

  public void setPlusOneCount(long paramLong)
  {
    Object localObject;
    if (paramLong == 0L)
      localObject = null;
    while (true)
    {
      if (!TextUtils.equals(this.mPlusOneCount, (CharSequence)localObject))
      {
        this.mPlusOneCount = ((String)localObject);
        requestLayout();
      }
      return;
      if (paramLong > 99L)
        localObject = getResources().getString(R.string.ninety_nine_plus);
      else
        localObject = Long.toString(paramLong);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventPhotoView
 * JD-Core Version:    0.6.2
 */