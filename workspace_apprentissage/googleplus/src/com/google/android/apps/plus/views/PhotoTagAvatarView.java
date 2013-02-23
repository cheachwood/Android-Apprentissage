package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;

public class PhotoTagAvatarView extends CompoundButton
  implements ImageCache.ImageConsumer, ImageCache.OnAvatarChangeListener
{
  private static Integer sAvatarHeight;
  private static Integer sAvatarWidth;
  private Drawable mAvatar;
  private final ImageCache mAvatarCache;
  private boolean mAvatarInvalidated;
  private AvatarRequest mAvatarRequest;
  private Rect mDrawRect = new Rect();
  private String mSubjectGaiaId;
  private int mTagHeight;
  private int mTagLeft;
  private int mTagTop;
  private int mTagWidth;

  public PhotoTagAvatarView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PhotoTagAvatarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mAvatarCache = ImageCache.getInstance(paramContext);
    if (sAvatarWidth == null)
    {
      Resources localResources = paramContext.getApplicationContext().getResources();
      sAvatarWidth = Integer.valueOf(localResources.getDimensionPixelSize(R.dimen.photo_tag_scroller_avatar_width));
      sAvatarHeight = Integer.valueOf(localResources.getDimensionPixelSize(R.dimen.photo_tag_scroller_avatar_height));
    }
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    if (this.mAvatar != null)
    {
      int[] arrayOfInt = getDrawableState();
      this.mAvatar.setState(arrayOfInt);
      invalidate();
    }
  }

  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    if (this.mAvatar != null)
      this.mAvatar.jumpToCurrentState();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerAvatarChangeListener(this);
  }

  public void onAvatarChanged(String paramString)
  {
    if ((paramString != null) && (paramString.equals(String.valueOf(this.mSubjectGaiaId))) && (this.mAvatarRequest != null))
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
    super.onDraw(paramCanvas);
    if ((this.mAvatar instanceof BitmapDrawable))
    {
      if ((this.mAvatarInvalidated) && (this.mAvatarRequest != null))
      {
        this.mAvatarInvalidated = false;
        this.mAvatarCache.refreshImage(this, this.mAvatarRequest);
      }
      paramCanvas.drawBitmap(((BitmapDrawable)this.mAvatar).getBitmap(), null, this.mDrawRect, null);
    }
    while (true)
    {
      return;
      if (this.mAvatar != null)
      {
        int i = getPaddingTop();
        int j = getPaddingLeft() + this.mTagLeft;
        int k = i + this.mTagTop;
        int m = j + sAvatarWidth.intValue();
        int n = k + sAvatarHeight.intValue();
        this.mAvatar.setBounds(j, k, m, n);
        this.mAvatar.draw(paramCanvas);
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    getWidth();
    getPaddingRight();
    int i;
    int j;
    switch (0x70 & getGravity())
    {
    default:
      i = getPaddingTop();
      switch (0x7 & getGravity())
      {
      default:
        j = getPaddingLeft();
        label115: this.mTagLeft = j;
        this.mTagTop = i;
        int k = this.mTagLeft + getPaddingLeft();
        int m = this.mTagTop + getPaddingTop();
        this.mDrawRect.set(k, m, k + sAvatarWidth.intValue(), m + sAvatarHeight.intValue());
        if (this.mAvatar == null)
        {
          if (this.mAvatarRequest == null)
            break label309;
          this.mAvatarCache.loadImage(this, this.mAvatarRequest);
        }
        break;
      case 1:
      case 5:
      }
      break;
    case 16:
    case 80:
    }
    while (true)
    {
      return;
      i = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - this.mTagHeight / 2;
      break;
      i = getHeight() - getPaddingBottom() - this.mTagHeight;
      break;
      j = (getPaddingLeft() + getWidth() - getPaddingRight()) / 2 - this.mTagWidth / 2;
      break label115;
      j = getWidth() - getPaddingRight() - this.mTagWidth;
      break label115;
      label309: setBitmap(null, true);
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int j;
    int i;
    if (this.mSubjectGaiaId != null)
    {
      int k = sAvatarWidth.intValue();
      int m = sAvatarHeight.intValue();
      int n = getPaddingTop() + getPaddingBottom();
      j = k + (getPaddingLeft() + getPaddingRight());
      i = n + m;
    }
    while (true)
    {
      this.mTagWidth = j;
      this.mTagHeight = i;
      super.onMeasure(paramInt1, paramInt2);
      setMeasuredDimension(Math.max(getMeasuredWidth(), j), Math.max(getMeasuredHeight(), i));
      return;
      i = 0;
      j = 0;
    }
  }

  public void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (paramBitmap == null);
    for (this.mAvatar = new BitmapDrawable(EsAvatarData.getSmallDefaultAvatar(getContext())); ; this.mAvatar = new BitmapDrawable(paramBitmap))
    {
      invalidate();
      return;
    }
  }

  public void setSubjectGaiaId(String paramString)
  {
    if (!TextUtils.equals(this.mSubjectGaiaId, paramString))
    {
      this.mSubjectGaiaId = paramString;
      if (paramString == null)
        break label43;
    }
    label43: for (this.mAvatarRequest = new AvatarRequest(paramString, 1); ; this.mAvatarRequest = null)
    {
      this.mAvatar = null;
      requestLayout();
      return;
    }
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if ((super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mAvatar));
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoTagAvatarView
 * JD-Core Version:    0.6.2
 */