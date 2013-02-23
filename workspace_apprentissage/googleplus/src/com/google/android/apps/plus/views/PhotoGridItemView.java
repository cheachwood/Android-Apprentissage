package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class PhotoGridItemView extends View
  implements Drawable.Callback
{
  private static int sCountLeftPadding;
  private static TextPaint sCountPaint;
  private static int sCountRightPadding;
  private static int sInfoHeight;
  private static Paint sInfoPaint;
  private static boolean sInitialized;
  private static int sItemHeight;
  private static int sItemInnerPadding;
  private static Bitmap sLoadingImage;
  private static int sNameLeftPadding;
  private static TextPaint sNamePaint;
  private static int sNameRightPadding;
  private CharSequence mAlbumCountText;
  private int mAlbumCountWidth;
  private CharSequence mAlbumNameText;
  private Handler mHandler = new Handler();
  private final Rect[] mLoadingRect = new Rect[1];
  private int mMaxCoverCount;
  private OnMeasuredListener mMeasuredListener;
  private final Bitmap[] mPhoto = new Bitmap[1];
  private DrawableInfo[] mPhotoDrawables;
  private final Rect[] mPhotoRect = new Rect[1];
  private final Size[] mPhotoSize = new Size[1];

  public PhotoGridItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PhotoGridItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sInitialized)
    {
      Resources localResources = getContext().getApplicationContext().getResources();
      sLoadingImage = ImageUtils.decodeResource(localResources, R.drawable.ic_missing_photo);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setColor(localResources.getColor(R.color.photo_home_album_name_color));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.photo_home_album_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.photo_home_album_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sCountPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sCountPaint.setColor(localResources.getColor(R.color.photo_home_album_count_color));
      sCountPaint.setTextSize(localResources.getDimension(R.dimen.photo_home_album_count_text_size));
      TextPaintUtils.registerTextPaint(sCountPaint, R.dimen.photo_home_album_count_text_size);
      Paint localPaint = new Paint();
      sInfoPaint = localPaint;
      localPaint.setAntiAlias(true);
      sInfoPaint.setColor(localResources.getColor(R.color.photo_home_info_background_color));
      sNameLeftPadding = localResources.getDimensionPixelSize(R.dimen.photo_home_album_name_left_padding);
      sNameRightPadding = localResources.getDimensionPixelSize(R.dimen.photo_home_album_name_right_padding);
      sCountLeftPadding = localResources.getDimensionPixelSize(R.dimen.photo_home_album_count_left_padding);
      sCountRightPadding = localResources.getDimensionPixelSize(R.dimen.photo_home_album_count_right_padding);
      sItemInnerPadding = localResources.getDimensionPixelSize(R.dimen.photo_home_item_inner_padding);
      sInfoHeight = localResources.getDimensionPixelSize(R.dimen.photo_home_info_height);
      sItemHeight = localResources.getDimensionPixelSize(R.dimen.photo_home_item_height);
      sInitialized = true;
    }
  }

  private void createPhotoRect()
  {
    int i = getMeasuredWidth();
    for (int j = 0; j <= 0; j++)
    {
      this.mPhotoSize[0] = null;
      this.mPhotoRect[0] = null;
      this.mLoadingRect[0] = null;
    }
    if (i == 0);
    while (true)
    {
      return;
      int k = sLoadingImage.getHeight();
      int m = sLoadingImage.getWidth();
      switch (this.mMaxCoverCount)
      {
      default:
        break;
      case 1:
        Size[] arrayOfSize11 = this.mPhotoSize;
        Size localSize7 = new Size(i, sItemHeight);
        arrayOfSize11[0] = localSize7;
        Rect[] arrayOfRect22 = this.mPhotoRect;
        Rect localRect21 = new Rect(0, 0, i, sItemHeight);
        arrayOfRect22[0] = localRect21;
        int i28 = (sItemHeight - k) / 2;
        int i29 = (i - m) / 2;
        Rect[] arrayOfRect23 = this.mLoadingRect;
        Rect localRect22 = new Rect(i29, i28, i29 + m, i28 + k);
        arrayOfRect23[0] = localRect22;
        break;
      case 2:
        int i22 = i * 667 / 1000 - sItemInnerPadding;
        int i23 = i - i22 - sItemInnerPadding;
        int i24 = i22 + sItemInnerPadding;
        Size[] arrayOfSize9 = this.mPhotoSize;
        Size localSize5 = new Size(i22, sItemHeight);
        arrayOfSize9[0] = localSize5;
        Size[] arrayOfSize10 = this.mPhotoSize;
        Size localSize6 = new Size(i23, sItemHeight);
        arrayOfSize10[1] = localSize6;
        Rect[] arrayOfRect18 = this.mPhotoRect;
        Rect localRect17 = new Rect(0, 0, i22, sItemHeight);
        arrayOfRect18[0] = localRect17;
        Rect[] arrayOfRect19 = this.mPhotoRect;
        Rect localRect18 = new Rect(i24, 0, i, sItemHeight);
        arrayOfRect19[1] = localRect18;
        int i25 = (sItemHeight - k) / 2;
        int i26 = (i22 - m) / 2;
        int i27 = i24 + (i23 - m) / 2;
        Rect[] arrayOfRect20 = this.mLoadingRect;
        Rect localRect19 = new Rect(i26, i25, i26 + m, i25 + k);
        arrayOfRect20[0] = localRect19;
        Rect[] arrayOfRect21 = this.mLoadingRect;
        Rect localRect20 = new Rect(i27, i25, i27 + m, i25 + k);
        arrayOfRect21[1] = localRect20;
        break;
      case 3:
      case 4:
        int i12 = i * 667 / 1000 - sItemInnerPadding;
        int i13 = i - i12 - sItemInnerPadding;
        int i14 = sItemHeight / 2 - sItemInnerPadding;
        int i15 = i12 + sItemInnerPadding;
        int i16 = i14 + sItemInnerPadding;
        Size[] arrayOfSize6 = this.mPhotoSize;
        Size localSize3 = new Size(i12, sItemHeight);
        arrayOfSize6[0] = localSize3;
        Size[] arrayOfSize7 = this.mPhotoSize;
        Size[] arrayOfSize8 = this.mPhotoSize;
        Size localSize4 = new Size(i13, i14);
        arrayOfSize8[2] = localSize4;
        arrayOfSize7[1] = localSize4;
        Rect[] arrayOfRect12 = this.mPhotoRect;
        Rect localRect11 = new Rect(0, 0, i12, sItemHeight);
        arrayOfRect12[0] = localRect11;
        Rect[] arrayOfRect13 = this.mPhotoRect;
        Rect localRect12 = new Rect(i15, 0, i, i14);
        arrayOfRect13[1] = localRect12;
        Rect[] arrayOfRect14 = this.mPhotoRect;
        Rect localRect13 = new Rect(i15, i16, i, sItemHeight);
        arrayOfRect14[2] = localRect13;
        int i17 = (sItemHeight - k) / 2;
        int i18 = (i14 - k) / 2;
        int i19 = i18 + i14 + sItemInnerPadding;
        int i20 = (i12 - m) / 2;
        int i21 = i15 + (i13 - m) / 2;
        Rect[] arrayOfRect15 = this.mLoadingRect;
        Rect localRect14 = new Rect(i20, i17, i20 + m, i17 + k);
        arrayOfRect15[0] = localRect14;
        Rect[] arrayOfRect16 = this.mLoadingRect;
        Rect localRect15 = new Rect(i21, i18, i21 + m, i18 + k);
        arrayOfRect16[1] = localRect15;
        Rect[] arrayOfRect17 = this.mLoadingRect;
        Rect localRect16 = new Rect(i21, i19, i21 + m, i19 + k);
        arrayOfRect17[2] = localRect16;
        break;
      case 5:
        int n = i * 667 / 1000 - sItemInnerPadding;
        int i1 = (i - n - sItemInnerPadding - sItemInnerPadding) / 2;
        int i2 = sItemHeight / 2 - sItemInnerPadding;
        int i3 = n + sItemInnerPadding;
        int i4 = i3 + i1 + sItemInnerPadding;
        int i5 = i2 + sItemInnerPadding;
        Size[] arrayOfSize1 = this.mPhotoSize;
        Size localSize1 = new Size(n, sItemHeight);
        arrayOfSize1[0] = localSize1;
        Size[] arrayOfSize2 = this.mPhotoSize;
        Size[] arrayOfSize3 = this.mPhotoSize;
        Size[] arrayOfSize4 = this.mPhotoSize;
        Size[] arrayOfSize5 = this.mPhotoSize;
        Size localSize2 = new Size(i1, i2);
        arrayOfSize5[4] = localSize2;
        arrayOfSize4[3] = localSize2;
        arrayOfSize3[2] = localSize2;
        arrayOfSize2[1] = localSize2;
        Rect[] arrayOfRect1 = this.mPhotoRect;
        Rect localRect1 = new Rect(0, 0, n, sItemHeight);
        arrayOfRect1[0] = localRect1;
        Rect[] arrayOfRect2 = this.mPhotoRect;
        Rect localRect2 = new Rect(i3, 0, i3 + i1, i2);
        arrayOfRect2[1] = localRect2;
        Rect[] arrayOfRect3 = this.mPhotoRect;
        Rect localRect3 = new Rect(i4, 0, i, i2);
        arrayOfRect3[2] = localRect3;
        Rect[] arrayOfRect4 = this.mPhotoRect;
        Rect localRect4 = new Rect(i3, i5, i3 + i1, sItemHeight);
        arrayOfRect4[3] = localRect4;
        Rect[] arrayOfRect5 = this.mPhotoRect;
        Rect localRect5 = new Rect(i4, i5, i, sItemHeight);
        arrayOfRect5[4] = localRect5;
        int i6 = (sItemHeight - k) / 2;
        int i7 = (i2 - k) / 2;
        int i8 = i7 + i2 + sItemInnerPadding;
        int i9 = (n - m) / 2;
        int i10 = i3 + (i1 - m) / 2;
        int i11 = i10 + i1 + sItemInnerPadding;
        Rect[] arrayOfRect6 = this.mLoadingRect;
        Rect localRect6 = new Rect(i9, i6, i9 + m, i6 + k);
        arrayOfRect6[0] = localRect6;
        Rect[] arrayOfRect7 = this.mLoadingRect;
        Rect[] arrayOfRect8 = this.mLoadingRect;
        Rect localRect7 = new Rect(i10, i7, i10 + m, i7 + k);
        arrayOfRect8[2] = localRect7;
        arrayOfRect7[1] = localRect7;
        Rect[] arrayOfRect9 = this.mLoadingRect;
        Rect localRect8 = new Rect(i11, i7, i11 + m, i7 + k);
        arrayOfRect9[2] = localRect8;
        Rect[] arrayOfRect10 = this.mLoadingRect;
        Rect localRect9 = new Rect(i10, i8, i10 + m, i8 + k);
        arrayOfRect10[3] = localRect9;
        Rect[] arrayOfRect11 = this.mLoadingRect;
        Rect localRect10 = new Rect(i11, i8, i11 + m, i8 + k);
        arrayOfRect11[4] = localRect10;
      }
    }
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = getMeasuredWidth();
    if (this.mPhotoDrawables != null)
      for (DrawableInfo localDrawableInfo : this.mPhotoDrawables)
        if (localDrawableInfo != null)
        {
          localDrawableInfo.mDrawable.draw(paramCanvas);
          localDrawableInfo.mDrawn = Boolean.valueOf(true);
        }
    int j = sItemHeight - sInfoHeight;
    paramCanvas.drawRect(0.0F, j, i, sItemHeight, sInfoPaint);
    float f1 = sNamePaint.descent() - sNamePaint.ascent();
    int k = (int)((int)(j + (sInfoHeight - f1) / 2.0F) - sNamePaint.ascent());
    if (this.mAlbumNameText != null)
    {
      int m = i - this.mAlbumCountWidth - sNameLeftPadding - sNameRightPadding - sCountLeftPadding - sCountRightPadding;
      CharSequence localCharSequence = TextUtils.ellipsize(this.mAlbumNameText, sNamePaint, m, TextUtils.TruncateAt.END);
      float f3 = sNameLeftPadding;
      paramCanvas.drawText(localCharSequence, 0, localCharSequence.length(), f3, k, sNamePaint);
    }
    if (this.mAlbumCountText != null)
    {
      float f2 = i - sCountRightPadding - this.mAlbumCountWidth;
      paramCanvas.drawText(this.mAlbumCountText, 0, this.mAlbumCountText.length(), f2, k, sCountPaint);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(sItemHeight, 1073741824));
    setMeasuredDimension(getMeasuredWidth(), sItemHeight);
    createPhotoRect();
    if (this.mMeasuredListener != null);
  }

  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    this.mHandler.postAtTime(paramRunnable, paramRunnable, paramLong);
  }

  public void setAlbumCount(Integer paramInteger)
  {
    if (paramInteger == null)
    {
      this.mAlbumCountText = null;
      this.mAlbumCountWidth = 0;
      invalidate();
      return;
    }
    Resources localResources = getResources();
    if (paramInteger.intValue() == 0);
    for (this.mAlbumCountText = localResources.getString(R.string.album_photo_count_zero); ; this.mAlbumCountText = localResources.getQuantityString(R.plurals.album_photo_count, paramInteger.intValue(), new Object[] { paramInteger }))
    {
      this.mAlbumCountWidth = ((int)sCountPaint.measureText(this.mAlbumCountText, 0, this.mAlbumCountText.length()));
      break;
    }
  }

  public void setAlbumName(CharSequence paramCharSequence)
  {
    this.mAlbumNameText = paramCharSequence;
    invalidate();
  }

  public void setCoverCount(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 > 1))
      return;
    if (paramInt2 != 0)
      if (paramInt2 < 10)
        this.mMaxCoverCount = Math.min(paramInt1, 1);
    while (true)
    {
      this.mPhotoDrawables = new DrawableInfo[this.mMaxCoverCount];
      createPhotoRect();
      break;
      if (paramInt2 < 20)
        this.mMaxCoverCount = Math.min(paramInt1, 2);
      else if (paramInt2 < 50)
        this.mMaxCoverCount = Math.min(paramInt1, 3);
      else
        this.mMaxCoverCount = Math.min(paramInt1, 5);
    }
  }

  public void setMediaRefs(MediaRef[] paramArrayOfMediaRef)
  {
    if ((paramArrayOfMediaRef != null) && (paramArrayOfMediaRef.length != 1));
    while (true)
    {
      return;
      setTag(paramArrayOfMediaRef);
    }
  }

  public void setOnMeasureListener(OnMeasuredListener paramOnMeasuredListener)
  {
    this.mMeasuredListener = paramOnMeasuredListener;
  }

  public void setPhoto(MediaRef paramMediaRef, Bitmap paramBitmap)
  {
    MediaRef[] arrayOfMediaRef = (MediaRef[])getTag();
    if (arrayOfMediaRef == null);
    while (true)
    {
      return;
      int i = 0;
      int j = 0;
      int k = arrayOfMediaRef.length;
      int m = 0;
      if (m < k)
      {
        MediaRef localMediaRef = arrayOfMediaRef[m];
        DrawableInfo localDrawableInfo2;
        if ((paramMediaRef == null) || (paramMediaRef.equals(localMediaRef)))
        {
          this.mPhoto[j] = paramBitmap;
          Rect localRect = this.mPhotoRect[j];
          if (localRect != null)
          {
            Bitmap localBitmap = this.mPhoto[j];
            if (((this.mPhotoDrawables[j] != null) && (!this.mPhotoDrawables[j].mLoadingDrawable.booleanValue())) || (localBitmap == null))
              break label284;
            Object localObject = new BitmapDrawable(getResources(), localBitmap);
            ((Drawable)localObject).setBounds(localRect);
            if ((this.mPhotoDrawables[j] != null) && (this.mPhotoDrawables[j].mDrawn.booleanValue()))
            {
              Drawable[] arrayOfDrawable = new Drawable[2];
              arrayOfDrawable[0] = this.mPhotoDrawables[j].mDrawable;
              arrayOfDrawable[1] = localObject;
              localObject = new TransitionDrawable(arrayOfDrawable);
              ((TransitionDrawable)localObject).setCallback(this);
              ((TransitionDrawable)localObject).setCrossFadeEnabled(true);
              ((TransitionDrawable)localObject).startTransition(250);
            }
            DrawableInfo localDrawableInfo1 = new DrawableInfo((byte)0);
            localDrawableInfo1.mDrawable = ((Drawable)localObject);
            localDrawableInfo1.mLoadingDrawable = Boolean.valueOf(false);
            localDrawableInfo2 = localDrawableInfo1;
          }
        }
        while (true)
        {
          if (localDrawableInfo2 != null)
          {
            localDrawableInfo2.mDrawn = Boolean.valueOf(false);
            this.mPhotoDrawables[j] = localDrawableInfo2;
          }
          i = 1;
          j++;
          m++;
          break;
          label284: DrawableInfo localDrawableInfo3 = this.mPhotoDrawables[j];
          localDrawableInfo2 = null;
          if (localDrawableInfo3 == null)
          {
            BitmapDrawable localBitmapDrawable = new BitmapDrawable(getResources(), sLoadingImage);
            localBitmapDrawable.setBounds(this.mLoadingRect[j]);
            localDrawableInfo2 = new DrawableInfo((byte)0);
            localDrawableInfo2.mDrawable = localBitmapDrawable;
            localDrawableInfo2.mLoadingDrawable = Boolean.valueOf(true);
          }
        }
      }
      if (i != 0)
        invalidate();
    }
  }

  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    this.mHandler.removeCallbacks(paramRunnable, paramDrawable);
  }

  private static final class DrawableInfo
  {
    public Drawable mDrawable;
    public Boolean mDrawn;
    public Boolean mLoadingDrawable;
  }

  public static abstract interface OnMeasuredListener
  {
  }

  public static final class Size
  {
    public final int height;
    public final int width;

    Size(int paramInt1, int paramInt2)
    {
      this.width = paramInt1;
      this.height = paramInt2;
    }

    public final boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof Size;
      boolean bool2 = false;
      if (bool1)
      {
        Size localSize = (Size)paramObject;
        int i = localSize.width;
        int j = this.width;
        bool2 = false;
        if (i == j)
        {
          int k = localSize.height;
          int m = this.height;
          bool2 = false;
          if (k == m)
            bool2 = true;
        }
      }
      return bool2;
    }

    public final int hashCode()
    {
      return this.width << 16 | 0xFFFF & this.height;
    }

    public final String toString()
    {
      return "size[" + this.width + ", " + this.height + "]";
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoGridItemView
 * JD-Core Version:    0.6.2
 */