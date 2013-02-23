package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;

public class AvatarView extends View
  implements ImageCache.ImageConsumer, ImageCache.OnAvatarChangeListener
{
  private static RectF sBoundsRect = new RectF();
  private static Paint sImageSelectedPaint;
  private boolean mAllowNonSquare;
  private Bitmap mAvatarBitmap;
  private final ImageCache mAvatarCache;
  private boolean mAvatarInvalidated;
  private AvatarRequest mAvatarRequest;
  private int mAvatarSize;
  private boolean mDimmed;
  private String mGaiaId;
  private Paint mResizePaint;
  private Rect mResizeRectDest;
  private Rect mResizeRectSrc;
  private boolean mResizeRequired;
  private boolean mRound;
  private boolean mScale;
  private Drawable mSelector;
  private int mSizeInPixels;

  public AvatarView(Context paramContext)
  {
    this(paramContext, null);
  }

  public AvatarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public AvatarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = paramContext.getResources();
    if (sImageSelectedPaint == null)
    {
      Paint localPaint = new Paint();
      sImageSelectedPaint = localPaint;
      localPaint.setAntiAlias(i);
      sImageSelectedPaint.setStrokeWidth(4.0F);
      sImageSelectedPaint.setColor(localResources.getColor(R.color.image_selected_stroke));
      sImageSelectedPaint.setStyle(Paint.Style.STROKE);
    }
    this.mSelector = localResources.getDrawable(R.drawable.stream_list_selector);
    this.mSelector.setCallback(this);
    this.mAvatarCache = ImageCache.getInstance(paramContext);
    String str1;
    if (paramAttributeSet != null)
    {
      str1 = paramAttributeSet.getAttributeValue(null, "size");
      if (str1 == null)
        throw new RuntimeException("Missing 'size' attribute");
      String str2 = paramAttributeSet.getAttributeValue(null, "round");
      if (str2 != null)
        this.mRound = Boolean.parseBoolean(str2);
      String str3 = paramAttributeSet.getAttributeValue(null, "scale");
      if (str3 != null)
        this.mScale = Boolean.parseBoolean(str3);
      if ("tiny".equals(str1))
      {
        i = 0;
        this.mAvatarSize = i;
        this.mAllowNonSquare = paramAttributeSet.getAttributeBooleanValue(null, "allowNonSquare", false);
      }
    }
    while (true)
    {
      setAvatarSize(this.mAvatarSize);
      return;
      if ("small".equals(str1))
        break;
      if ("medium".equals(str1))
      {
        int j = 2;
        break;
      }
      throw new IllegalArgumentException("Invalid avatar size: " + str1);
      this.mAvatarSize = 2;
    }
  }

  protected void drawableStateChanged()
  {
    this.mSelector.setState(getDrawableState());
    invalidate();
    super.drawableStateChanged();
  }

  public final String getGaiaId()
  {
    return this.mGaiaId;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerAvatarChangeListener(this);
  }

  public void onAvatarChanged(String paramString)
  {
    if ((paramString != null) && (paramString.equals(String.valueOf(this.mGaiaId))) && (this.mAvatarRequest != null))
    {
      this.mAvatarInvalidated = true;
      invalidate();
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ImageCache.unregisterAvatarChangeListener(this);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if ((this.mAvatarInvalidated) && (this.mAvatarRequest != null))
    {
      this.mAvatarInvalidated = false;
      this.mAvatarCache.refreshImage(this, this.mAvatarRequest);
    }
    if (this.mAvatarBitmap != null)
    {
      if (this.mDimmed)
      {
        sBoundsRect.set(0.0F, 0.0F, getWidth(), getHeight());
        paramCanvas.saveLayerAlpha(sBoundsRect, 105, 31);
      }
      if (!this.mResizeRequired)
        break label164;
      paramCanvas.drawBitmap(this.mAvatarBitmap, this.mResizeRectSrc, this.mResizeRectDest, this.mResizePaint);
      if (this.mDimmed)
        paramCanvas.restore();
    }
    if (((isPressed()) || (isFocused())) && (!this.mDimmed))
    {
      if (!this.mRound)
        break label178;
      int i = getWidth() / 2;
      paramCanvas.drawCircle(i, i, i - 2, sImageSelectedPaint);
    }
    while (true)
    {
      return;
      label164: paramCanvas.drawBitmap(this.mAvatarBitmap, 0.0F, 0.0F, null);
      break;
      label178: this.mSelector.draw(paramCanvas);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (!this.mRound)
      this.mSelector.setBounds(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    if (this.mAvatarBitmap == null)
    {
      if (this.mAvatarRequest == null)
        break label62;
      this.mAvatarCache.loadImage(this, this.mAvatarRequest);
    }
    while (true)
    {
      return;
      label62: setBitmap(null, true);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = this.mSizeInPixels;
    int j = View.MeasureSpec.getMode(paramInt1);
    int k;
    int m;
    label70: boolean bool;
    if (j == 1073741824)
    {
      i = View.MeasureSpec.getSize(paramInt1);
      k = View.MeasureSpec.getMode(paramInt2);
      if (!this.mAllowNonSquare)
        break label207;
      m = this.mSizeInPixels;
      if ((k == 1073741824) || (k == -2147483648))
        m = Math.min(m, View.MeasureSpec.getSize(paramInt2));
      if (i == this.mSizeInPixels)
        break label241;
      bool = true;
      label81: this.mResizeRequired = bool;
      if (this.mResizeRequired)
      {
        if (this.mResizePaint == null)
        {
          this.mResizePaint = new Paint(2);
          this.mResizeRectDest = new Rect();
        }
        this.mResizeRectDest.set(0, 0, i, m);
        if (this.mSizeInPixels <= i)
          break label307;
        this.mResizeRectSrc = new Rect();
        if (!this.mScale)
          break label247;
        this.mResizeRectSrc.set(0, 0, this.mSizeInPixels, this.mSizeInPixels);
      }
    }
    while (true)
    {
      setMeasuredDimension(i, m);
      return;
      if (j != -2147483648)
        break;
      i = Math.min(i, View.MeasureSpec.getSize(paramInt1));
      break;
      label207: if (k == 1073741824)
      {
        m = Math.min(i, View.MeasureSpec.getSize(paramInt2));
        break label70;
      }
      m = Math.min(i, this.mSizeInPixels);
      break label70;
      label241: bool = false;
      break label81;
      label247: int n = (this.mSizeInPixels - i) / 2;
      int i1 = (i + this.mSizeInPixels) / 2;
      int i2 = (this.mSizeInPixels - m) / 2;
      int i3 = (m + this.mSizeInPixels) / 2;
      this.mResizeRectSrc.set(n, i2, i1, i3);
      continue;
      label307: this.mResizeRectSrc = null;
    }
  }

  public void setAvatarSize(int paramInt)
  {
    this.mAvatarSize = paramInt;
    switch (this.mAvatarSize)
    {
    default:
      this.mSizeInPixels = EsAvatarData.getMediumAvatarSize(getContext());
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      this.mSizeInPixels = EsAvatarData.getTinyAvatarSize(getContext());
      continue;
      this.mSizeInPixels = EsAvatarData.getSmallAvatarSize(getContext());
    }
  }

  public void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (paramBitmap == null)
      switch (this.mAvatarSize)
      {
      default:
      case 0:
      case 1:
      case 2:
      }
    while (true)
    {
      invalidate();
      return;
      this.mAvatarBitmap = EsAvatarData.getTinyDefaultAvatar(getContext(), this.mRound);
      continue;
      this.mAvatarBitmap = EsAvatarData.getSmallDefaultAvatar(getContext(), this.mRound);
      continue;
      this.mAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), this.mRound);
      continue;
      this.mAvatarBitmap = paramBitmap;
    }
  }

  public void setDimmed(boolean paramBoolean)
  {
    this.mDimmed = paramBoolean;
    invalidate();
  }

  public void setGaiaId(String paramString)
  {
    if (!TextUtils.equals(this.mGaiaId, paramString))
    {
      this.mGaiaId = paramString;
      if (paramString == null)
        break label50;
    }
    label50: for (this.mAvatarRequest = new AvatarRequest(paramString, this.mAvatarSize, this.mRound); ; this.mAvatarRequest = null)
    {
      this.mAvatarBitmap = null;
      requestLayout();
      return;
    }
  }

  public void setGaiaIdAndAvatarUrl(String paramString1, String paramString2)
  {
    if (!TextUtils.equals(this.mGaiaId, paramString1))
    {
      this.mGaiaId = paramString1;
      if (paramString1 == null)
        break label51;
    }
    label51: for (this.mAvatarRequest = new AvatarRequest(paramString1, paramString2, this.mAvatarSize, this.mRound); ; this.mAvatarRequest = null)
    {
      this.mAvatarBitmap = null;
      requestLayout();
      return;
    }
  }

  public void setRounded(boolean paramBoolean)
  {
    this.mRound = paramBoolean;
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mSelector);
    for (boolean bool = true; ; bool = super.verifyDrawable(paramDrawable))
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.AvatarView
 * JD-Core Version:    0.6.2
 */