package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;

final class ClickableUserImage
  implements ImageCache.ImageConsumer, ClickableItem
{
  private static Paint sImageSelectedPaint;
  private final ImageCache mAvatarCache;
  private boolean mAvatarInvalidated;
  private boolean mAvatarLoaded;
  private AvatarRequest mAvatarRequest;
  private Bitmap mBitmap;
  private final UserImageClickListener mClickListener;
  private boolean mClicked;
  private CharSequence mContentDescription;
  private final Rect mContentRect;
  private final String mUserId;
  private final String mUserName;
  private final View mView;

  public ClickableUserImage(View paramView, String paramString1, String paramString2, String paramString3, UserImageClickListener paramUserImageClickListener)
  {
    this(paramView, paramString1, null, paramString3, paramUserImageClickListener, 1);
  }

  public ClickableUserImage(View paramView, String paramString1, String paramString2, String paramString3, UserImageClickListener paramUserImageClickListener, int paramInt)
  {
    this.mView = paramView;
    Context localContext = paramView.getContext();
    this.mContentRect = new Rect();
    this.mClickListener = paramUserImageClickListener;
    this.mUserId = paramString1;
    this.mUserName = paramString3;
    this.mContentDescription = paramString3;
    this.mAvatarCache = ImageCache.getInstance(localContext);
    this.mAvatarRequest = new AvatarRequest(this.mUserId, paramString2, paramInt, true);
    this.mAvatarInvalidated = true;
    if (sImageSelectedPaint == null)
    {
      Paint localPaint = new Paint();
      sImageSelectedPaint = localPaint;
      localPaint.setAntiAlias(true);
      sImageSelectedPaint.setStrokeWidth(4.0F);
      sImageSelectedPaint.setColor(localContext.getApplicationContext().getResources().getColor(R.color.image_selected_stroke));
      sImageSelectedPaint.setStyle(Paint.Style.STROKE);
    }
  }

  public final void drawSelectionRect(Canvas paramCanvas)
  {
    paramCanvas.drawCircle(this.mContentRect.centerX(), this.mContentRect.centerY(), this.mContentRect.width() / 2, sImageSelectedPaint);
  }

  public final Bitmap getBitmap()
  {
    if (this.mAvatarInvalidated)
    {
      this.mAvatarInvalidated = false;
      this.mAvatarCache.refreshImage(this, this.mAvatarRequest);
    }
    return this.mBitmap;
  }

  public final CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }

  public final Rect getRect()
  {
    return this.mContentRect;
  }

  public final boolean handleEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 1;
    if (paramInt3 == 3)
      this.mClicked = false;
    while (true)
    {
      return i;
      if (!this.mContentRect.contains(paramInt1, paramInt2))
      {
        if (paramInt3 == i)
          this.mClicked = false;
        i = 0;
      }
      else
      {
        switch (paramInt3)
        {
        default:
          break;
        case 0:
          this.mClicked = i;
          break;
        case 1:
          if ((this.mClicked) && (this.mClickListener != null))
            this.mClickListener.onUserImageClick(this.mUserId, this.mUserName);
          this.mClicked = false;
        }
      }
    }
  }

  public final boolean isClicked()
  {
    return this.mClicked;
  }

  public final void onAvatarChanged(String paramString)
  {
    if (TextUtils.equals(paramString, this.mUserId))
    {
      this.mAvatarInvalidated = true;
      this.mAvatarLoaded = false;
      this.mView.invalidate();
    }
  }

  public final void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (!paramBoolean);
    for (boolean bool = true; ; bool = false)
    {
      this.mAvatarLoaded = bool;
      this.mBitmap = paramBitmap;
      this.mView.invalidate();
      return;
    }
  }

  public final void setRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mContentRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static abstract interface UserImageClickListener
  {
    public abstract void onUserImageClick(String paramString1, String paramString2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableUserImage
 * JD-Core Version:    0.6.2
 */